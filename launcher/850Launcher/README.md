# 850Launcher v0.5

Client-only launcher/helper shell for the 8.50 target.

## v0.1 scope

PASS-ready now:

- external Server Name / IP / Port;
- writes legacy `ip.ini` expected by existing `LoginWithoutUI.exe`;
- starts `LoginWithoutUI.exe` with optional UAC elevation;
- x86 WinForms helper shell;
- tabs for Extend / Potion / State / Special / Items / Hotkeys / Timer;
- persistent `launcher.ini` + `helper.ini`;
- runtime bridge contract for Player / HPMP / Inventory.

Intentionally UNMAPPED:

- Player pointer;
- HP/MP addresses;
- inventory container/item layout;
- objectId -> UseItem;
- Buff/Skill runtime dispatch.

These remain disabled until WP3-WP6 runtime evidence passes. No 381/880 address is embedded.

## Deploy

Build the project, then place `850Launcher.exe` beside:

```text
Lin.bin2
LoginWithoutUI.exe
ip.ini
launcher.ini
helper.ini
```

Copy the example INI files once:

```powershell
Copy-Item launcher.ini.example launcher.ini
Copy-Item helper.ini.example helper.ini
```

## Build

```powershell
pwsh -File .\build.ps1
```

The project targets .NET Framework 4.0 and x86. `build.ps1` falls back to the Windows Framework v4 MSBuild/csc toolchain when no standalone MSBuild is on PATH.

## Runtime probe (v0.3)

After the character is logged into the 850 game world:

1. Open the **偵測** tab.
2. Enter the exact on-screen values for 目前 HP / 最大 HP / 目前 MP / 最大 MP.
3. Click **首次掃描**.
4. Change HP and/or MP in normal gameplay.
5. Enter the new exact values and click **再次篩選**.
6. Repeat until candidate counts are small.
7. Evidence is appended to `runtime_probe_evidence.txt` beside the launcher.

The probe is read-only:

```text
OpenProcess=QUERY_INFORMATION|VM_READ
ReadProcessMemory=YES
WriteProcessMemory=NO
DONOR_ADDRESS=NONE
```

Nearby HP/MP candidate fields within 0x100 bytes are ranked to help locate the player structure. A candidate is not accepted as WP3/WP4 PASS until it survives relog and full client restart.


## Inventory count probe (v0.4)

The **物品偵測** tab prepares WP5 without writing client memory.

Workflow:

1. Pick one stackable backpack item.
2. Enter its exact current count.
3. Click **首次掃描**.
4. Change only that stack count in normal gameplay (pick up/use/drop one).
5. Enter the new exact count and click **再次篩選**.
6. Repeat until candidate count is small.
7. Select a candidate to view nearby 32-bit values around ±0x40 bytes.
8. Evidence is appended to `inventory_probe_evidence.txt`.

This is intended to identify the real 850 item-record neighborhood before proving objectId/itemId/count/enchant/equipped fields.


## Pointer-chain + runtime map (v0.5)

After an HP/MP candidate survives controlled value changes:

1. Use **指標鏈** and paste the candidate absolute address.
2. Search 1-level / 2-level read-only pointer chains rooted in the Lin.bin2 module.
3. Copy a candidate expression such as:
   `PTR:0x00123456|0x18|0x2C`
4. Re-login / fully restart the client and repeat validation.
5. Only after the expression remains correct, paste it into **映射**.
6. Save `runtime-map.ini`.

Supported mapping syntax:

```text
RVA:0x00123456
PTR:0x00123456|0x18
PTR:0x00123456|0x18|0x2C
```

The launcher reloads `runtime-map.ini` while running. No runtime address from 381/880 is accepted.

## Developer mode

Reverse/probe tabs are hidden in normal player mode.

To enable the internal validation pages temporarily:

```ini
[Developer]
Enabled=1
```

Normal player UI keeps only the Chinese helper pages. Probe pages include HP/MP scanning, inventory count scanning, inventory record comparison, field validation, pointer-chain search and runtime mapping.

## Inventory field proof

WP5 now has a staged read-only proof workflow:

