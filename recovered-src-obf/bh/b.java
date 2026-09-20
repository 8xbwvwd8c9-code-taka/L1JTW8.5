/*
 * Decompiled with CFR 0.152.
 */
package bh;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private final int b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;

    public int a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.f;
    }

    private b(int id, String name, String title, String content) {
        this.b = id;
        this.c = name;
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        this.d = sdf.format(new Date());
        this.e = title;
        this.f = content;
    }

    private b(ResultSet rs) throws SQLException {
        this.b = rs.getInt("id");
        this.c = rs.getString("name");
        this.d = rs.getString("date");
        this.e = rs.getString("title");
        this.f = rs.getString("content");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized b a(String name, String title, String content) {
        b b2;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT max(id) + 1 as newid FROM board");
            rs = pstm.executeQuery();
            rs.next();
            int id = rs.getInt("newid");
            b topic = new b(id, name, title, content);
            pstm2 = con.prepareStatement("INSERT INTO board SET id=?, name=?, date=?, title=?, content=?");
            pstm2.setInt(1, topic.a());
            pstm2.setString(2, topic.b());
            pstm2.setString(3, topic.c());
            pstm2.setString(4, topic.d());
            pstm2.setString(5, topic.e());
            pstm2.execute();
            b2 = topic;
        }
        catch (SQLException e2) {
            try {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(pstm2);
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(pstm2);
            j.a(rs, pstm, con);
            return null;
        }
        j.a(pstm2);
        j.a(rs, pstm, con);
        return b2;
    }

    public void f() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM board WHERE id=?");
                    pstm.setInt(1, this.a());
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

    public static b a(int id) {
        block5: {
            ResultSet rs;
            PreparedStatement pstm;
            Connection con;
            block4: {
                b b2;
                con = null;
                pstm = null;
                rs = null;
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM board WHERE id=?");
                    pstm.setInt(1, id);
                    rs = pstm.executeQuery();
                    if (!rs.next()) break block4;
                    b2 = new b(rs);
                }
                catch (SQLException e2) {
                    try {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    }
                    catch (Throwable throwable) {
                        j.a(rs, pstm, con);
                        throw throwable;
                    }
                    j.a(rs, pstm, con);
                    break block5;
                }
                j.a(rs, pstm, con);
                return b2;
            }
            j.a(rs, pstm, con);
        }
        return null;
    }

    private static PreparedStatement a(Connection con, int id, int limit) throws SQLException {
        PreparedStatement result = null;
        int offset = 1;
        if (id == 0) {
            result = con.prepareStatement("SELECT * FROM board ORDER BY id DESC LIMIT ?");
        } else {
            result = con.prepareStatement("SELECT * FROM board WHERE id < ? ORDER BY id DESC LIMIT ?");
            result.setInt(1, id);
            ++offset;
        }
        result.setInt(offset, limit);
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static List<b> a(int id, int limit) {
        ArrayList<b> arrayList;
        ArrayList<b> result = new ArrayList<b>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = bh.b.a(con, id, limit);
            rs = pstm.executeQuery();
            while (rs.next()) {
                result.add(new b(rs));
            }
            arrayList = result;
        }
        catch (SQLException e2) {
            try {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return null;
        }
        j.a(rs, pstm, con);
        return arrayList;
    }
}

