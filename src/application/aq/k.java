/*
 * Decompiled with CFR 0.152.
 */
package aq;

import a.g;
import an.a;
import an.c;
import an.d;
import an.f;
import ao.ah;
import ap.q;
import bi.i;
import java.util.ArrayList;
import java.util.HashMap;

public class k {
    private int a = 0;
    private int b = 0;
    private final ArrayList<q> c = new ArrayList();
    private final HashMap<Integer, q> d = new HashMap();
    private final HashMap<Integer, ArrayList<q>> e = new HashMap();
    private final HashMap<Integer, q> f = new HashMap();
    private final ArrayList<Integer> g = new ArrayList();
    private q h = null;
    private q i = null;
    private int j = 0;
    private int k = 0;
    private int l = 1;
    private int m = 99;
    private int n = Short.MAX_VALUE;
    private int o = Short.MIN_VALUE;
    private int p = 100;
    private int q = Integer.MIN_VALUE;
    private int r = Integer.MAX_VALUE;
    private int s = 100;

    public int a() {
        return this.a;
    }

    public k(int craftID) {
        this.a = craftID;
        if (craftID == 576) {
            this.b = 128;
        } else if (craftID == 577) {
            this.b = 32;
        } else if (craftID == 578) {
            this.b = 4;
        } else if (craftID == 616) {
            q item = ah.a().b(413);
            this.f.put(item.fr(), item);
        }
    }

    public void a(int itemid, int count, int enchant) {
        q item = ah.a().b(itemid);
        item.e(count);
        item.a(enchant);
        item.n();
        this.c.add(item);
    }

    public q b() {
        return this.c.get(bi.i.a(this.c.size()));
    }

    public void a(int i2) {
        this.j = i2;
    }

    public int c() {
        return this.j;
    }

    public void b(int i2) {
        this.k = i2;
    }

    public int d() {
        return this.k;
    }

    public q e() {
        return this.i;
    }

    public void a(int itemid, int count) {
        if (itemid <= 0 || count <= 0) {
            return;
        }
        q item = ah.a().b(itemid);
        item.e(count);
        item.f(1);
        this.i = item;
    }

    public void c(int itemid) {
        if (itemid == 0) {
            return;
        }
        q item = ah.a().b(itemid);
        item.e(10);
        item.f(1);
        this.h = item;
    }

    public q f() {
        return this.h;
    }

    public void a(int itemid, int count, int enchant, int bless) {
        q item = ah.a().b(itemid);
        if (item == null) {
            System.out.println("L1Craft addMaterialItem is null id=" + itemid + "craftid=" + this.a);
            return;
        }
        item.e(count);
        item.f(bless);
        item.a(enchant);
        item.a(true);
        this.d.put(item.N(), item);
        this.e.put(itemid, new ArrayList());
        this.g.add(itemid);
    }

    public void a(int materialID, int exchangeItemid, int exchangeItemCount, int exchangeItemEnchant, int exchangeItemBless) {
        ArrayList<q> list = this.e.get(materialID);
        q item = ah.a().b(exchangeItemid);
        item.e(exchangeItemCount);
        item.f(exchangeItemBless);
        item.a(exchangeItemEnchant);
        item.a(true);
        list.add(item);
    }

    public HashMap<Integer, q> g() {
        return this.d;
    }

    public HashMap<Integer, ArrayList<q>> h() {
        return this.e;
    }

    public g i() {
        c.c.a builder = c.c.aa();
        builder.a(this.a);
        builder.e(this.n());
        builder.b(this.b);
        builder.f(this.q());
        builder.g(this.f(0));
        builder.h(this.m());
        builder.i(this.p());
        builder.j(this.o());
        builder.c(3);
        return a.g.a(builder.M().g());
    }

    private g m() {
        a.c.a builder2 = a.c.aa();
        builder2.b(1);
        builder2.c(this.f.size());
        for (q item : this.f.values()) {
            a.a.a builder1 = a.a.aa();
            builder1.a(item.m());
            builder1.b(item.E());
            builder1.c(1);
            builder2.e(builder1.M().f());
        }
        return a.g.a(builder2.M().g());
    }

    private g n() {
        a.a.a builder = a.a.aa();
        String name = this.c.get(0).a().j().trim();
        int nameid = 994;
        if (!name.isEmpty() && name.contains("$")) {
            String[] splite = name.split("\\$");
            nameid = Integer.parseInt(splite[splite.length - 1].trim());
        }
        builder.a(this.k > 0 ? this.k : nameid);
        builder.b(this.l);
        builder.c(this.m);
        builder.d(2);
        builder.e(this.o);
        builder.f(this.n);
        builder.g(this.q);
        builder.h(this.r);
        builder.i(this.p);
        return a.g.a(builder.M().g());
    }

