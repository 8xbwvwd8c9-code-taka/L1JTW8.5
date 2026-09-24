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


def _new_compiler(root: Path, dev_base: Path):
    return _INCREMENTAL.IncrementalCompiler(
        source_root=root / "core" / "src",
        class_dir=root / ".build850" / "classes",
        state_path=root / ".build850" / "state.json",
        dependency_index_path=root / ".build850" / "dependency-index.json",
        classpath=[dev_base, root / "lib" / "*"],
    )


def ensure_fast_dev(root: Path, *, fetch_latest: bool = True) -> dict[str, object]:
    """Ensure Fast Dev baseline, completed overlay and pinned source state exist.

    Authority is resolved exactly once from the completed repair branch and then
    passed through every bootstrap stage as one pinned SHA. Existing ``core/src``
    is never overwritten. If the Dev Base authority changes, stale runtime overlay
    classes are discarded only after the replacement Dev Base is built successfully.
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
            dev_base,
            runtime_map,
            completed_overlay=completed_overlay,
        )
        _atomic_write_json(cache_key_path, cache_key)
        rebuilt = True

    core_action = _ensure_working_core(root, authority_core)
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
