namespace L1JTW850Launcher
{
    internal sealed class ItemUseResult
    {
        public bool Success;
        public string Status = "";
    }

    internal interface IItemUseBridge
    {
        bool IsMapped { get; }
        ItemUseResult UseObject(uint objectId);
    }

    internal sealed class UnmappedItemUseBridge : IItemUseBridge
    {
        public bool IsMapped
        {
            get { return false; }
        }

        public ItemUseResult UseObject(uint objectId)
        {
            return new ItemUseResult
            {
                Success = false,
                Status = "WP7 尚未完成：850 native UseItem/packet path 未證明。"
            };
        }
    }
}
