# BuffState Mapping A2A — 2026-09-24

## GOAL
Advance functional target `P2=AUTO_BUFF` by mapping a trustworthy read-only source of active 850 buff/skill-state IDs for `IBuffStateBridge`.

This task is intentionally independent from the UseItem/native-send task. Do not solve SkillUse sending here. The deliverable is the smallest reliable source that lets `AutoBuffController` answer: "is selected skill X currently active?"

Current code state:

```text
BuffStateBridge=UNMAPPED
SkillUseBridge=UNMAPPED
SkillUseProtocol logical opcode=0x80
AutoBuffController already exists and is blocked by BuffState + native SkillUse
```

Relevant implementation:

- `launcher/850Launcher/BuffStateBridge.cs`
- `launcher/850Launcher/SkillCatalog.cs`
- `launcher/850Launcher/SkillUseProtocol.cs`
- `launcher/850Launcher/SkillUseProtocolControl.cs`
- `launcher/850Launcher/NativeReceiveFlowScanner.cs`
- `launcher/850Launcher/NativeReceiveFlowControl.cs`
- `launcher/850Launcher/NativeReceiveXrefScanner.cs`
- `launcher/850Launcher/RuntimeMap.cs`
- `launcher/850Launcher/RuntimeBridge.cs`

## MUST
- English only.
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.
- Read branch `README.md` Mission first.
- Use 850 client/runtime authority only.
- Keep the task scoped to the `AUTO_BUFF` blocker: active buff state.
- First classify what evidence already exists in the listed files and docs before proposing new runtime work.
- Prefer one of these bounded proof routes:
  1. a stable player/status object field or collection containing active skill IDs;
  2. a stable client receive/update handler that inserts/removes buff IDs into a known state object;
  3. a narrow UI/status-icon model only if it directly stores stable skill IDs and is not merely presentation/resource state.
- Record exact evidence for every candidate:
  - owner/source object
  - RVA or field offset
  - element/value width
  - add/update/remove semantics
  - skill ID provenance
  - whether IDs match `SkillCatalog` IDs
- Distinguish:
  - active skill ID
  - UI icon/resource ID
  - timer ID
  - packet opcode/subcommand
  - generic control ID
- If a candidate is a collection, prove at least add/remove or state transition semantics; shape alone is insufficient.
- If runtime validation is necessary, require only exact-target/bounded read-only validation. A simple before/after manual buff transition is acceptable only when the exact candidate field/object is already known.
- Produce a minimal implementation contract for a future mapped `IBuffStateBridge`:

```text
Read(runtime) -> HashSet<int> ActiveSkillIds
```

- State clearly whether restart stability has been proven or still required.

## DO NOT
- Do not implement or execute native SkillUse.
- Do not send packets.
- Do not write target memory.
- Do not patch/hook the client.
- Do not heap scan.
- Do not `MEM_PRIVATE` scan.
- Do not broad-scan process memory for skill IDs.
- Do not reopen Inventory UI/container reverse engineering.
- Do not assume a UI icon ID equals a SkillId.
- Do not import 381/880 buff addresses.
- Do not declare BuffState mapped from one coincidental value match.
- Do not mark WP9 PASS.

## VALIDATE
A promotable BuffState source must prove all applicable gates:

```text
850_AUTHORITY=YES
READ_ONLY=YES
OWNER_OR_UPDATE_PATH_PROVEN=YES
SKILL_ID_ROLE_PROVEN=YES
ACTIVE_INACTIVE_TRANSITION_PROVEN=YES
UI_RESOURCE_ID_CONFUSION_EXCLUDED=YES
```

Preferred strongest proof:

```text
manual buff becomes active
 -> exact known update handler/state field changes
 -> SkillId appears as active
 -> buff expires/cancels
 -> same source removes/deactivates SkillId
```

If a stable source is found, specify exact data layout and safe read algorithm. If not, return exactly one bounded next capture target (one function RVA, field, or object), not a broad discovery plan.

## FINAL
Create:

`launcher/850Launcher/BUFF_STATE_MAPPING_TRACE_20260924.md`

End exactly with:

```text
STATUS=<PASS_BUFF_STATE_CANDIDATE | PASS_UI_ONLY | PARTIAL_BUFF_STATE_TRACE | BLOCKED>
FUNCTIONAL_GOAL=AUTO_BUFF
CURRENT_BLOCKER=BUFF_STATE
BEST_SOURCE=
BEST_SOURCE_EVIDENCE=
OWNER_PROVEN=<YES|NO>
SKILL_ID_ROLE_PROVEN=<YES|NO>
ACTIVE_TRANSITION_PROVEN=<YES|NO>
INACTIVE_TRANSITION_PROVEN=<YES|NO>
UI_RESOURCE_CONFUSION_EXCLUDED=<YES|NO>
RESTART_STABLE=<YES|NO|NOT_TESTED>
MAPPED_BRIDGE_READY=<YES|NO>
SKILL_USE_BRIDGE=UNMAPPED
MEMORY_WRITE=NO
SEND_PACKET=NO
WP9=NOT_YET
NEXT=
```