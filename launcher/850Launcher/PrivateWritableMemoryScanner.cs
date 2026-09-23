using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class PrivateWritableMemoryScanner : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;
        private const uint MEM_COMMIT = 0x1000;
        private const uint MEM_PRIVATE = 0x20000;
        private const uint PAGE_NOACCESS = 0x01;
        private const uint PAGE_READWRITE = 0x04;
        private const uint PAGE_WRITECOPY = 0x08;
        private const uint PAGE_EXECUTE_READWRITE = 0x40;
        private const uint PAGE_EXECUTE_WRITECOPY = 0x80;
        private const uint PAGE_GUARD = 0x100;
        private const int ChunkSize = 1024 * 1024;
        private const int MaxCandidatesPerField = 4096;

        [StructLayout(LayoutKind.Sequential)]
        private struct MEMORY_BASIC_INFORMATION
        {
            public IntPtr BaseAddress;
            public IntPtr AllocationBase;
            public uint AllocationProtect;
            public UIntPtr RegionSize;
            public uint State;
            public uint Protect;
            public uint Type;
        }

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern IntPtr OpenProcess(uint access, bool inheritHandle, int processId);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool CloseHandle(IntPtr handle);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern int VirtualQueryEx(
            IntPtr processHandle,
            IntPtr address,
            out MEMORY_BASIC_INFORMATION buffer,
            uint length);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool ReadProcessMemory(
            IntPtr processHandle,
            IntPtr baseAddress,
            byte[] buffer,
            int size,
            out IntPtr bytesRead);

        private IntPtr _handle = IntPtr.Zero;

        public bool Attach(int processId, out string error)
        {
            error = "";
            DisposeHandle();
            _handle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, processId);
            if (_handle == IntPtr.Zero)
            {
                error = "OpenProcess failed, Win32=" + Marshal.GetLastWin32Error();
                return false;
            }
            return true;
        }

        public ProbeResult Scan(IDictionary<string, int> fields, long minAddress)
        {
            var result = new ProbeResult();
            foreach (var field in fields)
                result.Candidates[field.Key] = new List<IntPtr>();

            if (_handle == IntPtr.Zero)
            {
                result.Status = "not attached";
                return result;
            }

            var byValue = new Dictionary<int, List<string>>();
            foreach (var kv in fields)
            {
                List<string> names;
                if (!byValue.TryGetValue(kv.Value, out names))
                {
                    names = new List<string>();
                    byValue.Add(kv.Value, names);
                }
                names.Add(kv.Key);
            }

            var mbiSize = (uint)Marshal.SizeOf(typeof(MEMORY_BASIC_INFORMATION));
            long address = Math.Max(0x10000L, minAddress);
            const long maxAddress = 0x7FFF0000L;
            var regions = 0;
            var cappedFields = new HashSet<string>(StringComparer.OrdinalIgnoreCase);

            while (address < maxAddress)
            {
                MEMORY_BASIC_INFORMATION mbi;
                var queried = VirtualQueryEx(_handle, new IntPtr(address), out mbi, mbiSize);
                if (queried == 0) break;

                var regionBase = mbi.BaseAddress.ToInt64();
                var regionSize = (long)mbi.RegionSize.ToUInt32();
                if (regionSize <= 0) break;

                if (mbi.State == MEM_COMMIT &&
                    mbi.Type == MEM_PRIVATE &&
                    IsWritable(mbi.Protect) &&
                    (mbi.Protect & PAGE_GUARD) == 0 &&
                    (mbi.Protect & PAGE_NOACCESS) == 0)
                {
                    regions++;
                    ScanRegion(regionBase, regionSize, byValue, result, cappedFields);
                }

                var next = regionBase + regionSize;
                if (next <= address) break;
                address = next;
            }

            result.CandidateLimitReached = cappedFields.Count > 0;
            result.Status = "private writable scan complete; regions=" + regions +
                "; cappedFields=" + cappedFields.Count;
            return result;
        }

        private void ScanRegion(
            long regionBase,
            long regionSize,
            Dictionary<int, List<string>> targets,
            ProbeResult result,
            HashSet<string> cappedFields)
        {
            long offset = 0;
            while (offset < regionSize)
            {
                var remaining = regionSize - offset;
                var wanted = (int)Math.Min((long)ChunkSize, remaining);
                if (wanted < 4) break;

                var buffer = new byte[wanted];
                IntPtr bytesReadPtr;
                var ok = ReadProcessMemory(
                    _handle,
                    new IntPtr(regionBase + offset),
                    buffer,
                    wanted,
                    out bytesReadPtr);

                var bytesRead = ok ? (int)Math.Min((long)wanted, bytesReadPtr.ToInt64()) : 0;
                if (bytesRead >= 4)
                {
                    result.BytesScanned += bytesRead;
                    for (var i = 0; i <= bytesRead - 4; i += 4)
                    {
                        var value = BitConverter.ToInt32(buffer, i);
                        List<string> fields;
                        if (!targets.TryGetValue(value, out fields)) continue;

                        foreach (var field in fields)
                        {
                            var list = result.Candidates[field];
                            if (list.Count >= MaxCandidatesPerField)
                            {
                                cappedFields.Add(field);
                                continue;
                            }
                            list.Add(new IntPtr(regionBase + offset + i));
                        }
                    }
                }

                offset += wanted;
            }
        }

        private static bool IsWritable(uint protect)
        {
            var baseProtect = protect & 0xFF;
            return baseProtect == PAGE_READWRITE ||
                   baseProtect == PAGE_WRITECOPY ||
                   baseProtect == PAGE_EXECUTE_READWRITE ||
                   baseProtect == PAGE_EXECUTE_WRITECOPY;
        }

        private void DisposeHandle()
        {
            if (_handle != IntPtr.Zero)
            {
                CloseHandle(_handle);
                _handle = IntPtr.Zero;
            }
        }

        public void Dispose()
        {
            DisposeHandle();
        }
    }
}
