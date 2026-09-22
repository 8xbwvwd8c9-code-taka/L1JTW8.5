/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.q;
import aq.k;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class s {
    private static final Logger a = Logger.getLogger(s.class.getName());
    private static s b;
    private final HashMap<Integer, k> c = new HashMap();

    public static s a() {
        if (b == null) {
            b = new s();
        }
        return b;
    }

    private s() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM craft");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String note = rs.getString("note");
                k craft = new k(id);
                String craft_itemid = rs.getString("craft_itemid");
                String craft_count = rs.getString("craft_count");
                String craft_enchant = rs.getString("craft_enchant");
                String[] craft_itemID_List = craft_itemid.split(",");
                String[] craft_itemCount_List = craft_count.split(",");
                String[] craft_itemEnchant_List = craft_enchant.split(",");
                int i2 = 0;
                while (i2 < craft_itemID_List.length) {
                    try {
                        if (craft_itemID_List[i2].trim().length() > 0) {
                            int craft_itemID = Integer.parseInt(craft_itemID_List[i2]);
                            int craft_itemCount = Integer.parseInt(craft_itemCount_List[i2]);
                            int craft_itemEnchant = Integer.parseInt(craft_itemEnchant_List[i2]);
                            craft.a(craft_itemID, craft_itemCount, craft_itemEnchant);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ae2) {
                        System.out.println("Craft table [" + note + "] errer : check craft item");
                    }
                    ++i2;
                }
                String material = rs.getString("material");
                String material_count = rs.getString("material_count");
                String material_enchant = rs.getString("material_enchant");
                String material_bless = rs.getString("material_bless");
                String[] materialID_List = material.split(",");
                String[] materialCount_List = material_count.split(",");
                String[] materialEnchant_List = material_enchant.split(",");
                String[] materialBless_List = material_bless.split(",");
                int i3 = 0;
                while (i3 < materialID_List.length) {
                    try {
                        if (materialID_List[i3].trim().length() > 0) {
                            int materialID = Integer.parseInt(materialID_List[i3]);
                            int materialCount = Integer.parseInt(materialCount_List[i3]);
                            int materialEnchant = Integer.parseInt(materialEnchant_List[i3]);
                            int materialBless = Integer.parseInt(materialBless_List[i3]);
                            craft.a(materialID, materialCount, materialEnchant, materialBless);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ae3) {
                        System.out.println("Craft table [" + note + "] errer : check material");
                    }
                    ++i3;
                }
                craft.b(rs.getInt("min_level"), rs.getInt("max_level"));
                craft.c(rs.getInt("min_lawful"), rs.getInt("max_lawful"));
                craft.d(rs.getInt("min_karma"), rs.getInt("max_karma"));
                craft.e(rs.getInt("max_count"));
                craft.d(rs.getInt("change"));
                craft.c(rs.getInt("add_chance_itemid"));
                craft.a(rs.getInt("fail_itemid"), rs.getInt("fail_item_count"));
                craft.a(rs.getInt("perfect_chance"));
                craft.b(rs.getInt("craft_nameid"));
                if (this.c.containsKey(craft.a())) {
                    System.out.println("CraftListTable : craft ID = " + craft.a() + " repeat!!");
                    continue;
                }
                this.c.put(craft.a(), craft);
            }
            j.a(rs, pstm, con);
            rs = null;
            pstm = null;
            con = null;
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM craft_exchange");
            rs = pstm.executeQuery();
            block9: while (rs.next()) {
                int craft_id = rs.getInt("craft_id");
                int material_itemid = rs.getInt("material_itemid");
                int exchange_itemid = rs.getInt("exchange_itemid");
                int exchange_count = rs.getInt("exchange_count");
                int exchange_enchant = rs.getInt("exchange_enchant");
                int exchange_bless = rs.getInt("exchange_bless");
                if (!this.c.containsKey(craft_id)) {
                    System.out.println("craft_exchange Table: craftID" + craft_id + " is not exist");
                    continue;
                }
                k craft = this.c.get(craft_id);
                for (q item : craft.g().values()) {
                    if (item.N() != material_itemid) continue;
                    craft.a(material_itemid, exchange_itemid, exchange_count, exchange_enchant, exchange_bless);
                    continue block9;
                }
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        j.a(rs, pstm, con);
    }

    public ArrayList<k> b() {
        ArrayList<k> array = new ArrayList<k>();
        for (k craft : this.c.values()) {
            array.add(craft);
        }
        return array;
    }

    public k a(int id) {
        return this.c.get(id);
    }
}

