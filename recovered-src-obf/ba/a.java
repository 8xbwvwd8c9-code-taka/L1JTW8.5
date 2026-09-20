/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.b;
import aq.aq;
import bi.e;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class a {
    private static a a;
    private final CopyOnWriteArrayList<String> b = new CopyOnWriteArrayList();
    private int c = 0;

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private a() {
        e.a().a(new a(), 60000L, 60000 * l1j.server.a.v);
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            ListIterator iterator;
            ao.b.a().a(a.this.b);
            if (l1j.server.a.w) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String dts = sdf.format(new Date());
                aq.a().d("(" + dts + ")");
            }
            if ((iterator = a.this.b.listIterator()).hasNext()) {
                a a2 = a.this;
                a2.c = a2.c % a.this.b.size();
                aq.a().d((String)a.this.b.get(a.this.c));
                a a3 = a.this;
                a3.c = a3.c + 1;
            }
        }
    }
}

