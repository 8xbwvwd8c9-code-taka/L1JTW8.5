/*
 * Decompiled with CFR 0.152.
 */
package aq;

import a.g;
import an.a;
import an.c;
import an.e;
import an.f;
import ao.ah;
import ao.am;
import ap.q;
import ap.u;
import aq.aq;
import be.dc;
import be.ds;
import be.ee;
import bh.j;
import bi.i;
import java.util.ArrayList;
import java.util.HashMap;

public class b {
    private static b a;
    private final ArrayList<j> b = new ArrayList();
    private final ArrayList<j> c = new ArrayList();
    private final ArrayList<j> d = new ArrayList();
    private final ArrayList<j> e = new ArrayList();
    private final ArrayList<j> f = new ArrayList();

    public static b a() {
        if (a == null) {
            a = new b();
        }
        return a;
    }

    private b() {
    }

    public void a(HashMap<Integer, am.a> doll_list) {
        for (am.a dt2 : doll_list.values()) {
            if (dt2.d == 1) {
                this.b.add(ah.a().a(dt2.a));
                continue;
            }
            if (dt2.d == 2) {
                this.c.add(ah.a().a(dt2.a));
                continue;
            }
            if (dt2.d == 3) {
                this.d.add(ah.a().a(dt2.a));
                continue;
            }
            if (dt2.d == 4) {
                this.e.add(ah.a().a(dt2.a));
                continue;
            }
            if (dt2.d != 5) continue;
            this.f.add(ah.a().a(dt2.a));
        }
    }

