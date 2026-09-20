/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.q;
import ap.u;
import aq.f;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class bk {
    private static final Logger a = Logger.getLogger(bk.class.getName());
    private static bk b;
    private static final HashMap<Integer, b> c;

    static {
        c = new HashMap();
    }

    public static bk a() {
        if (b == null) {
            b = new bk();
        }
        return b;
    }

    private bk() {
        long begin;
        block9: {
            begin = System.currentTimeMillis();
            System.out.print("loading TreasureBoxTable...");
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM treasure_box");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int boxID = rs.getInt("box_itemid");
                        b box = null;
                        if (c.containsKey(boxID)) {
                            box = c.get(boxID);
                        } else {
                            box = new b(boxID);
                            c.put(boxID, box);
                        }
                        a item = new a();
                        item.a = rs.getInt("itemid");
                        item.b = rs.getInt("count");
                        item.c = rs.getInt("enchant");
                        item.d = rs.getInt("chance");
                        item.e = rs.getInt("use_day");
                        item.f = rs.getInt("bless_change");
                        item.g = rs.getInt("unbless_change");
                        box.c.add(item);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block9;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        for (b box : c.values()) {
            box.a();
        }
        System.out.println("OK! " + (System.currentTimeMillis() - begin) + "ms");
    }

    public boolean a(int itemid, u pc) {
        if (!c.containsKey(itemid)) {
            return false;
        }
        return bk.c.get(itemid).a(pc);
    }

    private class a {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;

        private a() {
        }
    }

    private class b {
        public int a;
        public int b;
        public CopyOnWriteArrayList<a> c = new CopyOnWriteArrayList();

        public b(int id) {
            this.a = id;
        }

        private void a() {
            for (a each : this.c) {
                this.b += each.d;
                if (ah.a().a(each.a) != null) continue;
                this.c.remove(each);
                System.out.println("item ID " + each.a + " is not found\u3002");
            }
            if (this.b != 0 && this.b != 1000000) {
                System.out.println("ID " + this.a + " \u7684\u7e3d\u6a5f\u7387\u4e0d\u7b49\u65bc100%\u3002" + this.b);
            }
            Collections.sort(this.c, new Comparator<a>(){

                public int a(a r1, a r2) {
                    return r1.d - r2.d;
                }

                @Override
                public /* synthetic */ int compare(Object object, Object object2) {
                    return this.a((a)object, (a)object2);
                }
            });
        }

        private boolean a(u pc) {
            q item = null;
            if (this.b == 0) {
                for (a each : this.c) {
                    int bless = 1;
                    if (i.a(100) < each.f) {
                        bless = 0;
                    } else if (i.a(100) < each.g) {
                        bless = 2;
                    }
                    item = ah.a(pc, each.a, each.b, each.c, bless, false, each.e);
                }
            } else {
                int chance = 0;
                int r2 = i.a(this.b);
                for (a each : this.c) {
                    if (r2 >= (chance += each.d) && each.d != 0) continue;
                    int bless = 1;
                    if (i.a(100) < each.f) {
                        bless = 0;
                    } else if (i.a(100) < each.g) {
                        bless = 2;
                    }
                    if (this.a == 640353) {
                        int[] counts;
                        if (each.a == 40308) {
                            counts = new int[]{50000, 50000, 50000, 100000, 100000, 500000};
                            each.b = counts[i.a(counts.length)];
                        } else if (each.a == 640369) {
                            counts = new int[]{3, 5, 10, 20};
                            each.b = counts[i.a(counts.length)];
                        }
                    }
                    item = ah.a(pc, each.a, each.b, each.c, bless, false, each.e);
                    if (each.d == 0) {
                        continue;
                    }
                    break;
                }
            }
            if (item == null) {
                return false;
            }
            if (this.a == 40576 || this.a == 40577 || this.a == 40578 || this.a == 40411 || this.a == 49013) {
                pc.b((f)null);
            }
            if (this.a == 46000 || this.a >= 640503 && this.a <= 640506) {
                q box = pc.j().b(this.a);
                box.g(box.I() - 1);
                pc.j().b(box);
                if (box.I() < 1) {
                    pc.j().b(box, 1);
                }
            }
            if (this.a == 640353) {
                ah.a(pc, 41352, 1);
            }
            return true;
        }
    }
}

