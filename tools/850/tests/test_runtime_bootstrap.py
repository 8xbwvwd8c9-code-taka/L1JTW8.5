import importlib.util
import json
import shutil
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "runtime_bootstrap.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_runtime_bootstrap", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def make_authority_core(root: Path, *, commit: str) -> Path:
    core = root / "authority-core"
    src = core / "src" / "l1j" / "server" / "test"
    src.mkdir(parents=True)
    (src / "A.java").write_text(
        "package l1j.server.test; public class A { public int value() { return 2; } }\n",
        encoding="utf-8",
    )
    (src / "B.java").write_text(
        "package l1j.server.test; public class B { public int value() { return 1; } }\n",
        encoding="utf-8",
    )
    (core / "source-index.json").write_text(
        json.dumps(
            [
                {
                    "original_internal": "aa/a",
                    "recovered_internal": "l1r/aa/A",
                    "dev_internal": "l1j/server/test/A",
                    "dev_source": "src/l1j/server/test/A.java",
                },
                {
                    "original_internal": "aa/b",
                    "recovered_internal": "l1r/aa/B",
                    "dev_internal": "l1j/server/test/B",
                    "dev_source": "src/l1j/server/test/B.java",
                },
            ]
        ),
        encoding="utf-8",
    )
    (core / "PINNED_AUTHORITY.json").write_text(
        json.dumps({"commit": commit}), encoding="utf-8"
    )
    return core


class RuntimeBootstrapContracts(unittest.TestCase):
    def test_promoted_normalized_paths_resolve_to_exact_semantic_sources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = make_authority_core(root, commit="2" * 40)
            resolved = mod.resolve_promoted_semantic_sources(
                authority,
                ["recovery/normalized-src-vf/l1r/aa/A.java"],
            )
            self.assertEqual(len(resolved), 1)
            self.assertEqual(resolved[0]["recovered_internal"], "l1r/aa/A")
            self.assertEqual(resolved[0]["dev_internal"], "l1j/server/test/A")
            self.assertEqual(
                resolved[0]["source_path"],
                authority / "src" / "l1j" / "server" / "test" / "A.java",
            )

    def test_unknown_promoted_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = make_authority_core(root, commit="2" * 40)
            with self.assertRaisesRegex(KeyError, "promoted source is not mapped"):
                mod.resolve_promoted_semantic_sources(
                    authority,
                    ["recovery/normalized-src-vf/l1r/aa/Missing.java"],
                )

    def test_first_run_publishes_authority_core_and_pins_active_commit(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = make_authority_core(root, commit="2" * 40)
            working = root / "core"
            result = mod.publish_or_validate_working_core(
                working,
                authority,
                completed_commit="2" * 40,
            )
            self.assertTrue(result["published"])
            self.assertIn("return 2", (working / "src" / "l1j" / "server" / "test" / "A.java").read_text(encoding="utf-8"))
            marker = json.loads((working / "ACTIVE_AUTHORITY.json").read_text(encoding="utf-8"))
            self.assertEqual(marker["completed_commit"], "2" * 40)

    def test_existing_edited_core_rejects_authority_advance(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            old_authority = make_authority_core(root, commit="1" * 40)
            working = root / "core"
            shutil.copytree(old_authority, working)
            (working / "ACTIVE_AUTHORITY.json").write_text(
                json.dumps({"completed_commit": "1" * 40}), encoding="utf-8"
            )
            source = working / "src" / "l1j" / "server" / "test" / "A.java"
            source.write_text(
                "package l1j.server.test; public class A { public int value() { return 99; } }\n",
                encoding="utf-8",
            )

            new_authority = root / "new-authority"
            shutil.copytree(old_authority, new_authority)
            with self.assertRaisesRegex(RuntimeError, "completed authority advanced"):
                mod.publish_or_validate_working_core(
                    working,
                    new_authority,
                    completed_commit="2" * 40,
                )


if __name__ == "__main__":
    unittest.main()
