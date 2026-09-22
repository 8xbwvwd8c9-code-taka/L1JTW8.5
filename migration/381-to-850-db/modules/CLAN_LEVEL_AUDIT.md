# L381 `w_血盟等級` isolated migration audit

## Final classification

```text
STATUS=PASS
MODULE=w_血盟等級
LEVEL=L4
CORE_DEP=YES
DB_ALTERS=ADDITIVE: w_血盟等級, w_血盟能量怪物, clan_data level/contribution fields, player contribution fields
MONSTER_HOOK=L1MonsterInstance death path, approximately source lines 713-730
TARGET_DOC=migration/381-to-850-db/modules/CLAN_LEVEL_AUDIT.md
COMMIT=pending
BLOCKERS=850 clan_data lacks level/contribution columns; 850 has no verified equivalent level/contribution runtime; w_血盟能量怪物 package is empty; level-up conditions are external config; stat removal on clan leave is not proven
```

## Scope and sources

- L381 source: `I:\L381\Atu-381伺服器端主\src`
- L381 DB package: `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用`
- 850 DB authority: `I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB`
- 850 core authority: `origin/completed/l1jtw85-core-fixes`
- `w_血盟技能` is intentionally excluded. Its shared-looking `clanskill*` columns are not silently assigned to this module.

## DB schema requirements

### `w_血盟等級`

Source file: `DB\381_DB_AI用\w_血盟等級_202609221205.sql`.

The supplied rows define levels 1–10 and these columns:

```text
note, ClanLevel, Contribution,
AddMaxHp, AddMaxMp, AddDmg, AddBowDmg, AddHit, AddBowHit,
AddMr, AddSp, AddAc, AddFire, AddWind, AddEarth, AddWater,
AddStr, AddDex, AddCon, AddWis, AddInt, AddCha,
reduction_dmg, reduction_magic_dmg, ExpRate, AddHpr, AddMpr, AddWeight
```

`ClanOriginal.getData()` reads every field above. `Contribution` is loaded into the in-memory row but the inspected level-up path uses `ConfigClan.clansLevelUpCondition`, not this column, to decide the required clan energy. The SQL rows therefore provide reward/stat data; they do not by themselves prove level-up thresholds.

### `w_血盟能量怪物`

The specified file exists but is zero bytes:

`DB\381_DB_AI用\w_血盟能量怪物_202609221205.sql`.

The schema required by `NpcClanContribution.load()` is nevertheless exact:

```text
npcid, min_血盟能量, max_血盟能量
```

The loader executes `SELECT * FROM w_血盟能量怪物`, indexes rows by `npcid`, and returns a random score calculated as `min + Random.nextInt(max)` when the NPC is configured. With the supplied empty package, no monster receives a configured score.

### `clan_data` and player persistence

381 `clan_data_202609221205.sql` inserts/uses:

```text
clan_level, clan_contribution
```

`ClanTable.load/create/update` reads and writes both fields. `L1Clan` stores them as `_clanLevel` and `_clanContribution`.

The player-side contribution fields are separate from clan level:

```text
characters.PcContribution
characters.ClanContribution
characters.ClanNameContribution
```

`MySqlCharacterStorage` loads and saves all three. The monster hook updates player `PcContribution` and `ClanContribution`; the clan aggregate is persisted through `ClanTable.updateClan()`.

## Exact monster-kill contribution hook

The proven hook is `com.lineage.server.model.Instance.L1MonsterInstance`, in the monster death/kill handling block around lines 713–730:

1. Require `ClanContribution.START` and `pc.getClanid() != 0`.
2. Call `NpcClanContribution.get().get_score(this.getNpcId())`.
3. Skip if score is zero.
4. Skip when `pc.getQuest().get_step(8544) == 1`.
5. Resolve `WorldClan.get().getClan(pc.getClanname())`.
6. Add score to `clan.clanContribution`.
7. Add score to `pc.PcContribution` and `pc.ClanContribution`.
8. Store item `92164` with the same score.
9. Enforce/reset the daily player contribution cap using `ConfigClan.PcContribution` and quest `8544`.

The event initializer is `ClanContribution.execute()`: it sets `START=true`, loads `NpcClanContribution`, and initializes `ClanOriginal`. `GameServer` also calls `ClanOriginal.getInstance()` at startup. This is a runtime/core dependency, not a DB-only feature.

The `CalcExp` contribution code is a different hometown contribution path (`HomeTownID`, `Contribution`) and is not the clan energy hook audited here.

## Clan level-up and reward path

`L1ActionPc.clanLevel(pc, yy)` dispatches the level system:

- `yy == 1` → `leverUpClan(pc)`.
- `leverUpClan` checks clan membership, max level, and `checkClanLevelUpCondition`.
- Conditions come from `ConfigClan` and `./config/其他控制端/血盟等級設定表.properties`: `clanenergyN`, `clanadenaN`, and material IDs/counts.
- On success, `leverUpClan(pc, clan)` increments `clan_level`, subtracts required energy and adena, and calls `ClanTable.updateClan()`.
- The method sends level-up messages to the initiating player/online members. It does not call `ClanOriginal.forIntensifyArmor()` for every online member.

