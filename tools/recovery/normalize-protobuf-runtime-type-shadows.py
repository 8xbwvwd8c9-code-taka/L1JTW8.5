#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r/an')
REC=Path('recovery')
OUT=REC/'normalized_runtime_type_shadow_transform.json'
MD=REC/'NORMALIZED_RUNTIME_TYPE_SHADOW_TRANSFORM.md'

def matching_brace(text,open_pos):
    depth=0; i=open_pos; ins=False; inc=False; esc=False; lc=False; bc=False
    while i<len(text):
        c=text[i]; n=text[i+1] if i+1<len(text) else ''
        if lc:
            if c=='\n': lc=False
            i+=1; continue
        if bc:
            if c=='*' and n=='/': bc=False; i+=2; continue
            i+=1; continue
        if ins:
            if esc: esc=False
            elif c=='\\': esc=True
            elif c=='"': ins=False
            i+=1; continue
        if inc:
            if esc: esc=False
            elif c=='\\': esc=True
            elif c=="'": inc=False
            i+=1; continue
        if c=='/' and n=='/': lc=True; i+=2; continue
        if c=='/' and n=='*': bc=True; i+=2; continue
        if c=='"': ins=True; i+=1; continue
        if c=="'": inc=True; i+=1; continue
        if c=='{': depth+=1
        elif c=='}':
            depth-=1
            if depth==0: return i
        i+=1
    raise ValueError('unmatched brace')

p_sites=0; changes=[]
for p in sorted(STAGE.glob('PBMessageALL*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    text2,n=re.subn(r'(?<![A-Za-z0-9_.$])p\.a\.a\s*\(', 'l1rpb.p.a.a(', text)
    if n:
        p_sites+=n; changes.append({'file':p.name,'kind':'p.a.a','count':n})
        text=text2
    p.write_text(text,encoding='utf-8')

# PBMessageALL2 has two message classes where an instance int field named `ap`
# shadows protobuf runtime type `ap`. Qualify only those enclosing class blocks.
p=STAGE/'PBMessageALL2.java'
text=p.read_text(encoding='utf-8',errors='replace')
class_rx=re.compile(r'public static final class\s+(L1R_[acegi])\b[^\{]*\{')
matches=list(class_rx.finditer(text))
shadow_blocks=0; ap_c_sites=0; ap_b_sites=0
for m in reversed(matches):
    open_pos=text.find('{',m.start())
    close_pos=matching_brace(text,open_pos)
    block=text[m.start():close_pos+1]
    if not re.search(r'(?m)^\s*private int ap;\s*$',block):
        continue
    shadow_blocks+=1
    block,nc=re.subn(r'(?<![A-Za-z0-9_.$])ap\.c\s*\(\s*\)', 'l1rpb.ap.c()', block)
    block,nb=re.subn(r'(?<![A-Za-z0-9_.$])ap\.b\s*\(\s*\)', 'l1rpb.ap.b()', block)
    ap_c_sites+=nc; ap_b_sites+=nb
    text=text[:m.start()]+block+text[close_pos+1:]
p.write_text(text,encoding='utf-8')

state={
  'error_family':'PROTOBUF_RUNTIME_TYPE_NAME_SHADOW',
  'expected_p_a_a_sites':28,
  'p_a_a_sites':p_sites,
  'expected_ap_shadow_blocks':2,
  'ap_shadow_blocks':shadow_blocks,
  'expected_ap_c_sites':2,
  'ap_c_sites':ap_c_sites,
  'expected_ap_b_sites':2,
  'ap_b_sites':ap_b_sites,
  'gameplay_logic_changed':False,
  'call_targets_changed':False,
  'normalization_required_for_donor_compare':True,
  'changes':changes,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(p_sites==28 and shadow_blocks==2 and ap_c_sites==2 and ap_b_sites==2)
MD.write_text(
 '# Normalized Protobuf Runtime Type Shadow Transform\n\n'
 f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 f'- `p.a.a(...)` qualified: **{p_sites} / 28**\n'
 f'- `ap` shadow blocks: **{shadow_blocks} / 2**\n'
 f'- `ap.c()` qualified in shadow blocks: **{ap_c_sites} / 2**\n'
 f'- `ap.b()` qualified in shadow blocks: **{ap_b_sites} / 2**\n'
 '- Method targets changed: **NO**\n'
 '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('runtime type shadow gate failed')
