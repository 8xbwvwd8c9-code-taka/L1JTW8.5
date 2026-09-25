# L1JTW8.5 Fast Dev — Core Architecture / Fusion Status

Date: 2026-09-25
Branch: `work/l1jtw85-fast-dev-build`
Milestone commit: `027ecc0702d8b38d915deca83a6c7b6eb5df8e4f`
Validation: GitHub Actions `L1JTW8.5 Fast Dev` Run #137 = PASS
Active-authority validation: `L1JTW8.5 Fast Dev Active Authority` Run #5 = PASS

## Status

```text
STATUS=PASS
FAST_DEV_ARCHITECTURE=GREEN
REAL_BOOTSTRAP=PASS
MYSQL57_IMPORT=PASS
DB_TABLES=99
DB_BACKED_RUNTIME_SMOKE=PASS
SERVER_PROCESS=ALIVE
PORT_2000=LISTENING
PRODUCTION_JAR_IMMUTABLE=PASS
```

## Authority model now in force

```text
recovery baseline
        +
completed/l1jtw85-core-fixes formal promotion scopes
        ↓
completed-authority-core
        ↓ Java 8 deployability gate by atomic promotion scope
        ├─ deployable completed scopes → completed repair overlay
        └─ deferred scopes → original semantic runtime class remains active
        ↓
runtime-active-authority-core
        ↓
core/src + incremental state + Fast Dev runtime
```

Rules:

1. `work/l1jtw85-core-fixes` is quarantine and never becomes runtime authority directly.
2. Only formal promotions on `completed/l1jtw85-core-fixes` are eligible.
3. Multi-file / overlapping promotions are merged into atomic source scopes before `javac`.
4. A promotion scope is published atomically; a partial scope never enters runtime.
5. Deferred completed repairs fall back to the relocated original runtime class instead of breaking bootstrap.
6. A seeded workspace remains pinned to its current authority during normal build/run cycles.
7. Only explicit `-Sync` refreshes the latest completed authority and performs the guarded three-way working-source sync.
8. `l1jserver2.jar` is never overwritten by Fast Dev.

## Real bootstrap result

Pinned completed authority used by Run #137:

```text
3e932ecae8050fa56962129683ab0dfa7d51c4a8
```

Result:

```text
APPLICATION_SOURCES=788
RUNTIME_APPLICATION_CLASSES=1109
FORMALLY_COMPLETED_SOURCES=39
DEPLOYABLE_COMPLETED_SOURCES=9
DEFERRED_COMPLETED_SOURCES=30
COMPLETED_OVERLAY_CLASSES=14
```

Deployable completed identities:

```text
l1j/server/DatabaseFactory
l1j/server/datatables/AccountTable
l1j/server/datatables/CharBuffTable
l1j/server/datatables/CharacterGiftTable
l1j/server/datatables/FurnitureSpawnTable
l1j/server/datatables/InnTable
l1j/server/datatables/WeaponSkillTable
l1j/server/model/item/action/FurnitureItem
l1j/server/templates/L1BookMark
```

The second bootstrap in the same seeded workspace stayed on the exact same authority and returned:

```text
CACHE_HIT=TRUE
CORE=preserved
BUILD=PASS MODE=noop CLASSES=0
```

This closes the previous race where the completed branch could advance between two bootstrap calls and silently move the runtime authority while preserving older working source.

## Runtime gate

Run #137 imported the real `db/8.5.sql` into MySQL 5.7:

```text
FAST_DEV_DB_TABLES=99
```

Then Fast Dev started the server with the semantic runtime classpath and production-compatible verifier policy:

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

Observed runtime result:

```text
FAST_DEV_RUNTIME_SMOKE=PASS
JAVA_PROCESS=ALIVE
PORT_2000=LISTENING
c3p0-0.9.5.2 initialized
maps loaded
mobs spawned
server initialization completed
waiting for client connection
```

Production JAR SHA-256 remained:

```text
8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
```

## Deferred completed repairs

The following 30 formally completed source identities are **not discarded**. They remain recorded as completed authority but are not activated until their complete promotion scope passes the Java 8 semantic compile gate:

```text
l1j/server/clientpackets/C_Attr
l1j/server/clientpackets/C_BanClan
l1j/server/clientpackets/C_Deposit
l1j/server/clientpackets/C_Drawal
l1j/server/clientpackets/C_Mail
l1j/server/clientpackets/C_NpcAction
l1j/server/clientpackets/C_PledgeWatch
l1j/server/clientpackets/C_ProtoBuffers
l1j/server/clientpackets/C_Result
l1j/server/clientpackets/C_Shop
l1j/server/clientpackets/C_ShopWorld
l1j/server/datatables/CastleTable
l1j/server/datatables/CharacterItemTable
l1j/server/datatables/CharacterMobsTable
l1j/server/datatables/CharacterMobsWeekTable
l1j/server/datatables/CharacterTable
l1j/server/datatables/ClanMembersTable
l1j/server/datatables/ClanTable
l1j/server/datatables/LuckyDrawTable
l1j/server/datatables/PetTable
l1j/server/datatables/QuestNewTable
l1j/server/datatables/ShopTable
l1j/server/datatables/ShopWorldTable
l1j/server/datatables/SoulTowerTable
l1j/server/model/L1Master
l1j/server/model/instance/L1PetInstance
l1j/server/model/inventory/L1Inventory
l1j/server/model/inventory/L1PcInventory
l1j/server/model/timer/CurrentTimeReseter
l1j/server/model/timer/HomeTownTimer
```

Major blocker families seen in the deployability gate include:

```text
ClanMembersTable missing-symbol dependency family
CastleTable missing-symbol dependency family
C_ProtoBuffers / protobuf call-shape family
C_Result / C_ShopWorld raw Object-to-generic type family
L1Inventory / L1PcInventory decompiler generic / signature family
L1PetInstance override-signature family
ShopTable Comparator generic family
HomeTownTimer raw Object-to-L1PcInstance family
```

These are now **core/source repair blockers, not Fast Dev architecture blockers**. They must be repaired/promoted in the core-fix authority; Fast Dev must not locally invent fixes for them.

## Next integration rule

When completed core repair authority advances:

```powershell
.\build850.ps1 -Sync
```

must be the explicit refresh boundary. The sync path refreshes completed authority, rebuilds promotion scopes, re-runs the deployability gate, updates runtime-active authority, and applies a guarded three-way source sync. Normal build/run does not chase the moving completed branch.
