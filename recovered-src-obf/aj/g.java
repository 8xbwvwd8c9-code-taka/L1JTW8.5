/*
 * Decompiled with CFR 0.152.
 */
package aj;

import ai.c;
import aj.cv;
import ao.a;
import ao.ah;
import ao.b;
import ap.u;
import aq.aq;
import aq.v;
import aq.x;
import be.bu;
import be.ca;
import be.ce;
import be.cm;
import be.ds;
import bj.d;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g
extends cv {
    private static final Logger a = Logger.getLogger(g.class.getName());

    public g(byte[] decrypt, d client) {
        super(decrypt);
        int action = this.c();
        switch (action) {
            case 11: {
                break;
            }
            case 28: {
                break;
            }
            case 6: {
                String accountInfo = this.g().toLowerCase();
                String password = this.g();
                String ip = client.g();
                String accountName = "";
                String host = "";
                String version = "";
                if (accountInfo.contains(":")) {
                    accountName = accountInfo.split(":")[0];
                    host = accountInfo.split(":")[1];
                } else {
                    accountName = accountInfo;
                }
                if (password.contains(":")) {
                    String[] splite = password.split(":");
                    password = splite[0];
                    version = splite[1];
                }
                if (l1j.server.a.aT && !l1j.server.a.aU.equalsIgnoreCase(version)) {
                    a.log(Level.SEVERE, "\u767b\u5165\u5668\u7248\u672c\u932f\u8aa4\u3002\u76ee\u524d\u7248\u672c=" + l1j.server.a.aU + " \u4f7f\u7528\u7248\u672c=" + version);
                    client.a(new bu(39));
                    return;
                }
                if (!accountName.matches("[0-9a-zA-Z]*")) {
                    a.log(Level.SEVERE, "\u4e0d\u5408\u6cd5\u7684\u5e33\u865f\u5b57\u5143\u3002account=" + accountName + " ip=" + ip);
                    client.a(new bu(9));
                    return;
                }
                bh.a account = ao.a.a().c(accountName);
                if (account == null) {
                    if (!l1j.server.a.n) {
                        a.log(Level.SEVERE, "\u76ee\u524d\u4e0d\u53ef\u5275\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
                        client.a(new bu(155));
                        return;
                    }
                    if (!host.equals("")) {
                        if (!ao.a.a().b(host)) {
                            a.log(Level.SEVERE, "\u5275\u5e33\u865f\u6578\u91cf\u8d85\u904e\u9650\u5236\u3002account=" + accountName + " mac=" + host);
                            client.a(new bu(156));
                            return;
                        }
                    } else if (!ao.a.a().a(ip)) {
                        a.log(Level.SEVERE, "\u5275\u5e33\u865f\u6578\u91cf\u8d85\u904e\u9650\u5236\u3002account=" + accountName + " ip=" + ip);
                        client.a(new bu(156));
                        return;
                    }
                    account = ao.a.a().a(accountName, password, ip, host);
                }
                if (!account.a(password)) {
                    a.log(Level.SEVERE, "\u5bc6\u78bc\u932f\u8aa4\u3002account=" + accountName + " ip=" + ip);
                    client.a(new bu(149));
                    return;
                }
                if (account.n()) {
                    boolean isNetConnectionOnline = false;
                    for (d netConnection : c.a().c()) {
                        if (netConnection.a() == null || !netConnection.a().equals(accountName)) continue;
                        a.log(Level.SEVERE, "\u5df2\u4f7f\u7528\u4e2d-\u8e22\u6389\u91cd\u8907\u7684\u9023\u7dda\u53ca\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
                        isNetConnectionOnline = true;
                        netConnection.a(0);
                    }
                    if (!isNetConnectionOnline) {
                        ao.a.a().a(account, false);
                        ao.a.a().b(account, false);
                        ao.a.a().e(account.d());
                        a.log(Level.SEVERE, "\u5df2\u4f7f\u7528\u4e2d-\u8e22\u6389\u91cd\u8907\u7684\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
                    }
                    client.a(new bu(22));
                    return;
                }
                if (account.j()) {
                    a.log(Level.SEVERE, "\u7981\u6b62\u767b\u5165\u7684\u5e33\u865f\u5617\u8a66\u767b\u5165\u3002account=" + accountName + " ip=" + ip);
                    client.a(new bu(153));
                    return;
                }
                if (l1j.server.a.u > 0) {
                    int count = 0;
                    for (d tempClient : c.a().c()) {
                        if (!ip.equalsIgnoreCase(tempClient.g())) continue;
                        ++count;
                    }
                    if (count > l1j.server.a.u) {
                        a.log(Level.SEVERE, "\u8d85\u904e\u591a\u958b\u8a2d\u5b9a-\u62d2\u7d55\u767b\u5165\u3002account=" + accountName + " ip=" + ip);
                        client.a(new bu(38));
                        return;
                    }
                }
                try {
                    account.d(ip);
                    account.f(host);
                    ao.a.a().a(account, client);
                    client.a(account);
                    client.a(new bu(0));
                    if (l1j.server.a.x) {
                        b.a().a(client);
                        client.a(new ce(client.b()));
                        break;
                    }
                    v.a(client);
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, "\u767b\u5165\u5e33\u865f\u6642\u7570\u5e38\u3002account=" + accountName + " ip=" + ip);
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    client.a(new bu(158));
                    client.c();
                }
                break;
            }
            case 14: {
                u pc = client.f();
                int giftID = pc.cZ();
                ah.a(pc, 615318 + giftID, 1);
                int nextGiftID = giftID == 25011 ? 25009 : giftID + 1;
                int giftTime = 180;
                if (nextGiftID == 25010) {
                    giftTime = 900;
                } else if (nextGiftID == 25011) {
                    giftTime = 3600;
                }
                pc.j(nextGiftID, giftTime * 1000);
                pc.a(new cm(150, nextGiftID - 25008, giftTime));
                break;
            }
            case 13: {
                u pc = client.f();
                int type = this.c();
                if (type == 0) {
                    String name = this.g();
                    u master = aq.a().a(name);
                    if (master == null || pc.et().equals(name)) {
                        pc.a(new ds(2965));
                        return;
                    }
                    if (master.ev() < 70 || master.bB(25007)) {
                        pc.a(new ds(2974));
                        return;
                    }
                    if (pc.ev() >= 70 || pc.cE() == -1 || pc.bB(25007)) {
                        pc.a(new ds(2975));
                        return;
                    }
                    if (x.a().c(master.fr()) >= 4) {
                        pc.a(new ds(2972));
                        return;
                    }
                    pc.a(new ca(2967, name));
                    pc.am(master.fr());
                    break;
                }
                if (type == 1) {
                    String name = this.g();
                    int masterid = pc.cE() == -1 ? pc.fr() : pc.cE();
                    x.a().a(masterid, name);
                    pc.j(25007, 28800000);
                    break;
                }
                if (type != 2) break;
                int masterid = pc.cE();
                if (masterid == -1) {
                    pc.a(new cm(146, pc.fr()));
                    break;
                }
                if (pc.cE() > 0) {
                    pc.a(new cm(146, masterid));
                    break;
                }
                pc.a(new ds(2979));
                break;
            }
            case 15: {
                int type = this.c();
                u u2 = client.f();
            }
        }
    }

    @Override
    public String a() {
        return "C_AuthLogin";
    }
}

