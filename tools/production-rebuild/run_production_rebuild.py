#!/usr/bin/env python3
import argparse
import hashlib
import importlib.util
import json
import subprocess
import sys
from pathlib import Path


EXPECTED_ORIGINAL_JAR_SHA256 = "8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814"
EXPECTED_APPLICATION_CLASSES = 1109


def _load_sibling(filename: str, module_name: str):
    path = Path(__file__).resolve().parent / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with Path(path).open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest().upper()


def _run(cmd, *, cwd: Path) -> None:
    print("+ " + " ".join(str(x) for x in cmd), flush=True)
    subprocess.run(cmd, cwd=cwd, check=True)


def _write_pipeline_result(output_dir: Path, state: dict) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    (output_dir / "PIPELINE_RESULT.json").write_text(
        json.dumps(state, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )
    compile_state = state["compile"]
    validation = state["validation"]
    lines = [
        "# L1JTW8.5 Production Rebuild Pipeline",
        "",
        f"Status: **{'PASS' if state['pass'] else 'FAIL'}**",
        "",
        f"- Source authority: `{state['source_authority']}`",
        f"- Recovery baseline: `{state['baseline_ref']}`",
        f"- Original JAR SHA-256: `{state['original_jar_sha256_before']}`",
        f"- Original JAR unchanged: **{'YES' if state['original_jar_unchanged'] else 'NO'}**",
        f"- Java sources: **{compile_state['java_sources']}**",
        f"- Generated application classes: **{compile_state['generated_classes']}**",
        f"- javac errors: **{compile_state['javac_error_headers']}**",
        f"- Selected repaired top-level classes: **{state['selection']['selected_count']}**",
        f"- Replaced runtime classes incl. inner/anonymous: **{state['build']['replaced_class_count']}**",
        f"- Structural validation: **{'PASS' if validation['pass'] else 'FAIL'}**",
        f"- Output: `{state['build']['test_jar']}`",
        "",
        "This is a structural/build PASS only. Server startup and unchanged 8.50c client login remain separate runtime gates.",
    ]
    (output_dir / "PIPELINE_RESULT.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


def run_pipeline(*, repo_root: Path, baseline_ref: str, current_ref: str, source_authority: str) -> dict:
    repo_root = Path(repo_root).resolve()
    original_jar = repo_root / "l1jserver2.jar"
    mapping_csv = repo_root / "recovery" / "source_namespace_map.csv"
    compiled_classes = repo_root / "recovery" / "normalized-build-classes"
    compile_state_path = repo_root / "recovery" / "normalized_compile.json"
    output_dir = repo_root / "recovery" / "production-build"
    output_jar = output_dir / "l1jserver2.repaired-test.jar"
    selection_file = output_dir / "selected-completed-tops.txt"
    selection_state_path = output_dir / "selection-state.json"

    if not original_jar.is_file():
        raise FileNotFoundError(original_jar)
    if not mapping_csv.is_file():
        raise FileNotFoundError(mapping_csv)

    original_before = sha256_file(original_jar)
    if original_before != EXPECTED_ORIGINAL_JAR_SHA256:
        raise ValueError(
            f"original JAR SHA-256 mismatch: expected {EXPECTED_ORIGINAL_JAR_SHA256}, got {original_before}"
        )

    # Reuse the accepted normalized compile pipeline. It has its own donor-class
    # closure check and never puts the donor game JAR on javac's classpath.
    _run(
        [sys.executable, "tools/normalized-recovery/compile-normalized-source.py"],
        cwd=repo_root,
    )
    compile_state = json.loads(compile_state_path.read_text(encoding="utf-8"))
    compile_gate = {
        "compiler_exit": compile_state.get("compiler_exit"),
        "java_sources": compile_state.get("java_sources"),
        "generated_classes": compile_state.get("generated_classes"),
        "donor_application_classes": compile_state.get("donor_application_classes"),
        "built_donor_classes_normalized": compile_state.get("built_donor_classes_normalized"),
        "missing_classes": compile_state.get("missing_classes"),
        "extra_classes": compile_state.get("extra_classes"),
        "javac_error_headers": compile_state.get("javac_error_headers"),
        "javac_error_files": compile_state.get("javac_error_files"),
        "donor_game_jar_on_classpath": compile_state.get("donor_game_jar_on_classpath"),
    }
    if compile_gate["compiler_exit"] != 0:
        raise ValueError(f"normalized compile failed: {compile_gate}")
    if compile_gate["javac_error_headers"] != 0 or compile_gate["javac_error_files"] != 0:
        raise ValueError(f"normalized compile emitted javac errors: {compile_gate}")
    if compile_gate["missing_classes"] != 0 or compile_gate["extra_classes"] != 0:
        raise ValueError(f"normalized class closure failed: {compile_gate}")
    if compile_gate["generated_classes"] != EXPECTED_APPLICATION_CLASSES:
        raise ValueError(
            f"application class-count changed: expected {EXPECTED_APPLICATION_CLASSES}, got {compile_gate['generated_classes']}"
        )
    if compile_gate["donor_application_classes"] != EXPECTED_APPLICATION_CLASSES:
        raise ValueError(f"donor inventory changed unexpectedly: {compile_gate}")
    if compile_gate["built_donor_classes_normalized"] != EXPECTED_APPLICATION_CLASSES:
        raise ValueError(f"not all donor application classes rebuilt: {compile_gate}")
    if compile_gate["donor_game_jar_on_classpath"] is not False:
        raise ValueError("donor game JAR must not be on normalized compile classpath")

    selector = _load_sibling("select_completed_repairs.py", "l1jtw85_select_completed")
    selection = selector.select_completed_repairs(
        repo_root=repo_root,
        baseline_ref=baseline_ref,
        current_ref=current_ref,
        mapping_csv=mapping_csv,
        output_file=selection_file,
        state_json=selection_state_path,
    )
    selected_tops = set(selection["selected_normalized_tops"])

    builder = _load_sibling("build_repaired_test_jar.py", "l1jtw85_build_repaired")
    build_state = builder.build_repaired_test_jar(
        original_jar=original_jar,
        normalized_classes=compiled_classes,
        mapping_csv=mapping_csv,
        selected_tops=selected_tops,
        output_jar=output_jar,
        expected_original_sha256=EXPECTED_ORIGINAL_JAR_SHA256,
        source_authority=source_authority,
    )

    validator = _load_sibling("validate_repaired_test_jar.py", "l1jtw85_validate_repaired")
    validation_state = validator.validate_repaired_test_jar(
        original_jar=original_jar,
        test_jar=output_jar,
        build_state=build_state,
        output_dir=output_dir,
    )

    original_after = sha256_file(original_jar)
    unchanged = original_after == original_before == EXPECTED_ORIGINAL_JAR_SHA256
    if not unchanged:
        raise RuntimeError(
            f"production JAR changed: before={original_before} after={original_after}"
        )

    state = {
        "pass": True,
        "repo_root": repo_root.as_posix(),
        "baseline_ref": baseline_ref,
        "current_ref": current_ref,
        "source_authority": source_authority,
        "original_jar_sha256_before": original_before,
        "original_jar_sha256_after": original_after,
        "original_jar_unchanged": unchanged,
        "compile": compile_gate,
        "selection": selection,
        "build": build_state,
        "validation": validation_state,
        "runtime_login_gate": "NOT_RUN",
    }
    _write_pipeline_result(output_dir, state)
    print(json.dumps(state, indent=2, ensure_ascii=False))
    return state


def main():
    parser = argparse.ArgumentParser(description="Build structurally validated L1JTW8.5 repaired test JAR")
    parser.add_argument("--repo-root", default=".")
    parser.add_argument("--baseline-ref", required=True)
    parser.add_argument("--current-ref", default="HEAD")
    parser.add_argument("--source-authority", required=True)
    args = parser.parse_args()

    run_pipeline(
        repo_root=Path(args.repo_root),
        baseline_ref=args.baseline_ref,
        current_ref=args.current_ref,
        source_authority=args.source_authority,
    )


if __name__ == "__main__":
    main()
