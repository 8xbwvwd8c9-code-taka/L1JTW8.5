# BUG-850-126 completed validation

## BUG-850-126 — Karma and item persistence could diverge

### Problem

Karma-related NPC exchanges persisted `character_items` independently while the matching `characters.Karma` change could remain only in RAM. A database failure or server interruption could therefore consume/grant an item without preserving the corresponding Karma change.

### DB and control evidence

- `characters.Karma` is the durable signed integer authority.
- `RateKarma` is loaded from DB-backed `ConfigTable` into `Config.D`; the bundled `_config` value is `100` and the code fallback is `1.0`.
- `character_items` and `characters` are converted to InnoDB by `db/migrations/BUG-850-130_contribution_exchange_atomicity.sql` before this shared transaction is allowed.

### Fix

- item persistence and the `characters.Karma` CAS update share one JDBC transaction;
- the Karma CAS must affect exactly one row;
- SQL, row-count, engine or commit failure rolls back both durable sides;
- live inventory and RAM Karma publish only after commit;
- Karma arithmetic promotes to `long` and clamps to `-15500000..15500000`;
- granted stack counts are bounded at `2000000000`;
- normalized and obfuscated service sources remain in parity.

### Validation

```text
PROMOTION_RUN=36000910828
SOURCE_PATCH_COMMIT=430c6c40b43c74b925d34bd62070a4cb1caecb8d
SOURCE_CONTRACT=PASS
DB_SCHEMA_CHAIN=PASS
RATE_KARMA_CONTROL_CHAIN=PASS
INNODB_MIGRATION_GATE=PASS
NO_NEW_JAVAC_REGRESSION=PASS
RUNTIME_MODEL=PASS
ROLLBACK_AND_COMMIT_ORDER=PASS
```

### Result

```text
BUG-850-126=L2
STATUS=PASS
PROMOTED=YES
```

