package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生 — 定時鐘點怪物活動施放載入器與排程排定
 */
public class HourlyMonsterSpawnTable {
    private static final Logger _log = Logger.getLogger(HourlyMonsterSpawnTable.class.getName());
    private static HourlyMonsterSpawnTable _instance;

    public static class HourlySpawn {
        public int id;
        public int limitDayOfWeek;
        public int hour;
        public int minute;
        public int npcId;
        public String name;
        public int count;
        public int locx, locy, randomRange, mapId;
        public String msgNotice, msgSpecial;
        public Integer gfxId;
        public boolean isTeleportPortal;
        public int teleX, teleY, teleM;
        public int existTimeSec;
    }

    private final List<HourlySpawn> _spawnList = new ArrayList<>();

    public static HourlyMonsterSpawnTable getInstance() {
        if (_instance == null) {
            _instance = new HourlyMonsterSpawnTable();
        }
        return _instance;
    }

    private HourlyMonsterSpawnTable() {
        load();
    }

    public void load() {
        _spawnList.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_hourly_monster_spawn ORDER BY hour ASC, minute ASC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                HourlySpawn s = new HourlySpawn();
                s.id = rs.getInt("id");
                s.limitDayOfWeek = rs.getInt("limit_day_of_week");
                s.hour = rs.getInt("hour");
                s.minute = rs.getInt("minute");
                s.npcId = rs.getInt("npc_id");
                s.name = rs.getString("name");
                s.count = rs.getInt("count");
                s.locx = rs.getInt("locx");
                s.locy = rs.getInt("locy");
                s.randomRange = rs.getInt("random_range");
                s.mapId = rs.getInt("map_id");
                s.msgNotice = rs.getString("msg_notice");
                s.msgSpecial = rs.getString("msg_special");
                int g = rs.getInt("gfx_id");
                s.gfxId = rs.wasNull() ? null : g;
                s.isTeleportPortal = rs.getInt("is_teleport_portal") == 1;
                s.teleX = rs.getInt("tele_x");
                s.teleY = rs.getInt("tele_y");
                s.teleM = rs.getInt("tele_m");
                s.existTimeSec = rs.getInt("exist_time_sec");

                _spawnList.add(s);
            }
            _log.info("載入定時鐘點怪物活動施放共 " + _spawnList.size() + " 筆");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_hourly_monster_spawn 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public List<HourlySpawn> getSpawnsForTime(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        List<HourlySpawn> match = new ArrayList<>();
        for (HourlySpawn s : _spawnList) {
            if ((s.limitDayOfWeek == -1 || s.limitDayOfWeek == dayOfWeek) &&
                s.hour == hour && s.minute == minute) {
                match.add(s);
            }
        }
        return match;
    }
}
