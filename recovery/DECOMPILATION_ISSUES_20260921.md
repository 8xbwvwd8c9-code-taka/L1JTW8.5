# L1JTW8.5 Decompilation / Source Recovery Issues

Date: 2026-09-21  
Branch: `analysis/l1jtw85-recovery`  
Donor truth: `l1jserver2.jar`

> Status: **DECOMPILATION / SOURCE RECOVERY NOT FULLY COMPLETE**
>
> The 788 application Java sources compile successfully and reproduce the 1109-class normalized application set, but the embedded 246-class protobuf runtime is not yet recovered to source-only compilable form.

## 問題處理紀錄格式

本報告後續統一採用與 380 / 880 相同的修復紀錄邏輯：

```text
問題
→ 現象 / 錯誤訊息
→ 根因
→ 解決方法
→ 使用工具 / 腳本
→ 驗證方式
→ 狀態
→ 注意事項 / 禁止重做的方法
```

原則：

- 先記「實際發生什麼」，再記「怎麼修」。
- 工具名稱、腳本路徑、輸入/輸出證據要保留。
- donor bytecode / javap / javac error 是主要證據，不用猜。
- Source representation 修正與 gameplay/runtime 行為修改必須分開。
- 如果只是在 recovery compile-ref 動手，必須明確標示，不得誤寫成正式 runtime 改動。
- 未完成項目必須保留 `OPEN`，不得因 application javac=0 就標記全部反編譯完成。

## 快速問題→解法→工具索引

| # | 問題 | 根因 | 解決方法 | 主要工具 / 腳本 | 驗證 | 狀態 |
|---:|---|---|---|---|---|---|
| 1 | Obfuscated identity 無法直接對應 source | package/class/member 全面混淆 | 以 classfile metadata + namespace map 建雙向 identity | `audit-source-identity-collisions.py`, `source_namespace_map.csv` | 1109↔1109 roundtrip | CLOSED |
| 2 | Java keyword 名稱不能編譯 | JVM 名稱合法、Java source identifier 非法 | recovery-only rename + reversible mapping | `build-candidate-stage.py`, `stage_transform.json` | keyword residual=0 | CLOSED |
| 3 | Nested same-name Builder source collision | JVM 可表達、Java source 不可表達 | `L1R_Builder` recovery alias | `repair-normalized-builder-collisions.py` | 9/9 reversible | CLOSED |
| 4 | `a/**` protobuf runtime 與 message type shadow | obfuscated runtime root package collision | relocate `a/** -> l1rpb/**` | `relocate-protobuf-runtime.py` | 246 classes rewritten, reversible | PARTIAL |
| 5 | Builder generic superclass javac regression | decompiler 無法重現 donor generic Signature | 保留 raw superclass，metadata 差異列 exception | `audit-generic-builder-signatures.py` | hierarchy PASS | CLOSED |
| 6 | Parser generic/covariant API 無法解析 | erased bridge + typed return source 表達衝突 | donor-proven typed aliases / preserve real overloads | parser probes + alias experiments | parser errors=0 | CLOSED |
| 7 | Builder typed return / abstract obligation | covariant return + raw generic source 關係不可表達 | 40 typed aliases in recovery compile-ref | compile-ref alias experiment scripts | invalid alias=0 | CLOSED |
| 8 | Decompiler把 synthetic bridges 直接生進 source | source bridge 與 javac bridge collision | 刪除 176 個 explicit bridge-only source methods | `normalize-protobuf-builder-source-bridges.py` | residual=0 | CLOSED |
| 9 | Boolean synthetic accessor 無法合法還原 | donor synthetic getter 被 decompiler 折掉 | 44 個 `l1r_m_Z` recovery fields 保留 read semantics | `probe-protobuf-boolean-accessors.py`, `normalize-protobuf-af-accessors.py` | 44/44 proven | CLOSED |
| 10 | `access$NNN` 名稱/return shape 不同 | javac compiler synthetic generation 差異 | 以 field-op + caller semantic identity 分類 | member ABI auditor + `probe-synthetic-accessor-as-a.py` | 1347 semantic parity PASS | CLOSED |
| 11 | `this$0` / captured / enum synthetic fields 不同 | javac compiler-generated field naming | owner+descriptor semantic pairing | member ABI audit | 131/131 paired | CLOSED |
| 12 | generated-only builder/parser/helper methods | javac generic/enum/bridge generation | exception ledger，不視為 application member loss | `audit-post-javac0-member-abi.py` | residual unclassified=0 | CLOSED |
| 13 | generic method Signature metadata 差異 | anonymous/enum compiler metadata | metadata-only exception | member ABI audit | runtime descriptors match | CLOSED |
| 14 | Non-protobuf local generic / overload / Override 問題 | decompiler type inference / shadowing失真 | targeted per-family normalizers | `normalize-nonprotobuf-*.py` | nonproto errors=0 | CLOSED |
| 15 | SourceFile filename collision | 不同 class 映射同原始檔名 | recovery filename disambiguation | namespace/source mapping tools | hierarchy unaffected | CLOSED |
| 16 | Embedded protobuf runtime source-only 3954 errors | runtime 本身反編譯 source 尚不可編譯 | 分 family 修復，先 `l1rpb/j.java` | Vineflower + `compile-protobuf-runtime-source.py` | source-only class set | OPEN |
| 17 | Broad experimental fixes造成回歸 | 過度全域修改 Signature/bridge/superclass | 改為 donor-evidence isolated A/B | experiment scripts + CI | no regression | CLOSED/LESSON |
| 18 | 單一 decompiler 品質不足 | CFR/Vineflower 在不同 class family 表現不同 | CFR broad + Vineflower hard-tail overrides | CFR, Vineflower | application javac=0 | CLOSED |
| 19 | L1Craft static factory 被 overload shadow | decompiled short name `a(...)` 綁錯 source target | qualify `l1rpb.g.a(...)` | `normalize-l1craft-static-factory.py` | target unchanged | CLOSED |
| 20 | External Builder caller 還引用 source-illegal identity | nested alias 改名後 caller 沒同步 | caller refs rewrite to `L1R_Builder` | builder alias caller scripts | old residual=0 | CLOSED |
| 21 | protobuf relocation 後仍殘留 old imports | import 沒被 broad transform 全部涵蓋 | 13 個 import 精準 rewrite | `normalize-protobuf-runtime-imports.py` | 13/13 | CLOSED |
| 22 | protobuf runtime type name 被 local field/class shadow | short type name 與 source local identity 撞名 | fully-qualified `l1rpb.*` | `normalize-protobuf-runtime-type-shadows.py` | 28+2+2+2 sites | CLOSED |
| 23 | `p$b` nested visibility javac 看不到 | InnerClasses metadata protected/public source mismatch | recovery compile-ref only visibility normalization | `normalize-protobuf-inner-visibility.py` | descriptor/code unchanged | CLOSED |
| 24 | synthetic bridge 被 javac source resolution 隱藏 | compile-ref method flags不利於 source resolution | exact whitelist clear SYNTHETIC | `normalize-protobuf-runtime-bridge-flags.py` | names/descriptors/code unchanged | CLOSED |
| 25 | donor-valid abstract inheritance javac 無法表達 | raw/generic/covariant hierarchy source-unrepresentable | exact donor provider pair prune only safe obligations | `normalize-protobuf-abstract-obligations.py` | javac0, real APIs retained | CLOSED |