    public void a(u pc) {
        e.g.a Builder19 = e.g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 4) {
            Builder19.e(this.c(i2));
            pc.a(new dc(123, Builder19.M().g()));
            ++i2;
        }
    }

    public void b(u pc) {
        e.g.a Builder19 = e.g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 5) {
            Builder19.f(this.b(i2));
            pc.a(new dc(123, Builder19.M().g()));
            ++i2;
        }
    }

    public void c(u pc) {
        e.g.a Builder19 = e.g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 4) {
            Builder19.g(this.a(i2));
            pc.a(new dc(123, Builder19.M().g()));
            ++i2;
        }
    }

    public g a(int level) {
        int[][] positions_3;
        int[][] positions_2;
        ArrayList<Object> list = new ArrayList();
        if (level == 1) {
            list = this.b;
        } else if (level == 2) {
            list = this.c;
        } else if (level == 3) {
            list = this.d;
        } else if (level == 4) {
            list = this.e;
        } else if (level == 5) {
            list = this.f;
        }
        f.g.a Builder24 = f.g.aa();
        Builder24.c(level);
        int position = 1;
        while (position <= 4) {
            a.a.a Builder1 = a.a.aa();
            Builder1.a(position);
            Builder1.b(0);
            Builder1.c(level);
            Builder24.e(Builder1.M().f());
            ++position;
        }
        int[][] nArrayArray = positions_2 = new int[][]{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}};
        int n2 = positions_2.length;
        int n3 = 0;
        while (n3 < n2) {
            int[] position2 = nArrayArray[n3];
            f.e.a Builder23 = f.e.aa();
            Builder23.b(position2[0]);
            Builder23.b(position2[1]);
            Builder23.c(level + 1);
            Builder24.f(Builder23.M().f());
            ++n3;
        }
        int[][] nArrayArray2 = positions_3 = new int[][]{{1, 2, 3}, {1, 2, 4}, {1, 3, 4}, {2, 3, 4}};
        int n4 = positions_3.length;
        n2 = 0;
        while (n2 < n4) {
            int[] position3 = nArrayArray2[n2];
            f.e.a Builder23 = f.e.aa();
            Builder23.b(position3[0]);
            Builder23.b(position3[1]);
            Builder23.b(position3[2]);
            Builder23.c(level + 1);
            Builder24.f(Builder23.M().f());
            ++n2;
        }
        f.e.a Builder23 = f.e.aa();
        Builder23.b(1);
        Builder23.b(2);
        Builder23.b(3);
        Builder23.b(4);
        Builder23.c(level + 1);
        if (level <= 3) {
            Builder23.e(g.a(new byte[]{16, (byte)(level + 2)}));
        }
        Builder24.f(Builder23.M().f());
        return Builder24.M().f();
    }

    public g b(int level) {
        ArrayList<Object> list = new ArrayList();
        if (level == 1) {
            list = this.b;
        } else if (level == 2) {
            list = this.c;
        } else if (level == 3) {
            list = this.d;
        } else if (level == 4) {
            list = this.e;
        } else if (level == 5) {
            list = this.f;
        }
        c.a.a Builder10 = c.a.aa();
        Builder10.b(level);
        for (j j2 : list) {
            a.a.a Builder1 = a.a.aa();
            Builder1.a(j2.W());
            Builder1.b(j2.m());
            Builder10.e(Builder1.M().f());
        }
        return Builder10.M().f();
    }

    public g c(int level) {
        ArrayList<Object> list = new ArrayList();
        if (level == 1) {
            list = this.b;
        } else if (level == 2) {
            list = this.c;
        } else if (level == 3) {
            list = this.d;
        } else if (level == 4) {
            list = this.e;
        } else if (level == 5) {
            list = this.f;
        }
        c.a.a Builder10 = c.a.aa();
        Builder10.b(level);
        for (j j2 : list) {
            a.a.a Builder1 = a.a.aa();
            Builder1.a(j2.W());
            Builder10.e(Builder1.M().f());
        }
        return Builder10.M().f();
    }

    public void a(u pc, int level, ArrayList<q> materialList) {
        for (q material : materialList) {
            if (material.bb() == null && !pc.O(material.fr())) continue;
            pc.a(new dc(125, 8, 0, 0));
            return;
        }
        int[] nArray = new int[5];
        nArray[2] = 20;
        nArray[3] = 35;
        nArray[4] = 45;
        int[] changes = nArray;
        boolean isCheckOK = true;
        for (q material : materialList) {
            if (pc.j().f(material) > 0) continue;
            isCheckOK = false;
        }
        if (isCheckOK) {
            int chance = changes[materialList.size()];
            int rnd = i.a(100);
            if (rnd < chance) {
                boolean isBigSuccess = i.a(100) < 10;
                ArrayList<Object> list = new ArrayList();
                if (level == 1) {
                    list = isBigSuccess ? this.d : this.c;
                } else if (level == 2) {
                    list = isBigSuccess ? this.e : this.d;
                } else if (level == 3) {
                    list = isBigSuccess ? this.f : this.e;
                } else if (level == 4) {
                    list = this.f;
                }
                j l1item = (j)list.get(i.a(list.size()));
                q newItem = ah.a(pc, l1item.g(), 1, true);
                pc.a(new dc(125, isBigSuccess ? 10 : 0, newItem.fr(), newItem.e()));
                if (list.equals(this.f)) {
                    pc.a(new ee(pc.fr(), 2047));
                    pc.b(new ee(pc.fr(), 2047));
                    aq.a().a(new ds(3599, newItem.a().j(), 0));
                }
            } else if (rnd > 40 + chance) {
                q newItem = materialList.get(i.a(materialList.size()));
                ah.a(pc, newItem.N(), 1, true);
                pc.a(new dc(125, 1, newItem.fr(), newItem.e()));
            } else {
                ArrayList<Object> list = new ArrayList();
                if (level == 1) {
                    list = this.b;
                } else if (level == 2) {
                    list = this.c;
                } else if (level == 3) {
                    list = this.d;
                } else if (level == 4) {
                    list = this.e;
                } else if (level == 5) {
                    list = this.f;
                }
                j l1item = (j)list.get(i.a(list.size()));
                q newItem = ah.a(pc, l1item.g(), 1, true);
                pc.a(new dc(125, 0, newItem.fr(), newItem.e()));
            }
        }
    }
}

