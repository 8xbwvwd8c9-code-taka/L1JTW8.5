/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ab;
import ap.t;
import ap.u;
import aq.an;
import aq.f;
import be.be;
import bh.i;
import bh.l;
import java.util.ArrayList;

public class b
extends t {
    public b(l template) {
        super(template);
    }

    @Override
    public void c(u pc) {
        ArrayList<i> sellList = new ArrayList<i>();
        for (i house : ab.a().c().values()) {
            if (!house.g()) continue;
            int houseId = house.b();
            if (an.a((f)this) == 7) {
                if (houseId < 262145 || houseId > 262189) continue;
                sellList.add(house);
                continue;
            }
            if (an.a((f)this) == 8) {
                if (houseId < 327681 || houseId > 327691) continue;
                sellList.add(house);
                continue;
            }
            if (an.a((f)this) == 12) {
                if (houseId < 458753 || houseId > 458819) continue;
                sellList.add(house);
                continue;
            }
            if (an.a((f)this) != 3 || houseId < 65537 || houseId > 65542) continue;
            sellList.add(house);
        }
        if (sellList.isEmpty()) {
            pc.a(new be(this.fr(), "agnolist"));
        } else {
            pc.a(new be.i(this.fr(), sellList));
        }
    }
}

