using System;
using System.Collections.Generic;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class MappedInventoryBridge : IInventoryBridge
    {
        private readonly string _mapPath;
        private readonly ItemNameResolver _names;

        public MappedInventoryBridge(string appDir)
        {
            _mapPath = Path.Combine(appDir, "inventory-map.ini");
            _names = new ItemNameResolver(appDir);
        }

        public InventoryReadResult Read(RuntimeSnapshot runtime)
        {
            var result = new InventoryReadResult();

            if (runtime == null || !runtime.Connected)
            {
                result.Status = "遊戲程序尚未連接。";
                return result;
            }

            InventoryMap map;
            try
            {
                map = InventoryMap.Load(_mapPath);
            }
            catch (Exception ex)
            {
                result.Status = "inventory-map.ini 格式錯誤：" + ex.Message;
                return result;
            }

            if (map.Mode == InventoryCollectionMode.Unmapped)
            {
                result.Status = "WP5/WP6 尚未完成：Inventory Collection.Mode=UNMAPPED。";
                return result;
            }

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                {
                    result.Status = "InventoryBridge 連接失敗：" + error;
                    return result;
                }

                IntPtr root;
                if (!TryResolveAddress(
                    probe,
                    runtime.ModuleBase,
                    map.Root,
                    out root,
                    out error))
                {
                    result.Status = "Inventory root 解析失敗：" + error;
                    return result;
                }

                var temp = new List<InventoryItem>();

                bool ok;
                if (map.Mode == InventoryCollectionMode.PointerArray)
                {
                    ok = ReadPointerArray(probe, root, map, temp, out error);
                }
                else if (map.Mode == InventoryCollectionMode.LinkedList)
                {
                    ok = ReadLinkedList(probe, root, map, temp, out error);
                }
                else if (map.Mode == InventoryCollectionMode.Contiguous)
                {
                    ok = ReadContiguous(probe, root, map, temp, out error);
                }
                else
                {
                    error = "Inventory collection mode 尚未支援。";
                    ok = false;
                }

                if (!ok)
                {
                    result.Status = "InventoryBridge 讀取失敗：" + error;
                    return result;
                }

                foreach (var item in temp)
                    result.Items.Add(item);

                result.Mapped = true;
                result.Status =
                    "InventoryBridge 已映射，Mode=" + map.Mode +
                    "，Items=" + result.Items.Count + "。";

                return result;
            }
        }

        private bool ReadPointerArray(
            RuntimeMemoryProbe probe,
            IntPtr root,
            InventoryMap map,
            List<InventoryItem> output,
            out string error)
        {
            error = "";

            int count;
            if (!ReadCollectionCount(probe, root, map, out count, out error))
                return false;

            uint dataPointer;
            if (!probe.TryReadUInt32(
                Add(root, map.DataPointerOffset),
                out dataPointer,
                out error))
                return false;

            if (count == 0)
                return true;

            if (dataPointer < 0x10000)
            {
                error = "PointerArray data pointer 無效。";
                return false;
            }

            for (var i = 0; i < count; i++)
            {
                var entryAddress =
                    (long)dataPointer +
                    ((long)i * map.EntryStride) +
                    map.EntryPointerOffset;

                if (entryAddress < 0x10000 || entryAddress > uint.MaxValue)
                {
                    error = "PointerArray entry 位址超出 x86 範圍。";
                    return false;
                }

                uint recordPointer;
                if (!probe.TryReadUInt32(
                    new IntPtr(entryAddress),
                    out recordPointer,
                    out error))
                    return false;

                if (recordPointer < 0x10000)
                {
                    error = "PointerArray record pointer 無效，index=" + i;
                    return false;
                }

                InventoryItem item;
                if (!TryReadRecord(
                    probe,
                    new IntPtr((long)recordPointer),
                    map,
                    out item,
                    out error))
                {
                    error = "index=" + i + "：" + error;
                    return false;
                }

                output.Add(item);
            }

            return true;
        }

        private bool ReadContiguous(
            RuntimeMemoryProbe probe,
            IntPtr root,
            InventoryMap map,
            List<InventoryItem> output,
            out string error)
        {
            error = "";

            int count;
            if (!ReadCollectionCount(probe, root, map, out count, out error))
                return false;

            uint dataPointer;
            if (!probe.TryReadUInt32(
                Add(root, map.DataPointerOffset),
                out dataPointer,
                out error))
                return false;

            if (count == 0)
                return true;

            if (dataPointer < 0x10000)
            {
                error = "Contiguous data pointer 無效。";
                return false;
            }

            for (var i = 0; i < count; i++)
            {
                var recordAddress =
                    (long)dataPointer +
                    ((long)i * map.RecordStride);

                if (recordAddress < 0x10000 || recordAddress > uint.MaxValue)
                {
                    error = "Contiguous record 位址超出 x86 範圍。";
                    return false;
                }

                InventoryItem item;
                if (!TryReadRecord(
                    probe,
                    new IntPtr(recordAddress),
                    map,
                    out item,
                    out error))
                {
                    error = "index=" + i + "：" + error;
                    return false;
                }

                output.Add(item);
            }

            return true;
        }

        private bool ReadLinkedList(
            RuntimeMemoryProbe probe,
            IntPtr root,
            InventoryMap map,
            List<InventoryItem> output,
            out string error)
        {
            error = "";

            uint node;
            if (!probe.TryReadUInt32(
                Add(root, map.HeadPointerOffset),
                out node,
                out error))
                return false;

            if (node == 0)
                return true;

            var visited = new HashSet<uint>();

            while (node != 0)
            {
                if (output.Count >= map.MaxItems)
                {
                    error = "LinkedList 達到 MaxItems，可能有循環或 mapping 錯誤。";
                    return false;
                }

                if (node < 0x10000)
                {
                    error = "LinkedList node pointer 無效。";
                    return false;
                }

                if (!visited.Add(node))
                {
                    error = "LinkedList 偵測到循環。";
                    return false;
                }

                IntPtr recordAddress;

                if (map.RecordInline)
                {
                    recordAddress = Add(
                        new IntPtr((long)node),
                        map.RecordPointerOffset);
                }
                else
                {
                    uint recordPointer;
                    if (!probe.TryReadUInt32(
                        Add(new IntPtr((long)node), map.RecordPointerOffset),
                        out recordPointer,
                        out error))
                        return false;

                    if (recordPointer < 0x10000)
                    {
                        error = "LinkedList record pointer 無效。";
                        return false;
                    }

                    recordAddress = new IntPtr((long)recordPointer);
                }

                InventoryItem item;
                if (!TryReadRecord(
                    probe,
                    recordAddress,
                    map,
                    out item,
                    out error))
                    return false;

                output.Add(item);

                uint next;
                if (!probe.TryReadUInt32(
                    Add(new IntPtr((long)node), map.NextPointerOffset),
                    out next,
                    out error))
                    return false;

                node = next;
            }

            return true;
        }

        private static bool ReadCollectionCount(
            RuntimeMemoryProbe probe,
            IntPtr root,
            InventoryMap map,
            out int count,
            out string error)
        {
            count = 0;

            if (!probe.TryReadInt32(
                Add(root, map.CountOffset),
                out count,
                out error))
                return false;

            if (count < 0 || count > map.MaxItems)
            {
                error =
                    "collection count=" + count +
                    " 超出 0.." + map.MaxItems + "，mapping 可能錯誤。";
                return false;
            }

            return true;
        }

        private bool TryReadRecord(
            RuntimeMemoryProbe probe,
            IntPtr record,
            InventoryMap map,
            out InventoryItem item,
            out string error)
        {
            item = null;
            error = "";

            long objectIdRaw;
            long itemIdRaw;
            long countRaw;
            long enchantRaw;
            long equippedRaw;

            if (!TryReadUnsignedWidth(
                probe,
                Add(record, map.ObjectIdOffset),
                map.ObjectIdWidth,
                out objectIdRaw,
                out error))
                return false;

            if (!TryReadWidth(
                probe,
                Add(record, map.ItemIdOffset),
                map.ItemIdWidth,
                out itemIdRaw,
                out error))
                return false;

            if (!TryReadWidth(
                probe,
                Add(record, map.CountFieldOffset),
                map.CountWidth,
                out countRaw,
                out error))
                return false;

            if (!TryReadWidth(
                probe,
                Add(record, map.EnchantOffset),
                map.EnchantWidth,
                out enchantRaw,
                out error))
                return false;

            if (!TryReadWidth(
                probe,
                Add(record, map.EquippedOffset),
                map.EquippedWidth,
                out equippedRaw,
                out error))
                return false;

            if (objectIdRaw <= 0 || objectIdRaw > uint.MaxValue)
            {
                error = "ObjectId 值不合理：" + objectIdRaw;
                return false;
            }

            if (itemIdRaw <= 0 || itemIdRaw > int.MaxValue)
            {
                error = "ItemId 值不合理：" + itemIdRaw;
                return false;
            }

            if (countRaw < 0)
            {
                error = "Count 值不合理：" + countRaw;
                return false;
            }

            if (enchantRaw < int.MinValue || enchantRaw > int.MaxValue)
            {
                error = "Enchant 超出 int 範圍。";
                return false;
            }

            item = new InventoryItem
            {
                ObjectId = (uint)objectIdRaw,
                ItemId = (int)itemIdRaw,
                Count = countRaw,
                Name = _names.Resolve((int)itemIdRaw),
                Enchant = (int)enchantRaw,
                Equipped = equippedRaw != 0
            };

            return true;
        }

        private static bool TryResolveAddress(
            RuntimeMemoryProbe probe,
            IntPtr moduleBase,
            RuntimeFieldMap map,
            out IntPtr address,
            out string error)
        {
            address = IntPtr.Zero;
            error = "";

            if (map == null)
            {
                error = "root map 為空。";
                return false;
            }

            var baseAddress =
                moduleBase.ToInt64() +
                (uint)map.BaseRva;

            if (baseAddress < 0x10000 || baseAddress > uint.MaxValue)
            {
                error = "root base RVA 解析後超出 x86 範圍。";
                return false;
            }

            if (!map.IsPointerChain)
            {
                address = new IntPtr(baseAddress);
                return true;
            }

            if (map.Offsets.Count == 0)
            {
                error = "root pointer chain 沒有 offset。";
                return false;
            }

            uint pointer;
            if (!probe.TryReadUInt32(
                new IntPtr(baseAddress),
                out pointer,
                out error))
                return false;

            if (pointer < 0x10000)
            {
                error = "root pointer 為 NULL/無效。";
                return false;
            }

            for (var i = 0; i < map.Offsets.Count - 1; i++)
            {
                var nextAddress =
                    (long)pointer +
                    map.Offsets[i];

                if (nextAddress < 0x10000 || nextAddress > uint.MaxValue)
                {
                    error = "root pointer chain 中間位址超出 x86 範圍。";
                    return false;
                }

                if (!probe.TryReadUInt32(
                    new IntPtr(nextAddress),
                    out pointer,
                    out error))
                    return false;

                if (pointer < 0x10000)
                {
                    error = "root pointer chain 遇到 NULL/無效 pointer。";
                    return false;
                }
            }

            var finalAddress =
                (long)pointer +
                map.Offsets[map.Offsets.Count - 1];

            if (finalAddress < 0x10000 || finalAddress > uint.MaxValue)
            {
                error = "root final address 超出 x86 範圍。";
                return false;
            }

            address = new IntPtr(finalAddress);
            return true;
        }

        private static bool TryReadUnsignedWidth(
            RuntimeMemoryProbe probe,
            IntPtr address,
            int width,
            out long value,
            out string error)
        {
            value = 0;

            if (width == 1)
            {
                byte v;
                if (!probe.TryReadByte(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            if (width == 2)
            {
                short v;
                if (!probe.TryReadInt16(address, out v, out error))
                    return false;
                value = unchecked((ushort)v);
                return true;
            }

            if (width == 4)
            {
                uint v;
                if (!probe.TryReadUInt32(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            error = "ObjectId 不支援寬度：" + width;
            return false;
        }

        private static bool TryReadWidth(
            RuntimeMemoryProbe probe,
            IntPtr address,
            int width,
            out long value,
            out string error)
        {
            value = 0;

            if (width == 1)
            {
                byte v;
                if (!probe.TryReadByte(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            if (width == 2)
            {
                short v;
                if (!probe.TryReadInt16(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            if (width == 4)
            {
                int v;
                if (!probe.TryReadInt32(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            if (width == 8)
            {
                long v;
                if (!probe.TryReadInt64(address, out v, out error))
                    return false;
                value = v;
                return true;
            }

            error = "不支援欄位寬度：" + width;
            return false;
        }

        private static IntPtr Add(IntPtr address, int offset)
        {
            return new IntPtr(address.ToInt64() + offset);
        }
    }
}
