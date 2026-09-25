package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 權威核心 — 變身箭矢外觀解析器
 * 取代 381 ArrowGfxid，改由資料庫驅動並透過 850 原生攻擊封包路徑解析。
 */
public class TransformArrowTable {
    private static final Logger _log = Logger.getLogger(TransformArrowTable.class.getName());
    private static TransformArrowTable _instance;

    private final Map<Integer, Integer> _polyToArrowMap = new HashMap<>();

    public static TransformArrowTable getInstance() {
        if (_instance == null) {
            _instance = new TransformArrowTable();
        }
        return _instance;
    }

    private TransformArrowTable() {
        load();
    }

    public void load() {
        _polyToArrowMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT poly_id, arrow_gfx_id FROM w_transform_arrow_effect");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int polyId = rs.getInt("poly_id");
                int arrowGfxId = rs.getInt("arrow_gfx_id");
                _polyToArrowMap.put(polyId, arrowGfxId);
            }
            _log.info("載入變身箭矢特效映射資料共 " + _polyToArrowMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_arrow_effect 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    /**
     * 依當前變身 ID 取得對應箭矢外觀
     * @param polyId 當前變身 ID
     * @param defaultArrowGfxId 預設箭矢 GFX ID (降級保護)
     * @return 匹配的 arrow gfx id 或預設值
     */
    public int getArrowGfxId(int polyId, int defaultArrowGfxId) {
        return _polyToArrowMap.getOrDefault(polyId, defaultArrowGfxId);
    }
}