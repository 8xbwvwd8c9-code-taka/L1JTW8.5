# L1JTW8.5 Candidate Full-Tree Source-Only Compile

Status: **FAIL**

## Candidate transform

- Seven CFR hard-tail files are replaced by Vineflower candidates.
- Java-keyword class names are recovery-only renamed in the ephemeral source tree.
- Donor bytecode is unchanged.

## Build boundary

- Game donor JAR on compile classpath: **NO**
- Only repository third-party JARs under lib/ are used as binary dependencies.
- Candidate source root: **_recovery-stage-src**

## Result

- Compiler: **javac 17.0.20.1**
- Source / target: **8 / 8**
- Java sources submitted: **788**
- javac exit code: **1**
- Generated class files: **0**
- Donor application classes: **1109**
- Built donor application classes after rename normalization: **0**
- Missing / extra after normalization: **1109 / 0**
- Raw missing / extra before normalization: **1109 / 0**

## Interpretation

- PASS requires javac exit 0 and normalized class-set missing/extra = 0/0.
- On FAIL, candidate_full_tree_javac.log is the next recovery queue.
