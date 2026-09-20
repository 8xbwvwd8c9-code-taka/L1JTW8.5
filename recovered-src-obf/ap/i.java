/*
 * Decompiled with CFR 0.152.
 */
package ap;

import am.c;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import aq.f;
import be.ak;
import be.cc;
import be.g;
import bh.l;
import bi.e;
import java.util.logging.Level;
import java.util.logging.Logger;

public class i
extends t {
    private static final Logger y = Logger.getLogger(i.class.getName());
    private a z = null;
    private int A = 0;

    public i(l template) {
        super(template);
    }

    @Override
    public void c(u pc) {
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new cc(this));
        if (this.z == null && this.f() != -1) {
            this.z = new a(this);
            this.z.a();
        }
    }

    public int f() {
        return this.A;
    }

    public void b(int i2) {
        this.A = i2;
    }

    private class a
    implements Runnable {
        private final i b;

        private a(i npc) {
            this.b = npc;
        }

        @Override
        public void run() {
            int sleep = 1000;
            int actid = 1;
            int moveRange = 0;
            int moveDirection = 0;
            int i2 = 0;
            while (i2 < 5) {
                if (i.this.ah()) {
                    return;
                }
                switch (this.b.f()) {
                    case 1: {
                        this.b.b(new ak(this.b.fr(), actid));
                        break;
                    }
                    case 21: {
                        f dummy = new f();
                        dummy.cG(this.b.fs());
                        dummy.cH(this.b.ft() - 8);
                        this.b.b(new g(this.b, dummy, actid, 2349, 0, 0, 0));
                        break;
                    }
                    case 0: {
                        int dir;
                        if (moveRange == 0) {
                            moveRange = bi.i.a(5) + 1;
                            moveDirection = bi.i.a(18);
                            if (i.this.X() != 0 && i.this.Y() != 0 && moveDirection < 8 && bi.i.a(3) == 0) {
                                moveDirection = i.this.a(i.this.X(), i.this.Y());
                            }
                        } else {
                            --moveRange;
                        }
                        if ((dir = i.this.a(i.this.fs(), i.this.ft(), i.this.fp(), moveDirection)) != -1) {
                            i.this.g(dir);
                        }
                        actid = 0;
                        break;
                    }
                    case -10: {
                        int _dir;
                        if (moveRange == 0) {
                            moveRange = bi.i.a(15) + 1;
                            moveDirection = bi.i.a(18);
                            if (i.this.X() != 0 && i.this.Y() != 0 && moveDirection < 8 && bi.i.a(3) == 0) {
                                moveDirection = i.this.a(i.this.X(), i.this.Y());
                            }
                        } else {
                            --moveRange;
                        }
                        if ((_dir = i.this.a(i.this.fs(), i.this.ft(), i.this.fp(), moveDirection)) != -1) {
                            i.this.g(_dir);
                        }
                        actid = 0;
                        for (u pc : aq.a().c(this.b, 2)) {
                            if (bi.i.a(100) < 25) {
                                int[][] locs = new int[][]{{32718, 33128}, {32815, 33124}, {32810, 33206}, {32714, 33219}};
                                int[] loc = locs[bi.i.a(locs.length)];
                                am.a(pc, loc[0], loc[1], 4, 3, true);
                                continue;
                            }
                            am.a(pc, 50);
                        }
                        break;
                    }
                    case -2: {
                        this.b.b(new ak(this.b.fr(), 3));
                        break;
                    }
                    default: {
                        this.b.b(new ak(this.b.fr(), this.b.f()));
                        actid = this.b.f();
                    }
                }
                try {
                    int temp = am.c.a().a(this.b.fe(), actid);
                    Thread.sleep(temp > 0 ? temp : 1000);
                }
                catch (InterruptedException e2) {
                    y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                ++i2;
            }
            if (!aq.a().f(this.b).isEmpty()) {
                this.a();
            } else {
                i.this.z = null;
            }
        }

        private void a() {
            bi.e.a().a(this);
        }
    }
}

