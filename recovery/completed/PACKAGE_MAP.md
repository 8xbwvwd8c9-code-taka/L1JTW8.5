# Obfuscated Package Map

This table classifies L1JTW8.5 application packages using preserved `SourceFile` names. The original Java package names are not yet reconstructed.

| Obfuscated package | Classes | Category | Current interpretation |
|---|---:|---|---|
| `l1j.server` | 3 | BOOTSTRAP | Server entry / config / database factory |
| `ai` | 5 | SERVER_CORE | GameServer / IDs / packet handler |
| `aj` | 100 | CLIENT_PACKET | Client -> server packet handlers |
| `ak` | 1 | COMMAND_ROUTER | Command dispatch |
| `al` | 58 | GM_COMMAND | GM/admin command implementations |
| `am` | 4 | DATA_READER | Sprite/monster/name readers |
| `an` | 9 | PROTO_MESSAGE | PB message aggregate classes |
| `ao` | 64 | DATATABLE | DB-backed table loaders |
| `ap` | 28 | INSTANCE | Runtime object instances |
| `aq` | 43 | GAME_MODEL | Core game model/services |
| `ar` | 9 | CLASS_FEATURE | Player class feature rules |
| `as` | 11 | GAME_SYSTEM | Dungeon/war/instance systems |
| `at` | 4 | GAME_TIME | Game time |
| `au` | 7 | INVENTORY | Inventory implementations |
| `av` | 1 | ITEM_DELAY | Item delay |
| `aw` | 4 | ITEM_HANDLER | Item use handlers |
| `ax` | 6 | MAP | Map readers/world map |
| `ay` | 6 | PC_MONITOR | Player monitor tasks |
| `az` | 4 | POISON | Poison/paralysis |
| `ba` | 10 | TIMER | Global/system timers |
| `bb` | 2 | TIMER | Effect/NPC timers |
| `bc` | 5 | TIMER | Player/weapon timers |
| `bd` | 10 | TRAP | Trap system |
| `be` | 151 | SERVER_PACKET | Server -> client packets |
| `bf` | 198 | SKILL_EXECUTOR | Skill executor/effect implementations |
| `bg` | 6 | SKILL_CORE | Skill IDs/timers/delay |
| `bh` | 23 | TEMPLATE | Game templates/DTOs |
| `bi` | 11 | UTILITY | Utility/calculation/thread/SQL helpers |
| `bj` | 5 | NETWORK | Cipher/client thread/opcodes |

## High-value donor areas

### Warrior

- `ar.h -> L1WarriorClassFeature.java`
- `bi.c -> CalcInitHpMp.java`
- `bi.d -> CalcStat.java`
- `ap.u -> L1PcInstance.java`
- `aq.c -> L1Attack.java`
- `aq.w -> L1Magic.java`
- `bf.* -> S_###.java` skill implementations
- `bg.b -> L1SkillId.java`

### Craft

- `ao.s -> CraftListTable.java`
- `ao.ac -> HtmlCraftTable.java`
- `aq.k -> L1Craft.java`
- `aj.bs -> C_ProtoBuffers.java`
- `be.dc -> S_ProtoBuffers.java`

### Item / equipment / attributes

- `ao.ah -> ItemTable.java`
- `ap.q -> L1ItemInstance.java`
- `aq.m -> L1EquipmentSlot.java`
- `be.as -> S_EquipmentSlot.java`
- `be.bj -> S_ItemAttribute.java`
- `be.bk -> S_ItemColor.java`
- `be.bl -> S_ItemDesc.java`
- `be.bm -> S_ItemName.java`
- `be.dn -> S_RuneSlot.java`

### Teleport / bookmark

- `aj.a -> C_AddBookmark.java`
- `aj.ad -> C_DeleteBookmark.java`
- `aj.cj -> C_Teleport.java`
- `aj.ck -> C_TeleportUser.java`
- `bh.c -> L1BookMark.java`
- `aq.am -> L1Teleport.java`
- `be.n -> S_Bookmarks.java`
- `be.ek -> S_Teleport.java`

### Network / protocol

- `ai.e -> PacketHandler.java`
- `aj.cv -> ClientBasePacket.java`
- `be.eu -> ServerBasePacket.java`
- `bj.a -> Cipher.java`
- `bj.b -> CipherClient.java`
- `bj.c -> CipherServer.java`
- `bj.d -> ClientThread.java`
- `bj.e -> Opcodes.java`
