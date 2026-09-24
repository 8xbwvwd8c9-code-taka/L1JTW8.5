# 381來源證據 — skill-grade-system

## 來源索引

本項目 381 方面無直接 DB 供體（grade 欄位不存在於 381）。

以下為已查明的關鍵事實：

### skills_202609221205.sql 摘要

| 項目 | 值 |
|------|-----|
| 絕對路徑 | `I:\L381\Atu-381伺服器端主\DB\381_DB_AI系\skills_202609221205.sql` |
| 檔案大小 | 89,784 bytes |
| INSERT rows（估算）| 約 648 筆 |
| `skill_level` 語意 | **職業別**：1=騎士 2=精靈 3=黑魔法師 4=龍騎 5=戰士... |
| grade 欄位 | **不存在** |

381 `skills` 欄位清單（INSERT 語序）：
```
name, skill_level, skill_number, mpConsume, hpConsume,
itemConsumeId, itemConsumeCount, reuseDelay, buffDuration,
target, target_to, damage_value, damage_dice, damage_dice_count,
probability_value, probability_dice, attr, type, lawful,
ranged, area, through, id, nameid, action_id,
castgfx, castgfx2, sysmsgID_happen, sysmsgID_stop, sysmsgID_fail
```

### skills_item_202609221205.sql 摘要

| 項目 | 值 |
|------|-----|
| 絕對路徑 | `I:\L381\Atu-381伺服器端主\DB\381_DB_AI系\skills_item_202609221205.sql` |
| 語意 | 學習技能所需素材（itemids, counts），按技能名稱對應 |
| grade 相關 | 無 |

### 結論

技能等級化（grade/稀有度）在 381 中**不存在**。本功能為企劃新增需求，850-first 設計：
- 在 850 `skills` 表新增 `grade` 欄位
- SkillsTable 讀取並傳遞給 L1Skills model
- 381 不作為行為語意供體

### 搜尋紀錄

- 搜尋 `skills_202609221205.sql` 無 `grade`、`rarity`、`color` 欄位
- 搜尋 `atu381_0906.sql` 有 `skill_level` 欄位（確認為職業別）
- 搜尋 381 DB 無 `skill_grade`、`mythic`、`legendary` 表名
