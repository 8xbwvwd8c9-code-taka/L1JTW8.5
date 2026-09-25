# L1JTW8.5 Fast Dev — Core Architecture / Fusion Status

Date: 2026-09-25
Branch: `work/l1jtw85-fast-dev-build`
Fusion implementation commit: `80a72442451f3a300a43dda13f2ba3bd984d1833`
Validation: GitHub Actions `L1JTW8.5 Fast Dev` Run #170 = PASS
Final completed core authority: `f27acba8917a2b261ed38125704bd866b00f9038`

## Final status

```text
STATUS=PASS
FAST_DEV_ARCHITECTURE=GREEN
COMPLETED_CORE_FUSION=PASS
APPLICATION_SOURCES=788
RUNTIME_APPLICATION_CLASSES=1109
FORMALLY_COMPLETED_SOURCES=77
DEPLOYABLE_COMPLETED_SOURCES=77
DEFERRED_COMPLETED_SOURCES=0
COMPLETED_OVERLAY_CLASSES=116
REAL_BOOTSTRAP=PASS
MYSQL57_IMPORT=PASS
DB_TABLES=99
DB_BACKED_RUNTIME_SMOKE=PASS
SERVER_PROCESS=ALIVE
PORT_2000=LISTENING
PRODUCTION_JAR_IMMUTABLE=PASS
```

All formally completed core repairs are now deployable in the Fast Dev semantic runtime. There are no remaining completed-repair identities deferred to original production bytecode.

## Authority model

```text
completed/l1jtw85-decompiled recovery baseline
        +
completed/l1jtw85-core-fixes formal promotion scopes
        ↓
completed-authority-core
        ↓ Java 8 deployability gate by atomic promotion scope
        ↓
completed repair overlay
        ↓
runtime-active-authority-core
        ↓
core/src + incremental state + Fast Dev runtime
```

Rules:

1. `completed/l1jtw85-core-fixes` is the only repaired-core authority.
2. `work/l1jtw85-core-fixes` remains quarantine and never enters active runtime directly.
3. Multi-file and overlapping promotions are compiled as atomic source scopes.
4. Partial promotion scopes never publish into runtime.
5. A seeded workspace remains pinned during normal build/run cycles.
6. Explicit `-Sync` is the refresh boundary for an existing local workspace.
7. `l1jserver2.jar` is immutable and is never overwritten by Fast Dev.

## Final completed authority

Run #170 clean-bootstrap pinned:

```text
AUTHORITY=f27acba8917a2b261ed38125704bd866b00f9038
COMPLETED_SOURCES=77
DEPLOYABLE=77
DEFERRED=0
RUNTIME_CLASSES=1109
COMPLETED_OVERLAY_CLASSES=116
```

The second bootstrap in the same seeded workspace returned:

```text
CACHE_HIT=TRUE
CORE=preserved
BUILD=PASS MODE=noop CLASSES=0
```

The completed branch advanced beyond the older `3e932ecae8050fa56962129683ab0dfa7d51c4a8` authority used by Run #168. The final fusion also absorbs the completed Batch E core state, including:

```text
l1j/server/clientpackets/C_DeleteChar
l1j/server/datatables/CharacterTable
l1j/server/datatables/SkillsTable
```

## Final missing-core closure

Run #168 had four completed identities still deferred by recovery/decompiler source artifacts:

```text
l1j/server/clientpackets/C_ItemUSe
l1j/server/datatables/ShopTable
l1j/server/model/instance/L1NpcInstance
l1j/server/model/timer/HomeTownTimer
```

Fast Dev now restores only donor-proven compile metadata/type information for these identities:

```text
C_ItemUSe    = L1Object overload-disambiguating casts
ShopTable    = collection element types + decompiler synthetic Comparator bridge shape
L1NpcInstance = ground-inventory collection types + int[] path queue type
HomeTownTimer = Collection<L1PcInstance>
```

These restorations do not invent gameplay behavior. They recover type/cast information preserved by the repaired obfuscated donor and lost by decompilation.

Regression validation used a RED/GREEN cycle:

```text
RED  commit 92f55ce6e20c697fd76d1404a7302772f01b7cc5
     Run #169 = expected failure
     21 bootstrap tests / exactly 4 new failures

GREEN commit 80a72442451f3a300a43dda13f2ba3bd984d1833
      Run #170 = PASS
      21 bootstrap tests = PASS
      REAL_BOOTSTRAP = PASS
      DEPLOYABLE = 77/77
      DEFERRED = 0
```

## Runtime gate

Run #170 imported the real `db/8.5.sql` into MySQL 5.7:

```text
FAST_DEV_DB_TABLES=99
```

Fast Dev then started the semantic runtime with:

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

Observed result:

```text
FAST_DEV_RUNTIME_SMOKE=PASS
JAVA_PROCESS=ALIVE
PORT_2000=LISTENING
c3p0-0.9.5.2 initialized
maps loaded
sprList.size=16147
mobs spawned
server initialization completed
waiting for client connection
```

Production JAR SHA-256 remained:

```text
8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
```

## Local workspace refresh

A pre-existing local `I:\L1JTW8.5` workspace may still be pinned to an older completed authority. Refresh it explicitly with:

```powershell
.\build850.ps1 -Sync
```

Normal build/run intentionally does not chase a moving authority. A clean bootstrap already resolves the final completed authority shown above.
