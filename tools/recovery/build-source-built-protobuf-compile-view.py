#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

REC=Path("recovery")
SRC=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
OUT=REC/"protobuf-2.5.0-source-built-compile-view.jar"
STATE=REC/"protobuf_2_5_0_source_built_compile_view.json"
TMP=REC/"protobuf-2.5.0-source-built-compile-view.tmp.jar"

ACC_ABSTRACT=0x0400
ACC_SYNTHETIC=0x1000
TARGET_INNER=b"l1rpb/p$b"
BRIDGE_FLAG_STATE=REC/"protobuf_builder_bridge_flag_experiment.json"
REMOVE={
    "l1rpb/ab.class": {
        ("e","(Ljava/io/InputStream;)Ljava/lang/Object;"),
        ("e","(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;"),
    },
    "l1rpb/y$a.class": {
        ("d","(Ljava/io/InputStream;)Ll1rpb/y$a;"),
        ("d","(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/y$a;"),
    },
    "l1rpb/a$a.class": {
        ("d","()Ll1rpb/a$a;"),
    },
    "l1rpb/b$a.class": {
        ("f","()Ll1rpb/b$a;"),
        ("b","(Ll1rpb/h;Ll1rpb/n;)Ll1rpb/b$a;"),
    },
}

ALIAS_STATE_FILES=[
    "protobuf_parser_typed_alias_experiment.json",
    "protobuf_builder_e_typed_alias_experiment.json",
    "protobuf_builder_current_d_alias_experiment.json",
    "protobuf_builder_current_d_byte_n_alias_experiment.json",
    "protobuf_builder_current_d_byte_ii_alias_experiment.json",
    "protobuf_builder_current_d_byte_alias_experiment.json",
    "protobuf_builder_current_d_g_n_alias_experiment.json",
    "protobuf_builder_current_d_g_alias_experiment.json",
    "protobuf_builder_current_b_ap_alias_experiment.json",
    "protobuf_builder_current_e_ap_alias_experiment.json",
    "protobuf_builder_current_c_kf_obj_alias_experiment.json",
    "protobuf_builder_current_b_kf_int_obj_alias_experiment.json",
    "protobuf_builder_current_f_kf_alias_experiment.json",
    "protobuf_builder_current_d_kf_obj_alias_experiment.json",
    "protobuf_builder_current_d_h_n_alias_experiment.json",
    "protobuf_builder_current_d_h_alias_experiment.json",
    "protobuf_builder_current_i_alias_experiment.json",
    "protobuf_builder_current_c_x_alias_experiment.json",
    "protobuf_builder_current_j_alias_experiment.json",
]

