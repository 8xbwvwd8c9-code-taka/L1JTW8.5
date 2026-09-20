/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import be.dc;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bf {
    private static final Logger a = Logger.getLogger(bf.class.getName());
    private static bf b;
    private final ArrayList<a> c;

    public static bf a() {
        if (b == null) {
            b = new bf();
        }
        return b;
    }

    private bf() {
        block6: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM soul_tower");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a rank = new a();
                        rank.a = rs.getString("name");
                        rank.b = rs.getInt("class");
                        rank.c = rs.getInt("time");
                        rank.d = rs.getDate("date").getTime();
                        this.c.add(rank);
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
    }

    public void a(u pc) {
        pc.a(new dc(this.c.toArray(new a[0])));
    }

    public void a(u pc, int time) {
        block8: {
            boolean isBestter = false;
            for (a rank : this.c) {
                if (time >= rank.c && this.c.size() >= 10) continue;
                isBestter = true;
                break;
            }
            if (isBestter) {
                a rank;
                rank = new a();
                rank.a = pc.et();
                rank.b = pc.ay();
                rank.c = time;
                Date now = new Date();
                java.sql.Date sqlDate = new java.sql.Date(now.getTime());
                rank.d = sqlDate.getTime();
                this.c.add(rank);
                Collections.sort(this.c, new Comparator<a>(){

                    public int a(a r1, a r2) {
                        return r1.c - r2.c;
                    }

                    @Override
                    public /* synthetic */ int compare(Object object, Object object2) {
                        return this.a((a)object, (a)object2);
                    }
                });
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    try {
                        con = l1j.server.b.a().b();
                        pstm = con.prepareStatement("DELETE FROM soul_tower WHERE rank >0");
                        pstm.execute();
                        int i2 = 0;
                        while (i2 < this.c.size() && i2 < 10) {
                            a str = this.c.get(i2);
                            pstm = con.prepareStatement("INSERT INTO soul_tower  SET rank=?,name=?,class=?,time=?,date=?");
                            pstm.setInt(1, i2 + 1);
                            pstm.setString(2, str.a);
                            pstm.setInt(3, str.b);
                            pstm.setInt(4, str.c);
                            Date utilDate = new Date();
                            utilDate.setTime(str.d);
                            pstm.setDate(5, new java.sql.Date(utilDate.getTime()));
                            pstm.execute();
                            ++i2;
                        }
                    }
                    catch (SQLException e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(pstm);
                        j.a(con);
                        break block8;
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
    }

    public class a {
        public String a;
        public int b;
        public int c;
        public long d;
    }
}

