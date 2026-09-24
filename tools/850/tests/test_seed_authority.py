import importlib.util
import shutil
import subprocess
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "compiler" / "incremental.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_incremental_seed_authority", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_sources(root: Path, a_value: int) -> None:
    pkg = root / "dev"
    pkg.mkdir(parents=True, exist_ok=True)
    (pkg / "A.java").write_text(
        f"package dev; public class A {{ public int value() {{ return {a_value}; }} }}\n",
        encoding="utf-8",
    )
    (pkg / "B.java").write_text(
        "package dev; public class B { public int read() { return new A().value(); } }\n",
        encoding="utf-8",
    )


def make_dev_base(source_root: Path, output: Path) -> None:
    classes = output.parent / "seed-classes"
    classes.mkdir(parents=True)
    sources = [str(path) for path in sorted(source_root.rglob("*.java"))]
    subprocess.run(
        ["javac", "-encoding", "UTF-8", "-source", "8", "-target", "8", "-d", str(classes), *sources],
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    with zipfile.ZipFile(output, "w") as archive:
        for path in sorted(classes.rglob("*.class")):
            archive.write(path, path.relative_to(classes).as_posix())


class SeedAuthorityContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def test_seed_uses_pinned_baseline_sources_not_edited_worktree(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            canonical = root / "canonical"
            current = root / "core" / "src"
            write_sources(canonical, 1)
            write_sources(current, 2)
            dev_base = root / "dev-base.jar"
            make_dev_base(canonical, dev_base)

            compiler = mod.IncrementalCompiler(
                source_root=current,
                class_dir=root / ".build850" / "classes",
                state_path=root / ".build850" / "state.json",
                dependency_index_path=root / ".build850" / "dependency-index.json",
                classpath=[dev_base],
            )
            compiler.seed_from_dev_base(dev_base, baseline_source_root=canonical)
            result = compiler.compile_changed()

            self.assertEqual(result["mode"], "incremental")
            self.assertEqual(result["compiled_identities"], ["dev.A"])
            self.assertTrue((root / ".build850" / "classes" / "dev" / "A.class").is_file())
            self.assertFalse((root / ".build850" / "classes" / "dev" / "B.class").exists())


if __name__ == "__main__":
    unittest.main()
