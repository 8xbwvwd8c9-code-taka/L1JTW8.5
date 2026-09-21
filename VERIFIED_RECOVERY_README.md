# L1JTW8.5 Verified Recovery Shelf

This branch contains only evidence/results already considered stable enough to preserve separately from active recovery experiments.

Verified snapshot:
- donor container is valid Java 8 bytecode; not whole-JAR encryption
- application class inventory: 1,109
- top-level: 788
- inner: 259
- anonymous: 62
- DB table inventory: 99
- package/source identity mapping retained
- protection/synthetic/keyword-member audits retained
- seven CFR hard-tail files have verified Vineflower alternatives with the known decompiler markers removed

Still WIP and intentionally NOT promoted here:
- protobuf source reconstruction
- recovery-only keyword renames
- compile-reference experiments
- whole-tree source-only compile
- ABI/class-set equivalence
- remaining per-class compile failures

Active work continues on analysis/l1jtw85-recovery.
