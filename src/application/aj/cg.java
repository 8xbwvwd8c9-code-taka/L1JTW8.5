/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.be;
import ap.u;
import be.dz;
import be.ee;
import bh.v;
import bj.d;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class cg
extends cv {
    public cg(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        v l1skills;
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int size = this.d();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        int i2 = 0;
        while (i2 < size) {
            block12: {
                int[] material_count;
                int[] material;
                int skillid;
                block14: {
                    int skillLv;
                    block15: {
                        block13: {
                            skillid = this.b() + 1;
                            l1skills = be.a().a(skillid);
                            skillLv = l1skills.c();
                            if (pc.U() < skillLv) break block12;
                            if (skillLv != 1) break block13;
                            material = new int[]{40503, 40494, 40520, 40519};
                            material_count = new int[]{10, 50, 100, 10};
                            break block14;
                        }
                        if (skillLv != 2) break block15;
                        material = new int[]{40495, 40499, 88, 40505};
                        material_count = new int[]{10, 8, 1, 3};
                        break block14;
                    }
                    if (skillLv != 3) break block12;
                    material = new int[]{40508, 40504, 40521, 88};
                    material_count = new int[]{45, 3, 3, 3};
                }
                boolean check = true;
                int index = 0;
                while (index < material.length) {
                    if (!pc.j().g(material[index], material_count[index])) {
                        check = false;
                        break;
                    }
                    ++index;
                }
                if (!check) {
                    pc.a(new dz(skillid));
                } else {
                    list.add(skillid);
                    index = 0;
                    while (index < material.length) {
                        pc.j().b(material[index], material_count[index]);
                        ++index;
                    }
                }
            }
            ++i2;
        }
        if (list.isEmpty()) {
            return;
        }
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            int id = (Integer)iterator.next();
            l1skills = be.a().a(id);
            be.a().a(pc.fr(), id, l1skills.b(), 0, 0);
        }
        pc.a(new ee(pc.fr(), 224));
        pc.b(new ee(pc.fr(), 224));
        pc.a(new be.d(pc, list));
    }

    @Override
    public String a() {
        return "C_SkillBuyItemOK";
    }
}

