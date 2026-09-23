using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class BytePatternScanResult
    {
        public readonly List<IntPtr> Addresses =
            new List<IntPtr>();

        public long BytesScanned;
        public bool CandidateLimitReached;
        public string Status = "";
    }

    internal sealed class RuntimeBytePatternScanner : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;

        private const uint MEM_COMMIT = 0x1000;
        private const uint PAGE_NOACCESS = 0x01;
        private const uint PAGE_READWRITE = 0x04;
        private const uint PAGE_WRITECOPY = 0x08;
        private const uint PAGE_GUARD = 0x100;
        private const uint PAGE_EXECUTE_READWRITE = 0x40;
        private const uint PAGE_EXECUTE_WRITECOPY = 0x80;

        private const int ChunkSize = 512 * 1024;
        private const int MaxHits = 4096;

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
        private static extern IntPtr OpenProcess(
            uint access,
            bool inheritHandle,
            int processId);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool CloseHandle(
            IntPtr handle);

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

        public bool Attach(
            int processId,
            out string error)
        {
            error = "";
            Dispose();

            _handle = OpenProcess(
                PROCESS_QUERY_INFORMATION |
                PROCESS_VM_READ,
                false,
                processId);

            if (_handle == IntPtr.Zero)
            {
                error =
                    "OpenProcess 失敗，Win32=" +
                    Marshal.GetLastWin32Error();

                return false;
            }

            return true;
        }

        public BytePatternScanResult ScanWritable(
            byte[] pattern)
        {
            var result = new BytePatternScanResult();

            if (_handle == IntPtr.Zero)
            {
                result.Status = "尚未連接程序。";
                return result;
            }

            if (pattern == null ||
                pattern.Length < 2 ||
                pattern.Length > 256)
            {
                result.Status = "Pattern 長度不合法。";
                return result;
            }

            var mbiSize =
                (uint)Marshal.SizeOf(
                    typeof(MEMORY_BASIC_INFORMATION));

            long address = 0x10000;
            const long maxAddress = 0x7FFF0000;

            while (address < maxAddress)
            {
                MEMORY_BASIC_INFORMATION mbi;

                var queried = VirtualQueryEx(
                    _handle,
                    new IntPtr(address),
                    out mbi,
                    mbiSize);

                if (queried == 0)
                    break;

                var regionBase =
                    mbi.BaseAddress.ToInt64();

                var regionSize =
                    (long)mbi.RegionSize.ToUInt32();

                if (regionSize <= 0)
                    break;

                if (mbi.State == MEM_COMMIT &&
                    (mbi.Protect & PAGE_GUARD) == 0 &&
                    (mbi.Protect & PAGE_NOACCESS) == 0 &&
                    IsWritable(mbi.Protect))
                {
                    ScanRegion(
                        regionBase,
                        regionSize,
                        pattern,
                        result);

                    if (result.CandidateLimitReached)
                        break;
                }

                var next =
                    regionBase + regionSize;

                if (next <= address)
                    break;

                address = next;
            }

            result.Status =
                result.CandidateLimitReached
                    ? "Pattern 掃描完成，但命中達安全上限。"
                    : "Pattern 掃描完成。";

            return result;
        }

        private void ScanRegion(
            long regionBase,
            long regionSize,
            byte[] pattern,
            BytePatternScanResult result)
        {
            long offset = 0;
            var overlap =
                pattern.Length - 1;

            while (offset < regionSize)
            {
                var remaining =
                    regionSize - offset;

                var wanted =
                    (int)Math.Min(
                        (long)ChunkSize,
                        remaining);

                if (wanted <= 0)
                    break;

                var buffer =
                    new byte[wanted];

                IntPtr bytesReadPtr;

                var ok = ReadProcessMemory(
                    _handle,
                    new IntPtr(
                        regionBase + offset),
                    buffer,
                    wanted,
                    out bytesReadPtr);

                var bytesRead =
                    ok
                        ? (int)Math.Min(
                            (long)wanted,
                            bytesReadPtr.ToInt64())
                        : 0;

                if (bytesRead >= pattern.Length)
                {
                    result.BytesScanned +=
                        bytesRead;

                    FindPattern(
                        buffer,
                        bytesRead,
                        pattern,
                        regionBase + offset,
                        result);

                    if (result.CandidateLimitReached)
                        return;
                }

                if (wanted == remaining)
                    break;

                var advance =
                    wanted - overlap;

                if (advance <= 0)
                    break;

                offset += advance;
            }
        }

        private static void FindPattern(
            byte[] buffer,
            int bytesRead,
            byte[] pattern,
            long baseAddress,
            BytePatternScanResult result)
        {
            var last =
                bytesRead - pattern.Length;

            for (var i = 0;
                 i <= last;
                 i++)
            {
                if (buffer[i] != pattern[0])
                    continue;

                var matched = true;

                for (var j = 1;
                     j < pattern.Length;
                     j++)
                {
                    if (buffer[i + j] !=
                        pattern[j])
                    {
                        matched = false;
                        break;
                    }
                }

                if (!matched)
                    continue;

                if (result.Addresses.Count >=
                    MaxHits)
                {
                    result.CandidateLimitReached = true;
                    return;
                }

                result.Addresses.Add(
                    new IntPtr(
                        baseAddress + i));
            }
        }

        private static bool IsWritable(
            uint protect)
        {
            return (protect & PAGE_READWRITE) != 0 ||
                   (protect & PAGE_WRITECOPY) != 0 ||
                   (protect & PAGE_EXECUTE_READWRITE) != 0 ||
                   (protect & PAGE_EXECUTE_WRITECOPY) != 0;
        }

        public void Dispose()
        {
            if (_handle != IntPtr.Zero)
            {
                CloseHandle(_handle);
                _handle = IntPtr.Zero;
            }
        }
    }
}
