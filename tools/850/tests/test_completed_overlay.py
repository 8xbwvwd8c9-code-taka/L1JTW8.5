import importlib.util
import json
import shutil
import subprocess
import tempfile
import textwrap
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "completed_overlay.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_completed_overlay", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def javac(*args: str) -> None:
    subprocess.run(
        ["javac", "-source", "8", "-target", "8", *args],
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )


class CompletedOverlayContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def make_authority_core(self, root: Path) -> Path:
        core = root / "authority-core"
        source_root = core / "src" / "dev" / "readable"
        source_root.mkdir(parents=True)
        (source_root / "A.java").write_text(
            textwrap.dedent(
                """
                package dev.readable;
                public class A {
                    public static final String MARKER = "completed-A";
                    public B dependency;
                    public static class Inner { public int value() { return 7; } }
                }
                """
            ).strip() + "\n",
            encoding="utf-8",
        )
        (source_root / "B.java").write_text(
            "package dev.readable; public class B { public int value() { return 2; } }\n",
            encoding="utf-8",
        )
        (core / "source-index.json").write_text(
            json.dumps(
                [
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
                ]
            ),
            encoding="utf-8",
        )
        return core

    def make_base_jar(self, root: Path) -> Path:
        src = root / "base-src" / "dev" / "readable"
        classes = root / "base-classes"
        src.mkdir(parents=True)
        classes.mkdir()
        (src / "B.java").write_text(
            "package dev.readable; public class B { public int value() { return 1; } }\n",
            encoding="utf-8",
        )
        javac("-d", str(classes), str(src / "B.java"))
        jar = root / "base.jar"
        with zipfile.ZipFile(jar, "w") as zf:
            for path in classes.rglob("*.class"):
                zf.write(path, path.relative_to(classes).as_posix())
        return jar

    def test_compiles_only_selected_completed_family_against_dev_base(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            core = self.make_authority_core(root)
            base = self.make_base_jar(root)
            output = root / "overlay"

            result = mod.compile_completed_overlay(
                authority_core=core,
                normalized_source_paths=[
                    "recovery/normalized-src-vf/l1r/aa/A.java",
                ],
                dev_base_jar=base,
                output_dir=output,
            )

            self.assertTrue((output / "dev/readable/A.class").is_file())
            self.assertTrue((output / "dev/readable/A$Inner.class").is_file())
            self.assertFalse((output / "dev/readable/B.class").exists())
            self.assertEqual(result["source_count"], 1)
            self.assertEqual(result["class_count"], 2)

    def test_unmapped_normalized_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            core = self.make_authority_core(root)
            base = self.make_base_jar(root)
            with self.assertRaisesRegex(KeyError, "completed repair source missing from authority index"):
                mod.compile_completed_overlay(
                    authority_core=core,
                    normalized_source_paths=[
                        "recovery/normalized-src-vf/l1r/aa/Missing.java",
                    ],
                    dev_base_jar=base,
                    output_dir=root / "overlay",
                )

    def test_failed_javac_preserves_last_known_good_overlay(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            core = self.make_authority_core(root)
            base = self.make_base_jar(root)
            output = root / "overlay"
            output.mkdir()
            sentinel = output / "keep.class"
            sentinel.write_bytes(b"last-known-good")
            source = core / "src" / "dev" / "readable" / "A.java"
            source.write_text(
                "package dev.readable; public class A { this is not java; }\n",
                encoding="utf-8",
            )

            with self.assertRaises(mod.OverlayCompileError):
                mod.compile_completed_overlay(
                    authority_core=core,
                    normalized_source_paths=[
                        "recovery/normalized-src-vf/l1r/aa/A.java",
                    ],
                    dev_base_jar=base,
                    output_dir=output,
                )

            self.assertEqual(sentinel.read_bytes(), b"last-known-good")
            self.assertEqual([p.name for p in output.iterdir()], ["keep.class"])


if __name__ == "__main__":
    unittest.main()
