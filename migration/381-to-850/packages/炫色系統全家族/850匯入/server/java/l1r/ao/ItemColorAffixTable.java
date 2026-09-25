package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 裝備隨機炫色詞綴與部位能力載入器
 */
public class ItemColorAffixTable {
    private static final Logger _log = Logger.getLogger(ItemColorAffixTable.class.getName());
    private static ItemColorAffixTable _instance;

    public static class ColorBonus {
        public int id;
        public String colorCode;
        public String title;
        public String itemType;
        public String description;
        public int chance;
        public int minDmg, maxDmg, hitModifier, extraDmg;
        public int addStr, addCon, addDex, addInt, addWis, addCha;
        public int addHp, addMp, reductionDmg, addHpr, addMpr, addSp, addMr;
        public int minDrainHp, maxDrainHp, drainHpChance;
        public int minDrainMp, maxDrainMp, drainMpChance;
        public int magicChance, magicGfxId, magicDmg;
        public int physBlock, magicBlock, pvpDmg, pvpReduction, potionHeal;
        public String broadcastMsg, displayText;
    }

    private final List<ColorBonus> _bonusList = new ArrayList<>();
    private final Map<String, String> _nameMap = new HashMap<>();

    public static ItemColorAffixTable getInstance() {
        if (_instance == null) {
            _instance = new ItemColorAffixTable();
        }
        return _instance;
    }

    private ItemColorAffixTable() {
        load();
    }

    public void load() {
        _bonusList.clear();
        _nameMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_color_bonus");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ColorBonus b = new ColorBonus();
                b.id = rs.getInt("id");
                b.colorCode = rs.getString("color_code");
                b.title = rs.getString("title");
                b.itemType = rs.getString("item_type");
                b.description = rs.getString("description");
                b.chance = rs.getInt("chance");
                b.minDmg = rs.getInt("min_dmg");
                b.maxDmg = rs.getInt("max_dmg");
                b.hitModifier = rs.getInt("hit_modifier");
                b.extraDmg = rs.getInt("extra_dmg");
                b.addStr = rs.getInt("add_str");
                b.addCon = rs.getInt("add_con");
                b.addDex = rs.getInt("add_dex");
                b.addInt = rs.getInt("add_int");
                b.addWis = rs.getInt("add_wis");
                b.addCha = rs.getInt("add_cha");
                b.addHp = rs.getInt("add_hp");
                b.addMp = rs.getInt("add_mp");
                b.reductionDmg = rs.getInt("reduction_dmg");
                b.addHpr = rs.getInt("add_hpr");
                b.addMpr = rs.getInt("add_mpr");
                b.addSp = rs.getInt("add_sp");
                b.addMr = rs.getInt("add_mr");
                b.minDrainHp = rs.getInt("min_drain_hp");
                b.maxDrainHp = rs.getInt("max_drain_hp");
                b.drainHpChance = rs.getInt("drain_hp_chance");
                b.minDrainMp = rs.getInt("min_drain_mp");
                b.maxDrainMp = rs.getInt("max_drain_mp");
                b.drainMpChance = rs.getInt("drain_mp_chance");
                b.magicChance = rs.getInt("magic_chance");
                b.magicGfxId = rs.getInt("magic_gfxid");
                b.magicDmg = rs.getInt("magic_dmg");
                b.physBlock = rs.getInt("phys_block");
                b.magicBlock = rs.getInt("magic_block");
                b.pvpDmg = rs.getInt("pvp_dmg");
                b.pvpReduction = rs.getInt("pvp_reduction");
                b.potionHeal = rs.getInt("potion_heal");
                b.broadcastMsg = rs.getString("broadcast_msg");
                b.displayText = rs.getString("display_text");

                _bonusList.add(b);
            }
            SQLUtil.close(rs);
            SQLUtil.close(pstm);

            pstm = con.prepareStatement("SELECT * FROM w_item_color_name");
            rs = pstm.executeQuery();
            while (rs.next()) {
                _nameMap.put(rs.getString("type_name"), rs.getString("note"));
            }

            _log.info("載入裝備炫色詞綴加成共 " + _bonusList.size() + " 種, 名稱 " + _nameMap.size() + " 類");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入炫色詞綴表失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
