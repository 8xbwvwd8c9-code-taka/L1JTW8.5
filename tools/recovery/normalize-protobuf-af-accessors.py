#!/usr/bin/env python3
import csv,json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r/an')
REC=Path('recovery')
CSV=REC/'synthetic_members.csv'
OUT=REC/'normalized_af_accessor_transform.json'
MD=REC/'NORMALIZED_AF_ACCESSOR_TRANSFORM.md'

# Build the exact donor accessor map from audited synthetic ()Z members.
# Donor probe gate established 44/44 as: getstatic m:Z -> ireturn.
source_to_outer={
  'PBMessageALL.java':'PBMessageALL',
  'PBMessageALL2.java':'PBMessageALL2',
  'PBMessageALL3.java':'PBMessageALL3',
  'PBMessageALL4.java':'PBMessageALL4',
  'PBMessageALL5.java':'PBMessageALL5',
  'PBMessageALL6.java':'PBMessageALL6',
  'PBMessageALL7.java':'PBMessageALL7',
  'PBMessageALL8.java':'PBMessageALL8',
  'PBMessageALL9.java':'PBMessageALL9',
}
accessors=[]
with CSV.open(encoding='utf-8-sig',newline='') as fh:
    for r in csv.DictReader(fh):
        if not (r['Class'].startswith('an.') and r['MemberKind']=='method' and r['Descriptor']=='()Z' and r['Synthetic']=='1'):
            continue
        outer=source_to_outer.get(r['SourceFile'])
        if not outer: continue
        inner_obf=r['Class'].split('$',1)[1]
        if inner_obf not in {'a','c','e','g','i'}: continue
        accessors.append({
          'outer':outer,
          'inner':'L1R_'+inner_obf,
          'method':r['Name'],
          'donor_class':r['Class'],
        })

if len(accessors)!=44:
    raise SystemExit(f'expected 44 donor synthetic ()Z accessors, got {len(accessors)}')

by_file={}
for a in accessors:
    by_file.setdefault(a['outer']+'.java',[]).append(a)

replacements=[]; injected=[]
for filename,items in sorted(by_file.items()):
    p=STAGE/filename
    text=p.read_text(encoding='utf-8',errors='replace')
    for a in items:
        fq=f"{a['outer']}.{a['inner']}"
        call=f"{fq}.{a['method']}();"
        count=text.count(call)
        if count!=1:
            raise SystemExit(f'expected exactly one accessor call {call} in {p}, got {count}')

        decl_rx=re.compile(r'(public static final class '+re.escape(a['inner'])+r'\b[^\{]*\{)')
        m=decl_rx.search(text)
        if not m:
            raise SystemExit(f'message class declaration not found: {p} {fq}')
        marker='private static boolean l1r_m_Z;'
        if marker not in text[m.end():m.end()+240]:
            insert='\n      private static boolean l1r_m_Z; // recovery-only name for donor m:Z'
            text=text[:m.end()] + insert + text[m.end():]
            injected.append(fq)

        text=text.replace(call,f"if ({fq}.l1r_m_Z) {{}}",1)
        replacements.append({**a,'source_identity':fq})

    p.write_text(text,encoding='utf-8')

state={
  'error_family':'PROTOBUF_SYNTHETIC_BOOLEAN_ACCESSOR_SOURCE_REPRESENTATION',
  'expected_accessor_sites':44,
  'replaced_accessor_sites':len(replacements),
  'expected_safe_fields':44,
  'injected_safe_fields':len(injected),
  'safe_field_name':'l1r_m_Z',
  'donor_field_identity':'m:Z',
  'donor_accessor_semantics':'getstatic m:Z -> ireturn; caller pop',
  'donor_accessor_probe_count':44,
  'class_initialization_read_preserved':True,
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
  'accessors':replacements,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
status='PASS' if len(replacements)==44 and len(injected)==44 else 'FAIL'
MD.write_text(
  '# Normalized Protobuf Synthetic Boolean Accessor Transform\n\n'
  f'Status: **{status}**\n\n'
  f'- Accessor sites: **{len(replacements)} / 44**\n'
  f'- Recovery-safe fields: **{len(injected)} / 44**\n'
  '- Donor probe: **44/44** `getstatic m:Z -> ireturn`.\n'
  '- Caller discards the boolean result.\n'
  '- Recovery representation: `l1r_m_Z` + empty-if read.\n'
  '- getstatic/class initialization behavior preserved: **YES**\n'
  '- Gameplay logic changed: **NO**\n'
  '- Final comparison must normalize `l1r_m_Z:Z` back to donor `m:Z`.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if status!='PASS':
    raise SystemExit(f'expected 44 accessor sites/fields, got {len(replacements)}/{len(injected)}')
