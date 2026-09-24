# BUG-850-121 completed validation

## BUG-850-121 — bookmark INSERT failure could still publish success

### Problem

The normalized single-bookmark create path caught `SQLException` from `character_teleport` INSERT but then fell through to append the bookmark to RAM and send `S_Bookmarks`, creating success state without durable DB state.

### Fix

- normalized SQL failure now returns before RAM/client publication;
- completed obfuscated source was already fail-closed through newer bookmark hardening and was left unchanged;
- successful INSERT still publishes RAM/client state in the original order;
- BUG-850-117/118 batch/import behavior is not altered by this promotion.

### Validation

```text
PROMOTION_RUN=36005257525
PATCH_COMMIT_NORMALIZED=d52acadc880afcb205a45fe20f73a4b1670f2d81
PATCH_COMMIT_OBF=6c5040361e79bc64c65517b4760be6800dd51dcd
NORMALIZED_HISTORICAL_RED=PASS
OBF_ALREADY_COVERED=PASS
SOURCE_CONTRACT=PASS
MINIMAL_SCOPE_NORMALIZED_ONLY=PASS
NO_NEW_JAVAC_REGRESSION=PASS
```

### Result

```text
BUG-850-121=L2
STATUS=PASS
PROMOTED=YES
```

