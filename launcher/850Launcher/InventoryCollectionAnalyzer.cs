using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class CollectionReferencePair
    {
        public IntPtr RecordAReference;
        public IntPtr RecordBReference;
        public long Distance;
        public long ClusterStart;
        public long ClusterEnd;
    }

    internal static class InventoryCollectionAnalyzer
    {
        public static List<CollectionReferencePair> Correlate(
            IList<IntPtr> recordAReferences,
            IList<IntPtr> recordBReferences,
            long maxDistance,
            int maxResults)
        {
            var output = new List<CollectionReferencePair>();

            if (recordAReferences == null || recordBReferences == null)
                return output;

            var a = ToSorted(recordAReferences);
            var b = ToSorted(recordBReferences);

            foreach (var left in a)
            {
                var index = LowerBound(b, left - maxDistance);

                while (index < b.Count)
                {
                    var right = b[index];
                    var distance = Math.Abs(right - left);
                    if (right > left + maxDistance)
                        break;

                    if (distance <= maxDistance)
                    {
                        output.Add(new CollectionReferencePair
                        {
                            RecordAReference = new IntPtr(left),
                            RecordBReference = new IntPtr(right),
                            Distance = distance,
                            ClusterStart = Math.Min(left, right),
                            ClusterEnd = Math.Max(left, right)
                        });

                        if (output.Count >= maxResults * 4)
                            break;
                    }

                    index++;
                }

                if (output.Count >= maxResults * 4)
                    break;
            }

            output.Sort(delegate(CollectionReferencePair x, CollectionReferencePair y)
            {
                var byDistance = x.Distance.CompareTo(y.Distance);
                if (byDistance != 0) return byDistance;
                return x.ClusterStart.CompareTo(y.ClusterStart);
            });

            if (output.Count > maxResults)
                output.RemoveRange(maxResults, output.Count - maxResults);

            return output;
        }

        private static List<long> ToSorted(IList<IntPtr> input)
        {
            var list = new List<long>();
            foreach (var p in input) list.Add(p.ToInt64());
            list.Sort();
            return list;
        }

        private static int LowerBound(List<long> list, long value)
        {
            var lo = 0;
            var hi = list.Count;

            while (lo < hi)
            {
                var mid = lo + ((hi - lo) / 2);
                if (list[mid] < value)
                    lo = mid + 1;
                else
                    hi = mid;
            }

            return lo;
        }
    }
}
