/*
 * Decompiled with CFR 0.152.
 */
package au;

import ao.ah;
import ao.l;
import ap.q;
import ap.t;
import ap.u;
import ap.v;
import aq.ae;
import aq.aq;
import aq.p;
import as.a;
import au.f;
import be.ag;
import be.as;
import be.bj;
import be.bk;
import be.bl;
import be.bm;
import be.c;
import be.ck;
import be.cm;
import be.dc;
import be.ds;
import be.ei;
import be.z;
import bh.s;
import bi.i;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g
extends f {
    private static final Logger g = Logger.getLogger(g.class.getName());
    private static final int h = 180;
    private final u i;
    private q j = null;
    private boolean[] k = new boolean[2];
    private boolean[] l = new boolean[2];
    private boolean[] m = new boolean[2];
    private int[] n = new int[4];

    public g(u owner) {
        this.i = owner;
    }

    public u b() {
        return this.i;
    }

    public int h() {
        return this.o(this.e());
    }

    private int o(int weight) {
        if (l1j.server.a.J == 0.0) {
            return 0;
        }
        double maxWeight = this.i.K();
        if ((double)weight > maxWeight) {
            return 100;
        }
        return (int)((double)(weight * 100) / maxWeight);
    }

    @Override
    public int a(q item, int count) {
        if (item == null) {
            return -1;
        }
        if (this.c() > 180 || this.c() == 180 && (!item.d() || !this.f(item.N()))) {
            this.p(263);
            return 1;
        }
        int weight = this.e() + item.a().l() * count / 1000 + 1;
        if (weight < 0 || item.a().l() * count / 1000 < 0) {
            this.p(82);
            return 2;
        }
        if (this.o(weight) >= 100) {
            this.p(82);
            return 2;
        }
        long adenaCount = this.g(40308);
        if (adenaCount + (long)count > 2000000000L) {
            this.b().a(new ei("\\aG\u6240\u6301\u6709\u7684\u91d1\u5e63\u8d85\u904e\u4e862000000000\u4e0a\u9650"));
            return 3;
        }
        if (item.N() == 640102) {
            if (p.a().a) {
                aq.a().b(item);
                return -1;
            }
            p.a().a = true;
            item.a(new Timestamp(System.currentTimeMillis()));
            p.a().a(this.i.fr(), 0L);
        }
        return 0;
    }

    private void p(int message_id) {
        if (this.i.cq() && message_id == 82) {
            message_id = 1518;
        }
        this.i.a(new ds(message_id));
    }

    @Override
    public synchronized q d(q item) {
        if (item == null || item.E() <= 0) {
            return null;
        }
        int itemId = item.N();
        if (item.d()) {
            q findItem = this.d(itemId, item.F());
            if (itemId == 40309) {
                findItem = this.a(item.a().j());
            } else if (itemId == 40312) {
                findItem = this.c(itemId);
            }
            if (findItem != null && findItem.F() == item.F()) {
                int oldCount = findItem.E();
                int newCount = oldCount + item.E();
                if (newCount <= 0 || newCount > 1500000000) {
                    return null;
                }
                findItem.e(newCount);
                this.b(findItem);
                if (findItem.E() != newCount) {
                    return null;
                }
                return findItem;
            }
        }
        int chargeCount = item.a().aM();
        if (item.N() >= 21340 && item.N() <= 21349) {
            item.d(this.i.ay());
        } else if (itemId == 40309) {
            as.a.a().b(item);
        } else if (itemId == 41401) {
            chargeCount -= bi.i.a(5);
        } else if (itemId == 20383) {
            chargeCount = 50;
        }
        item.g(chargeCount);
        if (item.f() && item.a().aP() == 2) {
            item.j(item.a().d());
        } else if (item.N() != 40312 && item.N() != 640615) {
            item.j(item.a().T());
        }
        item.n();
        this.a.add(item);
        this.a(item);
        if (!this.a.contains(item)) {
            return null;
        }
        return item;
    }

    @Override
    public synchronized q e(q item) {
        if (item == null || item.E() <= 0) {
            return null;
        }
        if (item.N() == 40312) {
            q findItem = this.c(item.M());
            if (findItem != null) {
                int newCount = findItem.E() + item.E();
                if (newCount <= 0 || newCount > 1500000000) {
                    return null;
                }
                findItem.e(newCount);
                this.b(findItem);
                return findItem.E() == newCount ? findItem : null;
            }
        } else if (item.d()) {
            q findItem = this.d(item.N(), item.F());
            if (findItem != null && findItem.F() == item.F()) {
                int newCount = findItem.E() + item.E();
                if (newCount <= 0 || newCount > 1500000000) {
                    return null;
                }
                findItem.e(newCount);
                this.b(findItem);
                return findItem.E() == newCount ? findItem : null;
            }
        } else if (item.bb() != null) {
            Timestamp current = new Timestamp(System.currentTimeMillis());
            if (item.bb().before(current)) {
                this.i.a(new ds(2535, item.b(), "0"));
                if (item.N() >= 21246 && item.N() <= 21251) {
                    int enchant = item.G();
                    int bless = item.F();
                    boolean isIdentified = item.C();
                    item = ah.a().b(20085);
                    item.a(enchant);
                    item.a(isIdentified);
                    item.f(bless);
                } else if (item.N() >= 21252 && item.N() <= 21257) {
                    int enchant = item.G();
                    int bless = item.F();
                    boolean isIdentified = item.C();
                    item = ah.a().b(20084);
                    item.a(enchant);
                    item.a(isIdentified);
                    item.f(bless);
                } else if (item.N() >= 21261 && item.N() <= 21300) {
                    item.b((Timestamp)null);
                } else {
                    return null;
                }
            }
        }
        this.a.add(item);
        this.a(item);
        if (!this.a.contains(item)) {
            return null;
        }
        return item;
    }

    @Override
    public void a() {
        try {
            this.a.clear();
            this.n = new int[4];
            this.m = new boolean[2];
            this.l = new boolean[2];
            this.k = new boolean[2];
            Timestamp current = new Timestamp(System.currentTimeMillis());
            for (q item : ao.l.a().a(this.i.fr())) {
                if (item.bb() != null && item.bb().before(current)) {
                    boolean isIdentified;
                    boolean isEquipped;
                    int bless;
                    int enchant;
                    this.i.a(new ds(2535, item.b(), "0"));
                    ao.l.a().a(item);
                    if (item.N() >= 21246 && item.N() <= 21251) {
                        enchant = item.G();
                        bless = item.F();
                        isEquipped = item.D();
                        isIdentified = item.C();
                        item = ah.a().b(20085);
                        item.a(enchant);
                        item.a(isIdentified);
                        item.f(bless);
                        item.b(isEquipped);
                        ao.l.a().a(this.i.fr(), item);
                    } else if (item.N() >= 21252 && item.N() <= 21257) {
                        enchant = item.G();
                        bless = item.F();
                        isEquipped = item.D();
                        isIdentified = item.C();
                        item = ah.a().b(20084);
                        item.a(enchant);
                        item.a(isIdentified);
                        item.f(bless);
                        item.b(isEquipped);
                        ao.l.a().a(this.i.fr(), item);
                    } else {
                        if (item.N() < 21261 || item.N() > 21300) continue;
                        int creatid = item.N();
                        boolean isIdentified2 = item.C();
                        item = ah.a().b(creatid);
                        item.a(isIdentified2);
                        ao.l.a().a(this.i.fr(), item);
                    }
                }
                this.a.add(item);
                if (item.D()) {
                    if (this.i.ev() < item.a().o() || item.a().p() > 0 && this.i.ev() > item.a().p()) {
                        item.b(false);
                    } else {
                        this.i.bK().a(item);
                    }
                }
                if (item.f() && item.a().aP() == 2) {
                    item.j(item.a().d());
                }
                if (item.N() >= 21340 && item.N() <= 21349) {
                    item.d(this.i.ay());
                }
                if (item.N() == 40309) {
                    as.a.a().a(item);
                }
                aq.a().a(item);
            }
        }
        catch (Exception e2) {
            g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    @Override
    public void a(q item) {
        try {
            ao.l.a().a(this.i.fr(), item);
        }
        catch (Exception e2) {
            g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            this.a.remove(item);
            aq.a().b(item);
            return;
        }
        for (s qn : this.i.dS().values()) {
            int i2 = 0;
            while (i2 < qn.r().length) {
                if (qn.r()[i2] == item.N() && qn.t()[i2] <= item.G()) {
                    qn.a(i2, item.E());
                }
                ++i2;
            }
        }
        this.i.a(new c(item));
        if (item.N() == 640100 || item.N() == 640102) {
            this.i.ae();
        }
        if (item.a().l() != 0) {
            this.i.a(new dc(485, this.i));
        }
    }

    @Override
    public void b(q item) {
        if (item.a().u() && !this.i(item)) {
            return;
        }
        for (s qn : this.i.dS().values()) {
            int i2 = 0;
            while (i2 < qn.r().length) {
                if (qn.r()[i2] == item.N() && qn.t()[i2] <= item.G()) {
                    qn.a(i2, item.E());
                }
                ++i2;
            }
        }
        if (item.o[1] != item.X() || item.p[1] != item.Y() || item.q[1] != item.Z() || item.r[1] != item.aa()) {
            this.i.a(new bl(item));
            item.o[1] = item.X();
            item.p[1] = item.Y();
            item.q[1] = item.Z();
            item.r[1] = item.aa();
        }
        if (item.n[1] != item.L()) {
            this.i.a(new bl(item));
            item.n[1] = item.L();
        }
        if (item.m[1] != item.K()) {
            this.i.a(new bl(item));
            item.m[1] = item.K();
        }
        if (item.j[1] != item.bb()) {
            this.i.a(new bl(item));
            item.j[1] = item.bb();
        }
        if (item.g[1] != item.H()) {
            this.i.a(new bl(item));
            item.g[1] = item.H();
        }
        if (item.i[1] != item.M()) {
            this.i.a(new bm(item));
            item.i[1] = item.M();
        }
        if (item.h[1] != item.I()) {
            this.i.a(new bm(item));
            item.h[1] = item.I();
        }
        if (item.c[1] != item.D()) {
            this.i.a(new bm(item));
            item.c[1] = item.D();
        }
        if (item.d[1] != item.U()) {
            this.i.a(new bm(item));
            item.d[1] = item.U();
        }
        if (item.b[1] != item.N()) {
            this.i.a(new bl(item));
            this.i.a(new bk(item));
            this.i.a(new dc(485, this.i));
            item.b[1] = item.N();
        }
        if (item.a[1] != item.E()) {
            this.i.a(new bl(item));
            this.i.a(new dc(485, this.i));
            item.a[1] = item.E();
        }
        if (item.l[1] != item.F()) {
            this.i.a(new bk(item));
            this.i.a(new bj(item));
            item.l[1] = item.F();
        }
        if (item.e[1] != item.G()) {
            this.i.a(new bl(item));
            this.i.a(new bj(item));
            item.e[1] = item.G();
        }
        if (item.f[1] != item.C()) {
            this.i.a(new bl(item));
            this.i.a(new bk(item));
            item.f[1] = item.C();
        }
    }

    public boolean i(q item) {
        try {
            ao.l.a().p(item);
            item.o[0] = item.X();
            item.p[0] = item.Y();
            item.q[0] = item.Z();
            item.r[0] = item.aa();
            item.n[0] = item.L();
            item.m[0] = item.K();
            item.l[0] = item.F();
            item.i[0] = item.M();
            item.j[0] = item.bb();
            item.h[0] = item.I();
            item.b[0] = item.N();
            item.k[0] = item.J();
            item.a[0] = item.E();
            item.c[0] = item.D();
            item.e[0] = item.G();
            item.f[0] = item.C();
            item.g[0] = item.H();
            return true;
        }
        catch (Exception e2) {
            g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            item.l(item.o[0]);
            item.m(item.p[0]);
            item.n(item.q[0]);
            item.o(item.r[0]);
            item.i(item.n[0]);
            item.h(item.m[0]);
            item.f(item.l[0]);
            item.j(item.i[0]);
            item.b(item.j[0]);
            item.g(item.h[0]);
            item.a(item.k[0]);
            item.e(item.a[0]);
            item.b(item.c[0]);
            item.a(item.e[0]);
            item.a(item.f[0]);
            item.b(item.g[0]);
            return false;
        }
    }

    public void j(q item) {
        this.b(item);
    }

    @Override
    public void c(q item) {
        if (item.D()) {
            this.a(item, false);
            if (item.D()) {
                return;
            }
        }
        try {
            ao.l.a().a(item);
        }
        catch (Exception e2) {
            g.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
        if (item.a().l() != 0) {
            this.i.a(new dc(485, this.i));
        }
        this.i.a(new ag(item));
        this.a.remove(item);
        if (item.N() == 640100) {
            this.i.bz(25005);
        }
        for (s qn : this.i.dS().values()) {
            int i2 = 0;
            while (i2 < qn.r().length) {
                if (qn.r()[i2] == item.N() && qn.t()[i2] <= item.G()) {
                    qn.a(i2, Math.max(0, qn.B()[i2] - item.E()));
                }
                ++i2;
            }
        }
    }

    public void a(q item, boolean equipped) {
        if (item.D() != equipped) {
            if (equipped) {
                item.b(true);
                this.i.bK().a(item);
            } else {
                if ((item.N() == 20077 || item.N() == 20062 || item.N() == 120077) && this.i.ff()) {
                    this.i.s();
                    return;
                }
                item.b(false);
                this.i.bK().b(item);
            }
            this.b(item);
            int index = this.l(item);
            if (index >= 0) {
                this.i.a(new as(item.fr(), index, item.D()));
            }
            this.i.a(new ck(this.i));
            if (item.g()) {
                this.i.a(new z(this.i));
                this.i.b(new z(this.i));
            }
            this.i.a(new cm(132, this.i.u()));
            this.i.a(new dc(485, this.i));
        }
    }

    public boolean h(int itemid) {
        for (q item : this.a) {
            if (item.N() != itemid || !item.D()) continue;
            return true;
        }
        return false;
    }

    public boolean b(int[] ids) {
        int[] nArray = ids;
        int n2 = ids.length;
        int n3 = 0;
        while (n3 < n2) {
            int id = nArray[n3];
            if (!this.h(id)) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public int i(int type) {
        int equipeCount = 0;
        for (q item : this.a) {
            if (!item.h() || item.a().aP() != type || !item.D()) continue;
            ++equipeCount;
        }
        return equipeCount;
    }

    public q j(int type) {
        for (q item : this.a) {
            if (!item.h() || item.a().aP() != type || !item.D()) continue;
            return item;
        }
        return null;
    }

    public ArrayList<q> i() {
        ArrayList<q> list = new ArrayList<q>();
        for (q item : this.a) {
            if (!item.h() || item.a().aP() != 9 || !item.D()) continue;
            list.add(item);
        }
        return list;
    }

    public void k(int itemobjid) {
        for (q item : this.a) {
            if (item.fr() != itemobjid) continue;
            if (item.g() && ae.a(this.i.fe(), item.a().aP())) {
                this.a(item, true);
            }
            if (!item.h() || !ae.b(this.i.fe(), item.a().aP()) || item.N() == 21397 && this.i.fp() != 1700 && this.i.fp() != 1703) continue;
            this.a(item, true);
        }
    }

    public void l(int polyid) {
        for (q item : this.a) {
            if (!item.D()) continue;
            if (item.g() && !ae.a(polyid, item.a().aP())) {
                this.a(item, false);
            }
            if (!item.h() || ae.b(polyid, item.a().aP())) continue;
            this.a(item, false);
        }
    }

    public void j() {
        for (q item : this.a) {
            if (!item.D()) continue;
            this.a(item, false);
        }
    }

    public boolean m(int itemid) {
        boolean b2 = false;
        for (q item : this.a) {
            if (item.N() != itemid || !item.D()) continue;
            this.a(item, false);
            b2 = true;
        }
        return b2;
    }

    protected int k() {
        return this.j == null ? 0 : this.j.fr();
    }

    public q n(int weaponType) {
        int itemType = 0;
        if (weaponType == 62) {
            itemType = 15;
        }
        if (this.j != null && this.j.E() > 1 && this.j.a().aP() == itemType) {
            return this.j;
        }
        for (q item : this.a) {
            if (!item.f() || item.a().aP() != itemType) continue;
            this.j = item;
            return this.j;
        }
        this.j = null;
        return this.j;
    }

    public void k(q item) {
        this.j = item;
    }

    public int l() {
        int hpr = 0;
        for (q item : this.a) {
            if (!item.D()) continue;
            hpr += item.a().O() + item.ag() + item.bc() + item.bD();
        }
        return hpr;
    }

    public int m() {
        int mpr = 0;
        for (q item : this.a) {
            if (!item.D()) continue;
            mpr += item.a().P() + item.ah() + item.bd() + item.bC();
            if (item.N() != 330 && item.N() != 332) continue;
            mpr += item.G();
        }
        return mpr;
    }

    public q n() {
        if (this.a.isEmpty()) {
            return null;
        }
        int rnd = bi.i.a(this.a.size());
        q penaltyItem = (q)this.a.get(rnd);
        if (penaltyItem.N() == 40308 || !penaltyItem.a().s()) {
            return null;
        }
        for (t npc : this.i.ek().values()) {
            if (!(npc instanceof v)) continue;
            v pet = (v)npc;
            if (penaltyItem.fr() != pet.k()) continue;
            return null;
        }
        this.a(penaltyItem, false);
        return penaltyItem;
    }

    private int o() {
        int i2 = 0;
        while (i2 < this.k.length) {
            if (!this.k[i2]) {
                this.k[i2] = true;
                return 9 - i2;
            }
            ++i2;
        }
        return -1;
    }

    private int p() {
        int i2 = 0;
        while (i2 < this.l.length) {
            if (!this.l[i2]) {
                this.l[i2] = true;
                return (i2 + 1) * 13;
            }
            ++i2;
        }
        return -1;
    }

    private int q() {
        int i2 = 0;
        while (i2 < this.m.length) {
            if (!this.m[i2]) {
                this.m[i2] = true;
                return 23 + i2 * 4;
            }
            ++i2;
        }
        return -1;
    }

    public int l(q item) {
        int index = 0;
        if (item.g()) {
            if (item.D()) {
                index = -1;
                if (item.V() == 0) {
                    index = this.o();
                    item.k(index);
                }
            } else {
                index = item.V();
                this.k[9 - index] = false;
                item.k(0);
            }
        } else if (item.h()) {
            switch (item.a().aP()) {
                case 13: {
                    if (item.D()) {
                        index = -1;
                        if (item.V() != 0) break;
                        index = this.p();
                        item.k(index);
                        break;
                    }
                    index = item.V();
                    this.l[index / 13 - 1] = false;
                    item.k(0);
                    break;
                }
                case 23: {
                    if (item.D()) {
                        index = -1;
                        if (item.V() != 0) break;
                        index = this.q();
                        item.k(index);
                        break;
                    }
                    index = item.V();
                    this.m[(index - 23) / 4] = false;
                    item.k(0);
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 11: 
                case 12: 
                case 15: 
                case 16: 
                case 18: 
                case 29: 
                case 30: {
                    index = item.a().aP();
                    break;
                }
                case 8: 
                case 10: {
                    index = 8;
                    break;
                }
                case 9: {
                    if (item.D()) {
                        if (item.V() == 0) {
                            index = this.r();
                            item.k(index);
                            break;
                        }
                        index = -1;
                        break;
                    }
                    index = item.V();
                    this.n[item.V() - 19] = 0;
                    item.k(0);
                }
            }
        }
        return index;
    }

    private int r() {
        int c2 = 0;
        while (c2 < this.n.length) {
            if (this.n[c2] == 0) {
                this.n[c2] = 1;
                return c2 + 19;
            }
            ++c2;
        }
        return -1;
    }
}

