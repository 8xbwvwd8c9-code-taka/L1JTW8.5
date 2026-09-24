# 381 -> 850 Weapon Attribute Enhancement Audit

## Scope
Module:
- `w_屬性強化系統`

Core:
- `ExtraAttrWeaponTable`
- `L1AttrWeapon`
- `AttrWeaponScroll`
- `AttrWeaponScroll2`
- `L1ItemInstance.attrEnchantKind`
- `L1ItemInstance.attrEnchantLevel`
- `L1AttackPower`
- inventory persistence mask 3072

This is NOT the same module as `w_道具附魔系統` / character_item_power.

## Data model

`w_屬性強化系統` is keyed by:
- id = attribute family
- stage = level within family

Fields include:
- name
- chance
- probability
- gfx
- failure type
- fixed/random damage
- fixed HP/MP drain
- bind
- HP/MP drain
- damage multiplier
- range/range damage
- light damage
- skill effects 1/2/3
- stun
- skill duration
- polymorph list
- remove weapon
- remove doll
- remove armor
- world broadcast

Known families include:
- earth
- water
- fire
- wind
- light
- dark
- holy
- evil
- disarm weapon
- remove doll
- advanced light/dark/evil/holy chains

This is a combat proc/effect framework, not a cosmetic enchant table.

## Per-item persistence

The selected attribute is stored directly on each weapon instance:
- `attrEnchantKind`
- `attrEnchantLevel`

`AttrWeaponScroll` can directly assign kind/stage.

`AttrWeaponScroll2`:
1. target selected weapon object
2. validate weapon/unsealed
3. determine next stage for configured attr family
4. consume one scroll
5. roll stage chance
6. success => update kind/level
7. failure behavior depends on row type
8. persist inventory fields with mask 3072

Thus attribute state survives independently per weapon object.

## Upgrade failure semantics

From `AttrWeaponScroll2`:
- type 0 = fail, keep current level
- type 1 = fail, level -1; level zero clears attribute
- type 2 = fail, destroy target weapon
- type 3 = fail, reset attribute kind+level to zero
- other = keep

This is independent from `server_item_power_update.mode`; do not unify the DB enums unless a new shared abstraction is explicitly designed.

## Combat hook

`L1AttackPc` reads:
- weapon.attrEnchantKind
- weapon.attrEnchantLevel

and creates:
`L1AttackPower(attacker,target,kind,level)`.

`L1AttackPower` loads:
`ExtraAttrWeaponTable.get(kind,level)`

and can apply runtime effects including:
- bind/paralysis-style hold
- HP drain
- MP drain
- outgoing damage multiplier
- area/range damage
- light/additional damage
- skill-state effects
- forced polymorph
- stun
- target weapon removal/disarm
- target doll removal
- random armor removal

Therefore the table is deeply integrated into the attack pipeline.

## Important distinction from vanilla elemental enchant

`L1AttackPc.calcAttrEnchantDmg()` also contains ordinary elemental attribute-enchant damage behavior.

The custom `w_屬性強化系統` adds `L1AttackPower` proc semantics on top.

Therefore target implementation must distinguish:
1. standard element enchant representation/damage
2. custom extended attribute proc system

Do not assume native elemental enchant support is equivalent.

## 850 comparison

Current 850 source search did not confirm:
- attrEnchantKind
- attrEnchantLevel
- ExtraAttrWeaponTable
- L1AttrWeapon
- persistence mask 3072 equivalent

Therefore 850 does not currently expose a proven compatible implementation.

This means the custom system cannot be migrated as DB-only content.

## Client/protocol dependency

Current interaction uses ordinary target-item use and server item/status/name packets.
No dedicated custom protocol has been proven.

However:
- effect gfx IDs
- polymorph IDs
- item-name rendering expectations

may require client resource validation.

Classification:
- core dependency = YES
- DB dependency = YES
- per-item persistence = YES
- attack-hook dependency = YES
- client protocol dependency = NOT_PROVEN
- client resource dependency = POSSIBLE

## Difficulty

**L3 confirmed**

Reason:
- per-item persistent state
- item-use upgrade executor
- attack pipeline integration
- multiple proc/control effects
- destructive failure modes
- target equipment/doll removal behavior

L4 is not justified by current evidence because no custom packet/opcode/client binary dependency has been proven.

## Recommended package split

```
weapon-attr-core/
  per-item attr kind/level state
  inventory persistence
  table/model loader
  attack adapter

weapon-attr-scrolls/
  direct-set scroll
  progressive scroll
  failure policies

weapon-attr-basic-elements/
  earth/water/fire/wind

weapon-attr-advanced-effects/
  light/dark/holy/evil
  bind/drain/range/skill/poly/stun/disarm/remove-doll/remove-armor
```

Basic elemental content must not silently depend on advanced effect handlers.

## Migration safety

Do NOT merge this module with:
- `w_道具附魔系統`
- `server_item_power_update`
- standard 850 craft
- ordinary vanilla elemental enchant damage

Do not port destructive/disarm/poly effects before target combat-state validation.

## Status

```
STATUS=PASS
MODULE=w_屬性強化系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
ITEM_INSTANCE_DEP=attrEnchantKind/attrEnchantLevel
ATTACK_HOOK=L1AttackPc -> L1AttackPower
CLIENT_PROTOCOL_DEP=NOT_PROVEN
CLIENT_RESOURCE_DEP=POSSIBLE
NATIVE_850_EQUIVALENT=NOT_FOUND
```
