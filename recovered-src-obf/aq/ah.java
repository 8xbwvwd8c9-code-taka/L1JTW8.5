/*
 * Decompiled with CFR 0.152.
 */
package aq;

import aq.ag;
import aq.aq;
import bh.l;
import bi.e;
import bi.i;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class ah
extends ag {
    private int b;
    private String c;
    private int d;
    private String e = "";
    private Time f;

    public ah(l mobTemplate) {
        super(mobTemplate);
    }

    @Override
    public void a(int spawnNumber, int objectId) {
        if (this.v() != 0) {
            return;
        }
        this.a(this.c(this.c), objectId);
    }

    private synchronized int v() {
        return --this.b;
    }

    public void a(String type) {
        this.c = type;
    }

    public void s(int percentage) {
        this.d = percentage;
    }

    private long c(String s2) {
        long delay = 0L;
        s2 = s2 == null ? "" : s2;
        try {
            Calendar now = Calendar.getInstance();
            if (this.e != null && this.e.length() > 0 && !this.e.contains("" + (now.get(7) - 1))) {
                String[] week = this.e.split(",");
                long minDelay = 604800000L;
                String[] stringArray = week;
                int n2 = week.length;
                int n3 = 0;
                while (n3 < n2) {
                    String token = stringArray[n3];
                    if (token.trim().length() != 0) {
                        long nowDelay;
                        int nowNum;
                        int weekNum = Integer.parseInt(token);
                        int diff = weekNum - (nowNum = now.get(7) - 1);
                        if (diff < 0) {
                            diff += 7;
                        }
                        if ((nowDelay = (long)(diff * 24 * 60 * 60 * 1000)) < minDelay) {
                            minDelay = nowDelay;
                        }
                    }
                    ++n3;
                }
                delay = minDelay;
            }
            if (s2.contains("d")) {
                s2 = s2.replace("d", "");
                delay += Long.parseLong(s2) * 24L * 60L * 60L * 1000L;
            } else if (s2.contains("h")) {
                s2 = s2.replace("h", "");
                delay += Long.parseLong(s2) * 60L * 60L * 1000L;
            } else if (s2.contains("m")) {
                s2 = s2.replace("m", "");
                delay += Long.parseLong(s2) * 60L * 1000L;
            } else if (this.f != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new java.sql.Date(this.f.getTime()));
                int now_hour = now.get(11);
                int now_minute = now.get(12);
                int set_hour = cal.get(11);
                int set_minute = cal.get(12);
                if (now_hour > set_hour) {
                    delay += (long)((1440 + ((set_hour - now_hour) * 60 + (set_minute - now_minute))) * 60 * 1000);
                } else if (now_hour < set_hour) {
                    delay += (long)(((set_hour - now_hour) * 60 + (set_minute - now_minute)) * 60 * 1000);
                } else if (now_hour == set_hour) {
                    if (now_minute >= set_minute) {
                        delay += (long)((1440 + ((set_hour - now_hour) * 60 + (set_minute - now_minute))) * 60 * 1000);
                    } else if (now_minute < set_minute) {
                        delay += (long)(set_minute - now_minute) * 60L * 1000L;
                    }
                }
            }
        }
        catch (NumberFormatException e2) {
            System.out.println("DB:spawnlist_boss \u4e0d\u6b63\u78ba\u7684\u65e5\u671f\u683c\u5f0f");
            delay = 3600000L;
        }
        return delay;
    }

    @Override
    public void a() {
        if (this.d <= 0) {
            return;
        }
        long delay = 0L;
        if (!l1j.server.a.al && i.a(100) < this.d || this.r() == 2 || this.e != null) {
            delay = this.c(this.c);
        }
        this.a(delay, 0);
    }

    private void a(long delay, int objectId) {
        if (delay < 0L) {
            return;
        }
        int cnt = this.b;
        this.b = this.c();
        while (cnt < this.c()) {
            ++cnt;
            bi.e.a().a(new a(0, objectId), delay);
        }
        if (this.d() == 97258) {
            aq.a().b[1] = (int)((new Date().getTime() + delay) / 1000L);
        } else if (this.d() == 190086) {
            aq.a().b[2] = (int)((new Date().getTime() + delay) / 1000L);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[MOB]npcid:" + this.d());
        builder.append("[\u73fe\u5728\u306e\u5468\u671f]");
        builder.append(" - ");
        builder.append("[\u51fa\u73fe\u6642\u9593]");
        return builder.toString();
    }

    public String t() {
        return this.e;
    }

    public void b(String s2) {
        this.e = s2;
    }

    public Time u() {
        return this.f;
    }

    public void a(Time t2) {
        this.f = t2;
    }

    private class a
    implements Runnable {
        private final int b;
        private final int c;

        private a(int spawnNumber, int objectId) {
            this.b = spawnNumber;
            this.c = objectId;
        }

        @Override
        public void run() {
            ah.this.b(this.b, this.c);
        }
    }
}

