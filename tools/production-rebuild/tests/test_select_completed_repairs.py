import csv
import importlib.util
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "production-rebuild" / "select_completed_repairs.py"


def load_module():
    spec = importlib.util.spec_from_file_location("select_completed_repairs", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(MODULE_PATH)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class SelectCompletedRepairsTests(unittest.TestCase):
    def mapping_csv(self, root: Path):
        p = root / "map.csv"
        p.write_text(
            "OldInternal,NewInternal,Kind\n"
            "aj/bk,l1r/aj/C_NpcAction,TOP_LEVEL\n"
            "aj/bk$a,l1r/aj/C_NpcAction$L1R_a,NAMED_INNER\n"
            "ao/q,l1r/ao/ClanTable,TOP_LEVEL\n",
            encoding="utf-8",
        )
        return p

    def test_modified_mapped_sources_select_unique_sorted_tops(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            selected = m.select_from_changes(
                [
                    ("M", "recovery/normalized-src-vf/l1r/ao/ClanTable.java"),
                    ("M", "recovery/normalized-src-vf/l1r/aj/C_NpcAction.java"),
                    ("M", "recovery/normalized-src-vf/l1r/aj/C_NpcAction.java"),
                ],
                m.load_top_level_mappings(self.mapping_csv(root)),
            )
            self.assertEqual(selected, ["l1r/aj/C_NpcAction", "l1r/ao/ClanTable"])

    def test_unmapped_normalized_source_fails_closed(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            mappings = m.load_top_level_mappings(self.mapping_csv(root))
            with self.assertRaises(ValueError):
                m.select_from_changes(
                    [("A", "recovery/normalized-src-vf/l1r/aq/NewHelper.java")],
                    mappings,
                )

    def test_deleted_or_renamed_source_fails_closed(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            mappings = m.load_top_level_mappings(self.mapping_csv(root))
            for status in ("D", "R100"):
                with self.subTest(status=status):
                    with self.assertRaises(ValueError):
                        m.select_from_changes(
                            [(status, "recovery/normalized-src-vf/l1r/aj/C_NpcAction.java")],
                            mappings,
                        )

    def test_non_normalized_paths_are_rejected_not_silently_selected(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            mappings = m.load_top_level_mappings(self.mapping_csv(root))
            with self.assertRaises(ValueError):
                m.select_from_changes([("M", "recovered-src-obf/aj/bk.java")], mappings)


if __name__ == "__main__":
    unittest.main()
