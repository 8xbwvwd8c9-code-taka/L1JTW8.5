package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 威望怪物擊殺掉落獎勵載入器
 */
public class PrestigeMonsterTable {
    private static final Logger _log = Logger.getLogger(PrestigeMonsterTable.class.getName());
    private static PrestigeMonsterTable _instance;
    private static final Random _rnd = new Random();

    public static class PrestigeMonster {
        public int npcId;
        public String name;
        public int minPrestige;
        public int maxPrestige;

        public int getRandomPrestige() {
            if (maxPrestige <= minPrestige) {
                return minPrestige;
            }
            return minPrestige + _rnd.nextInt(maxPrestige - minPrestige + 1);
        }
    }

    private final Map<Integer, PrestigeMonster> _monsterMap = new HashMap<>();

    public static PrestigeMonsterTable getInstance() {
        if (_instance == null) {
            _instance = new PrestigeMonsterTable();
        }
        return _instance;
    }

    private PrestigeMonsterTable() {
        load();
    }

    public void load() {
        _monsterMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_prestige_monster");
            rs = pstm.executeQuery();
            while (rs.next()) {
                PrestigeMonster m = new PrestigeMonster();
                m.npcId = rs.getInt("npc_id");
                m.name = rs.getString("name");
                m.minPrestige = rs.getInt("min_prestige");
                m.maxPrestige = rs.getInt("max_prestige");

                _monsterMap.put(m.npcId, m);
            }
            _log.info("載入威望怪物掉落獎勵共 " + _monsterMap.size() + " 隻");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_prestige_monster 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public PrestigeMonster getMonster(int npcId) {
        return _monsterMap.get(npcId);
    }

    public int getPrestigeReward(int npcId) {
        PrestigeMonster m = _monsterMap.get(npcId);
        if (m == null) {
            return 0;
        }
        return m.getRandomPrestige();
    }
}
