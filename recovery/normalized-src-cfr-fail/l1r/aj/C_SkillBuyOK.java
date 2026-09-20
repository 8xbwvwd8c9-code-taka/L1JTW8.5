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
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;
import l1r.bj.ClientThread;

public class C_SkillBuyOK
extends ClientBasePacket {
    public C_SkillBuyOK(byte[] abyte0, ClientThread clientthread) throws Exception {
        super(abyte0);
        L1Skills l1skills;
        L1PcInstance pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int size = this.d();
        int cost_price = 0;
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        int i = 0;
        while (i < size) {
            int skillid = this.b() + 1;
            l1skills = SkillsTable.a().a(skillid);
            int level = l1skills.c();
            int skillLv = l1skills.c();
            if (pc.U() >= skillLv) {
                cost_price += level * level * 100;
                list.add(skillid);
            }
            ++i;
        }
        if (list.isEmpty()) {
            return;
        }
        if (!pc.j().b(40308, cost_price)) {
            pc.a(new S_ServerMessage(189));
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
        return "C_SkillBuyOK";
    }
}
