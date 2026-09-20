/*
 * Decompiled with CFR 0.152.
 */
package l1r.al;

import java.util.ArrayList;
import java.util.Collection;
import l1r.al.L1CommandExecutor;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1Recall
implements L1CommandExecutor {
    private L1Recall() {
    }

    public static L1CommandExecutor a() {
        return new L1Recall();
    }

    @Override
    public void a(L1PcInstance pc, String cmdName, String arg) {
        try {
            Collection<L1PcInstance> targets = null;
            if (arg.equalsIgnoreCase("all")) {
                targets = L1World.a().c();
            } else {
                targets = new ArrayList<L1PcInstance>();
                L1PcInstance tg = L1World.a().a(arg);
                if (tg == null) {
                    pc.a(new S_SystemMessage("ID\u4e0d\u5b58\u5728\u3002"));
                    return;
                }
                targets.add(tg);
            }
            boolean c = false;
            for (L1PcInstance target : targets) {
                if (target.fr() == pc.fr()) continue;
                L1Teleport.a(target, pc, 10);
                pc.a(new S_SystemMessage(target.et() + "\u6210\u529f\u88ab\u60a8\u53ec\u559a\u56de\u4f86\u3002"));
                target.a(new S_SystemMessage("\u60a8\u88ab\u53ec\u559a\u5230GM\u8eab\u908a\u3002"));
            }
        }
        catch (Exception e) {
            pc.a(new S_SystemMessage("\u8acb\u8f38\u5165: " + cmdName + " all|\u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}
