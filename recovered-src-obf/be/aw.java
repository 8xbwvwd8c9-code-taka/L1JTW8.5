/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import ap.u;
import be.eu;
import java.util.ArrayList;
import java.util.List;

public class aw
extends eu {
    public aw(u pc) {
        this.c(215);
        this.a(200);
        ArrayList<q> weaponList = new ArrayList<q>();
        List<q> itemList = pc.j().d();
        for (q item : itemList) {
            if (!item.g() || item.H() <= 0) continue;
            weaponList.add(item);
        }
        this.b(weaponList.size());
        for (q weapon : weaponList) {
            this.a(weapon.fr());
            this.c(weapon.H());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_FixWeaponList";
    }
}

