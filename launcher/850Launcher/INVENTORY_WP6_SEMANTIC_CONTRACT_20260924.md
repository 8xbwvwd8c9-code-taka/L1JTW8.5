# 850 Inventory WP6 Semantic Contract — 2026-09-24

```text
STATUS=SEMANTIC_CONTRACT_ONLY
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
CLIENT_LAYOUT_OFFSETS=UNMAPPED
MEMORY_WRITE=NO
```

## Authority

This contract is restricted to the 8.50 target and the current launcher/recovery evidence.

```text
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
```

The recovered class mapping identifies the relevant 850 classes:

```text
be.c  -> l1j/server/server/serverpackets/S_AddItem.java
be.bh -> l1j/server/server/serverpackets/S_InvList.java
be.ag -> l1j/server/server/serverpackets/S_DeleteInventoryItem.java
be.as -> l1j/server/server/serverpackets/S_EquipmentSlot.java
be.bg -> l1j/server/server/serverpackets/S_ItemName.java
be.bi -> l1j/server/server/serverpackets/S_ItemStatus.java
be.bu -> l1j/server/server/serverpackets/S_RemoveItem.java
aj.az -> l1j/server/server/clientpackets/C_ItemUSe.java
ap.q  -> l1j/server/server/templates/L1ItemInstance.java
```

The mapping proves class identity only. It does not by itself prove packet field order or client memory offsets.

## Proven use-item action identity

Existing 850 server recovery in the launcher branch proves:

```text
PacketHandler opcode 94 / 0x5E -> C_ItemUSe
C_ItemUSe first field -> objectId via readD / LE32
normal healing potion use_type=normal -> no extra C_ItemUSe payload fields
```

Therefore the decrypted logical normal-potion payload is:

```text
5E <objectId LE32>
```

This establishes a critical semantic distinction:

```text
ObjectId = runtime item-instance identity used by C_ItemUSe
ItemId   = item-template/type identity, not sufficient to execute C_ItemUSe
```

The launcher must never substitute ItemId for ObjectId.

## Auto-potion semantic chain

The existing 850 launcher design already separates the roles:

```text
HP policy
 -> inventory priority ItemId
 -> resolve matching inventory instance
 -> ObjectId
 -> cooldown
 -> IItemUseBridge
```

Meaning:

```text
ItemId answers: "which kind of potion/item should be selected?"
ObjectId answers: "which concrete inventory instance should the client use?"
```

The native `IItemUseBridge` remains UNMAPPED. No direct packet send is authorized by this contract; session framing/encryption remains client-owned.

## Minimum WP6 record semantics

For an inventory record to support the already-defined launcher behaviors, the minimum semantic fields are:

```text
ObjectId : REQUIRED instance identity
ItemId   : REQUIRED template/catalog identity
Count    : REQUIRED stack quantity
```

Additional staged fields remain useful but are not yet part of the minimum action-identity proof:

```text
Enchant  : CANDIDATE / UNMAPPED
Equipped : CANDIDATE / UNMAPPED
```

No client offset is assigned to any of these fields here.

## Field invariants

### ObjectId

Required properties once a live record layout is proposed:

```text
nonzero for every occupied record
unique across simultaneously occupied inventory records
stable for the same concrete item instance during observations where that instance is unchanged
is the value consumed by the logical C_ItemUSe action contract
```

A value matching ItemId/catalog data is not enough to identify ObjectId.

### ItemId

Required properties:

```text
maps to the authoritative 850 item catalog
may repeat across distinct records/instances
is used for user-facing names and item-selection policy
must not be passed as C_ItemUSe objectId
```

The current offline `item-names.csv` contains the 850 item catalog used by the launcher, but catalog membership alone is weak evidence because unrelated/template-like memory can also contain legal ItemIds.

### Count

Required properties:

```text
positive for occupied stack records unless the 850 protocol proves a special zero-state
controlled stack change affects the proposed Count field consistently
sum across matching ItemId records equals current expected total
summation is overflow-safe
```

A field that merely contains the expected number once is not sufficient.

### Enchant / Equipped

These remain secondary semantic targets:

```text
exact memory offsets=UNMAPPED
promotion requires controlled action correlation
single-session coincidence=REJECT
```

## WP6 validation gate already defined by launcher

The 850 launcher's hidden inventory-validation workflow requires:

```text
InventoryBridge mapped
record list readable
ObjectId nonzero
ObjectId unique per record
expected ItemId totals equal observed totals
Count summation has no overflow
```

Restart comparison requires:

```text
>=3 checked validation sessions
all sessions PASS
>=2 distinct Lin.bin2 process instances
same expectation set across compared sessions
```

Therefore a structurally plausible vector or a high catalog-match percentage cannot independently satisfy WP6.

## Relation to current backing-model work

Current state:

```text
UI graph = proven
stable ROOT/GRID owner acquisition = not yet proven
restart-stable backing collection = not yet proven
ObjectId offset = unmapped
ItemId offset = unmapped
Count offset = unmapped
Enchant offset = unmapped
Equipped offset = unmapped
```

The prior `INVWIN+0x220` 28x64 candidate is explicitly not promoted because it failed the later process/restart gate even though one run showed a strong ItemId-like pattern.

## Promotion order

Do not reverse this order:

```text
1. prove stable ROOT/GRID/INVWIN owner acquisition
2. identify one restart-stable bounded backing collection
3. prove record stride/base from collection arithmetic
4. correlate ItemId using catalog + controlled inventory truth
5. correlate Count using controlled stack changes
6. identify ObjectId using uniqueness/stability + C_ItemUSe semantic requirement
7. validate >=3 sessions / >=2 process instances
8. only then FORMAL_WP6 may pass
```

Enchant/Equipped may be proven after the minimum ObjectId/ItemId/Count record contract, unless a launcher feature requires them earlier.

## Native action boundary

Even after ObjectId is mapped:

```text
DIRECT_NETWORK_SEND=NO
LOGICAL_PAYLOAD_EVIDENCE_ONLY=5E <objectId LE32>
SESSION_FRAMING_ENCRYPTION=CLIENT_OWNED
IItemUseBridge=NATIVE_CLIENT_PATH_REQUIRED
```

This keeps packet semantics separate from transport/native-dispatch proof.

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
MEMORY_WRITE=NO
```
