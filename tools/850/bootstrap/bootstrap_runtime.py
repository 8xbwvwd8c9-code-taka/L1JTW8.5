#!/usr/bin/env python3
from __future__ import annotations

import json
import os
import re
import shutil
import tempfile
from pathlib import Path


COMMIT_RE = re.compile(r"^[0-9a-fA-F]{40}$")
METADATA_FILES = (
    "package-map.csv",
    "runtime-class-map.json",
    "source-index.json",
    "MIGRATION_AUTHORITY.json",
    "PINNED_AUTHORITY.json",
)


def _validate_commit(value: str, label: str) -> str:
    commit = str(value).strip()
    if not COMMIT_RE.fullmatch(commit):
        raise ValueError(f"{label} must be an exact 40-hex SHA")
    return commit.lower()


def _java_tree(source_root: Path) -> dict[str, bytes]:
    root = Path(source_root)
    if not root.is_dir():
        raise FileNotFoundError(root)
    return {
        path.relative_to(root).as_posix(): path.read_bytes()
        for path in sorted(root.rglob("*.java"))
    }


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


def _write_marker(
    core: Path,
    *,
    old_commit: str,
    new_commit: str,
    baseline_commit: str,
) -> None:
    payload = {
        "commit": new_commit,
        "previous_commit": old_commit,
        "baseline_commit": baseline_commit,
    }
    (core / "FAST_DEV_AUTHORITY.json").write_text(
        json.dumps(payload, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )


def sync_core_from_authority(
    core: Path,
    *,
    old_authority: Path,
    new_authority: Path,
    old_commit: str,
    new_commit: str,
    baseline_commit: str,
) -> dict[str, object]:
    """Safely three-way sync completed authority changes into a working core tree.

    Authority changes update only working files that still equal the old authority.
    Files already equal to the new authority are accepted. Any locally modified file
    that collides with an authority change fails closed before publication. Local
    edits outside the authority delta are preserved.
    """
    core = Path(core).resolve()
    old_authority = Path(old_authority).resolve()
    new_authority = Path(new_authority).resolve()
    old_commit = _validate_commit(old_commit, "old_commit")
    new_commit = _validate_commit(new_commit, "new_commit")
    baseline_commit = _validate_commit(baseline_commit, "baseline_commit")

    work_src = core / "src"
    old_src = old_authority / "src"
    new_src = new_authority / "src"
    if not work_src.is_dir():
        raise FileNotFoundError(work_src)

    old_files = _java_tree(old_src)
    new_files = _java_tree(new_src)
    work_files = _java_tree(work_src)

    changed_authority = sorted(
        rel
        for rel in (set(old_files) | set(new_files))
        if old_files.get(rel) != new_files.get(rel)
    )

    updates: list[str] = []
    already_new: list[str] = []
    conflicts: list[str] = []
    for rel in changed_authority:
        old_data = old_files.get(rel)
        new_data = new_files.get(rel)
        current = work_files.get(rel)
        if new_data is None:
            raise RuntimeError(f"completed authority deletion is unsupported: {rel}")
        if current == new_data:
            already_new.append(rel)
        elif current == old_data:
            updates.append(rel)
        elif old_data is None and current is None:
            updates.append(rel)
        else:
            conflicts.append(rel)

    if conflicts:
        raise RuntimeError("completed authority sync conflict: " + ", ".join(conflicts))

    changed_set = set(changed_authority)
    preserved_local = sorted(
        rel
        for rel in set(work_files) | set(new_files)
        if rel not in changed_set and work_files.get(rel) != new_files.get(rel)
    )

    core.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="fast-dev-runtime-sync.", dir=core.parent) as td:
        stage = Path(td) / "core"
        shutil.copytree(core, stage)
        stage_src = stage / "src"

        for rel in updates:
            target = stage_src / rel
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(new_files[rel])

        for name in METADATA_FILES:
            source = new_authority / name
            if source.is_file():
                shutil.copy2(source, stage / name)

        _write_marker(
            stage,
            old_commit=old_commit,
            new_commit=new_commit,
            baseline_commit=baseline_commit,
        )
        _publish_directory(stage, core)

    return {
        "authority_updates": len(updates),
        "preserved_local_edits": len(preserved_local),
        "updated_files": updates,
        "already_new_files": already_new,
        "preserved_local_files": preserved_local,
        "changed_authority_files": changed_authority,
        "old_commit": old_commit,
        "new_commit": new_commit,
        "baseline_commit": baseline_commit,
    }
