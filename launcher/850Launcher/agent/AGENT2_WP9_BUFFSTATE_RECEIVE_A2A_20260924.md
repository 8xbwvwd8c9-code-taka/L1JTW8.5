# AGENT2_WP9_BUFFSTATE_RECEIVE_A2A_20260924

## GOAL

Advance **P2 AUTO_BUFF** independently from WP7 by locating the minimum authoritative 8.50c runtime source for active Buff/Skill state:

```text
selected SkillId
-> authoritative active/inactive state
-> BuffStateSnapshot.ActiveSkillIds
-> BuffStateBridge.Read(...)
```

Do not implement automatic casting yet.

Current authority:

```text
BRANCH=work/850-inventory-helper
TARGET=8.50c
CLIENT=Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SERVER_SKILL_OPCODE=0x80
BUFF_STATE=UNMAPPED
SKILL_USE_NATIVE_PATH=UNMAPPED
```

## MUST

Read first:

- `README.md`
- `launcher/850Launcher/agent/WP9_BUFFSTATE_MAPPING_A2A_20260924.md`
- `launcher/850Launcher/BuffStateBridge.cs`
- `launcher/850Launcher/SkillCatalog.cs`
- `launcher/850Launcher/SkillUseProtocol.cs`
- `launcher/850Launcher/RuntimeMap.cs`
- `launcher/850Launcher/RuntimeBridge.cs`
- `launcher/850Launcher/NativeReceiveXrefScanner.cs`
- `launcher/850Launcher/NativeReceiveFlowScanner.cs`
- `launcher/850Launcher/NativeReceiveFlowControl.cs`
- `launcher/850Launcher/AutoFeatureMatrix.cs`
- `launcher/850Launcher/skill-names.csv`

1. Reuse existing receive-side evidence first. Prefer receive/update state over UI presentation.
2. Begin from proven/known buff-related receive hints only as ranking evidence; packet opcode/icon hits alone are not BuffState proof.
3. Identify the smallest candidate state owner updated by a receive/event path.
4. For each candidate determine:
   - owner/global/pointer provenance
   - module + RVA of update/mutation function if provable
   - state key type: SkillId, icon id, spell id, timer id, resource id, or other
   - active/inactive representation
   - duration/expiry representation if present
   - translation from stored key to authoritative SkillId
5. If UI status icons are the only lead, trace backward to the state/model owner and stop using UI visibility as soon as an authoritative owner is found.
6. Validate one known buff with a controlled transition:

```text
inactive baseline
-> manual normal-game cast/receive
-> candidate appears/changes
-> expire/cancel
-> candidate disappears/reverts
```

7. Keep runtime work exact-target only. If no state owner is proven, return at most 1-3 exact module+RVA candidates for a controlled transition probe.
8. The required product output is only enough information to populate `BuffStateSnapshot.ActiveSkillIds`; a full timer/status UI is out of scope.

## DO NOT

- Do not work on WP7, UseItem, ObjectId, Inventory, or AutoPotion.
- Do not implement or invoke SkillUse.
- Do not send skill packets.
- Do not write target process memory.
- Do not hook or patch the client.
- Do not run heap-wide or MEM_PRIVATE-wide scans.
- Do not broadly scan every UI object/control.
- Do not use 381/880 runtime addresses as 850 authority.
- Do not assume icon/resource id equals SkillId.
- Do not mark BuffStateBridge ready from one screenshot or one UI-visible state.
- Do not modify Agent1 result/tooling files.
- Do not modify shared launcher source unless required for a bounded read-only proof; prefer a new isolated probe/tool and the result document.

## VALIDATE

A candidate can be promoted only when a controlled real-buff transition proves the state changes with the buff and reverses on expiry/cancel.

Minimum evidence:

```text
state owner
state key
active representation
key -> SkillId mapping
controlled transition
```

Preferred stronger evidence:

```text
update function RVA
receive/event provenance
restart-stable owner/fingerprint
duration/expiry source
```

If controlled transition is missing, keep `BUFFSTATE_BRIDGE_READY=NO` and return the exact smallest bounded next probe.

Before completion run source audit/tests relevant to any changed probe/tool files.

## FINAL

Create only:

`launcher/850Launcher/agent/AGENT2_WP9_BUFFSTATE_RECEIVE_RESULT_20260924.md`

End exactly with:

```text
STATUS=
BUFF_STATE_OWNER=
BUFF_STATE_OWNER_PROVEN=
STATE_KEY_TYPE=
SKILL_ID_DIRECT=
SKILL_ID_TRANSLATION=
ACTIVE_REPRESENTATION=
DURATION_SOURCE=
UPDATE_FUNCTION_RVA=
RECEIVE_PROVENANCE=
CONTROLLED_TRANSITION_PROVEN=
RESTART_STABLE=
BUFFSTATE_BRIDGE_READY=NO
SKILLUSE_BRIDGE_READY=NO
MEMORY_WRITE=NO
PACKET_SEND=NO
NEXT=
```

Allowed STATUS:

```text
PASS_BUFFSTATE_CANDIDATE
PARTIAL_BUFFSTATE_MAPPING
PASS_UI_PRESENTATION_ONLY
BLOCKED
```
