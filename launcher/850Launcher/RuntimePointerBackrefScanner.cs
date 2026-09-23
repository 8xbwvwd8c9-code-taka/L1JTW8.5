using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class PointerBackrefNode
    {
        public long OriginAddress;
        public long TargetAddress;
        public long ReferenceAddress;
        public int Depth;
        public bool ReferenceInsideModule;
        public uint ReferenceRva;
    }

    internal sealed class PointerBackrefScanResult
    {
        public readonly List<PointerBackrefNode> Nodes =
            new List<PointerBackrefNode>();

        public readonly List<PointerBackrefNode> ModuleRoots =
            new List<PointerBackrefNode>();

        public long BytesScanned;
        public bool CandidateLimitReached;
        public string Status = "";
    }

    internal sealed class RuntimePointerBackrefScanner : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;

        private const uint MEM_COMMIT = 0x1000;
        private const uint PAGE_NOACCESS = 0x01;
        private const uint PAGE_READONLY = 0x02;
        private const uint PAGE_READWRITE = 0x04;
        private const uint PAGE_WRITECOPY = 0x08;
        private const uint PAGE_EXECUTE = 0x10;
        private const uint PAGE_EXECUTE_READ = 0x20;
        private const uint PAGE_EXECUTE_READWRITE = 0x40;
        private const uint PAGE_EXECUTE_WRITECOPY = 0x80;
        private const uint PAGE_GUARD = 0x100;

        private const int ChunkSize = 512 * 1024;
        private const int MaxReferencesPerTarget = 128;
        private const int MaxTotalNodes = 4096;
        private const int MaxTargetsPerDepth = 128;

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

        public PointerBackrefScanResult Scan(
            IList<long> origins,
            IntPtr moduleBase,
            int moduleSize,
            int maxDepth)
        {
            var result =
                new PointerBackrefScanResult();

            if (_handle == IntPtr.Zero)
            {
                result.Status =
                    "尚未連接程序。";
                return result;
            }

            if (origins == null ||
                origins.Count == 0)
            {
                result.Status =
                    "沒有 behavior hit 可以反查。";
                return result;
            }

            if (maxDepth < 1)
                maxDepth = 1;

            if (maxDepth > 2)
                maxDepth = 2;

            var current =
                new Dictionary<uint, long>();

            foreach (var origin in origins)
            {
                if (origin <= 0 ||
                    origin > uint.MaxValue)
                    continue;

                var key = unchecked((uint)origin);

                if (!current.ContainsKey(key))
                    current.Add(key, origin);

                if (current.Count >=
                    MaxTargetsPerDepth)
                    break;
            }

            var seenReferenceAddresses =
                new HashSet<long>();

            for (var depth = 1;
                 depth <= maxDepth &&
                 current.Count > 0;
                 depth++)
            {
                var references =
                    ScanOneDepth(
                        current,
                        moduleBase,
                        moduleSize,
                        depth,
                        result,
                        seenReferenceAddresses);

                if (result.CandidateLimitReached)
                    break;

                var next =
                    new Dictionary<uint, long>();

                foreach (var node in references)
                {
                    if (node.ReferenceInsideModule)
                        continue;

                    if (node.ReferenceAddress <= 0 ||
                        node.ReferenceAddress > uint.MaxValue)
                        continue;

                    var key =
                        unchecked(
                            (uint)node.ReferenceAddress);

                    if (!next.ContainsKey(key))
                    {
                        next.Add(
                            key,
                            node.OriginAddress);
                    }

                    if (next.Count >=
                        MaxTargetsPerDepth)
                        break;
                }

                current = next;
            }

            result.Status =
                "pointer backref nodes=" +
                result.Nodes.Count +
                "，module roots=" +
                result.ModuleRoots.Count +
                "，bytes=" +
                result.BytesScanned +
                (result.CandidateLimitReached
                    ? "，候選達安全上限。"
                    : "。");

            return result;
        }

        private List<PointerBackrefNode> ScanOneDepth(
            Dictionary<uint, long> targets,
            IntPtr moduleBase,
            int moduleSize,
            int depth,
            PointerBackrefScanResult result,
            HashSet<long> seenReferenceAddresses)
        {
            var output =
                new List<PointerBackrefNode>();

            var counts =
                new Dictionary<uint, int>();

            foreach (var key in targets.Keys)
                counts[key] = 0;

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
                    IsDataReadable(mbi.Protect))
                {
                    ScanRegion(
                        regionBase,
                        regionSize,
                        targets,
                        counts,
                        moduleBase,
                        moduleSize,
                        depth,
                        result,
                        output,
                        seenReferenceAddresses);

                    if (result.CandidateLimitReached)
                        break;
                }

                var next =
                    regionBase + regionSize;

                if (next <= address)
                    break;

                address = next;
            }

            return output;
        }

        private void ScanRegion(
            long regionBase,
            long regionSize,
            Dictionary<uint, long> targets,
            Dictionary<uint, int> counts,
            IntPtr moduleBase,
            int moduleSize,
            int depth,
            PointerBackrefScanResult result,
            List<PointerBackrefNode> output,
            HashSet<long> seenReferenceAddresses)
        {
            long offset = 0;

            while (offset < regionSize)
            {
                var remaining =
                    regionSize - offset;

                var wanted =
                    (int)Math.Min(
                        (long)ChunkSize,
                        remaining);

                if (wanted < 4)
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

                if (bytesRead >= 4)
                {
                    result.BytesScanned +=
                        bytesRead;

                    var absoluteStart =
                        regionBase + offset;

                    var align =
                        (int)((4 -
                            (absoluteStart & 3)) & 3);

                    for (var i = align;
                         i <= bytesRead - 4;
                         i += 4)
                    {
                        var value =
                            BitConverter.ToUInt32(
                                buffer,
                                i);

                        long origin;
                        if (!targets.TryGetValue(
                            value,
                            out origin))
                            continue;

                        var count = counts[value];
                        if (count >=
                            MaxReferencesPerTarget)
                            continue;

                        var referenceAddress =
                            absoluteStart + i;

                        if (!seenReferenceAddresses.Add(
                            referenceAddress))
                            continue;

                        counts[value] = count + 1;

                        var node =
                            new PointerBackrefNode
                            {
                                OriginAddress = origin,
                                TargetAddress = value,
                                ReferenceAddress =
                                    referenceAddress,
                                Depth = depth
                            };

                        var moduleStart =
                            moduleBase.ToInt64();

                        var moduleEnd =
                            moduleStart +
                            Math.Max(0, moduleSize);

                        if (moduleBase != IntPtr.Zero &&
                            moduleSize > 0 &&
                            referenceAddress >= moduleStart &&
                            referenceAddress < moduleEnd)
                        {
                            node.ReferenceInsideModule =
                                true;

                            node.ReferenceRva =
                                (uint)(
                                    referenceAddress -
                                    moduleStart);

                            result.ModuleRoots.Add(
                                node);
                        }

                        result.Nodes.Add(node);
                        output.Add(node);

                        if (result.Nodes.Count >=
                            MaxTotalNodes)
                        {
                            result.CandidateLimitReached =
                                true;
                            return;
                        }
                    }
                }

                offset += wanted;
            }
        }

        private static bool IsDataReadable(
            uint protect)
        {
            if ((protect & PAGE_EXECUTE) != 0 ||
                (protect & PAGE_EXECUTE_READ) != 0 ||
                (protect & PAGE_EXECUTE_READWRITE) != 0 ||
                (protect & PAGE_EXECUTE_WRITECOPY) != 0)
                return false;

            return (protect & PAGE_READONLY) != 0 ||
                   (protect & PAGE_READWRITE) != 0 ||
                   (protect & PAGE_WRITECOPY) != 0;
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
