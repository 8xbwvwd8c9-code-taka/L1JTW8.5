/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bh.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ab {
    private static final Logger a = Logger.getLogger(ab.class.getName());
    private static ab b;
    private final HashMap<Integer, i> c;

    public static ab a() {
        if (b == null) {
            b = new ab();
        }
        return b;
    }

    public ab() {
        block6: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM house ORDER BY house_id");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        i house = new i();
                        house.a(rs.getInt("house_id"));
                        house.a(rs.getString("house_name"));
                        house.b(rs.getInt("house_area"));
                        house.b(rs.getString("location"));
                        house.c(rs.getInt("keeper_id"));
                        house.a(rs.getBoolean("is_on_sale"));
                        house.b(rs.getBoolean("is_purchase_basement"));
                        house.a(rs.getTimestamp("tax_deadline"));
                        house.b(rs.getTimestamp("deadline"));
                        house.d(rs.getInt("price"));
                        house.c(rs.getString("old_owner"));
                        house.e(rs.getInt("old_owner_id"));
                        house.d(rs.getString("bidder"));
                        house.f(rs.getInt("bidder_id"));
                        this.c.put(house.b(), house);
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

    public i a(int houseId) {
        return this.c.get(houseId);
    }

    public boolean a(i house) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE house SET house_name=?, house_area=?, location=?, keeper_id=?, is_on_sale=?, is_purchase_basement=?, tax_deadline=?,  deadline=?, price=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?");
            pstm.setString(1, house.c());
            pstm.setInt(2, house.d());
            pstm.setString(3, house.e());
            pstm.setInt(4, house.f());
            pstm.setBoolean(5, house.g());
            pstm.setBoolean(6, house.h());
            pstm.setTimestamp(7, house.i());
            pstm.setTimestamp(8, house.j());
            pstm.setInt(9, house.k());
            pstm.setString(10, house.l());
            pstm.setInt(11, house.m());
            pstm.setString(12, house.n());
            pstm.setInt(13, house.o());
            pstm.setInt(14, house.b());
            pstm.execute();
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

    public static List<Integer> b() {
        ArrayList<Integer> houseIdList;
        block6: {
            houseIdList = new ArrayList<Integer>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT house_id FROM house ORDER BY house_id");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int houseId = rs.getInt("house_id");
                        houseIdList.add(houseId);
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
        return houseIdList;
    }

    public HashMap<Integer, i> c() {
        return this.c;
    }
}

