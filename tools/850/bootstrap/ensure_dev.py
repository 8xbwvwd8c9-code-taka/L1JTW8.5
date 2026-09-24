#!/usr/bin/env python3
from __future__ import annotations

import importlib.util
import json
import os
import shutil
import tempfile
from pathlib import Path


HERE = Path(__file__).resolve().parent
ROOT = Path(__file__).resolve().parents[3]


def _load_module(path: Path, module_name: str):
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_AUTHORITY = _load_module(HERE / "authority_cache.py", "fast_dev_ensure_authority")
_DEV_BASE = _load_module(HERE / "build_dev_base.py", "fast_dev_ensure_dev_base")
_OVERLAY = _load_module(HERE / "completed_overlay.py", "fast_dev_ensure_overlay")
_INCREMENTAL = _load_module(
    ROOT / "tools" / "850" / "compiler" / "incremental.py",
    "fast_dev_ensure_incremental",
)


def _atomic_write_json(path: Path, payload: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    fd, name = tempfile.mkstemp(prefix=path.name + ".", suffix=".tmp", dir=path.parent)
    try:
        with os.fdopen(fd, "w", encoding="utf-8", newline="\n") as stream:
            json.dump(payload, stream, ensure_ascii=False, indent=2, sort_keys=True)
            stream.write("\n")
        os.replace(name, path)
    except Exception:
        try:
            os.unlink(name)
        except FileNotFoundError:
            pass
        raise


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


def _load_runtime_map(authority_core: Path) -> dict[str, str]:
    path = Path(authority_core) / "runtime-class-map.json"
    if not path.is_file():
        raise FileNotFoundError(path)
    raw = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(raw, dict) or not raw:
        raise ValueError("runtime class map must be a non-empty object")
    mapping = {str(k).strip("/"): str(v).strip("/") for k, v in raw.items()}
    if any(not key or not value for key, value in mapping.items()):
        raise ValueError("runtime class map contains an empty identity")
    if len(set(mapping.values())) != len(mapping):
        raise ValueError("runtime class map contains duplicate semantic identities")
    return mapping


def _ensure_working_core(root: Path, authority_core: Path) -> str:
    core = root / "core"
    source_root = core / "src"
    if source_root.is_dir():
        return "preserved"
    if core.exists():
        raise RuntimeError(
            "working core exists but core/src is missing; refusing to overwrite partial working core"
        )
    shutil.copytree(authority_core, core)
    if not source_root.is_dir():
        raise RuntimeError("pinned authority core did not contain src after copy")
    return "seeded"


def _java_tree(source_root: Path) -> dict[str, bytes]:
    root = Path(source_root)
    if not root.is_dir():
        raise FileNotFoundError(root)
    return {
        path.relative_to(root).as_posix(): path.read_bytes()
        for path in sorted(root.rglob("*.java"))
    }


def sync_working_core(
    previous_authority_core: Path,
    new_authority_core: Path,
    working_core: Path,
) -> dict[str, object]:
    """Three-way sync completed authority changes into ``core/src`` safely.

    Only files whose pinned authority content changed are candidates. A candidate
    is updated when the working file still equals the previous authority, left
    alone when it already equals the new authority, and treated as a conflict for
    any other local content. Conflict detection completes before publication, so
    no partial completed repair sync can enter the working source tree.
    """
    previous = Path(previous_authority_core).resolve()
    new = Path(new_authority_core).resolve()
    working = Path(working_core).resolve()
    old_src = previous / "src"
    new_src = new / "src"
    work_src = working / "src"
    if not work_src.is_dir():
        raise FileNotFoundError(work_src)

    old_files = _java_tree(old_src)
    new_files = _java_tree(new_src)
    changed = sorted(
        rel for rel in (set(old_files) | set(new_files))
        if old_files.get(rel) != new_files.get(rel)
    )

    conflicts: list[str] = []
    updates: list[str] = []
    already_new: list[str] = []
    for rel in changed:
        old_data = old_files.get(rel)
        new_data = new_files.get(rel)
        if new_data is None:
            raise RuntimeError(f"completed repair sync deletion is unsupported: {rel}")
        target = work_src / rel
        current = target.read_bytes() if target.is_file() else None
        if current == new_data:
            already_new.append(rel)
            continue
        if current == old_data:
            updates.append(rel)
            continue
        if old_data is None and current is None:
            updates.append(rel)
            continue
        conflicts.append(rel)

    if conflicts:
        raise RuntimeError(
            "completed repair sync conflict: " + ", ".join(conflicts)
        )

    if not updates:
        return {
            "updated_files": [],
            "already_new_files": already_new,
            "changed_authority_files": changed,
        }

    working.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="fast-dev-sync.", dir=working.parent) as td:
        stage = Path(td) / "core"
        shutil.copytree(working, stage)
        stage_src = stage / "src"
        for rel in updates:
            target = stage_src / rel
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(new_files[rel])

        for name in (
            "package-map.csv",
            "source-index.json",
            "runtime-class-map.json",
            "MIGRATION_AUTHORITY.json",
            "PINNED_AUTHORITY.json",
        ):
            source = new / name
            if source.is_file():
                shutil.copy2(source, stage / name)

        _publish_directory(stage, working)

    return {
        "updated_files": updates,
        "already_new_files": already_new,
        "changed_authority_files": changed,
    }


def _new_compiler(root: Path, dev_base: Path):
    return _INCREMENTAL.IncrementalCompiler(
        source_root=root / "core" / "src",
        class_dir=root / ".build850" / "classes",
        state_path=root / ".build850" / "state.json",
        dependency_index_path=root / ".build850" / "dependency-index.json",
        classpath=[dev_base, root / "lib" / "*"],
    )


