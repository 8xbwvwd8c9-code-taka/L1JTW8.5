# L1JTW8.5 Full-Tree Source-Only Compile

Status: **FAIL**

## Build boundary

- Game donor JAR on compile classpath: **NO**
- Only repository third-party JARs under `lib/` are used as binary dependencies.
- Recovered game sources: `recovered-src-obf/`.

## Result

- Compiler: **javac 17.0.20.1**
- Source / target: **8 / 8**
- Java sources submitted: **788**
- javac exit code: **1**
- Generated class files: **0**
- Donor application classes: **1109**
- Built donor application classes: **0**
- Missing donor application classes: **1109**
- Extra built classes: **0**

## Validation meaning

- PASS requires javac exit 0 and zero missing donor application classes.
- An extra built class is not automatically accepted; it requires source/ABI review.
- On FAIL, `full_tree_javac.log`, `class_set_missing.txt`, and `class_set_extra.txt` are the recovery queue inputs.
