import csv
import importlib.util
import json
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
PACKAGE_MAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "package_map.py"
RULES_PATH = ROOT / "tools" / "850" / "bootstrap" / "package_rules.json"
NAMESPACE_MAP = ROOT / "recovery" / "source_namespace_map.csv"
SOURCE_LIST = ROOT / "recovery" / "normalized-source-files.txt"
STATE_PATH = ROOT / "recovery" / "source_namespace_state.json"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_package_map_real", PACKAGE_MAP_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {PACKAGE_MAP_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def namespace_rows():
    with NAMESPACE_MAP.open("r", encoding="utf-8", newline="") as stream:
        return list(csv.DictReader(stream))


class RealMigrationContracts(unittest.TestCase):
    def test_real_top_level_namespace_map_is_complete_and_unique(self):
        mod = load_module()
        rules = json.loads(RULES_PATH.read_text(encoding="utf-8"))
        rows = namespace_rows()
        entries = mod.build_package_map_from_namespace(rows, rules)
        mod.validate_package_map(entries)
        state = json.loads(STATE_PATH.read_text(encoding="utf-8"))
        self.assertEqual(len(entries), state["top_level_mappings"])
        self.assertEqual(len(entries), 788)
        self.assertEqual(len({e.original_internal for e in entries}), 788)
        self.assertEqual(len({e.recovered_internal for e in entries}), 788)
        self.assertEqual(len({e.dev_internal for e in entries}), 788)

    def test_every_authoritative_normalized_source_is_represented_once(self):
        mod = load_module()
        rules = json.loads(RULES_PATH.read_text(encoding="utf-8"))
        entries = mod.build_package_map_from_namespace(namespace_rows(), rules)
        listed = [
            line.strip().removeprefix("recovery/normalized-src-vf/").removesuffix(".java")
            for line in SOURCE_LIST.read_text(encoding="utf-8").splitlines()
            if line.strip()
        ]
        recovered = [e.recovered_internal for e in entries]
        self.assertEqual(len(listed), 788)
        self.assertEqual(set(listed), set(recovered))

    def test_duplicate_sourcefile_recovery_variants_stay_distinct(self):
        mod = load_module()
        rules = json.loads(RULES_PATH.read_text(encoding="utf-8"))
        entries = mod.build_package_map_from_namespace(namespace_rows(), rules)
        by_original = {e.original_internal: e for e in entries}
        identities = {
            by_original["bg/c"].dev_internal,
            by_original["bg/d"].dev_internal,
            by_original["bg/f"].dev_internal,
        }
        self.assertEqual(len(identities), 3)
        self.assertTrue(any("__obf_c" in name for name in identities))
        self.assertTrue(any("__obf_d" in name for name in identities))
        self.assertTrue(any("__obf_f" in name for name in identities))

    def test_semantic_categories_cover_all_850_application_families(self):
        mod = load_module()
        rules = json.loads(RULES_PATH.read_text(encoding="utf-8"))
        entries = mod.build_package_map_from_namespace(namespace_rows(), rules)
        expected = set("ai aj ak al am an ao ap aq ar as at au av aw ax ay az ba bb bc bd be bf bg bh bi bj".split())
        actual = {
            e.original_internal.split("/", 1)[0]
            for e in entries
            if not e.original_internal.startswith("l1j/server/")
        }
        self.assertEqual(actual, expected)
        self.assertFalse(any("/misc/" in ("/" + e.dev_internal + "/") for e in entries))

    def test_bootstrap_server_classes_land_in_readable_server_namespace(self):
        mod = load_module()
        rules = json.loads(RULES_PATH.read_text(encoding="utf-8"))
        entries = mod.build_package_map_from_namespace(namespace_rows(), rules)
        by_original = {e.original_internal: e for e in entries}
        self.assertEqual(by_original["l1j/server/Server"].dev_internal, "l1j/server/Server")
        self.assertEqual(by_original["l1j/server/a"].dev_internal, "l1j/server/Config")
        self.assertEqual(by_original["l1j/server/b"].dev_internal, "l1j/server/DatabaseFactory")


if __name__ == "__main__":
    unittest.main()
