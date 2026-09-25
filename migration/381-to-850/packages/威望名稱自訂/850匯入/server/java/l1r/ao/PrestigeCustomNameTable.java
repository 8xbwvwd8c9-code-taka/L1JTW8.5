package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 威望系統自訂全服稱呼名稱載入器
 */
public class PrestigeCustomNameTable {
    private static final Logger _log = Logger.getLogger(PrestigeCustomNameTable.class.getName());
    private static PrestigeCustomNameTable _instance;

    private String _customName = "威望積分";

    public static PrestigeCustomNameTable getInstance() {
        if (_instance == null) {
            _instance = new PrestigeCustomNameTable();
        }
        return _instance;
    }

    private PrestigeCustomNameTable() {
        load();
    }

    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT type_name FROM w_prestige_custom_name LIMIT 1");
            rs = pstm.executeQuery();
            if (rs.next()) {
                _customName = rs.getString("type_name");
            }
            _log.info("載入威望自訂名稱: " + _customName);
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_prestige_custom_name 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public String getCustomName() {
        return _customName;
    }
}
