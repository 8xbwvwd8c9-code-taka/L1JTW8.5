#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
OUT=Path('recovery/protobuf_builder_provider_map.json')
MD=Path('recovery/PROTOBUF_BUILDER_PROVIDER_MAP.md')

CLASSES=['l1rpb.x$a','l1rpb.a$a','l1rpb.p$a','l1rpb.b$a','l1rpb.y$a']

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

def parse_block(owner,b):
    first=b.splitlines()[0].strip()
    nm=re.search(r'\s([A-Za-z_$][A-Za-z0-9_$]*)\([^)]*\)',first)
    dm=re.search(r'^\s*descriptor:\s+(\S+)',b,re.M)
    fm=re.search(r'^\s*flags:\s*\([^)]*\)\s*(.*)$',b,re.M)
    sm=re.findall(r'^\s*Signature:.*//\s*(.*)$',b,re.M)
    invokes=[]
    for m in re.finditer(r'// (?:InterfaceMethod|Method) (?:(?P<owner>[A-Za-z0-9_$/]+)\.)?(?P<name>[A-Za-z0-9_$<>"]+):(?P<desc>\S+)',b):
        invokes.append({
            'owner':m.group('owner') or owner.replace('.','/'),
            'name':m.group('name'),
            'descriptor':m.group('desc'),
        })
    return {
      'owner':owner,
      'decl':first,
      'name':nm.group(1) if nm else None,
      'descriptor':dm.group(1) if dm else None,
      'flags':fm.group(1) if fm else '',
      'signatures':sm,
      'invokes':invokes,
      'block':b,
    }

parsed={cls:[parse_block(cls,b) for b in blocks(javap(cls))] for cls in CLASSES}
index={}
for cls,methods in parsed.items():
    for m in methods:
        if m['name'] and m['descriptor']:
            index.setdefault((cls,m['name'],m['descriptor']),[]).append(m)

def params(desc):
    return desc[:desc.index(')')+1]

def ret(desc):
    return desc[desc.index(')')+1:]

xabs=[m for m in parsed['l1rpb.x$a'] if 'ACC_ABSTRACT' in m['flags'] and m['name'] and m['descriptor']]
maps=[]
safe=[]
for abs_m in xabs:
    exact=[]
    for owner in ['l1rpb.a$a','l1rpb.p$a','l1rpb.b$a']:
        for p in parsed[owner]:
            if p['name']==abs_m['name'] and p['descriptor']==abs_m['descriptor'] and 'ACC_ABSTRACT' not in p['flags']:
                provider={k:v for k,v in p.items() if k!='block'}
                provider['safe_typed_alias']=False
                provider['safe_reason']=None
                provider['typed_target']=None

                if 'ACC_SYNTHETIC' in p['flags']:
                    same_owner=[iv for iv in p['invokes'] if iv['owner']==owner.replace('.','/')]
                    same_params=[iv for iv in same_owner if params(iv['descriptor'])==params(p['descriptor'])]
                    # Require exactly one same-owner, same-parameter delegate target.
                    if len(same_params)==1:
                        iv=same_params[0]
                        targets=index.get((owner,iv['name'],iv['descriptor']),[])
                        if len(targets)==1:
                            t=targets[0]
                            sigs=t['signatures']
                            generic_self=any('TBuilderType;' in s for s in sigs)
                            owner_typed_ret=ret(t['descriptor']) in {
                                'Ll1rpb/a$a;','Ll1rpb/p$a;','Ll1rpb/b$a;'
                            }
                            concrete=('ACC_ABSTRACT' not in t['flags'])
                            if generic_self and owner_typed_ret and concrete:
                                provider['safe_typed_alias']=True
                                provider['safe_reason']='synthetic exact provider -> one same-owner/same-params delegate -> concrete typed target with TBuilderType Signature'
                                provider['typed_target']={k:v for k,v in t.items() if k!='block'}
                                safe.append({
                                  'abstract_name':abs_m['name'],
                                  'abstract_descriptor':abs_m['descriptor'],
                                  'provider_owner':owner,
                                  'provider_name':p['name'],
                                  'provider_descriptor':p['descriptor'],
                                  'typed_target_name':t['name'],
                                  'typed_target_descriptor':t['descriptor'],
                                  'typed_target_signatures':sigs,
                                  'alias_name':abs_m['name'],
                                  'alias_descriptor':t['descriptor'],
                                })
                            else:
                                provider['safe_reason']=f'target_not_safe generic_self={generic_self} owner_typed_ret={owner_typed_ret} concrete={concrete}'
                        else:
                            provider['safe_reason']=f'target_lookup_count={len(targets)}'
                    else:
                        provider['safe_reason']=f'same_owner_same_params_delegate_count={len(same_params)}'
                else:
                    provider['safe_reason']='provider_not_synthetic'
                exact.append(provider)

    maps.append({
      'abstract_owner':'l1rpb.x$a',
      'name':abs_m['name'],
      'descriptor':abs_m['descriptor'],
      'abstract_flags':abs_m['flags'],
      'exact_providers':exact,
    })

# Deduplicate aliases by owner/name/typed descriptor.
uniq={}
for x in safe:
    key=(x['provider_owner'],x['alias_name'],x['alias_descriptor'])
    uniq[key]=x
safe=list(uniq.values())

state={
 'interface':'l1rpb.x$a',
 'abstract_count':len(xabs),
 'maps':maps,
 'all_have_provider':all(bool(x['exact_providers']) for x in maps),
 'synthetic_provider_pair_count':sum(
     1 for x in maps for p in x['exact_providers'] if 'ACC_SYNTHETIC' in p['flags']
 ),
 'safe_typed_alias_count':len(safe),
 'safe_typed_aliases':safe,
 'unsafe_or_unproven_synthetic_pairs':[
   {
     'abstract_name':x['name'],
     'abstract_descriptor':x['descriptor'],
     'provider_owner':p['owner'],
     'provider_name':p['name'],
     'provider_descriptor':p['descriptor'],
     'reason':p['safe_reason'],
   }
   for x in maps for p in x['exact_providers']
   if 'ACC_SYNTHETIC' in p['flags'] and not p['safe_typed_alias']
 ],
 'selection_rule':'x$a abstract exact synthetic provider -> exactly one same-owner same-parameter invoke -> concrete a$a/p$a/b$a return target -> target Signature contains TBuilderType',
 'probe_only':True,
 'modified':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
lines=[
 '# Protobuf Builder Provider Map','',
 f'- x$a abstract methods: **{len(xabs)}**',
 f'- synthetic provider pairs: **{state["synthetic_provider_pair_count"]}**',
 f'- safe typed aliases: **{len(safe)}**',
 f'- unproven synthetic pairs: **{len(state["unsafe_or_unproven_synthetic_pairs"])}**',''
]
for x in safe:
    lines.append(
      f'- SAFE {x["provider_owner"]}.{x["alias_name"]}{x["alias_descriptor"]} '
      f'<- {x["typed_target_name"]}{x["typed_target_descriptor"]} '
      f'Signature={x["typed_target_signatures"]}'
    )
MD.write_text('\n'.join(lines)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