class R:
    def __init__(self,b): self.b=b; self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from(">H",self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from(">I",self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def parse_cp(r):
    if r.u4()!=0xCAFEBABE: raise ValueError("not class")
    r.skip(4)
    count=r.u2(); cp=[None]*count; i=1
    while i<count:
        tag=r.u1()
        if tag==1:
            n=r.u2(); raw=r.b[r.p:r.p+n]; r.skip(n); cp[i]=(1,raw)
        elif tag in (3,4): r.skip(4)
        elif tag in (5,6): r.skip(8); i+=1
        elif tag in (7,8,16,19,20):
            cp[i]=(tag,r.u2())
        elif tag in (9,10,11,12,17,18): r.skip(4)
        elif tag==15: r.skip(3)
        else: raise ValueError(f"unknown cp tag {tag}")
        i+=1
    return cp

def utf(cp,idx):
    if not idx: return None
    e=cp[idx]
    return e[1].decode("utf-8","replace") if e and e[0]==1 else None

def cls(cp,idx):
    if not idx: return None
    e=cp[idx]
    return cp[e[1]][1] if e and e[0]==7 and cp[e[1]] and cp[e[1]][0]==1 else None

def skip_attrs(r):
    for _ in range(r.u2()):
        r.skip(2); r.skip(r.u4())

def skip_member(r):
    r.skip(6); skip_attrs(r)

def remove_abstract_methods(data,wanted):
    r=R(data); cp=parse_cp(r)
    r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()): skip_member(r)
    count_pos=r.p; mc=r.u2()
    kept=[]; removed=[]
    for _ in range(mc):
        st=r.p
        flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        skip_attrs(r)
        raw=data[st:r.p]
        if (name,desc) in wanted:
            if not (flags & ACC_ABSTRACT):
                raise SystemExit(f"compile-view target is not abstract: {name}{desc} flags={flags:#x}")
            removed.append((name,desc,flags))
        else:
            kept.append(raw)
    out=bytearray(data[:count_pos])
    out+=struct.pack(">H",len(kept))
    for raw in kept: out+=raw
    out+=data[r.p:]
    return bytes(out),removed

def load_bridge_flag_targets():
    if not BRIDGE_FLAG_STATE.exists():
        raise SystemExit(f"missing bridge flag evidence: {BRIDGE_FLAG_STATE}")
    s=json.loads(BRIDGE_FLAG_STATE.read_text(encoding="utf-8"))
    if s.get("provider_class")!="l1rpb/b$a.class":
        raise SystemExit(f"unexpected bridge provider: {s.get('provider_class')}")
    targets={(x["name"],x["descriptor"]) for x in s.get("targets",[])}
    if len(targets)!=9:
        raise SystemExit(f"expected 9 proven builder bridge targets, got {len(targets)}")
    return targets

def clear_synthetic_bridge_flags(data,wanted):
    r=R(data); cp=parse_cp(r)
    r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()): skip_member(r)
    mc=r.u2()
    out=bytearray(data); hits=[]
    for _ in range(mc):
        flags_pos=r.p
        flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        skip_attrs(r)
        if (name,desc) in wanted:
            if flags & ACC_ABSTRACT:
                raise SystemExit(f"bridge provider unexpectedly abstract: {name}{desc}")
            if not (flags & ACC_SYNTHETIC):
                raise SystemExit(f"bridge provider missing ACC_SYNTHETIC before patch: {name}{desc} flags={flags:#x}")
            new_flags=flags & ~ACC_SYNTHETIC
            struct.pack_into(">H",out,flags_pos,new_flags)
            hits.append({"name":name,"descriptor":desc,"old_flags":flags,"new_flags":new_flags})
    missing=wanted-{(x["name"],x["descriptor"]) for x in hits}
    if missing:
        raise SystemExit(f"missing proven bridge providers: {sorted(missing)}")
    return bytes(out),hits

def widen_inner_visibility(data):
    r=R(data); cp=parse_cp(r)
    pos=r.p
    pos+=6
    ic=struct.unpack_from(">H",data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    rr=R(data); rr.p=pos
    for _ in range(fc): skip_member(rr)
    pos=rr.p
    mc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    rr.p=pos
    for _ in range(mc): skip_member(rr)
    pos=rr.p
    ac=struct.unpack_from(">H",data,pos)[0]; pos+=2
    out=bytearray(data); changed=0
    for _ in range(ac):
        name_i=struct.unpack_from(">H",data,pos)[0]
        ln=struct.unpack_from(">I",data,pos+2)[0]
        payload=pos+6
        if utf(cp,name_i)=="InnerClasses":
            n=struct.unpack_from(">H",data,payload)[0]
            q=payload+2
            for __ in range(n):
                inner_i,outer_i,inner_name_i,flags=struct.unpack_from(">HHHH",data,q)
                if cls(cp,inner_i)==TARGET_INNER:
                    new_flags=(flags & ~0x0004) | 0x0001
                    if new_flags!=flags:
                        struct.pack_into(">H",out,q+6,new_flags)
                        changed+=1
                q+=8
        pos+=6+ln
    return bytes(out),changed

def descriptor_params(desc):
    if not desc or not desc.startswith("("): return None
    return desc[:desc.index(")")+1]

def read_methods(data):
    r=R(data); cp=parse_cp(r)
    r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()): skip_member(r)
    rows=[]
    for _ in range(r.u2()):
        flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        skip_attrs(r)
        rows.append({"name":name,"descriptor":desc,"flags":flags,"abstract":bool(flags & ACC_ABSTRACT)})
    return rows

