package auto.hunt;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public final class AutoHuntBossIndexLoader {
    private AutoHuntBossIndexLoader() {
    }

    public static void load(AutoHuntBossIndex index) throws SQLException {
        if (index == null) throw new NullPointerException("index");
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Set<Integer> ids = new HashSet<Integer>();
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT DISTINCT npc_id FROM spawnlist_boss");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("npc_id"));
            }
        } finally {
            j.a(rs, pstm, con);
        }
        index.replaceAll(ids);
    }
}
