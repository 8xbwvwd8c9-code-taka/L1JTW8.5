/*
 * Decompiled with CFR 0.152.
 */
package ay;

import ap.u;
import ay.f;
import be.bn;
import be.bq;
import be.cm;
import be.dc;
import l1j.server.a;

public class d
extends f {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;

    public d(int oId) {
        super(oId);
    }

    @Override
    public void a(u pc) {
        if (this.a != pc.fa()) {
            int fightType;
            this.a = pc.fa();
            bq s_lawful = new bq(pc.fr(), this.a);
            pc.a(s_lawful);
            pc.b(s_lawful);
            if (l1j.server.a.aN && this.n != (fightType = this.a / 10000)) {
                pc.b(this.n, fightType);
                this.n = fightType;
            }
        }
        if (this.c != pc.P()) {
            this.c = pc.P();
            pc.a(new bn(pc));
        }
        if (this.b != pc.m()) {
            this.b = pc.m();
            pc.g();
        }
        if (this.d != pc.ez()) {
            this.d = pc.ez();
            int color = 2;
            if (this.i == 0) {
                this.i = pc.bf();
            } else if (this.i != pc.bf()) {
                this.i = pc.bf();
                color = 16;
            }
            int calcDmg = bi.d.a(pc.bf(), pc.ez());
            int calcHit = bi.d.b(pc.bf(), pc.ez());
            int calcCritical = bi.d.c(pc.bf(), pc.ez());
            pc.a(new dc(color, "str", calcDmg, calcHit, calcCritical, (int)pc.K()));
        }
        if (this.e != pc.eB()) {
            this.e = pc.eB();
            int color = 2;
            if (this.j == 0) {
                this.j = pc.bh();
            } else if (this.j != pc.bh()) {
                this.j = pc.bh();
                color = 16;
            }
            pc.W();
            pc.a(new cm(132, pc.u()));
            int calcAc = bi.d.a(pc.eB());
            int calcEr = bi.d.b(pc.eB());
            int calcBowDmg = bi.d.d(pc.bh(), pc.eB());
            int calcBowHit = bi.d.e(pc.bh(), pc.eB());
            int calcBowCritical = bi.d.f(pc.bh(), pc.eB());
            pc.a(new dc(color, "dex", calcBowDmg, calcBowHit, calcBowCritical, calcAc, calcEr));
        }
        if (this.g != pc.eD()) {
            this.g = pc.eD();
            int color = 2;
            if (this.l == 0) {
                this.l = pc.bj();
            } else if (this.l != pc.bj()) {
                this.l = pc.bj();
                color = 16;
            }
            int calcMagicDmg = bi.d.g(pc.bj(), pc.eD());
            int calcMagicHit = bi.d.h(pc.bj(), pc.eD());
            int calcMagicCritical = bi.d.i(pc.bj(), pc.eD());
            int calcMagicBouns = bi.d.c(pc.eD());
            int calcMagicDecrese = bi.d.d(pc.eD());
            pc.a(new dc(color, "int", calcMagicDmg, calcMagicHit, calcMagicCritical, calcMagicBouns, calcMagicDecrese));
        }
        if (this.f != pc.eA()) {
            this.f = pc.eA();
            int color = 2;
            if (this.k == 0) {
                this.k = pc.bg();
            } else if (this.k != pc.bg()) {
                this.k = pc.bg();
                color = 16;
            }
            int calcHpup = pc.aC().e() + bi.d.j(pc.aC().a()[2], pc.eA());
            int calcHpr = bi.d.k(pc.bg(), pc.eA());
            int calcPotionHpr = bi.d.l(pc.bg(), pc.eA());
            pc.a(new dc(color, "con", calcHpr, calcPotionHpr, (int)pc.K(), calcHpup, calcHpup + 1));
        }
        if (this.h != pc.eE()) {
            this.h = pc.eE();
            int color = 2;
            if (this.m == 0) {
                this.m = pc.bk();
            } else if (this.m != pc.bk()) {
                this.m = pc.bk();
                color = 16;
            }
            int mpup = pc.aC().j(pc.eE());
            int rnd = pc.aC().k(pc.eE());
            int calcMpr = bi.d.m(pc.bk(), pc.eE());
            int calcPotionMpr = bi.d.n(pc.bk(), pc.eE());
            int calcMr = bi.d.e(pc.eE());
            pc.a(new dc(color, "wis", calcMpr, calcPotionMpr, calcMr, mpup, mpup + rnd));
        }
    }
}

