# BUG-850-117 completed validation

## BUG-850-117 — bookmark import crossed separate durability boundaries

### Problem

The normalized bookmark import created destination bookmark state before source bookmark rows and the source item were durably consumed under one success boundary.

### Fix

- source SELECT, destination INSERT batch, source bookmark DELETE and source item DELETE share one JDBC transaction;
- source item delete requires affectedRows == 1;
- SQL/CAS failure rolls back and returns;
- RAM/client bookmark publication and live source-item removal happen only after commit;
- existing InnoDB authorities for character_teleport and character_items are required and verified;
- newer completed obfuscated implementation is preserved unchanged.

### Validation

```text
PROMOTION_RUN=36007857405
REPAIR_CI_RUN=35953448189
NORMALIZED_HISTORICAL_RED=PASS
OBF_ALREADY_COVERED=PASS
ENGINE_PREREQ=PASS
SOURCE_CONTRACT=PASS
BUG_850_118_PRESERVED=PASS
BUG_850_121_PRESERVED=PASS
MINIMAL_SCOPE=PASS
NO_NEW_JAVAC_REGRESSION=PASS
```

### Result

```text
BUG-850-117=L2
STATUS=PASS
PROMOTED=YES
```

