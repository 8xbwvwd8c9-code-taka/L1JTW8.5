# L1JTW8.5 Recovery — WIP

Active unresolved recovery work only.

## Current primary gate

**NORMALIZED_CORE_COMPILE (PBMessage isolated behind diagnostic bytecode reference)**

Current state:

- normalized non-PBMessage Java sources: **779**
- error headers: **119**
- error files: **50**
- final source-only gate: **NO**
- current failure list: `NORMALIZED_CORE_FAIL.csv`

This supersedes the older raw-obfuscated per-class queue as the main repair path.

## Historical diagnostic gate

Sanitized-reference per-class compile:

- PASS: **584 / 788**
- FAIL: **204 / 788**

The 584 PASS rows are frozen under `../completed/per_class_pass.csv`.
The older 204-row queue remains in `PER_CLASS_FAIL.csv` for provenance only.

## Current normalized failure priorities

1. **23 files — cannot find symbol**
   - recover lost owner/member/type identities from donor bytecode.
2. **11 override failures**
   - compare inherited descriptor/hierarchy against donor class.
3. **9 void-expression failures**
   - recover decompiler return-type/side-effect semantics.
4. **Object -> concrete type failures**
   - restore generic/cast information using donor descriptors.
5. **S_ProtoBuffers constructor mismatches**
   - reconcile normalized constructor overloads with donor bytecode.
6. Remaining ambiguity/access/primitive issues.

## Completion gate

1. normalized core compile PASS.
2. PBMessage source compile PASS.
3. donor-free full-tree compile PASS.
4. built application class-set = donor **1109 / 1109**.
5. hierarchy + field/method descriptor audit.
6. ABI freeze.
