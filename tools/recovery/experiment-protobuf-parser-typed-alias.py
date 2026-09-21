#!/usr/bin/env python3
import json, re, struct, subprocess, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.parsertypedalias.jar')
OUT=Path('recovery/protobuf_parser_typed_alias_experiment.json')
MD=Path('recovery/PROTOBUF_PARSER_TYPED_ALIAS_EXPERIMENT.md')

TARGET_CLASS='l1rpb/c.class'
INTERFACE='l1rpb.ab'
ACC_SYNTHETIC=0x1000

class R:
    def __init__(self,b): self.b=bytearray(b); self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def parse_cp(data):
    r=R(data)
    if r.u4()!=0xCAFEBABE: raise ValueError('not class')
    r.skip(4)
    count=r.u2(); cp=[None]*count; i=1
    while i<count:
        tag=r.u1()
        if tag==1:
            n=r.u2(); raw=bytes(r.b[r.p:r.p+n]); r.skip(n); cp[i]=(1,raw)
        elif tag in (3,4): r.skip(4)
        elif tag in (5,6): r.skip(8); i+=1
        elif tag in (7,8,16,19,20): r.skip(2)
        elif tag in (9,10,11,12,17,18): r.skip(4)
        elif tag==15: r.skip(3)
        else: raise ValueError(f'unknown cp tag {tag} at {i}')
        i+=1
    return r.p,cp

def utf(cp,idx):
    e=cp[idx]
    if not e or e[0]!=1: return None
    return e[1].decode('utf-8','replace')

def skip_attrs(data,pos):
    n=struct.unpack_from('>H',data,pos)[0]; pos+=2
    for _ in range(n):
        ln=struct.unpack_from('>I',data,pos+2)[0]
        pos+=6+ln
    return pos

def method_end(data,pos):
    return skip_attrs(data,pos+6)

