/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.g;
import ao.q;
import ap.r;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aj;
import aq.am;
import aq.ap;
import aq.aq;
import aq.e;
import aq.f;
import aq.i;
import as.b;
import be.ak;
import be.cc;
import be.o;
import bh.d;
import bh.l;
import java.util.ArrayList;

public class aa
extends t {
    private f y;
    private int z;
    private int A;
    private final ArrayList<r> B = new ArrayList();

    public aa(l template) {
        super(template);
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aq.aa)this);
        perceivedFrom.a(new cc(this));
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void b(f attacker, int damage) {
        if (this.B.isEmpty()) {
            for (aq.aa obj : aq.a().b((aq.aa)this, 20)) {
                if (!(obj instanceof r)) continue;
                this.B.add((r)obj);
            }
        }
        for (r keeper : this.B) {
            keeper.c(attacker, damage);
        }
        if (this.z == 0) {
            this.z = this.h() ? 7 : aq.e.a(this.fs(), this.ft(), this.fp());
        }
        if (this.z > 0 && as.b.a().a(this.z)) {
            if (this.z == 7 && !this.h()) {
                int subTowerDeadCount = 0;
                for (aq.aa l1object : aq.a().b()) {
                    Object tower;
                    if (l1object instanceof aa && ((aa)(tower = (aa)l1object)).h() && ((f)tower).eX() && ++subTowerDeadCount == 4) break;
                }
                if (subTowerDeadCount < 3) {
                    return;
                }
            }
            f cha = null;
            if (attacker instanceof u) {
                cha = attacker;
            } else if (attacker instanceof v) {
                cha = ((v)attacker).M();
            } else if (attacker instanceof z) {
                cha = ((z)attacker).M();
            } else if (attacker instanceof ap.a) {
                cha = attacker;
            }
            if (cha == null) {
                return;
            }
            boolean existDefenseClan = false;
            for (i clan : ao.q.a().b().values()) {
                int clanCastleId = clan.m();
                if (clanCastleId != this.z) continue;
                existDefenseClan = true;
                break;
            }
            if (cha instanceof u) {
                boolean isInWar;
                u pc = (u)cha;
                ap clanWar = aq.a().c(pc.aG());
                boolean bl2 = isInWar = clanWar != null && clanWar.d() == this.z;
                if (existDefenseClan && !isInWar) {
                    return;
                }
            }
            if (this.ea() > 0 && !this.eX()) {
                int newHp = this.ea() - damage;
                if (newHp <= 0 && !this.eX()) {
                    this.bx(0);
                    this.X(true);
                    this.cq(35);
                    this.y = attacker;
                    this.A = 0;
                    a death = new a();
                    bi.e.a().a(death);
                }
                if (newHp > 0) {
                    this.a(newHp);
                    if (this.ew() * 1 / 4 > this.ea()) {
                        if (this.A != 3) {
                            this.b(new ak(this.fr(), 34));
                            this.cq(34);
                            this.A = 3;
                        }
                    } else if (this.ew() * 2 / 4 > this.ea()) {
                        if (this.A != 2) {
                            this.b(new ak(this.fr(), 33));
                            this.cq(33);
                            this.A = 2;
                        }
                    } else if (this.ew() * 3 / 4 > this.ea() && this.A != 1) {
                        this.b(new ak(this.fr(), 32));
                        this.cq(32);
                        this.A = 1;
                    }
                }
            } else if (!this.eX()) {
                this.X(true);
                this.cq(35);
                this.y = attacker;
                a death = new a();
                bi.e.a().a(death);
            }
        }
    }

    @Override
    public void a(int i2) {
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
    }

    public boolean h() {
        return this.U_().b() == 81190 || this.U_().b() == 81191 || this.U_().b() == 81192 || this.U_().b() == 81193;
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            aa.this.bx(0);
            aa.this.X(true);
            aa.this.cq(35);
            aa.this.fq().a(aa.this.fu(), true);
            aa.this.b(new ak(aa.this.fr(), 35));
            if (aa.this.y instanceof ap.a) {
                int castle_id = aq.e.a(aa.this.fs(), aa.this.ft(), aa.this.fp());
                for (i defClan : ao.q.a().b().values()) {
                    if (castle_id != defClan.m()) continue;
                    defClan.g(0);
                    ao.q.a().b(defClan);
                    break;
                }
                d l1castale = ao.g.a().a(castle_id);
                l1castale.c(0);
                aq.a().a(new o(castle_id, 0));
                for (aq.aa obj : aq.a().b(aa.this.fp()).values()) {
                    ap.a attacker;
                    if (obj instanceof u) {
                        u pc = (u)obj;
                        if (!aq.e.a(castle_id, pc)) continue;
                        int[] loc = aq.e.e(castle_id);
                        int locx = loc[0];
                        int locy = loc[1];
                        int mapid = loc[2];
                        am.a(pc, locx, locy, mapid, 5, true);
                        continue;
                    }
                    if (!(obj instanceof ap.a) || !aq.e.a(castle_id, attacker = (ap.a)obj)) continue;
                    attacker.aa_();
                }
                String npcClanName = ((ap.a)aa.this.y).ap();
                ap npcClanWar = aq.a().c(npcClanName);
                if (npcClanWar != null) {
                    npcClanWar.a(npcClanName);
                }
                as.b.a().a(l1castale);
                return;
            }
            if (!aa.this.h()) {
                aj.a().b(aa.this.z);
            }
        }
    }
}

