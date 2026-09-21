# L1JTW8.5 Normalized Stage Source Compile

Status: **FAIL**

## Boundary

- Normalized game core supplied from source: **YES**
- Full donor game JAR on classpath: **NO**
- Embedded protobuf runtime supplied from relocated recovery-only binary reference: **YES**
- Current transform family: **PROTOBUF_ROOT_PACKAGE_SHADOW**

## Result

- Java sources submitted: **788**
- javac exit code: **4**
- Generated class files: **0**
- javac error headers: **743**
- Error files: **53**

## Top error files

| File | Errors |
|---|---:|
| l1r/an/PBMessageALL7.java | 79 |
| l1r/an/PBMessageALL2.java | 73 |
| l1r/an/PBMessageALL6.java | 70 |
| l1r/an/PBMessageALL4.java | 68 |
| l1r/an/PBMessageALL3.java | 68 |
| l1r/an/PBMessageALL8.java | 68 |
| l1r/an/PBMessageALL.java | 67 |
| l1r/an/PBMessageALL5.java | 67 |
| l1r/an/PBMessageALL9.java | 57 |
| l1r/be/S_ProtoBuffers.java | 21 |
| l1r/aq/L1Craft.java | 19 |
| l1r/aq/L1Alchemy.java | 9 |
| l1r/ap/L1DoorInstance.java | 6 |
| l1r/as/L1ThebesBattle.java | 6 |
| l1r/ap/L1MonsterInstance.java | 5 |
| l1r/aq/L1Character.java | 4 |
| l1r/ap/L1PetInstance.java | 4 |
| l1r/ap/L1PcInstance.java | 3 |
| l1r/ap/L1NpcInstance.java | 3 |
| l1r/aj/C_Result.java | 3 |
| l1r/as/L1OrimBattle.java | 3 |
| l1r/as/L1SoulTower.java | 3 |
| l1r/bh/L1Account.java | 2 |
| l1r/au/L1Inventory.java | 2 |
| l1r/aj/C_ItemUSe.java | 2 |
| l1r/al/L1GfxNpc.java | 2 |
| l1r/ao/DropTable.java | 2 |
| l1r/ao/ShopTable.java | 2 |
| l1r/ap/L1ItemInstance.java | 1 |
| l1r/aj/C_ProtoBuffers.java | 1 |
| l1r/aj/C_ShopWorld.java | 1 |
| l1r/aj/C_SkillBuyItemOK.java | 1 |
| l1r/aj/C_SkillBuyOK.java | 1 |
| l1r/al/L1Buff.java | 1 |
| l1r/al/L1Recall.java | 1 |
| l1r/ao/CharacterEquipment.java | 1 |
| l1r/ao/MobQuestWeekTable.java | 1 |
| l1r/ao/RankingTable.java | 1 |
| l1r/ap/L1TowerInstance.java | 1 |
| l1r/bf/L1SkillExecutor.java | 1 |
| l1r/ap/L1GfxInstance.java | 1 |
| l1r/ap/L1SummonInstance.java | 1 |
| l1r/aq/L1SpawnBoss.java | 1 |
| l1r/as/L1HardinBattle.java | 1 |
| l1r/bd/L1MonsterTrap.java | 1 |
| l1r/be/S_Board.java | 1 |
| l1r/be/S_CharEvent.java | 1 |
| l1r/be/S_FixWeaponList.java | 1 |
| l1r/be/S_Mail.java | 1 |
| l1r/be/S_MapID.java | 1 |

## Next

- Compare against the previous normalized compile (3983 errors / 64 files).
- If protobuf namespace-shadow errors collapse, handle nested same-name builder identities as the next isolated family.
