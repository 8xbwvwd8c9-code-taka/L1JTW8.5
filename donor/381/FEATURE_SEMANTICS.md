# 381 donor：功能語意證據

本文件只整理既有文字設定。`CONFIG_DECLARED` 表示設定檔明確宣告語意，並不表示 donor 執行成功，更不表示 850 已具備對應 native 能力。所有來源唯讀；未讀取二進位、未分析 RX/TX、未分析施法管理器。沒有移植 donor 位址、opcode、state ID 或 ABI。

## 可核對來源

- P = `I:\L381\Atu-381客戶端\atum_profiles\冏冏冏.ini`，逐行讀取，共 116 行。
- M = `I:\L381\Atu-381客戶端\atum_menu.txt`，逐行讀取，共 336 行。
- P:1–3 自稱 L1J_381 Atum per-character profile，版本欄位是 profile 格式，不是 850 合約版本。

## 功能矩陣輸入

| Feature key | 已宣告語意 | 證據 | 等級與邊界 |
|---|---|---|---|
| potion_threshold | 多列補水或瞬移；HP 低於門檻觸發；絕對值或百分比模式；預設無列由玩家設定 | P:4–7；M:1–31 | CONFIG_DECLARED；優先序、冷卻、邊界等號、同 tick 多列競爭未確認 |
| hpmp_conversion | 菜單列出心靈轉換與魂體轉換 | M:33–34 | CATALOG_ONLY；觸發條件、消耗限制未確認 |
| buff_maintain | master 開關與名稱導向清單，含 donor 狀態前綴／命令後綴；預設關閉且空清單 | P:8–10；M:36–218 | CONFIG_DECLARED；狀態索引與後綴含義未移植，執行語意未確認 |
| chat_rotation | 多行輪播；文字、間隔、頻道持久化；啟用狀態不持久，重登關閉，由玩家重開 | P:11–14 | CONFIG_DECLARED；排程時序與發送介面未確認 |
| timed_command | 多列定時重複指令；間隔以秒描述；全持久化；預設關閉且空清單 | P:15–16 | CONFIG_DECLARED；補發、漂移、重登基準未確認 |
| hotkey_groups | 群組互斥啟用；每組 F1–F4 指令巨集；全持久化，預設鍵空且關閉 | P:17–36 | CONFIG_DECLARED；命令展開及輸入攔截方式未確認 |
| item_delete | 名稱導向清單，每項 del 標籤，共用頁面開關；全持久化 | P:37–85 | CONFIG_DECLARED；範例啟用且有清單不代表可複製使用者刪除偏好 |
| item_dissolve | 同一清單支援 dis 標籤 | P:37 | CONFIG_DECLARED；此 profile 沒有 dis 範例；溶解媒介與數量策略未確認 |
| polymorph_maintain | 名稱導向；可指定變身道具，或卷軸與形態；預設關閉且空欄位 | P:86–91；M:220–323、332–333 | CONFIG_DECLARED；形態匹配與補用時機未確認 |
| doll_maintain | 名稱導向娃娃道具，自動維持；預設關閉且空欄位 | P:86–93；M:335–336 | CONFIG_DECLARED；活躍娃娃辨識與重召時序未確認 |
| antidote | 分開設定綠毒、卡延遲及麻痺解法；預設關閉且空欄位 | P:94–98；M:325–330 | CONFIG_DECLARED；donor 記憶體欄位刻意省略，850 狀態來源仍須確認 |
| stone_refine | 黑妖黑魔石週期升級；預設關閉且空欄位 | P:99–101 | CONFIG_DECLARED；命令後綴、週期和結果驗證未確認 |
| environment_toggles | 每角色記憶全白天、海底抽水、降低 CPU、自動吃肉開關；預設關閉 | P:102–106 | CONFIG_DECLARED；效果的 native 方法未確認 |
| display_and_repair_flags | 有修理、浮字、掉落提示、傷害字、時鐘、角色名、怪物名、正義值色彩、盟徽、經驗開關 | P:107–116 | KEY_PRESENT_ONLY；欄位存在，本文件不補造行為 |
| pet_hp_maintain | 資料不足，無法確認 | 上述 P/M 沒有直接宣告 | UNKNOWN；不能由一般補水功能推導 |
| summon_maintain | 資料不足，無法確認 | 上述 P/M 沒有直接宣告 | UNKNOWN |
| elf_spirit_maintain | 資料不足，無法確認 | 上述 P/M 沒有直接宣告 | UNKNOWN |
| auto_hunt | 資料不足，無法確認 | 上述 P/M 沒有直接宣告 | UNKNOWN；不建立狩獵、移動、選敵或攻擊政策 |

## 重建界面可採用的語意

可保留「功能名稱、使用者明確配置、持久化／重登規則、缺能力停用」等平台中立形狀。所有動作交給獨立 capability／action interface；850 能力只能來自已確認合約。未確認的庫存、buff、召喚物或動作能力須保持不可用。設定宣告不能替代 runtime 觀測或已確認 native 合約。

## 未確認事項

本次沒有 donor 原始控制器實作證據；因此無法確認 tick、冷卻、動作順序、執行緒、目標挑選、錯誤恢復、命令後綴文法及所有 native 呼叫方法。`atum_menu.txt` 是選項字典，不是執行器。P 中的 donor 記憶體描述沒有抄入 scaffold。外部公開資料不是這些私有 donor 檔案的權威來源。
