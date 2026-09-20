/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.am;
import be.ei;
import java.util.logging.Level;
import java.util.logging.Logger;

public class v
implements l {
    private static final Logger a = Logger.getLogger(v.class.getName());

    private v() {
    }

    public static l a() {
        return new v();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int i2 = 0;
            try {
                i2 = Integer.parseInt(arg);
            }
            catch (NumberFormatException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            if (i2 == 1) {
                am.a(pc, 32737, 32796, 99, 5, true);
            } else if (i2 == 2) {
                am.a(pc, 32734, 32799, 17100, 5, true);
            } else if (i2 == 3) {
                am.a(pc, 32644, 32955, 0, 5, true);
            } else if (i2 == 4) {
                am.a(pc, 33429, 32814, 4, 5, true);
            } else if (i2 == 5) {
                am.a(pc, 32894, 32535, 300, 5, true);
            }
        }
        catch (Exception exception) {
            pc.a(new ei("\u8acb\u8f38\u5165 .gmroom1\uff5e.gmroom5 or .gmroom name \u3002"));
        }
    }
}

