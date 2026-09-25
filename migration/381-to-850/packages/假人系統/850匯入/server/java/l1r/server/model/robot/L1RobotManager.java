package l1r.server.model.robot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 850 原生單一融合假人管理器
 * 負責假人生命週期、地圖過濾、血盟統一管理及定時攻城排程調度。
 */
public class L1RobotManager {
    private static final Logger _log = Logger.getLogger(L1RobotManager.class.getName());
    private static L1RobotManager _instance;

    public static L1RobotManager getInstance() {
        if (_instance == null) {
            _instance = new L1RobotManager();
        }
        return _instance;
    }

    private boolean _systemEnable = true;
    private int _totalCount = 500;
    private int _clanId = 99999;
    private String _clanName = "【王者之師】";
    private final Set<Integer> _excludedMaps = new HashSet<>();
    private final Set<Integer> _includedMaps = new HashSet<>();
    private final Map<Integer, List<String>> _chatMap = new HashMap<>();

    private L1RobotManager() {
        loadConfig();
        loadClan();
        loadChats();
    }

    public void loadConfig() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_robot_config WHERE id = 1");
            rs = pstm.executeQuery();
            if (rs.next()) {
                _systemEnable = rs.getInt("system_enable") != 0;
                _totalCount = rs.getInt("total_count");
                parseMaps(rs.getString("excluded_maps"), _excludedMaps);
                parseMaps(rs.getString("included_maps"), _includedMaps);
            }
            _log.info("假人系統配置載入完成，總開關: " + _systemEnable + "，總配額: " + _totalCount);
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_robot_config 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public void loadClan() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_robot_clan LIMIT 1");
            rs = pstm.executeQuery();
            if (rs.next()) {
                _clanId = rs.getInt("clan_id");
                _clanName = rs.getString("clan_name");
            }
            _log.info("假人專屬管理血盟: " + _clanName + " (ID: " + _clanId + ")");
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_robot_clan 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public void loadChats() {
        _chatMap.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_robot_chat");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String type = rs.getString("chat_type");
                String content = rs.getString("content");
                // 依類型存入
            }
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "載入 w_robot_chat 失敗", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void parseMaps(String csv, Set<Integer> target) {
        target.clear();
        if (csv == null || csv.trim().isEmpty()) return;
        for (String s : csv.split(",")) {
            try {
                target.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException ignored) {}
        }
    }

    public boolean isMapAllowed(int mapId) {
        if (!_systemEnable) return false;
        if (_excludedMaps.contains(mapId)) return false;
        return true;
    }

    public int getClanId() {
        return _clanId;
    }

    public String getClanName() {
        return _clanName;
    }
}