# L1JTW8.5 — L880C Auto-Hunt Resource Scan Helper — 2026-09-27

## STATUS

```text
BRANCH=work/850-auto-hunting
PURPOSE=LOCATE_880_AUTO_HUNT_UI_RESOURCE_CANDIDATES
TOOLKIT_ENTRYPOINT=scripts/lineage-tool.ps1
SOURCE_WRITEBACK=FORBIDDEN
HELPER_CONTRACT=PASS
ACTUAL_I_DRIVE_SCAN=NOT_EXECUTED_IN_THIS_ENVIRONMENT
NATIVE_PACKET_OPCODE=STILL_UNVERIFIED
```

## Why this helper exists

The 850 server-side packet trace infrastructure is ready, but the native 850 auto-hunt button transport still requires runtime correlation. L880C remains the UI/control donor, so this helper provides a separate evidence path for locating likely auto-hunt UI/config resource names without guessing packet opcodes and without modifying donor client archives.

A resource-name/text hit is only a candidate. It does **not** prove the native 850 packet opcode, handler, or ON/OFF payload semantics.

## Known local inputs

Toolkit root:

```text
I:\L共通工具\LineageAIResourceToolkit
```

Known L880C backup pair:

```text
I:\L880C\TEST\Text.idx.before_880_list_spr
I:\L880C\TEST\Text.pak.before_880_list_spr
```

The helper stages these backup files under the toolkit output directory using canonical names:

```text
outputs\850_auto_hunt_880_scan\input_copy\Text.idx
outputs\850_auto_hunt_880_scan\input_copy\Text.pak
```

This avoids depending on the backup suffix for IDX/PAK pairing and avoids touching the source pair.

## Files added

```text
tools/auto-hunt/test_scan_880_client_resources.py
tools/auto-hunt/scan_880_client_resources.py
tools/auto-hunt/scan_880_auto_hunt_ui.ps1
```

### `scan_880_auto_hunt_ui.ps1`

Responsibilities:

```text
1. Validate toolkit/scanner/source paths.
2. SHA-256 the source IDX and PAK.
3. Copy the backup pair to canonical output-only names.
4. Use scripts/lineage-tool.ps1 as the only toolkit entry point.
5. Run read-only `pak list ... --filter ...` searches.
6. Save one UTF-8 log per search term.
7. Run the Python evidence scanner over those logs.
8. Re-hash source IDX/PAK and abort if either source hash changed.
```

Default filename search terms:

```text
autohunt
auto
hunt
setting
config
ui
macro
```

The wrapper intentionally does not perform `add`, `delete`, `replace`, `import`, `encrypt`, `--apply`, or client write-back operations.

### `scan_880_client_resources.py`

Read-only text evidence scanner:

- accepts repeatable files/directories;
- supports UTF-8, UTF-8 BOM, CP950, and Big5;
- skips NUL-heavy binary input;
- performs case-insensitive English matching;
- includes Traditional Chinese default terms for extracted text/log analysis;
- sorts output deterministically;
- writes only the requested Markdown report path;
- never writes to an input path.

Default content terms include:

```text
自動狩獵
內掛設定
內掛
自動
狩獵
設定
autohunt
auto hunt
auto
hunt
setting
config
ui
macro
```

If there are no hits, the report emits:

```text
NO_MATCHES_OBSERVED
```

This means only that the supplied text inputs produced no match. It is not proof that the client resource or behavior is absent.

## Windows run command

From the L1JTW8.5 repository root:

```powershell
pwsh -NoProfile -File .\tools\auto-hunt\scan_880_auto_hunt_ui.ps1
```

Optional explicit paths:

```powershell
pwsh -NoProfile -File .\tools\auto-hunt\scan_880_auto_hunt_ui.ps1 `
  -ToolkitRoot 'I:\L共通工具\LineageAIResourceToolkit' `
  -SourceTextIdx 'I:\L880C\TEST\Text.idx.before_880_list_spr' `
  -SourceTextPak 'I:\L880C\TEST\Text.pak.before_880_list_spr'
```

Expected report:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_auto_hunt_880_scan\L880C_AUTO_HUNT_RESOURCE_SCAN.md
```

## TDD / validation evidence

### RED

The contract test was created before the scanner/wrapper. The first run failed because `scan_880_client_resources.py` did not exist yet.

Observed result:

```text
Ran 5 tests
FAILED (errors=5)
FileNotFoundError: scan_880_client_resources.py
```

A second focused RED was used to require accurate UTF-8 encoding reporting; normal UTF-8 was initially mislabeled `utf-8-sig`.

### GREEN

After the minimal implementation and BOM-specific correction:

```text
.....
Ran 5 tests in 0.005s
OK
```

Covered contracts:

```text
UTF8_MATCH=PASS
CP950_MATCH=PASS
ASCII_CASE_INSENSITIVE=PASS
DETERMINISTIC_REPORT=PASS
NO_MATCH_CAVEAT=PASS
SOURCE_HASH_UNCHANGED=PASS
NUL_HEAVY_BINARY_SKIP=PASS
TOOLKIT_ENTRYPOINT_ONLY=PASS
FORBIDDEN_WRITE_COMMANDS_ABSENT=PASS
```

Python compile verification:

```text
python -m py_compile tools/auto-hunt/scan_880_client_resources.py tools/auto-hunt/test_scan_880_client_resources.py
PASS
```

CLI smoke verification with a synthetic `AutoHuntPanel.xml` text input:

```text
TEXT_FILES_SCANNED=1
MATCHES=1
SOURCE_MODIFIED=NO
```

The verification container does not provide `pwsh` or Windows PowerShell and cannot mount the user's `I:` drive. Therefore the actual L880C archive scan and PowerShell runtime execution are **not claimed** here.

## Next evidence gate

Run the helper on the Windows machine and inspect the generated report.

If a likely resource filename is found:

```text
candidate filename
  -> safe toolkit extract-copy to outputs/
  -> scan extracted text/XML with scan_880_client_resources.py
  -> record UI labels/config fields/resource IDs
  -> correlate with Lin.bin2/native runtime behavior
  -> perform 850 button OFF->ON and ON->OFF PacketDebug capture
  -> prove exact opcode + handler + payload semantics
  -> only then wire the 850 Packet Adapter to AutoHuntService
```

Do not infer the packet opcode from an L880C resource hit alone.
