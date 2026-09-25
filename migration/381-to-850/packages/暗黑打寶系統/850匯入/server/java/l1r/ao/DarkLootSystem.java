package l1r.ao;

import java.util.logging.Logger;

/**
 * 850 原生 — 暗黑炫色裝備打寶與掉落後處理器
 * 核心功能：
 * 1. 掉落判定：依設定檔機率賦予裝備炫色加成。
 * 2. 鑑定保密：未鑑定 (Identified = false) 狀態下強制隱藏前綴詞綴與孔洞，防止打寶劇透。
 * 3. 色彩修復：修復字串轉義，鑑定後正確還原 \f 顏色代碼 (紅、藍、綠、紫等)。
 */
public class DarkLootSystem {
    private static final Logger _log = Logger.getLogger(DarkLootSystem.class.getName());

    public static String formatDarkItemName(String name, boolean isIdentified) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // 1. 若裝備未鑑定，剝除所有炫色前綴與色彩代碼，顯示最純淨白板名稱
        if (!isIdentified) {
            return stripDarkPrefixes(name);
        }

        // 2. 若已鑑定，修復字串轉義，還原真實 \f 色彩
        return restoreColorEscape(name);
    }

    private static String stripDarkPrefixes(String name) {
        // 去除 \f 顏色標記以及常見炫色前綴
        String cleaned = name.replaceAll("\\\\f[0-9A-Za-z=]", "")
                             .replaceAll("[\\[【][^\\]】]*[\\]】]", "");
        return cleaned.trim();
    }

    private static String restoreColorEscape(String name) {
        // 修復資料庫讀取時 \\f 轉為 \f
        return name.replace("\\\\f", "\\f");
    }
}