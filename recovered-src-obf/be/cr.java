/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import ap.v;
import be.eu;
import java.util.List;

public class cr
extends eu {
    public cr(v pet) {
        List<q> itemList = pet.y().d();
        this.c(162);
        this.a(pet.fr());
        this.b(itemList.size());
        this.c(11);
        for (q itemObject : itemList) {
            q petItem = itemObject;
            if (petItem == null) continue;
            this.a(petItem.fr());
            this.c(2);
            this.b(petItem.e());
            this.c(petItem.F());
            this.a(petItem.E());
            if (petItem.f() && petItem.a().aP() == 11 && petItem.D()) {
                this.c(petItem.C() ? 3 : 2);
            } else {
                this.c(petItem.C() ? 1 : 0);
            }
            this.a(petItem.r());
        }
        this.c(pet.ey());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PetInventory";
    }
}

