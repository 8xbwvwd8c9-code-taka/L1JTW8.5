/*
 * Decompiled with CFR 0.152.
 */
package bj;

import ai.e;
import ap.u;
import aq.aq;
import be.aj;
import be.eu;
import bi.g;
import bi.i;
import bj.b;
import bj.c;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d
implements Runnable {
    private static final Logger c = Logger.getLogger(d.class.getName());
    private final InputStream d;
    private final OutputStream e;
    private final e f;
    private a g;
    private final Socket h;
    private b i;
    private c j;
    public int a = 0;
    public long b = 0L;
    private eu k;
    private bh.a l;
    private u m = null;
    private final String n;
    private long o = System.currentTimeMillis();
    private long p = System.currentTimeMillis();
    private u q;
    private CopyOnWriteArrayList<String> r = new CopyOnWriteArrayList();
    private boolean s = false;
    private boolean t = false;

    public d(Socket socket) throws IOException {
        this.h = socket;
        this.n = socket.getInetAddress().getHostAddress();
        this.d = socket.getInputStream();
        this.e = new BufferedOutputStream(socket.getOutputStream());
        this.f = new e(this);
    }

    private byte[] k() throws Exception {
        int hiByte = this.d.read();
        int loByte = this.d.read();
        if (hiByte < 0 || loByte < 0) {
            c.log(Level.SEVERE, " \u3010\u95dc\u9589\u9023\u7dda\u3011\u5ba2\u6236\u7aef\u65b7\u7dda\u4e86 ip=" + this.n);
            return null;
        }
        int dataLength = (loByte << 8) + hiByte - 2;
        if (dataLength <= 0 || dataLength > 65533) {
            c.log(Level.SEVERE, " \u3010\u95dc\u9589\u9023\u7dda\u3011\u5ba2\u6236\u7aef\u5c01\u5305\u9577\u5ea6\u904e\u5927\u6216\u904e\u5c0f ip=" + this.n);
            return null;
        }
        byte[] data = new byte[dataLength];
        int readSize = 0;
        int i2 = 0;
        while (i2 != -1 && readSize < dataLength) {
            i2 = this.d.read(data, readSize, dataLength - readSize);
            readSize += i2;
        }
        if (readSize != dataLength) {
            c.log(Level.SEVERE, " \u3010\u95dc\u9589\u9023\u7dda\u3011\u5ba2\u6236\u7aef\u5c01\u5305\u9577\u5ea6\u4e0d\u7b26\u5408 ip=" + this.n);
            return null;
        }
        return this.i.b(data);
    }

    @Override
    public void run() {
        try {
            try {
                this.g = new a();
                this.g.a();
                this.a = bi.i.a(Integer.MAX_VALUE) + 1;
                int Bogus = 7;
                this.e.write(7);
                this.e.write(0);
                this.e.write(165);
                this.e.write((byte)(this.a & 0xFF));
                this.e.write((byte)(this.a >> 8 & 0xFF));
                this.e.write((byte)(this.a >> 16 & 0xFF));
                this.e.write((byte)(this.a >> 24 & 0xFF));
                this.e.flush();
                System.out.println("(" + this.n + ") \u9023\u7d50\u5230\u4f3a\u670d\u5668\u3002");
                System.out.println("\u4f7f\u7528\u4e86 " + bi.g.a() + "MB \u7684\u8a18\u61b6\u9ad4");
                System.out.println(bi.e.a().b());
                System.out.println("\u7b49\u5f85\u5ba2\u6236\u7aef\u9023\u63a5...");
                this.i = new b(this.a);
                this.j = new c(this.a);
                while (!this.s) {
                    this.l();
                    byte[] receive = this.k();
                    if (receive == null) {
                        byte[] content;
                        if (this.k != null && ((content = this.k.a())[0] & 0xFF) != 207) {
                            String text = "opcode: " + (content[0] & 0xFF) + " [" + this.k.b() + "]\r\n" + bi.g.a(content);
                            bi.g.a("./data/log/packetLog.txt", text);
                        }
                        break;
                    }
                    byte[] data = new byte[receive.length - 4];
                    System.arraycopy(receive, 4, data, 0, data.length);
                    int opcode = data[0] & 0xFF;
                    if (opcode == 158 && this.m != null) continue;
                    if (opcode == 171 || opcode == 72) {
                        long now;
                        this.b = now = System.currentTimeMillis();
                    }
                    this.f.a(data);
                }
            }
            catch (SocketException Bogus) {
                this.c();
            }
            catch (Exception e2) {
                c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                this.c();
            }
        }
        finally {
            this.c();
        }
    }

    public String a() {
        if (this.l == null) {
            return null;
        }
        return this.l.d();
    }

    private void l() {
        if (this.m == null) {
            return;
        }
        if ((long)(l1j.server.a.s * 1000) < System.currentTimeMillis() - this.o) {
            this.m.I();
            this.o = System.currentTimeMillis();
        }
        if ((long)(l1j.server.a.t * 1000) < System.currentTimeMillis() - this.p) {
            this.m.J();
            this.p = System.currentTimeMillis();
        }
    }

    public String b() {
        String result = null;
        if (!this.r.isEmpty()) {
            result = this.r.get(0);
            this.r.remove(0);
        }
        return result;
    }

    public void a(int reason) {
        try {
            try {
                this.a(new aj(reason));
            }
            catch (Exception e2) {
                c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                this.c();
            }
        }
        finally {
            this.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c() {
        block21: {
            if (this.s) {
                return;
            }
            this.s = true;
            ai.c.a().b(this);
            try {
                try {
                    this.h.close();
                }
                catch (Exception e2) {
                    c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    if (this.m != null) {
                        u u2 = this.m;
                        synchronized (u2) {
                            this.m.p();
                            this.m = null;
                        }
                    }
                    if (this.l != null) {
                        ao.a.a().a(this.l, false);
                        ao.a.a().b(this.l, false);
                        ao.a.a().e(this.l.d());
                    }
                    System.out.println(String.valueOf(this.n) + " \u3010\u95dc\u9589\u9023\u7dda\u3011(" + aq.a().c().size() + ")");
                    System.out.println(bi.e.a().b());
                    System.out.println("\u4f7f\u7528\u4e86: " + bi.g.a() + "MB \u7684\u8a18\u61b6\u9ad4");
                    break block21;
                }
            }
            catch (Throwable throwable) {
                if (this.m != null) {
                    u u3 = this.m;
                    synchronized (u3) {
                        this.m.p();
                        this.m = null;
                    }
                }
                if (this.l != null) {
                    ao.a.a().a(this.l, false);
                    ao.a.a().b(this.l, false);
                    ao.a.a().e(this.l.d());
                }
                System.out.println(String.valueOf(this.n) + " \u3010\u95dc\u9589\u9023\u7dda\u3011(" + aq.a().c().size() + ")");
                System.out.println(bi.e.a().b());
                System.out.println("\u4f7f\u7528\u4e86: " + bi.g.a() + "MB \u7684\u8a18\u61b6\u9ad4");
                throw throwable;
            }
            if (this.m != null) {
                u u4 = this.m;
                synchronized (u4) {
                    this.m.p();
                    this.m = null;
                }
            }
            if (this.l != null) {
                ao.a.a().a(this.l, false);
                ao.a.a().b(this.l, false);
                ao.a.a().e(this.l.d());
            }
            System.out.println(String.valueOf(this.n) + " \u3010\u95dc\u9589\u9023\u7dda\u3011(" + aq.a().c().size() + ")");
            System.out.println(bi.e.a().b());
            System.out.println("\u4f7f\u7528\u4e86: " + bi.g.a() + "MB \u7684\u8a18\u61b6\u9ad4");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void d() {
        block17: {
            if (this.s) {
                return;
            }
            this.s = true;
            ai.c.a().b(this);
            try {
                try {
                    this.h.close();
                }
                catch (Exception e2) {
                    c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    if (this.m == null) break block17;
                    u u2 = this.m;
                    synchronized (u2) {
                        this.m.p();
                        this.m = null;
                        break block17;
                    }
                }
            }
            catch (Throwable throwable) {
                if (this.m != null) {
                    u u3 = this.m;
                    synchronized (u3) {
                        this.m.p();
                        this.m = null;
                    }
                }
                throw throwable;
            }
            if (this.m != null) {
                u u4 = this.m;
                synchronized (u4) {
                    this.m.p();
                    this.m = null;
                }
            }
        }
    }

    public void a(eu packet) {
        this.g.a(packet);
    }

    public bh.a e() {
        return this.l;
    }

    public void a(bh.a account) {
        this.l = account;
    }

    public u f() {
        return this.m;
    }

    public void a(u activeChar) {
        this.m = activeChar;
    }

    public String g() {
        return this.n;
    }

    public u h() {
        return this.q;
    }

    public void b(u dummyCharData) {
        this.q = dummyCharData;
    }

    public CopyOnWriteArrayList<String> i() {
        return this.r;
    }

    public void a(CopyOnWriteArrayList<String> newsList) {
        this.r = newsList;
    }

    public boolean j() {
        return this.t;
    }

    public void a(boolean isLoadCraft) {
        this.t = isLoadCraft;
    }

    class a
    extends TimerTask {
        private final Queue<eu> b = new ConcurrentLinkedQueue<eu>();

        private a() {
        }

        private final void a(eu packet) {
            this.b.offer(packet);
        }

        private void a() {
            bi.e.a().b(this, 0L);
        }

        private void a(int delay) {
            bi.e.a().b(this, delay);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                if (d.this.s) {
                    throw new Exception();
                }
                while (true) {
                    if (this.b.isEmpty()) {
                        this.a(10);
                        return;
                    }
                    eu packet = this.b.poll();
                    byte[] content = packet.a();
                    if (content == null || content.length <= 0) continue;
                    byte[] data = (byte[])content.clone();
                    d.this.j.b(data);
                    int length = data.length + 2;
                    byte[] send = new byte[length];
                    System.arraycopy(data, 0, send, 2, data.length);
                    send[0] = (byte)(length & 0xFF);
                    send[1] = (byte)(length >> 8 & 0xFF);
                    d.this.e.write(send);
                    d.this.e.flush();
                    d.this.k = packet;
                }
            }
            catch (Exception e2) {
                this.b.clear();
                d.this.c();
            }
        }
    }
}

