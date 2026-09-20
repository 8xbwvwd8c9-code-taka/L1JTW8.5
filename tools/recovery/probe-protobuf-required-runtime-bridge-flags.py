#!/usr/bin/env python3
import json,subprocess,re
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_required_runtime_bridge_flags.json')
MD=Path('recovery/PROTOBUF_REQUIRED_RUNTIME_BRIDGE_FLAGS.md')
targets={
  'l1rpb.p$a':[('d','()Ll1rpb/a$a;')],
  'l1rpb.c':[('e','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;')],
}
rows=[]
for cls,wanted in targets.items():
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-v',cls],text=True,capture_output=True)
    if cp.returncode: raise SystemExit(f'javap failed {cls}: {cp.stderr}')
    lines=cp.stdout.splitlines()
    for name,desc in wanted:
        found=[]
        for i,line in enumerate(lines):
            st=line.strip()
            if not re.search(r'\b'+re.escape(name)+r'\([^;]*\);$',st): continue
            d=''; flags=''
            for x in lines[i+1:i+9]:
                xs=x.strip()
                if xs.startswith('descriptor:'): d=xs.split(':',1)[1].strip()
                if xs.startswith('flags:'): flags=xs
            if d==desc: found.append({'decl':st,'descriptor':d,'flags':flags})
        rows.append({'class':cls,'name':name,'descriptor':desc,'matches':found})

state={'targets':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=all(len(r['matches'])==1 and 'ACC_SYNTHETIC' in r['matches'][0]['flags'] for r in rows)
md=['# Required Protobuf Runtime Bridge Flags','',f'Status: **{"PASS" if ok else "FAIL"}**','']
for r in rows:
    md.append(f"- `{r['class']}.{r['name']}{r['descriptor']}` → `{r['matches'][0]['flags'] if r['matches'] else 'NOT_FOUND'}`")
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('required runtime bridge synthetic-flag probe failed')
