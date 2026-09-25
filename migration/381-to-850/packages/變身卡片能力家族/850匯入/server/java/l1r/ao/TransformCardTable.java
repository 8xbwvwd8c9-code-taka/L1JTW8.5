package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 權威核心 — 變身卡片與套卡能力資料載入器
 * 依 850 原生架構整合：登入卡片與套卡共用單一 CollectionOwner 與 idempotent 數值重算。
 */
public class TransformCardTable {
    private static final Logger _log = Logger.getLogger(TransformCardTable.class.getName());
    private static TransformCardTable _instance;

    private final Map<Integer, TransformCard> _cardsByQuest = new HashMap<>();
    private final Map<String, TransformCard> _cardsByAction = new HashMap<>();
    private final Map<Integer, TransformCardSet> _sets = new HashMap<>();
    private final Map<Integer, List<Integer>> _setRequirements = new HashMap<>();

    public static class TransformCard {
        public int questId;
        public String displayName;
        public String unlockDesc;
        public String actionCmd;
        public int polyId;
        public int polyDurationSec;
        public int consumeItemId;
        public int consumeItemCnt;
        public int str, dex, con, intStat, wis, cha, ac;
        public int maxHp, maxMp, hpr, mpr, dmg, bowDmg, hit, bowHit;
        public int physReduction, magicReduction, mr, sp, magicHit;
        public int fireRes, windRes, earthRes, waterRes;
    }

    public static class TransformCardSet {
        public int setId;
        public String setName;
        public int setQuest;
        public int str, dex, con, intStat, wis, cha, ac;
        public int maxHp, maxMp, hpr, mpr, dmg, bowDmg, hit, bowHit;
        public int physReduction, magicReduction, mr, sp, magicHit;
        public int fireRes, windRes, earthRes, waterRes;
    }

    public static TransformCardTable getInstance() {
        if (_instance == null) {
            _instance = new TransformCardTable();
        }
        return _instance;
    }

    private TransformCardTable() {
        load();
    }

    public void load() {
        _cardsByQuest.clear();
        _cardsByAction.clear();
        _sets.clear();
        _setRequirements.clear();

        loadCards();
        loadSets();
        loadSetRequirements();
    }

    private void loadCards() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_transform_card_login");
            rs = pstm.executeQuery();
            while (rs.next()) {
                TransformCard card = new TransformCard();
                card.questId = rs.getInt("quest_id");
                card.displayName = rs.getString("display_name");
                card.unlockDesc = rs.getString("unlock_desc");
                card.actionCmd = rs.getString("action_cmd");
                card.polyId = rs.getInt("poly_id");
                card.polyDurationSec = rs.getInt("poly_duration_sec");
                card.consumeItemId = rs.getInt("consume_item_id");
                card.consumeItemCnt = rs.getInt("consume_item_cnt");

                card.str = rs.getInt("add_str");
                card.dex = rs.getInt("add_dex");
                card.con = rs.getInt("add_con");
                card.intStat = rs.getInt("add_int");
                card.wis = rs.getInt("add_wis");
                card.cha = rs.getInt("add_cha");
                card.ac = rs.getInt("add_ac");
                card.maxHp = rs.getInt("add_max_hp");
                card.maxMp = rs.getInt("add_max_mp");
                card.hpr = rs.getInt("add_hpr");
                card.mpr = rs.getInt("add_mpr");
                card.dmg = rs.getInt("add_dmg");
                card.bowDmg = rs.getInt("add_bow_dmg");
                card.hit = rs.getInt("add_hit");
                card.bowHit = rs.getInt("add_bow_hit");
                card.physReduction = rs.getInt("reduction_dmg");
                card.magicReduction = rs.getInt("reduction_magic");
                card.mr = rs.getInt("add_mr");
                card.sp = rs.getInt("add_sp");
                card.magicHit = rs.getInt("add_magic_hit");
                card.fireRes = rs.getInt("add_fire");
                card.windRes = rs.getInt("add_wind");
                card.earthRes = rs.getInt("add_earth");
                card.waterRes = rs.getInt("add_water");

                _cardsByQuest.put(card.questId, card);
                if (card.actionCmd != null && !card.actionCmd.isEmpty()) {
                    _cardsByAction.put(card.actionCmd.toLowerCase(), card);
                }
            }
            _log.info("載入變身卡片登入資料共 " + _cardsByQuest.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_card_login 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void loadSets() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_transform_card_set");
            rs = pstm.executeQuery();
            while (rs.next()) {
                TransformCardSet set = new TransformCardSet();
                set.setId = rs.getInt("set_id");
                set.setName = rs.getString("set_name");
                set.setQuest = rs.getInt("set_quest");

                set.str = rs.getInt("add_str");
                set.dex = rs.getInt("add_dex");
                set.con = rs.getInt("add_con");
                set.intStat = rs.getInt("add_int");
                set.wis = rs.getInt("add_wis");
                set.cha = rs.getInt("add_cha");
                set.ac = rs.getInt("add_ac");
                set.maxHp = rs.getInt("add_max_hp");
                set.maxMp = rs.getInt("add_max_mp");
                set.hpr = rs.getInt("add_hpr");
                set.mpr = rs.getInt("add_mpr");
                set.dmg = rs.getInt("add_dmg");
                set.bowDmg = rs.getInt("add_bow_dmg");
                set.hit = rs.getInt("add_hit");
                set.bowHit = rs.getInt("add_bow_hit");
                set.physReduction = rs.getInt("reduction_dmg");
                set.magicReduction = rs.getInt("reduction_magic");
                set.mr = rs.getInt("add_mr");
                set.sp = rs.getInt("add_sp");
                set.magicHit = rs.getInt("add_magic_hit");
                set.fireRes = rs.getInt("add_fire");
                set.windRes = rs.getInt("add_wind");
                set.earthRes = rs.getInt("add_earth");
                set.waterRes = rs.getInt("add_water");

                _sets.put(set.setId, set);
            }
            _log.info("載入變身卡片套卡資料共 " + _sets.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_card_set 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void loadSetRequirements() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT set_id, require_quest FROM w_transform_card_set_require ORDER BY set_id, require_quest");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int setId = rs.getInt("set_id");
                int reqQuest = rs.getInt("require_quest");
                _setRequirements.computeIfAbsent(setId, k -> new ArrayList<>()).add(reqQuest);
            }
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_transform_card_set_require 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public TransformCard getCardByQuest(int questId) {
        return _cardsByQuest.get(questId);
    }

    public TransformCard getCardByAction(String action) {
        if (action == null) return null;
        return _cardsByAction.get(action.toLowerCase());
    }

    public Map<Integer, TransformCard> getAllCards() {
        return Collections.unmodifiableMap(_cardsByQuest);
    }

    public Map<Integer, TransformCardSet> getAllSets() {
        return Collections.unmodifiableMap(_sets);
    }

    public List<Integer> getSetRequirements(int setId) {
        return _setRequirements.getOrDefault(setId, Collections.emptyList());
    }
}