/*
 * Decompiled with CFR 0.152.
 */
package bi;

import ao.aw;
import ao.w;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.q;
import aq.x;
import be.cu;
import be.ds;
import bh.n;
import bi.f;
import java.util.concurrent.CopyOnWriteArrayList;
import l1j.server.a;

public class b {
    private b() {
    }

    public static void a(u lastAtk_pc, t npc, q hateList) {
        if (npc instanceof v || npc instanceof z) {
            return;
        }
        int totalHateExp = 0;
        int totalHateLawful = 0;
        CopyOnWriteArrayList<q.a> hateDataList = hateList.d();
        for (q.a data : hateDataList) {
            aq.f cha = data.a;
            int chaHate = data.b;
            if (cha == null || cha.eX()) {
                hateDataList.remove(data);
                continue;
            }
            totalHateExp += chaHate;
            if (!(cha instanceof u)) continue;
            totalHateLawful += chaHate;
        }
        if (totalHateExp == 0) {
            return;
        }
        if (totalHateLawful <= 0) {
            totalHateLawful = 1;
        }
        int exp = npc.m();
        int lawful = npc.fa();
        int acquire_exp = 0;
        int acquire_lawful = 0;
        if (!lastAtk_pc.q()) {
            for (q.a data : hateDataList) {
                aq.f cha = data.a;
                int chaHate = data.b;
                acquire_exp = exp * chaHate / totalHateExp;
                if (cha instanceof u) {
                    acquire_lawful = lawful * chaHate / totalHateLawful;
                    b.a((u)cha, acquire_exp, acquire_lawful);
                    continue;
                }
                if (!(cha instanceof v)) continue;
                b.a((v)cha, acquire_exp);
            }
            return;
        }
        int partyHateExp = 0;
        int partyHateLawful = 0;
        for (q.a data : hateDataList) {
            u master;
            aq.f cha = data.a;
            int chaHate = data.b;
            if (cha instanceof u) {
                u pc = (u)cha;
                if (pc == lastAtk_pc) {
                    partyHateExp += chaHate;
                    partyHateLawful += chaHate;
                    continue;
                }
                if (lastAtk_pc.aL().d(pc)) {
                    partyHateExp += chaHate;
                    partyHateLawful += chaHate;
                    continue;
                }
                acquire_exp = exp * chaHate / totalHateExp;
                acquire_lawful = lawful * chaHate / totalHateLawful;
                b.a(pc, acquire_exp, acquire_lawful);
                continue;
            }
            if (cha instanceof v) {
                v pet = (v)cha;
                master = (u)pet.M();
                if (master == lastAtk_pc) {
                    partyHateExp += chaHate;
                    continue;
                }
                if (lastAtk_pc.aL().d(master)) {
                    partyHateExp += chaHate;
                    continue;
                }
                acquire_exp = exp * chaHate / totalHateExp;
                b.a(pet, acquire_exp);
                continue;
            }
            if (!(cha instanceof z)) continue;
            z summon = (z)cha;
            master = (u)summon.M();
            if (master == lastAtk_pc) {
                partyHateExp += chaHate;
                continue;
            }
            if (!lastAtk_pc.aL().d(master)) continue;
            partyHateExp += chaHate;
        }
        int party_exp = exp * partyHateExp / totalHateExp;
        int party_lawful = lawful * partyHateLawful / totalHateLawful;
        double party_level = 0.0;
        double bonus = 1.0;
        for (u member : lastAtk_pc.aL().c()) {
            if (lastAtk_pc.b((aa)member) || lastAtk_pc.equals(member)) {
                party_level += (double)(member.ev() * member.ev());
            }
            if (!lastAtk_pc.b((aa)member)) continue;
            bonus += 0.04;
        }
        u leader = lastAtk_pc.aL().a();
        if (leader.x() && (lastAtk_pc.b((aa)leader) || lastAtk_pc.equals(leader))) {
            bonus += 0.059;
        }
        party_exp = (int)((double)party_exp * bonus);
        for (u member : lastAtk_pc.aL().c()) {
            v pet;
            u master;
            u pc;
            int chaHate;
            aq.f cha;
            if (member != lastAtk_pc && !lastAtk_pc.b((aa)member)) continue;
            double dist = (double)(member.ev() * member.ev()) / party_level;
            int member_exp = (int)((double)party_exp * dist);
            int member_lawful = (int)((double)party_lawful * dist);
            int ownHateExp = 0;
            for (q.a data : hateDataList) {
                cha = data.a;
                chaHate = data.b;
                if (cha instanceof u) {
                    pc = (u)cha;
                    if (pc != member) continue;
                    ownHateExp += chaHate;
                    continue;
                }
                if (!(cha instanceof v) || (master = (u)(pet = (v)cha).M()) != member) continue;
                ownHateExp += chaHate;
            }
            if (ownHateExp <= 0) {
                if (member.eX()) continue;
                b.a(member, member_exp, member_lawful);
                continue;
            }
            for (q.a data : hateDataList) {
                cha = data.a;
                chaHate = data.b;
                if (cha instanceof u) {
                    pc = (u)cha;
                    if (pc != member) continue;
                    acquire_exp = member_exp * chaHate / ownHateExp;
                    b.a(pc, acquire_exp, member_lawful);
                    continue;
                }
                if (!(cha instanceof v) || (master = (u)(pet = (v)cha).M()) != member) continue;
                acquire_exp = member_exp * chaHate / ownHateExp;
                b.a(pet, acquire_exp);
            }
        }
    }

