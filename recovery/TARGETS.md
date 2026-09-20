# High-Value Recovery Targets

This file prioritizes classes for semantic recovery before attempting a full 788-class source restoration.

## 1. Bootstrap / protocol

| Obfuscated | SourceFile | Purpose |
|---|---|---|
| `l1j.server.a` | `Config.java` | Server configuration |
| `l1j.server.b` | `DatabaseFactory.java` | DB connection pool |
| `ai.c` | `GameServer.java` | Main game-server bootstrap |
| `ai.e` | `PacketHandler.java` | Client packet dispatch |
| `bj.a` | `Cipher.java` | Network cipher base |
| `bj.b` | `CipherClient.java` | Client cipher |
| `bj.c` | `CipherServer.java` | Server cipher |
| `bj.d` | `ClientThread.java` | Client connection thread |
| `bj.e` | `Opcodes.java` | Protocol opcodes |
| `aj.cv` | `ClientBasePacket.java` | Client packet base |
| `be.eu` | `ServerBasePacket.java` | Server packet base |

## 2. Warrior / character / combat

| Obfuscated | SourceFile |
|---|---|
| `ar.h` | `L1WarriorClassFeature.java` |
| `bi.c` | `CalcInitHpMp.java` |
| `bi.d` | `CalcStat.java` |
| `ap.u` | `L1PcInstance.java` |
| `aq.c` | `L1Attack.java` |
| `aq.m` | `L1EquipmentSlot.java` |
| `aq.w` | `L1Magic.java` |
| `bg.b` | `L1SkillId.java` |
| `bf.gl` | `S_228.java` |
| `bf.gm` | `S_229.java` |
| `bf.gn` | `S_230.java` |
| `bf.go` | `S_231.java` |

Skill-number mappings are confirmed by preserved `SourceFile` metadata. Their gameplay names are not assigned here until decompiled behavior is inspected.

## 3. Craft / protobuf

- `ao.s -> CraftListTable.java`
- `ao.ac -> HtmlCraftTable.java`
- `aq.k -> L1Craft.java`
- `aj.bs -> C_ProtoBuffers.java`
- `be.dc -> S_ProtoBuffers.java`

## 4. Teleport / bookmark

- `aj.a -> C_AddBookmark.java`
- `aj.ad -> C_DeleteBookmark.java`
- `aj.an -> C_EnterPortal.java`
- `aj.ax -> C_GotoMap.java`
- `aj.ay -> C_GotoPortal.java`
- `aj.cj -> C_Teleport.java`
- `aj.ck -> C_TeleportUser.java`
- `bh.c -> L1BookMark.java`
- `aq.am -> L1Teleport.java`
- `be.n -> S_Bookmarks.java`
- `be.da -> S_Portal.java`
- `be.dr -> S_SendLocation.java`
- `be.ek -> S_Teleport.java`

## 5. Item / equipment / attribute / rune

- `ao.ah -> ItemTable.java`
- `ao.ak -> LostPowerItemTable.java`
- `ao.bl -> WeaponSkillTable.java`
- `ap.q -> L1ItemInstance.java`
- `aq.m -> L1EquipmentSlot.java`
- `aw.a -> Enchant.java`
- `be.as -> S_EquipmentSlot.java`
- `be.bj -> S_ItemAttribute.java`
- `be.bk -> S_ItemColor.java`
- `be.bl -> S_ItemDesc.java`
- `be.bm -> S_ItemName.java`
- `be.dn -> S_RuneSlot.java`

## 6. Soul Tower

- `ao.bf -> SoulTowerTable.java`
- `as.h -> L1SoulStone.java`
- `as.i -> L1SoulTower.java`

## Validation gate

A class moves from `SOURCEFILE_CONFIRMED` to `SEMANTIC_VERIFIED` only after its decompiled code is inspected and its key dependencies/callers are identified.
