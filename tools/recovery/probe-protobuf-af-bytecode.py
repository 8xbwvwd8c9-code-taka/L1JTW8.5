#!/usr/bin/env python3
import json,re,subprocess
from collections import Counter
from pathlib import Path

JAR=Path('l1jserver2.jar')
REC=Path('recovery')
OUT=REC/'protobuf_af_bytecode_probe.json'
MD=REC/'PROTOBUF_AF_BYTECODE_PROBE.md'

classes=[]
for top in 'abcdefghi':
    for inner in ['a','c','e','g','i']:
        cls=f'an.{top}${inner}'
        cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-c','-s',cls],text=True,capture_output=True)
        if cp.returncode==0:
            classes.append((cls,cp.stdout))

rows=[]
patterns=Counter()
for cls,txt in classes:
    lines=txt.splitlines()
    for i,line in enumerate(lines):
        if re.search(r'\baf\(\);$',line.strip()):
            block=[line.strip()]
            j=i+1
            while j<len(lines):
                st=lines[j].strip()
                if j>i+1 and re.match(r'^(public|private|protected|static|final|synchronized|native|abstract|strictfp).+\);$',st):
                    break
                if st.startswith('descriptor:') or re.match(r'^\d+:',st):
                    block.append(st)
                if st=='return' or st.endswith('ireturn'):
                    # keep scanning a little only if needed
                    pass
                if st=='}': break
                j+=1
            ops=[]
            for x in block:
                m=re.match(r'^(\d+):\s+([a-z0-9_]+)\s*(.*)$',x)
                if m: ops.append(m.group(2)+((' '+m.group(3)) if m.group(3) else ''))
            sig=' -> '.join(re.sub(r'\s+#\d+',' #',op) for op in ops)
            patterns[sig]+=1
            rows.append({'class':cls,'block':block,'ops':ops,'pattern':sig})
            break

state={'classes_probed':len(classes),'af_methods_found':len(rows),'patterns':patterns.most_common(),'rows':rows}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf af() Donor Bytecode Probe','',f'- Classes probed: **{len(classes)}**',f'- af() methods found: **{len(rows)}**','','## Patterns','']
for pat,count in patterns.most_common(): md.append(f'- **{count}x** `{pat}`')
md += ['','## Methods','']
for row in rows:
    md += [f"### {row['class']}",'','```text',*row['block'],'```','']
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({'classes_probed':len(classes),'af_methods_found':len(rows),'patterns':patterns.most_common()},indent=2))
