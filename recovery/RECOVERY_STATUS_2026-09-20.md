# Recovery Status — 2026-09-20

## Baseline

- Repository: `8xbwvwd8c9-code-taka/L1JTW8.5`
- Recovery branch: `analysis/l1jtw85-recovery`
- Original baseline remains on `main`.
- Server JAR: `l1jserver2.jar`
- Bytecode generation: Java 8 / class major version 52.
- Obfuscation: package/class/member names renamed.
- Whole-JAR encryption: not observed.
- Preserved evidence: `SourceFile`, line-number metadata, local-variable metadata in sampled classes, plaintext config strings.

## Completed

- WP0 bytecode/source identity inventory — PASS
- WP1 application-core filter — PASS
  - 1,765 total mapping rows
  - 788 application-core class mappings
  - 782 unique source filenames
- Package-level classification — PASS
- High-value donor target list — PASS
- Local DB table-index extraction tool — READY
- CFR full-decompile wrapper — READY
- Targeted javap extraction tool — READY

## Confirmed identities

- `l1j.server.a -> Config.java`
- `l1j.server.b -> DatabaseFactory.java`
- `ai.c -> GameServer.java`
- `aj.* -> client packet family`
- `ao.* -> DB/data table family`
- `ap.* -> runtime instance family`
- `aq.* -> core model family`
- `ar.h -> L1WarriorClassFeature.java`
- `be.* -> server packet family`
- `bf.* -> skill executor/effect family`
- `bj.e -> Opcodes.java`

## Pending evidence

1. Targeted javap corpus for the high-value list.
2. CFR decompile output under `recovered-src-obf/`.
3. `db_table_index.csv` generated from `db/8.5.sql`.
4. Semantic verification of Warrior skill IDs 228-231.
5. Dependency/caller maps for Warrior, Craft, Teleport, Item/Attribute/Rune and SoulTower.
6. Compile triage after decompilation.

## Safety boundary

Recovery only. No gameplay logic or DB behavior changes in this branch until a recovered source baseline is readable and dependency-checked.
