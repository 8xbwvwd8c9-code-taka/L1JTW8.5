/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.misc.BASE64Encoder
 */
package bh;

import bi.j;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;
import sun.misc.BASE64Encoder;

public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private String b;
    private String c;
    private String d;
    private Timestamp e = new Timestamp(System.currentTimeMillis());
    private int f;
    private String g;
    private boolean h = false;
    private int i;
    private boolean j = false;
    private int k = 0;
    private boolean l = false;
    private boolean m = false;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private int q = Integer.MAX_VALUE;

    public boolean a() {
        return this.f > 0;
    }

    public int b() {
        int result;
        block6: {
            result = 0;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    String sqlstr = "SELECT count(*) as cnt FROM characters WHERE account_name=?";
                    pstm = con.prepareStatement("SELECT count(*) as cnt FROM characters WHERE account_name=?");
                    pstm.setString(1, this.b);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        result = rs.getInt("cnt");
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
        return result;
    }

    public boolean a(String rawPassword) {
        try {
            this.j = this.d.equals(bh.a.b(rawPassword));
            return this.j;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
    }

    public static String b(String rawPassword) {
        byte[] buf = new byte[]{};
        try {
            buf = rawPassword.getBytes("UTF-8");
            buf = MessageDigest.getInstance("SHA").digest(buf);
        }
        catch (UnsupportedEncodingException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        catch (NoSuchAlgorithmException e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
        }
        return new BASE64Encoder().encode(buf);
    }

    public void a(int i2) {
        this.n = Math.min(this.n + i2, Integer.MAX_VALUE);
    }

    public void b(int i2) {
        this.o = Math.min(this.o + i2, Integer.MAX_VALUE);
    }

    public void c() {
        ++this.p;
    }

    public void c(int i2) {
        this.q = Math.min(i2, this.q);
    }

    public String d() {
        return this.b;
    }

    public void c(String name) {
        this.b = name;
    }

    public String e() {
        return this.c;
    }

    public void d(String ip) {
        this.c = ip;
    }

    public String f() {
        return this.d;
    }

    public void e(String password) {
        this.d = password;
    }

    public Timestamp g() {
        return this.e;
    }

    public void a(Timestamp lastActive) {
        this.e = lastActive;
    }

    public int h() {
        return this.f;
    }

    public void d(int accessLevel) {
        this.f = accessLevel;
    }

    public String i() {
        return this.g;
    }

    public void f(String host) {
        this.g = host;
    }

    public boolean j() {
        return this.h;
    }

    public void a(boolean isBanned) {
        this.h = isBanned;
    }

    public int k() {
        return this.i;
    }

    public void e(int characterMaxSlot) {
        this.i = characterMaxSlot;
    }

    public boolean l() {
        return this.j;
    }

    public void b(boolean isValid) {
        this.j = isValid;
    }

    public int m() {
        return this.k;
    }

    public void f(int warePassword) {
        this.k = warePassword;
    }

    public boolean n() {
        return this.l;
    }

    public void c(boolean isOnline) {
        this.l = isOnline;
    }

    public boolean o() {
        return this.m;
    }

    public void d(boolean isInGame) {
        this.m = isInGame;
    }

    public int p() {
        return this.n;
    }

    public void g(int worldShopAdena) {
        this.n = worldShopAdena;
    }

    public int q() {
        return this.o;
    }

    public void h(int tamPoint) {
        this.o = tamPoint;
    }

    public int r() {
        return this.p;
    }

    public int s() {
        return this.q;
    }
}

