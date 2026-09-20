# L1JTW8.5 Recovery Candidate Transform

This transform builds an ephemeral source tree for compile validation. It does not alter donor bytecode.

## Vineflower hard-tail overrides

- aj/aw.java
- aj/bx.java
- al/ab.java
- ao/aw.java
- ao/v.java
- be/dc.java
- bf/b.java

## Java-source representation renames

- be.do -> be.l1r_do_spmr (SourceFile=S_SPMR.java)
- bf.do -> bf.l1r_do_s134 (SourceFile=S_134.java)

Reason: do is a Java language keyword although the JVM classfile name is valid.
These are recovery-only names and must be normalized in later donor-vs-built ABI/class-set comparison.

Files changed by rename references: **18**
