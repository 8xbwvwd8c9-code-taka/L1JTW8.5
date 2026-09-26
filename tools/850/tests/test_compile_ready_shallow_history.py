import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "compile_ready_authority.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_compile_ready_shallow", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def git(root: Path, *args: str, check: bool = True) -> subprocess.CompletedProcess[str]:
    proc = subprocess.run(
        ["git", *args],
        cwd=root,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    if check and proc.returncode != 0:
        raise RuntimeError(proc.stderr.strip() or proc.stdout.strip())
    return proc


class CompileReadyShallowHistoryContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def make_shallow_repo(self, root: Path):
        origin = root / "origin"
        origin.mkdir()
        git(origin, "init", "-b", "main")
        git(origin, "config", "user.email", "test@example.invalid")
        git(origin, "config", "user.name", "Fast Dev Test")

        tools = origin / "tools" / "recovery"
        tools.mkdir(parents=True)
        (tools / "build-normalized-stage.py").write_text(
            "from pathlib import Path\n"
            "import shutil\n"
            "src=Path('recovery/normalized-src-vf')\n"
            "dst=Path('_normalized-stage-src')\n"
            "shutil.copytree(src,dst)\n",
            encoding="utf-8",
        )
        git(origin, "add", ".")
        git(origin, "commit", "-m", "historical normalizer")
        historical = git(origin, "rev-parse", "HEAD").stdout.strip()

        shutil.rmtree(tools)
        (origin / "HEAD_MARKER").write_text("latest\n", encoding="utf-8")
        git(origin, "add", "-A")
        git(origin, "commit", "-m", "remove historical normalizer")

        client = root / "client"
        clone = subprocess.run(
            [
                "git",
                "clone",
                "--depth=1",
                "--branch",
                "main",
                origin.resolve().as_uri(),
                str(client),
            ],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        if clone.returncode != 0:
            raise RuntimeError(clone.stderr.strip() or clone.stdout.strip())
        self.assertNotEqual(
            git(client, "cat-file", "-e", f"{historical}^{{commit}}", check=False).returncode,
            0,
        )
        return client, historical

    def make_authority(self, root: Path) -> Path:
        authority = root / "authority"
        source = authority / "recovery" / "normalized-src-vf" / "l1r" / "aa"
        source.mkdir(parents=True)
        (source / "A.java").write_text(
            "package l1r.aa; public class A {}\n",
            encoding="utf-8",
        )
        return authority

    def test_prepare_fetches_exact_historical_normalizer_from_shallow_origin(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            client, historical = self.make_shallow_repo(root)
            authority = self.make_authority(root)
            output = root / "output"

            result = mod.prepare_compile_ready_authority(
                client,
                authority,
                output,
                normalizer_commit=historical,
                script_names=("build-normalized-stage.py",),
                pre_stage=False,
                fetch_if_missing=True,
            )

            self.assertTrue(output.is_dir())
            self.assertEqual(result["normalizer_commit"], historical)
            self.assertEqual(
                git(client, "cat-file", "-e", f"{historical}^{{commit}}", check=False).returncode,
                0,
            )

    def test_prepare_fails_closed_when_fetch_is_disabled(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            client, historical = self.make_shallow_repo(root)
            authority = self.make_authority(root)
            output = root / "output"

            with self.assertRaisesRegex(RuntimeError, "normalizer commit unavailable"):
                mod.prepare_compile_ready_authority(
                    client,
                    authority,
                    output,
                    normalizer_commit=historical,
                    script_names=("build-normalized-stage.py",),
                    pre_stage=False,
                    fetch_if_missing=False,
                )
            self.assertFalse(output.exists())


if __name__ == "__main__":
    unittest.main()
