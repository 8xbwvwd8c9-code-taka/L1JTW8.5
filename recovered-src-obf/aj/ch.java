/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.be;
import ap.u;
import be.ds;
import be.ee;
import be.dy;
import bh.v;
import bj.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ch
extends cv {
    public ch(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        v l1skills;
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int size = this.d();
        int cost_price = 0;
        ArrayList<Integer> allowed = dy.a(pc);
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        int i2 = 0;
        while (i2 < size) {
            int skillid = this.b() + 1;
            if (!allowed.contains(skillid - 1)) {
                ++i2;
                continue;
            }
            l1skills = be.a().a(skillid);
            if (l1skills == null) {
                ++i2;
                continue;
            }
            int level = l1skills.c();
            int skillLv = l1skills.c();
            if (pc.U() >= skillLv) {
                cost_price += level * level * 100;
                list.add(skillid);
            }
            ++i2;
        }
        if (list.isEmpty()) {
            return;
        }
        if (!pc.j().b(40308, cost_price)) {
            pc.a(new ds(189));
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
        return "C_SkillBuyOK";
    }
}