def method_inventory(data):
    cp_end,cp=parse_cp(data)
    pos=cp_end+6
    ic=struct.unpack_from('>H',data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    for _ in range(fc): pos=skip_attrs(data,pos+6)
    methods_count_off=pos
    mc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    rows=[]; blocks=[]
    for _ in range(mc):
        st=pos
        flags,name_idx,desc_idx=struct.unpack_from('>HHH',data,pos)
        pos=method_end(data,pos)
        raw=bytes(data[st:pos])
        rows.append({
            'flags':flags,'name_idx':name_idx,'desc_idx':desc_idx,
            'name':utf(cp,name_idx),'descriptor':utf(cp,desc_idx),'raw':raw,
        })
        blocks.append(raw)
    return cp,methods_count_off,mc,rows,blocks,pos

def javap(cls):
    cp=subprocess.run(
        ['javap','-classpath',str(JAR),'-p','-s','-v','-c',cls],
        text=True,capture_output=True
    )
    if cp.returncode:
        raise SystemExit(cp.stderr)
    return cp.stdout

def parse_method_blocks(text):
    lines=text.splitlines()
    blocks=[]
    i=0
    decl_rx=re.compile(r'^  (?:public|protected|private) ')
    while i<len(lines):
        if decl_rx.match(lines[i]):
            j=i+1
            while j<len(lines) and not decl_rx.match(lines[j]) and not lines[j].startswith('  static {};'):
                j+=1
            blocks.append('\n'.join(lines[i:j]))
            i=j
        else:
            i+=1
    return blocks

def parse_decl_name(block):
    first=block.splitlines()[0].strip()
    m=re.search(r'\s([A-Za-z_$][A-Za-z0-9_$]*)\([^)]*\)',first)
    return m.group(1) if m else None

c_text=javap('l1rpb.c')
ab_text=javap(INTERFACE)

ab_methods=set()
for b in parse_method_blocks(ab_text):
    name=parse_decl_name(b)
    dm=re.search(r'^\s*descriptor:\s+(\S+)',b,re.M)
    if name and dm:
        ab_methods.add((name,dm.group(1)))

bridge_map=[]
for b in parse_method_blocks(c_text):
    name=parse_decl_name(b)
    dm=re.search(r'^\s*descriptor:\s+(\S+)',b,re.M)
    fm=re.search(r'^\s*flags:.*ACC_SYNTHETIC',b,re.M)
    if not (name and dm and fm):
        continue
    desc=dm.group(1)
    if (name,desc) not in ab_methods:
        continue
    # Every selected donor bridge is a trivial invokevirtual delegate to the
    # typed provider. Capture its exact target name+descriptor from javap.
    inv=re.findall(r'// Method ([A-Za-z0-9_$]+):(\S+)',b)
    typed=[x for x in inv if x[1].endswith('Ll1rpb/y;')]
    if len(typed)!=1:
        raise SystemExit(f'expected one typed y-return delegate for {name}{desc}, got {typed}')
    target_name,target_desc=typed[0]
    bridge_map.append({
        'interface_name':name,
        'bridge_descriptor':desc,
        'typed_provider_name':target_name,
        'typed_provider_descriptor':target_desc,
    })

if len(bridge_map)<20:
    raise SystemExit(f'expected broad parser bridge mapping, got only {len(bridge_map)}')

with zipfile.ZipFile(JAR,'r') as zin:
    raw_map={n:zin.read(n) for n in zin.namelist()}
raw=raw_map[TARGET_CLASS]
cp,methods_count_off,mc,rows,blocks,methods_end=method_inventory(raw)
utf_indices={}
for i,e in enumerate(cp):
    if e and e[0]==1:
        utf_indices.setdefault(e[1].decode('utf-8','replace'),i)

inventory={(x['name'],x['descriptor']):x for x in rows}
aliases=[]
alias_blocks=[]
seen=set()
for m in bridge_map:
    src=(m['typed_provider_name'],m['typed_provider_descriptor'])
    alias=(m['interface_name'],m['typed_provider_descriptor'])
    if src not in inventory:
        raise SystemExit(f'missing typed provider {src} for bridge {m}')
    if alias in inventory:
        # Already source-visible typed provider; no alias needed.
        continue
    if alias in seen:
        continue
    seen.add(alias)
    name_idx=utf_indices.get(m['interface_name'])
    if not name_idx:
        raise SystemExit(f'missing Utf8 for alias name {m["interface_name"]}')
    dup=bytearray(inventory[src]['raw'])
    struct.pack_into('>H',dup,2,name_idx)
    alias_blocks.append(bytes(dup))
    aliases.append({
        **m,
        'alias_name':m['interface_name'],
        'alias_descriptor':m['typed_provider_descriptor'],
        'typed_provider_preserved':True,
        'code_and_signature_copied_exactly':True,
    })

if len(aliases)<20:
    raise SystemExit(f'expected >=20 missing typed aliases, got {len(aliases)}')

patched=bytearray()
patched+=raw[:methods_count_off]
patched+=struct.pack('>H',mc+len(alias_blocks))
for b in blocks: patched+=b
for b in alias_blocks: patched+=b
patched+=raw[methods_end:]

with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        payload=bytes(patched) if info.filename==TARGET_CLASS else zin.read(info.filename)
        zout.writestr(info,payload)
TMP.replace(JAR)

state={
 'experiment':'PARSER_AB_INTERFACE_TYPED_PROVIDER_RECONSTRUCTION',
 'class':TARGET_CLASS,
 'interface':INTERFACE,
 'abstract_interface_method_count':len(ab_methods),
 'matched_synthetic_bridge_count':len(bridge_map),
 'added_typed_alias_count':len(aliases),
 'bridge_map':bridge_map,
 'aliases':aliases,
 'selection_rule':'exact ab name+erased descriptor -> c synthetic bridge -> single invokevirtual typed y-return provider',
 'purpose':'reconstruct Java-source-visible typed provider + erased synthetic bridge pairs after obfuscator renamed typed providers',
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'existing_method_names_changed':False,
 'existing_method_descriptors_changed':False,
 'existing_bytecode_changed':False,
 'gameplay_logic_changed':False,
 'pass_signal':'all parser abstract obligations disappear; nonprotobuf remains 0',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Protobuf Parser Typed Provider Reconstruction\n\n'
 + f'- Interface methods discovered: **{len(ab_methods)}**\n'
 + f'- Synthetic bridges matched: **{len(bridge_map)}**\n'
 + f'- Typed aliases added: **{len(aliases)}**\n'
 + '- Rule: exact interface bridge + donor bytecode single typed y-return delegate.\n'
 + '- Existing donor method names/descriptors/bytecode: unchanged.\n'
 + '- Scope: recovery compile-ref only.\n'
 + '- Recovered source/gameplay: unchanged.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
