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
    spec = importlib.util.spec_from_file_location("fast_dev_ensure", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_core_source(core: Path, name: str, text: str) -> Path:
    path = core / "src" / "l1j" / "server" / f"{name}.java"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(text, encoding="utf-8")
    return path


class FakeAuthority:
    RECOVERY_BASELINE_COMMIT = BASELINE

    def __init__(self):
        self.calls = []

    def resolve_completed_authority_commit(self, root, *, fetch_latest=True):
        self.calls.append(("resolve", Path(root), fetch_latest))
        return COMMIT

    def materialize_authority_core(
        self,
        root,
        cache_core,
        *,
        commit,
        baseline_commit=None,
        fetch_if_missing=True,
    ):
        self.calls.append(
            (
                "materialize",
                Path(root),
                Path(cache_core),
                commit,
                baseline_commit,
                fetch_if_missing,
            )
        )
        cache_core = Path(cache_core)
        source = cache_core / "src" / "l1j" / "server" / "A.java"
        source.parent.mkdir(parents=True, exist_ok=True)
        source.write_text("package l1j.server; public class A {}\n", encoding="utf-8")
        (cache_core / "runtime-class-map.json").write_text(
            json.dumps({"aa/a": "l1j/server/A"}), encoding="utf-8"
        )
        (cache_core / "package-map.csv").write_text("OriginalInternal,DevInternal\naa/a,l1j/server/A\n", encoding="utf-8")
        return {
            "commit": commit,
            "baseline_commit": baseline_commit,
            "source_count": 1,
            "promoted_source_count": 1,
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
        self.calls.append(
            ("scope", Path(root), commit, baseline_commit, fetch_if_missing)
        )
        return ["recovery/normalized-src-vf/l1r/aa/A.java"]


class FakeDevBase:
    SCHEMA_VERSION = "2"

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
    def __init__(self, *, deferred=False):
        self.calls = []
        self.deferred = deferred

    def compile_completed_overlay(self, **kwargs):
        self.calls.append(kwargs)
        output = Path(kwargs["output_dir"])
        if self.deferred:
            output.mkdir(parents=True, exist_ok=True)
            return {
                "source_count": 1,
                "class_count": 0,
                "deployable_source_count": 0,
                "deferred_source_count": 1,
                "deployable_identities": [],
                "deferred_identities": ["l1j/server/A"],
                "deferred": [
                    {
                        "identity": "l1j/server/A",
                        "javac_exit": 1,
                        "round": 1,
                        "errors": ["A.java:1: error: cannot find symbol"],
                    }
                ],
                "rounds": 1,
            }
        target = output / "l1j" / "server" / "A.class"
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_bytes(b"completed")
        return {
            "source_count": 1,
            "class_count": 1,
            "deployable_source_count": 1,
            "deferred_source_count": 0,
            "deployable_identities": ["l1j/server/A"],
            "deferred_identities": [],
            "deferred": [],
            "rounds": 1,
        }


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
            materialize_calls = [call for call in authority.calls if call[0] == "materialize"]
            self.assertEqual(len(materialize_calls), 1)
            self.assertEqual(materialize_calls[0][4], BASELINE)
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
            self.assertEqual(result["deployable_source_count"], 1)
            self.assertEqual(result["deferred_source_count"], 0)
            state_path = root / ".build850" / "cache" / "completed-overlay-state.json"
            self.assertTrue(state_path.is_file())
            state = json.loads(state_path.read_text(encoding="utf-8"))
            self.assertEqual(state["authority_commit"], COMMIT)
            self.assertEqual(state["source_count"], 1)
            self.assertEqual(state["deployable_source_count"], 1)
            self.assertEqual(state["deferred_source_count"], 0)

    def test_completed_repair_compile_deferral_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            self.install_fakes(mod)
            mod._OVERLAY = FakeOverlay(deferred=True)

            with self.assertRaisesRegex(RuntimeError, "completed repair overlay deferred"):
                mod.ensure_fast_dev(root, fetch_latest=False)

            self.assertFalse((root / ".build850" / "cache" / "850-dev-base.jar").exists())
            self.assertFalse((root / ".build850" / "state.json").exists())

    def test_cache_hit_requires_matching_completed_overlay_state(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.fixture(root)
            _, dev_base, overlay = self.install_fakes(mod, cache_hit=True)
            cached = root / ".build850" / "cache" / "850-dev-base.jar"
            cached.parent.mkdir(parents=True)
            cached.write_bytes(b"old-cache")

            result = mod.ensure_fast_dev(root, fetch_latest=False)

            self.assertFalse(result["cache_hit"])
            self.assertEqual(len(dev_base.build_calls), 2)
            self.assertEqual(len(overlay.calls), 1)

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

    def test_sync_working_core_updates_completed_files_and_preserves_unrelated_local_edits(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            old = root / "old"
            new = root / "new"
            work = root / "core"
            write_core_source(old, "A", "A old\n")
            write_core_source(old, "B", "B stable\n")
            write_core_source(new, "A", "A completed\n")
            write_core_source(new, "B", "B stable\n")
            a = write_core_source(work, "A", "A old\n")
            b = write_core_source(work, "B", "B local edit\n")

            result = mod.sync_working_core(old, new, work)

            self.assertEqual(a.read_text(encoding="utf-8"), "A completed\n")
            self.assertEqual(b.read_text(encoding="utf-8"), "B local edit\n")
            self.assertEqual(result["updated_files"], ["l1j/server/A.java"])

    def test_sync_working_core_conflict_is_atomic_and_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            old = root / "old"
            new = root / "new"
            work = root / "core"
            write_core_source(old, "A", "A old\n")
            write_core_source(old, "B", "B old\n")
            write_core_source(new, "A", "A completed\n")
            write_core_source(new, "B", "B completed\n")
            a = write_core_source(work, "A", "A local conflict\n")
            b = write_core_source(work, "B", "B old\n")

            with self.assertRaisesRegex(RuntimeError, "completed repair sync conflict"):
                mod.sync_working_core(old, new, work)

            self.assertEqual(a.read_text(encoding="utf-8"), "A local conflict\n")
            self.assertEqual(b.read_text(encoding="utf-8"), "B old\n")

    def test_existing_workspace_pin_disables_refresh_until_explicit_sync(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            work_src = root / "core" / "src"
            work_src.mkdir(parents=True)
            (root / "core" / "PINNED_AUTHORITY.json").write_text(
                json.dumps({"commit": COMMIT}), encoding="utf-8"
            )
            calls = []

            def fake_impl(root_arg, *, fetch_latest=True, sync_working_core=False):
                calls.append((fetch_latest, sync_working_core))
                return {"ok": True}

            mod._ensure_fast_dev_impl = fake_impl

            mod.ensure_fast_dev(root, fetch_latest=True, sync_working_core=False)
            self.assertEqual(calls[-1], (False, False))

            mod.ensure_fast_dev(root, fetch_latest=True, sync_working_core=True)
            self.assertEqual(calls[-1], (True, True))


if __name__ == "__main__":
    unittest.main()
