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
 * 850 原生 — 威望軍階屬性加成載入器
 */
public class PrestigeConfigTable {
    private static final Logger _log = Logger.getLogger(PrestigeConfigTable.class.getName());
    private static PrestigeConfigTable _instance;

    public static class PrestigeRank {
        public int id;
        public String note;
        public int minPrestige;
        public int maxPrestige;
        public String title;
        public int maxHp, maxMp;
        public int dmgUp, bowDmgUp, hitUp, bowHitUp;
        public int mr, sp;
        public int addStr, addDex, addCon, addWis, addInt, addCha;
        public int reductionDmg, magicReduction;
        public int gfxId, gfxTime;
    }

    private final List<PrestigeRank> _rankList = new ArrayList<>();

    public static PrestigeConfigTable getInstance() {
        if (_instance == null) {
            _instance = new PrestigeConfigTable();
        }
        return _instance;
    }

    private PrestigeConfigTable() {
        load();
    }

    public void load() {
        _rankList.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_prestige_config ORDER BY min_prestige ASC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                PrestigeRank r = new PrestigeRank();
                r.id = rs.getInt("id");
                r.note = rs.getString("note");
                r.minPrestige = rs.getInt("min_prestige");
                r.maxPrestige = rs.getInt("max_prestige");
                r.title = rs.getString("title");
                r.maxHp = rs.getInt("max_hp");
                r.maxMp = rs.getInt("max_mp");
                r.dmgUp = rs.getInt("dmg_up");
                r.bowDmgUp = rs.getInt("bow_dmg_up");
                r.hitUp = rs.getInt("hit_up");
                r.bowHitUp = rs.getInt("bow_hit_up");
                r.mr = rs.getInt("mr");
                r.sp = rs.getInt("sp");
                r.addStr = rs.getInt("add_str");
                r.addDex = rs.getInt("add_dex");
                r.addCon = rs.getInt("add_con");
                r.addWis = rs.getInt("add_wis");
                r.addInt = rs.getInt("add_int");
                r.addCha = rs.getInt("add_cha");
                r.reductionDmg = rs.getInt("reduction_dmg");
                r.magicReduction = rs.getInt("magic_reduction");
                r.gfxId = rs.getInt("gfx_id");
                r.gfxTime = rs.getInt("gfx_time");

                _rankList.add(r);
            }
            _log.info("載入威望軍階設定共 " + _rankList.size() + " 階");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_prestige_config 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public PrestigeRank getRank(int currentPrestige) {
        for (PrestigeRank r : _rankList) {
            if (currentPrestige >= r.minPrestige && currentPrestige <= r.maxPrestige) {
                return r;
            }
        }
        return null;
    }
}
