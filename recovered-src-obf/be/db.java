/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import aq.aq;
import be.eu;
import bh.q;
import bh.r;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class db
extends eu {
    public db(u pc, int objectId, int type) {
        u shopPc = (u)aq.a().a(objectId);
        if (shopPc == null) {
            return;
        }
        this.c(39);
        this.c(type);
        this.a(objectId);
        if (type == 0) {
            CopyOnWriteArrayList<r> list = shopPc.aU();
            int size = list.size();
            pc.an(size);
            this.b(size);
            int i2 = 0;
            while (i2 < size) {
                r pssl = (r)list.get(i2);
                int itemObjectId = pssl.a();
                int count = pssl.b() - pssl.d();
                int price = pssl.c();
                ap.q item = shopPc.j().e(itemObjectId);
                if (item != null) {
                    this.c(0);
                    this.a(count);
                    this.a(price);
                    this.b(item.a().m());
                    this.c(item.G());
                    this.c(item.C() ? 1 : 0);
                    this.c(item.F());
                    this.a(item.c(count));
                    byte[] status = item.t();
                    this.c(status.length);
                    byte[] byArray = status;
                    int n2 = status.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        byte b2 = byArray[n3];
                        this.c(b2);
                        ++n3;
                    }
                }
                ++i2;
            }
        } else if (type == 1) {
            ArrayList<q> list = shopPc.aV();
            int size = list.size();
            this.b(size);
            int i3 = 0;
            while (i3 < size) {
                q psbl = (q)list.get(i3);
                int itemObjectId = psbl.a();
                int count = psbl.b();
                int price = psbl.c();
                ap.q item = shopPc.j().e(itemObjectId);
                if (item == null) {
                    ++i3;
                    continue;
                }
                for (ap.q pcItem : pc.j().d()) {
                    if (item.N() != pcItem.N() || item.G() != pcItem.G()) continue;
                    this.c(i3);
                    this.a(count);
                    this.a(price);
                    this.a(pcItem.fr());
                    this.c(0);
                }
                ++i3;
            }
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }
}

