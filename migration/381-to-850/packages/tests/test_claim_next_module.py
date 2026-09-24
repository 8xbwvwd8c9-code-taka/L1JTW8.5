import copy
import importlib.util
import json
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[4]
TOOL = ROOT / "migration/381-to-850/packages/tools/claim_next_module.py"
QUEUE = ROOT / "migration/381-to-850/packages/reverse-order.json"


def load_tool():
    spec = importlib.util.spec_from_file_location("claim_next_module", TOOL)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class ClaimNextModuleTest(unittest.TestCase):
    def setUp(self):
        self.tool = load_tool()
        self.queue = json.loads(QUEUE.read_text(encoding="utf-8"))
        for item in self.queue["items"]:
            item["status"] = "PENDING"
            item.pop("claim", None)
            item.pop("evidence", None)

    def test_claims_only_first_pending_item(self):
        result = self.tool.claim_next(self.queue, "conversation-A")
        self.assertEqual("001-transform-status-item", result["module_id"])
        self.assertEqual("ACTIVE", self.queue["items"][0]["status"])
        self.assertEqual("conversation-A", self.queue["items"][0]["claim"]["actor"])

    def test_rejects_second_claim_while_item_is_active(self):
        self.tool.claim_next(self.queue, "conversation-A")
        with self.assertRaisesRegex(ValueError, "QUEUE_ACTIVE_MODULE_EXISTS"):
            self.tool.claim_next(self.queue, "conversation-B")

    def test_advances_after_prior_item_completed(self):
        self.queue["items"][0]["status"] = "COMPLETED"
        result = self.tool.claim_next(self.queue, "conversation-B")
        self.assertEqual("002-transform-status", result["module_id"])

    def test_completed_requires_all_promotion_evidence(self):
        active = self.tool.claim_next(self.queue, "conversation-A")
        with self.assertRaisesRegex(ValueError, "COMPLETION_EVIDENCE_MISSING"):
            self.tool.complete_active(
                self.queue,
                active["module_id"],
                {"outcome": "COMPLETED", "work_commit": "a" * 40},
            )

    def test_completed_records_reproducible_evidence(self):
        active = self.tool.claim_next(self.queue, "conversation-A")
        evidence = {
            "outcome": "COMPLETED",
            "work_commit": "a" * 40,
            "completed_commit": "b" * 40,
            "validation_path": "migration/381-to-850/packages/變身賦予狀態/驗證/validation.json",
            "homepage_anchor": "module-001-transform-status-item",
            "ci_url": "https://github.com/example/repo/actions/runs/1",
            "completed_at": "2026-09-25T00:00:00+08:00",
        }
        result = self.tool.complete_active(self.queue, active["module_id"], evidence)
        self.assertEqual("COMPLETED", result["status"])
        self.assertEqual(evidence["completed_commit"], result["evidence"]["completed_commit"])
        self.assertNotIn("claim", result)

    def test_hold_requires_blockers_and_validation_path(self):
        active = self.tool.claim_next(self.queue, "conversation-A")
        with self.assertRaisesRegex(ValueError, "HOLD_EVIDENCE_MISSING"):
            self.tool.complete_active(
                self.queue,
                active["module_id"],
                {"outcome": "HOLD", "blockers": []},
            )

    def test_hold_ends_active_claim_without_marking_completed(self):
        active = self.tool.claim_next(self.queue, "conversation-A")
        result = self.tool.complete_active(
            self.queue,
            active["module_id"],
            {
                "outcome": "HOLD",
                "blockers": ["RUNTIME_OWNER_NOT_PROVEN"],
                "validation_path": "migration/381-to-850/packages/變身賦予狀態/驗證/validation.json",
            },
        )
        self.assertEqual("HOLD", result["status"])
        self.assertNotEqual("COMPLETED", result["status"])

    def test_rejects_completion_for_non_active_module(self):
        self.tool.claim_next(self.queue, "conversation-A")
        with self.assertRaisesRegex(ValueError, "MODULE_NOT_ACTIVE"):
            self.tool.complete_active(
                self.queue,
                "002-transform-status",
                {"outcome": "HOLD", "blockers": ["x"], "validation_path": "x"},
            )


if __name__ == "__main__":
    unittest.main()