def ensure_fast_dev(
    root: Path,
    *,
    fetch_latest: bool = True,
    sync_working_core: bool = False,
) -> dict[str, object]:
    """Ensure Fast Dev baseline, completed overlay and pinned source state exist.

    Authority is resolved exactly once from the completed repair branch and then
    passed through every bootstrap stage as one pinned SHA. Normal bootstrap never
    overwrites an existing ``core/src``. Explicit sync performs a three-way update
    against the previous pinned authority and fails closed on local conflicts.
    """
    root = Path(root).resolve()
    original = root / "l1jserver2.jar"
    if not original.is_file():
        raise FileNotFoundError(original)

    build = root / ".build850"
    cache = build / "cache"
    cache.mkdir(parents=True, exist_ok=True)
    authority_core = cache / "completed-authority-core"
    preliminary = cache / "850-dev-base.original-semantic.jar"
    completed_overlay = cache / "completed-repair-overlay"
    dev_base = cache / "850-dev-base.jar"
    cache_key_path = cache / "850-dev-base.key.json"
    class_dir = build / "classes"
    state_path = build / "state.json"
    dependency_index_path = build / "dependency-index.json"
    working_core = root / "core"

    if sync_working_core and (working_core / "src").is_dir() and not (authority_core / "src").is_dir():
        raise RuntimeError(
            "cannot safely sync completed repairs: previous pinned authority cache is missing"
        )

    with tempfile.TemporaryDirectory(prefix="fast-dev-ensure.", dir=cache) as td:
        stage = Path(td)
        previous_authority = stage / "previous-authority-core"
        had_previous_authority = False
        if sync_working_core and (authority_core / "src").is_dir():
            shutil.copytree(authority_core, previous_authority)
            had_previous_authority = True

        authority_commit = _AUTHORITY.resolve_completed_authority_commit(
            root,
            fetch_latest=fetch_latest,
        )
        _AUTHORITY.materialize_authority_core(
            root,
            authority_core,
            commit=authority_commit,
            fetch_if_missing=fetch_latest,
        )
        completed_sources = _AUTHORITY.completed_repair_source_paths(
            root,
            commit=authority_commit,
        )
        runtime_map = _load_runtime_map(authority_core)

        package_map = authority_core / "package-map.csv"
        if not package_map.is_file():
            raise FileNotFoundError(package_map)
        cache_key = _DEV_BASE.make_cache_key(
            original,
            package_map,
            java_major=8,
            schema_version=_DEV_BASE.SCHEMA_VERSION,
            completed_authority_commit=authority_commit,
        )
        cache_hit = dev_base.is_file() and _DEV_BASE.cache_matches(cache_key_path, cache_key)
        rebuilt = False
        candidate_dev_base = stage / "850-dev-base.jar"

        try:
            if not cache_hit:
                _DEV_BASE.build_dev_base(
                    original,
                    preliminary,
                    runtime_map,
                )
                _OVERLAY.compile_completed_overlay(
                    authority_core=authority_core,
                    normalized_source_paths=completed_sources,
                    dev_base_jar=preliminary,
                    output_dir=completed_overlay,
                    lib_dir=root / "lib",
                )
                _DEV_BASE.build_dev_base(
                    original,
                    candidate_dev_base,
                    runtime_map,
                    completed_overlay=completed_overlay,
                )

            sync_result = None
            if sync_working_core and (working_core / "src").is_dir():
                if not had_previous_authority:
                    raise RuntimeError(
                        "cannot safely sync completed repairs without previous pinned authority"
                    )
                sync_result = globals()["sync_working_core"](
                    previous_authority,
                    authority_core,
                    working_core,
                )
                core_action = "synced" if sync_result["updated_files"] else "preserved"
            else:
                core_action = _ensure_working_core(root, authority_core)

            if not cache_hit:
                os.replace(candidate_dev_base, dev_base)
                _atomic_write_json(cache_key_path, cache_key)
                rebuilt = True
        except Exception:
            if sync_working_core and had_previous_authority:
                restore = stage / "restore-authority-core"
                shutil.copytree(previous_authority, restore)
                _publish_directory(restore, authority_core)
            raise

    seed_required = rebuilt or not state_path.is_file() or not dependency_index_path.is_file()
    if seed_required:
        if class_dir.exists():
            shutil.rmtree(class_dir)
        compiler = _new_compiler(root, dev_base)
        compiler.seed_from_dev_base(
            dev_base,
            baseline_source_root=authority_core / "src",
        )

    return {
        "authority_commit": authority_commit,
        "completed_source_count": len(completed_sources),
        "runtime_class_count": len(runtime_map),
        "dev_base": str(dev_base),
        "cache_hit": cache_hit,
        "rebuilt": rebuilt,
        "core_action": core_action,
        "state_seeded": seed_required,
        "synced_file_count": 0 if sync_result is None else len(sync_result["updated_files"]),
    }


def main() -> int:
    result = ensure_fast_dev(ROOT)
    print(
        "FAST_DEV_BOOTSTRAP=PASS "
        f"AUTHORITY={result['authority_commit']} "
        f"COMPLETED_SOURCES={result['completed_source_count']} "
        f"RUNTIME_CLASSES={result['runtime_class_count']} "
        f"CACHE_HIT={str(result['cache_hit']).upper()} "
        f"CORE={result['core_action']}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
