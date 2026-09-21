#!/usr/bin/env python3
import json, os, shutil, subprocess, zipfile
from pathlib import Path

ROOT=Path(".")
SRC=ROOT/"_normalized-stage-src"
REC=ROOT/"recovery"
RUNTIME=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
RUNTIME_STATE=REC/"protobuf_2_5_0_source_built_donor_abi.json"
BUILD=REC/"source-only-application-build"
LOG=REC/"source_only_application_javac.log"
STATE=REC/"source_only_application_compile.json"
SOURCES=REC/"source_only_application_sources.txt"

for p in (SRC,RUNTIME,RUNTIME_STATE):
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

classpath_entries=lib_jars+[RUNTIME]
classpath=os.pathsep.join(str(p) for p in classpath_entries)
cmd=[
    "javac","-encoding","UTF-8","-source","8","-target","8","-proc:none",
    "-Xmaxerrs","20000","-Xmaxwarns","5000",
    "-cp",classpath,"-d",str(BUILD),"@"+str(SOURCES)
]
with LOG.open("w",encoding="utf-8",errors="replace") as log:
    proc=subprocess.run(cmd,stdout=log,stderr=subprocess.STDOUT,text=True)

log_text=LOG.read_text(encoding="utf-8",errors="replace")
errors=[line for line in log_text.splitlines() if ": error:" in line]
generated=sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))

state={
    "gate":"SOURCE_ONLY_APPLICATION_WITH_SOURCE_BUILT_PROTOBUF",
    "application_java_sources":len(sources),
    "source_built_protobuf_classes":len(runtime_classes),
    "compile_exit_code":proc.returncode,
    "javac_error_headers":len(errors),
    "generated_application_classes":len(generated),
    "expected_application_classes":1109,
    "full_donor_game_jar_on_classpath":False,
    "donor_protobuf_binary_on_classpath":False,
    "official_prebuilt_protobuf_binary_on_classpath":False,
    "source_built_protobuf_abi_jar_on_classpath":True,
    "excluded_prebuilt_protobuf_jars":excluded,
    "classpath":[p.as_posix() for p in classpath_entries],
    "pass":proc.returncode==0 and len(errors)==0 and len(generated)==1109,
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if not state["pass"]:
    print(log_text[-12000:])
    raise SystemExit(1)
