# WP7 NEXT RUNBOOK — 2026-09-26

## GOAL

Close the shortest remaining 8.50c AutoPotion blocker without reopening retired broad scans.

Current authority:

```text
HP_MP_RUNTIME_MAP=PASS
SERVER_ITEMUSE_OPCODE=0x5E
SERVER_ITEMUSE_FIRST_FIELD=ObjectId LE32
NETWORK_WRAPPER_CHIGAMEC=0x0000100C SendEncryptedData
NETWORK_WRAPPER_LINEAGE=0x00084110
WP7_NATIVE_USEITEM_PASS=NO
```

## STEP 0 — expose the existing WP7 UI

From repo root on `work/850-inventory-helper`:

```powershell
pwsh -File .\launcher\850Launcher\tools\enable_wp7_developer_tabs.ps1
pwsh -File .\launcher\850Launcher\tools\test_wp7_developer_tabs.ps1
```

Expected:

```text
STATUS=PASS_PATCHED
STATUS=PASS_WP7_DEVELOPER_TABS
```

The patch is idempotent and creates `MainForm.cs.wp7-before` once.

## STEP 1 — inventory prerequisite

`ItemUseBehaviorCorrelationControl` requires `runtime.Items` entries with all of:

```text
ObjectId != 0
ItemId > 0
Count > 0
```

`ProcessRuntimeBridge` obtains them only from `MappedInventoryBridge`, which loads:

```text
inventory-map.ini
```

If `Collection.Mode=UNMAPPED` or the file is absent, STOP here. Do not fake ObjectId and do not assume ItemId == ObjectId.

Do not reopen heap-wide / MEM_PRIVATE-wide / generic pointer scans. Use only the existing bounded inventory evidence to finish the formal mapping.

## STEP 2 — enable DeveloperMode and build launcher

Developer Mode must be enabled in launcher config so the WP7 pages are visible.

Required pages after Step 0:

```text
UseItem協定
UseItem行為
UseItem Native
Send掃描
Send追蹤
Send比對
```

## STEP 3 — UseItem behavior correlation

Open `UseItem行為`.

1. Refresh inventory.
2. Select one real consumable/potion.
3. Run `1. 無操作基線` and do not use the item during the baseline window.
4. Run `2. 捕捉手動 UseItem`.
5. During the action window, manually use the selected item exactly once in the game client.

The control builds the authoritative logical pattern from the selected runtime ObjectId:

```text
5E + ObjectId LE32
```

It saves:

```text
itemuse_behavior_evidence.txt
```

Promotion requirement for this step:

```text
CLIENT_AUTHORITY=1
CORRELATED=1
NEW_HITS>0
CANDIDATE_LIMIT=0
```

A negative capture is not a failure of the client protocol. Repeat a clean baseline/action pair; do not broaden scan scope.

## STEP 4 — native correlation

Without restarting the client, open `UseItem Native`.

1. Refresh behavior evidence.
2. Confirm displayed PID/process instance matches the current Lin.bin2.
3. Backref depth = 2 maximum.
4. Run `反查 Native`.

This existing path is read-only:

```text
NEW_HIT
-> pointer backref (max depth 2)
-> Lin.bin2 module root
-> executable absolute xref
-> function candidate + 64-byte fingerprint
```

Evidence output:

```text
itemuse_native_correlation_evidence.txt
```

Do not promote from one unstable process instance. Prefer restart-stable evidence from at least two authoritative sessions.

## STEP 5 — compare with V18 network boundary

Known V18 boundaries remain:

```text
chigamec.dll+0x0000100C = SendEncryptedData
Lineage.exe+0x00084110 = generic raw-send wrapper
```

Do not label either one UseItem merely because it sends traffic.

WP7 requires both:

```text
A. item-specific identity provenance
B. native action provenance to network/session boundary
```

Only after both are proven may `ItemUseBridge` be mapped and AutoPotion enabled.

## DO NOT

- Do not assume ItemId == ObjectId.
- Do not copy 381/880 runtime addresses into 850.
- Do not write target process memory.
- Do not inject packets.
- Do not invoke candidate native functions.
- Do not reopen retired broad inventory scans.
- Do not mark WP7 PASS from opcode 0x5E alone.

## CURRENT BLOCKERS

```text
BLOCKER_1=inventory-map.ini formal ObjectId/ItemId/Count mapping may still be UNMAPPED
BLOCKER_2=manual UseItem correlated native function not yet restart-stable
BLOCKER_3=native candidate -> network boundary provenance not yet closed
```

## NEXT

Run Step 0 locally, then inspect `inventory-map.ini` before any new runtime experiment.
