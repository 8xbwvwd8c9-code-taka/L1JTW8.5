#!/usr/bin/env python3
import argparse
import csv
import json
import subprocess
from pathlib import Path


NORMALIZED_PREFIX = "recovery/normalized-src-vf/"


def load_top_level_mappings(mapping_csv: Path) -> dict[str, str]:
    mapping_csv = Path(mapping_csv)
    result: dict[str, str] = {}
    with mapping_csv.open(encoding="utf-8-sig", newline="") as f:
        reader = csv.DictReader(f)
        required = {"OldInternal", "NewInternal", "Kind"}
        if not reader.fieldnames or not required.issubset(reader.fieldnames):
            raise ValueError(f"mapping CSV missing columns: {sorted(required)}")
        for row in reader:
            if row["Kind"] != "TOP_LEVEL":
                continue
            new = row["NewInternal"].strip()
            old = row["OldInternal"].strip()
            if not new or not old:
                raise ValueError("empty top-level mapping")
            if new in result and result[new] != old:
                raise ValueError(f"duplicate normalized top-level mapping: {new}")
            result[new] = old
    if not result:
        raise ValueError("no TOP_LEVEL mappings found")
    return result


def select_from_changes(changes, top_level_mappings: dict[str, str]) -> list[str]:
    selected = set()
    for status, raw_path in changes:
        status = str(status).strip()
        path = str(raw_path).replace("\\", "/").strip()
        if status not in {"A", "M"}:
            raise ValueError(f"unsafe normalized-source change status {status}: {path}")
        if not path.startswith(NORMALIZED_PREFIX):
            raise ValueError(f"change outside normalized source authority: {path}")
        if not path.endswith(".java"):
            raise ValueError(f"unexpected non-Java normalized source change: {path}")
        normalized_top = path[len(NORMALIZED_PREFIX):-5]
        if not normalized_top.startswith("l1r/"):
            raise ValueError(f"unexpected normalized namespace: {normalized_top}")
        if normalized_top not in top_level_mappings:
            raise ValueError(f"changed normalized source has no runtime TOP_LEVEL mapping: {normalized_top}")
        selected.add(normalized_top)
    return sorted(selected)


def git_normalized_changes(repo_root: Path, baseline_ref: str, current_ref: str = "HEAD"):
    repo_root = Path(repo_root)
    cmd = [
        "git", "diff", "--name-status", "--no-renames",
        baseline_ref, current_ref, "--", "recovery/normalized-src-vf",
    ]
    proc = subprocess.run(
        cmd,
        cwd=repo_root,
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
        encoding="utf-8",
        errors="replace",
    )
    changes = []
    for line in proc.stdout.splitlines():
        if not line.strip():
            continue
        parts = line.split("\t")
        if len(parts) != 2:
            raise ValueError(f"unexpected git diff --name-status row: {line}")
        changes.append((parts[0], parts[1]))
    return changes


def select_completed_repairs(
    *,
    repo_root: Path,
    baseline_ref: str,
    current_ref: str,
    mapping_csv: Path,
    output_file: Path,
    state_json: Path,
) -> dict:
    mappings = load_top_level_mappings(mapping_csv)
    changes = git_normalized_changes(repo_root, baseline_ref, current_ref)
    selected = select_from_changes(changes, mappings)
    if not selected:
        raise ValueError("no completed repaired normalized top-level sources selected")

    output_file = Path(output_file)
    state_json = Path(state_json)
    output_file.parent.mkdir(parents=True, exist_ok=True)
    state_json.parent.mkdir(parents=True, exist_ok=True)
    output_file.write_text("\n".join(selected) + "\n", encoding="utf-8")
    state = {
        "baseline_ref": baseline_ref,
        "current_ref": current_ref,
        "mapping_csv": str(mapping_csv).replace("\\", "/"),
        "changed_normalized_sources": [
            {"status": status, "path": path.replace("\\", "/")} for status, path in changes
        ],
        "selected_normalized_tops": selected,
        "selected_count": len(selected),
        "policy": "A_OR_M_AND_EXISTING_TOP_LEVEL_MAPPING_ONLY",
    }
    state_json.write_text(json.dumps(state, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    return state


def main():
    parser = argparse.ArgumentParser(description="Select completed repaired L1JTW8.5 normalized top-level classes")
    parser.add_argument("--repo-root", default=".")
    parser.add_argument("--baseline-ref", required=True)
    parser.add_argument("--current-ref", default="HEAD")
    parser.add_argument("--mapping-csv", default="recovery/source_namespace_map.csv")
    parser.add_argument("--output", default="recovery/production-build/selected-completed-tops.txt")
    parser.add_argument("--state-json", default="recovery/production-build/selection-state.json")
    args = parser.parse_args()

    repo_root = Path(args.repo_root).resolve()
    state = select_completed_repairs(
        repo_root=repo_root,
        baseline_ref=args.baseline_ref,
        current_ref=args.current_ref,
        mapping_csv=repo_root / args.mapping_csv,
        output_file=repo_root / args.output,
        state_json=repo_root / args.state_json,
    )
    print(json.dumps(state, indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
