#!/usr/bin/env python3
import csv,json,re,subprocess
from collections import Counter
from pathlib import Path

JAR=Path('l1jserver2.jar')
CSV=Path('recovery/synthetic_members.csv')
OUT=Path('recovery/protobuf_boolean_accessor_probe.json')
MD=Path('recovery/PROTOBUF_BOOLEAN_ACCESSOR_PROBE.md')

rows=[]
with CSV.open(encoding='utf-8-sig',newline='') as f:
    for r in csv.DictReader(f):
        if r['Class'].startswith('an.') and r['MemberKind']=='method' and r['Descriptor']=='()Z' and r['Synthetic']=='1':
            rows.append(r)

probe=[]
field_counts=Counter(); pattern_counts=Counter()
for r in rows:
    cls=r['Class']; method=r['Name']
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-c','-s',cls],text=True,capture_output=True)
    if cp.returncode!=0:
        raise SystemExit(f'javap failed: {cls}: {cp.stderr}')
    lines=cp.stdout.splitlines()
    found=None
    for i,line in enumerate(lines):
        if re.search(r'\b'+re.escape(method)+r'\(\);$',line.strip()):
            buf=[line.strip()]; j=i+1
            while j<len(lines):
                st=lines[j].strip()
                if j>i+1 and re.match(r'^(public|private|protected|static|final|synchronized|native|abstract|strictfp).+\);$',st):
                    break
                if st.startswith('descriptor:') or re.match(r'^\d+:',st): buf.append(st)
                if st=='}': break
                j+=1
            found=buf; break
    if not found:
        raise SystemExit(f'method not found in javap: {cls}.{method}()Z')
    getstatic=[]; ops=[]
    for x in found:
        m=re.match(r'^(\d+):\s+([a-z0-9_]+)\s*(.*)$',x)
        if not m: continue
        op=m.group(2); tail=m.group(3); ops.append(op)
        if op=='getstatic':
            fm=re.search(r'// Field ([A-Za-z0-9_$]+):Z',tail)
            if fm: getstatic.append(fm.group(1))
    field=getstatic[0] if len(getstatic)==1 else None
    if field: field_counts[field]+=1
    pattern=' -> '.join(ops); pattern_counts[pattern]+=1
    probe.append({'class':cls,'source_file':r['SourceFile'],'method':method,'field_reads':getstatic,'ops':ops,'block':found})

state={'expected_methods':44,'methods_probed':len(probe),'field_counts':field_counts.most_common(),'patterns':pattern_counts.most_common(),'rows':probe}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Protobuf Synthetic Boolean Accessor Probe','',f'- Methods: **{len(probe)} / 44**','','## Field reads','']
for k,v in field_counts.most_common(): md.append(f'- `{k}:Z` — **{v}**')
md += ['','## Opcode patterns','']
for k,v in pattern_counts.most_common(): md.append(f'- **{v}x** `{k}`')
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({'methods_probed':len(probe),'field_counts':field_counts.most_common(),'patterns':pattern_counts.most_common()},indent=2))
if len(probe)!=44: raise SystemExit(f'expected 44 synthetic ()Z methods, got {len(probe)}')
