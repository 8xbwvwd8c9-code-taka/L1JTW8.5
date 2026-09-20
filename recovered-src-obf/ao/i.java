/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import be.aa;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class i {
    private static final Logger a = Logger.getLogger(i.class.getName());
    private static i b;

    public static i a() {
        if (b == null) {
            b = new i();
        }
        return b;
    }

    public void a(u pc) {
        block7: {
            ResultSet rs;
            PreparedStatement pstm;
            Connection con;
            block5: {
                byte[] data;
                block6: {
                    con = null;
                    pstm = null;
                    rs = null;
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_config WHERE object_id=?");
                    pstm.setInt(1, pc.fr());
                    rs = pstm.executeQuery();
                    if (!rs.next()) break block5;
                    data = rs.getBytes("data");
                    if (data != null) break block6;
                    j.a(rs, pstm, con);
                    return;
                }
                try {
                    try {
                        pc.a(new aa(data));
                    }
                    catch (Exception e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(rs, pstm, con);
                        break block7;
                    }
                }
                catch (Throwable throwable) {
                    j.a(rs, pstm, con);
                    throw throwable;
                }
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(int objectId, byte[] data) {
        if (this.a(objectId) > 0) {
            this.c(objectId, data);
        } else {
            this.b(objectId, data);
        }
    }

    private void b(int objectId, byte[] data) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_config SET object_id=?, data=?");
                    pstm.setInt(1, objectId);
                    pstm.setBytes(2, data);
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

    private void c(int objectId, byte[] data) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE character_config SET data=? WHERE object_id=?");
                    pstm.setBytes(1, data);
                    pstm.setInt(2, objectId);
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

    private int a(int objectId) {
        int result;
        block6: {
            result = 0;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT count(*) as cnt FROM character_config WHERE object_id=?");
                    pstm.setInt(1, objectId);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        result = rs.getInt("cnt");
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
}

