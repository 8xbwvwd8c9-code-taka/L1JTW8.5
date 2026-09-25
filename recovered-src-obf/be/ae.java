/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.be;
import be.eu;
import bh.v;

public class ae
extends eu {
    public ae(int ... skillids) {
        boolean hasLevel9to10;
        byte[] data = new byte[29];
        int[] nArray = skillids;
        int n2 = skillids.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillid = nArray[n3];
            v l1skills = be.a().a(skillid);
            int n4 = l1skills.c();
            data[n4] = (byte)(data[n4] | l1skills.r());
            ++n3;
        }
        boolean hasLevel5to8 = data[5] + data[6] + data[7] + data[8] > 0;
        boolean bl2 = hasLevel9to10 = data[9] + data[10] > 0;
        data[0] = hasLevel5to8 && !hasLevel9to10 ? 50 : (hasLevel9to10 ? 100 : 32);
        this.c(127);
        this.a(data);
        this.a(0);
        this.a(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_DelSkill";
    }
}