## Completion snapshot

### Closed

- Application Java sources: **788**
- Application javac: **0 errors**
- Generated application classes: **1109**
- Normalized class-set parity: **0 missing / 0 extra**
- Runtime hierarchy parity: **PASS**
- Compile-ref integrity: **PASS**
- Generic builder audit: **PASS_WITH_METADATA_DIFFERENCES**
- Member ABI recovery gate: **PASS_WITH_SOURCE_REPRESENTATION_EXCEPTIONS**
- Mapping reversibility: **PASS**
- Donor -> recovered -> donor roundtrip failures: **0**
- Recovered -> donor -> recovered roundtrip failures: **0**

### Still open

- Embedded protobuf runtime source-only recovery:
  - runtime classes: **246**
  - decompiled runtime Java files: **45**
  - source-only javac errors: **3954**
  - generated runtime classes: **0**
  - missing runtime classes: **246**
- Final source-only/runtime dependency closure: **OPEN**
- Final recovery completion gate: **OPEN**

---

## Issue ledger

## 1. Obfuscated package/class/member identities

**State: RESOLVED / MAPPED**

The donor uses heavily obfuscated package, class, field and method names.

Recovery rules:

- donor bytecode identity is authoritative;
- `SourceFile`, `InnerClasses`, `EnclosingMethod` and classfile descriptors are used as identity evidence;
- readable source names are recovery representations only;
- filename similarity is not identity authority.

Current complete application map:

- donor application classes: **1109**
- recovered application classes: **1109**
- forward mapped: **1109**
- reverse mapped: **1109**
- collisions: **0**
- one-to-many: **0**
- many-to-one: **0**

Evidence:

- `recovery/source_namespace_map.csv`
- `recovery/normalized_builder_collision_transform.json`
- `recovery/stage_transform.json`

---

## 2. Java-keyword class/member names

**State: RESOLVED WITH REVERSIBLE RECOVERY NAMES**

Some donor bytecode identities are legal JVM names but illegal Java source identifiers.

Top-level examples:

- `be/do.class`
- `bf/do.class`

Recovery-only names:

- `be.do -> be.l1r_do_spmr`
- `bf.do -> bf.l1r_do_s134`

Recovery-only member renames: **4**

These mappings are reversible and do not change gameplay behavior.

---

## 3. Nested same-name / source-illegal class identities

**State: RESOLVED**

The donor contains JVM-valid nested identities that collide when represented as Java source.

Important family:

- 9 protobuf nested Builder collisions.

Example:

`an/a$a$a -> l1r/an/PBMessageALL$L1R_a$L1R_a -> l1r/an/PBMessageALL$L1R_a$L1R_Builder`

All 9 mappings are one-to-one and roundtrip reversible.

---

## 4. Protobuf runtime package/type shadowing

**State: APPLICATION PATH RESOLVED; RUNTIME SOURCE RECOVERY OPEN**

The embedded protobuf runtime originally lives under donor namespace:

`a/**`

This collides with recovered protobuf message/source identities.

Recovery compile namespace:

`a/** -> l1rpb/**`

Facts:

- relocated runtime classes: **246**
- changed classes: **246**
- relocation is reversible;
- gameplay logic changed: **NO**
- relocated binary is **not** a final runtime artifact.

Current dependency:

`recovery/compile-ref-protobuf-l1rpb.jar`

Classification:

**TEMPORARY_BOOTSTRAP_ONLY + SOURCE_RECOVERY_REQUIRED**

---

## 5. Generic builder superclass representation

**State: CLOSED AS SOURCE-REPRESENTATION EXCEPTION**

Donor concrete builders carry generic class `Signature` metadata such as:

`p$a<TBuilder>`

The compilable recovered source currently uses raw `p$a`.

Results:

- builder classes audited: **44**
- donor generic Signature present: **44**
- generated class Signature null: **44**
- superclass descriptor mismatches: **0**
- interface mismatches: **0**
- runtime hierarchy: **PASS**

A broad typed-superclass restore was rejected because it caused a large javac regression.

Interpretation:

- runtime linkage hierarchy: **correct**
- reflective/generic metadata: **different**
- source representation exception: **documented**

---

## 6. Protobuf parser generic/covariant bridge recovery

**State: RESOLVED FOR APPLICATION COMPILE**

Decompiler output could not directly express several donor parser generic/covariant relationships.

Observed failure families included:

- parser `f(InputStream)`
- parser `f(InputStream,n)`
- typed return vs erased `Object`
- abstract interface obligations
- anonymous parser bridge obligations

Recovery outcome:

- parser API recovered;
- 21 typed parser aliases proved;
- direct parser calls preserved;
- `f(InputStream)` and `f(InputStream,n)` explicitly retained;
- parser recovery closed.

Do not prune these overloads.

---

## 7. Protobuf builder typed aliases / covariant returns

**State: RESOLVED / AUDITED**

