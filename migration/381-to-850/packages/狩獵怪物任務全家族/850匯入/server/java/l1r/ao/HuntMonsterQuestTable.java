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
 * 850 原生 — 狩獵任務目標與地圖殺怪進度快取管理
 */
public class HuntMonsterQuestTable {
    private static final Logger _log = Logger.getLogger(HuntMonsterQuestTable.class.getName());
    private static HuntMonsterQuestTable _instance;

    public static class HuntQuest {
        public int questId;
        public int questStep;
        public String note;
        public int lv;
        public String mobIds;
        public String mobCounts;
        public String itemId;
        public String itemLv;
        public String itemCount;
        public int saveQuestStep;
        public long addExp;
    }

    private final Map<Integer, HuntQuest> _questMap = new HashMap<>();

    public static HuntMonsterQuestTable getInstance() {
        if (_instance == null) {
            _instance = new HuntMonsterQuestTable();
        }
        return _instance;
    }

    private HuntMonsterQuestTable() {
        load();
    }

    public void load() {
        _questMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_hunt_quest");
            rs = pstm.executeQuery();
            while (rs.next()) {
                HuntQuest q = new HuntQuest();
                q.questId = rs.getInt("quest_id");
                q.questStep = rs.getInt("quest_step");
                q.note = rs.getString("note");
                q.lv = rs.getInt("lv");
                q.mobIds = rs.getString("mob_ids");
                q.mobCounts = rs.getString("mob_counts");
                q.itemId = rs.getString("item_id");
                q.itemLv = rs.getString("item_lv");
                q.itemCount = rs.getString("item_count");
                q.saveQuestStep = rs.getInt("save_quest_step");
                q.addExp = rs.getLong("addexp");

                _questMap.put(q.questId, q);
            }
            _log.info("載入狩獵怪物任務共 " + _questMap.size() + " 項");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_hunt_quest 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public HuntQuest getQuest(int questId) {
        return _questMap.get(questId);
    }
}
