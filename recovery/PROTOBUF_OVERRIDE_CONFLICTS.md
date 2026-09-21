# Protobuf Override Conflict Details

- Override-related javac blocks: **176**
- Normalized structural signatures: **37**

## Signature 1 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:180: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_a
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:179: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:784: error: L1R_Builder is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_Builder extends p.a implements PBMessageALL4.L1R_b {
                          ^
~~~

## Signature 2 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1611: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_c
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:1610: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2215: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_d {
                          ^
~~~

## Signature 3 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:3054: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_e$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_e
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:3053: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3658: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_f {
                          ^
~~~

## Signature 4 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:4489: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_g$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_g
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:4488: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5093: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_h {
                          ^
~~~

## Signature 5 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:173: error: <anonymous l1r.an.PBMessageALL4$L1R_a$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_a> a = new l1rpb.c<PBMessageALL4.L1R_a>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:180: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_a
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:179: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 6 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1604: error: <anonymous l1r.an.PBMessageALL4$L1R_c$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_c> a = new l1rpb.c<PBMessageALL4.L1R_c>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1611: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_c
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:1610: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 7 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:3047: error: <anonymous l1r.an.PBMessageALL4$L1R_e$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_e> a = new l1rpb.c<PBMessageALL4.L1R_e>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3054: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_e$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_e
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:3053: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 8 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:4482: error: <anonymous l1r.an.PBMessageALL4$L1R_g$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_g> a = new l1rpb.c<PBMessageALL4.L1R_g>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4489: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_g$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_g
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:4488: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 9 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:784: error: L1R_Builder is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_Builder extends p.a implements PBMessageALL4.L1R_b {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1604: error: <anonymous l1r.an.PBMessageALL4$L1R_c$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_c> a = new l1rpb.c<PBMessageALL4.L1R_c>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1611: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_c
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
~~~

## Signature 10 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2215: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_d {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3047: error: <anonymous l1r.an.PBMessageALL4$L1R_e$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_e> a = new l1rpb.c<PBMessageALL4.L1R_e>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3054: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_e$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_e
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
~~~

## Signature 11 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:3658: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_f {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4482: error: <anonymous l1r.an.PBMessageALL4$L1R_g$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_g> a = new l1rpb.c<PBMessageALL4.L1R_g>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4489: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_g$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_g
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
~~~

## Signature 12 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:5938: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_i$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_i
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:5937: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:6577: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_j {
                          ^
~~~

## Signature 13 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:179: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:784: error: L1R_Builder is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_Builder extends p.a implements PBMessageALL4.L1R_b {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1604: error: <anonymous l1r.an.PBMessageALL4$L1R_c$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_c> a = new l1rpb.c<PBMessageALL4.L1R_c>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:1611: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_c$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
~~~

## Signature 14 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1610: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:2215: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_d {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3047: error: <anonymous l1r.an.PBMessageALL4$L1R_e$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_e> a = new l1rpb.c<PBMessageALL4.L1R_e>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3054: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_e$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
~~~

## Signature 15 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:3053: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:3658: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_f {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4482: error: <anonymous l1r.an.PBMessageALL4$L1R_g$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_g> a = new l1rpb.c<PBMessageALL4.L1R_g>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:4489: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_g$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
~~~

## Signature 16 — count 7

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:5931: error: <anonymous l1r.an.PBMessageALL4$L1R_i$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_i> a = new l1rpb.c<PBMessageALL4.L1R_i>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5938: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_i$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_i
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
_normalized-stage-src/l1r/an/PBMessageALL4.java:5937: error: method does not override or implement a method from a supertype
         @Override
         ^
~~~

## Signature 17 — count 7

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:5093: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_h {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5931: error: <anonymous l1r.an.PBMessageALL4$L1R_i$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_i> a = new l1rpb.c<PBMessageALL4.L1R_i>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5938: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_i$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_i
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
~~~

## Signature 18 — count 7

~~~text
_normalized-stage-src/l1r/an/PBMessageALL.java:6668: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL.L1R_j {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL2.java:178: error: <anonymous l1r.an.PBMessageALL2$L1R_a$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL2.L1R_a> a = new l1rpb.c<PBMessageALL2.L1R_a>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL2.java:185: error: d(h,n) in <anonymous l1r.an.PBMessageALL2$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
  return type Object is not compatible with L1R_a
  where MessageType is a type-variable:
    MessageType extends Object declared in interface ab
~~~

## Signature 19 — count 7

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:4488: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5093: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL4.L1R_h {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5931: error: <anonymous l1r.an.PBMessageALL4$L1R_i$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL4.L1R_i> a = new l1rpb.c<PBMessageALL4.L1R_i>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL4.java:5938: error: d(h,n) in <anonymous l1r.an.PBMessageALL4$L1R_i$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
~~~

## Signature 20 — count 7

~~~text
_normalized-stage-src/l1r/an/PBMessageALL.java:6063: error: method does not override or implement a method from a supertype
         @Override
         ^
_normalized-stage-src/l1r/an/PBMessageALL.java:6668: error: L1R_a is not abstract and does not override abstract method e(InputStream,n) in a
      public static final class L1R_a extends p.a implements PBMessageALL.L1R_j {
                          ^
_normalized-stage-src/l1r/an/PBMessageALL2.java:178: error: <anonymous l1r.an.PBMessageALL2$L1R_a$1> is not abstract and does not override abstract method d(h,n) in ab
      public static ab<PBMessageALL2.L1R_a> a = new l1rpb.c<PBMessageALL2.L1R_a>() {
                                                                                   ^
_normalized-stage-src/l1r/an/PBMessageALL2.java:185: error: d(h,n) in <anonymous l1r.an.PBMessageALL2$L1R_a$1> cannot implement d(h,n) in ab
         public Object d(l1rpb.h var1, n var2) throws s {
                       ^
~~~