The recovery compile-ref needs typed aliases so javac can represent donor-valid covariant APIs.

Integrity result:

- active typed aliases: **40**
- parser aliases: **21**
- `a$a` builder aliases: **11**
- `p$a` builder aliases: **8**
- duplicate name+descriptor: **0**
- invalid aliases: **0**
- unexpected existing method changes: **0**
- unexpected class metadata changes: **0**
- existing method Code bytes changed: **0**

Same-parameter / different-return pairs: **40**.

These are valid JVM covariant identities, not duplicate descriptors.

---

## 8. Explicit decompiler synthetic bridge source methods

**State: RESOLVED**

Decompiler output materialized synthetic bridge methods that conflict with javac-generated bridges.

Exactly **176** explicit source bridge declarations were removed from recovered source representation:

- `i()`: 44
- `j()`: 44
- `d(h,n)`: 44
- `c(x)`: 44

The real typed providers remain.

Parser false positives were guarded separately.

---

## 9. Protobuf synthetic boolean accessor source representation

**State: RESOLVED WITH 44 SOURCE-ONLY FIELDS**

Donor audit proved 44 synthetic boolean accessors with semantics:

`getstatic m:Z -> ireturn`

The caller discards the boolean result.

Recovery representation injects:

`l1r_m_Z:Z`

Count:

- recovery-only fields: **44**

Purpose:

- preserve field/class-initialization read semantics;
- avoid illegal/unrepresentable synthetic source shape.

Final comparison normalizes these back to donor `m:Z`.

Gameplay logic changed: **NO**.

---

## 10. javac synthetic accessor renaming / return-shape differences

**State: RESOLVED AS COMPILER REPRESENTATION**

Raw member audit initially reported:

- donor missing methods: **1347**
- generated extra `access$NNN` methods: **1347**

All 1347 donor-missing methods are synthetic.

Classification:

- affected owner+parameter groups: **381**
- identical return groups: **230**
- return-different groups: **151**
- dominant assignment-accessor representation groups: **150**
- exceptional groups requiring exact probe: **1**

The final exceptional group was:

`as/a (Las/a;I)`

Exact bytecode/callsite probe:

- donor methods: **5**
- generated methods: **5**
- semantic group mismatches: **0**
- result: **PASS**

Therefore:

- non-synthetic method loss: **0**
- synthetic accessor semantic parity: **PASS**

---

## 11. Synthetic outer-instance / captured / enum fields

**State: RESOLVED AS COMPILER REPRESENTATION**

Raw field audit:

- donor missing fields: **131**
- generated extras: **175**

All 131 donor-missing fields are synthetic.

Paired compiler forms include:

- `this$0`: **127**
- captured local `val$...`
- enclosing instance `this$1`
- enum `$VALUES`

Exact owner+descriptor pairability: **131 / 131**.

One access-flag difference remains only in `ACC_SYNTHETIC`; linkage-relevant flags match.

---

## 12. Generated-only parser/builder/helper members

**State: CLOSED AS SOURCE/COMPILER REPRESENTATION**

Generated-only classified method families:

- builder bridge extras: **660**
- parser anonymous-class methods: **968 = 44 × 22**
- typed Comparator helpers: **9**
- enum `$values()` helpers: **2**

Residual unclassified generated extras: **0**.

No non-synthetic application member loss remains.

---

## 13. Generic method Signature metadata

**State: CLOSED AS METADATA EXCEPTION**

Exactly **3** method generic Signature mismatches remain, all on compiler-generated anonymous/enum constructors.

Runtime descriptors, linkage flags and Exceptions attributes are valid.

Classification:

**GENERIC_METADATA_ONLY**

---

## 14. Non-protobuf decompiler source problems

**State: RESOLVED FOR APPLICATION COMPILE**

During normalized application recovery, the following decompiler/source problems were encountered and fixed with targeted transforms:

- runtime `g` call shadows;
- `L1Alchemy` raw/local generics;
- `L1Thebes` raw/local generics;
- remaining local generic inference failures;
- non-protobuf overload shadows;
- invalid decompiler `@Override` annotations;
- `L1Account` Base64 source compatibility;
- external nested-builder alias references;
- duplicate local variable names;
- Java-keyword member names;
- nested inner visibility needed for source representation;
- decompiler-generated source collisions.

Current non-protobuf application compile errors: **0**.

Do not reopen without new javac evidence.

---

## 15. SourceFile filename collisions / disambiguation

**State: DOCUMENTED EXCEPTION**

Eight generated SourceFile differences are caused by deliberate recovery filename disambiguation, not hierarchy failure.

Examples:

- `ListSprReader.java -> ListSprReader__obf_b.java`
- `L1Trap.java -> L1Trap__obf_a.java`
- `L1SkillTimer.java -> L1SkillTimer__obf_c.java`

These remain in the final source-representation exception ledger.

---

## 16. Embedded protobuf runtime source decompilation

**State: OPEN — PRIMARY REMAINING BLOCKER**

The 246-class embedded protobuf runtime has been decompiled to **45 Java files**, but the source does not yet compile by itself.

Current source-only experiment:

- binary protobuf runtime on classpath: **NO**
- runtime Java sources: **45**
- expected class files: **246**
- javac exit: **1**
- javac errors: **3954**
- error files: **32**
- generated classes: **0**
- missing classes: **246**
- class-set pass: **NO**

Largest error files:

| File | Errors |
|---|---:|
| `l1rpb/j.java` | 2574 |
| `l1rpb/a.java` | 258 |
| `l1rpb/k.java` | 176 |
| `l1rpb/p.java` | 137 |
| `l1rpb/c.java` | 130 |
| `l1rpb/ap.java` | 115 |

Dominant error families:

| Family | Count |
|---|---:|
| cannot find symbol | 1143 |
| invalid/missing override | 751 |
| same-erasure name clash | 271 |
| ambiguous reference | 200 |
| nested type/package identity failures | repeated |
| invalid static/instance reconstruction | repeated |
| incompatible decompiled generic assignments | repeated |

This is now the primary unfinished decompilation area.

---

## 17. Failed approaches that must not be repeated blindly

**State: REJECTED**

