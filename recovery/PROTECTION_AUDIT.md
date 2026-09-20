# L1JTW8.5 Classfile Protection Audit

Ground truth: `l1jserver2.jar`. This report is generated directly from classfile structures, not decompiler guesses.

## Inventory

- JAR entries: **3369**
- All JAR class files: **2659**
- Application class files (L1J core filter): **1109**
- Parsed application classes: **1109**
- Top-level: **788**
- Inner: **259**
- Anonymous: **62**
- SourceFile present: **1109 / 1109**
- Class major versions: **{52: 1109}**

## Protection / Java-representation audit

- Synthetic classes: **0**
- Synthetic fields: **132**
- Synthetic methods: **2460**
- Synthetic constructors: **229**
- Classes affected by synthetic members/class flag: **322**
- Bridge methods: **0** across **0** classes
- Same-name/same-args methods differentiated only by return descriptor: **0**
- Same-name fields with multiple descriptors in one class: **0**

## Interpretation gate

- If synthetic member counts are widespread, create a compile-reference JAR that clears only ACC_SYNTHETIC, following the validated L380 recovery method.
- If return-type-only method collisions exist, use descriptor-based rename maps in a recovery-only representation and update every call site consistently.
- Inner/anonymous class inventory must be accounted for before any source-only full-tree compile can be accepted.
- CFR output remains provisional until source-only compilation and ABI/descriptor validation pass.
