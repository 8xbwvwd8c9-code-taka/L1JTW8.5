using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text.RegularExpressions;

namespace L1JTW850Launcher
{
    internal sealed class PointerEvidenceEntry
    {
        public int Depth;
        public string Expression = "";
        public uint RootRva;
    }

    internal sealed class PointerEvidenceSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;
        public string ClientSha256 = "";
        public bool ClientAuthority;
        public string Field = "";
        public readonly List<PointerEvidenceEntry> Entries =
            new List<PointerEvidenceEntry>();
    }

    internal sealed class StablePointerCandidate
    {
        public string Field = "";
        public string Expression = "";
        public int Depth;
        public int SessionCount;
        public int ProcessInstanceCount;
        public string Status = "";
    }

    internal static class PointerEvidenceComparer
    {
        private static readonly Regex EntryRegex =
            new Regex(
                @"^DEPTH=(?<depth>\d+)\s+EXPR=(?<expr>\S+)\s+ROOT=0x[0-9A-Fa-f]+\s+ROOT_RVA=0x(?<rva>[0-9A-Fa-f]+)",
                RegexOptions.Compiled);

        public static List<PointerEvidenceSession> Load(
            string path)
        {
            var output =
                new List<PointerEvidenceSession>();

            if (!File.Exists(path))
                return output;

            PointerEvidenceSession current =
                null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line =
                    raw.Trim();

                if (line.StartsWith(
                    "TIME=",
                    StringComparison.Ordinal))
                {
                    current =
                        new PointerEvidenceSession();

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

                if (line.StartsWith(
                    "PID=",
                    StringComparison.Ordinal))
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
                    "CLIENT_SHA256=",
                    StringComparison.Ordinal))
                {
                    current.ClientSha256 =
                        line.Substring(
                            "CLIENT_SHA256=".Length)
                        .Trim();

                    continue;
                }

                if (line.StartsWith(
                    "CLIENT_AUTHORITY=",
                    StringComparison.Ordinal))
                {
                    current.ClientAuthority =
                        line.EndsWith("=1");

                    continue;
                }

                if (line.StartsWith(
                    "FIELD=",
                    StringComparison.Ordinal))
                {
                    current.Field =
                        line.Substring(
                            "FIELD=".Length)
                        .Trim();

                    continue;
                }

                var match =
                    EntryRegex.Match(line);

                if (!match.Success)
                    continue;

                int depth;
                uint rootRva;

                if (!int.TryParse(
                    match.Groups["depth"].Value,
                    out depth))
                    continue;

                if (!uint.TryParse(
                    match.Groups["rva"].Value,
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out rootRva))
                    continue;

                current.Entries.Add(
                    new PointerEvidenceEntry
                    {
                        Depth = depth,
                        Expression =
                            match.Groups["expr"]
                            .Value,
                        RootRva =
                            rootRva
                    });
            }

            return output;
        }

        public static List<StablePointerCandidate> Compare(
            string path,
            int minimumSessions,
            int minimumProcessInstances)
        {
            if (minimumSessions < 2)
                minimumSessions = 2;

            if (minimumProcessInstances < 1)
                minimumProcessInstances = 1;

            var sessions =
                Load(path);

            var stats =
                new Dictionary<string, CandidateStats>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in sessions)
            {
                if (!IsAuthoritativeSession(
                    session))
                    continue;

                if (string.IsNullOrWhiteSpace(
                    session.Field))
                    continue;

                var sessionKey =
                    BuildSessionKey(session);

                var seenInSession =
                    new HashSet<string>(
                        StringComparer.OrdinalIgnoreCase);

                foreach (var entry in session.Entries)
                {
                    if (string.IsNullOrWhiteSpace(
                        entry.Expression))
                        continue;

                    var key =
                        session.Field +
                        "\n" +
                        entry.Expression;

                    if (!seenInSession.Add(key))
                        continue;

                    CandidateStats stat;

                    if (!stats.TryGetValue(
                        key,
                        out stat))
                    {
                        stat =
                            new CandidateStats
                            {
                                Field =
                                    session.Field,

                                Expression =
                                    entry.Expression,

                                Depth =
                                    entry.Depth
                            };

                        stats.Add(
                            key,
                            stat);
                    }

                    stat.Sessions.Add(
                        sessionKey);

                    stat.Processes.Add(
                        BuildProcessKey(
                            session));

                    if (entry.Depth <
                        stat.Depth)
                    {
                        stat.Depth =
                            entry.Depth;
                    }
                }
            }

            var output =
                new List<StablePointerCandidate>();

            foreach (var stat in stats.Values)
            {
                if (stat.Sessions.Count <
                    minimumSessions)
                    continue;

                var restartStable =
                    stat.Processes.Count >=
                    minimumProcessInstances;

                output.Add(
                    new StablePointerCandidate
                    {
                        Field =
                            stat.Field,

                        Expression =
                            stat.Expression,

                        Depth =
                            stat.Depth,

                        SessionCount =
                            stat.Sessions.Count,

                        ProcessInstanceCount =
                            stat.Processes.Count,

                        Status =
                            restartStable
                                ? "RESTART_STABLE"
                                : "SAME_PROCESS_ONLY"
                    });
            }

            output.Sort(
                delegate(
                    StablePointerCandidate a,
                    StablePointerCandidate b)
                {
                    var field =
                        string.Compare(
                            a.Field,
                            b.Field,
                            StringComparison.OrdinalIgnoreCase);

                    if (field != 0)
                        return field;

                    var processRank =
                        b.ProcessInstanceCount
                        .CompareTo(
                            a.ProcessInstanceCount);

                    if (processRank != 0)
                        return processRank;

                    var sessionRank =
                        b.SessionCount
                        .CompareTo(
                            a.SessionCount);

                    if (sessionRank != 0)
                        return sessionRank;

                    var depth =
                        a.Depth.CompareTo(
                            b.Depth);

                    if (depth != 0)
                        return depth;

                    return string.Compare(
                        a.Expression,
                        b.Expression,
                        StringComparison.OrdinalIgnoreCase);
                });

            return output;
        }

        private static bool IsAuthoritativeSession(
            PointerEvidenceSession session)
        {
            return session != null &&
                   session.ClientAuthority &&
                   string.Equals(
                       session.ClientSha256,
                       ClientVerifier.LinBin2Sha256,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static string BuildSessionKey(
            PointerEvidenceSession session)
        {
            return session.Time.ToString(
                       "o",
                       CultureInfo.InvariantCulture) +
                   "|" +
                   session.Pid +
                   "|" +
                   session.ClientSha256 +
                   "|" +
                   session.Field;
        }

        private static string BuildProcessKey(
            PointerEvidenceSession session)
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

        private sealed class CandidateStats
        {
            public string Field = "";
            public string Expression = "";
            public int Depth = int.MaxValue;

            public readonly HashSet<string> Sessions =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            public readonly HashSet<string> Processes =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);
        }
    }
}
