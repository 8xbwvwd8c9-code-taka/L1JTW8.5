# L1JTW8.5 Non-Protobuf Diagnostic Compile

Status: **FAIL**

## Boundary

- Gate: **NON_PROTO_DIAGNOSTIC_ONLY**
- an/** source excluded: **YES**
- a/** + an/** donor compile-reference: **YES**
- All other ai..bj/l1j.server game classes supplied from source: **YES**
- Donor full game JAR on classpath: **NO**

## Result

- Java sources submitted: **779**
- javac exit code: **1**
- Generated class files: **0**
- Donor non-protobuf application classes: **915**
- Built donor non-protobuf classes: **0**
- Missing / extra non-protobuf classes: **915 / 0**
- javac error headers: **14781**
- Error files: **570**

## Top error files

| File | Errors |
|---|---:|
| aj/bk.java | 921 |
| bg/c.java | 629 |
| ap/u.java | 612 |
| aq/c.java | 529 |
| aq/z.java | 462 |
| aj/bf.java | 334 |
| ap/s.java | 320 |
| ap/t.java | 317 |
| aj/az.java | 300 |
| ap/v.java | 292 |
| be/dc.java | 247 |
| aj/bs.java | 230 |
| aj/f.java | 218 |
| aq/s.java | 205 |
| ap/q.java | 186 |
| aq/m.java | 170 |
| aq/j.java | 145 |
| bi/g.java | 143 |
| ap/z.java | 141 |
| as/e.java | 133 |
| bf/a.java | 121 |
| aq/w.java | 117 |
| ai/c.java | 116 |
| as/g.java | 109 |
| as/i.java | 107 |
| bc/d.java | 99 |
| ao/bl.java | 93 |
| ap/e.java | 90 |
| au/g.java | 90 |
| as/k.java | 88 |

## Meaning

This gate isolates an/** protobuf source representability problems. A PASS here is not final full-source recovery PASS.