    public g a(q _craft_item, boolean isSettingBless) {
        a.e.a builder = a.e.ae();
        builder.a(_craft_item.N());
        builder.b(_craft_item.E());
        builder.c(-1);
        builder.d(_craft_item.G());
        builder.e(isSettingBless ? 0 : _craft_item.F());
        builder.f(0);
        builder.g(0);
        builder.e(bi.g.a(_craft_item.b()));
        builder.h(0);
        builder.i(0);
        builder.j(_craft_item.e());
        builder.f(a.g.a(""));
        builder.g(a.g.a(_craft_item.t()));
        builder.k(0);
        builder.l(0);
        builder.m(this.j > 0 ? 1 : 0);
        return a.g.a(builder.M().g());
    }

    public g j() {
        d.e.a builder = d.e.aa();
        builder.a(1);
        builder.b(0);
        builder.c(1);
        builder.e(this.a(this.c.get(0), true));
        return a.g.a(builder.M().g());
    }

    private g a(int index, q item) {
        c.e.a builder = c.e.aa();
        builder.a(item.m());
        builder.b(item.E());
        builder.c(index);
        builder.d(item.G());
        builder.e(item.F());
        builder.e(bi.g.a(item.s()));
        builder.f(item.e());
        return a.g.a(builder.M().g());
    }

    private g o() {
        a.g.a builder = a.g.aa();
        builder.e(this.s());
        builder.f(this.r());
        builder.a(this.s * 10000);
        return a.g.a(builder.M().g());
    }

    private g p() {
        d.i.a builder = d.i.aa();
        int index = 0;
        for (int i2 : this.g) {
            q item = this.d.get(i2);
            builder.e(this.a(++index, item));
            if (!this.e.containsKey(item.N())) continue;
            for (q exChange : this.e.get(item.N())) {
                builder.e(this.a(index, exChange));
            }
        }
        if (this.h != null) {
            builder.f(this.a(++index, this.h));
        }
        return a.g.a(builder.M().g());
    }

    private g q() {
        a.c.a builder = a.c.aa();
        builder.b(1);
        builder.c(0);
        builder.e(this.a(0L, 0L));
        return a.g.a(builder.M().g());
    }

    private g r() {
        a.g.a builder = a.g.aa();
        builder.e(this.a(0L, 0L));
        builder.f(this.a(0xFFFFFFFFL, 0xFFFFFFFFL));
        builder.a(0);
        builder.b(0);
        if (this.i != null) {
            builder.b(1);
            builder.h(this.k());
        }
        builder.c(0);
        return a.g.a(builder.M().g());
    }

    public g k() {
        a.e.a builder = a.e.ae();
        builder.a(this.i.N());
        builder.b(this.i.E());
        builder.c(-1);
        builder.d(this.i.G());
        builder.e(this.i.F());
        builder.f(0);
        builder.g(0);
        builder.e(bi.g.a(this.i.b()));
        builder.h(0);
        builder.i(0);
        builder.j(this.i.e());
        builder.f(a.g.a(""));
        builder.g(a.g.a(this.i.t()));
        return a.g.a(builder.M().g());
    }

    private g s() {
        f.i.a builder = f.i.aa();
        builder.e(this.a(0L, 0L));
        builder.f(this.a(0xFFFFFFFFL, 0xFFFFFFFFL));
        int size = this.c.size();
        builder.b(size > 1 ? size : 0);
        builder.c(1);
        if (size > 1) {
            for (q _craft_item : this.c) {
                builder.g(this.a(_craft_item, false));
            }
        } else {
            builder.h(this.a(this.c.get(0), false));
            builder.i(this.j());
        }
        builder.d(0);
        return a.g.a(builder.M().g());
    }

    private g f(int i2) {
        d.a.a builder = d.a.aa();
        builder.a(i2);
        return a.g.a(builder.M().g());
    }

    public g a(long i2, long j2) {
        d.a.a builder = d.a.aa();
        builder.a(i2);
        builder.b(j2);
        return a.g.a(builder.M().g());
    }

    public void d(int chance) {
        this.s = chance;
    }

    public int l() {
        return this.s;
    }

    public void b(int min, int max) {
        this.l = min;
        this.m = max;
    }

    public void c(int min, int max) {
        this.o = min;
        this.n = max;
    }

    public void d(int min, int max) {
        this.q = min;
        this.r = max;
    }

    public void e(int count) {
        this.p = count;
    }
}

