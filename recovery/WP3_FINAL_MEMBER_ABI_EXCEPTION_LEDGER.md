# WP3 Final Member ABI Exception Ledger

Status: **PASS_WITH_SOURCE_REPRESENTATION_EXCEPTIONS**

## Closed invariants

- Application classes compared: **1109**
- Normalized class set: **0 missing / 0 extra**
- Runtime superclass/interface/inner/enclosing hierarchy: **PASS**
- Non-synthetic field loss: **0**
- Non-synthetic method loss: **0**
- Exceptions attribute mismatches: **0**
- Linkage-flag mismatches excluding BRIDGE/SYNTHETIC: **0**

## Synthetic accessor family

Raw donor-missing synthetic methods: **1347**
Generated javac `access$NNN` methods: **1347**

Classification:
- per-class count mismatches: **0**
- owner+parameter groups: **381**
- identical return multiset groups: **230**
- return-different groups: **151**
- dominant compiler assignment-accessor representation groups: **150**
- exceptional `as/a (Las/a;I)` groups before exact probe: **1**

Exact `as/a` probe result:
- donor target methods: **5 / 5**
- generated target methods: **5 / 5**
- semantic group mismatches: **0**
- gate: **PASS**

Therefore the complete 1347-method synthetic accessor family is classified as compiler/source representation delta, not application member loss.

## Field representation exceptions

Donor-missing fields: **131 / 131 synthetic**.

Generated synthetic-name representation:
- `this$0`: **127**
- captured/enclosing/enum helper fields: **4**
- exact owner+descriptor pairability: **131 / 131**

Recovery-only protobuf fields:
- `l1r_m_Z:Z`: **44**
- donor semantic identity: `m:Z`
- evidence: protobuf boolean accessor transform/probe
- classification: **SOURCE_RECOVERY_REPRESENTATION_EXCEPTION**

Field flag mismatch:
- `aq/ak.l:[I`
- donor 4106 vs generated 10
- delta: **ACC_SYNTHETIC only**
- linkage-relevant flags unchanged

## Generated-only method representation exceptions

- WP2 builder bridge extras: **660**
  - classified by generic builder audit
  - known consequence of raw builder source representation
- protobuf parser anonymous-class generated methods: **968 = 44 × 22**
  - parser recovery already closed
  - generated erased/typed bridge surface differs from donor source shape
- typed `Comparator.compare(T,T)` helpers: **9**
  - compiler generic bridge/source representation
- enum `$values()` helpers: **2**
  - compiler enum helper representation
- residual unclassified generated extras: **0**

## Generic method Signature exceptions

Exactly **3** method Signature mismatches remain:

1. `ao/a$1.<init>(Lao/a;Lbj/d;)V`
2. `aq/ak$a.<init>(Ljava/lang/String;I)V`
3. `aq/l$b.<init>(Ljava/lang/String;I)V`

All three are compiler-generated anonymous/enum constructor metadata:
- runtime descriptor identity exists
- linkage flags are not mismatched
- Exceptions attributes are not mismatched
- classification: **GENERIC_METADATA_ONLY**

## WP3 conclusion

Strict byte-for-byte member identity is intentionally **not** the recovery completion criterion.

The remaining member differences are fully classified compiler/source-representation artifacts. No non-synthetic application field or method loss remains.

Gates:

`NON_SYNTHETIC_FIELD_LOSS=0`

`NON_SYNTHETIC_METHOD_LOSS=0`

`UNCLASSIFIED_MEMBER_DELTAS=0`

`SYNTHETIC_ACCESSOR_SEMANTIC_PARITY=PASS`

`LINKAGE_FLAG_MISMATCHES=0`

`EXCEPTIONS_ATTRIBUTE_MISMATCHES=0`

`MEMBER_ABI_RECOVERY_GATE=PASS`

No application source change is authorized or required by this ledger.
