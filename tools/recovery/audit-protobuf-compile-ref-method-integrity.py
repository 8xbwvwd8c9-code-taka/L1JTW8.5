#!/usr/bin/env python3
import argparse, hashlib, json, struct, zipfile
from collections import Counter, defaultdict
from pathlib import Path

JAR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
BASE=Path("recovery/protobuf_compile_ref_method_baseline.json")
OUT=Path("recovery/protobuf_compile_ref_method_integrity.json")
MD=Path("recovery/PROTOBUF_COMPILE_REF_METHOD_INTEGRITY.md")
OWNERS=("l1rpb/c.class","l1rpb/a$a.class","l1rpb/p$a.class")
EXPECTED_ALIASES=40
STATES=(
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
)

class R:
    def __init__(self,b): self.b=b; self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from(">H",self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from(">I",self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def cp_read(r):
    if r.u4()!=0xCAFEBABE: raise SystemExit("not class")
    r.skip(4); n=r.u2(); cp=[None]*n; i=1
    while i<n:
        t=r.u1()
        if t==1:
            z=r.u2(); cp[i]=(t,r.b[r.p:r.p+z]); r.skip(z)
        elif t in (3,4): cp[i]=(t,None); r.skip(4)
        elif t in (5,6): cp[i]=(t,None); r.skip(8); i+=1
        elif t in (7,8,16,19,20): cp[i]=(t,r.u2())
        elif t in (9,10,11,12,17,18): cp[i]=(t,None); r.skip(4)
        elif t==15: cp[i]=(t,None); r.skip(3)
        else: raise SystemExit(f"unknown cp tag {t}")
        i+=1
    return cp

def utf(cp,i):
    e=cp[i]
    return e[1].decode("utf-8","replace") if e and e[0]==1 else None

def cname(cp,i):
    e=cp[i] if i else None
    return utf(cp,e[1]) if e and e[0]==7 else None

def skip_attrs(r):
    for _ in range(r.u2()):
        r.skip(2); r.skip(r.u4())

def attrs(cp,raw):
    r=R(raw); r.skip(6); out=[]
    for _ in range(r.u2()):
        ni=r.u2(); ln=r.u4(); p=r.b[r.p:r.p+ln]; r.skip(ln); n=utf(cp,ni)
        x={"name":n,"length":ln}
        if n=="Signature" and ln==2: x["value"]=utf(cp,struct.unpack_from(">H",p,0)[0])
        if n=="Exceptions" and ln>=2:
            c=struct.unpack_from(">H",p,0)[0]; q=2; v=[]
            for _ in range(c):
                v.append(cname(cp,struct.unpack_from(">H",p,q)[0])); q+=2
            x["value"]=v
        out.append(x)
    return out

def methods(data):
    r=R(data); cp=cp_read(r); r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()): r.skip(6); skip_attrs(r)
    rows=[]
    for _ in range(r.u2()):
        st=r.p; fl=r.u2(); ni=r.u2(); di=r.u2()
        for _ in range(r.u2()): r.skip(2); r.skip(r.u4())
        raw=bytes(data[st:r.p])
        rows.append({"name":utf(cp,ni),"descriptor":utf(cp,di),"flags":fl,"raw":raw,
                     "sha256":hashlib.sha256(raw).hexdigest(),"attributes":attrs(cp,raw)})
    return rows

def invjar():
    if not JAR.exists(): raise SystemExit(f"missing {JAR}")
    out={}
    with zipfile.ZipFile(JAR) as z:
        names=set(z.namelist())
        for o in OWNERS:
            if o not in names: raise SystemExit(f"missing {o}")
            out[o]=methods(z.read(o))
    return out

def key(r): return (r["name"],r["descriptor"])
def pdesc(d): return d[:d.index(")")+1]
def rdesc(d): return d[d.index(")")+1:]

def snapshot():
    inv=invjar()
    s={"gate":"PROTOBUF_COMPILE_REF_METHOD_BASELINE","phase":"before_active_typed_alias_mutations","owners":{}}
    for o,rs in inv.items():
        s["owners"][o]={"method_count":len(rs),"methods":[{"name":x["name"],"descriptor":x["descriptor"],"sha256":x["sha256"]} for x in rs]}
    BASE.write_text(json.dumps(s,indent=2)+"\n",encoding="utf-8")
    print(json.dumps(s,indent=2))

def load_aliases():
    out=[]
    for fn in STATES:
        p=Path("recovery")/fn
        if not p.exists(): raise SystemExit(f"missing state {p}")
        s=json.loads(p.read_text(encoding="utf-8")); o=s.get("class") or s.get("target_class")
        if o not in OWNERS: raise SystemExit(f"unexpected owner {o} in {p}")
        aa=s.get("aliases")
        if aa is None:
            a=s.get("alias"); t=s.get("typed_provider")
            if not a or not t: raise SystemExit(f"missing alias/provider in {p}")
            aa=[{"alias_name":a["name"],"alias_descriptor":a["descriptor"],
                 "typed_provider_name":t["name"],"typed_provider_descriptor":t["descriptor"]}]
        for a in aa:
            out.append({"state":p.as_posix(),"experiment":s.get("experiment"),"owner":o,
                        "name":a["alias_name"],"descriptor":a["alias_descriptor"],
                        "provider_name":a["typed_provider_name"],"provider_descriptor":a["typed_provider_descriptor"]})
    return out

def audit():
    if not BASE.exists(): raise SystemExit(f"missing {BASE}")
    base=json.loads(BASE.read_text(encoding="utf-8")); final=invjar(); aliases=load_aliases()
    akeys=[(a["owner"],a["name"],a["descriptor"]) for a in aliases]; aset=set(akeys)
    adup=[{"owner":k[0],"name":k[1],"descriptor":k[2],"count":v} for k,v in Counter(akeys).items() if v>1]
    findex={}; dup=[]
    for o,rs in final.items():
        c=Counter(key(x) for x in rs)
        dup += [{"owner":o,"name":k[0],"descriptor":k[1],"count":v} for k,v in c.items() if v>1]
        findex[o]={key(x):x for x in rs}

    changed=[]; missing_existing=[]; unexpected=[]; added=[]
    for o in OWNERS:
        bm={ (x["name"],x["descriptor"]):x for x in base["owners"][o]["methods"] }
        fm=findex[o]
        for k,b in bm.items():
            f=fm.get(k)
            if not f: missing_existing.append({"owner":o,"name":k[0],"descriptor":k[1]})
            elif f["sha256"]!=b["sha256"]:
                changed.append({"owner":o,"name":k[0],"descriptor":k[1],"before":b["sha256"],"after":f["sha256"]})
        for k in fm:
            if k not in bm:
                q=(o,k[0],k[1]); added.append(q)
                if q not in aset: unexpected.append({"owner":o,"name":k[0],"descriptor":k[1]})

    groups=[]; cov=set()
    for o,rs in final.items():
        g=defaultdict(list)
        for x in rs: g[(x["name"],pdesc(x["descriptor"]))].append(x)
        for (n,p),xs in g.items():
            rets=sorted({rdesc(x["descriptor"]) for x in xs})
            if len(rets)>1:
                cov.add((o,n,p)); groups.append({"owner":o,"name":n,"parameter_descriptor":p,
                    "returns":rets,"descriptors":sorted(x["descriptor"] for x in xs)})

    missing_alias=[]; copy_bad=[]; no_cov=[]; details=[]
    for a in aliases:
        o=a["owner"]; x=findex[o].get((a["name"],a["descriptor"]))
        if not x: missing_alias.append(a); continue
        t=findex[o].get((a["provider_name"],a["provider_descriptor"]))
        if not t: copy_bad.append({**a,"reason":"provider_missing"}); continue
        safe=x["raw"][:2]==t["raw"][:2] and x["raw"][4:]==t["raw"][4:]
        if not safe: copy_bad.append({**a,"reason":"method_info_diff_beyond_name_index"})
        cv=(o,a["name"],pdesc(a["descriptor"])) in cov
        if not cv: no_cov.append(a)
        sig=[z.get("value") for z in x["attributes"] if z["name"]=="Signature"]
        exc=[z.get("value") for z in x["attributes"] if z["name"]=="Exceptions"]
        details.append({**a,"method_info_copy_safe":safe,"signature":sig[0] if len(sig)==1 else None,
                        "exceptions":exc[0] if len(exc)==1 else [],"same_params_different_return":cv})

    added_alias=sum(1 for q in added if q in aset)
    checks={
      "active_alias_count_exact":len(aliases)==EXPECTED_ALIASES,
      "alias_spec_duplicates_zero":not adup,
      "duplicate_name_descriptor_zero":not dup,
      "missing_existing_zero":not missing_existing,
      "existing_method_bytes_changed_zero":not changed,
      "unexpected_non_alias_added_zero":not unexpected,
      "added_alias_count_exact":added_alias==EXPECTED_ALIASES,
      "missing_aliases_zero":not missing_alias,
      "method_info_copy_mismatches_zero":not copy_bad,
      "all_aliases_have_covariant_sibling":not no_cov,
    }
    ok=all(checks.values())
    s={"gate":"POST_JAVAC0_COMPILE_REF_METHOD_INTEGRITY","status":"PASS" if ok else "FAIL",
       "owners":list(OWNERS),"expected_active_typed_alias_count":EXPECTED_ALIASES,
       "active_typed_alias_count":len(aliases),"added_alias_method_count":added_alias,
       "duplicate_name_descriptor_count":len(dup),"existing_method_bytes_changed":len(changed),
       "unexpected_non_alias_added_count":len(unexpected),"missing_alias_count":len(missing_alias),
       "method_info_copy_mismatch_count":len(copy_bad),"alias_without_covariant_sibling_count":len(no_cov),
       "same_name_same_params_different_return_group_count":len(groups),"checks":checks,
       "baseline_method_counts":{o:base["owners"][o]["method_count"] for o in OWNERS},
       "final_method_counts":{o:len(final[o]) for o in OWNERS},"aliases":details,
       "covariant_return_groups":groups,"duplicate_name_descriptors":dup,"alias_spec_duplicates":adup,
       "missing_existing_methods":missing_existing,"existing_method_changes":changed,
       "unexpected_non_alias_added":unexpected,"missing_aliases":missing_alias,
       "method_info_copy_mismatches":copy_bad,"aliases_without_covariant_sibling":no_cov,
       "notes":["Scope follows handoff: c.class, a$a.class, p$a.class typed-alias owners.",
                "b$a ACC_SYNTHETIC visibility mutation is separate and excluded from typed-alias count.",
                "Alias method_info must match its typed provider byte-for-byte except name_index."]}
    OUT.write_text(json.dumps(s,indent=2)+"\n",encoding="utf-8")
    lines=["# L1JTW8.5 Protobuf Compile-Ref Method Integrity","",f"Status: **{s['status']}**","",
      "## Core gate","",f"- ACTIVE_TYPED_ALIAS_COUNT: **{len(aliases)} / {EXPECTED_ALIASES}**",
      f"- ADDED_ALIAS_METHOD_COUNT: **{added_alias} / {EXPECTED_ALIASES}**",
      f"- DUPLICATE_NAME_DESCRIPTOR_COUNT: **{len(dup)}**",
      f"- EXISTING_METHOD_BYTES_CHANGED: **{len(changed)}**",
      f"- METHOD_INFO_COPY_MISMATCH_COUNT: **{len(copy_bad)}**",
      f"- MISSING_ALIAS_COUNT: **{len(missing_alias)}**",
      f"- UNEXPECTED_NON_ALIAS_ADDED_COUNT: **{len(unexpected)}**",
      f"- COVARIANT_RETURN_GROUPS: **{len(groups)}**",
      f"- ALIASES_WITHOUT_COVARIANT_SIBLING: **{len(no_cov)}**","",
      "## Owner method counts","","| Owner | Baseline | Final | Delta |","|---|---:|---:|---:|"]
    for o in OWNERS:
        b=base["owners"][o]["method_count"]; f=len(final[o]); lines.append(f"| {o} | {b} | {f} | {f-b:+d} |")
    MD.write_text("\n".join(lines)+"\n",encoding="utf-8")
    print(json.dumps(s,indent=2))
    if not ok: raise SystemExit("compile-ref method integrity gate failed")

if __name__=="__main__":
    ap=argparse.ArgumentParser(); g=ap.add_mutually_exclusive_group(required=True)
    g.add_argument("--snapshot",action="store_true"); g.add_argument("--audit",action="store_true"); a=ap.parse_args()
    snapshot() if a.snapshot else audit()
