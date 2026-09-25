package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 全服怪物能力動態倍率提升載入器
 * 由 w_全服怪物提升 表驅動，支援 GM 指令熱重載，動態乘算 HP/MP/MR/AC/DMG。
 */
public class MonsterBoostTable {
    private static final Logger _log = Logger.getLogger(MonsterBoostTable.class.getName());
    private static MonsterBoostTable _instance;

    public static MonsterBoostTable getInstance() {
        if (_instance == null) {
            _instance = new MonsterBoostTable();
        }
        return _instance;
    }

    private int _hpRate = 100;
    private int _mpRate = 100;
    private int _mrRate = 100;
    private int _acAdd = 0;
    private int _dmgRate = 100;

    private MonsterBoostTable() {
        load();
    }

    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_全服怪物提升 WHERE id = 1");
            rs = pstm.executeQuery();
            if (rs.next()) {
                _hpRate = Math.max(1, rs.getInt("hp_rate"));
                _mpRate = Math.max(1, rs.getInt("mp_rate"));
                _mrRate = Math.max(1, rs.getInt("mr_rate"));
                _acAdd = rs.getInt("ac_add");
                _dmgRate = Math.max(1, rs.getInt("dmg_rate"));
            }
            _log.info(String.format("全服怪物提升載入完成: HP=%d%%, MP=%d%%, MR=%d%%, AC_ADD=%d, DMG=%d%%",
                    _hpRate, _mpRate, _mrRate, _acAdd, _dmgRate));
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_全服怪物提升 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public int getHpRate() { return _hpRate; }
    public int getMpRate() { return _mpRate; }
    public int getMrRate() { return _mrRate; }
    public int getAcAdd() { return _acAdd; }
    public int getDmgRate() { return _dmgRate; }
}