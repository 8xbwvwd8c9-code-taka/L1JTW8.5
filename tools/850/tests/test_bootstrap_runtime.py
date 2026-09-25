import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "bootstrap_runtime.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_bootstrap_runtime", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class BootstrapRuntimeContracts(unittest.TestCase):
    def test_completed_scope_maps_only_promoted_recovered_sources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = root / "authority-core"
            (authority / "src" / "l1j" / "server" / "clientpackets").mkdir(parents=True)
            (authority / "src" / "l1j" / "server" / "model").mkdir(parents=True)
            npc = authority / "src" / "l1j" / "server" / "clientpackets" / "C_NpcAction.java"
            inv = authority / "src" / "l1j" / "server" / "model" / "L1Inventory.java"
            npc.write_text("package l1j.server.clientpackets; class C_NpcAction {}\n", encoding="utf-8")
            inv.write_text("package l1j.server.model; class L1Inventory {}\n", encoding="utf-8")
            (authority / "source-index.json").write_text(
                json.dumps(
                    [
                        {
                            "recovered_internal": "l1r/aj/C_NpcAction",
                            "dev_internal": "l1j/server/clientpackets/C_NpcAction",
                            "dev_source": "src/l1j/server/clientpackets/C_NpcAction.java",
                        },
                        {
                            "recovered_internal": "l1r/au/L1Inventory",
                            "dev_internal": "l1j/server/model/L1Inventory",
                            "dev_source": "src/l1j/server/model/L1Inventory.java",
                        },
                    ]
                ),
                encoding="utf-8",
            )

            selected = mod.select_completed_dev_sources(
                authority,
                ["recovery/normalized-src-vf/l1r/aj/C_NpcAction.java"],
            )
            self.assertEqual(selected, [npc])
            self.assertNotIn(inv, selected)

    def test_unknown_completed_scope_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            authority = Path(td) / "authority-core"
            authority.mkdir()
            (authority / "source-index.json").write_text("[]\n", encoding="utf-8")
            with self.assertRaisesRegex(KeyError, "completed promotion source not mapped"):
                mod.select_completed_dev_sources(
                    authority,
                    ["recovery/normalized-src-vf/l1r/zz/Missing.java"],
                )

    def test_initial_core_publish_copies_authority_once_and_preserves_user_edits(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            authority = root / "authority-core"
            source = authority / "src" / "dev" / "A.java"
            source.parent.mkdir(parents=True)
            source.write_text("package dev; class A { int v = 1; }\n", encoding="utf-8")
            (authority / "package-map.csv").write_text("map\n", encoding="utf-8")

            active = root / "core"
            self.assertTrue(mod.publish_initial_core(authority, active))
            active_source = active / "src" / "dev" / "A.java"
            self.assertIn("v = 1", active_source.read_text(encoding="utf-8"))

            active_source.write_text("package dev; class A { int v = 99; }\n", encoding="utf-8")
            source.write_text("package dev; class A { int v = 2; }\n", encoding="utf-8")
            self.assertFalse(mod.publish_initial_core(authority, active))
            self.assertIn("v = 99", active_source.read_text(encoding="utf-8"))


if __name__ == "__main__":
    unittest.main()
