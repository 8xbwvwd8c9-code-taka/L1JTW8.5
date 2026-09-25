# BUG-850-010 completed validation

STATUS=PASS_PROMOTED
LEVEL=L2
ACTIVE_SOURCE=recovered-src-obf/ao/s.java
ROOT_CAUSE=malformed craft row runtime parse errors escaped the row and aborted later valid rows; partial ArrayIndex catches could still publish incomplete craft data
FIX=row-scoped RuntimeException isolation; malformed row is skipped before publication; later rows continue
GITHUB_ACTIONS_RUN=36093165484
SOURCE_CONTRACT=PASS
PARTIAL_ROW_PUBLICATION=BLOCKED
LATER_VALID_ROW_CONTINUES=PASS
