#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_builder_e_bytecode_probe.json')
TXT=Path('recovery/PROTOBUF_BUILDER_E_BYTECODE_PROBE.txt')

targets=[
 ('l1rpb.a$a','e','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/x$a;'),
 ('l1rpb.a$a','e','(Ljava/io/InputStream;)Ll1rpb/x$a;'),
 ('l1rpb.a$a','a','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/a$a;'),
 ('l1rpb.a$a','a','(Ljava/io/InputStream;)Ll1rpb/a$a;'),
]

def javap(cls):
    cp=subprocess.run(
        ['javap','-classpath',str(JAR),'-p','-s','-v','-c',cls],
        text=True,capture_output=True
    )
    if cp.returncode:
        raise SystemExit(cp.stderr)
    return cp.stdout

def blocks(text):
    lines=text.splitlines(); out=[]; i=0
    decl=re.compile(r'^  (?:public|protected|private) ')
    while i<len(lines):
        if decl.match(lines[i]):
            j=i+1
            while j<len(lines) and not decl.match(lines[j]) and not lines[j].startswith('  static {};'):
                j+=1
            out.append('\n'.join(lines[i:j])); i=j
        else:
            i+=1
    return out

texts={}
rows=[]
for cls in sorted({x[0] for x in targets}):
    texts[cls]=javap(cls)

for cls,name,desc in targets:
    found=[]
    for b in blocks(texts[cls]):
        if f'descriptor: {desc}' not in b: continue
        first=b.splitlines()[0]
        if re.search(rf'\b{re.escape(name)}\(',first):
            found.append(b)
    if len(found)!=1:
        raise SystemExit(f'expected one {cls}.{name}{desc}, got {len(found)}')
    b=found[0]
    invokes=[]
    for m in re.finditer(r'// Method ([A-Za-z0-9_$/]+)\.?(?:([A-Za-z0-9_$]+))?:(\S+)',b):
        owner=m.group(1); mname=m.group(2)
        if mname is None and '/' not in owner:
            continue
        invokes.append({'owner':owner,'name':mname,'descriptor':m.group(3)})
    # javap same-class form: // Method a:(...)...
    for m in re.finditer(r'// Method ([A-Za-z0-9_$]+):(\S+)',b):
        invokes.append({'owner':cls.replace('.','/'),'name':m.group(1),'descriptor':m.group(2)})
    rows.append({
        'class':cls,'name':name,'descriptor':desc,
        'flags_line':next((x.strip() for x in b.splitlines() if x.strip().startswith('flags:')),None),
        'signature_lines':[x.strip() for x in b.splitlines() if 'Signature:' in x],
        'invokes':invokes,
        'block':b,
    })

state={'jar':str(JAR),'targets':rows,'probe_only':True,'modified':False}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
TXT.write_text('\n\n'.join(r['block'] for r in rows)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
