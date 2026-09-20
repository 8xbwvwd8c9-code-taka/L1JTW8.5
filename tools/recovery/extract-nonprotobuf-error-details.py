#!/usr/bin/env python3
import json,re
from collections import Counter,defaultdict
from pathlib import Path

LOG=Path('recovery/normalized_stage_javac.log')
OUT=Path('recovery/nonprotobuf_error_details.json')
MD=Path('recovery/NONPROTOBUF_ERROR_DETAILS.md')
lines=LOG.read_text(encoding='utf-8',errors='replace').splitlines()

rows=[]
for i,line in enumerate(lines):
    if ': error: ' not in line: continue
    if '_normalized-stage-src/l1r/an/' in line: continue
    if '_normalized-stage-src/l1r/' not in line: continue
    left,msg=line.split(': error: ',1)
    m=re.search(r'_normalized-stage-src/(l1r/[^:]+):(\d+)$',left)
    if not m: continue
    file=m.group(1); lineno=int(m.group(2))
    ctx=lines[i:min(len(lines),i+8)]
    rows.append({'file':file,'line':lineno,'message':msg,'context':ctx})

def family(msg):
    x=msg.lower()
    if 'cannot find symbol' in x: return 'CANNOT_FIND_SYMBOL'
    if 'name clash' in x or 'same erasure' in x: return 'GENERIC_ERASURE'
    if 'does not override abstract method' in x or 'cannot override' in x or 'does not override or implement' in x: return 'OVERRIDE_BRIDGE'
    if 'reference to ' in x and ' is ambiguous' in x: return 'AMBIGUOUS_REFERENCE'
    if 'cannot be converted' in x or 'incompatible types' in x: return 'INCOMPATIBLE_TYPES'
    if 'no suitable constructor' in x or 'constructor ' in x and 'cannot be applied' in x: return 'CONSTRUCTOR_RESOLUTION'
    if 'already defined' in x: return 'DUPLICATE_IDENTITY'
    if 'return type' in x and 'not compatible' in x: return 'RETURN_TYPE_MISMATCH'
    return 'OTHER'

families=Counter(family(r['message']) for r in rows)
files=Counter(r['file'] for r in rows)
messages=Counter(r['message'] for r in rows)
by_family=defaultdict(list)
for r in rows: by_family[family(r['message'])].append(r)

state={
  'nonprotobuf_error_headers':len(rows),
  'families':families.most_common(),
  'files':files.most_common(),
  'messages':messages.most_common(),
  'rows':rows,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Non-Protobuf Error Details','',f'- Error headers: **{len(rows)}**','','## Families','']
for k,v in families.most_common(): md.append(f'- **{k}**: {v}')
md += ['','## Top files','']
for k,v in files.most_common(30): md.append(f'- `{k}`: {v}')
for fam,count in families.most_common():
    md += ['',f'## {fam} ({count})','']
    for r in by_family[fam][:20]:
        md += [f"### {r['file']}:{r['line']} — {r['message']}",'','```text',*r['context'],'```','']
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps({'nonprotobuf_error_headers':len(rows),'families':families.most_common(),'top_files':files.most_common(20),'top_messages':messages.most_common(20)},indent=2))
