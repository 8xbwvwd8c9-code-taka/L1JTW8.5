# Protobuf Parser Typed Alias Experiment

- Recovery compile-ref only.
- Copy exact typed c.d(InputStream[,n])->MessageType method_info as c.f(InputStream[,n])->MessageType aliases.
- Preserve original d methods and existing synthetic f(...)->Object bridges.
- Existing donor method names/descriptors/bytecode: unchanged.
- Recovered source/gameplay: unchanged.
