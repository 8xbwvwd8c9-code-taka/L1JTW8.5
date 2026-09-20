/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.be;
import ap.u;
import be.eu;
import bh.v;
import java.util.concurrent.CopyOnWriteArrayList;

public class d
extends eu {
    private void a(int attr, int ... skillids) {
        byte[] data = new byte[32];
        int[] nArray = skillids;
        int n2 = skillids.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillid = nArray[n3];
            v l1skills = be.a().a(skillid);
            int n4 = l1skills.c() - 1;
            data[n4] = (byte)(data[n4] | l1skills.r());
            ++n3;
        }
        this.c(103);
        this.c(32);
        this.a(data);
        int value = 0;
        if (attr == 1) {
            value = 4;
        } else if (attr == 2) {
            value = 1;
        } else if (attr == 4) {
            value = 2;
        } else if (attr == 8) {
            value = 3;
        }
        this.c(value);
    }

    public d(u pc, int ... skillids) {
        this.a(pc.bC(), skillids);
    }

    public d(u pc, CopyOnWriteArrayList<Integer> list) {
        int[] data = new int[list.size()];
        int i2 = 0;
        while (i2 < list.size()) {
            data[i2] = list.get(i2);
            ++i2;
        }
        this.a(pc.bC(), data);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_AddSkill";
    }
}

