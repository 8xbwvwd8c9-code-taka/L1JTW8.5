# L1JTW8.5 Recovery — Conversation Handoff

Date: 2026-09-21

## Mission

Recover `l1jserver2.jar` into readable, source-only compilable Java while preserving donor behavior. This remains source recovery only: no gameplay/DB/protocol changes, no `main` changes.

## Repo / Branch / HEAD

- Repo: `8xbwvwd8c9-code-taka/L1JTW8.5`
- Branch: `analysis/l1jtw85-recovery`
- HEAD: `df371daa40c9020a687b21c6070ab908711c6d23`
- Latest commit: `research: normalize final protobuf abstract obligations`

## Hard Rules

- Donor `l1jserver2.jar` = ABI/behavior ground truth.
- Do not use full donor game JAR as application-class fallback for final source compile.
- Recovery-only namespace/identity/classfile metadata normalization is allowed only when reversible and behavior-preserving.
- Do not globally delete synthetic members.
- Do not globally strip generic `Signature` metadata.
- Do not reopen closed error families without new javac evidence.
- Do not merge to `main`.

## Donor / Identity Facts

- Application class paths: **1109**
- Top-level classes: **788**
- Inner classes: **259**
- Anonymous classes: **62**
- Java-keyword top-level classes: **2** — `be/do.class`, `bf/do.class`
- Nested/enclosing source-name collisions: **37**
- Adjacent parent/child same-name collisions: **33**
- Protobuf runtime-root `a/**` shadow paths under `an/**`: **77**

Interpretation: donor bytecode is valid, but some identities are not legal Java source. Recovery-only aliases are required; gameplay changes are not.

## Current Authoritative Baseline

Use GitHub Actions run **35553175442**, job **106191666320** (`normalized-fast`) as current truth.

Do **not** use the branch copy of `recovery/normalized_stage_compile.json` as the current baseline; it is stale.

Latest normalized-fast compile:

- Java sources: **788**
- full donor game JAR fallback: **NO**
- javac exit: **1**
- generated class files: **259**
- error headers: **132**
- error files: **9**
- Non-Protobuf errors: **0**
- Protobuf errors: **132**

All remaining errors are in the nine `l1r/an/PBMessageALL*.java` files.

### Remaining error groups

The 132 errors are three repeated 44-site groups:

1. **44× direct parser API call resolution**

```text
method f in interface ab<MessageType> cannot be applied to given types
required: InputStream
found:    InputStream,n
```

Typical source:

```java
return a.f(var0, var1);
```

2. **44× builder abstract obligation**

```text
L1R_a / L1R_Builder is not abstract and does not override abstract method f() in a
```

Current split:

- `L1R_a`: 35
- `L1R_Builder`: 9

3. **44× parser anonymous-class obligation**

```text
<anonymous ...> is not abstract and does not override abstract method f(InputStream) in ab
```

Therefore the current blocker is narrowly isolated to Protobuf generic/covariant/abstract-obligation source representability.

## Closed Milestone: Non-Protobuf = 0

The newest normalized-fast path has completely cleared normalized Non-Protobuf compile errors.

Closed transforms include targeted fixes for:

- runtime `g` call shadows
- `L1Alchemy` local generics
- remaining raw local generic batches
- overload shadows
- `L1Thebes` local generics
- invalid decompiler `@Override` annotations
- `L1Account` Base64 source compatibility
- external nested-builder alias references

Do not reopen Non-Protobuf work without new compiler evidence.

## Current Protobuf Recovery Model

### Runtime namespace relocation

Recovery-only:

`a/**` -> `l1rpb/**`

Final ABI comparison must normalize back to donor `a/**`.

### Nested builder identity

JVM-valid/source-illegal same-name nested builders are represented with recovery aliases such as `L1R_Builder`.

### Synthetic boolean accessors

Donor audit proved 44/44 synthetic `()Z` accessors are:

```text
getstatic m:Z
ireturn
```

The caller discards the boolean result. Recovery representation must preserve the field/class-init read, not replace it with arbitrary constants.

### Protected nested runtime type

`p$b.class` is public while donor `InnerClasses` metadata carries protected nested visibility. Recovery compile-ref visibility normalization is source-representation-only and reversible.

## Current Abstract Obligation Normalizer

Tool:

`tools/recovery/normalize-protobuf-abstract-obligations.py`

Exact special-case:

- `l1rpb/b$a.class`
  - `b(Ll1rpb/h;Ll1rpb/n;)Ll1rpb/b$a;`

ABI-derived pair rules:

- `l1rpb/ab.class` <- `l1rpb/c.class`
- `l1rpb/y$a.class` <- `l1rpb/b$a.class`
- `l1rpb/a$a.class` <- `l1rpb/p$a.class`

Current selected obligations:

`l1rpb/ab.class`

- `e(InputStream) -> Object`
- `e(InputStream,n) -> Object`
- `f(InputStream,n) -> Object`

`l1rpb/y$a.class`

- `d(InputStream) -> y$a`
- `d(InputStream,n) -> y$a`

`l1rpb/a$a.class`

- `d() -> a$a`

All changes are to the recovery compile reference only. Donor JAR and gameplay bytecode stay unchanged.

## Highest-Value Next Check

Current evidence strongly suggests `ab.f(InputStream,n)` is over-pruned.

Reason: generated source directly calls:

```java
a.f(var0, var1)
```

but after pruning javac sees only the one-argument overload and reports:

