#!/usr/bin/env python3
from __future__ import annotations

import json
import os
import shutil
import tempfile
from pathlib import Path
from typing import Iterable


METADATA_FILES = (
    "package-map.csv",
    "source-index.json",
    "runtime-class-map.json",
    "MIGRATION_AUTHORITY.json",
)


def _load_source_index(core: Path) -> dict[str, str]:
    path = Path(core) / "source-index.json"
    if not path.is_file():
        raise FileNotFoundError(path)
    payload = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(payload, list):
        raise ValueError("active authority source index must be a list")

    by_dev: dict[str, str] = {}
    for raw in payload:
        if not isinstance(raw, dict):
            raise ValueError("active authority source index row must be an object")
        dev_internal = str(raw.get("dev_internal", "")).strip("/")
        dev_source = str(raw.get("dev_source", "")).replace("\\", "/").strip("/")
        if not dev_internal or not dev_source:
            raise ValueError("active authority source index row is incomplete")
        if dev_internal in by_dev:
            raise ValueError(f"duplicate active authority identity: {dev_internal}")
        by_dev[dev_internal] = dev_source
    return by_dev


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


def _identity_list(values: Iterable[str]) -> list[str]:
    return sorted({str(value).strip("/") for value in values if str(value).strip("/")})


def build_active_authority_core(
    *,
    baseline_core: Path,
    completed_core: Path,
    output_core: Path,
    authority_commit: str,
    baseline_commit: str,
    deployable_identities: Iterable[str],
    deferred_identities: Iterable[str],
) -> dict[str, object]:
    """Publish source authority that exactly matches the runtime Dev Base policy.

    The baseline semantic core is the default for every source. Only completed
    identities that passed the Java 8 deployability gate are copied from the exact
    completed authority. Formally completed but deferred identities deliberately
    remain at baseline source so ``core/src`` and the runtime class bytes describe
    the same implementation. The completed authority itself is never modified.
    """
    baseline = Path(baseline_core).resolve()
    completed = Path(completed_core).resolve()
    output = Path(output_core).resolve()
    if not (baseline / "src").is_dir():
        raise FileNotFoundError(baseline / "src")
    if not (completed / "src").is_dir():
        raise FileNotFoundError(completed / "src")

    deployable = _identity_list(deployable_identities)
    deferred = _identity_list(deferred_identities)
    overlap = sorted(set(deployable).intersection(deferred))
    if overlap:
        raise RuntimeError(
            "active authority identity cannot be both deployable and deferred: "
            + ", ".join(overlap)
        )

    index = _load_source_index(completed)
    selected = deployable + deferred
    missing = sorted(identity for identity in selected if identity not in index)
    if missing:
        raise RuntimeError(
            "active authority identity missing from completed source index: "
            + ", ".join(missing)
        )

    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="fast-dev-active-authority.", dir=output.parent) as td:
        candidate = Path(td) / "core"
        shutil.copytree(baseline, candidate)

        for name in METADATA_FILES:
            source = completed / name
            if source.is_file():
                shutil.copy2(source, candidate / name)

        for identity in deferred:
            baseline_source = candidate / index[identity]
            if not baseline_source.is_file():
                raise RuntimeError(
                    "active authority baseline source missing for deferred identity "
                    f"{identity}: {index[identity]}"
                )

        for identity in deployable:
            relative = index[identity]
            source = completed / relative
            if not source.is_file():
                raise RuntimeError(
                    "active authority completed source missing for deployable identity "
                    f"{identity}: {relative}"
                )
            target = candidate / relative
            target.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(source, target)

        marker: dict[str, object] = {}
        completed_marker = completed / "PINNED_AUTHORITY.json"
        if completed_marker.is_file():
            try:
                raw = json.loads(completed_marker.read_text(encoding="utf-8"))
            except json.JSONDecodeError as exc:
                raise RuntimeError(
                    f"invalid completed authority marker: {completed_marker}"
                ) from exc
            if isinstance(raw, dict):
                marker.update(raw)
        marker.update(
            {
                "commit": str(authority_commit),
                "baseline_commit": str(baseline_commit),
                "runtime_active": True,
                "deployable_identities": deployable,
                "deferred_identities": deferred,
                "deployable_source_count": len(deployable),
                "deferred_source_count": len(deferred),
            }
        )
        (candidate / "PINNED_AUTHORITY.json").write_text(
            json.dumps(marker, indent=2, sort_keys=True) + "\n",
            encoding="utf-8",
        )
        _publish_directory(candidate, output)

    return {
        "authority_commit": str(authority_commit),
        "baseline_commit": str(baseline_commit),
        "deployable_source_count": len(deployable),
        "deferred_source_count": len(deferred),
        "deployable_identities": deployable,
        "deferred_identities": deferred,
        "source_count": sum(1 for _ in (output / "src").rglob("*.java")),
    }
