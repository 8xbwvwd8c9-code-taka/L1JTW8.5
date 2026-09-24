# Agent 1 — WP7 V18 ObjectId → Native UseItem Result

## Scope and authority

This investigation continued from the V18 checkpoint and stayed inside the requested WP7 boundary. It did not repeat HP/MP work, reopen ROOT / GRID / INVWIN, scan heap or MEM_PRIVATE, write client memory, send packets, invoke native candidates, hook or patch the client, or modify WP9/BuffState.

```text
BRANCH=work/850-inventory-helper
TARGET=8.50c
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
CLIENT_AUTHORITY=1
ANALYSIS_MODE=BOUNDED_OFFLINE_STATIC_READ_ONLY
```

The repository `README.md`, the supplied A2A, the repository V18 A2A, `WP7_USEITEM_BEHAVIOR.md`, and every source file named by the task were read before analysis. Existing V18 tooling and evidence were used; no V19 tooling was needed.

## V18 execution

The official runner consumed `I:\8.50c客服端\auto_static_game_network_evidence.txt` and produced `850_useitem_send_callers_v18.txt` in a temporary output directory. The official local-import decoder consumed the authoritative `Lin.bin2` and produced `850_session_wrapper_imports_v18.txt` in the same temporary directory.

```text
CAPSTONE_PACKAGE_VERSION=5.0.9
CAPSTONE_BINDING_VERSION=5.0.7
CAPSTONE_DECODER_SELFTEST=PASS
RUNTIME_SCAN=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
SEND_PACKET=NO
```

### Send-caller summary

```text
SEND_XREFS_SELECTED=3
UNIQUE_SEND_WRAPPERS_REPORTED=3
DIRECT_CALLERS_TOTAL=8
IMM_0x5E_HITS=0
MISSING_TARGET_FILES=0
STATUS=PASS_SEND_WRAPPERS_NO_0x5E_YET
OBJECT_ID_SEMANTICS_PROVEN=NO
```

| Module | Network callsite RVA | V18 owner/window RVA | Classification |
|---|---:|---:|---|
| `Lineage.exe` | `0x00084131` | `0x00084110` | real generic raw-send wrapper |
| `Lineage.exe` | `0x00096F44` | `0x00096EF0` | send import thunk at `0x96F44`; not item-use proof |
| `chigamec.dll` | `0x000010D8` | `0x0000100C` | exported encrypted-send wrapper |

The V18 decoder groups the `0x96F44` xref under the preceding prologue at `0x96EF0`; source audit shows `0x96F44` itself is a `jmp [send IAT]` thunk in a run of import thunks. It is not promoted as a third semantic wrapper.

## Candidate classification

### 1. `chigamec.dll+0x0000100C`

```text
EXPORT=SendEncryptedData
FUNCTION_RVA=0x0000100C
SEND_CALLSITE_RVA=0x000010D8
SEND_IAT_RVA=0x000102A0
DIRECT_STATIC_CALLERS_IN_MODULE=0
CALLING_CONVENTION=STDCALL_OR_CALLEE_CLEANUP
RET_POP_BYTES=12
MANUAL_USE_SPECIFIC=NO
```

- `[EBP+0x08]` is appended to local buffer `[EBP-0x404]` through IAT `0x00010184`.
- `[EBP+0x0C]` is appended to the same local buffer through IAT `0x00010180`.
- Internal RVA `0x00001BB9` transforms the local buffer before send.
- `[EBP+0x10]` becomes the socket argument at send callsite `0x000010D8`.
- The transmitted buffer is `[EBP-0x404]`; its length is obtained through IAT `0x00010200`.
- `ret 0x0C` proves three stack arguments with callee cleanup.

This is the strongest bounded network/session sink candidate, but no current evidence identifies either data input as a 32-bit ObjectId or an authoritative item record. There is no static direct caller in `chigamec.dll`, and the authoritative `Lin.bin2` has no static local-module import handoff to it.

### 2. `Lineage.exe+0x00084110`

```text
FUNCTION_RVA=0x00084110
SEND_CALLSITE_RVA=0x00084131
SEND_IAT_RVA=0x0009D5D8
DIRECT_CALLSITE_RVAS=0x0007450C,0x000747B4
DIRECT_CALLER_FUNCTION_RVAS=0x00074380,0x00074771
CALLING_CONVENTION=CDECL
RET_POP_BYTES=0
MANUAL_USE_SPECIFIC=NO
```

The wrapper passes `[EBP+0x08]` as the send buffer, `[EBP+0x0C]` as its length, loads the socket from global VA `0x0058E518`, handles partial sends, and returns with plain `ret`. Both direct callsites perform `add esp, 8`, proving caller cleanup for the two wrapper arguments.

The V18 depth-1 caller windows contain no immediate `0x5E`, no authoritative item-record load, and no proven 32-bit ObjectId source. They are generic traffic callers only.

### 3. `Lineage.exe+0x00096F44`

```text
FUNCTION_RVA=0x00096F44
KIND=JMP_SEND_IAT_THUNK
SEND_IAT_RVA=0x0009D5D8
MANUAL_USE_SPECIFIC=NO
```

This thunk supplies no packet construction or ObjectId provenance. The preceding function at `0x00096EF0` is not promoted merely because the bounded function-start heuristic associated the later thunk with its prologue.

