/*
 * Decompiled with CFR 0.152.
 */
package au;

import ai.d;
import ao.ah;
import ao.ai;
import ao.aw;
import ao.y;
import ap.l;
import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import au.b;
import au.c;
import au.g;
import bh.j;
import bi.i;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class f
extends aa {
    protected CopyOnWriteArrayList<q> a = new CopyOnWriteArrayList();
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

    public List<q> d() {
        return this.a;
    }

    public int e() {
        int weight = 0;
        for (q item : this.a) {
            weight += item.p();
        }
        return weight;
    }

    public int a(q item, int count) {
        if (item == null) {
            return -1;
        }
        if (item.E() <= 0 || count <= 0) {
            return -1;
        }
        if (this.c() > l1j.server.a.ap || this.c() == l1j.server.a.ap && (!item.d() || !this.f(item.N()))) {
            return 1;
        }
        int weight = this.e() + item.a().l() * count / 1000 + 1;
        if (weight < 0 || item.a().l() * count / 1000 < 0) {
            return 2;
        }
        if ((double)weight > 1500.0 * l1j.server.a.K) {
            return 2;
        }
        q itemExist = this.b(item.N());
        if (itemExist != null && itemExist.E() + count > 2000000000) {
            return 3;
        }
        return 0;
    }

    public int a(u pc, q item, int count) {
        if (item == null) {
            return -1;
        }
        if (item.E() <= 0 || count <= 0) {
            return -1;
        }
        int maxSize = 100;
        maxSize = this instanceof c ? l1j.server.a.ar : (this instanceof b ? pc.cJ() : l1j.server.a.aq);
        if (this.c() > maxSize || this.c() == maxSize && (!item.d() || !this.f(item.N()))) {
            return 1;
        }
        return 0;
    }

    public synchronized q a(int id, int count) {
        if (count <= 0) {
            return null;
        }
        j temp = ah.a().a(id);
        if (temp == null) {
            return null;
        }
        if (id == 40312) {
            q item = new q(temp, count);
            if (this.c(id) == null) {
                item.cF(ai.d.a().d());
                aq.a().a(item);
            }
            return this.d(item);
        }
        if (temp.aF()) {
            q item = new q(temp, count);
            if (this.b(id) == null) {
                item.cF(ai.d.a().d());
                aq.a().a(item);
            }
            return this.d(item);
        }
        q result = null;
        int i2 = 0;
        while (i2 < count) {
            q item = new q(temp, 1);
            item.cF(ai.d.a().d());
            aq.a().a(item);
            this.d(item);
            result = item;
            ++i2;
        }
        return result;
    }

    public synchronized q d(q item) {
        q findItem;
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
            chargeCount -= i.a(5);
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

    public synchronized q e(q item) {
        q findItem;
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
                    item = ah.a().b(20085);
                    item.a(enchant);
                    item.a(isIdentified);
                    item.f(bless);
                } else if (item.N() >= 21252 && item.N() <= 21257) {
                    enchant = item.G();
                    int bless = item.F();
                    boolean isIdentified = item.C();
                    item = ah.a().b(20084);
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
        if (ah.a().a(itemid).aF()) {
            q item = this.b(itemid);
            if (item != null && item.E() >= count) {
                this.b(item, count);
                return true;
            }
        } else {
            q[] itemList = this.d(itemid);
            if (itemList.length == count) {
                int i2 = 0;
                while (i2 < count) {
                    this.b(itemList[i2], 1);
                    ++i2;
                }
                return true;
            }
            if (itemList.length > count) {
                a dc2 = new a();
                Arrays.sort(itemList, dc2);
                int i3 = 0;
                while (i3 < count) {
                    this.b(itemList[i3], 1);
                    ++i3;
                }
                return true;
            }
        }
        return false;
    }

    public void a(int itemid) {
        for (q item : this.a) {
            if (item.N() != itemid) continue;
            this.f(item);
        }
    }

    public int c(int objectId, int count) {
        q item = this.e(objectId);
        return this.b(item, count);
    }

    public int f(q item) {
        return this.b(item, item.E());
    }

    public int b(q item, int count) {
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
                aw.a().a(item.fr());
            } else if (itemId >= 49016 && itemId <= 49025) {
                ai.a().a(item.fr());
            } else if (itemId >= 41383 && itemId <= 41400) {
                for (aa l1object : aq.a().b()) {
                    l furniture;
                    if (!(l1object instanceof l) || (furniture = (l)l1object).f() != item.fr()) continue;
                    y.a().b(furniture);
                }
            } else if (item.N() == 40309) {
                as.a.a().b(item.fr());
            }
            if (this instanceof g && ((g)this).k() == item.fr()) {
                ((g)this).k(null);
            }
            this.c(item);
            if (this.a.contains(item)) {
                return 0;
            }
            aq.a().b(item);
        } else {
            int oldCount = item.E();
            int newCount = oldCount - count;
            item.e(newCount);
            this.b(item);
            if (item.E() != newCount) {
                return 0;
            }
        }
        return count;
    }

    public void c(q item) {
        this.a.remove(item);
    }

    public synchronized q a(int objectId, int count, f targetInventory) {
        q item = this.e(objectId);
        return this.a(item, count, targetInventory);
    }

    public synchronized q a(q item, int count, f targetInventory) {
        q carryItem;
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
            if (this instanceof g && ((g)this).k() == item.fr()) {
                ((g)this).k(null);
            }
        } else {
            item.e(item.E() - count);
            this.b(item);
            carryItem = ah.a().b(item.N());
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

    public q g(q item) {
        return this.c(item, 1);
    }

    public q c(q item, int count) {
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

    public q h(q item) {
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

    public q b(int id) {
        for (q item : this.a) {
            if (item.N() != id) continue;
            return item;
        }
        return null;
    }

    public q d(int id, int bless) {
        for (q item : this.a) {
            if (item.N() != id || bless != 3 && item.F() != bless) continue;
            return item;
        }
        return null;
    }

    public q e(int id, int bless) {
        for (q item : this.a) {
            if (item.E() >= 1500000000 || item.N() != id || item.F() != bless) continue;
            return item;
        }
        return null;
    }

    public q c(int id) {
        for (q item : this.a) {
            if (item.M() != id) continue;
            return item;
        }
        return null;
    }

    public q[] d(int id) {
        return this.f(id, 3);
    }

    public q[] f(int id, int bless) {
        ArrayList<q> itemList = new ArrayList<q>();
        for (q item : this.a) {
            if (item.N() != id || bless != 3 && item.F() != bless) continue;
            itemList.add(item);
        }
        return itemList.toArray(new q[itemList.size()]);
    }

    private q[] h(int id) {
        ArrayList<q> itemList = new ArrayList<q>();
        for (q item : this.a) {
            if (item.N() != id || item.D()) continue;
            itemList.add(item);
        }
        return itemList.toArray(new q[itemList.size()]);
    }

    public q e(int objectId) {
        if (objectId == 0) {
            return null;
        }
        for (q itemObject : this.a) {
            q item = itemObject;
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
        q[] itemList;
        q item;
        if (count == 0) {
            return true;
        }
        return ah.a().a(itemid).aF() ? (item = this.d(itemid, bless)) != null && item.E() >= count : (itemList = this.f(itemid, bless)).length >= count;
    }

    public boolean h(int id, int count) {
        if (count == 0) {
            return true;
        }
        return count <= this.g(id);
    }

    public boolean a(int id, int enchant, int count, int bless) {
        int num = 0;
        for (q item : this.a) {
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
        for (q item : this.a) {
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
        int i2 = 0;
        while (i2 < len) {
            counts[i2] = 1;
            ++i2;
        }
        return this.a(ids, counts);
    }

    public boolean a(int[] ids, int[] counts) {
        int i2 = 0;
        while (i2 < ids.length) {
            if (!this.a(ids[i2], counts[i2], 3)) {
                return false;
            }
            ++i2;
        }
        return true;
    }

    public int g(int id) {
        if (ah.a().a(id).aF()) {
            q item = this.b(id);
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
        Iterator<q> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            q itemObject;
            q item = itemObject = iterator.next();
            aq.a().b(item);
        }
        this.a.clear();
    }

    public q a(String nameId) {
        for (q item : this.a) {
            if (!nameId.equals(item.a().j())) continue;
            return item;
        }
        return null;
    }

    public void a() {
    }

    public void a(q item) {
    }

    public void b(q item) {
    }

    private class a<T>
    implements Comparator<q> {
        private a() {
        }

        public int a(q item1, q item2) {
            return item1.G() - item2.G();
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.a((q)object, (q)object2);
        }
    }
}

