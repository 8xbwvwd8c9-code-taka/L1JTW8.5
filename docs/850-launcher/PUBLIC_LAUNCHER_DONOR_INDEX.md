# 850 Launcher / Encoder / Transform — Public Donor Index

> 目的：集中保存過往對話回收到的公版登入器、Encoder、變身編碼與客戶端教學來源，之後查資料先看這份，不再翻舊對話。
>
> 注意：部分外部論壇連結可能失效、附件可能需要登入或權限；本文件保存的是「來源索引與可借技術方向」，不是 850 位址 authority。

```text
BRANCH=work/850-launcher-helper
TARGET=850
CLIENT_AUTHORITY=850 Lin.bin2 ONLY
ADDRESS_AUTHORITY=850 Lin.bin2 ONLY
RESOURCE_AUTHORITY=850 client ONLY
DONOR_CODE=REFERENCE_ONLY
DONOR_ABSOLUTE_ADDRESS=DO_NOT_COPY
```

## 1. 主要 Donor / 倉庫

### A. 公開 3.81 Launcher donor

- Repository: https://github.com/eric761231/lineage3.81Launcher.git
- 過往回收重點：
  - `LinProj`
  - `LauncherDll`
  - `LinLauncher`
  - `ShareMemory`
  - IP / Port / Encrypt / RandEnc / RSA_N / RSA_D 類設定傳遞
  - FileMapping：
    - `Local\{385FC524-96E3-4839-9909-1F2135D4F928}`
    - `Global\{385FC524-96E3-4839-9909-1F2135D4F928}`
- 可借：Launcher ↔ DLL ↔ SharedMemory / crypto-config 架構。
- 不可借：固定位址、版本相依 pointer、client-specific patch offset。

### B. LoginWithoutUI donor

- Repository: https://github.com/8xbwvwd8c9-code-taka/LoginWithoutUI
- 本專案 recovery 亦保存 8.55c 原始碼：
  - `analysis/l1jtw85-recovery`
  - `歐皇8.55c可連線登入器/.../原始碼/LoginWithoutUI/`
- 已知 donor 流程：

```text
LoginWithoutUI
 -> start Lin.bin2
 -> argument 2130706433
 -> read ip.ini line1=IP line2=Port
 -> wait MainWindowHandle
 -> OpenProcess / ReadProcessMemory / WriteProcessMemory
 -> client/build guard
 -> write Port / IP / auth-crypto blob
 -> normal client connect
```

- 可借：登入器介入模式、client version guard、外部 IP/Port 設定流程。
- 不可借：8.55 fixed absolute addresses。

### C. 本專案 850 authority

- Repository: https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5
- Branch: `work/850-launcher-helper`
- Authority Lin.bin2 SHA256:

```text
FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
```

- 所有 WP3~WP10 runtime/resource 結論最終都必須回到 850 自身驗證。

## 2. 45天堂 / 公版登入器與變身編碼資料

### 880核心 + 登入器分享

- https://lineage45.com/thread-339928-1-1.html
- 過往用途：追 182/760 → 815 → 850 → 880 客戶端/登入器演進，TW1901142505、登入器、變檔關聯。

### 8.8C / LoginWithoutUI / Encoder / 變身編碼附件集中頁

- https://lineage45.com/thread-202188-1-1.html
- 過往記錄附件：
  - `LoginWithoutUI.8.8版本登入器.rar`
  - `Encoder登入器.rar`
  - `登入器 編碼器.rar`
  - `天堂880登入器.zip`
  - `天堂8.8C模擬器.rar`
  - `最新8.8c變身編碼.rar`
- 重要性：同一版本生態中同時出現 LoginWithoutUI / Encoder / Launcher / 變身編碼，支持「變身資源編碼可能與登入器/客戶端 revision 配套」的研究方向。

### 8.8C / TW1901142505 / Login.bin 配套

- https://lineage45.com/archiver/tid-209387.html
- 過往用途：查 8.8C、TW1901142505、Login.bin / 登入器配套。

### 8.15 登入器案例

- https://lineage45.com/thread-156799-1-1.html
- 過往用途：8.15 登入器 / EOF / 登入失敗等版本不匹配案例。

### 7.6 登入器配套

- https://lineage45.com/thread-169912-1-1.html
- 過往用途：確認 Launcher.exe / DLL / Login.bin / 設定檔不能只拿單一 EXE。

