/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.ao;
import ao.au;
import ap.s;
import ap.t;
import ap.u;
import aq.ag;
import aq.ai;
import aq.aq;
import aq.f;
import bh.l;
import bi.e;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bg {
    private static final Logger a = Logger.getLogger(bg.class.getName());
    private static bg b;
    private final HashMap<Integer, ag> c = new HashMap();
    private int d;

    public static bg a() {
        if (b == null) {
            b = new bg();
        }
        return b;
    }

    private bg() {
        long begin = System.currentTimeMillis();
        System.out.print("spawning mob...");
        this.b();
        System.out.println("OK! " + (System.currentTimeMillis() - begin) + " ms");
    }

    private void b() {
        block11: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        ag spawnDat;
                        int npcTemplateId = rs.getInt("npc_templateid");
                        l template1 = au.a().a(npcTemplateId);
                        if (template1 == null) {
                            System.out.println("mob data for id:" + npcTemplateId + " missing in npc table");
                            spawnDat = null;
                        } else {
                            if (rs.getInt("count") == 0) continue;
                            double amount_rate = ao.a().a(rs.getShort("mapid"));
                            int count = bg.a(template1, rs.getInt("count"), amount_rate);
                            if (count == 0) continue;
                            spawnDat = new ag(template1);
                            spawnDat.a(rs.getInt("id"));
                            spawnDat.b(count);
                            spawnDat.d(rs.getInt("group_id"));
                            spawnDat.e(rs.getInt("locx"));
                            spawnDat.f(rs.getInt("locy"));
                            spawnDat.g(rs.getInt("randomx"));
                            spawnDat.h(rs.getInt("randomy"));
                            spawnDat.i(rs.getInt("locx1"));
                            spawnDat.j(rs.getInt("locy1"));
                            spawnDat.k(rs.getInt("locx2"));
                            spawnDat.l(rs.getInt("locy2"));
                            spawnDat.m(rs.getInt("heading"));
                            spawnDat.n(rs.getInt("min_respawn_delay"));
                            spawnDat.o(rs.getInt("max_respawn_delay"));
                            spawnDat.p(rs.getShort("mapid"));
                            spawnDat.a(rs.getBoolean("respawn_screen"));
                            spawnDat.q(rs.getInt("movement_distance"));
                            spawnDat.b(rs.getBoolean("rest"));
                            spawnDat.r(rs.getInt("near_spawn"));
                            spawnDat.c(rs.getBoolean("night"));
                            if (count > 1 && spawnDat.j() == 0) {
                                int range = Math.min(count * 6, 30);
                                if (spawnDat.h() > 0 && spawnDat.i() > 0) {
                                    range = (spawnDat.h() + spawnDat.i()) / 2;
                                }
                                spawnDat.i(spawnDat.f() - range);
                                spawnDat.j(spawnDat.g() - range);
                                spawnDat.k(spawnDat.f() + range);
                                spawnDat.l(spawnDat.g() + range);
                            }
                            spawnDat.a();
                        }
                        if (spawnDat == null) {
                            System.out.println("spawntable has some error");
                            continue;
                        }
                        this.c.put(new Integer(spawnDat.b()), spawnDat);
                        if (spawnDat.b() <= this.d) continue;
                        this.d = spawnDat.b();
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block11;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public ag a(int Id) {
        return this.c.get(new Integer(Id));
    }

    public static void a(u pc, l npc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    boolean count = true;
                    int randomXY = 12;
                    int minRespawnDelay = 60;
                    int maxRespawnDelay = 120;
                    String note = npc.c();
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO spawnlist SET location=?,count=?,npc_templateid=?,group_id=?,locx=?,locy=?,randomx=?,randomy=?,heading=?,min_respawn_delay=?,max_respawn_delay=?,mapid=?", Statement.RETURN_GENERATED_KEYS);
                    pstm.setString(1, note);
                    pstm.setInt(2, 1);
                    pstm.setInt(3, npc.b());
                    pstm.setInt(4, 0);
                    pstm.setInt(5, pc.fs());
                    pstm.setInt(6, pc.ft());
                    pstm.setInt(7, 12);
                    pstm.setInt(8, 12);
                    pstm.setInt(9, pc.fb());
                    pstm.setInt(10, 60);
                    pstm.setInt(11, 120);
                    pstm.setInt(12, pc.fp());
                    if (pstm.executeUpdate() <= 0) {
                        return;
                    }
                    try (ResultSet keys = pstm.getGeneratedKeys()) {
                        if (!keys.next()) {
                            return;
                        }
                        int id = keys.getInt(1);
                        ag spawn = new ag(npc);
                        spawn.a(id);
                        spawn.b(1);
                        spawn.e(pc.fs());
                        spawn.f(pc.ft());
                        spawn.g(12);
                        spawn.h(12);
                        spawn.i(0);
                        spawn.j(0);
                        spawn.k(0);
                        spawn.l(0);
                        spawn.m(pc.fb());
                        spawn.n(60);
                        spawn.o(120);
                        spawn.p(pc.fp());
                        spawn.q(0);
                        bg table = bg.a();
                        synchronized (table.c) {
                            table.c.put(id, spawn);
                            if (id > table.d) {
                                table.d = id;
                            }
                        }
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    private static int a(l npc, int count, double rate) {
        if (rate == 0.0) {
            return 0;
        }
        if (rate == 1.0 || npc.af()) {
            return count;
        }
        return bg.a((double)count * rate);
    }

    private static int a(double number) {
        double percentage = (number - Math.floor(number)) * 100.0;
        if (percentage == 0.0) {
            return (int)number;
        }
        int r2 = i.a(100);
        if ((double)r2 < percentage) {
            return (int)number + 1;
        }
        return (int)number;
    }

    public static void a(int npcid, f cha, long delayMillis) {
        bg.a(npcid, cha.fs(), cha.ft(), cha.fp(), delayMillis);
    }

    public static void a(int npcid, int x2, int y2, int mapid, long delayMillis) {
        if (delayMillis > 0L) {
            new a(npcid, x2, y2, mapid, 5, delayMillis, null).a();
        } else {
            bg.b(npcid, x2, y2, mapid, 5, 0, 0L, null, false, 0);
        }
    }

    public static void a(int npcid, int x2, int y2, int mapid, int heading, long delayMillis, String clanName) {
        if (delayMillis > 0L) {
            new a(npcid, x2, y2, mapid, heading, delayMillis, clanName).a();
        } else {
            bg.b(npcid, x2, y2, mapid, 5, 0, 0L, clanName, false, 0);
        }
    }

    public static void a(int npcid, int x2, int y2, int mapid) {
        bg.b(npcid, x2, y2, mapid, 5, 0, 0L, null, false, 0);
    }

    public static t a(int npcid, int x2, int y2, int mapid, int heading, boolean isNoDrop) {
        return bg.b(npcid, x2, y2, mapid, heading, 0, 0L, null, isNoDrop, 0);
    }

    public static t a(int npcid, int x2, int y2, int mapid, int heading, int randomRange, boolean isNoDrop) {
        return bg.b(npcid, x2, y2, mapid, heading, randomRange, 0L, null, isNoDrop, 0);
    }

    public static void a(int npcid, int x2, int y2, int mapid, int heading, String clanName) {
        bg.b(npcid, x2, y2, mapid, heading, 0, 0L, clanName, false, 0);
    }

    public static void a(int npcid, int x2, int y2, int mapid, int heading, String clanName, int gfxid) {
        bg.b(npcid, x2, y2, mapid, heading, 0, 0L, clanName, false, gfxid);
    }

    public static void a(int npcid, f cha, int randomRange, long timeMillisToDelete) {
        bg.b(npcid, cha.fs(), cha.ft(), cha.fp(), cha.fb(), randomRange, timeMillisToDelete, null, false, 0);
    }

    public static void a(int npcid, int x2, int y2, int mapid, int heading, long timeMillisToDelete) {
        bg.b(npcid, x2, y2, mapid, heading, 0, timeMillisToDelete, null, false, 0);
    }

    private static t b(int npcid, int x2, int y2, int mapid, int heading, int randomRange, long timeMillisToDelete, String clanName, boolean isNoDrop, int gfxid) {
        try {
            t npc = au.a().b(npcid);
            if (npc.U_().d().equals("L1Attacker")) {
                ai.a().a(12261, 1000, x2, y2, mapid);
            }
            npc.cF(ai.d.a().c());
            npc.cE(mapid);
            if (randomRange == 0) {
                npc.fu().a(x2, y2, mapid);
            } else {
                int tryCount = 0;
                while (tryCount < 50) {
                    ++tryCount;
                    npc.cG(x2 + i.a(randomRange) - i.a(randomRange));
                    npc.cH(y2 + i.a(randomRange) - i.a(randomRange));
                    if (npc.fq().a(npc.fu()) && npc.fq().c(npc.fs(), npc.ft())) break;
                }
                if (tryCount >= 50) {
                    npc.fu().a(x2, y2, mapid);
                    npc.fu().d(heading);
                }
            }
            npc.q(npc.fs());
            npc.r(npc.ft());
            npc.ct(heading);
            npc.b(clanName);
            if (npc instanceof s) {
                ((s)npc).c(isNoDrop);
            }
            if (gfxid > 0) {
                npc.cw(gfxid);
            }
            aq.a().a(npc);
            aq.a().c(npc);
            if (npc instanceof s) {
                ((s)npc).b(true);
            }
            npc.fg();
            npc.a_(0);
            if (timeMillisToDelete > 0L) {
                npc.a(timeMillisToDelete);
            }
            return npc;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return null;
        }
    }

    public static void a(int summonId, t npc, int count, f target) {
        int heading = i.a(8);
        int randomRange = 8;
        int i2 = 0;
        while (i2 < count) {
            t summon = bg.b(summonId, npc.fs(), npc.ft(), npc.fp(), heading, 8, 0L, null, true, 0);
            if (target != null) {
                summon.c(target, 1);
            }
            ++i2;
        }
    }

    private static class a
    extends TimerTask {
        private final int a;
        private final int b;
        private final int c;
        private final int d;
        private final long e;
        private final String f;
        private final int g;

        private a(int _npcid, int _x, int _y, int _mapid, int _heading, long _delayMillis, String _clanName) {
            this.a = _x;
            this.b = _y;
            this.c = _mapid;
            this.d = _npcid;
            this.e = _delayMillis;
            this.f = _clanName;
            this.g = _heading;
        }

        @Override
        public void run() {
            bg.b(this.d, this.a, this.b, this.c, this.g, 0, 0L, this.f, false, 0);
        }

        private void a() {
            bi.e.a().a(this, this.e);
        }
    }
}

