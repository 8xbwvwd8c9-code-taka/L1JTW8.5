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
    spec = importlib.util.spec_from_file_location("fast_dev_authority_materialization", MODULE_PATH)
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


def write_authority(root: Path, a_value: int, b_value: int) -> None:
    recovery = root / "recovery"
    normalized = recovery / "normalized-src-vf" / "l1r" / "aa"
    normalized.mkdir(parents=True, exist_ok=True)
    (recovery / "source_namespace_map.csv").write_text(
        "Kind,OldInternal,NewInternal\n"
        "TOP_LEVEL,aa/a,l1r/aa/A\n"
        "TOP_LEVEL,aa/b,l1r/aa/B\n",
        encoding="utf-8",
    )
    (recovery / "source_namespace_state.json").write_text(
        json.dumps(
            {
                "top_level_mappings": 2,
                "application_class_mappings": 2,
                "duplicate_sourcefile_groups": 0,
            }
        ),
        encoding="utf-8",
    )
    (normalized / "A.java").write_text(
        f"package l1r.aa; public class A {{ public int value() {{ return {a_value}; }} }}\n",
        encoding="utf-8",
    )
    (normalized / "B.java").write_text(
        f"package l1r.aa; public class B {{ public int value() {{ return {b_value}; }} }}\n",
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


class AuthorityMaterializationContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def test_materialization_uses_baseline_plus_only_promoted_sources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            write_authority(repo, 1, 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery baseline")
            baseline = git(repo, "rev-parse", "HEAD")

            write_authority(repo, 1, 2)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "chore: regenerate unrelated B")

            write_authority(repo, 2, 2)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "fix(l2): promote BUG-850-123 completed A repair")
            completed = git(repo, "rev-parse", "HEAD")

            cache_core = repo / ".build850" / "cache" / "completed-authority-core"
            result = mod.materialize_authority_core(
                repo,
                cache_core,
                commit=completed,
                baseline_commit=baseline,
                fetch_if_missing=False,
            )

            a_source = cache_core / "src" / "l1j" / "server" / "test" / "A.java"
            b_source = cache_core / "src" / "l1j" / "server" / "test" / "B.java"
            self.assertIn("return 2", a_source.read_text(encoding="utf-8"))
            self.assertIn("return 1", b_source.read_text(encoding="utf-8"))
            self.assertNotIn("return 2", b_source.read_text(encoding="utf-8"))
            self.assertEqual(result["commit"], completed)
            self.assertEqual(result["promoted_source_count"], 1)

    def test_materialization_accepts_complete_bug_commit_as_completed_authority(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            write_authority(repo, 1, 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery baseline")
            baseline = git(repo, "rev-parse", "HEAD")

            write_authority(repo, 2, 1)
            git(repo, "add", ".")
            git(repo, "commit", "-m", "fix(l2): complete BUG-850-032 and close BUG-850-033 coverage")
            completed = git(repo, "rev-parse", "HEAD")

            scope = mod.completed_repair_source_paths(
                repo,
                commit=completed,
                baseline_commit=baseline,
                fetch_if_missing=False,
            )
            self.assertEqual(
                scope,
                ["recovery/normalized-src-vf/l1r/aa/A.java"],
            )


if __name__ == "__main__":
    unittest.main()
