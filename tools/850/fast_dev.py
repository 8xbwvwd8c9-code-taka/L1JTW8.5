#!/usr/bin/env python3
from __future__ import annotations

import argparse
import importlib.util
import os
import shutil
import subprocess
import sys
import time
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
RUNTIME_ENTRYPOINT = "l1j/server/Server.class"
PBMESSAGE_BASELINE_ONLY = tuple(
    f"l1j.server.proto.PBMessageALL{suffix}"
    for suffix in ("", "2", "3", "4", "5", "6", "7", "8", "9")
)


def _load_module(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def parse_cli(argv: list[str] | None = None):
    parser = argparse.ArgumentParser(description="L1JTW8.5 Fast Development Build")
    modes = parser.add_mutually_exclusive_group()
    modes.add_argument("-Full", dest="full", action="store_true", help="force full readable-source compile")
    modes.add_argument("-Clean", dest="clean", action="store_true", help="clear .build850 state/cache")
    modes.add_argument("-Sync", dest="sync", action="store_true", help="sync completed BUG repairs")
    modes.add_argument("-Pack", dest="pack", action="store_true", help="package readable dev runtime")
    parser.add_argument("-Run", dest="run", action="store_true", help="compile then start dev server")
    parser.add_argument("-Watch", dest="watch", action="store_true", help="compile on core/src changes")
    args = parser.parse_args(argv)
    if args.full:
        args.mode = "full"
    elif args.clean:
        args.mode = "clean"
    elif args.sync:
        args.mode = "sync"
    elif args.pack:
        args.mode = "pack"
    else:
        args.mode = "incremental"
    return args


def runtime_classpath(root: Path) -> list[Path]:
    root = Path(root)
    return [
        root / ".build850" / "classes",
        root / ".build850" / "cache" / "850-dev-base.jar",
        root / "lib" / "*",
    ]


def watch_root(root: Path) -> Path:
    return Path(root) / "core" / "src"


def ensure_runtime_jdbc_config(root: Path) -> bool:
    """Make the legacy local MySQL 5.7 JDBC URL explicit about disabling SSL.

    Old Connector/J versions may otherwise attempt SSL negotiation by default and
    fail against modern Java security policy before normal authentication starts.
    Only the JDBC URL line is changed; login/password and all other properties are
    preserved byte-for-byte apart from normal text re-encoding when a change is
    required.
    """
    path = Path(root) / "config" / "server.properties"
    if not path.is_file():
        raise RuntimeError(f"Fast Dev server config not found: {path}")

    raw = path.read_bytes()
    had_bom = raw.startswith(b"\xef\xbb\xbf")
    text = raw.decode("utf-8-sig")
    newline = "\r\n" if "\r\n" in text else "\n"
    lines = text.splitlines()
    trailing_newline = text.endswith(("\n", "\r"))

    url_indexes = [
        index
        for index, line in enumerate(lines)
        if line.strip().startswith("URL=jdbc:mysql://")
    ]
    if len(url_indexes) != 1:
        raise RuntimeError(
            f"Fast Dev expected exactly one MySQL JDBC URL in {path}, found {len(url_indexes)}"
        )

    index = url_indexes[0]
    line = lines[index]
    if "useSSL=false" in line:
        return False
    if "useSSL=" in line:
        raise RuntimeError(
            "Fast Dev MySQL JDBC URL has an explicit SSL mode other than useSSL=false"
        )

    separator = "&" if "?" in line else "?"
    lines[index] = line + separator + "useSSL=false"
    updated = newline.join(lines)
    if trailing_newline:
        updated += newline
    path.write_text(updated, encoding="utf-8-sig" if had_bom else "utf-8")
    return True


def clean_build_state(root: Path) -> None:
    build = Path(root) / ".build850"
    if build.exists():
        shutil.rmtree(build)


def _dev_base_runtime_ready(path: Path) -> bool:
    path = Path(path)
    if not path.is_file():
        return False
    try:
        with zipfile.ZipFile(path, "r") as archive:
            return RUNTIME_ENTRYPOINT in archive.namelist()
    except (OSError, zipfile.BadZipFile):
        return False


def _baseline_ready(root: Path) -> bool:
    build = Path(root) / ".build850"
    dev_base = build / "cache" / "850-dev-base.jar"
    if not all(
        path.is_file()
        for path in (
            dev_base,
            build / "state.json",
            build / "dependency-index.json",
        )
    ):
        return False
    return _dev_base_runtime_ready(dev_base)


def _ensure_baseline(root: Path) -> None:
    root = Path(root)
    if _baseline_ready(root):
        return

    build = root / ".build850"
    dev_base = build / "cache" / "850-dev-base.jar"
    if dev_base.is_file() and not _dev_base_runtime_ready(dev_base):
        dev_base.unlink()
        cache_key = build / "cache" / "850-dev-base.key.json"
        try:
            cache_key.unlink()
        except FileNotFoundError:
            pass

    bootstrap = _load_module(
        root / "tools" / "850" / "bootstrap" / "ensure_dev.py",
        "fast_dev_automatic_bootstrap",
    )
    bootstrap.ensure_fast_dev(root)
    if not _baseline_ready(root):
        raise RuntimeError("Fast Dev automatic bootstrap completed without a usable baseline/state")


def sync_completed(root: Path) -> dict[str, object]:
    """Refresh formally completed repairs and safely merge them into working core."""
    bootstrap = _load_module(
        Path(root) / "tools" / "850" / "bootstrap" / "ensure_dev.py",
        "fast_dev_completed_sync",
    )
    return bootstrap.ensure_fast_dev(
        Path(root),
        fetch_latest=True,
        sync_working_core=True,
    )


def _compiler(root: Path):
    _ensure_baseline(root)
    module = _load_module(root / "tools" / "850" / "compiler" / "incremental.py", "fast_dev_incremental_runtime")
    dev_base = root / ".build850" / "cache" / "850-dev-base.jar"
    return module.IncrementalCompiler(
        source_root=root / "core" / "src",
        class_dir=root / ".build850" / "classes",
        state_path=root / ".build850" / "state.json",
        dependency_index_path=root / ".build850" / "dependency-index.json",
        classpath=[dev_base, root / "lib" / "*"],
        baseline_jar=dev_base,
        baseline_only_identities=PBMESSAGE_BASELINE_ONLY,
    )


def _snapshot_sources(root: Path) -> dict[str, tuple[int, int]]:
    base = watch_root(root)
    if not base.exists():
        return {}
    return {
        path.relative_to(base).as_posix(): (path.stat().st_mtime_ns, path.stat().st_size)
        for path in base.rglob("*.java")
    }


def run_server(root: Path) -> int:
    ensure_runtime_jdbc_config(root)
    cp = os.pathsep.join(str(path) for path in runtime_classpath(root))
    # The production launcher uses -noverify because the original 8.5 runtime
    # contains legacy classfiles that modern Java 8 verification rejects before
    # application startup. Fast Dev preserves that runtime policy while keeping
    # the production JAR immutable.
    command = ["java", "-noverify", "-cp", cp, "l1j.server.Server"]
    return subprocess.call(command, cwd=root)


def watch_loop(root: Path, compiler, interval: float = 0.4) -> None:
    before = _snapshot_sources(root)
    print(f"WATCH={watch_root(root)}")
    try:
        while True:
            time.sleep(interval)
            after = _snapshot_sources(root)
            if after == before:
                continue
            before = after
            try:
                result = compiler.compile_changed()
                print(f"BUILD=PASS MODE={result['mode']} CLASSES={len(result['compiled_identities'])}")
            except Exception as exc:
                print(f"BUILD=FAIL {exc}", file=sys.stderr)
    except KeyboardInterrupt:
        print("WATCH=STOPPED")


def main(argv: list[str] | None = None) -> int:
    args = parse_cli(argv)
    root = ROOT

    if args.mode == "clean":
        clean_build_state(root)
        _ensure_baseline(root)
        print("FAST_DEV_CLEAN=PASS")
        return 0

    if args.mode == "sync":
        result = sync_completed(root)
        print(
            "FAST_DEV_SYNC=PASS "
            f"AUTHORITY={result['authority_commit']} "
            f"COMPLETED_SOURCES={result['completed_source_count']} "
            f"REBUILT={str(result['rebuilt']).upper()} "
            f"CORE={result['core_action']}"
        )
        return 0

    if args.mode == "pack":
        packer = root / "tools" / "850" / "release" / "pack_dev.py"
        if not packer.is_file():
            raise RuntimeError("Fast Dev packer is not installed yet")
        return subprocess.call([sys.executable, str(packer)], cwd=root)

    compiler = _compiler(root)
    result = compiler.full_compile() if args.mode == "full" else compiler.compile_changed()
    print(f"BUILD=PASS MODE={result['mode']} CLASSES={len(result['compiled_identities'])}")

    if args.watch:
        watch_loop(root, compiler)
        return 0
    if args.run:
        return run_server(root)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
