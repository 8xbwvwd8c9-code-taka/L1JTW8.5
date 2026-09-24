import copy
import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[4]
TOOL = ROOT / "migration/381-to-850/packages/tools/build_reverse_queue.py"
QUEUE = ROOT / "migration/381-to-850/packages/reverse-order.json"


def load_tool():
    spec = importlib.util.spec_from_file_location("build_reverse_queue", TOOL)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class ReverseQueueTest(unittest.TestCase):
    def setUp(self):
        self.tool = load_tool()
        self.queue = self.tool.load_queue(QUEUE)

    def write_queue(self, queue):
        temporary = tempfile.TemporaryDirectory()
        self.addCleanup(temporary.cleanup)
        path = Path(temporary.name) / "queue.json"
        path.write_text(json.dumps(queue, ensure_ascii=False), encoding="utf-8")
        return path

    def test_loads_all_items_in_project_reverse_order(self):
        items = self.queue["items"]
        self.assertEqual(53, len(items))
        self.assertEqual("w_變身賦予狀態_道具", items[0]["module_name"])
        self.assertEqual("技能等級化", items[-1]["module_name"])
        self.assertEqual(list(range(1, 54)), [item["sequence"] for item in items])
        self.assertEqual("變身賦予狀態", self.queue["package_folders"][items[0]["module_id"]])
        self.assertEqual("技能等級化與覺醒進度", self.queue["package_folders"][items[-1]["module_id"]])

    def test_all_queue_items_map_to_existing_package_folders(self):
        packages_root = QUEUE.parent
        self.assertEqual([], self.tool.verify_package_folders(self.queue, packages_root))

    def test_rejects_missing_sequence_number(self):
        changed = copy.deepcopy(self.queue)
        changed["items"][10]["sequence"] = 12
        with self.assertRaisesRegex(ValueError, "QUEUE_SEQUENCE_INVALID"):
            self.tool.load_queue(self.write_queue(changed))

    def test_rejects_duplicate_module_name(self):
        changed = copy.deepcopy(self.queue)
        changed["items"][1]["module_name"] = changed["items"][0]["module_name"]
        with self.assertRaisesRegex(ValueError, "QUEUE_MODULE_NAME_DUPLICATE"):
            self.tool.load_queue(self.write_queue(changed))

    def test_rejects_duplicate_module_id(self):
        changed = copy.deepcopy(self.queue)
        changed["items"][1]["module_id"] = changed["items"][0]["module_id"]
        with self.assertRaisesRegex(ValueError, "QUEUE_MODULE_ID_DUPLICATE"):
            self.tool.load_queue(self.write_queue(changed))

    def test_rejects_package_folder_map_missing_item(self):
        changed = copy.deepcopy(self.queue)
        del changed["package_folders"][changed["items"][0]["module_id"]]
        with self.assertRaisesRegex(ValueError, "QUEUE_PACKAGE_FOLDER_MAP_INVALID"):
            self.tool.load_queue(self.write_queue(changed))

    def test_reports_source_hash_drift(self):
        with tempfile.TemporaryDirectory() as temporary:
            source = Path(temporary) / "天堂企劃.txt"
            source.write_bytes(b"changed requirements")
            self.assertNotEqual(
                self.queue["source"]["sha256"],
                hashlib.sha256(source.read_bytes()).hexdigest().upper(),
            )
            self.assertEqual(
                ["SOURCE_SHA256_MISMATCH"],
                self.tool.verify_source(self.queue, source),
            )

    def test_accepts_source_with_recorded_hash(self):
        source = Path(r"I:\天堂企劃.txt")
        if not source.exists():
            self.skipTest("authoritative project file is unavailable")
        self.assertEqual([], self.tool.verify_source(self.queue, source))


if __name__ == "__main__":
    unittest.main()