    private static void a(u pc, int exp, int lawful) {
        int add_lawful = (int)((double)lawful * a.C) * -1;
        pc.cs(add_lawful);
        if (pc.dW() > 0) {
            int level = Math.min(pc.dW(), 11);
            exp += 100 + (level - 1) * 10;
        }
        int bless_exp = (int)((double)exp * 0.77);
        if (pc.ev() >= 49) {
            if (pc.cC() < bless_exp) {
                bless_exp = pc.cC();
            }
            pc.K(pc.cC() - bless_exp);
            exp += bless_exp;
        }
        if (pc.cE() == -1) {
            exp = (int)((double)exp * (1.0 + 0.1 * (double)x.a().c(pc.fr())));
        }
        if (pc.bB(4084) || pc.bB(4092)) {
            exp = (int)((double)exp * 1.2);
        }
        if (pc.bB(4092)) {
            exp = (int)((double)exp * 1.2);
        }
        if (pc.bB(4076)) {
            exp = (int)((double)exp * 1.23);
        }
        if (pc.dN() > 0) {
            exp = (int)((double)exp * (1.0 + 0.1 * (double)pc.dN()));
        }
        double exppenalty = w.d(pc.ev());
        double foodBonus = 1.0;
        double expBonus = 1.0;
        if (pc.bB(3007) || pc.bB(3015)) {
            foodBonus = 1.01;
        }
        if (pc.bB(3023) || pc.bB(3031) || pc.bB(3049) || pc.bB(3050) || pc.bB(3051)) {
            foodBonus = 1.02;
        }
        if (pc.bB(3039) || pc.bB(3047)) {
            foodBonus = 1.03;
        }
        if (pc.bB(3052)) {
            foodBonus = 1.04;
        }
        if (pc.bB(3053) || pc.bB(3054) || pc.bB(3055)) {
            foodBonus = 1.04;
        }
        if (pc.bB(3056)) {
            foodBonus = 1.06;
        }
        if (pc.bB(4007)) {
            expBonus = 1.2;
        } else if (pc.bB(4001)) {
            expBonus = 2.5;
        } else if (pc.bB(4002)) {
            expBonus = 2.75;
        } else if (pc.bB(4003)) {
            expBonus = 3.0;
        } else if (pc.bB(4004)) {
            expBonus = 3.25;
        } else if (pc.bB(4005)) {
            expBonus = 3.5;
        } else if (pc.bB(4070)) {
            expBonus = 2.0;
        } else if (pc.bB(4078)) {
            expBonus = 1.3;
        }
        double map_expRate = pc.fq().h;
        int add_exp = (int)((double)exp * exppenalty * a.B * foodBonus * expBonus * map_expRate);
        pc.x(add_exp);
        pc.H();
    }

    private static void a(v pet, int exp) {
        u pc = (u)pet.M();
        int petItemObjId = pet.k();
        int levelBefore = pet.ev();
        int totalExp = (int)((double)exp * a.B + (double)pet.m());
        if (totalExp >= w.a(51)) {
            totalExp = w.a(51) - 1;
        }
        pet.k(totalExp);
        pet.b(w.c(totalExp));
        int expPercentage = w.a(pet.ev(), totalExp);
        int gap = pet.ev() - levelBefore;
        int i2 = 1;
        while (i2 <= gap) {
            f hpUpRange = pet.av().e();
            f mpUpRange = pet.av().f();
            pet.bH(hpUpRange.a());
            pet.bJ(mpUpRange.a());
            ++i2;
        }
        pet.f(expPercentage);
        pc.a(new cu(pet, pc));
        if (gap != 0) {
            n petTemplate = aw.a().b(petItemObjId);
            if (petTemplate == null) {
                return;
            }
            petTemplate.g(pet.m());
            petTemplate.d(pet.ev());
            petTemplate.e(pet.ew());
            petTemplate.f(pet.ex());
            aw.a().a(petTemplate);
            pc.a(new ds(320, pet.et()));
        }
    }
}

