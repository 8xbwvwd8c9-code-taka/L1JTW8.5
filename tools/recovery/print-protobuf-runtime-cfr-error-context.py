#!/usr/bin/env python3
import re
from pathlib import Path

SRC=Path("recovery/protobuf-runtime-src-cfr")
LOG=Path("recovery/protobuf_runtime_source_cfr_javac.log")
pat=re.compile(r"^(.*\.java):(\d+): error: (.*)$")
seen=set()
for line in LOG.read_text(encoding="utf-8",errors="replace").splitlines():
    m=pat.match(line)
    if not m: continue
    path=Path(m.group(1)); lineno=int(m.group(2)); msg=m.group(3)
    key=(str(path),lineno,msg)
    if key in seen: continue
    seen.add(key)
    try:
        lines=path.read_text(encoding="utf-8",errors="replace").splitlines()
    except Exception:
        continue
    print(f"=== {path}:{lineno}: {msg} ===")
    lo=max(1,lineno-4); hi=min(len(lines),lineno+4)
    for n in range(lo,hi+1):
        mark=">>" if n==lineno else "  "
        print(f"{mark}{n:5}: {lines[n-1]}")
