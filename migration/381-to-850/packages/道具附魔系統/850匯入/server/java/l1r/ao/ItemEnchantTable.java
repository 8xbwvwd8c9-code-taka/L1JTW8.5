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
 * 850 原生 — 道具附魔卡片屬性與觸發載入器
 */
public class ItemEnchantTable {
    private static final Logger _log = Logger.getLogger(ItemEnchantTable.class.getName());
    private static ItemEnchantTable _instance;

    public static class EnchantPower {
        public int id;
        public String itemType;
        public int itemId;
        public String note;
        public int powerCount;
        public String powerName;
        public int probUnequip, unequipment;
        public int probPoly, polyId, polyTime;
        public int probability, skillId, targetTo;
        public int addMaxHp, addMaxMp;
        public int addStr, addCon, addDex, addInt, addWis, addCha;
        public int addHp, addMp, addHpr, addMpr, addSp;
        public int hitMod, dmgMod, bowHitMod, bowDmgMod, doubleDmgChance;
        public int addAc, mDef, dmgReduction;
        public Integer gfxId;
    }

    private final Map<Integer, EnchantPower> _powerMap = new HashMap<>();

    public static ItemEnchantTable getInstance() {
        if (_instance == null) {
            _instance = new ItemEnchantTable();
        }
        return _instance;
    }

    private ItemEnchantTable() {
        load();
    }

    public void load() {
        _powerMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_enchant_power");
            rs = pstm.executeQuery();
            while (rs.next()) {
                EnchantPower p = new EnchantPower();
                p.id = rs.getInt("id");
                p.itemType = rs.getString("item_type");
                p.itemId = rs.getInt("item_id");
                p.note = rs.getString("note");
                p.powerCount = rs.getInt("power_count");
                p.powerName = rs.getString("power_name");
                p.probUnequip = rs.getInt("prob_unequip");
                p.unequipment = rs.getInt("unequipment");
                p.probPoly = rs.getInt("prob_poly");
                p.polyId = rs.getInt("poly_id");
                p.polyTime = rs.getInt("poly_time");
                p.probability = rs.getInt("probability");
                p.skillId = rs.getInt("skill_id");
                p.targetTo = rs.getInt("target_to");
                p.addMaxHp = rs.getInt("add_max_hp");
                p.addMaxMp = rs.getInt("add_max_mp");
                p.addStr = rs.getInt("add_str");
                p.addCon = rs.getInt("add_con");
                p.addDex = rs.getInt("add_dex");
                p.addInt = rs.getInt("add_int");
                p.addWis = rs.getInt("add_wis");
                p.addCha = rs.getInt("add_cha");
                p.addHp = rs.getInt("add_hp");
                p.addMp = rs.getInt("add_mp");
                p.addHpr = rs.getInt("add_hpr");
                p.addMpr = rs.getInt("add_mpr");
                p.addSp = rs.getInt("add_sp");
                p.hitMod = rs.getInt("hit_modifier");
                p.dmgMod = rs.getInt("dmg_modifier");
                p.bowHitMod = rs.getInt("bow_hit_modifier");
                p.bowDmgMod = rs.getInt("bow_dmg_modifier");
                p.doubleDmgChance = rs.getInt("double_dmg_chance");
                p.addAc = rs.getInt("add_ac");
                p.mDef = rs.getInt("m_def");
                p.dmgReduction = rs.getInt("dmg_reduction");
                int g = rs.getInt("gfx_id");
                p.gfxId = rs.wasNull() ? null : g;

                _powerMap.put(p.itemId, p);
            }
            _log.info("載入道具附魔卡片共 " + _powerMap.size() + " 種");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_item_enchant_power 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public EnchantPower getPower(int itemId) {
        return _powerMap.get(itemId);
    }
}
