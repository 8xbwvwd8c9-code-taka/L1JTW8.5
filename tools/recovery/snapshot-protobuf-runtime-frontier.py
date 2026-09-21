#!/usr/bin/env python3
import json, shutil
from pathlib import Path

SRC=Path("recovery/protobuf-runtime-src-vf")
OUT=Path("recovery/wip/protobuf-runtime-frontier")
OUT.mkdir(parents=True, exist_ok=True)

targets=["p.java","k.java","x.java","j.java","a.java","c.java","ap.java"]
copied=[]
for name in targets:
    src=SRC/"l1rpb"/name
    if src.exists():
        dst=OUT/name
        shutil.copy2(src,dst)
        copied.append(name)

state={}
p=Path("recovery/protobuf_runtime_source_compile.json")
if p.exists():
    state=json.loads(p.read_text(encoding="utf-8"))
(OUT/"STATE.json").write_text(json.dumps({
    "copied":copied,
    "compile":state,
},indent=2)+"\n",encoding="utf-8")
print(json.dumps({"copied":copied,"out":str(OUT)},indent=2))
