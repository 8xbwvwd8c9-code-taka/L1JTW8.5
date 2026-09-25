/*
 * Decompiled with CFR 0.152.
 */
package ao;

import a.g;
import am.d;
import an.f;
import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class m {
    private static final Logger a = Logger.getLogger(m.class.getName());
    private static m b;

    public static m a() {
        if (b == null) {
            b = new m();
        }
        return b;
    }

    private void a(String login, int[] data) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_mobs SET login=?, data=?");
                    pstm.setString(1, login);
                    f.e.a builder = f.e.aa();
                    int i2 = 0;
                    while (i2 < data.length) {
                        builder.b(data[i2]);
                        ++i2;
                    }
                    pstm.setBytes(2, builder.M().g());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block6;
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

    public void a(u pc) {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_mobs (login,data) VALUES (?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                    f.e.a builder = f.e.aa();
                    int i2 = 0;
                    while (i2 < pc.dQ().length) {
                        builder.b(pc.dQ()[i2]);
                        ++i2;
                    }
                    byte[] array = new byte[pc.dR().length];
                    int i3 = 0;
                    while (i3 < array.length) {
                        array[i3] = (byte)pc.dR()[i3];
                        ++i3;
                    }
                    builder.e(g.a(array));
                    pstm.setString(1, pc.bc());
                    pstm.setBytes(2, builder.M().g());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block7;
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

    public void b(u pc) {
        block9: {
            int[] result = new int[d.a().b()];
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_mobs WHERE login=?");
                    pstm.setString(1, pc.bc());
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        byte[] data = rs.getBytes("data");
                        f.e msg = f.e.a(data);
                        int i2 = 0;
                        while (i2 < result.length) {
                            if (i2 >= msg.p()) break;
                            result[i2] = msg.a(i2);
                            ++i2;
                        }
                        byte[] array = msg.t().e();
                        int i3 = 0;
                        while (i3 < array.length) {
                            pc.dR()[i3] = array[i3];
                            ++i3;
                        }
                    } else {
                        this.a(pc.bc(), result);
                    }
                    pc.a(result);
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block9;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }
}

