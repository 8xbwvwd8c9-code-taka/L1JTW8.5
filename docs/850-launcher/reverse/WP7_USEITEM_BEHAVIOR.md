# WP7 — 850 UseItem Behavior Correlation

```text
BRANCH=work/850-launcher-helper
TARGET=850 Lin.bin2
AUTHORITY_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
MEMORY_WRITE=NO
SEND_PACKET=NO
```

## 已證明的 server contract

850 recovered server PacketHandler：

```text
C_ItemUSe opcode = 94 = 0x5E
normal item first field = objectId(D)
logical prefix = 5E + objectId little-endian
```

這只證明 server protocol，不代表 client native UseItem path 已定位。

## Behavior correlation

DeveloperMode 的 **UseItem協定** 頁可開啟 **UseItem 行為驗證**。

流程：

```text
WP6 背包列舉 PASS / InventoryBridge mapped
 -> 從目前背包選一筆 ObjectId
 -> 產生 5-byte logical pattern: 5E + ObjectId(D)
 -> 建立「無操作基線」
 -> 捕捉期間不要使用該道具
 -> 再開始「手動 UseItem 捕捉」
 -> 玩家在遊戲中正常手動使用該道具一次
 -> 唯讀掃描 writable memory
 -> 找 action 中出現、baseline 中沒有的 pattern address
 -> 寫入 itemuse_behavior_evidence.txt
```

## Evidence fields

```text
TIME
PID
PROCESS_START_UTC
CLIENT_SHA256
CLIENT_AUTHORITY
OBJECT_ID
ITEM_ID
ITEM_NAME
PATTERN
BASELINE_PASSES
BASELINE_BYTES
BASELINE_UNIQUE_HITS
ACTION_PASSES
ACTION_BYTES
ACTION_UNIQUE_HITS
NEW_HITS
CANDIDATE_LIMIT
CORRELATED
MEMORY_WRITE=NO
```

## Behavior restart gate

只有 authoritative 850 client session 會被 comparer 接受。

```text
CLIENT_AUTHORITY=1
CLIENT_SHA256=authority
CORRELATED=1
NEW_HITS>0
CANDIDATE_LIMIT=0
BASELINE_PASSES>0
ACTION_PASSES>0
```

跨完整 client restart：

```text
CORRELATED_SESSIONS>=2
DISTINCT_PROCESS_INSTANCES>=2
=> BEHAVIOR_RESTART_GATE=PASS
```

摘要另外記錄：

```text
DISTINCT_ITEM_IDS
DISTINCT_OBJECT_IDS
```

## 重要邊界

```text
BEHAVIOR_RESTART_GATE=PASS
!=
WP7_NATIVE_USEITEM_PASS
```

原因：logical payload pattern 在 writable memory 中出現只能證明「手動使用行為與該 payload pattern 有穩定關聯」，不能單獨證明哪一個 client function 是 authoritative native UseItem entry。

WP7 最終還需要：

```text
Send掃描
 -> Send追蹤
 -> Send比對
 -> behavior correlation
 -> 找到穩定 native call path / function fingerprint
 -> native ItemUse bridge
 -> controlled manual-vs-bridge equivalence test
 -> WP7 PASS
```

## 禁止捷徑

```text
DO_NOT_WRITE_LOGICAL_BYTES_DIRECTLY_TO_SOCKET
DO_NOT_COPY_381_880_FUNCTION_ADDRESS
DO_NOT_MARK_0x5E_IMMEDIATE_MATCH_AS_HANDLER
DO_NOT_MARK_SINGLE_SESSION_CORRELATION_AS_PASS
DO_NOT_ENABLE_AUTO_POTION_BEFORE_NATIVE_BRIDGE_PASS
```
