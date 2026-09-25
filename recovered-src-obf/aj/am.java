/*
 * Decompiled with CFR 0.152.
 */
package aj;

import ai.d;
import aj.cv;
import ao.q;
import ap.u;
import aq.i;
import be.v;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class am
extends cv {
    private static final Logger a = Logger.getLogger(am.class.getName());
    private static final String b = "[C] C_EmblemUpload";

    public am(byte[] abyte0, bj.d clientthread) throws Exception {
        super(abyte0);
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        if (player.aH() != 4 && player.aH() != 10) {
            return;
        }
        if (player.aF() != 0) {
            int newEmblemdId = d.a().d();
            String emblem_file = String.valueOf(newEmblemdId);
            FileOutputStream fos = null;
            try {
                try {
                    fos = new FileOutputStream("./emblem/" + emblem_file);
                    int cnt = 0;
                    while (cnt < 384) {
                        fos.write(this.c());
                        cnt = (short)(cnt + 1);
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    throw e2;
                }
            }
            finally {
                if (fos != null) {
                    fos.close();
                }
                fos = null;
            }
            i clan = q.a().a(player.aF());
            clan.d(newEmblemdId);
            q.a().b(clan);
            for (u pc : clan.b()) {
                pc.a(new v(60, pc.fr(), newEmblemdId));
                pc.b(new v(60, pc.fr(), newEmblemdId));
            }
        }
    }

    @Override
    public String a() {
        return b;
    }
}

