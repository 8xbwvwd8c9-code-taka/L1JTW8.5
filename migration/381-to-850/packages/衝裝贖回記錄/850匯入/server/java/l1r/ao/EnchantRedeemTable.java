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
 * 850 原生 — 衝裝贖回配置載入器
 */
public class EnchantRedeemTable {
    private static final Logger _log = Logger.getLogger(EnchantRedeemTable.class.getName());
    private static EnchantRedeemTable _instance;

    public static class RedeemConfig {
        public int id;
        public int npcId;
        public int itemId;
        public int count;
    }

    private final Map<Integer, RedeemConfig> _configMap = new HashMap<>();

    public static EnchantRedeemTable getInstance() {
        if (_instance == null) {
            _instance = new EnchantRedeemTable();
        }
        return _instance;
    }

    private EnchantRedeemTable() {
        load();
    }

    public void load() {
        _configMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_enchant_redeem_config");
            rs = pstm.executeQuery();
            while (rs.next()) {
                RedeemConfig c = new RedeemConfig();
                c.id = rs.getInt("id");
                c.npcId = rs.getInt("npc_id");
                c.itemId = rs.getInt("item_id");
                c.count = rs.getInt("count");

                _configMap.put(c.npcId, c);
            }
            _log.info("載入衝裝贖回NPC配置共 " + _configMap.size() + " 種");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_enchant_redeem_config 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public RedeemConfig getConfig(int npcId) {
        return _configMap.get(npcId);
    }
}
