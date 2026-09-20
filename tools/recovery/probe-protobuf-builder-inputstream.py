#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path
JAR=Path('l1jserver2.jar')
OUT=Path('recovery/protobuf_builder_inputstream_probe.json')
rows=[]
for cls in ['an.d$a$a','an.d$c$a']:
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-s',cls],text=True,capture_output=True)
    if cp.returncode: raise SystemExit(cp.stderr)
    lines=cp.stdout.splitlines(); hits=[]
    for i,line in enumerate(lines):
        if 'InputStream' in line:
            desc=''
            for x in lines[i+1:i+4]:
                if x.strip().startswith('descriptor:'): desc=x.strip().split(':',1)[1].strip()
            hits.append({'decl':line.strip(),'descriptor':desc})
    rows.append({'class':cls,'methods':hits})
state={'rows':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
