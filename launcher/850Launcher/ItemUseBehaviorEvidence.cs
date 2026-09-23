using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseBehaviorEvidenceSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;
        public string ClientSha256 = "";
        public bool ClientAuthority;
        public uint ObjectId;
        public int ItemId;
        public string PatternHex = "";
        public int BaselinePasses;
        public int ActionPasses;
        public int BaselineUniqueHits;
        public int ActionUniqueHits;
        public int NewHits;
        public bool Correlated;
    }

    internal sealed class ItemUseBehaviorEvidenceSummary
    {
        public int AuthoritativeSessions;
        public int CorrelatedSessions;
        public int DistinctProcessInstances;
        public bool RestartStable;
        public string Status = "";
    }

    internal static class ItemUseBehaviorEvidenceComparer
    {
        public static List<ItemUseBehaviorEvidenceSession> Load(
            string path)
        {
            var output =
                new List<ItemUseBehaviorEvidenceSession>();

            if (!File.Exists(path))
                return output;

            ItemUseBehaviorEvidenceSession current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();

                if (line.StartsWith(
                    "TIME=",
                    StringComparison.Ordinal))
                {
                    current =
                        new ItemUseBehaviorEvidenceSession();

                    DateTime time;
                    if (DateTime.TryParse(
                        line.Substring(5),
                        out time))
                    {
                        current.Time = time;
                    }

                    output.Add(current);
                    continue;
                }

                if (current == null ||
                    line.Length == 0)
                    continue;

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

                if (line.StartsWith("OBJECT_ID="))
                {
                    uint value;
                    if (uint.TryParse(
                        line.Substring(
                            "OBJECT_ID=".Length),
                        out value))
                    {
                        current.ObjectId = value;
                    }

                    continue;
                }

                if (line.StartsWith("ITEM_ID="))
                {
                    int value;
                    if (int.TryParse(
                        line.Substring(
                            "ITEM_ID=".Length),
                        out value))
                    {
                        current.ItemId = value;
                    }

                    continue;
                }

                if (line.StartsWith("PATTERN="))
                {
                    current.PatternHex =
                        line.Substring(
                            "PATTERN=".Length)
                        .Trim();
                    continue;
                }

                if (line.StartsWith("BASELINE_PASSES="))
                {
                    current.BaselinePasses =
                        ParseInt(
                            line,
                            "BASELINE_PASSES=");
                    continue;
                }

                if (line.StartsWith("ACTION_PASSES="))
                {
                    current.ActionPasses =
                        ParseInt(
                            line,
                            "ACTION_PASSES=");
                    continue;
                }

                if (line.StartsWith("BASELINE_UNIQUE_HITS="))
                {
                    current.BaselineUniqueHits =
                        ParseInt(
                            line,
                            "BASELINE_UNIQUE_HITS=");
                    continue;
                }

                if (line.StartsWith("ACTION_UNIQUE_HITS="))
                {
                    current.ActionUniqueHits =
                        ParseInt(
                            line,
                            "ACTION_UNIQUE_HITS=");
                    continue;
                }

                if (line.StartsWith("NEW_HITS="))
                {
                    current.NewHits =
                        ParseInt(
                            line,
                            "NEW_HITS=");
                    continue;
                }

                if (line.StartsWith("CORRELATED="))
                {
                    current.Correlated =
                        line.EndsWith("=1");
                }
            }

            return output;
        }

        public static ItemUseBehaviorEvidenceSummary Compare(
            string path)
        {
            var result =
                new ItemUseBehaviorEvidenceSummary();

            var all = Load(path);

            var processes =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in all)
            {
                if (!IsAuthoritative(
                    session))
                    continue;

                result.AuthoritativeSessions++;

                if (!session.Correlated ||
                    session.NewHits <= 0)
                    continue;

                result.CorrelatedSessions++;

                processes.Add(
                    BuildProcessKey(
                        session));
            }

            result.DistinctProcessInstances =
                processes.Count;

            result.RestartStable =
                result.CorrelatedSessions >= 2 &&
                result.DistinctProcessInstances >= 2;

            result.Status =
                "authoritative sessions=" +
                result.AuthoritativeSessions +
                "，correlated=" +
                result.CorrelatedSessions +
                "，process instances=" +
                result.DistinctProcessInstances +
                "，restart gate=" +
                (result.RestartStable
                    ? "PASS"
                    : "NOT_YET") +
                "。";

            return result;
        }

        private static bool IsAuthoritative(
            ItemUseBehaviorEvidenceSession session)
        {
            return session != null &&
                   session.ClientAuthority &&
                   string.Equals(
                       session.ClientSha256,
                       ClientVerifier.LinBin2Sha256,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static string BuildProcessKey(
            ItemUseBehaviorEvidenceSession session)
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

        private static int ParseInt(
            string line,
            string prefix)
        {
            int value;
            return int.TryParse(
                line.Substring(prefix.Length),
                out value)
                    ? value
                    : 0;
        }
    }
}
