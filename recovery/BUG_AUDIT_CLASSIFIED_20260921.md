# L1JTW8.5 BUG Audit — Three-Level Classification

Date: 2026-09-21  
Mode: **AUDIT_ONLY / BUG_ONLY**  
Source of truth: `recovery/BUG_AUDIT_2026-09-20.md`  
Project rule source: `main:README.md`

## Scope

This file is a classification/index layer only.

- Includes **BUG** findings only.
- Excludes **RISK** and **NOTE** findings.
- Does not change Java, SQL, config, DB data, or runtime behavior.
- Classification follows the current project three-level model and the main README validation chain:
  `CORE call path -> config -> DB/loader -> default/fallback -> ACTIVE source -> runtime evidence`.
- A finding is not promoted from cross-version evidence alone; 850 semantics must be directly confirmed.

## Three-Level Model

### L1 — Severe

Use for direct economic/item integrity failures, cross-account/cross-player/cross-owner authorization failures, unrestricted character-state mutation, unauthorized persistent world/castle/clan governance mutation, or service-critical failures.

### L2 — Major

Use for DB/RAM divergence, non-atomic exchanges, lifecycle/session inconsistency, concurrency defects, repeatable subsystem failures, and significant second-stage interaction/authorization failures.

### L3 — General

Use for bounded validation defects, packet/session robustness, stale/null/type/index guards, resource handling, lower-impact correctness, and observability defects.

## Summary

```text
TOTAL_BUGS=168
L1=41
L2=78
L3=49
RISK_INCLUDED=0
NOTE_INCLUDED=0
```

## L1 — Severe (41)

