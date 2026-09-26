#!/usr/bin/env python3
from __future__ import annotations

import importlib.util
import os
import re
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path
from typing import Iterable


HERE = Path(__file__).resolve().parent
COMMIT_RE = re.compile(r"^[0-9a-fA-F]{40}$")

# Exact recovery state immediately before the persisted 788-source application
# compile PASS (40a44a2). These scripts are source-representation normalizers,
# not gameplay fixes. Pinning the historical commit prevents current worktree
# experiments from silently changing Fast Dev bootstrap semantics.
PINNED_NORMALIZER_COMMIT = "e83c26c3c10190569acf929e9efe5c101b79163c"

# PBMessageALL* source is intentionally baseline-only in Fast Dev because that
# generated family is not round-trippable without the historical protobuf ABI
# research toolchain. Reuse only the proven stage builder plus normalizers that
# do not depend on the historical protobuf compile-view aliases/runtime namespace.
NORMALIZER_SCRIPTS = (
    "build-normalized-stage.py",
    "normalize-l1alchemy-local-generics.py",
    "normalize-nonprotobuf-local-generics.py",
    "normalize-nonprotobuf-tail-local-generics.py",
    "normalize-nonprotobuf-tail-local-generics-2.py",
    "normalize-nonprotobuf-tail-local-generics-3.py",
    "normalize-nonprotobuf-overload-shadows.py",
    "normalize-l1thebes-local-generics.py",
    "normalize-nonprotobuf-override-annotations.py",
    "normalize-l1account-base64-compat.py",
)


def _load_local(filename: str, module_name: str):
    path = HERE / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_PRE_STAGE = _load_local(
    "pre_stage_normalization.py",
    "fast_dev_pre_stage_normalization",
)


def _git(
    repo_root: Path,
    *args: str,
    check: bool = True,
) -> subprocess.CompletedProcess[str]:
    proc = subprocess.run(
        ["git", *args],
        cwd=repo_root,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    if check and proc.returncode != 0:
        detail = proc.stderr.strip() or proc.stdout.strip() or "git command failed"
        raise RuntimeError(detail)
    return proc


def _normalizer_commit_available(repo_root: Path, commit: str) -> bool:
    return _git(
        repo_root,
        "cat-file",
        "-e",
        f"{commit}^{{commit}}",
        check=False,
    ).returncode == 0


def _ensure_normalizer_commit(
    repo_root: Path,
    commit: str,
    *,
    fetch_if_missing: bool,
) -> bool:
    commit = str(commit).strip().lower()
    if not COMMIT_RE.fullmatch(commit):
        raise ValueError("normalizer commit must be an exact 40-hex SHA")
    if _normalizer_commit_available(repo_root, commit):
        return False
    if not fetch_if_missing:
        raise RuntimeError(f"normalizer commit unavailable: {commit}")

    attempts = (
        ("fetch", "--no-tags", "--depth=1", "origin", commit),
        ("fetch", "--no-tags", "origin", commit),
    )
    details: list[str] = []
    for args in attempts:
        proc = _git(repo_root, *args, check=False)
        if proc.returncode == 0 and _normalizer_commit_available(repo_root, commit):
            return True
        detail = proc.stderr.strip() or proc.stdout.strip()
        if detail:
            details.append(detail)

    detail_text = " || ".join(dict.fromkeys(details)) or "git fetch failed"
    raise RuntimeError(
        f"normalizer commit unavailable after exact-SHA fetch: {commit}: {detail_text}"
    )


def _git_show(repo_root: Path, commit: str, path: str) -> str:
    proc = _git(repo_root, "show", f"{commit}:{path}", check=False)
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
    pre_stage: bool = True,
    fetch_if_missing: bool = True,
) -> dict[str, object]:
    """Replay the proven recovery source-normalization stage on an authority copy.

    The input authority is never modified. Completed repair overlay happens before
    this function. Fast Dev first applies idempotent pre-stage representation fixes,
    then loads the historical stage scripts from one exact Git commit and executes
    them in order inside an isolated workspace. A shallow clone fetches only that
    exact pinned commit when it is missing. Publication is atomic and happens only
    if every transform succeeds.
    """
    repo_root = Path(repo_root).resolve()
    authority_root = Path(authority_root).resolve()
    output_root = Path(output_root).resolve()
    normalizer_commit = str(normalizer_commit).strip().lower()
    source_root = authority_root / "recovery" / "normalized-src-vf"
    if not source_root.is_dir():
        raise FileNotFoundError(source_root)
    if output_root.exists():
        raise FileExistsError(output_root)

    source_count = sum(1 for _ in source_root.rglob("*.java"))
    if source_count <= 0:
        raise RuntimeError("compile-ready authority has no normalized Java sources")

    normalizer_commit_fetched = _ensure_normalizer_commit(
        repo_root,
        normalizer_commit,
        fetch_if_missing=fetch_if_missing,
    )

    output_root.parent.mkdir(parents=True, exist_ok=True)
    temp_parent = Path(tempfile.mkdtemp(prefix="compile-ready.", dir=output_root.parent))
    workspace = temp_parent / "workspace"
    candidate = temp_parent / "candidate"
    pre_stage_state: dict[str, object] | None = None
    try:
        shutil.copytree(authority_root, workspace)
        if pre_stage:
            pre_stage_state = _PRE_STAGE.normalize_pre_stage_sources(workspace)

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
            "normalizer_commit_fetched": normalizer_commit_fetched,
            "scripts": names,
            "source_count": stage_count,
            "pre_stage": bool(pre_stage),
            "pre_stage_state": pre_stage_state,
        }
    finally:
        shutil.rmtree(temp_parent, ignore_errors=True)
