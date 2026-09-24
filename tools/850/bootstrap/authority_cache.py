#!/usr/bin/env python3
from __future__ import annotations

import importlib.util
import json
import shutil
import subprocess
import tarfile
import tempfile
from pathlib import Path


HERE = Path(__file__).resolve().parent
PINNED_COMPLETED_COMMIT = "fc473aef65485d1524283fa34d01ab7fad9a7b93"
COMPLETED_BRANCH = "completed/l1jtw85-core-fixes"
ARCHIVE_PATHS = (
    "recovery/source_namespace_map.csv",
    "recovery/source_namespace_state.json",
    "recovery/normalized-src-vf",
)


def _load_local(filename: str, module_name: str):
    path = HERE / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_MIGRATE = _load_local("migrate_core.py", "fast_dev_authority_migrate_core")


def _git(repo_root: Path, *args: str, check: bool = True) -> subprocess.CompletedProcess[str]:
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


def _commit_available(repo_root: Path, commit: str) -> bool:
    proc = _git(repo_root, "cat-file", "-e", f"{commit}^{{commit}}", check=False)
    return proc.returncode == 0


def ensure_completed_authority_commit(
    repo_root: Path,
    *,
    commit: str = PINNED_COMPLETED_COMMIT,
    fetch_if_missing: bool = True,
) -> None:
    repo_root = Path(repo_root).resolve()
    if _commit_available(repo_root, commit):
        return

    if fetch_if_missing:
        _git(
            repo_root,
            "fetch",
            "--no-tags",
            "origin",
            f"{COMPLETED_BRANCH}:refs/remotes/origin/{COMPLETED_BRANCH}",
            check=False,
        )

    if not _commit_available(repo_root, commit):
        raise RuntimeError(
            "completed authority commit unavailable: "
            f"{commit}; fetch {COMPLETED_BRANCH} and retry"
        )


def _cache_hit(cache_core: Path, commit: str) -> bool:
    marker = cache_core / "PINNED_AUTHORITY.json"
    source_root = cache_core / "src"
    if not marker.is_file() or not source_root.is_dir():
        return False
    try:
        payload = json.loads(marker.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return False
    return payload.get("commit") == commit and any(source_root.rglob("*.java"))


def materialize_authority_core(
    repo_root: Path,
    cache_core: Path,
    *,
    commit: str = PINNED_COMPLETED_COMMIT,
    fetch_if_missing: bool = True,
) -> dict[str, object]:
    """Materialize semantic core sources from one exact completed repair commit.

    The active working tree is never used as source authority. Only the pinned Git
    object is archived; newer work/WIP changes therefore cannot enter this cache.
    """
    repo_root = Path(repo_root).resolve()
    cache_core = Path(cache_core).resolve()

    if _cache_hit(cache_core, commit):
        return {
            "commit": commit,
            "source_count": sum(1 for _ in (cache_core / "src").rglob("*.java")),
            "cached": True,
        }

    ensure_completed_authority_commit(
        repo_root,
        commit=commit,
        fetch_if_missing=fetch_if_missing,
    )

    cache_core.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="fast-dev-authority.", dir=cache_core.parent) as td:
        stage = Path(td)
        authority_root = stage / "authority"
        authority_root.mkdir()
        archive_path = stage / "authority.tar"

        _git(
            repo_root,
            "archive",
            "--format=tar",
            f"--output={archive_path}",
            commit,
            *ARCHIVE_PATHS,
        )
        with tarfile.open(archive_path, "r") as archive:
            archive.extractall(authority_root, filter="data")

        rules_source = repo_root / "tools" / "850" / "bootstrap" / "package_rules.json"
        if not rules_source.is_file():
            raise FileNotFoundError(rules_source)
        rules_target = authority_root / "tools" / "850" / "bootstrap" / "package_rules.json"
        rules_target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(rules_source, rules_target)

        result = _MIGRATE.materialize_sources(authority_root, cache_core)

    marker = cache_core / "PINNED_AUTHORITY.json"
    marker.write_text(
        json.dumps(
            {
                "commit": commit,
                "branch": COMPLETED_BRANCH,
                "source_count": int(result["source_count"]),
            },
            indent=2,
        )
        + "\n",
        encoding="utf-8",
    )
    return {
        "commit": commit,
        "source_count": int(result["source_count"]),
        "cached": False,
    }
