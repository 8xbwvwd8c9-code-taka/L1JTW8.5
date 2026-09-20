# L1JTW8.5 Recovery WIP

This directory tracks only unresolved recovery work.

## Current queue

- FAIL top-level classes: **204 / 788**
- Normalized full-tree compile is still incomplete.
- Final gate remains donor-free source-only full-tree compile + class-set + ABI validation.

## Failure families

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
| class c is already defined in package ai | 1 |
| reference to d is ambiguous | 1 |
| incompatible types: ArrayList<u> cannot be converted to Collection<Object> | 1 |
| incompatible types: ArrayList<Integer> cannot be converted to ArrayList<Object> | 1 |
| d is already defined in this compilation unit | 1 |
| d has private access in ax | 1 |
| b has private access in aq | 1 |
| a type with the same simple name is already defined by the single-type-import of g | 1 |
| <anonymous ao.bf$1> is not abstract and does not override abstract method compare(bf.a,bf.a) in Comparator | 1 |
| <anonymous ao.bk$b$1> is not abstract and does not override abstract method compare(bk.a,bk.a) in Comparator | 1 |
| c has private access in ao | 1 |
| a type with the same simple name is already defined by the single-type-import of b | 1 |
| variable house2 is already defined in method a(u) | 1 |
| constructor a in class s.a cannot be applied to given types; | 1 |
| incompatible types: Object cannot be converted to o | 1 |
| <anonymous as.j$1> is not abstract and does not override abstract method compare(j.g,j.g) in Comparator | 1 |
| unreachable statement | 1 |
| class a is already defined in package av | 1 |
| class a is already defined in package az | 1 |
| class b is already defined in package az | 1 |
| class a is already defined in package ba | 1 |
| incompatible types: possible lossy conversion from int to byte | 1 |
| a type with the same simple name is already defined by the single-type-import of a | 1 |
| c is already defined in this compilation unit | 1 |
| <anonymous be.v$1> is not abstract and does not override abstract method compare(bd.b,bd.b) in Comparator | 1 |
| a has private access in bg | 1 |
| a has private access in az | 1 |
| class a is already defined in package bg | 1 |

## Work order

1. Fix Java-source identity/name conflicts and `<identifier> expected`.
2. Resolve normalized `cannot find symbol` families by bytecode-backed member/type mapping.
3. Resolve decompiler type errors (primitive dereference, generic reconstruction, comparator bridges).
4. Re-run per-class compile; promote newly passing classes to processed manifest.
5. Run donor-free normalized full-tree compile.
6. Compare class set, hierarchy, descriptors, then ABI.
