#!/usr/bin/env python3
import json,re,subprocess
from collections import Counter
from pathlib import Path

JAR=Path('l1jserver2.jar')
OUT=Path('recovery/protobuf_required_bridge_probe.json')
MD=Path('recovery/PROTOBUF_REQUIRED_BRIDGE_PROBE.md')

rows=[]; sigs=Counter()
for top in 'abcdefghi':
  for inner in 'acegi':
    msg=f'an.{top}${inner}'
    for role,suffix in [('builder','$a'),('parser','$1')]:
      cls=msg+suffix
      cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-c','-s','-v',cls],text=True,capture_output=True)
      if cp.returncode!=0:
        rows.append({'class':cls,'role':role,'missing':True,'stderr':cp.stderr}); continue
      lines=cp.stdout.splitlines(); methods=[]
      for i,line in enumerate(lines):
        st=line.strip()
        if role=='builder' and re.search(r'\bd\(\);$',st):
          desc=''; flags='';
          for x in lines[i+1:i+8]:
            xs=x.strip()
            if xs.startswith('descriptor:'): desc=xs.split(':',1)[1].strip()
            if xs.startswith('flags:'): flags=xs
          methods.append({'decl':st,'descriptor':desc,'flags':flags})
          sigs[(role,desc,flags)]+=1
        if role=='parser' and re.search(r'\be\(java\.io\.InputStream, a\.n\);$',st):
          desc=''; flags='';
          for x in lines[i+1:i+8]:
            xs=x.strip()
            if xs.startswith('descriptor:'): desc=xs.split(':',1)[1].strip()
            if xs.startswith('flags:'): flags=xs
          methods.append({'decl':st,'descriptor':desc,'flags':flags})
          sigs[(role,desc,flags)]+=1
      rows.append({'class':cls,'role':role,'missing':False,'methods':methods})

state={'rows':rows,'signature_counts':[{'role':k[0],'descriptor':k[1],'flags':k[2],'count':v} for k,v in sigs.items()]}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf Required Bridge Probe','']
for s in state['signature_counts']: md.append(f"- {s['role']} `{s['descriptor']}` `{s['flags']}` — **{s['count']}**")
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state['signature_counts'],indent=2))
