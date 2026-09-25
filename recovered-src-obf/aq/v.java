/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.a;
import ao.o;
import ao.q;
import aq.i;
import be.cm;
import be.dc;
import be.t;
import be.w;
import bi.j;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class v {
    private static final Logger a = Logger.getLogger(v.class.getName());

    public static void a(d client) {
        v.b(client);
        v.c(client);
        int amountOfChars = client.e().b();
        client.a(new t(amountOfChars, client));
        client.a(new be.v(10));
        if (amountOfChars > 0) {
            v.d(client);
        }
        client.a(new be.v(64));
        client.a(new dc(450, client.e().q()));
        ao.a.a().d(client.e());
        ao.a.a().b(client.e());
    }

    private static void b(d client) {
        String[] sss;
        String[] stringArray = sss = new String[0];
        int n2 = sss.length;
        int n3 = 0;
        while (n3 < n2) {
            String s2 = stringArray[n3];
            String[] ss = s2.trim().split(" ");
            byte[] data = new byte[ss.length];
            int i2 = 0;
            while (i2 < ss.length) {
                data[i2] = v.a(ss[i2])[0];
                ++i2;
            }
            client.a(new cm(data));
            ++n3;
        }
    }

    private static byte[] a(String hexString) {
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        int i2 = 0;
        while (i2 < length) {
            int low;
            int high = Character.digit(hex[i2 * 2], 16);
            int value = high << 4 | (low = Character.digit(hex[i2 * 2 + 1], 16));
            if (value > 127) {
                value -= 256;
            }
            rawData[i2] = (byte)value;
            ++i2;
        }
        return rawData;
    }

    private static void c(d client) {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
                    pstm.setString(1, client.a());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        Calendar cal;
                        long checkDeleteTime;
                        String name = rs.getString("char_name");
                        String clanname = rs.getString("Clanname");
                        Timestamp deleteTime = rs.getTimestamp("DeleteTime");
                        if (deleteTime == null || (checkDeleteTime = ((cal = Calendar.getInstance()).getTimeInMillis() - deleteTime.getTime()) / 1000L / 3600L) < 0L) continue;
                        i clan = q.a().c(clanname);
                        if (clan != null) {
                            clan.b(name);
                        }
                        o.a().a(client.a(), name);
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    private static void d(d client) {
        block18: {
            Connection conn = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    conn = b.a().b();
                    pstm = conn.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
                    pstm.setString(1, client.a());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String name = rs.getString("char_name");
                        String clanname = rs.getString("Clanname");
                        int type = rs.getInt("Type");
                        byte sex = rs.getByte("Sex");
                        int lawful = rs.getInt("Lawful");
                        int currenthp = rs.getInt("CurHp");
                        if (currenthp < 1) {
                            currenthp = 1;
                        } else if (currenthp > Short.MAX_VALUE) {
                            currenthp = Short.MAX_VALUE;
                        }
                        int currentmp = rs.getInt("CurMp");
                        if (currentmp < 1) {
                            currentmp = 1;
                        } else if (currentmp > Short.MAX_VALUE) {
                            currentmp = Short.MAX_VALUE;
                        }
                        int lvl = rs.getInt("level");
                        if (lvl < 1) {
                            lvl = 1;
                        } else if (lvl > 127) {
                            lvl = 127;
                        }
                        int ac2 = rs.getInt("Ac");
                        int str = rs.getInt("Str");
                        int dex = rs.getInt("Dex");
                        int con = rs.getInt("Con");
                        int wis = rs.getInt("Wis");
                        int cha = rs.getInt("Cha");
                        int intel = rs.getInt("Intel");
                        int accessLevel = rs.getInt("AccessLevel");
                        Timestamp _birthday = rs.getTimestamp("birthday");
                        SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
                        int birthday = Integer.parseInt(SimpleDate.format(_birthday.getTime()));
                        w cpk = new w(name, clanname, type, sex, lawful, currenthp, currentmp, ac2, lvl, str, dex, con, wis, cha, intel, accessLevel, birthday);
                        client.a(cpk);
                        Timestamp last = client.e().g();
                        Timestamp tam_ts = rs.getTimestamp("TamUseTime");
                        Timestamp current = new Timestamp(System.currentTimeMillis());
                        if (tam_ts == null) continue;
                        long diff = 0L;
                        if (tam_ts.after(current)) {
                            diff = current.getTime() - last.getTime();
                            client.e().c();
                        } else {
                            diff = tam_ts.getTime() - last.getTime();
                            o.a().a(rs.getInt("objid"));
                        }
                        if (tam_ts.getTime() - current.getTime() > 0L) {
                            client.e().c((int)(tam_ts.getTime() - current.getTime()) / 1000);
                        }
                        client.e().b(1200 * (int)(diff / 1000000L));
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, conn);
                    break block18;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, conn);
                throw throwable;
            }
            j.a(rs, pstm, conn);
        }
    }
}

