import importlib.util
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "package_map.py"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_package_map", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class PackageMapTests(unittest.TestCase):
    def test_known_clientpacket_maps_to_semantic_namespace(self):
        mod = load_module()
        rules = {
            "families": {
                "aj": {"package": "l1j/server/clientpackets", "category": "clientpackets"}
            },
            "overrides": {},
        }
        entries = mod.build_package_map(
            [{"Class": "aj.bk", "SourceFile": "C_NpcAction.java"}], rules
        )
        self.assertEqual(entries[0].original_internal, "aj/bk")
        self.assertEqual(entries[0].recovered_internal, "l1r/aj/C_NpcAction")
        self.assertEqual(entries[0].dev_internal, "l1j/server/clientpackets/C_NpcAction")
        self.assertEqual(entries[0].category, "clientpackets")

    def test_known_datatable_maps_to_semantic_namespace(self):
        mod = load_module()
        rules = {
            "families": {
                "ao": {"package": "l1j/server/datatables", "category": "datatables"}
            },
            "overrides": {},
        }
        entries = mod.build_package_map(
            [{"Class": "ao.a", "SourceFile": "AccountTable.java"}], rules
        )
        self.assertEqual(entries[0].dev_internal, "l1j/server/datatables/AccountTable")

    def test_unknown_application_family_fails_closed(self):
        mod = load_module()
        rules = {"families": {}, "overrides": {}}
        with self.assertRaisesRegex(ValueError, "unmapped application family: zz"):
            mod.build_package_map(
                [{"Class": "zz.a", "SourceFile": "Mystery.java"}], rules
            )

    def test_duplicate_dev_identity_is_rejected(self):
        mod = load_module()
        rules = {
            "families": {
                "aj": {"package": "l1j/server/clientpackets", "category": "clientpackets"},
                "zz": {"package": "l1j/server/clientpackets", "category": "clientpackets"},
            },
            "overrides": {},
        }
        entries = mod.build_package_map(
            [
                {"Class": "aj.a", "SourceFile": "C_Login.java"},
                {"Class": "zz.a", "SourceFile": "C_Login.java"},
            ],
            rules,
        )
        with self.assertRaisesRegex(ValueError, "duplicate DevInternal"):
            mod.validate_package_map(entries)

    def test_server_namespace_is_preserved_without_recovery_prefix(self):
        mod = load_module()
        rules = {"families": {}, "overrides": {}}
        entries = mod.build_package_map(
            [{"Class": "l1j.server.Server", "SourceFile": "Server.java"}], rules
        )
        self.assertEqual(entries[0].original_internal, "l1j/server/Server")
        self.assertEqual(entries[0].recovered_internal, "l1j/server/Server")
        self.assertEqual(entries[0].dev_internal, "l1j/server/Server")


if __name__ == "__main__":
    unittest.main()
