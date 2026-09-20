/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import aq.an;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class o {
    private static final Logger a = Logger.getLogger(o.class.getName());
    private static HashMap<Integer, ArrayList<o>> b = new HashMap();
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;

    private o() {
    }

    private boolean b() {
        return this.c != 0 && this.d != 0 && this.e != 0 && this.f != 0;
    }

    public static void a() {
        block7: {
            b.clear();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    String sSQL = "SELECT * FROM getback ORDER BY area_mapid,area_x1 DESC ";
                    pstm = con.prepareStatement("SELECT * FROM getback ORDER BY area_mapid,area_x1 DESC ");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        o getback = new o();
                        getback.c = rs.getInt("area_x1");
                        getback.d = rs.getInt("area_y1");
                        getback.e = rs.getInt("area_x2");
                        getback.f = rs.getInt("area_y2");
                        getback.g = rs.getInt("area_mapid");
                        getback.h = rs.getInt("getback_x1");
                        getback.i = rs.getInt("getback_y1");
                        getback.j = rs.getInt("getback_x2");
                        getback.k = rs.getInt("getback_y2");
                        getback.l = rs.getInt("getback_x3");
                        getback.m = rs.getInt("getback_y3");
                        getback.n = rs.getInt("getback_mapid");
                        getback.o = rs.getInt("getback_townid");
                        getback.p = rs.getInt("getback_townid_elf");
                        getback.q = rs.getInt("getback_townid_darkelf");
                        rs.getBoolean("scrollescape");
                        ArrayList<o> getbackList = b.get(getback.g);
                        if (getbackList == null) {
                            getbackList = new ArrayList();
                            b.put(getback.g, getbackList);
                        }
                        getbackList.add(getback);
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    public static int[] a(u pc) {
        int[] loc = new int[]{33082, 33399, 4};
        int nPosition = bi.i.a(3);
        int pcLocX = pc.fs();
        int pcLocY = pc.ft();
        int pcMapId = pc.fp();
        List getbackList = b.get(pcMapId);
        if (getbackList != null) {
            o getback = null;
            for (o gb2 : getbackList) {
                if (gb2.b()) {
                    if (gb2.c > pcLocX || pcLocX > gb2.e || gb2.d > pcLocY || pcLocY > gb2.f) continue;
                    getback = gb2;
                    break;
                }
                getback = gb2;
                break;
            }
            if (getback == null) {
                return loc;
            }
            loc = aq.o.a(getback, nPosition);
            if (pc.A() && getback.p > 0) {
                loc = an.a(getback.p);
            } else if (pc.C() && getback.q > 0) {
                loc = an.a(getback.q);
            } else if (getback.o > 0) {
                loc = an.a(getback.o);
            }
        }
        return loc;
    }

    private static int[] a(o getback, int nPosition) {
        int[] loc = new int[3];
        switch (nPosition) {
            case 0: {
                loc[0] = getback.h;
                loc[1] = getback.i;
                break;
            }
            case 1: {
                loc[0] = getback.j;
                loc[1] = getback.k;
                break;
            }
            case 2: {
                loc[0] = getback.l;
                loc[1] = getback.m;
            }
        }
        loc[2] = getback.n;
        return loc;
    }
}

