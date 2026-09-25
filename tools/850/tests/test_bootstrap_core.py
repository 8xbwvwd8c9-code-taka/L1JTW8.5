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