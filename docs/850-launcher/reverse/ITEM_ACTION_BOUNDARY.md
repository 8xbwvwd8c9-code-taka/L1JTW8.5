# Item Action Boundary: Delete / Recycle / Dissolution

## Purpose

The 850 helper may select items and trigger supported actions, but item removal and rewards must remain server-authoritative.

## UI / helper responsibility

- Read the real 850 inventory list after WP5/WP6.
- Present backpack items for selection.
- Store local automation rules:
  - item identity;
  - action mode;
  - quantity / trigger options.
- Trigger the normal 850 game request/command path only after that path is proven.
- Never calculate or grant currency/material rewards locally.

## Action semantics

### Delete

Pure destruction. Expected result:

```text
item removed
reward = none
```

If an action grants currency, it is not treated as plain Delete in the 850 helper model.

### Recycle

Server-authoritative exchange:

```text
selected item
 -> server validation
 -> remove item
 -> grant configured currency/reward
```

Recommended server data is a dedicated recycle table rather than encoding value rules into the launcher.

### Dissolution / Smelt

The L880C donor proves this is server core + DB driven.

Authoritative donor paths:

```text
L880C/880C服務端/src/com/lineage/data/item_etcitem/extra/Dissolution.java
L880C/880C服務端/src/com/lineage/server/datatables/ResolventTable.java
L880C/880C服務端/src/com/lineage/server/datatables/ResolventEXTable.java
L880C/880C服務端/資料庫&修正歷程_工具/tables/resolvent.sql
L880C/880C服務端/資料庫&修正歷程_工具/tables/resolvent_ex.sql
```

Observed donor behavior:

- `Dissolution.execute()` receives a selected inventory object id and resolves the actual server inventory item.
- Enchanted/equipped weapon or armor is rejected.
- `resolvent` maps `item_id -> crystal_count`.
- `resolvent_ex` maps one source `item_id` to one or more reward `crystal_id` values and min/max quantities.
- `ResolventEXTable` creates/stores reward item instances on the server.
- Source item removal is server-side.
- The fetched donor `Dissolution.java` reads standard `crystal_count` but does not visibly grant that count in the same class; do not assume the standard path is complete until its full call path is verified.

## 880 helper evidence

The previously inspected 880 `LinHelperZ.ini` contains an `[AllSmeltItem]` section. This proves the helper has a configurable/selectable smelt item list, but it does not make the helper authoritative for rewards.

## 850 design rule

```text
Helper:
inventory read + item selection + trigger

Server core:
validation + atomic remove + reward

DB:
item whitelist + reward mapping + amount/range
```

For the 850 port, do not copy 880 addresses. Reuse only the server-side behavior/data model after confirming compatibility with the 850 server core.

## Proposed 850 modes

```text
DELETE   = destroy only
RECYCLE  = exchange for currency/reward
SMELT    = exchange for configured materials/items
```

These modes must be separate in UI and config to prevent a destructive delete rule from being mistaken for a rewarded recycle/smelt rule.
