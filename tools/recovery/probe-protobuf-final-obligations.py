#!/usr/bin/env python3
import json, subprocess, re
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_final_obligation_probe.json')
MD=Path('recovery/PROTOBUF_FINAL_OBLIGATION_PROBE.md')

PAIRS=[
  {
    'abstract_class':'l1rpb.ab',
    'concrete_class':'l1rpb.c',
    'name':'f',
    'descriptor':'(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;',
    'label':'parser f(InputStream,n)',
  },
  {
    'abstract_class':'l1rpb.a$a',
    'concrete_class':'l1rpb.p$a',
    'name':'d',
    'descriptor':'()Ll1rpb/a$a;',
    'label':'builder d()',
  },
]

def methods(cls):
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-v',cls],text=True,capture_output=True)
    if cp.returncode:
        raise SystemExit(f'javap failed {cls}: {cp.stderr}')
    lines=cp.stdout.splitlines()
    rows=[]
    method_rx=re.compile(r'^  .+\([^)]*\).*$')
    for i,line in enumerate(lines):
        if not method_rx.match(line):
            continue
        decl=line.strip()
        desc=''; flags=''
        for x in lines[i+1:i+10]:
            st=x.strip()
            if st.startswith('descriptor:'): desc=st.split(':',1)[1].strip()
            if st.startswith('flags:'): flags=st
            if desc and flags: break
        # method name is the token before '(' after optional return type
        m=re.search(r'([A-Za-z_$][\w$]*)\s*\(',decl)
        if m and desc:
            rows.append({'name':m.group(1),'descriptor':desc,'flags':flags,'decl':decl})
    return rows

result=[]
for p in PAIRS:
    am=methods(p['abstract_class'])
    cm=methods(p['concrete_class'])
    a=[x for x in am if x['name']==p['name'] and x['descriptor']==p['descriptor']]
    c=[x for x in cm if x['name']==p['name'] and x['descriptor']==p['descriptor']]
    row=dict(p)
    row['abstract_matches']=a
    row['concrete_matches']=c
    row['abstract_exact']=len(a)==1 and 'ACC_ABSTRACT' in a[0]['flags']
    row['concrete_exact']=len(c)==1 and 'ACC_ABSTRACT' not in c[0]['flags']
    row['pass']=row['abstract_exact'] and row['concrete_exact']
    result.append(row)

state={'jar':str(JAR),'pairs':result,'all_pass':all(x['pass'] for x in result)}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Final Protobuf Obligation Probe','',f'- All exact donor ABI pairs PASS: **{"YES" if state["all_pass"] else "NO"}**','']
for r in result:
    md += [
      f"## {r['label']}",
      '',
      f"- Abstract: `{r['abstract_class']}.{r['name']}{r['descriptor']}` exact abstract = **{r['abstract_exact']}**",
      f"- Provider: `{r['concrete_class']}.{r['name']}{r['descriptor']}` exact concrete = **{r['concrete_exact']}**",
      ''
    ]
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
if not state['all_pass']:
    raise SystemExit('final protobuf obligation donor ABI probe failed')
