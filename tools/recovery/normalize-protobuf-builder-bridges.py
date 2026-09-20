#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
REC=Path('recovery')
OUT=REC/'normalized_builder_bridge_transform.json'
MD=REC/'NORMALIZED_BUILDER_BRIDGE_TRANSFORM.md'

builder_decl=re.compile(r'public static final class ([A-Za-z_$][\w$]*) extends p\.a<[^>]+>')
bridge_rx=re.compile(
    r'\n\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+[^\n\{]+\{\s*\n'
    r'\s*return\s+[^;]+;\s*\n'
    r'\s*\}\s*',
    re.MULTILINE,
)

def matching_brace(text, open_pos):
    depth=0; i=open_pos; in_str=False; in_chr=False; esc=False; line_comment=False; block_comment=False
    while i < len(text):
        c=text[i]; n=text[i+1] if i+1<len(text) else ''
        if line_comment:
            if c=='\n': line_comment=False
            i+=1; continue
        if block_comment:
            if c=='*' and n=='/': block_comment=False; i+=2; continue
            i+=1; continue
        if in_str:
            if esc: esc=False
            elif c=='\\': esc=True
            elif c=='"': in_str=False
            i+=1; continue
        if in_chr:
            if esc: esc=False
            elif c=='\\': esc=True
            elif c=="'": in_chr=False
            i+=1; continue
        if c=='/' and n=='/': line_comment=True; i+=2; continue
        if c=='/' and n=='*': block_comment=True; i+=2; continue
        if c=='"': in_str=True; i+=1; continue
        if c=="'": in_chr=True; i+=1; continue
        if c=='{': depth+=1
        elif c=='}':
            depth-=1
            if depth==0: return i
        i+=1
    raise ValueError('unmatched brace')

changes=[]
builder_total=0
bridge_total=0
for p in sorted((STAGE/'l1r'/'an').glob('PBMessageALL*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    matches=list(builder_decl.finditer(text))
    if not matches:
        continue
    builder_total += len(matches)
    # Process from end to start so source offsets stay valid.
    file_removed=0
    for m in reversed(matches):
        open_pos=text.find('{',m.end())
        if open_pos<0: raise SystemExit(f'no builder opening brace: {p}')
        close_pos=matching_brace(text,open_pos)
        block=text[m.start():close_pos+1]
        block2,n=bridge_rx.subn('\n',block)
        file_removed += n
        text=text[:m.start()] + block2 + text[close_pos+1:]
    p.write_text(text,encoding='utf-8')
    bridge_total += file_removed
    changes.append({'file':p.relative_to(STAGE).as_posix(),'builders':len(matches),'removed_override_bridges':file_removed})

state={
  'error_family':'PROTOBUF_EXPLICIT_SYNTHETIC_BUILDER_BRIDGES',
  'expected_builder_count':44,
  'builder_count':builder_total,
  'expected_bridge_count':528,
  'removed_override_bridges':bridge_total,
  'changed_files':len(changes),
  'changes':changes,
  'parser_bridges_touched':False,
  'synthetic_constructors_retained':True,
  'typed_methods_retained':True,
  'generic_superclass_retained':True,
  'gameplay_logic_changed':False,
  'bridges_expected_to_be_regenerated_by_javac':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
status='PASS' if builder_total==44 and bridge_total==528 else 'FAIL'
MD.write_text(
  '# Normalized Protobuf Builder Bridge Transform\n\n'
  f'Status: **{status}**\n\n'
  f'- Builders: **{builder_total} / 44**\n'
  f'- Removed explicit builder bridges: **{bridge_total} / 528**\n'
  '- Parser bridges touched: **NO**\n'
  '- Generic builder superclass retained: **YES**\n'
  '- Typed builder methods retained: **YES**\n'
  '- Gameplay logic changed: **NO**\n'
  '- javac is expected to regenerate erased/covariant bridges for ABI normalization.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if status!='PASS':
    raise SystemExit(f'expected builders=44/bridges=528, got {builder_total}/{bridge_total}')
