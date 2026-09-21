# Synthetic Accessor Descriptor Delta Audit

Status: **OPEN / CLASSIFIED**

Scope: the 302 owner+descriptor multiset mismatch keys previously isolated inside the 1347 donor-synthetic-vs-javac-`access$NNN` family.

## Result

The apparent 302 descriptor-key mismatches collapse into **151 owner+parameter groups**.

Across all affected groups:

- Total donor synthetic methods in the family: **1347**
- Total generated `access$NNN` methods: **1347**
- Per-class count mismatches: **0**
- Owner+parameter group count: **381**
- Groups with identical return-type multiset: **230**
- Groups with different return-type multiset: **151**
- Methods inside identical-return groups: **445**
- Methods inside return-different groups: **902**

For the **151 return-different groups**:

- **150 / 151 groups** are the same pattern:
  - donor synthetic accessor return: **void**
  - generated javac accessor return: **type of the last parameter / assigned value**
- These 150 groups are a compiler/source-expression synthetic-accessor representation delta, not loss of a non-synthetic application method.

One exceptional group remains:

`as/a (Las/a;I)`

Donor return multiset:
`V, V, V, V, [[I`

Generated return multiset:
`I, I, I, V, [[I`

This group requires exact bytecode/callsite proof before closure.

## Interpretation

The previous metric:

`OWNER_DESCRIPTOR_MULTISET_MISMATCH_KEYS=302`

does **not** represent 302 unrelated ABI defects. It is the symmetric descriptor-key manifestation of **151 same-owner/same-parameter synthetic accessor groups** whose return representation changed under recompilation.

The dominant pattern is consistent with javac-generated field-assignment accessors returning the assigned value where the donor compiler/source shape emitted a void synthetic accessor.

This classification does not authorize silently dropping strict ABI differences. It narrows the unresolved accessor blocker to one exceptional parameter group plus final policy on compiler-generated synthetic member exactness.

## Gate

`NON_SYNTHETIC_MEMBER_LOSS=0`

`ACCESSOR_CLASS_COUNT_PARITY=PASS`

`ACCESSOR_PARAMETER_GROUP_COUNT_PARITY=PASS`

`DOMINANT_RETURN_REPRESENTATION_GROUPS=150`

`EXCEPTIONAL_GROUPS=1`

`STRICT_BINARY_MEMBER_ABI=OPEN`

No application source modified.
