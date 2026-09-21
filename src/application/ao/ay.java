/*
 * Decompiled with CFR 0.152.
 */
package ao;

import aq.ae;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ay {
    private static final Logger a = Logger.getLogger(ay.class.getName());
    private static ay b;
    private final HashMap<String, ae> c = new HashMap();
    private final HashMap<Integer, ae> d = new HashMap();

    public static ay a() {
        if (b == null) {
            b = new ay();
        }
        return b;
    }

    private ay() {
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
                    pstm = con.prepareStatement("SELECT * FROM polymorphs");
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
            String name = rs.getString("name");
            int polyId = rs.getInt("polyid");
            int minLevel = rs.getInt("minlevel");
            int weaponEquipFlg = rs.getInt("weaponequip");
            int armorEquipFlg = rs.getInt("armorequip");
            boolean canUseSkill = rs.getBoolean("isSkillUse");
            int causeFlg = rs.getInt("cause");
            ae poly = new ae(polyId, minLevel, weaponEquipFlg, armorEquipFlg, canUseSkill, causeFlg);
            this.c.put(name, poly);
            this.d.put(polyId, poly);
        }
    }

    public ae a(String name) {
        return this.c.get(name);
    }

    public ae a(int polyId) {
        return this.d.get(polyId);
    }
}

