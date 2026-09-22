# XML Craft Delta Audit: 381 -> 850

## Scope
381:
- `data/xml/NpcActions/ItemMaking.xml`
- `data/xml/NpcActions/SingleItemMaking.xml`

850:
- `html_craft`
- `craft`
- `craft_exchange`

## NPC 70520
381 SingleItemMaking rows: 42
850 html_craft exact action+npc matches: 39

Missing:
- N: 40524 x1 + 40308 x10000 -> 40031 x5
- O: 40524 x10 + 40308 x100000 -> 40031 x50
- P: 40524 x50 + 40308 x500000 -> 40031 x250

Classification: **DB-only delta candidate**
Target: `html_craft`
No old XML framework port required.

## NPC 80102
381 ItemMaking rows:
- actions 0,2,3,4,5,6,7
- actions b,c,d,e,f

850 already contains exact html_craft rows for:
- 0,2,3,4,5,6,7

Missing:
- b -> 49072 x1; materials 49077 x10 + 40308 x100000; fail HTML fillis10
- c -> 49073 x1; materials 49078 x10 + 40308 x100000; fail HTML fillis10
- d -> 49074 x1; materials 49079 x10 + 40308 x100000; fail HTML fillis10
- e -> 49075 x1; materials 49080 x10 + 40308 x100000; fail HTML fillis10
- f -> 49076 x1; materials 49081 x10 + 40308 x100000; fail HTML fillis10

All output item IDs 49072-49076 exist in 850 item tables.

Classification: **DB-only delta candidate**, pending verification that:
- material IDs 49077-49081 exist
- fail HTML `fillis10` exists in client/server HTML resources
- NPC 80102 is present in authoritative 850 NPC data

Target: `html_craft`

## NPC 80089
Missing 381 recipes:
- request new surprise bag:
  41309 x150 -> 41416 x1
- request gold apple:
  41309 x1000 -> 41310 x1

Output/material IDs exist in 850 item tables.

Classification: **DB-only candidate**, pending authoritative NPC 80089 existence/action-menu verification.

Preferred target:
- `html_craft` if old NPC menu/action is retained
- do not create duplicate native craft entries unless intentionally migrating this NPC interaction to the high-version craft UI

## NPC 70904

381 has 22 recipes.

850 `craft` already contains native equivalents for the same content family:
- craft 135 steel lump
- 136 black mithril
- 137 silver
- 138 gold
- 139 platinum
- 140 silver plate
- 141 gold plate
- 142 platinum plate
- 143 black mithril plate
- 144 black mithril arrow
- 145 silver claw
- 146 silver dualblade
- 147 dark crossbow
- 148 dark claw
- 149 dark dualblade
- 150 blind crossbow
- 151 blind claw
- 152 blind dualblade
- 153 ancient water dragon scale armor
- 154 ancient earth dragon scale armor
- 155 ancient fire dragon scale armor
- 156 ancient wind dragon scale armor

For 21/22 rows, output/material quantities match the 381 XML semantics after ignoring material order.

### Important discrepancy
381 XML:
- black mithril arrow output = item 40747 x5000

850 craft id 144:
- output item 40747 x1

Materials match:
- 40440 x1
- 40443 x1
- 40507 x10
- 40308 x1000

This is a real data-semantic difference and must be reviewed before changing anything.

Classification:
- **DO NOT import NPC 70904 XML recipes into html_craft**
- 850 already migrated this content to its native high-version `craft` table
- importing the XML versions would duplicate crafting paths
- only audit/fix craft id 144 if 381 quantity x5000 is confirmed as the intended authority

## Current result

Pure delta candidates:
- NPC 70520: 3 rows
- NPC 80102: 5 rows
- NPC 80089: 2 rows

Already native in 850:
- NPC 70904 family: 22 rows -> craft IDs 135-156

Total immediate DB-only candidate rows: 10, subject to NPC/HTML/material existence gates.

## Migration policy
1. Never re-import a recipe already represented in 850 native craft.
2. Prefer delta-only SQL packages.
3. Keep each NPC/content family independently installable.
4. For old action-driven NPC recipes, use `html_craft` unless there is a deliberate UI migration.
5. Validate item/material IDs + NPC ID + HTML ID before marking import-ready.


## Final NPC/action availability check

### NPC 70520
850 `spawnlist_npc` contains multiple active spawns for NPC 70520 (煉金術師).
Status: **NPC EXISTS / PASS**

The three missing N/O/P recipes therefore remain valid `html_craft` DB-only candidates, subject only to action-menu/client HTML reachability verification.

### NPC 80102
850 `spawnlist_npc` contains:
- NPC 80102 傢飾商^菲力士
- coordinates 32593,32735 map 4

Status: **NPC EXISTS / PASS**

Existing actions 0,2,3,4,5,6,7 already work through 850 `html_craft`.
Missing actions b,c,d,e,f therefore fit the same server-side mechanism.

Material verification:
- 49077 exists
- 49078 exists
- 49079 exists
- 49080 exists
- 49081 exists

Remaining blocker:
- `fillis10` HTML resource is not present in the current server repository evidence.
- This may be a client Text/HTML resource rather than a server-side file.

Classification: **DB-only server delta, client-resource gate remains**

### NPC 80089
381 spawn data:
- 獎牌兌換管理人
- npcid 80089
- x=32635 y=32741 map=4

850:
- no NPC 80089 spawn found
- no spawn found at the same 32635,32741 location

Status: **NPC MISSING from confirmed 850 spawn data**

Therefore the two recipes are NOT pure html_craft-only imports.
To restore this content as the original NPC flow requires at minimum:
- prove NPC template 80089 exists in authoritative 850 npc table
- add an isolated spawnlist_npc row if template exists
- restore/verify the NPC talk/menu that emits:
  - `request new surprise bag`
  - `request gold apple`
- add the two html_craft rows

If the 850 NPC template/menu no longer exists, preferred migration is to move these two exchanges into an existing 850 high-version craft category instead of restoring the legacy NPC.

Classification: **L2 if native craft conversion; L2-L3 if legacy NPC/menu must be reconstructed**

## Updated actionable split

### Ready server-side DB delta
- 70520 N/O/P: 3 rows
- 80102 b/c/d/e/f: 5 rows (client fillis10 gate)

### Hold
- 80089: 2 rows, because NPC/menu path is absent in confirmed 850 spawn data

### Do not import
- 70904 old XML set: 22 rows, already represented by 850 native craft IDs 135-156

