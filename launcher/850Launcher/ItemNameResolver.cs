using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class ItemNameResolver
    {
        private readonly Dictionary<int, string> _names;

        public ItemNameResolver(string appDir)
        {
            _names = ItemCatalogLoader.Load(appDir);
        }

        public string Resolve(int itemId)
        {
            string name;
            return _names.TryGetValue(itemId, out name)
                ? name
                : "Item#" + itemId;
        }
    }
}