- `BUG-850-012` — castle treasury withdrawal grants adena even when treasury balance is insufficient — CRITICAL — economic integrity
- `BUG-850-014` — ShopWorld accepts negative purchase quantity/price and can increase account shop currency — CRITICAL — account currency integrity
- `BUG-850-015` — ShopWorld price validation uses overflow-prone 32-bit multiplication — HIGH — economic integrity / malformed packet handling
- `BUG-850-019` — achievement reward packet can grant the same reward repeatedly — CRITICAL — economic / progression integrity
- `BUG-850-020` — Tam/account-point NPC shop accepts negative purchase counts as positive balance adjustments — CRITICAL — account currency integrity
- `BUG-850-040` — castle treasury withdrawal lacks server-side member-rank/owner authorization — CRITICAL — clan treasury authorization / economic integrity
- `BUG-850-044` — private-shop arithmetic limit can be bypassed by 32-bit integer overflow — HIGH — economic arithmetic integrity
- `BUG-850-045` — negative castle-withdrawal amount increases and persists castle treasury balance — CRITICAL — castle treasury economic integrity
- `BUG-850-048` — LuckyDraw redemption trusts client-supplied reward quantity instead of recomputing it server-side — CRITICAL — direct item/currency integrity
- `BUG-850-075` — mail actions do not verify that the requested mail belongs to the current character — HIGH — mail authorization / confidentiality-integrity boundary
- `BUG-850-093` — character reset handler accepts reset-stage stat updates without verifying reset-mode state — HIGH — character attribute integrity
- `BUG-850-105` — delayed character-delete state is mutated before account ownership is verified — CRITICAL — cross-account character-state authorization
- `BUG-850-106` — delete-character request can mutate another character's clan RAM membership before ownership rejection — HIGH — cross-account live clan-state authorization
- `BUG-850-114` — item conversion path grants the replacement item before validating/removing the source item — CRITICAL — item/economic integrity
- `BUG-850-137` — inn rental accepts an unchecked client amount and can continue when computed charge is non-positive — CRITICAL — rental/economic integrity
- `BUG-850-140` — house-sale amount response mutates house sale state without revalidating ownership or authority — CRITICAL — cross-house authorization / persistent property integrity
- `BUG-850-141` — house bid response does not revalidate auction state, eligibility, or minimum/current price — HIGH/CRITICAL — auction and property-state integrity
- `BUG-850-144` — house-sale amount accepts an unchecked sale price that is later trusted by auction settlement — HIGH — auction price / payout integrity
- `BUG-850-171` — pet inventory menu does not verify that the requested pet belongs to the requesting player — HIGH — cross-player inventory confidentiality / object authorization
- `BUG-850-172` — skill-purchase confirmation trusts client-selected skill ids instead of revalidating the server-offered list — HIGH — learned-skill authorization / character progression integrity
- `BUG-850-177` — dungeon-entry packet trusts client-supplied entrance coordinates without verifying the player is standing there — HIGH — unauthorized teleport / map-transition integrity
- `BUG-850-194` — C_SelectTarget allows a client to issue combat-target commands for another player's pet — HIGH — cross-player pet-control authorization
- `BUG-850-195` — pet and summon action handlers do not verify the requesting player is the master — HIGH — cross-player pet/summon lifecycle and behavior control
- `BUG-850-197` — C_NpcAction pet/summon commands bypass distance without verifying ownership — HIGH — cross-player pet/summon control
- `BUG-850-198` — C_MercenaryArrange trusts a client castle id and summon count without verifying castle membership or server entitlement — HIGH — cross-castle resource / summon authorization
- `BUG-850-199` — C_MercenaryEmpoly trusts client castle, price and count values and uses overflow-prone arithmetic — CRITICAL — cross-castle treasury / mercenary inventory integrity
- `BUG-850-200` — C_UsePetItem can equip/unequip another player's pet because it never verifies pet ownership — HIGH — cross-player pet equipment/state integrity
- `BUG-850-208` — recovered C_GotoMap/C_Ship trusts client destination map/x/y and originally ignored ticket-consumption failure — CRITICAL/HIGH — unauthorized map transition / transport-cost integrity
- `BUG-850-210` — castle tax-rate changes do not require the requesting player to be the clan leader — HIGH — castle governance / persistent economic policy authorization
- `BUG-850-211` — any clan member can initiate or revoke clan-watch relationships — HIGH — cross-clan relationship authorization / persistent clan state
- `BUG-850-215` — clan emblem image/status mutations rely on rank codes instead of authoritative clan-leader identity — HIGH — clan governance / persistent shared-state integrity
- `BUG-850-218` — C_NpcTalk resolves a global object id without same-map/range binding even though talk callbacks can mutate player state — HIGH — item/player-state integrity across NPC interaction boundary
- `BUG-850-220` — online-gift claim does not consume/reset the expired gift state before granting the reward — CRITICAL — item/economic integrity
- `BUG-850-221` — mentor unlink trusts a client-supplied character name and can clear another character's MasterID outside the caller's relationship — HIGH — cross-player relationship authorization / persistent character integrity
- `BUG-850-222` — C_GMTeleport is dispatched to normal clients without any GM/access check and trusts client map/x/y — CRITICAL — unrestricted player-position/map mutation
- `BUG-850-226` — character-config packet allocates from an untrusted internal 32-bit length before validating it against packet bounds — CRITICAL — JVM heap availability
- `BUG-850-227` — C_RestartDead executes respawn teleport/state reset without requiring the character to be dead — HIGH — teleport / combat-state escape / respawn-state integrity
- `BUG-850-228` — C_GotoPortal can replay the last stored teleport destination without a one-shot pending-teleport token — HIGH — unauthorized world-position mutation
- `BUG-850-260` — wildcard IP-ban matching can throw on shorter client addresses and terminate the single GameServer accept thread — CRITICAL — new-connection accept service can stop for the process lifetime
- `BUG-850-261` — non-stackable cursed-drop branch mutates the wrong item object, so configured unbless chance is not applied to the item that actually drops — HIGH — persistent dropped-item bless/curse state
- `BUG-850-264` — HtmlCraft amount path allows 32-bit count multiplication to bypass material consumption while continuing output creation — CRITICAL — direct item/crafting integrity

## L2 — Major (78)

