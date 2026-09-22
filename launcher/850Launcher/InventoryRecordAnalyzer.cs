using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class InventoryRecordCell
    {
        public int Offset;
        public IntPtr Address;
        public int BaselineValue;
        public int CurrentValue;
        public bool Changed;
        public bool MatchesKnownItemId;
        public bool MatchesCurrentCount;
    }

    internal static class InventoryRecordAnalyzer
    {
        public static List<InventoryRecordCell> Compare(
            IntPtr center,
            IList<ProbeDword> baseline,
            IList<ProbeDword> current,
            int? knownItemId,
            int currentCount)
        {
            var result = new List<InventoryRecordCell>();
            var baselineByAddress = new Dictionary<long, int>();

            foreach (var row in baseline)
                baselineByAddress[row.Address.ToInt64()] = row.Value;

            foreach (var row in current)
            {
                int before;
                if (!baselineByAddress.TryGetValue(row.Address.ToInt64(), out before))
                    continue;

                var offsetLong = row.Address.ToInt64() - center.ToInt64();
                if (offsetLong < int.MinValue || offsetLong > int.MaxValue)
                    continue;

                result.Add(new InventoryRecordCell
                {
                    Offset = (int)offsetLong,
                    Address = row.Address,
                    BaselineValue = before,
                    CurrentValue = row.Value,
                    Changed = before != row.Value,
                    MatchesKnownItemId = knownItemId.HasValue && row.Value == knownItemId.Value,
                    MatchesCurrentCount = row.Value == currentCount
                });
            }

            result.Sort(delegate(InventoryRecordCell a, InventoryRecordCell b)
            {
                return a.Offset.CompareTo(b.Offset);
            });

            return result;
        }
    }
}
