/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ap.u;
import aq.aa;
import aq.aq;
import bd.e;
import bd.j;

public abstract class i {
    private final int a;
    private final int b;
    private final boolean c;

    public i(j storage) {
        this.a = storage.b("id");
        this.b = storage.b("gfxId");
        this.c = storage.c("isDetectionable");
    }

    public i(int id, int gfxId, boolean detectionable) {
        this.a = id;
        this.b = gfxId;
        this.c = detectionable;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    protected void a(aa trapObj) {
        if (this.b() == 0) {
            return;
        }
        for (u pc : aq.a().f(trapObj)) {
            pc.a(new be.aq(trapObj.fu(), this.b()));
        }
    }

    public abstract void a(u var1, aa var2);

    public void b(aa trapObj) {
        if (this.c) {
            this.a(trapObj);
        }
    }

    public static i c() {
        return new e();
    }
}

