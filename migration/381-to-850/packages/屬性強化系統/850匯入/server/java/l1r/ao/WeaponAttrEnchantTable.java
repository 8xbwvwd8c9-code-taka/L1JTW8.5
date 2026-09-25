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
 * 850 原生 — 武器屬性強化能力與攻擊觸發載入器
 */
public class WeaponAttrEnchantTable {
    private static final Logger _log = Logger.getLogger(WeaponAttrEnchantTable.class.getName());
    private static WeaponAttrEnchantTable _instance;

    public static class AttrEnchant {
        public int id;
        public String name;
        public int stage;
        public int chance;
        public int probability;
        public int gfxId;
        public int attrType;
        public int fixDamage, randomDamage;
        public int drainHpFixed, drainMpFixed;
        public double typeBind, typeDrainHp, typeDrainMp, typeDmgUp;
        public int typeRange;
        public double typeRangeDmg, typeLightDmg;
        public int typeSkill1, typeSkill2, typeSkill3, typeStun;
        public double typeSkillTime;
        public String typePolyList;
        public int typeRemoveWeapon, typeRemoveDoll, typeRemoveArmor;
        public String worldChat;
    }

    // Key: (attrType << 16) | stage
    private final Map<Integer, AttrEnchant> _stageMap = new HashMap<>();

    public static WeaponAttrEnchantTable getInstance() {
        if (_instance == null) {
            _instance = new WeaponAttrEnchantTable();
        }
        return _instance;
    }

    private WeaponAttrEnchantTable() {
        load();
    }

    public void load() {
        _stageMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_weapon_attr_enchant");
            rs = pstm.executeQuery();
            while (rs.next()) {
                AttrEnchant a = new AttrEnchant();
                a.id = rs.getInt("id");
                a.name = rs.getString("name");
                a.stage = rs.getInt("stage");
                a.chance = rs.getInt("chance");
                a.probability = rs.getInt("probability");
                a.gfxId = rs.getInt("gfx_id");
                a.attrType = rs.getInt("attr_type");
                a.fixDamage = rs.getInt("fix_damage");
                a.randomDamage = rs.getInt("random_damage");
                a.drainHpFixed = rs.getInt("drain_hp_fixed");
                a.drainMpFixed = rs.getInt("drain_mp_fixed");
                a.typeBind = rs.getDouble("type_bind");
                a.typeDrainHp = rs.getDouble("type_drain_hp");
                a.typeDrainMp = rs.getDouble("type_drain_mp");
                a.typeDmgUp = rs.getDouble("type_dmgup");
                a.typeRange = rs.getInt("type_range");
                a.typeRangeDmg = rs.getDouble("type_range_dmg");
                a.typeLightDmg = rs.getDouble("type_light_dmg");
                a.typeSkill1 = rs.getInt("type_skill_1");
                a.typeSkill2 = rs.getInt("type_skill_2");
                a.typeSkill3 = rs.getInt("type_skill_3");
                a.typeStun = rs.getInt("type_stun");
                a.typeSkillTime = rs.getDouble("type_skill_time");
                a.typePolyList = rs.getString("type_poly_list");
                a.typeRemoveWeapon = rs.getInt("type_remove_weapon");
                a.typeRemoveDoll = rs.getInt("type_remove_doll");
                a.typeRemoveArmor = rs.getInt("type_remove_armor");
                a.worldChat = rs.getString("world_chat");

                int key = (a.attrType << 16) | a.stage;
                _stageMap.put(key, a);
            }
            _log.info("載入武器屬性強化階段共 " + _stageMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_weapon_attr_enchant 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public AttrEnchant getEnchant(int attrType, int stage) {
        int key = (attrType << 16) | stage;
        return _stageMap.get(key);
    }
}
