# 381 -> 850 Drop Broadcast Audit

## Scope

Primary 381 module:
- `w_掉落廣播系統`

Required companion data:
- `廣播_掉寶_顯示`

Runtime:
- `com.lineage.server.datatables.ItemMsgTable`
- `com.lineage.config.ConfigDrop`
- drop distribution hook in `com.lineage.server.model.drop.DropShare`

## Current content

`w_掉落廣播系統` contains a large whitelist of item IDs whose successful monster-drop acquisition may produce a global announcement.

The table content includes legacy equipment, spellbooks, crystals, event items, world-boss boxes and custom items.

The current source artifact is INSERT-only:

```text
(itemid, 名稱)
```

No authoritative CREATE schema/key/index definition is present.

### Companion message table

Current split file:

```text
DB/381_DB_AI用/_廣播_掉寶_顯示__202609221205.sql
```

contains one visible message row:

```text
類型=2
訊息內容=恭喜玩家【%s】拿【%s】擊敗【%s】獲得【%s】我就是那上天眷顧的人
```

The INSERT column list does NOT include the runtime-required `代號` column.

## Whitelist loader

`ItemMsgTable.load()` queries:

```sql
SELECT * FROM w_掉落廣播系統
```

and stores each `itemid` in an in-memory ID list.

Runtime use is simply:

```java
ItemMsgTable.get().contains(item.getItemId())
```

## Drop hook semantics

The actual trigger is in `DropShare`, during monster-drop distribution to a player.

The relevant behavior requires:

```text
item ID is whitelisted
AND player has a weapon
AND player is not GM
AND player has skill effect 1691
```

Only then:

```java
ConfigDrop.msg(
  player.getName(),
  npc.getName(),
  item.getName(),
  player.getWeapon().getLogName()
);
```

A record/audit call is also made in the same branch.

Therefore this module is NOT simply:

```text
rare item generated -> announce
```

It is:

```text
eligible monster drop distributed to player
-> whitelist check
-> player state/effect gate
-> broadcast
```

This distinction must be preserved or deliberately redesigned.

## Announcement template loader

`ConfigDrop.load()` queries:

```sql
SELECT * FROM 廣播_掉寶_顯示
```

It reads:
- `代號`
- `類型`
- `訊息內容`

and only accepts rows where:

```text
代號 > 5
```

Current split INSERT omits `代號`.

Therefore:

```text
MESSAGE_ROW_ID_AUTHORITY=NOT_PROVEN
```

Without the authoritative CREATE schema / auto-increment state, the visible message row cannot be proven to load at runtime.

## Message formatting

`ConfigDrop.msg(...)` randomly chooses one loaded message template.

Supported types:

```text
type 0: player, mob, item
type 1: mob, player, item
type 2: player, weapon, mob, item
type 3: mob, player, weapon, item
```

Current visible row uses:

```text
type=2
```

so it expects four placeholders:

```text
player
weapon
mob
item
```

## Output packets

The output mode is controlled by:

```text
ConfigOther.dropmsg
```

If zero:
- `S_BoxMessage`

Otherwise:
- `S_PacketBoxGree`
- plus `S_ServerMessage`

No custom binary client protocol is proven.

## Critical donor defects / hazards

### 1. Name-normalization condition is permanently mismatched

`ItemMsgTable.load()` checks:

```java
if (!note.contains("=>")) {
    updata_name(item_id);
}
```

But both current DB data and the updater itself use:

```text
公告->
```

not:

```text
=>
```

The updater writes:

```java
"公告->" + itemname
```

Therefore the condition remains true on every later reload/startup.

Result:

```text
unnecessary UPDATE for every whitelist row on every load/reload
```

Target migration should remove this DB mutation entirely.

### 2. Loader mutates configuration data

`updata_name(...)` performs:

```sql
UPDATE w_掉落廣播系統 SET 名稱=? WHERE itemid=?
```

The `名稱` field is not required for the actual whitelist decision.

Configuration loaders should not rewrite production rows as a side effect.

### 3. Missing item template can throw before SQL error handling

`updata_name(...)` evaluates:

```java
ItemTable.get().getTemplate(item_id).getName()
```

before entering its SQL try/catch.

If an item ID is invalid, a null dereference can escape the method instead of producing a controlled validation error.

Target should validate all mapped item IDs at startup.

### 4. Whitelist lookup uses ArrayList

`contains(itemId)` is linear over the whitelist.

Current content size is still manageable, but target should use an immutable `Set<Integer>`.

### 5. Announcement depends on effect 1691

The announcement feature is gated by:

```text
player.hasSkillEffect(1691)
```

The ownership/source of this effect must be proven before migration.

Do not silently remove this gate or assume every player should receive announcement behavior.

### 6. Player must have a weapon

The donor additionally requires:

```text
player.getWeapon() != null
```

because current message type can include weapon text.

Unarmed players can therefore obtain a whitelisted drop without triggering the global announcement.

This is proven donor behavior.

### 7. Broadcast occurs in drop-sharing path

Porting this hook into item creation instead of final drop distribution can change semantics for:
- ground drops
- party distribution
- auto-loot
- restricted/filtered drops
- inventory-full fallback

Target should hook after successful recipient resolution.

## Client dependency

Proven output uses existing generic message packets.

```text
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=NO_PROVEN_CUSTOM_RESOURCE
```

No custom HTML/menu dependency exists.

## 850 comparison

Targeted 850 branch inspection found generic announcement infrastructure but no proven equivalent of:
- DB item whitelist
- drop-recipient hook
- effect-1691 gate
- random formatted drop templates
- weapon-inclusive drop message modes

Therefore:

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

850 generic broadcast packets are useful primitives, but not an equivalent framework.

## Migration shape

Prefer a small target-native service:

```text
drop-broadcast-core/
  immutable item whitelist
  message template loader
  recipient/drop event adapter
  eligibility policy
  formatter
  broadcaster
```

Recommended event contract:

```text
onSuccessfulMonsterDropRecipient(
  player,
  monster,
  item,
  sourceContext
)
```

Then:
1. validate whitelist
2. apply explicit player/effect policy
3. select validated template
4. format
5. broadcast

Do not mutate DB rows during load.

## Difficulty

`LEVEL=L3`

Reason:
- needs a precise drop-distribution lifecycle hook
- two DB-backed rule/template sources
- player/effect eligibility
- broadcast formatting
- no custom client dependency

This is more than L2 data mapping because 850 has no proven equivalent runtime hook/framework.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_掉落廣播系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
COMPANION_DB=廣播_掉寶_顯示
DROP_HOOK=DropShare
TRIGGER=successful player drop distribution
WEAPON_REQUIRED=YES
EFFECT_GATE=1691
CLIENT_DEP=NO
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
MESSAGE_SCHEMA=NOT_PROVEN
MESSAGE_ROW_ID_AUTHORITY=NOT_PROVEN
DONOR_DB_MUTATION_ON_LOAD=YES
DONOR_NAME_CHECK_BUG=YES
BLOCKERS=schemas,item semantic mapping,effect-1691 ownership,message row identity,target drop-event adapter
```
