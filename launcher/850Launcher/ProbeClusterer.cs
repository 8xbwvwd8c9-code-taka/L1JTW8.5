using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class ProbeCluster
    {
        public long CurrentHp;
        public long MaxHp;
        public long CurrentMp;
        public long MaxMp;
        public long Span;
    }

    internal static class ProbeClusterer
    {
        public static List<ProbeCluster> Find(
            IDictionary<string, List<IntPtr>> candidates,
            long window,
            int maxResults)
        {
            var output = new List<ProbeCluster>();

            var hp = ToSorted(candidates, "CurrentHP");
            var maxHp = ToSorted(candidates, "MaxHP");
            var mp = ToSorted(candidates, "CurrentMP");
            var maxMp = ToSorted(candidates, "MaxMP");

            if (hp.Count == 0 || maxHp.Count == 0 || mp.Count == 0 || maxMp.Count == 0)
                return output;

            foreach (var anchor in hp)
            {
                long a, b, c;
                if (!TryNearest(maxHp, anchor, window, out a)) continue;
                if (!TryNearest(mp, anchor, window, out b)) continue;
                if (!TryNearest(maxMp, anchor, window, out c)) continue;

                var min = Math.Min(Math.Min(anchor, a), Math.Min(b, c));
                var max = Math.Max(Math.Max(anchor, a), Math.Max(b, c));

                output.Add(new ProbeCluster
                {
                    CurrentHp = anchor,
                    MaxHp = a,
                    CurrentMp = b,
                    MaxMp = c,
                    Span = max - min
                });

                if (output.Count >= maxResults * 4) break;
            }

            output.Sort(delegate(ProbeCluster x, ProbeCluster y)
            {
                return x.Span.CompareTo(y.Span);
            });

            if (output.Count > maxResults)
                output.RemoveRange(maxResults, output.Count - maxResults);

            return output;
        }

        private static List<long> ToSorted(
            IDictionary<string, List<IntPtr>> candidates,
            string key)
        {
            var result = new List<long>();
            List<IntPtr> list;
            if (!candidates.TryGetValue(key, out list)) return result;

            foreach (var p in list) result.Add(p.ToInt64());
            result.Sort();
            return result;
        }

        private static bool TryNearest(
            List<long> sorted,
            long target,
            long window,
            out long nearest)
        {
            nearest = 0;
            if (sorted.Count == 0) return false;

            var index = sorted.BinarySearch(target);
            if (index >= 0)
            {
                nearest = sorted[index];
                return true;
            }

            index = ~index;
            var found = false;
            long bestDistance = long.MaxValue;

            if (index < sorted.Count)
            {
                var distance = Math.Abs(sorted[index] - target);
                if (distance <= window && distance < bestDistance)
                {
                    nearest = sorted[index];
                    bestDistance = distance;
                    found = true;
                }
            }

            if (index > 0)
            {
                var distance = Math.Abs(sorted[index - 1] - target);
                if (distance <= window && distance < bestDistance)
                {
                    nearest = sorted[index - 1];
                    found = true;
                }
            }

            return found;
        }
    }
}
