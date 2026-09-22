using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class PointerChainCandidate
    {
        public int Depth;
        public uint BaseRva;
        public int Offset1;
        public int Offset2;
        public long RootAddress;
        public long IntermediateAddress;

        public string Expression
        {
            get
            {
                if (Depth == 1)
                    return "PTR:0x" + BaseRva.ToString("X8") +
                           "|0x" + Offset1.ToString("X");

                return "PTR:0x" + BaseRva.ToString("X8") +
                       "|0x" + Offset1.ToString("X") +
                       "|0x" + Offset2.ToString("X");
            }
        }
    }

    internal sealed class PointerChainScanner : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;
        private const uint MEM_COMMIT = 0x1000;
        private const uint PAGE_NOACCESS = 0x01;
        private const uint PAGE_GUARD = 0x100;
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

        private sealed class InnerPointer
        {
            public long CellAddress;
            public int FinalOffset;
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

        public bool Attach(int pid, out string error)
        {
            error = "";
            Dispose();

            _handle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
            if (_handle == IntPtr.Zero)
            {
                error = "OpenProcess 失敗，Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            return true;
        }

        public List<PointerChainCandidate> Find(
            long targetAddress,
            long moduleBase,
            int moduleSize,
            int maxOffset,
            int maxResults,
            bool includeDepth2,
            out string status)
        {
            var result = new List<PointerChainCandidate>();
            status = "";

            if (_handle == IntPtr.Zero)
            {
                status = "尚未連接程序。";
                return result;
            }

            if (targetAddress <= 0 || targetAddress > uint.MaxValue)
            {
                status = "目標位址不是有效的 x86 位址。";
                return result;
            }

            if (maxOffset < 0) maxOffset = 0;
            if (maxOffset > 0x10000) maxOffset = 0x10000;
            if (maxResults < 1) maxResults = 1;
            if (maxResults > 1000) maxResults = 1000;

            FindDepth1(
                (uint)targetAddress,
                moduleBase,
                moduleSize,
                maxOffset,
                maxResults,
                result);

            if (includeDepth2 && result.Count < maxResults)
            {
                FindDepth2(
                    (uint)targetAddress,
                    moduleBase,
                    moduleSize,
                    maxOffset,
                    maxResults,
                    result);
            }

            status =
                "Pointer chain 掃描完成：找到 " + result.Count +
                " 筆，最大 offset=0x" + maxOffset.ToString("X") + "。";

            return result;
        }

        private void FindDepth1(
            uint target,
            long moduleBase,
            int moduleSize,
            int maxOffset,
            int maxResults,
            List<PointerChainCandidate> output)
        {
            ScanRange(moduleBase, moduleSize, delegate(long cellAddress, uint value)
            {
                if (output.Count >= maxResults) return false;
                if (value == 0 || value > target) return true;

                var diff = (long)target - value;
                if (diff < 0 || diff > maxOffset) return true;

                var rva = cellAddress - moduleBase;
                if (rva < 0 || rva > uint.MaxValue) return true;

                output.Add(new PointerChainCandidate
                {
                    Depth = 1,
                    BaseRva = (uint)rva,
                    Offset1 = (int)diff,
                    RootAddress = cellAddress,
                    IntermediateAddress = 0
                });

                return true;
            });
        }

        private void FindDepth2(
            uint target,
            long moduleBase,
            int moduleSize,
            int maxOffset,
            int maxResults,
            List<PointerChainCandidate> output)
        {
            var inner = FindInnerPointers(target, maxOffset, 20000);
            if (inner.Count == 0) return;

            inner.Sort(delegate(InnerPointer a, InnerPointer b)
            {
                return a.CellAddress.CompareTo(b.CellAddress);
            });

            ScanRange(moduleBase, moduleSize, delegate(long rootAddress, uint rootValue)
            {
                if (output.Count >= maxResults) return false;
                if (rootValue < 0x10000) return true;

                var index = LowerBound(inner, rootValue);
                while (index < inner.Count)
                {
                    var cell = inner[index];
                    var firstOffset = cell.CellAddress - rootValue;
                    if (firstOffset < 0)
                    {
                        index++;
                        continue;
                    }
                    if (firstOffset > maxOffset) break;

                    var rva = rootAddress - moduleBase;
                    if (rva >= 0 && rva <= uint.MaxValue)
                    {
                        output.Add(new PointerChainCandidate
                        {
                            Depth = 2,
                            BaseRva = (uint)rva,
                            Offset1 = (int)firstOffset,
                            Offset2 = cell.FinalOffset,
                            RootAddress = rootAddress,
                            IntermediateAddress = cell.CellAddress
                        });

                        if (output.Count >= maxResults) return false;
                    }

                    index++;
                }

                return true;
            });
        }

        private List<InnerPointer> FindInnerPointers(
            uint target,
            int maxOffset,
            int maxCandidates)
        {
            var output = new List<InnerPointer>();
            var mbiSize = (uint)Marshal.SizeOf(typeof(MEMORY_BASIC_INFORMATION));
            long address = 0x10000;
            const long maxAddress = 0x7FFF0000;

            while (address < maxAddress && output.Count < maxCandidates)
            {
                MEMORY_BASIC_INFORMATION mbi;
                if (VirtualQueryEx(_handle, new IntPtr(address), out mbi, mbiSize) == 0)
                    break;

                var regionBase = mbi.BaseAddress.ToInt64();
                var regionSize = (long)mbi.RegionSize.ToUInt32();
                if (regionSize <= 0) break;

                if (mbi.State == MEM_COMMIT &&
                    (mbi.Protect & PAGE_GUARD) == 0 &&
                    (mbi.Protect & PAGE_NOACCESS) == 0)
                {
                    ScanRange(regionBase, regionSize, delegate(long cellAddress, uint value)
                    {
                        if (output.Count >= maxCandidates) return false;
                        if (value == 0 || value > target) return true;

                        var diff = (long)target - value;
                        if (diff < 0 || diff > maxOffset) return true;

                        output.Add(new InnerPointer
                        {
                            CellAddress = cellAddress,
                            FinalOffset = (int)diff
                        });
                        return true;
                    });
                }

                var next = regionBase + regionSize;
                if (next <= address) break;
                address = next;
            }

            return output;
        }

        private delegate bool DwordVisitor(long address, uint value);

        private void ScanRange(long startAddress, long size, DwordVisitor visitor)
        {
            if (size <= 0) return;

            long offset = 0;
            while (offset < size)
            {
                var remaining = size - offset;
                var wanted = (int)Math.Min((long)ChunkSize, remaining);
                if (wanted < 4) break;

                var buffer = new byte[wanted];
                IntPtr bytesReadPtr;
                var ok = ReadProcessMemory(
                    _handle,
                    new IntPtr(startAddress + offset),
                    buffer,
                    wanted,
                    out bytesReadPtr);

                var bytesRead = ok ? (int)Math.Min((long)wanted, bytesReadPtr.ToInt64()) : 0;
                if (bytesRead >= 4)
                {
                    for (var i = 0; i <= bytesRead - 4; i += 4)
                    {
                        var value = BitConverter.ToUInt32(buffer, i);
                        if (!visitor(startAddress + offset + i, value))
                            return;
                    }
                }

                offset += wanted;
            }
        }

        private static int LowerBound(List<InnerPointer> list, uint value)
        {
            var lo = 0;
            var hi = list.Count;

            while (lo < hi)
            {
                var mid = lo + ((hi - lo) / 2);
                if (list[mid].CellAddress < value)
                    lo = mid + 1;
                else
                    hi = mid;
            }

            return lo;
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