```text
物品偵測
  -> isolate Count candidate

物品結構
  -> capture A/B around the candidate
  -> highlight changed/stable fields
  -> optional known ItemId match

物品欄位
  -> validate candidate record base + offsets
  -> ObjectId / ItemId / Count / Enchant / Equipped
  -> save repeated evidence snapshots

InventoryBridge
  -> remains UNMAPPED until the fields and collection structure are proven
```

No single-session or single-value match is accepted as WP5/WP6 PASS.

## WP7 / WP8 gate

Recovered 850 server proof:

```text
PacketHandler opcode 94 (0x5E) -> C_ItemUSe
C_ItemUSe first field -> objectId (readD / LE32)
heal potion rows use_type=normal -> no extra C_ItemUSe payload fields
```

Therefore the decrypted logical payload for a normal healing potion is:

```text
5E <objectId LE32>
```

This is **not** sent directly by the launcher. Session framing/encryption remains client-owned.

The hidden **UseItem協定** tab only builds the logical payload for comparison/evidence.
The hidden **Send掃描** tab parses the Lin.bin2 PE import table and finds runtime xrefs to send/WSASend-style IAT entries, read-only.

Auto-potion is wired end-to-end up to the WP7 gate:

```text
HP policy
 -> inventory priority itemId
 -> objectId
 -> cooldown
 -> IItemUseBridge
```

Until the native 850 use-item path is proven, the bridge remains unmapped and no use action is emitted.

## Offline item names

`item-names.csv` is generated from the current 850 `etcitem`, `weapon`, and `armor` SQL tables.
It currently contains 4388 item IDs and lets the mapped inventory display Chinese names without connecting the launcher to MySQL.

## Native send caller graph

The hidden **Send追蹤** page extends the read-only native send scan:

```text
send / WSASend IAT xref
 -> nearest x86 function candidate
 -> relative E8 callers
 -> repeat up to depth 5
 -> mark strong immediate 0x5E patterns
 -> fingerprint first 64 bytes with SHA-256
```

The 0x5E marker is heuristic-only. It is not accepted as C_ItemUSe identity by itself.

Stable native identity requires:

```text
same Lin.bin2 authority hash
same module-relative function RVA
same 64-byte function fingerprint
same caller relationship
relog stability
full client restart stability
manual normal-potion action correlation
```

Evidence is written to `native_call_graph_evidence.txt`.

## Native evidence comparison

The hidden **Send比對** page compares appended `native_call_graph_evidence.txt` sessions.

Recommended sequence:

```text
session 1: normal login
session 2: relog character
session 3: fully close Lin.bin2 and launch again
```

Stable candidates require:

```text
same function RVA
same SHA256(first 64 bytes)
stable caller edge(s)
at least 2 distinct PROCESS_START_UTC values for restart stability
```

A restart-stable candidate is still not WP7 PASS until it is correlated with a normal manual potion use.

## WP9 auto-buff scaffold

850 server recovery proves:

```text
C_UseSkill = aj.cr
PacketHandler opcode = 128 (0x80)
row = readC()
column = readC()
skillId = row * 8 + column + 1
general = targetId(D) + targetX(H) + targetY(H)
skill 58/63 = targetX(H) + targetY(H)
skill 5/69 = mapId(H) + x(H) + y(H)
skill 116/118 = message string branch
```

Player UI now loads `skill-names.csv` generated from the authoritative 850 `skills.sql`.
The **狀態** page shows Chinese buff-capable skills and persists checked SkillIds.

Execution remains gated:

```text
selected buff skills
 -> BuffStateBridge must be mapped
 -> SkillUseBridge must be mapped
 -> only then may AutoBuffController request a cast
```

The hidden **SkillUse協定** page builds logical decrypted payloads for evidence only. SEND=NO.

## WP3 player identity probe

The hidden **玩家偵測** page performs a read-only search for:

```text
player objectId
player X
player Y
```

Workflow:

1. Read the character objectId from the authoritative 850 character DB.
2. Enter current in-game X/Y.
3. Select X/Y memory width (2 or 4 bytes).
4. Run **首次掃描**.
5. Move the character.
6. Enter the new X/Y and run **再次篩選**.
7. Repeat until objectId/X/Y candidates form a small nearby cluster.
8. Convert stable addresses to module RVA / pointer chains.
9. Validate across relog and full client restart before saving them in `runtime-map.ini`.

The probe locks objectId and coordinate widths after the first scan so evidence cannot be mixed across incompatible layouts.
