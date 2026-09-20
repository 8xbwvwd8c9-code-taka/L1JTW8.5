/*
 * Decompiled with CFR 0.152.
 */
package bi;

import al.aa;
import al.ab;
import al.ac;
import al.ad;
import al.ae;
import al.af;
import al.ag;
import al.ah;
import al.ai;
import al.aj;
import al.ak;
import al.al;
import al.am;
import al.an;
import al.ao;
import al.ap;
import al.ar;
import al.as;
import al.at;
import al.au;
import al.av;
import al.aw;
import al.ax;
import al.ay;
import al.az;
import al.ba;
import al.bb;
import al.bc;
import al.bd;
import al.bf;
import an.c;
import an.e;
import ao.q;
import ap.a;
import ap.c;
import ap.d;
import ap.h;
import ap.l;
import ap.n;
import ap.o;
import ap.p;
import ap.r;
import ap.s;
import ap.t;
import ap.u;
import ap.w;
import ap.x;
import ap.y;
import aq.aq;
import aq.b;
import aq.i;
import aq.k;
import be.be;
import bf.bg;
import bf.bh;
import bf.bi;
import bf.bj;
import bf.bk;
import bf.bl;
import bf.bm;
import bf.bn;
import bf.bo;
import bf.bp;
import bf.bq;
import bf.br;
import bf.bs;
import bf.bt;
import bf.bu;
import bf.bv;
import bf.bw;
import bf.bx;
import bf.by;
import bf.bz;
import bf.ca;
import bf.cb;
import bf.cc;
import bf.cd;
import bf.ce;
import bf.cf;
import bf.cg;
import bf.ch;
import bf.ci;
import bf.cj;
import bf.ck;
import bf.cl;
import bf.cm;
import bf.cn;
import bf.co;
import bf.cp;
import bf.cq;
import bf.cr;
import bf.cs;
import bf.ct;
import bf.cu;
import bf.cv;
import bf.cw;
import bf.cx;
import bf.cy;
import bf.cz;
import bf.da;
import bf.db;
import bf.dc;
import bf.dd;
import bf.de;
import bf.df;
import bf.dg;
import bf.dh;
import bf.di;
import bf.dj;
import bf.dk;
import bf.dl;
import bf.dm;
import bf.dn;
import bf.do;
import bf.dp;
import bf.dq;
import bf.dr;
import bf.ds;
import bf.dt;
import bf.du;
import bf.dv;
import bf.dw;
import bf.dx;
import bf.dy;
import bf.dz;
import bf.e;
import bf.ea;
import bf.eb;
import bf.ec;
import bf.ed;
import bf.ee;
import bf.ef;
import bf.eg;
import bf.eh;
import bf.ei;
import bf.ej;
import bf.ek;
import bf.el;
import bf.em;
import bf.en;
import bf.eo;
import bf.ep;
import bf.eq;
import bf.er;
import bf.es;
import bf.et;
import bf.eu;
import bf.ev;
import bf.ew;
import bf.ex;
import bf.ey;
import bf.ez;
import bf.f;
import bf.fa;
import bf.fb;
import bf.fc;
import bf.fd;
import bf.fe;
import bf.ff;
import bf.fg;
import bf.fh;
import bf.fi;
import bf.fj;
import bf.fk;
import bf.fl;
import bf.fm;
import bf.fn;
import bf.fo;
import bf.fp;
import bf.fq;
import bf.fr;
import bf.fs;
import bf.ft;
import bf.fu;
import bf.fv;
import bf.fw;
import bf.fx;
import bf.fy;
import bf.fz;
import bf.ga;
import bf.gb;
import bf.gc;
import bf.gd;
import bf.ge;
import bf.gf;
import bf.gg;
import bf.gh;
import bf.gi;
import bf.gj;
import bf.gk;
import bf.gl;
import bf.gm;
import bf.gn;
import bf.go;
import bf.gp;
import bf.m;
import bf.v;
import bf.z;
import bi.j;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g {
    private static final Logger a = Logger.getLogger(g.class.getName());
    private static HashMap<String, String> b = new HashMap();

    public static void a(String fileName, String s2) {
        try {
            File file = new File(fileName);
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, true));
            bw2.write(s2);
            bw2.newLine();
            bw2.close();
        }
        catch (IOException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public static void a(String fileName, byte[] b2, boolean isAppend) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, isAppend);
            fos.write(b2);
            fos.close();
        }
        catch (IOException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public static a.g a(String s2) {
        a.g result = a.g.d;
        try {
            result = a.g.a(s2.getBytes(l1j.server.a.k));
        }
        catch (UnsupportedEncodingException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return result;
    }

    public static byte[] b(String s2) {
        byte[] result = new byte[]{};
        try {
            result = s2.getBytes(l1j.server.a.k);
        }
        catch (UnsupportedEncodingException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return result;
    }

    public static String a(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(46);
        if (index != -1) {
            return fileName.substring(index + 1, fileName.length());
        }
        return "";
    }

    public static String b(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(46);
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return "";
    }

    public static long a() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L;
    }

    public static void a(Closeable ... closeables) {
        Closeable[] closeableArray = closeables;
        int n2 = closeables.length;
        int n3 = 0;
        while (n3 < n2) {
            Closeable c2 = closeableArray[n3];
            try {
                if (c2 != null) {
                    c2.close();
                }
            }
            catch (IOException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ++n3;
        }
    }

    public static void b() {
        try {
            byte[] array;
            String path = "./data/craftinfo.dat";
            g.a("./data/craftinfo.dat", new byte[]{8, 2}, false);
            for (k craft : ao.s.a().b()) {
                c.a.a builder = c.a.aa();
                builder.e(craft.i());
                byte[] array2 = builder.M().g();
                g.a("./data/craftinfo.dat", array2, true);
            }
            byte[] data = Files.readAllBytes(Paths.get("./data/craftinfo.dat", new String[0]));
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(data);
            System.out.println("[\u81ea\u52d5\u751f\u6210\u9053\u5177\u6e05\u55ae\u9a57\u8b49\u78bc]");
            String s2 = "0a 14 ";
            byte[] byArray = sha.digest();
            int n2 = byArray.length;
            int n3 = 0;
            while (n3 < n2) {
                byte b2 = byArray[n3];
                s2 = String.valueOf(s2) + g.a(b2 & 0xFF, 2) + " ";
                ++n3;
            }
            l1j.server.a.aR = s2.trim();
            System.out.println(l1j.server.a.aR);
            String path2 = "./data/alchemyInfo.dat";
            g.a("./data/alchemyInfo.dat", new byte[]{8, 2}, false);
            int i2 = 1;
            while (i2 <= 4) {
                e.g.a Builder19 = e.g.aa();
                Builder19.e(aq.b.a().c(i2));
                array = Builder19.M().g();
                g.a("./data/alchemyInfo.dat", array, true);
                ++i2;
            }
            i2 = 1;
            while (i2 <= 5) {
                e.g.a Builder19 = e.g.aa();
                Builder19.f(aq.b.a().b(i2));
                array = Builder19.M().g();
                g.a("./data/alchemyInfo.dat", array, true);
                ++i2;
            }
            i2 = 1;
            while (i2 <= 4) {
                e.g.a Builder19 = e.g.aa();
                Builder19.g(aq.b.a().a(i2));
                array = Builder19.M().g();
                g.a("./data/alchemyInfo.dat", array, true);
                ++i2;
            }
            byte[] data2 = Files.readAllBytes(Paths.get("./data/alchemyInfo.dat", new String[0]));
            MessageDigest sha2 = MessageDigest.getInstance("sha-1");
            sha2.update(data2);
            System.out.println("[\u81ea\u52d5\u751f\u6210\u9b54\u6cd5\u5a03\u5a03\u5408\u6210\u6e05\u55ae\u9a57\u8b49\u78bc]");
            String s22 = "0a 14 ";
            byte[] byArray2 = sha2.digest();
            int n4 = byArray2.length;
            int n5 = 0;
            while (n5 < n4) {
                byte b3 = byArray2[n5];
                s22 = String.valueOf(s22) + g.a(b3 & 0xFF, 2) + " ";
                ++n5;
            }
            l1j.server.a.aS = s22.trim();
            System.out.println(l1j.server.a.aS);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public static void c() {
        long begin = System.currentTimeMillis();
        System.out.print("cleaning Accounts...");
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM accounts WHERE password = '1'");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String accountName = rs.getString("login");
                g.e(accountName);
                pstm = con.prepareStatement("DELETE FROM character_elf_warehouse WHERE account_name=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_mobs WHERE login=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_mobs_week WHERE login=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_shop WHERE acc_name=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_warehouse WHERE account_name=?");
                pstm.setString(1, accountName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM accounts WHERE login=?");
                pstm.setString(1, accountName);
                pstm.execute();
            }
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        j.a(rs, pstm, con);
        System.out.println("OK! " + (System.currentTimeMillis() - begin) + " ms");
    }

    private static void e(String accName) {
        block8: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name = ?");
                    pstm.setString(1, accName);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        i clan;
                        String charName = rs.getString("char_name");
                        u pc = ao.o.a().a(charName);
                        if (pc != null && (clan = q.a().a(pc.aF())) != null) {
                            clan.b(charName);
                            if (pc.x() && clan.k() == pc.fr()) {
                                g.a(pc, clan);
                            }
                        }
                        ao.o.a().a(accName, charName);
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block8;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private static void a(u pc, i clan) throws Exception {
        int castleId = clan.m();
        int houseId = clan.n();
        if (castleId != 0 || houseId != 0) {
            bh.i house;
            bh.d l1castale = ao.g.a().a(castleId);
            if (l1castale != null) {
                l1castale.c(0);
            }
            if ((house = ao.ab.a().a(houseId)) != null) {
                ba.i.a().a(house);
            }
        }
        int i2 = 0;
        while (i2 < clan.p().size()) {
            u offline_pc = ao.o.a().a(clan.p().get(i2));
            offline_pc.ah(0);
            offline_pc.c("");
            offline_pc.ai(0);
            offline_pc.f("");
            offline_pc.I();
            ++i2;
        }
        String emblem_file = String.valueOf(clan.i());
        File file = new File("./emblem/" + emblem_file);
        file.delete();
        q.a().b(clan.f());
        ao.p.a().b(clan.e());
    }

    public static u a(u pc, boolean isJoinClan) {
        int pcX = pc.fs();
        int pcY = pc.ft();
        int pcHeading = pc.fb();
        List<u> players = aq.a().c(pc, 1);
        if (players.isEmpty()) {
            pc.a(new be.ds(93));
            return null;
        }
        for (u target : players) {
            int targetX = target.fs();
            int targetY = target.ft();
            int targetHeading = target.fb();
            int[][] nArrayArray = new int[8][];
            int[] nArray = new int[2];
            nArray[1] = -1;
            nArrayArray[0] = nArray;
            nArrayArray[1] = new int[]{1, -1};
            int[] nArray2 = new int[2];
            nArray2[0] = 1;
            nArrayArray[2] = nArray2;
            nArrayArray[3] = new int[]{1, 1};
            int[] nArray3 = new int[2];
            nArray3[1] = 1;
            nArrayArray[4] = nArray3;
            nArrayArray[5] = new int[]{-1, 1};
            int[] nArray4 = new int[2];
            nArray4[0] = -1;
            nArrayArray[6] = nArray4;
            nArrayArray[7] = new int[]{-1, -1};
            int[][] diff = nArrayArray;
            int[] nArray5 = new int[8];
            nArray5[0] = 4;
            nArray5[1] = 5;
            nArray5[2] = 6;
            nArray5[3] = 7;
            nArray5[5] = 1;
            nArray5[6] = 2;
            nArray5[7] = 3;
            int[] faceHeading = nArray5;
            if (targetX != pcX + diff[pcHeading][0] || targetY != pcY + diff[pcHeading][1]) continue;
            if (targetHeading == faceHeading[pcHeading]) {
                return target;
            }
            if (isJoinClan) {
                pc.a(new be(0, "noseeb", target.et()));
            } else {
                pc.a(new be.ds(91, target.et()));
            }
            return null;
        }
        pc.a(new be.ds(93));
        return null;
    }

    public static t a(bh.l l1npc) {
        String impl = l1npc.d();
        if (impl.equalsIgnoreCase("L1AuctionBoard")) {
            return new ap.b(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Board")) {
            return new c(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Crown")) {
            return new d(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Dota")) {
            return new ap.g(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Effect")) {
            return new h(l1npc);
        }
        if (impl.equalsIgnoreCase("L1FieldObject")) {
            return new ap.i(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Fish")) {
            return new ap.j(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Furniture")) {
            return new l(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Guard")) {
            return new n(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Guardian")) {
            return new o(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Housekeeper")) {
            return new p(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Keeper")) {
            return new r(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Attacker")) {
            return new a(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Monster")) {
            return new s(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Npc")) {
            return new t(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Quest")) {
            return new w(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Scarecrow")) {
            return new x(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Signboard")) {
            return new y(l1npc);
        }
        if (impl.equalsIgnoreCase("L1Tower")) {
            return new ap.aa(l1npc);
        }
        return null;
    }

    public static bf.a a(int skillid) {
        if (skillid == 1) {
            return new bf.c();
        }
        if (skillid == 2) {
            return new bf.d();
        }
        if (skillid == 3) {
            return new e();
        }
        if (skillid == 4) {
            return new f();
        }
        if (skillid == 5) {
            return new bf.g();
        }
        if (skillid == 6) {
            return new bf.h();
        }
        if (skillid == 7) {
            return new bf.i();
        }
        if (skillid == 8) {
            return new bf.j();
        }
        if (skillid == 9) {
            return new bf.k();
        }
        if (skillid == 10) {
            return new bf.l();
        }
        if (skillid == 11) {
            return new m();
        }
        if (skillid == 12) {
            return new bf.n();
        }
        if (skillid == 13) {
            return new bf.o();
        }
        if (skillid == 14) {
            return new bf.p();
        }
        if (skillid == 15) {
            return new bf.q();
        }
        if (skillid == 16) {
            return new bf.r();
        }
        if (skillid == 17) {
            return new bf.s();
        }
        if (skillid == 18) {
            return new bf.t();
        }
        if (skillid == 19) {
            return new bf.u();
        }
        if (skillid == 20) {
            return new v();
        }
        if (skillid == 21) {
            return new bf.w();
        }
        if (skillid == 22) {
            return new bf.x();
        }
        if (skillid == 23) {
            return new bf.y();
        }
        if (skillid == 25) {
            return new z();
        }
        if (skillid == 26) {
            return new bf.aa();
        }
        if (skillid == 27) {
            return new bf.ab();
        }
        if (skillid == 28) {
            return new bf.ac();
        }
        if (skillid == 29) {
            return new bf.ad();
        }
        if (skillid == 30) {
            return new bf.ae();
        }
        if (skillid == 31) {
            return new bf.af();
        }
        if (skillid == 32) {
            return new bf.ag();
        }
        if (skillid == 33) {
            return new bf.ah();
        }
        if (skillid == 34) {
            return new bf.ai();
        }
        if (skillid == 35) {
            return new bf.aj();
        }
        if (skillid == 36) {
            return new bf.ak();
        }
        if (skillid == 37) {
            return new bf.al();
        }
        if (skillid == 38) {
            return new bf.am();
        }
        if (skillid == 39) {
            return new bf.an();
        }
        if (skillid == 40) {
            return new bf.ao();
        }
        if (skillid == 41) {
            return new bf.ap();
        }
        if (skillid == 42) {
            return new bf.aq();
        }
        if (skillid == 43) {
            return new bf.ar();
        }
        if (skillid == 44) {
            return new bf.as();
        }
        if (skillid == 45) {
            return new bf.at();
        }
        if (skillid == 46) {
            return new bf.au();
        }
        if (skillid == 47) {
            return new bf.av();
        }
        if (skillid == 48) {
            return new bf.aw();
        }
        if (skillid == 49) {
            return new bf.ax();
        }
        if (skillid == 50) {
            return new bf.ay();
        }
        if (skillid == 51) {
            return new bf.az();
        }
        if (skillid == 52) {
            return new bf.ba();
        }
        if (skillid == 53) {
            return new bf.bb();
        }
        if (skillid == 54) {
            return new bf.bc();
        }
        if (skillid == 55) {
            return new bf.bd();
        }
        if (skillid == 56) {
            return new bf.be();
        }
        if (skillid == 57) {
            return new bf.bf();
        }
        if (skillid == 58) {
            return new bg();
        }
        if (skillid == 59) {
            return new bh();
        }
        if (skillid == 60) {
            return new bi();
        }
        if (skillid == 61) {
            return new bj();
        }
        if (skillid == 62) {
            return new bk();
        }
        if (skillid == 63) {
            return new bl();
        }
        if (skillid == 64) {
            return new bm();
        }
        if (skillid == 65) {
            return new bn();
        }
        if (skillid == 66) {
            return new bo();
        }
        if (skillid == 67) {
            return new bp();
        }
        if (skillid == 68) {
            return new bq();
        }
        if (skillid == 69) {
            return new br();
        }
        if (skillid == 70) {
            return new bs();
        }
        if (skillid == 71) {
            return new bt();
        }
        if (skillid == 72) {
            return new bu();
        }
        if (skillid == 73) {
            return new bv();
        }
        if (skillid == 74) {
            return new bw();
        }
        if (skillid == 75) {
            return new bx();
        }
        if (skillid == 76) {
            return new by();
        }
        if (skillid == 77) {
            return new bz();
        }
        if (skillid == 78) {
            return new ca();
        }
        if (skillid == 79) {
            return new cb();
        }
        if (skillid == 80) {
            return new cc();
        }
        if (skillid == 87) {
            return new cd();
        }
        if (skillid == 88) {
            return new ce();
        }
        if (skillid == 89) {
            return new cf();
        }
        if (skillid == 90) {
            return new cg();
        }
        if (skillid == 91) {
            return new ch();
        }
        if (skillid == 92) {
            return new ci();
        }
        if (skillid == 97) {
            return new cj();
        }
        if (skillid == 98) {
            return new ck();
        }
        if (skillid == 99) {
            return new cl();
        }
        if (skillid == 100) {
            return new cm();
        }
        if (skillid == 101) {
            return new cn();
        }
        if (skillid == 102) {
            return new co();
        }
        if (skillid == 103) {
            return new cp();
        }
        if (skillid == 104) {
            return new cq();
        }
        if (skillid == 105) {
            return new cr();
        }
        if (skillid == 106) {
            return new cs();
        }
        if (skillid == 107) {
            return new ct();
        }
        if (skillid == 108) {
            return new cu();
        }
        if (skillid == 109) {
            return new cv();
        }
        if (skillid == 110) {
            return new cw();
        }
        if (skillid == 111) {
            return new cx();
        }
        if (skillid == 112) {
            return new cy();
        }
        if (skillid == 113) {
            return new cz();
        }
        if (skillid == 114) {
            return new da();
        }
        if (skillid == 115) {
            return new db();
        }
        if (skillid == 116) {
            return new dc();
        }
        if (skillid == 117) {
            return new dd();
        }
        if (skillid == 118) {
            return new de();
        }
        if (skillid == 119) {
            return new df();
        }
        if (skillid == 120) {
            return new dg();
        }
        if (skillid == 121) {
            return new dh();
        }
        if (skillid == 122) {
            return new di();
        }
        if (skillid == 129) {
            return new dj();
        }
        if (skillid == 130) {
            return new dk();
        }
        if (skillid == 131) {
            return new dl();
        }
        if (skillid == 132) {
            return new dm();
        }
        if (skillid == 133) {
            return new dn();
        }
        if (skillid == 134) {
            return new do();
        }
        if (skillid == 135) {
            return new dp();
        }
        if (skillid == 137) {
            return new dq();
        }
        if (skillid == 138) {
            return new dr();
        }
        if (skillid == 145) {
            return new ds();
        }
        if (skillid == 146) {
            return new dt();
        }
        if (skillid == 147) {
            return new du();
        }
        if (skillid == 148) {
            return new dv();
        }
        if (skillid == 149) {
            return new dw();
        }
        if (skillid == 150) {
            return new dx();
        }
        if (skillid == 151) {
            return new dy();
        }
        if (skillid == 152) {
            return new dz();
        }
        if (skillid == 153) {
            return new ea();
        }
        if (skillid == 154) {
            return new eb();
        }
        if (skillid == 155) {
            return new ec();
        }
        if (skillid == 156) {
            return new ed();
        }
        if (skillid == 157) {
            return new ee();
        }
        if (skillid == 158) {
            return new ef();
        }
        if (skillid == 159) {
            return new eg();
        }
        if (skillid == 160) {
            return new eh();
        }
        if (skillid == 161) {
            return new ei();
        }
        if (skillid == 162) {
            return new ej();
        }
        if (skillid == 163) {
            return new ek();
        }
        if (skillid == 164) {
            return new el();
        }
        if (skillid == 165) {
            return new em();
        }
        if (skillid == 166) {
            return new en();
        }
        if (skillid == 167) {
            return new eo();
        }
        if (skillid == 168) {
            return new ep();
        }
        if (skillid == 169) {
            return new eq();
        }
        if (skillid == 170) {
            return new er();
        }
        if (skillid == 171) {
            return new es();
        }
        if (skillid == 172) {
            return new et();
        }
        if (skillid == 173) {
            return new eu();
        }
        if (skillid == 174) {
            return new ev();
        }
        if (skillid == 175) {
            return new ew();
        }
        if (skillid == 176) {
            return new ex();
        }
        if (skillid == 181) {
            return new ey();
        }
        if (skillid == 182) {
            return new ez();
        }
        if (skillid == 183) {
            return new fa();
        }
        if (skillid == 184) {
            return new fb();
        }
        if (skillid == 185) {
            return new fc();
        }
        if (skillid == 186) {
            return new fd();
        }
        if (skillid == 187) {
            return new fe();
        }
        if (skillid == 188) {
            return new ff();
        }
        if (skillid == 189) {
            return new fg();
        }
        if (skillid == 190) {
            return new fh();
        }
        if (skillid == 191) {
            return new fi();
        }
        if (skillid == 192) {
            return new fj();
        }
        if (skillid == 193) {
            return new fk();
        }
        if (skillid == 194) {
            return new fl();
        }
        if (skillid == 195) {
            return new fm();
        }
        if (skillid == 196) {
            return new fn();
        }
        if (skillid == 201) {
            return new fo();
        }
        if (skillid == 202) {
            return new fp();
        }
        if (skillid == 203) {
            return new fq();
        }
        if (skillid == 204) {
            return new fr();
        }
        if (skillid == 205) {
            return new fs();
        }
        if (skillid == 206) {
            return new ft();
        }
        if (skillid == 207) {
            return new fu();
        }
        if (skillid == 208) {
            return new fv();
        }
        if (skillid == 209) {
            return new fw();
        }
        if (skillid == 210) {
            return new fx();
        }
        if (skillid == 211) {
            return new fy();
        }
        if (skillid == 212) {
            return new fz();
        }
        if (skillid == 213) {
            return new ga();
        }
        if (skillid == 214) {
            return new gb();
        }
        if (skillid == 215) {
            return new gc();
        }
        if (skillid == 216) {
            return new gd();
        }
        if (skillid == 217) {
            return new ge();
        }
        if (skillid == 218) {
            return new gf();
        }
        if (skillid == 219) {
            return new gg();
        }
        if (skillid == 220) {
            return new gh();
        }
        if (skillid == 222) {
            return new gi();
        }
        if (skillid == 225) {
            return new gj();
        }
        if (skillid == 226) {
            return new gk();
        }
        if (skillid == 228) {
            return new gl();
        }
        if (skillid == 229) {
            return new gm();
        }
        if (skillid == 230) {
            return new gn();
        }
        if (skillid == 231) {
            return new go();
        }
        if (skillid == 233) {
            return new gp();
        }
        return null;
    }

    public static al.l c(String name) {
        if (name.equalsIgnoreCase("L1Echo")) {
            return al.r.a();
        }
        if (name.equalsIgnoreCase("L1Status")) {
            return az.a();
        }
        if (name.equalsIgnoreCase("L1Summon")) {
            return ba.a();
        }
        if (name.equalsIgnoreCase("L1DeleteGroundItem")) {
            return al.p.a();
        }
        if (name.equalsIgnoreCase("L1AddSkill")) {
            return al.c.a();
        }
        if (name.equalsIgnoreCase("L1Level")) {
            return ag.a();
        }
        if (name.equalsIgnoreCase("L1Loc")) {
            return ai.a();
        }
        if (name.equalsIgnoreCase("L1Describe")) {
            return al.q.a();
        }
        if (name.equalsIgnoreCase("L1Who")) {
            return bf.a();
        }
        if (name.equalsIgnoreCase("L1AllBuff")) {
            return al.e.a();
        }
        if (name.equalsIgnoreCase("L1Speed")) {
            return ay.a();
        }
        if (name.equalsIgnoreCase("L1Adena")) {
            return al.d.a();
        }
        if (name.equalsIgnoreCase("L1HpBar")) {
            return aa.a();
        }
        if (name.equalsIgnoreCase("L1ResetTrap")) {
            return as.a();
        }
        if (name.equalsIgnoreCase("L1ReloadTrap")) {
            return ar.a();
        }
        if (name.equalsIgnoreCase("L1ShowTrap")) {
            return av.a();
        }
        if (name.equalsIgnoreCase("L1CastGfx")) {
            return al.h.a();
        }
        if (name.equalsIgnoreCase("L1GfxId")) {
            return al.w.a();
        }
        if (name.equalsIgnoreCase("L1InvGfxId")) {
            return ac.a();
        }
        if (name.equalsIgnoreCase("L1HomeTown")) {
            return al.z.a();
        }
        if (name.equalsIgnoreCase("L1GM")) {
            return al.u.a();
        }
        if (name.equalsIgnoreCase("L1Present")) {
            return ap.a();
        }
        if (name.equalsIgnoreCase("L1LevelPresent")) {
            return ah.a();
        }
        if (name.equalsIgnoreCase("L1Shutdown")) {
            return aw.a();
        }
        if (name.equalsIgnoreCase("L1CreateItem")) {
            return al.n.a();
        }
        if (name.equalsIgnoreCase("L1CreateItemSet")) {
            return al.o.a();
        }
        if (name.equalsIgnoreCase("L1Buff")) {
            return al.g.a();
        }
        if (name.equalsIgnoreCase("L1Patrol")) {
            return am.a();
        }
        if (name.equalsIgnoreCase("L1BanIp")) {
            return al.f.a();
        }
        if (name.equalsIgnoreCase("L1Chat")) {
            return al.j.a();
        }
        if (name.equalsIgnoreCase("L1ChatNG")) {
            return al.k.a();
        }
        if (name.equalsIgnoreCase("L1SKick")) {
            return au.a();
        }
        if (name.equalsIgnoreCase("L1Kick")) {
            return ae.a();
        }
        if (name.equalsIgnoreCase("L1PowerKick")) {
            return ao.a();
        }
        if (name.equalsIgnoreCase("L1AccountBanKick")) {
            return al.a.a();
        }
        if (name.equalsIgnoreCase("L1Poly")) {
            return an.a();
        }
        if (name.equalsIgnoreCase("L1Ress")) {
            return at.a();
        }
        if (name.equalsIgnoreCase("L1Kill")) {
            return af.a();
        }
        if (name.equalsIgnoreCase("L1GMRoom")) {
            return al.v.a();
        }
        if (name.equalsIgnoreCase("L1ToPC")) {
            return bc.a();
        }
        if (name.equalsIgnoreCase("L1Move")) {
            return ak.a();
        }
        if (name.equalsIgnoreCase("L1ChangeWeather")) {
            return al.i.a();
        }
        if (name.equalsIgnoreCase("L1ToSpawn")) {
            return bd.a();
        }
        if (name.equalsIgnoreCase("L1Favorite")) {
            return al.s.a();
        }
        if (name.equalsIgnoreCase("L1Recall")) {
            return al.aq.a();
        }
        if (name.equalsIgnoreCase("L1Visible")) {
            return al.be.a();
        }
        if (name.equalsIgnoreCase("L1PartyRecall")) {
            return al.a();
        }
        if (name.equalsIgnoreCase("L1Invisible")) {
            return ad.a();
        }
        if (name.equalsIgnoreCase("L1SpawnCmd")) {
            return ax.a();
        }
        if (name.equalsIgnoreCase("L1InsertSpawn")) {
            return ab.a();
        }
        if (name.equalsIgnoreCase("L1CommandHelp")) {
            return al.m.a();
        }
        if (name.equalsIgnoreCase("L1Action")) {
            return al.b.a();
        }
        if (name.equalsIgnoreCase("L1Tile")) {
            return bb.a();
        }
        if (name.equalsIgnoreCase("L1FindInvis")) {
            return al.t.a();
        }
        if (name.equalsIgnoreCase("L1MapMove")) {
            return aj.a();
        }
        if (name.equalsIgnoreCase("L1GfxNpc")) {
            return al.y.a();
        }
        if (name.equalsIgnoreCase("L1GfxInvList")) {
            return al.x.a();
        }
        return null;
    }

    public static bd.i a(String name, bd.j storage) {
        if (name.equalsIgnoreCase("L1DamageTrap")) {
            return new bd.b(storage);
        }
        if (name.equalsIgnoreCase("L1HealingTrap")) {
            return new bd.c(storage);
        }
        if (name.equalsIgnoreCase("L1MonsterTrap")) {
            return new bd.d(storage);
        }
        if (name.equalsIgnoreCase("L1PoisonTrap")) {
            return new bd.f(storage);
        }
        if (name.equalsIgnoreCase("L1SkillTrap")) {
            return new bd.g(storage);
        }
        if (name.equalsIgnoreCase("L1TeleportTrap")) {
            return new bd.h(storage);
        }
        return null;
    }

    public static String d(String desc) throws Exception {
        if (b.isEmpty()) {
            String line = null;
            LineNumberReader lnr = new LineNumberReader(new FileReader(new File("./data/desc-c.tbl")));
            while ((line = lnr.readLine()) != null) {
                if (line.startsWith("#") || line.trim().length() == 0) continue;
                b.put(String.valueOf(lnr.getLineNumber() - 2), line);
            }
            lnr.close();
        }
        String result = "";
        if (!desc.contains("$")) {
            return result;
        }
        if (desc.endsWith("R")) {
            desc = desc.replace("R", "");
        }
        if ((desc = String.valueOf(desc) + "\u25ce").contains(" (")) {
            desc = desc.replace(" (", "\u25ce(");
        }
        if (desc.contains(" ")) {
            desc = desc.replace(" ", "\u25ce ");
        }
        String[] stringArray = desc.split(" ");
        int n2 = stringArray.length;
        int n3 = 0;
        while (n3 < n2) {
            String s2 = stringArray[n3];
            String descNo = s2.substring(s2.indexOf("$") + 1, s2.indexOf("\u25ce"));
            result = b.containsKey(descNo.trim()) ? String.valueOf(result) + b.get(descNo) + " " : String.valueOf(result) + descNo + " ";
            ++n3;
        }
        return result.trim();
    }

    public static String a(byte[] abyte0) {
        StringBuffer stringbuffer = new StringBuffer();
        int j2 = 0;
        int k2 = 0;
        while (k2 < abyte0.length) {
            if (j2 % 16 == 0) {
                stringbuffer.append(g.a(k2, 4) + ": ");
            }
            stringbuffer.append(g.a(abyte0[k2] & 0xFF, 2) + " ");
            if (++j2 == 16) {
                stringbuffer.append("   ");
                int i1 = k2 - 15;
                int l1 = 0;
                while (l1 < 16) {
                    byte byte0;
                    if ((byte0 = abyte0[i1++]) > 31 && byte0 < 128) {
                        stringbuffer.append((char)byte0);
                    } else {
                        stringbuffer.append('.');
                    }
                    ++l1;
                }
                stringbuffer.append("\r\n");
                j2 = 0;
            }
            ++k2;
        }
        int l2 = abyte0.length % 16;
        if (l2 > 0) {
            int j1 = 0;
            while (j1 < 17 - l2) {
                stringbuffer.append("   ");
                ++j1;
            }
            int k1 = abyte0.length - l2;
            int i2 = 0;
            while (i2 < l2) {
                byte byte1;
                if ((byte1 = abyte0[k1++]) > 31 && byte1 < 128) {
                    stringbuffer.append((char)byte1);
                } else {
                    stringbuffer.append('.');
                }
                ++i2;
            }
            stringbuffer.append("\r\n");
        }
        return stringbuffer.toString();
    }

    public static String a(int i2, int j2) {
        String s2 = Integer.toHexString(i2);
        int k2 = s2.length();
        while (k2 < j2) {
            s2 = "0" + s2;
            ++k2;
        }
        return s2;
    }
}