The following broad approaches caused regressions or did not solve the real source representation problem:

- global protobuf `Signature` stripping;
- parser-only mass Signature stripping;
- broad typed builder superclass restoration;
- deleting all synthetic bridges;
- mass source bridge shims without donor evidence;
- global `SYNTHETIC/BRIDGE` flag toggles;
- pruning parser InputStream overloads;
- treating raw class-name differences as class-set failure;
- using full donor game JAR as application compile fallback.

All future fixes must be isolated and donor-evidence driven.

---

## Completion rule

Do **not** claim full decompilation/recovery complete until all are true:

1. 788 application Java sources compile — **PASS**
2. normalized 1109 application class set = 0/0 — **PASS**
3. runtime hierarchy parity — **PASS**
4. member ABI gate / documented reversible exceptions — **PASS**
5. mapping reversibility — **PASS**
6. embedded 246-class protobuf runtime source-only closure — **OPEN**
7. no permanent mutated donor-derived binary compile-ref dependency — **OPEN**
8. final end-to-end recovery gate — **OPEN**

Current accurate wording:

**APPLICATION SOURCE RECOVERY COMPILES COMPLETELY.**

**APPLICATION CLASS SET / HIERARCHY / MEMBER RECOVERY GATES PASS.**

**FULL DECOMPILATION / SOURCE-ONLY RECOVERY IS NOT YET COMPLETE.**


---

## 18. Multi-decompiler fallback / hard-tail overrides

**State: RESOLVED FOR APPLICATION RECOVERY; IMPORTANT PROCESS NOTE**

A single decompiler was not sufficient for all classes.

Recovery used a mixed strategy:

- CFR as the broad initial decompile;
- Vineflower targeted overrides for hard-tail classes;
- Vineflower targeted overrides for protobuf message sources.

Known hard-tail Vineflower overrides:

- `aj/aw.java`
- `aj/bx.java`
- `al/ab.java`
- `ao/aw.java`
- `ao/v.java`
- `be/dc.java`
- `bf/b.java`

Protobuf Vineflower overrides:

- `an/a.java`
- `an/b.java`
- `an/c.java`
- `an/d.java`
- `an/e.java`
- `an/f.java`
- `an/g.java`
- `an/h.java`
- `an/i.java`

This is a decompiler-output quality issue, not donor bytecode corruption.

---

## 19. L1Craft static factory overload shadow

**State: RESOLVED**

Recovered `L1Craft.java` contained calls where decompiled short name `a(...)` was shadowed by methods on `L1Craft` itself.

Recovery qualification:

`l1rpb.g.a(...)`

The transform only qualifies the donor-intended static factory target.

Evidence tool:

`tools/recovery/normalize-l1craft-static-factory.py`

Classification:

**STATIC_IMPORT / OVERLOAD SHADOW SOURCE REPRESENTATION**

Method target changed: **NO**  
Gameplay logic changed: **NO**

---

## 20. Non-protobuf and external protobuf Builder alias references

**State: RESOLVED**

After the 9 nested protobuf Builder identity collisions were repaired, external callers still referenced the source-illegal identity:

`PBMessageALL*.L1R_a.L1R_a`

Recovery caller identity:

`PBMessageALL*.L1R_a.L1R_Builder`

Dedicated transforms handled both non-protobuf and general external caller references.

Evidence tools:

- `normalize-nonproto-builder-aliases.py`
- `normalize-protobuf-builder-caller-refs.py`
- `normalize-protobuf-external-builder-identities.py`

These are source identity rewrites only and normalize back to the donor nested identity.

---

## 21. Residual protobuf runtime imports after namespace relocation

**State: RESOLVED**

The broad `a/** -> l1rpb/**` namespace relocation still left explicit Java imports referring to the old runtime namespace.

Exact residual import rewrites:

**13 / 13**

Transform:

`import a.*` / `import static a.*` -> `l1rpb.*`

Scope:

- import statements only;
- no method/body semantics changed.

Evidence:

`tools/recovery/normalize-protobuf-runtime-imports.py`

---

## 22. Protobuf runtime type-name shadowing inside recovered message source

**State: RESOLVED**

Decompiler output produced short runtime type names that were shadowed by local fields/classes in protobuf message source.

Exact known family:

- `p.a.a(...)` qualification: **28 / 28**
- message blocks with local `ap` shadow: **2 / 2**
- `ap.c()` qualifications: **2 / 2**
- `ap.b()` qualifications: **2 / 2**

Recovery uses fully qualified `l1rpb.*` identities.

Method targets changed: **NO**  
Gameplay logic changed: **NO**

Evidence:

`tools/recovery/normalize-protobuf-runtime-type-shadows.py`

---

## 23. Protobuf nested runtime visibility metadata

**State: RESOLVED FOR RECOVERY COMPILE-REF**

The runtime nested class `p$b` is publicly accessible at the class level, while donor `InnerClasses` metadata describes protected nested visibility.

Recovery javac could not represent/use the relationship cleanly.

Recovery-only compile-ref normalization:

`InnerClasses ACC_PROTECTED -> ACC_PUBLIC`

Target:

`l1rpb/p$b`

Important:

- class access flags changed: **NO**
- method descriptors changed: **NO**
- bytecode changed: **NO**
- donor JAR changed: **NO**
- gameplay logic changed: **NO**

This is compile-time source-representation metadata only.

---

## 24. Protobuf runtime synthetic bridge flags

**State: RESOLVED FOR RECOVERY COMPILE-REF**

Some donor-valid synthetic methods were hidden from javac source resolution because of decompiler/compile-reference bridge representation.

Targeted compile-ref flag normalization was applied only to donor-proven parent methods.

Important boundaries:

- method names unchanged;
- descriptors unchanged;
- Code bytes unchanged;
- donor JAR unchanged;
- no global SYNTHETIC/BRIDGE toggle.

Evidence:

`tools/recovery/normalize-protobuf-runtime-bridge-flags.py`

This must remain an exact whitelist transform.

---

## 25. Protobuf abstract obligation source representability

**State: RESOLVED FOR APPLICATION COMPILE**

