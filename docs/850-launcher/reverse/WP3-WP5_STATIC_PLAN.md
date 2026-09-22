# WP3-WP5 — 850 Client Static / Runtime Mapping Plan

STATUS=READY_FOR_LOCAL_SCAN  
AUTHORITY=850 `Lin.bin2`  
DONOR_ADDRESSES=FORBIDDEN  
SERVER_DOCS=EXCLUDED

## Current evidence

850 launcher baseline exists at:

```text
analysis/l1jtw85-recovery
└─ 登入器/8.5豋入用/
   ├─ Lin.bin2
   └─ LoginWithoutUI.exe
```

Recovered LoginWithoutUI evidence currently proves only the launcher patch chain:

```text
start Lin.bin2
→ wait MainWindowHandle
→ verify client signature
→ write server Port
→ write server IP
```

The recovered 8.55 source contains fixed process addresses, but they are donor/baseline evidence only.
They are NOT accepted as 850 Player/HP/MP/Inventory addresses.

## WP3 Player pointer

PASS requires all of the following:

1. Candidate pointer belongs to 850 process/module address space.
2. It remains stable across at least:
   - login
   - map change
   - relog
3. The object reachable from it contains at least two independently changing player fields.
4. Evidence is reproduced after process restart.

Do not mark PASS from one static absolute address.

## WP4 HP / MP

PASS requires runtime correlation:

```text
HP changes in game -> candidate field changes exactly
MP changes in game -> candidate field changes exactly
max HP/MP remain coherent
relog/restart -> pointer chain still resolves
```

Record:

```text
module base
pointer chain / RVA
field width
signedness
sample before/after values
restart result
```

## WP5 Inventory / Item list

Minimum accepted item record:

```text
objectId
itemId
count
name or stable name lookup key
enchant (if applicable)
equipped (if applicable)
```

PASS requires controlled inventory changes:

```text
pick up item  -> one record appears / count changes
drop item     -> corresponding record disappears / count changes
stack item    -> count changes without false duplicate
equip item    -> equipment state changes or linked state proves identity
relog         -> same inventory can be enumerated again
```

## WP6 Gate

WP6 remains the first major product gate:

```text
850 character logged in
→ helper identifies character
→ helper enumerates current backpack
→ UI receives real item entries
```

No auto-potion implementation is accepted before WP6.

## Static scan

Run:

```powershell
pwsh -File .\tools\scan_850_client.ps1 -ClientPath "I:\...\Lin.bin2"
```

Output:

```text
artifacts/850-client-static/
├─ SUMMARY.txt
├─ STRINGS_ASCII.txt
├─ STRINGS_UTF16.txt
├─ INTERESTING_STRINGS.txt
└─ HASHES.txt
```

The scanner is read-only.

## Runtime evidence policy

850 runtime mapping may use:

- debugger/watchpoint
- read-only process-memory probe
- controlled HP/MP changes
- controlled inventory changes
- module-relative RVA

Do not persist raw absolute addresses as final authority when ASLR/module relocation is involved.
Prefer:

```text
module + RVA
pointer chain
signature/pattern + validated target
```

## Donor use

381/880 may suggest semantic targets such as:

- current HP / MP
- inventory vector/list
- selected item
- object ID
- use-item dispatcher

They may NOT supply final 850 addresses.
