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
 * 850 原生 — 過安定防具額外屬性加成載入器
 */
public class OverStabilizeArmorTable {
    private static final Logger _log = Logger.getLogger(OverStabilizeArmorTable.class.getName());
    private static OverStabilizeArmorTable _instance;

    public static class ArmorBonus {
        public int overLevel;
        public int dmgReduction;
        public int magicDmgReduction;
        public int hp;
        public int mp;
    }

    private final Map<Integer, ArmorBonus> _bonusMap = new HashMap<>();

    public static OverStabilizeArmorTable getInstance() {
        if (_instance == null) {
            _instance = new OverStabilizeArmorTable();
        }
        return _instance;
    }

    private OverStabilizeArmorTable() {
        load();
    }

    public void load() {
        _bonusMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_over_stabilize_armor");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ArmorBonus b = new ArmorBonus();
                b.overLevel = rs.getInt("over_level");
                b.dmgReduction = rs.getInt("dmg_reduction");
                b.magicDmgReduction = rs.getInt("magic_dmg_reduction");
                b.hp = rs.getInt("hp");
                b.mp = rs.getInt("mp");

                _bonusMap.put(b.overLevel, b);
            }
            _log.info("載入過安定防具屬性加成共 " + _bonusMap.size() + " 階級");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_over_stabilize_armor 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public ArmorBonus getBonus(int overLevel) {
        return _bonusMap.get(overLevel);
    }
}