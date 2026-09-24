# BUG-850-129 Completed Validation — 2026-09-24

```text
BUG=BUG-850-129
SEVERITY=L2
STATUS=PASS_PROMOTED
ISSUE=inn refund/live publication could diverge from durable inns state when UPDATE/DELETE failed or when refund arithmetic overflowed
ACTIVE_OBF=recovered-src-obf/ao/af.java
ACTIVE_NORMALIZED=recovery/normalized-src-vf/l1r/ao/InnTable.java
DURABILITY_FIX=8a0f719a351409a39ee13d9eaf288467a5fc8ca2
OVERFLOW_FIX=c0e6f697364a497b50b152eb7dab8656a2d1de16
DURABILITY_RUN=35810284620
OVERFLOW_RUN=35810475334
DURABILITY_JOB=107020103766
OVERFLOW_JOB=107020701446
```

## Problem

The original inn refund path mutated live inn count/refund state even when the durable `inns` UPDATE/DELETE path failed or affected no row. This could leave the room-key refund/item path and the durable lease row split. The original refund arithmetic also used 32-bit multiplication/accumulation.

## Repair

The validated repair changes both normalized and obfuscated authorities together:

- release helper returns a boolean success/failure result;
- release is serialized on the live inn record;
- `count <= 0`, missing key, or release count greater than live count fail closed;
- partial release uses `UPDATE inns SET count=? WHERE keyid=? AND count=?`;
- terminal release uses `DELETE FROM inns WHERE keyid=? AND count=?`;
- durable mutation requires `executeUpdate() == 1` before RAM count/map publication;
- stale/CAS miss or SQL failure returns false and no refund item/live count publication follows;
- refund item removal and refund accumulation occur only after durable release succeeds;
- refund multiplication uses `60L * (long)count` and aggregate refund is accumulated as `long`;
- any single refund or aggregate total above `Integer.MAX_VALUE` is rejected before durable release/refund publication, preserving the existing `int` method contract.

## Validation evidence

### Durability gate

GitHub Actions run `35810284620` completed successfully.

```text
BUG_850_129_TRANSFORM=PASS
BUG_850_129_EXACT_SCOPE=PASS
BUG_850_129_SOURCE_CONTRACT=PASS
BASELINE_JAVAC_RC=0
FIX_JAVAC_RC=0
BUG_850_129_NO_NEW_JAVAC_REGRESSION=PASS
BUG_850_129_RUNTIME=PASS
```

The validated source commit produced by that run is `8a0f719a351409a39ee13d9eaf288467a5fc8ca2`.

### Overflow gate

GitHub Actions run `35810475334` completed successfully.

```text
BUG_850_129_OVERFLOW_TRANSFORM=PASS
BUG_850_129_OVERFLOW_EXACT_SCOPE=PASS
BUG_850_129_OVERFLOW_SOURCE_CONTRACT=PASS
BASELINE_JAVAC_RC=0
FIX_JAVAC_RC=0
BUG_850_129_OVERFLOW_NO_NEW_JAVAC_REGRESSION=PASS
BUG_850_129_OVERFLOW_RUNTIME=PASS
```

The validated source commit produced by that run is `c0e6f697364a497b50b152eb7dab8656a2d1de16`.

## Promotion safety

Current work source history shows `c0e6f697364a497b50b152eb7dab8656a2d1de16` is still the latest commit touching both BUG-850-129 source files; no later source edit needs to be separated from this promotion.

Promoted blob identities:

```text
OBF_BLOB=3a3a2fc6ecf2d158c17ff25745e0bbcf4399d37e
NORMALIZED_BLOB=ee90d13a7bb4609f050507d84813bf3597baa5bc
```

The completed branch previously held older blobs and therefore required promotion rather than an already-covered close.

## Result

```text
DURABLE_MUTATION_BEFORE_REFUND=PASS
AFFECTED_ROWS_GATE=PASS
CAS_STALE_WRITE_GATE=PASS
SQL_FAILURE_FAIL_CLOSED=PASS
RAM_DB_PUBLICATION_ORDER=PASS
NO_DOUBLE_REFUND_ON_STALE_RETRY=PASS
REFUND_MULTIPLY_LONG=PASS
REFUND_AGGREGATE_BOUND=PASS
NORMALIZED_OBFUSCATED_PARITY=PASS
BUG_850_129=PASS_PROMOTED
```
