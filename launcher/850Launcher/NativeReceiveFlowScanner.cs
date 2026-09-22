using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class NativeReceiveFlowNode
    {
        public uint FunctionRva;
        public uint ParentFunctionRva;
        public uint TriggerCallRva;
        public int Depth;
        public string Source = "";
        public string BuffOpcodeMarker = "";
    }

    internal sealed class NativeReceiveFlowResult
    {
        public readonly List<NativeReceiveFlowNode> Nodes =
            new List<NativeReceiveFlowNode>();

        public string Status = "";
    }

    internal static class NativeReceiveFlowScanner
    {
        private sealed class CodeSection
        {
            public uint Rva;
            public byte[] Bytes;
        }

        public static NativeReceiveFlowResult Build(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image,
            IList<NativeImportXref> roots,
            int maxDepth)
        {
            var result =
                new NativeReceiveFlowResult();

            if (probe == null ||
                runtime == null ||
                image == null ||
                roots == null)
            {
                result.Status =
                    "Receive flow 輸入不足。";

                return result;
            }

            if (maxDepth < 1)
                maxDepth = 1;

            if (maxDepth > 5)
                maxDepth = 5;

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

            var queue =
                new Queue<NativeReceiveFlowNode>();

            var seen =
                new HashSet<uint>();

            foreach (var root in roots)
            {
                var function =
                    FindFunctionStart(
                        sections,
                        root.InstructionRva,
                        0x500);

                if (!function.HasValue)
                    continue;

                if (!seen.Add(
                    function.Value))
                    continue;

                var node =
                    new NativeReceiveFlowNode
                    {
                        FunctionRva =
                            function.Value,

                        ParentFunctionRva = 0,

                        TriggerCallRva =
                            root.InstructionRva,

                        Depth = 0,

                        Source =
                            root.ImportName +
                            " " +
                            root.Kind,

                        BuffOpcodeMarker =
                            FindBuffOpcodeMarker(
                                sections,
                                function.Value,
                                0x800)
                    };

                result.Nodes.Add(node);
                queue.Enqueue(node);
            }

            while (queue.Count > 0)
            {
                var parent =
                    queue.Dequeue();

                if (parent.Depth >=
                    maxDepth)
                    continue;

                var callees =
                    FindDirectCallees(
                        sections,
                        parent.FunctionRva,
                        0x1000);

                foreach (var call in callees)
                {
                    if (!IsExecutableRva(
                        sections,
                        call.TargetRva))
                        continue;

                    if (!seen.Add(
                        call.TargetRva))
                        continue;

                    var node =
                        new NativeReceiveFlowNode
                        {
                            FunctionRva =
                                call.TargetRva,

                            ParentFunctionRva =
                                parent.FunctionRva,

                            TriggerCallRva =
                                call.CallRva,

                            Depth =
                                parent.Depth + 1,

                            Source =
                                "CALL from 0x" +
                                parent.FunctionRva
                                .ToString("X8"),

                            BuffOpcodeMarker =
                                FindBuffOpcodeMarker(
                                    sections,
                                    call.TargetRva,
                                    0x800)
                        };

                    result.Nodes.Add(node);
                    queue.Enqueue(node);
                }
            }

            result.Nodes.Sort(
                delegate(
                    NativeReceiveFlowNode a,
                    NativeReceiveFlowNode b)
                {
                    var depth =
                        a.Depth.CompareTo(
                            b.Depth);

                    if (depth != 0)
                        return depth;

                    return a.FunctionRva
                        .CompareTo(
                            b.FunctionRva);
                });

            var markers = 0;

            foreach (var node in result.Nodes)
            {
                if (!string.IsNullOrEmpty(
                    node.BuffOpcodeMarker))
                    markers++;
            }

            result.Status =
                "receive flow nodes=" +
                result.Nodes.Count +
                "，Buff opcode marker=" +
                markers +
                "，depth<=" +
                maxDepth +
                "。";

            return result;
        }

        private sealed class DirectCall
        {
            public uint CallRva;
            public uint TargetRva;
        }

        private static List<DirectCall> FindDirectCallees(
            IList<CodeSection> sections,
            uint functionRva,
            int maxBytes)
        {
            var output =
                new List<DirectCall>();

            CodeSection section;
            int start;

            if (!TryLocate(
                sections,
                functionRva,
                out section,
                out start))
                return output;

            var end =
                Math.Min(
                    section.Bytes.Length,
                    start + maxBytes);

            for (var i = start;
                 i <= end - 5;
                 i++)
            {
                if (section.Bytes[i] == 0xE8)
                {
                    var rel =
                        BitConverter.ToInt32(
                            section.Bytes,
                            i + 1);

                    var nextRva =
                        (long)section.Rva +
                        i +
                        5;

                    var target =
                        nextRva +
                        rel;

                    if (target >= 0 &&
                        target <= uint.MaxValue)
                    {
                        output.Add(
                            new DirectCall
                            {
                                CallRva =
                                    section.Rva +
                                    (uint)i,

                                TargetRva =
                                    (uint)target
                            });
                    }
                }

                if (i - start >= 32)
                {
                    if (section.Bytes[i] == 0xC3)
                        break;

                    if (section.Bytes[i] == 0xC2 &&
                        i + 2 < end)
                        break;
                }
            }

            return output;
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
                        Rva =
                            section.VirtualAddress,
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

            var min =
                Math.Max(
                    0,
                    index - maxBack);

            for (var i = index;
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

            for (var i = index - 1;
                 i >= min;
                 i--)
            {
                var b =
                    section.Bytes[i];

                if (b == 0xCC)
                {
                    var next =
                        i + 1;

                    while (next < index &&
                           section.Bytes[next] ==
                           0xCC)
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
            if (index < 0 ||
                index >= bytes.Length)
                return false;

            if (index + 2 <
                    bytes.Length &&
                bytes[index] == 0x55 &&
                bytes[index + 1] == 0x8B &&
                bytes[index + 2] == 0xEC)
                return true;

            if (index + 4 <
                    bytes.Length &&
                bytes[index] == 0x8B &&
                bytes[index + 1] == 0xFF &&
                bytes[index + 2] == 0x55 &&
                bytes[index + 3] == 0x8B &&
                bytes[index + 4] == 0xEC)
                return true;

            return false;
        }

        private static string FindBuffOpcodeMarker(
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

            var end =
                Math.Min(
                    section.Bytes.Length,
                    start + maxBytes);

            var opcodes =
                new[]
                {
                    new OpcodeMarker(241, "S_SkillBrave"),
                    new OpcodeMarker(121, "S_SkillIconAura"),
                    new OpcodeMarker(111, "S_SkillIconBlessOfEva"),
                    new OpcodeMarker(147, "S_SkillIconShield"),
                    new OpcodeMarker(225, "S_Invis")
                };

            for (var i = start;
                 i < end;
                 i++)
            {
                foreach (var marker in opcodes)
                {
                    var value =
                        marker.Opcode;

                    if (i + 1 < end &&
                        section.Bytes[i] == 0x6A &&
                        section.Bytes[i + 1] ==
                        value)
                    {
                        return marker.Name +
                               "(PUSH_IMM8)";
                    }

                    if (i + 1 < end &&
                        section.Bytes[i] == 0xB0 &&
                        section.Bytes[i + 1] ==
                        value)
                    {
                        return marker.Name +
                               "(MOV_AL)";
                    }

                    if (i + 1 < end &&
                        section.Bytes[i] == 0x3C &&
                        section.Bytes[i + 1] ==
                        value)
                    {
                        return marker.Name +
                               "(CMP_AL)";
                    }

                    if (i + 4 < end &&
                        section.Bytes[i] >= 0xB8 &&
                        section.Bytes[i] <= 0xBF &&
                        section.Bytes[i + 1] ==
                        value &&
                        section.Bytes[i + 2] == 0 &&
                        section.Bytes[i + 3] == 0 &&
                        section.Bytes[i + 4] == 0)
                    {
                        return marker.Name +
                               "(MOV_REG_IMM32)";
                    }
                }

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

        private sealed class OpcodeMarker
        {
            public readonly byte Opcode;
            public readonly string Name;

            public OpcodeMarker(
                int opcode,
                string name)
            {
                Opcode =
                    (byte)opcode;

                Name = name;
            }
        }

        private static bool IsExecutableRva(
            IList<CodeSection> sections,
            uint rva)
        {
            CodeSection section;
            int index;

            return TryLocate(
                sections,
                rva,
                out section,
                out index);
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
                var start =
                    candidate.Rva;

                var end =
                    (long)candidate.Rva +
                    candidate.Bytes.Length;

                if (rva < start ||
                    rva >= end)
                    continue;

                section = candidate;
                index =
                    (int)(rva - start);

                return true;
            }

            return false;
        }
    }
}
