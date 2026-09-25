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
 * 850 原生 — 角色天賦資料存取與數值應用器
 */
public class CharacterTalentTable {
    private static final Logger _log = Logger.getLogger(CharacterTalentTable.class.getName());
    private static CharacterTalentTable _instance;

    public static class CharacterTalent {
        public int charObjId;
        public int availablePoints;
        public int spentPoints;
        public int strPoint, dexPoint, intPoint;
        public int attackPoint, bowAttackPoint;
        public int hitPoint, bowHitPoint;
        public int spPoint, conPoint, wisPoint, chaPoint;
        public int hpPoint, mpPoint, mrPoint, reductionDmg;
        public int hprPoint, mprPoint, potionPoint, expPoint, acPoint;
        public int pvpDmgPoint, bowPvpDmgPoint;
    }

    private final Map<Integer, CharacterTalent> _talentMap = new HashMap<>();

    public static CharacterTalentTable getInstance() {
        if (_instance == null) {
            _instance = new CharacterTalentTable();
        }
        return _instance;
    }

    private CharacterTalentTable() {
    }

    public CharacterTalent getTalent(int charObjId) {
        if (_talentMap.containsKey(charObjId)) {
            return _talentMap.get(charObjId);
        }
        return loadTalent(charObjId);
    }

    public CharacterTalent loadTalent(int charObjId) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM character_talent WHERE char_obj_id = ?");
            pstm.setInt(1, charObjId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                CharacterTalent t = new CharacterTalent();
                t.charObjId = charObjId;
                t.availablePoints = rs.getInt("available_points");
                t.spentPoints = rs.getInt("spent_points");
                t.strPoint = rs.getInt("str_point");
                t.dexPoint = rs.getInt("dex_point");
                t.intPoint = rs.getInt("int_point");
                t.attackPoint = rs.getInt("attack_point");
                t.bowAttackPoint = rs.getInt("bow_attack_point");
                t.hitPoint = rs.getInt("hit_point");
                t.bowHitPoint = rs.getInt("bow_hit_point");
                t.spPoint = rs.getInt("sp_point");
                _talentMap.put(charObjId, t);
                return t;
            }
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 character_talent 失敗, charId=" + charObjId, e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return null;
    }
}