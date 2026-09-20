/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.o;
import ao.p;
import ao.q;
import ap.u;
import aq.aq;
import aq.i;
import be.ac;
import be.cm;
import be.cy;
import be.ds;
import be.v;
import be.y;
import bj.d;
import java.io.File;

public class bc
extends cv {
    public bc(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        String clan_name = this.g();
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        String player_name = pc.et();
        int clan_id = pc.aF();
        if (clan_id == 0) {
            return;
        }
        i clan = q.a().c(clan_name);
        if (clan == null) {
            pc.ah(0);
            pc.c("");
            pc.ai(0);
            pc.f("");
            pc.a(new y(pc.fr(), ""));
            pc.b(new y(pc.fr(), ""));
            pc.a(new v(60, pc.fr(), 0));
            pc.b(new v(60, pc.fr(), 0));
            pc.I();
            pc.a(new ds(178, player_name, clan_name));
            p.a().a(pc.fr());
            return;
        }
        if (pc.x() && pc.fr() == clan.k()) {
            int castleId = clan.m();
            int houseId = clan.n();
            if (castleId != 0 || houseId != 0) {
                pc.a(new ds(665));
                return;
            }
            if (aq.a().b(clan_name)) {
                pc.a(new ds(302));
                return;
            }
            int i2 = 0;
            while (i2 < clan.p().size()) {
                u online_pc = aq.a().a(clan.p().get(i2));
                if (online_pc != null) {
                    online_pc.a(new ds(269, player_name, clan_name));
                    online_pc.a(new cm(27, 11, ""));
                    online_pc.a(new v(60, online_pc.fr(), 0));
                    online_pc.a(new ac(online_pc, false));
                    online_pc.a(new cy());
                    if (online_pc.bB(4084)) {
                        online_pc.bz(4084);
                        online_pc.a(new cm(180, 450, 3240, 0));
                    }
                    online_pc.ah(0);
                    online_pc.c("");
                    online_pc.ai(0);
                    online_pc.f("");
                    online_pc.a(new y(online_pc.fr(), ""));
                    online_pc.b(new y(online_pc.fr(), ""));
                    online_pc.b(new v(60, online_pc.fr(), 0));
                    online_pc.I();
                } else {
                    u offline_pc = o.a().a(clan.p().get(i2));
                    offline_pc.ah(0);
                    offline_pc.c("");
                    offline_pc.ai(0);
                    offline_pc.f("");
                    offline_pc.I();
                }
                ++i2;
            }
            String emblem_file = String.valueOf(clan.i());
            File file = new File("./emblem/" + emblem_file);
            file.delete();
            q.a().b(clan_name);
            p.a().b(clan.e());
            return;
        }
        if (aq.a().b(clan_name)) {
            pc.a(new ds(331));
            return;
        }
        for (u member : clan.b()) {
            member.a(new ds(178, player_name, clan_name));
            if (clan.b().size() > 3 || !member.bB(4084)) continue;
            member.bz(4084);
            member.a(new cm(180, 450, 3240, 0));
        }
        if (clan.o() == pc.fr()) {
            clan.i(0);
        }
        pc.ah(0);
        pc.c("");
        pc.ai(0);
        pc.f("");
        pc.a(new y(pc.fr(), ""));
        pc.b(new y(pc.fr(), ""));
        pc.I();
        pc.a(new cy());
        pc.a(new cm(27, 11, ""));
        pc.a(new v(60, pc.fr(), 0));
        pc.b(new v(60, pc.fr(), 0));
        pc.a(new ac(pc, false));
        clan.b(player_name);
        p.a().a(pc.fr());
    }

    @Override
    public String a() {
        return "C_LeaveClan";
    }
}

