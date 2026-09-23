using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseNativeEvidenceCandidate
    {
        public uint RootRva;
        public uint FunctionRva;
        public string FunctionSha256 = "";

        public string Key
        {
            get
            {
                return RootRva.ToString("X8") +
                       ":" +
                       FunctionRva.ToString("X8") +
                       ":" +
                       (FunctionSha256 ?? "").ToUpperInvariant();
            }
        }
    }

    internal sealed class ItemUseNativeEvidenceSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;
        public string ClientSha256 = "";
        public bool ClientAuthority;
        public uint ObjectId;
        public int ItemId;
        public bool CandidateLimitReached;

        public readonly Dictionary<string, ItemUseNativeEvidenceCandidate> Candidates =
            new Dictionary<string, ItemUseNativeEvidenceCandidate>(
                StringComparer.OrdinalIgnoreCase);
    }

    internal sealed class ItemUseNativeStableCandidate
    {
        public uint RootRva;
        public uint FunctionRva;
        public string FunctionSha256 = "";
        public int SessionCount;
    }

    internal sealed class ItemUseNativeEvidenceSummary
    {
        public int AuthoritativeSessions;
        public int ValidSessions;
        public int SessionsCompared;
        public int DistinctProcessInstances;
        public bool RestartStable;

        public readonly List<ItemUseNativeStableCandidate> StableCandidates =
            new List<ItemUseNativeStableCandidate>();

        public string Status = "";
    }

    internal static class ItemUseNativeCorrelationEvidenceComparer
    {
        public static List<ItemUseNativeEvidenceSession> Load(
            string path)
        {
            var output =
                new List<ItemUseNativeEvidenceSession>();

            if (!File.Exists(path))
                return output;

            ItemUseNativeEvidenceSession current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();

                if (line.StartsWith(
                    "TIME=",
                    StringComparison.Ordinal))
                {
                    current =
                        new ItemUseNativeEvidenceSession();

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

                if (line.StartsWith("PROCESS_START_UTC="))
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

                if (line.StartsWith("CLIENT_SHA256="))
                {
                    current.ClientSha256 =
                        line.Substring(
                            "CLIENT_SHA256=".Length)
                        .Trim();
                    continue;
                }

                if (line.StartsWith("CLIENT_AUTHORITY="))
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

                if (line.StartsWith("CANDIDATE_LIMIT="))
                {
                    current.CandidateLimitReached =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith("CANDIDATE "))
                {
                    ItemUseNativeEvidenceCandidate candidate;
                    if (TryParseCandidate(
                        line,
                        out candidate))
                    {
                        current.Candidates[
                            candidate.Key] =
                            candidate;
                    }
                }
            }

            return output;
        }

        public static ItemUseNativeEvidenceSummary CompareLatest(
            string path,
            int sessionCount)
        {
            var result =
                new ItemUseNativeEvidenceSummary();

            if (sessionCount < 2)
                sessionCount = 2;

            var all = Load(path);

            var valid =
                new List<ItemUseNativeEvidenceSession>();

            foreach (var session in all)
            {
                if (!IsAuthoritative(session))
                    continue;

                result.AuthoritativeSessions++;

                if (session.CandidateLimitReached ||
                    session.Candidates.Count == 0)
                    continue;

                valid.Add(session);
                result.ValidSessions++;
            }

            if (valid.Count < sessionCount)
            {
                result.Status =
                    "authoritative valid sessions=" +
                    valid.Count +
                    "，需要至少 " +
                    sessionCount +
                    "。";
                return result;
            }

            var selected = valid.GetRange(
                valid.Count - sessionCount,
                sessionCount);

            result.SessionsCompared =
                selected.Count;

            var processes =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in selected)
            {
                processes.Add(
                    BuildProcessKey(session));
            }

            result.DistinctProcessInstances =
                processes.Count;

            var stableKeys =
                new HashSet<string>(
                    selected[0].Candidates.Keys,
                    StringComparer.OrdinalIgnoreCase);

            for (var i = 1;
                 i < selected.Count;
                 i++)
            {
                stableKeys.IntersectWith(
                    selected[i].Candidates.Keys);
            }

            foreach (var key in stableKeys)
            {
                ItemUseNativeEvidenceCandidate candidate;

                if (!selected[0].Candidates.TryGetValue(
                    key,
                    out candidate))
                    continue;

                result.StableCandidates.Add(
                    new ItemUseNativeStableCandidate
                    {
                        RootRva =
                            candidate.RootRva,
                        FunctionRva =
                            candidate.FunctionRva,
                        FunctionSha256 =
                            candidate.FunctionSha256,
                        SessionCount =
                            selected.Count
                    });
            }

            result.StableCandidates.Sort(
                delegate(
                    ItemUseNativeStableCandidate a,
                    ItemUseNativeStableCandidate b)
                {
                    var function =
                        a.FunctionRva.CompareTo(
                            b.FunctionRva);

                    if (function != 0)
                        return function;

                    return a.RootRva.CompareTo(
                        b.RootRva);
                });

            result.RestartStable =
                result.SessionsCompared >= 2 &&
                result.DistinctProcessInstances >= 2 &&
                result.StableCandidates.Count > 0;

            result.Status =
                "sessions=" +
                result.SessionsCompared +
                "，processes=" +
                result.DistinctProcessInstances +
                "，stable root/function candidates=" +
                result.StableCandidates.Count +
                "，native behavior gate=" +
                (result.RestartStable
                    ? "PASS"
                    : "NOT_YET") +
                "。";

            return result;
        }

        private static bool IsAuthoritative(
            ItemUseNativeEvidenceSession session)
        {
            return session != null &&
                   session.ClientAuthority &&
                   string.Equals(
                       session.ClientSha256,
                       ClientVerifier.LinBin2Sha256,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static string BuildProcessKey(
            ItemUseNativeEvidenceSession session)
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

        private static bool TryParseCandidate(
            string line,
            out ItemUseNativeEvidenceCandidate candidate)
        {
            candidate = null;

            var tokens = line.Split(
                new[] { ' ' },
                StringSplitOptions.RemoveEmptyEntries);

            uint rootRva = 0;
            uint functionRva = 0;
            var sha = "";

            foreach (var token in tokens)
            {
                if (token.StartsWith(
                    "root_rva=",
                    StringComparison.OrdinalIgnoreCase))
                {
                    if (!TryParseHexUInt(
                        token.Substring(
                            "root_rva=".Length),
                        out rootRva))
                        return false;
                    continue;
                }

                if (token.StartsWith(
                    "func_rva=",
                    StringComparison.OrdinalIgnoreCase))
                {
                    if (!TryParseHexUInt(
                        token.Substring(
                            "func_rva=".Length),
                        out functionRva))
                        return false;
                    continue;
                }

                if (token.StartsWith(
                    "sha256_64=",
                    StringComparison.OrdinalIgnoreCase))
                {
                    sha =
                        token.Substring(
                            "sha256_64=".Length)
                        .Trim()
                        .ToUpperInvariant();
                }
            }

            if (rootRva == 0 ||
                functionRva == 0 ||
                string.IsNullOrEmpty(sha))
                return false;

            candidate =
                new ItemUseNativeEvidenceCandidate
                {
                    RootRva = rootRva,
                    FunctionRva = functionRva,
                    FunctionSha256 = sha
                };

            return true;
        }

        private static bool TryParseHexUInt(
            string text,
            out uint value)
        {
            value = 0;
            text = (text ?? "").Trim();

            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
            {
                text = text.Substring(2);
            }

            return uint.TryParse(
                text,
                NumberStyles.HexNumber,
                CultureInfo.InvariantCulture,
                out value);
        }
    }
}
