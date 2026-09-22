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

The project targets .NET Framework 4.8 and x86.
