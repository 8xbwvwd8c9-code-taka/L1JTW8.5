/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.be;
import ap.t;
import ap.u;
import ap.z;
import aq.f;
import as.b;
import be.ak;
import be.cg;
import be.cm;
import be.ee;
import be.ei;
import bh.v;
import bi.d;
import bi.i;
import l1j.server.a;

public class w {
    private static final int a = 1;
    private static final int b = 2;
    private static final int c = 3;
    private static final int d = 4;
    private int e = 0;
    private f f = null;
    private f g = null;
    private u h = null;
    private u i = null;
    private t j = null;
    private t k = null;
    private static int[] l = new int[]{44, 27, 29, 33, 39, 40, 47, 56, 71, 76, 66, 50, 152, 153, 157, 161, 167, 174, 173, 133, 145, 87, 15003, 15004};
    private static final int[] m = new int[]{78, 50, 157, 15003, 15004, 120};
    private boolean n = false;

    public w(f attacker, f target) {
        this.g = attacker;
        this.f = target;
        if (attacker instanceof u) {
            if (target instanceof u) {
                this.e = 1;
                this.h = (u)attacker;
                this.i = (u)target;
            } else if (target instanceof t) {
                this.e = 2;
                this.h = (u)attacker;
                this.k = (t)target;
            }
        } else if (target instanceof u) {
            this.e = 3;
            this.j = (t)attacker;
            this.i = (u)target;
        } else if (target instanceof t) {
            this.e = 4;
            this.j = (t)attacker;
            this.k = (t)target;
        }
    }

