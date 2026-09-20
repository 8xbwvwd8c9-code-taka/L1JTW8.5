/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.p;
import ao.q;
import ao.w;
import ap.u;
import aq.i;
import be.cm;
import be.cw;
import be.dc;
import be.ds;
import bi.j;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bp
extends cv {
    private static final Logger a = Logger.getLogger(bp.class.getName());

    public bp(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        if (pc.aF() > 0) {
            i clan = q.a().a(pc.aF());
            pc.a(new dc(333, pc));
            pc.a(new dc(325, pc));
            pc.a(new cw(clan.e()));
            pc.a(new cw(this.b(clan.e())));
            Object[] memberList = clan.b().toArray(new u[clan.b().size()]);
            pc.a(new cm(171, memberList));
        } else {
            pc.a(new ds(1064));
        }
    }

    private ArrayList<a> b(int clanid) {
        ArrayList<a> list;
        block6: {
            list = new ArrayList<a>();
            i clan = q.a().a(clanid);
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters WHERE char_name=?");
                    for (String charName : clan.p()) {
                        pstm.setString(1, charName);
                        rs = pstm.executeQuery();
                        if (!rs.next()) continue;
                        a data = new a();
                        data.a = rs.getString("char_name");
                        data.b = rs.getInt("ClanRank");
                        data.c = w.c(rs.getInt("Exp"));
                        data.d = rs.getInt("objid");
                        data.e = rs.getInt("Type");
                        p.a().a(data);
                        list.add(data);
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
        return list;
    }

    @Override
    public String a() {
        return "C_Pledge";
    }

    public class a {
        public String a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public String g = "";
    }
}

