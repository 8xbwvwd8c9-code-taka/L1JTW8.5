import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"
PREFIX = "recovery/normalized-src-vf/l1r/aa/"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_promotion_scopes", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def git(root: Path, *args: str) -> str:
    proc = subprocess.run(
        ["git", *args], cwd=root, check=True,
        stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True,
    )
    return proc.stdout.strip()


def write_java(repo: Path, name: str, value: int) -> None:
    path = repo / PREFIX / f"{name}.java"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(
        f"package l1r.aa; public class {name} {{ public int value() {{ return {value}; }} }}\n",
        encoding="utf-8",
    )


def commit(repo: Path, subject: str) -> str:
    git(repo, "add", ".")
    git(repo, "commit", "-m", subject)
    return git(repo, "rev-parse", "HEAD")


class PromotionScopeAuthorityContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def new_repo(self, root: Path) -> Path:
        repo = root / "repo"
        repo.mkdir()
        git(repo, "init")
        git(repo, "config", "user.email", "test@example.invalid")
        git(repo, "config", "user.name", "Fast Dev Test")
        for name in ("A", "B", "C", "D", "E"):
            write_java(repo, name, 1)
        baseline = commit(repo, "recovery baseline")
        return repo, baseline

    def test_completed_promotion_subject_variants_are_all_authoritative(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo, baseline = self.new_repo(Path(td))

            write_java(repo, "A", 2)
            commit(repo, "fix(l2): promote BUG-850-001 exact")
            write_java(repo, "B", 2)
            commit(repo, "fix(l2): promote complete BUG-850-002 atomic repair")
            write_java(repo, "C", 2)
            commit(repo, "fix(l2): promote durable inventory update")
            write_java(repo, "D", 2)
            commit(repo, "promote(l3): replay validated source hunks")
            write_java(repo, "E", 99)
            completed = commit(repo, "docs(l2): record completion evidence")

            paths = mod.completed_repair_source_paths(
                repo,
                commit=completed,
                baseline_commit=baseline,
                fetch_if_missing=False,
            )
            self.assertEqual(
                paths,
                [PREFIX + f"{name}.java" for name in ("A", "B", "C", "D")],
            )
            self.assertNotIn(PREFIX + "E.java", paths)

    def test_overlapping_promotions_merge_into_one_atomic_scope(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo, baseline = self.new_repo(Path(td))

            write_java(repo, "A", 2)
            write_java(repo, "B", 2)
            first = commit(repo, "fix(l2): promote BUG-850-010 pair AB")

            write_java(repo, "B", 3)
            write_java(repo, "C", 2)
            second = commit(repo, "fix(l2): promote complete BUG-850-011 pair BC")

            write_java(repo, "D", 2)
            third = commit(repo, "fix(l2): promote durable independent D")
            completed = git(repo, "rev-parse", "HEAD")

            scopes = mod.completed_repair_source_scopes(
                repo,
                commit=completed,
                baseline_commit=baseline,
                fetch_if_missing=False,
            )

            self.assertEqual(len(scopes), 2)
            self.assertEqual(scopes[0]["commits"], [first, second])
            self.assertEqual(
                scopes[0]["source_paths"],
                [PREFIX + f"{name}.java" for name in ("A", "B", "C")],
            )
            self.assertEqual(scopes[1]["commits"], [third])
            self.assertEqual(scopes[1]["source_paths"], [PREFIX + "D.java"])


if __name__ == "__main__":
    unittest.main()
