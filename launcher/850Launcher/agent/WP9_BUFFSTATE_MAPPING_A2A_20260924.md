# WP9_BUFFSTATE_MAPPING_A2A_20260924

## GOAL

Advance **P2 AUTO_BUFF** by locating the authoritative 8.50c client source of currently active Buff/Skill status.

This task is intentionally independent from WP7 UseItem.

Primary question:

```text
How can the helper determine whether a selected buff skill is currently active,
using a stable 8.50c runtime source?
```

Target output should support:

```text
selected SkillId
 -> authoritative active/inactive state
 -> BuffStateBridge.Read(...)
```

Do NOT implement automatic skill casting yet.

## MUST

Read first:

- `README.md`
- `launcher/850Launcher/BuffStateBridge.cs`
- `launcher/850Launcher/SkillCatalog.cs`
- `launcher/850Launcher/SkillUseProtocol.cs`
- `launcher/850Launcher/RuntimeMap.cs`
- `launcher/850Launcher/RuntimeBridge.cs`
- `launcher/850Launcher/AutoFeatureMatrix.cs`
- `launcher/850Launcher/MainForm.cs`
- `launcher/850Launcher/skill-names.csv`

Authority:

```text
TARGET=8.50c
CLIENT=Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SERVER_LOGICAL_SKILL_OPCODE=0x80
BUFF_STATE=UNMAPPED
SKILL_USE_NATIVE_PATH=UNMAPPED
```

Important separation:

```text
BuffState mapping
!=
SkillUse native bridge
```

Task:

1. Find existing client/runtime evidence for buff/skill status before designing any new probe:
   - status icons
   - active skill arrays/maps
   - timers/durations
   - client receive handlers updating status
   - UI controls that consume already-decoded buff state
   - existing runtime-map fields/evidence

2. Prefer a **receive/update-side source** over UI presentation state.

3. If UI status icons are used as a lead:
   - trace backward to the model/state owner
   - do not treat icon existence/visibility itself as final BuffState proof.

4. For each candidate state source determine:
   - stable owner/global/pointer provenance
   - element/key type
   - whether key maps to SkillId, icon id, spell id, timer id, or UI control id
   - active/inactive representation
   - duration/expiry if present
   - mutation/update function
   - receive-handler or event source if provable

5. Check whether a known buff can provide a controlled validation:
   - inactive baseline
   - manually cast/receive buff
   - state appears
   - expiration/removal
   - state disappears

6. Keep any runtime probe bounded to exact candidate owner/field/handler.
   - exact-target only
   - no broad process scan

7. Return the minimum mapping needed for:

```csharp
BuffStateSnapshot.ActiveSkillIds
```

A full buff timer UI is not required.

8. If `SkillId` is not stored directly:
   - identify the stable translation key
   - document mapping path to `SkillId`
   - do not silently assume icon/resource id equals SkillId

## DO NOT

- Do not work on UseItem/ObjectId.
- Do not implement or invoke SkillUse.
- Do not send skill packets.
- Do not write target process memory.
- Do not patch/hook the client.
- Do not perform heap-wide scans.
- Do not perform MEM_PRIVATE-wide scans.
- Do not broadly scan all UI controls.
- Do not use 381/880 runtime addresses as 850 authority.
- Do not equate icon/resource ID with SkillId without evidence.
- Do not mark BuffStateBridge mapped from a single screenshot/UI state.
- Do not modify unrelated launcher functionality.

## VALIDATE

Promotion requires a controlled state transition tied to a real buff:

```text
BEFORE:
known buff inactive

ACTION:
manual normal game cast/receive

AFTER:
candidate state appears/changes

EXPIRE/CANCEL:
candidate state disappears/reverts
```

Evidence should identify at least:

```text
state owner
state key
active representation
how to resolve key -> SkillId
```

Stronger evidence additionally includes:

```text
update function RVA
receive/event provenance
duration/expiry source
restart-stable owner/fingerprint
```

Do not mark final PASS from UI visibility alone.

If not enough evidence exists, give one exact bounded NEXT probe.

## FINAL

Create:

`launcher/850Launcher/agent/WP9_BUFFSTATE_MAPPING_RESULT_20260924.md`

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

Do not modify unrelated files.
