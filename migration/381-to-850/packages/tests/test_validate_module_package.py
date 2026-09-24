import copy
import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[4]
TOOL = ROOT / "migration/381-to-850/packages/tools/validate_module_package.py"


def load_tool():
    spec = importlib.util.spec_from_file_location("validate_module_package", TOOL)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class ModulePackageValidationTest(unittest.TestCase):
    def setUp(self):
        self.tool = load_tool()
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.repo = Path(self.temporary.name)
        self.package = self.repo / "migration/381-to-850/packages/001-test"
        self.package.mkdir(parents=True)
        self.manifest = {
            "schema_version": 1,
            "module_id": "001-test",
            "module_name": "測試項目",
            "decision": "HOLD",
            "server_level": "NOT_FINAL",
            "client_gate": "NOT_PROVEN",
            "deployable": False,
            "source": {
                "requirements": [],
                "381_db": [],
                "381_core": [],
                "381_control": [],
                "381_client": [],
            },
            "imports": {name: [] for name in ("java", "class", "db", "control", "data", "client")},
            "path_map": {"core_fixes": [], "fast_dev_build": []},
            "dependencies": [],
            "validation": [],
            "rollback": [],
        }

    def write_manifest(self, manifest=None):
        value = self.manifest if manifest is None else manifest
        (self.package / "manifest.json").write_text(
            json.dumps(value, ensure_ascii=False, indent=2), encoding="utf-8"
        )

    def add_import(self, category, relative, content=b"content", **extra):
        target = self.package / Path(relative)
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_bytes(content)
        entry = {
            "path": relative.replace("\\", "/"),
            "sha256": hashlib.sha256(content).hexdigest().upper(),
            **extra,
        }
        self.manifest["imports"][category].append(entry)
        return entry

    def add_path_maps(self, logical):
        self.manifest["path_map"]["core_fixes"].append(
            {"logical": logical, "path": f"current/{logical}"}
        )
        self.manifest["path_map"]["fast_dev_build"].append(
            {"logical": logical, "path": f"future/{logical}"}
        )

    def test_accepts_empty_non_deployable_hold_package(self):
        self.write_manifest()
        self.assertEqual([], self.tool.validate_package(self.package, self.repo))

    def test_rejects_unlisted_import_file(self):
        target = self.package / "850匯入/db/install.sql"
        target.parent.mkdir(parents=True)
        target.write_text("SELECT 1;", encoding="utf-8")
        self.write_manifest()
        self.assertIn("IMPORT_FILE_UNLISTED", self.tool.validate_package(self.package, self.repo))

    def test_rejects_hash_mismatch(self):
        entry = self.add_import("db", "850匯入/db/install.sql")
        self.add_path_maps(entry["path"])
        entry["sha256"] = "0" * 64
        self.write_manifest()
        self.assertIn("IMPORT_SHA256_MISMATCH", self.tool.validate_package(self.package, self.repo))

    def test_rejects_java_owned_by_another_module(self):
        entry = self.add_import(
            "java",
            "850匯入/server/java/com/example/Feature.java",
            module_id="999-other",
            class_outputs=["850匯入/server/class/com/example/Feature.class"],
        )
        self.add_import(
            "class",
            "850匯入/server/class/com/example/Feature.class",
            module_id="001-test",
        )
        self.add_path_maps(entry["path"])
        self.add_path_maps("850匯入/server/class/com/example/Feature.class")
        self.write_manifest()
        self.assertIn("IMPORT_MODULE_OWNERSHIP_MISMATCH", self.tool.validate_package(self.package, self.repo))

    def test_rejects_missing_inner_class_output(self):
        source = self.add_import(
            "java",
            "850匯入/server/java/com/example/Feature.java",
            module_id="001-test",
            class_outputs=[
                "850匯入/server/class/com/example/Feature.class",
                "850匯入/server/class/com/example/Feature$Inner.class",
            ],
        )
        self.add_import(
            "class",
            "850匯入/server/class/com/example/Feature.class",
            module_id="001-test",
        )
        self.add_path_maps(source["path"])
        self.add_path_maps("850匯入/server/class/com/example/Feature.class")
        self.write_manifest()
        self.assertIn("JAVA_CLASS_OUTPUT_MISSING", self.tool.validate_package(self.package, self.repo))

    def test_rejects_complete_jar(self):
        entry = self.add_import("data", "850匯入/data/l1jserver2.jar")
        self.add_path_maps(entry["path"])
        self.write_manifest()
        self.assertIn("ARCHIVE_IMPORT_FORBIDDEN", self.tool.validate_package(self.package, self.repo))

    def test_rejects_client_file_without_call_evidence(self):
        entry = self.add_import("client", "850匯入/client/Sprite/123.spr")
        self.add_path_maps(entry["path"])
        self.write_manifest()
        self.assertIn("CLIENT_CALL_EVIDENCE_REQUIRED", self.tool.validate_package(self.package, self.repo))

    def test_rejects_deployable_hold_package(self):
        self.manifest["deployable"] = True
        self.write_manifest()
        self.assertIn("HOLD_CANNOT_BE_DEPLOYABLE", self.tool.validate_package(self.package, self.repo))

    def test_rejects_windows_path_escape(self):
        self.manifest["imports"]["db"].append(
            {"path": "850匯入/db/../../outside.sql", "sha256": "0" * 64}
        )
        self.write_manifest()
        self.assertIn("IMPORT_PATH_INVALID", self.tool.validate_package(self.package, self.repo))

    def test_rejects_missing_fast_dev_mapping(self):
        entry = self.add_import("db", "850匯入/db/install.sql")
        self.manifest["path_map"]["core_fixes"].append(
            {"logical": entry["path"], "path": "db/install.sql"}
        )
        self.write_manifest()
        self.assertIn("FAST_DEV_PATH_MAP_MISSING", self.tool.validate_package(self.package, self.repo))


if __name__ == "__main__":
    unittest.main()
