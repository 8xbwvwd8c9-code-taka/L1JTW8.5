/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.af;
import ao.x;
import aq.am;
import aq.u;
import at.c;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class l {
    private static final Logger a = Logger.getLogger(l.class.getName());
    private static l b = null;
    private final HashMap<Long, a> c;
    private static long d = (long)Math.pow(10.0, 10.0);
    private static long e = (long)Math.pow(10.0, 5.0);

    public static l a() {
        if (b == null) {
            b = new l();
        }
        return b;
    }

    private l() {
        block22: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM dungeon");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int srcMapId = rs.getInt("src_mapid");
                        int srcX = rs.getInt("src_x");
                        int srcY = rs.getInt("src_y");
                        long key = (long)srcX * d + (long)srcY * e + (long)srcMapId;
                        int newX = rs.getInt("new_x");
                        int newY = rs.getInt("new_y");
                        int newMapId = rs.getInt("new_mapid");
                        int heading = rs.getInt("new_heading");
                        b dungeonType = aq.l$b.a;
                        if (srcX >= 33430 && srcX <= 33432 && srcY == 33503 && srcMapId == 4 || (srcX == 32733 || srcX == 32734 || srcX == 32735 || srcX == 32736) && srcY == 32794 && srcMapId == 83) {
                            dungeonType = aq.l$b.b;
                        } else if ((srcX == 32935 || srcX == 32936 || srcX == 32937) && srcY == 33058 && srcMapId == 70 || (srcX == 32732 || srcX == 32733 || srcX == 32734 || srcX == 32735) && srcY == 32796 && srcMapId == 84) {
                            dungeonType = aq.l$b.c;
                        } else if ((srcX == 32750 || srcX == 32751 || srcX == 32752) && srcY == 32874 && srcMapId == 445 || (srcX == 32731 || srcX == 32732 || srcX == 32733) && srcY == 32796 && srcMapId == 447) {
                            dungeonType = aq.l$b.d;
                        } else if ((srcX == 32296 || srcX == 32297 || srcX == 32298) && srcY == 33087 && srcMapId == 440 || (srcX == 32735 || srcX == 32736 || srcX == 32737) && srcY == 32794 && srcMapId == 446) {
                            dungeonType = aq.l$b.e;
                        } else if ((srcX == 32630 || srcX == 32631 || srcX == 32632) && srcY == 32983 && srcMapId == 0 || (srcX == 32733 || srcX == 32734 || srcX == 32735) && srcY == 32796 && srcMapId == 5) {
                            dungeonType = aq.l$b.f;
                        } else if ((srcX == 32540 || srcX == 32542 || srcX == 32543 || srcX == 32544 || srcX == 32545) && srcY == 32728 && srcMapId == 4 || (srcX == 32734 || srcX == 32735 || srcX == 32736 || srcX == 32737) && srcY == 32794 && srcMapId == 6) {
                            dungeonType = aq.l$b.g;
                        } else if (srcX == 32810 && srcY >= 32890 && srcY <= 32891 && srcMapId == 10500) {
                            dungeonType = aq.l$b.i;
                        } else if (af.b(srcX, srcY, srcMapId)) {
                            dungeonType = aq.l$b.h;
                        }
                        x.a().a(13135, srcX, srcY, srcMapId);
                        a newDungeon = new a(newX, newY, (short)newMapId, heading, dungeonType);
                        if (this.c.containsKey(key)) {
                            System.out.println("dungeon \u50b3\u9001\u9ede\u91cd\u8907\u3002key=" + key);
                        }
                        this.c.put(key, newDungeon);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block22;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(u src, u dist) {
        long key = (long)src.f() * d + (long)src.g() * e + (long)src.b();
        a newDungeon = new a(dist.f(), dist.g(), (short)dist.b(), 5, aq.l$b.a);
        x.a().a(13135, src.f(), src.g(), src.b());
        if (this.c.containsKey(key)) {
            System.out.println("dungeon \u50b3\u9001\u9ede\u91cd\u8907\u3002key=" + key);
        }
        this.c.put(key, newDungeon);
    }

    public boolean a(int locX, int locY, int mapId, ap.u pc) {
        long key = (long)locX * d + (long)locY * e + (long)mapId;
        if (!this.c.containsKey(key)) {
            return false;
        }
        a newDungeon = this.c.get(key);
        int newMap = newDungeon.d;
        int newX = newDungeon.b;
        int newY = newDungeon.c;
        int heading = newDungeon.e;
        b dungeonType = newDungeon.f;
        boolean teleportable = false;
        if (dungeonType == aq.l$b.a) {
            teleportable = true;
        } else if (dungeonType == aq.l$b.i) {
            if (pc.dX() == 4) {
                newX = 32774;
                newY = 32816;
            } else if (pc.dX() == 5) {
                newX = 32694;
                newY = 32895;
            } else {
                newX = 32774;
                newY = 32975;
            }
            teleportable = true;
        } else {
            int nowtime;
            if (dungeonType == aq.l$b.h) {
                return af.a().a(pc);
            }
            if (dungeonType == aq.l$b.f && pc.j().g(40299, 1) || dungeonType == aq.l$b.b && pc.j().g(40300, 1) || dungeonType == aq.l$b.d && pc.j().g(40302, 1)) {
                int nowtime2 = at.c.a().b().c() % 86400;
                if (nowtime2 >= 5400 && nowtime2 < 9000 || nowtime2 >= 16200 && nowtime2 < 19800 || nowtime2 >= 27000 && nowtime2 < 30600 || nowtime2 >= 37800 && nowtime2 < 41400 || nowtime2 >= 48600 && nowtime2 < 52200 || nowtime2 >= 59400 && nowtime2 < 63000 || nowtime2 >= 70200 && nowtime2 < 73800 || nowtime2 >= 81000 && nowtime2 < 84600) {
                    teleportable = true;
                }
            } else if ((dungeonType == aq.l$b.g && pc.j().g(40298, 1) || dungeonType == aq.l$b.c && pc.j().g(40308, 1000) || dungeonType == aq.l$b.e && pc.j().g(40303, 1)) && ((nowtime = at.c.a().b().c() % 86400) >= 0 && nowtime < 360 || nowtime >= 10800 && nowtime < 14400 || nowtime >= 21600 && nowtime < 25200 || nowtime >= 32400 && nowtime < 36000 || nowtime >= 43200 && nowtime < 46800 || nowtime >= 54000 && nowtime < 57600 || nowtime >= 64800 && nowtime < 68400 || nowtime >= 75600 && nowtime < 79200)) {
                teleportable = true;
            }
        }
        if (teleportable) {
            pc.j(78, 2000);
            pc.b();
            pc.d();
            am.a(pc, newX, newY, newMap, heading, false);
            return true;
        }
        return false;
    }

    class a {
        private final int b;
        private final int c;
        private final int d;
        private final int e;
        private final b f;

        private a(int newX, int newY, short newMapId, int heading, b dungeonType) {
            this.b = newX;
            this.c = newY;
            this.d = newMapId;
            this.e = heading;
            this.f = dungeonType;
        }
    }

    private static enum b {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h,
        i;

    }
}

