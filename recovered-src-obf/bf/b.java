/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.ar;
import ao.bg;
import ap.h;
import ap.t;
import ap.u;
import aq.aa;
import aq.ae;
import aq.ai;
import aq.am;
import aq.aq;
import aq.f;
import aq.w;
import be.ak;
import be.cn;
import be.de;
import be.ee;
import bf.a;
import bi.g;
import bi.i;
import java.util.ArrayList;

public class b
extends a {
    @Override
    public void a(f _target, int timeSecs) {
    }

    /*
     * WARNING - void declaration
     */
    public void a(t mob, f target, ar.b data) {
        f effect_cha = data.g ? target : mob;
        ArrayList<Object> list = new ArrayList<f>();
        if (data.e == 0) {
            list.add(effect_cha);
        } else {
            list = this.a((f)mob, effect_cha, data.e);
        }
        if (data.n > 0) {
            bg.a(data.n, mob, 5, target);
        } else if (data.s) {
            this.b(target);
        } else {
            for (f f2 : list) {
                if (data.q) {
                    this.c(f2);
                    continue;
                }
                if (data.r && f2.e((aa)mob) > 5.0) {
                    am.a(f2, mob, 1);
                    continue;
                }
                if (data.o > 0) {
                    this.b(mob, f2, data.o);
                    continue;
                }
                if (data.p > 0) {
                    this.d(f2, data.p);
                    continue;
                }
                int dmg = data.b + i.a(data.c);
                dmg = (int)w.a(mob, target, dmg, data.m);
                f2.cD(dmg);
                if (f2 instanceof u) {
                    u targetPc = (u)f2;
                    targetPc.a(mob, (double)dmg, true);
                    continue;
                }
                if (!(f2 instanceof t)) continue;
                t targetNpc = (t)f2;
                targetNpc.b(mob, dmg);
            }
        }
        if (data.e != 0) {
            if (data.g) {
                mob.b(new de(mob, list, data.f, data.i, 8));
            } else {
                void var6_9;
                mob.b(new de(mob, list, data.f, data.i, 0));
                boolean bl2 = true;
                while (var6_9 < list.size()) {
                    f other = (f)list.get((int)var6_9);
                    if (other.fo() != 0) {
                        other.b(new ak(other.fr(), 2));
                        if (other instanceof u) {
                            u tpc = (u)other;
                            tpc.a(new ak(tpc.fr(), 2));
                        }
                    }
                    ++var6_9;
                }
            }
        } else if (data.g) {
            if (data.b <= 0) {
                mob.b(new ee(target.fr(), data.f));
                mob.b(new ak(mob.fr(), data.i));
                return;
            }
            int n2 = data.h ? 0 : 6;
            mob.ct(mob.a((aa)target));
            mob.b(new be.g(mob, target, data.i, data.f, target.fo(), n2, 0));
            if (target.fo() > 0) {
                target.a(new ak(target.fr(), 2), mob);
            }
        } else {
            mob.b(new ee(mob.fr(), data.f));
            mob.b(new ak(mob.fr(), data.i));
        }
    }

    private void d(f cha, int skillid) {
        if (i.a(127) <= cha.W_()) {
            return;
        }
        g.a(skillid).a(cha, -1);
    }

    private void b(f mob, f cha, int polyid) {
        if (i.a(127) <= cha.W_()) {
            return;
        }
        mob.b(new ee(cha.fr(), 230));
        ae.a(cha, polyid, 300, 4);
    }

    private void b(f cha) {
        int[][] diff;
        int locx = cha.fs();
        int locy = cha.ft();
        int[][] nArrayArray = new int[9][];
        int[] nArray = new int[2];
        nArray[1] = -2;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 2;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{-1, -1};
        nArrayArray[4] = new int[2];
        nArrayArray[5] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[0] = -2;
        nArrayArray[6] = nArray3;
        nArrayArray[7] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[1] = 2;
        nArrayArray[8] = nArray4;
        int[][] nArrayArray2 = diff = nArrayArray;
        int n2 = diff.length;
        int n3 = 0;
        while (n3 < n2) {
            int[] i2 = nArrayArray2[n3];
            h effect = ai.a().a(1263, 9000, locx + i2[0], locy + i2[1], cha.fp());
            for (h other : aq.a().d().values()) {
                if (other.equals(effect) || other.fq() != effect.fq() || !effect.fu().f(other.fu()) || other.fe() != 1263) continue;
                other.aa_();
            }
            ++n3;
        }
    }

    private void c(f cha) {
        if (i.a(127) <= cha.W_()) {
            return;
        }
        int fettersTime = 8000;
        if (w.a(cha)) {
            return;
        }
        ai.a().a(4184, 8000, cha.fs(), cha.ft(), cha.fp());
        if (cha instanceof u) {
            u targetPc = (u)cha;
            targetPc.j(1028, 8000);
            targetPc.a(new cn(6, true));
        } else if (cha instanceof t) {
            t npc = (t)cha;
            npc.j(1028, 8000);
            npc.n(true);
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
    }

    @Override
    public void a(f cha) {
    }
}

