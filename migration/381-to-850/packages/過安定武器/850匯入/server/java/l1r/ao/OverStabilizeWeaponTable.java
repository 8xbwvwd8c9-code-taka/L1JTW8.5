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
 * 850 原生 — 過安定武器額外屬性加成載入器
 */
public class OverStabilizeWeaponTable {
    private static final Logger _log = Logger.getLogger(OverStabilizeWeaponTable.class.getName());
    private static OverStabilizeWeaponTable _instance;

    public static class WeaponBonus {
        public int weaponType;
        public int overLevel;
        public int shortDmg, longDmg, shortHit, longHit, dmg, sp;
    }

    // Key: (weaponType << 8) | overLevel
    private final Map<Integer, WeaponBonus> _bonusMap = new HashMap<>();

    public static OverStabilizeWeaponTable getInstance() {
        if (_instance == null) {
            _instance = new OverStabilizeWeaponTable();
        }
        return _instance;
    }

    private OverStabilizeWeaponTable() {
        load();
    }

    public void load() {
        _bonusMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_over_stabilize_weapon");
            rs = pstm.executeQuery();
            while (rs.next()) {
                WeaponBonus b = new WeaponBonus();
                b.weaponType = rs.getInt("weapon_type");
                b.overLevel = rs.getInt("over_level");
                b.shortDmg = rs.getInt("short_dmg");
                b.longDmg = rs.getInt("long_dmg");
                b.shortHit = rs.getInt("short_hit");
                b.longHit = rs.getInt("long_hit");
                b.dmg = rs.getInt("dmg");
                b.sp = rs.getInt("sp");

                int key = (b.weaponType << 8) | b.overLevel;
                _bonusMap.put(key, b);
            }
            _log.info("載入過安定武器屬性加成共 " + _bonusMap.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_over_stabilize_weapon 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public WeaponBonus getBonus(int weaponType, int overLevel) {
        int key = (weaponType << 8) | overLevel;
        return _bonusMap.get(key);
    }
}