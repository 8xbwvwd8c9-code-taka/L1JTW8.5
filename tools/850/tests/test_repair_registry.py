import importlib.util
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
REGISTRY_PATH = ROOT / "tools" / "850" / "repair-sync" / "repair_registry.py"
SYNC_PATH = ROOT / "tools" / "850" / "repair-sync" / "sync_repairs.py"


def load(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class RepairRegistryTests(unittest.TestCase):
    def setUp(self):
        self.audit20 = {
            "BUG-850-010", "BUG-850-027", "BUG-850-032", "BUG-850-033",
            "BUG-850-034", "BUG-850-039", "BUG-850-043", "BUG-850-046",
            "BUG-850-049", "BUG-850-057", "BUG-850-058", "BUG-850-059",
            "BUG-850-082", "BUG-850-083", "BUG-850-087", "BUG-850-089",
            "BUG-850-095", "BUG-850-100", "BUG-850-102", "BUG-850-103",
        }

    def test_three_new_promotions_reduce_effective_pending_from_20_to_17(self):
        mod = load(REGISTRY_PATH, "repair_registry_count")
        registry = mod.reconcile_registry(
            audit_bug_ids=self.audit20,
            completed_bug_ids={"BUG-850-039", "BUG-850-043", "BUG-850-046"},
            in_repair_bug_ids=set(),
            existing_registry={},
        )
        pending = [e for e in registry.values() if e["state"] in {"PENDING", "IN_REPAIR"}]
        self.assertEqual(len(pending), 17)
        self.assertEqual(registry["BUG-850-039"]["state"], "PROMOTED_NOT_SYNCED")

    def test_work_only_bug_stays_quarantined_and_is_not_sync_eligible(self):
        mod = load(REGISTRY_PATH, "repair_registry_work")
        registry = mod.reconcile_registry(
            audit_bug_ids={"BUG-850-049"},
            completed_bug_ids=set(),
            in_repair_bug_ids={"BUG-850-049"},
            existing_registry={},
        )
        entry = registry["BUG-850-049"]
        self.assertEqual(entry["state"], "IN_REPAIR")
        self.assertFalse(mod.is_sync_eligible(entry))

    def test_stale_pending_entry_becomes_promoted_not_synced(self):
        mod = load(REGISTRY_PATH, "repair_registry_stale")
        existing = {
            "BUG-850-100": {
                "bug_id": "BUG-850-100",
                "level": "L2",
                "state": "PENDING",
                "source_files": [],
            }
        }
        registry = mod.reconcile_registry(
            audit_bug_ids={"BUG-850-100"},
            completed_bug_ids={"BUG-850-100"},
            in_repair_bug_ids=set(),
            existing_registry=existing,
        )
        self.assertEqual(registry["BUG-850-100"]["state"], "PROMOTED_NOT_SYNCED")
        self.assertTrue(mod.is_sync_eligible(registry["BUG-850-100"]))

    def test_existing_validated_dev_state_survives_recount(self):
        mod = load(REGISTRY_PATH, "repair_registry_validated")
        existing = {
            "BUG-850-039": {
                "bug_id": "BUG-850-039",
                "level": "L2",
                "state": "VALIDATED_DEV",
                "source_files": ["C_Shop.java"],
            }
        }
        registry = mod.reconcile_registry(
            audit_bug_ids={"BUG-850-039"},
            completed_bug_ids={"BUG-850-039"},
            in_repair_bug_ids=set(),
            existing_registry=existing,
        )
        self.assertEqual(registry["BUG-850-039"]["state"], "VALIDATED_DEV")

    def test_multi_bug_promotion_keeps_one_atomic_source_scope(self):
        mod = load(SYNC_PATH, "sync_repairs_scope")
        scopes = mod.build_promotion_scopes(
            [
                {
                    "commit": "d9448ca",
                    "bug_ids": ["BUG-850-039", "BUG-850-043", "BUG-850-046"],
                    "source_files": [
                        "recovery/normalized-src-vf/l1r/aj/C_Shop.java",
                        "recovery/normalized-src-vf/l1r/aj/C_ShopAndWarehouse.java",
                    ],
                }
            ]
        )
        for bug_id in ("BUG-850-039", "BUG-850-043", "BUG-850-046"):
            self.assertEqual(scopes[bug_id]["commits"], ["d9448ca"])
            self.assertEqual(
                scopes[bug_id]["source_files"],
                [
                    "recovery/normalized-src-vf/l1r/aj/C_Shop.java",
                    "recovery/normalized-src-vf/l1r/aj/C_ShopAndWarehouse.java",
                ],
            )


if __name__ == "__main__":
    unittest.main()
