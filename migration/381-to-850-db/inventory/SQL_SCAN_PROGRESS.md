# 381 → 850 SQL 全量掃描進度

- 381 SQL：320
- 非空 SQL：210
- 空 SQL：110
- 850 CREATE TABLE：99
- 850 同名 table：46
- SQL_SCANNED：320/320
- CORE_TRACED：0/320
- 850_COMPARED_SEMANTIC：0/320
- CLIENT_CHECKED：0/320
- AUDIT_COMPLETE：0/320

## 判定規則

同名 table 僅代表 DB 名稱重複，不代表語意完全相同。語意重複、部分重疊及 native replacement 必須完成 381 runtime 與 850 core 對照後才能判定。空 SQL 保持 HOLD，不得因零資料直接 SKIP。
