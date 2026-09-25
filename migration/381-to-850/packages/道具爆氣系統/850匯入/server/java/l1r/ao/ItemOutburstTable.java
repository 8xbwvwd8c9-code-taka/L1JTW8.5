package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 道具怒氣爆氣屬性與消耗載入器
 */
public class ItemOutburstTable {
    private static final Logger _log = Logger.getLogger(ItemOutburstTable.class.getName());
    private static ItemOutburstTable _instance;

    public static class OutburstTier {
        public int id;
        public int itemId;
        public int minLevel;
        public int maxLevel;
        public int shortDmg, longDmg, reductionDmg;
        public int addHp, addMp, addSp;
        public int gfxId;
        public int drainRagePerSec;
        public String msgEnable, msgDisable;
    }

    private final List<OutburstTier> _tierList = new ArrayList<>();

    public static ItemOutburstTable getInstance() {
        if (_instance == null) {
            _instance = new ItemOutburstTable();
        }
        return _instance;
    }

    private ItemOutburstTable() {
        load();
    }

    public void load() {
        _tierList.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_outburst ORDER BY min_level ASC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                OutburstTier t = new OutburstTier();
                t.id = rs.getInt("id");
                t.itemId = rs.getInt("item_id");
                t.minLevel = rs.getInt("min_level");
                t.maxLevel = rs.getInt("max_level");
                t.shortDmg = rs.getInt("short_dmg");
                t.longDmg = rs.getInt("long_dmg");
                t.reductionDmg = rs.getInt("reduction_dmg");
                t.addHp = rs.getInt("add_hp");
                t.addMp = rs.getInt("add_mp");
                t.addSp = rs.getInt("add_sp");
                t.gfxId = rs.getInt("gfx_id");
                t.drainRagePerSec = rs.getInt("drain_rage_per_sec");
                t.msgEnable = rs.getString("msg_enable");
                t.msgDisable = rs.getString("msg_disable");

                _tierList.add(t);
            }
            _log.info("載入道具爆氣等級配置共 " + _tierList.size() + " 階");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_item_outburst 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public OutburstTier getTier(int itemId, int playerLevel) {
        for (OutburstTier t : _tierList) {
            if (t.itemId == itemId && playerLevel >= t.minLevel && playerLevel <= t.maxLevel) {
                return t;
            }
        }
        return null;
    }
}
