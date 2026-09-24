# WP7 V19 Local Handoff Tooling Status — 2026-09-24

## FUNCTIONAL_GOAL

```text
P1=AUTO_POTION
```

## CURRENT_BLOCKER

```text
INVENTORY_ITEM_OBJECT_ID
CLIENT_NATIVE_USEITEM_PATH
```

## WHY_V19

V18 already narrowed the search to authoritative 8.50c client networking/session surfaces. V19 does not reopen inventory UI/container reverse engineering. It takes only the local-module IAT callsites emitted by V18 and classifies their containing `Lin.bin2` function owners plus exact direct callers.

Target chain:

```text
V18 local-module IAT xref
-> Lin.bin2 owner function
-> exact direct callers
-> top <=3 module-relative RVA targets
-> controlled manual-potion correlation
```

## FILES

```text
launcher/850Launcher/tools/decoder/decode_850_local_handoff_callers_v19.py
launcher/850Launcher/tools/decoder/tests/test_decode_850_local_handoff_callers_v19.py
launcher/850Launcher/tools/run_850_local_handoff_callers_v19.ps1
```

## SAFETY / SCOPE

```text
CLIENT_AUTHORITY=Lin.bin2 SHA256 FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
STATIC_FILE_READ_ONLY=YES
RUNTIME_ATTACH=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
PACKET_SEND=NO
NATIVE_CALL=NO
```

If `LIBEAY32.dll` callsites exist in V18 evidence, V19 focuses only those callsites. Otherwise it uses only the bounded local-module callsites already listed by V18.

A literal `0x5E` is only a ranking hint. V19 never promotes ObjectId semantics or native UseItem proof from that literal alone.

## LOCAL RUN

From the repository root on the 850 Windows machine:

```powershell
.\launcher\850Launcher\tools\run_850_local_handoff_callers_v19.ps1
```

Expected output file:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_local_handoff_callers_v19.txt
```

The output ends with a decision block and up to three exact targets:

```text
[TOP_EXACT_TARGETS]
TARGET=1 MODULE=Lin.bin2 OWNER_RVA=...
TARGET=2 MODULE=Lin.bin2 OWNER_RVA=...
TARGET=3 MODULE=Lin.bin2 OWNER_RVA=...

[DECISION]
STATUS=...
OBJECT_ID_PROVEN=NO
ITEM_SPECIFIC_ACTION_PROVEN=NO
NATIVE_SEND_PATH_PROVEN=NO
WP7_NATIVE_USEITEM_PASS=NO
MEMORY_WRITE=NO
PACKET_SEND=NO
NEXT=...
```

## PROMOTION GATE

V19 tooling readiness is not WP7 completion.

`WP7_NATIVE_USEITEM_PASS` stays `NO` until evidence proves both:

```text
A. authoritative runtime item ObjectId enters the candidate action path
B. that same path reaches the native client session/network send path
```

The next runtime probe must be bounded to V19's top 1–3 exact `Lin.bin2` RVAs and use a controlled sequence:

```text
no action
-> one normal manual potion use
-> no action
```

Do not expand into heap-wide scanning, generic inventory UI scanning, or raw packet injection.

## CURRENT_STATUS

```text
STATUS=V19_TOOLING_READY_RUNTIME_EVIDENCE_REQUIRED
WP4_HPMP=PASS
WP7_NATIVE_USEITEM_PASS=NO
AUTO_POTION=BLOCKED
BLOCKER_RUNTIME=RUN_V19_ON_AUTHORITATIVE_850_CLIENT
```