After namespace/generic normalization, javac exposed abstract obligations that donor JVM inheritance/covariance already satisfies but recovered Java source cannot express directly.

Recovery-only compile-ref obligation handling used exact donor parent/provider pairs, including:

- `ab <- c`
- `y$a <- b$a`
- `a$a <- p$a`
- `b$a <- p$a`

Only a safe, explicitly proven subset was removed from compile-time abstract obligations.

Important historical finding:

`f(InputStream)` and `f(InputStream,n)` are real public parser APIs and must **not** be pruned.

Evidence:

`tools/recovery/normalize-protobuf-abstract-obligations.py`

Classification:

**SOURCE-UNREPRESENTABLE ABSTRACT/COVARIANT INHERITANCE**

Donor behavior changed: **NO**

---

## Quick omission audit result

The issue ledger was rechecked against the active recovery scripts and transforms.

Newly documented in this pass:

1. multi-decompiler hard-tail / Vineflower override strategy;
2. `L1Craft` static-factory overload shadow;
3. non-protobuf/external Builder caller alias rewrites;
4. 13 residual protobuf runtime import rewrites;
5. protobuf runtime type-name shadows;
6. `p$b` nested visibility metadata normalization;
7. targeted protobuf runtime bridge-flag normalization;
8. donor-proven abstract-obligation source-representation handling.

No evidence from this audit changes the completion state.

**FULL DECOMPILATION / SOURCE-ONLY RECOVERY REMAINS NOT COMPLETE.**

The remaining primary blocker is still the embedded 246-class protobuf runtime source-only recovery.


## 問題處理實例 — 依 380 / 880 格式

### A. Java keyword class / member

**問題**

donor classfile 內出現 `do` 這類 JVM 合法、Java source 不合法的 identifier。

**現象**

反編譯 source 無法直接 javac；keyword class/member 會造成 syntax/identity collision。

**根因**

JVM classfile 對名稱限制與 Java source parser 不相同。Obfuscator 可以產生 JVM 可接受、但 source language 不可重新宣告的名稱。

**解決方法**

只在 recovery source representation 做 reversible rename：

- `be.do -> be.l1r_do_spmr`
- `bf.do -> bf.l1r_do_s134`
- 另外 4 個 member rename rules。

最後 ABI/identity validation 再 normalize 回 donor identity。

**使用工具**

- `build-candidate-stage.py`
- `stage_transform.json`
- `audit-mapping-reversibility.py`

**驗證**

- keyword-member residual=0
- forward/reverse mapping collision=0
- donor/recovered roundtrip failures=0

**狀態**

`CLOSED/PASS`

---

### B. Protobuf nested Builder 同名 collision

**問題**

donor nested identity 在 JVM 層可存在，但 Java source 重新宣告時同名 nested Builder 會衝突。

**現象**

raw class set 會看到：

`...$L1R_a$L1R_a.class`

而 javac recovery source 產生：

`...$L1R_a$L1R_Builder.class`

若用 raw filename 比對會誤判 missing/extra。

**根因**

這是 JVM identity 與 Java source representability 的差異，不是 class 遺失。

**解決方法**

建立 9 條 deterministic collision mappings，source 使用 `L1R_Builder`，最終 comparison normalize 回 donor nested identity。

**使用工具**

- `repair-normalized-builder-collisions.py`
- `normalized_builder_collision_transform.json`
- `source_namespace_map.csv`

**驗證**

- mapped pairs=9
- ambiguous pairs=0
- normalized missing/extra=0/0
- hierarchy PASS

**狀態**

`CLOSED/PASS`

---

### C. Protobuf parser / builder covariant return

**問題**

donor classfile 有 same-parameter / different-return 的 JVM-valid covariant bridge/provider 關係，decompiler 無法直接轉成 javac 可接受 source。

**現象**

曾出現：

- abstract method obligation
- method does not override
- same erasure name clash
- parser InputStream overload resolution failure
- builder return type collision

**根因**

classfile descriptor、generic Signature、bridge flag、erased interface method與 source generic inheritance 同時被 obfuscation/decompiler破壞。

**解決方法**

逐個 blocker 做 donor bytecode probe；只在 recovery compile-ref 增加 donor-proven typed alias。

最後 accepted aliases：

- parser `l1rpb/c.class`: 21
- builder `l1rpb/a$a.class`: 11
- builder `l1rpb/p$a.class`: 8
- total: 40

保留真實 parser APIs：

- `f(InputStream)`
- `f(InputStream,n)`

禁止再 prune。

**使用工具**

- `probe-protobuf-parser-f-bytecode.py`
- `probe-protobuf-builder-provider-map.py`
- `probe-protobuf-builder-e-bytecode.py`
- `audit-protobuf-compile-ref-method-integrity.py`
- 各 `experiment-protobuf-*-alias.py`

**驗證**

- ACTIVE_TYPED_ALIAS_COUNT=40
- DUPLICATE_NAME_DESCRIPTOR_COUNT=0
- INVALID_ALIAS_COUNT=0
- existing method Code changed=0
- unexpected metadata changed=0
- javac=0/0

**狀態**

`CLOSED/PASS`

---

### D. Decompiler explicit synthetic bridges

**問題**

Vineflower/CFR 會把 classfile synthetic bridge materialize 成 Java source method；javac 又會自動產生 bridge，形成 collision。

**現象**

典型 repeated family：

- `i()`
- `j()`
- `d(h,n)`
- `c(x)`

共 176 個 explicit source bridge declarations。

**根因**

Decompiler 忠實顯示 classfile synthetic method，但 source recompilation 不應把這些 bridge 當一般手寫 method 保留。

**解決方法**

只有在 donor bridge + real typed provider 都已證明時，刪除 explicit bridge-only source declaration，讓 javac 自動重新產生必要 bridge。

**使用工具**

- `normalize-protobuf-builder-source-bridges.py`
- donor bridge/provider probes
- normalized javac A/B

**驗證**

- removed=176
- residual=0
- parser false positive=0
- javac regression=NO

**狀態**

`CLOSED/PASS`

---

### E. Synthetic boolean accessor

**問題**

donor 的 synthetic `()Z` getter 被 decompiler 折疊後，Java source 失去原本「讀 static field 並觸發 class-init/read semantics」的表示。

