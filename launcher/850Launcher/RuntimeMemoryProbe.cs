using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class ProbeResult
    {
        public readonly Dictionary<string, List<IntPtr>> Candidates =
            new Dictionary<string, List<IntPtr>>(StringComparer.OrdinalIgnoreCase);
        public string Status = "";
        public long BytesScanned;
        public bool CandidateLimitReached;
    }

    internal sealed class RuntimeMemoryProbe : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;
        private const uint MEM_COMMIT = 0x1000;
        private const uint PAGE_NOACCESS = 0x01;
        private const uint PAGE_GUARD = 0x100;
        private const int MaxCandidatesPerField = 100000;
        private const int ChunkSize = 1024 * 1024;

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
        private int _pid;

        public bool Attach(int processId, out string error)
        {
            error = "";
            Detach();

            _handle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, processId);
            if (_handle == IntPtr.Zero)
            {
                error = "OpenProcess 失敗，Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            _pid = processId;
            return true;
        }

        public ProbeResult FirstScan(IDictionary<string, int> fields)
        {
            var result = NewResult(fields);
            if (_handle == IntPtr.Zero)
            {
                result.Status = "尚未連接程序。";
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
            long address = 0x10000;
            const long maxAddress = 0x7FFF0000;

            while (address < maxAddress)
            {
                MEMORY_BASIC_INFORMATION mbi;
                var queried = VirtualQueryEx(_handle, new IntPtr(address), out mbi, mbiSize);
                if (queried == 0) break;

                var regionBase = mbi.BaseAddress.ToInt64();
                var regionSize = (long)mbi.RegionSize.ToUInt32();
                if (regionSize <= 0) break;

                if (mbi.State == MEM_COMMIT &&
                    (mbi.Protect & PAGE_GUARD) == 0 &&
                    (mbi.Protect & PAGE_NOACCESS) == 0)
                {
                    ScanRegion(regionBase, regionSize, byValue, result);
                    if (result.CandidateLimitReached) break;
                }

                var next = regionBase + regionSize;
                if (next <= address) break;
                address = next;
            }

            result.Status = result.CandidateLimitReached
                ? "首次掃描完成，但候選數達安全上限；請先使用較具辨識度的數值。"
                : "首次掃描完成。";
            return result;
        }

        public ProbeResult Refine(
            IDictionary<string, int> currentValues,
            IDictionary<string, List<IntPtr>> previous)
        {
            var result = NewResult(currentValues);
            if (_handle == IntPtr.Zero)
            {
                result.Status = "尚未連接程序。";
                return result;
            }

            var buffer = new byte[4];
            foreach (var field in currentValues)
            {
                List<IntPtr> oldList;
                if (!previous.TryGetValue(field.Key, out oldList)) continue;

                foreach (var candidate in oldList)
                {
                    IntPtr bytesRead;
                    if (!ReadProcessMemory(_handle, candidate, buffer, 4, out bytesRead) ||
                        bytesRead.ToInt64() != 4)
                        continue;

                    if (BitConverter.ToInt32(buffer, 0) == field.Value)
                        result.Candidates[field.Key].Add(candidate);
                }
            }

            result.Status = "再次篩選完成。";
            return result;
        }

        private static ProbeResult NewResult(IDictionary<string, int> fields)
        {
            var result = new ProbeResult();
            foreach (var field in fields)
                result.Candidates[field.Key] = new List<IntPtr>();
            return result;
        }

        private void ScanRegion(
            long regionBase,
            long regionSize,
            Dictionary<int, List<string>> targets,
            ProbeResult result)
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
                                result.CandidateLimitReached = true;
                                return;
                            }
                            list.Add(new IntPtr(regionBase + offset + i));
                        }
                    }
                }

                offset += wanted;
            }
        }

        public void Detach()
        {
            if (_handle != IntPtr.Zero)
            {
                CloseHandle(_handle);
                _handle = IntPtr.Zero;
            }
            _pid = 0;
        }

        public void Dispose()
        {
            Detach();
        }
    }
}
