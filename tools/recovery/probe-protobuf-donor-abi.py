#!/usr/bin/env python3
import json, re, subprocess
from pathlib import Path

JAR = Path('l1jserver2.jar')
REC = Path('recovery')
OUT = REC / 'protobuf_donor_abi_probe.json'
MD = REC / 'PROTOBUF_DONOR_ABI_PROBE.md'

builders=[]
for top in 'abcdefghi':
    inners=['a$a','c$a','e$a','g$a'] + ([] if top=='i' else ['i$a'])
    for inner in inners:
        builders.append('an.' + top + '$' + inner)

def javap(cls):
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-s','-v',cls],text=True,capture_output=True)
    if cp.returncode != 0:
        raise SystemExit('javap failed for %s: %s' % (cls,cp.stderr))
    return cp.stdout

def method_blocks(txt):
    lines=txt.splitlines()
    blocks=[]
    method_rx=re.compile(r'^  .+\([^)]*\).+;$|^  .+\([^)]*\);$')
    for i,line in enumerate(lines):
        if not method_rx.match(line):
            continue
        chunk=[line.strip()]
        j=i+1
        while j<len(lines) and not method_rx.match(lines[j]):
            st=lines[j].strip()
            if st.startswith(('descriptor:','flags:','Signature:')):
                chunk.append(st)
            if lines[j].startswith('  Code:') or lines[j].startswith('}'):
                break
            j+=1
        blocks.append(chunk)
    return blocks

builder_rows=[]
for cls in builders:
    blocks=method_blocks(javap(cls))
    ds=[b for b in blocks if re.search(r'\bd\(\);$',b[0])]
    builder_rows.append({'class':cls,'noarg_d':ds})

runtime={}
for cls in ['a.ab','a.a$c','a.p$a','a.a$a']:
    runtime[cls]=method_blocks(javap(cls))

state={'builder_count':len(builders),'builders':builder_rows,'runtime':runtime}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')

md=['# Protobuf Donor ABI Probe','',f'- Builders: **{len(builders)}**','']
for row in builder_rows:
    md.append('## '+row['class'])
    if row['noarg_d']:
        for b in row['noarg_d']:
            md.append('- ' + ' | '.join(b))
    else:
        md.append('- no no-arg d() method')
    md.append('')
for cls,blocks in runtime.items():
    md += ['## Runtime '+cls,'']
    for b in blocks:
        if any(k in b[0] for k in (' d(',' e(',' c(',' a(')):
            md.append('- ' + ' | '.join(b))
    md.append('')
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({'builder_count':len(builders),'builders_with_noarg_d':sum(bool(x['noarg_d']) for x in builder_rows)},indent=2))
