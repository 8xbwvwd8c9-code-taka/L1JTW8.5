/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.f;
import ao.o;
import ap.u;
import be.ds;
import bj.d;

public class b
extends cv {
    public b(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        aq.d buddyList = f.a().a(pc.fr());
        String charName = this.g();
        if (charName.equalsIgnoreCase(pc.et())) {
            return;
        }
        if (buddyList.b(charName)) {
            pc.a(new ds(1052, charName));
            return;
        }
        o.a[] aArray = o.a().c();
        int n2 = aArray.length;
        int n3 = 0;
        while (n3 < n2) {
            o.a cn2 = aArray[n3];
            if (charName.equalsIgnoreCase(cn2.b)) {
                buddyList.a(cn2.a, cn2.b);
                f.a().a(pc.fr(), cn2.a, cn2.b);
                return;
            }
            ++n3;
        }
        pc.a(new ds(109, charName));
    }

    @Override
    public String a() {
        return "C_AddBuddy";
    }
}

