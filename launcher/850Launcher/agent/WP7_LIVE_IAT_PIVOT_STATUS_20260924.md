# WP7 Live-IAT Pivot Status — 2026-09-24

## Mission

```text
PRIMARY_PRODUCT=PUBLIC_850_LAUNCHER_WITH_FUNCTIONAL_HELPER
FUNCTIONAL_TARGET=P1_AUTO_POTION
CLIENT_AUTHORITY=850 Lin.bin2 ONLY
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SHORTEST_NATIVE_BRIDGE=YES
```

## Evidence hierarchy

### Repository-verified Agent1 result

Verified remote commit:

```text
9e6b146ad8632f5e0fcf7289fb57b2967fcf2dfb
```

Result:

```text
STATUS=PASS_GENERIC_NETWORK_ONLY
chigamec.dll+0x0000100C=SendEncryptedData generic encrypted-send wrapper
Lineage.exe+0x00084110=generic raw-send wrapper
Lin.bin2 static imports=kernel32.dll only
LOCAL_IMPORTED_DLL_COUNT=0
LIBEAY32_IMPORTED=0
OBJECT_ID_PROVEN=NO
ITEM_SPECIFIC_ACTION_PROVEN=NO
WP7_NATIVE_USEITEM_PASS=NO
```

Agent1 proved that the old static V18 path did not expose an item-specific native builder.

### User-reported Agent2 live-memory evidence

The Agent2 result was supplied in conversation but its requested result file was not present on `work/850-inventory-helper` when checked. Treat these values as **candidate live evidence**, not yet repository-authoritative proof:

```text
Lin.bin2 image base=0x00400000
WSAAsyncSelect IAT RVA=0x00EA5870
send IAT RVA=0x00EA5898
recv IAT RVA=0x00EA589C
PACKER=Themida/WinLicense reported
```

The important reconciliation is that an incomplete on-disk import table does not prove the unpacked runtime lacks Winsock imports.

## Ruling

```text
STATIC_LOCAL_IMPORT_PATH=DEMOTED_TO_OFFLINE_FALLBACK
LIVE_DECRYPTED_IAT_PATH=PRIMARY_WP7_PATH
V19_STATIC_LOCAL_HANDOFF_CLASSIFIER=KEEP_FOR_REFERENCE_NOT_PRIMARY
```

Root cause of the previous zero-import lane is now treated as packed/static-image visibility until runtime restart evidence confirms or rejects the live IAT candidate.

## V20 bounded path

Branch:

```text
work/850-wp7-live-iat
```

Added/changed path:

```text
NativeSendXrefScanner.ScanKnownRuntimeIat(...)
NativeSendProbeControl live-IAT fallback
run_850_wp7_live_send_iat_v20.ps1
compare_850_wp7_live_send_iat_v20.ps1
tools/decoder/tests/test_wp7_live_iat_contract.py
```

V20 rules:

```text
candidate send IAT RVA=0x00EA5898
client SHA guard=REQUIRED
live IAT slot read=4 bytes only
IAT target validation=target process ws2_32.dll export table
scan scope=Lin.bin2 executable MEM_IMAGE only
heap scan=NO
MEM_PRIVATE scan=NO
caller depth=1
max exact targets=3
memory write=NO
packet send=NO
native invocation=NO
```

The target `ws2_32.dll!send` export RVA is resolved from the **actual DLL image path loaded by the target client**, not from the PowerShell host architecture.

## Promotion gates

### Gate 1 — live send IAT identity

One session must prove:

```text
SEND_TARGET_WITHIN_WS2_32=1
SEND_TARGET_EXPORT_MATCH=1
```

Otherwise the candidate is not promoted.

### Gate 2 — restart stability

Two fully distinct client processes must show:

```text
CLIENT_AUTHORITY_BOTH=1
CLIENT_SHA_MATCH=1
DISTINCT_PROCESS_INSTANCE=1
SEND_IAT_RVA_STABLE=1
SEND_TARGET_EXPORT_MATCH_BOTH=1
STABLE_EXACT_TARGET_COUNT>=1
```

Only then may the network root be treated as restart-stable.

### Gate 3 — manual potion specificity

After Gate 2 only:

```text
no action
-> one normal manual potion use
-> no action
```

Correlation scope must be limited to the restart-stable `EXACT_TARGET` RVAs. Generic traffic is not UseItem proof.

### Gate 4 — ObjectId provenance

Promotion to native UseItem requires a runtime item ObjectId or authoritative item-record identity to be shown entering the manual-potion-specific native path.

## Current formal state

```text
WP4_HP_MP=PASS
LIVE_SEND_IAT_CANDIDATE=0x00EA5898
LIVE_SEND_IAT_RESTART_STABLE=NO
CONTROLLED_MANUAL_POTION_CORRELATION=NO
OBJECT_ID_PROVEN=NO
ITEM_SPECIFIC_ACTION_PROVEN=NO
NATIVE_SEND_PATH_PROVEN=NO
WP7_NATIVE_USEITEM_PASS=NO
WP8_AUTO_POTION=BLOCKED
MEMORY_WRITE=NO
PACKET_SEND=NO
```

## Next

```text
1. Sync work/850-wp7-live-iat after this bounded tooling is finalized.
2. Run V20 Session A.
3. Fully close and restart/login 8.50c.
4. Run V20 Session B to a separate evidence file.
5. Run V20 restart comparer.
6. Only on PASS_RESTART_STABLE_LIVE_SEND_IAT, build/run the bounded manual-potion correlation against the stable exact target set.
```
