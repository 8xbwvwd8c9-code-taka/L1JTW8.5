# USEITEM Native Bridge A2A — 2026-09-24

## GOAL
Advance functional target `P1=AUTO_POTION` by identifying the shortest authoritative 850 native UseItem path and the source of the runtime item `ObjectId` consumed by that path.

This is not an inventory-UI research task. Read the branch `README.md` Mission first and stop if work drifts into generic UI/container reverse engineering.

Current authoritative facts:

```text
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SERVER_CONTRACT=C_ItemUSe opcode 94 / 0x5E
LOGICAL_PREFIX=5E + objectId LE32
ITEM_ID_IS_NOT_OBJECT_ID=YES
WP7_NATIVE_USEITEM_PASS=NO
ITEM_USE_BRIDGE=UNMAPPED
```

Prior network evidence:

```text
Lin.bin2 direct send/WSASend xref = 0
Lineage.exe has WSOCK32.dll network imports = 18
chigamec.dll has WSOCK32.dll network imports = 12
loaded-module generic network xrefs were noisy and are NOT proof of game Send path
```

Primary objective:

```text
manual/native item-use caller
 -> logical 0x5E builder or equivalent item command object
 -> runtime objectId source
 -> game network wrapper
 -> send path
```

A negative result is acceptable if it sharply identifies the next exact function/module boundary.

## MUST
- English only.
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.
- Read branch `README.md` Mission before analysis.
- Use 850 authority only. Donor 381/880 addresses are forbidden.
- Start from existing implementation/evidence, especially:
  - `docs/850-launcher/reverse/WP7_USEITEM_BEHAVIOR.md`
  - `launcher/850Launcher/ItemUseBridge.cs`
  - `launcher/850Launcher/ItemUseProtocol.cs`
  - `launcher/850Launcher/ItemUseNativeCorrelationScanner.cs`
  - `launcher/850Launcher/ItemUseAbiControl.cs`
  - `launcher/850Launcher/NativeSendXrefScanner.cs`
  - `launcher/850Launcher/NativeSendProbeControl.cs`
  - `launcher/850Launcher/NativeCallGraphScanner.cs`
  - `launcher/850Launcher/AutoStaticGameNetworkDiscovery.cs`
  - `launcher/850Launcher/AutoLoadedModuleNetworkDiscovery.cs`
- Preserve prior fact that direct `Lin.bin2` send/WSASend xrefs were zero; do not repeat the same broad discovery.
- Focus static/bounded analysis on `Lineage.exe` and `chigamec.dll` network-import wrappers first.
- For every candidate wrapper/caller, record:
  - module
  - RVA
  - import/wrapper target
  - caller RVA
  - argument/ECX provenance when provable
  - whether a packet/buffer pointer and length are observable
  - whether `0x5E` or a 5-byte item-use logical payload is constructed or consumed
- If a candidate accepts an item-related 32-bit value, prove whether it is runtime `ObjectId`, template `ItemId`, UI index, or unknown.
- Prefer exact runtime `MEM_IMAGE` reads of known candidate RVAs only when on-disk bytes are packed/non-authoritative.
- Helper/call depth <= 2 unless one additional exact callee is strictly necessary to classify the boundary.
- Separate proven facts from inference.
- If the exact native entry is found, derive the minimum ABI needed by `IItemUseBridge.UseObject(uint objectId)` but DO NOT execute it.

## DO NOT
- Do not send packets.
- Do not call candidate native functions.
- Do not write target memory.
- Do not patch/hook the client.
- Do not perform heap scans.
- Do not perform `MEM_PRIVATE` scans.
- Do not perform broad process-memory scans.
- Do not reopen Inventory ROOT/GRID/INVWIN/F4/+0x148/+0x14C/+0x150/+0x154/+0x16C UI lanes.
- Do not use the old broad writable-memory behavior correlation as proof.
- Do not treat an immediate byte `0x5E` match by itself as an ItemUse builder.
- Do not treat a winsock import caller as game Send proof without packet/buffer provenance.
- Do not copy 381/880 function addresses.
- Do not mark WP7 PASS.

## VALIDATE
A promotable UseItem candidate must prove a chain materially equivalent to:

```text
item action caller
 -> 32-bit runtime item identity provenance
 -> item-use command/payload construction
 -> stable native client function/wrapper
 -> network/send boundary
```

At minimum, provide exact evidence for:

```text
MODULE=
FUNCTION_RVA=
CALLER_RVA=
OBJECT_ID_SOURCE=
OBJECT_ID_ROLE_PROVEN=<YES|NO>
OPCODE_0x5E_ROLE_PROVEN=<YES|NO>
BUFFER_OR_COMMAND_PROVEN=<YES|NO>
SEND_BOUNDARY_PROVEN=<YES|NO>
CALLING_CONVENTION=
ECX_ROLE=
STACK_ARGS=
```

If the native path cannot yet be closed, give exactly one bounded next action, such as one exact caller RVA or one exact module wrapper to capture. Do not propose a new broad scan.

## FINAL
Create:

`launcher/850Launcher/USEITEM_NATIVE_BRIDGE_TRACE_20260924.md`

End exactly with:

```text
STATUS=<PASS_NATIVE_USEITEM_CANDIDATE | PARTIAL_NATIVE_USEITEM_TRACE | PASS_NETWORK_WRAPPER_ONLY | BLOCKED>
FUNCTIONAL_GOAL=AUTO_POTION
CURRENT_BLOCKER=
LINEAGE_EXE_NETWORK_PATH=
CHIGAMEC_NETWORK_PATH=
BEST_USEITEM_CANDIDATE=
BEST_USEITEM_EVIDENCE=
OBJECT_ID_SOURCE=
OBJECT_ID_SEMANTICS_PROVEN=<YES|NO>
OPCODE_0x5E_ROLE_PROVEN=<YES|NO>
SEND_BOUNDARY_PROVEN=<YES|NO>
ABI_PROVEN=<YES|NO>
CALL_EXECUTED=NO
MEMORY_WRITE=NO
SEND_PACKET=NO
WP7_NATIVE_USEITEM_PASS=NO
NEXT=
```