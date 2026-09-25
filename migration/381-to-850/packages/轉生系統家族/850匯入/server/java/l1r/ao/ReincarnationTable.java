package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 轉生系統資料載入器 (ReincarnationTable)
 * 涵蓋轉生屬性、獎勵道具與經驗獲取懲罰倍率，提供 idempotent 重算與數值查詢。
 */
public class ReincarnationTable {
    private static final Logger _log = Logger.getLogger(ReincarnationTable.class.getName());
    private static ReincarnationTable _instance;

    public static class ReincarnationAbility {
        public int meteLevel;
        public int type; // 職業
        public String note;
        public String title;
        public int ac, hp, mp, hpr, mpr;
        public int str, con, dex, wis, cha, intel;
        public int sp, mr;
        public int hitModifier, dmgModifier, bowHitModifier, bowDmgModifier;
        public int magicDmgModifier, magicDmgReduction, reductionDmg;
        public int defenseWater, defenseWind, defenseFire, defenseEarth;
        public int registStun, registStone, registSleep, registFreeze, registSustain, registBlind;
    }

    public static class ReincarnationReward {
        public int meteLevel;
        public int itemId;
        public int count;
        public String note;
    }

    // Key: (meteLevel << 8) | type
    private final Map<Integer, ReincarnationAbility> _abilityMap = new HashMap<>();
    private final Map<Integer, List<ReincarnationReward>> _rewardMap = new HashMap<>();
    private final Map<Integer, Double> _expPenaltyMap = new HashMap<>();

    public static ReincarnationTable getInstance() {
        if (_instance == null) {
            _instance = new ReincarnationTable();
        }
        return _instance;
    }

    private ReincarnationTable() {
        load();
    }

    public void load() {
        _abilityMap.clear();
        _rewardMap.clear();
        _expPenaltyMap.clear();

        loadAbilities();
        loadRewards();
        loadExpPenalties();
    }

    private void loadAbilities() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_reincarnation");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ReincarnationAbility a = new ReincarnationAbility();
                a.meteLevel = rs.getInt("mete_level");
                a.type = rs.getInt("type");
                a.note = rs.getString("note");
                a.title = rs.getString("title");
                a.ac = rs.getInt("ac");
                a.hp = rs.getInt("hp");
                a.mp = rs.getInt("mp");
                a.hpr = rs.getInt("hpr");
                a.mpr = rs.getInt("mpr");
                a.str = rs.getInt("str");
                a.con = rs.getInt("con");
                a.dex = rs.getInt("dex");
                a.wis = rs.getInt("wis");
                a.cha = rs.getInt("cha");
                a.intel = rs.getInt("intel");
                a.sp = rs.getInt("sp");
                a.mr = rs.getInt("mr");
                a.hitModifier = rs.getInt("hit_modifier");
                a.dmgModifier = rs.getInt("dmg_modifier");
                a.bowHitModifier = rs.getInt("bow_hit_modifier");
                a.bowDmgModifier = rs.getInt("bow_dmg_modifier");
                a.magicDmgModifier = rs.getInt("magic_dmg_modifier");
                a.magicDmgReduction = rs.getInt("magic_dmg_reduction");
                a.reductionDmg = rs.getInt("reduction_dmg");
                a.defenseWater = rs.getInt("defense_water");
                a.defenseWind = rs.getInt("defense_wind");
                a.defenseFire = rs.getInt("defense_fire");
                a.defenseEarth = rs.getInt("defense_earth");
                a.registStun = rs.getInt("regist_stun");
                a.registStone = rs.getInt("regist_stone");
                a.registSleep = rs.getInt("regist_sleep");
                a.registFreeze = rs.getInt("regist_freeze");
                a.registSustain = rs.getInt("regist_sustain");
                a.registBlind = rs.getInt("regist_blind");

                int key = (a.meteLevel << 8) | a.type;
                _abilityMap.put(key, a);
            }
            _log.info("載入轉生屬性能力資料共 " + _abilityMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_reincarnation 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void loadRewards() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_reincarnation_giveitem");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ReincarnationReward r = new ReincarnationReward();
                r.meteLevel = rs.getInt("mete_level");
                r.itemId = rs.getInt("item_id");
                r.count = rs.getInt("count");
                r.note = rs.getString("note");
                _rewardMap.computeIfAbsent(r.meteLevel, k -> new ArrayList<>()).add(r);
            }
            _log.info("載入轉生獎勵道具資料共 " + _rewardMap.size() + " 階級");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_reincarnation_giveitem 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void loadExpPenalties() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_reincarnation_exp");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int level = rs.getInt("mete_level");
                double penalty = rs.getDouble("exp_penalty");
                _expPenaltyMap.put(level, penalty);
            }
            _log.info("載入轉生經驗懲罰倍率共 " + _expPenaltyMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_reincarnation_exp 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public ReincarnationAbility getAbility(int meteLevel, int type) {
        int key = (meteLevel << 8) | type;
        return _abilityMap.get(key);
    }

    public List<ReincarnationReward> getRewards(int meteLevel) {
        return _rewardMap.getOrDefault(meteLevel, Collections.emptyList());
    }

    public double getExpPenalty(int meteLevel) {
        return _expPenaltyMap.getOrDefault(meteLevel, 1.0);
    }
}