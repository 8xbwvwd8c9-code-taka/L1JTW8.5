# L1JTW8.5 — 850 Launcher / Helper

此支線只收納 **850 登入器與 850 客戶端內掛** 相關文件/程式。

```text
BRANCH=work/850-launcher-helper
TARGET=850
AUTHORITY=850
DONORS=381,880
SERVER_DOCS=EXCLUDED
```

- [850 登入器 / 內掛開發報告](docs/850-launcher/REPORT.md)
- [公版登入器 / Encoder / 變身編碼 Donor 索引](docs/850-launcher/PUBLIC_LAUNCHER_DONOR_INDEX.md)

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
