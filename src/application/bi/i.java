/*
 * Decompiled with CFR 0.152.
 */
package bi;

import java.util.concurrent.ThreadLocalRandom;

public class i {
    public static int a(int n2) {
        if (n2 <= 0) {
            return 0;
        }
        int result = ThreadLocalRandom.current().nextInt(n2);
        return result;
    }
}

