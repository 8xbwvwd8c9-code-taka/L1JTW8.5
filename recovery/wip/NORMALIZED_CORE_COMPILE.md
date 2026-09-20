# L1JTW8.5 Normalized Core Compile (PBMessage Isolated)

Diagnostic gate: compile normalized game core while PBMessage source is isolated behind a normalized bytecode reference.

- Java sources (excluding l1r/an): **779**
- javac exit: **4**
- error headers/files: **99 / 42**
- generated classes: **0**
- donor core classes excluding an/**: **915**
- built donor core classes: **0**
- missing / extra core classes: **915 / 0**

## Top failure families

| Error | Files/headers |
|---|---:|
| incompatible types: byte[] cannot be converted to int | 16 |
| method does not override or implement a method from a supertype | 11 |
| 'void' type not allowed here | 9 |
| no suitable constructor found for S_ProtoBuffers(int,String,Object[]) | 6 |
| incompatible types: Object cannot be converted to L1ItemInstance | 5 |
| incompatible types: Object cannot be converted to L1Item | 5 |
| cannot find symbol | 4 |
| incompatible types: void cannot be converted to boolean | 4 |
| reference to d is ambiguous | 4 |
| incompatible types: Object cannot be converted to int | 4 |
| incompatible types: Object cannot be converted to L1PcInstance | 3 |
| incompatible types: Object cannot be converted to L1NpcInstance | 3 |
| incompatible types: Object cannot be converted to L1DoorInstance | 3 |
| incompatible types: Object[] cannot be converted to L1ItemInstance[] | 2 |
| incompatible types: Object cannot be converted to int[] | 2 |
| incompatible types: String cannot be converted to int | 2 |
| incompatible types: Object cannot be converted to L1HateList.L1R_a | 2 |
| int cannot be dereferenced | 2 |
| incompatible types: Object cannot be converted to ArmorSetTable.L1R_a | 1 |
| incompatible types: Object cannot be converted to L1PrivateShopSellList | 1 |
| incompatible types: Object cannot be converted to L1PrivateShopBuyList | 1 |
| incompatible types: Object cannot be converted to MobQuestWeekTable.L1R_a | 1 |
| incompatible types: Object cannot be converted to L1ShopItem | 1 |
| incompatible types: Object cannot be converted to L1Object | 1 |
| no suitable method found for a(Object) | 1 |
| incompatible types: Object cannot be converted to Point | 1 |
| incompatible types: Object cannot be converted to L1BoardTopic | 1 |
| incompatible types: Object cannot be converted to ShopWorldTable.L1R_b | 1 |
| incompatible types: Object cannot be converted to L1Mail | 1 |
| incompatible types: Object cannot be converted to S_PledgeWarehouseHistory.L1R_a | 1 |

## Boundary

- compile-ref-normalized-proto.jar is diagnostic-only.
- Final PASS still requires PBMessage source recovery and donor-free full-tree compile.
