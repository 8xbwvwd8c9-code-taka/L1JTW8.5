# L1JTW8.5 Source Namespace Normalization

Recovery-only class/package normalization. Original donor JAR remains unchanged.

- Top-level mappings: **788**
- Application class mappings incl. inner/anonymous: **1109**
- Named inner mappings: **259**
- Renamed class entries: **1109**
- InnerClasses name-index patches: **898**
- Inner-name UTF8 entries added: **786**
- Rewritten structural UTF8 entries: **20868**
- Preserved string-constant hits: **0**
- Duplicate SourceFile groups: **3**

Rules:
- Package prefix: l1r/ + original obfuscated package.
- Top-level simple name: preserved SourceFile stem.
- Duplicate SourceFile stem in one package: append __obf_<old-simple>.
- Named inner segments get L1R_ prefix.
- Numeric anonymous/local segments remain numeric.
- InnerClasses.inner_name_index is patched to the normalized named-inner simple name.
- String constants are preserved when constant-string-only.
- This JAR is not a runtime replacement.

Duplicate SourceFile groups:
- am/ListSprReader: am/b, am/c
- bd/L1Trap: bd/a, bd/e, bd/i
- bg/L1SkillTimer: bg/c, bg/d, bg/f
