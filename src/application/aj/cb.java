/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.e;
import ap.t;
import ap.u;
import ap.v;
import aq.ae;
import be.ak;
import be.al;
import be.ds;
import be.ei;
import bh.q;
import bh.r;
import bj.d;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cb
extends cv {
    private static final Logger a = Logger.getLogger(cb.class.getName());
    private static final String b = "[C] C_Shop";

    public cb(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int mapId = pc.fp();
        if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370 && mapId != 800) {
            pc.a(new ds(876));
            return;
        }
        CopyOnWriteArrayList<r> sellList = pc.aU();
        ArrayList<q> buyList = pc.aV();
        boolean tradable = true;
        int type = this.c();
        if (type == 0) {
            ap.q checkItem;
            int sellTotalCount = this.d();
            int i2 = 0;
            while (i2 < sellTotalCount) {
                int sellObjectId = this.b();
                int sellPrice = this.b();
                int sellCount = this.b();
                checkItem = pc.j().e(sellObjectId);
                if (checkItem != null) {
                    if (!checkItem.a().s()) {
                        tradable = false;
                        pc.a(new ei(String.valueOf(checkItem.a().h()) + "\u9019\u662f\u4e0d\u53ef\u80fd\u8655\u7406\u3002"));
                    }
                    for (t petNpc : pc.ek().values()) {
                        if (!(petNpc instanceof v)) continue;
                        v pet = (v)petNpc;
                        if (checkItem.fr() != pet.k()) continue;
                        tradable = false;
                        pc.a(new ei(String.valueOf(checkItem.a().h()) + "\u9019\u662f\u4e0d\u53ef\u80fd\u8655\u7406\u3002"));
                        break;
                    }
                    r pssl = new r();
                    pssl.a(sellObjectId);
                    pssl.c(sellPrice);
                    pssl.b(sellCount);
                    sellList.add(pssl);
                }
                ++i2;
            }
            int buyTotalCount = this.d();
            int i3 = 0;
            while (i3 < buyTotalCount) {
                int buyObjectId = this.b();
                int buyPrice = this.b();
                int buyCount = this.b();
                checkItem = pc.j().e(buyObjectId);
                if (checkItem != null) {
                    if (!checkItem.a().s()) {
                        tradable = false;
                        pc.a(new ei(String.valueOf(checkItem.a().h()) + "\u9019\u662f\u4e0d\u53ef\u80fd\u8655\u7406\u3002"));
                    }
                    if (checkItem.F() >= 128) {
                        pc.a(new ds(210, checkItem.a().h()));
                        return;
                    }
                    if (checkItem.E() > 1 && !checkItem.a().aF()) {
                        pc.a(new ei("\u6b64\u7269\u54c1\u975e\u5806\u758a\uff0c\u4f46\u7570\u5e38\u5806\u758a\u7121\u6cd5\u4ea4\u6613\u3002"));
                        return;
                    }
                    for (t petNpc : pc.ek().values()) {
                        if (!(petNpc instanceof v)) continue;
                        v pet = (v)petNpc;
                        if (checkItem.fr() != pet.k()) continue;
                        tradable = false;
                        pc.a(new ds(1187));
                        break;
                    }
                    for (e doll : pc.el().values()) {
                        if (doll.f() != checkItem.fr()) continue;
                        tradable = false;
                        pc.a(new ds(1181));
                        break;
                    }
                    q psbl = new q();
                    psbl.a(buyObjectId);
                    psbl.c(buyPrice);
                    psbl.b(buyCount);
                    buyList.add(psbl);
                }
                ++i3;
            }
            if (!tradable) {
                sellList.clear();
                buyList.clear();
                pc.g(false);
                pc.a(new ak(pc.fr(), 3));
                pc.b(new ak(pc.fr(), 3));
                return;
            }
            byte[] chat = this.h();
            pc.a(chat);
            pc.g(true);
            pc.a(new al(pc.fr(), 70, chat));
            pc.b(new al(pc.fr(), 70, chat));
            int SelectedPolyNum = 0;
            try {
                SelectedPolyNum = Integer.parseInt(new String(chat, "utf8").split("tradezone")[1].substring(0, 1));
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ae.a(pc, SelectedPolyNum);
        } else if (type == 1) {
            sellList.clear();
            buyList.clear();
            pc.g(false);
            pc.a(new ak(pc.fr(), 3));
            pc.b(new ak(pc.fr(), 3));
            ae.a(pc);
        }
    }

    @Override
    public String a() {
        return b;
    }
}

