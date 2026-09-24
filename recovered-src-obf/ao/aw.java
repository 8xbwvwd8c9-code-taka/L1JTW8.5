/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.au;
import ao.ax;
import ap.t;
import ap.v;
import bh.l;
import bh.n;
import bh.p;
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

public class aw {
    private static final Logger a = Logger.getLogger(aw.class.getName());
    private static aw b;
    private final HashMap<Integer, n> c = new HashMap();

    public static aw a() {
        if (b == null) {
            b = new aw();
        }
        return b;
    }

    private aw() {
        this.c();
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM pets");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        n pet = new n();
                        int itemobjid = rs.getInt(1);
                        pet.a(itemobjid);
                        pet.b(rs.getInt(2));
                        pet.c(rs.getInt(3));
                        pet.a(rs.getString(4));
                        pet.d(rs.getInt(5));
                        pet.e(rs.getInt(6));
                        pet.f(rs.getInt(7));
                        pet.g(rs.getInt(8));
                        pet.h(rs.getInt(9));
                        pet.i(rs.getInt(10));
                        this.c.put(new Integer(itemobjid), pet);
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
    }

    public void a(int npcid, int itemobjid) {
        l l1npc = au.a().a(npcid);
        n l1pet = new n();
        l1pet.a(itemobjid);
        l1pet.b(d.a().c());
        l1pet.c(l1npc.b());
        l1pet.a(l1npc.c());
        l1pet.d(l1npc.e());
        l1pet.e(l1npc.f());
        l1pet.f(l1npc.g());
        l1pet.g(750);
        l1pet.h(0);
        l1pet.i(50);
        if (this.insertDurable(l1pet)) {
            this.c.put(new Integer(itemobjid), l1pet);
        }
    }

    public void a(t pet, int objid, int itemobjid) {
        n l1pet = new n();
        l1pet.a(itemobjid);
        l1pet.b(objid);
        l1pet.c(pet.U_().b());
        l1pet.a(pet.U_().c());
        l1pet.d(pet.U_().e());
        l1pet.e(pet.ew());
        l1pet.f(pet.ex());
        l1pet.g(750);
        l1pet.h(0);
        l1pet.i(50);
        if (this.insertDurable(l1pet)) {
            this.c.put(new Integer(itemobjid), l1pet);
        }
    }

    public void a(n pet) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE pets SET objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=? WHERE item_obj_id=?");
                    pstm.setInt(1, pet.b());
                    pstm.setInt(2, pet.c());
                    pstm.setString(3, pet.d());
                    pstm.setInt(4, pet.e());
                    pstm.setInt(5, pet.f());
                    pstm.setInt(6, pet.g());
                    pstm.setInt(7, pet.h());
                    pstm.setInt(8, pet.i());
                    pstm.setInt(9, pet.j());
                    pstm.setInt(10, pet.a());
                    pstm.execute();
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
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

    public void a(v pet) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE pets SET food=? WHERE item_obj_id=?");
                    pstm.setInt(1, pet.fj());
                    pstm.setInt(2, pet.k());
                    pstm.execute();
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
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

    public void a(int itemobjid) {
        if (this.deleteDurable(itemobjid)) {
            this.c.remove(itemobjid);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static boolean a(String nameCaseInsensitive) {
        block7: {
            block6: {
                nameLower = nameCaseInsensitive.toLowerCase();
                con = null;
                pstm = null;
                rs = null;
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("SELECT item_obj_id FROM pets WHERE LOWER(name)=?");
                pstm.setString(1, nameLower);
                rs = pstm.executeQuery();
                if (rs.next()) break block6;
                j.a(rs, pstm, con);
                return false;
            }
            try {
                ** if (!ax.b().a((String)nameLower)) goto lbl-1000
            }
            catch (SQLException e) {
                try {
                    aw.a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                }
                catch (Throwable var6_6) {
                    j.a(rs, pstm, con);
                    throw var6_6;
                }
                j.a(rs, pstm, con);
                break block7;
            }
lbl-1000:
            // 1 sources

            {
                j.a(rs, pstm, con);
                return false;
            }
lbl-1000:
            // 1 sources

            {
            }
            j.a(rs, pstm, con);
        }
        return true;
    }

    public void a(int petNpcId, int objid, int itemobjid, int upLv, int lvExp) {
        p petType = ax.b().a(petNpcId);
        n l1pet = new n();
        l1pet.a(itemobjid);
        l1pet.b(objid);
        l1pet.c(petNpcId);
        l1pet.a(petType.c());
        l1pet.d(upLv);
        int hpUpMin = petType.e().b();
        int hpUpMax = petType.e().c();
        int mpUpMin = petType.f().b();
        int mpUpMax = petType.f().c();
        short randomhp = (short)((hpUpMin + hpUpMax) / 2);
        short randommp = (short)((mpUpMin + mpUpMax) / 2);
        int i2 = 1;
        while (i2 < upLv) {
            randomhp = (short)(randomhp + (i.a(hpUpMax - hpUpMin) + hpUpMin + 1));
            randommp = (short)(randommp + (i.a(mpUpMax - mpUpMin) + mpUpMin + 1));
            ++i2;
        }
        l1pet.e(randomhp);
        l1pet.f(randommp);
        l1pet.g(lvExp);
        l1pet.h(0);
        l1pet.i(50);
        if (this.insertDurable(l1pet)) {
            this.c.put(new Integer(itemobjid), l1pet);
        }
    }

    private boolean insertDurable(n pet) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("INSERT INTO pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=?");
            this.bindPet(pstm, pet, false, 0);
            return pstm.executeUpdate() == 1;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    private boolean deleteDurable(int itemobjid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("DELETE FROM pets WHERE item_obj_id=?");
            pstm.setInt(1, itemobjid);
            return pstm.executeUpdate() == 1;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    public boolean replaceDurable(int oldItemObjId, n pet) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=? WHERE item_obj_id=?");
            this.bindPet(pstm, pet, true, oldItemObjId);
            if (pstm.executeUpdate() != 1) {
                return false;
            }
            this.c.remove(oldItemObjId);
            this.c.put(new Integer(pet.a()), pet);
            return true;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    private void bindPet(PreparedStatement pstm, n pet, boolean replace, int oldItemObjId) throws SQLException {
        pstm.setInt(1, pet.a());
        pstm.setInt(2, pet.b());
        pstm.setInt(3, pet.c());
        pstm.setString(4, pet.d());
        pstm.setInt(5, pet.e());
        pstm.setInt(6, pet.f());
        pstm.setInt(7, pet.g());
        pstm.setInt(8, pet.h());
        pstm.setInt(9, pet.i());
        pstm.setInt(10, pet.j());
        if (replace) {
            pstm.setInt(11, oldItemObjId);
        }
    }

    public n b(int itemobjid) {
        return this.c.get(new Integer(itemobjid));
    }

    public n[] b() {
        return this.c.values().toArray(new n[this.c.size()]);
    }
}

