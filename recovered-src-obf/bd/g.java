/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ap.u;
import aq.aa;
import aq.f;
import bd.i;
import bd.j;
import bf.a;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g
extends i {
    private static final Logger a = Logger.getLogger(g.class.getName());
    private final int b;
    private final int c;

    public g(j storage) {
        super(storage);
        this.b = storage.b("skillId");
        this.c = storage.b("skillTimeSeconds");
    }

    @Override
    public void a(u trodFrom, aa trapObj) {
        this.a(trapObj);
        try {
            a executor = bi.g.a(this.b);
            executor.a((f)trodFrom, this.c);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }
}

