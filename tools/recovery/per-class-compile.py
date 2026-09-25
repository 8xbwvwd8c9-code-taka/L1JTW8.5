#!/usr/bin/env python3
import csv
import json
import os
import re
import shutil
import subprocess
import tempfile
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path

ROOT=Path(".")
BASE=ROOT/"recovered-src-obf"
VF1=ROOT/"recovery/vineflower-hard-tail"
VF2=ROOT/"recovery/vineflower-stage2"
REC=ROOT/"recovery"
REF=REC/"l1jserver2-sanitized-ref.jar"
CSV_OUT=REC/"per_class_compile.csv"
STATE=REC/"per_class_compile.json"
REPORT=REC/"PER_CLASS_COMPILE.md"
FAIL_LOG=REC/"per_class_failures.log"

HARD={
    "aj/aw.java","aj/bx.java","al/ab.java","ao/aw.java","ao/v.java","be/dc.java","bf/b.java"
}
PROTO={f"an/{x}.java" for x in "abcdefghi"}

if not REF.exists():
    raise SystemExit(f"missing sanitized reference: {REF}")

libs=sorted((ROOT/"lib").glob("*.jar"))
classpath=os.pathsep.join(str(p) for p in ([REF]+libs))
empty_sourcepath=Path(tempfile.mkdtemp(prefix="l1jtw85-empty-sourcepath-"))

targets=[]
for p in sorted(BASE.rglob("*.java")):
    rel=p.relative_to(BASE).as_posix()
    actual=p
    engine="CFR"
    if rel in HARD and (VF1/rel).exists():
        actual=VF1/rel; engine="VINEFLOWER_HARDTAIL"
    elif rel in PROTO and (VF2/rel).exists():
        actual=VF2/rel; engine="VINEFLOWER_PROTO"
    targets.append((rel,actual,engine))

def compile_one(item):
    rel,src,engine=item
    with tempfile.TemporaryDirectory(prefix="l1jtw85-javac-") as td:
        cmd=[
            "javac",
            "-encoding","UTF-8",
            "-source","8",
            "-target","8",
            "-proc:none",
            "-implicit:none",
            "-sourcepath",str(empty_sourcepath),
            "-cp",classpath,
            "-d",td,
            str(src),
        ]
        try:
            p=subprocess.run(cmd,capture_output=True,text=True,timeout=60)
            out=(p.stdout or "")+(p.stderr or "")
            code=p.returncode
        except subprocess.TimeoutExpired as e:
            out=(e.stdout or "")+(e.stderr or "")+"\nTIMEOUT\n"
            code=124

    errors=[]
    for line in out.splitlines():
        if ": error:" in line:
            errors.append(line.split(": error:",1)[1].strip())

    return {
        "File":rel,
        "Engine":engine,
        "Exit":code,
        "Status":"PASS" if code==0 else "FAIL",
        "ErrorHeaders":len(errors),
        "PrimaryError":errors[0] if errors else (out.strip().splitlines()[-1] if out.strip() else ""),
        "Log":out,
    }

results=[]
with ThreadPoolExecutor(max_workers=4) as ex:
    futures=[ex.submit(compile_one,t) for t in targets]
    for f in as_completed(futures):
        results.append(f.result())

shutil.rmtree(empty_sourcepath,ignore_errors=True)
results.sort(key=lambda r:r["File"])

with CSV_OUT.open("w",encoding="utf-8",newline="") as f:
    fields=["File","Engine","Status","Exit","ErrorHeaders","PrimaryError"]
    w=csv.DictWriter(f,fieldnames=fields)
    w.writeheader()
    for r in results:
        w.writerow({k:r[k] for k in fields})

fails=[r for r in results if r["Status"]=="FAIL"]
passes=[r for r in results if r["Status"]=="PASS"]

with FAIL_LOG.open("w",encoding="utf-8") as f:
    for r in fails:
        f.write(f"===== {r['File']} [{r['Engine']}] EXIT={r['Exit']} =====\n")
        lines=r["Log"].splitlines()
        f.write("\n".join(lines[:160]))
        f.write("\n\n")

families={}
for r in fails:
    key=r["PrimaryError"] or "UNKNOWN"
    families[key]=families.get(key,0)+1

state={
    "gate":"PER_CLASS_SANITIZED_REFERENCE",
    "sanitized_reference":REF.as_posix(),
    "donor_full_game_reference_used":True,
    "final_full_tree_gate":False,
    "targets":len(results),
    "pass":len(passes),
    "fail":len(fails),
    "pass_rate":round(len(passes)/len(results),6) if results else 0,
    "engines":{
        "CFR":sum(1 for r in results if r["Engine"]=="CFR"),
        "VINEFLOWER_HARDTAIL":sum(1 for r in results if r["Engine"]=="VINEFLOWER_HARDTAIL"),
        "VINEFLOWER_PROTO":sum(1 for r in results if r["Engine"]=="VINEFLOWER_PROTO"),
    },
    "top_failure_families":sorted(families.items(),key=lambda kv:(-kv[1],kv[0]))[:50],
}
STATE.write_text(json.dumps(state,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")

md=[
    "# L1JTW8.5 Per-Class Compile Gate",
    "",
    "Gate: **PER_CLASS_SANITIZED_REFERENCE**",
    "",
    f"- Targets: **{len(results)}**",
    f"- PASS: **{len(passes)}**",
    f"- FAIL: **{len(fails)}**",
    f"- PASS rate: **{state['pass_rate']:.2%}**",
    "",
    "## Boundary",
    "",
    "- Each recovered top-level source is compiled independently.",
    "- Dependencies resolve from the sanitized donor reference + repository third-party libraries.",
    "- Sanitized donor clears ACC_SYNTHETIC only.",
    "- This gate can identify locally compilable source but **cannot prove full source recovery**.",
    "- Final completion still requires donor-free source-only full-tree compile and ABI validation.",
    "",
    "## Top failure families",
    "",
    "| Primary error | Files |",
    "|---|---:|",
]
for k,v in state["top_failure_families"][:30]:
    md.append(f"| {k.replace('|','\\|')} | {v} |")
REPORT.write_text("\n".join(md)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2,ensure_ascii=False))
