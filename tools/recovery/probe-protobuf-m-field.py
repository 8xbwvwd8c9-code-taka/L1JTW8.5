#!/usr/bin/env python3
import json,re,subprocess
from collections import Counter
from pathlib import Path

JAR=Path('l1jserver2.jar')
REC=Path('recovery')
OUT=REC/'protobuf_m_field_probe.json'
MD=REC/'PROTOBUF_M_FIELD_PROBE.md'

classes=[]
for top in 'abcdefghi':
    for inner in ['a','c','e','g','i']:
        cls=f'an.{top}${inner}'
        cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-c','-v',cls],text=True,capture_output=True)
        if cp.returncode!=0: continue
        txt=cp.stdout
        if re.search(r'\bstatic boolean af\(\);|\bstatic final boolean af\(\);|\bstatic .*boolean af\(\);',txt):
            classes.append((cls,txt))

rows=[]
patterns=Counter()
for cls,txt in classes:
    lines=txt.splitlines()
    field=[]; clinit=[]; all_putstatic=[]
    for i,line in enumerate(lines):
        if re.match(r'^\s+(?:private|protected|public).*\bboolean m;$',line):
            field=lines[i:min(len(lines),i+4)]
        if line.strip()=='static {};':
            j=i; buf=[]
            while j<len(lines) and len(buf)<120:
                buf.append(lines[j])
                if j>i and lines[j].startswith('}'): break
                j+=1
            clinit=buf
    all_putstatic=[ln.strip() for ln in lines if 'putstatic' in ln and '// Field m:Z' in ln]
    putctx=[]
    for i,line in enumerate(clinit):
        if 'putstatic' in line and '// Field m:Z' in line:
            putctx=clinit[max(0,i-8):min(len(clinit),i+4)]
            break
    pat=' | '.join(x.strip() for x in putctx)
    patterns[pat]+=1
    rows.append({'class':cls,'field':field,'putstatic_context':putctx,'all_putstatic_m_z':all_putstatic})

total_putstatic=sum(len(x['all_putstatic_m_z']) for x in rows)
state={'classes_with_static_boolean_af':len(classes),'total_putstatic_m_z':total_putstatic,'rows':rows,'putstatic_patterns':patterns.most_common()}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf m:Z Field Probe','',f'- Classes with static boolean af(): **{len(classes)}**','','## putstatic patterns','']
for pat,n in patterns.most_common(): md.append(f'- **{n}x** `{pat}`')
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({'classes_with_static_boolean_af':len(classes),'total_putstatic_m_z':total_putstatic,'putstatic_patterns':patterns.most_common()},indent=2))
