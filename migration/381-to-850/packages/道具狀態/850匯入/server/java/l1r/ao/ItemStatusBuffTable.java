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
 * 850 原生 — 道具時效狀態與BUFF屬性載入器
 */
public class ItemStatusBuffTable {
    private static final Logger _log = Logger.getLogger(ItemStatusBuffTable.class.getName());
    private static ItemStatusBuffTable _instance;

    public static class StatusBuff {
        public int itemId;
        public String name;
        public int durationSec;
        public String note;
        public int buffType;
        public boolean isOverride;
        public int polyId;
        public boolean cancellation;
        public int gfxId;
        public boolean saveGfx;
        public int addStr, addDex, addCon, addInt, addWis, addCha;
        public int addAc, addHp, addMp, addHpr, addMpr;
        public int addDmg, addHit, addBowDmg, addBowHit;
        public int addDmgR, addMagicR, addMr, addSp;
        public int addFire, addWind, addEarth, addWater;
        public int addStun, addStone, addSleep, addFreeze, addSustain, addBlind;
        public String conflictMsg;
        public boolean factionPointDouble;
        public boolean deleteItem;
    }

    private final Map<Integer, StatusBuff> _buffMap = new HashMap<>();

    public static ItemStatusBuffTable getInstance() {
        if (_instance == null) {
            _instance = new ItemStatusBuffTable();
        }
        return _instance;
    }

    private ItemStatusBuffTable() {
        load();
    }

    public void load() {
        _buffMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_item_status_buff");
            rs = pstm.executeQuery();
            while (rs.next()) {
                StatusBuff b = new StatusBuff();
                b.itemId = rs.getInt("item_id");
                b.name = rs.getString("name");
                b.durationSec = rs.getInt("duration_sec");
                b.note = rs.getString("note");
                b.buffType = rs.getInt("buff_type");
                b.isOverride = rs.getInt("is_override") == 1;
                b.polyId = rs.getInt("poly_id");
                b.cancellation = rs.getInt("cancellation") == 1;
                b.gfxId = rs.getInt("gfx_id");
                b.saveGfx = rs.getInt("save_gfx") == 1;
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
                b.addDmgR = rs.getInt("add_dmg_r");
                b.addMagicR = rs.getInt("add_magic_r");
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
                b.conflictMsg = rs.getString("conflict_msg");
                b.factionPointDouble = rs.getInt("faction_point_double") == 1;
                b.deleteItem = rs.getInt("delete_item") == 1;

                _buffMap.put(b.itemId, b);
            }
            _log.info("載入道具時效BUFF共 " + _buffMap.size() + " 種");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_item_status_buff 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public StatusBuff getBuff(int itemId) {
        return _buffMap.get(itemId);
    }
}
