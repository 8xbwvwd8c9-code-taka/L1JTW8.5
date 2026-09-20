/*
 * Decompiled with CFR 0.152.
 */
package l1r.aj;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.aj.ClientBasePacket;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_AddSkill;
import l1r.be.S_SkillBuyItem;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;
import l1r.bj.ClientThread;

public class C_SkillBuyItemOK
extends ClientBasePacket {
    public C_SkillBuyItemOK(byte[] abyte0, ClientThread client) throws Exception {
        super(abyte0);
        L1Skills l1skills;
        L1PcInstance pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int size = this.d();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        int i = 0;
        while (i < size) {
            block12: {
                int[] material_count;
                int[] material;
                int skillid;
                block14: {
                    int skillLv;
                    block15: {
                        block13: {
                            skillid = this.b() + 1;
                            l1skills = SkillsTable.a().a(skillid);
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
                    pc.a(new S_SkillBuyItem(skillid));
                } else {
                    list.add(skillid);
                    index = 0;
                    while (index < material.length) {
                        pc.j().b(material[index], material_count[index]);
                        ++index;
                    }
                }
            }
            ++i;
        }
        if (list.isEmpty()) {
            return;
        }
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            int id = (Integer)iterator.next();
            l1skills = SkillsTable.a().a(id);
            SkillsTable.a().a(pc.fr(), id, l1skills.b(), 0, 0);
        }
        pc.a(new S_SkillSound(pc.fr(), 224));
        pc.b(new S_SkillSound(pc.fr(), 224));
        pc.a(new S_AddSkill(pc, list));
    }

    @Override
    public String a() {
        return "C_SkillBuyItemOK";
    }
}