- `BUG-850-010` — one malformed craft row can abort loading all remaining craft definitions — HIGH for content availability / startup correctness
- `BUG-850-027` — character deletion is not transactional across dependent tables — HIGH — persistent data integrity
- `BUG-850-032` — ShopWorld claim persists player item before deleting the pending-claim row — HIGH — economic / persistence integrity under DB failure
- `BUG-850-033` — ShopWorld purchase queue mutates memory before confirming the INSERT — MEDIUM — memory/DB consistency
- `BUG-850-034` — stale ShopWorld item ids can abort ShopWorldTable initialization — HIGH — ShopWorld availability / content migration robustness
- `BUG-850-039` — private-shop completion bookkeeping is hard-coded to eight entries — MEDIUM/HIGH — private-shop transaction robustness
- `BUG-850-043` — private-shop creation accepts unbounded listing count, price, and quantity values — HIGH — private-shop integrity / malformed transaction surface
- `BUG-850-046` — private-shop lists can exceed the settlement handler's fixed eight-slot cleanup bitmap — HIGH — private-shop transaction availability/integrity
- `BUG-850-049` — LuckyDraw redemption grants reward before confirming pending-entry deletion in DB — HIGH — persistence/economic integrity under DB failure
- `BUG-850-050` — warehouse withdrawals are not atomic with warehouse DB persistence — HIGH — persistent inventory duplication/loss risk under DB failure
- `BUG-850-053` — stale LuckyDraw item ids can abort pending-result loading — HIGH — LuckyDraw availability / migration robustness
- `BUG-850-054` — LuckyDraw pending results are published in memory before DB insertion succeeds — HIGH — persistence / player-asset consistency under DB failure
- `BUG-850-055` — warehouse deposits are not atomic with destination DB insertion — HIGH — persistent item-loss risk under DB failure
- `BUG-850-057` — player inventory addition publishes the item in memory before persistent INSERT succeeds — HIGH — persistent inventory consistency
- `BUG-850-058` — player inventory deletion removes the item from memory even when DB DELETE fails — HIGH — persistent inventory duplication/reappearance risk under DB failure
- `BUG-850-059` — player inventory count/state mutations can diverge from DB after failed UPDATE — HIGH — persistent inventory state consistency
- `BUG-850-061` — craft material validation is destructive and can consume a prefix before later validation fails — HIGH — player item-loss / craft integrity
- `BUG-850-064` — character creation can report success and reserve the name after the characters INSERT fails — HIGH — character creation / persistent-state integrity
- `BUG-850-065` — character creation writes dependent starter state before the main character row is durably created — HIGH — orphaned character data / creation atomicity
- `BUG-850-072` — mail creation can return a non-persisted mail object after DB INSERT failure — HIGH — mail memory/DB consistency
- `BUG-850-082` — clan creation continues after clan_data INSERT failure and can charge the player for a non-persisted clan — HIGH — clan/persistent economic integrity
- `BUG-850-083` — clan deletion continues destructive cleanup after clan_data DELETE failure — HIGH — clan/warehouse persistence integrity
- `BUG-850-085` — clan join is not atomic across character state, clan member list, and clan_members persistence — HIGH — clan membership consistency
- `BUG-850-086` — leaving a clan clears character/clan state even if clan_members DELETE fails — HIGH — clan membership persistence consistency
- `BUG-850-087` — clan merge/migration performs per-member destructive rewrites without transaction or rollback — HIGH — multi-character clan migration consistency
- `BUG-850-089` — learned-skill state is published in memory before character_skills INSERT succeeds — HIGH — skill progression persistence consistency
- `BUG-850-095` — clan kick clears authoritative character state before clan_members DELETE succeeds — HIGH — clan membership persistence consistency
- `BUG-850-099` — deleting a master character does not clear disciples' characters.MasterID references — MEDIUM/HIGH — persistent mentor relationship integrity
- `BUG-850-100` — deleting a married character does not clear the surviving partner's PartnerID — MEDIUM/HIGH — persistent relationship integrity
- `BUG-850-102` — character deletion can report success even after SQL deletion fails — HIGH — character deletion / persistent-state integrity
- `BUG-850-103` — character deletion removes clan RAM membership before durable deletion is known to succeed — MEDIUM/HIGH — clan/session-state consistency
- `BUG-850-109` — mentor relationship acceptance updates only RAM state and does not persist MasterID immediately — HIGH — mentor relationship persistence integrity
- `BUG-850-112` — pet rename consumes the rename item before validating that the target pet still exists — MEDIUM/HIGH — player item loss and packet/session robustness
- `BUG-850-117` — bookmark import grants destination bookmarks before source data/item consumption is durably completed — HIGH — persistent item/bookmark integrity
- `BUG-850-118` — failed bookmark INSERT can still publish a non-persisted bookmark into the live player list — MEDIUM/HIGH — bookmark persistence consistency
- `BUG-850-121` — single bookmark creation publishes RAM state even when INSERT fails — MEDIUM/HIGH — bookmark persistence consistency
- `BUG-850-124` — house fee/upgrade payments are consumed before house persistence succeeds — HIGH — player currency / house persistence integrity
- `BUG-850-126` — karma-for-item NPC exchanges persist inventory independently from karma state — HIGH — item/economic and character-state persistence integrity
- `BUG-850-129` — inn refund is paid even when persistent inn-count release fails — HIGH — refund / rental-state persistence integrity
- `BUG-850-130` — NPC material exchanges can award Contribution only in RAM after item persistence has completed — MEDIUM/HIGH — character progression persistence integrity
- `BUG-850-132` — town salary payout still returns Pay when the database reset-to-zero fails — HIGH — currency payout / persistent salary integrity
- `BUG-850-136` — inn rental completes payment/key delivery before persistent lease INSERT succeeds — HIGH — rental/payment persistence integrity
- `BUG-850-142` — house bid payment, persistence, and previous-bidder refund are non-atomic — HIGH — auction currency / persistence integrity
- `BUG-850-143` — C_Amount responses are not bound to NPC proximity or a verified pending amount-dialog context — HIGH — interaction authorization / stale-response integrity
- `BUG-850-145` — house auction settlement pays the seller before ownership changes are durably committed — HIGH — property/currency settlement atomicity
- `BUG-850-146` — auction settlement can close a sold house without assigning it to any bidder clan — HIGH — persistent property ownership integrity
- `BUG-850-149` — tax-expiry foreclosure clears clan ownership and resets house auction state through independent persistence operations — HIGH — property ownership persistence integrity
- `BUG-850-152` — board post payment and DB creation are not one success contract — MEDIUM/HIGH — economic and persistence ordering
- `BUG-850-154` — logout clears its duel target id before using that id to clear the opponent — MEDIUM — duel/session lifecycle consistency
- `BUG-850-155` — doll cleanup side effects are not idempotent across timer/logout races — MEDIUM/HIGH — runtime character-stat integrity
- `BUG-850-162` — follower logout cleanup can re-delete a stale follower whose master was already cleared — MEDIUM/HIGH — logout lifecycle consistency
- `BUG-850-165` — logout hides summons from clients but does not actually release/delete them — MEDIUM/HIGH — world/lifecycle consistency
- `BUG-850-166` — clan creation persists clan/member state before confirming the 30000 Adena fee was consumed — HIGH — economic/persistence atomicity
- `BUG-850-167` — NPC AI task exception can leave the AI-running flag permanently set — MEDIUM/HIGH — NPC lifecycle/availability
- `BUG-850-178` — board-write mutation accepts any existing world object as its interaction context — MEDIUM/HIGH — interaction authorization / remote state mutation
- `BUG-850-179` — skill-purchase confirmation is not bound to a current trainer/NPC interaction — MEDIUM/HIGH — gameplay authorization / trainer interaction integrity
- `BUG-850-187` — pending-delete character types 32–39 are accepted by the normal login pipeline — HIGH — character lifecycle / deletion-state integrity
- `BUG-850-190` — C_GiveItem transfers to a world NPC/pet without binding the request to the target's actual location — MEDIUM/HIGH — remote state mutation / item-transfer integrity
- `BUG-850-193` — C_Door trusts a global object id without type, map, or distance validation — HIGH — remote world-state mutation / packet robustness
- `BUG-850-202` — C_LeaveClan trusts the client-supplied clan name instead of the player's actual clan id — HIGH — clan RAM/DB consistency
- `BUG-850-214` — weapon repair accepts any nearby world object without validating repair-NPC service authority — MEDIUM/HIGH — service-context authorization / item-state integrity
- `BUG-850-216` — bookmark reorder mutates RAM before an unchecked client index can abort persistence — MEDIUM/HIGH — bookmark persistence consistency / packet robustness
- `BUG-850-244` — character gift claim marks RAM before persistence and still grants rewards when the DB update fails — HIGH — repeatable reward state after persistence failure
- `BUG-850-245` — castle treasury deposit/withdrawal continue after swallowed castle persistence failure — HIGH — player currency vs castle treasury integrity under DB failure
- `BUG-850-246` — logout buff persistence deletes the full durable set before rebuilding it with non-transactional, failure-swallowing inserts — HIGH — durable character-buff consistency
- `BUG-850-249` — PetTable publishes creates before INSERT success and removes live entries even when DELETE fails — HIGH — persistent pet lifecycle / ownership-state integrity
- `BUG-850-250` — SoulTower top-10 list is never trimmed in RAM, so qualifying results accumulate indefinitely and distort later admission checks — MEDIUM/HIGH — long-running ranking-state growth / repeated sort cost
- `BUG-850-251` — SoulTower leaderboard persistence deletes the full table before rebuilding the top ten without a transaction — MEDIUM/HIGH — durable subsystem consistency
- `BUG-850-252` — malformed existing character_mobs_week rows cannot self-heal because login fallback uses INSERT against a primary-key row that already exists — MEDIUM/HIGH — weekly progression state remains permanently unrecoverable until DB repair
- `BUG-850-254` — weekly quest reset continues after swallowed bulk-delete failure and can resurrect pre-reset progress after restart — HIGH — weekly progression persistence integrity
- `BUG-850-255` — first-row INSERT failure leaves several progress systems live in RAM while logout UPDATEs a row that still does not exist — HIGH — character progress loss after otherwise normal sessions
- `BUG-850-256` — daily and weekly reset timers only schedule their next run after all reset work succeeds, so one exception can permanently stop future resets — HIGH — repeatable global subsystem outage until restart
- `BUG-850-257` — SoulTower leaderboard cannot bootstrap from the bundled empty table because the first result is accepted only inside an existing-entry loop — HIGH — repeatable subsystem failure from clean bundled DB
- `BUG-850-258` — furniture placement/removal mutates World state before persistence and continues after swallowed INSERT/DELETE failure — MEDIUM/HIGH — persistent furniture/world-state integrity
- `BUG-850-262` — weapon-skill proc probability uses an inclusive threshold, making configured rates one percentage point too high — MEDIUM/HIGH — systematic weapon proc-rate distortion
- `BUG-850-263` — account login registers the connection before binding the account to the client, so the accounts.online update is skipped every normal login — HIGH — account lifecycle / online-state consistency
- `BUG-850-265` — HomeTown monthly salary calculation zeros Contribution before calculating Pay — HIGH — town salary subsystem / persistent reward calculation failure
- `BUG-850-266` — MobSkills probability gate uses an inclusive 0..99 threshold and biases configured rates upward — MEDIUM/HIGH — systematic monster-skill selection-rate distortion

