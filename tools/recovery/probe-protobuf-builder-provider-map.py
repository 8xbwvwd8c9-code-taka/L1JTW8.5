#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_builder_provider_map.json')
MD=Path('recovery/PROTOBUF_BUILDER_PROVIDER_MAP.md')

def javap(cls):
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-s','-v','-c',cls],text=True,capture_output=True)
    if cp.returncode:
        raise SystemExit(cp.stderr)
    return cp.stdout

def blocks(text):
    lines=text.splitlines(); out=[]; i=0
    decl=re.compile(r'^  (?:public|protected|private) ')
    while i<len(lines):
        if decl.match(lines[i]):
            j=i+1
            while j<len(lines) and not decl.match(lines[j]) and not lines[j].startswith('  static {};'):
                j+=1
            out.append('\n'.join(lines[i:j])); i=j
        else:
            i+=1
    return out

def parse_block(b):
    first=b.splitlines()[0].strip()
    nm=re.search(r'\s([A-Za-z_$][A-Za-z0-9_$]*)\([^)]*\)',first)
    dm=re.search(r'^\s*descriptor:\s+(\S+)',b,re.M)
    fm=re.search(r'^\s*flags:\s*\([^)]*\)\s*(.*)$',b,re.M)
    sm=re.findall(r'^\s*Signature:.*//\s*(.*)$',b,re.M)
    invokes=[]
    for m in re.finditer(r'// Method (?:(?P<owner>[A-Za-z0-9_$/]+)\.)?(?P<name>[A-Za-z0-9_$]+):(?P<desc>\S+)',b):
        invokes.append({
            'owner':m.group('owner'),
            'name':m.group('name'),
            'descriptor':m.group('desc'),
        })
    return {
      'decl':first,
      'name':nm.group(1) if nm else None,
      'descriptor':dm.group(1) if dm else None,
      'flags':fm.group(1) if fm else '',
      'signatures':sm,
      'invokes':invokes,
      'block':b,
    }

classes=['l1rpb.x$a','l1rpb.a$a','l1rpb.p$a','l1rpb.b$a','l1rpb.y$a']
parsed={cls:[parse_block(b) for b in blocks(javap(cls))] for cls in classes}

xabs=[]
for m in parsed['l1rpb.x$a']:
    if 'ACC_ABSTRACT' in m['flags'] and m['name'] and m['descriptor']:
        xabs.append(m)

maps=[]
for abs_m in xabs:
    exact=[]
    for owner in ['l1rpb.a$a','l1rpb.p$a','l1rpb.b$a']:
        for m in parsed[owner]:
            if m['name']==abs_m['name'] and m['descriptor']==abs_m['descriptor'] and 'ACC_ABSTRACT' not in m['flags']:
                exact.append({'owner':owner,**m})
    maps.append({
      'abstract_owner':'l1rpb.x$a',
      'name':abs_m['name'],
      'descriptor':abs_m['descriptor'],
      'abstract_flags':abs_m['flags'],
      'exact_providers':exact,
    })

state={
 'interface':'l1rpb.x$a',
 'abstract_count':len(xabs),
 'maps':maps,
 'all_have_provider':all(bool(x['exact_providers']) for x in maps),
 'probe_only':True,
 'modified':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
lines=['# Protobuf Builder Provider Map','',f'- x$a abstract methods: **{len(xabs)}**',f'- all have exact provider: **{state["all_have_provider"]}**','']
for x in maps:
    lines.append(f'## {x["name"]}{x["descriptor"]}')
    for p in x['exact_providers']:
        lines.append(f'- {p["owner"]}: flags={p["flags"]}; invokes={p["invokes"]}; signatures={p["signatures"]}')
MD.write_text('\n'.join(lines)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
