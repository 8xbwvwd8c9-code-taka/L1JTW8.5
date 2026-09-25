import importlib.util
import shutil
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "compiler" / "incremental.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_incremental_baseline_only", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class BaselineOnlySourceTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def _compiler(self, mod, root: Path, *, classpath=None, baseline_jar=None, baseline_only=None):
        return mod.IncrementalCompiler(
            source_root=root / "core" / "src",
            class_dir=root / ".build850" / "classes",
            state_path=root / ".build850" / "state.json",
            dependency_index_path=root / ".build850" / "dependency-index.json",
            classpath=classpath or [],
            baseline_jar=baseline_jar,
            baseline_only_identities=baseline_only or [],
        )

    def _make_project(self, root: Path):
        pkg = root / "core" / "src" / "dev"
        pkg.mkdir(parents=True)
        (pkg / "A.java").write_text(
            "package dev; public class A { public int value() { return 1; } }\n",
            encoding="utf-8",
        )
        (pkg / "Protocol.java").write_text(
            "package dev; public class Protocol { public int value() { return 7; } }\n",
            encoding="utf-8",
        )

    def _make_dev_base(self, mod, root: Path) -> Path:
        producer = mod.IncrementalCompiler(
            source_root=root / "core" / "src",
            class_dir=root / ".producer" / "classes",
            state_path=root / ".producer" / "state.json",
            dependency_index_path=root / ".producer" / "dependency-index.json",
            classpath=[],
        )
        producer.full_compile()
        jar = root / "dev-base.jar"
        with zipfile.ZipFile(jar, "w") as archive:
            for path in sorted((root / ".producer" / "classes").rglob("*.class")):
                archive.write(path, path.relative_to(root / ".producer" / "classes").as_posix())
        return jar

    def test_full_compile_keeps_non_roundtrippable_protocol_family_in_dev_base(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self._make_project(root)
            dev_base = self._make_dev_base(mod, root)
            protocol = root / "core" / "src" / "dev" / "Protocol.java"
            protocol.write_text(
                "package dev; public class Protocol { public MissingType value() { return null; } }\n",
                encoding="utf-8",
            )

            compiler = self._compiler(
                mod,
                root,
                classpath=[dev_base],
                baseline_jar=dev_base,
                baseline_only=["dev.Protocol"],
            )
            result = compiler.full_compile()

            self.assertEqual(result["mode"], "full")
            self.assertEqual(result["compiled_identities"], ["dev.A"])
            self.assertEqual(result["baseline_identities"], ["dev.Protocol"])
            self.assertTrue((root / ".build850" / "classes" / "dev" / "A.class").is_file())
            self.assertFalse((root / ".build850" / "classes" / "dev" / "Protocol.class").exists())

    def test_editing_baseline_only_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self._make_project(root)
            dev_base = self._make_dev_base(mod, root)
            compiler = self._compiler(
                mod,
                root,
                classpath=[dev_base],
                baseline_jar=dev_base,
                baseline_only=["dev.Protocol"],
            )
            compiler.full_compile()
            protocol = root / "core" / "src" / "dev" / "Protocol.java"
            protocol.write_text(
                "package dev; public class Protocol { public int value() { return 8; } }\n",
                encoding="utf-8",
            )
            with self.assertRaisesRegex(RuntimeError, "baseline-only"):
                compiler.compile_changed()


if __name__ == "__main__":
    unittest.main()
