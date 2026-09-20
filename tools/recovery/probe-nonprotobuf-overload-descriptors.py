#!/usr/bin/env python3
import json,re,subprocess
from pathlib import Path

JAR=Path('l1jserver2.jar')
OUT=Path('recovery/nonprotobuf_overload_descriptor_probe.json')
MD=Path('recovery/NONPROTOBUF_OVERLOAD_DESCRIPTOR_PROBE.md')

checks=[
 ('aq.L1Character', r'\.b:\(Laq/L1Object;\)Z', 'L1PcInstance/L1Character visibility boolean b(L1Object)'),
 ('ap.L1MonsterInstance', r'\.b:\(Laq/L1Object;\)Z', 'monster visibility calls boolean b(L1Object)'),
 ('ap.L1PcInstance', r'\.d:\(Laq/L1Character;\)Z', 'monster hate/visibility boolean d(L1Character)'),
 ('aj.C_ItemUSe', r'\.a:\(Laq/L1Object;\)I', 'heading/direction int a(L1Object)'),
 ('ap.L1DoorInstance', r'\.d:\(Lap/L1PcInstance;\)V', 'door private update d(L1PcInstance)'),
]
rows=[]
for cls,pat,label in checks:
    cp=subprocess.run(['javap','-classpath',str(JAR),'-p','-s','-c',cls],text=True,capture_output=True)
    if cp.returncode: raise SystemExit(f'javap failed {cls}: {cp.stderr}')
    hits=[ln.strip() for ln in cp.stdout.splitlines() if re.search(pat,ln)]
    rows.append({'class':cls,'label':label,'pattern':pat,'hit_count':len(hits),'hits':hits[:30]})

ok=all(r['hit_count']>0 for r in rows)
state={'checks':rows,'all_expected_descriptors_observed':ok,'donor_jar':str(JAR)}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
md=['# Non-Protobuf Overload Descriptor Probe','',f'Status: **{"PASS" if ok else "FAIL"}**','']
for r in rows: md.append(f"- `{r['class']}` {r['label']}: **{r['hit_count']}** hit(s)")
MD.write_text('\n'.join(md)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('expected donor overload descriptor missing')
