using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSemanticValidationSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;

        public bool CheckHpMp;
        public bool CheckPlayer;

        public bool HpMpPass;
        public bool PlayerPass;
    }

    internal sealed class RuntimeSemanticValidationSummary
    {
        public int Sessions;
        public int DistinctProcessInstances;

        public int HpMpCheckedSessions;
        public int HpMpPassSessions;
        public bool HpMpRestartPass;

        public int PlayerCheckedSessions;
        public int PlayerPassSessions;
        public bool PlayerRestartPass;

        public string Status = "";
    }

    internal static class RuntimeSemanticValidationEvidenceComparer
    {
        public static List<RuntimeSemanticValidationSession> Load(
            string path)
        {
            var output =
                new List<RuntimeSemanticValidationSession>();

            if (!File.Exists(path))
                return output;

            RuntimeSemanticValidationSession current =
                null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line =
                    raw.Trim();

                if (line.StartsWith("TIME=", StringComparison.Ordinal))
                {
                    current =
                        new RuntimeSemanticValidationSession();

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

                if (line.StartsWith("PID=", StringComparison.Ordinal))
                {
                    int pid;
                    if (int.TryParse(
                        line.Substring(4),
                        out pid))
                    {
                        current.Pid = pid;
                    }

                    continue;
                }

                if (line.StartsWith(
                    "PROCESS_START_UTC=",
                    StringComparison.Ordinal))
                {
                    DateTime start;
                    if (DateTime.TryParse(
                        line.Substring(
                            "PROCESS_START_UTC=".Length),
                        null,
                        DateTimeStyles.RoundtripKind,
                        out start))
                    {
                        current.ProcessStartUtc =
                            start.ToUniversalTime();
                    }

                    continue;
                }

                if (line.StartsWith(
                    "CHECK_HPMP=",
                    StringComparison.Ordinal))
                {
                    current.CheckHpMp =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith(
                    "CHECK_PLAYER=",
                    StringComparison.Ordinal))
                {
                    current.CheckPlayer =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith(
                    "HPMP_PASS=",
                    StringComparison.Ordinal))
                {
                    current.HpMpPass =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith(
                    "PLAYER_PASS=",
                    StringComparison.Ordinal))
                {
                    current.PlayerPass =
                        line.EndsWith("=1");
                    continue;
                }
            }

            return output;
        }

        public static RuntimeSemanticValidationSummary CompareLatest(
            string path,
            int latestSessions)
        {
            if (latestSessions < 3)
                latestSessions = 3;

            var all =
                Load(path);

            var result =
                new RuntimeSemanticValidationSummary();

            if (all.Count == 0)
            {
                result.Status =
                    "尚無 runtime semantic validation evidence。";
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

            var processInstances =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in sessions)
            {
                processInstances.Add(
                    BuildProcessKey(session));

                if (session.CheckHpMp)
                {
                    result.HpMpCheckedSessions++;

                    if (session.HpMpPass)
                        result.HpMpPassSessions++;
                }

                if (session.CheckPlayer)
                {
                    result.PlayerCheckedSessions++;

                    if (session.PlayerPass)
                        result.PlayerPassSessions++;
                }
            }

            result.DistinctProcessInstances =
                processInstances.Count;

            result.HpMpRestartPass =
                result.HpMpCheckedSessions >= 3 &&
                result.HpMpPassSessions ==
                    result.HpMpCheckedSessions &&
                result.DistinctProcessInstances >= 2;

            result.PlayerRestartPass =
                result.PlayerCheckedSessions >= 3 &&
                result.PlayerPassSessions ==
                    result.PlayerCheckedSessions &&
                result.DistinctProcessInstances >= 2;

            result.Status =
                "sessions=" +
                result.Sessions +
                " / processes=" +
                result.DistinctProcessInstances +
                " / HPMP=" +
                result.HpMpPassSessions +
                "/" +
                result.HpMpCheckedSessions +
                " / Player=" +
                result.PlayerPassSessions +
                "/" +
                result.PlayerCheckedSessions +
                "。";

            return result;
        }

        private static string BuildProcessKey(
            RuntimeSemanticValidationSession session)
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
