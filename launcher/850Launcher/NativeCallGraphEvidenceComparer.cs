using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text.RegularExpressions;

namespace L1JTW850Launcher
{
    internal sealed class NativeEvidenceFunction
    {
        public uint Rva;
        public int Depth;
        public string Sha256 = "";
        public string Marker = "";
        public string Source = "";
    }

    internal sealed class NativeEvidenceEdge
    {
        public uint CallerFunctionRva;
        public uint CallRva;
        public uint TargetFunctionRva;
        public int Depth;

        public string Key
        {
            get
            {
                return CallerFunctionRva.ToString("X8") + ">" +
                       TargetFunctionRva.ToString("X8") + "@" +
                       CallRva.ToString("X8");
            }
        }
    }

    internal sealed class NativeEvidenceSession
    {
        public DateTime Time;
        public int Pid;
        public long ModuleBase;

        public readonly Dictionary<uint, NativeEvidenceFunction> Functions =
            new Dictionary<uint, NativeEvidenceFunction>();

        public readonly Dictionary<string, NativeEvidenceEdge> Edges =
            new Dictionary<string, NativeEvidenceEdge>();
    }

    internal sealed class NativeStableFunction
    {
        public uint Rva;
        public string Sha256 = "";
        public int SessionCount;
        public int StableIncidentEdges;
        public string Marker = "";
        public int MinDepth;
    }

    internal sealed class NativeEvidenceComparison
    {
        public int SessionsCompared;
        public int StableEdges;
        public readonly List<NativeStableFunction> StableFunctions =
            new List<NativeStableFunction>();

        public string Status = "";
    }

    internal static class NativeCallGraphEvidenceComparer
    {
        private static readonly Regex FuncRegex =
            new Regex(
                @"^FUNC depth=(?<depth>d+) rva=0x(?<rva>[0-9A-Fa-f]+) trigger=0x[0-9A-Fa-f]+ marker=(?<marker>S*) sha256_64=(?<sha>[0-9A-Fa-f]*) bytes=d+ source=(?<source>.*)$",
                RegexOptions.Compiled);

        private static readonly Regex EdgeRegex =
            new Regex(
                @"^EDGE depth=(?<depth>d+) caller_func=0x(?<caller>[0-9A-Fa-f]+) call_rva=0x(?<call>[0-9A-Fa-f]+) target_func=0x(?<target>[0-9A-Fa-f]+) marker=.*$",
                RegexOptions.Compiled);

        public static List<NativeEvidenceSession> LoadSessions(
            string path)
        {
            var output = new List<NativeEvidenceSession>();

            if (!File.Exists(path))
                return output;

            NativeEvidenceSession current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();

                if (line.StartsWith("TIME=", StringComparison.Ordinal))
                {
                    current = new NativeEvidenceSession();

                    DateTime time;
                    if (DateTime.TryParse(
                        line.Substring(5),
                        out time))
                        current.Time = time;

                    output.Add(current);
                    continue;
                }

                if (current == null ||
                    line.Length == 0)
                    continue;

                if (line.StartsWith("PID=", StringComparison.Ordinal))
                {
                    int pid;
                    if (int.TryParse(line.Substring(4), out pid))
                        current.Pid = pid;
                    continue;
                }

                if (line.StartsWith("MODULE_BASE=", StringComparison.Ordinal))
                {
                    long value;
                    if (TryParseHexLong(
                        line.Substring("MODULE_BASE=".Length),
                        out value))
                        current.ModuleBase = value;
                    continue;
                }

                var fm = FuncRegex.Match(line);
                if (fm.Success)
                {
                    uint rva;
                    int depth;

                    if (!uint.TryParse(
                        fm.Groups["rva"].Value,
                        NumberStyles.HexNumber,
                        CultureInfo.InvariantCulture,
                        out rva))
                        continue;

                    int.TryParse(
                        fm.Groups["depth"].Value,
                        out depth);

                    current.Functions[rva] =
                        new NativeEvidenceFunction
                        {
                            Rva = rva,
                            Depth = depth,
                            Sha256 =
                                fm.Groups["sha"].Value.ToUpperInvariant(),
                            Marker =
                                fm.Groups["marker"].Value,
                            Source =
                                fm.Groups["source"].Value
                        };

                    continue;
                }

                var em = EdgeRegex.Match(line);
                if (em.Success)
                {
                    uint caller;
                    uint call;
                    uint target;
                    int depth;

                    if (!TryParseHexUInt(
                        em.Groups["caller"].Value,
                        out caller) ||
                        !TryParseHexUInt(
                        em.Groups["call"].Value,
                        out call) ||
                        !TryParseHexUInt(
                        em.Groups["target"].Value,
                        out target))
                        continue;

                    int.TryParse(
                        em.Groups["depth"].Value,
                        out depth);

                    var edge = new NativeEvidenceEdge
                    {
                        CallerFunctionRva = caller,
                        CallRva = call,
                        TargetFunctionRva = target,
                        Depth = depth
                    };

                    current.Edges[edge.Key] = edge;
                }
            }

