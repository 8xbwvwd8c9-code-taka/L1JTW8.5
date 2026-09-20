/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_NPCPack;
import l1r.be.S_SkillSound;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;

public class L1GfxInstance
extends L1NpcInstance {
    private static final Logger y = Logger.getLogger(L1GfxInstance.class.getName());
    private boolean z = false;
    private int A = 0;

    public L1GfxInstance(L1Npc template) {
        super(template);
    }

    @Override
    public void c(L1PcInstance pc) {
        pc.a(new S_SkillSound(pc.fr(), this.A));
        L1Character cha = new L1Character();
        cha.cG(pc.fs() + 5);
        cha.cH(pc.ft() + 5);
        pc.ct(pc.a((L1Object)cha));
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_NPCPack(this));
        perceivedFrom.a(new S_SkillSound(this.fr(), this.A));
        if (!this.z) {
            this.z = true;
            new L1R_a().a();
        }
    }

    private void h() {
        for (L1PcInstance pc : L1World.a().f(this)) {
            pc.a(new S_SkillSound(this.fr(), this.A));
        }
    }

    public int f() {
        return this.A;
    }

    public void b(int i) {
        this.A = i;
    }

    private class L1R_a
    implements Runnable {
        private L1R_a() {
        }

        public void a() {
            GeneralThreadPool.a().a(this);
        }

        @Override
        public void run() {
            try {
                while (L1GfxInstance.this.z) {
                    L1GfxInstance.this.h();
                    Thread.sleep(2000L);
                }
            }
            catch (Exception e) {
                y.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }
}
