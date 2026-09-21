/*
 * Decompiled with CFR 0.152.
 */
package be;

import am.d;
import an.a;
import an.b;
import an.c;
import an.d;
import an.e;
import an.f;
import an.g;
import an.h;
import ao.bf;
import ap.q;
import ap.u;
import aq.aq;
import aq.i;
import aq.k;
import as.j;
import be.eu;
import bh.s;
import bi.g;
import bi.j;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;
import l1j.server.b;

public class dc
extends eu {
    private static final Logger ao = Logger.getLogger(dc.class.getName());
    public static final int a = 55;
    public static final int b = 57;
    public static final int c = 59;
    public static final int d = 63;
    public static final int e = 65;
    public static final int f = 68;
    public static final int g = 76;
    public static final int h = 93;
    public static final int i = 101;
    public static final int j = 102;
    public static final int k = 103;
    public static final int l = 104;
    public static final int m = 110;
    public static final int n = 113;
    public static final int o = 116;
    public static final int p = 118;
    public static final int q = 119;
    public static final int r = 120;
    public static final int s = 123;
    public static final int t = 125;
    public static final int u = 126;
    public static final int v = 128;
    public static final int w = 133;
    public static final int x = 136;
    public static final int y = 141;
    public static final int z = 145;
    public static final int A = 318;
    public static final int B = 320;
    public static final int C = 325;
    public static final int D = 327;
    public static final int E = 333;
    public static final int F = 335;
    public static final int G = 337;
    public static final int H = 339;
    public static final int I = 401;
    public static final int J = 402;
    public static final int K = 450;
    public static final int L = 461;
    public static final int M = 463;
    public static final int N = 483;
    public static final int O = 485;
    public static final int P = 487;
    public static final int Q = 489;
    public static final int R = 490;
    public static final int S = 515;
    public static final int T = 516;
    public static final int U = 518;
    public static final int V = 519;
    public static final int W = 521;
    public static final int X = 525;
    public static final int Y = 537;
    public static final int Z = 539;
    public static final int aa = 540;
    public static final int ab = 559;
    public static final int ac = 560;
    public static final int ad = 564;
    public static final int ae = 567;
    public static final int af = 568;
    public static final int ag = 579;
    public static final int ah = 588;
    public static final int ai = 800;
    public static final int aj = 803;
    public static final int ak = 810;
    public static final int al = 813;
    public static final int am = 814;
    public static final int an = 821;

    public dc(int type) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 126: {
                a.a.a builderb = a.a.aa();
                builderb.a(0);
                builderb.b(1);
                this.a(builderb.M().g());
                break;
            }
            case 116: {
                a.a.a builder1 = a.a.aa();
                builder1.a(1);
                this.a(builder1.M().g());
                break;
            }
            case 141: {
                a.c.a builder2 = a.c.aa();
                builder2.b(1);
                builder2.c(1);
                String[] texts = new String[]{"", "$1506", "$11993", "$16298"};
                int i2 = 1;
                while (i2 <= 3) {
                    h.e.a builder = h.e.aa();
                    builder.a(i2);
                    String http = "http://g.lineage.power.plaync.com/wiki/%EC%97%90%EB%A5%B4%EC%9E%90%EB%B2%A0";
                    builder.e(bi.g.a("http://g.lineage.power.plaync.com/wiki/%EC%97%90%EB%A5%B4%EC%9E%90%EB%B2%A0"));
                    builder.f(bi.g.a(texts[i2]));
                    builder.c(aq.a().b[i2]);
                    builder.d(aq.a().b[i2] + (i2 == 3 ? 2400 : 600));
                    e.c.a builder17 = e.c.aa();
                    builder17.e(bi.g.a("4654"));
                    builder17.a(1000);
                    builder.g(builder17.M().f());
                    builder2.e(builder.M().f());
                    ++i2;
                }
                int n2 = 22;
                int hour = Calendar.getInstance().get(11);
                int minute = Calendar.getInstance().get(12);
                long timeMill = 0L;
                timeMill = hour > 22 ? (long)((1440 + ((22 - hour) * 60 - minute)) * 60 * 1000) : (long)(((22 - hour) * 60 - minute) * 60 * 1000);
                int time = (int)((new Date().getTime() + timeMill) / 1000L);
                h.e.a builder = h.e.aa();
                builder.a(12);
                builder.e(bi.g.a(""));
                builder.f(bi.g.a("$25034"));
                builder.c(time);
                builder.d(time + 3600);
                e.c.a builder17 = e.c.aa();
                builder17.e(bi.g.a("4654"));
                builder17.a(1000);
                builder.g(builder17.M().f());
                builder2.e(builder.M().f());
                this.a(builder2.M().g());
            }
        }
        this.b(0);
    }

    public dc(int type, int[][] value) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 810: {
                a.g.a builder1114 = a.g.aa();
                g.i.a builder115 = g.i.aa();
                g.g.a builder124 = g.g.aa();
                e.e.a builder118 = e.e.aa();
                a.a.a builder1111 = a.a.aa();
                builder118.a(1);
                builder118.b(721306);
                builder118.c(0);
                builder1111.a(17368);
                builder1111.b(1);
                builder118.e(builder1111.M().f());
                builder124.e(builder118.M().f());
                builder118.a(2);
                builder118.b(7213060);
                builder118.c(15391);
                builder1111.a(17368);
                builder1111.b(5);
                builder118.e(builder1111.M().f());
                builder124.e(builder118.M().f());
                builder118.a(3);
                builder118.b(16229385);
                builder118.c(17038);
                builder1111.a(1695);
                builder1111.b(1);
                builder118.e(builder1111.M().f());
                builder124.e(builder118.M().f());
                a.a.a builder111 = a.a.aa();
                builder111.a(1);
                builder111.b(7213060);
                builder111.c(17345);
                builder124.f(builder111.M().f());
                builder111.a(2);
                builder111.b(14426037);
                builder111.c(17344);
                builder124.f(builder111.M().f());
                builder124.d(55);
                builder115.e(builder124.M().f());
                int i2 = 0;
                while (i2 < 3) {
                    e.a.a builder116 = e.a.aa();
                    builder116.b(i2);
                    builder116.d(value[i2 * 3][3]);
                    int j2 = 0;
                    while (j2 < 3) {
                        int number = value[i2 * 3 + j2][0];
                        int count = value[i2 * 3 + j2][1];
                        int kill = value[i2 * 3 + j2][2];
                        a.a.a builder1 = a.a.aa();
                        builder1.a(j2);
                        builder1.b(count);
                        builder1.c(number * 3 - 2);
                        builder1.d(number);
                        builder1.e(kill);
                        builder116.e(builder1.M().f());
                        ++j2;
                    }
                    builder115.f(builder116.M().f());
                    ++i2;
                }
                builder1114.e(builder115.M().f());
                this.a(builder1114.M().g());
            }
        }
        this.b(0);
    }

    public dc(int type, int value) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 800: {
                a.a.a bue1 = a.a.aa();
                bue1.a(value);
                this.a(bue1.M().g());
                this.b(0);
                break;
            }
            case 525: {
                a.a.a bu11 = a.a.aa();
                bu11.a(0);
                bu11.b(value);
                this.a(bu11.M().g());
                this.b(0);
                break;
            }
            case 487: {
                boolean isMax = value == 3;
                int[] nArray = new int[4];
                nArray[1] = 25;
                nArray[2] = 35;
                nArray[3] = 45;
                int[] guides = nArray;
                f.c.a bu22 = f.c.aa();
                a.a.a bu_str = a.a.aa();
                bu_str.a(guides[value]);
                bu_str.b(isMax ? 3 : 1);
                bu_str.c(isMax ? 3 : 1);
                if (isMax) {
                    bu_str.d(1);
                }
                bu22.e(bu_str.M().f());
                a.a.a bu_int = a.a.aa();
                bu_int.a(guides[value]);
                bu_int.b(isMax ? 3 : 1);
                bu_int.c(isMax ? 3 : 1);
                if (isMax) {
                    bu_int.d(1);
                }
                bu22.f(bu_int.M().f());
                a.a.a bu_wis = a.a.aa();
                bu_wis.a(guides[value]);
                bu_wis.b(isMax ? 3 : 1);
                bu_wis.c(isMax ? 3 : 1);
                bu_wis.g(50 * value);
                bu22.g(bu_wis.M().f());
                a.a.a bu_dex = a.a.aa();
                bu_dex.a(guides[value]);
                bu_dex.b(isMax ? 3 : 1);
                bu_dex.c(isMax ? 3 : 1);
                if (isMax) {
                    bu_dex.d(1);
                }
                bu22.h(bu_dex.M().f());
                a.a.a bu_con = a.a.aa();
                bu_con.a(guides[value]);
                bu_con.b(isMax ? 3 : 1);
                if (isMax) {
                    bu_con.c(2);
                }
                bu_con.f(50 * value);
                bu22.i(bu_con.M().f());
                a.a.a bu_cha = a.a.aa();
                bu_cha.a(-1);
                bu22.j(bu_cha.M().f());
                this.a(bu22.M().g());
                this.b(0);
                break;
            }
            case 123: {
                a.a.a bu2 = a.a.aa();
                bu2.a(value);
                this.a(bu2.M().g());
                this.b(0);
                break;
            }
            case 450: {
                a.a.a buildert = a.a.aa();
                buildert.a(value);
                this.a(buildert.M().g());
                this.b(0);
                break;
            }
            case 103: {
                d.g.a builder141 = d.g.aa();
                builder141.a(14144);
                builder141.b(value * 2);
                builder141.e(a.g.a(new byte[]{-30, 112, -1}));
                builder141.c(20);
                this.a(builder141.M().g());
                this.b(0);
                break;
            }
            case 76: {
                d.g.a builder14 = d.g.aa();
                builder14.a(1);
                builder14.b(0);
                builder14.e(bi.g.a(""));
                this.a(builder14.M().g());
                this.b(0);
                break;
            }
            case 402: {
                this.c(8);
                this.c(value);
                this.b(0);
                break;
            }
            case 93: {
                a.a.a builderc = a.a.aa();
                builderc.a(0);
                builderc.b(value);
                this.a(builderc.M().g());
                this.b(0);
                break;
            }
            case 55: {
                this.c(8);
                this.c(value);
                this.b(0);
                break;
            }
            case 463: {
                a.a.a builder1 = a.a.aa();
                builder1.a(value);
                builder1.b(0);
                builder1.c(0);
                this.a(builder1.M().g());
                this.b(0);
                break;
            }
            case 110: {
                a.a.a builder = a.a.aa();
                builder.a(3);
                builder.b(value);
                builder.c(0);
                builder.d(0);
                builder.e(0);
                builder.f(0);
                builder.g(1);
                builder.h(0);
                builder.i(0);
                builder.j(0);
                builder.k(0);
                this.a(builder.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, byte[] data) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 123: {
                this.a(data);
                this.b(0);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public dc(int type, u pc) {
        super();
        this.c(1);
        this.b(type);
        switch (type) {
            case 803: {
                builder38 = c.g.aa();
                builder108 = c.a.aa();
                builder108.b(1);
                builder108.e(bi.g.a("\u53e4\u9b6f\u4e01\u5730\u76e3"));
                builder108.c(pc.cS());
                builder108.d(21600);
                builder38.e(builder108.M().f());
                builder108.b(2);
                builder108.e(bi.g.a("\u65b0\u5922\u5e7b\u4e4b\u5cf6"));
                builder108.c(pc.cW());
                builder108.d(7200);
                builder38.e(builder108.M().f());
                this.a(builder38.M().g());
                this.b(0);
                break;
            }
            case 113: {
                builder118 = e.e.aa();
                ip = l1j.server.a.f;
                splite = ip.split("\\.");
                code = Integer.parseInt(splite[0]) & 255;
                code |= Integer.parseInt(splite[1]) << 8 & 65280;
                code |= Integer.parseInt(splite[2]) << 16 & 0xFF0000;
                builder118.a(code |= Integer.parseInt(splite[3]) << 24 & -16777216);
                builder118.b(l1j.server.a.g);
                builder118.c(1);
                text = String.valueOf(pc.bc()) + "-" + pc.eu();
                builder118.e(bi.g.a(text));
                builder118.d(3);
                this.a(builder118.M().g());
                this.b(0);
                break;
            }
            case 559: {
                bu1 = a.c.aa();
                bu1.b(0);
                bu1.c(0);
                i = 0;
                while (i < pc.dQ().length) {
                    stage = am.d.a().a(i, pc.dQ()[i]);
                    if (stage > 0) {
                        idx = i * 3;
                        while (idx <= i * 3 + stage - 1) {
                            builder = a.a.aa();
                            achievementIDX = idx + 1;
                            if (achievementIDX >= 1670) {
                                achievementIDX += 30;
                            }
                            builder.a(achievementIDX);
                            builder.b(pc.fr());
                            builder.c(pc.dR()[idx]);
                            bu1.e(builder.M().f());
                            ++idx;
                        }
                    }
                    ++i;
                }
                this.a(bu1.M().g());
                this.b(0);
                break;
            }
            case 560: {
                bu2 = a.c.aa();
                bu2.b(0);
                bu2.c(0);
                list = pc.dQ();
                i = 0;
                while (i < list.length) {
                    if (list[i] > 0) {
                        bu01 = a.a.aa();
                        bu01.a(i + 1);
                        bu01.b(list[i]);
                        bu2.e(bu01.M().f());
                    }
                    ++i;
                }
                this.a(bu2.M().g());
                this.b(0);
                break;
            }
            case 489: {
                builderx = a.a.aa();
                builderx.a(pc.bB());
                this.a(builderx.M().g());
                this.b(0);
                break;
            }
            case 490: {
                builderb = a.a.aa();
                builderb.a(pc.bf());
                builderb.b(pc.bj());
                builderb.c(pc.bk());
                builderb.d(pc.bh());
                builderb.e(pc.bg());
                builderb.f(pc.bi());
                this.a(builderb.M().g());
                this.b(0);
                break;
            }
            case 485: {
                builderl = a.a.aa();
                builderl.a(pc.j().h());
                builderl.b(pc.j().e());
                builderl.c((int)pc.K());
                this.a(builderl.M().g());
                this.b(0);
                break;
            }
            case 325: {
                builder180 = e.e.aa();
                builder180.a(2);
                this.a(builder180.M().g());
                this.b(0);
                break;
            }
            case 327: {
                builder181 = e.e.aa();
                builder181.a(0);
                this.a(builder181.M().g());
                this.b(0);
                break;
            }
            case 333: {
                clan = ao.q.a().a(pc.aF());
                builder18 = e.e.aa();
                builder18.a(1);
                builder18.b(clan.q());
                builder18.c(clan.r());
                builder18.e(a.g.a(clan.s()));
                this.a(builder18.M().g());
                this.b(0);
                break;
            }
            case 76: {
                l1clan = ao.q.a().a(pc.aF());
                currentWar = aq.a().c(pc.aG());
                builder14 = d.g.aa();
                builder14.a(l1clan.a() != false ? 2 : 1);
                if (currentWar != null) {
                    name = currentWar.c(pc.aG()).get(0).f();
                    builder14.b(currentWar.a() * 2);
                    builder14.e(bi.g.a(name));
                    this.a(builder14.M().g());
                }
                this.b(0);
                break;
            }
            case 539: {
                builder = c.c.aa();
                builder.a(2);
                builder.e(bi.g.a(pc.et()));
                this.a(builder.M().g());
                this.b(0);
                break;
            }
            case 461: {
                builder8 = c.g.aa();
                con = null;
                pstm = null;
                rs = null;
                try {
                    try {
                        con = l1j.server.b.a().b();
                        pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
                        pstm.setString(1, pc.bc());
                        rs = pstm.executeQuery();
                        while (rs.next()) {
                            builder1 = d.e.aa();
                            builder1.a(l1j.server.a.a);
                            builder1.b(rs.getInt("objid"));
                            ts = rs.getTimestamp("TamUseTime");
                            if (ts == null) {
                                builder1.c(0);
                            } else {
                                diff = ts.getTime() - System.currentTimeMillis();
                                builder1.c(diff < 0L ? 0 : (int)(diff / 1000L));
                            }
                            builder1.d(0);
                            builder1.e(a.g.a(rs.getString("char_name").getBytes(l1j.server.a.k)));
                            builder1.e(rs.getInt("level"));
                            builder1.f(rs.getInt("Type"));
                            builder1.g(rs.getInt("Sex"));
                            builder8.e(builder1.M().f());
                        }
                    }
                    catch (Exception e) {
                        dc.ao.log(Level.SEVERE, e.getLocalizedMessage(), e);
                        bi.j.a(rs, pstm, con);
                        ** GOTO lbl239
                    }
                }
                catch (Throwable var32_40) {
                    bi.j.a(rs, pstm, con);
                    throw var32_40;
                }
                bi.j.a(rs, pstm, con);
lbl239:
                // 2 sources

                builder8.b(3);
                builder8.c(0);
                builder8.d(0);
                this.a(builder8.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, int value, int value2, int value3) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 813: {
                a.a.a builder = a.a.aa();
                builder.a(value);
                builder.b(value2);
                builder.c(value3);
                this.a(builder.M().g());
                break;
            }
            case 125: {
                c.c.a builder6 = c.c.aa();
                builder6.a(value);
                a.a.a builderp = a.a.aa();
                builderp.a(value2);
                builderp.b(value3);
                builder6.e(builderp.M().f());
                this.a(builder6.M().g());
            }
        }
        this.b(0);
    }

    public dc(int type, int value, int value2) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 564: 
            case 567: 
            case 568: 
            case 814: {
                a.a.a bu01 = a.a.aa();
                bu01.a(value);
                bu01.b(value2);
                this.a(bu01.M().g());
                this.b(0);
                break;
            }
            case 65: {
                a.a.a builderm = a.a.aa();
                builderm.a(value);
                builderm.b(value2);
                this.a(builderm.M().g());
                this.b(0);
                break;
            }
            case 320: {
                a.a.a builderE = a.a.aa();
                builderE.a(value);
                builderE.b(2);
                builderE.c(value2);
                this.a(builderE.M().g());
                this.b(0);
                break;
            }
            case 339: {
                a.a.a builder1 = a.a.aa();
                builder1.a(value);
                builder1.b(value2);
                this.a(builder1.M().g());
                this.b(0);
                break;
            }
            case 63: {
                a.a.a builder = a.a.aa();
                builder.a(1);
                builder.b(value);
                builder.c(value2);
                this.a(builder.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, k craft, q craft_item, int value) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 59: {
                d.c.a builder12 = d.c.aa();
                builder12.a(value);
                builder12.e(craft.a(craft_item, false));
                builder12.f(craft.a(0L, 0L));
                this.a(builder12.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, k craft, int value) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 55: {
                this.c(8);
                this.c(value);
                c.a.a builder = c.a.aa();
                builder.e(craft.i());
                this.a(builder.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, String ... data) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 57: {
                c.a.a builder10 = c.a.aa();
                builder10.b(0);
                builder10.c(0);
                String[] stringArray = data;
                int n2 = data.length;
                int n3 = 0;
                while (n3 < n2) {
                    String s2 = stringArray[n3];
                    if (s2.trim().length() != 0) {
                        a.a.a builder1 = a.a.aa();
                        builder1.a(Integer.parseInt(s2));
                        builder1.b(0);
                        builder1.c(0);
                        builder10.e(builder1.M().f());
                    }
                    ++n3;
                }
                this.a(builder10.M().g());
                this.b(0);
                break;
            }
            case 337: {
                c.a.a build10 = c.a.aa();
                build10.b(1);
                String[] stringArray = data;
                int n4 = data.length;
                int n5 = 0;
                while (n5 < n4) {
                    String s3 = stringArray[n5];
                    if (s3.trim().length() != 0) {
                        e.c.a builder17 = e.c.aa();
                        builder17.e(bi.g.a(s3));
                        int isOnLine = aq.a().a(s3) == null ? 0 : 1;
                        builder17.a(isOnLine);
                        builder17.f(bi.g.a(""));
                        build10.e(builder17.M().f());
                    }
                    ++n5;
                }
                this.a(build10.M().g());
                this.b(0);
            }
        }
    }

    public dc(bf.a ... ranks) {
        this.c(1);
        this.b(335);
        d.i.a builder15 = d.i.aa();
        bf.a[] aArray = ranks;
        int n2 = ranks.length;
        int n3 = 0;
        while (n3 < n2) {
            bf.a rank = aArray[n3];
            h.i.a builder8 = h.i.aa();
            builder8.e(bi.g.a(rank.a));
            builder8.a(rank.b);
            builder8.b(rank.c);
            builder8.c((int)(rank.d / 1000L));
            builder8.d(1);
            if (builder15.p() < 3) {
                builder15.e(builder8.M().f());
            }
            if (builder15.r() < 10) {
                builder15.f(builder8.M().f());
            }
            ++n3;
        }
        this.a(builder15.M().g());
        this.b(0);
    }

    public dc(int type, bh.d ... castleList) {
        this.c(1);
        this.b(type);
        c.g.a builder8 = c.g.aa();
        bh.d[] dArray = castleList;
        int n2 = castleList.length;
        int n3 = 0;
        while (n3 < n2) {
            bh.d castle = dArray[n3];
            if (castle.a() != 3) {
                String clanName = "\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castle.a();
                String clanLeaderName = "Srwh";
                i clan = ao.q.a().a(castle.g());
                if (clan != null) {
                    clanName = clan.f();
                    clanLeaderName = clan.l();
                }
                int money = castle.f();
                d.c.a builder12 = d.c.aa();
                builder12.a(castle.a());
                builder12.e(bi.g.a(clanName));
                builder12.f(bi.g.a(clanLeaderName));
                builder12.b(castle.a());
                builder12.a((long)castle.c().getFirstDayOfWeek());
                builder12.b((long)(money / 2 * 2 + money * 2));
                builder12.c((long)(money * 2));
                builder8.e(builder12.M().f());
            }
            ++n3;
        }
        this.a(builder8.M().g());
        this.b(0);
    }

    public dc(int type, int val, String s2) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 540: {
                c.c.a builder6 = c.c.aa();
                builder6.a(val);
                builder6.e(bi.g.a(s2));
                this.a(builder6.M().g());
                this.b(0);
                break;
            }
            case 102: {
                d.g.a builder14 = d.g.aa();
                builder14.a(6298);
                builder14.b(3755);
                byte[] byArray = new byte[3];
                byArray[0] = -1;
                byArray[2] = -1;
                builder14.e(a.g.a(byArray));
                builder14.c(10);
                builder14.d(val * 2);
                builder14.f(bi.g.a(s2));
                this.a(builder14.M().g());
                this.b(0);
            }
        }
    }

    public dc(int type, ArrayList<Integer> list) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 401: {
                c.g.a builder8 = c.g.aa();
                for (int i2 : list) {
                    a.a.a builder1 = a.a.aa();
                    builder1.a(i2);
                    if (i2 == 5) {
                        builder1.b(10);
                    }
                    builder8.e(builder1.M().f());
                }
                this.a(builder8.M().g());
                this.b(0);
            }
        }
    }

    public dc(int id, int time, int unit, int icon, int iconEnd, int stringc, int startMsg, int endMsg, int seq) {
        this.c(1);
        this.b(110);
        a.a.a builder = a.a.aa();
        builder.a(time == 0 ? 3 : 1);
        builder.b(id);
        builder.c(time);
        builder.d(unit);
        builder.e(icon);
        builder.f(iconEnd);
        builder.g(seq);
        builder.h(stringc);
        builder.i(startMsg);
        builder.j(endMsg);
        builder.k(1);
        this.a(builder.M().g());
        this.b(0);
    }

    public dc(HashMap<Integer, q> list, int type) {
        this.c(1);
        this.b(101);
        a.c.a builder2 = a.c.aa();
        builder2.b(99);
        builder2.c(type);
        for (int key : list.keySet()) {
            q item = list.get(key);
            b.a.a builder = b.a.y();
            builder.a(item.N());
            builder.b(item.E());
            builder.c(item.e());
            builder.d(key);
            builder.e(bi.g.a(item.b()));
            builder2.e(builder.z().f());
        }
        this.a(builder2.M().g());
        this.b(0);
    }

    public dc(int type, String field, int ... data) {
        this.c(1);
        this.b(483);
        e.g.a builder19 = e.g.aa();
        builder19.a(type);
        a.a.a builder = a.a.aa();
        builder.a(data[0]);
        builder.b(data[1]);
        builder.c(data[2]);
        builder.d(data[3]);
        if (data.length >= 5) {
            builder.e(data[4]);
        }
        if (data.length >= 6) {
            builder.f(data[5]);
        }
        if (field == "str") {
            builder19.e(builder.M().f());
        } else if (field == "int") {
            builder19.f(builder.M().f());
        } else if (field == "wis") {
            builder19.g(builder.M().f());
        } else if (field == "dex") {
            builder19.h(builder.M().f());
        } else if (field == "con") {
            builder19.i(builder.M().f());
        } else if (field == "cha") {
            builder19.j(builder.M().f());
        }
        this.a(builder19.M().g());
        this.b(0);
    }

    public dc(u pc, String chatText, int chatCount) {
        this.c(1);
        this.b(515);
        f.a.a builder = f.a.aa();
        builder.a(chatCount);
        builder.b(1);
        builder.e(bi.g.a(chatText));
        builder.f(bi.g.a(pc.et()));
        builder.c(l1j.server.a.a);
        builder.d(0);
        this.a(builder.M().g());
        this.b(0);
    }

    public dc(int type, int val, String[] s2, int[] data) {
        this.c(1);
        this.b(type);
        switch (type) {
            case 579: {
                c.a.a builder = c.a.aa();
                builder.b(val);
                int i2 = 0;
                while (i2 < data.length) {
                    a.g.a builder4 = a.g.aa();
                    builder4.e(bi.g.a(s2[i2]));
                    if (data[i2] > 0) {
                        a.a.a builder1 = a.a.aa();
                        builder1.a(7);
                        builder1.b(data[i2]);
                        builder1.c(1);
                        builder4.f(builder1.M().f());
                    }
                    builder.e(builder4.M().f());
                    ++i2;
                }
                this.a(builder.M().g());
                this.b(0);
            }
        }
    }

    public dc(u pc, String chatText, int chatType, int chatCount) {
        this.c(1);
        this.b(516);
        e.i.a builder = e.i.aa();
        builder.a(chatCount);
        builder.b(chatType);
        builder.e(bi.g.a(chatText));
        builder.f(bi.g.a(pc.et()));
        builder.d(l1j.server.a.a);
        if (chatType == 0 || chatType == 2) {
            builder.e(pc.fr());
            builder.f(pc.fs());
            builder.g(pc.ft());
        }
        this.a(builder.M().g());
        this.b(0);
    }

    public dc(ArrayList<Integer> equipList_1, ArrayList<Integer> equipList_2, int page) {
        this.c(1);
        this.b(800);
        Collections.sort(equipList_1);
        Collections.sort(equipList_2);
        c.a.a builder10 = c.a.aa();
        builder10.b(page);
        b.c.a builder = b.c.s();
        b.c.a builder2 = b.c.s();
        builder.b(0);
        for (int i2 : equipList_1) {
            builder.c(i2);
        }
        builder10.e(builder.t().f());
        builder2.b(1);
        for (int i2 : equipList_1) {
            builder2.c(i2);
        }
        builder10.e(builder2.t().f());
        builder10.c(2);
        builder10.d(1);
        this.a(builder10.M().g());
        this.b(0);
    }

    public dc(int type, s qn) {
        int i2;
        this.c(1);
        this.b(type);
        e.a.a builder16 = e.a.aa();
        builder16.b(qn.a());
        builder16.c(qn.y().fr());
        a.a.a builder1 = a.a.aa();
        int index = 1;
        if (qn.n() > 0) {
            builder1.a(index++);
            builder1.b(qn.z());
            builder1.c(qn.n());
            builder16.e(builder1.M().f());
        }
        if (qn.p().length > 0) {
            i2 = 0;
            while (i2 < qn.p().length) {
                builder1.a(index++);
                builder1.b(qn.A()[i2]);
                builder1.c(qn.q()[i2]);
                builder16.e(builder1.M().f());
                ++i2;
            }
        }
        if (qn.r().length > 0) {
            i2 = 0;
            while (i2 < qn.r().length) {
                builder1.a(index++);
                builder1.b(qn.B()[i2]);
                builder1.c(qn.s()[i2]);
                builder16.e(builder1.M().f());
                ++i2;
            }
        }
        if (qn.u().length > 0) {
            i2 = 0;
            while (i2 < qn.u().length) {
                builder1.a(index++);
                builder1.b(qn.C()[i2]);
                builder1.c(qn.v()[i2]);
                builder16.e(builder1.M().f());
                ++i2;
            }
        }
        builder16.e(1);
        e.c.a builder = e.c.aa();
        builder.e(builder16.M().f());
        this.a(builder.M().g());
        this.b(0);
    }

    public dc(int score, String name, j.g ... data) {
        j.g tr;
        this.c(1);
        this.b(133);
        d.i.a builder = d.i.aa();
        int i2 = 0;
        while (i2 < data.length) {
            if (i2 >= 10) break;
            c.c.a builder6 = c.c.aa();
            tr = data[i2];
            builder6.a(i2 + 1);
            builder6.e(bi.g.a(tr.a));
            builder6.b(tr.b);
            builder.e(builder6.M().f());
            ++i2;
        }
        boolean b2 = false;
        int i3 = 0;
        while (i3 < data.length) {
            tr = data[i3];
            if (tr.a.equalsIgnoreCase(name)) {
                c.c.a builder6 = c.c.aa();
                builder6.a(i3 + 1);
                builder6.e(bi.g.a(tr.a));
                builder6.b(tr.b);
                builder.f(builder6.M().f());
                b2 = true;
                break;
            }
            ++i3;
        }
        if (!b2) {
            c.c.a builder6 = c.c.aa();
            builder6.a(0);
            builder6.e(bi.g.a(name));
            builder6.b(0);
            builder.f(builder6.M().f());
        }
        builder.c(score);
        this.a(builder.M().g());
        this.b(0);
    }

    public dc(int remainTime, int[] data) {
        this.c(1);
        this.b(134);
        c.g.a builder = c.g.aa();
        a.a.a builder1 = a.a.aa();
        builder1.a(4);
        builder1.b(data[0]);
        builder1.c(l1j.server.a.a);
        builder.e(builder1.M().f());
        builder1.a(5);
        builder1.b(data[1]);
        builder1.c(l1j.server.a.a);
        builder.e(builder1.M().f());
        builder1.a(6);
        builder1.b(data[2]);
        builder1.c(l1j.server.a.a);
        builder.e(builder1.M().f());
        builder.b(remainTime);
        this.a(builder.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ProtoBuffers";
    }
}

