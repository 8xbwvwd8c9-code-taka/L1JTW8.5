# L1JTW8.5 — 850 Launcher / Helper

此支線只收納 **850 登入器與 850 客戶端內掛** 相關文件/程式。

## Mission — 功能完成優先，逆向只做最小必要 Bridge

這條支線的最終目標不是完整還原 8.50c 客戶端，也不是把 UI / Inventory 架構全部逆向完。

**最終目標是做出可以實際使用的公版登入器 + 輔助功能。**

```text
PRIMARY_PRODUCT=PUBLIC_850_LAUNCHER_WITH_FUNCTIONAL_HELPER
CLIENT_AUTHORITY=850 Lin.bin2 ONLY
REVERSE_ENGINEERING=MEANS_NOT_GOAL
MINIMUM_REQUIRED_BRIDGE_ONLY=YES
```

### 功能性目標

依目前優先順序：

```text
P1=AUTO_POTION
P2=AUTO_BUFF
P3=AUTO_TRANSFORM
P4=DAMAGE_NUMBER_UI
P5=AUTO_DELETE_ITEM
P6=RECYCLE_DISSOLVE_ITEM
```

功能完成定義：

- **自動喝水**：讀取 HP/MP，找到藥水 runtime item identity，經客戶端原生 UseItem 路徑安全使用。
- **自動施放增益**：能判斷技能 / Buff 狀態，經客戶端原生技能路徑自動補 Buff。
- **自動變身**：能判斷目前變身狀態，使用 850 專用 Transform / Skill / Item 路徑自動變身。
- **傷害數字 UI**：取得可信 damage event / value，於 helper overlay 或客戶端 UI 顯示傷害數字。
- **自動刪物品**：列出背包物品並使用正確 runtime identity 執行客戶端原生刪除流程。
- **回收 / 溶解**：在 Inventory identity 與 native action bridge 成立後，再接回收 / 溶解規則與 UI。

### 目前最小必要 Bridge

```text
HP_MP_RUNTIME_MAP=PASS

AUTO_POTION_BLOCKERS:
- INVENTORY_ITEM_OBJECT_ID
- CLIENT_NATIVE_USEITEM_PATH

AUTO_BUFF_BLOCKERS:
- CLIENT_NATIVE_SKILL_USE_PATH
- BUFF_STATE

AUTO_TRANSFORM_BLOCKERS:
- TRANSFORM_STATE
- 850_NATIVE_TRANSFORM_SKILL_OR_ITEM_PATH

DAMAGE_NUMBER_UI_BLOCKERS:
- DAMAGE_EVENT_OR_VALUE_SOURCE
- DISPLAY_BRIDGE

AUTO_DELETE_BLOCKERS:
- INVENTORY_ENUMERATION
- OBJECT_ID
- CLIENT_NATIVE_DELETE_ACTION
```

### Anti-drift rule — 偏離時先重讀首頁

任何工作開始前、代理人接手前、或懷疑方向偏離時，先重新閱讀本 README 的 `Mission`。

```text
DRIFT_GUARD=ENABLED

EVERY_RE_TASK_MUST_ADVANCE_AT_LEAST_ONE_FUNCTIONAL_TARGET=YES
DO_NOT_REVERSE_FOR_COMPLETENESS=YES
DO_NOT_EXPAND_UI_HIERARCHY_WITHOUT_FUNCTIONAL_BLOCKER_LINK=YES
DO_NOT_CHASE_GENERIC_CONTAINER_WITHOUT_ITEM_SKILL_DAMAGE_TRANSFORM_EVIDENCE=YES
PREFER_SHORTEST_NATIVE_BRIDGE_PATH=YES
```

若出現以下任一情況，立即停止目前擴張並重新看首頁：

1. 連續工作只增加 class / offset / vtable 知識，卻沒有縮小任何功能 blocker。
2. 正在研究 UI / generic container，但無法說明它如何直接推進 AutoPotion / AutoBuff / AutoTransform / Damage UI / AutoDelete。
3. 已有負面證據證明某 lane 為 UI / framework / resource，仍持續往下深挖。
4. 逆向範圍從 exact-target / bounded helper 開始變成 broad scan。
5. 有更短的反向路徑可用，例如從 `UseItem / SkillUse / damage consumer` 回追 runtime identity，卻仍持續掃 UI owner。

