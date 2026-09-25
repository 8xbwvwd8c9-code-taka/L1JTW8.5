"""Regression contracts for finalized recovery and completed promotion history."""

import importlib.util
import shutil
import subprocess
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"
REPLAY_PROMOTION = "6dcb4eeb92e83395e1b972246fda9191a056de5d"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_baseline_contract", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def git(*args: str) -> str:
    proc = subprocess.run(
        ["git", *args],
        cwd=ROOT,
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    return proc.stdout


class RecoveryBaselineContract(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def test_configured_baseline_contains_final_named_inner_namespace(self):
        mod = load_module()
        baseline = mod.ensure_recovery_baseline_commit(
            ROOT,
            baseline_commit=mod.RECOVERY_BASELINE_COMMIT,
            fetch_if_missing=True,
        )
        namespace_map = git("show", f"{baseline}:recovery/source_namespace_map.csv")
        pledge_source = git(
            "show",
            f"{baseline}:recovery/normalized-src-vf/l1r/aj/C_Pledge.java",
        )

        self.assertIn(
            "aj/bp$a,l1r/aj/C_Pledge$L1R_a,NAMED_INNER",
            namespace_map,
        )
        self.assertIn("public class L1R_a", pledge_source)
        self.assertNotIn("public class a {", pledge_source)

    def test_completed_history_discovers_validated_replay_promotion(self):
        mod = load_module()
        completed = mod.resolve_completed_authority_commit(ROOT, fetch_latest=True)
        baseline = mod.ensure_recovery_baseline_commit(
            ROOT,
            baseline_commit=mod.RECOVERY_BASELINE_COMMIT,
            fetch_if_missing=True,
        )
        promotions = mod._promotion_commits(ROOT, baseline, completed)
        self.assertIn(REPLAY_PROMOTION, promotions)

    def test_completed_union_includes_validated_replay_promotion_sources(self):
        mod = load_module()
        completed = mod.resolve_completed_authority_commit(ROOT, fetch_latest=True)
        paths = set(
            mod.completed_repair_source_paths(
                ROOT,
                commit=completed,
                baseline_commit=mod.RECOVERY_BASELINE_COMMIT,
                fetch_if_missing=True,
            )
        )

        # 6dcb4eeb... is a completed-branch `promote(l3): replay ...` commit.
        # These companion sources add APIs consumed by CharacterTable and must not
        # disappear merely because the promotion arrived off the first-parent path.
        self.assertIn(
            "recovery/normalized-src-vf/l1r/ao/MailTable.java",
            paths,
        )
        self.assertIn(
            "recovery/normalized-src-vf/l1r/ao/BuddyTable.java",
            paths,
        )


if __name__ == "__main__":
    unittest.main()
