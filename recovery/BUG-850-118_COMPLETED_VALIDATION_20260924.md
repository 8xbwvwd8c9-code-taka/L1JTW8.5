# BUG-850-118 completed validation

## BUG-850-118 — batch bookmark creation could partially persist and publish

### Problem

The normalized batch bookmark path inserted rows independently and could publish RAM/client state after partial SQL failure.

### Fix

- stage bookmarks before persistence;
- use one JDBC batch transaction with rollback on failure;
- publish RAM/client state only after commit;
- include existing BUG-850-216 character_teleport InnoDB migration;
- preserve newer completed obfuscated atomic implementation.

### Validation

```text
PROMOTION_RUN=36007435999
REPAIR_CI_RUN=35953101310
HISTORICAL_RED=PASS
OBF_ALREADY_COVERED=PASS
CHARACTER_TELEPORT_INNODB=PASS
SOURCE_CONTRACT=PASS
BUG_850_121_PRESERVED=PASS
BUG_850_117_SCOPE_EXCLUDED=PASS
MINIMAL_SCOPE=PASS
NO_NEW_JAVAC_REGRESSION=PASS
```

### Result

```text
BUG-850-118=L2
STATUS=PASS
PROMOTED=YES
```

