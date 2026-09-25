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
 * 850 原生 — 敵人死亡奪寶與紅人假人追殺規則載入器
 */
public class EnemyDeathLootTable {
    private static final Logger _log = Logger.getLogger(EnemyDeathLootTable.class.getName());
    private static EnemyDeathLootTable _instance;

    public static class DeathLootRule {
        public int id;
        public int itemId;
        public String note;
        public int stealChance;
        public int minStealCount;
        public int maxStealCount;
        public boolean isBroadcast;
        public boolean dropOnFloor;
        public int antiStealItemId;
        public int minLevel;
        public int meteLevel;
        public String dropMsg;
    }

    private final List<DeathLootRule> _rules = new ArrayList<>();

    public static EnemyDeathLootTable getInstance() {
        if (_instance == null) {
            _instance = new EnemyDeathLootTable();
        }
        return _instance;
    }

    private EnemyDeathLootTable() {
        load();
    }

    public void load() {
        _rules.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_enemy_death_loot");
            rs = pstm.executeQuery();
            while (rs.next()) {
                DeathLootRule r = new DeathLootRule();
                r.id = rs.getInt("id");
                r.itemId = rs.getInt("item_id");
                r.note = rs.getString("note");
                r.stealChance = rs.getInt("steal_chance");
                r.minStealCount = rs.getInt("min_steal_count");
                r.maxStealCount = rs.getInt("max_steal_count");
                r.isBroadcast = rs.getInt("is_broadcast") != 0;
                r.dropOnFloor = rs.getInt("drop_on_floor") != 0;
                r.antiStealItemId = rs.getInt("anti_steal_item_id");
                r.minLevel = rs.getInt("min_level");
                r.meteLevel = rs.getInt("mete_level");
                r.dropMsg = rs.getString("drop_msg");
                _rules.add(r);
            }
            _log.info("載入敵人死亡奪寶規則共 " + _rules.size() + " 條");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_enemy_death_loot 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public List<DeathLootRule> getRules() {
        return _rules;
    }
}