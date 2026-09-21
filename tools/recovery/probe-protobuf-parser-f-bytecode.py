#!/usr/bin/env python3
import json, subprocess, re
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_parser_f_bytecode_probe.json')
TXT=Path('recovery/PROTOBUF_PARSER_F_BYTECODE_PROBE.txt')

if not JAR.exists():
    raise SystemExit(f'missing {JAR}')

cp=subprocess.run(
    ['javap','-classpath',str(JAR),'-p','-s','-v','-c','l1rpb.c'],
    text=True,capture_output=True
)
if cp.returncode:
    raise SystemExit(cp.stderr)

text=cp.stdout
TXT.write_text(text,encoding='utf-8')

targets=[
    ('f','(Ljava/io/InputStream;)Ljava/lang/Object;'),
    ('f','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
]

# Split javap output into method-like blocks. Keep exact descriptor-matching blocks.
blocks=[]
lines=text.splitlines()
for i,line in enumerate(lines):
    if re.search(r'public java\.lang\.Object f\(java\.io\.InputStream', line):
        j=i+1
        while j<len(lines) and not re.match(r'\s{2}(?:public|private|protected|static|final|abstract)', lines[j]):
            j+=1
        blocks.append('\n'.join(lines[i:j]))

rows=[]
for name,desc in targets:
    found=[b for b in blocks if f'descriptor: {desc}' in b]
    if len(found)!=1:
        raise SystemExit(f'expected one block for {name}{desc}, found {len(found)}')
    b=found[0]
    invokes=[]
    for m in re.finditer(r'// Method ([^\s]+)\.([^:]+):([^\s]+)', b):
        invokes.append({'owner':m.group(1),'name':m.group(2),'descriptor':m.group(3)})
    rows.append({
        'name':name,
        'descriptor':desc,
        'block':b,
        'invokes':invokes,
        'flags_line':next((x.strip() for x in b.splitlines() if x.strip().startswith('flags:')),None),
        'signature_lines':[x.strip() for x in b.splitlines() if 'Signature:' in x],
    })

state={
    'class':'l1rpb.c',
    'jar':str(JAR),
    'targets':rows,
    'probe_only':True,
    'modified_classfiles':False,
    'modified_source':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
