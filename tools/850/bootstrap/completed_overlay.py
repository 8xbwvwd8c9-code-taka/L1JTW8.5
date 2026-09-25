#!/usr/bin/env python3
from __future__ import annotations

import json
import os
import shutil
import subprocess
import tempfile
from pathlib import Path
from typing import Iterable


NORMALIZED_PREFIX = "recovery/normalized-src-vf/"


class OverlayCompileError(RuntimeError):
    def __init__(self, command: list[str], stdout: str, stderr: str):
        super().__init__(stderr.strip() or stdout.strip() or "completed overlay javac failed")
        self.command = command
        self.stdout = stdout
        self.stderr = stderr


def _load_source_index(authority_core: Path) -> dict[str, dict[str, str]]:
    path = Path(authority_core) / "source-index.json"
    if not path.is_file():
        raise FileNotFoundError(path)
    payload = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(payload, list):
        raise ValueError("authority source index must be a list")

    by_recovered: dict[str, dict[str, str]] = {}
    for raw in payload:
        if not isinstance(raw, dict):
            raise ValueError("authority source index row must be an object")
        recovered = str(raw.get("recovered_internal", "")).strip("/")
        dev_internal = str(raw.get("dev_internal", "")).strip("/")
        dev_source = str(raw.get("dev_source", ""))
        if not recovered or not dev_internal or not dev_source:
            raise ValueError("authority source index row is incomplete")
        if recovered in by_recovered:
            raise ValueError(f"duplicate recovered authority identity: {recovered}")
        by_recovered[recovered] = {
            "recovered_internal": recovered,
            "dev_internal": dev_internal,
            "dev_source": dev_source,
        }
    return by_recovered


def _recovered_internal(normalized_path: str) -> str:
    value = str(normalized_path).replace("\\", "/")
    if not value.startswith(NORMALIZED_PREFIX) or not value.endswith(".java"):
        raise ValueError(f"invalid normalized repair source path: {normalized_path}")
    internal = value[len(NORMALIZED_PREFIX):-5].strip("/")
    if not internal:
        raise ValueError(f"invalid normalized repair source path: {normalized_path}")
    return internal


def select_semantic_sources(
    authority_core: Path,
    normalized_source_paths: Iterable[str],
) -> list[tuple[Path, str]]:
    core = Path(authority_core).resolve()
    index = _load_source_index(core)
    selected: list[tuple[Path, str]] = []
    seen: set[str] = set()

    for normalized_path in sorted(set(str(path) for path in normalized_source_paths)):
        recovered = _recovered_internal(normalized_path)
        row = index.get(recovered)
        if row is None:
            raise KeyError(
                "completed repair source missing from authority index: "
                f"{normalized_path}"
            )
        dev_internal = row["dev_internal"]
        if dev_internal in seen:
            raise ValueError(f"duplicate completed semantic source identity: {dev_internal}")
        source = core / row["dev_source"]
        if not source.is_file():
            raise FileNotFoundError(source)
        selected.append((source, dev_internal))
        seen.add(dev_internal)

    return selected


def _validate_generated_families(class_root: Path, selected: list[tuple[Path, str]]) -> int:
    identities = {identity for _, identity in selected}
    top_level_found: set[str] = set()
    count = 0

    for path in sorted(class_root.rglob("*.class")):
        internal = path.relative_to(class_root).as_posix()[:-6]
        owner = None
        for identity in identities:
            if internal == identity or internal.startswith(identity + "$"):
                owner = identity
                break
        if owner is None:
            raise RuntimeError(f"javac generated class outside completed repair scope: {internal}")
        if internal == owner:
            top_level_found.add(owner)
        count += 1

    missing = identities - top_level_found
    if missing:
        raise RuntimeError(
            "javac produced no top-level class for completed repair source: "
            + ", ".join(sorted(missing))
        )
    return count


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


def _copy_scope(candidate_classes: Path, accepted_classes: Path) -> int:
    copied = 0
    for source in sorted(candidate_classes.rglob("*.class")):
        rel = source.relative_to(candidate_classes).as_posix()
        internal = rel[:-6]
        target = accepted_classes / rel
        target.parent.mkdir(parents=True, exist_ok=True)
        if target.exists() and target.read_bytes() != source.read_bytes():
            raise RuntimeError(f"completed repair class collision: {internal}")
        shutil.copy2(source, target)
        copied += 1
    return copied


def _error_summary(stdout: str, stderr: str, limit: int = 8) -> list[str]:
    rows: list[str] = []
    for line in (stderr + "\n" + stdout).splitlines():
        text = line.strip()
        if not text:
            continue
        if "error:" in text or text.endswith("errors") or text.endswith("error"):
            rows.append(text)
        if len(rows) >= limit:
            break
    return rows


def _normalize_scopes(
    *,
    normalized_source_paths: Iterable[str] | None,
    normalized_source_scopes: Iterable[Iterable[str]] | None,
) -> list[list[str]]:
    if normalized_source_paths is not None and normalized_source_scopes is not None:
        raise ValueError("provide completed repair paths or scopes, not both")
    if normalized_source_paths is None and normalized_source_scopes is None:
        raise ValueError("completed repair paths or scopes are required")

    if normalized_source_scopes is None:
        return [[path] for path in sorted(set(str(path) for path in normalized_source_paths or []))]

    scopes: list[list[str]] = []
    seen: set[str] = set()
    for raw_scope in normalized_source_scopes:
        scope = sorted(set(str(path) for path in raw_scope))
        if not scope:
            continue
        overlap = seen.intersection(scope)
        if overlap:
            raise ValueError(
                "completed repair source appears in multiple atomic scopes: "
                + ", ".join(sorted(overlap))
            )
        seen.update(scope)
        scopes.append(scope)
    return scopes


