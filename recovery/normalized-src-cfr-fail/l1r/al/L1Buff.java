/*
 * Decompiled with CFR 0.152.
 */
package l1r.al;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import l1r.al.L1CommandExecutor;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bi.LineageUtil;

public class L1Buff
implements L1CommandExecutor {
    private L1Buff() {
    }

    public static L1CommandExecutor a() {
        return new L1Buff();
    }

    @Override
    public void a(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            Collection<Object> players = null;
            String s = tok.nextToken();
            if (s.equalsIgnoreCase("me")) {
                players = new ArrayList<L1PcInstance>();
                players.add(pc);
                s = tok.nextToken();
            } else if (s.equalsIgnoreCase("all")) {
                players = L1World.a().c();
                s = tok.nextToken();
            } else {
                players = L1World.a().f(pc);
            }
            int skillId = Integer.parseInt(s);
            int time = 0;
            if (tok.hasMoreTokens()) {
                time = Integer.parseInt(tok.nextToken());
            }
            L1SkillExecutor executor = LineageUtil.a(skillId);
            for (L1PcInstance l1PcInstance : players) {
                executor.a((L1Character)l1PcInstance, time);
            }
        }
        catch (Exception e) {
            pc.a(new S_SystemMessage("\u8acb\u8f38\u5165 " + cmdName + " [all|me] skillId time\u3002"));
        }
    }
}
