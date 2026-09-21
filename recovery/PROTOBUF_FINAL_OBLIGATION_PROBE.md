# Final Protobuf Obligation Probe

- All exact donor ABI pairs PASS: **YES**

## parser f(InputStream,n)

- Abstract: `l1rpb.ab.f(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;` exact abstract = **True**
- Provider: `l1rpb.c.f(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;` exact concrete = **True**

## builder d()

- Abstract: `l1rpb.a$a.d()Ll1rpb/a$a;` exact abstract = **True**
- Provider: `l1rpb.p$a.d()Ll1rpb/a$a;` exact concrete = **True**

## Remaining obligation discovery

### remaining parser f(InputStream)

- Abstract owner: `l1rpb.ab`
- Concrete provider candidate: `l1rpb.c`
- Abstract candidates: **1**
- Every candidate has exact concrete descriptor provider: **True**
- Direct generated-source call safety checked: **NO**
- SAFE_TO_PRUNE: **UNKNOWN**

### remaining builder f()

- Abstract owner: `l1rpb.b$a`
- Concrete provider candidate: `l1rpb.p$a`
- Abstract candidates: **1**
- Every candidate has exact concrete descriptor provider: **True**
- Direct generated-source call safety checked: **NO**
- SAFE_TO_PRUNE: **UNKNOWN**

