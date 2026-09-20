/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l1r.ai.IdFactory;
import l1r.ao.DropTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTypeTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_DoActionGFX;
import l1r.be.S_HPMeter;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PetCtrlMenu;
import l1r.be.S_PetMenuPacket;
import l1r.be.S_PetPack;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SummonPack;
import l1r.bh.L1Npc;
import l1r.bh.L1PetType;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1SummonInstance
extends L1NpcInstance {
    private ScheduledFuture<?> y;
    private static final long z = 3600000L;
    private int A;
    private final boolean B;
    private boolean C = false;
    private int D;

    @Override
    public boolean a() {
        switch (this.A) {
            case 3: {
                return true;
            }
            case 4: {
                if (this.k == null || this.k.fp() != this.fp() || this.fu().c(this.k.fu()) >= 5) {
                    this.A = 3;
                    return true;
                }
                this.D = this.c(this.k.fs(), this.k.ft());
                this.D = this.a(this.fs(), this.ft(), this.fp(), this.D);
                this.g(this.D);
                this.v(this.f(this.N(), 0));
                return false;
            }
            case 5: {
                if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
                    this.D = this.a(this.X(), this.Y());
                    if (this.D == -1) {
                        this.q(this.fs());
                        this.r(this.ft());
                    } else {
                        this.g(this.D);
                        this.v(this.f(this.N(), 0));
                    }
                }
                return false;
            }
        }
        if (this.k != null && this.k.fp() == this.fp()) {
            if (this.fu().c(this.k.fu()) > 2) {
                this.D = this.a(this.k.fs(), this.k.ft());
                this.g(this.D);
                this.v(this.f(this.N(), 0));
            }
        } else {
            this.A = 3;
            return true;
        }
        return false;
    }

    public L1SummonInstance(L1Npc template, L1Character master) {
        super(template);
        this.cF(IdFactory.a().c());
        this.y = GeneralThreadPool.a().a(new L1R_a(), 3600000L);
        this.e(master);
        this.cG(master.fs() + Random.a(5) - 2);
        this.cH(master.ft() + Random.a(5) - 2);
        this.cE(master.fp());
        this.ct(5);
        this.s(template.ae());
        this.A = 3;
        this.B = false;
        L1World.a().a(this);
        L1World.a().c(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    public L1SummonInstance(L1MonsterInstance target, L1Character master, boolean isCreateZombie) {
        super(null);
        this.cF(IdFactory.a().c());
        if (isCreateZombie) {
            int npcId = 45065;
            L1PcInstance pc = (L1PcInstance)master;
            int level = pc.ev();
            if (pc.B()) {
                if (level >= 24 && level <= 31) {
                    npcId = 81183;
                } else if (level >= 32 && level <= 39) {
                    npcId = 81184;
                } else if (level >= 40 && level <= 43) {
                    npcId = 81185;
                } else if (level >= 44 && level <= 47) {
                    npcId = 81186;
                } else if (level >= 48 && level <= 51) {
                    npcId = 81187;
                } else if (level >= 52) {
                    npcId = 81188;
                }
            } else if (pc.A() && level >= 48) {
                npcId = 81183;
            }
            L1Npc template = NpcTable.a().a(npcId).a();
            this.a(template);
        } else {
            this.a(target.U_());
            this.bx(target.ea());
            this.by(target.eb());
        }
        this.y = GeneralThreadPool.a().a(new L1R_a(), 3600000L);
        this.e(master);
        this.cG(target.fs());
        this.cH(target.ft());
        this.cE(target.fp());
        this.ct(target.fb());
        this.s(target.aa());
        this.o(6);
        if (!target.i()) {
            DropTable.a().a((L1NpcInstance)target, target.y());
        }
        this.a(target.y());
        target.a((L1Inventory)null);
        this.A = 3;
        this.B = true;
        for (L1NpcInstance each : master.ek().values()) {
            each.c(target);
        }
        target.aa_();
        L1World.a().a(this);
        L1World.a().c(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    @Override
    public void b(L1Character attacker, int damage) {
        if (this.ea() > 0) {
            int newHp;
            if (damage > 0) {
                this.c(attacker, 0);
                this.bz(66);
                this.bz(153);
                if (!this.L()) {
                    this.A = 1;
                    this.f(attacker);
                }
            }
            if (attacker instanceof L1PcInstance && damage > 0) {
                L1PcInstance player = (L1PcInstance)attacker;
                player.a(this);
            }
            if (attacker instanceof L1PetInstance) {
                L1PetInstance pet = (L1PetInstance)attacker;
                if (this.ep() == 1 || pet.ep() == 1) {
                    damage = 0;
                }
            } else if (attacker instanceof L1SummonInstance) {
                L1SummonInstance summon = (L1SummonInstance)attacker;
                if (this.ep() == 1 || summon.ep() == 1) {
                    damage = 0;
                }
            }
            if ((newHp = this.ea() - damage) <= 0) {
                this.j();
            } else {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            System.out.println("\u8b66\u544a\uff1a\u30b5\u30e2\u30f3\u306e\uff28\uff30\u6e1b\u5c11\u51e6\u7406\u304c\u6b63\u3057\u304f\u884c\u308f\u308c\u3066\u3044\u306a\u3044\u7b87\u6240\u304c\u3042\u308a\u307e\u3059\u3002\u203b\u3082\u3057\u304f\u306f\u6700\u521d\u304b\u3089\uff28\uff30\uff10");
            this.j();
        }
    }

    private synchronized void j() {
        if (!this.eX()) {
            this.X(true);
            this.a(0);
            this.cq(8);
            this.fq().a(this.fu(), true);
            L1Inventory targetInventory = this.k.y();
            List<L1ItemInstance> items = this.o.d();
            for (L1ItemInstance item : items) {
                if (this.k.y().a(item, item.E()) == 0) {
                    this.o.a(item, item.E(), targetInventory);
                    ((L1PcInstance)this.k).a(new S_ServerMessage(143, this.et(), item.s()));
                    continue;
                }
                targetInventory = L1World.a().a(this.fs(), this.ft(), this.fp());
                this.o.a(item, item.E(), targetInventory);
            }
            if (this.B) {
                this.b(new S_DoActionGFX(this.fr(), 8));
                this.A();
            } else {
                this.aa_();
            }
        }
    }

    public synchronized void h() {
        this.C = true;
        if (!this.B) {
            this.fq().a(this.fu(), true);
            L1Inventory targetInventory = this.k.y();
            List<L1ItemInstance> items = this.o.d();
            for (L1ItemInstance item : items) {
                if (this.k.y().a(item, item.E()) == 0) {
                    this.o.a(item, item.E(), targetInventory);
                    ((L1PcInstance)this.k).a(new S_ServerMessage(143, this.et(), item.s()));
                    continue;
                }
                targetInventory = L1World.a().a(this.fs(), this.ft(), this.fp());
                this.o.a(item, item.E(), targetInventory);
            }
            this.aa_();
        } else {
            this.k();
        }
    }

    private void k() {
        L1MonsterInstance monster = new L1MonsterInstance(this.U_());
        monster.cF(IdFactory.a().c());
        monster.cG(this.fs());
        monster.cH(this.ft());
        monster.cE(this.fp());
        monster.ct(this.fb());
        monster.c(true);
        L1Inventory inv = new L1Inventory();
        for (L1ItemInstance item : this.y().d()) {
            inv.d(item);
        }
        monster.a(inv);
        monster.bx(this.ea());
        monster.by(this.eb());
        monster.k(0);
        if (this.k instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this.k;
            pc.a(new S_ServerMessage(666, this.T()));
        }
        if (!this.eX()) {
            this.X(true);
            this.a(0);
            this.fq().a(this.fu(), true);
        }
        this.aa_();
        L1World.a().a(monster);
        L1World.a().c(monster);
    }

    @Override
    public synchronized void aa_() {
        if (this.ah()) {
            return;
        }
        if (!this.B && !this.C) {
            this.b(new S_SkillSound(this.fr(), 169));
        }
        if (this.k instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this.k;
            pc.a(new S_PetCtrlMenu(pc, this, false));
        }
        this.k.ek().remove(this.fr());
        super.aa_();
        if (this.y != null) {
            this.y.cancel(true);
            this.y = null;
        }
    }

    public void f(L1Character target) {
        if (target != null && (this.A == 1 || this.A == 2 || this.A == 5)) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    public void g(L1Character target) {
        if (target != null && (this.A == 1 || this.A == 5)) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        if (pc == null) {
            return;
        }
        L1Character cha = this.M();
        if (cha == null) {
            return;
        }
        L1PcInstance master = (L1PcInstance)cha;
        if (master.aR()) {
            return;
        }
        if ((this.ep() == 1 || pc.ep() == 1) && this.L()) {
            L1Attack attack_mortion = new L1Attack(pc, this, skillId);
            attack_mortion.c();
            return;
        }
        if (pc.a(pc, this, false)) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void a(L1PcInstance player) {
        if (this.eX()) {
            return;
        }
        if (this.k.equals(player)) {
            player.a(new S_PetMenuPacket(this, 0));
        }
    }

    @Override
    public void a(L1PcInstance player, String action) {
        int status = this.c(action);
        if (status == 0) {
            return;
        }
        if (status == 6) {
            Object[] petList;
            L1PcInstance petMaster = (L1PcInstance)this.k;
            if (this.B) {
                this.k();
            } else {
                this.j();
            }
            Object[] objectArray = petList = petMaster.ek().values().toArray();
            int n = petList.length;
            int n2 = 0;
            while (n2 < n) {
                Object petObject = objectArray[n2];
                if (petObject instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)petObject;
                    petMaster.a(new S_SummonPack(summon, petMaster));
                    return;
                }
                if (petObject instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance)petObject;
                    petMaster.a(new S_PetPack(pet, petMaster));
                    return;
                }
                ++n2;
            }
        } else {
            Object[] petList;
            Object[] objectArray = petList = this.k.ek().values().toArray();
            int n = petList.length;
            int n3 = 0;
            while (n3 < n) {
                Object petObject = objectArray[n3];
                if (petObject instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)petObject;
                    summon.d(status);
                } else if (petObject instanceof L1PetInstance) {
                    L1PetType type;
                    int id;
                    L1PetInstance pet = (L1PetInstance)petObject;
                    if (player != null && player.ev() >= pet.ev() && pet.fj() > 0) {
                        pet.e(status);
                    } else if (!pet.eX() && (id = (type = PetTypeTable.b().a(pet.U_().b())).h()) != 0) {
                        pet.b(new S_NpcChatPacket(pet, "$" + id, 0));
                    }
                }
                ++n3;
            }
        }
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_SummonPack(this, perceivedFrom));
    }

    @Override
    public void Y_() {
        if (!this.w) {
            this.e(1, 100);
        }
        if (this.ea() * 100 / this.ew() < 40) {
            this.e(0, 100);
        }
    }

    @Override
    public void a(L1ItemInstance item) {
        Arrays.sort(r);
        Arrays.sort(s);
        if (Arrays.binarySearch(r, item.N()) >= 0) {
            if (this.ea() != this.ew()) {
                this.e(0, 100);
            }
        } else if (Arrays.binarySearch(s, item.N()) >= 0) {
            this.e(1, 100);
        }
    }

    private int c(String action) {
        int status = 0;
        if (action.equalsIgnoreCase("aggressive")) {
            status = 1;
        } else if (action.equalsIgnoreCase("defensive")) {
            status = 2;
        } else if (action.equalsIgnoreCase("stay")) {
            status = 3;
        } else if (action.equalsIgnoreCase("extend")) {
            status = 4;
        } else if (action.equalsIgnoreCase("alert")) {
            status = 5;
        } else if (action.equalsIgnoreCase("dismiss")) {
            status = 6;
        }
        return status;
    }

    @Override
    public void a(int i) {
        int currentHp = i;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        if (this.ew() > this.ea()) {
            this.u();
        }
        if (this.k instanceof L1PcInstance) {
            L1PcInstance Master = (L1PcInstance)this.k;
            Master.a(new S_HPMeter(this));
        }
    }

    @Override
    public void i_(int i) {
        int currentMp = i;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        if (this.ex() > this.eb()) {
            this.w();
        }
    }

    public void d(int i) {
        this.A = i;
        if (this.A == 5) {
            this.q(this.fs());
            this.r(this.ft());
        }
        if (this.A == 3) {
            this.t();
        } else if (!this.ae()) {
            this.q();
        }
    }

    public int i() {
        return this.A;
    }

    private class L1R_a
    implements Runnable {
        private L1R_a() {
        }

        @Override
        public void run() {
            if (L1SummonInstance.this.ah()) {
                return;
            }
            if (L1SummonInstance.this.B) {
                L1SummonInstance.this.k();
            } else {
                L1SummonInstance.this.j();
            }
        }
    }
}