Other `yy` branches add clan energy/adena through configured contribution items/currency and persist the clan aggregate. These branches are part of the level/contribution module, not `w_血盟技能`.

`ClanOriginal.forIntensifyArmor(pc)` is called during login (`C_LoginToServer`) and applies the row whose `ClanLevel` equals the player clan level. This is the actual reward/stat application timing proven by source.

## Every player stat modified by clan level

`ClanOriginal.forIntensifyArmor` can modify these player stats from the `w_血盟等級` row:

| DB field | Player operation |
|---|---|
| `AddMaxHp` | `addMaxHp`, then current HP is increased by same amount |
| `AddMaxMp` | `addMaxMp`, then current MP is increased by same amount |
| `AddDmg` | `addDmgup` |
| `AddBowDmg` | `addBowDmgup` |
| `AddHit` | `addHitup` |
| `AddBowHit` | `addBowHitup` |
| `AddMr` | `addMr` |
| `AddSp` | `addSp` |
| `AddAc` | `addAc(-value)`; this is an AC reduction/improvement convention |
| `AddFire` / `AddWind` / `AddEarth` / `AddWater` | corresponding elemental resistance add |
| `AddStr` / `AddDex` / `AddCon` / `AddWis` / `AddInt` / `AddCha` | corresponding primary stat add |
| `reduction_dmg` | `addClan_ReductionDmg` |
| `reduction_magic_dmg` | `add_Clanmagic_reduction_dmg` |
| `ExpRate` | `addExpByArmor` |
| `AddHpr` / `AddMpr` | `addHpr` / `addMpr` |
| `AddWeight` | `addWeightReduction` |

After application, the source sends `S_SPMR`, `S_OwnCharStatus`, `S_OwnCharStatus2`, `S_HPUpdate`, and `S_MPUpdate`.

### Removal behavior

The inspected clan leave/ban paths reset player contribution bookkeeping fields and clan name tracking. A targeted search did not find a corresponding call that subtracts `ClanOriginal` stat effects when a player leaves a clan or when clan level decreases. The proven stat application is login-time additive. Therefore stat removal/recalculation on membership change is **not proven** and is a migration blocker; 850 implementation must use idempotent recomputation or explicit inverse deltas rather than copying the additive login call blindly.

## 381 versus 850 compatibility

850 AI-check DB `clan_data.sql` contains only the base clan columns through `watch_clanid`; it does not contain `clan_level`, `clan_contribution`, `clanskill`, `skilltime`, `clanskill_id`, `clanskill_lv`, or `clan_adena`. 850 `characters.sql` contains the ordinary `Contribution` field but no verified `PcContribution`, `ClanContribution`, or `ClanNameContribution` fields in the checked schema package.

850 DB targeted search found no `w_血盟等級` or `w_血盟能量怪物` table package. Target-core exact search found no verified equivalent `ClanOriginal`, `NpcClanContribution`, or clan level/contribution framework. Existing generic clan support is not evidence of this module’s behavior.

## Additive install and rollback boundary

Do not replace `clan_data`. Add a migration package with:

1. `CREATE TABLE IF NOT EXISTS w_血盟等級 (...)` containing the exact reward columns.
2. `CREATE TABLE IF NOT EXISTS w_血盟能量怪物 (...)` containing `npcid`, `min_血盟能量`, `max_血盟能量` and an explicit primary key/index policy.
3. `ALTER TABLE clan_data ADD COLUMN ...` only for the module-owned `clan_level` and `clan_contribution`, after checking column existence.
4. Add player columns only if the target runtime contract requires individual contribution persistence; keep them namespaced or documented as module-owned and do not reuse columns owned by another optional module.
5. Seed/reload scripts for the two configuration tables, with no destructive `DROP`, `TRUNCATE`, or replacement of existing clan rows.

Rollback must disable the event/hook and remove only module-owned additive columns/tables after an export/backup. It must not delete base clan records or touch `w_血盟技能` data.

## 850 equivalent and migration level

This is `L4`: the target lacks the required schema and runtime hooks, the contribution path is inside monster death core, level-up changes persisted clan state, login applies a broad stat vector, and stat removal is not proven. A DB-only import is insufficient. A reusable adapter is possible, but requires target core integration points for monster death, clan persistence, login stat recomputation, and level-up transaction semantics.

## Blockers

- 850 `clan_data` lacks the required level/contribution schema and player contribution fields.
- 850 has no verified equivalent clan-level/contribution framework.
- `w_血盟能量怪物` source package is empty, so no actual NPC score rows can be migrated from this package.
- Level-up thresholds/reward consumption are split: reward stats are in `w_血盟等級`; thresholds/materials are in `ConfigClan` properties, not in the requested DB package.
- Stat application is additive at login; removal/recalculation during clan leave/level changes is not proven.
- Must remain independent from `w_血盟技能`; shared `clan_data` columns require explicit ownership and additive migration controls.
