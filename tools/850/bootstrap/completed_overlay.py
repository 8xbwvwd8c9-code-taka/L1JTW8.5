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


def compile_completed_overlay(
    *,
    authority_core: Path,
    normalized_source_paths: Iterable[str],
    dev_base_jar: Path,
    output_dir: Path,
    lib_dir: Path | None = None,
    javac: str = "javac",
) -> dict[str, int]:
    """Compile only formally completed repair families against semantic Dev Base.

    Source selection comes exclusively from the exact materialized authority core.
    An empty sourcepath and ``-implicit:none`` prevent javac from discovering and
    compiling unrelated worktree sources. Publication is atomic so a failed compile
    leaves the previous completed overlay untouched.
    """
    core = Path(authority_core).resolve()
    base = Path(dev_base_jar).resolve()
    output = Path(output_dir).resolve()
    if not base.is_file():
        raise FileNotFoundError(base)

    selected = select_semantic_sources(core, normalized_source_paths)
    output.parent.mkdir(parents=True, exist_ok=True)

    with tempfile.TemporaryDirectory(prefix="completed-overlay.", dir=output.parent) as td:
        stage = Path(td)
        classes = stage / "classes"
        classes.mkdir()
        empty_sourcepath = stage / "empty-sourcepath"
        empty_sourcepath.mkdir()

        if selected:
            classpath = [str(base)]
            if lib_dir is not None:
                libraries = Path(lib_dir).resolve()
                if not libraries.is_dir():
                    raise FileNotFoundError(libraries)
                classpath.append(str(libraries / "*"))
            command = [
                javac,
                "-encoding", "UTF-8",
                "-source", "8",
                "-target", "8",
                "-implicit:none",
                "-sourcepath", str(empty_sourcepath),
                "-classpath", os.pathsep.join(classpath),
                "-d", str(classes),
                *[str(source) for source, _ in selected],
            ]
            proc = subprocess.run(
                command,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                text=True,
            )
            if proc.returncode != 0:
                raise OverlayCompileError(command, proc.stdout, proc.stderr)

        class_count = _validate_generated_families(classes, selected)
        _publish_directory(classes, output)

    return {
        "source_count": len(selected),
        "class_count": class_count,
    }
