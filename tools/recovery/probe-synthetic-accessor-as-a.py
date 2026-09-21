#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

ROOT=Path(".")
DONOR=ROOT/"l1jserver2.jar"
BUILD=ROOT/"recovery"/"normalized-stage-build"
OUT=ROOT/"recovery"/"synthetic_accessor_as_a_probe.json"
MD=ROOT/"recovery"/"SYNTHETIC_ACCESSOR_AS_A_PROBE.md"

DONOR_TOP="as.a"
DONOR_INNERS=["as.a$a","as.a$b","as.a$c","as.a$d"]
GEN_TOP="l1r.as.L1BugBearRace"
GEN_INNERS=[
 "l1r.as.L1BugBearRace$L1R_a",
 "l1r.as.L1BugBearRace$L1R_b",
 "l1r.as.L1BugBearRace$L1R_c",
 "l1r.as.L1BugBearRace$L1R_d",
]
TARGET_PARAMS="(Las/a;I)"

def javap(cp,cls):
    p=subprocess.run(["javap","-classpath",str(cp),"-p","-c","-s",cls],text=True,capture_output=True)
    if p.returncode!=0:
        raise SystemExit(f"javap failed {cls}: {p.stderr}")
    return p.stdout

def normalize_desc(s):
    return s.replace("Ll1r/as/L1BugBearRace;","Las/a;")

def parse_methods(txt):
    lines=txt.splitlines()
    out=[]
    i=0
    while i<len(lines):
        line=lines[i].strip()
        if line.endswith(");") or line=="static {};":
            decl=line
            desc=None; code=[]
            j=i+1
            while j<len(lines):
                st=lines[j].strip()
                if st.startswith("descriptor:"):
                    desc=normalize_desc(st.split("descriptor:",1)[1].strip())
                elif re.match(r"^\d+:",st):
                    code.append(st)
                elif j>i+1 and (st.endswith(");") or st=="static {};"):
                    break
                j+=1
            out.append({"decl":decl,"descriptor":desc,"code":code})
            i=j; continue
        i+=1
    return out

def member_name(decl):
    x=decl.split("(",1)[0].strip().split()
    return x[-1] if x else decl

def field_ops(code):
    arr=[]
    for x in code:
        m=re.search(r"(getfield|putfield|getstatic|putstatic)\s+#\d+\s+// Field ([^:]+):(.+)$",x)
        if m:
            arr.append({"op":m.group(1),"field":m.group(2),"descriptor":normalize_desc(m.group(3))})
    return arr

def returns(code):
    return [re.search(r"\d+:\s+([a-z]+return)\b",x).group(1)
            for x in code if re.search(r"\d+:\s+([a-z]+return)\b",x)]

def calls(txt,owner_pat):
    out=[]
    for clsline in txt.splitlines():
        m=re.search(r"invokestatic\s+#\d+\s+// Method "+owner_pat+r"\.([^:]+):(\([^)]*\).+)$",clsline)
        if m:
            out.append({"name":m.group(1),"descriptor":normalize_desc(m.group(2)),"line":clsline.strip()})
    return out

donor_top=javap(DONOR,DONOR_TOP)
gen_top=javap(BUILD,GEN_TOP)

donor_methods=[]
for m in parse_methods(donor_top):
    if m["descriptor"] and m["descriptor"].startswith(TARGET_PARAMS):
        donor_methods.append({
          "name":member_name(m["decl"]),
          "descriptor":m["descriptor"],
          "code":m["code"],
          "field_ops":field_ops(m["code"]),
          "returns":returns(m["code"]),
        })

gen_methods=[]
for m in parse_methods(gen_top):
    name=member_name(m["decl"])
    if name.startswith("access$") and m["descriptor"] and m["descriptor"].startswith(TARGET_PARAMS):
        gen_methods.append({
          "name":name,
          "descriptor":m["descriptor"],
          "code":m["code"],
          "field_ops":field_ops(m["code"]),
          "returns":returns(m["code"]),
        })

donor_calls=[]
for cls in DONOR_INNERS:
    txt=javap(DONOR,cls)
    donor_calls += [{"caller":cls,**x} for x in calls(txt,r"as/a") if x["descriptor"].startswith(TARGET_PARAMS)]

gen_calls=[]
for cls in GEN_INNERS:
    txt=javap(BUILD,cls)
    gen_calls += [{"caller":cls,**x} for x in calls(txt,r"l1r/as/L1BugBearRace") if x["descriptor"].startswith(TARGET_PARAMS)]

state={
 "gate":"SYNTHETIC_ACCESSOR_AS_A_EXACT_PROBE",
 "target_class":"as/a",
 "target_params":TARGET_PARAMS,
 "donor_methods":donor_methods,
 "generated_methods":gen_methods,
 "donor_callsites":donor_calls,
 "generated_callsites":gen_calls,
 "counts":{
   "donor_methods":len(donor_methods),
   "generated_methods":len(gen_methods),
   "donor_callsites":len(donor_calls),
   "generated_callsites":len(gen_calls),
 },
}

# exact semantic grouping: field target + opcode family is authoritative here; method names are not.
def semkey(m):
    return tuple((x["op"],x["field"],x["descriptor"]) for x in m["field_ops"])
dg={}
gg={}
for m in donor_methods: dg.setdefault(semkey(m),[]).append(m)
for m in gen_methods: gg.setdefault(semkey(m),[]).append(m)
state["semantic_groups"]=[]
for k in sorted(set(dg)|set(gg),key=str):
    state["semantic_groups"].append({
      "field_ops":[{"op":a,"field":b,"descriptor":c} for a,b,c in k],
      "donor":[{"name":x["name"],"descriptor":x["descriptor"],"returns":x["returns"]} for x in dg.get(k,[])],
      "generated":[{"name":x["name"],"descriptor":x["descriptor"],"returns":x["returns"]} for x in gg.get(k,[])],
    })

# PASS only if every semantic field-access group exists on both sides and method cardinality matches.
state["semantic_group_mismatches"]=sum(
  1 for row in state["semantic_groups"] if len(row["donor"])!=len(row["generated"])
)
state["status"]="PASS" if (
 len(donor_methods)==5 and len(gen_methods)==5 and state["semantic_group_mismatches"]==0
) else "FAIL"

OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
md=[
 "# Synthetic Accessor as/a Exact Probe","",
 f"Status: **{state['status']}**","",
 f"- Donor target methods: **{len(donor_methods)} / 5**",
 f"- Generated target accessors: **{len(gen_methods)} / 5**",
 f"- Semantic group mismatches: **{state['semantic_group_mismatches']}**",
 f"- Donor callsites: **{len(donor_calls)}**",
 f"- Generated callsites: **{len(gen_calls)}**","",
 "Identity authority: field-access opcode + field target + descriptor; synthetic method names are non-authoritative.","",
 "This probe does not modify application source."
]
MD.write_text("\n".join(md)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if state["status"]!="PASS":
    raise SystemExit("as/a synthetic accessor semantic parity not proven")
