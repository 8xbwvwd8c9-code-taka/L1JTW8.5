# 381 / 880 / 850 功能矩陣

381 是文字設定宣告，880 是服務端 Java 語意，850 是既有 maps 的契約狀態；三者的證據層級分開列出。METADATA_ONLY 只代表可閱讀已確認語意，所有 native 呼叫均未綁定。UNAVAILABLE 表示候選、未知、缺乏完整能力或本 scaffold 尚未暴露該能力。

來源：[381 證據](../381/FEATURE_SEMANTICS.md)、[880 證據](../880/FEATURE_SEMANTICS.md)、唯讀 `I:/850C-Client-RE/maps/`。JSON 保留逐個 canonical key 與 evidence_ref，可由 validator 比對；不含位址、opcode 或 ABI。

| 功能 | 381 宣告 | 880 Java 語意 | 850 狀態 | 重建處置 |
|---|---|---|---|---|
| controller_lifecycle | 既有設定來源沒有直接宣告此執行語意。 | startAI 提交 Runnable；死亡、離線、生命耗盡或 inactive 終止；正常路徑清目標，例外清理保證未知。 | bot_controller_state_singleton_getter=CONFIRMED; bot_controller_state_singleton=CONFIRMED; bot_stop_routine_shim=CANDIDATE | 純 Python 協調器生命週期；native stop 是 CANDIDATE，維持不可用。 |
| target_lookup_id | 既有設定來源沒有直接宣告此執行語意。 | 每輪先 checkTarget；無目標才 searchTarget；noTarget/onTarget 分流，內部政策未分析。 | bot_controller_get_target_id=CONFIRMED; ENTITY_LOOKUP=CONFIRMED; ENTITY_ID_FIELD=CONFIRMED | 接受外部明確意圖及目標 snapshot；不建立搜尋或攻擊策略。 |
| action_queue | 既有設定來源沒有直接宣告此執行語意。 | 此次 Java 證據未確認此功能。 | ENTITY_ACTION_QUEUE=CONFIRMED | 消費已確認 queue 語意 metadata；目前沒有 queue 實作或 native queue binding。 |
| item_action | 補水、刪除、溶解、變身、娃娃皆有名稱導向設定；未確認執行器。 | 此次 Java 證據未確認此功能。 | item_action_execute=CONFIRMED; item_resolver_singleton_getter=CONFIRMED | 850 邊界已確認，但目前不在 scaffold allowlist；沒有道具 native binding。 |
| ui_settings | 每角色設定與群組巨集持久化；chat 啟用狀態重登關閉。 | Hang_fu 顯示技能選擇、間隔、魔力量、範圍和開關；縮寫完整語意未知。 | BotWindow=CANDIDATE; BotOpenUI=CANDIDATE | UI native contracts 全部 CANDIDATE，不提供 callback binding；僅比較配置形狀。 |
| hpmp_observation | HP 低門檻補水支援絕對值或百分比；門檻等號與排程未確認。 | 此次 Java 證據未確認此功能。 | player_hp_current=CANDIDATE; player_mp_current=CANDIDATE; hpevent_ctor=CONFIRMED; mpevent_ctor=CONFIRMED | event 邊界確認不等於可讀 HP/MP 合約；direct globals 是 CANDIDATE，保持 unavailable。 |
| timing_and_pause | 定時命令與聊天輪播有間隔設定；排程漂移未確認。 | 睡眠或麻痺暫停 AIProcess；主迴圈依速度資料等待。 | UNKNOWN / 無所需合約 | 控制器支援顯式 pause/resume；不移植 donor 時間係數或狀態欄位。 |
| auto_hunt_policy | 既有設定來源沒有直接宣告此執行語意。 | 存在地圖權限與離起點超範圍處置；未確認選敵攻擊內部算法。 | UNKNOWN / 無所需合約 | DenyPolicy 預設拒絕；不採用地圖、半徑、傳送、攻擊或選怪策略。 |
| buff_and_conversion | buff master 與名稱清單；HPMP 轉換只有選單目錄。 | 此次 Java 證據未確認此功能。 | UNKNOWN / 無所需合約 | buff 維持與轉換功能 unavailable；不分析施法管理器。 |
| maintenance_actions | 解毒、煉石、變身、娃娃有配置；觸發與完成驗證未確認。 | 此次 Java 證據未確認此功能。 | UNKNOWN / 無所需合約 | 保留比較證據；缺乏 confirmed 完整能力，維持 unavailable。 |
| environment_and_display | 全白天、抽水、CPU、吃肉宣告；修理、名稱與浮字等只確認鍵存在。 | 此次 Java 證據未確認此功能。 | UNKNOWN / 無所需合約 | 功能鍵不轉成 850 原生能力。 |
| pet_summon_spirit | 寵物 HP、召喚物、精靈維持沒有直接宣告。 | 此次 Java 證據未確認此功能。 | UNKNOWN / 無所需合約 | 資料不足，無法確認；所有動作 unavailable。 |

重建介面採現有 `BotController`、`Adapter.observe/perform`、`Snapshot`、`Intent`、`Policy` 與 `DenyPolicy`。controller 狀態是平台中立協調語意，不是 850 原生狀態機還原。候選值不升級；donor 預設設定不複製為使用者偏好。
