#!/usr/bin/env python3
import argparse
import hashlib
import importlib.util
import json
from pathlib import Path


EXPECTED_ORIGINAL_JAR_SHA256 = "8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814"
EXPECTED_DEPLOYABILITY_POLICY = "PROMOTION_COMMIT_PLUS_CURRENT_JAVA8_RC0_ONLY"


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


def _compiled_class_inventory(classes_dir: Path) -> list[str]:
    return sorted(
        path.relative_to(classes_dir).as_posix()
        for path in classes_dir.rglob("*.class")
    )


def _validate_deployability_state(state: dict, *, completed_ref: str, classes_dir: Path) -> None:
    if state.get("completed_ref") != completed_ref:
        raise ValueError(
            f"deployability authority mismatch: expected {completed_ref}, got {state.get('completed_ref')}"
        )
    if state.get("policy") != EXPECTED_DEPLOYABILITY_POLICY:
        raise ValueError(f"unexpected deployability policy: {state.get('policy')}")

    deployable = list(state.get("deployable", []))
    deferred = list(state.get("deferred", []))
    tops = list(state.get("deployable_normalized_tops", []))
    if not deployable or not tops:
        raise ValueError("deployability gate produced no deployable repairs")
    if state.get("deployable_count") != len(deployable) or len(deployable) != len(tops):
        raise ValueError("deployability count mismatch")
    if state.get("deferred_count") != len(deferred):
        raise ValueError("deferred count mismatch")
    if state.get("candidate_count") != len(deployable) + len(deferred):
        raise ValueError("candidate/deployable/deferred counts do not close")
    if len(tops) != len(set(tops)):
        raise ValueError("duplicate deployable normalized top")

    for row in deployable:
        if row.get("status") != "DEPLOYABLE" or row.get("javac_exit") != 0:
            raise ValueError(f"invalid deployable row: {row.get('normalized_top')}")
        if row.get("normalized_top") not in tops:
            raise ValueError(f"deployable row omitted from top list: {row.get('normalized_top')}")
    for row in deferred:
        if row.get("status") != "DEFERRED":
            raise ValueError(f"invalid deferred row: {row.get('normalized_top')}")

    if not classes_dir.is_dir():
        raise FileNotFoundError(classes_dir)
    inventory = _compiled_class_inventory(classes_dir)
    if not inventory:
        raise ValueError("deployable classes directory is empty")
    inventory_internal = [entry[:-6] for entry in inventory]
    for top in tops:
        if top not in inventory_internal:
            raise ValueError(f"deployable top-level class missing from compiled output: {top}")
    for internal in inventory_internal:
        if not any(internal == top or internal.startswith(top + "$") for top in tops):
            raise ValueError(f"compiled output contains non-deployable class: {internal}")


def _load_or_compile_deployability(*, repo_root: Path, completed_ref: str, deployable_dir: Path) -> dict:
    state_path = deployable_dir / "DEPLOYABILITY_STATE.json"
    classes_dir = deployable_dir / "classes"
    if state_path.is_file():
        state = json.loads(state_path.read_text(encoding="utf-8"))
        try:
            _validate_deployability_state(
                state, completed_ref=completed_ref, classes_dir=classes_dir
            )
            print("REUSE_DEPLOYABILITY_STATE=YES", flush=True)
            return state
        except Exception as exc:
            print(f"REUSE_DEPLOYABILITY_STATE=NO reason={exc}", flush=True)

    compiler = _load_sibling("compile_deployable_repairs.py", "l1jtw85_compile_deployable")
    state = compiler.compile_deployable_repairs(
        repo_root=repo_root,
        completed_ref=completed_ref,
        output_dir=deployable_dir,
    )
    _validate_deployability_state(
        state, completed_ref=completed_ref, classes_dir=classes_dir
    )
    return state


