using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class InventoryValidationSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;
        public string ClientSha256 = "";
        public bool ClientAuthority;
        public bool InventoryMapped;
        public bool Passed;
        public int RecordCount;
        public int UniqueObjectIds;
        public string ExpectedFingerprint = "";
        public string ExpectedItemIdsFingerprint = "";
        public int ExpectedItemCount;
    }

    internal sealed class InventoryValidationSummary
    {
        public int Sessions;
        public int PassSessions;
        public int RecordIdentityPassSessions;
        public int DistinctProcessInstances;
        public int DistinctExpectationSets;
        public int DistinctItemIdSets;
        public int MinimumExpectedItems;
        public bool RestartStablePass;
        public string Status = "";
    }

    internal static class InventoryValidationEvidenceComparer
    {
        public static List<InventoryValidationSession> Load(
            string path)
        {
            var output =
                new List<InventoryValidationSession>();

            if (!File.Exists(path))
                return output;

            InventoryValidationSession current =
                null;

            var expected =
                new List<string>();

            var inExpected = false;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line =
                    raw.Trim();

                if (line.StartsWith(
                    "TIME=",
                    StringComparison.Ordinal))
                {
                    FinalizeExpected(
                        current,
                        expected);

                    current =
                        new InventoryValidationSession();

                    DateTime time;

                    if (DateTime.TryParse(
                        line.Substring(5),
                        out time))
                    {
                        current.Time =
                            time;
                    }

                    output.Add(current);
                    expected.Clear();
                    inExpected = false;
                    continue;
                }

                if (current == null)
                    continue;

                if (line == "[EXPECTED]")
                {
                    inExpected = true;
                    continue;
                }

                if (line.StartsWith("[") &&
                    line.EndsWith("]") &&
                    line != "[EXPECTED]")
                {
                    inExpected = false;
                    continue;
                }

                if (inExpected)
                {
                    if (line.Length > 0)
                        expected.Add(line);

                    continue;
                }

                if (line.StartsWith("PID="))
                {
                    int value;
                    if (int.TryParse(
                        line.Substring(4),
                        out value))
                    {
                        current.Pid = value;
                    }

                    continue;
                }

                if (line.StartsWith(
                    "PROCESS_START_UTC="))
                {
                    DateTime value;

                    if (DateTime.TryParse(
                        line.Substring(
                            "PROCESS_START_UTC=".Length),
                        null,
                        DateTimeStyles.RoundtripKind,
                        out value))
                    {
                        current.ProcessStartUtc =
                            value.ToUniversalTime();
                    }

                    continue;
                }

                if (line.StartsWith(
                    "CLIENT_SHA256="))
                {
                    current.ClientSha256 =
                        line.Substring(
                            "CLIENT_SHA256=".Length)
                        .Trim();

                    continue;
                }

                if (line.StartsWith(
                    "CLIENT_AUTHORITY="))
                {
                    current.ClientAuthority =
                        line.EndsWith("=1");

                    continue;
                }

                if (line.StartsWith(
                    "INVENTORY_MAPPED="))
                {
                    current.InventoryMapped =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith(
                    "PASS="))
                {
                    current.Passed =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith(
                    "RECORD_COUNT="))
                {
                    int value;
                    if (int.TryParse(
                        line.Substring(
                            "RECORD_COUNT=".Length),
                        out value))
                    {
                        current.RecordCount =
                            value;
                    }

                    continue;
                }

                if (line.StartsWith(
                    "UNIQUE_OBJECT_IDS="))
                {
                    int value;
                    if (int.TryParse(
                        line.Substring(
                            "UNIQUE_OBJECT_IDS=".Length),
                        out value))
                    {
                        current.UniqueObjectIds =
                            value;
                    }

                    continue;
                }
            }

            FinalizeExpected(
                current,
                expected);

            return output;
        }

        public static InventoryValidationSession LatestAuthoritative(
            string path)
        {
            var all = Load(path);

            for (var i = all.Count - 1;
                 i >= 0;
                 i--)
            {
                if (IsAuthoritativeSession(
                    all[i]))
                {
                    return all[i];
                }
            }

            return null;
        }

        public static InventoryValidationSummary CompareLatest(
            string path,
            int latestSessions)
        {
            if (latestSessions < 2)
                latestSessions = 2;

            var all =
                Load(path);

            var result =
                new InventoryValidationSummary();

            if (all.Count == 0)
            {
                result.Status =
                    "尚無 inventory validation evidence。";

                return result;
            }

            var authoritative =
                new List<InventoryValidationSession>();

            foreach (var session in all)
            {
                if (IsAuthoritativeSession(
                    session))
                {
                    authoritative.Add(
                        session);
                }
            }

            if (authoritative.Count == 0)
            {
                result.Status =
                    "尚無具 authority hash + process-start identity 的 850 inventory evidence。";

                return result;
            }

            var start =
                Math.Max(
                    0,
                    authoritative.Count -
                    latestSessions);

            var sessions =
                authoritative.GetRange(
                    start,
                    authoritative.Count - start);

            result.Sessions =
                sessions.Count;

            var processes =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            var expectedSets =
                new HashSet<string>(
                    StringComparer.Ordinal);

            var itemIdSets =
                new HashSet<string>(
                    StringComparer.Ordinal);

            var minimumExpectedItems =
                int.MaxValue;

            var allPass = true;
            var allRecordIdentityPass = true;

            foreach (var session in sessions)
            {
                if (session.Passed &&
                    session.InventoryMapped)
                {
                    result.PassSessions++;
                }
                else
                {
                    allPass = false;
                }

                if (session.RecordCount > 0 &&
                    session.UniqueObjectIds ==
                        session.RecordCount)
                {
                    result.RecordIdentityPassSessions++;
                }
                else
                {
                    allRecordIdentityPass = false;
                }

                processes.Add(
                    BuildProcessKey(
                        session));

                expectedSets.Add(
                    session.ExpectedFingerprint ??
                    "");

                itemIdSets.Add(
                    session.ExpectedItemIdsFingerprint ??
                    "");

                if (session.ExpectedItemCount <
                    minimumExpectedItems)
                {
                    minimumExpectedItems =
                        session.ExpectedItemCount;
                }
            }

            result.DistinctProcessInstances =
                processes.Count;

            result.DistinctExpectationSets =
                expectedSets.Count;

            result.DistinctItemIdSets =
                itemIdSets.Count;

            result.MinimumExpectedItems =
                minimumExpectedItems ==
                    int.MaxValue
                    ? 0
                    : minimumExpectedItems;

            result.RestartStablePass =
                sessions.Count >= 3 &&
                allPass &&
                allRecordIdentityPass &&
                processes.Count >= 2 &&
                expectedSets.Count == 1 &&
                itemIdSets.Count == 1 &&
                result.MinimumExpectedItems >= 2;

            result.Status =
                "sessions=" +
                result.Sessions +
                "，pass=" +
                result.PassSessions +
                "，record identity=" +
                result.RecordIdentityPassSessions +
                "/" +
                result.Sessions +
                "，process instances=" +
                result.DistinctProcessInstances +
                "，expectation sets=" +
                result.DistinctExpectationSets +
                "，itemId sets=" +
                result.DistinctItemIdSets +
                "，min expected items=" +
                result.MinimumExpectedItems +
                "，WP6 restart gate=" +
                (result.RestartStablePass
                    ? "PASS"
                    : "NOT_YET") +
                "。";

            return result;
        }

        public static bool IsAuthoritativeSession(
            InventoryValidationSession session)
        {
            return session != null &&
                   session.Pid > 0 &&
                   session.ProcessStartUtc.HasValue &&
                   session.ClientAuthority &&
                   string.Equals(
                       session.ClientSha256,
                       ClientVerifier.LinBin2Sha256,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static void FinalizeExpected(
            InventoryValidationSession session,
            List<string> expected)
        {
            if (session == null)
                return;

            expected.Sort(
                StringComparer.Ordinal);

            session.ExpectedFingerprint =
                string.Join(
                    "|",
                    expected.ToArray());

            var itemIds =
                new List<int>();

            foreach (var line in expected)
            {
                var split =
                    line.IndexOf('=');

                if (split <= 0)
                    continue;

                int itemId;

                if (int.TryParse(
                    line.Substring(0, split)
                    .Trim(),
                    out itemId) &&
                    itemId > 0 &&
                    !itemIds.Contains(itemId))
                {
                    itemIds.Add(itemId);
                }
            }

            itemIds.Sort();

            session.ExpectedItemCount =
                itemIds.Count;

            session.ExpectedItemIdsFingerprint =
                string.Join(
                    ",",
                    itemIds.ConvertAll(
                        delegate(int value)
                        {
                            return value.ToString();
                        })
                    .ToArray());
        }

        private static string BuildProcessKey(
            InventoryValidationSession session)
        {
            if (session.ProcessStartUtc.HasValue)
            {
                return session.ProcessStartUtc
                    .Value
                    .ToString(
                        "o",
                        CultureInfo.InvariantCulture);
            }

            return "MISSING_PROCESS_START";
        }
    }
}
