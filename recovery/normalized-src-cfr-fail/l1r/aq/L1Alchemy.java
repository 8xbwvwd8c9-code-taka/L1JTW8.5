/*
 * Decompiled with CFR 0.152.
 */
package l1r.aq;

import a.g;
import java.util.ArrayList;
import java.util.HashMap;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL5;
import l1r.an.PBMessageALL6;
import l1r.ao.ItemTable;
import l1r.ao.MagicDollTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Item;
import l1r.bi.Random;

public class L1Alchemy {
    private static L1Alchemy a;
    private final ArrayList<L1Item> b = new ArrayList();
    private final ArrayList<L1Item> c = new ArrayList();
    private final ArrayList<L1Item> d = new ArrayList();
    private final ArrayList<L1Item> e = new ArrayList();
    private final ArrayList<L1Item> f = new ArrayList();

    public static L1Alchemy a() {
        if (a == null) {
            a = new L1Alchemy();
        }
        return a;
    }

    private L1Alchemy() {
    }

    public void a(HashMap<Integer, MagicDollTable.L1R_a> doll_list) {
        for (MagicDollTable.L1R_a dt : doll_list.values()) {
            if (dt.d == 1) {
                this.b.add(ItemTable.a().a(dt.a));
                continue;
            }
            if (dt.d == 2) {
                this.c.add(ItemTable.a().a(dt.a));
                continue;
            }
            if (dt.d == 3) {
                this.d.add(ItemTable.a().a(dt.a));
                continue;
            }
            if (dt.d == 4) {
                this.e.add(ItemTable.a().a(dt.a));
                continue;
            }
            if (dt.d != 5) continue;
            this.f.add(ItemTable.a().a(dt.a));
        }
    }

    public void a(L1PcInstance pc) {
        PBMessageALL5.L1R_g.L1R_a Builder19 = PBMessageALL5.L1R_g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 4) {
            Builder19.e(this.c(i2));
            pc.a(new S_ProtoBuffers(123, Builder19.M().g()));
            ++i2;
        }
    }

    public void b(L1PcInstance pc) {
        PBMessageALL5.L1R_g.L1R_a Builder19 = PBMessageALL5.L1R_g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 5) {
            Builder19.f(this.b(i2));
            pc.a(new S_ProtoBuffers(123, Builder19.M().g()));
            ++i2;
        }
    }

    public void c(L1PcInstance pc) {
        PBMessageALL5.L1R_g.L1R_a Builder19 = PBMessageALL5.L1R_g.aa();
        Builder19.a(1);
        int i2 = 1;
        while (i2 <= 4) {
            Builder19.g(this.a(i2));
            pc.a(new S_ProtoBuffers(123, Builder19.M().g()));
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
        PBMessageALL6.L1R_g.L1R_a Builder24 = PBMessageALL6.L1R_g.aa();
        Builder24.c(level);
        int position = 1;
        while (position <= 4) {
            PBMessageALL.L1R_a.L1R_a Builder1 = PBMessageALL.L1R_a.aa();
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
            PBMessageALL6.L1R_e.L1R_a Builder23 = PBMessageALL6.L1R_e.aa();
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
            PBMessageALL6.L1R_e.L1R_a Builder23 = PBMessageALL6.L1R_e.aa();
            Builder23.b(position3[0]);
            Builder23.b(position3[1]);
            Builder23.b(position3[2]);
            Builder23.c(level + 1);
            Builder24.f(Builder23.M().f());
            ++n2;
        }
        PBMessageALL6.L1R_e.L1R_a Builder23 = PBMessageALL6.L1R_e.aa();
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
        PBMessageALL3.L1R_a.L1R_a Builder10 = PBMessageALL3.L1R_a.aa();
        Builder10.b(level);
        for (L1Item l1Item : list) {
            PBMessageALL.L1R_a.L1R_a Builder1 = PBMessageALL.L1R_a.aa();
            Builder1.a(l1Item.W());
            Builder1.b(l1Item.m());
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
        PBMessageALL3.L1R_a.L1R_a Builder10 = PBMessageALL3.L1R_a.aa();
        Builder10.b(level);
        for (L1Item l1Item : list) {
            PBMessageALL.L1R_a.L1R_a Builder1 = PBMessageALL.L1R_a.aa();
            Builder1.a(l1Item.W());
            Builder10.e(Builder1.M().f());
        }
        return Builder10.M().f();
    }

    public void a(L1PcInstance pc, int level, ArrayList<L1ItemInstance> materialList) {
        for (L1ItemInstance material : materialList) {
            if (material.bb() == null && !pc.O(material.fr())) continue;
            pc.a(new S_ProtoBuffers(125, 8, 0, 0));
            return;
        }
        int[] nArray = new int[5];
        nArray[2] = 20;
        nArray[3] = 35;
        nArray[4] = 45;
        int[] changes = nArray;
        boolean isCheckOK = true;
        for (L1ItemInstance material : materialList) {
            if (pc.j().f(material) > 0) continue;
            isCheckOK = false;
        }
        if (isCheckOK) {
            int chance = changes[materialList.size()];
            int rnd = Random.a(100);
            if (rnd < chance) {
                boolean isBigSuccess = Random.a(100) < 10;
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
                L1Item l1item = (L1Item)list.get(Random.a(list.size()));
                L1ItemInstance newItem = ItemTable.a(pc, l1item.g(), 1, true);
                pc.a(new S_ProtoBuffers(125, isBigSuccess ? 10 : 0, newItem.fr(), newItem.e()));
                if (list.equals(this.f)) {
                    pc.a(new S_SkillSound(pc.fr(), 2047));
                    pc.b(new S_SkillSound(pc.fr(), 2047));
                    L1World.a().a(new S_ServerMessage(3599, newItem.a().j(), 0));
                }
            } else if (rnd > 40 + chance) {
                L1ItemInstance newItem = materialList.get(Random.a(materialList.size()));
                ItemTable.a(pc, newItem.N(), 1, true);
                pc.a(new S_ProtoBuffers(125, 1, newItem.fr(), newItem.e()));
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
                L1Item l1item = (L1Item)list.get(Random.a(list.size()));
                L1ItemInstance newItem = ItemTable.a(pc, l1item.g(), 1, true);
                pc.a(new S_ProtoBuffers(125, 0, newItem.fr(), newItem.e()));
            }
        }
    }
}
