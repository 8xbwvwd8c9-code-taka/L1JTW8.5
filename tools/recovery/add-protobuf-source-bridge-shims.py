#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r/an')
OUT=Path('recovery/protobuf_source_bridge_shims.json')
MD=Path('recovery/PROTOBUF_SOURCE_BRIDGE_SHIMS.md')

parser_count=0; builder_count=0; details=[]

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

for p in sorted(STAGE.glob('PBMessageALL*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')

    # Parser anonymous classes: new l1rpb.c<Concrete>() { ... }
    parser_rx=re.compile(r'new\s+l1rpb\.c<(?P<msg>[A-Za-z0-9_.$]+)>\(\)\s*\{')
    pm=list(parser_rx.finditer(text))
    for m in reversed(pm):
        msg=m.group('msg')
        open_pos=text.find('{',m.start())
        shim=("\n         // recovery source bridges for erased Parser.e(InputStream[,n])\n"
              "         @Override\n"
              f"         public {msg} e(java.io.InputStream var1, l1rpb.n var2) throws l1rpb.s {{\n"
              f"            return ({msg})super.e(var1, var2);\n"
              "         }\n\n"
              "         @Override\n"
              f"         public {msg} e(java.io.InputStream var1) throws l1rpb.s {{\n"
              f"            return ({msg})super.e(var1);\n"
              "         }\n")
        text=text[:open_pos+1]+shim+text[open_pos+1:]
        parser_count+=1
        details.append({'file':p.name,'kind':'parser','message_type':msg})

    # Builder classes after collision repair/raw-super normalization.
    # They already have typed e(h,n)->ConcreteBuilder; add only the erased b(h,n) obligation.
    builder_rx=re.compile(r'public\s+static\s+final\s+class\s+(?P<name>[A-Za-z0-9_$]+)\s+extends\s+p\.a\s+implements\s+(?P<intf>[A-Za-z0-9_.$]+)\s*\{')
    bm=list(builder_rx.finditer(text))
    for m in reversed(bm):
        open_pos=text.find('{',m.start())
        close_pos=matching_brace(text,open_pos)
        block=text[m.start():close_pos+1]
        typed=re.search(r'public\s+(?P<ret>[A-Za-z0-9_.$]+)\s+e\s*\(\s*(?:l1rpb\.)?h\s+var1\s*,\s*(?:l1rpb\.)?n\s+var2\s*\)\s+throws\s+IOException\s*\{',block)
        if not typed:
            continue
        shim=("\n         // recovery source bridges for raw p.a / x$a covariance\n"
              "         @Override\n"
              "         public l1rpb.b.a b(l1rpb.h var1, l1rpb.n var2) throws java.io.IOException {\n"
              "            return this.e(var1, var2);\n"
              "         }\n\n"
              "         @Override\n"
              "         public l1rpb.x.a d(java.io.InputStream var1, l1rpb.n var2) throws java.io.IOException {\n"
              "            return (l1rpb.x.a)super.d(var1, var2);\n"
              "         }\n\n"
              "         @Override\n"
              "         public l1rpb.x.a d(java.io.InputStream var1) throws java.io.IOException {\n"
              "            return (l1rpb.x.a)super.d(var1);\n"
              "         }\n")
        text=text[:open_pos+1]+shim+text[open_pos+1:]
        builder_count+=1
        details.append({'file':p.name,'kind':'builder','class_name':m.group('name'),'typed_return':typed.group('ret')})

    p.write_text(text,encoding='utf-8')

state={
  'expected_parser_shims':44,
  'parser_shims':parser_count,
  'expected_builder_shims':44,
  'builder_shims':builder_count,
  'parser_delegate':'super.e(InputStream[,n]) + covariant cast only',
  'builder_delegate':'typed e(h,n) plus super.d(InputStream[,n]) covariant casts',
  'gameplay_logic_changed':False,
  'source_representation_only':True,
  'normalization_required_for_donor_compare':True,
  'details':details,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(parser_count==44 and builder_count==44)
MD.write_text(
  '# Protobuf Source Bridge Shims\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + f'- Parser shims: **{parser_count} / 44**\n'
  + f'- Builder shims: **{builder_count} / 44**\n'
  + '- Parser shims delegate to existing runtime `super.e(InputStream[,n])`.\n'
  + '- Builder shims delegate to existing generated `e(h,n)` and inherited `super.d(InputStream[,n])`.\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Recovery source representation only: **YES**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('protobuf source bridge shim gate failed')
