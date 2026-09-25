import importlib.util
import shutil
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "ensure_dev.py"
NEW_COMMIT = "b" * 40
BASELINE_COMMIT = "a" * 40


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_sync_authority_atomicity", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_source(core: Path, marker: str) -> Path:
    path = core / "src" / "l1j" / "server" / "A.java"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(f"// {marker}\n", encoding="utf-8")
    return path


class FailingScopeAuthority:
    RECOVERY_BASELINE_COMMIT = BASELINE_COMMIT

    def resolve_completed_authority_commit(self, root, *, fetch_latest=True):
        return NEW_COMMIT

    def materialize_authority_core(
        self,
        root,
        cache_core,
        *,
        commit,
        baseline_commit=None,
        fetch_if_missing=True,
    ):
        cache_core = Path(cache_core)
        if cache_core.exists():
            shutil.rmtree(cache_core)
        write_source(cache_core, "NEW AUTHORITY")
        return {
            "commit": commit,
            "baseline_commit": baseline_commit,
            "source_count": 1,
            "cached": False,
        }

    def completed_repair_source_paths(
        self,
        root,
        *,
        commit,
        baseline_commit=None,
        fetch_if_missing=True,
    ):
        raise RuntimeError("scope boom")


class SyncAuthorityAtomicityContracts(unittest.TestCase):
    def test_sync_restores_previous_authority_when_scope_resolution_fails(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            (root / "l1jserver2.jar").write_bytes(b"production")
            (root / "lib").mkdir()
            write_source(root / "core", "WORKING")

            authority_core = root / ".build850" / "cache" / "completed-authority-core"
            previous = write_source(authority_core, "OLD AUTHORITY")
            mod._AUTHORITY = FailingScopeAuthority()

            with self.assertRaisesRegex(RuntimeError, "scope boom"):
                mod.ensure_fast_dev(
                    root,
                    fetch_latest=False,
                    sync_working_core=True,
                )

            restored = authority_core / "src" / "l1j" / "server" / "A.java"
            self.assertTrue(restored.is_file())
            self.assertEqual(restored.read_text(encoding="utf-8"), previous.read_text(encoding="utf-8"))
            self.assertIn("OLD AUTHORITY", restored.read_text(encoding="utf-8"))


if __name__ == "__main__":
    unittest.main()
