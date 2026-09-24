import importlib.util
import unittest
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1] / "select_deployable_repairs.py"
spec = importlib.util.spec_from_file_location("select_deployable_repairs", MODULE)
if spec is None or spec.loader is None:
    raise ImportError(MODULE)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)


class DeployableRepairPolicyTests(unittest.TestCase):
    def test_only_promotion_commits_create_candidates(self):
        promotions = [
            {
                "sha": "a1",
                "message": "fix(l2): promote BUG-850-121 bookmark fail-closed",
                "files": ["recovery/normalized-src-vf/l1r/bh/L1BookMark.java", "README.md"],
            },
            {
                "sha": "a2",
                "message": "docs: update handoff",
                "files": ["recovery/normalized-src-vf/l1r/aj/C_Login.java"],
            },
        ]
        candidates = mod.select_promotion_candidates(
            promotions,
            {"l1r/bh/L1BookMark": "bh/c", "l1r/aj/C_Login": "aj/a"},
        )
        self.assertEqual(1, len(candidates))
        self.assertEqual("l1r/bh/L1BookMark", candidates[0]["normalized_top"])
        self.assertEqual(["BUG-850-121"], candidates[0]["bug_ids"])
        self.assertEqual(["a1"], candidates[0]["promotion_commits"])

    def test_repeated_promotions_for_same_class_retain_all_provenance(self):
        promotions = [
            {
                "sha": "p124",
                "message": "fix(l2): promote BUG-850-124 house payment atomicity",
                "files": ["recovery/normalized-src-vf/l1r/aj/C_NpcAction.java"],
            },
            {
                "sha": "p126",
                "message": "fix(l2): promote BUG-850-126 karma exchange atomicity",
                "files": ["recovery/normalized-src-vf/l1r/aj/C_NpcAction.java"],
            },
        ]
        candidates = mod.select_promotion_candidates(
            promotions, {"l1r/aj/C_NpcAction": "aj/bk"}
        )
        self.assertEqual(1, len(candidates))
        self.assertEqual(["BUG-850-124", "BUG-850-126"], candidates[0]["bug_ids"])
        self.assertEqual(["p124", "p126"], candidates[0]["promotion_commits"])
        self.assertEqual("p126", candidates[0]["latest_promotion_commit"])

    def test_unmapped_promoted_normalized_source_fails_closed(self):
        promotions = [{
            "sha": "x",
            "message": "fix(l2): promote BUG-850-999 unknown",
            "files": ["recovery/normalized-src-vf/l1r/new/Unknown.java"],
        }]
        with self.assertRaises(ValueError):
            mod.select_promotion_candidates(promotions, {"l1r/bh/L1BookMark": "bh/c"})

    def test_compile_rc_zero_with_expected_top_class_is_deployable(self):
        candidate = {"normalized_top": "l1r/bh/L1BookMark"}
        result = mod.classify_compile_result(
            candidate,
            javac_exit=0,
            generated_classes=["l1r/bh/L1BookMark.class", "l1r/bh/L1BookMark$1.class"],
        )
        self.assertEqual("DEPLOYABLE", result["status"])
        self.assertEqual(2, result["generated_class_count"])

    def test_compile_failure_or_missing_top_class_is_deferred(self):
        candidate = {"normalized_top": "l1r/aj/C_NpcAction"}
        failed = mod.classify_compile_result(
            candidate, javac_exit=1, generated_classes=[]
        )
        self.assertEqual("DEFERRED", failed["status"])
        self.assertEqual("JAVAC_EXIT_NONZERO", failed["reason"])

        missing = mod.classify_compile_result(
            candidate, javac_exit=0, generated_classes=["l1r/aj/C_NpcAction$1.class"]
        )
        self.assertEqual("DEFERRED", missing["status"])
        self.assertEqual("EXPECTED_TOP_CLASS_MISSING", missing["reason"])


if __name__ == "__main__":
    unittest.main()
