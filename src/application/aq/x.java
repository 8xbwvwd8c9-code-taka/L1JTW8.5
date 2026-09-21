/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import aq.aa;
import aq.aq;
import be.ci;
import be.cm;
import be.do;
import be.ds;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class x {
    private static final Logger a = Logger.getLogger(x.class.getName());
    private static x b;
    private static HashMap<Integer, CopyOnWriteArrayList<u>> c;
    private static HashMap<Integer, String> d;

    static {
        c = new HashMap();
        d = new HashMap();
    }

    public static x a() {
        if (b == null) {
            b = new x();
        }
        return b;
    }

    private x() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters ");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int objid = rs.getInt("objid");
                        int masterID = rs.getInt("MasterID");
                        String charName = rs.getString("char_name");
                        if (masterID > 0) {
                            u disciple = u.b(charName);
                            this.a(masterID, disciple);
                            continue;
                        }
                        if (masterID >= 0) continue;
                        d.put(objid, charName);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private String d(int objid) {
        String name;
        block6: {
            name = null;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters  WHERE objid=?");
                    pstm.setInt(1, objid);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        name = rs.getString("char_name");
                        d.put(objid, name);
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
        return name;
    }

    private void a(String name) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET MasterID = 0 WHERE char_name =?");
                    pstm.setString(1, name);
                    pstm.execute();
                    pstm.close();
                }
                catch (SQLException e2) {
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

    public String a(int masterID) {
        if (d.containsKey(masterID)) {
            return d.get(masterID);
        }
        return this.d(masterID);
    }

    public CopyOnWriteArrayList<u> b(int masterID) {
        return c.get(masterID);
    }

    private ArrayList<u> e(int masterID) {
        ArrayList<u> onlineList = new ArrayList<u>();
        for (u disciple : c.get(masterID)) {
            aa obj = aq.a().a(disciple.fr());
            if (!(obj instanceof u)) continue;
            onlineList.add((u)obj);
        }
        return onlineList;
    }

    public int c(int masterID) {
        return c.containsKey(masterID) ? c.get(masterID).size() : 0;
    }

    public void a(int masterID, u disciple) {
        if (c.containsKey(masterID)) {
            c.get(masterID).add(disciple);
        } else {
            CopyOnWriteArrayList<u> disciple_list = new CopyOnWriteArrayList<u>();
            disciple_list.add(disciple);
            c.put(masterID, disciple_list);
        }
        this.b(disciple, 4059);
    }

    public void a(int masterID, String name) {
        int i2 = 0;
        while (i2 < c.get(masterID).size()) {
            u temp = c.get(masterID).get(i2);
            if (temp.et().equalsIgnoreCase(name)) {
                c.get(masterID).remove(i2);
                break;
            }
            ++i2;
        }
        u disciple = aq.a().a(name);
        if (disciple == null) {
            this.a(name);
        } else {
            disciple.aV(0);
            disciple.I();
            disciple.a(new ds(2977));
            this.f(disciple);
        }
        aa obj = aq.a().a(masterID);
        if (obj instanceof u) {
            ((u)obj).a(new ds(2977));
        }
        if (x.a().c(masterID) <= 0) {
            if (obj instanceof u) {
                ((u)obj).aV(0);
                ((u)obj).I();
            } else {
                String masterName = x.a().a(masterID);
                x.a().a(masterName);
            }
        }
    }

    public void a(u pc) {
        if (pc.cE() == -1) {
            for (u disciple : this.e(pc.fr())) {
                this.f(disciple);
            }
        }
    }

    public void b(u pc) {
        aa obj;
        if (pc.cE() == -1) {
            for (u disciple : this.e(pc.fr())) {
                int count = 0;
                if (disciple.q()) {
                    for (u menber : disciple.aL().c()) {
                        if (menber.cE() != pc.fr()) continue;
                        ++count;
                    }
                } else {
                    count = 1;
                }
                int skillid = 4058 + count;
                this.b(disciple, skillid);
            }
        } else if (pc.cE() > 0 && (obj = aq.a().a(pc.cE())) != null) {
            this.b(pc, 4059);
        }
    }

    public void c(u pc) {
        block5: {
            block4: {
                if (pc.cE() >= 0) break block4;
                for (u disciple : this.e(pc.fr())) {
                    if (!pc.aL().d(disciple)) continue;
                    this.a(disciple, -4);
                }
                break block5;
            }
            if (pc.cE() <= 0) break block5;
            for (u disciple : this.e(pc.cE())) {
                if (disciple.fr() == pc.fr()) {
                    this.a(disciple, -8);
                    continue;
                }
                if (!pc.aL().d(disciple)) continue;
                if (pc.aL().e(pc)) {
                    this.a(disciple, -8);
                    continue;
                }
                this.a(disciple, -1);
            }
        }
    }

    public void d(u pc) {
        block3: {
            block2: {
                if (pc.cE() != -1) break block2;
                for (u disciple : this.e(pc.fr())) {
                    if (!pc.aL().d(disciple)) continue;
                    this.a(disciple, 4);
                }
                break block3;
            }
            if (pc.cE() <= 0) break block3;
            for (u disciple : this.e(pc.cE())) {
                if (pc.fr() == disciple.fr() || !pc.aL().d(disciple)) continue;
                this.a(disciple, 1);
            }
        }
    }

    public void e(u pc) {
        if (pc.cE() == -1) {
            for (u disciple : this.e(pc.fr())) {
                if (!pc.aL().d(disciple)) continue;
                this.a(disciple, 4);
            }
        } else if (pc.cE() > 0) {
            int skillid = 0;
            for (u disciple : this.e(pc.cE())) {
                if (pc.fr() == disciple.fr() || !pc.aL().d(disciple)) continue;
                skillid = this.a(disciple, 1);
            }
            if (skillid != 0) {
                this.c(pc, 4059);
                this.b(pc, skillid);
            }
        }
    }

    private int a(u pc, int diff) {
        int newid = 0;
        int id = 4059;
        while (id <= 4066) {
            if (pc.bB(id)) {
                newid = Math.min(Math.max(4059, id + diff), 4066);
                this.c(pc, id);
                this.b(pc, newid);
                break;
            }
            ++id;
        }
        return newid;
    }

    private void b(u pc, int skillid) {
        pc.j(skillid, 0);
        pc.a(new cm(147, skillid - 4059, 1));
        if (skillid == 4062 || skillid == 4066) {
            pc.a(new cm(132, pc.u()));
        }
        if (skillid >= 4061 && skillid <= 4062) {
            pc.bY(2);
            pc.bZ(2);
            pc.ca(2);
            pc.cb(2);
        }
        if (skillid >= 4060 && skillid <= 4062) {
            pc.co(1);
            pc.a(new do(pc));
        }
        if (skillid >= 4059 && skillid <= 4062) {
            pc.bL(-1);
            pc.a(new ci(pc));
        }
        if (skillid >= 4065 && skillid <= 4066) {
            pc.bY(6);
            pc.bZ(6);
            pc.ca(6);
            pc.cb(6);
        }
        if (skillid >= 4064 && skillid <= 4066) {
            pc.co(3);
            pc.a(new do(pc));
        }
        if (skillid >= 4063 && skillid <= 4066) {
            pc.bL(-3);
            pc.a(new ci(pc));
        }
    }

    private void c(u pc, int skillid) {
        pc.bz(skillid);
        pc.a(new cm(147, skillid - 4059, 0));
        if (skillid == 4062 || skillid == 4066) {
            pc.a(new cm(132, pc.u()));
        }
        if (skillid >= 4061 && skillid <= 4062) {
            pc.bY(-2);
            pc.bZ(-2);
            pc.ca(-2);
            pc.cb(-2);
        }
        if (skillid >= 4060 && skillid <= 4062) {
            pc.co(-1);
            pc.a(new do(pc));
        }
        if (skillid >= 4059 && skillid <= 4062) {
            pc.bL(1);
            pc.a(new ci(pc));
        }
        if (skillid >= 4065 && skillid <= 4066) {
            pc.bY(-6);
            pc.bZ(-6);
            pc.ca(-6);
            pc.cb(-6);
        }
        if (skillid >= 4064 && skillid <= 4066) {
            pc.co(-3);
            pc.a(new do(pc));
        }
        if (skillid >= 4063 && skillid <= 4066) {
            pc.bL(3);
            pc.a(new ci(pc));
        }
    }

    private void f(u pc) {
        int id = 4059;
        while (id <= 4066) {
            if (pc.bB(id)) {
                this.c(pc, id);
            }
            ++id;
        }
    }
}

