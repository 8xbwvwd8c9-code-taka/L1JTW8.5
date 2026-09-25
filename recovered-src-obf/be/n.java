/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import bh.c;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class n
extends eu {
    private static final Logger a = Logger.getLogger(n.class.getName());

    public n(String name, int map, int id, int x2, int y2) {
        this.c(2);
        this.a(name);
        this.b(map);
        this.b(x2);
        this.b(y2);
        this.a(id);
        this.c(0);
    }

    public n(u pc) {
        block9: {
            ArrayList<c> bookList = new ArrayList<c>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY order_id  ASC");
                    pstm.setInt(1, pc.fr());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        c bookmark = new c();
                        bookmark.a(rs.getInt("id"));
                        bookmark.b(rs.getInt("char_id"));
                        bookmark.a(rs.getString("name"));
                        bookmark.c(rs.getInt("locx"));
                        bookmark.d(rs.getInt("locy"));
                        bookmark.g(rs.getShort("mapid"));
                        bookmark.e(rs.getInt("order_id"));
                        bookmark.f(rs.getInt("order_id_fast"));
                        bookList.add(bookmark);
                    }
                    this.c(42);
                    this.c(42);
                    this.c(128);
                    this.c(0);
                    this.c(2);
                    byte[] init = new byte[127];
                    int i2 = 0;
                    while (i2 < bookList.size()) {
                        init[i2] = (byte)i2;
                        ++i2;
                    }
                    for (c bookmark : bookList) {
                        if (bookmark.g() < 0) continue;
                        int index = bookList.size() + bookmark.g();
                        init[index] = (byte)bookmark.f();
                    }
                    this.a(init);
                    this.b(pc.cI());
                    this.b(bookList.size());
                    for (c bookmark : bookList) {
                        this.a(bookmark.a());
                        this.a(bookmark.c());
                        this.b(bookmark.h());
                        this.b(bookmark.d());
                        this.b(bookmark.e());
                        pc.ba().add(bookmark);
                    }
                    this.b(0);
                }
                catch (SQLException e2) {
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

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Bookmarks";
    }
}

