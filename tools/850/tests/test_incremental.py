import hashlib
import importlib.util
import json
import shutil
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "compiler" / "incremental.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_incremental", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def digest(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


class IncrementalCompilerTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def make_project(self, root: Path):
        src = root / "core" / "src"
        pkg = src / "dev"
        pkg.mkdir(parents=True)
        (pkg / "A.java").write_text(
            "package dev; public class A { public int value() { return 1; } }\n",
            encoding="utf-8",
        )
        (pkg / "B.java").write_text(
            "package dev; import dev.A; public class B { public long read() { return new A().value(); } }\n",
            encoding="utf-8",
        )
        (pkg / "C.java").write_text(
            "package dev; public class C { public int value() { return 3; } }\n",
            encoding="utf-8",
        )
        return src

    def compiler(self, mod, root: Path):
        return mod.IncrementalCompiler(
            source_root=root / "core" / "src",
            class_dir=root / ".build850" / "classes",
            state_path=root / ".build850" / "state.json",
            dependency_index_path=root / ".build850" / "dependency-index.json",
            classpath=[],
        )

    def test_method_body_only_change_compiles_one_top_level_class(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.make_project(root)
            compiler = self.compiler(mod, root)
            compiler.full_compile()
            b_class = root / ".build850" / "classes" / "dev" / "B.class"
            b_before = digest(b_class)
            (root / "core" / "src" / "dev" / "A.java").write_text(
                "package dev; public class A { public int value() { return 2; } }\n",
                encoding="utf-8",
            )
            result = compiler.compile_changed()
            self.assertEqual(result["mode"], "incremental")
            self.assertEqual(result["compiled_identities"], ["dev.A"])
            self.assertEqual(digest(b_class), b_before)

    def test_abi_change_expands_to_reverse_dependents(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.make_project(root)
            compiler = self.compiler(mod, root)
            compiler.full_compile()
            (root / "core" / "src" / "dev" / "A.java").write_text(
                "package dev; public class A { public long value() { return 2L; } }\n",
                encoding="utf-8",
            )
            result = compiler.compile_changed()
            self.assertEqual(result["mode"], "incremental")
            self.assertEqual(set(result["compiled_identities"]), {"dev.A", "dev.B"})

    def test_unknown_dependency_closure_escalates_to_full_compile(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.make_project(root)
            compiler = self.compiler(mod, root)
            compiler.full_compile()
            dep_path = root / ".build850" / "dependency-index.json"
            data = json.loads(dep_path.read_text(encoding="utf-8"))
            data["complete"] = False
            dep_path.write_text(json.dumps(data), encoding="utf-8")
            (root / "core" / "src" / "dev" / "A.java").write_text(
                "package dev; public class A { public long value() { return 2L; } }\n",
                encoding="utf-8",
            )
            result = compiler.compile_changed()
            self.assertEqual(result["mode"], "full")
            self.assertEqual(set(result["compiled_identities"]), {"dev.A", "dev.B", "dev.C"})

    def test_deleted_source_forces_clean_full_compile_and_removes_old_class(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.make_project(root)
            compiler = self.compiler(mod, root)
            compiler.full_compile()
            c_class = root / ".build850" / "classes" / "dev" / "C.class"
            self.assertTrue(c_class.exists())
            (root / "core" / "src" / "dev" / "C.java").unlink()
            result = compiler.compile_changed()
            self.assertEqual(result["mode"], "full")
            self.assertFalse(c_class.exists())

    def test_failed_compile_preserves_last_known_good_overlay_and_state(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.make_project(root)
            compiler = self.compiler(mod, root)
            compiler.full_compile()
            a_class = root / ".build850" / "classes" / "dev" / "A.class"
            state_path = root / ".build850" / "state.json"
            class_before = digest(a_class)
            state_before = state_path.read_bytes()
            (root / "core" / "src" / "dev" / "A.java").write_text(
                "package dev; public class A { public int value( { return 9; } }\n",
                encoding="utf-8",
            )
            with self.assertRaises(mod.CompileError):
                compiler.compile_changed()
            self.assertEqual(digest(a_class), class_before)
            self.assertEqual(state_path.read_bytes(), state_before)


if __name__ == "__main__":
    unittest.main()
