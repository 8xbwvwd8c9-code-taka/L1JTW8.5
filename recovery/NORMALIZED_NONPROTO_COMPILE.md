# L1JTW8.5 Normalized Non-Protobuf Diagnostic Compile

Status: **FAIL**

## Boundary

- Gate: **NORMALIZED_NONPROTO_DIAGNOSTIC_ONLY**
- l1r/an/** source excluded: **YES**
- a/** runtime + normalized l1r/an/** donor compile reference: **YES**
- Other normalized game packages supplied from source: **YES**
- Full donor game JAR on classpath: **NO**
- This gate is diagnostic only; it cannot satisfy final source-only PASS.

## Result

- Java sources submitted: **779**
- javac exit code: **4**
- Generated class files: **0**
- javac error headers: **99**
- Error files: **42**

## Top error files

| File | Errors |
|---|---:|
| l1r/aq/L1Craft.java | 18 |
| l1r/ap/L1DoorInstance.java | 6 |
| l1r/as/L1ThebesBattle.java | 6 |
| l1r/ap/L1MonsterInstance.java | 5 |
| l1r/aq/L1Character.java | 4 |
| l1r/ap/L1PetInstance.java | 4 |
| l1r/aq/L1Alchemy.java | 4 |
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
| l1r/be/S_ProtoBuffers.java | 2 |
| l1r/ap/L1ItemInstance.java | 1 |
| l1r/aj/C_ProtoBuffers.java | 1 |
| l1r/aj/C_ShopWorld.java | 1 |
| l1r/aj/C_SkillBuyItemOK.java | 1 |
| l1r/aj/C_SkillBuyOK.java | 1 |
| l1r/al/L1Buff.java | 1 |
| l1r/al/L1Recall.java | 1 |
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
| l1r/be/S_PacketBox.java | 1 |
| l1r/be/S_PetList.java | 1 |
| l1r/be/S_PledgeWarehouseHistory.java | 1 |

## Meaning

- If this gate is near-clean, protobuf source representation is the dominant blocker.
- Remaining non-protobuf failures become the next isolated error-family queue.
