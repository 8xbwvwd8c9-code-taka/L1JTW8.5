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
        public int HpMpDistinctProcessInstances;
        public int PlayerDistinctProcessInstances;

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
            int latestSessionsPerGate)
        {
            if (latestSessionsPerGate < 3)
                latestSessionsPerGate = 3;

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

            var hpSessions =
                TakeLatestChecked(
                    all,
                    latestSessionsPerGate,
                    true);

            var playerSessions =
                TakeLatestChecked(
                    all,
                    latestSessionsPerGate,
                    false);

            var unionProcesses =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            var hpProcesses =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            var playerProcesses =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in hpSessions)
            {
                var key =
                    BuildProcessKey(session);

                unionProcesses.Add(key);
                hpProcesses.Add(key);

                result.HpMpCheckedSessions++;

                if (session.HpMpPass)
                    result.HpMpPassSessions++;
            }

            foreach (var session in playerSessions)
            {
                var key =
                    BuildProcessKey(session);

                unionProcesses.Add(key);
                playerProcesses.Add(key);

                result.PlayerCheckedSessions++;

                if (session.PlayerPass)
                    result.PlayerPassSessions++;
            }

            result.Sessions =
                Math.Max(
                    hpSessions.Count,
                    playerSessions.Count);

            result.DistinctProcessInstances =
                unionProcesses.Count;

            result.HpMpDistinctProcessInstances =
                hpProcesses.Count;

            result.PlayerDistinctProcessInstances =
                playerProcesses.Count;

            result.HpMpRestartPass =
                result.HpMpCheckedSessions >= 3 &&
                result.HpMpPassSessions ==
                    result.HpMpCheckedSessions &&
                result.HpMpDistinctProcessInstances >= 2;

            result.PlayerRestartPass =
                result.PlayerCheckedSessions >= 3 &&
                result.PlayerPassSessions ==
                    result.PlayerCheckedSessions &&
                result.PlayerDistinctProcessInstances >= 2;

            result.Status =
                "latest/gate=" +
                latestSessionsPerGate +
                " / HPMP=" +
                result.HpMpPassSessions +
                "/" +
                result.HpMpCheckedSessions +
                "@" +
                result.HpMpDistinctProcessInstances +
                "proc / Player=" +
                result.PlayerPassSessions +
                "/" +
                result.PlayerCheckedSessions +
                "@" +
                result.PlayerDistinctProcessInstances +
                "proc。";

            return result;
        }

        private static List<RuntimeSemanticValidationSession> TakeLatestChecked(
            IList<RuntimeSemanticValidationSession> all,
            int count,
            bool hpMp)
        {
            var output =
                new List<RuntimeSemanticValidationSession>();

            for (var i = all.Count - 1;
                 i >= 0 &&
                 output.Count < count;
                 i--)
            {
                var session =
                    all[i];

                var selected =
                    hpMp
                        ? session.CheckHpMp
                        : session.CheckPlayer;

                if (!selected)
                    continue;

                output.Add(session);
            }

            output.Reverse();
            return output;
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
