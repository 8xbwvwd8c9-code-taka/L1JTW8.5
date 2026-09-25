/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.bc;
import ap.q;
import as.a;
import be.be;
import be.eu;
import bh.t;
import bh.u;
import java.util.ArrayList;

public class dv
extends eu {
    public dv(ap.t npc, ap.u pc) {
        t shop = bc.a().a(npc.z());
        if (shop == null) {
            pc.a(new be(npc.fr(), "nosell", npc.T()));
            return;
        }
        ArrayList<int[]> orderList = new ArrayList<int[]>();
        for (u shopItem : shop.c()) {
            q[] qArray = pc.j().d(shopItem.a());
            int n2 = qArray.length;
            int n3 = 0;
            while (n3 < n2) {
                q item = qArray[n3];
                if (item != null && !item.D() && item.G() == 0 && item.F() < 128) {
                    int price = (int)((double)shopItem.c() * l1j.server.a.M / (double)shopItem.d());
                    if (item.N() == 40309) {
                        price = a.a().a(item.fr());
                    }
                    int[] order = new int[]{item.fr(), price};
                    orderList.add(order);
                }
                ++n3;
            }
        }
        if (orderList.isEmpty()) {
            pc.a(new be(npc.fr(), "nosell", npc.T()));
            return;
        }
        this.c(55);
        this.a(npc.fr());
        this.b(orderList.size());
        for (int[] order : orderList) {
            this.a(order[0]);
            this.a(order[1]);
        }
        if (npc.z() == 190005) {
            this.b(14921);
        } else {
            this.b(7);
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ShopBuyList";
    }
}

