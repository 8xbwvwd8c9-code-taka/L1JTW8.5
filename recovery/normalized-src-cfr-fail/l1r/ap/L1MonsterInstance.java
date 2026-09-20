/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.am.MonsterListReader;
import l1r.ao.DoorTable;
import l1r.ao.DropTable;
import l1r.ao.ItemTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1GuardianSoul;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.as.L1Dragon;
import l1r.as.L1ThebesBattle;
import l1r.be.S_ChangeName;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Liquor;
import l1r.be.S_NPCPack;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bf.S_048;
import l1r.bf.S_057;
import l1r.bf.S_079;
import l1r.bf.S_150;
import l1r.bf.S_211;
import l1r.bh.L1Npc;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcExp;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1MonsterInstance
extends L1NpcInstance {
    private static final Logger y = Logger.getLogger(L1MonsterInstance.class.getName());
    private boolean z = false;
    private boolean A = false;
    private final Object B = new Object();
    private boolean C = false;

    public L1MonsterInstance(L1Npc template) {
        super(template);
    }

    @Override
    public void Y_() {
        if (!this.w && this.m != null) {
            this.e(1, 40);
            this.a(true);
        }
        if (this.ea() * 100 / this.ew() < 40) {
            this.e(0, 50);
        }
    }

    @Override
    public void a(boolean isChangeShape) {
        if (!this.U_().X()) {
            return;
        }
        boolean updateShape = false;
        if (!isChangeShape) {
            this.a(this.U_().A());
            this.cr(this.U_().p());
            this.cw(this.G());
            updateShape = true;
        } else if (!this.z && this.m instanceof L1PcInstance) {
            this.v(300);
            L1PcInstance tpc = (L1PcInstance)this.m;
            this.e(tpc.et());
            this.a(tpc.et());
            this.cr(tpc.fa());
            this.cw(tpc.aB());
            if (tpc.aB() != 6671 && tpc.aB() != 48) {
                this.cq(4);
            } else {
                this.cq(11);
            }
            this.z = true;
            updateShape = true;
        }
        this.m(ListSprReader__obf_c.a().a(this.fe(), this.eY()));
        this.n(ListSprReader__obf_c.a().a(this.fe(), this.eY() + 1));
        if (!updateShape) {
            return;
        }
        this.b(new S_ChangeName(this.fr(), this.T()));
        this.b(new S_NpcChangeShape(this.fr(), this.fe(), this.fa(), this.eY()));
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        if (this.ea() <= 0 && (this.fe() == 7864 || this.fe() == 7869)) {
            return;
        }
        perceivedFrom.a(new S_NPCPack(this));
        perceivedFrom.c((L1Object)this);
        this.Z_();
    }

    @Override
    public void c() {
        L1PcInstance lastTarget = null;
        if (this.m instanceof L1PcInstance) {
            lastTarget = (L1PcInstance)this.m;
            this.s();
        }
        L1PcInstance targetPlayer = null;
        for (L1PcInstance pc : L1World.a().f(this)) {
            if (pc == lastTarget || pc.ea() <= 0 || pc.eX() || pc.l() || pc.bN()) continue;
            if (this.z() == 45600 && (pc.x() || pc.C() || pc.fe() != pc.aB())) {
                targetPlayer = pc;
                break;
            }
            if (this.U_().aa() < 0 && pc.Q() >= 1 || this.U_().aa() > 0 && pc.Q() <= -1 || pc.fe() == 6034 && this.U_().aa() < 0 || pc.fe() == 6035 && this.U_().aa() > 0 || pc.fe() == 6035 && this.U_().b() == 46070 || pc.fe() == 6035 && this.U_().b() == 46072) continue;
            if (!this.V_() && !this.W() && this.U_().F() < 0 && this.U_().G() < 0) {
                if (pc.fa() >= -1000) continue;
                targetPlayer = pc;
                break;
            }
            if (pc.ff() && !this.V()) continue;
            if (pc.bB(67)) {
                if (this.W()) {
                    targetPlayer = pc;
                    break;
                }
            } else if (this.V_()) {
                targetPlayer = pc;
                break;
            }
            if (pc.fe() != this.U_().F() && pc.fe() != this.U_().G()) continue;
            targetPlayer = pc;
            break;
        }
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    @Override
    public void a(L1Character cha) {
        if (cha == null) {
            return;
        }
        if (this.n.b()) {
            this.n.a(cha, 0);
            this.d();
        }
    }

    @Override
    public void Z_() {
        if (this.ae()) {
            return;
        }
        if (!this.A) {
            DropTable.a().a((L1NpcInstance)this, this.y());
            this.y().f();
            this.A = true;
        }
        this.w = false;
        this.q();
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void a(L1Character attacker, int mpDamage) {
        int newMp;
        if (mpDamage <= 0 || this.eX()) {
            return;
        }
        this.c(attacker, mpDamage);
        this.Z_();
        if (attacker instanceof L1PcInstance) {
            this.c((L1PcInstance)attacker, this.U_().D());
        }
        if ((newMp = this.eb() - mpDamage) < 0) {
            newMp = 0;
        }
        this.i_(newMp);
    }

    @Override
    public void b(L1Character attacker, int damage) {
        if (this.ea() <= 0) {
            if (!this.eX()) {
                this.cq(8);
                GeneralThreadPool.a().a(new L1R_a(attacker));
            }
            return;
        }
        if (this.ac() == 1 || this.ac() == 2) {
            return;
        }
        if (damage >= 0) {
            if (!(attacker instanceof L1EffectInstance)) {
                this.c(attacker, damage);
            }
            this.bz(66);
            this.bz(153);
        }
        this.Z_();
        if (attacker instanceof L1PcInstance) {
            if (damage > 0) {
                ((L1PcInstance)attacker).a(this);
            }
            this.c((L1PcInstance)attacker, this.U_().D());
        }
        if (this.z() >= 97044 && this.z() <= 97046 && attacker.bB(4011)) {
            damage = (int)((double)damage * 1.5);
        } else if (this.z() >= 97094 && this.z() <= 97096 && attacker.bB(4012)) {
            damage = (int)((double)damage * 1.5);
        }
        int newHp = this.ea() - damage;
        if (newHp <= 0) {
            int transformId = this.U_().ab();
            if (transformId == -1 || this.fp() == 1931 && Random.a(1000) > 3) {
                GeneralThreadPool.a().a(new L1R_a(attacker));
            } else {
                this.g_(transformId);
            }
            return;
        }
        this.a(newHp);
        this.l();
    }

    @Override
    public synchronized void a(int i) {
        int currentHp = i;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        if (this.ew() > this.ea()) {
            this.u();
        }
    }

    @Override
    public synchronized void i_(int i) {
        int currentMp = i;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        if (this.ex() > this.eb()) {
            this.w();
        }
    }

    private void f(L1Character lastAttacker) {
        if (lastAttacker == null) {
            return;
        }
        if (lastAttacker instanceof L1EffectInstance && !this.n.b()) {
            lastAttacker = this.n.c();
        }
        L1PcInstance pc = null;
        if (lastAttacker instanceof L1PcInstance) {
            pc = (L1PcInstance)lastAttacker;
        } else if (lastAttacker instanceof L1PetInstance) {
            pc = (L1PcInstance)((L1PetInstance)lastAttacker).M();
        } else if (lastAttacker instanceof L1SummonInstance) {
            pc = (L1PcInstance)((L1SummonInstance)lastAttacker).M();
        }
        if (pc == null) {
            return;
        }
        CalcExp.a(pc, this, this.n);
        this.k();
        this.f(pc);
        this.g(pc);
    }

    private void k() {
        block8: {
            int npcId;
            block9: {
                block7: {
                    npcId = this.U_().b();
                    if (npcId != 45640 || npcId == 45640 && this.fe() == 2332) {
                        L1GuardianSoul.a().a(this);
                        DropTable.a().a((L1NpcInstance)this, this.l);
                    }
                    if (npcId != 190026) break block7;
                    for (L1Object obj : L1World.a().b(this.fp()).values()) {
                        L1PcInstance tpc;
                        if (!(obj instanceof L1PcInstance) || !this.l.a(tpc = (L1PcInstance)obj) || tpc.eX()) continue;
                        ItemTable.a(tpc, 640305, 1, this.T());
                    }
                    break block8;
                }
                if (npcId != 97008 && npcId != 97046 && npcId != 97096) break block9;
                int coinid = 640639;
                if (npcId == 97046) {
                    coinid = 640640;
                } else if (npcId == 97096) {
                    coinid = 640641;
                }
                for (L1Object obj : L1World.a().b(this.fp()).values()) {
                    L1PcInstance tpc;
                    if (!(obj instanceof L1PcInstance) || !this.l.a(tpc = (L1PcInstance)obj) || tpc.eX()) continue;
                    ItemTable.a(tpc, coinid, 1, this.T());
                }
                break block8;
            }
            if (npcId != 46123 && npcId != 46124 && npcId != 190577) break block8;
            for (L1Object obj : L1World.a().b(this.fp()).values()) {
                L1PcInstance tpc;
                if (!(obj instanceof L1PcInstance) || !this.l.a(tpc = (L1PcInstance)obj)) continue;
                ItemTable.a(tpc, 40308, 5000000, this.T());
                ItemTable.a(tpc, 640621, 50000, this.T());
            }
        }
    }

    private void f(L1PcInstance pc) {
        int karma = this.P();
        if (karma != 0) {
            int karmaSign = Integer.signum(karma);
            int pcKarmaLevel = pc.Q();
            int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
            if (pcKarmaLevelSign != 0 && karmaSign != pcKarmaLevelSign) {
                karma *= 5;
            }
            pc.B((int)((double)karma * Config.D));
        }
    }

    private void g(L1PcInstance pc) {
        int rnd;
        if (pc.bB(5006)) {
            if (pc.dW() < 30) {
                pc.bv(pc.dW() + 1);
            }
            pc.a(new S_PacketBox(204, pc));
        } else if (pc.cC() > 0 && (rnd = Random.a(1000)) < 10) {
            pc.bv(1);
            pc.j(5006, 50000);
            pc.a(new S_PacketBox(204, pc));
        }
        if (this.U_().an() > 0) {
            int index = this.U_().an() - 1;
            int count = pc.dQ()[index] + 1;
            int stage = MonsterListReader.a().a(index, pc.dQ()[index]);
            pc.dQ()[index] = count;
            pc.a(new S_ProtoBuffers(567, this.U_().an(), count));
            int new_stage = MonsterListReader.a().a(index, count);
            if (new_stage > stage) {
                int idx = index * 3 + new_stage - 1;
                pc.a(new S_ProtoBuffers(568, idx + 1, pc.fr()));
            }
            if (pc.dY() != null) {
                int i = 0;
                while (i < pc.dY().length) {
                    int[] data = pc.dY()[i];
                    if (data[0] == this.U_().an()) {
                        if (data[2] >= data[1]) break;
                        data[2] = data[2] + 1;
                        pc.a(new S_ProtoBuffers(813, i / 3, i % 3, data[2]));
                        if (data[2] < data[1]) break;
                        boolean isCompleted = true;
                        int check = i / 3;
                        int j = 0;
                        while (j < 3) {
                            int[] data2 = pc.dY()[check * 3 + j];
                            if (data2[2] < data2[1]) {
                                isCompleted = false;
                            }
                            ++j;
                        }
                        if (!isCompleted) break;
                        pc.dY()[check * 3][3] = 3;
                        pc.a(new S_ProtoBuffers(814, i / 3, 3));
                        break;
                    }
                    ++i;
                }
            }
        }
        for (L1QuestNew qn : pc.dS().values()) {
            int i = 0;
            while (i < qn.p().length) {
                if (qn.p()[i] == this.z()) {
                    qn.b(i);
                }
                ++i;
            }
        }
    }

    public void d(L1PcInstance pc) {
        if (pc.bB(60) || pc.bB(97)) {
            return;
        }
        if (this.ac() == 1) {
            if (this.ea() == this.ew() && pc.fu().c(this.fu()) <= 2) {
                this.e(pc);
            }
        } else if (this.ac() == 2) {
            if (this.ea() == this.ew()) {
                if (pc.fu().c(this.fu()) <= 1) {
                    this.e(pc);
                }
            } else {
                this.r();
            }
        } else if (this.ac() == 3 && this.ea() < this.ew()) {
            this.e(pc);
        }
    }

    public void e(L1PcInstance pc) {
        int hiddenType = this.ac();
        if (hiddenType == 1) {
            if (ListSprReader__obf_c.a().c(this.fe())) {
                this.b(new S_DoActionGFX(this.fr(), 11));
            } else {
                this.b(new S_DoActionGFX(this.fr(), 4));
            }
        } else if (hiddenType == 2) {
            this.b(new S_DoActionGFX(this.fr(), 45));
        } else if (hiddenType == 3) {
            this.b(new S_DoActionGFX(this.fr(), 11));
        }
        this.t(0);
        this.cq(ListSprReader__obf_c.a().a(this));
        this.b(new S_CharVisualUpdate(this, this.eY()));
        if (!(pc.bB(60) || pc.bB(97) || pc.l())) {
            this.n.a(pc, 0);
            this.m = pc;
        }
        this.Z_();
        this.a_(2);
    }

    public void b(boolean isSummon) {
        if (this.fe() == 7548 || this.fe() == 7550 || this.fe() == 7552 || this.fe() == 7554 || this.fe() == 7585 || this.fe() == 7591) {
            for (L1PcInstance pc : L1World.a().f(this)) {
                if (pc.b((L1Object)this)) continue;
                pc.c((L1Object)this);
            }
            this.b(new S_NPCPack(this));
            this.b(new S_DoActionGFX(this.fr(), 11));
        } else if (this.fe() == 7539 || this.fe() == 7557 || this.fe() == 7558 || this.fe() == 7864 || this.fe() == 7869 || this.fe() == 7870 || this.fe() == 8036 || this.fe() == 8054 || this.fe() == 8055) {
            for (L1PcInstance pc : L1World.a().f(this)) {
                if (pc.b((L1Object)this)) continue;
                pc.c((L1Object)this);
            }
            this.cq(4);
            this.b(new S_NPCPack(this));
            this.b(new S_DoActionGFX(this.fr(), 11));
            this.b(11, 1);
            this.cq(0);
            this.b(new S_CharVisualUpdate(this, this.eY()));
        } else if (ListSprReader__obf_c.a().b(this.fe()) && this.ac() != 1) {
            for (L1PcInstance pc : L1World.a().f(this)) {
                if (pc.b((L1Object)this)) continue;
                pc.c((L1Object)this);
            }
            this.m(isSummon);
            this.cq(11);
            this.b(new S_NPCPack(this));
            this.b(new S_DoActionGFX(this.fr(), 4));
            this.b(4, 1);
            this.cq(0);
            this.b(new S_CharVisualUpdate(this, this.eY()));
        } else if (this.fe() == 14036 || this.fe() == 14271) {
            this.cq(0);
        } else if (this.fe() == 10071) {
            for (L1PcInstance pc : L1World.a().f(this)) {
                if (pc.b((L1Object)this)) continue;
                pc.c((L1Object)this);
            }
            this.cq(11);
            this.b(new S_NPCPack(this));
            this.b(new S_DoActionGFX(this.fr(), 4));
            this.b(4, 1);
            this.cq(4);
            this.b(new S_CharVisualUpdate(this, this.eY()));
        }
        if (isSummon) {
            this.Z_();
        }
    }

    private void l() {
        int rnd;
        int npcid = this.U_().b();
        if (ListSprReader__obf_c.a().b(this.fe())) {
            int rnd2;
            if (this.ew() / 3 > this.ea() && 2 > (rnd2 = Random.a(10))) {
                this.t();
                this.t(1);
                this.b(new S_DoActionGFX(this.fr(), 11));
                this.cq(11);
                this.b(new S_CharVisualUpdate(this, this.eY()));
            }
        } else if (this.fe() == 10071) {
            if (this.ew() / 3 > this.ea() && Random.a(100) < 2) {
                this.t();
                this.t(1);
                this.b(new S_DoActionGFX(this.fr(), 11));
                this.cq(11);
                this.b(new S_CharVisualUpdate(this, this.eY()));
            }
        } else if (this.fe() == 7558) {
            if (this.ew() / 3 > this.ea() && Random.a(100) < 1) {
                this.t();
                this.t(1);
                this.b(new S_DoActionGFX(this.fr(), 20));
                this.cq(20);
                this.b(new S_CharVisualUpdate(this, this.eY()));
            }
        } else if (ListSprReader__obf_c.a().a(this.fe())) {
            int rnd3;
            if (this.ew() / 3 > this.ea() && 2 > (rnd3 = Random.a(10))) {
                this.t();
                this.t(2);
                this.b(new S_DoActionGFX(this.fr(), 44));
            }
        } else if ((npcid == 46107 || npcid == 46108) && this.ew() / 4 > this.ea() && 2 > (rnd = Random.a(10))) {
            this.t();
            this.t(1);
            this.b(new S_DoActionGFX(this.fr(), 11));
            this.cq(11);
            this.b(new S_CharVisualUpdate(this, this.eY()));
        }
    }

    public void h() {
        int npcid = this.U_().b();
        if (ListSprReader__obf_c.a().b(this.fe()) && !this.ar()) {
            if (Random.a(100) < 33) {
                this.t(1);
            }
        } else if (ListSprReader__obf_c.a().c(this.fe())) {
            if (Random.a(100) < 33) {
                this.t(1);
                this.cq(4);
                return;
            }
        } else if (ListSprReader__obf_c.a().a(this.fe())) {
            this.t(2);
        } else if (this.fe() == 6555 || this.fe() == 6555) {
            if (Random.a(100) < 33) {
                this.t(1);
            }
        } else if (npcid >= 46125 && npcid <= 46128) {
            this.t(3);
        } else if (ListSprReader__obf_c.a().d(this.fe())) {
            this.t(3);
        }
        this.cq(ListSprReader__obf_c.a().a(this));
    }

    public void a(L1NpcInstance leader) {
        int npcid = this.U_().b();
        if (leader.ac() == 1) {
            if (ListSprReader__obf_c.a().b(this.fe())) {
                this.t(1);
            } else {
                if (ListSprReader__obf_c.a().c(this.fe())) {
                    this.t(1);
                    this.cq(4);
                    return;
                }
                if (npcid == 46107 || npcid == 46108) {
                    this.t(1);
                }
            }
        } else if (leader.ac() == 2) {
            if (ListSprReader__obf_c.a().a(this.fe())) {
                this.t(2);
            }
        } else if (npcid >= 46125 && npcid <= 46128) {
            this.t(3);
        } else if (ListSprReader__obf_c.a().d(this.fe())) {
            this.t(3);
        }
        this.cq(ListSprReader__obf_c.a().a(this));
    }

    @Override
    public void g_(int transformId) {
        super.g_(transformId);
        this.y().g();
        DropTable.a().a((L1NpcInstance)this, this.y());
        this.y().f();
    }

    public boolean i() {
        return this.A;
    }

    public void c(boolean isAllocatedDrop) {
        this.A = isAllocatedDrop;
    }

    protected class L1R_a
    implements Runnable {
        private final L1Character b;

        public L1R_a(L1Character _lastAttacker) {
            this.b = _lastAttacker;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                if (L1MonsterInstance.this.eX()) {
                    return;
                }
                L1MonsterInstance.this.X(true);
                L1MonsterInstance.this.j(true);
                L1MonsterInstance.this.bx(0);
                L1MonsterInstance.this.a_(1);
                if (L1MonsterInstance.this.z() == 190044) {
                    for (L1Object object : L1World.a().b((L1Object)L1MonsterInstance.this, -1)) {
                        if (!(object instanceof L1MonsterInstance)) continue;
                        L1MonsterInstance mob = (L1MonsterInstance)object;
                        mob.b(new S_DoActionGFX(mob.fr(), 2));
                        mob.b(this.b, 300);
                    }
                    L1MonsterInstance.this.b(new S_SkillSound(L1MonsterInstance.this.fr(), 7771));
                } else if (L1MonsterInstance.this.z() == 190078) {
                    if (this.b instanceof L1PcInstance && this.b.fp() >= 807 && this.b.fp() <= 812) {
                        L1Teleport.a((L1PcInstance)this.b, 32769, 32764, this.b.fp() + 1, 5, true);
                    }
                } else if (L1MonsterInstance.this.z() == 190079) {
                    if (this.b instanceof L1PcInstance) {
                        L1Teleport.a((L1PcInstance)this.b, 200);
                    }
                } else if (L1MonsterInstance.this.z() == 46142 && L1MonsterInstance.this.fp() >= 2101 && L1MonsterInstance.this.fp() <= 2150) {
                    L1DoorInstance door = DoorTable.b().a(32852, 32920, L1MonsterInstance.this.fp());
                    if (door != null) {
                        door.f();
                    }
                    if (this.b instanceof L1PcInstance) {
                        ((L1PcInstance)this.b).a(new S_PacketBox(84, 2, "$13383"));
                    }
                } else if (L1MonsterInstance.this.z() >= 190750 && L1MonsterInstance.this.z() <= 190762) {
                    if (this.b instanceof L1PcInstance && Random.a(1000) < 3) {
                        L1Teleport.a((L1PcInstance)this.b, 33392, 32346, 4, 0, true);
                    }
                } else if (L1MonsterInstance.this.z() == 97006 || L1MonsterInstance.this.z() == 97007 || L1MonsterInstance.this.z() == 97044 || L1MonsterInstance.this.z() == 97045 || L1MonsterInstance.this.z() == 97094 || L1MonsterInstance.this.z() == 97095) {
                    Object door = L1MonsterInstance.this.B;
                    synchronized (door) {
                        if (!L1MonsterInstance.this.C) {
                            SpawnTable.a(L1MonsterInstance.this.z() + 1, L1MonsterInstance.this, 30000L);
                            L1MonsterInstance.this.C = true;
                        }
                    }
                } else if (L1MonsterInstance.this.z() == 97008 || L1MonsterInstance.this.z() == 97046 || L1MonsterInstance.this.z() == 97096) {
                    int skillid = 4011;
                    if (L1MonsterInstance.this.z() == 97046) {
                        skillid = 4012;
                    } else if (L1MonsterInstance.this.z() == 97096) {
                        skillid = 4077;
                    }
                    for (L1PcInstance pc : L1World.a().c(L1MonsterInstance.this, 100)) {
                        Timestamp limit = new Timestamp(System.currentTimeMillis() + 259200000L);
                        pc.a(new S_SkillSound(pc.fr(), 7783));
                        pc.b(new S_SkillSound(pc.fr(), 7783));
                        L1ItemQuestBuff.a(pc, skillid, 259200, limit);
                    }
                    L1Dragon.a().a(L1MonsterInstance.this);
                } else if (L1MonsterInstance.this.z() >= 190574 && L1MonsterInstance.this.z() <= 190576) {
                    int camp = 0;
                    if (this.b instanceof L1PcInstance) {
                        L1PcInstance pc;
                        pc = (L1PcInstance)this.b;
                        camp = pc.dX();
                    }
                    L1ThebesBattle.a().a(camp);
                } else if (L1MonsterInstance.this.z() >= 190237 && L1MonsterInstance.this.z() <= 190242 && this.b instanceof L1PcInstance) {
                    switch (L1MonsterInstance.this.z()) {
                        case 190237: {
                            L1PcInstance pc = (L1PcInstance)this.b;
                            if (pc.bB(1038)) {
                                pc.a(new S_ServerMessage(79));
                                break;
                            }
                            pc.j(1027, 300000);
                            pc.a(new S_Liquor(pc.fr(), 8));
                            pc.b(new S_Liquor(pc.fr(), 8));
                            pc.a(new S_SkillSound(pc.fr(), 8910));
                            pc.b(new S_SkillSound(pc.fr(), 8910));
                            pc.a(new S_ServerMessage(1065));
                            pc.a(new S_PacketBox(60, 300));
                            break;
                        }
                        case 190238: {
                            new S_057().a(this.b, 0);
                            break;
                        }
                        case 190239: {
                            new S_048().a(this.b, 0);
                            break;
                        }
                        case 190240: {
                            new S_211().a(this.b, 0);
                            break;
                        }
                        case 190241: {
                            new S_079().a(this.b, 0);
                            break;
                        }
                        case 190242: {
                            new S_150().a(this.b, 0);
                        }
                    }
                }
                if (L1MonsterInstance.this.an() > 0 && L1MonsterInstance.this.ao() > 0) {
                    int step = -1;
                    while (step++ < 30) {
                        if (L1MonsterInstance.this.fs() == L1MonsterInstance.this.an() && L1MonsterInstance.this.ft() == L1MonsterInstance.this.ao()) break;
                        L1MonsterInstance.this.g(L1MonsterInstance.this.a(L1MonsterInstance.this.an(), L1MonsterInstance.this.ao()));
                        try {
                            Thread.sleep(L1MonsterInstance.this.f(L1MonsterInstance.this.N(), 0) * 3 / 4);
                        }
                        catch (InterruptedException e) {
                            y.log(Level.SEVERE, e.getLocalizedMessage(), e);
                        }
                    }
                }
                L1MonsterInstance.this.cq(8);
                L1MonsterInstance.this.fq().a(L1MonsterInstance.this.fu(), true);
                L1MonsterInstance.this.b(new S_DoActionGFX(L1MonsterInstance.this.fr(), 8));
                L1MonsterInstance.this.a(false);
                L1MonsterInstance.this.f(this.b);
                L1MonsterInstance.this.j(false);
                L1MonsterInstance.this.k(0);
                L1MonsterInstance.this.A(0);
                L1MonsterInstance.this.A();
            }
            catch (Exception e) {
                y.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }
}