## L3 — General (49)

- `BUG-850-001` — GM command parameter underflow can reach ClientThread disconnect path — HIGH for GM/admin session stability
- `BUG-850-002` — .allBuff missing target produces recurring severe exception logging — LOW / MEDIUM
- `BUG-850-003` — packet header EOF validation checks only the second header byte — MEDIUM
- `BUG-850-005` — login password block count is trusted before indexing a fixed 128-byte buffer — HIGH for connection robustness
- `BUG-850-007` — character class/type is used as an array index before range validation — MEDIUM / HIGH for malformed create-character packets
- `BUG-850-008` — movement heading is used directly as an 8-entry direction-table index — MEDIUM for malformed movement packets
- `BUG-850-009` — CraftListTable overwrites first query resources without closing them — LOW / MEDIUM resource leak
- `BUG-850-016` — invalid ShopWorld item id is dereferenced before existence validation — MEDIUM — packet robustness
- `BUG-850-017` — death item-loss path indexes inventory zero when the inventory is empty — MEDIUM — death processing stability
- `BUG-850-021` — quest optional-reward index is trusted before array access — MEDIUM — packet robustness
- `BUG-850-022` — achievement number is dereferenced before validity/range checks — MEDIUM — packet robustness
- `BUG-850-023` — weekly reward line is used as a multidimensional-array index without range validation — MEDIUM — packet robustness
- `BUG-850-024` — craft request dereferences unknown craft ids before validation — MEDIUM — packet robustness
- `BUG-850-025` — private-shop buy-list serialization can dereference a stale owner inventory item — MEDIUM — private-shop availability
- `BUG-850-028` — character deletion repeatedly overwrites PreparedStatement handles without closing earlier statements — LOW/MEDIUM — JDBC resource pressure
- `BUG-850-035` — learned skill can be dereferenced even when the SkillsTable record is missing — MEDIUM/HIGH — skill-use/session robustness
- `BUG-850-037` — ShopWorld clan announcement action dereferences clan state without membership validation — MEDIUM — packet robustness
- `BUG-850-038` — private-shop transaction order index is used without bounds validation — MEDIUM/HIGH — private-shop transaction robustness
- `BUG-850-051` — equipment-page serialization writes page 1 contents into both pages — MEDIUM — equipment-page state/UI correctness
- `BUG-850-066` — deleting a character does not remove mail rows tied to that character inbox id — MEDIUM — persistent orphan data / mail-state integrity
- `BUG-850-067` — deleting a character removes only that character's buddy-list rows, not reverse references from other players — MEDIUM — social-state/orphan reference integrity
- `BUG-850-069` — mail read/archive actions dereference unknown mail ids before validation — MEDIUM — mail packet robustness
- `BUG-850-070` — mail payload parsing reads one byte past the buffer on odd-length input — MEDIUM — malformed packet robustness
- `BUG-850-071` — mail postage is consumed before recipient/clan validation completes — MEDIUM — player currency loss / mail-flow integrity
- `BUG-850-073` — buddy addition updates in-memory state before the DB INSERT succeeds — MEDIUM — buddy memory/DB consistency
- `BUG-850-076` — marking mail as read mutates live state before confirming the DB UPDATE — MEDIUM — mail memory/DB consistency
- `BUG-850-077` — mail archive/type change mutates the live object before confirming persistence — MEDIUM — mail memory/DB consistency
- `BUG-850-079` — mail batch-operation count is trusted without validating remaining packet length — MEDIUM — malformed packet robustness
- `BUG-850-094` — high-id learned skills can reach a null skill executor — HIGH — skill-use/session robustness
- `BUG-850-096` — clan disband does not remove clan_warehouse_history rows for the deleted clan — MEDIUM — persistent orphan/audit-data accumulation
- `BUG-850-108` — duel response dereferences a stale/missing opponent without validation — MEDIUM — packet/session robustness
- `BUG-850-111` — house rename response dereferences a stale/missing house id — MEDIUM — packet/session robustness
- `BUG-850-115` — legacy pet-name response dereferences a stale/missing pet without validation — MEDIUM — packet/session robustness
- `BUG-850-120` — duplicate bookmark names are warned but still inserted — MEDIUM — bookmark integrity / duplicate logical state
- `BUG-850-123` — bookmark reorder persistence can stop mid-list and leave partially updated order metadata — MEDIUM — bookmark persistence/order consistency
- `BUG-850-138` — C_Amount casts the requested world object to NPC before validating its runtime type — MEDIUM — packet/session robustness
- `BUG-850-148` — auction-board house selection dereferences an unknown house id without validation — MEDIUM — packet/session robustness
- `BUG-850-153` — board write has no server-side title/content length bound — LOW/MEDIUM — input validation / DB-UI pressure
- `BUG-850-182` — board-read directly casts an arbitrary world object to L1BoardInstance — MEDIUM — packet/session robustness
- `BUG-850-183` — board-page directly casts any non-null world object to L1BoardInstance — MEDIUM — packet/session robustness
- `BUG-850-184` — board-delete authorizes by post author but is not bound to a board interaction — LOW/MEDIUM — interaction-context integrity
- `BUG-850-201` — C_UsePetItem directly casts the pet object and trusts the client inventory index — MEDIUM — packet/session robustness
- `BUG-850-217` — fixed-location teleport branch uses an unchecked client point as an array index — LOW/MEDIUM — malformed/stale packet handler failure
- `BUG-850-223` — C_BanParty dereferences the party object before verifying that the requester is in a party — LOW/MEDIUM — malformed/stale party request handler failure
- `BUG-850-229` — C_ChangeHeading accepts direction values outside the eight-direction domain used by downstream array-index logic — LOW/MEDIUM — packet robustness / latent array-index failure
- `BUG-850-231` — C_ShopList/S_PrivateShop casts an arbitrary global object id to player before validating its type — LOW/MEDIUM — malformed private-shop request handler failure
- `BUG-850-247` — RankingTable places Type 7 characters into the Type 3 ranking bucket and never populates the dedicated eighth-class bucket — LOW/MEDIUM — ranking correctness / class leaderboard integrity
- `BUG-850-248` — RankingTable's intended top-50 truncation only reassigns a local variable and does not trim the stored ranking lists — LOW/MEDIUM — ranking response size / leaderboard correctness

- `BUG-850-268` — persistent NPC spawn insertion does not update NpcSpawnTable live index until restart — MEDIUM — live admin/runtime state does not match successful persistent spawn creation

## Classification maintenance rule

For future audit rounds:

1. record only findings that meet **BUG** confidence;
2. assign exactly one of `L1 / L2 / L3`;
3. verify relevant core/config/DB/default/active-source/runtime path before promotion;
4. if evidence is insufficient for BUG confidence, do not add it to this BUG-only classification;
5. do not modify runtime code while the project remains in `AUDIT_ONLY`.
