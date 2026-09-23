using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class NativeAbiCallSite
    {
        public uint CallRva;
        public int CallerCleanupBytes;
    }

    internal sealed class NativeAbiAnalysisResult
    {
        public uint FunctionRva;
        public string FunctionSha256 = "";
        public string Prologue = "UNKNOWN";
        public bool EarlyEcxHeuristic;
        public bool PlainRetSeen;
        public readonly List<int> RetPopBytes =
            new List<int>();
        public readonly List<NativeAbiCallSite> CallSites =
            new List<NativeAbiCallSite>();
        public string ConventionCandidate = "UNKNOWN";
        public string Status = "";
    }

    internal static class NativeFunctionAbiAnalyzer
    {
        private sealed class CodeSection
        {
            public uint Rva;
            public byte[] Bytes;
        }

        public static NativeAbiAnalysisResult Analyze(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image,
            uint functionRva)
        {
            var result =
                new NativeAbiAnalysisResult
                {
                    FunctionRva = functionRva
                };

            if (probe == null ||
                runtime == null ||
                image == null ||
                functionRva == 0)
            {
                result.Status =
                    "ABI analyzer 輸入不足。";
                return result;
            }

            var fingerprint =
                NativeCodeWindow.Read(
                    probe,
                    runtime,
                    functionRva,
                    64);

            result.FunctionSha256 =
                fingerprint.Sha256;

            byte[] functionBytes;
            string error;

            if (!probe.TryReadBytes(
                new IntPtr(
                    runtime.ModuleBase.ToInt64() +
                    functionRva),
                512,
                out functionBytes,
                out error))
            {
                result.Status =
                    "讀取候選 function 失敗：" +
                    error;
                return result;
            }

            result.Prologue =
                DetectPrologue(functionBytes);

            result.EarlyEcxHeuristic =
                DetectEarlyEcxUse(functionBytes);

            ScanReturns(
                functionBytes,
                result);

            var sections =
                LoadExecutableSections(
                    probe,
                    runtime,
                    image);

            ScanDirectCallSites(
                sections,
                functionRva,
                result);

            result.ConventionCandidate =
                InferConvention(result);

            result.Status =
                "func=0x" +
                functionRva.ToString("X8") +
                " / prologue=" +
                result.Prologue +
                " / ECX=" +
                (result.EarlyEcxHeuristic
                    ? "YES"
                    : "NO") +
                " / retPop=" +
                JoinInts(result.RetPopBytes) +
                " / callsites=" +
                result.CallSites.Count +
                " / convention=" +
                result.ConventionCandidate +
                "。";

            return result;
        }

        private static string DetectPrologue(
            byte[] bytes)
        {
            if (bytes == null ||
                bytes.Length < 3)
                return "UNKNOWN";

            if (bytes.Length >= 5 &&
                bytes[0] == 0x8B &&
                bytes[1] == 0xFF &&
                bytes[2] == 0x55 &&
                bytes[3] == 0x8B &&
                bytes[4] == 0xEC)
                return "HOTPATCH_EBP";

            if (bytes[0] == 0x55 &&
                bytes[1] == 0x8B &&
                bytes[2] == 0xEC)
                return "EBP";

            if (bytes.Length >= 3 &&
                bytes[0] == 0x83 &&
                bytes[1] == 0xEC)
                return "ESP_FRAME";

            if (bytes.Length >= 6 &&
                bytes[0] == 0x81 &&
                bytes[1] == 0xEC)
                return "ESP_FRAME";

            return "OTHER";
        }

        private static bool DetectEarlyEcxUse(
            byte[] bytes)
        {
            if (bytes == null)
                return false;

            var limit =
                Math.Min(
                    bytes.Length - 1,
                    48);

            for (var i = 0;
                 i < limit;
                 i++)
            {
                if (bytes[i] == 0x8B &&
                    (bytes[i + 1] == 0xF1 ||
                     bytes[i + 1] == 0xF9 ||
                     bytes[i + 1] == 0xC1 ||
                     bytes[i + 1] == 0xD1))
                    return true;

                if (bytes[i] == 0x51)
                    return true;

                if (bytes[i] == 0x89 &&
                    (bytes[i + 1] & 0x38) == 0x08)
                    return true;
            }

            return false;
        }

        private static void ScanReturns(
            byte[] bytes,
            NativeAbiAnalysisResult result)
        {
            if (bytes == null)
                return;

            var seenPop =
                new HashSet<int>();

            var limit =
                Math.Min(
                    bytes.Length,
                    512);

            for (var i = 0;
                 i < limit;
                 i++)
            {
                if (bytes[i] == 0xC3)
                {
                    result.PlainRetSeen = true;
                    continue;
                }

                if (bytes[i] == 0xC2 &&
                    i + 2 < limit)
                {
                    var pop =
                        bytes[i + 1] |
                        (bytes[i + 2] << 8);

                    if (pop >= 0 &&
                        pop <= 0x100 &&
                        (pop % 4) == 0 &&
                        seenPop.Add(pop))
                    {
                        result.RetPopBytes.Add(pop);
                    }
                }
            }

            result.RetPopBytes.Sort();
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

                if (!probe.TryReadBytes(
                    new IntPtr(
                        runtime.ModuleBase.ToInt64() +
                        section.VirtualAddress),
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

        private static void ScanDirectCallSites(
            IList<CodeSection> sections,
            uint functionRva,
            NativeAbiAnalysisResult result)
        {
            foreach (var section in sections)
            {
                if (section.Bytes == null)
                    continue;

                for (var i = 0;
                     i <= section.Bytes.Length - 5;
                     i++)
                {
                    if (section.Bytes[i] != 0xE8)
                        continue;

                    var rel =
                        BitConverter.ToInt32(
                            section.Bytes,
                            i + 1);

                    var nextRva =
                        (long)section.Rva +
                        i +
                        5;

                    var target =
                        nextRva + rel;

                    if (target != functionRva)
                        continue;

                    var callRva =
                        section.Rva +
                        (uint)i;

                    result.CallSites.Add(
                        new NativeAbiCallSite
                        {
                            CallRva = callRva,
                            CallerCleanupBytes =
                                ReadCallerCleanup(
                                    section.Bytes,
                                    i + 5)
                        });
                }
            }
        }

        private static int ReadCallerCleanup(
            byte[] bytes,
            int index)
        {
            if (bytes == null ||
                index < 0 ||
                index >= bytes.Length)
                return 0;

            if (index + 2 < bytes.Length &&
                bytes[index] == 0x83 &&
                bytes[index + 1] == 0xC4)
            {
                return bytes[index + 2];
            }

            if (index + 5 < bytes.Length &&
                bytes[index] == 0x81 &&
                bytes[index + 1] == 0xC4)
            {
                return BitConverter.ToInt32(
                    bytes,
                    index + 2);
            }

            return 0;
        }

        private static string InferConvention(
            NativeAbiAnalysisResult result)
        {
            var callerCleanup = 0;
            var nonZeroCleanup = 0;

            foreach (var site in result.CallSites)
            {
                if (site.CallerCleanupBytes <= 0)
                    continue;

                nonZeroCleanup++;

                if (callerCleanup == 0)
                    callerCleanup =
                        site.CallerCleanupBytes;
                else if (callerCleanup !=
                         site.CallerCleanupBytes)
                    return "MIXED_OR_UNKNOWN";
            }

            if (nonZeroCleanup > 0)
                return "CDECL_LIKE";

            if (result.RetPopBytes.Count == 1)
            {
                if (result.EarlyEcxHeuristic)
                    return "THISCALL_OR_CALLEE_CLEANUP";

                return "STDCALL_OR_CALLEE_CLEANUP";
            }

            if (result.RetPopBytes.Count > 1)
                return "MIXED_OR_UNKNOWN";

            if (result.EarlyEcxHeuristic)
                return "THISCALL_CANDIDATE";

            if (result.PlainRetSeen)
                return "CDECL_OR_UNKNOWN";

            return "UNKNOWN";
        }

        private static string JoinInts(
            IList<int> values)
        {
            if (values == null ||
                values.Count == 0)
                return "-";

            var parts =
                new string[values.Count];

            for (var i = 0;
                 i < values.Count;
                 i++)
            {
                parts[i] =
                    values[i].ToString();
            }

            return string.Join(
                ",",
                parts);
        }
    }
}
