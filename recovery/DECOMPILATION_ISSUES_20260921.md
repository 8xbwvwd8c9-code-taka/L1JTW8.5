# L1JTW8.5 Decompilation / Source Recovery Issues

Date: 2026-09-21  
Branch: `analysis/l1jtw85-recovery`  
Donor truth: `l1jserver2.jar`

> Status: **DECOMPILATION / SOURCE RECOVERY NOT FULLY COMPLETE**
>
> The 788 application Java sources compile successfully and reproduce the 1109-class normalized application set, but the embedded 246-class protobuf runtime is not yet recovered to source-only compilable form.

## Completion snapshot

### Closed

- Application Java sources: **788**
- Application javac: **0 errors**
- Generated application classes: **1109**
- Normalized class-set parity: **0 missing / 0 extra**
- Runtime hierarchy parity: **PASS**
- Compile-ref integrity: **PASS**
- Generic builder audit: **PASS_WITH_METADATA_DIFFERENCES**
- Member ABI recovery gate: **PASS_WITH_SOURCE_REPRESENTATION_EXCEPTIONS**
- Mapping reversibility: **PASS**
- Donor -> recovered -> donor roundtrip failures: **0**
- Recovered -> donor -> recovered roundtrip failures: **0**

### Still open

- Embedded protobuf runtime source-only recovery:
  - runtime classes: **246**
  - decompiled runtime Java files: **45**
  - source-only javac errors: **3954**
  - generated runtime classes: **0**
  - missing runtime classes: **246**
- Final source-only/runtime dependency closure: **OPEN**
- Final recovery completion gate: **OPEN**

---

## Issue ledger

## 1. Obfuscated package/class/member identities

**State: RESOLVED / MAPPED**

The donor uses heavily obfuscated package, class, field and method names.

Recovery rules:

- donor bytecode identity is authoritative;
- `SourceFile`, `InnerClasses`, `EnclosingMethod` and classfile descriptors are used as identity evidence;
- readable source names are recovery representations only;
- filename similarity is not identity authority.

Current complete application map:

- donor application classes: **1109**
- recovered application classes: **1109**
- forward mapped: **1109**
- reverse mapped: **1109**
- collisions: **0**
- one-to-many: **0**
- many-to-one: **0**

Evidence:

- `recovery/source_namespace_map.csv`
- `recovery/normalized_builder_collision_transform.json`
- `recovery/stage_transform.json`

---

## 2. Java-keyword class/member names

**State: RESOLVED WITH REVERSIBLE RECOVERY NAMES**

Some donor bytecode identities are legal JVM names but illegal Java source identifiers.

Top-level examples:

- `be/do.class`
- `bf/do.class`

Recovery-only names:

- `be.do -> be.l1r_do_spmr`
- `bf.do -> bf.l1r_do_s134`

Recovery-only member renames: **4**

These mappings are reversible and do not change gameplay behavior.

---

## 3. Nested same-name / source-illegal class identities

**State: RESOLVED**

The donor contains JVM-valid nested identities that collide when represented as Java source.

Important family:

- 9 protobuf nested Builder collisions.

Example:

`an/a$a$a -> l1r/an/PBMessageALL$L1R_a$L1R_a -> l1r/an/PBMessageALL$L1R_a$L1R_Builder`

All 9 mappings are one-to-one and roundtrip reversible.

---

## 4. Protobuf runtime package/type shadowing

**State: APPLICATION PATH RESOLVED; RUNTIME SOURCE RECOVERY OPEN**

The embedded protobuf runtime originally lives under donor namespace:

`a/**`

This collides with recovered protobuf message/source identities.

Recovery compile namespace:

`a/** -> l1rpb/**`

Facts:

- relocated runtime classes: **246**
- changed classes: **246**
- relocation is reversible;
- gameplay logic changed: **NO**
- relocated binary is **not** a final runtime artifact.

Current dependency:

`recovery/compile-ref-protobuf-l1rpb.jar`

Classification:

**TEMPORARY_BOOTSTRAP_ONLY + SOURCE_RECOVERY_REQUIRED**

---

## 5. Generic builder superclass representation

**State: CLOSED AS SOURCE-REPRESENTATION EXCEPTION**

Donor concrete builders carry generic class `Signature` metadata such as:

`p$a<TBuilder>`

The compilable recovered source currently uses raw `p$a`.

Results:

