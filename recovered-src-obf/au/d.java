/*
 * Decompiled with CFR 0.152.
 */
package au;

import ao.ah;
import ap.q;
import ap.u;
import aq.aq;
import au.f;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class d
extends f {
    private static final Logger g = Logger.getLogger(d.class.getName());
    private final u h;

    public d(u owner) {
        this.h = owner;
    }

    @Override
    public void a() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_elf_warehouse WHERE account_name = ?");
                    pstm.setString(1, this.h.bc());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int objectId = rs.getInt("id");
                        bh.j itemTemplate = ah.a().a(rs.getInt("item_id"));
                        int count = rs.getInt("count");
                        q item = new q(itemTemplate, count);
                        item.cF(objectId);
                        item.l(rs.getInt("super_enchant_field_1"));
                        item.m(rs.getInt("super_enchant_field_2"));
                        item.n(rs.getInt("super_enchant_field_3"));
                        item.o(rs.getInt("super_enchant_field_4"));
                        item.a(rs.getInt("enchantlvl"));
                        item.n();
                        item.b(false);
                        item.a(rs.getBoolean("is_id"));
                        item.b(rs.getInt("durability"));
                        item.g(rs.getInt("charge_count"));
                        item.j(rs.getInt("temp_value"));
                        item.a(rs.getTimestamp("last_used"));
                        item.f(rs.getInt("bless"));
                        item.h(rs.getInt("attr_enchant_kind"));
                        item.i(rs.getInt("attr_enchant_level"));
                        item.b(rs.getTimestamp("limit_time"));
                        this.a.add(item);
                        aq.a().a(item);
                    }
                }
                catch (SQLException e2) {
                    g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    @Override
    public void a(q item) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_elf_warehouse SET id = ?, account_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=?,limit_time=?");
                    pstm.setInt(1, item.fr());
                    pstm.setString(2, this.h.bc());
                    pstm.setInt(3, item.N());
                    pstm.setString(4, item.b());
                    pstm.setInt(5, item.E());
                    pstm.setInt(6, item.G());
                    pstm.setInt(7, item.C() ? 1 : 0);
                    pstm.setInt(8, item.H());
                    pstm.setInt(9, item.I());
                    pstm.setInt(10, item.M());
                    pstm.setTimestamp(11, item.J());
                    pstm.setInt(12, item.F());
                    pstm.setInt(13, item.K());
                    pstm.setInt(14, item.L());
                    pstm.setInt(15, item.X());
                    pstm.setInt(16, item.Y());
                    pstm.setInt(17, item.Z());
                    pstm.setInt(18, item.aa());
                    pstm.setTimestamp(19, item.bb());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    @Override
    public void b(q item) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE character_elf_warehouse SET count = ? WHERE id = ?");
                    pstm.setInt(1, item.E());
                    pstm.setInt(2, item.fr());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    @Override
    public void c(q item) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_elf_warehouse WHERE id = ?");
                    pstm.setInt(1, item.fr());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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
        this.a.remove(this.a.indexOf(item));
    }
}

