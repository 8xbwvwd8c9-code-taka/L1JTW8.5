# 381 -> 850 Little Mary Gambling Audit

## Scope

381 module:
- `w_小瑪莉`

Runtime:
- `com.lineage.data.event.MarySet`
- `com.lineage.data.npc.gam.Npc_Mary`
- `MaryReading`
- `MaryTable`

## Current DB content

Current split SQL contains one visible configuration row.

Observed state/config fields:
- `all_stake`
- `all_user_prize`
- `out_prize`
- `下注道具ID`
- `獎勵道具ID`
- `count`
- payout multipliers:
  - `x_a1/x_a2`
  - `x_b1/x_b2`
  - `x_c1/x_c2`
  - `x_d1/x_d2`
  - `x_e1/x_e2`
  - `x_f1/x_f2`
  - `x_g1/x_g2`

Current values include:
```text
all_stake=2417
all_user_prize=597
out_prize=10
下注道具ID=44070
獎勵道具ID=44019
count=282
```

Current split artifact is INSERT-only, so authoritative CREATE schema/key/defaults are not proven.

## Event startup

`MarySet.execute(...)`:
1. sets `MarySet.START=true`
2. calls `MaryReading.get().load()`

This loads DB state into static fields on `Npc_Mary`.

## Global state

`Npc_Mary` stores mutable process-wide state:

```text
_itemid
_itemid1
_count
x_a1..x_g2
_out_prize
_all_stake
_all_user_prize
```

These represent both configuration and live jackpot/accounting state.

## Per-player state

The NPC keeps:
```java
Map<Integer, MaryTemp> _maryUsers
```

keyed by player object ID.

`MaryTemp` tracks player-local session state such as:
- current bets per symbol
- held/rebet values
- pending prize
- jackpot snapshot
- high/low continuation state
- play count
- stop/lock flag
- x10 mode

This state is in memory and not independently persisted.

## UI dependency

The NPC actively renders and transitions through custom HTML IDs such as:

```text
bar_00
bar_01..bar_22
bar_hl
```

The current repository tree did not expose corresponding HTML files by path.

Therefore:

```text
CLIENT_HTML_DEP=YES
HTML_RESOURCE_PRESENT=NOT_PROVEN
```

No separate custom binary packet protocol was proven; the runtime mainly uses standard `S_NPCTalkReturn`, skill effects, item delivery and server messages.

## Main interaction model

NPC actions include:
- `start`
- `re`
- `get`
- symbol bet buttons `a..g`
- bulk bet actions `XL99/XL50/XL10/XL05/XL01`
- high/low `h/i`
- multiplier toggle `x10`
- payout/help page `HL`

This is a stateful multi-step UI rather than a one-shot action.

## Bet flow

A normal start:
1. totals all selected symbol bets
2. verifies the configured stake item
3. starts a `MaryTimer`
4. consumes/settles through asynchronous UI progression
5. computes prize against configured multipliers and jackpot budget
6. sends result HTML/messages
7. updates global accounting counters

The code includes repeated sleeps/animation stepping inside timer-driven logic.

## Jackpot / payout budget

`all_prize()` derives a payout pool from:

```text
all_stake * out_prize / 100
- all_user_prize
```

A portion of the remaining payout budget becomes the displayed jackpot.

The outcome-selection code also checks remaining payout capacity before allowing larger wins, and can fall back to lower/minimum outcomes.

This means the RNG is not an independent flat random table: it is coupled to accumulated server-wide accounting state.

## High/low subsystem

After a win the player can continue into high/low.

The high/low result logic:
- can double the current pending prize
- caps the displayed win streak at five
- changes effect/sound presentation by streak
- adjusts probability through `random()`
- considers available global payout budget

This is a second state machine layered on top of the base slot result.

## Persistence

Global state writes through:

```java
MaryReading.get().update(all_stake, all_user_prize, count)
```

which executes:

```sql
UPDATE w_小瑪莉
SET all_stake=?, all_user_prize=?, count=?
WHERE id=1
```

Critical schema assumption:

