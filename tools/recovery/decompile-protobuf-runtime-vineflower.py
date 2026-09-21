#!/usr/bin/env python3
import json, shutil, subprocess, sys, tempfile, zipfile
from pathlib import Path

SRC=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf-runtime-src-vf')
STATE=Path('recovery/protobuf_runtime_source_decompile.json')
MD=Path('recovery/PROTOBUF_RUNTIME_SOURCE_DECOMPILE.md')

if len(sys.argv)!=2:
    raise SystemExit('usage: decompile-protobuf-runtime-vineflower.py <vineflower.jar>')
VF=Path(sys.argv[1])
if not SRC.exists(): raise SystemExit(f'missing {SRC}')
if not VF.exists(): raise SystemExit(f'missing {VF}')

if OUT.exists(): shutil.rmtree(OUT)
OUT.mkdir(parents=True)
tmp=Path(tempfile.mkdtemp(prefix='l1jtw85-pbrt-vf-'))
vfout=tmp/'out'; vfout.mkdir()
try:
    cmd=[
      'java','-jar',str(VF),
      '--log-level=warn',
      '--use-lvt-names=0',
      '--use-method-parameters=0',
      '--remove-synthetic=0',
      '--remove-bridge=0',
      '--skip-extra-files=1',
      '--rename-members=0',
      str(SRC),str(vfout),
    ]
    proc=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True)
    (OUT/'vineflower.log').write_text(proc.stdout,encoding='utf-8',errors='replace')
    java_files=list(vfout.rglob('*.java'))
    copied=0
    for p in java_files:
        rel=p.relative_to(vfout)
        dst=OUT/rel; dst.parent.mkdir(parents=True,exist_ok=True)
        shutil.copy2(p,dst); copied+=1
    class_files=0; top=set()
    with zipfile.ZipFile(SRC) as zf:
        for n in zf.namelist():
            if n.endswith('.class'):
                class_files+=1; top.add(n.split('$',1)[0])
    state={
      'source_jar':str(SRC),
      'vineflower_exit_code':proc.returncode,
      'runtime_class_files':class_files,
      'runtime_top_level_classes':len(top),
      'java_files_copied':copied,
      'expected_top_level_java_files':len(top),
      'package':'l1rpb',
      'gameplay_logic_changed':False,
    }
    STATE.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
    ok=(proc.returncode==0 and copied==len(top))
    MD.write_text(
      '# Protobuf Runtime Source Decompile\n\n'
      + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
      + f'- Runtime class files: **{class_files}**\n'
      + f'- Top-level classes: **{len(top)}**\n'
      + f'- Vineflower Java files: **{copied} / {len(top)}**\n'
      + '- Input is the recovery-relocated `l1rpb/**` runtime JAR.\n'
      + '- Gameplay logic changed: **NO**\n',
      encoding='utf-8')
    print(json.dumps(state,indent=2))
    if not ok: raise SystemExit(f'protobuf runtime decompile gate failed: {copied}/{len(top)} exit={proc.returncode}')
finally:
    shutil.rmtree(tmp,ignore_errors=True)
