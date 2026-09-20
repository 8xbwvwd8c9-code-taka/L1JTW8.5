# L1JTW8.5 Hard-Tail Recovery Inventory

- Java sources scanned: **788**
- Files with decompiler/reconstruction markers: **7**
- Java-keyword top-level class names: **2**
- Java-keyword class references found: **102**

## Marker counts

| Marker | Count |
|---|---:|
| PSEUDO_GOTO | 32 |
| UNABLE_STRUCTURE | 6 |
| COULD_NOT_DECOMPILE | 0 |
| WHILE_FALSE | 0 |
| VOID_LOCAL | 1 |

## Java-keyword classes

- `be/do` → `S_SPMR.java` (keyword: `do`)
- `bf/do` → `S_134.java` (keyword: `do`)

## Recovery policy

- Pseudo-GOTO / unstructured files are hard-tail candidates for alternate decompiler output and bytecode arbitration.
- Java-keyword class names require a recovery-only source representation rename; donor bytecode remains unchanged.
- Do not mass-edit gameplay logic to make javac pass.
