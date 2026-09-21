# Protobuf 2.5.0 Runtime Identity Fingerprint

Status: **STRONG_MATCH / BINARY_EQUIVALENCE_PENDING**

## Donor embedded runtime

Recovery namespace:

`l1rpb/**`

Known inventory:

```text
TOP_LEVEL_JAVA_SOURCES=45
RUNTIME_CLASSES=246
```

The runtime was extracted from the donor core and relocated only for recovery work.

## Official comparison target

Artifact:

`com.google.protobuf:protobuf-java:2.5.0`

Official source package inventory from tag `v2.5.0`:

- handwritten Java runtime sources: **44**
- generated `DescriptorProtos.java`: **1**
- total Java sources: **45**

CI probe of the official 2.5.0 sources:

```text
VERSION=2.5.0
JAVA_SOURCES=45
COMPILE_EXIT=0
GENERATED_CLASSES=246
```

This exactly matches the donor embedded runtime source/class counts.

## descriptor.proto fingerprint

The donor embedded `DescriptorProtos` data contains:

```text
weak_dependency=true
java_generate_equals_and_hash=true
experimental_map_key=true
cc_generic_services=true
java_generic_services=true
py_generic_services=true
java_string_check_utf8=false
```

Official protobuf-java 2.5.0 source produces the same fingerprint.

## Current conclusion

The combined evidence strongly identifies the embedded runtime as an obfuscated/relocated protobuf-java **2.5.0-era runtime**.

However, WP5 is **not closed yet**.

Exact dependency identity additionally requires evidence that the donor copy has no private semantic modifications.

Required final proof:

1. official binary class count = donor runtime class count = 246;
2. structural class fingerprint equivalence after obfuscation-insensitive normalization;
3. method/field shape parity;
4. bytecode semantic fingerprint parity or equivalent exact evidence;
5. no donor-private classes/members/logic;
6. application compiles/runs source-only against the accepted standard runtime source/dependency.

Until those gates pass:

```text
EXTERNAL_DEPENDENCY_IDENTITY_PROVEN=false
WP5=OPEN
FULL_DECOMPILATION=NOT_COMPLETE
```

## Decompiler evidence

Decompiler source-recovery experiments remain useful as independent confirmation:

```text
Vineflower = 3970 errors / 32 files
CFR normal = 24 errors / 6 files
CFR renamed = 21 errors / 4 files
CFR flat = 6 syntax errors / 5 files
```

Flat CFR is not accepted as the final source representation because disabling normal inner-class reconstruction exposes large nested hierarchy fallout after syntax repair.

The protobuf 2.5.0 identity route is now preferred for WP5 because it avoids accepting decompiler-induced Java-language namespace collisions as runtime semantics.

## Gate

```text
OFFICIAL_2_5_0_SOURCE_COUNT_MATCH=PASS
OFFICIAL_2_5_0_CLASS_COUNT_MATCH=PASS
DESCRIPTOR_PROTO_FINGERPRINT_MATCH=PASS
BINARY_EQUIVALENCE=PENDING
DONOR_PRIVATE_MODIFICATIONS=PENDING
WP5=OPEN
```
