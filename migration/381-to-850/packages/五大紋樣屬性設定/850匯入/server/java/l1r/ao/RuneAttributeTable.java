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
 * 850 原生 — 五大紋樣屬性加成載入與快取管理
 * 神殿類型: 1=伊娃(Eva), 2=沙哈(Sayha), 3=帕格里奧(Paagrio), 4=殷海薩(Einhasad), 5=馬普勒(Maphr)
 */
public class RuneAttributeTable {
    private static final Logger _log = Logger.getLogger(RuneAttributeTable.class.getName());
    private static RuneAttributeTable _instance;

    public static final int TYPE_EVA = 1;
    public static final int TYPE_SAYHA = 2;
    public static final int TYPE_PAAGRIO = 3;
    public static final int TYPE_EINHASAD = 4;
    public static final int TYPE_MAPHR = 5;

    public static class RuneBonus {
        public int runeType;
        public String runeName;
        public String runeCode;
        public int level;
        public int addStr, addDex, addCon, addInt, addWis, addCha;
        public int addAc, addHp, addMp, addHpr, addMpr;
        public int addDmg, addHit, addBowDmg, addBowHit;
        public int reductionDmg, magicReduction;
        public int addMr, addSp;
        public int addFire, addWind, addEarth, addWater;
        public int addStun, addStone, addSleep, addFreeze, addSustain, addBlind;
        public double addExp;
        public int hpPotion;
        public int addDoubleDmg, addWeight, addPvpDmg, addPvpR;
    }

    // Key: (runeType << 8) | level
    private final Map<Integer, RuneBonus> _runeMap = new HashMap<>();

    public static RuneAttributeTable getInstance() {
        if (_instance == null) {
            _instance = new RuneAttributeTable();
        }
        return _instance;
    }

    private RuneAttributeTable() {
        load();
    }

    public void load() {
        _runeMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_rune_attribute");
            rs = pstm.executeQuery();
            while (rs.next()) {
                RuneBonus b = new RuneBonus();
                b.runeType = rs.getInt("rune_type");
                b.runeName = rs.getString("rune_name");
                b.runeCode = rs.getString("rune_code");
                b.level = rs.getInt("level");
                b.addStr = rs.getInt("add_str");
                b.addDex = rs.getInt("add_dex");
                b.addCon = rs.getInt("add_con");
                b.addInt = rs.getInt("add_int");
                b.addWis = rs.getInt("add_wis");
                b.addCha = rs.getInt("add_cha");
                b.addAc = rs.getInt("add_ac");
                b.addHp = rs.getInt("add_hp");
                b.addMp = rs.getInt("add_mp");
                b.addHpr = rs.getInt("add_hpr");
                b.addMpr = rs.getInt("add_mpr");
                b.addDmg = rs.getInt("add_dmg");
                b.addHit = rs.getInt("add_hit");
                b.addBowDmg = rs.getInt("add_bow_dmg");
                b.addBowHit = rs.getInt("add_bow_hit");
                b.reductionDmg = rs.getInt("reduction_dmg");
                b.magicReduction = rs.getInt("magic_reduction");
                b.addMr = rs.getInt("add_mr");
                b.addSp = rs.getInt("add_sp");
                b.addFire = rs.getInt("add_fire");
                b.addWind = rs.getInt("add_wind");
                b.addEarth = rs.getInt("add_earth");
                b.addWater = rs.getInt("add_water");
                b.addStun = rs.getInt("add_stun");
                b.addStone = rs.getInt("add_stone");
                b.addSleep = rs.getInt("add_sleep");
                b.addFreeze = rs.getInt("add_freeze");
                b.addSustain = rs.getInt("add_sustain");
                b.addBlind = rs.getInt("add_blind");
                b.addExp = rs.getDouble("add_exp");
                b.hpPotion = rs.getInt("hp_potion");
                b.addDoubleDmg = rs.getInt("add_double_dmg");
                b.addWeight = rs.getInt("add_weight");
                b.addPvpDmg = rs.getInt("add_pvp_dmg");
                b.addPvpR = rs.getInt("add_pvp_r");

                int key = (b.runeType << 8) | b.level;
                _runeMap.put(key, b);
            }
            _log.info("載入五大紋樣神殿刻印屬性共 " + _runeMap.size() + " 階");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_rune_attribute 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public RuneBonus getBonus(int runeType, int level) {
        int key = (runeType << 8) | level;
        return _runeMap.get(key);
    }
}
