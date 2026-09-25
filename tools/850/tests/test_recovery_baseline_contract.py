import importlib.util
import shutil
import subprocess
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"


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


if __name__ == "__main__":
    unittest.main()
