/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ao;
import ao.z;
import ap.q;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import aq.l;
import ax.b;
import ax.d;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class af {
    private static final Logger d = Logger.getLogger(af.class.getName());
    private static af e;
    private final ConcurrentHashMap<Integer, a> f = new ConcurrentHashMap();
    public static final int a = -1;
    public static final int b = -2;
    public static final int c = -3;

    public static af a() {
        if (e == null) {
            e = new af();
        }
        return e;
    }

    private af() {
        this.b();
        this.c();
    }

    public boolean a(int keyid, int count, int roomid) {
        if (keyid <= 0 || count <= 0 || roomid < 0 || this.f.containsKey(keyid)) {
            return false;
        }
        a data = new a();
        data.a = keyid;
        data.b = "note";
        data.c = count;
        data.d = roomid;
        data.e = new Timestamp(System.currentTimeMillis() + 14400000L);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("INSERT INTO inns SET keyid=?,note=?, count=?, roomid=?, dueTime=? ");
            pstm.setInt(1, data.a);
            pstm.setString(2, data.b);
            pstm.setInt(3, data.c);
            pstm.setInt(4, data.d);
            pstm.setTimestamp(5, data.e);
            pstm.execute();
            this.f.put(data.a, data);
            return true;
        }
        catch (SQLException e2) {
            d.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    private boolean a(int keyid, int count) {
        a data = this.f.get(keyid);
        if (data == null || count <= 0) {
            return false;
        }
        synchronized (data) {
            if (count > data.c) {
                return false;
            }
            int nextCount = data.c - count;
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = l1j.server.b.a().b();
                if (nextCount <= 0) {
                    pstm = con.prepareStatement("DELETE FROM inns WHERE keyid=? AND count=?");
                    pstm.setInt(1, keyid);
                    pstm.setInt(2, data.c);
                } else {
                    pstm = con.prepareStatement("UPDATE inns SET count=? WHERE keyid=? AND count=?");
                    pstm.setInt(1, nextCount);
                    pstm.setInt(2, keyid);
                    pstm.setInt(3, data.c);
                }
                if (pstm.executeUpdate() != 1) {
                    return false;
                }
                if (nextCount <= 0) {
                    this.f.remove(keyid, data);
                } else {
                    data.c = nextCount;
                }
                return true;
            }
            catch (SQLException e2) {
                d.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                return false;
            }
            finally {
                j.a(pstm);
                j.a(con);
            }
        }
    }

    private void b(int keyid) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM inns WHERE keyid = ?");
                    pstm.setInt(1, keyid);
                    pstm.execute();
                    this.f.remove(keyid);
                }
                catch (SQLException e2) {
                    d.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    private void b() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM inns");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getInt("keyid");
                        data.c = rs.getInt("count");
                        data.d = rs.getInt("roomid");
                        data.e = rs.getTimestamp("dueTime");
                        Calendar now = Calendar.getInstance();
                        if (now.getTimeInMillis() > data.e.getTime()) {
                            this.b(data.a);
                            continue;
                        }
                        if (this.f.containsKey(data.a)) continue;
                        this.f.put(data.a, data);
                    }
                }
                catch (SQLException e2) {
                    d.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private void c() {
        int[] baseMapIDs;
        int[] nArray = baseMapIDs = new int[]{16384, 16896, 17408, 17920, 18432, 18944, 19456, 19968, 20480, 20992, 21504, 22016, 22528, 23040, 23552, 24064, 24576, 25088};
        int n2 = baseMapIDs.length;
        int n3 = 0;
        while (n3 < n2) {
            int baseMapID = nArray[n3];
            b map = ax.d.b().a(baseMapID);
            int i2 = 1;
            while (i2 < l1j.server.a.aw) {
                try {
                    b clone = map.s();
                    clone.a = baseMapID + i2;
                    ao.a().a(clone);
                    ax.d.b().a().put(clone.a, clone);
                    z.a().a(baseMapID, clone.a);
                    if (baseMapID == 16384) {
                        l.a().a(new aq.u(32746, 32803, clone.a), new aq.u(32599, 32931, 0));
                    } else if (baseMapID == 16896) {
                        l.a().a(new aq.u(32744, 32808, clone.a), new aq.u(32599, 32931, 0));
                    } else if (baseMapID == 17408) {
                        l.a().a(new aq.u(32744, 32803, clone.a), new aq.u(32631, 32761, 4));
                    } else if (baseMapID == 17920) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(32631, 32761, 4));
                    } else if (baseMapID == 18432) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(33437, 32790, 4));
                    } else if (baseMapID == 18944) {
                        l.a().a(new aq.u(32745, 32087, clone.a), new aq.u(33437, 32790, 4));
                    } else if (baseMapID == 19456) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(34067, 32254, 4));
                    } else if (baseMapID == 19968) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(34067, 32254, 4));
                    } else if (baseMapID == 20480) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(32632, 33165, 4));
                    } else if (baseMapID == 20992) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(32632, 33165, 4));
                    } else if (baseMapID == 21504) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(33112, 33376, 4));
                    } else if (baseMapID == 22016) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(33112, 33376, 4));
                    } else if (baseMapID == 22528) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(33604, 33276, 4));
                    } else if (baseMapID == 23040) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(33604, 33276, 4));
                    } else if (baseMapID == 23552) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(33985, 33312, 4));
                    } else if (baseMapID == 24064) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(33985, 33312, 4));
                    } else if (baseMapID == 24576) {
                        l.a().a(new aq.u(32745, 32803, clone.a), new aq.u(32450, 33047, 440));
                    } else if (baseMapID == 25088) {
                        l.a().a(new aq.u(32745, 32807, clone.a), new aq.u(32450, 33047, 440));
                    }
                }
                catch (CloneNotSupportedException e2) {
                    d.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                ++i2;
            }
            ++n3;
        }
    }

    public boolean a(u pc) {
        q[] qArray = pc.j().d(40312);
        int n2 = qArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Calendar now;
            Timestamp dueTime;
            q item = qArray[n3];
            a data = this.f.get(item.M());
            if (data != null && (dueTime = data.e) != null && (now = Calendar.getInstance()).getTimeInMillis() <= dueTime.getTime()) {
                int teleportX = 0;
                int teleportY = 0;
                int mapid = data.d;
                if (mapid >= 16384 && mapid <= 16895) {
                    teleportX = 32745;
                    teleportY = 32803;
                } else if (mapid >= 16896 && mapid <= 17407) {
                    teleportX = 32743;
                    teleportY = 32808;
                } else if (mapid >= 17408 && mapid <= 17919) {
                    teleportX = 32743;
                    teleportY = 32803;
                } else if (mapid >= 17920 && mapid <= 18431) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 18432 && mapid <= 18943) {
                    teleportX = 32744;
                    teleportY = 32803;
                } else if (mapid >= 18944 && mapid <= 19455) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 19456 && mapid <= 19967) {
                    teleportX = 32744;
                    teleportY = 32803;
                } else if (mapid >= 19968 && mapid <= 20479) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 20480 && mapid <= 20991) {
                    teleportX = 32744;
                    teleportY = 32803;
                } else if (mapid >= 20992 && mapid <= 21503) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 21504 && mapid <= 22016) {
                    teleportX = 32744;
                    teleportY = 32803;
                } else if (mapid >= 22016 && mapid <= 22527) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 22528 && mapid <= 23039) {
                    teleportX = 32744;
                    teleportY = 32803;
                } else if (mapid >= 23040 && mapid <= 23551) {
                    teleportX = 32744;
                    teleportY = 32807;
                } else if (mapid >= 23552 && mapid <= 24063) {
                    teleportX = 32745;
                    teleportY = 32803;
                } else if (mapid >= 24064 && mapid <= 24575) {
                    teleportX = 32745;
                    teleportY = 32807;
                } else if (mapid >= 24576 && mapid <= 25087) {
                    teleportX = 32745;
                    teleportY = 32803;
                } else if (mapid >= 25088 && mapid <= 25599) {
                    teleportX = 32745;
                    teleportY = 32807;
                }
                if (teleportX * teleportY == 0) {
                    return false;
                }
                am.a(pc, teleportX, teleportY, mapid, 6, true, false);
                return true;
            }
            ++n3;
        }
        return false;
    }

    public int b(u pc) {
        long price = 0L;
        q[] qArray = pc.j().d(40312);
        int n2 = qArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Timestamp dueTime;
            q item = qArray[n3];
            a data = this.f.get(item.M());
            if (data != null && (dueTime = data.e) != null) {
                Calendar cal = Calendar.getInstance();
                long refund = 0L;
                if (cal.getTimeInMillis() < dueTime.getTime()) {
                    refund = 60L * (long)item.E();
                    if (refund > Integer.MAX_VALUE || price + refund > Integer.MAX_VALUE) {
                        ++n3;
                        continue;
                    }
                }
                if (!this.a(item.M(), item.E())) {
                    ++n3;
                    continue;
                }
                pc.j().f(item);
                price += refund;
            }
            ++n3;
        }
        return (int)price;
    }

    public boolean a(int roomid) {
        for (a data : this.f.values()) {
            if (data.d != roomid) continue;
            Timestamp dueTime = data.e;
            if (dueTime == null) {
                return true;
            }
            Calendar now = Calendar.getInstance();
            if (now.getTimeInMillis() > dueTime.getTime()) {
                for (aa obj : aq.a().b(roomid).values()) {
                    if (!(obj instanceof u)) continue;
                    u tpc = (u)obj;
                    z.a().b(tpc);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public int a(u pc, int npcid, boolean isHall) {
        q[] qArray = pc.j().d(40312);
        int n2 = qArray.length;
        int n3 = 0;
        while (n3 < n2) {
            q item = qArray[n3];
            a data = this.f.get(item.M());
            if (data != null && !this.a(data.d)) {
                return this.c(data.d) ? -2 : -1;
            }
            ++n3;
        }
        int[] datas = null;
        switch (npcid) {
            case 70012: {
                datas = new int[]{16384, 16896};
                break;
            }
            case 70019: {
                datas = new int[]{17408, 17920};
                break;
            }
            case 70031: {
                datas = new int[]{18432, 18944};
                break;
            }
            case 70065: {
                datas = new int[]{19456, 19968};
                break;
            }
            case 70070: {
                datas = new int[]{20480, 20992};
                break;
            }
            case 70075: {
                datas = new int[]{21504, 22016};
                break;
            }
            case 70084: {
                datas = new int[]{22528, 23040};
                break;
            }
            case 70054: {
                datas = new int[]{23552, 24064};
                break;
            }
            case 70096: {
                datas = new int[]{24576, 25088};
                break;
            }
            default: {
                System.out.println("\u9032\u5165\u623f\u9593\u6216\u6703\u8b70\u5ef3 has some error");
                return -3;
            }
        }
        int useableRoomID = -3;
        int i2 = 0;
        while (i2 < l1j.server.a.aw) {
            int room_baseid = datas[isHall ? 1 : 0];
            if (this.a(room_baseid + i2)) {
                useableRoomID = room_baseid + i2;
                break;
            }
            ++i2;
        }
        return useableRoomID;
    }

    private boolean c(int roomid) {
        if (roomid >= 16896 && roomid <= 17407) {
            return true;
        }
        if (roomid >= 17920 && roomid <= 18431) {
            return true;
        }
        if (roomid >= 18944 && roomid <= 19455) {
            return true;
        }
        if (roomid >= 19968 && roomid <= 20479) {
            return true;
        }
        if (roomid >= 20992 && roomid <= 21503) {
            return true;
        }
        if (roomid >= 22016 && roomid <= 22527) {
            return true;
        }
        if (roomid >= 23040 && roomid <= 23551) {
            return true;
        }
        if (roomid >= 24064 && roomid <= 24575) {
            return true;
        }
        return roomid >= 25088 && roomid <= 25599;
    }

    public static boolean b(int srcX, int srcY, int srcMapId) {
        if (srcX == 32600 && srcY == 32931 && srcMapId == 0) {
            return true;
        }
        if (srcX == 32632 && srcY == 32761 && srcMapId == 4) {
            return true;
        }
        if (srcX == 33112 && srcY == 33376 && srcMapId == 4) {
            return true;
        }
        if (srcX == 32632 && srcY == 33165 && srcMapId == 4) {
            return true;
        }
        if (srcX == 33605 && srcY == 33275 && srcMapId == 4) {
            return true;
        }
        if (srcX == 33437 && srcY == 32789 && srcMapId == 4) {
            return true;
        }
        if (srcX == 34068 && srcY == 32254 && srcMapId == 4) {
            return true;
        }
        if (srcX == 33985 && srcY == 33312 && srcMapId == 4) {
            return true;
        }
        return srcX == 32450 && srcY == 33047 && srcMapId == 440;
    }

    public static String a(q item) {
        StringBuilder name = new StringBuilder();
        name.append(" #");
        String chatText = String.valueOf(item.M());
        String s1 = "";
        String s2 = "";
        int i2 = 0;
        while (i2 < chatText.length()) {
            if (i2 >= 5) break;
            s1 = String.valueOf(s1) + String.valueOf(chatText.charAt(i2));
            ++i2;
        }
        name.append(s1);
        i2 = 0;
        while (i2 < chatText.length()) {
            if (i2 % 2 == 0) {
                s1 = String.valueOf(chatText.charAt(i2));
            } else {
                s2 = String.valueOf(s1) + String.valueOf(chatText.charAt(i2));
                name.append(Integer.toHexString(Integer.valueOf(s2)).toLowerCase());
            }
            ++i2;
        }
        return name.toString();
    }

    private class a {
        public int a;
        public String b;
        public int c;
        public int d;
        public Timestamp e;

        private a() {
        }
    }
}