偏離後的固定恢復流程：

```text
STOP
-> READ README MISSION
-> STATE FUNCTIONAL_GOAL
-> STATE CURRENT_BLOCKER
-> STATE WHY_NEXT_STEP_REDUCES_BLOCKER
-> CONTINUE ONLY IF DIRECTLY RELEVANT
```

**判定原則：**

> 能讓登入器功能更快可用的路徑優先；完整逆向不是交付物。

---

```text
BRANCH=work/850-inventory-helper
TARGET=850
AUTHORITY=850
DONORS=381,880
SERVER_DOCS=EXCLUDED
```

- [850 登入器 / 內掛開發報告](docs/850-launcher/REPORT.md)
- [公版登入器 / Encoder / 變身編碼 Donor 索引](docs/850-launcher/PUBLIC_LAUNCHER_DONOR_INDEX.md)
- [WP7 UseItem 行為關聯 / Native Bridge 驗證邊界](docs/850-launcher/reverse/WP7_USEITEM_BEHAVIOR.md)

## 快速研究入口

之後查登入器、Encoder、變身編碼或版本配套，先從上面的 Donor 索引進去，不再翻舊對話。

```text
LOGIN_ARCHITECTURE_DONOR = 8.55 LoginWithoutUI
SHAREDMEMORY_DONOR       = lineage3.81Launcher
ENCODER_DONOR            = 8.8/880 public packages
CLIENT_AUTHORITY          = 850 Lin.bin2 ONLY
ADDRESS_AUTHORITY         = 850 Lin.bin2 ONLY
RESOURCE_AUTHORITY        = 850 client ONLY
```

主要外部來源已集中保存：

- https://github.com/eric761231/lineage3.81Launcher.git
- https://github.com/8xbwvwd8c9-code-taka/LoginWithoutUI
- https://lineage45.com/thread-339928-1-1.html
- https://lineage45.com/thread-202188-1-1.html
- https://lineage45.com/archiver/tid-209387.html
- https://lineage45.com/thread-156799-1-1.html
- https://lineage45.com/thread-169912-1-1.html
- https://lineage45.com/thread-228-1-1.html
- https://lineage45.com/archiver/fid-40.html?page=3
- https://lineages.tw/thread-644-1-1.html
- https://www.lineage.cn/forum.php?mod=misc&action=attachpay&aid=3673&tid=5053
- https://morosedog.gitlab.io/private-lineage-20210712-private-lineage-0/
- https://github.com/MoroseDog/private-lineage-tutorial

### Donor 使用邊界

```text
DO_NOT_COPY_381_880_ABSOLUTE_ADDRESS
DO_NOT_ASSUME_ENCODER_FORMAT_COMPATIBLE
DO_NOT_ASSUME_LOGIN_BIN_COMPATIBLE
DO_NOT_ASSUME_POLYMORPH_LIST_AUTHORITY
DO_NOT_REPLACE_850_TILE_TEXT_BLINDLY
DO_NOT_MARK_DONOR_BEHAVIOR_AS_850_PROOF
```

變身 / Encoder 另走 WP10：先證明 850 自己的資源、編碼與登入器載入關係，再建立 850 專用 Transform pipeline。

## Current runtime gate

```text
WP3 Player      = runtime evidence required
WP4 HP/MP       = PASS (formal runtime map proven)
WP5 Inventory   = authoritative map/session validation required
WP6 Inventory   = restart gate required
WP7 UseItem     = behavior correlation + native send path required
WP8 AutoPotion  = blocked until WP7 native bridge PASS
WP9 Skill/Buff  = runtime/native evidence required
WP10 Transform  = separate Encoder/client-resource track
```

WP7 特別注意：

```text
BEHAVIOR_RESTART_GATE=PASS
!=
WP7_NATIVE_USEITEM_PASS
```

## Scope

IN:

- LoginWithoutUI
- Lin.bin2 啟動/登入映射
- IP / Port / ServerName 外部設定
- 850 Player / HP / MP
- 850 Inventory / Item bridge
- 850 UseItem / Skill / Buff bridge
- LinHelperZ donor study
- helper UI
- launcher/helper tests
- WP10 850 Transform / Encoder compatibility research

OUT:

- 服務端 Java core
- 核心修復文件
- DB migration
- server SQL / config 文件
- 服務端 handoff / repair ledger
