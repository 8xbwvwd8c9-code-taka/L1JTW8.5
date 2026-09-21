# Post-javac0 Member ABI Classification

Status: **OPEN**

This report classifies the raw member delta from `post_javac0_member_abi.json` without modifying application source.

## Baseline

- Classes compared: **1109**
- Class-set parity: **PASS**
- Runtime hierarchy parity: **PASS**
- Full donor game JAR on javac classpath: **NO**

## Fields

Raw:
- Missing: **131**
- Extra: **175**
- Access-flag mismatches: **1**

Classification:
- Donor missing fields that are synthetic in `synthetic_members.csv`: **131 / 131**
- Non-synthetic donor field missing: **0**
- Exact owner+descriptor synthetic-name pairs: **131 / 131**
  - generated `this$0` pairs: **127**
  - other compiler synthetic field-name pairs: **4**
    - captured local `val$var1`: 1
    - enclosing-instance `this$1`: 1
    - enum `$VALUES`: 2
- Recovery-only protobuf `l1r_m_Z:Z` fields: **44**
  - intentional source-recovery representation
  - existing transform evidence states donor identity is `m:Z`
  - these remain a documented source-representation exception until final closure
- Sole field flag mismatch: `aq/ak.l:[I`
  - donor flags 4106 vs generated 10
  - difference is `ACC_SYNTHETIC` only; linkage flags otherwise unchanged

## Methods

Raw:
- Missing: **1347**
- Extra: **2986**
- Known WP2 builder bridge extras: **660**
- Raw unclassified extras: **2326**

Missing-side classification:
- Donor missing methods synthetic in `synthetic_members.csv`: **1347 / 1347**
- Non-synthetic donor method missing: **0**

Generated-extra classification:
- javac `access$NNN` synthetic accessor family: **1347**
- WP2 builder bridge family: **660**
- protobuf parser anonymous-class family: **968**
  - **44 parser classes × 22 methods each**
- typed `Comparator.compare(T,T)` methods: **9**
- enum `$values()` helpers: **2**
- residual unclassified generated extras after these families: **0**

## Critical accessor result

The donor-synthetic missing method count and generated `access$NNN` count are both **1347**, and per-class counts match for every affected class.

However strict descriptor-multiset parity is **not** proven:
- owner+descriptor multiset mismatch keys: **302**

Therefore this family is a source/compiler representation delta, but it is **not yet acceptable as strict binary ABI parity**.

## Generic / Exceptions

- Method generic Signature mismatches: **3**
- Method Exceptions mismatches: **0**
- Linkage-flag mismatches excluding BRIDGE/SYNTHETIC: **0**

The three method Signature mismatches are constructors in compiler-generated/anonymous or enum-related classes and require final exception-ledger treatment.

## Result

`NON_SYNTHETIC_FIELD_MISSING=0`

`NON_SYNTHETIC_METHOD_MISSING=0`

`RESIDUAL_UNCLASSIFIED_GENERATED_EXTRAS=0`

This materially narrows WP3, but **STRICT_MEMBER_ABI remains OPEN** because:
1. the 1347 donor-synthetic accessor methods are regenerated under different names/descriptors;
2. 44 recovery-only `l1r_m_Z` fields remain;
3. 660 WP2 builder bridges remain generated-only;
4. 968 parser anonymous-class methods remain generated-only;
5. 9 typed comparator methods and 2 enum helpers remain generated-only;
6. three method generic Signature attributes differ.

No application source was modified.
