/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import ap.u;
import be.ds;
import be.eu;
import java.io.IOException;
import java.util.Iterator;

public class dk
extends eu {
    public dk(int objid, u pc) {
        if (pc.j().c() < 180) {
            int size = pc.aw().c();
            if (size > 0) {
                this.c(162);
                this.a(objid);
                this.b(size);
                this.c(18);
                Iterator<q> iterator = pc.aw().d().iterator();
                while (iterator.hasNext()) {
                    q itemObject;
                    q item = itemObject = iterator.next();
                    this.a(item.fr());
                    this.c(item.a().U());
                    this.b(item.e());
                    this.c(item.F());
                    this.a(item.E());
                    this.c(item.C() ? 1 : 0);
                    this.a(item.r());
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
                this.a(0);
                this.a(pc.cJ());
                this.b(0);
                this.b(0);
            } else {
                pc.a(new ds(1625));
            }
        } else {
            pc.a(new ds(263));
        }
    }

    @Override
    public byte[] a() throws IOException {
        return this.d();
    }
}

