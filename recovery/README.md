# L1JTW8.5 Recovery

Branch: `analysis/l1jtw85-recovery`  
Baseline: `main@272a64fd9cc8bdfec706ed7654d2425889541850`

## Scope

Recover and classify the obfuscated Java 8 server core in `l1jserver2.jar` without changing game behavior.

Current evidence:

- JAR is valid Java bytecode, not a whole-file encrypted container.
- Class/package names are heavily obfuscated.
- `SourceFile`, line number metadata, and many constant strings are preserved.
- `class_source_mapping.csv` contains 1,765 class/source mappings.
- Application core filter currently yields **788 classes / 782 unique source filenames**.
- Third-party classes (MySQL, c3p0, Lombok and related dependencies) are excluded from `core_class_map.csv`.

## Recovery rules

1. Preserve `main` as the original runnable baseline.
2. Do all recovery work on `analysis/l1jtw85-recovery`.
3. Do not rename obfuscated bytecode identities in the first recovered-source pass.
4. Keep `obfuscated class -> SourceFile` mapping authoritative where metadata is present.
5. Separate evidence from inference:
   - `SOURCEFILE_CONFIRMED`: original source filename preserved in class metadata.
   - package/category labels are analyst classifications.
6. Do not modify DB/core behavior during recovery.
7. First target: readable decompile. Second target: compile-equivalent recovered source.
8. Third-party library sources are not part of the L1J recovery target.

## Key confirmed targets

| Obfuscated | Original source |
|---|---|
| `l1j.server.a` | `Config.java` |
| `l1j.server.b` | `DatabaseFactory.java` |
| `ai.c` | `GameServer.java` |
| `ai.e` | `PacketHandler.java` |
| `aj.az` | `C_ItemUSe.java` |
| `ao.s` | `CraftListTable.java` |
| `ao.ah` | `ItemTable.java` |
| `ao.be` | `SkillsTable.java` |
| `ao.bf` | `SoulTowerTable.java` |
| `ap.q` | `L1ItemInstance.java` |
| `ap.u` | `L1PcInstance.java` |
| `aq.c` | `L1Attack.java` |
| `aq.k` | `L1Craft.java` |
| `aq.m` | `L1EquipmentSlot.java` |
| `aq.w` | `L1Magic.java` |
| `aq.am` | `L1Teleport.java` |
| `ar.h` | `L1WarriorClassFeature.java` |
| `be.bj` | `S_ItemAttribute.java` |
| `be.bk` | `S_ItemColor.java` |
| `be.dn` | `S_RuneSlot.java` |
| `be.ek` | `S_Teleport.java` |
| `bj.e` | `Opcodes.java` |

## Work units

- WP0 — bytecode/source identity inventory: **PASS**
- WP1 — core-only mapping + package classification: **PASS**
- WP2 — DB table inventory: script ready; output pending
- WP3 — full decompile to `recovered-src-obf/`: pending
- WP4 — compile triage / missing dependencies: pending
- WP5 — semantic package restoration: pending
- WP6 — donor extraction (Warrior/Craft/Item/Rune/SoulTower/Teleport): pending

## Files

- `core_class_map.csv` — 8.5 application-core mapping only.
- `PACKAGE_MAP.md` — package-level classification.
- `../tools/recovery/extract-db-table-index.ps1` — local SQL table index extraction.
- `../tools/recovery/decompile-cfr.ps1` — CFR wrapper for full decompilation.
