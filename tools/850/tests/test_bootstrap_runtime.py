import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "bootstrap_runtime.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_bootstrap_runtime", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_core(root: Path, a_value: int, b_value: int) -> None:
    src = root / "src" / "dev"
    src.mkdir(parents=True, exist_ok=True)
    (src / "A.java").write_text(
        f"package dev; public class A {{ public int value() {{ return {a_value}; }} }}\n",
        encoding="utf-8",
    )
    (src / "B.java").write_text(
        f"package dev; public class B {{ public int value() {{ return {b_value}; }} }}\n",
        encoding="utf-8",
    )
    (root / "package-map.csv").write_text("map\n", encoding="utf-8")
    (root / "runtime-class-map.json").write_text("{}\n", encoding="utf-8")
    (root / "source-index.json").write_text("[]\n", encoding="utf-8")


class BootstrapRuntimeContracts(unittest.TestCase):
    def test_sync_updates_clean_authority_change_and_preserves_unrelated_local_edit(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            old = root / "old-authority"
            new = root / "new-authority"
            core = root / "core"
            write_core(old, 1, 1)
            write_core(new, 2, 1)
            write_core(core, 1, 99)

            result = mod.sync_core_from_authority(
                core,
                old_authority=old,
                new_authority=new,
                old_commit="1" * 40,
                new_commit="2" * 40,
                baseline_commit="0" * 40,
            )

            self.assertIn("return 2", (core / "src" / "dev" / "A.java").read_text(encoding="utf-8"))
            self.assertIn("return 99", (core / "src" / "dev" / "B.java").read_text(encoding="utf-8"))
            self.assertEqual(result["authority_updates"], 1)
            self.assertEqual(result["preserved_local_edits"], 1)
            marker = json.loads((core / "FAST_DEV_AUTHORITY.json").read_text(encoding="utf-8"))
            self.assertEqual(marker["commit"], "2" * 40)
            self.assertEqual(marker["baseline_commit"], "0" * 40)


if __name__ == "__main__":
    unittest.main()
