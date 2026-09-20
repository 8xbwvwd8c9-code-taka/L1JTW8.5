# L1JTW8.5 Normalized Core Compile (PBMessage Isolated)

Diagnostic gate: compile normalized game core while PBMessage source is isolated behind a normalized bytecode reference.

- Java sources (excluding l1r/an): **779**
- javac exit: **1**
- error headers/files: **176 / 68**
- generated classes: **0**
- donor core classes excluding an/**: **915**
- built donor core classes: **0**
- missing / extra core classes: **915 / 0**

## Top failure families

| Error | Files/headers |
|---|---:|
| int cannot be dereferenced | 22 |
| method does not override or implement a method from a supertype | 20 |
| incompatible types: Object cannot be converted to int | 12 |
| 'void' type not allowed here | 11 |
| incompatible types: Object cannot be converted to L1PcInstance | 10 |
| incompatible types: Object cannot be converted to L1Character | 9 |
| cannot find symbol | 7 |
| incompatible types: Object cannot be converted to L1HateList.L1R_a | 7 |
| no suitable constructor found for S_ProtoBuffers(int,String,Object[]) | 6 |
| incompatible types: Object cannot be converted to L1ItemInstance | 5 |
| incompatible types: Object cannot be converted to L1Item | 5 |
| incompatible types: void cannot be converted to boolean | 4 |
| reference to d is ambiguous | 4 |
| incompatible types: Object cannot be converted to L1ShopItem | 4 |
| incompatible types: Object cannot be converted to int[] | 3 |
| incompatible types: Object cannot be converted to L1NpcInstance | 3 |
| incompatible types: Object cannot be converted to L1DoorInstance | 3 |
| incompatible types: Object[] cannot be converted to L1ItemInstance[] | 2 |
| incompatible types: Object cannot be converted to L1PrivateShopSellList | 2 |
| incompatible types: Object cannot be converted to L1PrivateShopBuyList | 2 |
| incompatible types: Object cannot be converted to L1BookMark | 2 |
| package javl1r.ax.L1MapArearypto does not exist | 1 |
| L1Inventory.L1R_a is not abstract and does not override abstract method compare(L1ItemInstance,L1ItemInstance) in Comparator | 1 |
| name clash: compare(Object,Object) in L1Inventory.L1R_a and compare(L1ItemInstance,L1ItemInstance) in Comparator have the same erasure, yet neither overrides the other | 1 |
| incompatible types: Object cannot be converted to ArmorSetTable.L1R_a | 1 |
| incompatible types: Object cannot be converted to String | 1 |
| incompatible types: Object cannot be converted to ArrayList | 1 |
| incompatible types: Object cannot be converted to L1Drop | 1 |
| incompatible types: Object cannot be converted to MobQuestWeekTable.L1R_a | 1 |
| <anonymous l1r.ao.RankingTable$1> is not abstract and does not override abstract method compare(RankingTable.L1R_a,RankingTable.L1R_a) in Comparator | 1 |

## Boundary

- compile-ref-normalized-proto.jar is diagnostic-only.
- Final PASS still requires PBMessage source recovery and donor-free full-tree compile.
