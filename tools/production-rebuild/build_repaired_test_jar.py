#!/usr/bin/env python3
import hashlib
import importlib.util
import json
import os
import zipfile
from pathlib import Path


_THIS_DIR = Path(__file__).resolve().parent
_INVERSE_PATH = _THIS_DIR / "inverse_remap.py"


def _load_inverse():
    spec = importlib.util.spec_from_file_location("l1jtw85_inverse_remap", _INVERSE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {_INVERSE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with Path(path).open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest().upper()


def _selected_class_files(normalized_classes: Path, selected_tops: set[str]):
    if not selected_tops:
        raise ValueError("selected_tops must not be empty")
    normalized_classes = Path(normalized_classes)
    all_classes = sorted(normalized_classes.rglob("*.class"))
    by_internal = {
        p.relative_to(normalized_classes).as_posix()[:-6]: p
        for p in all_classes
    }
    for top in selected_tops:
        if top not in by_internal:
            raise ValueError(f"selected top-level class was not compiled: {top}")

    chosen = []
    for internal, path in sorted(by_internal.items()):
        if any(internal == top or internal.startswith(top + "$") for top in selected_tops):
            chosen.append((internal, path))
    return chosen


def _write_audit(output_dir: Path, state: dict) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    json_path = output_dir / "production_build_state.json"
    md_path = output_dir / "REPLACED_CLASSES.md"

    json_path.write_text(json.dumps(state, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    lines = [
        "# L1JTW8.5 Replaced Classes",
        "",
        f"- Source authority: `{state['source_authority']}`",
        f"- Original JAR SHA-256: `{state['original_jar_sha256']}`",
        f"- Test JAR SHA-256: `{state['test_jar_sha256']}`",
        f"- Replaced classes: **{state['replaced_class_count']}**",
        "",
        "| ORIGINAL_CLASS_NAME | NORMALIZED_CLASS_NAME | SOURCE_FIX_AUTHORITY | COMPILED_CLASS_PATH | REMAPPED_RUNTIME_CLASS_PATH |",
        "|---|---|---|---|---|",
    ]
    for row in state["replaced_classes"]:
        lines.append(
            "| `{}` | `{}` | `{}` | `{}` | `{}` |".format(
                row["ORIGINAL_CLASS_NAME"],
                row["NORMALIZED_CLASS_NAME"],
                row["SOURCE_FIX_AUTHORITY"],
                row["COMPILED_CLASS_PATH"],
                row["REMAPPED_RUNTIME_CLASS_PATH"],
            )
        )
    md_path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def build_repaired_test_jar(
    *,
    original_jar: Path,
    normalized_classes: Path,
    mapping_csv: Path,
    selected_tops: set[str],
    output_jar: Path,
    expected_original_sha256: str,
    source_authority: str,
) -> dict:
    original_jar = Path(original_jar)
    normalized_classes = Path(normalized_classes)
    mapping_csv = Path(mapping_csv)
    output_jar = Path(output_jar)

    if original_jar.resolve() == output_jar.resolve():
        raise ValueError("output JAR must not overwrite the production JAR")
    if not original_jar.is_file():
        raise FileNotFoundError(original_jar)
    if not normalized_classes.is_dir():
        raise FileNotFoundError(normalized_classes)
    if not mapping_csv.is_file():
        raise FileNotFoundError(mapping_csv)

    original_hash = sha256_file(original_jar)
    expected_hash = expected_original_sha256.strip().upper()
    if original_hash != expected_hash:
        raise ValueError(
            f"original JAR SHA-256 mismatch: expected {expected_hash}, got {original_hash}"
        )

    inverse = _load_inverse()
    reverse = inverse.load_reverse_map(mapping_csv)
    chosen = _selected_class_files(normalized_classes, set(selected_tops))

    patches: dict[str, bytes] = {}
    audit_rows = []
    for normalized_internal, compiled_path in chosen:
        if normalized_internal not in reverse:
            raise KeyError(f"selected compiled class is not mapped: {normalized_internal}")
        runtime_internal = reverse[normalized_internal]
        runtime_path = runtime_internal + ".class"
        if runtime_path in patches:
            raise ValueError(f"runtime output collision: {runtime_path}")

        remapped = inverse.remap_class_bytes(compiled_path.read_bytes(), reverse)
        actual_name = inverse.class_internal_name(remapped)
        if actual_name != runtime_internal:
            raise ValueError(
                f"remapped internal-name mismatch: expected {runtime_internal}, got {actual_name}"
            )
        patches[runtime_path] = remapped
        audit_rows.append({
            "ORIGINAL_CLASS_NAME": runtime_internal,
            "NORMALIZED_CLASS_NAME": normalized_internal,
            "SOURCE_FIX_AUTHORITY": source_authority,
            "COMPILED_CLASS_PATH": compiled_path.as_posix(),
            "REMAPPED_RUNTIME_CLASS_PATH": runtime_path,
        })

    output_jar.parent.mkdir(parents=True, exist_ok=True)
    temp_jar = output_jar.with_name(output_jar.name + ".tmp")
    if temp_jar.exists():
        temp_jar.unlink()

    try:
        with zipfile.ZipFile(original_jar, "r") as zin:
            original_names = {info.filename for info in zin.infolist()}
            missing_runtime = sorted(set(patches) - original_names)
            if missing_runtime:
                raise ValueError(
                    "selected remapped classes are absent from original JAR: "
                    + ", ".join(missing_runtime)
                )

            with zipfile.ZipFile(temp_jar, "w") as zout:
                for info in zin.infolist():
                    data = patches.get(info.filename, zin.read(info.filename))
                    zout.writestr(info, data)

        if sha256_file(original_jar) != original_hash:
            raise RuntimeError("production JAR changed during build")
        os.replace(temp_jar, output_jar)
    except Exception:
        if temp_jar.exists():
            temp_jar.unlink()
        raise

    test_hash = sha256_file(output_jar)
    state = {
        "source_authority": source_authority,
        "original_jar": original_jar.as_posix(),
        "original_jar_sha256": original_hash,
        "test_jar": output_jar.as_posix(),
        "test_jar_sha256": test_hash,
        "selected_top_level_classes": sorted(selected_tops),
        "replaced_class_count": len(audit_rows),
        "replaced_classes": audit_rows,
        "production_jar_modified": False,
    }
    _write_audit(output_jar.parent, state)
    return state