- builder classes audited: **44**
- donor generic Signature present: **44**
- generated class Signature null: **44**
- superclass descriptor mismatches: **0**
- interface mismatches: **0**
- runtime hierarchy: **PASS**

A broad typed-superclass restore was rejected because it caused a large javac regression.

Interpretation:

- runtime linkage hierarchy: **correct**
- reflective/generic metadata: **different**
- source representation exception: **documented**

---

## 6. Protobuf parser generic/covariant bridge recovery

**State: RESOLVED FOR APPLICATION COMPILE**

Decompiler output could not directly express several donor parser generic/covariant relationships.

Observed failure families included:

- parser `f(InputStream)`
- parser `f(InputStream,n)`
- typed return vs erased `Object`
- abstract interface obligations
- anonymous parser bridge obligations

Recovery outcome:

- parser API recovered;
- 21 typed parser aliases proved;
- direct parser calls preserved;
- `f(InputStream)` and `f(InputStream,n)` explicitly retained;
- parser recovery closed.

Do not prune these overloads.

---

## 7. Protobuf builder typed aliases / covariant returns

**State: RESOLVED / AUDITED**

The recovery compile-ref needs typed aliases so javac can represent donor-valid covariant APIs.

Integrity result:

- active typed aliases: **40**
- parser aliases: **21**
- `a$a` builder aliases: **11**
- `p$a` builder aliases: **8**
- duplicate name+descriptor: **0**
- invalid aliases: **0**
- unexpected existing method changes: **0**
- unexpected class metadata changes: **0**
- existing method Code bytes changed: **0**

Same-parameter / different-return pairs: **40**.

These are valid JVM covariant identities, not duplicate descriptors.

---

## 8. Explicit decompiler synthetic bridge source methods

**State: RESOLVED**

Decompiler output materialized synthetic bridge methods that conflict with javac-generated bridges.

Exactly **176** explicit source bridge declarations were removed from recovered source representation:

- `i()`: 44
- `j()`: 44
- `d(h,n)`: 44
- `c(x)`: 44

The real typed providers remain.

Parser false positives were guarded separately.

---

## 9. Protobuf synthetic boolean accessor source representation

**State: RESOLVED WITH 44 SOURCE-ONLY FIELDS**

Donor audit proved 44 synthetic boolean accessors with semantics:

`getstatic m:Z -> ireturn`

The caller discards the boolean result.

Recovery representation injects:

`l1r_m_Z:Z`

Count:

- recovery-only fields: **44**

Purpose:

- preserve field/class-initialization read semantics;
- avoid illegal/unrepresentable synthetic source shape.

Final comparison normalizes these back to donor `m:Z`.

Gameplay logic changed: **NO**.

---

## 10. javac synthetic accessor renaming / return-shape differences

**State: RESOLVED AS COMPILER REPRESENTATION**

Raw member audit initially reported:

- donor missing methods: **1347**
- generated extra `access$NNN` methods: **1347**

All 1347 donor-missing methods are synthetic.

Classification:

- affected owner+parameter groups: **381**
- identical return groups: **230**
- return-different groups: **151**
- dominant assignment-accessor representation groups: **150**
- exceptional groups requiring exact probe: **1**

The final exceptional group was:

`as/a (Las/a;I)`

Exact bytecode/callsite probe:

- donor methods: **5**
- generated methods: **5**
- semantic group mismatches: **0**
- result: **PASS**

Therefore:

- non-synthetic method loss: **0**
- synthetic accessor semantic parity: **PASS**

---

## 11. Synthetic outer-instance / captured / enum fields

**State: RESOLVED AS COMPILER REPRESENTATION**

Raw field audit:

- donor missing fields: **131**
- generated extras: **175**

All 131 donor-missing fields are synthetic.

Paired compiler forms include:

- `this$0`: **127**
- captured local `val$...`
- enclosing instance `this$1`
- enum `$VALUES`

Exact owner+descriptor pairability: **131 / 131**.

One access-flag difference remains only in `ACC_SYNTHETIC`; linkage-relevant flags match.

---

## 12. Generated-only parser/builder/helper members

**State: CLOSED AS SOURCE/COMPILER REPRESENTATION**

Generated-only classified method families:

- builder bridge extras: **660**
- parser anonymous-class methods: **968 = 44 × 22**
- typed Comparator helpers: **9**
- enum `$values()` helpers: **2**

