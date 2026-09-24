# AGENT1_WP7_V18_OBJECTID_USEITEM_A2A_20260924

## GOAL

Advance **P1 AUTO_POTION** from the current V18 checkpoint by proving the shortest authoritative 8.50c path:

```text
runtime item ObjectId
-> native item-use builder/action
-> session/network handoff
-> native send path
```

Do not reopen Inventory UI/container reverse engineering.

Current authority:

```text
BRANCH=work/850-inventory-helper
TARGET=8.50c
CLIENT=Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
HP_MP_RUNTIME_MAP=PASS
SERVER_ITEMUSE_OPCODE=0x5E
SERVER_ITEMUSE_FIRST_FIELD=ObjectId LE32
WP7_NATIVE_USEITEM_PASS=NO
```

## MUST

Read first:

- `README.md`
- `docs/850-launcher/reverse/WP7_USEITEM_BEHAVIOR.md`
- `launcher/850Launcher/agent/WP7_USEITEM_OBJECTID_NATIVE_BRIDGE_A2A_20260924.md`
- `launcher/850Launcher/tools/run_850_useitem_send_callers_v18.ps1`
- `launcher/850Launcher/tools/decoder/decode_850_useitem_send_callers_v18.py`
- `launcher/850Launcher/tools/decoder/decode_850_session_wrapper_imports_v18.py`
- `launcher/850Launcher/ItemUseNativeCorrelationScanner.cs`
- `launcher/850Launcher/NativeFunctionAbiAnalyzer.cs`
- `launcher/850Launcher/AutoStaticGameNetworkDiscovery.cs`

Start from the current V18 evidence. Do not repeat retired broad inventory scans.

1. Run or consume `850_useitem_send_callers_v18.txt` and classify only the bounded send wrappers/callers already selected for `Lineage.exe` / `chigamec.dll`.
2. Run or consume the Lin.bin2 local-module import trace from `decode_850_session_wrapper_imports_v18.py` using the authoritative `Lin.bin2`.
3. If V18 reports a local handoff candidate such as `LIBEAY32`, inspect only the listed IAT callsites and their direct function owners. Do not scan unrelated modules/functions.
4. Correlate the two sides only when evidence supports the handoff. Do not assume `Lin.bin2`, `Lineage.exe`, `chigamec.dll`, or a crypto DLL owns packet construction merely because it imports networking/crypto APIs.
5. For each surviving candidate record:
   - module
   - function RVA
   - callsite RVA
   - immediate caller/callee relation
   - calling-convention evidence
   - whether a 32-bit item runtime identity enters the function
   - exact register/stack/field source if provable
   - session/network sink provenance
   - manual-use-specific evidence versus generic traffic
6. A literal `0x5E` is only a ranking hint. If the logical command is transformed before the final wrapper, prove provenance through the builder/caller chain instead.
7. Prefer the shortest path that can later support AutoPotion and other item actions without requiring a complete inventory UI model.
8. If current evidence cannot prove ObjectId provenance, return the exact smallest bounded runtime probe: maximum 1-3 exact module+RVA targets and a controlled `no action -> manual potion use -> no action` comparison.

## DO NOT

- Do not reopen ROOT / GRID / INVWIN inventory UI lanes.
- Do not run heap-wide, MEM_PRIVATE-wide, or generic pointer scans.
- Do not scan every loaded module.
- Do not write target process memory.
- Do not send packets.
- Do not invoke candidate native functions.
- Do not hook or patch the client.
- Do not copy 381/880 addresses.
- Do not assume `ItemId == ObjectId`.
- Do not mark a generic send/crypto wrapper as UseItem proof.
- Do not mark a literal `0x5E` alone as UseItem proof.
- Do not map `ItemUseBridge` or enable AutoPotion.
- Do not modify WP9/BuffState files.
- Do not modify shared launcher source unless absolutely necessary for a bounded read-only probe; prefer new `v19` tooling files and the result document.

## VALIDATE

Promotion requires BOTH:

```text
A. ITEM-SPECIFIC IDENTITY
ObjectId or authoritative item-record identity source is shown entering the candidate path.

B. NATIVE ACTION PROVENANCE
item-use builder/caller -> session/network handoff -> native send path is shown.
```

Strong proof additionally requires controlled manual-use correlation and stable module-relative RVA/fingerprint across restart.

If either A or B is missing, keep `WP7_NATIVE_USEITEM_PASS=NO` and report the smallest exact next probe.

Before completion run relevant decoder tests/source audit for any files changed.

## FINAL

Create only:

`launcher/850Launcher/agent/AGENT1_WP7_V18_OBJECTID_USEITEM_RESULT_20260924.md`

End exactly with:

```text
STATUS=
NETWORK_OWNER_MODULE=
SESSION_HANDOFF_MODULE=
NETWORK_WRAPPER_RVA=
USEITEM_CANDIDATE_RVA=
OBJECT_ID_SOURCE=
OBJECT_ID_PROVEN=
ITEM_SPECIFIC_ACTION_PROVEN=
NATIVE_SEND_PATH_PROVEN=
CALLING_CONVENTION=
CALLER_DEPTH_USED=
V18_SEND_CALLER_EVIDENCE=
V18_LOCAL_IMPORT_EVIDENCE=
AUTO_POTION_BLOCKER_OBJECT_ID=
AUTO_POTION_BLOCKER_NATIVE_USEITEM=
WP7_NATIVE_USEITEM_PASS=NO
MEMORY_WRITE=NO
PACKET_SEND=NO
NEXT=
```

Allowed STATUS:

```text
PASS_NATIVE_USEITEM_CANDIDATE
PARTIAL_NATIVE_USEITEM_PATH
PASS_GENERIC_NETWORK_ONLY
BLOCKED
```