The current INSERT split does not include an `id` column, but runtime UPDATE hardcodes:

```text
WHERE id=1
```

Therefore an authoritative source CREATE schema is mandatory before migration.

Do not synthesize this silently.

## Concurrency / consistency risks

### 1. Global accounting is process-shared mutable state

`_all_stake`, `_all_user_prize`, and `_count` are static mutable values.

`MaryReading` serializes individual DB load/update calls with a JVM `ReentrantLock`, but the surrounding gameplay calculation and mutation are not one atomic DB transaction.

Concurrent players can race across:
- stake increment
- prize calculation
- payout eligibility
- reward delivery
- persisted aggregate update

### 2. DB update is not an atomic settlement transaction

The global accounting UPDATE is separated from:
- stake consumption
- outcome generation
- player payout delivery

A crash or concurrent play can create DB/game-state divergence.

### 3. Player session state is memory-only

Pending prize/high-low state is held in `MaryTemp`.

Disconnect/restart handling for unsettled sessions is not proven by this module.

Formal migration must define:
- pending-prize recovery
- disconnect behavior
- shutdown behavior
- idempotent cash-out

### 4. Hardcoded row ownership

Runtime UPDATE always targets `id=1`.

Multiple config rows are not safely supported by the proven persistence path.

### 5. Blocking presentation logic

The timer/UI sequence contains repeated `Thread.sleep(...)` calls.

Even when offloaded to worker tasks, target should avoid long blocking animation logic and use scheduled state transitions.

### 6. Outcome logic is payout-budget-sensitive

The donor intentionally biases/filters outcomes based on remaining configured payout budget.

Replacing it with simple uniform RNG would materially change behavior.

### 7. Item IDs require semantic mapping

Stake item `44070` and reward item `44019` must be mapped semantically to 850.

Numeric identity alone is unsafe.

## 850 comparison

Targeted 850 inspection did not locate:
- `Npc_Mary`
- `MaryTable`
- `MaryReading`
- `bar_*` UI
- equivalent jackpot/stateful minigame framework

850 generic NPC/item/timer primitives are not an equivalent implementation.

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Recommended migration shape

Do not port the donor class verbatim.

Suggested split:

```text
mary-game-core/
  immutable config
  per-player session state
  atomic settlement
  jackpot accounting
  outcome engine
  high-low engine
  disconnect/recovery policy

mary-game-ui/
  NPC action router
  bar_* view model
  animation scheduler

mary-game-persistence/
  config table
  jackpot/accounting state
  settlement/idempotency records
```

For correctness, prefer a persistent settlement record with a unique play/session ID rather than relying only on aggregate counters.

## Client dependency

```text
CLIENT_HTML_DEP=YES
CLIENT_HTML=bar_00..bar_22,bar_hl
CLIENT_HTML_RESOURCE_PRESENT=NOT_PROVEN
CLIENT_PROTOCOL_DEP=NOT_PROVEN
```

The missing custom HTML/resources make functional parity dependent on client-side assets even without a custom packet protocol.

## Difficulty

`LEVEL=L4`

Reason:
- custom NPC UI state machine
- extensive HTML/menu dependency
- global jackpot/accounting persistence
- per-player in-memory sessions
- asynchronous animation/timer flow
- payout-budget-coupled RNG
- high/low secondary state machine
- no proven 850 equivalent

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_小瑪莉
LEVEL=L4
CORE_DEP=YES
DB_DEP=YES
NPC_DEP=YES
GLOBAL_STATE=all_stake+all_user_prize+count
PLAYER_SESSION_STATE=IN_MEMORY
CLIENT_HTML_DEP=YES
CLIENT_HTML=bar_00..bar_22,bar_hl
CLIENT_HTML_RESOURCE_PRESENT=NOT_PROVEN
CLIENT_PROTOCOL_DEP=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
HARDCODED_ROW_ID=1
ATOMIC_SETTLEMENT=NO
BLOCKERS=schema,HTML resources,item mapping,settlement/concurrency design,disconnect recovery,RNG parity
```
