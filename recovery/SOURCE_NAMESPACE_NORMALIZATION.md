# L1JTW8.5 Source Namespace Normalization

Recovery-only class/package normalization. Original donor JAR remains unchanged.

- Top-level mappings: **788**
- Application class mappings incl. inner/anonymous: **1109**
- Renamed class entries: **1109**
- Rewritten structural UTF8 entries: **20871**
- Preserved string-constant hits: **0**
- Duplicate SourceFile groups: **3**

Rules:
- Package prefix: l1r/ + original obfuscated package.
- Top-level simple name: preserved SourceFile stem.
- Duplicate SourceFile stem in one package: append __obf_<old-simple>.
- Inner/anonymous suffixes remain donor suffixes.
- String constants are preserved when constant-string-only.
- This JAR is not a runtime replacement.

Duplicate SourceFile groups:
- am/ListSprReader: am/b, am/c
- bd/L1Trap: bd/a, bd/e, bd/i
- bg/L1SkillTimer: bg/c, bg/d, bg/f