**現象**

如果直接改常數或刪掉 read，雖然可能 compile，但行為證據不再與 donor 一致。

**根因**

synthetic accessor bytecode：

`getstatic m:Z -> ireturn`

caller 會丟棄 boolean return，但 field read 本身仍屬 donor 行為。

**解決方法**

在 recovery source 注入 44 個 `l1r_m_Z` representation fields/reads，保留 field-read semantics，最後 comparison normalize 回 donor `m:Z`。

**使用工具**

- `probe-protobuf-boolean-accessors.py`
- `probe-protobuf-m-field.py`
- `normalize-protobuf-af-accessors.py`

**驗證**

- donor accessor pattern=44/44
- recovery representation=44
- gameplay logic changed=NO

**狀態**

`CLOSED/PASS_WITH_SOURCE_REPRESENTATION_EXCEPTION`

---

### F. javac `access$NNN` synthetic accessor 差異

**問題**

重新 javac 後 synthetic accessor 名稱與 return type 不一定跟 donor compiler 完全一致。

**現象**

初始 member ABI raw diff：

- donor missing synthetic methods=1347
- generated `access$NNN` extras=1347
- descriptor mismatch keys=302

**根因**

這些方法是 compiler synthetic implementation detail；不同 source shape / javac generation 可產生不同 accessor name 與 assignment-return shape。

**解決方法**

不用 method name 當 identity authority，改比：

- owner
- parameter group
- field opcode
- target field
- descriptor
- caller semantics

最後唯一特殊 `as/a (Las/a;I)` 也做 exact probe。

**使用工具**

- `audit-post-javac0-member-abi.py`
- `synthetic_members.csv`
- `probe-synthetic-accessor-as-a.py`
- `javap -p -c -s`

**驗證**

- non-synthetic missing methods=0
- per-class synthetic accessor count mismatch=0
- `as/a`: donor/generated=5/5
- semantic_group_mismatches=0

**狀態**

`CLOSED/PASS`

---

### G. L1Craft static factory overload shadow

**問題**

反編譯後的 `L1Craft.java` 中，protobuf static factory `a(...)` 被同 class 的短名 overload shadow。

**現象**

javac 解析到錯的 `a(...)` candidate，造成 overload/type errors。

**根因**

Obfuscation 後 runtime type與 local/member 都叫 `a`；decompiler 輸出短名後失去原 classfile owner information。

**解決方法**

只把 donor-intended call target fully qualify：

`l1rpb.g.a(...)`

**使用工具**

- `normalize-l1craft-static-factory.py`
- donor descriptor probe
- javac before/after

**驗證**

- transformed sites >0
- method target changed=NO
- gameplay logic changed=NO
- related error family清零

**狀態**

`CLOSED/PASS`

---

### H. Protobuf runtime imports / type shadow

**問題**

把 runtime 從 `a/**` relocate 到 `l1rpb/**` 後，source 裡還有 old imports、short type names、local field shadow。

**現象**

典型 errors：

- package/type does not exist
- ambiguous reference
- non-static method referenced from static context
- local `ap` field 被當 protobuf type

**根因**

Package relocation只改 binary/runtime identity，不會自動修復所有 Java import與 lexical name-resolution。

**解決方法**

分兩層修：

1. residual import rewrite：
   - 13/13 `import a.* -> l1rpb.*`
2. runtime type shadow qualification：
   - `p.a.a(...)`: 28
   - `ap` shadow blocks: 2
   - `ap.c()`: 2
   - `ap.b()`: 2

**使用工具**

- `normalize-protobuf-runtime-imports.py`
- `normalize-protobuf-runtime-type-shadows.py`

**驗證**

- import rewrites=13/13
- type shadow expected counts全部吻合
- call targets changed=NO
- gameplay logic changed=NO

**狀態**

`CLOSED/PASS`

---

### I. InnerClasses visibility / bridge flags / abstract obligations

**問題**

donor JVM hierarchy 可正常運作，但 Java source/javac 對 nested visibility、synthetic method visibility、abstract generic obligation的解析與 classfile runtime規則不完全相同。

**現象**

曾出現：

- protected nested runtime type source不可見
- parent synthetic provider不參與預期 source resolution
- child明明有 donor concrete provider，javac仍報 abstract method obligation

**根因**

Classfile runtime linkage 與 source compiler accessibility/generic obligation不是一套規則；obfuscation與raw generic representation又放大差異。

**解決方法**

只改 recovery compile-ref metadata/obligation：

- `p$b` InnerClasses metadata：protected→public
- exact whitelist synthetic bridge flag normalization
- donor parent/provider exact descriptor evidence後，prune safe abstract obligations

**使用工具**

- `normalize-protobuf-inner-visibility.py`
- `normalize-protobuf-runtime-bridge-flags.py`
- `normalize-protobuf-abstract-obligations.py`
- provider/ABI probes

**驗證**

- class access flags unchanged
- method descriptors unchanged
- bytecode unchanged
- donor JAR unchanged
- javac closed family不回歸

**狀態**

`CLOSED/PASS_FOR_RECOVERY_COMPILE_REF`

---

### J. Non-protobuf decompiler type inference / overload problems

**問題**

非 protobuf application source 也有大量反編譯後 type inference、local generic、overload、Override、Base64 compatibility 等 source 問題。

**現象**

曾集中於：

- `L1Alchemy`
- `L1Thebes`
- `L1Craft`
- runtime `g` calls
- duplicate locals
- invalid `@Override`
- external nested builder aliases
- Java 8 Base64 source compatibility

**根因**

Decompiler 在 obfuscated generic/local symbol環境下無法完整還原 source-level type information。

**解決方法**

每一 error family單獨 normalizer，不做 repo-wide regex 或 blanket cast。

**使用工具**

主要腳本：

- `normalize-l1alchemy-local-generics.py`
- `normalize-l1thebes-local-generics.py`
- `normalize-nonprotobuf-local-generics.py`
- `normalize-nonprotobuf-tail-local-generics*.py`
- `normalize-nonprotobuf-overload-shadows.py`
- `normalize-nonprotobuf-override-annotations.py`
- `normalize-nonprotobuf-runtime-g-calls.py`
- `normalize-l1account-base64-compat.py`
- `normalize-external-builder-alias-refs.py`

