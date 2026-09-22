/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.a;
import ao.aa;
import ao.ah;
import ao.au;
import ao.bh;
import ao.g;
import ap.q;
import aq.al;
import aq.an;
import aq.aq;
import aq.e;
import aq.f;
import be.dc;
import be.ds;
import be.ei;
import bh.d;
import bh.l;
import bh.t;
import bh.u;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bc {
    private static final Logger a = Logger.getLogger(bc.class.getName());
    private static bc b;
    private final Map<Integer, t> c = new HashMap<Integer, t>();

    public static bc a() {
        if (b == null) {
            b = new bc();
        }
        return b;
    }

    private bc() {
        this.e();
        this.c();
    }

    private void c() {
        this.a(81002, 2, new int[]{13});
        this.a(81012, 2, new int[]{1});
        this.a(81013, 2, new int[]{8, 10});
        this.a(81014, 2, new int[]{7});
        this.a(81015, 2, new int[]{6});
        this.a(81016, 2, new int[]{3});
        this.a(81017, 2, new int[]{2});
        this.a(81018, 2, new int[]{4});
        this.a(81019, 2, new int[]{11});
        this.a(81020, 2, new int[]{9});
        this.a(81021, 2, new int[]{12});
        this.a(81031, 2, new int[]{5});
        this.a(81032, 2, new int[]{23});
        this.a(81033, 2, new int[]{15, 18, 16});
        this.a(81003, 1, new int[]{7});
        this.a(81004, 1, new int[]{1});
        this.a(81005, 1, new int[]{257});
        this.a(81006, 1, new int[]{5, 261});
        this.a(81007, 1, new int[]{2, 258});
        this.a(81008, 1, new int[]{260});
        this.a(81009, 1, new int[]{264});
        this.a(81010, 1, new int[]{6, 262});
        this.a(81011, 1, new int[]{3, 259, 9});
        this.a(81241, 1, new int[]{266});
        this.a(81242, 1, new int[]{8});
        this.a(81034, 0, new int[]{17, 22});
        this.a(81027, 0, new int[]{6, 23, 24, 25, 26, 27});
        this.a(81028, 0, new int[]{8, 28, 31, 32});
    }

    private void a(int npcid, int shopType, int[] itemType) {
        HashMap<Integer, bh.j> list = ah.a().c();
        if (shopType == 1) {
            list = ah.a().d();
        } else if (shopType == 0) {
            list = ah.a().b();
        }
        ArrayList<u> sellingList = new ArrayList<u>();
        for (bh.j l1item : list.values()) {
            int[] nArray = itemType;
            int n2 = itemType.length;
            int n3 = 0;
            while (n3 < n2) {
                int type = nArray[n3];
                if (l1item.aP() == type) {
                    u shop_item = new u(l1item.g(), 1, 1);
                    sellingList.add(shop_item);
                }
                ++n3;
            }
        }
        Collections.sort(sellingList, new Comparator<u>(){

            public int a(u d1, u d2) {
                return d1.a() - d2.a();
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((u)object, (u)object2);
            }
        });
        this.c.put(npcid, new t(npcid, sellingList, new ArrayList<u>()));
    }

    public void b() {
        for (t shop : this.c.values()) {
            int npcid = shop.a();
            l npc = au.a().a(npcid);
            if (npc == null) {
                System.out.println("npcid: " + npcid + " = null");
                continue;
            }
            String npcName = npc.c();
            List<u> list = shop.b();
            list.addAll(shop.c());
            for (u shopitem : list) {
                int itemid = shopitem.a();
                bh.j item = ah.a().a(itemid);
                if (item == null) {
                    System.out.println("itemid: " + itemid + " = null");
                    continue;
                }
                String itemName = item.h();
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE shop SET item_name=? WHERE item_id=?");
                    pstm.setString(1, itemName);
                    pstm.setInt(2, itemid);
                    pstm.execute();
                    pstm = con.prepareStatement("UPDATE shop SET npc_name=? WHERE npc_id=?");
                    pstm.setString(1, npcName);
                    pstm.setInt(2, npcid);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                j.a(pstm);
                j.a(con);
            }
        }
    }

    private List<Integer> d() {
        ArrayList<Integer> ids;
        block6: {
            ids = new ArrayList<Integer>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT DISTINCT npc_id FROM shop");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        ids.add(rs.getInt("npc_id"));
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
        return ids;
    }

    private t a(int npcId, ResultSet rs) throws SQLException {
        ArrayList<u> sellingList = new ArrayList<u>();
        ArrayList<u> purchasingList = new ArrayList<u>();
        while (rs.next()) {
            u item;
            int itemId = rs.getInt("item_id");
            int sellingPrice = rs.getInt("selling_price");
            int purchasingPrice = rs.getInt("purchasing_price");
            int packCount = rs.getInt("pack_count");
            int n2 = packCount = packCount == 0 ? 1 : packCount;
            if (sellingPrice >= 0) {
                item = new u(itemId, sellingPrice, packCount);
                sellingList.add(item);
            }
            if (purchasingPrice < 0) continue;
            item = new u(itemId, purchasingPrice, packCount);
            purchasingList.add(item);
        }
        return new t(npcId, sellingList, purchasingList);
    }

    private void e() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM shop WHERE npc_id=? ORDER BY order_id");
                    for (int npcId : this.d()) {
                        pstm.setInt(1, npcId);
                        rs = pstm.executeQuery();
                        t shop = this.a(npcId, rs);
                        this.c.put(npcId, shop);
                        rs.close();
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

    public t a(int npcId) {
        return this.c.get(npcId);
    }

    private u a(int itemid, List<u> list) {
        for (u shopitem : list) {
            if (shopitem.a() != itemid) continue;
            return shopitem;
        }
        return null;
    }

    public void a(ap.u pc, ArrayList<int[]> orderList, ap.t npc, int coinID) {
        t shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        long totalPrice = 0L;
        ArrayList<int[]> validatedOrderList = new ArrayList<int[]>();
        for (int[] order : orderList) {
            int objid = order[0];
            int count = order[1];
            if (count <= 0) {
                return;
            }
            q item = pc.j().e(objid);
            if (item == null) {
                pc.a(new ds(156));
                continue;
            }
            u shopItem = this.a(item.N(), shop.c());
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u6536\u8cfc\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + item.N());
                continue;
            }
            int true_count = Math.min(count, item.E());
            long linePrice = (long)((double)shopItem.c() * (double)true_count * l1j.server.a.M / (double)shopItem.d());
            if (item.N() == 40309) {
                linePrice = (long)as.a.a().a(item.fr()) * (long)true_count;
            }
            if (linePrice <= 0L || linePrice > 2000000000L || totalPrice > 2000000000L - linePrice) {
                pc.a(new ei("\u7e3d\u91d1\u984d\u7121\u6cd5\u8d85\u904e2000000000\u91d1\u5e63\u3002"));
                return;
            }
            totalPrice += linePrice;
            validatedOrderList.add(new int[]{objid, true_count});
            aa.a().d(pc, "\u8ce3\u7d66\u5546\u5e97", item, true_count);
        }
        if (totalPrice > 2000000000L) {
            pc.a(new ei("\u7e3d\u91d1\u984d\u7121\u6cd5\u8d85\u904e2000000000\u91d1\u5e63\u3002"));
            return;
        }
        if (totalPrice <= 0L) {
            pc.a(new ei("\u7e3d\u91d1\u984d" + totalPrice + "\u91d1\u5e63\uff0c\u4ea4\u6613\u5931\u6557\u3002"));
            return;
        }
        int invCount = pc.j().g(coinID);
        if (totalPrice + (long)invCount > 2000000000L) {
            pc.a(new ei("\u7e3d\u5171\u8ca9\u8ce3\u50f9\u683c\u7121\u6cd5\u8d85\u904e" + (2000000000 - invCount) + "\u91d1\u5e63\u3002"));
            return;
        }
        for (int[] order : validatedOrderList) {
            int objid = order[0];
            int count = order[1];
            pc.j().c(objid, count);
        }
        ah.a(pc, coinID, (int)totalPrice, 0, npc.T());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(ap.u pc, ArrayList<int[]> orderList, ap.t npc, int coinID) {
        t shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        al _taxCalc = new al(npc);
        long total_price = 0L;
        long total_price_withTax = 0L;
        long total_weight = 0L;
        int pcInventoryCount = pc.j().c();
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            if (count <= 0) {
                return;
            }
            u shopItem = this.a(itemid, shop.b());
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u8ca9\u8ce3\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + itemid);
                continue;
            }
            int price = (int)((double)shopItem.c() * l1j.server.a.L);
            long linePrice = (long)price * (long)count;
            long linePriceWithTax = (long)_taxCalc.a(price) * (long)count;
            long itemCount = (long)count * (long)shopItem.d();
            if (itemCount <= 0L || itemCount > 1500000000L) {
                return;
            }
            if (linePrice < 0L || linePriceWithTax < 0L || total_price > 2000000000L - linePrice || total_price_withTax > 2000000000L - linePriceWithTax) {
                pc.a(new ds(904, 2000000000));
                return;
            }
            total_price += linePrice;
            total_price_withTax += linePriceWithTax;
            long lineWeight = (long)shopItem.b().l() * itemCount;
            if (lineWeight < 0L || total_weight > Long.MAX_VALUE - lineWeight) {
                return;
            }
            total_weight += lineWeight;
            bh.j temp = shopItem.b();
            if (temp.aF() && !pc.j().f(temp.g())) {
                ++pcInventoryCount;
                continue;
            }
            ++pcInventoryCount;
        }
        if (coinID != 40308) {
            total_price_withTax = total_price;
        }
        if (total_price_withTax > 2000000000L) {
            pc.a(new ds(904, 2000000000));
            return;
        }
        if (!pc.j().g(coinID, (int)total_price_withTax)) {
            if (coinID == 40308) {
                pc.a(new ds(189));
            } else if (coinID == 640268) {
                pc.a(new ds(3429));
            } else {
                pc.a(new ds(2742));
            }
            return;
        }
        long currentWeight = (long)pc.j().e() * 1000L;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new ds(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new ds(263));
            return;
        }
        if (!pc.j().b(coinID, (int)total_price_withTax)) {
            pc.a(new ds(1752));
            return;
        }
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            u shopItem = this.a(itemid, shop.b());
            boolean isIdentified = true;
            int itemEnchant = 0;
            long itemCountLong = (long)count * (long)shopItem.d();
            if (itemCountLong <= 0L || itemCountLong > 1500000000L) {
                return;
            }
            int itemCount = (int)itemCountLong;
            if (npc.z() == 70068 || npc.z() == 70020) {
                isIdentified = false;
                int chance = i.a(100) + 1;
                itemEnchant = -1;
            }
            ah.a(pc, itemid, itemCount, itemEnchant, isIdentified, false);
        }
        if (coinID == 40308) {
            int town_id;
            int castleId = e.a(npc);
            int castleTax = _taxCalc.b((int)total_price);
            if (castleId != 0 && castleTax > 0) {
                d castle;
                d count = castle = g.a().a(castleId);
                synchronized (count) {
                    if (castle.f() < 2000000000) {
                        castle.b(castle.f() + castleTax);
                        g.a().a(castle);
                    }
                }
            }
            if (!aq.a().l() && (town_id = an.a((f)npc)) >= 1 && town_id <= 10) {
                bh.a().a(town_id, (int)total_price);
            }
            int warTax = _taxCalc.d((int)total_price);
            d aden = g.a().a(7);
            d diad = g.a().a(8);
            if (warTax > 0) {
                d d2 = aden;
                synchronized (d2) {
                    if (aden.f() < 2000000000) {
                        aden.b(aden.f() + warTax / 2);
                        g.a().a(aden);
                    }
                }
                d2 = diad;
                synchronized (d2) {
                    if (diad.f() < 2000000000) {
                        diad.b(diad.f() + warTax / 2);
                        g.a().a(diad);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(ap.u pc, ArrayList<int[]> orderList, ap.t npc) {
        int town_id;
        t shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        al taxCalc = new al(npc);
        long total_price = 0L;
        int total_weight = 0;
        int pcInventoryCount = pc.j().c();
        for (int[] order : orderList) {
            int index = order[0];
            int count = order[1];
            u shopItem = shop.b().get(index);
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u8ca9\u8ce3\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z());
                continue;
            }
            int price = (int)((double)shopItem.c() * l1j.server.a.L);
            total_price += (long)(price * count);
            total_weight += shopItem.b().l() * count * shopItem.d();
            bh.j temp = shopItem.b();
            if (temp.aF() && !pc.j().f(temp.g())) {
                ++pcInventoryCount;
                continue;
            }
            ++pcInventoryCount;
        }
        if (total_price > 2000000000L) {
            pc.a(new ds(904, 2000000000));
            return;
        }
        if (!pc.j().g(40308, (int)total_price)) {
            pc.a(new ds(189));
            return;
        }
        int currentWeight = pc.j().e() * 1000;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new ds(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new ds(263));
            return;
        }
        if (!pc.j().b(40308, (int)total_price)) {
            pc.a(new ds(1752));
            return;
        }
        for (int[] order : orderList) {
            int index = order[0];
            int count = order[1];
            u shopItem = shop.b().get(index);
            q item = ah.a().b(shopItem.a());
            item.e(count * shopItem.d());
            item.a(true);
            item.a(shopItem.b());
            pc.j().d(item);
        }
        int castleId = e.a(npc);
        int castleTax = taxCalc.b((int)total_price);
        if (castleId != 0 && castleTax > 0) {
            d castle;
            d count = castle = g.a().a(castleId);
            synchronized (count) {
                if (castle.f() < 2000000000) {
                    castle.b(castle.f() + castleTax);
                    g.a().a(castle);
                }
            }
        }
        if (!aq.a().l() && (town_id = an.a((f)npc)) >= 1 && town_id <= 10) {
            bh.a().a(town_id, (int)total_price);
        }
        int warTax = taxCalc.d((int)total_price);
        d aden = g.a().a(7);
        d diad = g.a().a(8);
        if (warTax > 0) {
            d d2 = aden;
            synchronized (d2) {
                if (aden.f() < 2000000000) {
                    aden.b(aden.f() + warTax / 2);
                    g.a().a(aden);
                }
            }
            d2 = diad;
            synchronized (d2) {
                if (diad.f() < 2000000000) {
                    diad.b(diad.f() + warTax / 2);
                    g.a().a(diad);
                }
            }
        }
    }

    public void b(ap.u pc, ArrayList<int[]> orderList, ap.t npc) {
        t shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        long total_price = 0L;
        int total_weight = 0;
        int pcInventoryCount = pc.j().c();
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            u shopItem = this.a(itemid, shop.b());
            if (shopItem == null) {
                a.log(Level.SEVERE, "Tam-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + itemid);
                continue;
            }
            int price = (int)((double)shopItem.c() * l1j.server.a.L);
            total_price += (long)(price * count);
            total_weight += shopItem.b().l() * count * shopItem.d();
            bh.j temp = shopItem.b();
            if (temp.aF() && !pc.j().f(temp.g())) {
                ++pcInventoryCount;
                continue;
            }
            ++pcInventoryCount;
        }
        bh.a account = pc.aK().e();
        if ((long)account.q() < total_price) {
            pc.a(new ds(3901));
            return;
        }
        int currentWeight = pc.j().e() * 1000;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new ds(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new ds(263));
            return;
        }
        account.h(account.q() - (int)total_price);
        ao.a.a().d(account);
        pc.a(new dc(450, account.q()));
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            ah.a(pc, itemid, count, 0, true, false);
        }
    }
}