```text
required: InputStream
found: InputStream,n
```

Next experiment should be isolated:

1. Remove only `f(InputStream,n)->Object` from `SAFE_DERIVED['l1rpb/ab.class']`.
2. Remove only that method from the `required` set.
3. Keep all other current obligation rules unchanged.
4. Run `normalized-fast`.
5. Compare against baseline 132 / 9 files / nonproto 0.

PASS signal: the 44 direct call-resolution errors disappear without reopening closed families.

Do **not** blindly add every newly exposed abstract method to `SAFE_DERIVED`. Before pruning any method, verify both donor concrete provider evidence and generated-source direct call sites.

## Current Parser Raw Experiments

Active in normalized-fast:

### `experiment-raw-protobuf-parser-class.py`

Recovery-only `l1rpb/c.class` generic Signature normalization.

### `experiment-raw-protobuf-parser-source.py`

Transforms exactly 44 sites:

`new l1rpb.c<Message>()` -> raw `new l1rpb.c()`

Static field type `ab<Message>` remains unchanged. Anonymous parser body unchanged.

These experiments are part of the 132-error baseline. Do not remove them without A/B evidence.

## Rejected / Failed Approaches

Do not repeat blindly:

- Global Protobuf method Signature stripping: large generic-erasure regression.
- Parser-only `ab.class` method Signature stripping: ~214 -> ~698 errors, ~484 generic-erasure failures.
- Restoring parameterized builder superclass directly: large regression (~217 -> ~569).
- Removing all explicit builder synthetic bridges: regression.
- Mass 44+44 source bridge shims: moved obligations forward / later increased errors.
- Clearing exact or class-scoped `ACC_SYNTHETIC`: no improvement to core bridge family.
- Making Protobuf runtime fully source-compilable first: separate experiment currently ~3954 errors; not the immediate application recovery path.

## Workflow State

Workflow: `.github/workflows/l1jtw85-recovery.yml`

Jobs:

- `protobuf-runtime-source`
- `normalized-fast`
- `recover`

Latest HEAD run **35553175442**:

- `protobuf-runtime-source`: job success; internal runtime source compile still fails with ~3954 errors
- `normalized-fast`: success; authoritative application baseline = 132 errors
- `recover`: technical steps completed, final artifact-commit step failed

The `recover` red status is currently a packaging/pathspec problem, not a recovery logic failure.

Failure:

```text
fatal: pathspec 'recovery/normalized_af_accessor_transform.json' did not match any files
```

Fix later with conditional artifact add / generated manifest / controlled `git add -A`.

## Stale Artifact Warning

Branch copy of `recovery/normalized_stage_compile.json` still shows an old 1507-error result.

Ignore it for current progress.

Current compile truth = Actions run **35553175442**, `normalized-fast` job **106191666320**.

## Next Work Order

### WP-1
Test whether `ab.f(InputStream,n)` was over-pruned.

### WP-2
After WP-1, probe exact donor ABI for:

- parser `f(InputStream)`
- builder `f()`

Do not infer owners from short javac class names; use relocated internal-name/descriptor evidence.

### WP-3
Drive Protobuf 132 -> 0 one family at a time.

### WP-4
Fix full recover artifact commit pathspec so fresh generated reports persist.

### WP-5
After javac 0:

- normalized class-set missing 0 / extra 0
- hierarchy PASS
- ABI/descriptor PASS
- documented recovery aliases normalized back to donor identity
- donor game JAR not used as fallback
- main untouched

## Final PASS Gate

Do not declare COMPLETE until all are true:

- source-only application javac EXIT 0
- normalized class set missing/extra 0/0
- hierarchy PASS
- ABI / field / method descriptors PASS except documented reversible source-representation normalization
- protobuf recovery mappings reversible to donor identity
- main untouched

## Ready-to-Paste A2A

```text
GOAL
Continue L1JTW8.5 recovery on analysis/l1jtw85-recovery from HEAD df371daa40c9020a687b21c6070ab908711c6d23. Drive normalized-fast from current 132 protobuf-only errors to javac0 without gameplay changes.

MUST
- Donor l1jserver2.jar = ABI/behavior truth.
- main untouched.
- Current authority = run 35553175442 / job 106191666320, not stale committed normalized_stage_compile.json.
- Baseline: 788 src, exit1, 132 errors, 9 files, nonproto=0, protobuf=132.
- Preserve closed nonproto work.
- One error family per WP.
- Check direct source call sites before pruning abstract methods.
- Recovery-only identity/namespace/metadata transforms must be reversible.

DO NOT
- No gameplay/DB/protocol changes.
- No full donor game-JAR fallback.
- No global synthetic removal.
- No global Signature stripping.
- No mass source bridge shims without donor evidence.
- Do not reopen nonproto=0 without new javac evidence.

VALIDATE
First WP: remove only ab.f(InputStream,n)->Object from SAFE_DERIVED + required in normalize-protobuf-abstract-obligations.py, rerun normalized-fast, and check whether the 44 direct call-resolution errors disappear without regression.
Then donor-probe exact parser f(InputStream) and builder f() obligations before further pruning.
Final gate: javac0 -> normalized class-set 0/0 -> hierarchy PASS -> ABI PASS.

FINAL
Report HEAD, javac errors/files, protobuf/nonprotobuf counts, exact normalization, donor evidence, gameplay change=NO, next blocker. Never claim COMPLETE early.
```
