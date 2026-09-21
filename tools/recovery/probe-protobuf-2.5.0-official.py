#!/usr/bin/env python3
import json, os, shutil, subprocess, sys, zipfile
from pathlib import Path

SRCJAR=Path(sys.argv[1]) if len(sys.argv)>1 else Path("protobuf-java-2.5.0-sources.jar")
OUT=Path("recovery/protobuf-2.5.0-official-src")
BUILD=Path("recovery/protobuf-2.5.0-official-build")
STATE=Path("recovery/protobuf_2_5_0_fingerprint.json")
for p in (OUT,BUILD):
    if p.exists(): shutil.rmtree(p)
    p.mkdir(parents=True)
with zipfile.ZipFile(SRCJAR) as zf:
    zf.extractall(OUT)
sources=sorted(OUT.rglob("*.java"))
runtime=[p for p in sources if "/com/google/protobuf/" in p.as_posix()]
src_list=Path("recovery/protobuf_2_5_0_source_files.txt")
src_list.write_text("\n".join(p.as_posix() for p in runtime)+"\n",encoding="utf-8")
cmd=["javac","-encoding","UTF-8","-source","8","-target","8","-proc:none","-Xmaxerrs","20000","-d",str(BUILD),"@"+str(src_list)]
proc=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True)
Path("recovery/protobuf_2_5_0_javac.log").write_text(proc.stdout,encoding="utf-8",errors="replace")
classes=sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))
# DescriptorProtos fingerprint terms known from donor embedded descriptor.proto.
terms=["weak_dependency","java_generate_equals_and_hash","experimental_map_key","cc_generic_services","java_generic_services","py_generic_services"]
descriptor=[]
for p in runtime:
    if p.name=="DescriptorProtos.java":
        descriptor=p.read_text(encoding="utf-8",errors="replace")
        break
state={
 "version":"2.5.0",
 "java_sources":len(runtime),
 "compile_exit_code":proc.returncode,
 "generated_classes":len(classes),
 "descriptor_terms":{t:(t in descriptor) for t in terms},
 "java_string_check_utf8_present":("java_string_check_utf8" in descriptor),
 "class_sample":classes[:30],
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
