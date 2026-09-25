/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.aq;
import ao.ba;
import ao.n;
import ao.o;
import ap.u;
import be.dc;
import bi.e;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private static d b;

    public static d a() {
        if (b == null) {
            b = new d();
        }
        return b;
    }

    public d() {
        this.d();
        this.e();
        if (l1j.server.a.y) {
            this.c();
        }
    }

    private void c() {
        short n2 = l1j.server.a.z;
        int hour = Calendar.getInstance().get(11);
        int minute = Calendar.getInstance().get(12);
        long timeMill = 0L;
        timeMill = hour >= n2 ? (long)((1440 + ((n2 - hour) * 60 - minute)) * 60 * 1000) : (long)(((n2 - hour) * 60 - minute) * 60 * 1000);
        System.out.println("\u4f3a\u670d\u5668\u91cd\u7f6e:\u8ddd\u96e2\u57f7\u884c\u6642\u9593\u9084\u6709..." + timeMill / 1000L / 60L + "\u5206");
        e.a().a(new b(), timeMill);
    }

    private void d() {
        int n2 = 9;
        int hour = Calendar.getInstance().get(11);
        int minute = Calendar.getInstance().get(12);
        long timeMill = 0L;
        timeMill = hour >= 9 ? (long)((1440 + ((9 - hour) * 60 - minute)) * 60 * 1000) : (long)(((9 - hour) * 60 - minute) * 60 * 1000);
        System.out.println("\u8a08\u6642\u5730\u5716/\u6392\u884c\u699c\u91cd\u7f6e:\u8ddd\u96e2\u57f7\u884c\u6642\u9593\u9084\u6709..." + timeMill / 1000L / 60L + "\u5206");
        e.a().a(new a(), timeMill);
    }

    private void e() {
        int n2 = 23;
        int hour = Calendar.getInstance().get(11);
        int minute = Calendar.getInstance().get(12);
        boolean w2 = false;
        int week = Calendar.getInstance().get(7);
        if (Calendar.getInstance().getFirstDayOfWeek() == 1) {
            --week;
        }
        long timeMill = 0L;
        if (week > 0) {
            timeMill = (7 - week - 1) * 24 * 60 * 60 * 1000;
        }
        timeMill = hour >= 23 ? (timeMill += (long)((1440 + ((23 - hour) * 60 - minute)) * 60 * 1000)) : (timeMill += (long)(((23 - hour) * 60 - minute) * 60 * 1000));
        System.out.println("\u6bcf\u9031\u4efb\u52d9\u91cd\u7f6e:\u8ddd\u96e2\u57f7\u884c\u6642\u9593\u9084\u6709..." + timeMill / 1000L / 60L + "\u5206");
        e.a().a(new c(), timeMill);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                for (u pc : aq.aq.a().c()) {
                    pc.ap();
                    pc.y(false);
                }
                o.a().b();
                System.out.println("[\u8a08\u6642\u5730\u5716\u91cd\u7f6e\u4e86]");
                ba.b();
                System.out.println("[\u6392\u884c\u699c\u6392\u540d\u66f4\u65b0\u4e86]");
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            finally {
                System.out.println("\u8a08\u6642\u5730\u5716/\u6392\u884c\u699c\u91cd\u7f6e:\u8ddd\u96e2\u57f7\u884c\u6642\u9593\u9084\u6709...1440\u5206");
                e.a().a(new a(), 86400000L);
            }
        }

    }

    private class b
    extends TimerTask {
        private b() {
        }

        @Override
        public void run() {
            try {
                ai.c.a().a(60, true);
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class c
    extends TimerTask {
        private c() {
        }

        @Override
        public void run() {
            try {
                if (!n.a().deleteAllDurable()) {
                    return;
                }
                for (u pc : aq.aq.a().c()) {
                    pc.a(aq.a().b());
                    n.a().b(pc);
                    pc.a(new dc(810, pc.dY()));
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            finally {
                System.out.println("\u6bcf\u9031\u4efb\u52d9\u91cd\u7f6e:\u8ddd\u96e2\u57f7\u884c\u6642\u9593\u9084\u6709...10080\u5206");
                e.a().a(new c(), 604800000L);
            }
        }

    }
}

