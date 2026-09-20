/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.au;
import ao.be;
import ap.t;
import ap.u;
import ap.z;
import aq.f;
import be.ds;
import bf.a;
import bh.l;
import bh.v;
import bi.g;

public class az
extends a {
    private final int a = 51;
    private final v b;
    private final String[] c = new String[]{"0100", "0200", "0700", "0d00", "1300", "1900", "1f00", "2500", "2b00", "3100", "3700"};
    private final int[] d = new int[]{190891, 190892, 190893, 190894, 190895, 190896, 190897, 190898, 190899, 190900, 190901};
    private final int[] e = new int[]{28, 28, 40, 52, 64, 76, 80, 82, 84, 86, 88};
    private final int[] f = new int[]{25, 25, 25, 25, 25, 25, 35, 35, 35, 35, 35};

    public az() {
        this.b = be.a().a(51);
    }

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            int level = _targetId & 0xFF;
            int index = _targetId >> 8 & 0xFF;
            String s2 = String.valueOf(g.a(level, 2)) + g.a(index, 2);
            if (!pc.j().h(20284)) {
                s2 = "0100";
            }
            int summonid = 0;
            int levelrange = 0;
            int summoncost = 0;
            int i2 = 0;
            while (i2 < this.c.length) {
                if (s2.equalsIgnoreCase(this.c[i2])) {
                    summonid = this.d[i2];
                    levelrange = this.e[i2];
                    summoncost = this.f[i2];
                    break;
                }
                ++i2;
            }
            if (summonid == 0) {
                pc.a(new ds(79));
                return;
            }
            if (pc.ev() < levelrange) {
                pc.a(new ds(743));
                return;
            }
            int petcost = 0;
            for (t petNpc : pc.ek().values()) {
                petcost += petNpc.Q();
            }
            int charisma = pc.eC() + 6 - petcost;
            int summoncount = Math.min(charisma / summoncost, 5);
            if (summoncount == 0) {
                pc.a(new ds(3039));
                return;
            }
            l npcTemp = au.a().a(summonid);
            int cnt = 0;
            while (cnt < summoncount) {
                z summon = new z(npcTemp, pc);
                summon.o(summoncost);
                ++cnt;
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

