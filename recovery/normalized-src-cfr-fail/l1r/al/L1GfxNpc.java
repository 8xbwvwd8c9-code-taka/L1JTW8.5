/*
 * Decompiled with CFR 0.152.
 */
package l1r.al;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.al.L1CommandExecutor;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.NpcTable;
import l1r.ap.L1GfxInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;

public class L1GfxNpc
implements L1CommandExecutor {
    private static final Logger a = Logger.getLogger(L1GfxNpc.class.getName());

    private L1GfxNpc() {
    }

    public static L1CommandExecutor a() {
        return new L1GfxNpc();
    }

    @Override
    public void a(L1PcInstance pc, String cmdName, String arg) {
        try {
            int i;
            String type = arg;
            int mapid = 4;
            int begin_x = 32000;
            int begin_y = 33553;
            int range = 6;
            for (L1Object obj : L1World.a().b(4).values()) {
                L1NpcInstance npc;
                if (!(obj instanceof L1NpcInstance) || (npc = (L1NpcInstance)obj).z() != 45001) continue;
                L1World.a().d(npc);
            }
            ArrayList<int[]> locList = new ArrayList<int[]>();
            int j = 33553;
            while (j < 34053) {
                int i2 = 32000;
                while (i2 < 32500) {
                    locList.add(new int[]{i2, j});
                    i2 += 6;
                }
                j += 6;
            }
            int count = 0;
            ArrayList<Object> findList = new ArrayList();
            if (type.equals("effect")) {
                findList = ListSprReader__obf_c.a().a(true);
            } else if (type.equals("effect2")) {
                findList = ListSprReader__obf_c.a().a(false);
            } else if (type.equals("mob")) {
                findList = ListSprReader__obf_c.a().b();
            } else if (type.equals("poly")) {
                int[] nArray = L1SpeedChecker.e;
                int n = L1SpeedChecker.e.length;
                int n2 = 0;
                while (n2 < n) {
                    i = nArray[n2];
                    findList.add(i);
                    ++n2;
                }
            } else {
                findList = ListSprReader__obf_c.a().e(Integer.parseInt(type));
            }
            Collections.sort(findList);
            Iterator<Object> iterator = findList.iterator();
            while (iterator.hasNext()) {
                i = (Integer)iterator.next();
                if (count >= locList.size()) continue;
                int[] loc = (int[])locList.get(count);
                int x = loc[0];
                int y = loc[1];
                L1Npc l1npc = NpcTable.a().a(45001);
                if (l1npc != null) {
                    L1NpcInstance temp = new L1NpcInstance(l1npc);
                    temp.cw(i);
                    if (type.equals("effect") || type.equals("effect2")) {
                        temp = new L1GfxInstance(l1npc);
                        temp.cw(l1npc.z());
                        ((L1GfxInstance)temp).b(i);
                    }
                    temp.cF(IdFactory.a().c());
                    temp.a("\u7de8\u865f: " + i);
                    temp.cE(4);
                    temp.ct(5);
                    temp.cG(x);
                    temp.cH(y);
                    L1World.a().a(temp);
                    L1World.a().c(temp);
                    ++count;
                    continue;
                }
                pc.a(new S_SystemMessage("\u5730\u5716\u6c92\u7a7a\u4f4d\u653e\u4e86!!"));
            }
            L1Teleport.a(pc, 32000, 33553, 4, 5, true);
        }
        catch (Exception e) {
            pc.a(new S_SystemMessage(String.valueOf(cmdName) + " + \u8981\u5c0b\u627e\u7684type\u3002"));
            a.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void a(int x, int y) {
        L1Npc l1npc = NpcTable.a().a(45060);
        if (l1npc != null) {
            L1NpcInstance temp = new L1NpcInstance(l1npc);
            temp.cF(IdFactory.a().c());
            temp.a(" ");
            temp.cE(4);
            temp.ct(5);
            temp.cG(x);
            temp.cH(y);
            L1World.a().a(temp);
            L1World.a().c(temp);
        }
    }
}
