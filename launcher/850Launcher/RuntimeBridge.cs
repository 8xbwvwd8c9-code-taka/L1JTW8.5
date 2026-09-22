using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSnapshot
    {
        public bool Connected;
        public string Status = "UNMAPPED";
        public int? CurrentHp;
        public int? MaxHp;
        public int? CurrentMp;
        public int? MaxMp;
        public readonly List<InventoryItem> Items = new List<InventoryItem>();
    }

    internal sealed class InventoryItem
    {
        public uint ObjectId;
        public int ItemId;
        public long Count;
        public string Name = "";
        public int? Enchant;
        public bool? Equipped;
    }

    internal interface IRuntimeBridge
    {
        RuntimeSnapshot Read();
    }

    internal sealed class UnmappedRuntimeBridge : IRuntimeBridge
    {
        public RuntimeSnapshot Read()
        {
            return new RuntimeSnapshot
            {
                Connected = false,
                Status = "UNMAPPED: WP3/WP4/WP5 runtime addresses are not proven yet."
            };
        }
    }
}