def strip_signature_attrs(data):
    r=R(data); cp=parse_cp(r)
    cp_end=r.p
    pos=cp_end
    out=bytearray(data[:cp_end])

    out+=data[pos:pos+6]; pos+=6
    ic=struct.unpack_from(">H",data,pos)[0]
    out+=data[pos:pos+2+2*ic]; pos+=2+2*ic

    def copy_member_table(pos,count):
        nonlocal out
        removed=0
        for _ in range(count):
            out+=data[pos:pos+6]; pos+=6
            ac=struct.unpack_from(">H",data,pos)[0]; pos+=2
            kept=[]
            for __ in range(ac):
                st=pos
                ni=struct.unpack_from(">H",data,pos)[0]
                ln=struct.unpack_from(">I",data,pos+2)[0]
                pos+=6+ln
                if utf(cp,ni)=="Signature":
                    removed+=1
                else:
                    kept.append(data[st:pos])
            out+=struct.pack(">H",len(kept))
            for raw in kept: out+=raw
        return pos,removed

    fc=struct.unpack_from(">H",data,pos)[0]
    out+=data[pos:pos+2]; pos+=2
    pos,field_removed=copy_member_table(pos,fc)

    mc=struct.unpack_from(">H",data,pos)[0]
    out+=data[pos:pos+2]; pos+=2
    pos,method_removed=copy_member_table(pos,mc)

    ac=struct.unpack_from(">H",data,pos)[0]; pos+=2
    kept=[]; class_removed=0
    for _ in range(ac):
        st=pos
        ni=struct.unpack_from(">H",data,pos)[0]
        ln=struct.unpack_from(">I",data,pos+2)[0]
        pos+=6+ln
        # Keep the class Signature so c<MessageType> remains a generic type.
        kept.append(data[st:pos])
    out+=struct.pack(">H",len(kept))
    for raw in kept: out+=raw
    if pos!=len(data): raise SystemExit(f"signature strip parse ended {pos}/{len(data)}")
    return bytes(out),class_removed,method_removed,field_removed

