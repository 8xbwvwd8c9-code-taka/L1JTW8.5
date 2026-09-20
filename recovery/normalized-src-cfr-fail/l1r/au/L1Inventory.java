/*
 * Decompiled with CFR 0.152.
 */
package l1r.au;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ai.IdFactory;
import l1r.ao.FurnitureSpawnTable;
import l1r.ao.ItemTable;
import l1r.ao.LetterTable;
import l1r.ao.PetTable;
import l1r.ap.L1FurnitureInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.au.L1CharInventory;
import l1r.au.L1ClanInventory;
import l1r.au.L1PcInventory;
import l1r.bh.L1Item;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1Inventory
extends L1Object {
    protected CopyOnWriteArrayList<L1ItemInstance> a = new CopyOnWriteArrayList();
    public static final int b = 2000000000;
    private static final int g = 1500000000;
    private static final int h = 1500;
    public static final int c = 0;
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 3;

    public int c() {
        return this.a.size();
    }

    public List<L1ItemInstance> d() {
        return this.a;
    }

    public int e() {
        int weight = 0;
        for (L1ItemInstance item : this.a) {
            weight += item.p();
        }
        return weight;
    }

    public int a(L1ItemInstance item, int count) {
        if (item == null) {
            return -1;
        }
        if (item.E() <= 0 || count <= 0) {
            return -1;
        }
        if (this.c() > Config.ap || this.c() == Config.ap && (!item.d() || !this.f(item.N()))) {
            return 1;
        }
        int weight = this.e() + item.a().l() * count / 1000 + 1;
        if (weight < 0 || item.a().l() * count / 1000 < 0) {
            return 2;
        }
        if ((double)weight > 1500.0 * Config.K) {
            return 2;
        }
        L1ItemInstance itemExist = this.b(item.N());
        if (itemExist != null && itemExist.E() + count > 2000000000) {
            return 3;
        }
        return 0;
    }

    public int a(L1PcInstance pc, L1ItemInstance item, int count) {
        if (item == null) {
            return -1;
        }
        if (item.E() <= 0 || count <= 0) {
            return -1;
        }
        int maxSize = 100;
        maxSize = this instanceof L1ClanInventory ? Config.ar : (this instanceof L1CharInventory ? pc.cJ() : Config.aq);
        if (this.c() > maxSize || this.c() == maxSize && (!item.d() || !this.f(item.N()))) {
            return 1;
        }
        return 0;
    }

    public synchronized L1ItemInstance a(int id, int count) {
        if (count <= 0) {
            return null;
        }
        L1Item temp = ItemTable.a().a(id);
        if (temp == null) {
            return null;
        }
        if (id == 40312) {
            L1ItemInstance item = new L1ItemInstance(temp, count);
            if (this.c(id) == null) {
                item.cF(IdFactory.a().d());
                L1World.a().a(item);
            }
            return this.d(item);
        }
        if (temp.aF()) {
            L1ItemInstance item = new L1ItemInstance(temp, count);
            if (this.b(id) == null) {
                item.cF(IdFactory.a().d());
                L1World.a().a(item);
            }
            return this.d(item);
        }
        L1ItemInstance result = null;
        int i = 0;
        while (i < count) {
            L1ItemInstance item = new L1ItemInstance(temp, 1);
            item.cF(IdFactory.a().d());
            L1World.a().a(item);
            this.d(item);
            result = item;
            ++i;
        }
        return result;
    }

    public synchronized L1ItemInstance d(L1ItemInstance item) {
        L1ItemInstance findItem;
        if (item.E() <= 0) {
            return null;
        }
        int itemId = item.N();
        if (item.d() && (findItem = this.d(itemId, item.F())) != null && findItem.F() == item.F()) {
            findItem.e(findItem.E() + item.E());
            this.b(findItem);
            return findItem;
        }
        item.cG(this.fs());
        item.cH(this.ft());
        item.cE(this.fp());
        int chargeCount = item.a().aM();
        if (itemId == 41401) {
            chargeCount -= Random.a(5);
        } else if (itemId == 20383) {
            chargeCount = 50;
        }
        item.g(chargeCount);
        if (item.f() && item.a().aP() == 2) {
            item.j(item.a().d());
        } else if (item.N() != 40312) {
            item.j(item.a().T());
        }
        item.n();
        this.a.add(item);
        this.a(item);
        return item;
    }

    public synchronized L1ItemInstance e(L1ItemInstance item) {
        L1ItemInstance findItem;
        if (item.N() == 40312 && (findItem = this.c(item.M())) != null) {
            findItem.e(findItem.E() + item.E());
            this.b(findItem);
            return findItem;
        }
        if (item.d() && (findItem = this.e(item.N(), item.F())) != null && findItem.F() == item.F()) {
            int countLimit = Math.max(1500000000 - findItem.E(), 0);
            if (item.E() <= countLimit) {
                findItem.e(findItem.E() + item.E());
                this.b(findItem);
                return findItem;
            }
            findItem.e(1500000000);
            this.b(findItem);
            item.e(item.E() - countLimit);
        }
        if (item.bb() != null) {
            Timestamp current = new Timestamp(System.currentTimeMillis());
            if (item.bb().before(current)) {
                int enchant;
                if (item.N() >= 21246 && item.N() <= 21251) {
                    enchant = item.G();
                    int bless = item.F();
                    boolean isIdentified = item.C();
                    item = ItemTable.a().b(20085);
                    item.a(enchant);
                    item.a(isIdentified);
                    item.f(bless);
                } else if (item.N() >= 21252 && item.N() <= 21257) {
                    enchant = item.G();
                    int bless = item.F();
                    boolean isIdentified = item.C();
                    item = ItemTable.a().b(20084);
                    item.a(enchant);
                    item.a(isIdentified);
                    item.f(bless);
                } else if (item.N() >= 21261 && item.N() <= 21300) {
                    item.b((Timestamp)null);
                } else {
                    this.c(item);
                    return null;
                }
            }
        }
        item.cG(this.fs());
        item.cH(this.ft());
        item.cE(this.fp());
        this.a.add(item);
        this.a(item);
        return item;
    }

    public boolean b(int itemid, int count) {
        if (count <= 0) {
            return false;
        }
        if (ItemTable.a().a(itemid).aF()) {
            L1ItemInstance item = this.b(itemid);
            if (item != null && item.E() >= count) {
                this.b(item, count);
                return true;
            }
        } else {
            L1ItemInstance[] itemList = this.d(itemid);
            if (itemList.length == count) {
                int i = 0;
                while (i < count) {
                    this.b(itemList[i], 1);
                    ++i;
                }
                return true;
            }
            if (itemList.length > count) {
                L1R_a dc = new L1R_a();
                Arrays.sort(itemList, dc);
                int i = 0;
                while (i < count) {
                    this.b(itemList[i], 1);
                    ++i;
                }
                return true;
            }
        }
        return false;
    }

    public void a(int itemid) {
        for (L1ItemInstance item : this.a) {
            if (item.N() != itemid) continue;
            this.f(item);
        }
    }

    public int c(int objectId, int count) {
        L1ItemInstance item = this.e(objectId);
        return this.b(item, count);
    }

    public int f(L1ItemInstance item) {
        return this.b(item, item.E());
    }

    public int b(L1ItemInstance item, int count) {
        if (item == null) {
            return 0;
        }
        if (item.E() <= 0 || count <= 0) {
            return 0;
        }
        if (item.E() < count) {
            count = item.E();
        }
        if (item.E() == count) {
            int itemId = item.N();
            if (itemId == 40314 || itemId == 40316) {
                PetTable.a().a(item.fr());
            } else if (itemId >= 49016 && itemId <= 49025) {
                LetterTable.a().a(item.fr());
            } else if (itemId >= 41383 && itemId <= 41400) {
                for (L1Object l1object : L1World.a().b()) {
                    L1FurnitureInstance furniture;
                    if (!(l1object instanceof L1FurnitureInstance) || (furniture = (L1FurnitureInstance)l1object).f() != item.fr()) continue;
                    FurnitureSpawnTable.a().b(furniture);
                }
            } else if (item.N() == 40309) {
                L1BugBearRace.a().b(item.fr());
            }
            if (this instanceof L1PcInventory && ((L1PcInventory)this).k() == item.fr()) {
                ((L1PcInventory)this).k(null);
            }
            this.c(item);
            L1World.a().b(item);
        } else {
            item.e(item.E() - count);
            this.b(item);
        }
        return count;
    }

    public void c(L1ItemInstance item) {
        this.a.remove(item);
    }

    public synchronized L1ItemInstance a(int objectId, int count, L1Inventory targetInventory) {
        L1ItemInstance item = this.e(objectId);
        return this.a(item, count, targetInventory);
    }

    public synchronized L1ItemInstance a(L1ItemInstance item, int count, L1Inventory targetInventory) {
        L1ItemInstance carryItem;
        if (item == null) {
            return null;
        }
        if (item.E() <= 0 || count <= 0) {
            return null;
        }
        if (item.D()) {
            return null;
        }
        if (!this.a(item.N(), count, item.F())) {
            return null;
        }
        if (item.E() <= count) {
            this.c(item);
            carryItem = item;
            if (this instanceof L1PcInventory && ((L1PcInventory)this).k() == item.fr()) {
                ((L1PcInventory)this).k(null);
            }
        } else {
            item.e(item.E() - count);
            this.b(item);
            carryItem = ItemTable.a().b(item.N());
            carryItem.e(count);
            carryItem.a(item.G());
            carryItem.a(item.C());
            carryItem.b(item.H());
            carryItem.g(item.I());
            carryItem.j(item.M());
            carryItem.a(item.J());
            carryItem.f(item.F());
        }
        return targetInventory.e(carryItem);
    }

    public L1ItemInstance g(L1ItemInstance item) {
        return this.c(item, 1);
    }

    public L1ItemInstance c(L1ItemInstance item, int count) {
        int currentDurability = item.H();
        if (currentDurability == 0 && item.f() || currentDurability < 0) {
            item.b(0);
            return null;
        }
        int durability = currentDurability + count;
        int maxDurability = item.G() + 5;
        if (durability > maxDurability) {
            durability = maxDurability;
        }
        if (currentDurability < durability) {
            item.b(durability);
        }
        this.b(item);
        return item;
    }

    public L1ItemInstance h(L1ItemInstance item) {
        if (item == null) {
            return null;
        }
        int durability = item.H();
        if (durability == 0 && !item.f() || durability < 0) {
            item.b(0);
            return null;
        }
        item.b(durability - 1);
        this.b(item);
        return item;
    }

    public L1ItemInstance b(int id) {
        for (L1ItemInstance item : this.a) {
            if (item.N() != id) continue;
            return item;
        }
        return null;
    }

    public L1ItemInstance d(int id, int bless) {
        for (L1ItemInstance item : this.a) {
            if (item.N() != id || bless != 3 && item.F() != bless) continue;
            return item;
        }
        return null;
    }

    public L1ItemInstance e(int id, int bless) {
        for (L1ItemInstance item : this.a) {
            if (item.E() >= 1500000000 || item.N() != id || item.F() != bless) continue;
            return item;
        }
        return null;
    }

    public L1ItemInstance c(int id) {
        for (L1ItemInstance item : this.a) {
            if (item.M() != id) continue;
            return item;
        }
        return null;
    }

    public L1ItemInstance[] d(int id) {
        return this.f(id, 3);
    }

    public L1ItemInstance[] f(int id, int bless) {
        ArrayList<L1ItemInstance> itemList = new ArrayList<L1ItemInstance>();
        for (L1ItemInstance item : this.a) {
            if (item.N() != id || bless != 3 && item.F() != bless) continue;
            itemList.add(item);
        }
        return itemList.toArray(new L1ItemInstance[itemList.size()]);
    }

    private L1ItemInstance[] h(int id) {
        ArrayList<L1ItemInstance> itemList = new ArrayList<L1ItemInstance>();
        for (L1ItemInstance item : this.a) {
            if (item.N() != id || item.D()) continue;
            itemList.add(item);
        }
        return itemList.toArray(new L1ItemInstance[itemList.size()]);
    }

    public L1ItemInstance e(int objectId) {
        if (objectId == 0) {
            return null;
        }
        for (L1ItemInstance itemObject : this.a) {
            L1ItemInstance item = itemObject;
            if (item.fr() != objectId) continue;
            return item;
        }
        return null;
    }

    public boolean f(int itemid) {
        return this.a(itemid, 1, 3);
    }

    public boolean g(int itemid, int count) {
        return this.a(itemid, count, 3);
    }

    public boolean a(int itemid, int count, int bless) {
        L1ItemInstance[] itemList;
        L1ItemInstance item;
        if (count == 0) {
            return true;
        }
        return ItemTable.a().a(itemid).aF() ? (item = this.d(itemid, bless)) != null && item.E() >= count : (itemList = this.f(itemid, bless)).length >= count;
    }

    public boolean h(int id, int count) {
        if (count == 0) {
            return true;
        }
        return count <= this.g(id);
    }

    public boolean a(int id, int enchant, int count, int bless) {
        int num = 0;
        for (L1ItemInstance item : this.a) {
            if (item.D() || bless < 3 && item.F() != bless || item.N() != id || item.G() != enchant) continue;
            if (item.d()) {
                return item.E() >= count;
            }
            if (++num != count) continue;
            return true;
        }
        return false;
    }

    public boolean b(int id, int enchant, int count, int bless) {
        int num = 0;
        for (L1ItemInstance item : this.a) {
            if (item.D() || bless < 3 && item.F() != bless || item.N() != id || item.G() != enchant) continue;
            num = item.d() ? (num += this.b(item, count)) : (num += this.f(item));
            if (num != count) continue;
            return true;
        }
        return false;
    }

    public boolean a(int[] ids) {
        int len = ids.length;
        int[] counts = new int[len];
        int i = 0;
        while (i < len) {
            counts[i] = 1;
            ++i;
        }
        return this.a(ids, counts);
    }

    public boolean a(int[] ids, int[] counts) {
        int i = 0;
        while (i < ids.length) {
            if (!this.a(ids[i], counts[i], 3)) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public int g(int id) {
        if (ItemTable.a().a(id).aF()) {
            L1ItemInstance item = this.b(id);
            if (item != null) {
                return item.E();
            }
        } else {
            return this.h(id).length;
        }
        return 0;
    }

    public void f() {
        Collections.shuffle(this.a);
    }

    public void g() {
        Iterator<L1ItemInstance> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            L1ItemInstance itemObject;
            L1ItemInstance item = itemObject = iterator.next();
            L1World.a().b(item);
        }
        this.a.clear();
    }

    public L1ItemInstance a(String nameId) {
        for (L1ItemInstance item : this.a) {
            if (!nameId.equals(item.a().j())) continue;
            return item;
        }
        return null;
    }

    public void a() {
    }

    public void a(L1ItemInstance item) {
    }

    public void b(L1ItemInstance item) {
    }

    private class L1R_a<T>
    implements Comparator<L1ItemInstance> {
        private L1R_a() {
        }

        public int a(L1ItemInstance item1, L1ItemInstance item2) {
            return item1.G() - item2.G();
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.a((L1ItemInstance)object, (L1ItemInstance)object2);
        }
    }
}
