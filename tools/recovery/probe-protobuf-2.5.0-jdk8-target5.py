#!/usr/bin/env python3
import json, os, shutil, subprocess, sys, zipfile
from pathlib import Path

SRCJAR=Path(sys.argv[1])
JAVAC=Path(sys.argv[2]) if len(sys.argv)>2 else Path("javac")
OUT=Path("recovery/protobuf-2.5.0-official-src-jdk8")
BUILD=Path("recovery/protobuf-2.5.0-official-build-jdk8-target5")
STATE=Path("recovery/protobuf_2_5_0_jdk8_target5.json")
for p in (OUT,BUILD):
    if p.exists(): shutil.rmtree(p)
    p.mkdir(parents=True)
with zipfile.ZipFile(SRCJAR) as zf: zf.extractall(OUT)
sources=sorted(OUT.rglob("com/google/protobuf/*.java"))
lst=Path("recovery/protobuf_2_5_0_jdk8_target5_files.txt")
lst.write_text("\n".join(p.as_posix() for p in sources)+"\n",encoding="utf-8")
cmd=[str(JAVAC),"-encoding","UTF-8","-source","1.5","-target","1.5","-proc:none","-d",str(BUILD),"@"+str(lst)]
proc=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True)
Path("recovery/protobuf_2_5_0_jdk8_target5_javac.log").write_text(proc.stdout,encoding="utf-8",errors="replace")
classes=sorted(BUILD.rglob("*.class"))
state={"gate":"PROTOBUF_2_5_0_JDK8_TARGET5","javac":str(JAVAC),"java_sources":len(sources),
       "compile_exit_code":proc.returncode,"generated_classes":len(classes)}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
print(proc.stdout[-4000:])
