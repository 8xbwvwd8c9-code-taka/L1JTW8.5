import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "compile_ready_authority.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_compile_ready_authority", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def git(root: Path, *args: str) -> str:
    proc = subprocess.run(
        ["git", *args],
        cwd=root,
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    return proc.stdout.strip()


class CompileReadyAuthorityContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def test_replays_pinned_normalizers_without_using_current_worktree_tools(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            repo = root / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            tools = repo / "tools" / "recovery"
            tools.mkdir(parents=True)
            (tools / "build-normalized-stage.py").write_text(
                "from pathlib import Path\n"
                "import shutil\n"
                "src=Path('recovery/normalized-src-vf')\n"
                "dst=Path('_normalized-stage-src')\n"
                "shutil.copytree(src,dst)\n",
                encoding="utf-8",
            )
            (tools / "normalize-marker.py").write_text(
                "from pathlib import Path\n"
                "p=Path('_normalized-stage-src/l1r/aa/A.java')\n"
                "p.write_text(p.read_text(encoding='utf-8').replace('RAW','READY'),encoding='utf-8')\n",
                encoding="utf-8",
            )
            git(repo, "add", ".")
            git(repo, "commit", "-m", "pinned compile normalizers")
            normalizer_commit = git(repo, "rev-parse", "HEAD")

            # Current worktree intentionally no longer has recovery tools. The
            # compile-ready stage must come from the pinned historical commit.
            shutil.rmtree(tools)
            git(repo, "add", "-A")
            git(repo, "commit", "-m", "remove historical recovery tools")

            authority = root / "authority"
            source = authority / "recovery" / "normalized-src-vf" / "l1r" / "aa"
            source.mkdir(parents=True)
            (source / "A.java").write_text(
                "package l1r.aa; public class A { String marker = \"RAW\"; }\n",
                encoding="utf-8",
            )
            (authority / "recovery" / "source_namespace_map.csv").write_text(
                "Kind,OldInternal,NewInternal\nTOP_LEVEL,aa/a,l1r/aa/A\n",
                encoding="utf-8",
            )

            output = root / "compile-ready-authority"
            result = mod.prepare_compile_ready_authority(
                repo,
                authority,
                output,
                normalizer_commit=normalizer_commit,
                script_names=("build-normalized-stage.py", "normalize-marker.py"),
            )

            transformed = output / "recovery" / "normalized-src-vf" / "l1r" / "aa" / "A.java"
            self.assertIn("READY", transformed.read_text(encoding="utf-8"))
            self.assertNotIn("RAW", transformed.read_text(encoding="utf-8"))
            self.assertEqual(result["normalizer_commit"], normalizer_commit)
            self.assertEqual(result["source_count"], 1)
            self.assertEqual(
                result["scripts"],
                ["build-normalized-stage.py", "normalize-marker.py"],
            )
            self.assertFalse((output / "_normalized-stage-src").exists())
            self.assertFalse((output / "tools" / "recovery").exists())

    def test_normalizer_failure_is_fail_closed_and_does_not_publish_output(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            repo = root / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")
            tools = repo / "tools" / "recovery"
            tools.mkdir(parents=True)
            (tools / "build-normalized-stage.py").write_text(
                "raise SystemExit('intentional normalizer failure')\n",
                encoding="utf-8",
            )
            git(repo, "add", ".")
            git(repo, "commit", "-m", "failing normalizer")
            normalizer_commit = git(repo, "rev-parse", "HEAD")

            authority = root / "authority"
            source = authority / "recovery" / "normalized-src-vf"
            source.mkdir(parents=True)
            (source / "A.java").write_text("class A {}\n", encoding="utf-8")
            output = root / "compile-ready-authority"

            with self.assertRaisesRegex(RuntimeError, "build-normalized-stage.py"):
                mod.prepare_compile_ready_authority(
                    repo,
                    authority,
                    output,
                    normalizer_commit=normalizer_commit,
                    script_names=("build-normalized-stage.py",),
                )
            self.assertFalse(output.exists())

    def test_authority_cache_wires_compile_ready_before_semantic_migration(self):
        cache_path = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"
        text = cache_path.read_text(encoding="utf-8")
        self.assertIn(
            '_COMPILE_READY = _load_local("compile_ready_authority.py"',
            text,
        )
        prepare = "_COMPILE_READY.prepare_compile_ready_authority("
        migrate = "_MIGRATE.materialize_sources("
        self.assertIn(prepare, text)
        self.assertIn(migrate, text)
        self.assertLess(text.index(prepare), text.index(migrate))
        self.assertIn("AUTHORITY_CACHE_SCHEMA_VERSION = 3", text)


if __name__ == "__main__":
    unittest.main()
