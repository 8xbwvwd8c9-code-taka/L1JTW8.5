using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class AutoPotionTickResult
    {
        public bool TriggerConditionMet;
        public bool ItemFound;
        public bool UseAttempted;
        public bool UseSucceeded;
        public uint ObjectId;
        public int ItemId;
        public string Status = "";
    }

    internal sealed class AutoPotionController
    {
        private readonly HelperSettings _settings;
        private readonly IItemUseBridge _itemUse;
        private DateTime _lastUseUtc = DateTime.MinValue;

        public AutoPotionController(
            HelperSettings settings,
            IItemUseBridge itemUse)
        {
            _settings = settings;
            _itemUse = itemUse;
        }

        public AutoPotionTickResult Tick(RuntimeSnapshot snapshot)
        {
            var result = new AutoPotionTickResult();

            if (snapshot == null)
            {
                result.Status = "Runtime snapshot 不存在。";
                return result;
            }

            var decision = AutoPotionPolicy.Evaluate(
                _settings,
                snapshot.CurrentHp,
                snapshot.MaxHp);

            result.TriggerConditionMet = decision.ShouldUse;

            if (!decision.ShouldUse)
            {
                result.Status = decision.Reason;
                return result;
            }

            var ids = _settings.GetPotionItemIds();
            if (ids.Count == 0)
            {
                result.Status = "喝水條件已成立，但尚未選擇藥水道具。";
                return result;
            }

            InventoryItem selected = null;

            foreach (var wantedId in ids)
            {
                foreach (var item in snapshot.Items)
                {
                    if (item.ItemId != wantedId)
                        continue;

                    if (item.Count <= 0)
                        continue;

                    selected = item;
                    break;
                }

                if (selected != null)
                    break;
            }

            if (selected == null)
            {
                result.Status = "喝水條件已成立，但背包中找不到設定的藥水。";
                return result;
            }

            result.ItemFound = true;
            result.ObjectId = selected.ObjectId;
            result.ItemId = selected.ItemId;

            var cooldown = _settings.PotionCooldownMs;
            if (cooldown < 50) cooldown = 50;
            if (cooldown > 10000) cooldown = 10000;

            var elapsed = DateTime.UtcNow - _lastUseUtc;
            if (elapsed.TotalMilliseconds < cooldown)
            {
                result.Status =
                    "喝水條件已成立；冷卻中，剩餘約 " +
                    Math.Max(0, cooldown - (int)elapsed.TotalMilliseconds) +
                    " ms。";
                return result;
            }

            if (_itemUse == null || !_itemUse.IsMapped)
            {
                result.Status =
                    "喝水條件已成立；已找到 ItemId=" + selected.ItemId +
                    " / ObjectId=" + selected.ObjectId +
                    "，但 WP7 UseItem bridge 尚未驗證，因此不送出動作。";
                return result;
            }

            result.UseAttempted = true;

            var useResult = _itemUse.UseObject(selected.ObjectId);
            result.UseSucceeded = useResult.Success;
            result.Status = useResult.Status;

            if (useResult.Success)
            {
                _lastUseUtc = DateTime.UtcNow;

                if (selected.Count > 0)
                    selected.Count--;
            }

            return result;
        }
    }
}
