# BUG-850-124 completed validation

## BUG-850-124 — house payment and house persistence could diverge

### Problem

House basement upgrade and house renewal charged Adena before the matching `house` row mutation was durably committed. SQL failure or interruption could therefore consume Adena while leaving house state unchanged, or expose RAM state that did not match durable state.

### Fix

- Adena CAS update/delete and the matching `house` mutation now share one JDBC transaction;
- basement purchase uses expected-state CAS on `is_purchase_basement`;
- renewal uses expected-state CAS on `tax_deadline`;
- both `character_items` and `house` must be InnoDB;
- SQL/CAS/commit failure rolls back without publishing live inventory or house state;
- live inventory and house RAM publish only after commit;
- renewal deadline arithmetic uses `Math.multiplyExact` and `Math.addExact`.

### DB transaction authority

`db/migrations/BUG-850-142_house_bid_innodb.sql` converts both `house` and `character_items` to InnoDB.

### Validation

```text
PROMOTION_RUN=36004274021
SOURCE_PATCH_COMMIT=b404dd3d2bc797802e2f1f7da33ae8737621be1e
HISTORICAL_RED=PASS
SOURCE_CONTRACT=PASS
INNODB_AUTHORITY=PASS
NO_NEW_JAVAC_REGRESSION=PASS
RUNTIME_MODEL=PASS
ROLLBACK_AND_POST_COMMIT_PUBLICATION=PASS
```

### Result

```text
BUG-850-124=L2
STATUS=PASS
PROMOTED=YES
```

