#!/usr/bin/env python3
import csv, os, shutil, subprocess, sys, tempfile, zipfile, json
from pathlib import Path

REC=Path("recovery")
FAIL=REC/"wip"/"NORMALIZED_CORE_FAIL.csv"
JAR=REC/"l1jserver2-source-normalized.jar"
OUT=REC/"normalized-src-cfr-fail"
REPORT=REC/"wip"/"NORMALIZED_CFR_FALLBACK.csv"
MD=REC/"wip"/"NORMALIZED_CFR_FALLBACK.md"

if len(sys.argv)!=2:
    raise SystemExit("usage: decompile-normalized-fail-cfr.py <cfr.jar>")
CFR=Path(sys.argv[1])

if OUT.exists():
    shutil.rmtree(OUT)
OUT.mkdir(parents=True,exist_ok=True)

with FAIL.open(encoding="utf-8-sig",newline="") as f:
    targets=[r["File"] for r in csv.DictReader(f)]

rows=[]
with zipfile.ZipFile(JAR) as zf:
    names=set(zf.namelist())
    for source_path in targets:
        internal=source_path[:-5]
        class_path=internal+".class"
        if class_path not in names:
            rows.append({"File":source_path,"Status":"MISS_CLASS","Exit":"","JavaFound":0})
            continue
        tmp=Path(tempfile.mkdtemp(prefix="l1jtw85-cfr-fail-"))
        try:
            cf=tmp/Path(class_path).name
            cf.write_bytes(zf.read(class_path))
            outdir=tmp/"out"
            outdir.mkdir()
            cmd=[
                "java","-jar",str(CFR),str(cf),
                "--extraclasspath",str(JAR),
                "--outputdir",str(outdir),
                "--silent","true",
                "--comments","false",
                "--renameillegalidents","true",
            ]
            proc=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True)
            candidates=list(outdir.rglob(Path(source_path).name))
            if not candidates:
                candidates=list(outdir.rglob(cf.stem+".java"))
            if candidates:
                dst=OUT/source_path
                dst.parent.mkdir(parents=True,exist_ok=True)
                shutil.copy2(candidates[0],dst)
                rows.append({"File":source_path,"Status":"DECOMPILED","Exit":proc.returncode,"JavaFound":1})
            else:
                rows.append({"File":source_path,"Status":"NO_JAVA","Exit":proc.returncode,"JavaFound":0})
        finally:
            shutil.rmtree(tmp,ignore_errors=True)

with REPORT.open("w",encoding="utf-8",newline="") as f:
    w=csv.DictWriter(f,fieldnames=["File","Status","Exit","JavaFound"])
    w.writeheader();w.writerows(rows)

ok=sum(1 for r in rows if r["JavaFound"]==1)
MD.write_text(
    "# Normalized CFR Fallback\n\n"
    f"- Targets: **{len(rows)}**\n"
    f"- CFR Java produced: **{ok}**\n"
    f"- Missing/failed: **{len(rows)-ok}**\n\n"
    "These are alternate candidates only; they do not replace Vineflower source until compile comparison passes.\n",
    encoding="utf-8"
)
print(json.dumps({"targets":len(rows),"produced":ok,"failed":len(rows)-ok},indent=2))
