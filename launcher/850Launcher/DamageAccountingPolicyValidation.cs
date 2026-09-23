using System;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class DamageAccountingPolicyValidation
    {
        public static void Run(string appDir)
        {
            try
            {
                var totals = new DamageTotals();
                DamageAccountingPolicy.Add(totals, DamageKind.PhysicalAttack, 120);
                DamageAccountingPolicy.Add(totals, DamageKind.MagicWeaponProc, 65);
                DamageAccountingPolicy.Add(totals, DamageKind.MagicSpell, 40);
                DamageAccountingPolicy.Add(totals, DamageKind.PhysicalSkill, 30);

                var pass =
                    totals.PhysicalDamage == 150 &&
                    totals.MagicDamage == 105 &&
                    totals.TotalDamage == 255;

                var procOnly = new DamageTotals();
                DamageAccountingPolicy.Add(procOnly, DamageKind.MagicWeaponProc, 65);
                pass = pass &&
                    procOnly.PhysicalDamage == 0 &&
                    procOnly.MagicDamage == 65 &&
                    procOnly.TotalDamage == 65;

                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("MODE=DAMAGE_ACCOUNTING_POLICY_VALIDATION");
                sb.AppendLine("SHOW_DAMAGE_MASTER_SWITCH=Extend.ShowDamage");
                sb.AppendLine("PHYSICAL_EXPECTED=150");
                sb.AppendLine("PHYSICAL_ACTUAL=" + totals.PhysicalDamage);
                sb.AppendLine("MAGIC_EXPECTED=105");
                sb.AppendLine("MAGIC_ACTUAL=" + totals.MagicDamage);
                sb.AppendLine("TOTAL_EXPECTED=255");
                sb.AppendLine("TOTAL_ACTUAL=" + totals.TotalDamage);
                sb.AppendLine("MAGIC_WEAPON_PROC_ONLY_PHYSICAL=" + procOnly.PhysicalDamage);
                sb.AppendLine("MAGIC_WEAPON_PROC_ONLY_MAGIC=" + procOnly.MagicDamage);
                sb.AppendLine("MAGIC_WEAPON_PROC_DOUBLE_COUNT=" + (procOnly.PhysicalDamage != 0 ? 1 : 0));
                sb.AppendLine("STATUS=" + (pass ? "PASS" : "FAIL"));
                sb.AppendLine("CLIENT_MEMORY_WRITE=NO");

                File.WriteAllText(
                    Path.Combine(appDir, "auto_damage_accounting_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch (Exception ex)
            {
                try
                {
                    File.WriteAllText(
                        Path.Combine(appDir, "auto_damage_accounting_evidence.txt"),
                        "TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine +
                        "STATUS=ERROR" + Environment.NewLine +
                        "ERROR=" + ex.GetType().Name + ": " + ex.Message + Environment.NewLine +
                        "CLIENT_MEMORY_WRITE=NO" + Environment.NewLine,
                        new UTF8Encoding(false));
                }
                catch { }
            }
        }
    }
}
