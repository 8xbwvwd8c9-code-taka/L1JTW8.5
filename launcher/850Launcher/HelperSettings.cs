using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class HelperSettings
    {
        public bool AutoPotion;
        public bool PotionUsePercent = true;
        public int PotionHpPercent = 70;
        public int PotionHpExact = 1000;
        public string PotionItemIds = "";
        public int PotionCooldownMs = 350;
        public bool AutoBuff;
        public bool AutoTransform;
        public bool AutoAntidote;
        public bool AutoRepair;
        public bool AutoFood;
        public bool ShowClock = true;
        public bool ShowDamage;
        public int TimerSeconds = 60;

        public List<int> GetPotionItemIds()
        {
            var result = new List<int>();

            if (string.IsNullOrWhiteSpace(PotionItemIds))
                return result;

            var parts = PotionItemIds.Split(new[] { ',', ';', ' ' }, StringSplitOptions.RemoveEmptyEntries);
            foreach (var raw in parts)
            {
                int itemId;
                if (!int.TryParse(raw.Trim(), out itemId) || itemId <= 0)
                    continue;

                if (!result.Contains(itemId))
                    result.Add(itemId);
            }

            return result;
        }

        public static HelperSettings Load(string path)
        {
            var ini = IniDocument.Load(path);
            return new HelperSettings
            {
                AutoPotion = ini.GetBool("Potion", "Enabled", false),
                PotionUsePercent = ini.GetBool("Potion", "UsePercent", true),
                PotionHpPercent = ini.GetInt("Potion", "HPPercent", 70),
                PotionHpExact = ini.GetInt("Potion", "HPExact", 1000),
                PotionItemIds = ini.Get("Potion", "ItemIds", ""),
                PotionCooldownMs = ini.GetInt("Potion", "CooldownMs", 350),
                AutoBuff = ini.GetBool("State", "AutoBuff", false),
                AutoTransform = ini.GetBool("Special", "AutoTransform", false),
                AutoAntidote = ini.GetBool("Special", "AutoAntidote", false),
                AutoRepair = ini.GetBool("Extend", "AutoRepair", false),
                AutoFood = ini.GetBool("Extend", "AutoFood", false),
                ShowClock = ini.GetBool("Extend", "ShowClock", true),
                ShowDamage = ini.GetBool("Extend", "ShowDamage", false),
                TimerSeconds = ini.GetInt("Timer", "Seconds", 60)
            };
        }

        public void Save(string path)
        {
            var ini = new IniDocument();
            ini.Set("Potion", "Enabled", AutoPotion ? 1 : 0);
            ini.Set("Potion", "UsePercent", PotionUsePercent ? 1 : 0);
            ini.Set("Potion", "HPPercent", PotionHpPercent);
            ini.Set("Potion", "HPExact", PotionHpExact);
            ini.Set("Potion", "ItemIds", PotionItemIds ?? "");
            ini.Set("Potion", "CooldownMs", PotionCooldownMs);
            ini.Set("State", "AutoBuff", AutoBuff ? 1 : 0);
            ini.Set("Special", "AutoTransform", AutoTransform ? 1 : 0);
            ini.Set("Special", "AutoAntidote", AutoAntidote ? 1 : 0);
            ini.Set("Extend", "AutoRepair", AutoRepair ? 1 : 0);
            ini.Set("Extend", "AutoFood", AutoFood ? 1 : 0);
            ini.Set("Extend", "ShowClock", ShowClock ? 1 : 0);
            ini.Set("Extend", "ShowDamage", ShowDamage ? 1 : 0);
            ini.Set("Timer", "Seconds", TimerSeconds);
            ini.Save(path);
        }
    }
}
