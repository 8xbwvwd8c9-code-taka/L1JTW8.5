import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "ensure_dev.py"
COMMIT = "a" * 40
BASELINE = "b" * 40


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_runtime_map_cache", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class StopAfterCacheKey(RuntimeError):
    pass


class FakeAuthority:
    RECOVERY_BASELINE_COMMIT = BASELINE

    def resolve_completed_authority_commit(self, root, *, fetch_latest=True):
        return COMMIT

    def materialize_authority_core(self, root, cache_core, **kwargs):
        cache_core = Path(cache_core)
        (cache_core / "src").mkdir(parents=True, exist_ok=True)
        (cache_core / "runtime-class-map.json").write_text(
            json.dumps({"aa/a": "l1j/server/A", "aa/a$x": "l1j/server/A$L1R_x"}),
            encoding="utf-8",
        )
        (cache_core / "package-map.csv").write_text(
            "OriginalInternal,DevInternal\naa/a,l1j/server/A\n",
            encoding="utf-8",
        )

    def completed_repair_source_paths(self, root, **kwargs):
        return []


class CaptureDevBase:
    SCHEMA_VERSION = "2"

    def __init__(self):
        self.identity_path = None

    def make_cache_key(
        self,
        original,
        package_map,
        *,
        java_major,
        schema_version,
        completed_authority_commit,
    ):
        self.identity_path = Path(package_map)
        raise StopAfterCacheKey("captured cache identity")


class RuntimeMapCacheContracts(unittest.TestCase):
    def test_dev_base_cache_identity_uses_full_runtime_class_map(self):
        mod = load_module()
        capture = CaptureDevBase()
        mod._AUTHORITY = FakeAuthority()
        mod._DEV_BASE = capture

        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            (root / "l1jserver2.jar").write_bytes(b"production")
            with self.assertRaises(StopAfterCacheKey):
                mod.ensure_fast_dev(root, fetch_latest=False)

        self.assertIsNotNone(capture.identity_path)
        self.assertEqual(capture.identity_path.name, "runtime-class-map.json")


if __name__ == "__main__":
    unittest.main()
