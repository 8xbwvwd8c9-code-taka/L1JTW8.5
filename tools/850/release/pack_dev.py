#!/usr/bin/env python3
from __future__ import annotations

import argparse
import os
import tempfile
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
_FIXED_TIMESTAMP = (1980, 1, 1, 0, 0, 0)


def _normalized_info(name: str) -> zipfile.ZipInfo:
    info = zipfile.ZipInfo(name, date_time=_FIXED_TIMESTAMP)
    info.compress_type = zipfile.ZIP_DEFLATED
    info.create_system = 3
    info.external_attr = 0o100644 << 16
    return info


def _overlay_entries(overlay_dir: Path) -> dict[str, bytes]:
    if not overlay_dir.exists():
        return {}
    if not overlay_dir.is_dir():
        raise NotADirectoryError(overlay_dir)
    entries: dict[str, bytes] = {}
    for path in sorted(p for p in overlay_dir.rglob("*") if p.is_file()):
        rel = path.relative_to(overlay_dir).as_posix()
        if rel in entries:
            raise ValueError(f"duplicate overlay entry: {rel}")
        entries[rel] = path.read_bytes()
    return entries


def pack_dev(dev_base_jar: Path, overlay_dir: Path, output_jar: Path) -> dict[str, int]:
    base = Path(dev_base_jar)
    overlay = Path(overlay_dir)
    output = Path(output_jar)
    if not base.is_file():
        raise FileNotFoundError(base)
    if base.resolve() == output.resolve():
        raise ValueError("output JAR must not overwrite Fast Dev base JAR")

    base_bytes = base.read_bytes()
    entries: dict[str, bytes] = {}
    with zipfile.ZipFile(base, "r") as zin:
        for info in zin.infolist():
            if info.is_dir():
                continue
            if info.filename in entries:
                raise ValueError(f"duplicate base JAR entry: {info.filename}")
            entries[info.filename] = zin.read(info.filename)

    overlay_entries = _overlay_entries(overlay)
    entries.update(overlay_entries)

    output.parent.mkdir(parents=True, exist_ok=True)
    fd, temp_name = tempfile.mkstemp(prefix=output.name + ".", suffix=".tmp", dir=output.parent)
    os.close(fd)
    temp = Path(temp_name)
    try:
        with zipfile.ZipFile(temp, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9) as zout:
            for name in sorted(entries):
                zout.writestr(_normalized_info(name), entries[name])
        os.replace(temp, output)
    finally:
        if temp.exists():
            temp.unlink()

    if base.read_bytes() != base_bytes:
        raise RuntimeError("Fast Dev base JAR changed while packaging")

    return {
        "base_entries": len(entries) - len([k for k in overlay_entries if k not in entries]),
        "overlay_entries": len(overlay_entries),
        "output_entries": len(entries),
    }


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Package L1JTW8.5 readable Fast Dev JAR")
    parser.add_argument("--root", type=Path, default=ROOT)
    args = parser.parse_args(argv)
    root = args.root.resolve()
    base = root / ".build850" / "cache" / "850-dev-base.jar"
    overlay = root / ".build850" / "classes"
    output = root / "dist" / "l1jserver2-dev.jar"
    result = pack_dev(base, overlay, output)
    print(f"PACK=PASS OUTPUT={output} ENTRIES={result['output_entries']} OVERLAY={result['overlay_entries']}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
