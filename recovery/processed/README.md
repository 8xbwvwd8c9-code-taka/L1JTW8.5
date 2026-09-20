# L1JTW8.5 Processed Recovery Checkpoint

This directory is the frozen "processed side" requested for recovery work separation.

## Gate

- Gate: `PER_CLASS_SANITIZED_REFERENCE`
- Top-level targets: **788**
- PASS: **584**
- FAIL moved to WIP: **204**
- PASS rate: **74.11%**

## What PASS means

A PASS source compiles independently with:
- repository third-party libraries;
- a donor reference JAR where `ACC_SYNTHETIC` is cleared.

This is a strong local recovery checkpoint, but it is **not yet final donor-free full-tree / ABI equivalence**.

## Frozen evidence already completed

- class/source identity mapping
- 1,109 application-class inventory
- 788 top-level / 259 inner / 62 anonymous inventory
- protection/synthetic audit
- 7 CFR hard-tail classes cross-checked with Vineflower
- 9 protobuf top-level sources recovered with Vineflower
- Java-keyword class/member recovery mappings
- 44 protobuf builder boolean-read semantics verified from donor bytecode
- 99 DB tables indexed

Do not move WIP failures into this side until their per-class gate passes.
