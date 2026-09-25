# BUG-850-032 completed validation

STATUS=PASS_PROMOTED
LEVEL=L2
ACTIVE_SOURCES=recovered-src-obf/aj/cd.java,recovered-src-obf/ao/bd.java,recovered-src-obf/ao/l.java,recovered-src-obf/au/g.java
ROOT_CAUSE=inventory persistence/RAM publication and character_shop pending deletion used separate authority boundaries, allowing duplicate-claim divergence on partial failure
FIX=single InnoDB transaction locks exact pending row, persists inventory insert/stack CAS, exact-deletes pending row, commits before RAM publication; unknown commit outcome performs authoritative reread without blind retry
GITHUB_ACTIONS_RUN=36095041746
JAVA8_TRANSACTION_MODEL=PASS
SOURCE_CONTRACT=PASS
FOR_UPDATE_GATE=PASS
AFFECTED_ROW_GATE=PASS
INNODB_GATE=PASS
COMMIT_BEFORE_RAM=PASS
UNKNOWN_COMMIT_REREAD=PASS
UNKNOWN_COMMIT_AUTOCOMMIT_RESTORE=BLOCKED
STACK_OVERFLOW_GATE=PASS
PROMOTION_INVARIANT_SCOPE_EQUALS_REVIEWED_WORK=PASS
