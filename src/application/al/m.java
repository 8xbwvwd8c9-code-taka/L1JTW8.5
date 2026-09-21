/*
 * Decompiled with CFR 0.152.
 */
package al;

import ak.a;
import al.l;
import ap.u;
import be.ei;
import bh.e;
import java.util.List;

public class m
implements l {
    private m() {
    }

    public static l a() {
        return new m();
    }

    private String a(List<e> list, String with) {
        StringBuilder result = new StringBuilder();
        for (e cmd : list) {
            if (result.length() > 0) {
                result.append(with);
            }
            result.append(cmd.a());
        }
        return result.toString();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        List<e> list = a.a(pc.az());
        pc.a(new ei(this.a(list, ", ")));
    }
}