### 客戶端版本索引

- https://lineage45.com/thread-228-1-1.html
- 過往用途：8.1C / 8.5C / 8.8C / 9.0R 版本脈絡。

### L1JTW 高版本資料索引

- https://lineage45.com/archiver/fid-40.html?page=3
- 過往用途：查 880 / 高版本公開資料。

## 3. 其他登入器 / Encoder 來源

### Lineages.tw

- https://lineages.tw/thread-644-1-1.html
- 過往用途：不同版本登入器產品 / 配套資料，交叉確認版本與登入器綁定。

### lineage.cn Encoder / Launcher 附件

- https://www.lineage.cn/forum.php?mod=misc&action=attachpay&aid=3673&tid=5053
- 過往記錄檔名：`8.0-8.80登入器 編碼器.rar`
- 研究用途：Encoder + Launcher 是否成套、8.0~8.80 版本共用/分版界線。

## 4. 客戶端 / 變身 / Sprite 教學來源

### J.J. / MoroseDog 私服教學

- GitLab site: https://morosedog.gitlab.io/private-lineage-20210712-private-lineage-0/
- GitHub: https://github.com/MoroseDog/private-lineage-tutorial
- 過往用途：
  - Client 結構
  - 登入器
  - 變身檔
  - gfxid / polyid
  - spr_action
  - DB 與 client resource 關係

## 5. 880 donor binary 留下的線索

過往從 880 登入器 donor strings / 檔案中回收到：

```text
c:\ericswork\login_880_1810102501\login\linlauncher\
./Launcher.new
\LinLogin.bin
launcher.xml
%s/%s.bin?t=%d
LOhuLoader.exe
LOhuMonitor.exe
```

`LinLauncher.dat` 過往亦看到：

```text
https://www.google.com.tw
http://192.168.1.100/list.txt
```

研究假設：

```text
Launcher
 -> update/list
 -> LinLogin.bin
 -> LauncherDll
 -> client
```

這是 donor 架構線索，不代表 850 已證明採用同一流程。

## 6. 不完整但值得保留的歷史來源

過往曾使用 `private-servers-game.com` 的某頁來佐證：

```text
TW1810102501 = 8.80
TW1901142505 = 8.80
```

但舊對話沒有保存完整 path，因此：

```text
STATUS=INCOMPLETE_URL
DO_NOT_INVENT_URL=YES
```

## 7. 850 採用規則

### Login / Launcher

```text
LOGIN_ARCHITECTURE_DONOR = 8.55 LoginWithoutUI
SHAREDMEMORY_DONOR       = lineage3.81Launcher
ENCODER_DONOR            = 8.8/880 public packages
ADDRESS_AUTHORITY        = 850 Lin.bin2 ONLY
```

### Transform / 變身

```text
TRANSFORM_TRACK=WP10
850_CLIENT_RESOURCE=AUTHORITY
DONOR_ENCODER=REFERENCE_ONLY
DONOR_POLYMORPH_RESOURCE=REFERENCE_ONLY
```

驗證順序：

```text
850 original client
 -> identify actual transform resources
 -> PolymorphUI.xml / Polymorphlist*.xml / desc-c.tbl
 -> identify decode/extract path
 -> modify one controlled test entry
 -> re-encode/package
 -> launch through 850 launcher
 -> verify whether launcher participates in decrypt/patch/resource loading
 -> only then create 850-specific transform pipeline
```

## 8. 禁止誤用

```text
DO_NOT_COPY_381_880_ABSOLUTE_ADDRESS
DO_NOT_ASSUME_ENCODER_FORMAT_COMPATIBLE
DO_NOT_ASSUME_LOGIN_BIN_COMPATIBLE
DO_NOT_ASSUME_POLYMORPH_LIST_AUTHORITY
DO_NOT_REPLACE_850_TILE_TEXT_BLINDLY
DO_NOT_MARK_DONOR_BEHAVIOR_AS_850_PROOF
```

## 9. 快速研究優先順序

```text
P1 = 本專案 recovery 的 8.55 LoginWithoutUI 原始碼
P2 = eric761231/lineage3.81Launcher
P3 = lineage45 8.8C/880 Encoder + Launcher + transform packages
P4 = MoroseDog client/transform tutorial
P5 = 其他論壇案例與歷史版本索引
```
