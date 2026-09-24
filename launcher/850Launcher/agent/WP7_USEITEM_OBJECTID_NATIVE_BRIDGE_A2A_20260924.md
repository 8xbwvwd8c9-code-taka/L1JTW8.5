# WP7_USEITEM_OBJECTID_NATIVE_BRIDGE_A2A_20260924

## GOAL

Advance **P1 AUTO_POTION** by finding the shortest evidence-backed path from an item runtime `ObjectId` to the authoritative 8.50c client native UseItem/send path.

Do NOT continue inventory UI/container reverse engineering.

Primary question:

```text
Where does the 8.50c client obtain/use ObjectId when a player manually uses an item,
and which native client function/path owns that action?
```

Target outcome:

```text
ObjectId source
 -> native item-use builder/caller
 -> network/session wrapper
 -> authoritative client send path
```

A complete inventory map is NOT required for this task.

## MUST

Read first:

- `README.md`
- `docs/850-launcher/reverse/WP7_USEITEM_BEHAVIOR.md`
- `launcher/850Launcher/ItemUseProtocol.cs`
- `launcher/850Launcher/ItemUseBridge.cs`
- `launcher/850Launcher/ItemUseNativeCorrelationScanner.cs`
- `launcher/850Launcher/ItemUseNativeCorrelationEvidence.cs`
- `launcher/850Launcher/ItemUseAbiControl.cs`
- `launcher/850Launcher/NativeSendXrefScanner.cs`
- `launcher/850Launcher/NativeSendProbeControl.cs`
- `launcher/850Launcher/NativeCallGraphScanner.cs`
- `launcher/850Launcher/NativeFunctionAbiAnalyzer.cs`
- `launcher/850Launcher/AutoAppDirNetworkDiscovery.cs`
- `launcher/850Launcher/AutoLoadedModuleNetworkDiscovery.cs`
- `launcher/850Launcher/AutoStaticGameNetworkDiscovery.cs`

Authority:

```text
TARGET=8.50c
CLIENT=Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SERVER_CONTRACT:
  C_ItemUSe opcode=0x5E
  first field=ObjectId uint32 little-endian
```

Known boundary:

```text
server logical packet contract is proven
!=
client native UseItem path proven
```

Known negative/partial evidence:

- Direct `Lin.bin2` send/WSASend IAT xref scanning previously yielded no authoritative native UseItem entry.
- Network ownership may be wrapped through another loaded module/process surface.
- Existing WP7 scanner/ABI tools are heuristic/read-only and MUST NOT be promoted to a callable bridge without stronger proof.
- Inventory UI lanes already retired must not be reopened.

Task:

1. Identify the actual network/session module(s) involved in 8.50c runtime:
   - `Lin.bin2`
   - `Lineage.exe`
   - loaded game/security/network DLLs such as `chigamec.dll` if present
   - use existing network discovery evidence/classes before designing new scans.

2. For each relevant network wrapper:
   - identify send/WSASend/import thunk or local wrapper
   - identify direct caller(s) in executable MEM_IMAGE
   - bounded caller depth <= 2
   - record exact module + RVA + instruction RVA

3. Search only within the bounded caller set for item-use construction semantics compatible with:
   - opcode/logical command `0x5E`
   - one 32-bit runtime identity argument
   - ObjectId copied/read from an item object/record
   - call sequence leading into the same network/session wrapper

4. If `0x5E` is transformed/encrypted before the network wrapper:
   - do NOT require literal `0x5E` at the final send wrapper
   - instead prove the builder/caller provenance and where ObjectId enters the path.

5. Compare candidate call paths against existing:
   - ItemUse native correlation evidence
   - ABI evidence
   - network module discovery evidence
   - manual-use behavior evidence, if available locally

6. For each candidate report:
   - module
   - function RVA
   - callsite RVA
   - calling convention evidence
   - ObjectId source register/stack slot/field if provable
   - immediate/direct callees
   - network/session sink
   - whether evidence is manual-use-specific or generic send traffic

7. Prefer a path that can later support:
   - AutoPotion
   - AutoDelete item action identity
   - AutoTransform item use if transformation uses an item

## DO NOT

- Do not reopen ROOT / GRID / INVWIN inventory UI discovery.
- Do not scan generic UI control hierarchies.
- Do not perform heap-wide scans.
- Do not perform MEM_PRIVATE-wide scans.
- Do not perform broad pointer sweeps.
- Do not write target process memory.
- Do not send packets.
- Do not invoke candidate native functions.
- Do not hook or patch the client.
- Do not copy absolute addresses from 381/880 donors.
- Do not assume `ItemId == ObjectId`.
- Do not mark a literal `0x5E` match alone as UseItem proof.
- Do not mark a generic network send wrapper as UseItem proof.
- Do not change `ItemUseBridge` to mapped.
- Do not enable AutoPotion.

## VALIDATE

A candidate can be promoted only if the evidence chain contains BOTH:

```text
A. item-specific runtime identity provenance
   ObjectId or item-record field/source

AND

B. client-native action provenance
   builder/caller -> session/network wrapper
```

Preferred proof chain:

```text
manual item-use caller
 -> ObjectId loaded/passed
 -> item-use builder/action function
 -> packet/session wrapper
 -> network send wrapper
```

If the shortest path cannot yet be proven, return the exact smallest next bounded probe.

Examples of acceptable NEXT:

```text
capture runtime MEM_IMAGE bytes for exact candidate RVAs X,Y,Z
trace only callers of wrapper RVA X to depth 1
compare two manual-use sessions against one no-action session
```

Examples of unacceptable NEXT:

```text
scan all writable memory
scan all UI objects
scan entire process for ObjectId
```

## FINAL

Create:

`launcher/850Launcher/agent/WP7_USEITEM_OBJECTID_NATIVE_BRIDGE_RESULT_20260924.md`

End exactly with:

```text
STATUS=
NETWORK_OWNER_MODULE=
NETWORK_WRAPPER_RVA=
USEITEM_CANDIDATE_RVA=
OBJECT_ID_SOURCE=
OBJECT_ID_PROVEN=
ITEM_SPECIFIC_ACTION_PROVEN=
NATIVE_SEND_PATH_PROVEN=
CALLING_CONVENTION=
CALLER_DEPTH_USED=
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

Do not modify unrelated files.
