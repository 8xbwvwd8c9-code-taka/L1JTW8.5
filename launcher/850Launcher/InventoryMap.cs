using System;
using System.Globalization;
using System.IO;

namespace L1JTW850Launcher
{
    internal enum InventoryCollectionMode
    {
        Unmapped,
        PointerArray,
        LinkedList,
        Contiguous
    }

    internal sealed class InventoryMap
    {
        public InventoryCollectionMode Mode = InventoryCollectionMode.Unmapped;
        public RuntimeFieldMap Root;

        public int MaxItems = 500;

        public int CountOffset;
        public int DataPointerOffset;
        public int EntryStride = 4;
        public int EntryPointerOffset;

        public int HeadPointerOffset;
        public int NextPointerOffset;
        public int RecordPointerOffset;
        public bool RecordInline;

        public int RecordStride;

        public int ObjectIdOffset;
        public int ObjectIdWidth = 4;
        public int ItemIdOffset;
        public int ItemIdWidth = 4;
        public int CountFieldOffset;
        public int CountWidth = 4;
        public int EnchantOffset;
        public int EnchantWidth = 2;
        public int EquippedOffset;
        public int EquippedWidth = 1;

        public static InventoryMap Load(string path)
        {
            if (!File.Exists(path))
                return new InventoryMap();

            var ini = IniDocument.Load(path);
            var map = new InventoryMap();

            map.Mode = ParseMode(ini.Get("Collection", "Mode", "UNMAPPED"));

            var rootText = ini.Get("Collection", "Root", "");
            if (!string.IsNullOrWhiteSpace(rootText))
                map.Root = RuntimeFieldMap.Parse("InventoryRoot", rootText);

            map.MaxItems = Clamp(
                ParseInt(ini.Get("Collection", "MaxItems", "500"), 500),
                1,
                5000);

            map.CountOffset = ParseOffset(ini.Get("Collection", "CountOffset", "0"));
            map.DataPointerOffset = ParseOffset(ini.Get("Collection", "DataPointerOffset", "0"));
            map.EntryStride = Clamp(
                ParseInt(ini.Get("Collection", "EntryStride", "4"), 4),
                1,
                4096);
            map.EntryPointerOffset = ParseOffset(ini.Get("Collection", "EntryPointerOffset", "0"));

            map.HeadPointerOffset = ParseOffset(ini.Get("Collection", "HeadPointerOffset", "0"));
            map.NextPointerOffset = ParseOffset(ini.Get("Collection", "NextPointerOffset", "0"));
            map.RecordPointerOffset = ParseOffset(ini.Get("Collection", "RecordPointerOffset", "0"));
            map.RecordInline = ParseBool(ini.Get("Collection", "RecordInline", "0"));

            map.RecordStride = Clamp(
                ParseInt(ini.Get("Collection", "RecordStride", "0"), 0),
                0,
                65536);

            map.ObjectIdOffset = ParseOffset(ini.Get("Record", "ObjectIdOffset", "0"));
            map.ObjectIdWidth = ParseWidth(ini.Get("Record", "ObjectIdWidth", "4"), 4);
            map.ItemIdOffset = ParseOffset(ini.Get("Record", "ItemIdOffset", "0"));
            map.ItemIdWidth = ParseWidth(ini.Get("Record", "ItemIdWidth", "4"), 4);
            map.CountFieldOffset = ParseOffset(ini.Get("Record", "CountOffset", "0"));
            map.CountWidth = ParseWidth(ini.Get("Record", "CountWidth", "4"), 4);
            map.EnchantOffset = ParseOffset(ini.Get("Record", "EnchantOffset", "0"));
            map.EnchantWidth = ParseWidth(ini.Get("Record", "EnchantWidth", "2"), 2);
            map.EquippedOffset = ParseOffset(ini.Get("Record", "EquippedOffset", "0"));
            map.EquippedWidth = ParseWidth(ini.Get("Record", "EquippedWidth", "1"), 1);

            map.Validate();
            return map;
        }

        public void Validate()
        {
            if (Mode == InventoryCollectionMode.Unmapped)
                return;

            if (Root == null)
                throw new InvalidDataException("Inventory Collection.Root 尚未設定。");

            if (Mode == InventoryCollectionMode.PointerArray)
            {
                if (EntryStride <= 0)
                    throw new InvalidDataException("PointerArray EntryStride 必須大於 0。");
            }
            else if (Mode == InventoryCollectionMode.LinkedList)
            {
                if (NextPointerOffset == RecordPointerOffset && !RecordInline)
                {
                    // Allowed in theory, but almost certainly a bad mapping.
                }
            }
            else if (Mode == InventoryCollectionMode.Contiguous)
            {
                if (RecordStride <= 0)
                    throw new InvalidDataException("Contiguous RecordStride 必須大於 0。");
            }
        }

        private static InventoryCollectionMode ParseMode(string text)
        {
            text = (text ?? "").Trim();

            if (text.Equals("UNMAPPED", StringComparison.OrdinalIgnoreCase) ||
                text.Length == 0)
                return InventoryCollectionMode.Unmapped;

            if (text.Equals("POINTER_ARRAY", StringComparison.OrdinalIgnoreCase))
                return InventoryCollectionMode.PointerArray;

            if (text.Equals("LINKED_LIST", StringComparison.OrdinalIgnoreCase))
                return InventoryCollectionMode.LinkedList;

            if (text.Equals("CONTIGUOUS", StringComparison.OrdinalIgnoreCase))
                return InventoryCollectionMode.Contiguous;

            throw new InvalidDataException("未知 Inventory Collection.Mode：" + text);
        }

        private static int ParseWidth(string text, int fallback)
        {
            var value = ParseInt(text, fallback);
            if (value != 1 && value != 2 && value != 4 && value != 8)
                throw new InvalidDataException("欄位寬度只支援 1/2/4/8 bytes。");
            return value;
        }

        private static bool ParseBool(string text)
        {
            text = (text ?? "").Trim();
            return text == "1" ||
                   text.Equals("true", StringComparison.OrdinalIgnoreCase) ||
                   text.Equals("yes", StringComparison.OrdinalIgnoreCase);
        }

        private static int ParseInt(string text, int fallback)
        {
            if (string.IsNullOrWhiteSpace(text))
                return fallback;

            text = text.Trim();

            int value;
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            {
                if (!int.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                    throw new InvalidDataException("整數格式錯誤：" + text);
                return value;
            }

            if (!int.TryParse(text, out value))
                throw new InvalidDataException("整數格式錯誤：" + text);

            return value;
        }

        private static int ParseOffset(string text)
        {
            if (string.IsNullOrWhiteSpace(text))
                return 0;

            text = text.Trim().Replace("+", "");
            var negative = text.StartsWith("-");
            if (negative)
                text = text.Substring(1);

            var value = ParseInt(text, 0);
            return negative ? -value : value;
        }

        private static int Clamp(int value, int min, int max)
        {
            if (value < min) return min;
            if (value > max) return max;
            return value;
        }
    }
}
