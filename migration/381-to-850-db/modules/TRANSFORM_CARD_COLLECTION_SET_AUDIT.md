# 381 -> 850 Transformation Card Collection / Set Audit

## Scope
Tables:
- w_變身卡片能力登入
- w_變身卡片能力組合套卡

## Source proof
w_變身卡片能力組合套卡:
- SOURCE_ROWS=10
- INSERT columns=30
- set ids=1..10
- set quest ids=5100..5109
- CREATE schema not proven

Parallel requirement arrays are aligned on all current rows.
All current 需求的變身卡編號 values are literal 1 placeholders rather than distinct card row ids. Effective requirement identity is the per-card quest-id array.

## Proven donor ownership
Loader/model: CardSetTable / CardPolySet
Card unlock: Cards
UI/control: CardBookCmd
Login application: C_LoginToServer.getCard

Cards.execute() writes the card quest, then scans every CardPolySet and when all required quest ids are complete writes the set quest id to step=1.

SET_OWNER=QUEST_STATE
SET_ACTIVE_CONDITION=all required card quest ids satisfied
SET_PERSISTENCE=set quest id
SET_UNLOCK_EVENT=card registration

## Actual stat application
C_LoginToServer.getCard() proves gameplay mutation. On login it loops every unlocked ACard and every unlocked CardPolySet and directly adds configured values to the live player.
Applied fields include STR/DEX/CON/INT/WIS/CHA, AC, max HP/max MP, HPR/MPR, melee/ranged damage and hit, physical/magic reduction, SP, magic hit/modifier, MR, and elemental resistances.

STAT_APPLICATION=PROVEN
LOGIN_REAPPLY=PROVEN
DISPLAY_ONLY=NO

CardBookCmd.CardAllSet() is display aggregation; gameplay mutation is separate in login code.

## Lifecycle model
Quest state is authoritative unlock persistence. Donor login code incrementally adds card and set vectors. No collection-owned subtract/recompute counterpart is proven.
If baseline is B and collection vector V: first apply B->B+V; duplicate invocation on same live player B+V->B+2V.
DONOR_INCREMENTAL=YES
DONOR_RECOMPUTE=NO
DRIFT_RISK=YES

## Indexing risk
Collection loops use i=0..table.size() and getCard(i). Current set IDs 1..10 happen to be contiguous. Sparse/high map keys would be silently skipped. 850 must iterate authoritative values/entries.

## Current set-only vector
5100 STR+1
5101 DEX+2
5102 CON+3
5103 INT+4
5104 WIS+5
5105 CHA+7
5106 STR+8
5107 DEX+9
5108 CON+10
5109 INT+11

TOTAL_SET_VECTOR=STR9;DEX11;CON13;INT15;WIS5;CHA7
INT_OVERFLOW=NO_CURRENT_SET_VALUES

## 850 target model
Base cards and sets share Quest persistence, one collection domain, one numeric vocabulary, and one login application hook. They should share one 850 CollectionOwner.

Recommended target:
CollectionOwner -> authoritative unlocked card quest states -> derive active sets -> recompute CollectionBonus = sum(card vectors)+sum(set vectors) -> character stat rebuild exactly once.

Shared StatModifierDefinition may be reused for numeric payload only. Lifecycle ownership remains collection-specific.

SHARED_STAT_VECTOR=YES
SHARED_LIFECYCLE=NO
COLLECTION_OWNER_SHARED_WITH_BASE_CARDS=YES
TIMED_OWNER_SHARED=NO
EQUIPMENT_OWNER_SHARED=NO
TRANSFORM_OWNER_SHARED=NO

## UI
Donor UI uses card_0, card_10, card_11, cardset, cardset2, polycard, and a1..a64. These are presentation/control paths, not authoritative collection state. Preserve semantics, not necessarily donor HTML.

## Classification
LEVEL=L4
Pure server collection/recompute portion is conceptually L3, but current module includes donor UI/polymorph integration and no proven 850-native collection equivalent.

## Status
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_變身卡片能力組合套卡
LEVEL=L4
SOURCE_ROWS=10
PARALLEL_ARRAYS=PASS
SET_OWNER=QUEST_STATE
SET_UNLOCK_EVENT=PROVEN
STAT_APPLICATION=PROVEN
LOGIN_HOOK=C_LoginToServer.getCard
DISPLAY_RUNTIME=BOTH
DONOR_INCREMENTAL=YES
DONOR_RECOMPUTE=NO
DRIFT_RISK=YES
SET_ONLY_MAX_VECTOR=STR9;DEX11;CON13;INT15;WIS5;CHA7
INT_OVERFLOW=NO_CURRENT_SET_VALUES
SHARED_MODIFIER=YES_STAT_VECTOR_ONLY
COLLECTION_OWNER_SHARED_WITH_BASE_CARDS=YES
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=850 collection/recompute owner unproven; login exactly-once assumption; sparse-key iteration bug pattern; semantic quest/card identity mapping; UI/polymorph integration
