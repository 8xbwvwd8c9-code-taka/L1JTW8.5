# 381 → 850 DB migration module index

| MODULE | STATUS | LEVEL | AUDIT | COMMIT | DECISION |
|---|---|---|---|---|---|
| `w_變身賦予狀態_FAMILY` | `BLOCKED` | `BASE=L1; ITEM=L4; SHARED=L3/L4` | [`TRANSFORM_STATUS_FAMILY_AUDIT.md`](modules/TRANSFORM_STATUS_FAMILY_AUDIT.md) | `787bd7d9ce57777e0bf08321ccd421238bb6205f` | Base is empty and skipped; item sibling is separately owned; map semantic modifiers into 850 lifecycle only after persistence, clamp, replacement and client identity proof. |
| `w_道具輔助系統` | `BLOCKED` | `L4` | [`ITEM_ASSIST_SYSTEM_AUDIT.md`](modules/ITEM_ASSIST_SYSTEM_AUDIT.md) | `f1b74bd555c9840d23086ae11b5988a0aef15a75` | Split active effects into 850-native semantic targets; no production port until schema, identity mapping, and permanent-state persistence are proven. |
