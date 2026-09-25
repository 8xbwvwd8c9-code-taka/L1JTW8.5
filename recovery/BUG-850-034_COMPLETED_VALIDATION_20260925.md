# BUG-850-034 completed validation

STATUS=PASS_PROMOTED
LEVEL=L2
ACTIVE_SOURCE=recovered-src-obf/ao/bd.java
ROOT_CAUSE=shop_world and character_shop rows could resolve unknown item ids to null and publish or dereference them
FIX=unknown item templates fail closed before live-map publication; runtime add path rejects unknown item ids
GITHUB_ACTIONS_RUN=36093165484
SOURCE_CONTRACT=PASS
SHOP_WORLD_NULL_PUBLICATION=BLOCKED
CHARACTER_SHOP_NULL_PUBLICATION=BLOCKED
RUNTIME_UNKNOWN_ITEM_REJECTED=PASS
