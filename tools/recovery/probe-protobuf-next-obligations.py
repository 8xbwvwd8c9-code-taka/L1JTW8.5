#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_next_obligation_probe.json')
targets=['l1rpb.a$a','l1rpb.b$a','l1rpb.p$a','l1rpb.x$a','l1rpb.y$a','l1rpb.ab','l1rpb.c']
rows=[]
for cls in targets:
  cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-v',cls],text=True,capture_output=True)
  if cp.returncode: raise SystemExit(f'javap failed {cls}: {cp.stderr}')
  lines=cp.stdout.splitlines()
  for i,line in enumerate(lines):
    st=line.strip()
    if not (re.search(r'\bd\s*\(java\.io\.InputStream, l1rpb\.n\)',st) or re.search(r'\be\s*\(java\.io\.InputStream\)',st)):
      continue
    desc=''; flags=''
    for x in lines[i+1:i+8]:
      xs=x.strip()
      if xs.startswith('descriptor:'): desc=xs.split(':',1)[1].strip()
      if xs.startswith('flags:'): flags=xs
    rows.append({'class':cls,'decl':st,'descriptor':desc,'flags':flags})
state={'rows':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
