#!/usr/bin/env python3
import json, shutil, subprocess, zipfile
from collections import Counter
from pathlib import Path

SRC=Path('recovery/protobuf-runtime-src-vf')
BUILD=Path('recovery/protobuf-runtime-source-build')
LOG=Path('recovery/protobuf_runtime_source_javac.log')
STATE=Path('recovery/protobuf_runtime_source_compile.json')
MD=Path('recovery/PROTOBUF_RUNTIME_SOURCE_COMPILE.md')
LIST=Path('recovery/protobuf_runtime_source_files.txt')

if not SRC.exists(): raise SystemExit(f'missing {SRC}')
if BUILD.exists(): shutil.rmtree(BUILD)
BUILD.mkdir(parents=True)
sources=sorted(p for p in SRC.rglob('*.java'))
LIST.write_text('\n'.join(p.as_posix() for p in sources)+'\n',encoding='utf-8')

cmd=['javac','-encoding','UTF-8','-source','8','-target','8','-proc:none','-Xmaxerrs','20000','-Xmaxwarns','5000','-d',str(BUILD),'@'+str(LIST)]
with LOG.open('w',encoding='utf-8',errors='replace') as fh:
    proc=subprocess.run(cmd,stdout=fh,stderr=subprocess.STDOUT,text=True)

lines=LOG.read_text(encoding='utf-8',errors='replace').splitlines()
headers=[x for x in lines if ': error: ' in x]
files=Counter(); messages=Counter()
for line in headers:
    try:
        left,msg=line.split(': error: ',1)
        path=left.rsplit(':',1)[0]
        rel=path.replace(SRC.as_posix()+'/', '')
        files[rel]+=1; messages[msg]+=1
    except Exception:
        pass
classes=sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob('*.class'))

RUNTIME_JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
with zipfile.ZipFile(RUNTIME_JAR) as zf:
    expected_classes=sorted(
        n for n in zf.namelist()
        if n.endswith('.class') and not n.startswith('META-INF/')
    )

built_set=set(classes)
expected_set=set(expected_classes)
missing=sorted(expected_set-built_set)
extra=sorted(built_set-expected_set)

(RECOVERY_MISSING:=Path('recovery/protobuf_runtime_source_class_set_missing.txt')).write_text(
    '\n'.join(missing)+('\\n' if missing else ''),
    encoding='utf-8',
)
(RECOVERY_EXTRA:=Path('recovery/protobuf_runtime_source_class_set_extra.txt')).write_text(
    '\n'.join(extra)+('\\n' if extra else ''),
    encoding='utf-8',
)

state={
  'gate':'PROTOBUF_RUNTIME_SOURCE_ONLY_EXPERIMENT',
  'source_root':str(SRC),
  'binary_runtime_on_classpath':False,
  'java_sources_submitted':len(sources),
  'compile_exit_code':proc.returncode,
  'generated_class_files_total':len(classes),
  'expected_runtime_class_files':len(expected_classes),
  'missing_runtime_classes':len(missing),
  'extra_runtime_classes':len(extra),
  'class_set_pass':proc.returncode==0 and not missing and not extra,
  'javac_error_headers':len(headers),
  'error_file_count':len(files),
  'top_error_files':files.most_common(50),
  'top_error_messages':messages.most_common(50),
}
STATE.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=[
 '# Protobuf Runtime Source-Only Compile Experiment','',
 f'Status: **{"PASS" if proc.returncode==0 else "FAIL"}**','',
 '- Recovery-relocated protobuf runtime supplied from Vineflower source: **YES**',
 '- Binary protobuf runtime on classpath: **NO**',
 '- L1J game sources included: **NO**','',
 f'- Java sources: **{len(sources)}**',
 f'- javac exit: **{proc.returncode}**',
 f'- Generated classes: **{len(classes)} / {len(expected_classes)}**',
 f'- Missing / extra runtime classes: **{len(missing)} / {len(extra)}**',
 f'- Class-set gate: **{"PASS" if proc.returncode==0 and not missing and not extra else "FAIL"}**',
 f'- Error headers: **{len(headers)}**',
 f'- Error files: **{len(files)}**','',
 '## Top error files','',
 '| File | Errors |','|---|---:|'
]
md += [f'| {k} | {v} |' for k,v in files.most_common(30)]
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
