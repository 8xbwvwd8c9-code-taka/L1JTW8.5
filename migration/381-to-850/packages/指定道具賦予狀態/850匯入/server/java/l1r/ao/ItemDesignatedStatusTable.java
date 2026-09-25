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
 * 850 原生 — 指定道具賦予狀態載入與快取管理器
 */
public class ItemDesignatedStatusTable {
    private static final Logger _log = Logger.getLogger(ItemDesignatedStatusTable.class.getName());
    private static ItemDesignatedStatusTable _instance;

    public static class ItemStatus {
        public int itemId;
        public String name;
        public int classId;
        public int skinId;
        public int type;
        public int weaponDmg;
        public int weaponPro;
        public int addStr, addDex, addCon, addInt, addWis, addCha;
        public int addAc, addHp, addMp, addHpr, addMpr;
        public int addDmg, addHit, addBowDmg, addBowHit;
        public int addDmgR, addMagicR, addMr, addSp;
        public int addFire, addWind, addEarth, addWater;
        public int addStun, addStone, addSleep, addFreeze, addSustain, addBlind;
        public int addGf;
        public double addExp;
        public int effectIcon;
        public String displayTitle;
        public int polyId;
        public int polyTime;
    }

    private final Map<Integer, ItemStatus> _statusMap = new HashMap<>();

    public static ItemDesignatedStatusTable getInstance() {
        if (_instance == null) {
            _instance = new ItemDesignatedStatusTable();
        }
        return _instance;
    }

    private ItemDesignatedStatusTable() {
        load();
    }

    public void load() {
        _statusMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_designated_status");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ItemStatus s = new ItemStatus();
                s.itemId = rs.getInt("item_id");
                s.name = rs.getString("name");
                s.classId = rs.getInt("class_id");
                s.skinId = rs.getInt("skin_id");
                s.type = rs.getInt("type");
                s.weaponDmg = rs.getInt("weapon_dmg");
                s.weaponPro = rs.getInt("weapon_pro");
                s.addStr = rs.getInt("add_str");
                s.addDex = rs.getInt("add_dex");
                s.addCon = rs.getInt("add_con");
                s.addInt = rs.getInt("add_int");
                s.addWis = rs.getInt("add_wis");
                s.addCha = rs.getInt("add_cha");
                s.addAc = rs.getInt("add_ac");
                s.addHp = rs.getInt("add_hp");
                s.addMp = rs.getInt("add_mp");
                s.addHpr = rs.getInt("add_hpr");
                s.addMpr = rs.getInt("add_mpr");
                s.addDmg = rs.getInt("add_dmg");
                s.addHit = rs.getInt("add_hit");
                s.addBowDmg = rs.getInt("add_bow_dmg");
                s.addBowHit = rs.getInt("add_bow_hit");
                s.addDmgR = rs.getInt("add_dmg_r");
                s.addMagicR = rs.getInt("add_magic_r");
                s.addMr = rs.getInt("add_mr");
                s.addSp = rs.getInt("add_sp");
                s.addFire = rs.getInt("add_fire");
                s.addWind = rs.getInt("add_wind");
                s.addEarth = rs.getInt("add_earth");
                s.addWater = rs.getInt("add_water");
                s.addStun = rs.getInt("add_stun");
                s.addStone = rs.getInt("add_stone");
                s.addSleep = rs.getInt("add_sleep");
                s.addFreeze = rs.getInt("add_freeze");
                s.addSustain = rs.getInt("add_sustain");
                s.addBlind = rs.getInt("add_blind");
                s.addGf = rs.getInt("add_gf");
                s.addExp = rs.getDouble("add_exp");
                s.effectIcon = rs.getInt("effect_icon");
                s.displayTitle = rs.getString("display_title");
                s.polyId = rs.getInt("poly_id");
                s.polyTime = rs.getInt("poly_time");

                _statusMap.put(s.itemId, s);
            }
            _log.info("載入指定道具賦予狀態共 " + _statusMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_item_designated_status 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public ItemStatus getStatus(int itemId) {
        return _statusMap.get(itemId);
    }

    public boolean hasStatus(int itemId) {
        return _statusMap.containsKey(itemId);
    }
}
