/*
 * Decompiled with CFR 0.152.
 */
package bi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class e {
    private static final Logger a = Logger.getLogger(e.class.getName());
    private final Executor b;
    private final ScheduledExecutorService c;
    private final Executor d;
    private final ScheduledExecutorService e;
    private static e f;
    private int g = l1j.server.a.o;
    private int h = l1j.server.a.o * 3;
    private int i;
    private int j = l1j.server.a.o * 2;

    public static e a() {
        if (f == null) {
            f = new e();
        }
        return f;
    }

    private e() {
        this.i = l1j.server.a.o * 3;
        if (l1j.server.a.c) {
            this.g = 200;
            this.h = 200;
            this.j = 400;
            this.i = 400;
        }
        this.b = Executors.newScheduledThreadPool(this.j);
        this.c = Executors.newScheduledThreadPool(this.i, new a("GerenalSTPool", 5));
        this.d = Executors.newScheduledThreadPool(this.h);
        this.e = Executors.newScheduledThreadPool(this.g, new a("PcMonitorSTPool", 5));
    }

    public String b() {
        String result = "";
        result = String.valueOf(result) + "\u7cfb\u7d71\u7dda\u7a0b\u6c60\u4f7f\u7528\u91cf:";
        result = String.valueOf(result) + "[EXECUTOR:" + ((ThreadPoolExecutor)this.b).getActiveCount() + "/" + this.j + "]\t";
        result = String.valueOf(result) + "[SCHEDULED:" + ((ThreadPoolExecutor)((Object)this.c)).getActiveCount() + "/" + this.i + "]\r\n";
        result = String.valueOf(result) + "\u73a9\u5bb6\u7dda\u7a0b\u6c60\u4f7f\u7528\u91cf:";
        result = String.valueOf(result) + "[EXECUTOR:" + ((ThreadPoolExecutor)this.d).getActiveCount() + "/" + this.h + "]\t";
        result = String.valueOf(result) + "[SCHEDULED:" + ((ThreadPoolExecutor)((Object)this.e)).getActiveCount() + "/" + this.g + "]";
        return result;
    }

    public void a(Runnable r2) {
        try {
            this.b.execute(r2);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
        }
    }

    public ScheduledFuture<?> a(Runnable r2, long delay) {
        block5: {
            if (delay > 0L) break block5;
            this.b.execute(r2);
            return null;
        }
        try {
            return this.c.schedule(r2, delay, TimeUnit.MILLISECONDS);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
            return null;
        }
        catch (RejectedExecutionException e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            return null;
        }
        catch (Exception e4) {
            a.log(Level.SEVERE, e4.getLocalizedMessage(), e4);
            return null;
        }
    }

    public ScheduledFuture<?> a(Runnable r2, long initialDelay, long period) {
        try {
            return this.c.scheduleAtFixedRate(r2, initialDelay, period, TimeUnit.MILLISECONDS);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
            return null;
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            return null;
        }
    }

    public void b(Runnable r2) {
        try {
            this.d.execute(r2);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
        }
    }

    public ScheduledFuture<?> b(Runnable r2, long delay) {
        block5: {
            if (delay > 0L) break block5;
            this.d.execute(r2);
            return null;
        }
        try {
            return this.e.schedule(r2, delay, TimeUnit.MILLISECONDS);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
            return null;
        }
        catch (RejectedExecutionException e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            return null;
        }
        catch (Exception e4) {
            a.log(Level.SEVERE, e4.getLocalizedMessage(), e4);
            return null;
        }
    }

    public ScheduledFuture<?> b(Runnable r2, long initialDelay, long period) {
        try {
            return this.e.scheduleAtFixedRate(r2, initialDelay, period, TimeUnit.MILLISECONDS);
        }
        catch (OutOfMemoryError e2) {
            a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
            a.log(Level.SEVERE, this.b());
            return null;
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            return null;
        }
    }

    private class a
    implements ThreadFactory {
        private final int b;
        private final String c;
        private final AtomicInteger d = new AtomicInteger(1);
        private final ThreadGroup e;

        private a(String name, int prio) {
            this.b = prio;
            this.c = name;
            this.e = new ThreadGroup(this.c);
        }

        @Override
        public Thread newThread(Runnable r2) {
            Thread t2 = new Thread(this.e, r2);
            t2.setName(String.valueOf(this.c) + "-" + this.d.getAndIncrement());
            t2.setPriority(this.b);
            return t2;
        }
    }
}

