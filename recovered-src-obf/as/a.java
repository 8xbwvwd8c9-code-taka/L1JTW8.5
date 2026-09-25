/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ah;
import ao.au;
import ao.bc;
import ap.f;
import ap.q;
import aq.aa;
import aq.aq;
import be.ak;
import be.cg;
import bh.t;
import bh.u;
import bi.e;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private ScheduledExecutorService b;
    private static a c;
    private ap.t d;
    private static final int e = 0;
    private static final int f = 1;
    private static final int g = 2;
    private int h = 5000;
    private int i = 0;
    private int j;
    private int k = 0;
    private HashMap<Integer, ap.t> l = new HashMap();
    private final ConcurrentHashMap<Integer, a> m = new ConcurrentHashMap();
    private HashMap<Integer, a> n = new HashMap();
    private final HashMap<Integer, b> o = new HashMap();
    private final int[][] p;
    private final int[][] q;
    private final int[][] r;
    private final int[][] s;
    private final int[][] t;

    public static a a() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    private a() {
        int[][] nArrayArray = new int[9][];
        nArrayArray[0] = new int[]{6, 46};
        nArrayArray[1] = new int[]{7, 3};
        int[] nArray = new int[2];
        nArray[1] = 5;
        nArrayArray[2] = nArray;
        nArrayArray[3] = new int[]{1, 11};
        nArrayArray[4] = new int[]{2, 18};
        nArrayArray[5] = new int[]{1, 1};
        nArrayArray[6] = new int[]{2, 10};
        nArrayArray[7] = new int[]{1, 2};
        nArrayArray[8] = new int[]{2, 9};
        this.p = nArrayArray;
        int[][] nArrayArray2 = new int[7][];
        nArrayArray2[0] = new int[]{6, 44};
        nArrayArray2[1] = new int[]{7, 3};
        int[] nArray2 = new int[2];
        nArray2[1] = 8;
        nArrayArray2[2] = nArray2;
        nArrayArray2[3] = new int[]{1, 10};
        nArrayArray2[4] = new int[]{2, 32};
        nArrayArray2[5] = new int[]{1, 1};
        nArrayArray2[6] = new int[]{2, 7};
        this.q = nArrayArray2;
        int[][] nArrayArray3 = new int[12][];
        nArrayArray3[0] = new int[]{6, 34};
        nArrayArray3[1] = new int[]{7, 1};
        nArrayArray3[2] = new int[]{6, 6};
        nArrayArray3[3] = new int[]{7, 5};
        int[] nArray3 = new int[2];
        nArray3[1] = 6;
        nArrayArray3[4] = nArray3;
        nArrayArray3[5] = new int[]{1, 7};
        int[] nArray4 = new int[2];
        nArray4[1] = 1;
        nArrayArray3[6] = nArray4;
        nArrayArray3[7] = new int[]{1, 2};
        nArrayArray3[8] = new int[]{3, 1};
        nArrayArray3[9] = new int[]{2, 6};
        nArrayArray3[10] = new int[]{1, 1};
        nArrayArray3[11] = new int[]{2, 35};
        this.r = nArrayArray3;
        int[][] nArrayArray4 = new int[11][];
        nArrayArray4[0] = new int[]{6, 35};
        nArrayArray4[1] = new int[]{7, 4};
        nArrayArray4[2] = new int[]{6, 3};
        nArrayArray4[3] = new int[]{7, 3};
        int[] nArray5 = new int[2];
        nArray5[1] = 2;
        nArrayArray4[4] = nArray5;
        nArrayArray4[5] = new int[]{1, 2};
        int[] nArray6 = new int[2];
        nArray6[1] = 4;
        nArrayArray4[6] = nArray6;
        nArrayArray4[7] = new int[]{1, 4};
        int[] nArray7 = new int[2];
        nArray7[1] = 1;
        nArrayArray4[8] = nArray7;
        nArrayArray4[9] = new int[]{1, 2};
        nArrayArray4[10] = new int[]{2, 45};
        this.s = nArrayArray4;
        int[][] nArrayArray5 = new int[9][];
        nArrayArray5[0] = new int[]{6, 34};
        nArrayArray5[1] = new int[]{7, 10};
        int[] nArray8 = new int[2];
        nArray8[1] = 1;
        nArrayArray5[2] = nArray8;
        nArrayArray5[3] = new int[]{1, 1};
        int[] nArray9 = new int[2];
        nArray9[1] = 4;
        nArrayArray5[4] = nArray9;
        nArrayArray5[5] = new int[]{1, 3};
        nArrayArray5[6] = new int[]{2, 1};
        nArrayArray5[7] = new int[]{1, 3};
        nArrayArray5[8] = new int[]{2, 48};
        this.t = nArrayArray5;
        this.g();
        this.h();
        new c().a();
    }

    private void e() {
        int diff = 0;
        while (this.l.size() < 5) {
            int npcid = 91350 + new Random().nextInt(20);
            if (this.l.containsKey(npcid)) continue;
            aq.u loc = new aq.u(33522 - diff * 2, 32861 + diff * 2, 4);
            this.l.put(npcid, this.a(loc, npcid));
            ++diff;
            int num = npcid - 91350 + 1;
            a round_ticket = new a();
            this.n.put(num, round_ticket);
        }
    }

    private void f() {
        this.b.shutdownNow();
        for (ap.t npc : this.l.values()) {
            npc.aa_();
        }
        this.l = new HashMap();
        this.n = new HashMap();
        this.h = 5000;
        this.k = 0;
        f[] fArray = ao.t.b().c();
        int n2 = fArray.length;
        int n3 = 0;
        while (n3 < n2) {
            f door = fArray[n3];
            if (door.i() <= 812 && door.i() >= 808) {
                door.g();
            }
            ++n3;
        }
    }

    private void g() {
        int i2 = 1;
        while (i2 <= 20) {
            this.o.put(i2, new b());
            ++i2;
        }
        for (aa obj : aq.a().b()) {
            if (!(obj instanceof ap.t) || ((ap.t)obj).z() != 70041) continue;
            this.d = (ap.t)obj;
        }
    }

    private void b(String msg) {
        this.d.d(new cg(this.d, msg, 2));
    }

    private ap.t a(aq.u loc, int npcid) {
        ap.t npc = au.a().b(npcid);
        npc.cF(ai.d.a().c());
        npc.a("#" + (npc.z() - 91350 + 1) + " " + npc.T());
        npc.ct(6);
        npc.cG(loc.f());
        npc.cH(loc.g());
        npc.cE(loc.b());
        aq.a().a(npc);
        aq.a().c(npc);
        return npc;
    }

    private double c(int num) {
        double win = this.o.get(num).b;
        double race = this.o.get(num).c;
        if (race == 0.0) {
            return 0.0;
        }
        return win / race * 100.0;
    }

    public int b() {
        return this.i;
    }

    public String[] c() {
        String[] htmldata = new String[15];
        int i2 = -1;
        for (ap.t npc : this.l.values()) {
            int num = npc.z() - 91350 + 1;
            htmldata[++i2] = "#" + String.format("%02d", num);
            htmldata[++i2] = (new String[]{"$368$368", "$369", "$370"})[new Random().nextInt(3)];
            htmldata[++i2] = String.format("%.2f", this.c(num)).toString();
        }
        return htmldata;
    }

    public void a(q item) {
        a ticket = this.m.get(item.fr());
        if (ticket != null) {
            bh.j temp = (bh.j)item.a().clone();
            String buf = String.valueOf(temp.j()) + " " + ticket.d + "-" + ticket.g;
            temp.a(buf);
            temp.b(buf);
            temp.c(buf);
            item.a(temp);
        }
    }

    public void b(q item) {
        String[] info = item.a().j().split("-");
        int num = Integer.parseInt(info[1]);
        a a2 = this.n.get(num);
        a2.b = a2.b + item.E();
        this.h += item.E() * 500;
        a ticket = new a();
        ticket.c = item.fr();
        ticket.d = this.j;
        ticket.g = num;
        this.m.put(new Integer(ticket.c), ticket);
        this.a(ticket);
    }

    public int a(int itemobjid) {
        a ticket = this.m.get(itemobjid);
        if (ticket == null) {
            return 0;
        }
        return (int)(ticket.e * (double)ticket.f) * 500;
    }

    public String a(String itemName) {
        String[] temp = itemName.split(" ");
        String s2 = temp[temp.length - 1];
        temp = s2.split("-");
        return String.valueOf(s2) + " $" + (1212 + Integer.parseInt(temp[temp.length - 1]));
    }

    private void h() {
        block10: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM race_ticket");
                    int maxRoundNumber = 0;
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a ticket = new a();
                        int itemobjid = rs.getInt(1);
                        int round = rs.getInt(2);
                        int num = rs.getInt(5);
                        if (itemobjid > 0) {
                            ticket.c = itemobjid;
                            ticket.d = round;
                            ticket.e = (double)rs.getInt(3);
                            ticket.f = rs.getInt(4);
                            ticket.g = num;
                            this.m.put(itemobjid, ticket);
                        } else if (num > 0) {
                            b b2 = this.o.get(num);
                            b2.b = b2.b + 1;
                            int i2 = 6;
                            while (i2 <= 10) {
                                b b3 = this.o.get(rs.getInt(i2));
                                b3.c = b3.c + 1;
                                ++i2;
                            }
                        }
                        if (round <= maxRoundNumber) continue;
                        maxRoundNumber = round;
                    }
                    this.j = maxRoundNumber;
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block10;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    private void a(a ticket) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO race_ticket SET item_objid=?,round=?,runner_num=?");
                    pstm.setInt(1, ticket.c);
                    pstm.setInt(2, ticket.d);
                    pstm.setInt(3, ticket.g);
                    pstm.execute();
                }
                catch (Exception e2) {
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

    public void b(int itemobjid) {
        block6: {
            if (this.m.containsKey(itemobjid)) {
                this.m.remove(itemobjid);
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("delete from race_ticket WHERE item_objid=?");
                    pstm.setInt(1, itemobjid);
                    pstm.execute();
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block6;
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

    private void d(int winnerID) {
        int winner_num = winnerID - 91350 + 1;
        double value = this.n.get(winner_num).e;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE race_ticket SET victory=? ,odds=? WHERE round=? and runner_num=?");
            pstm.setInt(1, 1);
            pstm.setDouble(2, value);
            pstm.setInt(3, this.j);
            pstm.setInt(4, winner_num);
            pstm.execute();
            int i2 = 0;
            pstm = con.prepareStatement("UPDATE race_ticket SET odds=?,runner_num=?,runner_1=?,runner_2=?,runner_3=?,runner_4=?,runner_5=? WHERE round=? AND item_objid=?");
            pstm.setDouble(++i2, value);
            pstm.setInt(++i2, winner_num);
            b b2 = this.o.get(winner_num);
            b2.b = b2.b + 1;
            for (ap.t npc : this.l.values()) {
                int num = npc.z() - 91350 + 1;
                pstm.setInt(++i2, num);
                b b3 = this.o.get(num);
                b3.c = b3.c + 1;
            }
            pstm.setInt(++i2, this.j);
            pstm.setInt(++i2, 0);
            pstm.execute();
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        bi.j.a(pstm);
        bi.j.a(con);
        for (a ticket : this.m.values()) {
            if (ticket.d != this.j || ticket.g != winner_num) continue;
            ticket.e = value;
            ticket.f = 1;
        }
    }

    private int[][] e(int x2) {
        int[][] path = new int[0][0];
        path = x2 == 33514 ? this.t : (x2 == 33516 ? this.s : (x2 == 33518 ? this.r : (x2 == 33520 ? this.q : this.p)));
        return path;
    }

    private class a {
        private int b = 0;
        private int c = 0;
        private int d = 0;
        private double e = 0.0;
        private int f = 0;
        private int g = 0;

        private a() {
        }
    }

    private class b {
        private int b = 0;
        private int c = 0;

        private b() {
        }
    }

    private class c
    extends TimerTask {
        private c() {
        }

        private void a() {
            bi.e.a().a(this, 3000L);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    while (true) {
                        a.this.b = Executors.newScheduledThreadPool(5);
                        a.this.i = 0;
                        a a2 = a.this;
                        a2.j = a2.j + 1;
                        a round_temp = new a();
                        round_temp.d = a.this.j;
                        a.this.a(round_temp);
                        a.this.b("$376 10 $377");
                        Thread.sleep(600000L);
                        a.this.e();
                        t shop1 = bc.a().a(70035);
                        t shop2 = bc.a().a(70041);
                        t shop3 = bc.a().a(70042);
                        Iterator<Object> iterator = a.this.l.keySet().iterator();
                        while (iterator.hasNext()) {
                            int npcid = (Integer)iterator.next();
                            bh.j l1item = (bh.j)ah.a().a(40309).clone();
                            int num = npcid - 91350 + 1;
                            String view_name = String.valueOf(l1item.j()) + " " + a.this.j + "-" + num;
                            l1item.a(view_name);
                            l1item.b(view_name);
                            l1item.c(view_name);
                            u shopItem = new u(l1item, 500);
                            shop1.b().add(shopItem);
                            shop2.b().add(shopItem);
                            shop3.b().add(shopItem);
                        }
                        a.this.i = 1;
                        int i2 = 5;
                        while (i2 > 0) {
                            a.this.b("$376 " + i2 + " $377");
                            Thread.sleep(60000L);
                            --i2;
                        }
                        a.this.b("$363");
                        Thread.sleep(1000L);
                        i2 = 10;
                        while (i2 > 0) {
                            a.this.b("" + i2);
                            Thread.sleep(1000L);
                            --i2;
                        }
                        a.this.b("$364");
                        a.this.i = 2;
                        shop1.b().clear();
                        shop2.b().clear();
                        shop3.b().clear();
                        f[] num = ao.t.b().c();
                        int l1item = num.length;
                        int n2 = 0;
                        while (n2 < l1item) {
                            f door = num[n2];
                            if (door.i() <= 812 && door.i() >= 808) {
                                door.f();
                            }
                            ++n2;
                        }
                        for (ap.t npc : a.this.l.values()) {
                            a.this.b.schedule(new d(npc, a.this.e(npc.fs())), 0L, TimeUnit.MILLISECONDS);
                        }
                        Thread.sleep(3000L);
                        for (ap.t npc : a.this.l.values()) {
                            int num2 = npc.z() - 91350 + 1;
                            a ticket = (a)a.this.n.get(num2);
                            if (ticket.b > 0) {
                                ticket.e = (double)(a.this.h / ticket.b) / 500.0;
                            } else {
                                ticket.e = 1.0;
                            }
                            a.this.b(String.valueOf(npc.T()) + " $402 " + ticket.e);
                            Thread.sleep(500L);
                        }
                        while (a.this.k == 0) {
                            Thread.sleep(1000L);
                        }
                        a.this.d(a.this.k);
                        a.this.f();
                    }
                }
                catch (InterruptedException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    continue;
                }
                break;
            }
        }
    }

    private class d
    implements Runnable {
        ap.t a;
        int[][] b;

        private d(ap.t npc, int[][] path) {
            this.a = npc;
            this.b = path;
        }

        @Override
        public void run() {
            try {
                int[][] nArray = this.b;
                int n2 = this.b.length;
                int n3 = 0;
                while (n3 < n2) {
                    int[] dir = nArray[n3];
                    int i2 = 0;
                    while (i2 < dir[1]) {
                        if (i2 == 0 && this.a.fb() != dir[0]) {
                            this.a.ct(dir[0]);
                        }
                        this.a.g(this.a.fb());
                        int rnd = new Random().nextInt(30);
                        if (rnd >= new Random().nextInt(2000)) {
                            this.a.b(new ak(this.a.fr(), 30));
                            Thread.sleep(am.c.a().a(this.a.fe(), 30));
                        }
                        Thread.sleep(this.a.N() * 30 / (16 + rnd));
                        ++i2;
                    }
                    ++n3;
                }
                if (a.this.k == 0) {
                    a.this.k = this.a.z();
                    a.this.b("$375 " + a.this.j + "$366 " + this.a.T() + "$367");
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }
}

