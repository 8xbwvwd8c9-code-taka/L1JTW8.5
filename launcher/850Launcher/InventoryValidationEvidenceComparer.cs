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
        public bool InventoryMapped;
        public bool Passed;
        public int RecordCount;
        public int UniqueObjectIds;
        public string ExpectedFingerprint = "";
    }

    internal sealed class InventoryValidationSummary
    {
        public int Sessions;
        public int PassSessions;
        public int DistinctProcessInstances;
        public int DistinctExpectationSets;
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

            var start =
                Math.Max(
                    0,
                    all.Count -
                    latestSessions);

            var sessions =
                all.GetRange(
                    start,
                    all.Count - start);

            result.Sessions =
                sessions.Count;

            var processes =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            var expectedSets =
                new HashSet<string>(
                    StringComparer.Ordinal);

            var allPass = true;

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

                processes.Add(
                    BuildProcessKey(
                        session));

                expectedSets.Add(
                    session.ExpectedFingerprint ??
                    "");
            }

            result.DistinctProcessInstances =
                processes.Count;

            result.DistinctExpectationSets =
                expectedSets.Count;

            result.RestartStablePass =
                sessions.Count >= 3 &&
                allPass &&
                processes.Count >= 2 &&
                expectedSets.Count == 1;

            result.Status =
                "sessions=" +
                result.Sessions +
                "，pass=" +
                result.PassSessions +
                "，process instances=" +
                result.DistinctProcessInstances +
                "，expectation sets=" +
                result.DistinctExpectationSets +
                "，WP6 restart gate=" +
                (result.RestartStablePass
                    ? "PASS"
                    : "NOT_YET") +
                "。";

            return result;
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

            return "PID:" +
                   session.Pid;
        }
    }
}
