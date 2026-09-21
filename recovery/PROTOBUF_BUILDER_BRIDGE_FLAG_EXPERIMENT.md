# Protobuf Builder Bridge Flag Experiment

- Abstract owner: l1rpb/y$a.class
- Concrete provider: l1rpb/b$a.class
- Exact synthetic providers selected: **9**
- Rule: same JVM name + descriptor, abstract in y$a, concrete + ACC_SYNTHETIC in b$a.
- Change: clear ACC_SYNTHETIC only on that exact ABI-derived provider set.
- Parser runtime: UNCHANGED
- Donor JAR: UNCHANGED
- Recovered source: UNCHANGED
- Bytecode: UNCHANGED
- Gameplay logic: UNCHANGED
