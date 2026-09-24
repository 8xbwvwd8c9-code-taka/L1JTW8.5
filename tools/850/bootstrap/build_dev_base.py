#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import importlib.util
import json
import os
import re
import tempfile
import zipfile
from pathlib import Path
from typing import Mapping


ROOT = Path(__file__).resolve().parents[3]
TRANSFORMER_PATH = ROOT / "tools" / "production-rebuild" / "inverse_remap.py"
SCHEMA_VERSION = "1"
COMMIT_RE = re.compile(r"^[0-9a-fA-F]{40}$")


def _load_transformer():
    spec = importlib.util.spec_from_file_location("fast_dev_class_transformer", TRANSFORMER_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load class transformer: {TRANSFORMER_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_TRANSFORMER = _load_transformer()
class_utf8_values = _TRANSFORMER.class_utf8_values
class_internal_name = _TRANSFORMER.class_internal_name


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest().upper()


def make_cache_key(
    original_jar: Path,
    package_map: Path,
    *,
    java_major: int,
    schema_version: str = SCHEMA_VERSION,
    completed_authority_commit: str | None = None,
) -> dict[str, object]:
    key: dict[str, object] = {
        "original_jar_sha256": sha256_file(original_jar),
        "package_map_sha256": sha256_file(package_map),
        "java_major": int(java_major),
        "schema_version": str(schema_version),
    }
    if completed_authority_commit is not None:
        commit = str(completed_authority_commit).strip()
        if not COMMIT_RE.fullmatch(commit):
            raise ValueError("completed authority commit must be an exact 40-hex SHA")
        key["completed_authority_commit"] = commit.lower()
    return key


def cache_matches(cache_key_path: Path, expected: Mapping[str, object]) -> bool:
    path = Path(cache_key_path)
    if not path.is_file():
        return False
    try:
        actual = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return False
    return actual == dict(expected)


def _application_roots(mapping: Mapping[str, str]) -> set[str]:
    roots: set[str] = set()
    for source in mapping:
        clean = source.strip("/")
        if clean:
            roots.add(clean.split("/", 1)[0])
    return roots


def _is_application_class(internal: str, roots: set[str]) -> bool:
    root = internal.split("/", 1)[0]
    return root in roots


def _target_class_path(source_internal: str, mapping: Mapping[str, str]) -> str:
    try:
        target = mapping[source_internal]
    except KeyError as exc:
        raise KeyError(f"unmapped application class: {source_internal}") from exc
    if not target or target.startswith("/") or target.endswith("/"):
        raise ValueError(f"invalid dev identity: {target!r}")
    return target + ".class"


def _load_completed_overlay(
    completed_overlay: Path | None,
    normalized_mapping: Mapping[str, str],
) -> dict[str, bytes]:
    if completed_overlay is None:
        return {}

    root = Path(completed_overlay)
    if not root.is_dir():
        raise FileNotFoundError(root)

    allowed = {target + ".class" for target in normalized_mapping.values()}
    overlay: dict[str, bytes] = {}
    for path in sorted(root.rglob("*")):
        if not path.is_file():
            continue
        if path.suffix != ".class":
            raise ValueError(f"completed overlay contains non-class file: {path}")
        relative = path.relative_to(root).as_posix()
        if relative not in allowed:
            raise KeyError(f"completed overlay class is not mapped application authority: {relative}")
        data = path.read_bytes()
        expected_internal = relative[:-6]
        actual_internal = class_internal_name(data)
        if actual_internal != expected_internal:
            raise ValueError(
                "completed overlay class identity mismatch: "
                f"{actual_internal} != {expected_internal}"
            )
        if relative in overlay:
            raise ValueError(f"duplicate completed overlay class: {relative}")
        overlay[relative] = data
    return overlay


def build_dev_base(
    original_jar: Path,
    output_jar: Path,
    mapping: Mapping[str, str],
    *,
    completed_overlay: Path | None = None,
) -> dict[str, int]:
    """Relocate original classes, then overlay formally completed repairs.

    Classes that live under an application root represented by ``mapping`` must be
    mapped explicitly. Classes outside those roots are treated as third-party/runtime
    dependencies and are copied byte-for-byte. ``completed_overlay`` may replace only
    semantic application classes already represented by the mapping; this prevents a
    work/in-progress or foreign class from entering the Dev Base. The original JAR is
    never modified.
    """
    original = Path(original_jar)
    output = Path(output_jar)
    if original.resolve() == output.resolve():
        raise ValueError("output JAR must not overwrite original JAR")
    if not original.is_file():
        raise FileNotFoundError(original)
    if not mapping:
        raise ValueError("empty application mapping")

    normalized_mapping = {str(k).strip("/"): str(v).strip("/") for k, v in mapping.items()}
    if len(set(normalized_mapping.values())) != len(normalized_mapping):
        raise ValueError("duplicate dev class identity")

    completed_classes = _load_completed_overlay(completed_overlay, normalized_mapping)
    roots = _application_roots(normalized_mapping)
    output.parent.mkdir(parents=True, exist_ok=True)
    before_sha = sha256_file(original)
    relocated = 0
    preserved_classes = 0
    preserved_resources = 0

    fd, temp_name = tempfile.mkstemp(prefix=output.name + ".", suffix=".tmp", dir=output.parent)
    os.close(fd)
    temp_path = Path(temp_name)
    try:
        with zipfile.ZipFile(original, "r") as zin, zipfile.ZipFile(temp_path, "w") as zout:
            seen_targets: set[str] = set()
            for info in zin.infolist():
                data = zin.read(info.filename)
                if not info.filename.endswith(".class"):
                    zout.writestr(info, data)
                    preserved_resources += 1
                    continue

                source_internal = info.filename[:-6]
                if source_internal in normalized_mapping:
                    target_path = _target_class_path(source_internal, normalized_mapping)
                    if target_path in seen_targets:
                        raise ValueError(f"duplicate output class: {target_path}")
                    if target_path in completed_classes:
                        remapped = completed_classes[target_path]
                    else:
                        remapped = _TRANSFORMER.remap_class_bytes(data, normalized_mapping)
                    actual = class_internal_name(remapped)
                    expected = normalized_mapping[source_internal]
                    if actual != expected:
                        raise ValueError(f"relocated class identity mismatch: {actual} != {expected}")
                    target_info = zipfile.ZipInfo(target_path, date_time=info.date_time)
                    target_info.compress_type = info.compress_type
                    target_info.external_attr = info.external_attr
                    target_info.comment = info.comment
                    target_info.extra = info.extra
                    zout.writestr(target_info, remapped)
                    seen_targets.add(target_path)
                    relocated += 1
                elif _is_application_class(source_internal, roots):
                    raise KeyError(f"unmapped application class: {source_internal}")
                else:
                    zout.writestr(info, data)
                    preserved_classes += 1

            missing_overlay = set(completed_classes) - seen_targets
            if missing_overlay:
                raise KeyError(
                    "completed overlay target missing from original application JAR: "
                    + ", ".join(sorted(missing_overlay))
                )

        if sha256_file(original) != before_sha:
            raise RuntimeError("original JAR changed during Fast Dev bootstrap")
        os.replace(temp_path, output)
    finally:
        if temp_path.exists():
            temp_path.unlink()

    return {
        "relocated_classes": relocated,
        "preserved_classes": preserved_classes,
        "preserved_resources": preserved_resources,
        "overlaid_classes": len(completed_classes),
    }