**驗證**

`NONPROTO_JAVAC_ERRORS=0`

**狀態**

`CLOSED/PASS`

---

### K. 單一 decompiler 不足 / hard-tail

**問題**

部分 class 用 CFR 產出的 source 無法乾淨重編譯；另一批 protobuf class 用 Vineflower較可用。

**現象**

同一 JAR 不同 class family 的 decompiler output quality差異很大。

**根因**

Decompiler reconstruction heuristics不同；obfuscated generics、synthetic bridge、nested classes、local variable recovery會讓不同工具各有優劣。

**解決方法**

採混合 decompiler strategy：

- CFR：全體 broad pass
- Vineflower：hard-tail / protobuf targeted override
- javap：最終 classfile truth，不把任何 decompiler當 ABI authority

**使用工具**

- CFR
- Vineflower 1.12.0
- `javap`
- `decompile-hard-tail-vineflower.py`
- `decompile-stage2-vineflower.py`
- `decompile-protobuf-runtime-vineflower.py`

**驗證**

Application normalized javac：

`788 sources / 0 errors / 1109 classes`

**狀態**

`CLOSED_FOR_APPLICATION`

---

### L. Embedded protobuf runtime source-only recovery

**問題**

最後 246-class embedded protobuf runtime 尚未能由反編譯 source 自己編譯。

**現象**

目前 source-only baseline：

- Java files=45
- expected classes=246
- javac errors=3954
- error files=32
- generated classes=0
- missing classes=246

最大集中：

- `l1rpb/j.java`=2574
- `l1rpb/a.java`=258
- `l1rpb/k.java`=176
- `l1rpb/p.java`=137
- `l1rpb/c.java`=130
- `l1rpb/ap.java`=115

**根因**

尚未完全分類。已知大量屬：

- nested type identity loss
- generic erasure/name clash
- ambiguous short identifiers
- static/instance reconstruction errors
- invalid overrides
- decompiler type inference loss

目前不能假設只有單一根因。

**解決方法**

按 880/380 相同原則：

1. 先按 javac error family分類；
2. 找 root error，不追 cascade error；
3. 用 donor javap/classfile確認 owner+descriptor；
4. 一次只修一個高信心 representation family；
5. A/B rerun source-only runtime compile；
6. error family下降且無 unrelated regression才接受。

**使用工具**

- Vineflower 1.12.0
- `javap -p -c -s`
- `compile-protobuf-runtime-source.py`
- GitHub Actions `protobuf-runtime-source`
- runtime source experiment artifact
- targeted normalization scripts（依新 family建立）

**驗證**

最終要求：

```text
RUNTIME_JAVA_SOURCE_COMPILE=PASS
GENERATED_RUNTIME_CLASSES=246
MISSING_RUNTIME_CLASSES=0
EXTRA_RUNTIME_CLASSES=0
BINARY_RUNTIME_ON_CLASSPATH=NO
```

**狀態**

`OPEN / PRIMARY REMAINING BLOCKER`


---

## 26. `l1rpb.j$j` same-name nested interface source representation

**State: OPEN / FAILED CANDIDATE RECORDED**

### 問題

Donor 存在：

`l1rpb.j$j`

其中：

- enclosing class：`l1rpb.j`
- nested type simple name：`j`
- nested type flags：`ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT`
- superclass/interface signature：`l1rpb.p$e<l1rpb.j$i>`
- donor `InnerClasses` 明確記錄：
  `j = class l1rpb/j$j of class l1rpb/j`

Vineflower 將它表示為 `j.java` 內：

`public final class j { public interface j { ... } }`

這在 Java source 中不可合法宣告，因為 nested type 與 enclosing type simple name 相同。

### 現象

Source-only runtime baseline：

- runtime Java sources：**45**
- javac exit：**1**
- javac errors：**3970**
- error files：**32**
- `l1rpb/j.java`：**2553**
- generated runtime classes：**0**
- missing runtime classes：**246**
- extra runtime classes：**0**

### 根因

這不是 donor bytecode 錯誤。

根因是：

**JVM nested identity 可表達 `j$j`，但 Java source language 無法在 class `j` 內再次宣告名為 `j` 的 nested type。**

Decompiler 因此產生 source-illegal representation。

### 已測試候選修正

僅在 TEMP 副本做：

1. 移出 1 個 nested-interface declaration；
2. 改寫 7 個 type references；
3. 新增 top-level `j$j.java`；
4. application Java、compile-ref、正式 branch 均未修改。

### 使用工具

- Vineflower 1.12.0
- `javap` / donor classfile metadata
- `compile-protobuf-runtime-source.py`
- Java 8 source/target javac gate
- TEMP source copy A/B

Javac provenance：

```text
javac -encoding UTF-8 -source 8 -target 8 -proc:none
  -Xmaxerrs 20000 -Xmaxwarns 5000
  -d recovery/protobuf-runtime-source-build
  @recovery/protobuf_runtime_source_files.txt
```

`binary_runtime_on_classpath=false`

### 候選結果

After TEMP workaround：

- runtime Java sources：**46**
- javac exit：**1**
- javac errors：**3969**
- error files：**33**
- `l1rpb/j.java`：**2551**
- `l1rpb/j$j.java`：**1**
- generated runtime classes：**0**
- class set：**246 missing / 0 extra**

Error reduction：

`3970 -> 3969`

只減少 **1**。

### 為什麼拒絕

Top-level `j$j.java` 雖可嘗試保留 binary name spelling，但：

1. 無法保留 donor 的 `InnerClasses` enclosing identity；
2. error files 反而從 32 增加到 33；
3. errors 只下降 1；
4. generated runtime classes 仍為 0；
5. source-only class-set 仍為 246 missing。

因此：

**TOP_LEVEL_DOLLAR_WORKAROUND = REJECTED**

### 解決方向

下一步不得再把 `j$j` 當普通 top-level class 解。

需要的是：

**保留 donor nested identity 的 source-representation repair**

