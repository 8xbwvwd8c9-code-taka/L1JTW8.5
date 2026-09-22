/*
 * Decompiled with CFR 0.152.
 */
package ao;

import aq.d;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class f {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private static f b;
    private final HashMap<Integer, d> c;

    public static f a() {
        if (b == null) {
            b = new f();
        }
        return b;
    }

    private f() {
        block12: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT distinct(char_id) as char_id FROM character_buddys");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        PreparedStatement pstm2 = null;
                        ResultSet rs2 = null;
                        try {
                            try {
                                pstm2 = con.prepareStatement("SELECT buddy_id, buddy_name FROM character_buddys WHERE char_id = ?");
                                int charId = rs.getInt("char_id");
                                pstm2.setInt(1, charId);
                                d buddy = new d(charId);
                                rs2 = pstm2.executeQuery();
                                while (rs2.next()) {
                                    buddy.a(rs2.getInt("buddy_id"), rs2.getString("buddy_name"));
                                }
                                this.c.put(buddy.a(), buddy);
                            }
                            catch (Exception e2) {
                                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                                j.a(rs2);
                                j.a(pstm2);
                                continue;
                            }
                        }
                        catch (Throwable throwable) {
                            j.a(rs2);
                            j.a(pstm2);
                            throw throwable;
                        }
                        j.a(rs2);
                        j.a(pstm2);
                    }
                }
                catch (SQLException e3) {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                    j.a(rs, pstm, con);
                    break block12;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public d a(int charId) {
        d buddy = this.c.get(charId);
        if (buddy == null) {
            buddy = new d(charId);
            this.c.put(charId, buddy);
        }
        return buddy;
    }

    public void a(int charId, int objId, String name) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_buddys SET char_id=?, buddy_id=?, buddy_name=?");
                    pstm.setInt(1, charId);
                    pstm.setInt(2, objId);
                    pstm.setString(3, name);
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

    public boolean b(int charId, int objId, String name) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("INSERT INTO character_buddys SET char_id=?, buddy_id=?, buddy_name=?");
            pstm.setInt(1, charId);
            pstm.setInt(2, objId);
            pstm.setString(3, name);
            return pstm.executeUpdate() > 0;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    public void a(int charId, String buddyName) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            d buddy = this.a(charId);
            if (!buddy.b(buddyName)) {
                return;
            }
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_buddys WHERE char_id=? AND buddy_name=?");
                    pstm.setInt(1, charId);
                    pstm.setString(2, buddyName);
                    pstm.execute();
                    buddy.a(buddyName);
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
}

