/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.t;
import bh.l;
import bi.g;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class au {
    private static final Logger a = Logger.getLogger(au.class.getName());
    private final boolean b;
    private static au c;
    private final HashMap<Integer, l> d = new HashMap();
    private static final Map<String, Integer> e;

    static {
        e = au.d();
    }

    public static au a() {
        if (c == null) {
            c = new au();
        }
        return c;
    }

    public boolean b() {
        return this.b;
    }

    private au() {
        this.c();
        this.b = true;
    }

    private void c() {
        block10: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM npc");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        l npc = new l();
                        int npcId = rs.getInt("npcid");
                        npc.a(npcId);
                        npc.a(rs.getString("name"));
                        npc.d(rs.getString("nameid"));
                        npc.b(rs.getString("impl"));
                        npc.k(rs.getInt("gfxid"));
                        npc.b(rs.getInt("lvl"));
                        npc.c(rs.getInt("hp"));
                        npc.d(rs.getInt("mp"));
                        npc.e(rs.getInt("ac"));
                        npc.a(rs.getByte("str"));
                        npc.b(rs.getByte("con"));
                        npc.c(rs.getByte("dex"));
                        npc.d(rs.getByte("wis"));
                        npc.e(rs.getByte("intel"));
                        npc.f(rs.getInt("mr"));
                        npc.g(rs.getInt("exp"));
                        npc.h(rs.getInt("lawful"));
                        npc.c(rs.getString("size"));
                        npc.i(rs.getInt("weakAttr"));
                        npc.j(rs.getInt("ranged"));
                        npc.c(rs.getBoolean("tamable"));
                        npc.d(rs.getInt("can_move") > 0);
                        npc.e(rs.getInt("can_attack") > 0);
                        npc.m(rs.getBoolean("isDwarf"));
                        npc.I(rs.getInt("base_damage"));
                        npc.J(rs.getInt("random_damage"));
                        npc.l(rs.getInt("undead"));
                        npc.m(rs.getInt("poison_atk"));
                        npc.f(rs.getBoolean("agro"));
                        npc.a(rs.getBoolean("agrososc"));
                        npc.b(rs.getBoolean("agrocoi"));
                        Integer family = e.get(rs.getString("family"));
                        if (family == null) {
                            npc.n(0);
                        } else {
                            npc.n(family);
                        }
                        int agrofamily = rs.getInt("agrofamily");
                        if (npc.D() == 0 && agrofamily == 1) {
                            npc.o(0);
                        } else {
                            npc.o(agrofamily);
                        }
                        npc.p(rs.getInt("agrogfxid1"));
                        npc.q(rs.getInt("agrogfxid2"));
                        npc.g(rs.getBoolean("picupitem"));
                        npc.r(rs.getInt("digestitem"));
                        npc.h(rs.getBoolean("bravespeed"));
                        npc.s(rs.getInt("hprinterval"));
                        npc.t(rs.getInt("hpr"));
                        npc.u(rs.getInt("mprinterval"));
                        npc.v(rs.getInt("mpr"));
                        npc.i(rs.getBoolean("teleport"));
                        npc.w(rs.getInt("randomlevel"));
                        npc.x(rs.getInt("randomhp"));
                        npc.y(rs.getInt("randommp"));
                        npc.z(rs.getInt("randomac"));
                        npc.A(rs.getInt("randomexp"));
                        npc.B(rs.getInt("randomlawful"));
                        npc.C(rs.getInt("damage_reduction"));
                        npc.j(rs.getBoolean("hard"));
                        npc.k(rs.getBoolean("doppel"));
                        npc.l(rs.getBoolean("IsErase"));
                        npc.D(rs.getInt("bowActId"));
                        npc.E(rs.getInt("karma"));
                        npc.F(rs.getInt("transform_id"));
                        npc.G(rs.getInt("transform_gfxid"));
                        npc.H(rs.getInt("light_size"));
                        npc.n(rs.getBoolean("amount_fixed"));
                        npc.o(rs.getBoolean("change_head"));
                        npc.p(rs.getBoolean("cant_resurrect"));
                        npc.e(rs.getString("normal_action"));
                        npc.f(rs.getString("caotic_action"));
                        npc.g(rs.getString("craft_list"));
                        npc.K(rs.getInt("mobList_number"));
                        this.d.put(npcId, npc);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block10;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public l a(int id) {
        return this.d.get(id);
    }

    public t b(int id) {
        l npcTemp = this.a(id);
        if (npcTemp == null) {
            throw new IllegalArgumentException(String.format("NpcTemplate: %d not found", id));
        }
        return g.a(npcTemp);
    }

    private static Map<String, Integer> d() {
        HashMap<String, Integer> result;
        block6: {
            result = new HashMap<String, Integer>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("select distinct(family) as family from npc WHERE NOT trim(family) =''");
                    rs = pstm.executeQuery();
                    int id = 1;
                    while (rs.next()) {
                        String family = rs.getString("family");
                        result.put(family, id++);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        return result;
    }

    public int a(String name) {
        for (l npc : this.d.values()) {
            if (!npc.c().replace(" ", "").equals(name)) continue;
            return npc.b();
        }
        return 0;
    }
}