可接受方向必須同時滿足：

- recovered source 可 javac；
- generated class internal name 對應 donor `l1rpb/j$j`；
- `InnerClasses` / enclosing relationship 可在 post-javac normalization 後精確還原；
- member descriptors unchanged；
- caller references unchanged after donor normalization；
- no application source changes；
- no permanent donor binary fallback。

若 Java source 本身無法直接表示該 nested identity，允許研究：

- recovery-only legal alias + deterministic post-javac classfile identity restoration；
- 但必須證明 classfile rename / InnerClasses patch / self references / constant-pool owners 全部一致且可逆。

不得只改 filename 或 top-level class name就視為完成。

### 驗證 Gate

至少要求：

```text
SOURCE_COMPILE_ERROR_FAMILY_REDUCED=YES
GENERATED_CLASS_FOR_ALIAS=YES
NORMALIZED_INTERNAL_NAME=l1rpb/j$j
INNERCLASSES_IDENTITY_MATCH=YES
ENCLOSING_IDENTITY_MATCH=YES
METHOD_DESCRIPTOR_DIFF=0
FIELD_DESCRIPTOR_DIFF=0
CALLSITE_OWNER_DIFF=0
GAMEPLAY_LOGIC_CHANGED=NO
```

### 狀態

`OPEN / SOURCE_REPRESENTATION_REPAIR_REQUIRED`

### 禁止重做

- 不再使用 top-level `j$j.java` 當最終解；
- 不以 filename 等同 class identity；
- 不犧牲 donor `InnerClasses` metadata；
- 不用 binary compile-ref 掩蓋 source-only failure；
- 不因 error count -1 就接受 transform。


---

## 27. `l1rpb.j$j` legal alias + post-javac identity restoration

**State: TECHNIQUE_PROVEN / ROOT_FAMILY_NOT_MATERIAL**

### 問題

前一個 WP 已確認 donor `l1rpb.j$j` 是 JVM 合法、Java source 不可直接表達的 same-name nested interface。

前一個 top-level `j$j.java` workaround 因為無法保留 donor `InnerClasses` identity 而被拒絕。

### 本次候選

使用合法 nested source alias：

`j.jj`

讓 javac 產生：

`l1rpb/j$jj`

再做 deterministic post-javac classfile normalization：

`l1rpb/j$jj -> l1rpb/j$j`

並同步修正：

- constant-pool class owner references
- descriptors
- generic signatures
- `InnerClasses.inner_name`: `jj -> j`

### 使用工具

- Java 8 javac
- donor `javap` / classfile metadata
- TEMP source fixture
- exact constant-pool classfile rewrite
- forward/reverse normalization proof
- source-only runtime compile gate

### Donor 證據

Donor nested interface：

`l1rpb.j$j extends l1rpb.p$e<l1rpb.j$i>`

5 個 abstract methods與 source alias interface 完全一致：

- `()Ljava/util/List<Ll1rpb/j$ag;>;`
- `(I)Ll1rpb/j$ag;`
- `()I`
- `()Ljava/util/List<+Ll1rpb/j$ah;>;`
- `(I)Ll1rpb/j$ah;`

### Identity restoration 驗證

```text
SOURCE_ALIAS_COMPILES=YES
GENERATED_ALIAS_CLASS=YES
NORMALIZED_INTERNAL_NAME=l1rpb/j$j
INNERCLASSES_OUTER=l1rpb/j
INNER_NAME=j

FIELD_DESCRIPTOR_DIFF=0
METHOD_DESCRIPTOR_DIFF=0
CALLSITE_OWNER_DIFF=0
INTERFACE_DIFF=0
SIGNATURE_UNCLASSIFIED_DIFF=0
ENCLOSING_METADATA_DIFF=0

INNER_IDENTITY=PASS
DESCRIPTOR_PARITY=PASS
CALLSITE_PARITY=PASS
GAMEPLAY_LOGIC_CHANGED=NO
```

另外：

- normalized caller field/method descriptors 已回到 `Ll1rpb/j$j;`
- stale `l1rpb/j$jj` references = 0
- donor / normalized target 均無 `EnclosingMethod` / Nest metadata
- forward / reverse classfile transform = byte-identical

### Source-only compile結果

Baseline：

```text
JAVAC_ERRORS=3970
J_ERROR=2553
ERROR_FILES=32
GENERATED_RUNTIME_CLASSES=0
MISSING_RUNTIME_CLASSES=246
```

Alias + normalization候選：

```text
JAVAC_ERRORS=3969
J_ERROR=2552
ERROR_FILES=32
GENERATED_RUNTIME_CLASSES=0
MISSING_RUNTIME_CLASSES=246
```

Delta：

`3970 -> 3969`

只下降 **1 error**。

### 結論

這次不是失敗在 identity restoration。

相反地，本次已證明：

**LEGAL_SOURCE_ALIAS + DETERMINISTIC_POST_JAVAC_CLASSFILE_IDENTITY_RESTORATION 是可行技術。**

但 `l1rpb.j$j` 只佔目前 javac frontier 的極小部分，因此：

`TECHNIQUE=PROVEN`

`MATERIAL_FRONTIER_REDUCTION=NO`

不能把它當 WP5 的主要 root fix。

### 為什麼未正式採用

完整 runtime compile仍在 source phase失敗，javac沒有產生任何 runtime class：

`GENERATED_RUNTIME_CLASSES=0/246`

因此完整 pipeline根本還到不了 post-javac normalization stage。

目前優先順序應該是：

1. 先找出 `l1rpb/j.java` 2552 errors中的高占比 root families；
2. 修到 javac開始能產生 runtime classes；
3. 再把本次已證明可行的 alias/identity normalization套進真正需要的 same-name nested identities。

### 下一步

**不要繼續只修單顆 same-name nested identity。**

下一個 WP 必須先對 `l1rpb/j.java` 做 root-cause clustering：

- 按 error message
- symbol
- source line
- declaration/callsite owner
- cascade dependency

找出能一次消掉大量錯誤的前 1–3 個 root families。

### 狀態

`OPEN / TECHNIQUE_PROVEN / MATERIAL_ROOT_NOT_FOUND`
