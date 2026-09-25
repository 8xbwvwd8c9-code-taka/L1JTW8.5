import importlib.util
import json
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


class RuntimeBootstrapContracts(unittest.TestCase):
    def test_promoted_paths_resolve_to_semantic_dev_identities(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            core = Path(td) / "authority-core"
            core.mkdir()
            (core / "source-index.json").write_text(
                json.dumps(
                    [
                        {
                            "recovered_internal": "l1r/aj/C_NpcAction",
                            "dev_internal": "l1j/server/clientpackets/C_NpcAction",
                        },
                        {
                            "recovered_internal": "l1r/au/L1Inventory",
                            "dev_internal": "l1j/server/model/Instance/L1Inventory",
                        },
                    ]
                ),
                encoding="utf-8",
            )
            promoted = [
                "recovery/normalized-src-vf/l1r/au/L1Inventory.java",
                "recovery/normalized-src-vf/l1r/aj/C_NpcAction.java",
            ]
            self.assertEqual(
                mod.promoted_dev_identities(core, promoted),
                [
                    "l1j/server/clientpackets/C_NpcAction",
                    "l1j/server/model/Instance/L1Inventory",
                ],
            )

    def test_unknown_promoted_source_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            core = Path(td) / "authority-core"
            core.mkdir()
            (core / "source-index.json").write_text("[]\n", encoding="utf-8")
            with self.assertRaisesRegex(KeyError, "promoted source missing from source index"):
                mod.promoted_dev_identities(
                    core,
                    ["recovery/normalized-src-vf/l1r/aj/Unknown.java"],
                )

    def test_install_active_core_is_first_run_only(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = root / "authority-core"
            (authority / "src" / "dev").mkdir(parents=True)
            (authority / "src" / "dev" / "A.java").write_text("authority\n", encoding="utf-8")
            (authority / "PINNED_AUTHORITY.json").write_text(
                json.dumps({"commit": "1" * 40}) + "\n",
                encoding="utf-8",
            )
            active = root / "core"

            self.assertTrue(mod.install_active_core(authority, active))
            self.assertEqual((active / "src" / "dev" / "A.java").read_text(encoding="utf-8"), "authority\n")

            (active / "src" / "dev" / "A.java").write_text("local-edit\n", encoding="utf-8")
            self.assertFalse(mod.install_active_core(authority, active))
            self.assertEqual((active / "src" / "dev" / "A.java").read_text(encoding="utf-8"), "local-edit\n")

    def test_stage_completed_overlay_copies_only_selected_class_families(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            compiled = root / "compiled"
            selected = compiled / "dev" / "pkg"
            selected.mkdir(parents=True)
            (selected / "A.class").write_bytes(b"A")
            (selected / "A$Inner.class").write_bytes(b"AI")
            (selected / "B.class").write_bytes(b"B")
            overlay = root / "overlay"

            count = mod.stage_completed_overlay(
                compiled,
                overlay,
                ["dev/pkg/A"],
            )

            self.assertEqual(count, 2)
            self.assertEqual((overlay / "dev" / "pkg" / "A.class").read_bytes(), b"A")
            self.assertEqual((overlay / "dev" / "pkg" / "A$Inner.class").read_bytes(), b"AI")
            self.assertFalse((overlay / "dev" / "pkg" / "B.class").exists())

    def test_stage_completed_overlay_requires_top_level_class(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            compiled = root / "compiled"
            compiled.mkdir()
            with self.assertRaisesRegex(RuntimeError, "compiled completed class family missing"):
                mod.stage_completed_overlay(
                    compiled,
                    root / "overlay",
                    ["dev/pkg/Missing"],
                )


if __name__ == "__main__":
    unittest.main()
