# Normalized Builder Collision Transform

Recovery-only rename for the one builder per PBMessageALL* file whose source-level simple name collides with its enclosing message type.

- Files repaired: **9**
- Donor bytecode changed: **NO**
- Gameplay logic changed: **NO**
- Other non-colliding L1R_a builders renamed: **NO**
- Final donor comparison must normalize L1R_Builder back to the donor nested identity.

## Repairs

- `PBMessageALL$L1R_a$L1R_a` -> `PBMessageALL$L1R_a$L1R_Builder` (refs=48, ctors=3)
- `PBMessageALL2$L1R_a$L1R_a` -> `PBMessageALL2$L1R_a$L1R_Builder` (refs=28, ctors=3)
- `PBMessageALL3$L1R_a$L1R_a` -> `PBMessageALL3$L1R_a$L1R_Builder` (refs=50, ctors=3)
- `PBMessageALL4$L1R_a$L1R_a` -> `PBMessageALL4$L1R_a$L1R_Builder` (refs=48, ctors=3)
- `PBMessageALL5$L1R_a$L1R_a` -> `PBMessageALL5$L1R_a$L1R_Builder` (refs=50, ctors=3)
- `PBMessageALL6$L1R_a$L1R_a` -> `PBMessageALL6$L1R_a$L1R_Builder` (refs=48, ctors=3)
- `PBMessageALL7$L1R_a$L1R_a` -> `PBMessageALL7$L1R_a$L1R_Builder` (refs=50, ctors=3)
- `PBMessageALL8$L1R_a$L1R_a` -> `PBMessageALL8$L1R_a$L1R_Builder` (refs=52, ctors=3)
- `PBMessageALL9$L1R_a$L1R_a` -> `PBMessageALL9$L1R_a$L1R_Builder` (refs=38, ctors=3)
