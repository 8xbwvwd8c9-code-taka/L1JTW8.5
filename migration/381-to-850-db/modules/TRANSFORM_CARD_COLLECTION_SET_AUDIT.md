# L381 `w_變身卡片能力組合套卡` → 850 audit

## Result

`STATUS=BLOCKED`. The set table is an active stat source at login and after card registration; it is not display-only. Its authoritative activation state is the set quest row (`5100..5109`), while the prerequisite card quests (`5001..5064`) are used to grant that set quest. A future 850 `CollectionOwner` should own both base-card and set unlock state, but numeric modifier definitions remain lifecycle-independent.

## Source proof

- SQL: `L381/DB/381_DB_AI用/w_變身卡片能力組合套卡_202609221205.sql`
- `SOURCE_ROWS=10`
- `INSERT_COLUMN_COUNT=30`
- `ROW_VALUE_COUNTS=30 for every row`
- `COLUMN_ALIGNMENT=PROVEN`: `CardSetTable.load()` reads the same 30 semantic columns in the same order; each three-array field is parsed independently.
- `CREATE_SCHEMA=NOT_PROVEN`: split artifact contains INSERT only; no CREATE/ALTER.
- `EMPTY_FIELDS=all numeric stat fields are zero except the one intended primary stat in each row; no NULL stat payloads were found.`
- `DUPLICATE_IDS=NONE`: set IDs 1..10.
- `DUPLICATE_QUEST_IDS=NONE`: set quest IDs 5100..5109.

### Exact rows

| SET_ID | SET_NAME | REQUIRED_CARD_IDS | REQUIRED_QUEST_IDS | REQUIRED_CARD_NAMES | SET_QUEST_ID | nonzero STAT_VECTOR |
|---:|---|---|---|---|---:|---|
| 1 | 一階 | 1×10 | 5001..5010 | 10 names | 5100 | STR=1 |
| 2 | 二階 | 1×6 | 5011..5016 | 6 names | 5101 | DEX=2 |
| 3 | 三階 | 1×4 | 5017..5020 | 4 names | 5102 | CON=3 |
| 4 | 四階 | 1×4 | 5021..5024 | 4 names | 5103 | INT=4 |
| 5 | 五階 | 1×2 | 5025..5026 | 2 names | 5104 | WIS=5 |
| 6 | 傳說 真 齊天大聖 | 1×2 | 5027..5028 | 2 names | 5105 | CHA=7 |
| 7 | 傳說 真 素還真 | 1×2 | 5029..5030 | 2 names | 5106 | STR=8 |
| 8 | 傳說 真 棄天帝 | 1×2 | 5031..5032 | 2 names | 5107 | DEX=9 |
| 9 | 傳說 真 黑暗殺手 | 1×2 | 5033..5034 | 2 names | 5108 | CON=10 |
| 10 | 傳說 真 葉小釵 | 1×2 | 5035..5036 | 2 names | 5109 | INT=11 |

## Requirement-array integrity

`PARALLEL_ARRAYS=PASS`. For rows 1–10, the lengths of `需求的變身卡編號`, `需求的玩家任務編號`, and `需求的變身卡名稱` are respectively 10/10/10, 6/6/6, 4/4/4, 4/4/4, 2/2/2, 2/2/2, 2/2/2, 2/2/2, 2/2/2, 2/2/2. No mismatch was found, so CardSetTable's independent array parsing does not currently shift a requirement/name pair.

## Card-reference integrity

The related card SQL has 64 card rows with quest IDs 5001..5064. Every set prerequisite quest 5001..5036 exists and matches the corresponding card row's `任務編號`; `QUEST_REFERENCES=PASS`.

`需求的變身卡編號` is `1` in every set slot. Card table `流水號` includes card identity values, but the set runtime never calls `CardPolySet.getNeedids()` anywhere outside its accessor. The active checks use `getNeedQuest()` only. Therefore:

- `CARD_ID_REFERENCE_MATCH=PARTIAL/UNUSED`: value 1 exists, but repeated `1` does not identify the listed cards and is not used by active unlock/stat logic.
- `NAME_REFERENCE_MATCH=PROVEN for the displayed prerequisite names against the corresponding card display names in the targeted SQL; names are display metadata, not ownership keys.`
- `missing card=NONE by quest reference`.
- `wrong quest=NONE for 5001..5036`.
- `duplicate reference=the card-id field repeats 1 in all 36 slots; this is a proven data-quality issue only if a future consumer treats that field as identity.`
- `same quest used by multiple cards=NONE in the targeted card rows`.
- `circular set dependency=NONE`: sets require card quests, not set quests.

## Exact ownership and runtime graph

