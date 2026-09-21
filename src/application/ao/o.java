/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import ax.b;
import ax.d;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class o {
    private static final Logger a = Logger.getLogger(o.class.getName());
    private static o b;
    private final ConcurrentHashMap<String, a> c = new ConcurrentHashMap();

    public static o a() {
        if (b == null) {
            b = new o();
        }
        return b;
    }

    public o() {
        this.e();
        this.d();
        this.b();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(u pc) throws Exception {
        u u2 = pc;
        synchronized (u2) {
            block9: {
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    try {
                        int i2 = 0;
                        con = l1j.server.b.a().b();
                        pstm = con.prepareStatement("INSERT INTO characters SET account_name=?,objid=?,char_name=?,birthday=?,level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,MasterID=?,ClanID=?,Clanname=?,ClanRank=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,Pay=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?");
                        pstm.setString(++i2, pc.bc());
                        pstm.setInt(++i2, pc.fr());
                        pstm.setString(++i2, pc.et());
                        pstm.setInt(++i2, pc.o());
                        pstm.setInt(++i2, pc.ev());
                        pstm.setInt(++i2, pc.bz());
                        pstm.setInt(++i2, pc.m());
                        pstm.setInt(++i2, pc.bd());
                        pstm.setInt(++i2, Math.max(1, pc.ea()));
                        pstm.setInt(++i2, pc.be());
                        pstm.setInt(++i2, pc.eb());
                        pstm.setInt(++i2, pc.ey());
                        pstm.setInt(++i2, pc.bf());
                        pstm.setInt(++i2, pc.bg());
                        pstm.setInt(++i2, pc.bh());
                        pstm.setInt(++i2, pc.bi());
                        pstm.setInt(++i2, pc.bj());
                        pstm.setInt(++i2, pc.bk());
                        pstm.setInt(++i2, pc.k());
                        pstm.setInt(++i2, pc.aB());
                        pstm.setInt(++i2, pc.aJ());
                        pstm.setInt(++i2, pc.ay());
                        pstm.setInt(++i2, pc.fb());
                        pstm.setInt(++i2, pc.fs());
                        pstm.setInt(++i2, pc.ft());
                        pstm.setInt(++i2, pc.fp());
                        pstm.setInt(++i2, pc.fj());
                        pstm.setInt(++i2, pc.fa());
                        pstm.setString(++i2, pc.eZ());
                        pstm.setInt(++i2, pc.cE());
                        pstm.setInt(++i2, pc.aF());
                        pstm.setString(++i2, pc.aG());
                        pstm.setInt(++i2, pc.aH());
                        pstm.setInt(++i2, pc.bA());
                        pstm.setInt(++i2, pc.bB());
                        pstm.setInt(++i2, pc.bC());
                        pstm.setInt(++i2, pc.aD());
                        pstm.setInt(++i2, pc.aE());
                        pstm.setInt(++i2, pc.ca());
                        pstm.setInt(++i2, pc.bD());
                        pstm.setInt(++i2, pc.az());
                        pstm.setInt(++i2, pc.bE());
                        pstm.setInt(++i2, pc.bF());
                        pstm.setInt(++i2, pc.bG());
                        pstm.setInt(++i2, 0);
                        pstm.setInt(++i2, pc.bI());
                        pstm.setBoolean(++i2, pc.bJ());
                        pstm.setInt(++i2, pc.P());
                        pstm.setTimestamp(++i2, pc.bO());
                        pstm.setTimestamp(++i2, pc.bP());
                        pstm.setTimestamp(++i2, pc.bQ());
                        pstm.execute();
                    }
                    catch (SQLException e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(pstm);
                        j.a(con);
                        break block9;
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
            String name = pc.et();
            if (!this.c.containsKey(name)) {
                a cn2 = new a();
                cn2.b = name;
                cn2.a = pc.fr();
                this.c.put(name, cn2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(u pc) throws Exception {
        u u2 = pc;
        synchronized (u2) {
            block8: {
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    try {
                        int i2 = 0;
                        con = l1j.server.b.a().b();
                        pstm = con.prepareStatement("UPDATE characters SET level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,MasterID=?,ClanID=?,Clanname=?,ClanRank=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,LogoutTime=?,DeleteTime=?,TamUseTime=?,EinhasadsBless=?,CharStoreSpace=?,RuneOpenStatus=?,WsaRecord=?,MapTime_1=?,MapTime_2=?,MapTime_3=?,MapTime_4=?,MapTime_5=?,MapTime_6=?,MapTime_7=? WHERE objid=?");
                        pstm.setInt(++i2, pc.ev());
                        pstm.setInt(++i2, pc.bz());
                        pstm.setInt(++i2, pc.m());
                        pstm.setInt(++i2, pc.bd());
                        pstm.setInt(++i2, Math.max(1, pc.ea()));
                        pstm.setInt(++i2, pc.be());
                        pstm.setInt(++i2, pc.eb());
                        pstm.setInt(++i2, pc.ey());
                        pstm.setInt(++i2, pc.bf());
                        pstm.setInt(++i2, pc.bg());
                        pstm.setInt(++i2, pc.bh());
                        pstm.setInt(++i2, pc.bi());
                        pstm.setInt(++i2, pc.bj());
                        pstm.setInt(++i2, pc.bk());
                        pstm.setInt(++i2, pc.k());
                        pstm.setInt(++i2, pc.aB());
                        pstm.setInt(++i2, pc.aJ());
                        pstm.setInt(++i2, pc.ay());
                        pstm.setInt(++i2, pc.fb());
                        pstm.setInt(++i2, pc.fs());
                        pstm.setInt(++i2, pc.ft());
                        pstm.setInt(++i2, pc.fp());
                        pstm.setInt(++i2, pc.fj());
                        pstm.setInt(++i2, pc.fa());
                        pstm.setString(++i2, pc.eZ());
                        pstm.setInt(++i2, pc.cE());
                        pstm.setInt(++i2, pc.aF());
                        pstm.setString(++i2, pc.aG());
                        pstm.setInt(++i2, pc.aH());
                        pstm.setInt(++i2, pc.bA());
                        pstm.setInt(++i2, pc.bB());
                        pstm.setInt(++i2, pc.bC());
                        pstm.setInt(++i2, pc.aD());
                        pstm.setInt(++i2, pc.aE());
                        pstm.setInt(++i2, pc.ca());
                        pstm.setInt(++i2, pc.bD());
                        pstm.setInt(++i2, pc.az());
                        pstm.setInt(++i2, pc.bE());
                        pstm.setInt(++i2, pc.bF());
                        pstm.setInt(++i2, pc.bG());
                        pstm.setInt(++i2, pc.bI());
                        pstm.setBoolean(++i2, pc.bJ());
                        pstm.setInt(++i2, pc.P());
                        pstm.setTimestamp(++i2, pc.bO());
                        pstm.setTimestamp(++i2, pc.bP());
                        pstm.setTimestamp(++i2, pc.cB());
                        pstm.setTimestamp(++i2, pc.bQ());
                        pstm.setTimestamp(++i2, pc.bR());
                        pstm.setInt(++i2, pc.cC());
                        pstm.setInt(++i2, pc.cJ());
                        pstm.setInt(++i2, pc.cP());
                        pstm.setInt(++i2, pc.cQ());
                        pstm.setInt(++i2, pc.cS());
                        pstm.setInt(++i2, pc.cT());
                        pstm.setInt(++i2, pc.cU());
                        pstm.setInt(++i2, pc.cV());
                        pstm.setInt(++i2, pc.cW());
                        pstm.setInt(++i2, pc.cX());
                        pstm.setInt(++i2, pc.cY());
                        pstm.setInt(++i2, pc.fr());
                        pstm.execute();
                        pstm.close();
                    }
                    catch (SQLException e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(pstm);
                        j.a(con);
                        break block8;
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
    }

    public void a(String accountName, String charName) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? AND char_name=?");
            pstm.setString(1, accountName);
            pstm.setString(2, charName);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                return;
            }
            pstm = con.prepareStatement("DELETE FROM character_buddys WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_buff WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_config WHERE object_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_equip WHERE id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_gift WHERE objid IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_items WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_quests_new WHERE objid IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_skills WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_teleport WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM character_warehouse_only WHERE char_objid IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM clan_members WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM soul_tower WHERE name=?");
            pstm.setString(1, charName);
            pstm.execute();
            pstm = con.prepareStatement("DELETE FROM characters WHERE char_name=?");
            pstm.setString(1, charName);
            pstm.execute();
            if (this.c.containsKey(charName)) {
                this.c.remove(charName);
            }
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        j.a(rs, pstm, con);
    }

    public u a(String charName) throws Exception {
        u pc;
        block6: {
            pc = null;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters WHERE char_name=?");
                    pstm.setString(1, charName);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        pc = new u();
                        pc.d(rs.getString("account_name"));
                        pc.cF(rs.getInt("objid"));
                        pc.e(rs.getString("char_name"));
                        pc.a(rs.getTimestamp("birthday"));
                        pc.ax(rs.getInt("HighLevel"));
                        pc.k(rs.getInt("Exp"));
                        pc.m(rs.getInt("MaxHp"));
                        pc.bx(Math.max(1, rs.getInt("CurHp")));
                        pc.X(false);
                        pc.cq(0);
                        pc.n(rs.getInt("MaxMp"));
                        pc.by(rs.getInt("CurMp"));
                        pc.o(rs.getInt("Str"));
                        pc.p(rs.getInt("Con"));
                        pc.q(rs.getInt("Dex"));
                        pc.r(rs.getInt("Cha"));
                        pc.s(rs.getInt("Intel"));
                        pc.t(rs.getInt("Wis"));
                        int classId = rs.getInt("Class");
                        pc.i(classId);
                        pc.cw(classId);
                        pc.aj(rs.getInt("Sex"));
                        pc.ad(rs.getInt("Type"));
                        int head = rs.getInt("Heading");
                        pc.ct(head > 7 ? 0 : head);
                        pc.cG(rs.getInt("locX"));
                        pc.cH(rs.getInt("locY"));
                        pc.cE(rs.getInt("MapID"));
                        pc.c_(rs.getInt("Food"));
                        pc.cr(rs.getInt("Lawful"));
                        pc.f(rs.getString("Title"));
                        pc.aV(rs.getInt("MasterID"));
                        pc.ah(rs.getInt("ClanID"));
                        pc.c(rs.getString("Clanname"));
                        pc.ai(rs.getInt("ClanRank"));
                        pc.ay(rs.getInt("BonusStatus"));
                        pc.az(rs.getInt("ElixirStatus"));
                        pc.aA(rs.getInt("ElfAttr"));
                        pc.af(rs.getInt("PKcount"));
                        pc.ag(rs.getInt("PkCountForElf"));
                        pc.aH(rs.getInt("ExpRes"));
                        pc.aB(rs.getInt("PartnerID"));
                        pc.ae(rs.getInt("AccessLevel"));
                        pc.aC(rs.getInt("OnlineStatus"));
                        pc.aD(rs.getInt("HomeTownID"));
                        pc.aE(rs.getInt("Contribution"));
                        pc.aF(rs.getInt("Pay"));
                        pc.aG(rs.getInt("HellTime"));
                        pc.i(rs.getBoolean("Banned"));
                        pc.A(rs.getInt("Karma"));
                        pc.b(rs.getTimestamp("LastPk"));
                        pc.c(rs.getTimestamp("LastPkForElf"));
                        pc.f(rs.getTimestamp("LogoutTime"));
                        pc.d(rs.getTimestamp("DeleteTime"));
                        pc.e(rs.getTimestamp("TamUseTime"));
                        pc.ao(rs.getInt("OriginalStr"));
                        pc.ap(rs.getInt("OriginalCon"));
                        pc.aq(rs.getInt("OriginalDex"));
                        pc.ar(rs.getInt("OriginalCha"));
                        pc.as(rs.getInt("OriginalInt"));
                        pc.at(rs.getInt("OriginalWis"));
                        pc.K(rs.getInt("EinhasadsBless"));
                        pc.aZ(rs.getInt("BookMarkSpace"));
                        pc.ba(rs.getInt("CharStoreSpace"));
                        pc.bc(rs.getInt("RuneOpenStatus"));
                        pc.bd(rs.getInt("WsaRecord"));
                        pc.be(rs.getInt("MapTime_1"));
                        pc.bf(rs.getInt("MapTime_2"));
                        pc.bg(rs.getInt("MapTime_3"));
                        pc.bh(rs.getInt("MapTime_4"));
                        pc.bi(rs.getInt("MapTime_5"));
                        pc.bj(rs.getInt("MapTime_6"));
                        pc.bk(rs.getInt("MapTime_7"));
                        rs.close();
                        pc.aa();
                        pc.cu(0);
                        pc.cv(0);
                        pc.b(false);
                        pc.bb().a();
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
        return pc;
    }

    public u b(String charName) throws Exception {
        u pc = null;
        try {
            pc = this.a(charName);
            b map = d.b().a(pc.fp());
            if (!map.b(pc.fs(), pc.ft())) {
                pc.cG(33087);
                pc.cH(33396);
                pc.cE(4);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return pc;
    }

    public void b() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET MapTime_1=21600,MapTime_2=7200,MapTime_3=21600,MapTime_4=21600,MapTime_5=7200,MapTime_6=14400,MapTime_7=14400");
                    pstm.execute();
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

    public void a(int objid, long addTimeMillis) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET TamUseTime=? WHERE objid=?");
                    pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis() + addTimeMillis));
                    pstm.setInt(2, objid);
                    pstm.execute();
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

    public void a(int objid) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET TamUseTime=? WHERE objid=?");
                    pstm.setTimestamp(1, null);
                    pstm.setInt(2, objid);
                    pstm.execute();
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

    private void d() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET OnlineStatus=0");
                    pstm.execute();
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

    public void c(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET OnlineStatus=? WHERE objid=?");
                    pstm.setInt(1, pc.bE());
                    pstm.setInt(2, pc.fr());
                    pstm.execute();
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

    public void b(int targetId) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET PartnerID=0 WHERE objid=?");
                    pstm.setInt(1, targetId);
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

    public void d(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET OriginalStr= ?, OriginalCon= ?, OriginalDex= ?, OriginalCha= ?, OriginalInt= ?, OriginalWis= ? WHERE objid=?");
                    pstm.setInt(1, pc.bf());
                    pstm.setInt(2, pc.bg());
                    pstm.setInt(3, pc.bh());
                    pstm.setInt(4, pc.bi());
                    pstm.setInt(5, pc.bj());
                    pstm.setInt(6, pc.bk());
                    pstm.setInt(7, pc.fr());
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

    public boolean c(String name) {
        boolean result;
        block5: {
            result = true;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT account_name FROM characters WHERE char_name=?");
                    pstm.setString(1, name);
                    rs = pstm.executeQuery();
                    result = rs.next();
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
        return result;
    }

    private void e() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM characters");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a cn2 = new a();
                        cn2.b = rs.getString("char_name");
                        cn2.a = rs.getInt("objid");
                        this.c.put(cn2.b, cn2);
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

    public a[] c() {
        return this.c.values().toArray(new a[this.c.size()]);
    }

    public class a {
        public int a;
        public String b;
    }
}

