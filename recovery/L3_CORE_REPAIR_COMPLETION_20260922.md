# L3 Core Repair Completion — 2026-09-22

## Status
- Scope: classified L3 items.
- Repaired: 54 / 54.
- Normalized + obfuscated sources kept in sync where both exist.
- Validation: targeted source-contract + targeted javac regression.
- Completed-base promotion contract: PASS.
- Promotion validation run: 35680834150.

## Repaired IDs and fixes

### Batch 1
IDs: 001, 002, 003, 005, 007, 008, 009

Problems:
- missing command token guards;
- incomplete packet header handling;
- login password block count could exceed the fixed buffer;
- unchecked class/movement indexes;
- craft loader JDBC handles reused before close.

Fix:
- fail closed before token/index/buffer access;
- validate packet/login lengths and class/heading bounds;
- close craft JDBC resources before handle reuse.

### Batch 2
IDs: 016, 017, 021, 022, 023, 024, 025

Problems:
- missing shop/inventory objects dereferenced;
- random selection on empty inventory;
- client-controlled quest/achievement/weekly indexes;
- invalid craft IDs and deleted private-shop items dereferenced.

Fix:
- null/empty guards;
- validate reward/achievement/weekly indexes;
- reject unknown craft IDs;
- skip missing private-shop items safely.

### Batch 3
IDs: 035, 037, 038, 051

Problems:
- missing skill/clan lookup results;
- private-shop order index trusted before list access;
- second equipment page serialized the wrong list.

Fix:
- null guards;
- list-bound checks;
- page 2 serializes its own equipment list.

### Batch 4
IDs: 028, 066, 067, 069, 070, 071, 073, 076, 077, 079

Problems:
- character delete leaked/reused SQL handles and left orphan mail/buddy state;
- mail decode range issues;
- postage could debit before validation;
- RAM mail/buddy mutation could happen before durable DB success;
- batch-mail count trusted packet data.

Fix:
- scoped statements with authoritative objid;
- remove orphan mail/reverse buddy rows and live caches;
- clamp mail decode ranges;
- validate recipient/mailbox/clan before postage;
- DB success precedes live mutation;
- bound batch count by remaining bytes.

### Batch 5
IDs: 094, 096, 108, 111, 115, 120, 123, 138, 148, 153

Problems:
- missing executor/house/fight/pet/NPC objects used directly;
- clan deletion omitted warehouse history;
- duplicate bookmark creation continued;
- bookmark delete/reorder was not atomic;
- auction/board inputs lacked safe missing-object/length handling.

Fix:
- fail closed on null/wrong-type objects;
- clan + warehouse-history delete transaction;
- duplicate bookmark returns immediately;
- bookmark delete/reorder transaction with rollback and commit-before-RAM;
- safe auction response and board length limits.

### Batch 6
IDs: 182, 183, 184, 201, 217, 223, 229, 231, 247, 248, 268, 278, 279, 288

Problems:
- board actions trusted arbitrary object IDs/range;
- pet-item packet indexes/lookups unchecked;
- location/party/heading values unchecked;
- private-shop target cast unchecked;
- ranking class routing/truncation wrong;
- runtime-created spawn DB rows absent from live indexes;
- quest progress could go negative;
- ShopWorld NPC cast unchecked;
- bookmark limit hard-coded.

Fix:
- board type + same-map + <=3 tile proximity gate;
- pet/index/type/item guards;
- validate location/party/heading;
- safe target type checks;
- correct ranking routing/top-50 truncation;
- generated DB key registration into live spawn metadata;
- clamp quest progress at zero;
- use pc.cI() bookmark capacity.

### Batch 7
IDs: 271, 285

Problems:
- duplicate polyid rules lost the selected morph-row identity, allowing later equipment validation to use the wrong rule;
- 8.5 ghost mode lacked saved return state, so ExitGhost had no authoritative return path.

Fix:
- bind selected L1PolyMorph rule to the player and use it for weapon/armor revalidation;
- clear active morph rule on unpolymorph;
- save x/y/map/heading on ghost entry;
- ExitGhost initiates teleport-back;
- ghost state clears only after teleport finalization.

## Validation
- Batch1 PASS — run 35671637284
- Batch2 PASS — run 35671890739
- Batch3 PASS — run 35673217261
- Batch4 PASS
- Batch5 PASS — run 35674825429
- Batch6 PASS — run 35678192602
- Batch7 PASS — run 35678060134
- Completed-base promotion contract PASS — run 35680834150

The promotion gate accepts an existing completed-branch bookmark implementation that performs an equivalent or stronger transactional reorder using a direct SQL decrement rather than the work-branch batch-loop form.

## Result
L3_REPAIR_SCOPE=54
REPAIRED=54
PROMOTION_CONTRACT=PASS
RESTART_REQUIRED=YES after building/deploying the repaired core
