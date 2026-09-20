#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('l1jserver2.jar')
OUT=Path('recovery/protobuf_bridge_delegate_probe.json')
MD=Path('recovery/PROTOBUF_BRIDGE_DELEGATE_PROBE.md')
classes=['an.d$a$a','an.d$c$a','an.d$a$1','an.d$c$1']
rows=[]
for cls in classes:
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-c','-s',cls],text=True,capture_output=True)
    if cp.returncode:
        rows.append({'class':cls,'error':cp.stderr}); continue
    lines=cp.stdout.splitlines()
    hits=[]
    for i,line in enumerate(lines):
        st=line.strip()
        if (' b(a.h, a.n)' in st or ' b(a.h,a.n)' in st or
            ' e(java.io.InputStream, a.n)' in st or ' e(java.io.InputStream,a.n)' in st or
            re.search(r'\bb\(a\.h, a\.n\)',st) or re.search(r'\be\(java\.io\.InputStream, a\.n\)',st)):
            block=lines[i:min(len(lines),i+28)]
            hits.append({'decl':st,'context':block})
    rows.append({'class':cls,'hits':hits})
state={'classes':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf Bridge Delegate Probe','']
for r in rows:
    md.append(f"## {r['class']}")
    md.append('')
    if 'error' in r: md += ['```text',r['error'],'```','']; continue
    for h in r['hits']:
        md += ['```text',*h['context'],'```','']
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
