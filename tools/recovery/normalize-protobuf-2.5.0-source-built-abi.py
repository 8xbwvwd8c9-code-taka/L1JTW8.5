#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

BUILD=Path("recovery/protobuf-2.5.0-official-build-jdk8-target5")
CLASSMAP=Path("recovery/protobuf_2_5_0_order_mapping.json")
MEMBERMAP=Path("recovery/protobuf_2_5_0_member_mapping_v3.json")
OUTJAR=Path("recovery/protobuf-2.5.0-source-built-donor-abi.jar")
STATE=Path("recovery/protobuf_2_5_0_source_built_donor_abi.json")

if not BUILD.exists(): raise SystemExit(f"missing {BUILD}")
cm=json.loads(CLASSMAP.read_text(encoding="utf-8"))["mapping"]
cm_rev={v:k for k,v in cm.items()}
mm=json.loads(MEMBERMAP.read_text(encoding="utf-8"))["rows"]
if len(cm_rev)!=246: raise SystemExit(f"class map expected 246, got {len(cm_rev)}")
if len(mm)!=5999: raise SystemExit(f"member map expected 5999, got {len(mm)}")

# official owner -> {(official name, official desc, kind): (donor name, donor desc)}
member_rev={}
for r in mm:
    k=(r["official_class"],r["official_name"],r["official_desc"],r["kind"])
    v=(r["donor_name"],r["donor_desc"])
    if k in member_rev and member_rev[k]!=v:
        raise SystemExit(f"non-unique member reverse map: {k}")
    member_rev[k]=v

def rewrite_desc(s):
    if not s: return s
    # longest first protects nested names
    out=s
    for old,new in sorted(cm_rev.items(), key=lambda kv: len(kv[0]), reverse=True):
        out=out.replace("L"+old+";","L"+new+";")
        out=out.replace("L"+old+"<","L"+new+"<")
        # internal-name occurrences in signatures/attributes
        out=out.replace(old,new)
    return out

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

class CP:
    def __init__(self,data):
        self.data=data
        self.minor=struct.unpack_from(">H",data,4)[0]
        self.major=struct.unpack_from(">H",data,6)[0]
        self.count=struct.unpack_from(">H",data,8)[0]
        self.entries=[None]
        p=10; i=1
        while i<self.count:
            start=p; tag=data[p]; p+=1
            if tag==1:
                n=struct.unpack_from(">H",data,p)[0]; p+=2
                raw=data[p:p+n]; p+=n
                self.entries.append([tag,raw.decode("utf-8","replace")])
            elif tag in (3,4):
                self.entries.append([tag,data[p:p+4]]); p+=4
            elif tag in (5,6):
                self.entries.append([tag,data[p:p+8]]); p+=8
                self.entries.append(None); i+=1
            elif tag in (7,8,16,19,20):
                x=struct.unpack_from(">H",data,p)[0]; p+=2
                self.entries.append([tag,x])
            elif tag in (9,10,11,12,17,18):
                a,b=struct.unpack_from(">HH",data,p); p+=4
                self.entries.append([tag,a,b])
            elif tag==15:
                a=data[p]; b=struct.unpack_from(">H",data,p+1)[0]; p+=3
                self.entries.append([tag,a,b])
            else: raise ValueError(f"cp tag {tag}")
            i+=1
        self.rest=bytearray(data[p:])
        self.utf_cache={}
        self.nt_cache={}
        for idx,e in enumerate(self.entries):
            if e and e[0]==1: self.utf_cache.setdefault(e[1],idx)
            if e and e[0]==12: self.nt_cache.setdefault((e[1],e[2]),idx)

    def utf(self,idx):
        e=self.entries[idx]
        return e[1] if e and e[0]==1 else None
    def class_name(self,idx):
        e=self.entries[idx]
        return self.utf(e[1]) if e and e[0]==7 else None
    def add_utf(self,s):
        if s in self.utf_cache: return self.utf_cache[s]
        idx=len(self.entries); self.entries.append([1,s]); self.utf_cache[s]=idx
        return idx
    def add_nt(self,name,desc):
        ni=self.add_utf(name); di=self.add_utf(desc)
        key=(ni,di)
        if key in self.nt_cache: return self.nt_cache[key]
        idx=len(self.entries); self.entries.append([12,ni,di]); self.nt_cache[key]=idx
        return idx
    def encode(self):
        out=bytearray(b"\xca\xfe\xba\xbe")
        out+=struct.pack(">HHH",self.minor,self.major,len(self.entries))
        i=1
        while i<len(self.entries):
            e=self.entries[i]
            if e is None:
                i+=1; continue
            tag=e[0]; out.append(tag)
            if tag==1:
                raw=e[1].encode("utf-8"); out+=struct.pack(">H",len(raw))+raw
            elif tag in (3,4): out+=e[1]
            elif tag in (5,6): out+=e[1]
            elif tag in (7,8,16,19,20): out+=struct.pack(">H",e[1])
            elif tag in (9,10,11,12,17,18): out+=struct.pack(">HH",e[1],e[2])
            elif tag==15: out+=bytes([e[1]])+struct.pack(">H",e[2])
            i+=1
        out+=self.rest
        return bytes(out)

