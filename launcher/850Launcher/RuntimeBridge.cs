using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSnapshot
    {
        public bool Connected;
        public string Status = "UNMAPPED";
        public int? CurrentHp = null;
        public int? MaxHp = null;
        public int? CurrentMp = null;
        public int? MaxMp = null;
        public readonly List<InventoryItem> Items = new List<InventoryItem>();
    }

    internal sealed class InventoryItem
    {
        public uint ObjectId = 0;
        public int ItemId = 0;
        public long Count = 0;
        public string Name = "";
        public int? Enchant = null;
        public bool? Equipped = null;
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
