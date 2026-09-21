#!/usr/bin/env python3
import json, shutil, subprocess, zipfile
from collections import Counter
from pathlib import Path

SRC=Path("recovery/protobuf-runtime-src-cfr-flat")
BUILD=Path("recovery/protobuf-runtime-source-cfr-flat-build")
LOG=Path("recovery/protobuf_runtime_source_cfr_flat_javac.log")
STATE=Path("recovery/protobuf_runtime_source_cfr_flat_compile.json")
LIST=Path("recovery/protobuf_runtime_source_cfr_flat_files.txt")
if BUILD.exists(): shutil.rmtree(BUILD)
BUILD.mkdir(parents=True)
sources=sorted(SRC.rglob("*.java"))
LIST.write_text("\n".join(p.as_posix() for p in sources)+"\n",encoding="utf-8")
cmd=["javac","-encoding","UTF-8","-source","8","-target","8","-proc:none","-Xmaxerrs","20000","-Xmaxwarns","5000","-d",str(BUILD),"@"+str(LIST)]
with LOG.open("w",encoding="utf-8",errors="replace") as fh:
    proc=subprocess.run(cmd,stdout=fh,stderr=subprocess.STDOUT,text=True)
lines=LOG.read_text(encoding="utf-8",errors="replace").splitlines()
headers=[x for x in lines if ": error: " in x]
files=Counter(); msgs=Counter()
for line in headers:
    try:
        left,msg=line.split(": error: ",1); path=left.rsplit(":",1)[0]
        rel=path.replace(SRC.as_posix()+"/",""); files[rel]+=1; msgs[msg]+=1
    except Exception: pass
classes=sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))
with zipfile.ZipFile("recovery/compile-ref-protobuf-l1rpb.jar") as zf:
    expected=sorted(n for n in zf.namelist() if n.endswith(".class") and not n.startswith("META-INF/"))
missing=sorted(set(expected)-set(classes)); extra=sorted(set(classes)-set(expected))
state={"gate":"PROTOBUF_RUNTIME_CFR_FLAT_SOURCE_ONLY_EXPERIMENT","source_root":str(SRC),
"binary_runtime_on_classpath":False,"java_sources_submitted":len(sources),"compile_exit_code":proc.returncode,
"generated_class_files_total":len(classes),"expected_runtime_class_files":len(expected),
"missing_runtime_classes":len(missing),"extra_runtime_classes":len(extra),
"class_set_pass":proc.returncode==0 and not missing and not extra,"javac_error_headers":len(headers),
"error_file_count":len(files),"top_error_files":files.most_common(100),"top_error_messages":msgs.most_common(100),
"missing_sample":missing[:50],"extra_sample":extra[:50]}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
