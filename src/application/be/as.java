/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.util.Map;

public class as
extends eu {
    public as(int ItemObjid, int index, boolean equipOnOff) {
        this.c(42);
        this.c(66);
        this.a(ItemObjid);
        this.c(index);
        this.c(equipOnOff ? 1 : 0);
        this.b(0);
    }

    public as(Map<Integer, Integer> items) {
        this.c(42);
        this.c(65);
        this.c(items.size());
        for (int id : items.keySet()) {
            this.a(id);
            this.a(items.get(id));
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_EquipmentSlot";
    }
}