def rewrite_class(data, official_name):
    global inherited_or_external_memberrefs
    cp=CP(data)

    # First rewrite class-name/descriptors/signatures in all Utf8. Member names are NOT globally rewritten.
    for e in cp.entries:
        if e and e[0]==1:
            e[1]=rewrite_desc(e[1])

    # Need current class owner after rewrite.
    rest=cp.rest
    rp=0
    access=struct.unpack_from(">H",rest,rp)[0]; rp+=2
    this_i=struct.unpack_from(">H",rest,rp)[0]; rp+=2
    super_i=struct.unpack_from(">H",rest,rp)[0]; rp+=2
    donor_owner=cm_rev[official_name]

    # interfaces
    ic=struct.unpack_from(">H",rest,rp)[0]; rp+=2+2*ic

    # helper to patch declaration name/desc indices in cp.rest
    def patch_members(kind,rp):
        count=struct.unpack_from(">H",rest,rp)[0]; rp+=2
        for _ in range(count):
            flags_off=rp
            flags,name_i,desc_i=struct.unpack_from(">HHH",rest,rp); rp+=6
            official_member_name=cp.utf(name_i)
            official_desc_before=None
            # Official desc has already been rewritten globally in Utf8, so derive official descriptor from member map candidates
            # by matching owner/name and translated donor descriptor.
            current_desc=cp.utf(desc_i)
            candidates=[]
            for (oo,on,od,k),(dn,dd) in member_rev.items():
                if oo==official_name and on==official_member_name and k==kind and rewrite_desc(od)==current_desc:
                    candidates.append((od,dn,dd))
            if len(candidates)!=1:
                raise SystemExit(f"declaration mapping {official_name} {kind} {official_member_name} {current_desc}: {len(candidates)}")
            od,dn,dd=candidates[0]
            new_name_i=cp.add_utf(dn)
            new_desc_i=cp.add_utf(dd)
            struct.pack_into(">HH",rest,flags_off+2,new_name_i,new_desc_i)

            ac=struct.unpack_from(">H",rest,rp)[0]; rp+=2
            for __ in range(ac):
                rp+=2
                ln=struct.unpack_from(">I",rest,rp)[0]; rp+=4+ln
        return rp

    rp=patch_members("field",rp)
    rp=patch_members("method",rp)

    # Rewrite CONSTANT member refs owner-specifically. Class refs already point to rewritten class-name Utf8.
    for e in cp.entries:
        if not e or e[0] not in (9,10,11): continue
        tag,ci,nti=e
        owner=cp.class_name(ci)
        nt=cp.entries[nti]
        if not nt or nt[0]!=12: continue
        name=cp.utf(nt[1]); desc=cp.utf(nt[2])
        kind="field" if tag==9 else "method"
        # owner is donor name now; reverse to official owner
        official_owner=cm.get(owner)
        if not official_owner: continue

        matches=[]
        for (oo,on,od,k),(dn,dd) in member_rev.items():
            if oo==official_owner and k==kind and rewrite_desc(od)==desc and on==name:
                matches.append((dn,dd))
        if len(matches)==1:
            dn,dd=matches[0]
            e[2]=cp.add_nt(dn,dd)
        elif len(matches)==0:
            # No direct declaration on this protobuf owner. JVM refs can use a subtype owner
            # for inherited JDK/protobuf members. Preserve the member name; class/descriptors
            # are already translated. A later linkage validator audits these references.
            inherited_or_external_memberrefs += 1
        else:
            raise SystemExit(f"memberref mapping ambiguous {official_owner} {kind} {name}{desc}: {len(matches)}")

    # Rebuild UTF caches after mutations not necessary for encoding.
    out=cp.encode()
    return out, donor_owner

classes=0
inherited_or_external_memberrefs=0
with zipfile.ZipFile(OUTJAR,"w",zipfile.ZIP_DEFLATED) as zout:
    for p in sorted(BUILD.rglob("*.class")):
        official=p.relative_to(BUILD).as_posix()[:-6]
        if official not in cm_rev:
            raise SystemExit(f"official class missing mapping: {official}")
        raw=p.read_bytes()
        new,donor=rewrite_class(raw,official)
        zout.writestr(donor+".class",new)
        classes+=1

state={"gate":"PROTOBUF_2_5_0_SOURCE_BUILT_DONOR_ABI",
       "official_build":BUILD.as_posix(),"output":OUTJAR.as_posix(),
       "class_map":len(cm_rev),"member_map":len(mm),"written_classes":classes,
       "inherited_or_external_memberrefs_left_named":inherited_or_external_memberrefs,
       "donor_binary_used_as_runtime_output":False,
       "gameplay_logic_changed":False}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
