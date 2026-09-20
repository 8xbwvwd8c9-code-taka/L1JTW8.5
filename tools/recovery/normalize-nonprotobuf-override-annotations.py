#!/usr/bin/env python3
import json,re
from pathlib import Path

OUT=Path('recovery/nonprotobuf_override_annotation_normalization.json')
MD=Path('recovery/NONPROTOBUF_OVERRIDE_ANNOTATION_NORMALIZATION.md')

targets={
 'l1r/ap/L1PcInstance.java':['public void c(int var1)'],
 'l1r/ap/L1PetInstance.java':['public void d(int var1)','public void b(boolean var1)','public void i()','public void b(L1ItemInstance var1)'],
 'l1r/ap/L1DoorInstance.java':['public void c(int var1)','public void d(int var1)'],
 'l1r/ap/L1MonsterInstance.java':['public void b(boolean var1)'],
 'l1r/ap/L1TowerInstance.java':['public boolean h()'],
}

changes=[]; total=0
for rel,sigs in targets.items():
    p=Path('_normalized-stage-src')/rel
    text=p.read_text(encoding='utf-8',errors='replace')
    file_n=0
    for sig in sigs:
        rx=re.compile(r'(?m)^(\s*)@Override\n\1'+re.escape(sig)+r'\s*\{')
        text,n=rx.subn(lambda m:m.group(1)+sig+' {',text,count=1)
        if n!=1: raise SystemExit(f'override annotation target missing/ambiguous: {rel} :: {sig} ({n})')
        file_n+=1; total+=1
    p.write_text(text,encoding='utf-8')
    changes.append({'file':rel,'annotations_removed':file_n,'signatures':sigs})

state={
 'error_family':'DECOMPILER_SOURCE_OVERRIDE_ANNOTATION',
 'annotations_removed':total,
 'expected_annotations':9,
 'changes':changes,
 'method_bodies_changed':False,
 'method_descriptors_changed':False,
 'runtime_annotations_changed':False,
 'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==9)
MD.write_text(
 '# Non-Protobuf Override Annotation Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- Invalid decompiler `@Override` annotations removed: **{total} / 9**\n'
 + '- Method bodies/descriptors changed: **NO / NO**\n'
 + '- Runtime behavior changed: **NO** (`@Override` is source-only).\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('override annotation normalization gate failed')