Residual unclassified generated extras: **0**.

No non-synthetic application member loss remains.

---

## 13. Generic method Signature metadata

**State: CLOSED AS METADATA EXCEPTION**

Exactly **3** method generic Signature mismatches remain, all on compiler-generated anonymous/enum constructors.

Runtime descriptors, linkage flags and Exceptions attributes are valid.

Classification:

**GENERIC_METADATA_ONLY**

---

## 14. Non-protobuf decompiler source problems

**State: RESOLVED FOR APPLICATION COMPILE**

During normalized application recovery, the following decompiler/source problems were encountered and fixed with targeted transforms:

- runtime `g` call shadows;
- `L1Alchemy` raw/local generics;
- `L1Thebes` raw/local generics;
- remaining local generic inference failures;
- non-protobuf overload shadows;
- invalid decompiler `@Override` annotations;
- `L1Account` Base64 source compatibility;
- external nested-builder alias references;
- duplicate local variable names;
- Java-keyword member names;
- nested inner visibility needed for source representation;
- decompiler-generated source collisions.

Current non-protobuf application compile errors: **0**.

Do not reopen without new javac evidence.

---

## 15. SourceFile filename collisions / disambiguation

**State: DOCUMENTED EXCEPTION**

Eight generated SourceFile differences are caused by deliberate recovery filename disambiguation, not hierarchy failure.

Examples:

- `ListSprReader.java -> ListSprReader__obf_b.java`
- `L1Trap.java -> L1Trap__obf_a.java`
- `L1SkillTimer.java -> L1SkillTimer__obf_c.java`

These remain in the final source-representation exception ledger.

---

## 16. Embedded protobuf runtime source decompilation

**State: OPEN — PRIMARY REMAINING BLOCKER**

The 246-class embedded protobuf runtime has been decompiled to **45 Java files**, but the source does not yet compile by itself.

Current source-only experiment:

- binary protobuf runtime on classpath: **NO**
- runtime Java sources: **45**
- expected class files: **246**
- javac exit: **1**
- javac errors: **3954**
- error files: **32**
- generated classes: **0**
- missing classes: **246**
- class-set pass: **NO**

Largest error files:

| File | Errors |
|---|---:|
| `l1rpb/j.java` | 2574 |
| `l1rpb/a.java` | 258 |
| `l1rpb/k.java` | 176 |
| `l1rpb/p.java` | 137 |
| `l1rpb/c.java` | 130 |
| `l1rpb/ap.java` | 115 |

Dominant error families:

| Family | Count |
|---|---:|
| cannot find symbol | 1143 |
| invalid/missing override | 751 |
| same-erasure name clash | 271 |
| ambiguous reference | 200 |
| nested type/package identity failures | repeated |
| invalid static/instance reconstruction | repeated |
| incompatible decompiled generic assignments | repeated |

This is now the primary unfinished decompilation area.

---

## 17. Failed approaches that must not be repeated blindly

**State: REJECTED**

The following broad approaches caused regressions or did not solve the real source representation problem:

- global protobuf `Signature` stripping;
- parser-only mass Signature stripping;
- broad typed builder superclass restoration;
- deleting all synthetic bridges;
- mass source bridge shims without donor evidence;
- global `SYNTHETIC/BRIDGE` flag toggles;
- pruning parser InputStream overloads;
- treating raw class-name differences as class-set failure;
- using full donor game JAR as application compile fallback.

All future fixes must be isolated and donor-evidence driven.

---

## Completion rule

Do **not** claim full decompilation/recovery complete until all are true:

1. 788 application Java sources compile — **PASS**
2. normalized 1109 application class set = 0/0 — **PASS**
3. runtime hierarchy parity — **PASS**
4. member ABI gate / documented reversible exceptions — **PASS**
5. mapping reversibility — **PASS**
6. embedded 246-class protobuf runtime source-only closure — **OPEN**
7. no permanent mutated donor-derived binary compile-ref dependency — **OPEN**
8. final end-to-end recovery gate — **OPEN**

Current accurate wording:

**APPLICATION SOURCE RECOVERY COMPILES COMPLETELY.**

**APPLICATION CLASS SET / HIERARCHY / MEMBER RECOVERY GATES PASS.**

**FULL DECOMPILATION / SOURCE-ONLY RECOVERY IS NOT YET COMPLETE.**
