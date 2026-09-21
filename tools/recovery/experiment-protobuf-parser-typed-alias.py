#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.parsertypedalias.jar')
OUT=Path('recovery/protobuf_parser_typed_alias_experiment.json')
MD=Path('recovery/PROTOBUF_PARSER_TYPED_ALIAS_EXPERIMENT.md')

TARGET_CLASS='l1rpb/c.class'
SOURCES={
    ('d','(Ljava/io/InputStream;)Ll1rpb/y;'):'f',
    ('d','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/y;'):'f',
}

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
        tag=r.u1(); start=r.p-1
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
    # access,name,descriptor,attributes_count + attributes
    p=pos+6
    return skip_attrs(data,p)

def patch(data):
    cp_end,cp=parse_cp(data)
    f_name_indices=[i for i,e in enumerate(cp) if e and e[0]==1 and e[1]==b'f']
    if not f_name_indices:
        raise SystemExit('Utf8 constant f not found')
    f_idx=f_name_indices[0]

    pos=cp_end
    pos+=6
    ic=struct.unpack_from('>H',data,pos)[0]; pos+=2+2*ic

    fc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    for _ in range(fc):
        pos=skip_attrs(data,pos+6)

    methods_count_off=pos
    mc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    method_blocks=[]
    found={}
    existing=set()
    for _ in range(mc):
        st=pos
        flags,name_idx,desc_idx=struct.unpack_from('>HHH',data,pos)
        pos=method_end(data,pos)
        raw=bytearray(data[st:pos])
        name=utf(cp,name_idx); desc=utf(cp,desc_idx)
        existing.add((name,desc))
        if (name,desc) in SOURCES:
            found[(name,desc)]=(raw,flags,name_idx,desc_idx)
        method_blocks.append(bytes(raw))

    if set(found)!=set(SOURCES):
        raise SystemExit(f'missing typed source methods: expected={sorted(SOURCES)} found={sorted(found)}')

    aliases=[]
    for key,new_name in SOURCES.items():
        raw,flags,name_idx,desc_idx=found[key]
        desc=utf(cp,desc_idx)
        if (new_name,desc) in existing:
            raise SystemExit(f'alias already exists: {new_name}{desc}')
        dup=bytearray(raw)
        struct.pack_into('>H',dup,2,f_idx)  # method_info name_index
        aliases.append(bytes(dup))

    out=bytearray()
    out+=data[:methods_count_off]
    out+=struct.pack('>H',mc+len(aliases))
    for b in method_blocks: out+=b
    for b in aliases: out+=b
    out+=data[pos:]

    return bytes(out),[
        {
          'source_name':src[0],
          'source_descriptor':src[1],
          'alias_name':SOURCES[src],
          'alias_descriptor':src[1],
          'existing_source_method_preserved':True,
          'code_and_signature_copied_exactly':True,
        }
        for src in sorted(SOURCES)
    ]

hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename==TARGET_CLASS:
            raw,hits=patch(raw)
        zout.writestr(info,raw)

if len(hits)!=2:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f'expected 2 aliases, got {len(hits)}')
TMP.replace(JAR)

state={
 'experiment':'PARSER_TYPED_F_ALIAS_FROM_D',
 'class':TARGET_CLASS,
 'aliases':hits,
 'purpose':'reconstruct Java-source-visible typed provider + erased synthetic bridge pair after obfuscator renamed typed provider',
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'existing_method_names_changed':False,
 'existing_method_descriptors_changed':False,
 'existing_bytecode_changed':False,
 'added_alias_method_count':2,
 'gameplay_logic_changed':False,
 'pass_signal':'parser 44 f(InputStream[,n]) obligations disappear; nonprotobuf remains 0',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Protobuf Parser Typed Alias Experiment\n\n'
 '- Recovery compile-ref only.\n'
 '- Copy exact typed c.d(InputStream[,n])->MessageType method_info as c.f(InputStream[,n])->MessageType aliases.\n'
 '- Preserve original d methods and existing synthetic f(...)->Object bridges.\n'
 '- Existing donor method names/descriptors/bytecode: unchanged.\n'
 '- Recovered source/gameplay: unchanged.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
