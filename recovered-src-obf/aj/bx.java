/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.aa;
import ao.ah;
import ao.al;
import ao.au;
import ao.aw;
import ao.bc;
import ap.t;
import ap.u;
import ap.v;
import aq.aq;
import aq.i;
import au.c;
import au.f;
import be.dc;
import be.ds;
import bh.q;
import bj.d;
import java.util.ArrayList;

public class bx
extends cv {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public bx(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        pc = client.f();
        if (pc == null) {
            return;
        }
        npcObjectId = this.b();
        resultType = this.c();
        size = this.d();
        if (resultType == 29) {
            i = 0;
            while (i < size) {
                index = this.b();
                count = this.b();
                whList = al.a().c(client.e().d());
                if (index < 0 || index >= whList.size() || count <= 0) {
                    return;
                }
                item = whList.get(index);
                if (item == null) break;
                authoritativeCount = item.E();
                if (authoritativeCount <= 0) break;
                if (pc.j().a(item, authoritativeCount) != 0) break;
                pc.j().d(item);
                pc.a(new ds(403, new String[]{item.s()}));
                al.a().a(client.e().d(), index);
                pc.a(new dc(al.a().c(client.e().d()), 0));
                ++i;
            }
            return;
        }
        npcId = 0;
        isDwarf = false;
        isPrivateShop = false;
        findObject = aq.a().a(npcObjectId);
        if (findObject == null) {
            return;
        }
        if (size == 0) {
            if (resultType == 5 && (clan = ao.q.a().a(pc.aF())) != null) {
                clan.i(0);
            }
            return;
        }
        if (findObject.fu().c(pc.fu()) > 11) {
            return;
        }
        if (findObject instanceof t) {
            targetNpc = (t)findObject;
            npcId = targetNpc.U_().b();
            isDwarf = targetNpc.H();
        } else if (findObject instanceof u) {
            isPrivateShop = true;
        } else {
            return;
        }
        switch (resultType) {
            case 0: {
                if (!isPrivateShop) {
                    orderList = new ArrayList<int[]>();
                    i = 0;
                    while (i < size) {
                        order = new int[]{this.b(), this.b()};
                        orderList.add(order);
                        ++i;
                    }
                    if (npcId == 190095) {
                        bc.a().b(pc, orderList, (t)findObject);
                        return;
                    }
                    if (npcId == 70035 || npcId == 70041 || npcId == 70042) {
                        bc.a().a(pc, orderList, (t)findObject);
                        return;
                    }
                    coinID = 40308;
                    if (npcId == 190005) {
                        coinID = 640268;
                    } else if (npcId == 190045) {
                        coinID = 640312;
                    } else if (npcId == 190353) {
                        coinID = 640621;
                    }
                    bc.a().b(pc, orderList, (t)findObject, coinID);
                    break;
                }
                targetPc = (u)findObject;
                if (targetPc.aY()) {
                    return;
                }
                sellList = targetPc.aU();
                isRemoveFromList = new boolean[8];
                var14_29 = sellList;
                synchronized (var14_29) {
                    if (pc.aZ() != sellList.size()) {
                        return;
                    }
                    targetPc.h(true);
                    i = 0;
                    while (i < size) {
                        order = this.b();
                        count = this.b();
                        pssl = sellList.get(order);
                        itemObjectId = pssl.a();
                        sellPrice = pssl.c();
                        sellTotalCount = pssl.b();
                        sellCount = pssl.d();
                        item = targetPc.j().e(itemObjectId);
                        if (item == null) ** GOTO lbl128
                        if (count > sellTotalCount - sellCount) {
                            count = sellTotalCount - sellCount;
                        }
                        if (count == 0) ** GOTO lbl128
                        if (pc.j().a(item, count) != 0) ** GOTO lbl126
                        j = 0;
                        while (j < count) {
                            if (sellPrice * j > 2000000000) {
                                pc.a(new ds(904, new String[]{"2000000000"}));
                                targetPc.h(false);
                                return;
                            }
                            ++j;
                        }
                        price = count * sellPrice;
                        if (!pc.j().g(40308, price)) ** GOTO lbl124
                        adena = pc.j().b(40308);
                        if (adena == null) ** GOTO lbl128
                        if (targetPc.j().a(item, count, (f)pc.j()) == null) {
                            targetPc.h(false);
                            return;
                        }
                        pc.j().a(adena, price, (f)targetPc.j());
                        message = String.valueOf(item.a().h()) + " (" + String.valueOf(count) + ")";
                        targetPc.a(new ds(877, new String[]{pc.et(), message}));
                        pssl.d(count + sellCount);
                        sellList.set(order, pssl);
                        if (pssl.d() != pssl.b()) ** GOTO lbl128
                        isRemoveFromList[order] = true;
                        ** GOTO lbl128
lbl124:
                        // 1 sources

                        pc.a(new ds(189));
                        break;
lbl126:
                        // 1 sources

                        pc.a(new ds(270));
                        break;
lbl128:
                        // 5 sources

                        ++i;
                    }
                    i = 7;
                    while (i >= 0) {
                        if (isRemoveFromList[i]) {
                            sellList.remove(i);
                        }
                        --i;
                    }
                    targetPc.h(false);
                    break;
                }
            }
            case 1: {
                if (!isPrivateShop) {
                    shop = bc.a().a(npcId);
                    orderList = new ArrayList<int[]>();
                    i = 0;
                    while (i < size) {
                        order = new int[]{this.b(), this.b()};
                        orderList.add(order);
                        ++i;
                    }
                    coinID = 40308;
                    if (npcId == 190005) {
                        coinID = 640268;
                    }
                    bc.a().a(pc, orderList, (t)findObject, coinID);
                    break;
                }
                targetPc = (u)findObject;
                if (targetPc.aY()) {
                    return;
                }
                targetPc.h(true);
                buyList = targetPc.aV();
                isRemoveFromList = new boolean[8];
                i = 0;
                while (i < size) {
                    itemObjectId = this.b();
                    count = this.e();
                    order = this.c();
                    item = pc.j().e(itemObjectId);
                    if (item == null) ** GOTO lbl204
                    psbl = (q)buyList.get(order);
                    buyPrice = psbl.c();
                    buyTotalCount = psbl.b();
                    if (count > buyTotalCount - (buyCount = psbl.d())) {
                        count = buyTotalCount - buyCount;
                    }
                    if (!item.D()) ** GOTO lbl178
                    pc.a(new ds(905));
                    ** GOTO lbl204
lbl178:
                    // 1 sources

                    if (item.F() >= 128) ** GOTO lbl204
                    if (targetPc.j().a(item, count) != 0) ** GOTO lbl202
                    j = 0;
                    while (j < count) {
                        if (buyPrice * j > 2000000000) {
                            targetPc.a(new ds(904, new String[]{"2000000000"}));
                            return;
                        }
                        ++j;
                    }
                    if (!targetPc.j().g(40308, count * buyPrice)) ** GOTO lbl200
                    adena = targetPc.j().b(40308);
                    if (adena == null) ** GOTO lbl204
                    targetPc.j().a(adena, count * buyPrice, (f)pc.j());
                    pc.j().a(item, count, (f)targetPc.j());
                    psbl.d(count + buyCount);
                    buyList.set(order, psbl);
                    if (psbl.d() != psbl.b()) ** GOTO lbl204
                    isRemoveFromList[order] = true;
                    ** GOTO lbl204
lbl200:
                    // 1 sources

                    targetPc.a(new ds(189));
                    break;
lbl202:
                    // 1 sources

                    pc.a(new ds(271));
                    break;
lbl204:
                    // 6 sources

                    ++i;
                }
                i = 7;
                while (i >= 0) {
                    if (isRemoveFromList[i]) {
                        buyList.remove(i);
                    }
                    --i;
                }
                targetPc.h(false);
                break;
            }
            case 2: 
            case 4: 
            case 8: 
            case 17: {
                if (!isDwarf || pc.ev() < 5) break;
                this.b(pc, size, resultType);
                pc.J();
                break;
            }
            case 3: 
            case 5: 
            case 9: 
            case 18: {
                if (!isDwarf || pc.ev() < 5) break;
                this.a(pc, size, resultType);
                break;
            }
            case 12: {
                if (isPrivateShop) break;
                i = 0;
                while (i < size) {
                    petCost = 0;
                    divisor = 6;
                    itemObjectId = this.b();
                    itemCount = this.b();
                    if (itemCount <= 0) {
                        return;
                    }
                    item = pc.j().e(itemObjectId);
                    if (item == null || item.N() != 40314 && item.N() != 40316 || pc.N(item.fr())) {
                        return;
                    }
                    for (t petNpc : pc.ek().values()) {
                        petCost += petNpc.Q();
                    }
                    charisma = pc.eC() + (pc.A() != false ? 12 : 6);
                    l1pet = aw.a().b(itemObjectId);
                    if (l1pet == null) {
                        return;
                    }
                    npcId = l1pet.c();
                    divisor = npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712 ? 12 : 6;
                    petCount = (charisma -= petCost) / divisor;
                    if (petCount <= 0) {
                        pc.a(new ds(489));
                        return;
                    }
                    npcTemp = au.a().a(npcId);
                    if (npcTemp == null) {
                        return;
                    }
                    if (!pc.j().b(40308, 115)) {
                        pc.a(new ds(189));
                        return;
                    }
                    try {
                        pet = new v(npcTemp, pc, l1pet);
                        pet.o(divisor);
                    }
                    catch (Exception e2) {
                        ah.a(pc, 40308, 115, 0, false);
                        throw e2;
                    }
                    ++i;
                }
                break;
            }
        }
    }

    private void a(u pc, int size, int resultType) {
        i clan = null;
        f inventory = pc.au();
        if (resultType == 5) {
            clan = ao.q.a().a(pc.aF());
            if (clan == null) {
                pc.a(new ds(208));
                return;
            }
            inventory = clan.c();
        } else if (resultType == 9) {
            if (!pc.A()) {
                pc.a(new ds(1238));
                return;
            }
            inventory = pc.av();
        } else if (resultType == 18) {
            inventory = pc.aw();
        }
        int i2 = 0;
        while (i2 < size) {
            int objectId = this.b();
            int count = this.b();
            ap.q item = inventory.e(objectId);
            if (item != null) {
                if (pc.j().a(item, count) != 0) {
                    pc.a(new ds(270));
                    break;
                }
                if (resultType == 9) {
                    if (!pc.j().b(40494, 4)) {
                        pc.a(new ds(337, "$767"));
                        break;
                    }
                } else if (resultType == 5) {
                    if (!pc.j().b(40308, 500)) {
                        pc.a(new ds(189));
                        break;
                    }
                } else if (!pc.j().b(40308, 100)) {
                    pc.a(new ds(189));
                    break;
                }
                inventory.a(item, count, (f)pc.j());
                if (resultType == 3) {
                    aa.a().e(pc, "\u9818\u51fa", item, count);
                } else if (resultType == 5) {
                    aa.a().f(pc, "\u9818\u51fa", item, count);
                } else if (resultType == 9) {
                    aa.a().g(pc, "\u9818\u51fa", item, count);
                }
                if (resultType == 5) {
                    ((c)inventory).a(pc, item, count, 1);
                }
            }
            ++i2;
        }
        if (clan != null) {
            clan.i(0);
        }
    }

    private void b(u pc, int size, int resultType) {
        f inventory = pc.au();
        if (resultType == 4) {
            i clan = ao.q.a().a(pc.aF());
            if (clan == null) {
                pc.a(new ds(208));
                return;
            }
            inventory = clan.c();
        } else if (resultType == 8) {
            if (!pc.A()) {
                pc.a(new ds(1238));
                return;
            }
            inventory = pc.av();
        } else if (resultType == 17 && (inventory = pc.aw()).c() >= pc.cJ()) {
            pc.a(new ds(1623));
            return;
        }
        int i2 = 0;
        while (i2 < size) {
            int itemobjid = this.b();
            int count = this.b();
            ap.q item = pc.j().e(itemobjid);
            if (item != null) {
                if (!item.a().s() && resultType != 17) {
                    pc.a(new ds(210, item.a().h()));
                } else if (item.F() >= 128 && resultType == 4) {
                    pc.a(new ds(210, item.a().h()));
                } else if (pc.N(item.fr())) {
                    pc.a(new ds(1187));
                } else if (pc.O(item.fr())) {
                    pc.a(new ds(1181));
                } else {
                    if (inventory.a(pc, item, count) == 1) {
                        pc.a(new ds(resultType == 17 ? 1623 : 75));
                        break;
                    }
                    pc.j().a(itemobjid, count, inventory);
                    pc.fg();
                    if (resultType == 2) {
                        aa.a().e(pc, "\u5b58\u5165", item, count);
                    } else if (resultType == 4) {
                        aa.a().f(pc, "\u5b58\u5165", item, count);
                    } else if (resultType == 8) {
                        aa.a().g(pc, "\u5b58\u5165", item, count);
                    }
                    if (resultType == 4) {
                        ((c)inventory).a(pc, item, count, 0);
                    }
                }
            }
            ++i2;
        }
    }

    @Override
    public String a() {
        return "C_Result";
    }
}

