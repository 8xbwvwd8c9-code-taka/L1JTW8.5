#!/usr/bin/env python3
import argparse
import hashlib
import json
import re
import sys
from pathlib import Path, PurePosixPath


IMPORT_CATEGORIES = ("java", "class", "db", "control", "data", "client")
IMPORT_PREFIXES = {
    "java": "850匯入/server/java/",
    "class": "850匯入/server/class/",
    "db": "850匯入/db/",
    "control": "850匯入/control/",
    "data": "850匯入/data/",
    "client": "850匯入/client/",
}
DECISIONS = {"MIGRATE", "ADAPT", "MERGE", "HOLD", "SKIP"}
SERVER_LEVELS = {"L1", "L2", "L3", "L4", "NOT_FINAL"}
SHA256 = re.compile(r"[0-9A-F]{64}")


def _append(errors, code):
    if code not in errors:
        errors.append(code)


def _safe_relative(value):
    if not isinstance(value, str) or not value or "\\" in value:
        return False
    path = PurePosixPath(value)
    return not path.is_absolute() and ":" not in value and ".." not in path.parts


def _load_manifest(package_dir, errors):
    manifest_path = package_dir / "manifest.json"
    try:
        return json.loads(manifest_path.read_text(encoding="utf-8"))
    except FileNotFoundError:
        _append(errors, "MANIFEST_NOT_FOUND")
    except (OSError, json.JSONDecodeError):
        _append(errors, "MANIFEST_INVALID_JSON")
    return None


def _validate_shape(manifest, errors):
    required = {
        "schema_version", "module_id", "module_name", "decision",
        "server_level", "client_gate", "deployable", "source", "imports",
        "path_map", "dependencies", "validation", "rollback",
    }
    if not isinstance(manifest, dict) or set(manifest) != required:
        _append(errors, "MANIFEST_SHAPE_INVALID")
        return False
    if manifest["schema_version"] != 1:
        _append(errors, "MANIFEST_SCHEMA_VERSION_INVALID")
    if not isinstance(manifest["module_id"], str) or not manifest["module_id"]:
        _append(errors, "MANIFEST_MODULE_ID_INVALID")
    if not isinstance(manifest["module_name"], str) or not manifest["module_name"]:
        _append(errors, "MANIFEST_MODULE_NAME_INVALID")
    if manifest["decision"] not in DECISIONS:
        _append(errors, "MANIFEST_DECISION_INVALID")
    if manifest["server_level"] not in SERVER_LEVELS:
        _append(errors, "MANIFEST_SERVER_LEVEL_INVALID")
    if not isinstance(manifest["client_gate"], str) or not manifest["client_gate"]:
        _append(errors, "MANIFEST_CLIENT_GATE_INVALID")
    if not isinstance(manifest["deployable"], bool):
        _append(errors, "MANIFEST_DEPLOYABLE_INVALID")
    if manifest["decision"] == "HOLD" and manifest["deployable"]:
        _append(errors, "HOLD_CANNOT_BE_DEPLOYABLE")

    source_keys = {"requirements", "381_db", "381_core", "381_control", "381_client"}
    source = manifest["source"]
    if not isinstance(source, dict) or set(source) != source_keys:
        _append(errors, "MANIFEST_SOURCE_INVALID")
    elif any(not isinstance(source[key], list) for key in source_keys):
        _append(errors, "MANIFEST_SOURCE_INVALID")

    imports = manifest["imports"]
    if not isinstance(imports, dict) or set(imports) != set(IMPORT_CATEGORIES):
        _append(errors, "MANIFEST_IMPORTS_INVALID")
        return False
    if any(not isinstance(imports[key], list) for key in IMPORT_CATEGORIES):
        _append(errors, "MANIFEST_IMPORTS_INVALID")
        return False

    path_map = manifest["path_map"]
    if not isinstance(path_map, dict) or set(path_map) != {"core_fixes", "fast_dev_build"}:
        _append(errors, "MANIFEST_PATH_MAP_INVALID")
        return False
    if not all(isinstance(path_map[key], list) for key in path_map):
        _append(errors, "MANIFEST_PATH_MAP_INVALID")
        return False
    for key in ("dependencies", "validation", "rollback"):
        if not isinstance(manifest[key], list):
            _append(errors, "MANIFEST_SHAPE_INVALID")
    return True


def _map_logicals(entries, error_code, errors):
    logicals = set()
    for entry in entries:
        if not isinstance(entry, dict):
            _append(errors, "MANIFEST_PATH_MAP_INVALID")
            continue
        logical = entry.get("logical")
        path = entry.get("path")
        if not _safe_relative(logical) or not _safe_relative(path):
            _append(errors, "PATH_MAP_PATH_INVALID")
            continue
        if logical in logicals:
            _append(errors, error_code)
        logicals.add(logical)
    return logicals


