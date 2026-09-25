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
 * 850 原生 — 天M娃娃與卡片合成系統載入器 (w_tm_combine)
 */
public class TmCombineTable {
    private static final Logger _log = Logger.getLogger(TmCombineTable.class.getName());
    private static TmCombineTable _instance;

    public static class CombineRule {
        public int id;
        public int npcId;
        public String note;
        public String action;
        public List<Integer> reqItemIds = new ArrayList<>();
        public int reqCount;
        public int chance;
        public String rewardItemId;
        public String msgLack, msgFail, msgSuccess, broadcast;
        public boolean returnOnFail;
        public int pityRecordId;
        public int pityCount;
    }

    private final Map<String, CombineRule> _actionMap = new HashMap<>();

    public static TmCombineTable getInstance() {
        if (_instance == null) {
            _instance = new TmCombineTable();
        }
        return _instance;
    }

    private TmCombineTable() {
        load();
    }

    public void load() {
        _actionMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_tm_combine");
            rs = pstm.executeQuery();
            while (rs.next()) {
                CombineRule r = new CombineRule();
                r.id = rs.getInt("id");
                r.npcId = rs.getInt("npcid");
                r.note = rs.getString("note");
                r.action = rs.getString("action");
                String req = rs.getString("req_item_ids");
                if (req != null) {
                    for (String s : req.split(",")) {
                        try { r.reqItemIds.add(Integer.parseInt(s.trim())); } catch (Exception ignored) {}
                    }
                }
                r.reqCount = rs.getInt("req_count");
                r.chance = rs.getInt("chance");
                r.rewardItemId = rs.getString("reward_item_id");
                r.msgLack = rs.getString("msg_lack");
                r.msgFail = rs.getString("msg_fail");
                r.msgSuccess = rs.getString("msg_success");
                r.broadcast = rs.getString("broadcast");
                r.returnOnFail = rs.getInt("return_on_fail") != 0;
                r.pityRecordId = rs.getInt("pity_record_id");
                r.pityCount = rs.getInt("pity_count");

                _actionMap.put(r.action.toLowerCase(), r);
            }
            _log.info("載入天M合成規則共 " + _actionMap.size() + " 條");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_tm_combine 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public CombineRule getRule(String action) {
        if (action == null) return null;
        return _actionMap.get(action.toLowerCase());
    }
}