# 880 donor 功能語意（唯讀來源）

本文件只整理現存 Java 原始碼中的服務端語意，作為重建介面的參考。它不證明 880 原生客戶端具備相同功能，也不提供 850 原生實作授權或契約。所有來源維持唯讀；本次沒有執行二進位、封包、RX/TX 或技能原生函式逆向。

## 證據來源

- `I:/L880C/880c服務端/src/com/lineage/server/model/Instance/PcAI.java`（下稱 PcAI）
- `I:/L880C/880c服務端/src/com/lineage/data/item_etcitem/teleport/Hang_fu.java`（下稱 Hang_fu）

以上是直接讀取的來源檔；行號以此次讀取版本為準。`I:/L880C/880_AUTOHUNT_RUNTIME_CHAIN.txt` 是既有搜尋摘要，沒有作為原生地址、封包或 ABI 證據採納。

## 功能矩陣用語意

| Feature ID | 確認的 880 donor 語意 | 精確來源 | 重建邊界 |
| --- | --- | --- | --- |
| lifecycle_start | PcAI 實作 Runnable；startAI 將本物件提交給 NpcAiThreadPool。 | PcAI:16–25 | 可參考控制器與執行器分離；不宣稱 850 有此 native entry。 |
| lifecycle_stop | AIProcess 對死亡、離線、生命值耗盡與 inactive 回傳終止。 | PcAI:77–92 | 可表達明確停止原因；850 各狀態來源須由已確認契約提供。 |
| lifecycle_cleanup | 正常迴圈結束後，程式先等待死亡狀態結束，再清除所有目標、清掉 aiRunning 和 actived。 | PcAI:53–65 | 僅 donor 的正常路徑；例外路徑沒有同等 cleanup 保證，不能標示為可靠 finally。 |
| incapacitation_pause | 睡眠或麻痺時，run 迴圈等待後繼續，沒有呼叫 AIProcess。 | PcAI:33–40 | 可描述暫停狀態；不移植 donor 等待時間或狀態取值方法。 |
| target_validation | 每輪先 checkTarget，再以目前目標是否存在決定是否 searchTarget。 | PcAI:133–145 | 可拆成 target snapshot / validation / selection 介面；選擇政策仍待明確輸入。 |
| target_dispatch | 沒有目標時呼叫 noTarget；有目標時呼叫 onTarget，並清除 pathfinding 標記。 | PcAI:147–158 | 只確認 dispatch 邊界，未在本 WP 分析被呼叫方法中的行為。 |
| movement_timing | 主迴圈移動等待從 SprTable 的圖像與武器相關速度取得；intervalR 依技能與加速狀態調整。 | PcAI:44–46、176–228 | 計時應抽象為注入介面；不拷貝 donor 速度係數作為 850 政策。 |
| map_permission | donor 原始碼具有區域限制，以及非 GM 在不允許 AutoBot 的地圖上終止的分支。 | PcAI:93–110 | 記錄 donor 功能存在；地圖、GM 例外與處置均非 850 AutoHunt 政策。 |
| origin_radius | 起始座標有效且離起點超出配置距離時，donor 執行傳送並清除目前目標。 | PcAI:116–124 | 只作功能比較；不移植座標、半徑或 teleport 行為。 |
| configuration_display | Hang_fu 讀取兩項技能選擇、技能間隔、魔力量、limao、範圍與兩個開關，組成顯示資料；技能 template 存在才使用其名稱。 | Hang_fu:43–78 | 可參考配置與狀態顯示分離；欄位命名不是 850 native layout。 |

## 明確未知與限制

- 880 原生客戶端 BotController、戰鬥 worker、callback、ABI、地址與狀態機：【資料不足，無法確認】。本次沒有讀取或執行相關逆向腳本來擴充證據。
- `getygjnzc`、`getgjjnzc`、`getgjml`、`limao` 與兩個 `sy` 開關的完整功能意義：【資料不足，無法確認】。本文件只確認它們參與顯示，沒有由縮寫猜測完整中文政策。
- noTarget、onTarget、checkTarget、searchTarget 內部的攻擊、移動、技能、道具或拾取演算法沒有在本 WP 證實。
- HP/MP 補給、inventory、item use、buff tracking 是否由這套 880 donor AI 提供：【資料不足，無法確認】。
- 未分析 CSpellManager、AutoAttackCastSpell 或任何 RX/TX/protocol 路徑；不含 donor 地址或 opcode 常數。
- 此 donor 原始碼不是已确认 850 maps/contracts。重建只能消費獨立確認的 850 契約；UNKNOWN/CANDIDATE 原生能力維持不可執行。

## 給 scaffold 的可採納結構

只採納抽象分工：控制器生命週期、唯讀狀態 snapshot、目標提供者、時間來源、外部政策與 capability gate。donor 的地圖限制、傳送、選怪、攻擊、技能與補給策略不構成 850 預設行為。沒有對應 850 已確認契約的能力應回報 unavailable，並保持無原生副作用。
