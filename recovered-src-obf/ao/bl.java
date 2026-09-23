/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.e;
import ap.q;
import ap.s;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.ai;
import aq.aq;
import aq.f;
import aq.w;
import be.ak;
import be.cg;
import be.cn;
import be.ee;
import be.g;
import bf.ay;
import bf.be;
import bf.fu;
import bf.t;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bl {
    private static final Logger a = Logger.getLogger(bl.class.getName());
    private static bl b;
    private final HashMap<Integer, a> c = new HashMap();

    public static bl a() {
        if (b == null) {
            b = new bl();
        }
        return b;
    }

    private bl() {
        this.b();
    }

    private void b() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM weapon_skill");
                    rs = pstm.executeQuery();
                    this.a(rs);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(q weapon) {
        if (this.c.containsKey(weapon.N())) {
            weapon.b(this.c.get(weapon.N()).c);
        }
    }

    private void a(ResultSet rs) throws SQLException {
        while (rs.next()) {
            a data = new a();
            data.b = rs.getInt("weapon_id");
            data.c = rs.getString("skill_name");
            data.d = rs.getInt("probability");
            data.e = rs.getInt("prob_every_enchant");
            data.f = rs.getInt("base_damage");
            data.g = rs.getInt("random_damage");
            data.h = rs.getInt("area");
            data.i = rs.getInt("effect_id");
            data.j = rs.getBoolean("is_target_effect");
            data.k = rs.getBoolean("is_arrow");
            data.l = rs.getInt("attr");
            data.m = rs.getDouble("berserkers_rate");
            data.n = rs.getInt("leech_hp");
            data.o = rs.getInt("leech_mp");
            data.p = rs.getInt("leech_every_enchant");
            data.q = rs.getInt("chaser_attack_gfxid");
            data.r = rs.getBoolean("is_caotic");
            data.s = rs.getBoolean("is_skill_freeze");
            data.t = rs.getBoolean("is_skill_disease");
            data.u = rs.getBoolean("is_skill_bind");
            data.v = rs.getBoolean("is_skill_turn_undead");
            data.w = rs.getBoolean("is_skill_mind_break");
            this.c.put(data.b, data);
        }
    }

    public double a(u pc, f target, int weaponid) {
        int value;
        if (target instanceof e) {
            return 0.0;
        }
        if (!this.c.containsKey(weaponid)) {
            return 0.0;
        }
        if (weaponid == 310 ? pc.fp() < 2600 || pc.fp() > 2698 : weaponid == 413 && pc.fp() != 6311) {
            return 0.0;
        }
        a data = this.c.get(weaponid);
        long rawProbability = (long)data.d + (long)data.e * (long)pc.v().G();
        int probability = (int)Math.max(0L, Math.min(100L, rawProbability));
        if (i.a(100) >= probability) {
            return 0.0;
        }
        f effect_cha = data.j ? target : pc;
        int effectId = data.i;
        if (effectId > 0) {
            if (data.k) {
                f dummy = new f();
                if (pc.v().a().aO() == 20) {
                    dummy.cG(pc.fs());
                    dummy.cH(pc.ft());
                } else {
                    dummy.cG(target.fs());
                    dummy.cH(target.ft());
                }
                pc.a(new g(dummy, target, 1, effectId, 0, 6, 0));
                pc.b(new g(dummy, target, 1, effectId, 0, 6, 0));
            } else {
                pc.a(new ee(effect_cha.fr(), effectId));
                pc.b(new ee(effect_cha.fr(), effectId));
            }
        }
        if (data.u) {
            this.d(target);
        }
        if (data.t) {
            this.b(target);
        }
        if (data.v) {
            this.a(target);
        }
        if (data.w) {
            this.a(pc, target);
        }
        if (data.s) {
            this.c(target);
        }
        if (data.q > 0) {
            new bc.e(pc, target, data.q).a();
        }
        double damage = data.f;
        if (data.g != 0) {
            damage += (double)i.a(data.g);
        }
        if (data.r && pc.fa() < 0) {
            damage += (double)i.a(pc.fa() / -300);
        }
        if (data.n > 0) {
            value = data.n + i.a(data.p * pc.v().G());
            if ((value -= target.W_() / 2) < 0) {
                value = 0;
            }
            pc.a(pc.ea() + value);
        }
        if (data.o > 0) {
            value = i.a(data.o + data.p * pc.v().G());
            if (target instanceof u) {
                u tpc = (u)target;
                value = value > tpc.eb() ? tpc.eb() : value;
                tpc.i_(tpc.eb() - value);
            } else if (target instanceof ap.t) {
                ap.t npc = (ap.t)target;
                value = npc.i(value);
                npc.by(npc.eb() - value);
            }
            pc.i_(pc.eb() + value);
        }
        if (data.m > 0.0) {
            damage *= data.m;
        }
        if (data.l != -1) {
            damage = w.a(pc, target, damage, data.l);
        }
        if (data.h > 0 || data.h == -1) {
            for (aa object : aq.a().b((aa)effect_cha, data.h)) {
                if (!(object instanceof f) || object.fr() == pc.fr() || object.fr() == target.fr()) continue;
                double dmg = data.f;
                if (data.g != 0) {
                    dmg += (double)i.a(data.g);
                }
                if (object instanceof u) {
                    u tpc = (u)object;
                    if (tpc.bN() || tpc.aA() || tpc.eX() || pc.a(pc, tpc, true)) {
                        continue;
                    }
                } else if (object instanceof s) {
                    s mob = (s)object;
                    if (mob.eX() || mob.ac() == 1 || mob.ac() == 2) {
                        continue;
                    }
                } else {
                    if (!(object instanceof z) && !(object instanceof v)) continue;
                    ap.t pet = (ap.t)object;
                    u master = (u)pet.M();
                    if (master != null) {
                        if (master.bE() > 0) {
                            dmg /= 8.0;
                        }
                        if (master.fr() == pc.fr() || master.a(pc, master, true)) continue;
                    }
                }
                if (data.u && i.a(100) < 45) {
                    this.d((f)object);
                }
                if (data.t && i.a(100) < 45) {
                    this.b((f)object);
                }
                if (data.v && i.a(100) < 45) {
                    this.a((f)object);
                }
                if (data.s && i.a(100) < 45) {
                    this.c((f)object);
                }
                if (data.w && i.a(100) < 45) {
                    this.a(pc, (f)object);
                }
                if (data.q > 0) {
                    new bc.e(pc, (f)object, data.q).a();
                }
                if (data.n > 0) {
                    int value2 = data.n + i.a(data.p * pc.v().G());
                    if ((value2 -= ((f)object).W_() / 2) < 0) {
                        value2 = 0;
                    }
                    pc.a(pc.ea() + value2);
                }
                if (data.o > 0) {
                    int value3 = i.a(data.o + data.p * pc.v().G());
                    if (object instanceof u) {
                        u tpc = (u)object;
                        value3 = value3 > tpc.eb() ? tpc.eb() : value3;
                        tpc.i_(tpc.eb() - value3);
                    } else if (object instanceof ap.t) {
                        ap.t npc = (ap.t)object;
                        value3 = npc.i(value3);
                        npc.by(npc.eb() - value3);
                    }
                    pc.i_(pc.eb() + value3);
                }
                if (data.l != -1) {
                    dmg = w.a(pc, (f)object, dmg, data.l);
                }
                if (l1j.server.a.S && pc.l()) {
                    f cha = new f();
                    cha.cF(object.fr());
                    cha.cG(object.fs());
                    cha.cH(object.ft() - 1);
                    pc.a(new cg(cha, "\\\\fRf4\u2193 \u9b54\u50b7\\\\fRfM (" + dmg + ")"));
                }
                if (dmg <= 0.0) continue;
                if (object instanceof u) {
                    u targetPc = (u)object;
                    targetPc.a(new ak(targetPc.fr(), 2));
                    targetPc.b(new ak(targetPc.fr(), 2));
                    targetPc.a((f)pc, (double)((int)dmg), false);
                    continue;
                }
                if (!(object instanceof ap.t)) continue;
                ap.t targetNpc = (ap.t)object;
                targetNpc.b(new ak(targetNpc.fr(), 2));
                targetNpc.b(pc, (int)dmg);
            }
        }
        return damage;
    }

    private void a(u pc, f cha) {
        if (i.a(100) <= cha.W_()) {
            return;
        }
        new fu().b(pc, cha, -1);
    }

    private void a(f cha) {
        if (i.a(100) <= cha.W_()) {
            return;
        }
        new t().a(cha, -1);
    }

    private void b(f cha) {
        if (i.a(100) <= cha.W_()) {
            return;
        }
        new be().a(cha, -1);
    }

    private void c(f cha) {
        if (i.a(100) <= cha.W_()) {
            return;
        }
        new ay().a(cha, -1);
    }

    private void d(f cha) {
        int fettersTime = 8000;
        if (w.a(cha)) {
            return;
        }
        ai.a().a(4184, 8000, cha.fs(), cha.ft(), cha.fp());
        if (cha instanceof u) {
            u targetPc = (u)cha;
            targetPc.j(1028, 8000);
            targetPc.a(new cn(6, true));
        } else if (cha instanceof ap.t) {
            ap.t npc = (ap.t)cha;
            npc.j(1028, 8000);
            npc.n(true);
        }
    }

    public double a(u pc, f target, int effectid, int area, double damage) {
        if (target instanceof e) {
            return 0.0;
        }
        pc.a(new ee(target.fr(), effectid));
        pc.b(new ee(target.fr(), effectid));
        for (aa object : aq.a().b((aa)target, area)) {
            double dmg;
            s mob;
            ap.t pet;
            u master;
            if (!(object instanceof f) || object.fr() == pc.fr() || object.fr() == target.fr()) continue;
            if (object instanceof u) {
                u tpc = (u)object;
                if (tpc.bN() || tpc.aA() || tpc.eX() || pc.a(pc, tpc, true)) {
                    continue;
                }
            } else if (!(object instanceof s) ? !(object instanceof z) && !(object instanceof v) || (master = (u)(pet = (ap.t)object).M()) != null && (master.fr() == pc.fr() || master.a(pc, master, true)) : (mob = (s)object).eX() || mob.ac() == 1 || mob.ac() == 2) continue;
            if ((dmg = w.a(pc, (f)object, damage, 0)) <= 0.0) continue;
            if (object instanceof u) {
                u targetPc = (u)object;
                targetPc.a(new ak(targetPc.fr(), 2));
                targetPc.b(new ak(targetPc.fr(), 2));
                targetPc.a((f)pc, (double)((int)dmg), false);
                continue;
            }
            if (!(object instanceof ap.t)) continue;
            ap.t targetNpc = (ap.t)object;
            targetNpc.b(new ak(targetNpc.fr(), 2));
            targetNpc.b(pc, (int)dmg);
        }
        return w.a(pc, target, damage, 0);
    }

    private class a {
        private int b;
        private String c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;
        private boolean j;
        private boolean k;
        private int l;
        private double m;
        private int n;
        private int o;
        private int p;
        private int q;
        private boolean r;
        private boolean s;
        private boolean t;
        private boolean u;
        private boolean v;
        private boolean w;

        private a() {
        }
    }
}

