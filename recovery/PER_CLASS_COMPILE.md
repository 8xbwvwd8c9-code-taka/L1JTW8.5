# L1JTW8.5 Per-Class Compile Gate

Gate: **PER_CLASS_SANITIZED_REFERENCE**

- Targets: **788**
- PASS: **584**
- FAIL: **204**
- PASS rate: **74.11%**

## Boundary

- Each recovered top-level source is compiled independently.
- Dependencies resolve from the sanitized donor reference + repository third-party libraries.
- Sanitized donor clears ACC_SYNTHETIC only.
- This gate can identify locally compilable source but **cannot prove full source recovery**.
- Final completion still requires donor-free source-only full-tree compile and ABI validation.

## Top failure families

| Primary error | Files |
|---|---:|
| cannot find symbol | 127 |
| <identifier> expected | 23 |
| not a statement | 9 |
| int cannot be dereferenced | 5 |
| unreported exception SQLException; must be caught or declared to be thrown | 4 |
| a has private access in eu | 2 |
| constructor cv in class cv cannot be applied to given types; | 2 |
| double cannot be dereferenced | 2 |
| e has private access in aq | 2 |
| <anonymous ao.bf$1> is not abstract and does not override abstract method compare(bf.a,bf.a) in Comparator | 1 |
| <anonymous ao.bk$b$1> is not abstract and does not override abstract method compare(bk.a,bk.a) in Comparator | 1 |
| <anonymous as.j$1> is not abstract and does not override abstract method compare(j.g,j.g) in Comparator | 1 |
| <anonymous be.v$1> is not abstract and does not override abstract method compare(bd.b,bd.b) in Comparator | 1 |
| a has private access in az | 1 |
| a has private access in bg | 1 |
| a type with the same simple name is already defined by the single-type-import of a | 1 |
| a type with the same simple name is already defined by the single-type-import of b | 1 |
| a type with the same simple name is already defined by the single-type-import of g | 1 |
| b has private access in aq | 1 |
| c has private access in ao | 1 |
| c is already defined in this compilation unit | 1 |
| class a is already defined in package av | 1 |
| class a is already defined in package az | 1 |
| class a is already defined in package ba | 1 |
| class a is already defined in package bg | 1 |
| class b is already defined in package az | 1 |
| class c is already defined in package ai | 1 |
| constructor a in class s.a cannot be applied to given types; | 1 |
| d has private access in ax | 1 |
| d is already defined in this compilation unit | 1 |
