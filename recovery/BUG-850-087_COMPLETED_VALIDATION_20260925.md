# BUG-850-087 Completed Validation

BUG=BUG-850-087
OPERATION=CLAN_MERGE

INVARIANT
- Durable merge transaction commits before RAM/live clan publication.
- Source/target clan rows and character/clan_members membership are locked and validated.
- affectedRows/CAS mismatches fail closed and roll back.
- Unknown commit outcome is resolved by authoritative reread; no blind retry.

ACTIVE_SOURCE
- recovery/normalized-src-vf/l1r/aj/C_Attr.java
- recovery/normalized-src-vf/l1r/ao/ClanMembersTable.java
- recovered-src-obf/aj/f.java
- recovered-src-obf/ao/p.java

DATE=2026-09-25
WORKFLOW_RUN=36118199234
PROMOTION_COMMIT=4d251d0697c7d5d27dc13ce796c13b011a7c5074
STATUS=PASS_PROMOTED

VALIDATION
- Apply reviewed invariant scope: PASS
- Java 8 no-new-regression: PASS
- Failure model: PASS
- Verify exact promotion scope: PASS
- Commit promotion: PASS

DB_ENGINE_GATE
- characters=InnoDB
- clan_members=InnoDB
- clan_data=InnoDB
- clan_warehouse_history=InnoDB
