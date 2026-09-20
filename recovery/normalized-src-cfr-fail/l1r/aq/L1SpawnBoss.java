/*
 * Decompiled with CFR 0.152.
 */
package l1r.aq;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import l1r.aq.L1Spawn;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1SpawnBoss
extends L1Spawn {
    private int b;
    private String c;
    private int d;
    private String e = "";
    private Time f;

    public L1SpawnBoss(L1Npc mobTemplate) {
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

    private long c(String s) {
        long delay = 0L;
        s = s == null ? "" : s;
        try {
            Calendar now = Calendar.getInstance();
            if (this.e != null && this.e.length() > 0 && !this.e.contains("" + (now.get(7) - 1))) {
                String[] week = this.e.split(",");
                long minDelay = 604800000L;
                String[] stringArray = week;
                int n = week.length;
                int n2 = 0;
                while (n2 < n) {
                    String token = stringArray[n2];
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
                    ++n2;
                }
                delay = minDelay;
            }
            if (s.contains("d")) {
                s = s.replace("d", "");
                delay += Long.parseLong(s) * 24L * 60L * 60L * 1000L;
            } else if (s.contains("h")) {
                s = s.replace("h", "");
                delay += Long.parseLong(s) * 60L * 60L * 1000L;
            } else if (s.contains("m")) {
                s = s.replace("m", "");
                delay += Long.parseLong(s) * 60L * 1000L;
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
                        delay += (long)(set_minute - now_minute);
                    }
                }
            }
        }
        catch (NumberFormatException e) {
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
        if (!Config.al && Random.a(100) < this.d || this.r() == 2 || this.e != null) {
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
            GeneralThreadPool.a().a(new L1R_a(0, objectId), delay);
        }
        if (this.d() == 97258) {
            L1World.a().b[1] = (int)((new Date().getTime() + delay) / 1000L);
        } else if (this.d() == 190086) {
            L1World.a().b[2] = (int)((new Date().getTime() + delay) / 1000L);
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

    public void b(String s) {
        this.e = s;
    }

    public Time u() {
        return this.f;
    }

    public void a(Time t) {
        this.f = t;
    }

    private class L1R_a
    implements Runnable {
        private final int b;
        private final int c;

        private L1R_a(int spawnNumber, int objectId) {
            this.b = spawnNumber;
            this.c = objectId;
        }

        @Override
        public void run() {
            L1SpawnBoss.this.b(this.b, this.c);
        }
    }
}
