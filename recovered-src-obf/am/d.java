/*
 * Decompiled with CFR 0.152.
 */
package am;

import a.g;
import an.a;
import an.b;
import an.c;
import an.d;
import an.e;
import an.f;
import an.g;
import an.h;
import ao.ah;
import bi.i;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private static d b;
    private static HashMap<Integer, String> c;
    private static HashMap<Integer, c> d;
    private static HashMap<Integer, d> e;
    private static HashMap<Integer, a> f;
    private static HashMap<Integer, b> g;
    private static HashMap<Integer, int[][]> h;
    private int i = 0;
    private final HashMap<Integer, d.i> j = new HashMap();
    private int[] k;

    static {
        c = new HashMap();
        d = new HashMap();
        e = new HashMap();
        f = new HashMap();
        g = new HashMap();
        h = new HashMap();
    }

    public static d a() {
        if (b == null) {
            b = new d();
        }
        return b;
    }

    public int b() {
        return this.i;
    }

    private d() {
        try {
            a.a msg1;
            byte[] data = Files.readAllBytes(Paths.get("./data/contents/npc-common.bin", new String[0]));
            byte[] data2 = Files.readAllBytes(Paths.get("./data/contents/criteria-common.bin", new String[0]));
            byte[] data3 = Files.readAllBytes(Paths.get("./data/contents/item-common.bin", new String[0]));
            byte[] data4 = Files.readAllBytes(Paths.get("./data/contents/quest-common.bin", new String[0]));
            byte[] data5 = Files.readAllBytes(Paths.get("./data/contents/achievement-common.bin", new String[0]));
            c.a msg10 = c.a.a(data3);
            for (g bs2 : msg10.q()) {
                e.g msg19 = e.g.a(bs2);
                int index = msg19.p();
                b.g item = b.g.a(msg19.r());
                a idata = new a();
                idata.a = item.p();
                idata.b = item.r();
                idata.c = item.t();
                idata.d = new String(item.v().e(), "BIG5");
                idata.e = new String(item.x().e(), "BIG5");
                idata.f = item.B();
                String classToken = "A";
                if (item.H() > 0) {
                    String[] tokens = new String[]{"P", "K", "E", "W", "D", "R", "I", "O"};
                    classToken = "";
                    Iterator<Object> iterator = item.G().iterator();
                    while (iterator.hasNext()) {
                        int i2 = (Integer)iterator.next();
                        classToken = String.valueOf(classToken) + tokens[i2];
                    }
                }
                idata.g = classToken;
                idata.h = -item.an();
                idata.i = item.aH();
                idata.j = item.aJ();
                f.put(index, idata);
            }
            f.g msg24 = f.g.a(data);
            for (g bs3 : msg24.q()) {
                c.c msg6 = c.c.a(bs3);
                b.i msg27 = b.i.a(msg6.r());
                int id = msg27.p();
                String name = new String(msg27.t().e(), "BIG5");
                c.put(id, name);
                c nd = new c();
                nd.a = bi.g.d(name);
                nd.b = msg27.r();
                nd.c = msg27.v();
                nd.d = msg27.x();
                nd.e = msg27.z();
                nd.f = msg27.B();
                nd.g = msg27.X();
                nd.h = msg27.E_();
                for (g bs2 : msg27.H_()) {
                    msg1 = a.a.a(bs2);
                    int idx = msg1.p();
                    a idata = f.get(idx);
                    if (idata == null || nd.i.contains(bi.g.d(idata.e))) continue;
                    nd.i.add(bi.g.d(idata.e));
                }
                d.put(id, nd);
            }
            f.g msg242 = f.g.a(data2);
            for (g bs4 : msg242.q()) {
                c.c msg6 = c.c.a(bs4);
                int number = msg6.p();
                g.a msg26 = g.a.a(msg6.r());
                int index = msg26.a(0);
                if (c.containsKey(index)) {
                    String desc = c.get(index);
                    b mobdata = new b(number, desc.replace(" ", ""), bi.g.d(desc).replace(" ", ""));
                    g.put(number, mobdata);
                }
                if (number <= this.i) continue;
                this.i = number;
            }
            c.a msg101 = c.a.a(data4);
            for (g bs5 : msg101.q()) {
                e.g msg19 = e.g.a(bs5);
                int index = msg19.p();
                h.g quest = h.g.a(msg19.r());
                int questID = quest.p();
                d qd = new d();
                e.put(questID, qd);
                qd.a = questID;
                if (!quest.B().d()) {
                    c.g msg8 = c.g.a(quest.B());
                    for (g bs2 : msg8.o()) {
                        a.a msg12 = a.a.a(bs2);
                        int type = msg12.p();
                        int itemid = msg12.r();
                        int count = msg12.t();
                        if (type == 2) {
                            qd.c.add("rewardExp =" + count + ";");
                            continue;
                        }
                        String itemName = "" + itemid;
                        if (f.containsKey(itemid)) {
                            itemName = bi.g.d(am.d.f.get((Object)Integer.valueOf((int)itemid)).e);
                        }
                        qd.c.add(String.valueOf(itemName) + ":" + count);
                    }
                }
                if (!quest.D().d()) {
                    c.a msg100 = c.a.a(quest.D());
                    int choiceCount = msg100.p();
                    for (g bs2 : msg100.q()) {
                        a.a msg13 = a.a.a(bs2);
                        int type = msg13.p();
                        int itemid = msg13.r();
                        int count = msg13.t();
                        if (type == 2) {
                            qd.d.add("rewardExp =" + count + ";");
                            continue;
                        }
                        String itemName = "" + itemid;
                        if (f.containsKey(itemid)) {
                            itemName = bi.g.d(am.d.f.get((Object)Integer.valueOf((int)itemid)).e);
                        }
                        qd.d.add(String.valueOf(itemName) + ":" + count);
                    }
                }
                if (quest.F().d()) continue;
                msg1 = a.a.a(quest.F());
                int mapid = msg1.r();
                int x2 = msg1.t();
                int y2 = msg1.v();
                qd.b = String.valueOf(x2) + "," + y2 + "," + mapid;
            }
            this.k = new int[this.b() * 3 + 30];
            c.a msg10a = c.a.a(data5);
            for (g bs6 : msg10a.q()) {
                c.a msg102 = c.a.a(bs6);
                for (g bs2 : msg102.q()) {
                    int stageCount;
                    h.c msga = h.c.a(bs2);
                    int index = msga.p();
                    a.g msg4 = a.g.a(msga.t());
                    a.a msg41 = a.a.a(msg4.p());
                    int number = msg41.p();
                    this.k[index - 1] = stageCount = msg41.r();
                    d.i msg15 = d.i.a(msga.x());
                    this.j.put(index, msg15);
                    a.a msg14 = a.a.a(msga.B());
                    int mapid = msg14.v();
                    int locx1 = msg14.x();
                    int locy1 = msg14.z();
                    int locx2 = msg14.B();
                    int locy2 = msg14.D();
                    int[][] locs = new int[2][3];
                    locs[0] = new int[]{locx1, locy1, mapid};
                    locs[1] = new int[]{locx2, locy2, mapid};
                    h.put(index, locs);
                }
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public d.i a(int number) {
        return this.j.get(number);
    }

    public int a(int idx, int killcount) {
        int stage = 0;
        while (stage < 3) {
            int needcount;
            int achievementIDX = idx * 3 + stage;
            if (achievementIDX >= 1670) {
                achievementIDX += 30;
            }
            if (killcount < (needcount = this.k[achievementIDX])) break;
            ++stage;
        }
        return stage;
    }

    public int[] b(int index) {
        if (h.containsKey(index)) {
            return h.get(index)[bi.i.a(2)];
        }
        return null;
    }

    public void c() throws Exception {
        File file = new File("QuestInfo.txt");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, false));
        for (d data : e.values()) {
            bw2.write("\u4efb\u52d9\u7de8\u865f:" + data.a + "\r\n");
            bw2.write("\t\u56fa\u5b9a\u734e\u52f5:\r\n");
            for (String s2 : data.c) {
                bw2.write("\t\t" + s2 + "\r\n");
            }
            String id = "";
            String count = "";
            String enchant = "";
            String note = "//";
            for (String s3 : data.c) {
                if (s3.contains("rewardExp")) continue;
                String[] ss = s3.split(":");
                id = String.valueOf(id) + ah.a().b(ss[0]) + ",";
                count = String.valueOf(count) + ss[1] + ",";
                enchant = String.valueOf(enchant) + "0,";
                note = String.valueOf(note) + s3 + "/";
            }
            bw2.write("\t\trewardItemid=new int[]{" + id + "};" + note + "\r\n");
            bw2.write("\t\trewardItemCount=new int[]{" + count + "};" + "\r\n");
            bw2.write("\t\trewardItemEnchant=new int[]{" + enchant + "};" + "\r\n");
            bw2.write("\t\u9078\u64c7\u734e\u52f5:\r\n");
            for (String s3 : data.d) {
                bw2.write("\t\t" + s3 + "\r\n");
            }
            bw2.write("\t\u79fb\u52d5\u5ea7\u6a19:\r\n\t\tteleportLoc = new int[]{" + data.b + "};\r\n");
            bw2.newLine();
        }
        bw2.close();
        System.out.println("\u4efb\u52d9\u8cc7\u6599(" + e.size() + ")\u76f8\u95dc\u8cc7\u8a0a\u5df2\u532f\u51fa\u81f3 QuestInfo.txt");
    }

    public void d() throws Exception {
        File file = new File("NpcInfo.txt");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, false));
        for (c data : d.values()) {
            if (data.a.trim().length() == 0) continue;
            String drop = "";
            for (String s2 : data.i) {
                drop = String.valueOf(drop) + s2 + "||";
            }
            bw2.write(String.valueOf(data.a) + "\t" + data.c + "\t" + data.b + "\t" + data.e + "\t" + data.f + "\t" + data.d + "\t" + data.h + "\t" + data.g + "\t" + drop);
            bw2.newLine();
        }
        bw2.close();
        System.out.println("NPC\u8cc7\u6599(" + d.size() + ")\u76f8\u95dc\u8cc7\u8a0a\u5df2\u532f\u51fa\u81f3 NpcInfo.txt");
    }

    public void e() throws Exception {
        File file = new File("ItemInfo.txt");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, false));
        for (a data : f.values()) {
            bw2.write(String.valueOf(data.a) + "\t" + bi.g.d(data.e) + "\t" + data.e + "\t" + data.b + "\t" + data.c + "\t" + data.f);
            bw2.newLine();
        }
        bw2.close();
        System.out.println("\u9053\u5177\u8cc7\u6599(" + f.size() + ")\u76f8\u95dc\u8cc7\u8a0a\u5df2\u532f\u51fa\u81f3 ItemInfo.txt");
    }

    public void f() throws Exception {
        File file = new File("MonsterListInfo.txt");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, true));
        for (b mobdata : g.values()) {
            bw2.write(String.valueOf(mobdata.a) + "\t" + mobdata.b + "\t" + mobdata.c);
            bw2.newLine();
        }
        bw2.close();
        System.out.println("\u602a\u7269\u5716\u9451(" + g.size() + ")\u76f8\u95dc\u8cc7\u8a0a\u5df2\u532f\u51fa\u81f3 MonsterListInfo.txt");
    }

    class a {
        public int a;
        public int b;
        public int c;
        public String d;
        public String e;
        public int f;
        public String g = "A";
        public int h;
        public int i;
        public int j;

        a() {
        }
    }

    class b {
        public int a;
        public String b;
        public String c;

        public b(int _number, String _desc, String _name) {
            this.a = _number;
            this.b = _desc;
            this.c = _name;
        }
    }

    class c {
        public String a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public ArrayList<String> i = new ArrayList();

        c() {
        }
    }

    class d {
        public int a;
        public String b = "";
        public ArrayList<String> c = new ArrayList();
        public ArrayList<String> d = new ArrayList();

        d() {
        }
    }
}

