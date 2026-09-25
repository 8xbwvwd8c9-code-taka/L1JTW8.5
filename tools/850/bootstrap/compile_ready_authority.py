#!/usr/bin/env python3
from __future__ import annotations

import os
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path
from typing import Iterable


# Exact recovery state immediately before the persisted 788-source application
# compile PASS (40a44a2). These scripts are source-representation normalizers,
# not gameplay fixes. Pinning the historical commit prevents current worktree
# experiments from silently changing Fast Dev bootstrap semantics.
PINNED_NORMALIZER_COMMIT = "e83c26c3c10190569acf929e9efe5c101b79163c"

NORMALIZER_SCRIPTS = (
    "build-normalized-stage.py",
    "normalize-protobuf-runtime-imports.py",
    "normalize-protobuf-runtime-type-shadows.py",
    "repair-normalized-builder-collisions.py",
    "normalize-external-builder-alias-refs.py",
    "normalize-nonprotobuf-runtime-g-calls.py",
    "normalize-l1alchemy-local-generics.py",
    "normalize-nonprotobuf-local-generics.py",
    "normalize-nonprotobuf-tail-local-generics.py",
    "normalize-nonprotobuf-tail-local-generics-2.py",
    "normalize-nonprotobuf-tail-local-generics-3.py",
    "normalize-nonprotobuf-overload-shadows.py",
    "normalize-l1thebes-local-generics.py",
    "normalize-nonprotobuf-override-annotations.py",
    "normalize-l1account-base64-compat.py",
    "normalize-protobuf-af-accessors.py",
    "normalize-protobuf-builder-superclass.py",
    "normalize-protobuf-builder-source-bridges.py",
    "normalize-protobuf-parser-bridges.py",
    "normalize-protobuf-duplicate-locals.py",
)


def _git_show(repo_root: Path, commit: str, path: str) -> str:
    proc = subprocess.run(
        ["git", "show", f"{commit}:{path}"],
        cwd=repo_root,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    if proc.returncode != 0:
        detail = proc.stderr.strip() or proc.stdout.strip() or "git show failed"
        raise RuntimeError(f"cannot read pinned normalizer {path} from {commit}: {detail}")
    return proc.stdout


def _extract_scripts(
    repo_root: Path,
    workspace: Path,
    *,
    normalizer_commit: str,
    script_names: Iterable[str],
) -> list[str]:
    target_root = workspace / "tools" / "recovery"
    target_root.mkdir(parents=True, exist_ok=True)
    names = list(script_names)
    if not names:
        raise ValueError("compile-ready normalizer script list is empty")
    for name in names:
        if Path(name).name != name or not name.endswith(".py"):
            raise ValueError(f"invalid normalizer script name: {name}")
        text = _git_show(
            repo_root,
            normalizer_commit,
            f"tools/recovery/{name}",
        )
        (target_root / name).write_text(text, encoding="utf-8", newline="\n")
    return names


def _run_scripts(workspace: Path, script_names: Iterable[str]) -> None:
    for name in script_names:
        script = workspace / "tools" / "recovery" / name
        proc = subprocess.run(
            [sys.executable, str(script)],
            cwd=workspace,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        if proc.returncode != 0:
            detail = proc.stderr.strip() or proc.stdout.strip() or "normalizer failed"
            raise RuntimeError(f"compile-ready normalizer failed: {name}: {detail}")


def prepare_compile_ready_authority(
    repo_root: Path,
    authority_root: Path,
    output_root: Path,
    *,
    normalizer_commit: str = PINNED_NORMALIZER_COMMIT,
    script_names: Iterable[str] = NORMALIZER_SCRIPTS,
) -> dict[str, object]:
    """Replay the proven recovery source-normalization stage on an authority copy.

    The input authority is never modified. Historical scripts are loaded from one
    exact Git commit, executed in order inside an isolated temporary workspace,
    and published only if every script succeeds. The historical stage directory
    then replaces ``recovery/normalized-src-vf`` while namespace evidence remains
    unchanged for the later semantic-package migration.
    """
    repo_root = Path(repo_root).resolve()
    authority_root = Path(authority_root).resolve()
    output_root = Path(output_root).resolve()
    source_root = authority_root / "recovery" / "normalized-src-vf"
    if not source_root.is_dir():
        raise FileNotFoundError(source_root)
    if output_root.exists():
        raise FileExistsError(output_root)

    source_count = sum(1 for _ in source_root.rglob("*.java"))
    if source_count <= 0:
        raise RuntimeError("compile-ready authority has no normalized Java sources")

    output_root.parent.mkdir(parents=True, exist_ok=True)
    temp_parent = Path(tempfile.mkdtemp(prefix="compile-ready.", dir=output_root.parent))
    workspace = temp_parent / "workspace"
    candidate = temp_parent / "candidate"
    try:
        shutil.copytree(authority_root, workspace)
        names = _extract_scripts(
            repo_root,
            workspace,
            normalizer_commit=normalizer_commit,
            script_names=script_names,
        )
        _run_scripts(workspace, names)

        stage_root = workspace / "_normalized-stage-src"
        if not stage_root.is_dir():
            raise RuntimeError("compile-ready normalizers produced no _normalized-stage-src")
        stage_count = sum(1 for _ in stage_root.rglob("*.java"))
        if stage_count != source_count:
            raise RuntimeError(
                "compile-ready normalizers changed source cardinality: "
                f"before={source_count} after={stage_count}"
            )

        normalized = workspace / "recovery" / "normalized-src-vf"
        shutil.rmtree(normalized)
        os.replace(stage_root, normalized)
        shutil.rmtree(workspace / "tools" / "recovery", ignore_errors=True)
        shutil.rmtree(workspace / "_normalized-stage-src", ignore_errors=True)

        os.replace(workspace, candidate)
        os.replace(candidate, output_root)
        return {
            "normalizer_commit": normalizer_commit,
            "scripts": names,
            "source_count": stage_count,
        }
    finally:
        shutil.rmtree(temp_parent, ignore_errors=True)
