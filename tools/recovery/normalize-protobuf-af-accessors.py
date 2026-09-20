#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r/an')
REC=Path('recovery')
OUT=REC/'normalized_af_accessor_transform.json'
MD=REC/'NORMALIZED_AF_ACCESSOR_TRANSFORM.md'

call_rx=re.compile(r'(?P<outer>PBMessageALL\d*)\.(?P<inner>L1R_[acegi])\.af\(\);')

replacements=[]
injected=[]
for p in sorted(STAGE.glob('PBMessageALL*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    calls=list(call_rx.finditer(text))
    if not calls:
        continue
    targets=sorted({(m.group('outer'),m.group('inner')) for m in calls})

    # Preserve the donor getstatic side effect with a source-safe field name.
    # The donor m:Z is never written (33 classes, total putstatic m:Z = 0),
    # therefore its JVM-default value is false. Reading this field still
    # triggers class initialization exactly as a getstatic does.
    for outer,inner in targets:
        decl_rx=re.compile(r'(public static final class '+re.escape(inner)+r'\b[^\{]*\{)')
        m=decl_rx.search(text)
        if not m:
            raise SystemExit(f'message class declaration not found: {p} {outer}.{inner}')
        insert='\n      private static boolean l1r_m_Z; // recovery-only name for donor m:Z'
        text=text[:m.end()] + insert + text[m.end():]
        injected.append(f'{outer}.{inner}')

    def repl(m):
        fq=f"{m.group('outer')}.{m.group('inner')}"
        replacements.append(fq)
        return f"if ({fq}.l1r_m_Z) {{}}"
    text=call_rx.sub(repl,text)
    p.write_text(text,encoding='utf-8')

state={
  'error_family':'PROTOBUF_SYNTHETIC_BOOLEAN_ACCESSOR_SOURCE_REPRESENTATION',
  'expected_accessor_sites':33,
  'replaced_accessor_sites':len(replacements),
  'expected_safe_fields':33,
  'injected_safe_fields':len(injected),
  'safe_field_name':'l1r_m_Z',
  'donor_field_identity':'m:Z',
  'donor_total_putstatic_m_z':0,
  'donor_accessor_semantics':'getstatic m:Z -> ireturn; caller pop',
  'class_initialization_read_preserved':True,
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
  'classes':injected,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
status='PASS' if len(replacements)==33 and len(injected)==33 else 'FAIL'
MD.write_text(
  '# Normalized Protobuf af() Accessor Transform\n\n'
  f'Status: **{status}**\n\n'
  f'- Accessor sites: **{len(replacements)} / 33**\n'
  f'- Recovery-safe fields: **{len(injected)} / 33**\n'
  '- Donor accessor: `getstatic m:Z -> ireturn`; caller discards result.\n'
  '- Donor writes to m:Z: **0**.\n'
  '- Recovery representation: `l1r_m_Z` + empty-if read.\n'
  '- getstatic/class initialization behavior preserved: **YES**\n'
  '- Gameplay logic changed: **NO**\n'
  '- Final comparison must normalize `l1r_m_Z:Z` back to donor `m:Z`.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if status!='PASS':
    raise SystemExit(f'expected 33 af accessor sites/fields, got {len(replacements)}/{len(injected)}')
