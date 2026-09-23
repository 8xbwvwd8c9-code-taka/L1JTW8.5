/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.a;
import ao.aa;
import ao.ah;
import ao.al;
import ao.bb;
import ao.bd;
import ao.p;
import ap.q;
import ap.t;
import ap.u;
import aq.aq;
import aq.i;
import be.cw;
import be.dc;
import be.dj;
import be.ds;
import be.v;
import bj.d;
import java.util.concurrent.ConcurrentHashMap;

public class cd
extends cv {
    public cd(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int action = this.c();
        block0 : switch (action) {
            case 10: {
                int size = this.b();
                int i2 = 0;
                while (i2 < size) {
                    int index = this.b();
                    int itemCount = this.b();
                    ConcurrentHashMap<Integer, q> whList = bd.a().a(client.a());
                    q item = whList.get(index);
                    if (item == null) break block0;
                    int authoritativeCount = item.E();
                    if (authoritativeCount <= 0) break block0;
                    if (pc.j().a(item, authoritativeCount) != 0) break block0;
                    pc.j().d(item);
                    pc.a(new ds(403, item.s()));
                    bd.a().a(client.a(), index);
                    aa.a().h(pc, "\u9818\u53d6", item, authoritativeCount);
                    ++i2;
                }
                break;
            }
            case 11: {
                if (pc.cN()) break;
                pc.a(new v(bd.a().b()));
                pc.a(new dc(al.a().c(client.a()), 0));
                pc.x(true);
                break;
            }
            case 13: {
                int npcobjid = this.b();
                int itemobjid = this.b();
                int itemobjid2 = this.b();
                aq.aa npcObject = aq.a().a(npcobjid);
                if (!(npcObject instanceof t)) {
                    return;
                }
                t npc = (t)npcObject;
                if (npc.fu().b() != pc.fp() || npc.fu().c(pc.fu()) > 11) {
                    return;
                }
                q resolvent = pc.j().e(itemobjid);
                if (resolvent == null) {
                    return;
                }
                int crystalCount = bb.a().a(resolvent);
                q subitem = pc.j().e(itemobjid2);
                if (subitem != null && subitem.N() == 640343) {
                    crystalCount = (int)((double)crystalCount * 1.5);
                    pc.j().b(subitem, 1);
                }
                ah.a(pc, 640341, crystalCount, npc.T());
                pc.j().b(resolvent, 1);
                break;
            }
            case 9: {
                ConcurrentHashMap<Integer, q> whList = bd.a().a(client.a());
                if (whList.isEmpty()) {
                    pc.a(new ds(2746));
                    return;
                }
                pc.a(new dj(whList));
                break;
            }
            case 8: {
                int unknow = this.b();
                int itemid = this.b();
                int sysID = this.b();
                int count = this.b();
                int price = this.b();
                int unknow2 = this.b();
                int type = this.c();
                if (type == 0) {
                    bd.b shopData = bd.a().b().get(itemid);
                    if (shopData == null) {
                        pc.a(new ds(156));
                        return;
                    }
                    q item = shopData.a;
                    bh.a account = client.e();
                    if (price != shopData.b * count) {
                        pc.a(new ds(156));
                        return;
                    }
                    if (item == null || item.m() != sysID) {
                        pc.a(new ds(156));
                        return;
                    }
                    int adena = account.p();
                    if (adena < price) {
                        pc.a(new ds(2742));
                        return;
                    }
                    if (pc.aK().e().h() < shopData.d) {
                        pc.a(new ds(2743));
                        return;
                    }
                    int newBalance = adena - price;
                    if (!bd.a().a(account.d(), adena, newBalance, item.N(), count)) {
                        pc.a(new ds(156));
                        return;
                    }
                    account.g(newBalance);
                    pc.a(new ds(2745));
                    pc.a(new v(37, account.p()));
                    pc.bd(pc.cQ() + price);
                    aa.a().h(pc, "\u8cfc\u8cb7", item, count);
                    break;
                }
                if (type == 1) {
                    String shopData = this.g();
                    break;
                }
                if (type != 1) break;
                String shopData = this.g();
                break;
            }
            case 15: {
                String announce = this.g();
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                if (!pc.x() || pc.fr() != clan.k()) {
                    pc.a(new ds(518));
                    return;
                }
                clan.f(announce);
                ao.q.a().b(clan);
                break;
            }
            case 16: {
                String notes = this.g();
                if (notes == null) {
                    notes = "";
                }
                p.a().a(pc, notes);
                pc.a(new cw(pc.et(), notes));
            }
        }
    }

    @Override
    public String a() {
        return "C_ShopWorld";
    }
}

