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
 * 850 原生 — 血盟等級能力加成載入器
 */
public class ClanLevelTable {
    private static final Logger _log = Logger.getLogger(ClanLevelTable.class.getName());
    private static ClanLevelTable _instance;

    public static class ClanLevelBonus {
        public int clanLevel;
        public String note;
        public int contribution;
        public int addMaxHp, addMaxMp;
        public int addDmg, addBowDmg, addHit, addBowHit;
        public int addMr, addSp, addAc;
        public int addFire, addWind, addEarth, addWater;
        public int addStr, addDex, addCon, addWis, addInt, addCha;
        public int reductionDmg, reductionMagicDmg;
        public double expRate;
        public int addHpr, addMpr, addWeight;
    }

    private final Map<Integer, ClanLevelBonus> _bonusMap = new HashMap<>();

    public static ClanLevelTable getInstance() {
        if (_instance == null) {
            _instance = new ClanLevelTable();
        }
        return _instance;
    }

    private ClanLevelTable() {
        load();
    }

    public void load() {
        _bonusMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_clan_level ORDER BY clan_level ASC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ClanLevelBonus b = new ClanLevelBonus();
                b.clanLevel = rs.getInt("clan_level");
                b.note = rs.getString("note");
                b.contribution = rs.getInt("contribution");
                b.addMaxHp = rs.getInt("add_max_hp");
                b.addMaxMp = rs.getInt("add_max_mp");
                b.addDmg = rs.getInt("add_dmg");
                b.addBowDmg = rs.getInt("add_bow_dmg");
                b.addHit = rs.getInt("add_hit");
                b.addBowHit = rs.getInt("add_bow_hit");
                b.addMr = rs.getInt("add_mr");
                b.addSp = rs.getInt("add_sp");
                b.addAc = rs.getInt("add_ac");
                b.addFire = rs.getInt("add_fire");
                b.addWind = rs.getInt("add_wind");
                b.addEarth = rs.getInt("add_earth");
                b.addWater = rs.getInt("add_water");
                b.addStr = rs.getInt("add_str");
                b.addDex = rs.getInt("add_dex");
                b.addCon = rs.getInt("add_con");
                b.addWis = rs.getInt("add_wis");
                b.addInt = rs.getInt("add_int");
                b.addCha = rs.getInt("add_cha");
                b.reductionDmg = rs.getInt("reduction_dmg");
                b.reductionMagicDmg = rs.getInt("reduction_magic_dmg");
                b.expRate = rs.getDouble("exp_rate");
                b.addHpr = rs.getInt("add_hpr");
                b.addMpr = rs.getInt("add_mpr");
                b.addWeight = rs.getInt("add_weight");

                _bonusMap.put(b.clanLevel, b);
            }
            _log.info("載入血盟等級加成共 " + _bonusMap.size() + " 階");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_clan_level 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public ClanLevelBonus getBonus(int clanLevel) {
        return _bonusMap.get(clanLevel);
    }
}
