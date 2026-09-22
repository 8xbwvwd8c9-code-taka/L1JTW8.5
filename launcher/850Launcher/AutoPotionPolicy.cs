using System;

namespace L1JTW850Launcher
{
    internal sealed class PotionDecision
    {
        public bool ShouldUse;
        public string Reason = "";
        public int CurrentHp;
        public int MaxHp;
        public int ThresholdValue;
        public bool PercentMode;
    }

    internal static class AutoPotionPolicy
    {
        public static PotionDecision Evaluate(
            HelperSettings settings,
            int? currentHp,
            int? maxHp)
        {
            var decision = new PotionDecision
            {
                ShouldUse = false,
                PercentMode = settings != null && settings.PotionUsePercent,
                CurrentHp = currentHp ?? 0,
                MaxHp = maxHp ?? 0
            };

            if (settings == null)
            {
                decision.Reason = "設定不存在。";
                return decision;
            }

            if (!settings.AutoPotion)
            {
                decision.Reason = "自動喝水未啟用。";
                return decision;
            }

            if (!currentHp.HasValue || !maxHp.HasValue)
            {
                decision.Reason = "HP runtime mapping 尚未就緒。";
                return decision;
            }

            if (currentHp.Value <= 0 || maxHp.Value <= 0)
            {
                decision.Reason = "HP 數值無效。";
                return decision;
            }

            if (currentHp.Value > maxHp.Value)
            {
                decision.Reason = "目前 HP 大於最大 HP，mapping 尚未可信。";
                return decision;
            }

            if (settings.PotionUsePercent)
            {
                var percent = settings.PotionHpPercent;
                if (percent < 1) percent = 1;
                if (percent > 100) percent = 100;

                decision.ThresholdValue = percent;

                // Avoid integer overflow: current*100 and max*percent use long.
                var left = (long)currentHp.Value * 100L;
                var right = (long)maxHp.Value * percent;

                decision.ShouldUse = left <= right;
                decision.Reason = decision.ShouldUse
                    ? "HP 已低於/等於百分比門檻。"
                    : "HP 高於百分比門檻。";

                return decision;
            }

            var exact = settings.PotionHpExact;
            if (exact < 1) exact = 1;

            decision.ThresholdValue = exact;
            decision.ShouldUse = currentHp.Value <= exact;
            decision.Reason = decision.ShouldUse
                ? "HP 已低於/等於精準門檻。"
                : "HP 高於精準門檻。";

            return decision;
        }
    }
}
