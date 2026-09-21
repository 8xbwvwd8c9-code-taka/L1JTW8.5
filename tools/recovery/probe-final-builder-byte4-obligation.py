#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path

JAR=Path("recovery/protobuf-2.5.0-source-built-donor-abi.jar")
OUT=Path("recovery/final_builder_byte4_obligation_probe.json")
TARGET_PARAMS="([BLl1rpb/n;)"

class R:
    def __init__(self,b): self.b=b; self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from(">H",self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from(">I",self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def cpread(r):
    if r.u4()!=0xCAFEBABE: raise SystemExit("bad class")
    r.skip(4); n=r.u2(); cp=[None]*n; i=1
    while i<n:
        t=r.u1()
        if t==1:
            z=r.u2(); cp[i]=(t,r.b[r.p:r.p+z].decode("utf-8","replace")); r.skip(z)
        elif t in (3,4): r.skip(4)
        elif t in (5,6): r.skip(8); i+=1
        elif t in (7,8,16,19,20): cp[i]=(t,r.u2())
        elif t in (9,10,11,12,17,18): r.skip(4)
        elif t==15: r.skip(3)
        else: raise SystemExit(f"cp tag {t}")
        i+=1
    return cp

def utf(cp,i):
    e=cp[i] if i else None
    return e[1] if e and e[0]==1 else None
def cls(cp,i):
    e=cp[i] if i else None
    return utf(cp,e[1]) if e and e[0]==7 else None
def skipattrs(r):
    for _ in range(r.u2()):
        r.skip(2); r.skip(r.u4())

def parse(data):
    r=R(data); cp=cpread(r)
    flags=r.u2(); this_i=r.u2(); super_i=r.u2()
    ic=r.u2(); ifaces=[cls(cp,r.u2()) for _ in range(ic)]
    for _ in range(r.u2()):
        r.skip(6); skipattrs(r)
    methods=[]
    for _ in range(r.u2()):
        fl=r.u2(); ni=r.u2(); di=r.u2()
        methods.append({"name":utf(cp,ni),"descriptor":utf(cp,di),"flags":fl,"abstract":bool(fl&0x0400)})
        skipattrs(r)
    return {"name":cls(cp,this_i),"super":cls(cp,super_i),"interfaces":ifaces,"methods":methods}

if not JAR.exists(): raise SystemExit(f"missing {JAR}")
classes={}
with zipfile.ZipFile(JAR) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            x=parse(z.read(n)); classes[x["name"]]=x

matches=[]
for owner,x in classes.items():
    for m in x["methods"]:
        if m["name"]=="c" and m["descriptor"].startswith(TARGET_PARAMS):
            matches.append({"owner":owner,**m,"super":x["super"],"interfaces":x["interfaces"]})

same_params=[]
for owner,x in classes.items():
    for m in x["methods"]:
        if m["descriptor"].startswith(TARGET_PARAMS):
            same_params.append({"owner":owner,**m,"super":x["super"],"interfaces":x["interfaces"]})

state={"target_name":"c","target_params":TARGET_PARAMS,"c_matches":matches,"same_param_matches":same_params}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if not any(x["abstract"] for x in matches):
    raise SystemExit("no abstract c(byte[],n) match")
