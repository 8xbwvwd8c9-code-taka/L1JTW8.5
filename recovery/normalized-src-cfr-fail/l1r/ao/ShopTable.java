/*
 * Decompiled with CFR 0.152.
 */
package l1r.ao;

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
import l1r.ao.AccountTable;
import l1r.ao.CastleTable;
import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.NpcTable;
import l1r.ao.TownTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1TaxCalculator;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Account;
import l1r.bh.L1Castle;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Shop;
import l1r.bh.L1ShopItem;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class ShopTable {
    private static final Logger a = Logger.getLogger(ShopTable.class.getName());
    private static ShopTable b;
    private final Map<Integer, L1Shop> c = new HashMap<Integer, L1Shop>();

    public static ShopTable a() {
        if (b == null) {
            b = new ShopTable();
        }
        return b;
    }

    private ShopTable() {
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
        HashMap<Integer, L1Item> list = ItemTable.a().c();
        if (shopType == 1) {
            list = ItemTable.a().d();
        } else if (shopType == 0) {
            list = ItemTable.a().b();
        }
        ArrayList<L1ShopItem> sellingList = new ArrayList<L1ShopItem>();
        for (L1Item l1item : list.values()) {
            int[] nArray = itemType;
            int n = itemType.length;
            int n2 = 0;
            while (n2 < n) {
                int type = nArray[n2];
                if (l1item.aP() == type) {
                    L1ShopItem shop_item = new L1ShopItem(l1item.g(), 1, 1);
                    sellingList.add(shop_item);
                }
                ++n2;
            }
        }
        Collections.sort(sellingList, new Comparator<L1ShopItem>(){

            public int a(L1ShopItem d1, L1ShopItem d2) {
                return d1.a() - d2.a();
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((L1ShopItem)object, (L1ShopItem)object2);
            }
        });
        this.c.put(npcid, new L1Shop(npcid, sellingList, new ArrayList<L1ShopItem>()));
    }

    public void b() {
        for (L1Shop shop : this.c.values()) {
            int npcid = shop.a();
            L1Npc npc = NpcTable.a().a(npcid);
            if (npc == null) {
                System.out.println("npcid: " + npcid + " = null");
                continue;
            }
            String npcName = npc.c();
            List<L1ShopItem> list = shop.b();
            list.addAll(shop.c());
            for (L1ShopItem shopitem : list) {
                int itemid = shopitem.a();
                L1Item item = ItemTable.a().a(itemid);
                if (item == null) {
                    System.out.println("itemid: " + itemid + " = null");
                    continue;
                }
                String itemName = item.h();
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("UPDATE shop SET item_name=? WHERE item_id=?");
                    pstm.setString(1, itemName);
                    pstm.setInt(2, itemid);
                    pstm.execute();
                    pstm = con.prepareStatement("UPDATE shop SET npc_name=? WHERE npc_id=?");
                    pstm.setString(1, npcName);
                    pstm.setInt(2, npcid);
                    pstm.execute();
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                }
                SQLUtil.a(pstm);
                SQLUtil.a(con);
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
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("SELECT DISTINCT npc_id FROM shop");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        ids.add(rs.getInt("npc_id"));
                    }
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(rs, pstm, con);
                throw throwable;
            }
            SQLUtil.a(rs, pstm, con);
        }
        return ids;
    }

    private L1Shop a(int npcId, ResultSet rs) throws SQLException {
        ArrayList<L1ShopItem> sellingList = new ArrayList<L1ShopItem>();
        ArrayList<L1ShopItem> purchasingList = new ArrayList<L1ShopItem>();
        while (rs.next()) {
            L1ShopItem item;
            int itemId = rs.getInt("item_id");
            int sellingPrice = rs.getInt("selling_price");
            int purchasingPrice = rs.getInt("purchasing_price");
            int packCount = rs.getInt("pack_count");
            int n = packCount = packCount == 0 ? 1 : packCount;
            if (sellingPrice >= 0) {
                item = new L1ShopItem(itemId, sellingPrice, packCount);
                sellingList.add(item);
            }
            if (purchasingPrice < 0) continue;
            item = new L1ShopItem(itemId, purchasingPrice, packCount);
            purchasingList.add(item);
        }
        return new L1Shop(npcId, sellingList, purchasingList);
    }

    private void e() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("SELECT * FROM shop WHERE npc_id=? ORDER BY order_id");
                    for (int npcId : this.d()) {
                        pstm.setInt(1, npcId);
                        rs = pstm.executeQuery();
                        L1Shop shop = this.a(npcId, rs);
                        this.c.put(npcId, shop);
                        rs.close();
                    }
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(rs, pstm, con);
                throw throwable;
            }
            SQLUtil.a(rs, pstm, con);
        }
    }

    public L1Shop a(int npcId) {
        return this.c.get(npcId);
    }

    private L1ShopItem a(int itemid, List<L1ShopItem> list) {
        for (L1ShopItem shopitem : list) {
            if (shopitem.a() != itemid) continue;
            return shopitem;
        }
        return null;
    }

    public void a(L1PcInstance pc, ArrayList<int[]> orderList, L1NpcInstance npc, int coinID) {
        L1Shop shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        long totalPrice = 0L;
        for (int[] order : orderList) {
            int objid = order[0];
            int count = order[1];
            L1ItemInstance item = pc.j().e(objid);
            if (item == null) {
                pc.a(new S_ServerMessage(156));
                continue;
            }
            L1ShopItem shopItem = this.a(item.N(), shop.c());
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u6536\u8cfc\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + item.N());
                continue;
            }
            int true_count = item.E() - count >= 0 ? count : item.E();
            int price = (int)((double)(shopItem.c() * true_count) * Config.M / (double)shopItem.d());
            if (item.N() == 40309) {
                price = L1BugBearRace.a().a(item.fr()) * true_count;
            }
            totalPrice += (long)price;
            HistoryTable.a().d(pc, "\u8ce3\u7d66\u5546\u5e97", item, true_count);
        }
        if (totalPrice > 2000000000L) {
            pc.a(new S_SystemMessage("\u7e3d\u91d1\u984d\u7121\u6cd5\u8d85\u904e2000000000\u91d1\u5e63\u3002"));
            return;
        }
        if (totalPrice <= 0L) {
            pc.a(new S_SystemMessage("\u7e3d\u91d1\u984d" + totalPrice + "\u91d1\u5e63\uff0c\u4ea4\u6613\u5931\u6557\u3002"));
            return;
        }
        int invCount = pc.j().g(coinID);
        if (totalPrice + (long)invCount > 2000000000L) {
            pc.a(new S_SystemMessage("\u7e3d\u5171\u8ca9\u8ce3\u50f9\u683c\u7121\u6cd5\u8d85\u904e" + (2000000000 - invCount) + "\u91d1\u5e63\u3002"));
            return;
        }
        for (int[] order : orderList) {
            int objid = order[0];
            int count = order[1];
            pc.j().c(objid, count);
        }
        ItemTable.a(pc, coinID, (int)totalPrice, 0, npc.T());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(L1PcInstance pc, ArrayList<int[]> orderList, L1NpcInstance npc, int coinID) {
        L1Shop shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        L1TaxCalculator _taxCalc = new L1TaxCalculator(npc);
        long total_price = 0L;
        long total_price_withTax = 0L;
        int total_weight = 0;
        int pcInventoryCount = pc.j().c();
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            L1ShopItem shopItem = this.a(itemid, shop.b());
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u8ca9\u8ce3\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + itemid);
                continue;
            }
            int price = (int)((double)shopItem.c() * Config.L);
            total_price += (long)(price * count);
            total_price_withTax += (long)(_taxCalc.a(price) * count);
            total_weight += shopItem.b().l() * count * shopItem.d();
            L1Item temp = shopItem.b();
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
            pc.a(new S_ServerMessage(904, 2000000000));
            return;
        }
        if (!pc.j().g(coinID, (int)total_price_withTax)) {
            if (coinID == 40308) {
                pc.a(new S_ServerMessage(189));
            } else if (coinID == 640268) {
                pc.a(new S_ServerMessage(3429));
            } else {
                pc.a(new S_ServerMessage(2742));
            }
            return;
        }
        int currentWeight = pc.j().e() * 1000;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new S_ServerMessage(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new S_ServerMessage(263));
            return;
        }
        if (!pc.j().b(coinID, (int)total_price_withTax)) {
            pc.a(new S_ServerMessage(1752));
            return;
        }
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            L1ShopItem shopItem = this.a(itemid, shop.b());
            boolean isIdentified = true;
            int itemEnchant = 0;
            int itemCount = count * shopItem.d();
            if (npc.z() == 70068 || npc.z() == 70020) {
                isIdentified = false;
                int chance = Random.a(100) + 1;
                itemEnchant = -1;
            }
            ItemTable.a(pc, itemid, itemCount, itemEnchant, isIdentified, false);
        }
        if (coinID == 40308) {
            int town_id;
            int castleId = L1CastleLocation.a(npc);
            int castleTax = _taxCalc.b((int)total_price);
            if (castleId != 0 && castleTax > 0) {
                L1Castle castle;
                L1Castle count = castle = CastleTable.a().a(castleId);
                synchronized (count) {
                    if (castle.f() < 2000000000) {
                        castle.b(castle.f() + castleTax);
                        CastleTable.a().a(castle);
                    }
                }
            }
            if (!L1World.a().l() && (town_id = L1TownLocation.a((L1Character)npc)) >= 1 && town_id <= 10) {
                TownTable.a().a(town_id, (int)total_price);
            }
            int warTax = _taxCalc.d((int)total_price);
            L1Castle aden = CastleTable.a().a(7);
            L1Castle diad = CastleTable.a().a(8);
            if (warTax <= 0) {
                L1Castle l1Castle = aden;
                synchronized (l1Castle) {
                    if (aden.f() < 2000000000) {
                        aden.b(aden.f() + warTax / 2);
                        CastleTable.a().a(aden);
                    }
                }
                l1Castle = diad;
                synchronized (l1Castle) {
                    if (diad.f() < 2000000000) {
                        diad.b(diad.f() + warTax / 2);
                        CastleTable.a().a(diad);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(L1PcInstance pc, ArrayList<int[]> orderList, L1NpcInstance npc) {
        int town_id;
        L1Shop shop = this.c.get(npc.z());
        if (shop == null) {
            a.log(Level.SEVERE, "\u4e0d\u5b58\u5728\u7684\u5546\u5e97,npcid=" + npc.z());
            return;
        }
        L1TaxCalculator taxCalc = new L1TaxCalculator(npc);
        long total_price = 0L;
        int total_weight = 0;
        int pcInventoryCount = pc.j().c();
        for (int[] order : orderList) {
            int index = order[0];
            int count = order[1];
            L1ShopItem shopItem = shop.b().get(index);
            if (shopItem == null) {
                a.log(Level.SEVERE, "\u8ca9\u8ce3\u9053\u5177-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z());
                continue;
            }
            int price = (int)((double)shopItem.c() * Config.L);
            total_price += (long)(price * count);
            total_weight += shopItem.b().l() * count * shopItem.d();
            L1Item temp = shopItem.b();
            if (temp.aF() && !pc.j().f(temp.g())) {
                ++pcInventoryCount;
                continue;
            }
            ++pcInventoryCount;
        }
        if (total_price > 2000000000L) {
            pc.a(new S_ServerMessage(904, 2000000000));
            return;
        }
        if (!pc.j().g(40308, (int)total_price)) {
            pc.a(new S_ServerMessage(189));
            return;
        }
        int currentWeight = pc.j().e() * 1000;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new S_ServerMessage(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new S_ServerMessage(263));
            return;
        }
        if (!pc.j().b(40308, (int)total_price)) {
            pc.a(new S_ServerMessage(1752));
            return;
        }
        for (int[] order : orderList) {
            int index = order[0];
            int count = order[1];
            L1ShopItem shopItem = shop.b().get(index);
            L1ItemInstance item = ItemTable.a().b(shopItem.a());
            item.e(count * shopItem.d());
            item.a(true);
            item.a(shopItem.b());
            pc.j().d(item);
        }
        int castleId = L1CastleLocation.a(npc);
        int castleTax = taxCalc.b((int)total_price);
        if (castleId != 0 && castleTax > 0) {
            L1Castle castle;
            L1Castle count = castle = CastleTable.a().a(castleId);
            synchronized (count) {
                if (castle.f() < 2000000000) {
                    castle.b(castle.f() + castleTax);
                    CastleTable.a().a(castle);
                }
            }
        }
        if (!L1World.a().l() && (town_id = L1TownLocation.a((L1Character)npc)) >= 1 && town_id <= 10) {
            TownTable.a().a(town_id, (int)total_price);
        }
        int warTax = taxCalc.d((int)total_price);
        L1Castle aden = CastleTable.a().a(7);
        L1Castle diad = CastleTable.a().a(8);
        if (warTax <= 0) {
            L1Castle l1Castle = aden;
            synchronized (l1Castle) {
                if (aden.f() < 2000000000) {
                    aden.b(aden.f() + warTax / 2);
                    CastleTable.a().a(aden);
                }
            }
            l1Castle = diad;
            synchronized (l1Castle) {
                if (diad.f() < 2000000000) {
                    diad.b(diad.f() + warTax / 2);
                    CastleTable.a().a(diad);
                }
            }
        }
    }

    public void b(L1PcInstance pc, ArrayList<int[]> orderList, L1NpcInstance npc) {
        L1Shop shop = this.c.get(npc.z());
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
            L1ShopItem shopItem = this.a(itemid, shop.b());
            if (shopItem == null) {
                a.log(Level.SEVERE, "Tam-\u4e0d\u5b58\u5728\u7684shop_item!! npcid=" + npc.z() + "itemid=" + itemid);
                continue;
            }
            int price = (int)((double)shopItem.c() * Config.L);
            total_price += (long)(price * count);
            total_weight += shopItem.b().l() * count * shopItem.d();
            L1Item temp = shopItem.b();
            if (temp.aF() && !pc.j().f(temp.g())) {
                ++pcInventoryCount;
                continue;
            }
            ++pcInventoryCount;
        }
        L1Account account = pc.aK().e();
        if ((long)account.q() < total_price) {
            pc.a(new S_ServerMessage(3901));
            return;
        }
        int currentWeight = pc.j().e() * 1000;
        if ((double)(currentWeight + total_weight) > pc.K() * 1000.0) {
            pc.a(new S_ServerMessage(82));
            return;
        }
        if (pcInventoryCount > 180) {
            pc.a(new S_ServerMessage(263));
            return;
        }
        account.h(account.q() - (int)total_price);
        AccountTable.a().d(account);
        pc.a(new S_ProtoBuffers(450, account.q()));
        for (int[] order : orderList) {
            int itemid = order[0];
            int count = order[1];
            ItemTable.a(pc, itemid, count, 0, true, false);
        }
    }
}