## Lin.bin2 local-module handoff

```text
IMAGE_BASE=0x00400000
IMPORT_DLL_COUNT=1
IMPORT_ENTRY_COUNT=1
DLL=kernel32.dll
LOCAL_IMPORTED_DLL_COUNT=0
LOCAL_IMPORTED_ENTRY_COUNT=0
LOCAL_IAT_XREF_COUNT=0
LIBEAY32_IMPORTED=0
LIBEAY32_IAT_XREFS=0
STATUS=NO_LOCAL_WRAPPER_IMPORTS
FORMAL_WP7=NOT_YET
```

There are no listed Lin.bin2 local-module IAT callsites or direct function owners to inspect. `LIBEAY32.dll` network imports observed in loaded-module evidence remain generic library traffic; no Lin.bin2 → LIBEAY32 session handoff is inferred.

## ObjectId and native action gates

```text
SERVER_ITEMUSE_OPCODE=0x5E
SERVER_ITEMUSE_FIRST_FIELD=ObjectId uint32 little-endian
A_ITEM_SPECIFIC_IDENTITY=FAIL
B_NATIVE_ACTION_PROVENANCE=FAIL
WP7_NATIVE_USEITEM_PASS=NO
```

The server contract does not prove the client-native builder. V18 found zero immediate `0x5E` hits in the bounded wrapper/caller set, but this negative result is not used to assume the command cannot be transformed. No authoritative ObjectId or item-record field is shown entering any surviving wrapper, and no manual-potion-specific caller reaches a network/session sink.

## Smallest exact bounded next probe

```text
sequence=no action -> one manual potion use -> no action
target 1=chigamec.dll+0x0000100C (SendEncryptedData entry)
capture 1=entry [ESP+4], [ESP+8], [ESP+0x0C] plus bounded bytes referenced by the first two arguments
target 2=Lineage.exe+0x00084110
capture 2=entry [ESP+4] buffer pointer, [ESP+8] length, plus bounded buffer bytes
scope=only these two module+RVA targets
prohibited=hook, patch, memory write, candidate invocation, packet injection, unrelated module scan
```

Promotion requires a manual-use-only invocation or argument/buffer delta with authoritative ObjectId-derived provenance. Only after that correlation should the single observed runtime caller be traced one level back to its Lin.bin2 producer. If one target must be chosen, start with `chigamec.dll+0x0000100C` because it is the named encrypted-send export with a statically proven three-argument ABI.

## Validation

```text
README_READ=PASS
ATTACHED_A2A_READ=PASS
REPOSITORY_V18_A2A_READ=PASS
AUTHORITATIVE_CLIENT_HASH=PASS
V18_SEND_CALLER_RUNNER=PASS
V18_LOCAL_IMPORT_DECODER=PASS
V18_SEND_CALLER_FIXTURE_TEST=PASS
V18_SESSION_IMPORT_TESTS=PASS_2_OF_2
V18_SOURCE_AUDIT=PASS
CONTROLLED_MANUAL_USE_CORRELATION=NOT_RUN
RESTART_STABLE_NATIVE_FINGERPRINT=NOT_PROVEN
REPOSITORY_TOOLING_CHANGED=NO
LAUNCHER_BUILD=BLOCKED_MSB3644_MISSING_DOTNET_FRAMEWORK_V4_REFERENCE_ASSEMBLIES
RESULT_FILE_SOURCE_AUDIT=PASS
MEMORY_WRITE=NO
PACKET_SEND=NO
```

STATUS=PASS_GENERIC_NETWORK_ONLY
NETWORK_OWNER_MODULE=chigamec.dll
SESSION_HANDOFF_MODULE=UNKNOWN
NETWORK_WRAPPER_RVA=0x0000100C
USEITEM_CANDIDATE_RVA=UNKNOWN
OBJECT_ID_SOURCE=UNKNOWN
OBJECT_ID_PROVEN=NO
ITEM_SPECIFIC_ACTION_PROVEN=NO
NATIVE_SEND_PATH_PROVEN=NO
CALLING_CONVENTION=STDCALL_OR_CALLEE_CLEANUP (chigamec.dll+0x0000100C); CDECL (Lineage.exe+0x00084110)
CALLER_DEPTH_USED=1
V18_SEND_CALLER_EVIDENCE=PASS_SEND_WRAPPERS_NO_0x5E_YET; 3 xrefs; 8 direct callers; 0 immediate 0x5E hits
V18_LOCAL_IMPORT_EVIDENCE=NO_LOCAL_WRAPPER_IMPORTS; LIBEAY32_IMPORTED=0; LOCAL_IAT_XREF_COUNT=0
AUTO_POTION_BLOCKER_OBJECT_ID=YES
AUTO_POTION_BLOCKER_NATIVE_USEITEM=YES
WP7_NATIVE_USEITEM_PASS=NO
MEMORY_WRITE=NO
PACKET_SEND=NO
NEXT=Read-only no action -> manual potion use -> no action capture at chigamec.dll+0x0000100C and Lineage.exe+0x00084110; record entry arguments/bounded buffers, then trace only a manual-use-correlated runtime caller one level back to Lin.bin2.
