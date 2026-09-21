#!/usr/bin/env python3
import json, struct, sys, zipfile
from collections import Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1]) if len(sys.argv)>1 else Path("protobuf-java-2.5.0.jar")
OUT=Path("recovery/protobuf_2_5_0_binary_structural_fingerprint.json")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse(data):
    p=0
    magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad class")
    minor,p=u2(data,p); major,p=u2(data,p)
    cpc,p=u2(data,p); cp=[None]*cpc; i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            n,p=u2(data,p); cp[i]=(tag,data[p:p+n].decode("utf-8","replace")); p+=n
        elif tag in (3,4): cp[i]=(tag,None); p+=4
        elif tag in (5,6): cp[i]=(tag,None); p+=8; i+=1
        elif tag in (7,8,16,19,20):
            x,p=u2(data,p); cp[i]=(tag,x)
        elif tag in (9,10,11,12,17,18):
            a,p=u2(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        elif tag==15:
            a,p=u1(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        else: raise ValueError(f"cp tag {tag}")
        i+=1
    def utf(idx):
        e=cp[idx] if idx else None
        return e[1] if e and e[0]==1 else None
    access,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p)
    ic,p=u2(data,p); p+=2*ic
    def member(kind):
        nonlocal p
        flags,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p); ac,p=u2(data,p)
        code_shapes=[]
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); name=utf(ai)
            if kind=="method" and name=="Code":
                q=p
                max_stack,q=u2(data,q); max_locals,q=u2(data,q); code_len,q=u4(data,q)
                q+=code_len
                exn,q=u2(data,q); q+=8*exn
                subattrs,q=u2(data,q)
                code_shapes.append((max_stack,max_locals,code_len,exn,subattrs))
            p+=ln
        return flags,tuple(code_shapes)
    fc,p=u2(data,p); fields=[member("field") for _ in range(fc)]
    mc,p=u2(data,p); methods=[member("method") for _ in range(mc)]
    class_ac,p=u2(data,p)
    class_attrs=[]
    for _ in range(class_ac):
        ai,p=u2(data,p); ln,p=u4(data,p); class_attrs.append(utf(ai)); p+=ln
    method_code=sorted(shape for _,shapes in methods for shape in shapes)
    return {
      "major":major,"minor":minor,"access":access,
      "interfaces":ic,"fields":fc,"methods":mc,
      "code_count":len(method_code),
      "code_shapes":method_code,
      "class_attr_names":sorted(class_attrs),
    }

def readjar(path):
    rows=[]
    with zipfile.ZipFile(path) as z:
        for n in sorted(z.namelist()):
            if n.endswith(".class") and not n.startswith("META-INF/"):
                rows.append(parse(z.read(n)))
    return rows

donor=readjar(DONOR); official=readjar(OFFICIAL)

def sig(x):
    return (
      x["major"],x["minor"],x["access"] & 0x7631,
      x["interfaces"],x["fields"],x["methods"],x["code_count"],
      tuple(x["code_shapes"]),
      tuple(x["class_attr_names"]),
    )

def basic(x):
    return (x["major"],x["minor"],x["interfaces"],x["fields"],x["methods"],x["code_count"])

dc=Counter(sig(x) for x in donor); oc=Counter(sig(x) for x in official)
db=Counter(basic(x) for x in donor); ob=Counter(basic(x) for x in official)
state={
 "gate":"PROTOBUF_2_5_0_BINARY_STRUCTURAL_FINGERPRINT",
 "donor_classes":len(donor),"official_classes":len(official),
 "donor_major_versions":dict(Counter(x["major"] for x in donor)),
 "official_major_versions":dict(Counter(x["major"] for x in official)),
 "basic_multiset_match":db==ob,
 "strict_structural_multiset_match":dc==oc,
 "basic_only_in_donor":sum((db-ob).values()),
 "basic_only_in_official":sum((ob-db).values()),
 "strict_only_in_donor":sum((dc-oc).values()),
 "strict_only_in_official":sum((oc-dc).values()),
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
