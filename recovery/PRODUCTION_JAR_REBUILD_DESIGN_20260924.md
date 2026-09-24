# L1JTW8.5 Production JAR Rebuild Design — 2026-09-24

## Goal

Build a repeatable deployment bridge from the validated repaired source in `completed/l1jtw85-core-fixes` to a runnable obfuscated test JAR without modifying the production `l1jserver2.jar`.

## Authorities

```text
LOCAL=I:\L1JTW8.5
SOURCE_AUTHORITY=completed/l1jtw85-core-fixes@fc473aef65485d1524283fa34d01ab7fad9a7b93
RECOVERY_AUTHORITY=completed/l1jtw85-decompiled@c0c00f9ad36b45dbca75f5c79b7f02ae20b0038c
ACCEPTED_RECOVERY_HEAD=999f2f6571e6984217c33cf0449cc8c7f1679dbd
ORIGINAL_JAR_SHA256=8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
ORIGINAL_JAR_GIT_BLOB=5a0093f8278802aabdb9ab81b3ff68a40815df29
```

## Source selection rule

The completed core-fix branch is the mixed production source authority:

- validated/promoted repairs use the repaired source already present on `completed/l1jtw85-core-fixes`;
- not-yet-repaired cores remain at their original/unrepaired implementation on that branch;
- in-progress/unvalidated work from `work/l1jtw85-core-fixes` is excluded;
- no runtime classes are copied from 381/815/880 donor projects.

This implements the user rule: prefer repaired cores, but for cores still under repair use the unrepaired authority rather than a half-finished work-branch version.

## Existing recovery assets to reuse

- `tools/normalized-recovery/build-normalized-jar.py` — authoritative forward classfile namespace relocation logic.
- `tools/normalized-recovery/compile-normalized-source.py` — normalized javac pipeline.
- `recovery/source_namespace_map.csv` — `OldInternal,NewInternal,Kind` mapping including inner classes.
- `recovery/normalized-src-vf/` — compile-safe normalized source tree maintained with completed repairs.
- `completed/l1jtw85-decompiled/identity/source_only_application_compile.json` — accepted 788-source / 1109-class / javac-zero baseline.

## Missing bridge

Implement an inverse classfile remapper that transforms compiled normalized classes back to original runtime internal names. It must rewrite symbolic class references, descriptors/signatures and `InnerClasses` naming consistently; renaming ZIP/file paths alone is not accepted.

The inverse remapper should reuse the same classfile constant-pool strategy as the existing forward normalizer, with the mapping direction reversed.

## Output policy

Build only:

```text
recovery/production-build/l1jserver2.repaired-test.jar
```

Start from an untouched copy of `l1jserver2.jar`, replace only structurally validated remapped application classes, preserve manifest/resources/non-class entries, and emit an auditable replaced-class manifest.

Never overwrite `l1jserver2.jar` automatically.

## Gates

```text
NORMALIZED_COMPILE: javac exit 0, application class count 1109 unless a documented completed-fix delta explains otherwise
INVERSE_REMAP: every compiled application class maps to an original runtime class
NAMESPACE_LEAK: no unexpected l1r/ application namespace remains in remapped output
STRUCTURE: javap recognizes original runtime class names, inner/nested identities remain coherent
JAR: original manifest/resources preserved, original JAR hash unchanged
RUNTIME: server initialization and port 2000
LOGIN: unchanged 8.50c client must pass account login, character select and enter-game
```

Compilation or server-start alone is not final PASS.

## Boundary

GitHub CI can prove deterministic compile/remap/JAR structural gates. The final DB/server/client login smoke test must run against the user's authoritative Windows runtime at `I:\L1JTW8.5` and unchanged `I:\8.50c客服端\Lin.bin2`.
