/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.ei;

public class q
implements l {
    private q() {
    }

    public static l a() {
        return new q();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringBuilder msg = new StringBuilder();
            pc.a(new ei("-- describe: " + pc.et() + " --"));
            int hpr = pc.ar() + pc.j().l();
            int mpr = pc.as() + pc.j().m();
            msg.append("Dmg: +" + pc.eR() + " / ");
            msg.append("Hit: +" + pc.eT() + " / ");
            msg.append("MR: " + pc.W_() + " / ");
            msg.append("HPR: " + hpr + " / ");
            msg.append("MPR: " + mpr + " / ");
            msg.append("Karma: " + pc.P() + " / ");
            msg.append("Item: " + pc.j().c() + " / ");
            pc.a(new ei(msg.toString()));
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " \u6307\u4ee4\u932f\u8aa4"));
        }
    }
}

