import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "ensure_dev.py"
COMMIT = "a" * 40


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_ensure", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class FakeAuthority:
    def __init__(self):
        self.calls = []

    def resolve_completed_authority_commit(self, root, *, fetch_latest=True):
        self.calls.append(("resolve", Path(root), fetch_latest))
        return COMMIT

    def materialize_authority_core(self, root, cache_core, *, commit, fetch_if_missing=True):
        self.calls.append(("materialize", Path(root), Path(cache_core), commit, fetch_if_missing))
        cache_core = Path(cache_core)
        source = cache_core / "src" / "l1j" / "server" / "A.java"
        source.parent.mkdir(parents=True, exist_ok=True)
        source.write_text("package l1j.server; public class A {}\n", encoding="utf-8")
        (cache_core / "runtime-class-map.json").write_text(
            json.dumps({"aa/a": "l1j/server/A"}), encoding="utf-8"
        )
        (cache_core / "package-map.csv").write_text("OriginalInternal,DevInternal\naa/a,l1j/server/A\n", encoding="utf-8")
        return {"commit": commit, "source_count": 1, "cached": False}

    def completed_repair_source_paths(self, root, *, commit):
        self.calls.append(("scope", Path(root), commit))
        return ["recovery/normalized-src-vf/l1r/aa/A.java"]


class FakeDevBase:
    SCHEMA_VERSION = "1"

    def __init__(self, cache_hit=False):
        self.cache_hit = cache_hit
        self.build_calls = []

    def make_cache_key(self, original, package_map, *, java_major, schema_version, completed_authority_commit):
        return {
            "original": str(original),
            "package_map": str(package_map),
            "java_major": java_major,
            "schema_version": schema_version,
            "completed_authority_commit": completed_authority_commit,
        }

    def cache_matches(self, path, expected):
        return self.cache_hit

    def build_dev_base(self, original, output, mapping, *, completed_overlay=None):
        self.build_calls.append(
            {
                "original": Path(original),
                "output": Path(output),
                "mapping": dict(mapping),
                "completed_overlay": None if completed_overlay is None else Path(completed_overlay),
            }
        )
        Path(output).parent.mkdir(parents=True, exist_ok=True)
        Path(output).write_bytes(b"dev-base")
        return {"relocated_classes": 1, "overlaid_classes": 1 if completed_overlay else 0}


class FakeOverlay:
    def __init__(self):
        self.calls = []

    def compile_completed_overlay(self, **kwargs):
        self.calls.append(kwargs)
        output = Path(kwargs["output_dir"])
        target = output / "l1j" / "server" / "A.class"
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_bytes(b"completed")
        return {"source_count": 1, "class_count": 1}


class FakeCompiler:
    instances = []

    def __init__(self, **kwargs):
        self.kwargs = kwargs
        self.seed_calls = []
        type(self).instances.append(self)

    def seed_from_dev_base(self, dev_base, *, baseline_source_root=None):
        self.seed_calls.append((Path(dev_base), Path(baseline_source_root)))
        state = Path(self.kwargs["state_path"])
        deps = Path(self.kwargs["dependency_index_path"])
        state.parent.mkdir(parents=True, exist_ok=True)
        state.write_text("{}", encoding="utf-8")
        deps.write_text("{}", encoding="utf-8")
        return {"mode": "seed", "seeded_identities": ["l1j.server.A"]}


class FakeIncremental:
    IncrementalCompiler = FakeCompiler


class EnsureDevContracts(unittest.TestCase):
    def setUp(self):
        FakeCompiler.instances = []

    def fixture(self, root: Path):
        (root / "l1jserver2.jar").write_bytes(b"production")
        (root / "lib").mkdir()

    def install_fakes(self, mod, *, cache_hit=False):
        authority = FakeAuthority()
        dev_base = FakeDevBase(cache_hit=cache_hit)
        overlay = FakeOverlay()
        mod._AUTHORITY = authority
        mod._DEV_BASE = dev_base
        mod._OVERLAY = overlay
        mod._INCREMENTAL = FakeIncremental
        return authority, dev_base, overlay

    def test_missing_dev_base_bootstraps_from_one_pinned_completed_authority(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            authority, dev_base, overlay = self.install_fakes(mod)

            result = mod.ensure_fast_dev(root, fetch_latest=True)

            self.assertEqual(result["authority_commit"], COMMIT)
            self.assertEqual(len(dev_base.build_calls), 2)
            self.assertIsNone(dev_base.build_calls[0]["completed_overlay"])
            self.assertEqual(
                dev_base.build_calls[1]["completed_overlay"],
                root / ".build850" / "cache" / "completed-repair-overlay",
            )
            self.assertEqual(len(overlay.calls), 1)
            self.assertEqual(overlay.calls[0]["normalized_source_paths"], ["recovery/normalized-src-vf/l1r/aa/A.java"])
            self.assertEqual(len(FakeCompiler.instances), 1)
            self.assertEqual(
                FakeCompiler.instances[0].seed_calls[0][1],
                root / ".build850" / "cache" / "completed-authority-core" / "src",
            )

    def test_existing_working_core_is_never_overwritten_by_bootstrap(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            work = root / "core" / "src" / "l1j" / "server" / "A.java"
            work.parent.mkdir(parents=True)
            work.write_text("// WIP MUST SURVIVE\n", encoding="utf-8")
            self.install_fakes(mod)

            mod.ensure_fast_dev(root, fetch_latest=False)

            self.assertEqual(work.read_text(encoding="utf-8"), "// WIP MUST SURVIVE\n")

    def test_missing_working_core_is_seeded_from_pinned_authority(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            self.install_fakes(mod)

            mod.ensure_fast_dev(root, fetch_latest=False)

            copied = root / "core" / "src" / "l1j" / "server" / "A.java"
            self.assertTrue(copied.is_file())
            self.assertIn("public class A", copied.read_text(encoding="utf-8"))

    def test_dev_base_rebuild_discards_stale_runtime_overlay_before_reseeding(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            stale = root / ".build850" / "classes" / "l1j" / "server" / "Old.class"
            stale.parent.mkdir(parents=True)
            stale.write_bytes(b"stale")
            self.install_fakes(mod, cache_hit=False)

            mod.ensure_fast_dev(root, fetch_latest=False)

            self.assertFalse(stale.exists())
            self.assertTrue((root / ".build850" / "state.json").is_file())


if __name__ == "__main__":
    unittest.main()
