# DONOR_FEATURE_MATRIX_AND_RECONSTRUCTION_SCAFFOLD 整合邊界

此 WP 僅產出 donor 語意證據、功能矩陣、非侵入式重建骨架及測試。
850 正式契約來源：`I:/850C-Client-RE/maps/`；唯讀。載入僅保留已確認事實的語意與來源，移除位址、ABI、opcode、原生欄位與 notes。CONFIRMED/STABLE 是來源狀態，並不表示骨架已具備原生呼叫能力。

## 寫入分區

| 執行者 | 唯一寫入範圍 |
|---|---|
| SUB1 | donor/381/** |
| SUB2 | donor/880/** |
| SUB3 | donor/matrix/** |
| SUB4 | reconstruction/** |
| SUB5 | tests/** |
| 主代理 | donor/INTEGRATION.md |

並行槽位為三個子代理；SUB3、SUB5 在先前子代理完成後啟動。所有代理共用工作區，禁止修改其他分區及唯讀輸入。提交由主代理統一執行。

## 使用與驗證

在此倉庫根目錄執行 `python -B -m unittest discover -s tests -v`。
骨架透過呼叫者提供的 Adapter 接收語意快照與明確意圖；預設策略拒絕執行。測試 Adapter 僅記錄呼叫，沒有程序記憶體操作或網路傳輸。
契約載入不探索二進位、不升級 CANDIDATE、不補齊 UNKNOWN；HP/MP、BotUI callback 與 AutoHunt 決策規則不從 donor 移植。狀態機是重建協調器的生命週期，並非宣稱已還原 850 原生 AutoHunt 狀態機。

本 WP 不推送、不合併。既有工作區變更不納入提交。

## 完成驗證

381 報告整理 19 項設定／目錄語意；880 報告整理 10 項服務端語意；矩陣提供 12 組比較列。五個子任務依表列分區寫入，SUB1 完成後以同一代理執行 SUB5，後續寫入僅限 tests。
正式唯讀載入得到 8 項已確認語意事實及 5 個輸入 CSV 雜湊。測試包含原始輸入 bytes 前後相同、預設拒絕、生命週期、故障與 schema/SHA/候選隔離，以及矩陣契約核對。合成 CSV 以記憶體 fixture 提供。
驗證命令：`python -B -m unittest discover -s tests -v`。靜態檢查確認 reconstruction 沒有 native 十六進位常數或記憶體／網路呼叫套件。提交僅包含表列 WP 檔案。
