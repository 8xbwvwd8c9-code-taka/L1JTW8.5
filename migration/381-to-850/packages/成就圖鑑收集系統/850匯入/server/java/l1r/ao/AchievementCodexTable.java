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
 * 850 原生 — 成就圖鑑收集要求與屬性獎勵載入器 (w_achievement_codex + w_achievement_reward)
 */
public class AchievementCodexTable {
    private static final Logger _log = Logger.getLogger(AchievementCodexTable.class.getName());
    private static AchievementCodexTable _instance;

    public static class CodexEntry {
        public int id;
        public int npcId;
        public String action;
        public String note;
        public int checkLevel;
        public int checkClass;
        public String materials;
        public String materialsNote;
        public String materialsCount;
        public String materialsEnchants;
        public int questId;
        public String abilityDesc;
    }

    public static class CodexReward {
        public int questId;
        public String note1;
        public String note;
        public int attack, bowAttack, hit, bowHit, sp;
        public int str, dex, intel, con, cha, wis;
        public int hp, mp, mr, reductionDmg, hpr, mpr, hpPotion;
        public double exp;
        public int ac, weight;
        public int registStun, registStone, registSleep, registFreeze, registSustain, registBlind;
        public int pvpDmg, pvpHit, pvpBowDmg, pvpBowHit, pvpDmgR, pvpMagicR;
    }

    private final Map<Integer, CodexEntry> _entryByQuest = new HashMap<>();
    private final Map<String, CodexEntry> _entryByAction = new HashMap<>();
    private final Map<Integer, CodexReward> _rewardByQuest = new HashMap<>();

    public static AchievementCodexTable getInstance() {
        if (_instance == null) {
            _instance = new AchievementCodexTable();
        }
        return _instance;
    }

    private AchievementCodexTable() {
        load();
    }

    public void load() {
        _entryByQuest.clear();
        _entryByAction.clear();
        _rewardByQuest.clear();

        loadEntries();
        loadRewards();
    }

    private void loadEntries() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_achievement_codex");
            rs = pstm.executeQuery();
            while (rs.next()) {
                CodexEntry e = new CodexEntry();
                e.id = rs.getInt("id");
                e.npcId = rs.getInt("npcid");
                e.action = rs.getString("action");
                e.note = rs.getString("note");
                e.checkLevel = rs.getInt("check_level");
                e.checkClass = rs.getInt("check_class");
                e.materials = rs.getString("materials");
                e.materialsNote = rs.getString("materials_note");
                e.materialsCount = rs.getString("materials_count");
                e.materialsEnchants = rs.getString("materials_enchants");
                e.questId = rs.getInt("quest_id");
                e.abilityDesc = rs.getString("ability_desc");

                _entryByQuest.put(e.questId, e);
                _entryByAction.put(e.action.toLowerCase(), e);
            }
            _log.info("載入成就圖鑑收集項目共 " + _entryByQuest.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_achievement_codex 失敗", e);
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
            pstm = con.prepareStatement("SELECT * FROM w_achievement_reward");
            rs = pstm.executeQuery();
            while (rs.next()) {
                CodexReward r = new CodexReward();
                r.questId = rs.getInt("quest_id");
                r.note1 = rs.getString("note1");
                r.note = rs.getString("note");
                r.attack = rs.getInt("attack");
                r.bowAttack = rs.getInt("bow_attack");
                r.hit = rs.getInt("hit");
                r.bowHit = rs.getInt("bow_hit");
                r.sp = rs.getInt("sp");
                r.str = rs.getInt("str");
                r.dex = rs.getInt("dex");
                r.intel = rs.getInt("intel");
                r.con = rs.getInt("con");
                r.cha = rs.getInt("cha");
                r.wis = rs.getInt("wis");
                r.hp = rs.getInt("hp");
                r.mp = rs.getInt("mp");
                r.mr = rs.getInt("mr");
                r.reductionDmg = rs.getInt("reduction_dmg");
                r.hpr = rs.getInt("hpr");
                r.mpr = rs.getInt("mpr");
                r.hpPotion = rs.getInt("hp_potion");
                r.exp = rs.getDouble("exp");
                r.ac = rs.getInt("ac");
                r.weight = rs.getInt("weight");
                r.registStun = rs.getInt("regist_stun");
                r.registStone = rs.getInt("regist_stone");
                r.registSleep = rs.getInt("regist_sleep");
                r.registFreeze = rs.getInt("regist_freeze");
                r.registSustain = rs.getInt("regist_sustain");
                r.registBlind = rs.getInt("regist_blind");
                r.pvpDmg = rs.getInt("pvp_dmg");
                r.pvpHit = rs.getInt("pvp_hit");
                r.pvpBowDmg = rs.getInt("pvp_bow_dmg");
                r.pvpBowHit = rs.getInt("pvp_bow_hit");
                r.pvpDmgR = rs.getInt("pvp_dmg_r");
                r.pvpMagicR = rs.getInt("pvp_magic_r");

                _rewardByQuest.put(r.questId, r);
            }
            _log.info("載入成就圖鑑屬性獎勵共 " + _rewardByQuest.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_achievement_reward 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public CodexReward getReward(int questId) {
        return _rewardByQuest.get(questId);
    }

    public CodexEntry getEntryByAction(String action) {
        if (action == null) return null;
        return _entryByAction.get(action.toLowerCase());
    }
}