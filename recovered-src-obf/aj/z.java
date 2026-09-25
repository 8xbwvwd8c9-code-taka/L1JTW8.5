/*
 * Decompiled with CFR 0.152.
 */
package aj;

import ai.d;
import aj.cv;
import am.a;
import ao.be;
import ao.o;
import be.cd;
import be.u;
import bh.v;
import bi.c;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class z
extends cv {
    private static final Logger b = Logger.getLogger(z.class.getName());
    public static final int[] a;
    private static final int[] c;
    private static final int d = 32780;
    private static final int e = 32825;
    private static final short f = 7783;

    static {
        int[] nArray = new int[8];
        nArray[1] = 61;
        nArray[2] = 138;
        nArray[3] = 734;
        nArray[4] = 2786;
        nArray[5] = 6658;
        nArray[6] = 6671;
        nArray[7] = 12490;
        a = nArray;
        c = new int[]{1, 48, 37, 1186, 2796, 6661, 6650, 12494};
    }

    public z(byte[] abyte0, bj.d client) throws Exception {
        super(abyte0);
        int statusAmount;
        ap.u pc = new ap.u();
        String name = this.g();
        bh.a account = client.e();
        int characterSlot = account.k();
        int maxAmount = l1j.server.a.au + characterSlot;
        name = name.replaceAll("\\s", "").replaceAll("\u3000", "");
        if (name.length() == 0 || z.b(name)) {
            client.a(new u(9));
            return;
        }
        if (o.a().c(name)) {
            client.a(new u(6));
            return;
        }
        if (client.e().k() >= maxAmount) {
            client.a(new u(21));
            System.out.println("account: " + client.a() + " \u8d85\u904e\u89d2\u8272\u4e0a\u9650\u6578\u76ee: " + maxAmount + "\u3002");
            return;
        }
        pc.e(name);
        pc.ad(this.c());
        pc.aj(this.c());
        if (pc.ay() < 0 || pc.ay() >= a.length) {
            client.a(new u(21));
            return;
        }
        pc.i(pc.aJ() == 0 ? a[pc.ay()] : c[pc.ay()]);
        pc.o(this.c());
        pc.q(this.c());
        pc.p(this.c());
        pc.t(this.c());
        pc.r(this.c());
        pc.s(this.c());
        boolean isStatusError = false;
        int originalStr = pc.aC().a()[0];
        int originalDex = pc.aC().a()[1];
        int originalCon = pc.aC().a()[2];
        int originalInt = pc.aC().a()[3];
        int originalWis = pc.aC().a()[4];
        int originalCha = pc.aC().a()[5];
        int originalAmount = pc.aC().b();
        if (pc.bf() < originalStr || pc.bh() < originalDex || pc.bg() < originalCon || pc.bk() < originalWis || pc.bi() < originalCha || pc.bj() < originalInt || pc.bf() > originalStr + originalAmount || pc.bh() > originalDex + originalAmount || pc.bg() > originalCon + originalAmount || pc.bk() > originalWis + originalAmount || pc.bi() > originalCha + originalAmount || pc.bj() > originalInt + originalAmount) {
            isStatusError = true;
        }
        if ((statusAmount = pc.eB() + pc.eC() + pc.eA() + pc.eD() + pc.ez() + pc.eE()) != 75 || isStatusError) {
            client.a(new u(21));
            System.out.println("[\u9ede\u6578ERROR]: statusAmount=" + statusAmount + " / isStatusError=" + isStatusError);
        } else {
            client.a(new u(2));
            z.a(client, pc);
        }
    }

    private static void a(bj.d client, ap.u pc) throws IOException, Exception {
        pc.cF(ai.d.a().d());
        pc.n();
        pc.cG(32780);
        pc.cH(32825);
        pc.cE(7783);
        int initHp = bi.c.a(pc);
        int initMp = bi.c.c(pc);
        pc.m(initHp);
        pc.a(initHp);
        pc.n(initMp);
        pc.i_(initMp);
        pc.c_(40);
        pc.W();
        pc.Y();
        if (pc.B()) {
            v skill = be.a().a(4);
            be.a().a(pc.fr(), skill.a(), skill.b(), 0, 0);
            pc.a(new be.d(pc, 4));
        }
        ao.d.a().a(pc);
        pc.d(client.a());
        o.a().a(pc);
        o.a().d(pc);
        client.a(new cd(pc));
    }

    private static boolean a(String s2) {
        boolean flag = true;
        char[] ac2 = s2.toCharArray();
        for (int i2 = 0; i2 < ac2.length; ++i2) {
            if (Character.isLetterOrDigit(ac2[i2])) continue;
            flag = false;
            break;
        }
        return flag;
    }

    private static boolean b(String name) {
        int numOfNameBytes = 0;
        if (am.a.a().a(name)) {
            return true;
        }
        try {
            numOfNameBytes = name.getBytes(l1j.server.a.k).length;
        }
        catch (UnsupportedEncodingException e2) {
            b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        if (z.a(name)) {
            return false;
        }
        if (5 < numOfNameBytes - name.length() || 12 < numOfNameBytes) {
            return false;
        }
        return !am.a.a().a(name);
    }

    @Override
    public String a() {
        return "C_CreateChar";
    }
}

