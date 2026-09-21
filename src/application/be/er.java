/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.util.Calendar;
import l1j.server.a;

public class er
extends eu {
    public er(Calendar cal) {
        Calendar base_cal = Calendar.getInstance();
        base_cal.set(1997, 0, 1, 17, 0);
        long base_millis = base_cal.getTimeInMillis();
        long millis = cal.getTimeInMillis();
        long diff = millis - base_millis;
        diff -= 72000000L;
        int time = (int)((diff /= 60000L) / 182L);
        this.c(214);
        this.b(6);
        this.a(a.l);
        this.c(0);
        this.c(0);
        this.c(0);
        this.a(time);
        this.c(0);
        this.a(time - 1);
        this.c(0);
        this.a(time - 2);
        this.c(0);
        this.a(time - 3);
        this.c(0);
        this.a(time - 4);
        this.c(0);
        this.a(time - 5);
        this.c(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_WarTime";
    }
}