def method_inventory(data):
    r=R(data); cp=parse_cp(r)
    cp_end=r.p
    pos=cp_end+6
    ic=struct.unpack_from(">H",data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    for _ in range(fc):
        pos+=6
        rr=R(data); rr.p=pos
        skip_attrs(rr); pos=rr.p
    methods_count_off=pos
    mc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    rows=[]; blocks=[]
    for _ in range(mc):
        st=pos
        flags,name_i,desc_i=struct.unpack_from(">HHH",data,pos); pos+=6
        rr=R(data); rr.p=pos
        skip_attrs(rr); pos=rr.p
        raw=data[st:pos]
        rows.append({"flags":flags,"name_i":name_i,"desc_i":desc_i,
                     "name":utf(cp,name_i),"descriptor":utf(cp,desc_i),"raw":raw})
        blocks.append(raw)
    return cp,methods_count_off,mc,rows,blocks,pos

def add_typed_aliases(data,specs):
    cp,count_off,mc,rows,blocks,methods_end=method_inventory(data)
    utf_indices={}
    for i,e in enumerate(cp):
        if e and e[0]==1:
            utf_indices.setdefault(e[1].decode("utf-8","replace"),i)
    inv={(x["name"],x["descriptor"]):x for x in rows}
    added=[]
    alias_blocks=[]
    seen=set()
    for spec in specs:
        alias=(spec["alias_name"],spec["alias_descriptor"])
        provider=(spec["typed_provider_name"],spec["typed_provider_descriptor"])
        if alias in seen: raise SystemExit(f"duplicate alias spec {alias}")
        seen.add(alias)
        if alias in inv: raise SystemExit(f"alias already exists before compile-view patch: {alias}")
        src=inv.get(provider)
        if src is None: raise SystemExit(f"typed provider missing for alias {alias}: {provider}")
        ni=utf_indices.get(spec["alias_name"])
        if not ni: raise SystemExit(f"alias name Utf8 missing: {spec['alias_name']}")
        dup=bytearray(src["raw"])
        struct.pack_into(">H",dup,2,ni)
        alias_blocks.append(bytes(dup))
        added.append({
            "alias_name":spec["alias_name"],"alias_descriptor":spec["alias_descriptor"],
            "typed_provider_name":spec["typed_provider_name"],
            "typed_provider_descriptor":spec["typed_provider_descriptor"],
        })
    out=bytearray()
    out+=data[:count_off]
    out+=struct.pack(">H",mc+len(alias_blocks))
    for raw in blocks: out+=raw
    for raw in alias_blocks: out+=raw
    out+=data[methods_end:]
    return bytes(out),added

def load_alias_specs():
    by_owner={}
    total=0
    for fn in ALIAS_STATE_FILES:
        p=REC/fn
        if not p.exists(): raise SystemExit(f"missing proven alias state: {p}")
        s=json.loads(p.read_text(encoding="utf-8"))
        owner=s.get("class") or s.get("target_class")
        if owner not in ("l1rpb/c.class","l1rpb/a$a.class","l1rpb/p$a.class"):
            raise SystemExit(f"unexpected alias owner {owner} in {p}")
        aa=s.get("aliases")
        if aa is None:
            a=s.get("alias"); t=s.get("typed_provider")
            if not a or not t: raise SystemExit(f"missing alias/provider in {p}")
            aa=[{"alias_name":a["name"],"alias_descriptor":a["descriptor"],
                 "typed_provider_name":t["name"],"typed_provider_descriptor":t["descriptor"]}]
        for a in aa:
            by_owner.setdefault(owner,[]).append({
                "alias_name":a["alias_name"],"alias_descriptor":a["alias_descriptor"],
                "typed_provider_name":a["typed_provider_name"],
                "typed_provider_descriptor":a["typed_provider_descriptor"],
                "state":fn,
            })
            total+=1
    if total!=40: raise SystemExit(f"expected 40 proven typed aliases, got {total}")
    return by_owner,total

if not SRC.exists(): raise SystemExit(f"missing exact source-built ABI jar: {SRC}")
removed=[]
visibility=0
alias_specs_by_owner,expected_alias_count=load_alias_specs()
typed_aliases_added=[]
bridge_flag_targets=load_bridge_flag_targets()
bridge_flags_cleared=[]
with zipfile.ZipFile(SRC,"r") as zin:
    raw_map={name:zin.read(name) for name in zin.namelist() if name.endswith(".class")}
    class_count=len(raw_map)
    if class_count!=246: raise SystemExit(f"exact source-built jar must contain 246 classes, got {class_count}")

targets={k:set(v) for k,v in REMOVE.items()}
pair_details=[]

with zipfile.ZipFile(SRC,"r") as zin, zipfile.ZipFile(TMP,"w",zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename.endswith(".class"):
            raw,n=widen_inner_visibility(raw)
            visibility+=n
            if info.filename in targets:
                raw,hits=remove_abstract_methods(raw,targets[info.filename])
                removed += [{"class":info.filename,"name":n,"descriptor":d,"flags":f} for n,d,f in hits]
            if info.filename=="l1rpb/b$a.class":
                raw,hits=clear_synthetic_bridge_flags(raw,bridge_flag_targets)
                bridge_flags_cleared += [{"class":info.filename,**x} for x in hits]
            if info.filename in alias_specs_by_owner:
                raw,added=add_typed_aliases(raw,alias_specs_by_owner[info.filename])
                typed_aliases_added += [{"class":info.filename,**x} for x in added]
        zout.writestr(info,raw)

expected_removed=sum(len(v) for v in targets.values())
if len(removed)!=expected_removed:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f"expected {expected_removed} compile-only obligations removed, got {len(removed)}")
if visibility==0:
    TMP.unlink(missing_ok=True)
    raise SystemExit("expected at least one p$b InnerClasses visibility patch")
if len(typed_aliases_added)!=expected_alias_count:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f"expected {expected_alias_count} typed aliases added, got {len(typed_aliases_added)}")
if len(bridge_flags_cleared)!=9:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f"expected 9 proven builder bridge synthetic flags cleared, got {len(bridge_flags_cleared)}")
TMP.replace(OUT)

state={
    "gate":"SOURCE_BUILT_PROTOBUF_COMPILE_VIEW",
    "source_exact_abi_jar":SRC.as_posix(),
    "output_compile_view":OUT.as_posix(),
    "source_built_classes":246,
    "abstract_obligations_removed":removed,
    "proven_typed_alias_count":len(typed_aliases_added),
    "typed_aliases_added":typed_aliases_added,
    "builder_bridge_synthetic_flags_cleared":bridge_flags_cleared,
    "builder_bridge_synthetic_flag_clear_count":len(bridge_flags_cleared),
    "abstract_obligation_count":len(removed),
    "inner_visibility_target":"l1rpb/p$b",
    "inner_visibility_entries_widened":visibility,
    "runtime_exact_abi_jar_modified":False,
    "donor_binary_used":False,
    "method_bytecode_changed":False,
    "gameplay_logic_changed":False,
    "scope":"javac compile view only; 40 proven typed aliases + 9 proven builder bridge synthetic-flag clears + targeted obligations/visibility; runtime/linkage validation remains against exact source-built donor ABI",
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