    public boolean a(int skillId) {
        boolean isSuccess;
        if (this.g == null || this.f == null) {
            return false;
        }
        if (this.f.bB(31)) {
            this.f.bz(31);
            return false;
        }
        if (skillId == 44) {
            if (this.e == 1) {
                if (this.h.fr() == this.i.fr()) {
                    return true;
                }
                if (this.h.aF() > 0 && this.h.aF() == this.i.aF()) {
                    return true;
                }
                if (this.h.q() && this.h.aL().d(this.i)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        if (this.e == 1) {
            if (this.h.fu().c() || this.i.fu().c()) {
                int[] nArray = l;
                int n2 = l.length;
                int n3 = 0;
                while (n3 < n2) {
                    int id = nArray[n3];
                    if (skillId == id) {
                        return false;
                    }
                    ++n3;
                }
            }
        } else if (this.e == 2 && this.h.d(this.h, this.k.U_().b())) {
            return false;
        }
        if (this.f.bB(157) && skillId != 27 && skillId != 44) {
            return false;
        }
        if (this.f.bB(120)) {
            return false;
        }
        int probability = this.d(skillId);
        if (probability > 90) {
            probability = 90;
        }
        boolean bl2 = isSuccess = bi.i.a(100) < probability;
        if (l1j.server.a.S) {
            if (this.g instanceof u && this.h.l()) {
                this.h.a(new ei("\u5c0d" + this.f.et() + " \u65bd\u653e\u9b54\u6cd5" + (isSuccess ? "\\aL\u6210\u529f" : "\\aG\u5931\u6557") + " (\u6a5f\u7387=" + probability + "%)"));
            } else if (this.f instanceof u && this.i.l()) {
                this.i.a(new ei(String.valueOf(this.g.et()) + "\u5c0d\u4f60\u65bd\u653e\u9b54\u6cd5" + (isSuccess ? "\\aL\u6210\u529f" : "\\aG\u5931\u6557") + " (\u6a5f\u7387=" + probability + "%)"));
            }
        }
        return isSuccess;
    }

    private int d(int skillId) {
        v l1skills = be.a().a(skillId);
        int attackLevel = this.g.ev();
        int defenseLevel = this.f.ev();
        int probability = 0;
        if (skillId == 230 || skillId == 228) {
            probability = l1skills.l() + (attackLevel - defenseLevel) * 5;
            if (this.h.bB(222)) {
                probability += 7;
            }
        } else if (skillId == 87) {
            probability = l1skills.l() + (attackLevel - defenseLevel) * 2;
            if (this.h.bB(222)) {
                probability += 7;
            }
        } else if (skillId == 91) {
            probability = l1skills.l() + attackLevel - defenseLevel;
        } else if (skillId == 183 || skillId == 188 || skillId == 192 || skillId == 193) {
            probability = l1skills.l() + (attackLevel - defenseLevel) * 3;
            probability += this.g.eD() * l1skills.m();
            probability = (int)((double)probability - (double)this.f.W_() / 2.5);
        } else {
            int mr;
            probability = l1skills.l();
            probability += (this.g.U() + bi.d.c(this.g.eD())) * l1skills.m();
            int magicHit = 0;
            if (this.g instanceof u) {
                magicHit = this.h.dD() + bi.d.h(this.h.bj(), this.h.eD());
            }
            if ((mr = this.f.W_()) > 150) {
                mr = (int)(150.0 + (double)(mr - 150) * 0.3);
            }
            probability += magicHit - mr;
            if (skillId == 36) {
                double probabilityRevision = 0.8 + (double)(this.k.ew() - this.k.ea()) / (double)this.k.ew();
                probability = (int)((double)probability * probabilityRevision);
            } else if (skillId == 208 && this.h.bB(222)) {
                probability += 7;
            }
        }
        if (skillId == 157) {
            if (this.e == 1 || this.e == 3) {
                probability -= this.i.eO();
            }
        } else if (skillId == 87) {
            if (this.e == 1 || this.e == 3) {
                probability -= 2 * this.i.eK();
            }
            if (this.e == 1 || this.e == 2) {
                probability += this.h.dK();
            }
        } else if (skillId == 33) {
            if (this.e == 1 || this.e == 3) {
                probability -= this.i.eL();
            }
        } else if (skillId == 66) {
            if (this.e == 1 || this.e == 3) {
                probability -= this.i.eM();
            }
        } else if (skillId == 50 || skillId == 15003 || skillId == 15004) {
            if (this.e == 1 || this.e == 3) {
                probability -= this.i.eN();
                if (w.a(this.i)) {
                    return 0;
                }
            }
        } else if (skillId == 20 || skillId == 40) {
            if (this.e == 1 || this.e == 3) {
                probability -= this.i.eP();
            }
        } else if (skillId == 230 && (this.e == 1 || this.e == 3)) {
            probability -= this.i.eQ();
        }
        return probability;
    }

    public static boolean a(f cha) {
        int[] nArray = m;
        int n2 = m.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillid = nArray[n3];
            if (cha.bB(skillid)) {
                return true;
            }
            ++n3;
        }
        if (cha.bB(31)) {
            cha.bz(31);
            return true;
        }
        return false;
    }

    public int b(int skillId) {
        if (this.g == null || this.f == null) {
            return 0;
        }
        if (w.a(this.f)) {
            return 0;
        }
        if (this.f.bB(608)) {
            int advence = 0;
            double hpRange = 0.4;
            if (this.f instanceof u) {
                u pc = (u)this.f;
                hpRange += (double)pc.dH() * 0.01;
            }
            if (this.f.bB(231) && this.f.ev() >= 80) {
                advence = Math.min(5 + this.f.ev() - 80, 10);
            }
            if ((double)this.f.ea() < (double)this.f.ew() * hpRange && bi.i.a(100) < 34 + advence) {
                if (this.e == 1 || this.e == 2) {
                    this.h.a(this.f, (double)(this.f.ev() * 2), true);
                    this.h.a(new ee(this.h.fr(), 12559));
                    this.h.b(new ee(this.h.fr(), 12559));
                    this.h.a(new ak(this.h.fr(), 2));
                    this.h.b(new ak(this.h.fr(), 2));
                } else if (this.e == 3 || this.e == 4) {
                    this.j.b(this.f, this.f.ev() * 2);
                    this.j.b(new ee(this.j.fr(), 12559));
                    this.j.b(new ak(this.j.fr(), 2));
                }
                return 0;
            }
        }
        return this.e(skillId);
    }

    public int a() {
        v l1skills;
        if (w.a(this.f)) {
            return 0;
        }
        double attrDeffence = w.a(this.f, 2);
        int dmg = (int)(attrDeffence * (double)(l1skills = be.a().a(58)).i());
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    private int e(int skillId) {
        if (this.e == 1 && this.i.a(this.h, this.i, false)) {
            return 0;
        }
        if (this.e == 1 ? this.i.ep() == 1 || this.h.ep() == 1 : (this.e == 2 ? this.h.d(this.h, this.k.U_().b()) : (this.e == 3 ? !(!(this.j instanceof ap.v) && !(this.j instanceof z) || this.i.ep() != 1 && this.j.ep() != 1 && !this.i.a(this.i, this.j, false)) : !(this.e != 4 || !(this.j instanceof ap.v) && !(this.j instanceof z) || !(this.k instanceof ap.v) && !(this.k instanceof z) || this.k.ep() != 1 && this.j.ep() != 1)))) {
            return 0;
        }
        double dmg = this.f(skillId);
        if (skillId == 207 && this.i.eb() >= 5) {
            this.i.i_(this.i.eb() - 5);
            dmg += (double)(this.g.eE() * 5);
        }
        dmg = w.a(this.g, this.f, dmg);
        if (this.e == 2 && (this.k instanceof ap.v || this.k instanceof z) && this.k.L() && !as.b.a().a(this.k)) {
            dmg /= 8.0;
        }
        if (dmg < 0.0) {
            dmg = 0.0;
        }
        return (int)dmg;
    }

    private static double a(f attacker, f target, double dmg) {
        if (target instanceof u) {
            u tpc = (u)target;
            dmg -= (double)tpc.bV();
            if (tpc.dC() > 0 && bi.i.a(100) < 5) {
                dmg -= (double)tpc.dC();
            }
            dmg -= (double)tpc.ah();
            if (tpc.bB(3008) || tpc.bB(3009) || tpc.bB(3010) || tpc.bB(3011) || tpc.bB(3012) || tpc.bB(3013) || tpc.bB(3014) || tpc.bB(3024) || tpc.bB(3025) || tpc.bB(3026) || tpc.bB(3027) || tpc.bB(3028) || tpc.bB(3029) || tpc.bB(3030) || tpc.bB(3040) || tpc.bB(3041) || tpc.bB(3042) || tpc.bB(3043) || tpc.bB(3044) || tpc.bB(3045) || tpc.bB(3046)) {
                dmg -= 5.0;
            }
            if (tpc.bB(3015) || tpc.bB(3031) || tpc.bB(3047)) {
                dmg -= 5.0;
            }
            if (tpc.bB(88)) {
                int targetPcLvl = tpc.ev();
                if (targetPcLvl < 50) {
                    targetPcLvl = 50;
                }
                dmg -= (double)((targetPcLvl - 50) / 5 + 1);
            }
            if (tpc.bB(181)) {
                dmg -= 2.0;
            }
            if (tpc.bB(211)) {
                dmg -= 2.0;
            }
            if (tpc.bB(159)) {
                dmg -= 2.0;
            }
            if (tpc.bB(4058)) {
                dmg -= 60.0;
            }
            if (tpc.bB(219)) {
                dmg *= 1.05;
            }
            if (tpc.bB(68)) {
                dmg /= 2.0;
            }
        } else if (target instanceof t) {
            t t2 = (t)target;
        }
        return dmg;
    }

    public int c(int skillId) {
        v l1skills = be.a().a(skillId);
        int dice = l1skills.j();
        int value = l1skills.i();
        int magicDamage = 0;
        int magicBonus = bi.d.c(this.g.eD());
        if (magicBonus > 10) {
            magicBonus = 10;
        }
        int diceCount = value + magicBonus;
        int i2 = 0;
        while (i2 < diceCount) {
            magicDamage += bi.i.a(dice) + 1;
            ++i2;
        }
        double alignmentRevision = 1.0;
        if (this.g.fa() > 0) {
            alignmentRevision += (double)this.g.fa() / 32768.0;
        }
        magicDamage = (int)((double)magicDamage * alignmentRevision);
        if (this.f.bB(170)) {
            magicDamage *= 2;
            this.f.bA(170);
            if (this.f instanceof u) {
                this.i.a(new cm(59));
            }
        }
        if (this.f.bB(173)) {
            magicDamage /= 2;
        }
        return magicDamage;
    }

    public void a(int damage, int drainMana) {
        if (this.e == 1 || this.e == 3) {
            this.b(damage, drainMana);
        } else if (this.e == 2 || this.e == 4) {
            this.c(damage, drainMana);
        }
        if (l1j.server.a.S) {
            if ((this.e == 1 || this.e == 2) && this.h.l()) {
                this.h.a(new cg(this.f, "\\\\fRf4\u2193 \u9b54\u6cd5\u50b7\u5bb3\\\\fRfM (" + damage + ")"));
                this.h.a(new ei("\u5c0d" + this.f.et() + "\u9020\u6210\u9b54\u6cd5\u50b7\u5bb3= " + damage));
            } else if (this.e == 1 || this.e == 3) {
                this.i.l();
            }
        }
    }

    private void b(int damage, int drainMana) {
        if (this.e == 1) {
            if (drainMana > 0 && this.i.eb() > 0) {
                if (drainMana > this.i.eb()) {
                    drainMana = this.i.eb();
                }
                int newMp = this.h.eb() + drainMana;
                this.h.i_(newMp);
            }
            this.i.a((f)this.h, drainMana);
            this.i.a((f)this.h, (double)damage, true);
        } else if (this.e == 3) {
            this.i.a(this.j, (double)damage, true);
        }
    }

    private void c(int damage, int drainMana) {
        if (this.e == 2) {
            if (drainMana > 0) {
                int drainValue = this.k.i(drainMana);
                int newMp = this.h.eb() + drainValue;
                this.h.i_(newMp);
            }
            this.k.a((f)this.h, drainMana);
            this.k.b(this.h, damage);
        } else if (this.e == 4) {
            this.k.b(this.j, damage);
        }
    }

    private double f(int skillId) {
        v l1skills = be.a().a(skillId);
        int dice = l1skills.j();
        int diceCount = l1skills.k();
        double skillDamage = l1skills.i();
        int i2 = 0;
        while (i2 < diceCount) {
            skillDamage += (double)(bi.i.a(dice) + 1);
            ++i2;
        }
        int spByItem = this.g.eV() - this.g.eW();
        double charaIntelligence = this.g.eD() + spByItem;
        if (charaIntelligence < 1.0) {
            charaIntelligence = 1.0;
        }
        int resist = 0;
        if (l1skills.n() == 1) {
            resist = this.f.eI();
        } else if (l1skills.n() == 2) {
            resist = this.f.eH();
        } else if (l1skills.n() == 4) {
            resist = this.f.eG();
        } else if (l1skills.n() == 8) {
            resist = this.f.eF();
        }
        double coefficient = 75.0 * charaIntelligence - 100.0 - (double)(8 * resist);
        if (coefficient < 0.0) {
            coefficient = 0.0;
        }
        double magicDamage = skillDamage * coefficient / 800.0;
        double critical = bi.d.i(this.h.bj(), this.h.eD()) + this.h.dG() + this.h.aC().d();
        if ((double)bi.i.a(100) < critical) {
            magicDamage *= 1.5;
            this.n = true;
        }
        if (this.e == 1 || this.e == 2) {
            magicDamage += (double)bi.d.g(this.h.bj(), this.h.eD());
            magicDamage += (double)bi.d.c(this.h.eD());
        }
        magicDamage *= w.a(this.f, this.g);
        if (this.g.bB(219)) {
            magicDamage += 10.0;
        }
        if (skillId == 108) {
            magicDamage = this.e == 1 ? (double)this.g.ea() * 0.76 + (double)this.g.eb() * 0.66 : (double)this.g.ex();
        }
        return magicDamage;
    }

    public static double a(f attacker, f target, double dmg, int attr) {
        int spByItem = attacker.eV() - attacker.eW();
        double charaIntelligence = attacker.eD() + spByItem;
        if (charaIntelligence < 1.0) {
            charaIntelligence = 1.0;
        }
        int resist = 0;
        if (attr == 1) {
            resist = target.eI();
        } else if (attr == 2) {
            resist = target.eH();
        } else if (attr == 4) {
            resist = target.eG();
        } else if (attr == 8) {
            resist = target.eF();
        }
        double coefficient = 75.0 * charaIntelligence - 100.0 - (double)(8 * resist);
        if (coefficient < 0.0) {
            coefficient = 0.0;
        }
        double magicDamage = dmg * coefficient / 800.0;
        if (attacker instanceof t) {
            magicDamage *= 1.76;
        }
        magicDamage *= w.a(target, attacker);
        magicDamage = w.a(attacker, target, magicDamage);
        return (int)magicDamage;
    }

    private static double a(f target, f attacker) {
        if (w.a(target)) {
            return 0.0;
        }
        int magicHit = 0;
        if (attacker instanceof u) {
            u pc = (u)attacker;
            magicHit = pc.dD() + bi.d.h(pc.bj(), pc.eD());
        }
        double resistHis = target.W_() - magicHit;
        double coefficient2 = (100.0 - Math.min(Math.max(0.0, resistHis), 100.0) / 2.0 - Math.max(0.0, resistHis - 100.0) / 10.0) / 100.0;
        return Math.max(coefficient2, 0.3);
    }

    private static double a(f cha, int attr) {
        int resist = 0;
        if (attr == 1) {
            resist = cha.eI();
        } else if (attr == 2) {
            resist = cha.eH();
        } else if (attr == 4) {
            resist = cha.eG();
        } else if (attr == 8) {
            resist = cha.eF();
        }
        double attrDeffence = 0.0;
        attrDeffence = resist < 10 ? 0.01 : (resist < 20 ? 0.02 : (resist < 30 ? 0.03 : (resist < 40 ? 0.04 : (resist < 50 ? 0.05 : (resist < 60 ? 0.06 : (resist < 70 ? 0.1 : (resist < 80 ? 0.15 : (resist < 90 ? 0.2 : (resist < 100 ? 0.25 : 0.3)))))))));
        return 1.0 - attrDeffence;
    }

    public boolean b() {
        return this.n;
    }
}

