# L1JTW8.5 Source Identity Collision Audit

Purpose: identify JVM-valid identities that cannot be represented directly by Java source without recovery-only naming normalization.

- Inventory class paths: **1109**
- Java-keyword top-level classes: **2**
- Nested names colliding with an enclosing type: **37**
- Adjacent parent/child same-name collisions: **33**
- an/** class paths containing type name `a` while protobuf runtime root is `a/**`: **77**

## Nested/enclosing collisions

- `ai/c$c.class`
- `am/d$d.class`
- `an/a$a$1.class`
- `an/a$a$a.class`
- `an/a$a.class`
- `an/a$c$a.class`
- `an/a$e$a.class`
- `an/a$g$a.class`
- `an/a$i$a.class`
- `an/b$a$a.class`
- `an/b$b.class`
- `an/c$a$a.class`
- `an/c$c$1.class`
- `an/c$c$a.class`
- `an/c$c.class`
- `an/d$a$a.class`
- `an/d$d.class`
- `an/e$a$a.class`
- `an/e$e$1.class`
- `an/e$e$a.class`
- `an/e$e.class`
- `an/f$a$a.class`
- `an/f$f.class`
- `an/g$a$a.class`
- `an/g$g$1.class`
- `an/g$g$a.class`
- `an/g$g.class`
- `an/h$a$a.class`
- `an/h$h.class`
- `an/i$a$a.class`
- `as/a$a.class`
- `av/a$a.class`
- `az/a$a.class`
- `az/b$b.class`
- `ba/a$a.class`
- `bc/a$a.class`
- `bg/a$a.class`

## Interpretation

- These are source-representation blockers, not evidence of donor gameplay defects.
- Recovery-only renames must be normalized back to donor binary identities during class-set / hierarchy / ABI comparison.
- The protobuf a/** namespace collision must be handled as a representation problem; do not alter message semantics to make javac quiet.
