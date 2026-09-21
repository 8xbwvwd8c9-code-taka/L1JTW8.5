# Protobuf Override Conflict Details

- Override-related javac blocks: **605**
- Normalized structural signatures: **168**

## Signature 1 — count 35

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2914: error: a(h,n) in L1R_a cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2913: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2926: error: a(x) in L1R_a cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
~~~

## Signature 2 — count 35

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2913: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2926: error: a(x) in L1R_a cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2925: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 3 — count 34

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2895: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2914: error: a(h,n) in L1R_a cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2913: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 4 — count 33

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2925: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2956: error: ah() in L1R_a cannot override ah() in a
         public p.a ah() {
                    ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2955: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 5 — count 32

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2926: error: a(x) in L1R_a cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2925: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2956: error: ah() in L1R_a cannot override ah() in a
         public p.a ah() {
                    ^
~~~

## Signature 6 — count 32

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2896: error: ai() in L1R_a cannot override ai() in a
         public p.a ai() {
                    ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2895: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2914: error: a(h,n) in L1R_a cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
~~~

## Signature 7 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1472: error: a(h,n) in L1R_Builder cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1471: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1484: error: a(x) in L1R_Builder cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
~~~

## Signature 8 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1484: error: a(x) in L1R_Builder cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1483: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1514: error: ah() in L1R_Builder cannot override ah() in a
         public p.a ah() {
                    ^
~~~

## Signature 9 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1454: error: ai() in L1R_Builder cannot override ai() in a
         public p.a ai() {
                    ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1453: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1472: error: a(h,n) in L1R_Builder cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
~~~

## Signature 10 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:179: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_a
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:178: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:783: error: L1R_Builder is not abstract and does not override abstract method d() in a
      public static final class L1R_Builder extends p.a<PBMessageALL4.L1R_a.L1R_Builder> implements PBMessageALL4.L1R_b {
                          ^
~~~

## Signature 11 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1609: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_c
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:1608: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2213: error: L1R_a is not abstract and does not override abstract method d() in a
      public static final class L1R_a extends p.a<PBMessageALL4.L1R_c.L1R_a> implements PBMessageALL4.L1R_d {
                          ^
~~~

## Signature 12 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1453: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1472: error: a(h,n) in L1R_Builder cannot override a(h,n) in a
         public l1rpb.a.a a(l1rpb.h var1, n var2) throws IOException {
                          ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1471: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 13 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1471: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1484: error: a(x) in L1R_Builder cannot override a(x) in a
         public l1rpb.a.a a(x var1) {
                          ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1483: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 14 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1483: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1514: error: ah() in L1R_Builder cannot override ah() in a
         public p.a ah() {
                    ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1513: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 15 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:172: error: <anonymous l1r.an.PBMessageALL4$L1R_a$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_a> a = new l1rpb.c<PBMessageALL4.L1R_a>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:179: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_a
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:178: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 16 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1602: error: <anonymous l1r.an.PBMessageALL4$L1R_c$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_c> a = new l1rpb.c<PBMessageALL4.L1R_c>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1609: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_c
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:1608: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 17 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:3044: error: <anonymous l1r.an.PBMessageALL4$L1R_e$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_e> a = new l1rpb.c<PBMessageALL4.L1R_e>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3051: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_e$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_e
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:3050: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 18 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:4478: error: <anonymous l1r.an.PBMessageALL4$L1R_g$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_g> a = new l1rpb.c<PBMessageALL4.L1R_g>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4485: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_g$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_g
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:4484: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 19 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1514: error: ah() in L1R_Builder cannot override ah() in a
         public p.a ah() {
                    ^
  return type a is not compatible with L1R_Builder
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1513: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1602: error: <anonymous l1r.an.PBMessageALL4$L1R_c$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_c> a = new l1rpb.c<PBMessageALL4.L1R_c>() {
                                                                                   ^
~~~

## Signature 20 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2956: error: ah() in L1R_a cannot override ah() in a
         public p.a ah() {
                    ^
  return type a is not compatible with L1R_a
  where BuilderType is a type-variable:
    BuilderType extends a declared in class a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2955: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3044: error: <anonymous l1r.an.PBMessageALL4$L1R_e$1> is not abstract and does not override abstract method e(InputStream,n) in ab
      public static ab<PBMessageALL4.L1R_e> a = new l1rpb.c<PBMessageALL4.L1R_e>() {
                                                                                   ^
~~~

