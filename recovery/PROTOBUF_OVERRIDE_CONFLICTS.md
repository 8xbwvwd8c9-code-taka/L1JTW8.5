# Protobuf Override Conflict Details

- Override-related javac blocks: **176**
- Normalized structural signatures: **16**

## Signature 1 — count 35

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2910: error: d(h,n) in L1R_a cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2922: error: c(x) in L1R_a cannot override c(x) in l1rpb.a.a
         public x.a c(x var1) {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 2 — count 32

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2922: error: c(x) in L1R_a cannot override c(x) in l1rpb.a.a
         public x.a c(x var1) {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2952: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 3 — count 32

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2892: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2910: error: d(h,n) in L1R_a cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 4 — count 23

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:2952: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:4327: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 5 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1479: error: c(x) in L1R_Builder cannot override c(x) in l1rpb.a.a
         public x.a c(x var1) {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1509: error: j() in L1R_Builder cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 6 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1467: error: d(h,n) in L1R_Builder cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1479: error: c(x) in L1R_Builder cannot override c(x) in l1rpb.a.a
         public x.a c(x var1) {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 7 — count 9

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1449: error: i() in L1R_Builder cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:1467: error: d(h,n) in L1R_Builder cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 8 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:1509: error: j() in L1R_Builder cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL4.java:2892: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 9 — count 8

~~~text
_normalized-stage-src/l1r/an/PBMessageALL4.java:7388: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL.java:1450: error: i() in L1R_Builder cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 10 — count 3

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:1550: error: c(x) in L1R_a cannot override c(x) in l1rpb.a.a
         public x.a c(x var1) {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:1568: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 11 — count 2

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:1568: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:1586: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 12 — count 2

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:4068: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:4086: error: d(h,n) in L1R_a cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 13 — count 1

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:6796: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:8703: error: i() in L1R_a cannot override i() in l1rpb.p.a
         public x.a i() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

## Signature 14 — count 1

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:908: error: j() in L1R_Builder cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:1538: error: d(h,n) in L1R_a cannot override d(h,n) in l1rpb.a.a
         public x.a d(l1rpb.h var1, n var2) throws IOException {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.a.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.a.a declared in class l1rpb.a.a
~~~

## Signature 15 — count 1

~~~text
_normalized-stage-src/l1r/an/PBMessageALL9.java:5608: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/ao/DropTable.java:70: warning: [removal] Integer(int) in Integer has been deprecated and marked for removal
               var1.put(new Integer(var13.e()), var14);
                        ^
_normalized-stage-src/l1r/ao/ItemTable.java:108: warning: [removal] Integer(int) in Integer has been deprecated and marked for removal
      g.put("none", new Integer(0));
                    ^
~~~

## Signature 16 — count 1

~~~text
_normalized-stage-src/l1r/an/PBMessageALL2.java:1586: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
_normalized-stage-src/l1r/an/PBMessageALL2.java:4068: error: j() in L1R_a cannot override j() in l1rpb.p.a
         public x.a j() {
                    ^
  return type l1rpb.x.a is not compatible with l1rpb.p.a
  where BuilderType is a type-variable:
    BuilderType extends l1rpb.p.a declared in class l1rpb.p.a
~~~

