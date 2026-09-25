/*
 * Decompiled with CFR 0.152.
 */
package ax;

import ax.a;
import ax.b;
import java.io.IOException;
import java.util.Map;

public abstract class e {
    public abstract Map<Integer, b> a() throws IOException;

    public abstract b a(int var1) throws IOException;

    public static e b() {
        return new a();
    }
}