def _write_pipeline_result(output_dir: Path, state: dict) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    (output_dir / "PIPELINE_RESULT.json").write_text(
        json.dumps(state, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )

    deployability = state["deployability"]
    build = state["build"]
    validation = state["validation"]
    lines = [
        "# L1JTW8.5 Production Rebuild Pipeline",
        "",
        f"Status: **{'PASS' if state['pass'] else 'FAIL'}**",
        "",
        f"- Completed repair authority: `{state['completed_ref']}`",
        f"- Selection policy: `{deployability['policy']}`",
        f"- Promotion candidates: **{deployability['candidate_count']}**",
        f"- Java 8 deployable top-level repairs: **{deployability['deployable_count']}**",
        f"- Deferred top-level repairs: **{deployability['deferred_count']}**",
        f"- Replaced runtime classes incl. inner/anonymous: **{build['replaced_class_count']}**",
        f"- Original JAR SHA-256: `{state['original_jar_sha256_before']}`",
        f"- Original JAR unchanged: **{'YES' if state['original_jar_unchanged'] else 'NO'}**",
        f"- Structural validation: **{'PASS' if validation['pass'] else 'FAIL'}**",
        f"- Test JAR SHA-256: `{build['test_jar_sha256']}`",
        f"- Test JAR: `{build['test_jar']}`",
        "",
        "Deferred repairs are intentionally left as the original runtime classes; they are not silently included.",
        "This is a structural/build PASS only. Server startup and unchanged 8.50c client login remain separate runtime gates.",
    ]
    (output_dir / "PIPELINE_RESULT.md").write_text(
        "\n".join(lines) + "\n", encoding="utf-8"
    )


def run_pipeline(*, repo_root: Path, completed_ref: str) -> dict:
    repo_root = Path(repo_root).resolve()
    original_jar = repo_root / "l1jserver2.jar"
    mapping_csv = repo_root / "recovery" / "source_namespace_map.csv"
    output_dir = repo_root / "recovery" / "production-build"
    deployable_dir = output_dir / "deployable"
    deployable_classes = deployable_dir / "classes"
    output_jar = output_dir / "l1jserver2.repaired-test.jar"

    if not original_jar.is_file():
        raise FileNotFoundError(original_jar)
    if not mapping_csv.is_file():
        raise FileNotFoundError(mapping_csv)

    original_before = sha256_file(original_jar)
    if original_before != EXPECTED_ORIGINAL_JAR_SHA256:
        raise ValueError(
            f"original JAR SHA-256 mismatch: expected {EXPECTED_ORIGINAL_JAR_SHA256}, got {original_before}"
        )

    deployability = _load_or_compile_deployability(
        repo_root=repo_root,
        completed_ref=completed_ref,
        deployable_dir=deployable_dir,
    )
    selected_tops = set(deployability["deployable_normalized_tops"])
    source_authority = (
        f"completed/l1jtw85-core-fixes@{completed_ref}; "
        f"policy={EXPECTED_DEPLOYABILITY_POLICY}"
    )

    builder = _load_sibling("build_repaired_test_jar.py", "l1jtw85_build_repaired")
    build_state = builder.build_repaired_test_jar(
        original_jar=original_jar,
        normalized_classes=deployable_classes,
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
        "completed_ref": completed_ref,
        "source_authority": source_authority,
        "original_jar_sha256_before": original_before,
        "original_jar_sha256_after": original_after,
        "original_jar_unchanged": unchanged,
        "deployability": deployability,
        "build": build_state,
        "validation": validation_state,
        "runtime_startup_gate": "NOT_RUN",
        "runtime_login_gate": "NOT_RUN",
    }
    _write_pipeline_result(output_dir, state)
    print(json.dumps(state, indent=2, ensure_ascii=False), flush=True)
    return state


def main():
    parser = argparse.ArgumentParser(
        description="Build a structurally validated L1JTW8.5 test JAR from Java-8-deployable promoted repairs only"
    )
    parser.add_argument("--repo-root", default=".")
    parser.add_argument("--completed-ref", required=True)
    args = parser.parse_args()

    run_pipeline(
        repo_root=Path(args.repo_root),
        completed_ref=args.completed_ref,
    )


if __name__ == "__main__":
    main()
