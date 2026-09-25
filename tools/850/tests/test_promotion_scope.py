import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "promotion_scope.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_promotion_scope", MODULE_PATH)
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


def write_source(root: Path, name: str, value: int) -> None:
    path = root / "recovery" / "normalized-src-vf" / "l1r" / "aa" / f"{name}.java"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(
        f"package l1r.aa; public class {name} {{ public int value() {{ return {value}; }} }}\n",
        encoding="utf-8",
    )


def validation(root: Path, bug: str) -> None:
    path = root / "recovery" / f"{bug}_COMPLETED_VALIDATION_20260925.md"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(f"STATUS=PASS\nBUG={bug}\n", encoding="utf-8")


class PromotionScopeContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def make_repo(self, root: Path):
        repo = root / "repo"
        repo.mkdir()
        git(repo, "init")
        git(repo, "config", "user.email", "test@example.invalid")
        git(repo, "config", "user.name", "Fast Dev Test")
        write_source(repo, "A", 1)
        write_source(repo, "B", 1)
        git(repo, "add", ".")
        git(repo, "commit", "-m", "recovery: import normalized authority")

        # Recovery-only cleanup is not a completed repair and must not enter scope.
        write_source(repo, "B", 2)
        git(repo, "add", ".")
        git(repo, "commit", "-m", "recovery: normalize decompiler artifact")

        write_source(repo, "A", 2)
        validation(repo, "BUG-850-001")
        git(repo, "add", ".")
        git(repo, "commit", "-m", "fix(l2): promote BUG-850-001 durable write")

        write_source(repo, "B", 3)
        validation(repo, "BUG-850-002")
        git(repo, "add", ".")
        git(repo, "commit", "-m", "fix(l2): complete BUG-850-002")
        completed = git(repo, "rev-parse", "HEAD")

        # Later work must be invisible when discovery is pinned to completed.
        write_source(repo, "A", 99)
        git(repo, "add", ".")
        git(repo, "commit", "-m", "wip: half repaired source")
        return repo, completed

    def test_discovers_only_formally_completed_normalized_sources_at_pinned_commit(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo, completed = self.make_repo(Path(td))
            result = mod.discover_completed_source_scope(repo, completed)

            self.assertEqual(
                result["source_files"],
                [
                    "recovery/normalized-src-vf/l1r/aa/A.java",
                    "recovery/normalized-src-vf/l1r/aa/B.java",
                ],
            )
            self.assertEqual(len(result["promotion_commits"]), 2)
            self.assertEqual(result["authority_commit"], completed)

    def test_recovery_cleanup_without_completion_evidence_is_excluded(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")
            write_source(repo, "A", 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery: import authority")
            write_source(repo, "A", 2)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery: cleanup")
            head = git(repo, "rev-parse", "HEAD")

            result = mod.discover_completed_source_scope(repo, head)
            self.assertEqual(result["source_files"], [])
            self.assertEqual(result["promotion_commits"], [])

    def test_deleted_completed_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")
            write_source(repo, "A", 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery: import authority")
            (repo / "recovery" / "normalized-src-vf" / "l1r" / "aa" / "A.java").unlink()
            validation(repo, "BUG-850-003")
            git(repo, "add", "-A")
            git(repo, "commit", "-m", "fix(l2): promote BUG-850-003")
            head = git(repo, "rev-parse", "HEAD")

            with self.assertRaisesRegex(RuntimeError, "deleted completed source"):
                mod.discover_completed_source_scope(repo, head)


if __name__ == "__main__":
    unittest.main()
