/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.util.ArrayList;
import java.util.List;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.ServerBasePacket;

public class S_FixWeaponList
extends ServerBasePacket {
    public S_FixWeaponList(L1PcInstance pc) {
        this.c(215);
        this.a(200);
        ArrayList<L1ItemInstance> weaponList = new ArrayList<L1ItemInstance>();
        List<L1ItemInstance> itemList = pc.j().d();
        for (L1ItemInstance item : itemList) {
            if (!item.g() || item.H() <= 0) continue;
            weaponList.add(item);
        }
        this.b(weaponList.size());
        for (L1ItemInstance weapon : weaponList) {
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
