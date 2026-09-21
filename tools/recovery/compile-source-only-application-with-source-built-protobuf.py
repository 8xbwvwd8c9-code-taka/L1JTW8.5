#!/usr/bin/env python3
import json, os, shutil, subprocess, zipfile
from collections import Counter
from pathlib import Path

ROOT=Path(".")
SRC=ROOT/"_normalized-stage-src"
REC=ROOT/"recovery"
RUNTIME=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
RUNTIME_STATE=REC/"protobuf_2_5_0_source_built_donor_abi.json"
COMPILE_VIEW=REC/"protobuf-2.5.0-source-built-compile-view.jar"
COMPILE_VIEW_STATE=REC/"protobuf_2_5_0_source_built_compile_view.json"
BUILD=REC/"source-only-application-build"
LOG=REC/"source_only_application_javac.log"
STATE=REC/"source_only_application_compile.json"
SOURCES=REC/"source_only_application_sources.txt"

for p in (SRC,RUNTIME,RUNTIME_STATE,COMPILE_VIEW,COMPILE_VIEW_STATE):
    if not p.exists():
        raise SystemExit(f"missing required input: {p}")

runtime_state=json.loads(RUNTIME_STATE.read_text(encoding="utf-8"))
if runtime_state.get("written_classes") != 246:
    raise SystemExit(f"source-built protobuf class count must be 246: {runtime_state.get('written_classes')}")
if runtime_state.get("donor_binary_used_as_runtime_output") is not False:
    raise SystemExit("source-built protobuf state says donor binary was used as runtime output")

with zipfile.ZipFile(RUNTIME) as z:
    runtime_classes=sorted(n for n in z.namelist() if n.endswith(".class"))
if len(runtime_classes)!=246:
    raise SystemExit(f"source-built protobuf jar must contain 246 classes, got {len(runtime_classes)}")

if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True)

sources=sorted(SRC.rglob("*.java"))
SOURCES.write_text("\n".join(p.as_posix() for p in sources)+"\n",encoding="utf-8")
if len(sources)!=788:
    raise SystemExit(f"normalized application source count must be 788, got {len(sources)}")

# Final application compile excludes both the checked-in/prebuilt official protobuf jar
# and all donor/recovery protobuf compile-reference jars. The only protobuf classpath
# entry is the 246-class jar deterministically produced from official 2.5.0 source.
lib_jars=[]
excluded=[]
for p in sorted((ROOT/"lib").glob("*.jar")):
    n=p.name.lower()
    if n.startswith("protobuf-java-"):
        excluded.append(p.as_posix())
        continue
    lib_jars.append(p)

classpath_entries=lib_jars+[COMPILE_VIEW]
classpath=os.pathsep.join(str(p) for p in classpath_entries)
cmd=[
    "javac","-encoding","UTF-8","-source","8","-target","8","-proc:none",
    "-Xmaxerrs","20000","-Xmaxwarns","5000",
    "-cp",classpath,"-d",str(BUILD),"@"+str(SOURCES)
]
with LOG.open("w",encoding="utf-8",errors="replace") as log:
    proc=subprocess.run(cmd,stdout=log,stderr=subprocess.STDOUT,text=True)

log_text=LOG.read_text(encoding="utf-8",errors="replace")
lines=log_text.splitlines()
errors=[line for line in lines if ": error:" in line]
error_files=Counter()
error_messages=Counter()
error_contexts=[]
for idx,line in enumerate(lines):
    if ": error:" not in line:
        continue
    try:
        left,msg=line.split(": error:",1)
        filepart=left.rsplit(":",1)[0]
        if filepart.startswith(SRC.as_posix()+"/"):
            filepart=filepart[len(SRC.as_posix())+1:]
        error_files[filepart]+=1
        error_messages[msg.strip()]+=1
    except Exception:
        pass
    if len(error_contexts)<30:
        lo=max(0,idx-1); hi=min(len(lines),idx+5)
        error_contexts.append("\n".join(lines[lo:hi]))
generated=sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))

state={
    "gate":"SOURCE_ONLY_APPLICATION_WITH_SOURCE_BUILT_PROTOBUF",
    "application_java_sources":len(sources),
    "source_built_protobuf_classes":len(runtime_classes),
    "compile_exit_code":proc.returncode,
    "javac_error_headers":len(errors),
    "generated_application_classes":len(generated),
    "error_file_count":len(error_files),
    "top_error_files":error_files.most_common(30),
    "top_error_messages":error_messages.most_common(30),
    "error_contexts":error_contexts,
    "expected_application_classes":1109,
    "full_donor_game_jar_on_classpath":False,
    "donor_protobuf_binary_on_classpath":False,
    "official_prebuilt_protobuf_binary_on_classpath":False,
    "source_built_protobuf_abi_jar_on_classpath":False,
    "source_built_protobuf_compile_view_on_classpath":True,
    "source_built_protobuf_exact_runtime_jar_preserved":True,
    "excluded_prebuilt_protobuf_jars":excluded,
    "classpath":[p.as_posix() for p in classpath_entries],
    "pass":proc.returncode==0 and len(errors)==0 and len(generated)==1109,
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if not state["pass"]:
    print("TOP_ERROR_FILES="+json.dumps(error_files.most_common(30)))
    print("TOP_ERROR_MESSAGES="+json.dumps(error_messages.most_common(30)))
    print("ERROR_CONTEXTS="+json.dumps(error_contexts,ensure_ascii=False))
    raise SystemExit(1)
