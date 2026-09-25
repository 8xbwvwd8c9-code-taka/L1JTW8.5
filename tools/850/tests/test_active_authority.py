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
    spec = importlib.util.spec_from_file_location("fast_dev_active_authority", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def write_core(core: Path, *, a: str, b: str) -> None:
    src = core / "src" / "l1j" / "server"
    src.mkdir(parents=True, exist_ok=True)
    (src / "A.java").write_text(a, encoding="utf-8")
    (src / "B.java").write_text(b, encoding="utf-8")
    (core / "source-index.json").write_text(
        json.dumps([
            {
                "recovered_internal": "l1r/aa/A",
                "dev_internal": "l1j/server/A",
                "dev_source": "src/l1j/server/A.java",
            },
            {
                "recovered_internal": "l1r/aa/B",
                "dev_internal": "l1j/server/B",
                "dev_source": "src/l1j/server/B.java",
            },
        ]),
        encoding="utf-8",
    )
    (core / "runtime-class-map.json").write_text(
        json.dumps({"aa/a": "l1j/server/A", "aa/b": "l1j/server/B"}),
        encoding="utf-8",
    )
    (core / "package-map.csv").write_text(
        "OriginalInternal,DevInternal\naa/a,l1j/server/A\naa/b,l1j/server/B\n",
        encoding="utf-8",
    )


class ActiveAuthorityContracts(unittest.TestCase):
    def test_active_core_uses_completed_source_only_for_deployable_identity(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            baseline = root / "baseline"
            completed = root / "completed"
            active = root / "active"
            write_core(baseline, a="A baseline\n", b="B baseline\n")
            write_core(completed, a="A repaired\n", b="B repaired but deferred\n")

            result = mod.build_active_authority_core(
                baseline_core=baseline,
                completed_core=completed,
                output_core=active,
                authority_commit=COMMIT,
                baseline_commit=BASELINE,
                deployable_identities=["l1j/server/A"],
                deferred_identities=["l1j/server/B"],
            )

            self.assertEqual(
                (active / "src/l1j/server/A.java").read_text(encoding="utf-8"),
                "A repaired\n",
            )
            self.assertEqual(
                (active / "src/l1j/server/B.java").read_text(encoding="utf-8"),
                "B baseline\n",
            )
            marker = json.loads(
                (active / "PINNED_AUTHORITY.json").read_text(encoding="utf-8")
            )
            self.assertEqual(marker["commit"], COMMIT)
            self.assertEqual(marker["baseline_commit"], BASELINE)
            self.assertEqual(marker["deployable_identities"], ["l1j/server/A"])
            self.assertEqual(marker["deferred_identities"], ["l1j/server/B"])
            self.assertEqual(result["deployable_source_count"], 1)
            self.assertEqual(result["deferred_source_count"], 1)

    def test_unknown_deployable_identity_fails_closed_without_publishing(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            baseline = root / "baseline"
            completed = root / "completed"
            active = root / "active"
            write_core(baseline, a="A baseline\n", b="B baseline\n")
            write_core(completed, a="A repaired\n", b="B repaired\n")

            with self.assertRaisesRegex(RuntimeError, "active authority identity missing"):
                mod.build_active_authority_core(
                    baseline_core=baseline,
                    completed_core=completed,
                    output_core=active,
                    authority_commit=COMMIT,
                    baseline_commit=BASELINE,
                    deployable_identities=["l1j/server/NOPE"],
                    deferred_identities=[],
                )
            self.assertFalse(active.exists())


if __name__ == "__main__":
    unittest.main()