            return output;
        }

        public static NativeEvidenceComparison CompareLatest(
            string path,
            int sessionCount)
        {
            var result = new NativeEvidenceComparison();
            var sessions = LoadSessions(path);

            if (sessionCount < 2)
                sessionCount = 2;

            if (sessions.Count < sessionCount)
            {
                result.Status =
                    "證據 session 不足：目前 " +
                    sessions.Count +
                    "，需要至少 " +
                    sessionCount + "。";
                return result;
            }

            var selected = sessions.GetRange(
                sessions.Count - sessionCount,
                sessionCount);

            result.SessionsCompared = selected.Count;

            var stableEdgeKeys =
                new HashSet<string>(
                    selected[0].Edges.Keys,
                    StringComparer.OrdinalIgnoreCase);

            for (var i = 1; i < selected.Count; i++)
                stableEdgeKeys.IntersectWith(
                    selected[i].Edges.Keys);

            result.StableEdges = stableEdgeKeys.Count;

            foreach (var first in selected[0].Functions.Values)
            {
                if (string.IsNullOrEmpty(first.Sha256))
                    continue;

                var stable = true;
                var minDepth = first.Depth;
                var marker = first.Marker;

                for (var i = 1; i < selected.Count; i++)
                {
                    NativeEvidenceFunction other;
                    if (!selected[i].Functions.TryGetValue(
                        first.Rva,
                        out other) ||
                        !string.Equals(
                            first.Sha256,
                            other.Sha256,
                            StringComparison.OrdinalIgnoreCase))
                    {
                        stable = false;
                        break;
                    }

                    if (other.Depth < minDepth)
                        minDepth = other.Depth;

                    if (string.IsNullOrEmpty(marker) &&
                        !string.IsNullOrEmpty(other.Marker))
                        marker = other.Marker;
                }

                if (!stable)
                    continue;

                var incidentEdges = 0;
                foreach (var key in stableEdgeKeys)
                {
                    NativeEvidenceEdge edge;
                    if (!selected[0].Edges.TryGetValue(
                        key,
                        out edge))
                        continue;

                    if (edge.CallerFunctionRva == first.Rva ||
                        edge.TargetFunctionRva == first.Rva)
                        incidentEdges++;
                }

                result.StableFunctions.Add(
                    new NativeStableFunction
                    {
                        Rva = first.Rva,
                        Sha256 = first.Sha256,
                        SessionCount = selected.Count,
                        StableIncidentEdges = incidentEdges,
                        Marker = marker,
                        MinDepth = minDepth
                    });
            }

            result.StableFunctions.Sort(
                delegate(
                    NativeStableFunction a,
                    NativeStableFunction b)
                {
                    var edgeRank =
                        b.StableIncidentEdges.CompareTo(
                            a.StableIncidentEdges);

                    if (edgeRank != 0)
                        return edgeRank;

                    var markerRank =
                        (!string.IsNullOrEmpty(
                            b.Marker)).CompareTo(
                            !string.IsNullOrEmpty(
                                a.Marker));

                    if (markerRank != 0)
                        return markerRank;

                    var depthRank =
                        a.MinDepth.CompareTo(
                            b.MinDepth);

                    if (depthRank != 0)
                        return depthRank;

                    return a.Rva.CompareTo(b.Rva);
                });

            result.Status =
                "比對 " + selected.Count +
                " sessions：stable functions=" +
                result.StableFunctions.Count +
                "，stable edges=" +
                result.StableEdges + "。";

            return result;
        }

        private static bool TryParseHexUInt(
            string text,
            out uint value)
        {
            value = 0;

            text = text.Trim();
            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
                text = text.Substring(2);

            return uint.TryParse(
                text,
                NumberStyles.HexNumber,
                CultureInfo.InvariantCulture,
                out value);
        }

        private static bool TryParseHexLong(
            string text,
            out long value)
        {
            value = 0;

            text = text.Trim();
            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
                text = text.Substring(2);

            ulong unsigned;
            if (!ulong.TryParse(
                text,
                NumberStyles.HexNumber,
                CultureInfo.InvariantCulture,
                out unsigned))
                return false;

            value = unchecked((long)unsigned);
            return true;
        }
    }
}
