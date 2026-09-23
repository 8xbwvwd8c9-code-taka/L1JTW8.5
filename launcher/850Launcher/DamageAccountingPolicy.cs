using System;

namespace L1JTW850Launcher
{
    internal enum DamageKind
    {
        PhysicalAttack = 0,
        PhysicalSkill = 1,
        MagicSpell = 2,
        MagicSkill = 3,
        MagicWeaponProc = 4
    }

    internal sealed class DamageTotals
    {
        public long PhysicalDamage;
        public long MagicDamage;

        public long TotalDamage
        {
            get { return PhysicalDamage + MagicDamage; }
        }

        public void Reset()
        {
            PhysicalDamage = 0;
            MagicDamage = 0;
        }
    }

    internal static class DamageAccountingPolicy
    {
        // Contract:
        // - Normal/physical attacks count only toward PhysicalDamage.
        // - Spell/skill magic counts only toward MagicDamage.
        // - Magic-weapon proc damage counts only toward MagicDamage and MUST NOT
        //   be duplicated into PhysicalDamage even when it was triggered by a normal hit.
        // - TotalDamage is derived as PhysicalDamage + MagicDamage.
        public static void Add(DamageTotals totals, DamageKind kind, int amount)
        {
            if (totals == null || amount <= 0)
                return;

            switch (kind)
            {
                case DamageKind.PhysicalAttack:
                case DamageKind.PhysicalSkill:
                    totals.PhysicalDamage += amount;
                    break;

                case DamageKind.MagicSpell:
                case DamageKind.MagicSkill:
                case DamageKind.MagicWeaponProc:
                    totals.MagicDamage += amount;
                    break;

                default:
                    throw new ArgumentOutOfRangeException("kind");
            }
        }
    }
}