```text
card item -> Cards.execute -> card quest 5001..5064 set_step(...,1)
          -> scan all CardPolySet requirements
          -> if every required card quest != 0: set quest 5100..5109 set_step(...,1)

login -> C_LoginToServer.getCard
      -> card quest != 0: apply ACard vector
      -> set quest != 0: apply CardPolySet vector

CardBookCmd.CardSet/CardAllSet or Cards display paths
      -> read quests and send HTML/status only
```

`SET_OWNER=character quest state`; `SET_ACTIVE_CONDITION=pc.getQuest().get_step(setQuestId) != 0` in login and aggregate display. The prerequisite condition is used by `Cards.execute` to write the set quest. `SET_QUEST_WRITE_PATH=Cards.execute -> L1PcQuest.set_step -> quest persistence`; no separate set table row is written. `SET_QUEST_READ_PATH=C_LoginToServer.getCard, CardBookCmd.CardSet/CardAllSet, Cards.execute`; `SET_UNLOCK_EVENT=successful card registration followed by all prerequisite card quest steps nonzero`.

This is a hybrid: unlock is implicit from all card prerequisites at the registration event, then explicit set quest state is persisted and becomes the runtime activation predicate. A manually or externally mutated set quest can activate the bonus without the prerequisite scan; that is a proven consequence of the login predicate.

## Stat application versus display

`STAT_APPLY_HOOK=C_LoginToServer.getCard`. It directly calls player mutation methods for both `ACard` and `CardPolySet`, including `addMagicDmgReduction`, `addMagicDmgModifier`, HP/MP and all listed vectors. Thus set bonuses are actually applied at login, not merely displayed.

`STAT_REMOVE_HOOK=NOT_PROVEN`. No targeted set-specific removal/recompute was found. `LOGIN_REAPPLY=PROVEN`; `RECALC_HOOK=NOT_PROVEN`; `QUEST_UNLOCK_RECALC=NOT_PROVEN` beyond the next-login path; `LOGOUT_BEHAVIOR=state is quest-persisted, but inverse stat cleanup is not proven`.

`CardBookCmd.CardAllSet()` computes and sends `card_11`; `CardBookCmd.CardSet()` sends `card_10`/set detail. These display sums independently test `getNeedQuest()` for set detail but use `getQuestId()` for the set contribution in `CardAllSet`, matching login's explicit set-quest activation. They are not the application hook.

| Field | Runtime status |
|---|---|
| STR/DEX/CON/INT/WIS/CHA | BOTH: login mutation and UI aggregation |
| AC/HP/MP/HPR/MPR | BOTH |
| melee/ranged damage and hit | BOTH |
| physical/magic reduction | BOTH; login calls native-looking reduction APIs |
| SP/magic hit/MR | BOTH |
| FIRE/WATER/WIND/EARTH | BOTH; loader order is fire, water, wind, earth and login uses the same order |

## Recompute, drift and stacking

