using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class PlayerIdentityCluster
    {
        public long ObjectIdAddress;
        public long XAddress;
        public long YAddress;
        public long Span;
    }

    internal static class PlayerIdentityClusterer
    {
        public static List<PlayerIdentityCluster> Find(
            IList<IntPtr> objectIds,
            IList<IntPtr> xs,
            IList<IntPtr> ys,
            long window,
            int maxResults)
        {
            var output =
                new List<PlayerIdentityCluster>();

            if (objectIds == null ||
                xs == null ||
                ys == null)
                return output;

            var xSorted = ToSorted(xs);
            var ySorted = ToSorted(ys);

            foreach (var objectId in objectIds)
            {
                var anchor =
                    objectId.ToInt64();

                long x;
                long y;

                if (!TryNearest(
                    xSorted,
                    anchor,
                    window,
                    out x))
                    continue;

                if (!TryNearest(
                    ySorted,
                    anchor,
                    window,
                    out y))
                    continue;

                var min =
                    Math.Min(
                        anchor,
                        Math.Min(x, y));

                var max =
                    Math.Max(
                        anchor,
                        Math.Max(x, y));

                output.Add(
                    new PlayerIdentityCluster
                    {
                        ObjectIdAddress = anchor,
                        XAddress = x,
                        YAddress = y,
                        Span = max - min
                    });

                if (output.Count >=
                    maxResults * 4)
                    break;
            }

            output.Sort(
                delegate(
                    PlayerIdentityCluster a,
                    PlayerIdentityCluster b)
                {
                    var span =
                        a.Span.CompareTo(
                            b.Span);

                    if (span != 0)
                        return span;

                    return a.ObjectIdAddress
                        .CompareTo(
                            b.ObjectIdAddress);
                });

            if (output.Count > maxResults)
            {
                output.RemoveRange(
                    maxResults,
                    output.Count -
                    maxResults);
            }

            return output;
        }

        private static List<long> ToSorted(
            IList<IntPtr> input)
        {
            var output =
                new List<long>();

            foreach (var value in input)
                output.Add(
                    value.ToInt64());

            output.Sort();
            return output;
        }

        private static bool TryNearest(
            List<long> sorted,
            long target,
            long window,
            out long nearest)
        {
            nearest = 0;

            if (sorted.Count == 0)
                return false;

            var index =
                sorted.BinarySearch(
                    target);

            if (index >= 0)
            {
                nearest =
                    sorted[index];

                return true;
            }

            index = ~index;

            var found = false;
            var bestDistance =
                long.MaxValue;

            if (index < sorted.Count)
            {
                var distance =
                    Math.Abs(
                        sorted[index] -
                        target);

                if (distance <= window)
                {
                    nearest =
                        sorted[index];

                    bestDistance =
                        distance;

                    found = true;
                }
            }

            if (index > 0)
            {
                var distance =
                    Math.Abs(
                        sorted[index - 1] -
                        target);

                if (distance <= window &&
                    distance < bestDistance)
                {
                    nearest =
                        sorted[index - 1];

                    found = true;
                }
            }

            return found;
        }
    }
}
