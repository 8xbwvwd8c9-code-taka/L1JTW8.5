#!/usr/bin/env python3
import importlib.util
import json
import zipfile
from pathlib import Path


_THIS_DIR = Path(__file__).resolve().parent
_INVERSE_PATH = _THIS_DIR / "inverse_remap.py"


def _load_inverse():
    spec = importlib.util.spec_from_file_location("l1jtw85_inverse_validation", _INVERSE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {_INVERSE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def _load_state(build_state):
    if isinstance(build_state, dict):
        return build_state
    path = Path(build_state)
    return json.loads(path.read_text(encoding="utf-8"))


def _structural_utf8_values(data: bytes, inverse) -> list[str]:
    entries, _ = inverse._parse_cp(data)
    string_utf8 = set()
    structural_utf8 = set()
    for entry in entries:
        if not entry:
            continue
        tag = entry["tag"]
        if tag == 8:
            string_utf8.add(entry["ref"])
        elif tag in (7, 16, 19, 20):
            structural_utf8.add(entry["ref"])
        elif tag == 12:
            structural_utf8.add(entry["b"])

    values = []
    for idx, entry in enumerate(entries):
        if not idx or not entry or entry["tag"] != 1:
            continue
        # A UTF8 entry used exclusively as a CONSTANT_String literal is data,
        # not a symbolic runtime type reference. Preserve and ignore it here.
        if idx in string_utf8 and idx not in structural_utf8:
            continue
        values.append(entry["text"])
    return values


def _write_result(output_dir: Path, state: dict) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    (output_dir / "VALIDATION_RESULT.json").write_text(
        json.dumps(state, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )
    lines = [
        "# L1JTW8.5 Repaired Test JAR Validation",
        "",
        f"Status: **{'PASS' if state['pass'] else 'FAIL'}**",
        "",
        f"- Replaced classes checked: **{state['replaced_class_count']}**",
        f"- Missing entries: **{len(state['missing_entries'])}**",
        f"- Unexpected entries: **{len(state['unexpected_entries'])}**",
        f"- Internal-name mismatches: **{len(state['internal_name_mismatches'])}**",
        f"- Namespace leaks: **{len(state['namespace_leaks'])}**",
        f"- Changed preserved entries: **{len(state['changed_preserved_entries'])}**",
    ]
    (output_dir / "VALIDATION_RESULT.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


def validate_repaired_test_jar(
    *,
    original_jar: Path,
    test_jar: Path,
    build_state,
    output_dir: Path | None = None,
) -> dict:
    original_jar = Path(original_jar)
    test_jar = Path(test_jar)
    if not original_jar.is_file():
        raise FileNotFoundError(original_jar)
    if not test_jar.is_file():
        raise FileNotFoundError(test_jar)

    state_in = _load_state(build_state)
    replaced_rows = list(state_in.get("replaced_classes", []))
    if not replaced_rows:
        raise ValueError("build state has no replaced_classes")

    replaced_paths = set()
    expected_internal = {}
    for row in replaced_rows:
        runtime_path = row.get("REMAPPED_RUNTIME_CLASS_PATH")
        original_name = row.get("ORIGINAL_CLASS_NAME")
        if not runtime_path or not original_name:
            raise ValueError("invalid replaced_classes row")
        if runtime_path in replaced_paths:
            raise ValueError(f"duplicate replaced runtime path: {runtime_path}")
        replaced_paths.add(runtime_path)
        expected_internal[runtime_path] = original_name

    inverse = _load_inverse()
    result = {
        "pass": False,
        "replaced_class_count": len(replaced_paths),
        "missing_entries": [],
        "unexpected_entries": [],
        "internal_name_mismatches": [],
        "namespace_leaks": [],
        "changed_preserved_entries": [],
    }

    with zipfile.ZipFile(original_jar, "r") as original, zipfile.ZipFile(test_jar, "r") as test:
        original_infos = original.infolist()
        test_infos = test.infolist()
        original_names = [i.filename for i in original_infos]
        test_names = [i.filename for i in test_infos]

        if len(original_names) != len(set(original_names)):
            raise ValueError("original JAR contains duplicate ZIP entries")
        if len(test_names) != len(set(test_names)):
            raise ValueError("test JAR contains duplicate ZIP entries")

        original_set = set(original_names)
        test_set = set(test_names)
        result["missing_entries"] = sorted(original_set - test_set)
        result["unexpected_entries"] = sorted(test_set - original_set)

        for path in sorted(replaced_paths):
            if path not in test_set:
                if path not in result["missing_entries"]:
                    result["missing_entries"].append(path)
                continue
            data = test.read(path)
            try:
                actual = inverse.class_internal_name(data)
            except Exception as exc:
                result["internal_name_mismatches"].append({
                    "path": path,
                    "expected": expected_internal[path],
                    "actual": f"<invalid class: {exc}>",
                })
                continue
            expected = expected_internal[path]
            if actual != expected:
                result["internal_name_mismatches"].append({
                    "path": path,
                    "expected": expected,
                    "actual": actual,
                })

            leaks = sorted({text for text in _structural_utf8_values(data, inverse) if "l1r/" in text})
            if leaks:
                result["namespace_leaks"].append({"path": path, "values": leaks})

        # Everything except explicitly replaced class entries must be byte-identical,
        # including META-INF/MANIFEST.MF, resources and unrepaired/unselected classes.
        for path in sorted(original_set & test_set):
            if path in replaced_paths:
                continue
            if original.read(path) != test.read(path):
                result["changed_preserved_entries"].append(path)

        # A normalized application class entry must never be emitted into runtime JAR.
        for path in sorted(test_set):
            if path.startswith("l1r/") and path.endswith(".class"):
                result["namespace_leaks"].append({"path": path, "values": ["normalized class entry"]})

    result["missing_entries"] = sorted(set(result["missing_entries"]))
    result["pass"] = not any([
        result["missing_entries"],
        result["unexpected_entries"],
        result["internal_name_mismatches"],
        result["namespace_leaks"],
        result["changed_preserved_entries"],
    ])

    out = Path(output_dir) if output_dir is not None else test_jar.parent
    _write_result(out, result)

    if not result["pass"]:
        raise ValueError("repaired test JAR structural validation failed")
    return result
