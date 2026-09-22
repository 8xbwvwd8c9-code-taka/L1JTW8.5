using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class InventoryReadResult
    {
        public bool Mapped;
        public string Status = "UNMAPPED";
        public readonly List<InventoryItem> Items = new List<InventoryItem>();
    }

    internal interface IInventoryBridge
    {
        InventoryReadResult Read(RuntimeSnapshot runtime);
    }

    internal sealed class UnmappedInventoryBridge : IInventoryBridge
    {
        public InventoryReadResult Read(RuntimeSnapshot runtime)
        {
            return new InventoryReadResult
            {
                Mapped = false,
                Status = "WP5/WP6 尚未完成：InventoryBridge 未啟用。"
            };
        }
    }
}
