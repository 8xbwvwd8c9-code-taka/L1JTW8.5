# 850Launcher v0.1

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

