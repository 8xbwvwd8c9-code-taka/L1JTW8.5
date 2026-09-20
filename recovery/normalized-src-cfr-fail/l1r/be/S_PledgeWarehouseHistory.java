/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.be.ServerBasePacket;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class S_PledgeWarehouseHistory
extends ServerBasePacket {
    private static final Logger a = Logger.getLogger(S_PledgeWarehouseHistory.class.getName());

    public S_PledgeWarehouseHistory(int clanId) {
        this.c(121);
        this.c(117);
        ArrayList<L1R_a> list = this.f(clanId);
        this.a(list.size());
        for (L1R_a record : list) {
            this.a(record.a);
            this.c(record.b);
            this.a(record.c);
            this.a(record.d);
            this.a((int)((System.currentTimeMillis() - record.e.getTime()) / 60000L));
        }
    }

    private void e(int clanid) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=? AND record_time < ?");
                    pstm.setInt(1, clanid);
                    pstm.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 259200000L));
                    pstm.execute();
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(pstm);
                    SQLUtil.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(pstm);
                SQLUtil.a(con);
                throw throwable;
            }
            SQLUtil.a(pstm);
            SQLUtil.a(con);
        }
    }

    private ArrayList<L1R_a> f(int clanid) {
        ArrayList<L1R_a> result;
        block6: {
            this.e(clanid);
            result = new ArrayList<L1R_a>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("SELECT * FROM clan_warehouse_history WHERE clan_id=? ORDER BY id DESC");
                    pstm.setInt(1, clanid);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        L1R_a record = new L1R_a();
                        record.a = rs.getString("char_name");
                        record.b = rs.getInt("type");
                        record.c = rs.getString("item_name");
                        record.d = rs.getInt("item_count");
                        record.e = rs.getTimestamp("record_time");
                        result.add(record);
                    }
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(rs, pstm, con);
                throw throwable;
            }
            SQLUtil.a(rs, pstm, con);
        }
        return result;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PledgeWarehouseHistory";
    }

    private class L1R_a {
        public String a;
        public int b;
        public String c;
        public int d;
        public Timestamp e;

        private L1R_a() {
        }
    }
}
