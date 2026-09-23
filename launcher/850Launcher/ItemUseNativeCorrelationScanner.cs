using System;
using System.Collections.Generic;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseNativeCorrelationCandidate
    {
        public long BehaviorHitAddress;
        public int BackrefDepth;
        public long ModuleRootAddress;
        public uint ModuleRootRva;
        public uint CodeImmediateRva;
        public uint FunctionRva;
        public string FunctionSha256 = "";
    }

    internal sealed class ItemUseNativeCorrelationResult
    {
        public int BehaviorHitsUsed;
        public int BackrefNodes;
        public int ModuleRoots;
        public int NativeXrefs;
        public bool CandidateLimitReached;

        public readonly List<ItemUseNativeCorrelationCandidate> Candidates =
            new List<ItemUseNativeCorrelationCandidate>();

        public string Status = "";
    }

    internal static class ItemUseNativeCorrelationScanner
    {
        public static ItemUseNativeCorrelationResult Scan(
            string appDir,
            RuntimeSnapshot runtime,
            ItemUseBehaviorEvidenceSession behavior,
            int maxBackrefDepth)
        {
            var result =
                new ItemUseNativeCorrelationResult();

            string error;
            if (!ValidateInputs(
                appDir,
                runtime,
                behavior,
                out error))
            {
                result.Status = error;
                return result;
            }

            if (maxBackrefDepth < 1)
                maxBackrefDepth = 1;

            if (maxBackrefDepth > 2)
                maxBackrefDepth = 2;

            var origins =
                new List<long>();

            foreach (var address in
                     behavior.NewHitAddresses)
            {
                if (address <= 0 ||
                    address > uint.MaxValue)
                    continue;

                if (!origins.Contains(address))
                    origins.Add(address);

                if (origins.Count >= 32)
                    break;
            }

            result.BehaviorHitsUsed =
                origins.Count;

            if (origins.Count == 0)
            {
                result.Status =
                    "behavior evidence 沒有可用的 x86 NEW_HIT 位址。";
                return result;
            }

            PointerBackrefScanResult backrefs;

            using (var scanner =
                   new RuntimePointerBackrefScanner())
            {
                if (!scanner.Attach(
                    runtime.ProcessId,
                    out error))
                {
                    result.Status = error;
                    return result;
                }

                backrefs = scanner.Scan(
                    origins,
                    runtime.ModuleBase,
                    runtime.ModuleSize,
                    maxBackrefDepth);
            }

            result.BackrefNodes =
                backrefs.Nodes.Count;

            result.ModuleRoots =
                backrefs.ModuleRoots.Count;

            result.CandidateLimitReached =
                backrefs.CandidateLimitReached;

            if (backrefs.CandidateLimitReached)
            {
                result.Status =
                    "pointer backref 候選達安全上限，不判 native 關聯。";
                return result;
            }

            if (backrefs.ModuleRoots.Count == 0)
            {
                result.Status =
                    "找到 behavior buffer，但 2 層內沒有回到 Lin.bin2 module data root。";
                return result;
            }

            PeImageInfo image;

            try
            {
                image = PeImportParser.Parse(
                    Path.Combine(
                        appDir,
                        "Lin.bin2"));
            }
            catch (Exception ex)
            {
                result.Status =
                    "Lin.bin2 PE 解析失敗：" +
                    ex.Message;
                return result;
            }

            NativeAbsoluteReferenceResult xrefs;

            using (var probe =
                   new RuntimeMemoryProbe())
            {
                if (!probe.Attach(
                    runtime.ProcessId,
                    out error))
                {
                    result.Status = error;
                    return result;
                }

                xrefs =
                    NativeAbsoluteReferenceScanner.Scan(
                        probe,
                        runtime,
                        image,
                        backrefs.ModuleRoots);

                result.NativeXrefs =
                    xrefs.Hits.Count;

                var rootsByRva =
                    BuildRootIndex(
                        backrefs.ModuleRoots);

                var seen =
                    new HashSet<string>(
                        StringComparer.OrdinalIgnoreCase);

                foreach (var xref in xrefs.Hits)
                {
                    PointerBackrefNode root;

                    if (!rootsByRva.TryGetValue(
                        xref.RootRva,
                        out root))
                        continue;

                    var fingerprint =
                        NativeCodeWindow.Read(
                            probe,
                            runtime,
                            xref.FunctionRva,
                            64);

                    if (fingerprint.BytesRead <= 0 ||
                        string.IsNullOrEmpty(
                            fingerprint.Sha256))
                        continue;

                    var key =
                        xref.RootRva.ToString("X8") +
                        ":" +
                        xref.FunctionRva.ToString("X8") +
                        ":" +
                        xref.ImmediateRva.ToString("X8");

                    if (!seen.Add(key))
                        continue;

                    result.Candidates.Add(
                        new ItemUseNativeCorrelationCandidate
                        {
                            BehaviorHitAddress =
                                root.OriginAddress,
                            BackrefDepth =
                                root.Depth,
                            ModuleRootAddress =
                                root.ReferenceAddress,
                            ModuleRootRva =
                                root.ReferenceRva,
                            CodeImmediateRva =
                                xref.ImmediateRva,
                            FunctionRva =
                                xref.FunctionRva,
                            FunctionSha256 =
                                fingerprint.Sha256
                        });
                }
            }

            result.Candidates.Sort(
                delegate(
                    ItemUseNativeCorrelationCandidate a,
                    ItemUseNativeCorrelationCandidate b)
                {
                    var depth =
                        a.BackrefDepth.CompareTo(
                            b.BackrefDepth);

                    if (depth != 0)
                        return depth;

                    var function =
                        a.FunctionRva.CompareTo(
                            b.FunctionRva);

                    if (function != 0)
                        return function;

                    return a.ModuleRootRva.CompareTo(
                        b.ModuleRootRva);
                });

            result.Status =
                "behavior hits=" +
                result.BehaviorHitsUsed +
                "，backrefs=" +
                result.BackrefNodes +
                "，module roots=" +
                result.ModuleRoots +
                "，native xrefs=" +
                result.NativeXrefs +
                "，function candidates=" +
                result.Candidates.Count +
                "。";

            return result;
        }

        private static Dictionary<uint, PointerBackrefNode> BuildRootIndex(
            IList<PointerBackrefNode> roots)
        {
            var output =
                new Dictionary<uint, PointerBackrefNode>();

            foreach (var root in roots)
            {
                if (root == null ||
                    !root.ReferenceInsideModule)
                    continue;

                if (!output.ContainsKey(
                    root.ReferenceRva))
                {
                    output.Add(
                        root.ReferenceRva,
                        root);
                }
            }

            return output;
        }

        private static bool ValidateInputs(
            string appDir,
            RuntimeSnapshot runtime,
            ItemUseBehaviorEvidenceSession behavior,
            out string error)
        {
            error = "";

            if (runtime == null ||
                !runtime.Connected ||
                !runtime.ClientHashAuthoritative)
            {
                error =
                    "850 authoritative runtime 尚未連接。";
                return false;
            }

            if (behavior == null ||
                !ItemUseBehaviorEvidenceComparer
                    .IsAuthoritative(behavior) ||
                !behavior.Correlated ||
                behavior.CandidateLimitReached ||
                behavior.NewHitAddresses.Count == 0)
            {
                error =
                    "沒有可用的 authoritative UseItem behavior evidence。";
                return false;
            }

            if (runtime.ProcessId != behavior.Pid ||
                runtime.ProcessStartTimeUtc !=
                    behavior.ProcessStartUtc ||
                !string.Equals(
                    runtime.ClientSha256,
                    behavior.ClientSha256,
                    StringComparison.OrdinalIgnoreCase))
            {
                error =
                    "behavior evidence 不屬於目前 Lin.bin2 process instance；請重新做 UseItem 行為驗證。";
                return false;
            }

            var clientPath =
                Path.Combine(
                    appDir,
                    "Lin.bin2");

            if (!File.Exists(clientPath))
            {
                error =
                    "找不到 Lin.bin2。";
                return false;
            }

            return true;
        }
    }
}
