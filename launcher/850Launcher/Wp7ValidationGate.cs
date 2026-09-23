using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class Wp7ValidationState
    {
        public string State = "BLOCKED";
        public string Evidence = "";
        public string Next = "";

        public bool BehaviorRestartStable;
        public bool NativeBehaviorRestartStable;
        public int StableNativeBehaviorCandidates;
        public bool AbiObserved;
    }

    internal static class Wp7ValidationGate
    {
        public static Wp7ValidationState Evaluate(
            string appDir,
            bool wp6Pass)
        {
            var result =
                new Wp7ValidationState();

            var behavior =
                ItemUseBehaviorEvidenceComparer
                .Compare(
                    Path.Combine(
                        appDir,
                        "itemuse_behavior_evidence.txt"));

            var nativeBehavior =
                ItemUseNativeCorrelationEvidenceComparer
                .CompareLatest(
                    Path.Combine(
                        appDir,
                        "itemuse_native_correlation_evidence.txt"),
                    2);

            var abi =
                ItemUseAbiEvidenceComparer
                .CompareAgainstStable(
                    Path.Combine(
                        appDir,
                        "itemuse_abi_evidence.txt"),
                    nativeBehavior.StableCandidates);

            var genericNative =
                NativeCallGraphEvidenceComparer
                .CompareLatest(
                    Path.Combine(
                        appDir,
                        "native_call_graph_evidence.txt"),
                    2);

            result.BehaviorRestartStable =
                behavior.RestartStable;

            result.NativeBehaviorRestartStable =
                nativeBehavior.RestartStable;

            result.StableNativeBehaviorCandidates =
                nativeBehavior.StableCandidates.Count;

            result.AbiObserved =
                abi.AbiObserved;

            result.Evidence =
                "protocol=PASS" +
                " / behavior=" +
                (behavior.RestartStable
                    ? "RESTART_STABLE"
                    : "NOT_YET") +
                "(" +
                behavior.CorrelatedSessions +
                " sessions/" +
                behavior.DistinctProcessInstances +
                " proc)" +
                " / nativeBehavior=" +
                (nativeBehavior.RestartStable
                    ? "RESTART_STABLE"
                    : "NOT_YET") +
                "(" +
                nativeBehavior.StableCandidates.Count +
                " candidates/" +
                nativeBehavior.DistinctProcessInstances +
                " proc)" +
                " / ABI=" +
                (abi.AbiObserved
                    ? "OBSERVED"
                    : "NOT_YET") +
                "(" +
                abi.StableCandidateMatches +
                " matches)" +
                " / genericSend=" +
                genericNative.StableFunctions.Count +
                " funcs/" +
                genericNative.StableEdges +
                " edges" +
                " / ItemUseBridge=UNMAPPED";

            if (!wp6Pass)
            {
                result.State = "BLOCKED";
                result.Next =
                    "等待 WP6 背包列舉 PASS。";
                return result;
            }

            if (!behavior.RestartStable)
            {
                result.State = "READY";
                result.Next =
                    "UseItem協定 → 行為驗證：基線後手動使用道具，至少跨 2 個完整 client instance。";
                return result;
            }

            if (!nativeBehavior.RestartStable)
            {
                result.State =
                    "BEHAVIOR_STABLE";

                result.Next =
                    "UseItem協定 → Native 關聯：同一 client instance 的 NEW_HIT 反查 module root / function candidate；完整重啟後重做。";
                return result;
            }

            if (nativeBehavior.StableCandidates.Count != 1)
            {
                result.State =
                    "NATIVE_BEHAVIOR_AMBIGUOUS";

                result.Next =
                    "用不同 ItemId/ObjectId 再做行為驗證 + Native 關聯，讓跨 session 交集縮到單一 stable candidate；不可任選一顆直接呼叫。";
                return result;
            }

            if (!abi.AbiObserved)
            {
                result.State =
                    "NATIVE_BEHAVIOR_STABLE";

                result.Next =
                    "UseItem協定 → ABI 分析：核對 first64 fingerprint，觀察 prologue / ret cleanup / caller cleanup / ECX。";
                return result;
            }

            result.State =
                "ABI_OBSERVED";

            result.Next =
                "從 direct callsites 繼續證明參數語意與 objectId 傳遞位置；完成 controlled normal UseItem call 前不可啟用 ItemUseBridge。";

            return result;
        }
    }
}