def compile_completed_overlay(
    *,
    authority_core: Path,
    normalized_source_paths: Iterable[str] | None = None,
    normalized_source_scopes: Iterable[Iterable[str]] | None = None,
    dev_base_jar: Path,
    output_dir: Path,
    lib_dir: Path | None = None,
    javac: str = "javac",
) -> dict[str, object]:
    """Compile deployable formally completed repair scopes.

    Flat ``normalized_source_paths`` remain backward-compatible and are treated as
    independent one-source scopes. ``normalized_source_scopes`` preserves atomic
    multi-source promotion boundaries: every source in one scope is compiled and
    published together, so repaired APIs may depend on peers from the same formal
    promotion closure without exposing a partially repaired runtime.

    Scopes are gated independently against the semantic Dev Base. Successful scopes
    are accumulated and made available to later rounds so completed repairs may
    depend on earlier completed scopes. A scope that still cannot compile after no
    further progress is deferred as a whole and therefore falls back to relocated
    original runtime classes already present in Dev Base.

    Source selection comes exclusively from the exact materialized completed
    authority. ``-sourcepath`` is empty and ``-implicit:none`` prevents javac from
    discovering unrelated worktree sources. Publication is atomic and replaces any
    stale overlay from an older authority, including the all-deferred case.
    """
    core = Path(authority_core).resolve()
    base = Path(dev_base_jar).resolve()
    output = Path(output_dir).resolve()
    if not base.is_file():
        raise FileNotFoundError(base)

    normalized_scopes = _normalize_scopes(
        normalized_source_paths=normalized_source_paths,
        normalized_source_scopes=normalized_source_scopes,
    )
    selected_scopes = [select_semantic_sources(core, scope) for scope in normalized_scopes]
    selected = [pair for scope in selected_scopes for pair in scope]
    identities = [identity for _, identity in selected]
    if len(set(identities)) != len(identities):
        raise ValueError("duplicate completed semantic source identity across atomic scopes")

    output.parent.mkdir(parents=True, exist_ok=True)

    with tempfile.TemporaryDirectory(prefix="completed-overlay.", dir=output.parent) as td:
        stage = Path(td)
        accepted_classes = stage / "classes"
        accepted_classes.mkdir()
        empty_sourcepath = stage / "empty-sourcepath"
        empty_sourcepath.mkdir()
        work_root = stage / "work"
        work_root.mkdir()

        libraries: Path | None = None
        if lib_dir is not None:
            libraries = Path(lib_dir).resolve()
            if not libraries.is_dir():
                raise FileNotFoundError(libraries)

        pending = list(enumerate(selected_scopes))
        deferred_details: dict[str, dict[str, object]] = {}
        deployable_identities: list[str] = []
        deployable_scope_indexes: set[int] = set()
        accepted_class_count = 0
        round_no = 0

        while pending:
            round_no += 1
            progressed = False
            next_pending: list[tuple[int, list[tuple[Path, str]]]] = []

            for scope_index, scope in pending:
                scope_identities = [identity for _, identity in scope]
                candidate_classes = work_root / f"round-{round_no}" / f"scope-{scope_index}" / "classes"
                candidate_classes.mkdir(parents=True, exist_ok=True)

                classpath = [str(accepted_classes), str(base)]
                if libraries is not None:
                    classpath.append(str(libraries / "*"))
                command = [
                    javac,
                    "-encoding", "UTF-8",
                    "-source", "8",
                    "-target", "8",
                    "-implicit:none",
                    "-sourcepath", str(empty_sourcepath),
                    "-classpath", os.pathsep.join(classpath),
                    "-d", str(candidate_classes),
                    *[str(source) for source, _ in scope],
                ]
                proc = subprocess.run(
                    command,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    text=True,
                )
                if proc.returncode != 0:
                    errors = _error_summary(proc.stdout, proc.stderr)
                    for identity in scope_identities:
                        deferred_details[identity] = {
                            "identity": identity,
                            "scope_identities": sorted(scope_identities),
                            "javac_exit": proc.returncode,
                            "round": round_no,
                            "errors": errors,
                        }
                    next_pending.append((scope_index, scope))
                    continue

                _validate_generated_families(candidate_classes, scope)
                accepted_class_count += _copy_scope(candidate_classes, accepted_classes)
                deployable_identities.extend(scope_identities)
                deployable_scope_indexes.add(scope_index)
                for identity in scope_identities:
                    deferred_details.pop(identity, None)
                progressed = True

            if not progressed:
                pending = next_pending
                break
            pending = next_pending

        deferred_identities = sorted(
            identity
            for _, scope in pending
            for _, identity in scope
        )
        deployable_identities = sorted(set(deployable_identities))
        deployable_set = set(deployable_identities)
        class_count = _validate_generated_families(
            accepted_classes,
            [pair for pair in selected if pair[1] in deployable_set],
        ) if deployable_identities else 0
        if class_count != accepted_class_count:
            raise RuntimeError(
                f"completed overlay class count mismatch: {class_count} != {accepted_class_count}"
            )

        _publish_directory(accepted_classes, output)

    return {
        "source_count": len(selected),
        "scope_count": len(selected_scopes),
        "class_count": class_count,
        "deployable_source_count": len(deployable_identities),
        "deferred_source_count": len(deferred_identities),
        "deployable_scope_count": len(deployable_scope_indexes),
        "deferred_scope_count": len(pending),
        "deployable_identities": deployable_identities,
        "deferred_identities": deferred_identities,
        "deferred": [deferred_details[i] for i in deferred_identities],
        "rounds": round_no,
    }
