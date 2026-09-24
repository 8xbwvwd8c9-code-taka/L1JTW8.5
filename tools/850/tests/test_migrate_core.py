import csv
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "migrate_core.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_migrate_core", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class RealCoreMaterializationTests(unittest.TestCase):
    def test_materializes_exactly_788_semantic_sources_and_metadata(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            output = Path(td) / "core"
            result = mod.materialize_sources(ROOT, output)
            self.assertEqual(result["source_count"], 788)
            java_files = list((output / "src").rglob("*.java"))
            self.assertEqual(len(java_files), 788)

            package_map = output / "package-map.csv"
            with package_map.open("r", encoding="utf-8", newline="") as stream:
                rows = list(csv.DictReader(stream))
            self.assertEqual(len(rows), 788)

            source_index = json.loads((output / "source-index.json").read_text(encoding="utf-8"))
            self.assertEqual(len(source_index), 788)

    def test_known_sources_land_in_semantic_paths_with_semantic_packages(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            output = Path(td) / "core"
            mod.materialize_sources(ROOT, output)
            expected = [
                output / "src" / "l1j" / "server" / "clientpackets" / "C_NpcAction.java",
                output / "src" / "l1j" / "server" / "skill" / "L1SkillTimer__obf_c.java",
                output / "src" / "l1j" / "server" / "network" / "ClientThread.java",
                output / "src" / "l1j" / "server" / "Config.java",
            ]
            for path in expected:
                self.assertTrue(path.is_file(), str(path))
                text = path.read_text(encoding="utf-8")
                self.assertNotIn("package l1r.", text)
            self.assertIn("package l1j.server.clientpackets;", expected[0].read_text(encoding="utf-8"))
            self.assertIn("package l1j.server.skill;", expected[1].read_text(encoding="utf-8"))
            self.assertIn("package l1j.server.network;", expected[2].read_text(encoding="utf-8"))
            self.assertIn("package l1j.server;", expected[3].read_text(encoding="utf-8"))

    def test_output_has_no_recovery_namespace_import_or_fq_reference(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            output = Path(td) / "core"
            mod.materialize_sources(ROOT, output)
            offenders = []
            for path in (output / "src").rglob("*.java"):
                text = path.read_text(encoding="utf-8")
                if "l1r." in text:
                    offenders.append(path.relative_to(output).as_posix())
            self.assertEqual(offenders, [])

    def test_existing_output_is_replaced_atomically_not_merged_with_stale_sources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            output = Path(td) / "core"
            stale = output / "src" / "stale" / "Old.java"
            stale.parent.mkdir(parents=True)
            stale.write_text("class Old {}\n", encoding="utf-8")
            mod.materialize_sources(ROOT, output)
            self.assertFalse(stale.exists())
            self.assertEqual(len(list((output / "src").rglob("*.java"))), 788)


if __name__ == "__main__":
    unittest.main()
