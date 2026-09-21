/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.au;
import ao.be;
import ap.h;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import ax.b;
import ax.d;
import be.cc;
import be.ee;
import bh.l;

public class ai {
    private static ai a;

    private ai() {
    }

    public static ai a() {
        if (a == null) {
            a = new ai();
        }
        return a;
    }

    public h a(int gfxid, int time, int locX, int locY, int mapId) {
        return this.a(gfxid, time, locX, locY, mapId, null, 0);
    }

    public h a(int gfxid, int time, int locX, int locY, int mapId, u user, int skiiId) {
        l template = au.a().a(189999);
        template.k(gfxid);
        h effect = new h(template);
        effect.cF(ai.d.a().c());
        effect.cG(locX);
        effect.cH(locY);
        effect.ct(0);
        effect.cE(mapId);
        effect.d(user);
        effect.h_(skiiId);
        effect.f();
        aq.a().a(effect);
        aq.a().c(effect);
        for (u pc : aq.a().f(effect)) {
            effect.c((aa)pc);
            pc.c((aa)effect);
            pc.a(new cc(effect));
            pc.a(new ee(effect.fr(), effect.fe()));
        }
        effect.a((long)time);
        return effect;
    }

    public void a(u cha, int targetX, int targetY) {
        int duration = be.a().a(58).v();
        f base = cha;
        b map = d.b().a(cha.fp());
        int i2 = 0;
        while (i2 < 8) {
            h effect;
            int a2 = base.h(targetX, targetY);
            int x2 = base.fs();
            int y2 = base.ft();
            if (a2 == 1) {
                ++x2;
                --y2;
            } else if (a2 == 2) {
                ++x2;
            } else if (a2 == 3) {
                ++x2;
                ++y2;
            } else if (a2 == 4) {
                ++y2;
            } else if (a2 == 5) {
                --x2;
                ++y2;
            } else if (a2 == 6) {
                --x2;
            } else if (a2 == 7) {
                --x2;
                --y2;
            } else if (a2 == 0) {
                --y2;
            }
            if (!base.c(x2, y2, 1)) {
                x2 = base.fs();
                y2 = base.ft();
            }
            if (!map.d(x2, y2, cha.fb()) || (effect = this.a(168, duration * 1000, x2, y2, cha.fp(), cha, 58)) == null) break;
            for (h other : aq.a().d().values()) {
                if (other.equals(effect) || other.fq() != map || !effect.fu().f(other.fu()) || other.fe() != 168) continue;
                other.aa_();
            }
            if (targetX == x2 && targetY == y2) break;
            base = effect;
            ++i2;
        }
    }
}

