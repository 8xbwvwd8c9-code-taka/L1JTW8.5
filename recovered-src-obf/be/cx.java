/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class cx
extends eu {
    private static final Logger a = Logger.getLogger(cx.class.getName());

    public cx(int clanId) {
        this.c(121);
        this.c(117);
        ArrayList<a> list = this.f(clanId);
        this.a(list.size());
        for (a record : list) {
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
                    con = b.a().b();
                    pstm = con.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=? AND record_time < ?");
                    pstm.setInt(1, clanid);
                    pstm.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 259200000L));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    private ArrayList<a> f(int clanid) {
        ArrayList<a> result;
        block6: {
            this.e(clanid);
            result = new ArrayList<a>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM clan_warehouse_history WHERE clan_id=? ORDER BY id DESC");
                    pstm.setInt(1, clanid);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a record = new a();
                        record.a = rs.getString("char_name");
                        record.b = rs.getInt("type");
                        record.c = rs.getString("item_name");
                        record.d = rs.getInt("item_count");
                        record.e = rs.getTimestamp("record_time");
                        result.add(record);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
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

    private class a {
        public String a;
        public int b;
        public String c;
        public int d;
        public Timestamp e;

        private a() {
        }
    }
}

