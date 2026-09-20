/*
 * Decompiled with CFR 0.152.
 */
package bi;

import at.a;
import java.sql.Time;

public class k {
    private final Time a;
    private final Time b;

    public k(Time timeStart, Time timeEnd) {
        if (timeStart.equals(timeEnd)) {
            throw new IllegalArgumentException("timeBegin must not equals timeEnd");
        }
        this.a = timeStart;
        this.b = timeEnd;
    }

    private boolean a(a time, Time timeStart, Time timeEnd) {
        Time when = time.b();
        return timeStart.compareTo(when) <= 0 && timeEnd.compareTo(when) > 0;
    }

    public boolean a(a time) {
        return this.a.after(this.b) ? !this.a(time, this.b, this.a) : this.a(time, this.a, this.b);
    }
}

