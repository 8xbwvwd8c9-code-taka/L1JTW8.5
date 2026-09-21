/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.bs;
import be.ds;
import be.dx;
import be.ea;
import be.ee;
import be.ei;

public class ay
implements l {
    public static l a() {
        return new ay();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            this.b(pc);
            this.c(pc);
            this.d(pc);
        }
        catch (Exception e2) {
            pc.a(new ei(".speed \u6307\u4ee4\u932f\u8aa4"));
        }
    }

    public void a(u pc) {
        this.a(pc, null, null);
    }

    private void b(u pc) {
        int[] status;
        int objId = pc.fr();
        int type = 1;
        int timeSec = 600;
        int[] nArray = status = new int[]{29, 76, 152};
        int n2 = status.length;
        int n3 = 0;
        while (n3 < n2) {
            int i2 = nArray[n3];
            if (pc.bB(i2)) {
                pc.bA(i2);
            }
            ++n3;
        }
        pc.j(1001, timeSec * 1000);
        pc.a(new ee(objId, 191));
        pc.b(new ee(objId, 191));
        pc.a(new ea(objId, type, timeSec));
        pc.b(new ea(objId, type, timeSec));
        pc.a(new ds(184));
        pc.cu(type);
    }

    private void c(u pc) {
        int objId = pc.fr();
        int type = 1;
        int timeSec = 600;
        pc.j(1000, timeSec * 1000);
        pc.a(new ee(objId, 751));
        pc.b(new ee(objId, 751));
        pc.a(new dx(objId, type, timeSec));
        pc.b(new dx(objId, type, timeSec));
        pc.cv(type);
    }

    private void d(u pc) {
        pc.j(1027, 600000);
        pc.a(new bs(pc.fr(), 8));
        pc.b(new bs(pc.fr(), 8));
        pc.a(new ee(pc.fr(), 7976));
        pc.b(new ee(pc.fr(), 7976));
        pc.a(new ds(1065));
    }
}

