import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "runtime_bootstrap.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_runtime_bootstrap", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_authority_core(root: Path) -> Path:
    core = root / "authority-core"
    src = core / "src" / "dev" / "test"
    src.mkdir(parents=True)
    (src / "A.java").write_text(
        "package dev.test; public class A { public int value() { return 2; } }\n",
        encoding="utf-8",
    )
    (src / "B.java").write_text(
        "package dev.test; public class B { public int value() { return 1; } }\n",
        encoding="utf-8",
    )
    (core / "source-index.json").write_text(
        """[
  {
    "recovered_internal": "l1r/aa/A",
    "dev_internal": "dev/test/A",
    "dev_source": "src/dev/test/A.java"
  },
  {
    "recovered_internal": "l1r/aa/B",
    "dev_internal": "dev/test/B",
    "dev_source": "src/dev/test/B.java"
  }
]\n""",
        encoding="utf-8",
    )
    (core / "package-map.csv").write_text("OriginalInternal,DevInternal\naa/A,dev/test/A\naa/B,dev/test/B\n", encoding="utf-8")
    (core / "runtime-class-map.json").write_text(
        '{"aa/A":"dev/test/A","aa/B":"dev/test/B"}\n', encoding="utf-8"
    )
    return core


class RuntimeBootstrapContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def test_completed_diff_maps_only_to_exact_authority_dev_sources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            authority = write_authority_core(Path(td))
            selected = mod.completed_dev_source_paths(
                authority,
                ["recovery/normalized-src-vf/l1r/aa/A.java"],
            )
            self.assertEqual(selected, [authority / "src" / "dev" / "test" / "A.java"])

    def test_completed_diff_unknown_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            authority = write_authority_core(Path(td))
            with self.assertRaisesRegex(KeyError, "completed normalized source missing from authority index"):
                mod.completed_dev_source_paths(
                    authority,
                    ["recovery/normalized-src-vf/l1r/aa/Unknown.java"],
                )

    def test_compile_completed_overlay_emits_only_selected_class_family(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = write_authority_core(root)
            output = root / "overlay"
            mod.compile_completed_overlay(
                [authority / "src" / "dev" / "test" / "A.java"],
                output,
                classpath=[],
            )
            self.assertTrue((output / "dev" / "test" / "A.class").is_file())
            self.assertFalse((output / "dev" / "test" / "B.class").exists())

    def test_publish_working_core_is_first_bootstrap_only(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = write_authority_core(root)
            working = root / "core"

            self.assertTrue(mod.publish_working_core_if_missing(authority, working))
            local = working / "src" / "dev" / "test" / "A.java"
            local.write_text("LOCAL EDIT\n", encoding="utf-8")

            self.assertFalse(mod.publish_working_core_if_missing(authority, working))
            self.assertEqual(local.read_text(encoding="utf-8"), "LOCAL EDIT\n")

    def test_partial_existing_core_fails_closed_instead_of_overwriting(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = write_authority_core(root)
            working = root / "core"
            working.mkdir()
            (working / "note.txt").write_text("keep", encoding="utf-8")

            with self.assertRaisesRegex(RuntimeError, "core exists without src"):
                mod.publish_working_core_if_missing(authority, working)
            self.assertEqual((working / "note.txt").read_text(encoding="utf-8"), "keep")


if __name__ == "__main__":
    unittest.main()
