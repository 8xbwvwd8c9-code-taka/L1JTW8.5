using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class NativeFunctionCandidate
    {
        public uint FunctionRva;
        public uint TriggerRva;
        public int Depth;
        public string Source = "";
        public bool StrongOpcode5EMarker;
        public string MarkerKind = "";
    }

    internal sealed class NativeCallEdge
    {
        public uint CallerInstructionRva;
        public uint CallerFunctionRva;
        public uint TargetFunctionRva;
        public int Depth;
        public bool CallerStrongOpcode5EMarker;
        public string MarkerKind = "";
    }

    internal sealed class NativeCallGraphResult
    {
        public readonly List<NativeFunctionCandidate> Functions =
            new List<NativeFunctionCandidate>();

        public readonly List<NativeCallEdge> Edges =
            new List<NativeCallEdge>();

        public string Status = "";
    }

    internal static class NativeCallGraphScanner
    {
        private sealed class CodeSection
        {
            public uint Rva;
            public byte[] Bytes;
        }

        public static NativeCallGraphResult Build(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image,
            IList<NativeImportXref> roots,
            int maxDepth)
        {
            var result = new NativeCallGraphResult();

            if (probe == null || runtime == null || image == null || roots == null)
            {
                result.Status = "Call graph 輸入不足。";
                return result;
            }

            if (maxDepth < 1) maxDepth = 1;
            if (maxDepth > 5) maxDepth = 5;

            var sections = LoadExecutableSections(
                probe,
                runtime,
                image);

            if (sections.Count == 0)
            {
                result.Status = "無法讀取任何 executable section。";
                return result;
            }

            var queued = new Queue<NativeFunctionCandidate>();
            var seenFunctions = new HashSet<uint>();
            var seenEdges = new HashSet<string>();

            foreach (var root in roots)
            {
                var functionRva = FindFunctionStart(
                    sections,
                    root.InstructionRva,
                    0x400);

                if (!functionRva.HasValue)
                    continue;

                var marker = FindStrongOpcode5EMarker(
                    sections,
                    functionRva.Value,
                    0x600);

                var node = new NativeFunctionCandidate
                {
                    FunctionRva = functionRva.Value,
                    TriggerRva = root.InstructionRva,
                    Depth = 0,
                    Source = root.ImportName + " " + root.Kind,
                    StrongOpcode5EMarker = marker != "",
                    MarkerKind = marker
                };

                if (seenFunctions.Add(node.FunctionRva))
                {
                    result.Functions.Add(node);
                    queued.Enqueue(node);
                }
            }

            while (queued.Count > 0)
            {
                var target = queued.Dequeue();
                if (target.Depth >= maxDepth)
                    continue;

                var callers = FindRelativeCallers(
                    sections,
                    target.FunctionRva);

                foreach (var callRva in callers)
                {
                    var callerFunction = FindFunctionStart(
                        sections,
                        callRva,
                        0x500);

                    if (!callerFunction.HasValue)
                        continue;

                    var key =
                        callerFunction.Value.ToString("X8") + ">" +
                        target.FunctionRva.ToString("X8") + "@" +
                        callRva.ToString("X8");

                    if (!seenEdges.Add(key))
                        continue;

                    var marker = FindStrongOpcode5EMarker(
                        sections,
                        callerFunction.Value,
                        0x800);

                    result.Edges.Add(new NativeCallEdge
                    {
                        CallerInstructionRva = callRva,
                        CallerFunctionRva = callerFunction.Value,
                        TargetFunctionRva = target.FunctionRva,
                        Depth = target.Depth + 1,
                        CallerStrongOpcode5EMarker = marker != "",
                        MarkerKind = marker
                    });

                    if (seenFunctions.Add(callerFunction.Value))
                    {
                        var callerNode = new NativeFunctionCandidate
                        {
                            FunctionRva = callerFunction.Value,
                            TriggerRva = callRva,
                            Depth = target.Depth + 1,
                            Source = "CALL -> 0x" + target.FunctionRva.ToString("X8"),
                            StrongOpcode5EMarker = marker != "",
                            MarkerKind = marker
                        };

                        result.Functions.Add(callerNode);
                        queued.Enqueue(callerNode);
                    }
                }
            }

            result.Functions.Sort(delegate(
                NativeFunctionCandidate a,
                NativeFunctionCandidate b)
            {
                var depth = a.Depth.CompareTo(b.Depth);
                if (depth != 0) return depth;
                return a.FunctionRva.CompareTo(b.FunctionRva);
            });

            result.Edges.Sort(delegate(
                NativeCallEdge a,
                NativeCallEdge b)
            {
                var depth = a.Depth.CompareTo(b.Depth);
                if (depth != 0) return depth;
                return a.CallerInstructionRva.CompareTo(
                    b.CallerInstructionRva);
            });

            var markerCount = 0;
            foreach (var node in result.Functions)
            {
                if (node.StrongOpcode5EMarker)
                    markerCount++;
            }

            result.Status =
                "wrapper/function=" + result.Functions.Count +
                "，caller edge=" + result.Edges.Count +
                "，strong 0x5E marker=" + markerCount +
                "，depth<=" + maxDepth + "。";

            return result;
        }

        private static List<CodeSection> LoadExecutableSections(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image)
        {
            var output = new List<CodeSection>();

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

                if (sizeLong > 32L * 1024L * 1024L)
                    sizeLong = 32L * 1024L * 1024L;

                byte[] bytes;
                string error;

                var address = new IntPtr(
                    runtime.ModuleBase.ToInt64() +
                    section.VirtualAddress);

                if (!probe.TryReadBytes(
                    address,
                    (int)sizeLong,
                    out bytes,
                    out error))
                    continue;

                output.Add(new CodeSection
                {
                    Rva = section.VirtualAddress,
                    Bytes = bytes
                });
            }

            return output;
        }

        private static uint? FindFunctionStart(
            IList<CodeSection> sections,
            uint instructionRva,
            int maxBack)
        {
            CodeSection section;
            int index;

            if (!TryLocate(
                sections,
                instructionRva,
                out section,
                out index))
                return null;

            var min = Math.Max(0, index - maxBack);

            for (var i = index; i >= min; i--)
            {
                if (IsStrongPrologue(section.Bytes, i))
                    return section.Rva + (uint)i;
            }

            // Fallback: after RET/INT3 padding, align to next byte.
            for (var i = index - 1; i >= min; i--)
            {
                var b = section.Bytes[i];

                if (b == 0xCC)
                {
                    var next = i + 1;
                    while (next < index &&
                           section.Bytes[next] == 0xCC)
                        next++;

                    return section.Rva + (uint)next;
                }

                if (b == 0xC3)
                    return section.Rva + (uint)(i + 1);

                if (b == 0xC2 && i + 2 < section.Bytes.Length)
                    return section.Rva + (uint)(i + 3);
            }

            return null;
        }

        private static bool IsStrongPrologue(
            byte[] bytes,
            int index)
        {
            if (index < 0 || index >= bytes.Length)
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

            if (index + 3 < bytes.Length &&
                bytes[index] == 0x53 &&
                bytes[index + 1] == 0x8B &&
                bytes[index + 2] == 0xDC &&
                bytes[index + 3] == 0x83)
                return true;

            return false;
        }

        private static List<uint> FindRelativeCallers(
            IList<CodeSection> sections,
            uint targetRva)
        {
            var output = new List<uint>();

            foreach (var section in sections)
            {
                var bytes = section.Bytes;

                for (var i = 0; i <= bytes.Length - 5; i++)
                {
                    if (bytes[i] != 0xE8)
                        continue;

                    var rel = BitConverter.ToInt32(
                        bytes,
                        i + 1);

                    var nextRva =
                        (long)section.Rva +
                        i +
                        5;

                    var target =
                        nextRva +
                        rel;

                    if (target < 0 ||
                        target > uint.MaxValue)
                        continue;

                    if ((uint)target == targetRva)
                    {
                        output.Add(
                            section.Rva +
                            (uint)i);
                    }
                }
            }

            return output;
        }

        private static string FindStrongOpcode5EMarker(
            IList<CodeSection> sections,
            uint functionRva,
            int maxBytes)
        {
            CodeSection section;
            int start;

            if (!TryLocate(
                sections,
                functionRva,
                out section,
                out start))
                return "";

            var end = Math.Min(
                section.Bytes.Length,
                start + maxBytes);

            for (var i = start; i < end; i++)
            {
                // push 0x5E
                if (i + 1 < end &&
                    section.Bytes[i] == 0x6A &&
                    section.Bytes[i + 1] == 0x5E)
                    return "PUSH_IMM8_5E";

                // push 0x0000005E
                if (i + 4 < end &&
                    section.Bytes[i] == 0x68 &&
                    section.Bytes[i + 1] == 0x5E &&
                    section.Bytes[i + 2] == 0x00 &&
                    section.Bytes[i + 3] == 0x00 &&
                    section.Bytes[i + 4] == 0x00)
                    return "PUSH_IMM32_5E";

                // mov eax,0x5E or mov ecx/edx/ebx/etc,0x5E
                if (i + 4 < end &&
                    section.Bytes[i] >= 0xB8 &&
                    section.Bytes[i] <= 0xBF &&
                    section.Bytes[i + 1] == 0x5E &&
                    section.Bytes[i + 2] == 0x00 &&
                    section.Bytes[i + 3] == 0x00 &&
                    section.Bytes[i + 4] == 0x00)
                    return "MOV_REG_IMM32_5E";

                // mov al,0x5E
                if (i + 1 < end &&
                    section.Bytes[i] == 0xB0 &&
                    section.Bytes[i + 1] == 0x5E)
                    return "MOV_AL_5E";

                // stop after a plausible function end once we scanned
                // enough bytes. Early returns before 32 bytes are ignored.
                if (i - start >= 32)
                {
                    if (section.Bytes[i] == 0xC3)
                        break;

                    if (section.Bytes[i] == 0xC2 &&
                        i + 2 < end)
                        break;
                }
            }

            return "";
        }

        private static bool TryLocate(
            IList<CodeSection> sections,
            uint rva,
            out CodeSection section,
            out int index)
        {
            section = null;
            index = 0;

            foreach (var candidate in sections)
            {
                var start = candidate.Rva;
                var end =
                    (long)candidate.Rva +
                    candidate.Bytes.Length;

                if (rva < start ||
                    rva >= end)
                    continue;

                section = candidate;
                index = (int)(rva - start);
                return true;
            }

            return false;
        }
    }
}
