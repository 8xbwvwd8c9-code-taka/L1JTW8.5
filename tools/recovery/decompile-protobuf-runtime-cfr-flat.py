#!/usr/bin/env python3
import json, shutil, subprocess, sys, zipfile
from pathlib import Path

SRC=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OUT=Path("recovery/protobuf-runtime-src-cfr-flat")
STATE=Path("recovery/protobuf_runtime_source_cfr_flat_decompile.json")
if len(sys.argv)!=2:
    raise SystemExit("usage: decompile-protobuf-runtime-cfr-flat.py <cfr.jar>")
CFR=Path(sys.argv[1])
if OUT.exists(): shutil.rmtree(OUT)
OUT.mkdir(parents=True)
cmd=["java","-jar",str(CFR),str(SRC),"--outputdir",str(OUT),"--silent","true",
     "--innerclasses","false","--removeinnerclasssynthetics","false"]
p=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True)
(OUT/"cfr.log").write_text(p.stdout,encoding="utf-8",errors="replace")
java=sorted(OUT.rglob("*.java"))
with zipfile.ZipFile(SRC) as zf:
    classes=[n for n in zf.namelist() if n.endswith(".class") and not n.startswith("META-INF/")]
state={"source_jar":str(SRC),"cfr_exit_code":p.returncode,"runtime_class_files":len(classes),
       "java_files_copied":len(java),"innerclasses":False,"removeinnerclasssynthetics":False,
       "package":"l1rpb","gameplay_logic_changed":False}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if p.returncode!=0 or not java:
    raise SystemExit("CFR flat runtime decompile failed")
