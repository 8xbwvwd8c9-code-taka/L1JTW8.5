# L1JTW8.5 Recovery — COMPLETED

Frozen/verified recovery evidence. Active experiments belong in `recovery/wip/`.

Split baseline: `bb8c8e1d880c6f6be4571d39e695ea233ef35ce5`

## Stable facts

- JAR: valid Java 8 bytecode; whole-JAR encryption not observed.
- Application classes: **1109**
  - top-level: **788**
  - inner: **259**
  - anonymous: **62**
- `SourceFile` metadata: **1109 / 1109**
- Top-level source identity map: **788**
- DB tables indexed: **99**
- JVM return-type-only method collisions: **0**
- Synthetic-affected application classes: **322 / 1109**

## Verified completed work

- Package/class identity mapping.
- Classfile protection audit.
- DB table inventory.
- Seven CFR hard-tail files cross-decompiled with Vineflower.
- Nine PBMessage top-level files structurally recovered with Vineflower.
- Two Java-keyword class identities identified and normalized for source representation.
- Four Java-keyword member identities identified and normalized for source representation.
- 44 PBMessage builder boolean-read methods verified against donor bytecode as:
  `invokestatic <message-accessor>():Z -> pop -> return`.
- Sanitized-reference per-class compile: **584 / 788 PASS**.
  These rows are frozen in `per_class_pass.csv`.

## Not completed

Do not treat this directory as a full recovered-source release yet. Remaining gates:

- normalized core compile
- PBMessage source-only compile
- donor-free full-tree compile
- class-set 1109/1109
- hierarchy/descriptors/ABI

Those remain under `recovery/wip/`.