`DONOR_RECOMPUTE=login-time additive replay from quest state, not a pure recompute from a cleared baseline`. `DONOR_INCREMENTAL=card registration writes quest state and set quest state, but does not apply stats immediately; user is told to relog. `DRIFT_RISK=HIGH if login/getCard can run more than once on the same live player without a preceding inverse reset`.

The safe formula is `B + Σ(active card vectors) + Σ(active set vectors)`. The donor login method is equivalent only when called exactly once after a clean character stat baseline. It does not prove repeated login/recalc idempotency, quest reset subtraction, GM quest mutation reconciliation, table reload safety, or manual UI-call safety. CardBookCmd methods only sum/display and do not mutate stats. A card may belong to multiple sets; the design permits cumulative set stacking, and no contradictory runtime evidence was found. `MULTI_SET_MEMBERSHIP=allowed by data model`; `MULTI_SET_STACKING=proven additive if multiple set quests are nonzero`; `MAX_SET_MEMBERSHIP_PER_CARD=not bounded by runtime`.

`CARD_QUEST_COUNT=64`; `SET_QUEST_COUNT=10`; `CROSS_COLLISIONS=none between 5001..5064 and 5100..5109`; `INVALID_QUEST_IDS=none (all positive)`. Other unrelated quest collisions were not established by this targeted scan.

## Maximum aggregate vector

Using long accumulation over the 64 card rows plus all 10 set rows, the set contribution is STR=9, DEX=11, CON=13, INT=15, WIS=5, CHA=7 and all other set fields zero. The card SQL contribution is STR=14, DEX=14, CON=14, INT=18, WIS=12, CHA=0 and all other listed numeric fields zero. Therefore:

`MAX_VECTOR={STR=23, DEX=25, CON=27, INT=33, WIS=17, CHA=7, AC=0, HP=0, MP=0, HPR=0, MPR=0, MELEE_DMG=0, RANGED_DMG=0, MELEE_HIT=0, RANGED_HIT=0, PHYSICAL_REDUCTION=0, MAGIC_REDUCTION=0, SP=0, MAGIC_HIT=0, MR=0, FIRE=0, WATER=0, WIND=0, EARTH=0}`.

`INT_OVERFLOW=NO for the supplied rows`; `SAFE_ACCUMULATION=use long during import/validation, then range-check before applying target APIs`. The donor uses Java `int` accumulators, but the supplied sums are far below integer range.

## State-conservation matrix

| Event | Donor result |
|---|---|
| login once | applies active card + set vectors |
| login twice | NOT_PROVEN safe; additive double-apply risk |
| unlock one card | quest state persists; no immediate stat apply proven |
| unlock final card | writes set quest; no immediate stat apply proven |
| unlock two sets | both set quests can activate and stack |
| quest reset | quest state can clear, but inverse stat removal/recalc not proven |
| server restart/relog | login reapplies from quest state |
| reload tables | definitions reload; live-player inverse/reapply not proven |
| manual UI open/CardAllSet | display only; no stat mutation |

## 850-native mapping

850 has the native quest persistence primitive and player stat mutation/rebuild primitives, plus the polymorph/stat systems from the related card module. A dedicated equivalent collection/set engine or replacement UI was not proven in the targeted 850 authority tree. `850_NATIVE_QUEST_OWNER=YES`; `850_NATIVE_COLLECTION=NOT_PROVEN`; `850_NATIVE_STAT_RECOMPUTE=PARTIAL`; `850_NATIVE_POLY_COLLECTION=NOT_PROVEN`; `850_NATIVE_UI=NOT_PROVEN`.

Minimal architecture: `CollectionDefinition(cards, sets, requirements, StatModifierDefinition)`, `CollectionOwner(authoritative card/set quest state, active-set computation)`, and `CharacterRecompute(clear-and-rebuild or baseline-derived effective vector)`. Share the numeric `StatModifierDefinition` with `w_變身卡片能力登入` and other modifier domains only. Do not share lifecycle ownership with timed item buffs, equipment/passive items or transformation status; `COLLECTION_OWNER_SHARED_WITH_BASE_CARDS=YES`, `TRANSFORM_OWNER_SHARED=NO`, `TIMED_OWNER_SHARED=NO`, `EQUIPMENT_OWNER_SHARED=NO`.

## Client/UI boundary

`HTML_REQUIRED=for donor collection browsing only`; `HTML_DISPLAY_ONLY=card_10/card_11 and set portions of card_0`; `CLIENT_PROTOCOL_REQUIRED=existing server HTML packet/action contract if UI is retained`; `SERVER_ONLY_PORTION=quest ownership, set activation, stat recompute`. `cardset`, `cardset2`, `polycard`, `card_0`, `card_10`, `card_11` and `a1..a64` are not proof that 850 must copy donor HTML. No client changes were made.

## Proven donor issues

1. `BUG=CardSetTable/CardBookCmd/C_LoginToServer iterate i <= HashMap.size()`; `EVIDENCE=CardCardSize returns map size and getCard(i) assumes keys 0..size`; `IMPACT=sparse/non-zero-based set IDs are skipped, and an extra null lookup occurs`; current IDs 1..10 happen to be contiguous but start at 1, so i=0 is wasted and this is not a safe general contract; `DO_NOT_REPRODUCE=iterate definitions, not numeric key range`.
2. `BUG=need card-id field is repeated 1`; `EVIDENCE=all 36 slots are literal 1 while the named/quest references vary`; `IMPACT=any future consumer of getNeedids sees wrong identity`; active donor unlock does not use it; `DO_NOT_REPRODUCE=use canonical card quest/card key mapping`.
3. `BUG=stat application is additive without proven inverse/recompute`; `EVIDENCE=C_LoginToServer.getCard directly mutates player and no targeted remove hook was found`; `IMPACT=repeat login/recalc or quest reset can drift`; `DO_NOT_REPRODUCE=850 derive effective state from authoritative ownership`.

No proven parallel-array mismatch, duplicate set quest, card/set quest collision, integer overflow, wind/water/earth swap, or display-only misclassification was found.

## Classification / blockers

`CARD_TABLE_LEVEL=L1/L2` (quest/data mapping plus native stat primitives); `SET_TABLE_LEVEL=L2` (native quest owner plus adapter, but explicit set activation contract required); `COLLECTION_ENGINE_LEVEL=L3` (authoritative recompute/idempotency/lifecycle); `CLIENT_UI_LEVEL=L4` only if donor UI is required. Current migration is blocked by absent CREATE schema, no proven 850 equivalent collection engine/UI, unresolved idempotent stat recompute/removal, and the donor's unused repeated card-id field. No production/core/DB/client files were modified.
