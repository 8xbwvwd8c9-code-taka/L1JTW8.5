#!/usr/bin/env python3
import subprocess,json
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_p_b_visibility_probe.txt')
STATE=Path('recovery/protobuf_p_b_visibility_probe.json')
chunks=[]; rows=[]
for cls in ['l1rpb.p','l1rpb.p$b','l1rpb.p$a']:
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-v',cls],text=True,capture_output=True)
    rows.append({'class':cls,'exit_code':cp.returncode})
    chunks.append('===== '+cls+' =====\n'+cp.stdout+'\n'+cp.stderr)
OUT.write_text('\n'.join(chunks),encoding='utf-8')
STATE.write_text(json.dumps({'jar':str(JAR),'targets':rows},indent=2)+'\n',encoding='utf-8')
print(OUT.read_text(encoding='utf-8'))
if any(x['exit_code']!=0 for x in rows): raise SystemExit('javap visibility probe failed')
