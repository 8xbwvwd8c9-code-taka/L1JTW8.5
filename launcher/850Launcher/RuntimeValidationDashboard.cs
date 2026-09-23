using System;
using System.Collections.Generic;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeGateRow
    {
        public string WorkPackage = "";
        public string State = "NOT_YET";
        public string Evidence = "";
        public string Next = "";
    }

    internal sealed class RuntimeValidationDashboardState
    {
        public bool ClientConnected;
        public string ClientStatus = "";
        public string ClientSha256 = "";
        public bool ClientHashAuthoritative;
        public readonly List<RuntimeGateRow> Rows =
            new List<RuntimeGateRow>();
        public string NextAction = "";
    }

    internal static class RuntimeValidationDashboard
    {
        public static RuntimeValidationDashboardState Evaluate(
            string appDir)
        {
            var state =
                new RuntimeValidationDashboardState();

            var runtime =
                new ProcessRuntimeBridge(appDir)
                .Read();

            state.ClientConnected =
                runtime.Connected;

            state.ClientStatus =
                runtime.Status;

            state.ClientSha256 =
                runtime.ClientSha256 ?? "";

            state.ClientHashAuthoritative =
                runtime.ClientHashAuthoritative;

            var pointerPath =
                Path.Combine(
                    appDir,
                    "pointer_probe_evidence.txt");

            var stablePointers =
                PointerEvidenceComparer.Compare(
                    pointerPath,
                    3,
                    2);

            var stableFields =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var candidate in stablePointers)
            {
                if (candidate.Status ==
                    "RESTART_STABLE")
                {
                    stableFields.Add(
                        candidate.Field);
                }
            }

            RuntimeMap runtimeMap =
                null;

            var runtimeMapError =
                "";

            try
            {
                var path =
                    Path.Combine(
                        appDir,
                        "runtime-map.ini");

                if (File.Exists(path))
                {
                    runtimeMap =
                        RuntimeMap.Load(path);
                }
            }
            catch (Exception ex)
            {
                runtimeMapError =
                    ex.Message;
            }

            var hpStable =
                HasAll(
                    stableFields,
                    "CurrentHP",
                    "MaxHP",
                    "CurrentMP",
                    "MaxMP");

            var hpMapped =
                runtimeMap != null &&
                runtimeMap.HasHpMp;

            var semanticPath =
                Path.Combine(
                    appDir,
                    "runtime_semantic_validation_evidence.txt");

            var semantic =
                RuntimeSemanticValidationEvidenceComparer
                .CompareLatest(
                    semanticPath,
                    3);

            var hpSemanticPass =
                semantic.HpMpRestartPass;

            Add(
                state,
                "WP4 HP/MP",
                hpStable && hpMapped && hpSemanticPass
                    ? "PASS"
                    : (hpStable && hpMapped
                        ? "RESTART_STABLE"
                        : "NOT_YET"),
                BuildTripleEvidence(
                    "stable=",
                    hpStable,
                    "map=",
                    hpMapped,
                    "semantic=",
                    hpSemanticPass,
                    runtimeMapError),
                hpStable
                    ? (hpMapped
                        ? (hpSemanticPass
                            ? "WP4 完成"
                            : "映射驗證 → 語意比對")
                        : "映射比對 → 選 RESTART_STABLE → 套用映射")
                    : "偵測 → 指標鏈 → 映射比對");

            var playerStable =
                HasAll(
                    stableFields,
                    "PlayerObjectId",
                    "PlayerX",
                    "PlayerY");

            var playerMapped =
                runtimeMap != null &&
                runtimeMap.HasPlayerIdentity;

            var playerSemanticPass =
                semantic.PlayerRestartPass;

            Add(
                state,
                "WP3 Player",
                playerStable && playerMapped && playerSemanticPass
                    ? "PASS"
                    : (playerStable && playerMapped
                        ? "RESTART_STABLE"
                        : "NOT_YET"),
                BuildTripleEvidence(
                    "stable=",
                    playerStable,
                    "map=",
                    playerMapped,
                    "semantic=",
                    playerSemanticPass,
                    runtimeMapError),
                playerStable
                    ? (playerMapped
                        ? (playerSemanticPass
                            ? "WP3 完成"
                            : "映射驗證 → 語意比對")
                        : "映射比對 → 選 RESTART_STABLE → 套用映射")
                    : "玩家偵測 → 指標鏈 → 映射比對");

            InventoryMap inventoryMap =
                null;

            var inventoryMapError =
                "";

            try
            {
                var path =
                    Path.Combine(
                        appDir,
                        "inventory-map.ini");

                if (File.Exists(path))
                {
                    inventoryMap =
                        InventoryMap.Load(path);
                }
            }
            catch (Exception ex)
            {
                inventoryMapError =
                    ex.Message;
            }

            var inventoryMapEnabled =
                inventoryMap != null &&
                inventoryMap.Mode !=
                    InventoryCollectionMode.Unmapped;

            var validationPath =
                Path.Combine(
                    appDir,
                    "inventory_validation_evidence.txt");

            var latestInventory =
                InventoryValidationEvidenceComparer
                .LatestAuthoritative(
                    validationPath);

            var inventorySessionStructurallyValid =
                latestInventory != null &&
                latestInventory.InventoryMapped &&
                latestInventory.RecordCount > 0 &&
                latestInventory.UniqueObjectIds ==
                    latestInventory.RecordCount;

            var wp5State =
                inventoryMapEnabled &&
                inventorySessionStructurallyValid
                    ? "PASS_SESSION"
                    : "NOT_YET";

            Add(
                state,
                "WP5 Inventory",
                wp5State,
                BuildInventoryEvidence(
                    inventoryMap,
                    inventoryMapEnabled,
                    latestInventory,
                    inventoryMapError),
                !inventoryMapEnabled
                    ? "物品偵測 → 物品結構 → 物品欄位 → 背包容器 → 背包映射"
                    : (!inventorySessionStructurallyValid
                        ? "在「背包驗證」執行一次正式列舉"
                        : "進入 WP6 跨 session 驗證"));

            var wp6 =
                InventoryValidationEvidenceComparer
                .CompareLatest(
                    validationPath,
                    3);

            Add(
                state,
                "WP6 背包列舉",
                wp6.RestartStablePass
                    ? "PASS"
                    : (inventorySessionStructurallyValid
                        ? "NOT_YET"
                        : "BLOCKED"),
                wp6.Status,
                wp6.RestartStablePass
                    ? "進入 WP7 UseItem 行為關聯"
                    : (inventorySessionStructurallyValid
                        ? "登入驗證 → 重登驗證 → 完整重啟驗證"
                        : "先完成 WP5 正式列舉"));

            var behaviorPath =
                Path.Combine(
                    appDir,
                    "itemuse_behavior_evidence.txt");

            var behavior =
                ItemUseBehaviorEvidenceComparer
                .Compare(
                    behaviorPath);

            var nativePath =
                Path.Combine(
                    appDir,
                    "native_call_graph_evidence.txt");

            var native =
                NativeCallGraphEvidenceComparer
                .CompareLatest(
                    nativePath,
                    2);

            var nativeCandidatesStable =
                native.SessionsCompared >= 2 &&
                native.DistinctProcessInstances >= 2 &&
                native.StableFunctions.Count > 0 &&
                native.StableEdges > 0;

            string wp7State;
            string wp7Next;

            if (!wp6.RestartStablePass)
            {
                wp7State = "BLOCKED";
                wp7Next = "等待 WP6 PASS";
            }
            else if (!behavior.RestartStable)
            {
                wp7State = "READY";
                wp7Next =
                    "UseItem協定 → UseItem行為：基線後手動使用道具，跨完整 client restart 重複";
            }
            else if (!nativeCandidatesStable)
            {
                wp7State = "BEHAVIOR_STABLE";
                wp7Next =
                    "Send掃描 → Send追蹤 → Send比對，建立跨 restart native 候選圖";
            }
            else
            {
                wp7State = "NATIVE_CANDIDATES";
                wp7Next =
                    "將手動 UseItem 行為與 stable native send 候選做直接同-session 關聯；未完成前不可啟用 ItemUseBridge";
            }

            Add(
                state,
                "WP7 UseItem",
                wp7State,
                "protocol=PASS / behavior=" +
                (behavior.RestartStable
                    ? "RESTART_STABLE"
                    : "NOT_YET") +
                " / behaviorSessions=" +
                behavior.CorrelatedSessions +
                " / behaviorProcesses=" +
                behavior.DistinctProcessInstances +
                " / nativeFunctions=" +
                native.StableFunctions.Count +
                " / nativeEdges=" +
                native.StableEdges +
                " / nativeProcesses=" +
                native.DistinctProcessInstances +
                " / ItemUseBridge=UNMAPPED",
                wp7Next);

            state.NextAction =
                ResolveNextAction(
                    state);

            return state;
        }

        private static string ResolveNextAction(
            RuntimeValidationDashboardState state)
        {
            if (!state.ClientConnected)
            {
                return "啟動並登入 850 角色後刷新驗證總覽。";
            }

            foreach (var row in state.Rows)
            {
                if (row.WorkPackage.StartsWith("WP4") &&
                    row.State != "PASS")
                    return row.Next;

                if (row.WorkPackage.StartsWith("WP3") &&
                    row.State != "PASS")
                    return row.Next;

                if (row.WorkPackage.StartsWith("WP5") &&
                    row.State != "PASS_SESSION")
                    return row.Next;

                if (row.WorkPackage.StartsWith("WP6") &&
                    row.State != "PASS")
                    return row.Next;

                if (row.WorkPackage.StartsWith("WP7"))
                    return row.Next;
            }

            return "目前 runtime gate 已完成。";
        }

        private static bool HasAll(
            HashSet<string> fields,
            params string[] required)
        {
            foreach (var field in required)
            {
                if (!fields.Contains(field))
                    return false;
            }

            return true;
        }

        private static string BuildTripleEvidence(
            string labelA,
            bool valueA,
            string labelB,
            bool valueB,
            string labelC,
            bool valueC,
            string error)
        {
            var text =
                labelA +
                (valueA ? "YES" : "NO") +
                " / " +
                labelB +
                (valueB ? "YES" : "NO") +
                " / " +
                labelC +
                (valueC ? "YES" : "NO");

            if (!string.IsNullOrEmpty(error))
            {
                text +=
                    " / map error=" +
                    error;
            }

            return text;
        }

        private static string BuildInventoryEvidence(
            InventoryMap map,
            bool enabled,
            InventoryValidationSession latest,
            string error)
        {
            if (!string.IsNullOrEmpty(error))
            {
                return "inventory-map error=" +
                       error;
            }

            if (!enabled)
            {
                return "Mode=UNMAPPED";
            }

            var mode =
                map == null
                    ? "UNKNOWN"
                    : map.Mode.ToString();

            if (latest == null)
            {
                return "Mode=" +
                       mode +
                       " / no validation session";
            }

            return "Mode=" +
                   mode +
                   " / mapped=" +
                   (latest.InventoryMapped
                       ? "YES"
                       : "NO") +
                   " / records=" +
                   latest.RecordCount +
                   " / uniqueObjectIds=" +
                   latest.UniqueObjectIds +
                   " / sessionPass=" +
                   (latest.Passed
                       ? "YES"
                       : "NO");
        }

        private static void Add(
            RuntimeValidationDashboardState state,
            string wp,
            string gateState,
            string evidence,
            string next)
        {
            state.Rows.Add(
                new RuntimeGateRow
                {
                    WorkPackage = wp,
                    State = gateState,
                    Evidence = evidence,
                    Next = next
                });
        }
    }
}