def validate_package(package_dir: Path, repo_root: Path) -> list[str]:
    package_dir = Path(package_dir).resolve()
    repo_root = Path(repo_root).resolve()
    errors = []
    try:
        package_dir.relative_to(repo_root)
    except ValueError:
        return ["PACKAGE_OUTSIDE_REPOSITORY"]

    manifest = _load_manifest(package_dir, errors)
    if manifest is None or not _validate_shape(manifest, errors):
        return errors

    listed = {}
    java_outputs = set()
    for category in IMPORT_CATEGORIES:
        for entry in manifest["imports"][category]:
            if not isinstance(entry, dict):
                _append(errors, "IMPORT_ENTRY_INVALID")
                continue
            relative = entry.get("path")
            if not _safe_relative(relative) or not relative.startswith(IMPORT_PREFIXES[category]):
                _append(errors, "IMPORT_PATH_INVALID")
                continue
            if relative in listed:
                _append(errors, "IMPORT_PATH_DUPLICATE")
                continue
            listed[relative] = entry
            if PurePosixPath(relative).suffix.lower() in {".jar", ".zip"}:
                _append(errors, "ARCHIVE_IMPORT_FORBIDDEN")
            digest = entry.get("sha256", "")
            if not isinstance(digest, str) or not SHA256.fullmatch(digest):
                _append(errors, "IMPORT_SHA256_INVALID")
            target = package_dir.joinpath(*PurePosixPath(relative).parts)
            if not target.is_file():
                _append(errors, "IMPORT_FILE_MISSING")
            elif hashlib.sha256(target.read_bytes()).hexdigest().upper() != digest:
                _append(errors, "IMPORT_SHA256_MISMATCH")
            if category in {"java", "class"} and entry.get("module_id") != manifest["module_id"]:
                _append(errors, "IMPORT_MODULE_OWNERSHIP_MISMATCH")
            if category == "java":
                outputs = entry.get("class_outputs")
                if not isinstance(outputs, list) or not outputs:
                    _append(errors, "JAVA_CLASS_OUTPUTS_REQUIRED")
                else:
                    for output in outputs:
                        if not _safe_relative(output) or not output.startswith(IMPORT_PREFIXES["class"]):
                            _append(errors, "JAVA_CLASS_OUTPUT_PATH_INVALID")
                        else:
                            java_outputs.add(output)
            if category == "client" and not entry.get("call_evidence"):
                _append(errors, "CLIENT_CALL_EVIDENCE_REQUIRED")

    import_root = package_dir / "850匯入"
    actual = set()
    if import_root.exists():
        for path in import_root.rglob("*"):
            if path.is_file():
                actual.add(path.relative_to(package_dir).as_posix())
    if actual - set(listed):
        _append(errors, "IMPORT_FILE_UNLISTED")
    if set(listed) - actual:
        _append(errors, "IMPORT_FILE_MISSING")

    class_paths = {entry["path"] for entry in manifest["imports"]["class"] if isinstance(entry, dict) and "path" in entry}
    if java_outputs - class_paths:
        _append(errors, "JAVA_CLASS_OUTPUT_MISSING")
    if class_paths - java_outputs:
        _append(errors, "CLASS_WITHOUT_JAVA_SOURCE")

    core_logicals = _map_logicals(
        manifest["path_map"]["core_fixes"], "CORE_PATH_MAP_DUPLICATE", errors
    )
    fast_logicals = _map_logicals(
        manifest["path_map"]["fast_dev_build"], "FAST_DEV_PATH_MAP_DUPLICATE", errors
    )
    logicals = set(listed)
    if logicals - core_logicals:
        _append(errors, "CORE_PATH_MAP_MISSING")
    if logicals - fast_logicals:
        _append(errors, "FAST_DEV_PATH_MAP_MISSING")
    if core_logicals - logicals or fast_logicals - logicals:
        _append(errors, "PATH_MAP_WITHOUT_IMPORT")
    return errors


def main(argv=None) -> int:
    parser = argparse.ArgumentParser(description="Validate one isolated 381 to 850 module package.")
    parser.add_argument("package_dir", type=Path)
    parser.add_argument("--repo-root", type=Path, default=Path.cwd())
    args = parser.parse_args(argv)
    errors = validate_package(args.package_dir, args.repo_root)
    if errors:
        print("\n".join(errors), file=sys.stderr)
        return 1
    print(f"PACKAGE_OK module={args.package_dir.name}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
