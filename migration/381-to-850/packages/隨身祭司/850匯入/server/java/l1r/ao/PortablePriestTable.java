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
 * 850 原生 — 隨身祭司輔助技能載入器
 */
public class PortablePriestTable {
    private static final Logger _log = Logger.getLogger(PortablePriestTable.class.getName());
    private static PortablePriestTable _instance;

    public static class PriestSkill {
        public int npcId;
        public String name;
        public String skillIds;
        public String skillMps;
        public String note;
    }

    private final Map<Integer, PriestSkill> _priestMap = new HashMap<>();

    public static PortablePriestTable getInstance() {
        if (_instance == null) {
            _instance = new PortablePriestTable();
        }
        return _instance;
    }

    private PortablePriestTable() {
        load();
    }

    public void load() {
        _priestMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_portable_priest");
            rs = pstm.executeQuery();
            while (rs.next()) {
                PriestSkill p = new PriestSkill();
                p.npcId = rs.getInt("npc_id");
                p.name = rs.getString("name");
                p.skillIds = rs.getString("skill_ids");
                p.skillMps = rs.getString("skill_mps");
                p.note = rs.getString("note");

                _priestMap.put(p.npcId, p);
            }
            _log.info("載入隨身祭司設定共 " + _priestMap.size() + " 種");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_portable_priest 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public PriestSkill getPriest(int npcId) {
        return _priestMap.get(npcId);
    }
}
