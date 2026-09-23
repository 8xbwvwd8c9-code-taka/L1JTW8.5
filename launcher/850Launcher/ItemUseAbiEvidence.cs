using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseAbiEvidenceSession
    {
        public DateTime Time;
        public int Pid;
        public DateTime? ProcessStartUtc;
        public string ClientSha256 = "";
        public bool ClientAuthority;
        public uint RootRva;
        public uint FunctionRva;
        public string ExpectedSha256 = "";
        public string ActualSha256 = "";
        public bool FingerprintMatch;
        public string Prologue = "";
        public bool EarlyEcx;
        public bool PlainRet;
        public string RetPopBytes = "";
        public int CallSites;
        public string ConventionCandidate = "";
        public bool MemoryWrite;
        public bool CallExecuted;
    }

    internal sealed class ItemUseAbiEvidenceSummary
    {
        public int AuthoritativeSessions;
        public int FingerprintMatchedSessions;
        public int StableCandidateMatches;
        public bool AbiObserved;
        public string Status = "";
    }

    internal static class ItemUseAbiEvidenceComparer
    {
        public static List<ItemUseAbiEvidenceSession> Load(
            string path)
        {
            var output =
                new List<ItemUseAbiEvidenceSession>();

            if (!File.Exists(path))
                return output;

            ItemUseAbiEvidenceSession current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();

                if (line.StartsWith(
                    "TIME=",
                    StringComparison.Ordinal))
                {
                    current =
                        new ItemUseAbiEvidenceSession();

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

                if (line.StartsWith("ROOT_RVA="))
                {
                    TryParseHexUInt(
                        line.Substring(
                            "ROOT_RVA=".Length),
                        out current.RootRva);
                    continue;
                }

                if (line.StartsWith("FUNCTION_RVA="))
                {
                    TryParseHexUInt(
                        line.Substring(
                            "FUNCTION_RVA=".Length),
                        out current.FunctionRva);
                    continue;
                }

                if (line.StartsWith("EXPECTED_SHA256_64="))
                {
                    current.ExpectedSha256 =
                        line.Substring(
                            "EXPECTED_SHA256_64=".Length)
                        .Trim()
                        .ToUpperInvariant();
                    continue;
                }

                if (line.StartsWith("ACTUAL_SHA256_64="))
                {
                    current.ActualSha256 =
                        line.Substring(
                            "ACTUAL_SHA256_64=".Length)
                        .Trim()
                        .ToUpperInvariant();
                    continue;
                }

                if (line.StartsWith("FINGERPRINT_MATCH="))
                {
                    current.FingerprintMatch =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith("PROLOGUE="))
                {
                    current.Prologue =
                        line.Substring(
                            "PROLOGUE=".Length)
                        .Trim();
                    continue;
                }

                if (line.StartsWith("EARLY_ECX="))
                {
                    current.EarlyEcx =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith("PLAIN_RET="))
                {
                    current.PlainRet =
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith("RET_POP_BYTES="))
                {
                    current.RetPopBytes =
                        line.Substring(
                            "RET_POP_BYTES=".Length)
                        .Trim();
                    continue;
                }

                if (line.StartsWith("CALLSITES="))
                {
                    int value;
                    if (int.TryParse(
                        line.Substring(
                            "CALLSITES=".Length),
                        out value))
                    {
                        current.CallSites = value;
                    }
                    continue;
                }

                if (line.StartsWith("CONVENTION_CANDIDATE="))
                {
                    current.ConventionCandidate =
                        line.Substring(
                            "CONVENTION_CANDIDATE=".Length)
                        .Trim();
                    continue;
                }

                if (line.StartsWith("MEMORY_WRITE="))
                {
                    current.MemoryWrite =
                        line.EndsWith("=YES") ||
                        line.EndsWith("=1");
                    continue;
                }

                if (line.StartsWith("CALL_EXECUTED="))
                {
                    current.CallExecuted =
                        line.EndsWith("=YES") ||
                        line.EndsWith("=1");
                }
            }

            return output;
        }

        public static ItemUseAbiEvidenceSummary CompareAgainstStable(
            string path,
            IList<ItemUseNativeStableCandidate> stableCandidates)
        {
            var result =
                new ItemUseAbiEvidenceSummary();

            var stableKeys =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            if (stableCandidates != null)
            {
                foreach (var candidate in
                         stableCandidates)
                {
                    stableKeys.Add(
                        BuildKey(
                            candidate.RootRva,
                            candidate.FunctionRva,
                            candidate.FunctionSha256));
                }
            }

            var matchedKeys =
                new HashSet<string>(
                    StringComparer.OrdinalIgnoreCase);

            foreach (var session in Load(path))
            {
                if (!IsAuthoritative(session))
                    continue;

                result.AuthoritativeSessions++;

                if (!session.FingerprintMatch ||
                    session.MemoryWrite ||
                    session.CallExecuted ||
                    !string.Equals(
                        session.ExpectedSha256,
                        session.ActualSha256,
                        StringComparison.OrdinalIgnoreCase))
                    continue;

                result.FingerprintMatchedSessions++;

                var key =
                    BuildKey(
                        session.RootRva,
                        session.FunctionRva,
                        session.ActualSha256);

                if (stableKeys.Contains(key))
                    matchedKeys.Add(key);
            }

            result.StableCandidateMatches =
                matchedKeys.Count;

            result.AbiObserved =
                result.StableCandidateMatches > 0;

            result.Status =
                "authoritative=" +
                result.AuthoritativeSessions +
                "，fingerprintMatch=" +
                result.FingerprintMatchedSessions +
                "，stable candidate ABI matches=" +
                result.StableCandidateMatches +
                "，ABI gate=" +
                (result.AbiObserved
                    ? "OBSERVED"
                    : "NOT_YET") +
                "。";

            return result;
        }

        private static bool IsAuthoritative(
            ItemUseAbiEvidenceSession session)
        {
            return session != null &&
                   session.ClientAuthority &&
                   string.Equals(
                       session.ClientSha256,
                       ClientVerifier.LinBin2Sha256,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static string BuildKey(
            uint rootRva,
            uint functionRva,
            string sha)
        {
            return rootRva.ToString("X8") +
                   ":" +
                   functionRva.ToString("X8") +
                   ":" +
                   (sha ?? "").ToUpperInvariant();
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
