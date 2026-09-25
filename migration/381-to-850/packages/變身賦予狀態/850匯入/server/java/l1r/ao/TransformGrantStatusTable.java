package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 權威核心 — 變身賦予狀態資料載入器
 * 涵蓋主表 w_transform_grant_status 與道具觸發表 w_transform_grant_status_item。
 */
public class TransformGrantStatusTable {
    private static final Logger _log = Logger.getLogger(TransformGrantStatusTable.class.getName());
    private static TransformGrantStatusTable _instance;

    private final Map<Integer, TransformGrantStatus> _statusByGfx = new HashMap<>();
    private final Map<Integer, TransformGrantStatusItem> _itemStatusByGfx = new HashMap<>();

    public static class TransformGrantStatus {
        public int id;
        public String note;
        public int gfxId;
        public boolean deadExp;
        public boolean cancellation;
        public int str, dex, con, intStat, wis, cha, ac;
        public int maxHp, maxMp, hpr, mpr, dmg, bowDmg, hit, bowHit;
        public int physReduction, magicReduction, mr, sp, magicHit;
        public int fireRes, windRes, earthRes, waterRes;
        public double addExp;
        public int potionHeal, pvpDmg, pvpDmgReduction;
        public int registStun, registStone, registSleep, registFreeze, registSustain, registBlind;
    }

    public static class TransformGrantStatusItem {
        public int id;
        public String note;
        public int gfxId;
        public boolean deadExp;
        public boolean cancellation;
        public int str, dex, con, intStat, wis, cha, ac;
        public int maxHp, maxMp, hpr, mpr, dmg, bowDmg, hit, bowHit;
        public int physReduction, magicReduction, mr, sp, magicHit;
        public int fireRes, windRes, earthRes, waterRes;
        public double addExp;
        public int potionHeal, pvpDmg, pvpDmgReduction;
        public int registStun, registStone, registSleep, registFreeze, registSustain, registBlind;
    }

    public static TransformGrantStatusTable getInstance() {
        if (_instance == null) {
            _instance = new TransformGrantStatusTable();
        }
        return _instance;
    }

    private TransformGrantStatusTable() {
        load();
    }

    public void load() {
        _statusByGfx.clear();
        _itemStatusByGfx.clear();
        loadStatus();
        loadItemStatus();
    }

    private void loadStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_transform_grant_status");
            rs = pstm.executeQuery();
            while (rs.next()) {
                TransformGrantStatus status = new TransformGrantStatus();
                status.id = rs.getInt("id");
                status.note = rs.getString("note");
                status.gfxId = rs.getInt("gfx_id");
                status.deadExp = rs.getInt("dead_exp") != 0;
                status.cancellation = rs.getInt("cancellation") != 0;
                status.str = rs.getInt("add_str");
                status.dex = rs.getInt("add_dex");
                status.con = rs.getInt("add_con");
                status.intStat = rs.getInt("add_int");
                status.wis = rs.getInt("add_wis");
                status.cha = rs.getInt("add_cha");
                status.ac = rs.getInt("add_ac");
                status.maxHp = rs.getInt("add_max_hp");
                status.maxMp = rs.getInt("add_max_mp");
                status.hpr = rs.getInt("add_hpr");
                status.mpr = rs.getInt("add_mpr");
                status.dmg = rs.getInt("add_dmg");
                status.bowDmg = rs.getInt("add_bow_dmg");
                status.hit = rs.getInt("add_hit");
                status.bowHit = rs.getInt("add_bow_hit");
                status.physReduction = rs.getInt("reduction_dmg");
                status.magicReduction = rs.getInt("reduction_magic_dmg");
                status.mr = rs.getInt("add_mr");
                status.sp = rs.getInt("add_sp");
                status.magicHit = rs.getInt("add_magic_hit");
                status.fireRes = rs.getInt("add_fire");
                status.windRes = rs.getInt("add_wind");
                status.earthRes = rs.getInt("add_earth");
                status.waterRes = rs.getInt("add_water");
                status.addExp = rs.getDouble("add_exp");
                status.potionHeal = rs.getInt("potion_heal");
                status.pvpDmg = rs.getInt("pvp_dmg");
                status.pvpDmgReduction = rs.getInt("pvp_dmg_reduction");
                status.registStun = rs.getInt("regist_stun");
                status.registStone = rs.getInt("regist_stone");
                status.registSleep = rs.getInt("regist_sleep");
                status.registFreeze = rs.getInt("regist_freeze");
                status.registSustain = rs.getInt("regist_sustain");
                status.registBlind = rs.getInt("regist_blind");

                _statusByGfx.put(status.gfxId, status);
            }
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_grant_status 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void loadItemStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_transform_grant_status_item");
            rs = pstm.executeQuery();
            while (rs.next()) {
                TransformGrantStatusItem status = new TransformGrantStatusItem();
                status.id = rs.getInt("id");
                status.note = rs.getString("note");
                status.gfxId = rs.getInt("gfx_id");
                status.deadExp = rs.getInt("dead_exp") != 0;
                status.cancellation = rs.getInt("cancellation") != 0;
                status.str = rs.getInt("add_str");
                status.dex = rs.getInt("add_dex");
                status.con = rs.getInt("add_con");
                status.intStat = rs.getInt("add_int");
                status.wis = rs.getInt("add_wis");
                status.cha = rs.getInt("add_cha");
                status.ac = rs.getInt("add_ac");
                status.maxHp = rs.getInt("add_max_hp");
                status.maxMp = rs.getInt("add_max_mp");
                status.hpr = rs.getInt("add_hpr");
                status.mpr = rs.getInt("add_mpr");
                status.dmg = rs.getInt("add_dmg");
                status.bowDmg = rs.getInt("add_bow_dmg");
                status.hit = rs.getInt("add_hit");
                status.bowHit = rs.getInt("add_bow_hit");
                status.physReduction = rs.getInt("reduction_dmg");
                status.magicReduction = rs.getInt("reduction_magic_dmg");
                status.mr = rs.getInt("add_mr");
                status.sp = rs.getInt("add_sp");
                status.magicHit = rs.getInt("add_magic_hit");
                status.fireRes = rs.getInt("add_fire");
                status.windRes = rs.getInt("add_wind");
                status.earthRes = rs.getInt("add_earth");
                status.waterRes = rs.getInt("add_water");
                status.addExp = rs.getDouble("add_exp");
                status.potionHeal = rs.getInt("potion_heal");
                status.pvpDmg = rs.getInt("pvp_dmg");
                status.pvpDmgReduction = rs.getInt("pvp_dmg_reduction");
                status.registStun = rs.getInt("regist_stun");
                status.registStone = rs.getInt("regist_stone");
                status.registSleep = rs.getInt("regist_sleep");
                status.registFreeze = rs.getInt("regist_freeze");
                status.registSustain = rs.getInt("regist_sustain");
                status.registBlind = rs.getInt("regist_blind");

                _itemStatusByGfx.put(status.gfxId, status);
            }
            _log.info("載入變身賦予狀態道具資料共 " + _itemStatusByGfx.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_grant_status_item 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public TransformGrantStatus getStatusByGfx(int gfxId) {
        return _statusByGfx.get(gfxId);
    }

    public TransformGrantStatusItem getItemStatusByGfx(int gfxId) {
        return _itemStatusByGfx.get(gfxId);
    }

    public Map<Integer, TransformGrantStatusItem> getAllItemStatuses() {
        return Collections.unmodifiableMap(_itemStatusByGfx);
    }
}