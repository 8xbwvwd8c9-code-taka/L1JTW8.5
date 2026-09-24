#!/usr/bin/env python3
from __future__ import annotations

import argparse
import csv
import importlib.util
import json
import os
import shutil
import tempfile
from pathlib import Path


HERE = Path(__file__).resolve().parent
DEFAULT_REPO_ROOT = Path(__file__).resolve().parents[3]


def _load_local(filename: str, module_name: str):
    path = HERE / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_PACKAGE_MAP = _load_local("package_map.py", "fast_dev_migrate_package_map")
_BOOTSTRAP = _load_local("bootstrap_core.py", "fast_dev_migrate_bootstrap")


def _read_namespace_rows(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8", newline="") as stream:
        return list(csv.DictReader(stream))


def _load_authority(repo_root: Path):
    recovery = repo_root / "recovery"
    rows = _read_namespace_rows(recovery / "source_namespace_map.csv")
    rules = json.loads((repo_root / "tools" / "850" / "bootstrap" / "package_rules.json").read_text(encoding="utf-8"))
    state = json.loads((recovery / "source_namespace_state.json").read_text(encoding="utf-8"))
    entries = _PACKAGE_MAP.build_package_map_from_namespace(rows, rules)
    _PACKAGE_MAP.validate_package_map(entries)
    if len(entries) != int(state["top_level_mappings"]):
        raise ValueError(
            f"top-level authority mismatch: mapped={len(entries)} expected={state['top_level_mappings']}"
        )
    return rows, state, entries


def _load_baseline_sources(repo_root: Path, entries) -> dict[str, str]:
    normalized_root = repo_root / "recovery" / "normalized-src-vf"
    sources: dict[str, str] = {}
    for entry in entries:
        source_path = normalized_root / (entry.recovered_internal + ".java")
        if not source_path.is_file():
            raise FileNotFoundError(
                f"authoritative normalized source missing: {entry.recovered_internal} -> {source_path}"
            )
        sources[entry.recovered_internal] = source_path.read_text(encoding="utf-8")
    return sources


def _write_package_map(path: Path, entries) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8", newline="") as stream:
        writer = csv.writer(stream, lineterminator="\n")
        writer.writerow(
            ["OriginalInternal", "RecoveredInternal", "DevInternal", "SourceFile", "Category"]
        )
        for entry in entries:
            writer.writerow(
                [
                    entry.original_internal,
                    entry.recovered_internal,
                    entry.dev_internal,
                    entry.source_file,
                    entry.category,
                ]
            )


def _write_source_index(path: Path, entries, bootstrap_index: dict[str, dict[str, str]]) -> None:
    payload = []
    for entry in entries:
        authority = bootstrap_index[entry.dev_internal]["authority"]
        payload.append(
            {
                "original_internal": entry.original_internal,
                "recovered_internal": entry.recovered_internal,
                "dev_internal": entry.dev_internal,
                "source_file": entry.source_file,
                "category": entry.category,
                "dev_source": f"src/{entry.dev_internal}.java",
                "authority": authority,
            }
        )
    path.write_text(json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


def _assert_no_recovery_namespace(source_root: Path) -> None:
    offenders: list[str] = []
    for path in source_root.rglob("*.java"):
        text = path.read_text(encoding="utf-8")
        if "l1r." in text:
            offenders.append(path.relative_to(source_root).as_posix())
            if len(offenders) >= 20:
                break
    if offenders:
        raise ValueError("recovery namespace leaked into semantic core: " + ", ".join(offenders))


def _publish_directory(candidate: Path, output: Path) -> None:
    output.parent.mkdir(parents=True, exist_ok=True)
    backup = output.with_name(output.name + ".previous")
    if backup.exists():
        shutil.rmtree(backup)
    had_old = output.exists()
    try:
        if had_old:
            os.replace(output, backup)
        os.replace(candidate, output)
    except Exception:
        if output.exists():
            shutil.rmtree(output)
        if backup.exists():
            os.replace(backup, output)
        raise
    finally:
        if backup.exists():
            shutil.rmtree(backup)


def materialize_sources(repo_root: Path, output_core: Path) -> dict[str, int]:
    repo_root = Path(repo_root).resolve()
    output_core = Path(output_core).resolve()
    rows, state, entries = _load_authority(repo_root)
    sources = _load_baseline_sources(repo_root, entries)

    output_core.parent.mkdir(parents=True, exist_ok=True)
    stage_parent = Path(tempfile.mkdtemp(prefix="fast-dev-core.", dir=output_core.parent))
    candidate = stage_parent / "core"
    candidate.mkdir()
    try:
        tree, bootstrap_index = _BOOTSTRAP.build_core_tree(
            entries=entries,
            baseline_sources=sources,
            completed_sources={},
            quarantined_sources=None,
        )
        expected = int(state["top_level_mappings"])
        if len(tree) != expected:
            raise RuntimeError(f"materialized source count mismatch: {len(tree)} != {expected}")

        source_root = candidate / "src"
        for dev_path, text in tree.items():
            target = source_root / dev_path
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_text(text, encoding="utf-8", newline="\n")

        actual_files = list(source_root.rglob("*.java"))
        if len(actual_files) != expected:
            raise RuntimeError(
                f"materialized Java file count mismatch: {len(actual_files)} != {expected}"
            )

        _assert_no_recovery_namespace(source_root)
        _write_package_map(candidate / "package-map.csv", entries)
        _write_source_index(candidate / "source-index.json", entries, bootstrap_index)
        (candidate / "MIGRATION_AUTHORITY.json").write_text(
            json.dumps(
                {
                    "source_namespace_map": "recovery/source_namespace_map.csv",
                    "normalized_source_root": "recovery/normalized-src-vf",
                    "top_level_sources": expected,
                    "application_class_mappings": int(state["application_class_mappings"]),
                    "duplicate_source_groups": int(state["duplicate_source_groups"]),
                },
                indent=2,
            )
            + "\n",
            encoding="utf-8",
        )

        _publish_directory(candidate, output_core)
    finally:
        if stage_parent.exists():
            shutil.rmtree(stage_parent)

    return {
        "source_count": len(entries),
        "runtime_class_count": int(state["application_class_mappings"]),
        "namespace_row_count": len(rows),
    }


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Materialize L1JTW8.5 Fast Dev semantic core tree")
    parser.add_argument("--repo-root", type=Path, default=DEFAULT_REPO_ROOT)
    parser.add_argument("--output-core", type=Path)
    args = parser.parse_args(argv)
    repo_root = args.repo_root.resolve()
    output = args.output_core.resolve() if args.output_core else repo_root / "core"
    result = materialize_sources(repo_root, output)
    print(
        "MIGRATION=PASS "
        f"SOURCES={result['source_count']} RUNTIME_CLASSES={result['runtime_class_count']} "
        f"OUTPUT={output}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
