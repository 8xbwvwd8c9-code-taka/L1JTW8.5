using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class NativeAbsoluteReferenceHit
    {
        public uint RootRva;
        public long RootAddress;
        public uint ImmediateRva;
        public uint FunctionRva;
    }

    internal sealed class NativeAbsoluteReferenceResult
    {
        public readonly List<NativeAbsoluteReferenceHit> Hits =
            new List<NativeAbsoluteReferenceHit>();

        public string Status = "";
    }

    internal static class NativeAbsoluteReferenceScanner
    {
        private sealed class CodeSection
        {
            public uint Rva;
            public byte[] Bytes;
        }

        public static NativeAbsoluteReferenceResult Scan(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image,
            IList<PointerBackrefNode> moduleRoots)
        {
            var result =
                new NativeAbsoluteReferenceResult();

            if (probe == null ||
                runtime == null ||
                image == null ||
                moduleRoots == null)
            {
                result.Status =
                    "native xref 輸入不足。";
                return result;
            }

            var sections =
                LoadExecutableSections(
                    probe,
                    runtime,
                    image);

            if (sections.Count == 0)
            {
                result.Status =
                    "無法讀取 executable section。";
                return result;
            }

            var seen =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var root in moduleRoots)
            {
                if (root == null ||
                    !root.ReferenceInsideModule)
                    continue;

                var rootVa =
                    unchecked(
                        (uint)root.ReferenceAddress);

                foreach (var section in sections)
                {
                    ScanSectionForValue(
                        section,
                        rootVa,
                        root.ReferenceRva,
                        root.ReferenceAddress,
                        result,
                        seen);
                }
            }

            result.Hits.Sort(
                delegate(
                    NativeAbsoluteReferenceHit a,
                    NativeAbsoluteReferenceHit b)
                {
                    var byFunction =
                        a.FunctionRva.CompareTo(
                            b.FunctionRva);

                    if (byFunction != 0)
                        return byFunction;

                    var byRoot =
                        a.RootRva.CompareTo(
                            b.RootRva);

                    if (byRoot != 0)
                        return byRoot;

                    return a.ImmediateRva.CompareTo(
                        b.ImmediateRva);
                });

            result.Status =
                "module roots=" +
                moduleRoots.Count +
                "，native absolute xrefs=" +
                result.Hits.Count +
                "。";

            return result;
        }

        private static List<CodeSection> LoadExecutableSections(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image)
        {
            var output =
                new List<CodeSection>();

            foreach (var section in image.Sections)
            {
                if (!section.IsExecutable)
                    continue;

                var sizeLong =
                    (long)Math.Max(
                        section.VirtualSize,
                        section.RawSize);

                if (sizeLong <= 0)
                    continue;

                if (sizeLong >
                    32L * 1024L * 1024L)
                {
                    sizeLong =
                        32L * 1024L * 1024L;
                }

                byte[] bytes;
                string error;

                var address =
                    new IntPtr(
                        runtime.ModuleBase.ToInt64() +
                        section.VirtualAddress);

                if (!probe.TryReadBytes(
                    address,
                    (int)sizeLong,
                    out bytes,
                    out error))
                    continue;

                output.Add(
                    new CodeSection
                    {
                        Rva = section.VirtualAddress,
                        Bytes = bytes
                    });
            }

            return output;
        }

        private static void ScanSectionForValue(
            CodeSection section,
            uint value,
            uint rootRva,
            long rootAddress,
            NativeAbsoluteReferenceResult result,
            HashSet<string> seen)
        {
            if (section.Bytes == null ||
                section.Bytes.Length < 4)
                return;

            var b0 = (byte)(value & 0xFF);
            var b1 = (byte)((value >> 8) & 0xFF);
            var b2 = (byte)((value >> 16) & 0xFF);
            var b3 = (byte)((value >> 24) & 0xFF);

            for (var i = 0;
                 i <= section.Bytes.Length - 4;
                 i++)
            {
                if (section.Bytes[i] != b0 ||
                    section.Bytes[i + 1] != b1 ||
                    section.Bytes[i + 2] != b2 ||
                    section.Bytes[i + 3] != b3)
                    continue;

                var immediateRva =
                    section.Rva +
                    (uint)i;

                var functionRva =
                    FindFunctionStart(
                        section,
                        i,
                        0x500);

                if (!functionRva.HasValue)
                    continue;

                var key =
                    rootRva.ToString("X8") +
                    ":" +
                    immediateRva.ToString("X8") +
                    ":" +
                    functionRva.Value.ToString("X8");

                if (!seen.Add(key))
                    continue;

                result.Hits.Add(
                    new NativeAbsoluteReferenceHit
                    {
                        RootRva = rootRva,
                        RootAddress = rootAddress,
                        ImmediateRva = immediateRva,
                        FunctionRva =
                            functionRva.Value
                    });
            }
        }

        private static uint? FindFunctionStart(
            CodeSection section,
            int instructionIndex,
            int maxBack)
        {
            var min =
                Math.Max(
                    0,
                    instructionIndex - maxBack);

            for (var i = instructionIndex;
                 i >= min;
                 i--)
            {
                if (IsStrongPrologue(
                    section.Bytes,
                    i))
                {
                    return section.Rva +
                           (uint)i;
                }
            }

            for (var i = instructionIndex - 1;
                 i >= min;
                 i--)
            {
                var b =
                    section.Bytes[i];

                if (b == 0xCC)
                {
                    var next = i + 1;

                    while (next < instructionIndex &&
                           section.Bytes[next] == 0xCC)
                    {
                        next++;
                    }

                    return section.Rva +
                           (uint)next;
                }

                if (b == 0xC3)
                {
                    return section.Rva +
                           (uint)(i + 1);
                }

                if (b == 0xC2 &&
                    i + 2 <
                    section.Bytes.Length)
                {
                    return section.Rva +
                           (uint)(i + 3);
                }
            }

            return null;
        }

        private static bool IsStrongPrologue(
            byte[] bytes,
            int index)
        {
            if (bytes == null ||
                index < 0 ||
                index >= bytes.Length)
                return false;

            if (index + 2 < bytes.Length &&
                bytes[index] == 0x55 &&
                bytes[index + 1] == 0x8B &&
                bytes[index + 2] == 0xEC)
                return true;

            if (index + 4 < bytes.Length &&
                bytes[index] == 0x8B &&
                bytes[index + 1] == 0xFF &&
                bytes[index + 2] == 0x55 &&
                bytes[index + 3] == 0x8B &&
                bytes[index + 4] == 0xEC)
                return true;

            return false;
        }
    }
}
