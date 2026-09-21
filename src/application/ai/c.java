/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ao.aa;
import ao.ac;
import ao.ad;
import ao.ae;
import ao.af;
import ao.ag;
import ao.ah;
import ao.al;
import ao.am;
import ao.an;
import ao.ao;
import ao.ap;
import ao.ar;
import ao.as;
import ao.at;
import ao.au;
import ao.aw;
import ao.ax;
import ao.ay;
import ao.az;
import ao.ba;
import ao.bb;
import ao.bc;
import ao.bd;
import ao.be;
import ao.bg;
import ao.bi;
import ao.bk;
import ao.bl;
import ao.k;
import ao.q;
import ao.s;
import ao.t;
import ao.v;
import ao.y;
import ao.z;
import aq.aq;
import aq.l;
import aq.o;
import aq.p;
import aq.x;
import ba.f;
import ba.h;
import ba.i;
import ba.j;
import be.ds;
import bi.g;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c {
    private static final Logger b = Logger.getLogger(c.class.getName());
    public final int a = (int)(System.currentTimeMillis() / 1000L);
    private static c c;
    private final ConcurrentHashMap<String, b> d = new ConcurrentHashMap();
    private final CopyOnWriteArrayList<String> e = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<bj.d> f = new CopyOnWriteArrayList();
    private e g = null;
    private static int h;

    static {
        h = 0;
    }

    public static c a() {
        if (c == null) {
            c = new c();
        }
        return c;
    }

    public void b() throws Exception {
        if (l1j.server.a.c) {
            l1j.server.a.o = 10;
        }
        double rateXp = l1j.server.a.B;
        double LA = l1j.server.a.C;
        double rateKarma = l1j.server.a.D;
        double rateDropItems = l1j.server.a.F;
        double rateDropAdena = l1j.server.a.E;
        short chatlvl = l1j.server.a.N;
        int port = l1j.server.a.g;
        System.out.println("\u250c\u300c\u7d93\u9a57\u503c\u300d: " + rateXp + "\u3010\u500d\u3011");
        System.out.println("\u251c\u300c\u6b63\u7fa9\u503c\u300d: " + LA + "\u3010\u500d\u3011");
        System.out.println("\u251c\u300c\u53cb\u597d\u5ea6\u300d: " + rateKarma + "\u3010\u500d\u3011");
        System.out.println("\u251c\u300c\u7269\u54c1\u6389\u843d\u300d: " + rateDropItems + "\u3010\u500d\u3011");
        System.out.println("\u251c\u300c\u91d1\u5e63\u6389\u843d\u300d: " + rateDropAdena + "\u3010\u500d\u3011");
        System.out.println("\u251c\u300c\u5ee3\u64ad\u983b\u9053\u53ef\u7528\u7b49\u7d1a\u300d: " + chatlvl + "\u3010\u7d1a\u3011");
        System.out.println("\u2514\u300cNon-PvP\u8a2d\u5b9a\u300d: " + (l1j.server.a.R ? "\u3010\u6709\u6548 (PvP\u4e0d\u53ef)\u3011" : "\u3010\u7121\u6548 (PvP\u53ef\u80fd)\u3011") + "\n");
        System.out.println("\u9023\u7dda\u4eba\u6578\u4e0a\u9650: " + l1j.server.a.o + " \u4eba ");
        if (l1j.server.a.c) {
            System.out.println("\u3010\uff01\uff01\uff01\u3011\u6b64\u6a21\u64ec\u5668\u50c5\u63d0\u4f9b\u55ae\u6a5f\u6e2c\u8a66\uff0c\u5df2\u9650\u5236\u9023\u7dda\u4eba\u6578");
            System.out.println("\u3010\uff01\uff01\uff01\u3011\u6b64\u6a21\u64ec\u5668\u50c5\u63d0\u4f9b\u55ae\u6a5f\u6e2c\u8a66\uff0c\u5df2\u9650\u5236\u9023\u7dda\u4eba\u6578");
            System.out.println("\u3010\uff01\uff01\uff01\u3011\u6b64\u6a21\u64ec\u5668\u50c5\u63d0\u4f9b\u55ae\u6a5f\u6e2c\u8a66\uff0c\u5df2\u9650\u5236\u9023\u7dda\u4eba\u6578");
        }
        ai.d.a();
        ax.d.b();
        am.d.a();
        ao.a.a();
        ao.o.a();
        at.c.a();
        if (l1j.server.a.am > 0) {
            ba.e elementalStoneGenerator = ba.e.a();
            bi.e.a().a(elementalStoneGenerator);
        }
        ba.h.a();
        bc.a.b();
        f lightTimeController = ba.f.a();
        bi.e.a().a(lightTimeController);
        ba.a.a();
        ba.c.a();
        au.a();
        if (!au.a().b()) {
            throw new Exception("Could not initialize the npc table");
        }
        ao.a();
        t.a();
        am.c.a();
        ao.b.a();
        as.i.a();
        bg.a();
        ap.a();
        be.a();
        ay.a();
        bb.a();
        ah.a();
        v.a();
        bc.a();
        aq.a();
        bi.a();
        at.a();
        ag.a();
        aw.a();
        q.a();
        ao.g.a();
        aq.e.a();
        z.a();
        bi.e.a();
        bl.a();
        o.a();
        ax.a();
        bk.a();
        ao.e.a();
        y.a();
        as.a();
        an.a();
        as.a.a();
        af.a();
        l.a();
        am.b();
        ao.x.a();
        ba.a();
        bd.a();
        al.a();
        ac.a();
        ae.a();
        ad.a();
        s.a();
        k.a();
        ar.a();
        aa.a();
        ba.b.a();
        j.a();
        i.a();
        as.b.a();
        ao.aq.a();
        as.h.a();
        as.f.a();
        p.a();
        x.a();
        az.a();
        bi.g.c();
        ba.d.a();
        as.j.a();
        bi.g.b();
        System.out.println("\u521d\u59cb\u5316\u5b8c\u7562");
        bi.e.a().a(new a(this.d), 60000L, 60000L);
        bi.e.a().a(new a(this.e), 1800000L, 1800000L);
        bi.e.a().a(new d(new ServerSocket(l1j.server.a.g, -1)));
        System.out.println("\u4f7f\u7528\u4e86: " + bi.g.a() + "MB \u7684\u8a18\u61b6\u9ad4");
        System.out.println(bi.e.a().b());
        System.out.println("\u7b49\u5f85\u5ba2\u6236\u7aef\u9023\u63a5\u4e2d...");
    }

    public void a(bj.d client) {
        this.f.add(client);
    }

    public void b(bj.d client) {
        this.f.remove(client);
    }

    public CopyOnWriteArrayList<bj.d> c() {
        return this.f;
    }

    public void a(int secondsCount, boolean isReStart) {
        if (this.g != null) {
            return;
        }
        this.g = new e(secondsCount, isReStart);
        bi.e.a().a(this.g);
    }

    public void a(boolean isRestart) {
        for (bj.d client : this.c()) {
            if (client == null) continue;
            try {
                client.a(231);
            }
            catch (Exception e2) {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
        l1j.server.a.b = true;
        bi.e.a().a(new c(), 60000L);
        try {
            int count = 0;
            while (true) {
                if (this.c().isEmpty() || count++ > 180) {
                    if (isRestart) {
                        Runtime.getRuntime().exec("cmd /c start " + l1j.server.a.A);
                    }
                    System.exit(0);
                }
                Thread.sleep(1000L);
            }
        }
        catch (Exception e3) {
            b.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            return;
        }
    }

    public void d() {
        if (this.g == null) {
            return;
        }
        this.g.interrupt();
        this.g = null;
    }

    public static int e() {
        return ++h;
    }

    class a
    extends TimerTask {
        private CopyOnWriteArrayList<?> b = null;
        private ConcurrentHashMap<String, b> c = null;

        public a(CopyOnWriteArrayList<?> _list) {
            this.b = _list;
        }

        public a(ConcurrentHashMap<String, b> _map) {
            this.c = _map;
        }

        @Override
        public void run() {
            try {
                if (this.b != null) {
                    this.b.clear();
                } else if (this.c != null) {
                    this.c.clear();
                }
            }
            catch (Exception e2) {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    class b {
        public String a;
        public int b = 0;

        public b(String _ip) {
            this.a = _ip;
        }
    }

    private class c
    implements Runnable {
        private c() {
        }

        @Override
        public void run() {
            System.exit(0);
        }
    }

    private class d
    extends Thread {
        private final ServerSocket b;

        private d(ServerSocket _serverSocket) {
            this.b = _serverSocket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Socket socket = this.b.accept();
                    if (!l1j.server.a.b) {
                        b cdata;
                        if (socket == null) continue;
                        String host = socket.getInetAddress().getHostAddress();
                        System.out.println("\u5f9e " + socket.getInetAddress() + " \u8a66\u5716\u9023\u7dda");
                        if (ag.a().b(host)) {
                            System.out.println("banned IP(" + host + ")");
                            continue;
                        }
                        String ip = socket.getInetAddress().getHostAddress();
                        if (c.this.e.contains(ip)) continue;
                        if (c.this.d.containsKey(ip)) {
                            cdata = (b)c.this.d.get(ip);
                        } else {
                            cdata = new b(ip);
                            c.this.d.put(ip, cdata);
                        }
                        if (++cdata.b > 60) {
                            if (!c.this.e.contains(ip)) {
                                c.this.e.add(ip);
                            }
                            b.log(Level.SEVERE, "\u77ed\u6642\u9593\u5167\u9023\u7dda\u6b21\u6578\u904e\u591a\u52a0\u5165\u9ed1\u540d\u55ae IP=" + ip);
                            continue;
                        }
                        socket.setTcpNoDelay(true);
                        socket.setSoLinger(true, 0);
                        if (l1j.server.a.c && c.this.c().size() >= 10) continue;
                        bj.d client = new bj.d(socket);
                        c.this.a(client);
                        bi.e.a().b(client);
                        continue;
                    }
                    break;
                }
            }
            catch (Exception e2) {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class e
    extends Thread {
        private final int b;
        private boolean c = false;

        public e(int secondsCount, boolean isReStart) {
            this.b = secondsCount;
            this.c = isReStart;
        }

        @Override
        public void run() {
            aq world = aq.a();
            try {
                int secondsCount = this.b;
                world.d("\u4f3a\u670d\u5668\u5373\u5c07\u95dc\u9589\u3002");
                world.d("\u8acb\u73a9\u5bb6\u79fb\u52d5\u5230\u5b89\u5168\u5340\u57df\u5148\u884c\u767b\u51fa");
                while (secondsCount > 0) {
                    if (secondsCount <= 30) {
                        world.a(new ds(72, "" + secondsCount));
                    } else if (secondsCount % 60 == 0) {
                        world.a(new ds(72, "" + secondsCount));
                    }
                    Thread.sleep(1000L);
                    --secondsCount;
                }
                c.this.a(this.c);
            }
            catch (InterruptedException e2) {
                world.d("\u5df2\u53d6\u6d88\u4f3a\u670d\u5668\u95dc\u6a5f\u3002\u4f3a\u670d\u5668\u5c07\u6703\u6b63\u5e38\u904b\u4f5c\u3002");
                return;
            }
        }
    }
}

