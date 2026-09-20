/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ai.b;
import ao.q;
import ap.s;
import ap.u;
import aq.aa;
import aq.aq;
import aq.i;
import be.ab;
import be.cg;
import be.cm;
import be.dc;
import be.ds;
import java.util.ArrayList;
import java.util.Arrays;
import l1j.server.a;

public class g {
    public static void a(u pc, int chatType, String chatText, int chatCount, String targetName) {
        if (pc.bB(64) || pc.bB(161) || pc.bB(1007)) {
            return;
        }
        if (pc.bB(1005)) {
            pc.a(new ds(242));
            return;
        }
        if (chatType == 0 || chatType == 2) {
            int range;
            if (chatText.startsWith(".") && chatText.length() > 1 && pc.l()) {
                String cmd = chatText.substring(1);
                b.a().a(pc, cmd);
                return;
            }
            if (pc.bN()) {
                return;
            }
            int n2 = range = chatType == 2 ? 50 : -1;
            if (!pc.cd().c(pc.et())) {
                pc.a(new dc(pc, chatText, chatType, chatCount));
            }
            for (u listner : aq.a().c(pc, range)) {
                if (listner.cd().c(pc.et())) continue;
                listner.a(new dc(pc, chatText, chatType, chatCount));
            }
            for (aa obj : pc.eq()) {
                s mob;
                if (!(obj instanceof s) || !(mob = (s)obj).U_().X() || !mob.et().equals(pc.et()) || mob.eX()) continue;
                for (u listner : aq.a().c(mob, range)) {
                    listner.a(new cg(mob, chatText, chatType));
                }
            }
            ao.aa.a().a(pc, chatType == 2 ? "\u5927\u53eb" : "\u4e00\u822c", chatText);
        } else if (chatType == 1) {
            if (pc.bB(1005)) {
                pc.a(new ds(242));
                return;
            }
            if (pc.ev() < a.O) {
                pc.a(new ds(404, String.valueOf(a.O)));
                return;
            }
            u whisperTo = aq.a().a(targetName);
            if (whisperTo == null) {
                pc.a(new ds(73, targetName));
                return;
            }
            if (whisperTo.equals(pc)) {
                return;
            }
            if (whisperTo.cd().c(pc.et())) {
                pc.a(new ds(117, whisperTo.et()));
                return;
            }
            if (!whisperTo.ck()) {
                pc.a(new ds(205, whisperTo.et()));
                return;
            }
            pc.a(new dc(whisperTo, chatText, chatCount));
            whisperTo.a(new dc(pc, chatText, chatType, chatCount));
            ao.aa.a().a(pc, "\u5bc6\u8a9e\u7d66(" + whisperTo.et() + ")", chatText);
        } else if (chatType == 3) {
            g.a(pc, chatText, chatType, chatCount);
            ao.aa.a().a(pc, "\u5168\u9ad4", chatText);
        } else if (chatType == 4) {
            if (pc.aF() == 0) {
                return;
            }
            i clan = q.a().a(pc.aF());
            for (u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !listner.cm()) continue;
                listner.a(new dc(pc, chatText, chatType, chatCount));
            }
            ao.aa.a().a(pc, "\u8840\u76df", chatText);
        } else if (chatType == 11) {
            if (!pc.q()) {
                return;
            }
            for (u listner : pc.aL().c()) {
                if (listner.cd().c(pc.et()) || !listner.cn()) continue;
                listner.a(new dc(pc, chatText, chatType, chatCount));
            }
            ao.aa.a().a(pc, "\u7d44\u968a", chatText);
        } else if (chatType == 12) {
            g.a(pc, chatText, chatType, chatCount);
            ao.aa.a().a(pc, "\u8cb7\u8ce3", chatText);
        } else if (chatType == 13) {
            if (pc.aF() == 0) {
                return;
            }
            ArrayList<Integer> access = new ArrayList<Integer>(Arrays.asList(9, 4, 10, 3, 6));
            i clan = q.a().a(pc.aF());
            if (!access.contains(pc.aH())) {
                return;
            }
            for (u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !access.contains(listner.aH())) continue;
                listner.a(new dc(pc, chatText, chatType, chatCount));
            }
            ao.aa.a().a(pc, "\u806f\u5408\u8840\u76df", chatText);
        } else if (chatType == 14) {
            if (!pc.r()) {
                return;
            }
            u[] uArray = pc.aM().e();
            int n3 = uArray.length;
            int clan = 0;
            while (clan < n3) {
                u listner = uArray[clan];
                if (!listner.cd().c(pc.et())) {
                    listner.a(new dc(pc, chatText, chatType, chatCount));
                }
                ++clan;
            }
            ao.aa.a().a(pc, "\u804a\u5929\u7d44\u968a", chatText);
        } else if (chatType == 15) {
            if (pc.aF() == 0) {
                return;
            }
            i clan = q.a().a(pc.aF());
            for (u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !listner.cm()) continue;
                listner.a(new dc(pc, chatText, chatType, chatCount));
            }
            ao.aa.a().a(pc, "\u540c\u76df", chatText);
        } else if (chatType == 17) {
            if (pc.aF() == 0) {
                return;
            }
            if (pc.aH() == 10 || pc.aH() == 4) {
                i clan = q.a().a(pc.aF());
                for (u listner : clan.b()) {
                    listner.a(new dc(pc, chatText, chatType, chatCount));
                }
            }
            ao.aa.a().a(pc, "\u8840\u76df\u738b\u65cf\u516c\u544a", chatText);
        }
        if (!pc.l()) {
            pc.ab();
        }
    }

    private static void a(u pc, String chatText, int chatType, int chatCount) {
        if (pc.l()) {
            aq.a().a(new ab(pc, chatText, chatType));
            return;
        }
        if (pc.ev() < a.N) {
            pc.a(new ds(195, String.valueOf(a.N)));
            return;
        }
        if (!aq.a().k()) {
            pc.a(new ds(510));
            return;
        }
        if (pc.fj() < 6) {
            pc.a(new ds(462));
            return;
        }
        for (u listner : aq.a().c()) {
            if (listner.cd().c(pc.et()) || chatType == 12 && !listner.cl() || chatType == 3 && !listner.co()) continue;
            listner.a(new dc(pc, chatText, chatType, chatCount));
        }
        pc.c_(pc.fj() - 5);
        pc.a(new cm(11, pc.fj()));
    }
}

