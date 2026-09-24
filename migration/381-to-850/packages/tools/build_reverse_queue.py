#!/usr/bin/env python3
import argparse
import hashlib
import json
import re
import sys
from pathlib import Path


EXPECTED_COUNT = 53
EXPECTED_FIRST = "w_變身賦予狀態_道具"
EXPECTED_LAST = "技能等級化"
VALID_STATUSES = {"PENDING", "ACTIVE", "HOLD", "COMPLETED"}


def _fail(code):
    raise ValueError(code)


def load_queue(path: Path) -> dict:
    queue = json.loads(Path(path).read_text(encoding="utf-8"))
    if queue.get("schema_version") != 1:
        _fail("QUEUE_SCHEMA_VERSION_INVALID")
    source = queue.get("source")
    if not isinstance(source, dict):
        _fail("QUEUE_SOURCE_INVALID")
    digest = source.get("sha256", "")
    if not re.fullmatch(r"[0-9A-F]{64}", digest):
        _fail("QUEUE_SOURCE_SHA256_INVALID")

    items = queue.get("items")
    if not isinstance(items, list) or len(items) != EXPECTED_COUNT:
        _fail("QUEUE_ITEM_COUNT_INVALID")
    if [item.get("sequence") for item in items] != list(range(1, EXPECTED_COUNT + 1)):
        _fail("QUEUE_SEQUENCE_INVALID")

    module_ids = [item.get("module_id") for item in items]
    if any(not isinstance(value, str) or not value for value in module_ids):
        _fail("QUEUE_MODULE_ID_INVALID")
    if len(set(module_ids)) != len(module_ids):
        _fail("QUEUE_MODULE_ID_DUPLICATE")

    package_folders = queue.get("package_folders")
    if not isinstance(package_folders, dict) or set(package_folders) != set(module_ids):
        _fail("QUEUE_PACKAGE_FOLDER_MAP_INVALID")
    if any(not isinstance(value, str) or not value for value in package_folders.values()):
        _fail("QUEUE_PACKAGE_FOLDER_INVALID")

    module_names = [item.get("module_name") for item in items]
    if any(not isinstance(value, str) or not value for value in module_names):
        _fail("QUEUE_MODULE_NAME_INVALID")
    if len(set(module_names)) != len(module_names):
        _fail("QUEUE_MODULE_NAME_DUPLICATE")
    if module_names[0] != EXPECTED_FIRST or module_names[-1] != EXPECTED_LAST:
        _fail("QUEUE_REVERSE_BOUNDARY_INVALID")

    for item in items:
        if not isinstance(item.get("family"), str) or not item["family"]:
            _fail("QUEUE_FAMILY_INVALID")
        if item.get("status") not in VALID_STATUSES:
            _fail("QUEUE_STATUS_INVALID")
    return queue


def verify_source(queue: dict, source: Path) -> list[str]:
    source = Path(source)
    if not source.is_file():
        return ["SOURCE_NOT_FOUND"]
    actual = hashlib.sha256(source.read_bytes()).hexdigest().upper()
    if actual != queue["source"]["sha256"]:
        return ["SOURCE_SHA256_MISMATCH"]
    return []


def verify_package_folders(queue: dict, packages_root: Path) -> list[str]:
    packages_root = Path(packages_root)
    missing = sorted(
        {
            folder
            for folder in queue["package_folders"].values()
            if not (packages_root / folder).is_dir()
        }
    )
    return [f"PACKAGE_FOLDER_NOT_FOUND:{folder}" for folder in missing]


def main(argv=None) -> int:
    parser = argparse.ArgumentParser(description="Validate the fixed reverse migration queue.")
    parser.add_argument("--source", required=True, type=Path)
    parser.add_argument("--check", required=True, type=Path)
    args = parser.parse_args(argv)
    try:
        queue = load_queue(args.check)
    except (OSError, json.JSONDecodeError, ValueError) as error:
        print(str(error), file=sys.stderr)
        return 1
    errors = verify_source(queue, args.source)
    errors.extend(verify_package_folders(queue, args.check.resolve().parent))
    if errors:
        print("\n".join(errors), file=sys.stderr)
        return 1
    items = queue["items"]
    print(
        "QUEUE_OK "
        f"count={len(items)} "
        f"first={items[0]['module_name']} "
        f"last={items[-1]['module_name']}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
