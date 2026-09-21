/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import aq.aq;
import bh.v;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class be {
    private static final Logger a = Logger.getLogger(be.class.getName());
    private static be b;
    private final HashMap<Integer, v> c = new HashMap();
    private final boolean d;

    public static be a() {
        if (b == null) {
            b = new be();
        }
        return b;
    }

    private be() {
        this.d = true;
        this.c();
    }

    private void c() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM skills");
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

    private void a(ResultSet rs) throws SQLException {
        while (rs.next()) {
            v l1skills = new v();
            int skill_id = rs.getInt("skill_id");
            l1skills.a(skill_id);
            l1skills.a(rs.getString("name"));
            l1skills.b(rs.getInt("skill_level"));
            l1skills.c(rs.getInt("mpConsume"));
            l1skills.d(rs.getInt("hpConsume"));
            l1skills.e(rs.getInt("itemConsumeId"));
            l1skills.f(rs.getInt("itemConsumeCount"));
            l1skills.g(rs.getInt("reuseDelay"));
            l1skills.u(rs.getInt("buffDuration"));
            l1skills.h(rs.getInt("damage_value"));
            l1skills.i(rs.getInt("damage_dice"));
            l1skills.j(rs.getInt("damage_dice_count"));
            l1skills.k(rs.getInt("probability_value"));
            l1skills.l(rs.getInt("probability_dice"));
            l1skills.m(rs.getInt("attr"));
            l1skills.n(rs.getInt("lawful"));
            l1skills.o(rs.getInt("ranged"));
            l1skills.p(rs.getInt("area"));
            l1skills.q(rs.getInt("id"));
            l1skills.r(rs.getInt("action_id"));
            l1skills.s(rs.getInt("castgfx"));
            l1skills.t(rs.getInt("castgfx2"));
            this.c.put(new Integer(skill_id), l1skills);
        }
    }

    public void a(int playerobjid, int skillid, String skillname, int active, int time) {
        block7: {
            if (this.a(playerobjid, skillid)) {
                return;
            }
            u pc = (u)aq.a().a(playerobjid);
            if (pc != null) {
                pc.f(skillid);
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_skills SET char_obj_id=?, skill_id=?, skill_name=?, is_active=?, activetimeleft=?");
                    pstm.setInt(1, playerobjid);
                    pstm.setInt(2, skillid);
                    pstm.setString(3, skillname);
                    pstm.setInt(4, active);
                    pstm.setInt(5, time);
                    pstm.execute();
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    public boolean a(int playerobjid, int skillid) {
        boolean ret;
        block5: {
            ret = false;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=? AND skill_id=?");
                    pstm.setInt(1, playerobjid);
                    pstm.setInt(2, skillid);
                    rs = pstm.executeQuery();
                    ret = rs.next();
                }
                catch (Exception e2) {
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
        return ret;
    }

    public boolean b() {
        return this.d;
    }

    public v a(int i2) {
        return this.c.get(new Integer(i2));
    }
}

