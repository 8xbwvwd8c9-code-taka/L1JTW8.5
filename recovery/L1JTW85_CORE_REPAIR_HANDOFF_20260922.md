# L1JTW8.5 Core Repair Handoff — 2026-09-22

## Branches

```text
WORK=work/l1jtw85-core-fixes
WORK_HEAD=fc75c3866ca3de0ae419cf5f297220deefb3a3fa

COMPLETED=completed/l1jtw85-core-fixes
COMPLETED_HEAD=53ee8758292bacd3821851df22c9be3e0276c8ba
```

## Current checkpoint

Recently completed/promoted repair group:

```text
166,165,162,155,154,152,149,148,146,145,144,143,142,141,140
```

Important rules preserved:

- 850 behavior remains target authority; donor cores are reference only.
- normalized + exact obfuscated parity required.
- DB-first / transaction-first where durable state is involved.
- RAM publication only after durable commit.
- `affectedRows==1` for CAS/one-time mutation authority.
- calculation-heavy formulas use CALC authority; otherwise mark CALC_BLOCKED and skip.

## Ready for promotion

Latest isolated validation:

```text
BUG-850-137=PASS
  inn charge = 300L * amount
  MAX_SAFE_AMOUNT=7158278

BUG-850-136=PASS
  lease INSERT failure compensation
  remove key + refund full Adena
  success only after durable lease insert

BUG-850-132=PASS
  town salary FOR UPDATE + CAS reset
  reset failure / stale claim returns 0
```

Next action:

```text
1. Promote BUG-850-132 / 136 / 137 to completed.
2. Update completed README + work ledger/log.
3. Continue older L2 bugs from <=131.
4. Skip heavy arithmetic cores unless an existing CALC authority matches exactly.
```

## Calculation authority

Primary ledger:

```text
work/l1jtw85-core-fixes:
recovery/CALC_AUTHORITY_LEDGER_20260922.md
```

Latest reusable proofs include:

- contribution threshold / 31% packing;
- damage/reward conservation and largest remainder;
- RNG basis-point boundaries;
- timer/cooldown integer safety;
- house-auction 90% payout / refund / deadline math;
- salary / inn / contribution / karma / house-fee economy invariants.

Latest calculation ledger commit:

```text
fc75c3866ca3de0ae419cf5f297220deefb3a3fa
```

## Do not reopen

- completed decompilation branch is frozen.
- do not overwrite completed fixes with whole-file donor copies.
- do not mark PARTIAL/BLOCKED bugs DONE without missing authority.
- do not recompute a formula if an exact-domain CALC authority already exists.

## Resume command

```text
Read this handoff + main README.
Resume on work/l1jtw85-core-fixes.
First promote 132/136/137, then continue older L2 bugs.
```
