import importlib.util
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "ensure_dev.py"
COMPLETED = "c" * 40
BASELINE = "b" * 40


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_ensure_authority_policy", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class StopAfterMaterialize(RuntimeError):
    pass


class FakeAuthority:
    RECOVERY_BASELINE_COMMIT = BASELINE

    def __init__(self):
        self.materialize_kwargs = None

    def resolve_completed_authority_commit(self, root, *, fetch_latest=True):
        return COMPLETED

    def materialize_authority_core(self, root, cache_core, **kwargs):
        self.materialize_kwargs = dict(kwargs)
        raise StopAfterMaterialize("stop after authority policy capture")


class EnsureDevAuthorityPolicyContracts(unittest.TestCase):
    def test_normal_bootstrap_materializes_baseline_plus_promotions_only(self):
        mod = load_module()
        authority = FakeAuthority()
        mod._AUTHORITY = authority

        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            (root / "l1jserver2.jar").write_bytes(b"production")

            with self.assertRaises(StopAfterMaterialize):
                mod.ensure_fast_dev(root, fetch_latest=False)

        self.assertIsNotNone(authority.materialize_kwargs)
        self.assertEqual(authority.materialize_kwargs["commit"], COMPLETED)
        self.assertEqual(authority.materialize_kwargs.get("baseline_commit"), BASELINE)
        self.assertFalse(authority.materialize_kwargs["fetch_if_missing"])


if __name__ == "__main__":
    unittest.main()
