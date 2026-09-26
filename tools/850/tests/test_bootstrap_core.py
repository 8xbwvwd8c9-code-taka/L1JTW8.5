import importlib.util
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
BOOTSTRAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "bootstrap_core.py"
PACKAGE_MAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "package_map.py"


def load(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class BootstrapCoreTests(unittest.TestCase):
    def entries(self):
        pm = load(PACKAGE_MAP_PATH, "fast_dev_package_map_fixture")
        rules = {
            "families": {
                "ao": {"package": "l1j/server/datatables", "category": "datatables"},
                "ap": {"package": "l1j/server/model/instance", "category": "model-instance"},
            },
            "overrides": {},
        }
        entries = pm.build_package_map(
            [
                {"Class": "ao.a", "SourceFile": "AccountTable.java"},
                {"Class": "ap.v", "SourceFile": "L1PetInstance.java"},
            ],
            rules,
        )
        pm.validate_package_map(entries)
        return entries

    def collision_entries(self):
        pm = load(PACKAGE_MAP_PATH, "fast_dev_package_map_collision_fixture")
        rules = {
            "families": {
                "aj": {"package": "l1j/server/clientpackets", "category": "clientpackets"},
                "ax": {"package": "l1j/server/model/map", "category": "model-map"},
            },
            "overrides": {},
        }
        entries = pm.build_package_map(
            [
                {"Class": "aj.be", "SourceFile": "C_Login.java"},
                {"Class": "ax.c", "SourceFile": "L1MapArea.java"},
            ],
            rules,
        )
        pm.validate_package_map(entries)
        return entries

    def decompiler_artifact_entries(self):
        pm = load(PACKAGE_MAP_PATH, "fast_dev_package_map_decompiler_fixture")
        rules = {
            "families": {
                "aj": {"package": "l1j/server/clientpackets", "category": "clientpackets"},
                "ao": {"package": "l1j/server/datatables", "category": "datatables"},
                "ap": {"package": "l1j/server/model/instance", "category": "model-instance"},
                "aq": {"package": "l1j/server/model", "category": "model"},
                "au": {"package": "l1j/server/model/inventory", "category": "inventory"},
            },
            "overrides": {},
        }
        entries = pm.build_package_map(
            [
                {"Class": "aj.c", "SourceFile": "C_Amount.java"},
                {"Class": "ao.q", "SourceFile": "ClanTable.java"},
                {"Class": "ap.u", "SourceFile": "L1PcInstance.java"},
                {"Class": "ap.s", "SourceFile": "L1MonsterInstance.java"},
                {"Class": "aq.aa", "SourceFile": "L1Object.java"},
                {"Class": "aq.f", "SourceFile": "L1Character.java"},
                {"Class": "aq.i", "SourceFile": "L1Clan.java"},
                {"Class": "aq.am", "SourceFile": "L1Teleport.java"},
                {"Class": "au.f", "SourceFile": "L1Inventory.java"},
            ],
            rules,
        )
        pm.validate_package_map(entries)
        return entries

    def test_rewrites_package_import_and_fully_qualified_reference(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap")
        entries = self.entries()
        source = (
            "package l1r.ao;\n"
            "import l1r.ap.L1PetInstance;\n"
            "public class AccountTable { l1r.ap.L1PetInstance pet; }\n"
        )
        rewritten = mod.rewrite_java_source(source, entries[0], entries)
        self.assertIn("package l1j.server.datatables;", rewritten)
        self.assertIn("import l1j.server.model.instance.L1PetInstance;", rewritten)
        self.assertIn("l1j.server.model.instance.L1PetInstance pet", rewritten)
        self.assertNotIn("l1r.", rewritten)

    def test_restores_recovery_identity_embedded_inside_non_application_identifier(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_collision")
        entries = self.collision_entries()
        login = next(entry for entry in entries if entry.source_file == "C_Login.java")
        source = (
            "package l1r.aj;\n"
            "import javl1r.ax.L1MapArearypto.Cipher;\n"
            "import l1r.ax.L1MapArea;\n"
            "public class C_Login {\n"
            "  javl1r.ax.L1MapArearypto.Cipher cipher;\n"
            "  l1r.ax.L1MapArea map;\n"
            "}\n"
        )

        rewritten = mod.rewrite_java_source(source, login, entries)

        self.assertIn("import javax.crypto.Cipher;", rewritten)
        self.assertIn("javax.crypto.Cipher cipher;", rewritten)
        self.assertIn("import l1j.server.model.map.L1MapArea;", rewritten)
        self.assertIn("l1j.server.model.map.L1MapArea map;", rewritten)
        self.assertNotIn("javl1j", rewritten)

    def test_restores_donor_backed_l1pc_overload_casts_and_override_metadata(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_l1pc_artifacts")
        entries = self.decompiler_artifact_entries()
        pc = next(entry for entry in entries if entry.source_file == "L1PcInstance.java")
        source = (
            "package l1r.ap;\n"
            "import l1r.aq.L1Character;\n"
            "import l1r.aq.L1Object;\n"
            "public class L1PcInstance extends L1Character {\n"
            "   @Override\n"
            "   public void c(int var1) {}\n"
            "   public void h() {\n"
            "      L1MonsterInstance var4 = null;\n"
            "      if (var4.d(this)) {}\n"
            "   }\n"
            "   public void decoy(L1Character var1) {\n"
            "      if (!this.b(var1) && var1 != null) {}\n"
            "   }\n"
            "   public void a(L1Character var1) {\n"
            "      if (!this.b(var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {}\n"
            "   }\n"
            "}\n"
        )

        rewritten = mod.rewrite_java_source(source, pc, entries)

        self.assertNotIn("@Override\n   public void c(int var1)", rewritten)
        self.assertIn("var4.d((L1Character)this)", rewritten)
        self.assertIn("if (!this.b(var1) && var1 != null)", rewritten)
        self.assertIn(
            "if (!this.b((L1Object)var1) && var1.fp() == this.fp()",
            rewritten,
        )

    def test_restores_donor_backed_l1teleport_subject_generic(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_l1teleport_artifacts")
        entries = self.decompiler_artifact_entries()
        teleport = next(entry for entry in entries if entry.source_file == "L1Teleport.java")
        source = (
            "package l1r.aq;\n"
            "import java.util.HashSet;\n"
            "import l1r.ap.L1PcInstance;\n"
            "public class L1Teleport {\n"
            "   public static void a(L1PcInstance var0) {\n"
            "      HashSet var7 = new HashSet<>();\n"
            "      var7.add(var0);\n"
            "      for (L1PcInstance var17 : var7) { var17.h(); }\n"
            "   }\n"
            "}\n"
        )

        rewritten = mod.rewrite_java_source(source, teleport, entries)

        self.assertIn("HashSet<L1PcInstance> var7 = new HashSet<>();", rewritten)

    def test_restores_donor_backed_c_amount_clan_type_imports(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_c_amount_artifacts")
        entries = self.decompiler_artifact_entries()
        amount = next(entry for entry in entries if entry.source_file == "C_Amount.java")
        source = (
            "package l1r.aj;\n\n"
            "import l1r.ap.L1PcInstance;\n"
            "public class C_Amount {\n"
            "   public void bid(L1PcInstance pc) {\n"
            "      L1Clan clan = ClanTable.a().a(pc.aF());\n"
            "   }\n"
            "}\n"
        )

        rewritten = mod.rewrite_java_source(source, amount, entries)

        self.assertIn("import l1j.server.datatables.ClanTable;", rewritten)
        self.assertIn("import l1j.server.model.L1Clan;", rewritten)
        self.assertIn("L1Clan clan = ClanTable.a().a(pc.aF());", rewritten)

    def test_restores_donor_backed_l1inventory_collection_and_comparator_types(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_l1inventory_artifacts")
        entries = self.decompiler_artifact_entries()
        inventory = next(entry for entry in entries if entry.source_file == "L1Inventory.java")
        source = (
            "package l1r.au;\n"
            "import java.util.ArrayList;\n"
            "import java.util.Comparator;\n"
            "import l1r.ap.L1ItemInstance;\n"
            "public class L1Inventory {\n"
            "   public L1ItemInstance[] f(int var1, int var2) {\n"
            "      ArrayList var3 = new ArrayList<>();\n"
            "      return var3.toArray(new L1ItemInstance[var3.size()]);\n"
            "   }\n"
            "   private L1ItemInstance[] h(int var1) {\n"
            "      ArrayList var2 = new ArrayList<>();\n"
            "      return var2.toArray(new L1ItemInstance[var2.size()]);\n"
            "   }\n"
            "   private class L1R_a<T> implements Comparator<L1ItemInstance> {\n"
            "      public int a(L1ItemInstance var1, L1ItemInstance var2) { return 0; }\n"
            "      @Override\n"
            "      public int compare(Object var1, Object var2) {\n"
            "         return this.a((L1ItemInstance)var1, (L1ItemInstance)var2);\n"
            "      }\n"
            "   }\n"
            "}\n"
        )

        rewritten = mod.rewrite_java_source(source, inventory, entries)

        self.assertIn("ArrayList<L1ItemInstance> var3 = new ArrayList<>();", rewritten)
        self.assertIn("ArrayList<L1ItemInstance> var2 = new ArrayList<>();", rewritten)
        self.assertIn(
            "private class L1R_a<T> implements Comparator<L1ItemInstance> {",
            rewritten,
        )
        self.assertNotIn("implements Comparator {", rewritten)

    def test_completed_source_wins_over_baseline(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_completed")
        entries = self.entries()
        baseline = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable { String v=\"BASE\"; }",
            "l1r/ap/L1PetInstance": "package l1r.ap; public class L1PetInstance {}",
        }
        completed = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable { String v=\"FIXED\"; }"
        }
        tree, index = mod.build_core_tree(entries, baseline, completed)
        account = tree["l1j/server/datatables/AccountTable.java"]
        self.assertIn("FIXED", account)
        self.assertNotIn("BASE", account)
        self.assertEqual(index["l1j/server/datatables/AccountTable"]["authority"], "completed")

    def test_quarantined_work_source_never_wins(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_quarantine")
        entries = self.entries()
        baseline = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable { String v=\"BASE\"; }",
            "l1r/ap/L1PetInstance": "package l1r.ap; public class L1PetInstance {}",
        }
        quarantine = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable { String v=\"WORK_ONLY\"; }"
        }
        tree, index = mod.build_core_tree(
            entries, baseline, completed_sources={}, quarantined_sources=quarantine
        )
        account = tree["l1j/server/datatables/AccountTable.java"]
        self.assertIn("BASE", account)
        self.assertNotIn("WORK_ONLY", account)
        self.assertEqual(index["l1j/server/datatables/AccountTable"]["authority"], "baseline")

    def test_missing_source_fails_closed(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_missing")
        entries = self.entries()
        baseline = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable {}"
        }
        with self.assertRaisesRegex(ValueError, "missing source identities"):
            mod.build_core_tree(entries, baseline, completed_sources={})

    def test_output_count_matches_mapped_source_count(self):
        mod = load(BOOTSTRAP_PATH, "fast_dev_bootstrap_count")
        entries = self.entries()
        baseline = {
            "l1r/ao/AccountTable": "package l1r.ao; public class AccountTable {}",
            "l1r/ap/L1PetInstance": "package l1r.ap; public class L1PetInstance {}",
        }
        tree, index = mod.build_core_tree(entries, baseline, completed_sources={})
        self.assertEqual(len(tree), len(entries))
        self.assertEqual(len(index), len(entries))


if __name__ == "__main__":
    unittest.main()
