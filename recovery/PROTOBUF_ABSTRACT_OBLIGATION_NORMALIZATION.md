# Protobuf Abstract Obligation Normalization

- Removed compile-ref abstract obligations: **7 / 7**
- Exact special-case obligations: **1**
- ABI-derived pair rules: **4**
- Derived pruning is restricted to exact donor ABI obligations with concrete providers. Source-visible parser f(InputStream[,n]) declarations remain present; builder f() is pruned only as an inheritance-only synthetic-bridge obligation.
- Required current parser/builder bridge obligation family present: **YES**
- Donor JAR changed: **NO**
- Recovered game source changed: **NO**
- Method bytecode changed: **NO**
- Gameplay logic changed: **NO**
- Scope: recovery compile reference only.
- Final ABI validation remains against the unmodified donor runtime identity.
