# 驗證記錄 — skill-grade-system

## 狀態：HOLD（尚未開始驗證）

驗證工作須在以下前提下執行：
1. `install_skill_grade.sql` 成功執行（ALTER TABLE）
2. SkillsTable Java adapter 完成並通過 Java 8 編譯
3. 服務端重啟無 SQLException

---

## 驗證清單

### DB 驗證

- [ ] `SHOW COLUMNS FROM skills LIKE 'grade'` → 確認欄位存在、型態 tinyint(1)、default -1
- [ ] `SHOW INDEX FROM skills WHERE Key_name='idx_skill_grade'` → 確認索引存在
- [ ] `SELECT COUNT(*) FROM skills WHERE grade != -1` → 確認初始狀態所有技能 grade=-1

### rollback 驗證

- [ ] 執行 `rollback_skill_grade.sql`
- [ ] `SHOW COLUMNS FROM skills LIKE 'grade'` → 確認欄位已移除
- [ ] 重新執行 install 確認 idempotent

### 核心驗證（待 Java 完成）

- [ ] SkillsTable.loadAllSkills() 無 SQL error
- [ ] L1Skills.getGrade() 回傳 -1（預設值）
- [ ] 設定 grade=5 的技能，L1Skills.getGrade() 回傳 5

### 控制驗證

- [ ] 設定 `SkillGradeEnabled=false` → SkillsTable 略過 grade 欄位
- [ ] 設定 `SkillGradeEnabled=true` → 正常讀取

---

## 驗證結果紀錄（尚未執行）

```
DB_INSTALL=PENDING
ROLLBACK_GATE=PENDING
CORE_COMPILE=PENDING
RUNTIME=PENDING
CLIENT=NOT_PROVEN（客戶端呼叫鏈未確認）
```
