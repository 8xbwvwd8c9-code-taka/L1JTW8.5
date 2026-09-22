using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class InventoryExpectation
    {
        public int ItemId;
        public long Count;
    }

    internal sealed class InventoryValidationMismatch
    {
        public int ItemId;
        public long Expected;
        public long Actual;
    }

    internal sealed class InventoryValidationResult
    {
        public bool Connected;
        public bool InventoryMapped;
        public bool Passed;
        public string Status = "";
        public int RecordCount;
        public int UniqueObjectIds;
        public readonly List<InventoryValidationMismatch> Mismatches =
            new List<InventoryValidationMismatch>();
    }

    internal static class InventoryRuntimeValidator
    {
        public static List<InventoryExpectation> ParseExpectations(
            string text)
        {
            var output =
                new List<InventoryExpectation>();

            if (string.IsNullOrWhiteSpace(text))
                return output;

            var seen =
                new HashSet<int>();

            var lines =
                text.Replace("\r", "")
                    .Split(
                        new[] { '\n', ';' },
                        StringSplitOptions.RemoveEmptyEntries);

            foreach (var raw in lines)
            {
                var line =
                    raw.Trim();

                if (line.Length == 0 ||
                    line.StartsWith("#"))
                    continue;

                var split =
                    line.IndexOf('=');

                if (split <= 0 ||
                    split >= line.Length - 1)
                {
                    throw new InvalidDataException(
                        "格式錯誤：" + line +
                        "；請使用 itemId=count。");
                }

                int itemId;
                long count;

                if (!int.TryParse(
                    line.Substring(0, split).Trim(),
                    out itemId) ||
                    itemId <= 0)
                {
                    throw new InvalidDataException(
                        "ItemId 格式錯誤：" + line);
                }

                if (!long.TryParse(
                    line.Substring(split + 1).Trim(),
                    out count) ||
                    count < 0)
                {
                    throw new InvalidDataException(
                        "Count 格式錯誤：" + line);
                }

                if (!seen.Add(itemId))
                {
                    throw new InvalidDataException(
                        "ItemId 重複：" + itemId);
                }

                output.Add(
                    new InventoryExpectation
                    {
                        ItemId = itemId,
                        Count = count
                    });
            }

            return output;
        }

        public static InventoryValidationResult Validate(
            RuntimeSnapshot runtime,
            InventoryReadResult inventory,
            IList<InventoryExpectation> expected)
        {
            var result =
                new InventoryValidationResult();

            if (runtime == null ||
                !runtime.Connected)
            {
                result.Status =
                    "Lin.bin2 尚未連接。";

                return result;
            }

            result.Connected = true;

            if (inventory == null ||
                !inventory.Mapped)
            {
                result.Status =
                    inventory == null
                        ? "InventoryBridge 沒有結果。"
                        : inventory.Status;

                return result;
            }

            result.InventoryMapped = true;
            result.RecordCount =
                inventory.Items.Count;

            var objectIds =
                new HashSet<uint>();

            var totals =
                new Dictionary<int, long>();

            foreach (var item in inventory.Items)
            {
                if (item.ObjectId != 0)
                    objectIds.Add(item.ObjectId);

                long current;
                totals.TryGetValue(
                    item.ItemId,
                    out current);

                try
                {
                    checked
                    {
                        totals[item.ItemId] =
                            current +
                            item.Count;
                    }
                }
                catch (OverflowException)
                {
                    result.Status =
                        "背包數量加總 overflow，mapping 不可信。";

                    return result;
                }
            }

            result.UniqueObjectIds =
                objectIds.Count;

            if (inventory.Items.Count > 0 &&
                objectIds.Count !=
                    inventory.Items.Count)
            {
                result.Status =
                    "ObjectId 有重複；Inventory mapping 尚不可信。";

                return result;
            }

            if (expected == null ||
                expected.Count == 0)
            {
                result.Status =
                    "InventoryBridge 可讀，但尚未提供任何已知 itemId=count 驗證資料。";

                return result;
            }

            foreach (var item in expected)
            {
                long actual;
                totals.TryGetValue(
                    item.ItemId,
                    out actual);

                if (actual != item.Count)
                {
                    result.Mismatches.Add(
                        new InventoryValidationMismatch
                        {
                            ItemId =
                                item.ItemId,

                            Expected =
                                item.Count,

                            Actual =
                                actual
                        });
                }
            }

            result.Passed =
                result.Mismatches.Count == 0;

            result.Status =
                result.Passed
                    ? "WP6 背包內容核對 PASS（本 session）。"
                    : "背包內容不符，請檢查 mapping 或輸入值。";

            return result;
        }

        public static string FormatExpectations(
            IList<InventoryExpectation> list)
        {
            var sb =
                new StringBuilder();

            if (list == null)
                return "";

            foreach (var item in list)
            {
                sb.AppendLine(
                    item.ItemId +
                    "=" +
                    item.Count);
            }

            return sb.ToString();
        }
    }
}
