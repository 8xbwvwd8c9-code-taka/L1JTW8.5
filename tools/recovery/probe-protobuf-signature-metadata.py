#!/usr/bin/env python3
import json, subprocess, re
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_signature_metadata_probe.json')
MD=Path('recovery/PROTOBUF_SIGNATURE_METADATA_PROBE.md')
targets=['l1rpb.ab','l1rpb.c','l1rpb.a$a','l1rpb.p$a','l1rpb.x$a']
rows=[]
for cls in targets:
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-v',cls],text=True,capture_output=True)
    if cp.returncode: raise SystemExit(f'javap failed {cls}: {cp.stderr}')
    lines=cp.stdout.splitlines()
    sigs=[]
    for i,line in enumerate(lines):
        st=line.strip()
        if st.startswith('Signature:'):
            ctx=lines[max(0,i-3):min(len(lines),i+2)]
            sigs.append({'line':st,'context':ctx})
    rows.append({'class':cls,'signature_count':len(sigs),'signatures':sigs})
state={'jar':str(JAR),'targets':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf Signature Metadata Probe','']
for r in rows:
    md += [f"## {r['class']}",f"- Signature attrs: **{r['signature_count']}**",'']
    for x in r['signatures']:
        md += ['```text',*x['context'],'```','']
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({r['class']:r['signature_count'] for r in rows},indent=2))
