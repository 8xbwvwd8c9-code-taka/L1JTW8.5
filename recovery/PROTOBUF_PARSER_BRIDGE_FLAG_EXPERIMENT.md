# Protobuf Parser Bridge Flag Experiment

- Targets: exact two source-visible c.f(InputStream[,n]) erased provider methods.
- Change: add ACC_BRIDGE only; preserve ACC_PUBLIC and ACC_SYNTHETIC.
- Parser APIs are not pruned.
- Donor JAR/source/bytecode/gameplay: unchanged.
