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
 * 850 原生 — 道具升級置換載入器
 */
public class ItemUpgradeTable {
    private static final Logger _log = Logger.getLogger(ItemUpgradeTable.class.getName());
    private static ItemUpgradeTable _instance;

    public static class UpgradeRule {
        public int itemId;
        public int integrationId;
        public int random;
        public int newItem;
        public int newItemCount;
        public String msg;
        public int gfxId;
    }

    private final Map<Integer, UpgradeRule> _ruleMap = new HashMap<>();

    public static ItemUpgradeTable getInstance() {
        if (_instance == null) {
            _instance = new ItemUpgradeTable();
        }
        return _instance;
    }

    private ItemUpgradeTable() {
        load();
    }

    public void load() {
        _ruleMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_upgrade");
            rs = pstm.executeQuery();
            while (rs.next()) {
                UpgradeRule r = new UpgradeRule();
                r.itemId = rs.getInt("item_id");
                r.integrationId = rs.getInt("integration_id");
                r.random = rs.getInt("random");
                r.newItem = Integer.parseInt(rs.getString("new_item"));
                r.newItemCount = Integer.parseInt(rs.getString("new_item_counts"));
                r.msg = rs.getString("msg");
                r.gfxId = rs.getInt("gfx_id");

                _ruleMap.put(r.itemId, r);
            }
            _log.info("載入道具升級規則共 " + _ruleMap.size() + " 項");
        } catch (Exception e) {
            _log.log(Level.SEVERE, "載入 w_item_upgrade 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public UpgradeRule getRule(int itemId) {
        return _ruleMap.get(itemId);
    }
}
