import importlib.util
import json
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_authority_cache", MODULE_PATH)
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


def write_authority(root: Path, value: int) -> None:
    recovery = root / "recovery"
    normalized = recovery / "normalized-src-vf" / "l1r" / "aa"
    normalized.mkdir(parents=True, exist_ok=True)
    (recovery / "source_namespace_map.csv").write_text(
        "Kind,OldInternal,NewInternal\nTOP_LEVEL,aa/a,l1r/aa/A\n",
        encoding="utf-8",
    )
    (recovery / "source_namespace_state.json").write_text(
        json.dumps(
            {
                "top_level_mappings": 1,
                "application_class_mappings": 1,
                "duplicate_sourcefile_groups": 0,
            }
        ),
        encoding="utf-8",
    )
    (normalized / "A.java").write_text(
        f"package l1r.aa; public class A {{ public int value() {{ return {value}; }} }}\n",
        encoding="utf-8",
    )

    rules = root / "tools" / "850" / "bootstrap" / "package_rules.json"
    rules.parent.mkdir(parents=True, exist_ok=True)
    rules.write_text(
        json.dumps(
            {
                "families": {"aa": {"package": "l1j/server/test", "category": "test"}},
                "overrides": {},
            }
        ),
        encoding="utf-8",
    )


class AuthorityCacheContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def test_resolve_completed_authority_commit_pins_completed_tip_not_work_head(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            write_authority(repo, 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "completed one")
            git(repo, "branch", mod.COMPLETED_BRANCH)

            write_authority(repo, 2)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "completed two")
            completed_tip = git(repo, "rev-parse", "HEAD")
            git(repo, "branch", "-f", mod.COMPLETED_BRANCH, completed_tip)

            write_authority(repo, 99)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "wip source")
            work_head = git(repo, "rev-parse", "HEAD")
            self.assertNotEqual(completed_tip, work_head)

            resolved = mod.resolve_completed_authority_commit(
                repo,
                fetch_latest=False,
            )
            self.assertEqual(resolved, completed_tip)
            self.assertNotEqual(resolved, work_head)

    def test_materialization_uses_pinned_commit_not_newer_worktree_source(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            write_authority(repo, 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "completed authority")
            completed = git(repo, "rev-parse", "HEAD")

            write_authority(repo, 99)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "wip authority")

            cache_core = repo / ".build850" / "cache" / "completed-authority-core"
            result = mod.materialize_authority_core(
                repo,
                cache_core,
                commit=completed,
                fetch_if_missing=False,
            )

            source = cache_core / "src" / "l1j" / "server" / "test" / "A.java"
            self.assertTrue(source.is_file())
            text = source.read_text(encoding="utf-8")
            self.assertIn("return 1", text)
            self.assertNotIn("return 99", text)
            self.assertEqual(result["commit"], completed)
            self.assertEqual(result["source_count"], 1)

    def test_missing_pinned_commit_fails_closed_without_publishing_cache(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            write_authority(repo, 1)
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")
            git(repo, "add", ".")
            git(repo, "commit", "-m", "only commit")

            cache_core = repo / ".build850" / "cache" / "completed-authority-core"
            with self.assertRaisesRegex(RuntimeError, "completed authority commit unavailable"):
                mod.materialize_authority_core(
                    repo,
                    cache_core,
                    commit="0" * 40,
                    fetch_if_missing=False,
                )
            self.assertFalse(cache_core.exists())


if __name__ == "__main__":
    unittest.main()
