# L1JTW8.5 Normalized Stage Source Compile

Status: **FAIL**

## Boundary

- Normalized game core supplied from source: **YES**
- Full donor game JAR on classpath: **NO**
- Embedded protobuf runtime supplied from relocated recovery-only binary reference: **YES**
- Current transform family: **PROTOBUF_ROOT_PACKAGE_SHADOW**

## Result

- Java sources submitted: **788**
- javac exit code: **1**
- Generated class files: **259**
- javac error headers: **44**
- Error files: **9**

## Top error files

| File | Errors |
|---|---:|
| l1r/an/PBMessageALL4.java | 5 |
| l1r/an/PBMessageALL.java | 5 |
| l1r/an/PBMessageALL2.java | 5 |
| l1r/an/PBMessageALL3.java | 5 |
| l1r/an/PBMessageALL5.java | 5 |
| l1r/an/PBMessageALL6.java | 5 |
| l1r/an/PBMessageALL7.java | 5 |
| l1r/an/PBMessageALL8.java | 5 |
| l1r/an/PBMessageALL9.java | 4 |

## Next

- Compare against the previous normalized compile (3983 errors / 64 files).
- If protobuf namespace-shadow errors collapse, handle nested same-name builder identities as the next isolated family.
