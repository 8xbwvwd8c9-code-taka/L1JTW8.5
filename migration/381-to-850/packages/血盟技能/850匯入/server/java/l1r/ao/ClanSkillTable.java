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
 * 850 原生 — 血盟技能定義與條件快取器
 */
public class ClanSkillTable {
    private static final Logger _log = Logger.getLogger(ClanSkillTable.class.getName());
    private static ClanSkillTable _instance;

    public static class ClanSkill {
        public int skillId;
        public int skillLv;
        public String skillName;
        public String note;
        public int material;
        public long materialCount;
        public int materialLevel;
        public int checkLvturn;
        public int checkLevel;
        public int addMaxHp, addMaxMp, addHpr, addMpr;
        public int addStr, addCon, addDex, addInt, addWis, addCha;
        public int reductionDmg, reductionMagicDmg;
        public int addWater, addWind, addAc, addSp, addMr;
        public int addDmg, addBowDmg, addHit, addBowHit, addFire, addEarth;
    }

    // Key: (skillId << 16) | skillLv
    private final Map<Integer, ClanSkill> _skillMap = new HashMap<>();

    public static ClanSkillTable getInstance() {
        if (_instance == null) {
            _instance = new ClanSkillTable();
        }
        return _instance;
    }

    private ClanSkillTable() {
        load();
    }

    public void load() {
        _skillMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_clan_skill ORDER BY clan_skill_id ASC, clan_skill_lv ASC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ClanSkill s = new ClanSkill();
                s.skillId = rs.getInt("clan_skill_id");
                s.skillLv = rs.getInt("clan_skill_lv");
                s.skillName = rs.getString("clan_skill_name");
                s.note = rs.getString("note");
                s.material = rs.getInt("material");
                s.materialCount = rs.getLong("material_count");
                s.materialLevel = rs.getInt("material_level");
                s.checkLvturn = rs.getInt("check_lvturn");
                s.checkLevel = rs.getInt("check_level");
                s.addMaxHp = rs.getInt("add_max_hp");
                s.addMaxMp = rs.getInt("add_max_mp");
                s.addHpr = rs.getInt("add_hpr");
                s.addMpr = rs.getInt("add_mpr");
                s.addStr = rs.getInt("add_str");
                s.addCon = rs.getInt("add_con");
                s.addDex = rs.getInt("add_dex");
                s.addInt = rs.getInt("add_int");
                s.addWis = rs.getInt("add_wis");
                s.addCha = rs.getInt("add_cha");
                s.reductionDmg = rs.getInt("reduction_dmg");
                s.reductionMagicDmg = rs.getInt("reduction_magic_dmg");
                s.addWater = rs.getInt("add_water");
                s.addWind = rs.getInt("add_wind");
                s.addAc = rs.getInt("add_ac");
                s.addSp = rs.getInt("add_sp");
                s.addMr = rs.getInt("add_mr");
                s.addDmg = rs.getInt("add_dmg");
                s.addBowDmg = rs.getInt("add_bow_dmg");
                s.addHit = rs.getInt("add_hit");
                s.addBowHit = rs.getInt("add_bow_hit");
                s.addFire = rs.getInt("add_fire");
                s.addEarth = rs.getInt("add_earth");

                int key = (s.skillId << 16) | s.skillLv;
                _skillMap.put(key, s);
            }
            _log.info("載入血盟技能設定共 " + _skillMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_clan_skill 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public ClanSkill getSkill(int skillId, int skillLv) {
        int key = (skillId << 16) | skillLv;
        return _skillMap.get(key);
    }
}
