/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import aq.aq;
import be.df;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ba {
    private static final Logger a = Logger.getLogger(ba.class.getName());
    private final int b = 50;
    private static ba c;
    private final HashMap<Integer, Integer> d = new HashMap();
    private final HashMap<Integer, Integer> e = new HashMap();
    private final HashMap<Integer, Integer> f = new HashMap();
    private final HashMap<Integer, Integer> g = new HashMap();
    private final HashMap<Integer, Integer> h = new HashMap();
    private final HashMap<Integer, Integer> i = new HashMap();
    private final HashMap<Integer, Integer> j = new HashMap();
    private final HashMap<Integer, Integer> k = new HashMap();
    private final HashMap<Integer, Integer> l = new HashMap();
    private final ArrayList<a> m = new ArrayList();
    private final ArrayList<a> n = new ArrayList();
    private final ArrayList<a> o = new ArrayList();
    private final ArrayList<a> p = new ArrayList();
    private final ArrayList<a> q = new ArrayList();
    private final ArrayList<a> r = new ArrayList();
    private final ArrayList<a> s = new ArrayList();
    private final ArrayList<a> t = new ArrayList();
    private final ArrayList<a> u = new ArrayList();

    public static ba a() {
        if (c == null) {
            c = new ba();
        }
        return c;
    }

    public static void b() {
        for (u pc : aq.a().c()) {
            pc.I();
        }
        c = new ba();
    }

    private ba() {
        this.c();
        this.d();
    }

    public void a(u pc, int type) {
        switch (type) {
            case 8: {
                pc.a(new df(type, this.m.toArray(new a[0])));
                break;
            }
            case 0: {
                pc.a(new df(type, this.n.toArray(new a[0])));
                break;
            }
            case 1: {
                pc.a(new df(type, this.o.toArray(new a[0])));
                break;
            }
            case 2: {
                pc.a(new df(type, this.p.toArray(new a[0])));
                break;
            }
            case 3: {
                pc.a(new df(type, this.q.toArray(new a[0])));
                break;
            }
            case 4: {
                pc.a(new df(type, this.r.toArray(new a[0])));
                break;
            }
            case 5: {
                pc.a(new df(type, this.s.toArray(new a[0])));
                break;
            }
            case 6: {
                pc.a(new df(type, this.t.toArray(new a[0])));
                break;
            }
            case 7: {
                pc.a(new df(type, this.u.toArray(new a[0])));
            }
        }
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM ranking");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int rank = rs.getInt("no");
                        this.d.put(rs.getInt("level_char_id_all"), rank);
                        this.e.put(rs.getInt("level_char_id_p"), rank);
                        this.f.put(rs.getInt("level_char_id_k"), rank);
                        this.g.put(rs.getInt("level_char_id_e"), rank);
                        this.h.put(rs.getInt("level_char_id_w"), rank);
                        this.i.put(rs.getInt("level_char_id_d"), rank);
                        this.j.put(rs.getInt("level_char_id_r"), rank);
                        this.k.put(rs.getInt("level_char_id_i"), rank);
                        this.l.put(rs.getInt("level_char_id_o"), rank);
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
    }

    private void d() {
        block21: {
            long begin = System.currentTimeMillis();
            System.out.print("loading rankLevel...");
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        rs.getInt("AccessLevel");
                        a data = new a();
                        data.a = rs.getInt("objid");
                        data.b = rs.getString("char_name");
                        data.c = rs.getInt("level");
                        data.d = rs.getInt("Exp");
                        data.e = rs.getInt("Type");
                        if (data.e == 0) {
                            this.n.add(data);
                        } else if (data.e == 1) {
                            this.o.add(data);
                        } else if (data.e == 2) {
                            this.p.add(data);
                        } else if (data.e == 3) {
                            this.q.add(data);
                        } else if (data.e == 4) {
                            this.r.add(data);
                        } else if (data.e == 5) {
                            this.s.add(data);
                        } else if (data.e == 6) {
                            this.t.add(data);
                        } else if (data.e == 7) {
                            this.u.add(data);
                        }
                        this.m.add(data.a());
                    }
                    this.a(this.m);
                    this.a(this.n);
                    this.a(this.o);
                    this.a(this.p);
                    this.a(this.q);
                    this.a(this.r);
                    this.a(this.s);
                    this.a(this.t);
                    this.a(this.u);
                    this.a(this.m, "all");
                    this.a(this.n, "p");
                    this.a(this.o, "k");
                    this.a(this.p, "e");
                    this.a(this.q, "w");
                    this.a(this.r, "d");
                    this.a(this.s, "r");
                    this.a(this.t, "i");
                    this.a(this.u, "o");
                    System.out.println("OK! " + (System.currentTimeMillis() - begin) + " ms");
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block21;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    private void a(ArrayList<a> list, String calssToken) {
        HashMap<Object, Object> templList = new HashMap();
        if (calssToken.equals("p")) {
            templList = this.e;
        } else if (calssToken.equals("k")) {
            templList = this.f;
        } else if (calssToken.equals("e")) {
            templList = this.g;
        } else if (calssToken.equals("w")) {
            templList = this.h;
        } else if (calssToken.equals("d")) {
            templList = this.i;
        } else if (calssToken.equals("r")) {
            templList = this.j;
        } else if (calssToken.equals("i")) {
            templList = this.k;
        } else if (calssToken.equals("o")) {
            templList = this.l;
        } else if (calssToken.equals("all")) {
            templList = this.d;
        }
        int i2 = 0;
        while (i2 < list.size()) {
            int rank = i2 + 1;
            int charID = list.get((int)i2).a;
            if (i2 < 50) {
                this.a(charID, rank, "level_char_id_" + calssToken);
                if (templList.containsKey(charID)) {
                    int old_rank;
                    list.get((int)i2).f = old_rank = ((Integer)templList.get(charID)).intValue();
                }
            }
            ++i2;
        }
        while (list.size() > 50) {
            list.remove(list.size() - 1);
        }
    }

    private void a(ArrayList<a> list) {
        Collections.sort(list, new Comparator<a>(){

            public int a(a d1, a d2) {
                if (d2.c == d1.c) {
                    return d2.d - d1.d;
                }
                return d2.c - d1.c;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((a)object, (a)object2);
            }
        });
    }

    private void a(int id, int index, String column) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE ranking SET " + column + "=" + id + " WHERE no =" + index);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(pstm);
                bi.j.a(con);
                throw throwable;
            }
            bi.j.a(pstm);
            bi.j.a(con);
        }
    }

    public class a
    implements Cloneable {
        public int a;
        public String b;
        public int c;
        public int d;
        public int e;
        public int f = 0;

        public a a() {
            a data = null;
            try {
                data = (a)super.clone();
            }
            catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
            }
            return data;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return this.a();
        }
    }
}

