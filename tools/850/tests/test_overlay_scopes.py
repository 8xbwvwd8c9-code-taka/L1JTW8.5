import importlib.util
import json
import shutil
import subprocess
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "completed_overlay.py"
A_PATH = "recovery/normalized-src-vf/l1r/aa/A.java"
B_PATH = "recovery/normalized-src-vf/l1r/aa/B.java"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_overlay_scopes", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def javac(*args: str) -> None:
    subprocess.run(
        ["javac", "-source", "8", "-target", "8", *args],
        check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True,
    )


class AtomicOverlayScopeContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def make_fixture(self, root: Path):
        core = root / "authority-core"
        src = core / "src" / "dev" / "readable"
        src.mkdir(parents=True)
        (src / "A.java").write_text(
            "package dev.readable; public class A { "
            "public static int repairedA() { return B.repairedB(); } }\n",
            encoding="utf-8",
        )
        (src / "B.java").write_text(
            "package dev.readable; public class B { "
            "public static int repairedB() { return A.repairedA(); } }\n",
            encoding="utf-8",
        )
        (core / "source-index.json").write_text(
            json.dumps([
                {
                    "recovered_internal": "l1r/aa/A",
                    "dev_internal": "dev/readable/A",
                    "dev_source": "src/dev/readable/A.java",
                },
                {
                    "recovered_internal": "l1r/aa/B",
                    "dev_internal": "dev/readable/B",
                    "dev_source": "src/dev/readable/B.java",
                },
            ]),
            encoding="utf-8",
        )

        base_src = root / "base-src" / "dev" / "readable"
        base_classes = root / "base-classes"
        base_src.mkdir(parents=True)
        base_classes.mkdir()
        (base_src / "A.java").write_text(
            "package dev.readable; public class A {}\n", encoding="utf-8"
        )
        (base_src / "B.java").write_text(
            "package dev.readable; public class B {}\n", encoding="utf-8"
        )
        javac(
            "-d", str(base_classes),
            str(base_src / "A.java"), str(base_src / "B.java"),
        )
        base = root / "base.jar"
        with zipfile.ZipFile(base, "w") as zf:
            for path in base_classes.rglob("*.class"):
                zf.write(path, path.relative_to(base_classes).as_posix())
        return core, base

    def test_mutually_dependent_completed_sources_compile_as_one_scope(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            core, base = self.make_fixture(root)
            output = root / "overlay"

            # Either source fails against the old base when compiled alone because
            # the other repaired-only method is absent. The promotion is valid only
            # when its two files are compiled and published atomically.
            result = mod.compile_completed_overlay(
                authority_core=core,
                normalized_source_scopes=[[A_PATH, B_PATH]],
                dev_base_jar=base,
                output_dir=output,
            )

            self.assertTrue((output / "dev/readable/A.class").is_file())
            self.assertTrue((output / "dev/readable/B.class").is_file())
            self.assertEqual(result["source_count"], 2)
            self.assertEqual(result["deployable_source_count"], 2)
            self.assertEqual(result["deferred_source_count"], 0)
            self.assertEqual(result["scope_count"], 1)


if __name__ == "__main__":
    unittest.main()
