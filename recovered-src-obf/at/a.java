/*
 * Decompiled with CFR 0.152.
 */
package at;

import bi.f;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class a {
    private final int a;
    private final Calendar b;

    private Calendar b(int time) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(0L);
        cal.add(13, time);
        return cal;
    }

    private a(int time) {
        this.a = time;
        this.b = this.b(time);
    }

    private static a a(long timeMillis) {
        long t1 = timeMillis;
        if (t1 < 0L) {
            throw new IllegalArgumentException();
        }
        int t2 = (int)(t1 * 6L / 1000L);
        int t3 = t2 % 3;
        return new a(t2 - t3);
    }

    public static a a() {
        return at.a.a(System.currentTimeMillis());
    }

    public Time b() {
        int t2 = this.a % 86400;
        return new Time((long)t2 * 1000L - (long)TimeZone.getDefault().getRawOffset());
    }

    public int a(int field) {
        return this.b.get(field);
    }

    public int c() {
        return this.a;
    }

    public Calendar d() {
        return (Calendar)this.b.clone();
    }

    public boolean e() {
        int hour = this.b.get(11);
        return !f.a(hour, 6, 17);
    }

    public String toString() {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        f2.setTimeZone(this.b.getTimeZone());
        return String.valueOf(f2.format(this.b.getTime())) + "(" + this.c() + ")";
    }
}

