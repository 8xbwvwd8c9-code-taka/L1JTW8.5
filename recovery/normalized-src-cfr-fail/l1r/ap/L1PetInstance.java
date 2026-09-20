/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import l1r.ai.IdFactory;
import l1r.ao.ExpTable;
import l1r.ao.PetItemTable;
import l1r.ao.PetTable;
import l1r.ao.PetTypeTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1GroundInventory;
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
import l1r.bh.L1Pet;
import l1r.bh.L1PetItem;
import l1r.bh.L1PetType;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1PetInstance
extends L1NpcInstance {
    private int y;
    private L1ItemInstance z;
    private L1ItemInstance A;
    private int B;
    private int C;
    private int D;
    private final L1PcInstance E;
    private int F;
    private L1PetType G;
    private int H;
    private ScheduledFuture<?> I;

    @Override
    public boolean a() {
        switch (this.D) {
            case 3: {
                return true;
            }
            case 4: {
                if (this.E == null || this.E.fp() != this.fp() || this.fu().c(this.E.fu()) >= 5) {
                    this.D = 3;
                    return true;
                }
                this.y = this.c(this.E.fs(), this.E.ft());
                this.y = this.a(this.fs(), this.ft(), this.fp(), this.y);
                this.g(this.y);
                this.v(this.f(this.N(), 0));
                return false;
            }
            case 5: {
                if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
                    int dir = this.a(this.X(), this.Y());
                    if (dir == -1) {
                        this.q(this.fs());
                        this.r(this.ft());
                    } else {
                        this.g(dir);
                        this.v(this.f(this.N(), 0));
                    }
                }
                return false;
            }
            case 7: {
                if (this.E != null && this.E.fp() == this.fp() && this.fu().c(this.E.fu()) <= 1) {
                    this.D = 3;
                    return true;
                }
                int locx = this.E.fs() + Random.a(1);
                int locy = this.E.ft() + Random.a(1);
                this.y = this.a(locx, locy);
                if (this.y == -1) {
                    this.D = 3;
                    return true;
                }
                this.g(this.y);
                this.v(this.f(this.N(), 0));
                return false;
            }
        }
        if (this.E != null && this.E.fp() == this.fp()) {
            if (this.fu().c(this.E.fu()) > 2) {
                this.y = this.a(this.E.fs(), this.E.ft());
                this.g(this.y);
                this.v(this.f(this.N(), 0));
            }
        } else {
            this.D = 3;
            return true;
        }
        return false;
    }

    public L1PetInstance(L1Npc template, L1PcInstance master, L1Pet l1pet) {
        super(template);
        this.E = master;
        this.F = l1pet.a();
        this.G = PetTypeTable.b().a(template.b());
        this.cF(l1pet.b());
        this.e(l1pet.d());
        this.b(l1pet.e());
        this.bG(l1pet.f());
        this.bx(l1pet.f());
        this.bI(l1pet.g());
        this.by(l1pet.g());
        this.k(l1pet.h());
        this.f(ExpTable.a(l1pet.e(), l1pet.h()));
        this.cr(l1pet.i());
        this.c_(l1pet.j());
        this.aw();
        this.e(master);
        this.cG(master.fs() + Random.a(5) - 2);
        this.cH(master.ft() + Random.a(5) - 2);
        this.cE(master.fp());
        this.ct(5);
        this.s(template.ae());
        this.D = 3;
        L1World.a().a(this);
        L1World.a().c(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    public L1PetInstance(L1NpcInstance target, L1PcInstance master, int itemid) {
        super(target.U_());
        this.E = master;
        this.F = itemid;
        this.G = PetTypeTable.b().a(this.z());
        this.cF(IdFactory.a().c());
        this.bx(target.ea());
        this.by(target.eb());
        this.k(750);
        this.f(0);
        this.cr(0);
        this.c_(50);
        this.aw();
        this.e(master);
        this.cG(target.fs());
        this.cH(target.ft());
        this.cE(target.fp());
        this.ct(target.fb());
        this.s(target.aa());
        this.o(6);
        this.a(target.y());
        target.a((L1Inventory)null);
        this.D = 3;
        this.v();
        if (this.ew() > this.ea()) {
            this.u();
        }
        this.x();
        if (this.ex() > this.eb()) {
            this.w();
        }
        target.aa_();
        L1World.a().a(this);
        L1World.a().c(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
        PetTable.a().a(target, this.fr(), itemid);
    }

    @Override
    public void b(L1Character attacker, int damage) {
        if (this.ea() > 0) {
            int newHp;
            if (damage > 0) {
                this.c(attacker, 0);
                this.bz(66);
                this.bz(153);
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
                this.ay();
            } else {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            this.ay();
        }
    }

    private synchronized void ay() {
        if (!this.eX()) {
            this.X(true);
            this.ax();
            this.cq(8);
            this.a(0);
            this.fq().a(this.fu(), true);
            this.b(new S_DoActionGFX(this.fr(), 8));
        }
    }

    public void d(int new_itemobjid) {
        L1PetInstance new_pet;
        L1Pet l1pet = PetTable.a().b(this.F);
        if (l1pet == null) {
            return;
        }
        int newNpcId = this.G.g();
        int evolvItem = this.G.i();
        short tmpMaxHp = this.ew();
        short tmpMaxMp = this.ex();
        this.g_(newNpcId);
        this.G = PetTypeTable.b().a(newNpcId);
        this.b(1L);
        this.bG(tmpMaxHp / 2);
        this.bI(tmpMaxMp / 2);
        this.bx(this.ew());
        this.by(this.ex());
        this.k(0);
        this.f(0);
        this.y().b(evolvItem, 1);
        L1Object obj = L1World.a().a(l1pet.b());
        if (obj != null && obj instanceof L1NpcInstance) {
            new_pet = (L1PetInstance)obj;
            L1Inventory new_petInventory = new_pet.y();
            List<L1ItemInstance> itemList = this.y().d();
            for (L1ItemInstance itemObject : itemList) {
                L1ItemInstance item = itemObject;
                if (item == null) continue;
                if (item.D()) {
                    item.b(false);
                    L1PetItem petItem = PetItemTable.a().a(item.N());
                    if (petItem.n() == 1) {
                        this.b((L1ItemInstance)null);
                        new_pet.a(this, item);
                    } else if (petItem.n() == 0) {
                        this.c((L1ItemInstance)null);
                        new_pet.b(this, item);
                    }
                }
                if (new_pet.y().a(item, item.E()) == 0) {
                    this.y().a(item, item.E(), new_petInventory);
                    continue;
                }
                new_petInventory = L1World.a().a(this.fs(), this.ft(), this.fp());
                this.y().a(item, item.E(), new_petInventory);
            }
            new_pet.b(new S_SkillSound(new_pet.fr(), 2127));
        }
        PetTable.a().a(this.F);
        l1pet.a(new_itemobjid);
        l1pet.c(newNpcId);
        l1pet.a(this.et());
        l1pet.d(this.ev());
        l1pet.e(this.ew());
        l1pet.f(this.ex());
        l1pet.g(this.m());
        l1pet.i(this.fj());
        PetTable.a().a(this, this.fr(), new_itemobjid);
        this.F = new_itemobjid;
        if (obj != null && obj instanceof L1NpcInstance) {
            new_pet = (L1PetInstance)obj;
            this.aw();
        }
    }

    private void az() {
        L1MonsterInstance monster = new L1MonsterInstance(this.U_());
        monster.cF(IdFactory.a().c());
        monster.cG(this.fs());
        monster.cH(this.ft());
        monster.cE(this.fp());
        monster.ct(this.fb());
        monster.c(true);
        monster.a(this.y());
        this.a((L1Inventory)null);
        monster.b(this.ev());
        monster.bG(this.ew());
        monster.bx(this.ea());
        monster.bI(this.ex());
        monster.by(this.eb());
        this.E.ek().remove(this.fr());
        if (this.E.ek().isEmpty()) {
            this.E.a(new S_PetCtrlMenu(this.E, monster, false));
        }
        this.aa_();
        this.E.j().c(this.F, 1);
        PetTable.a().a(this.F);
        L1World.a().a(monster);
        L1World.a().c(monster);
        for (L1PcInstance pc : L1World.a().f(monster)) {
            this.b(pc);
        }
    }

    public void b(boolean isDepositnpc) {
        L1Inventory targetInventory = this.E.j();
        List<L1ItemInstance> itemList = this.y().d();
        for (L1ItemInstance itemObject : itemList) {
            L1ItemInstance item = itemObject;
            if (item == null) continue;
            if (item.D()) {
                if (!isDepositnpc) continue;
                L1PetItem petItem = PetItemTable.a().a(item.N());
                if (petItem.n() == 1) {
                    this.b((L1ItemInstance)null);
                } else if (petItem.n() == 0) {
                    this.c((L1ItemInstance)null);
                }
                item.b(false);
            }
            if (this.E.j().a(item, item.E()) == 0) {
                this.y().a(item, item.E(), targetInventory);
                this.E.a(new S_ServerMessage(143, this.et(), item.s()));
                continue;
            }
            targetInventory = L1World.a().a(this.fs(), this.ft(), this.fp());
            this.y().a(item, item.E(), targetInventory);
        }
    }

    public void h() {
        L1GroundInventory targetInventory = L1World.a().a(this.fs(), this.ft(), this.fp());
        for (L1ItemInstance item : this.o.d()) {
            if (item.D()) {
                L1PetItem petItem = PetItemTable.a().a(item.N());
                if (petItem.n() == 1) {
                    this.b((L1ItemInstance)null);
                } else if (petItem.n() == 0) {
                    this.c((L1ItemInstance)null);
                }
                item.b(false);
            }
            this.o.a(item, item.E(), (L1Inventory)targetInventory);
        }
    }

    public void i() {
        int id = this.G.a(L1PetType.b(this.ev()));
        if (id != 0 && !this.eX()) {
            if (this.fj() == 0) {
                id = this.G.h();
            }
            this.b(new S_NpcChatPacket(this, "$" + id, 0));
        }
        if (this.fj() > 0) {
            this.e(7);
        } else {
            this.e(3);
        }
    }

    public void f(L1Character target) {
        if (target != null && (this.D == 1 || this.D == 2 || this.D == 5) && this.fj() > 0) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    public void g(L1Character target) {
        if (target != null && (this.D == 1 || this.D == 5) && this.fj() > 0) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_PetPack(this, perceivedFrom));
        if (this.eX()) {
            perceivedFrom.a(new S_DoActionGFX(this.fr(), 8));
        }
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        L1Character cha = this.M();
        L1PcInstance master = (L1PcInstance)cha;
        if (master.aR()) {
            return;
        }
        if (this.ep() == 1) {
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
        if (this.E.equals(player)) {
            player.a(new S_PetMenuPacket(this, this.l()));
            L1Pet l1pet = PetTable.a().b(this.F);
            if (l1pet != null) {
                l1pet.g(this.m());
                l1pet.d(this.ev());
                l1pet.e(this.ew());
                l1pet.f(this.ex());
                l1pet.i(this.fj());
                PetTable.a().a(l1pet);
            }
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
            this.az();
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
            Object[] objectArray = petList = this.E.ek().values().toArray();
            int n = petList.length;
            int n3 = 0;
            while (n3 < n) {
                Object petObject = objectArray[n3];
                if (petObject instanceof L1PetInstance) {
                    L1PetType type;
                    int id;
                    L1PetInstance pet = (L1PetInstance)petObject;
                    if (this.E != null && this.E.ev() >= pet.ev() && pet.fj() > 0) {
                        pet.e(status);
                    } else if (!pet.eX() && (id = (type = PetTypeTable.b().a(pet.U_().b())).h()) != 0) {
                        pet.b(new S_NpcChatPacket(pet, "$" + id, 0));
                    }
                } else if (petObject instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)petObject;
                    summon.d(status);
                }
                ++n3;
            }
        }
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
        } else if (action.equalsIgnoreCase("getitem")) {
            this.b(false);
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
        if (this.E != null) {
            L1PcInstance Master = this.E;
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

    public void e(int i) {
        this.D = i;
        if (this.D == 5) {
            this.q(this.fs());
            this.r(this.ft());
        }
        if (this.D == 7) {
            this.t();
        }
        if (this.D == 3) {
            this.t();
        } else if (!this.ae()) {
            this.q();
        }
    }

    public int j() {
        return this.D;
    }

    public int k() {
        return this.F;
    }

    public void f(int expPercent) {
        this.H = expPercent;
    }

    public int l() {
        return this.H;
    }

    public void b(L1ItemInstance weapon) {
        this.z = weapon;
    }

    public L1ItemInstance n() {
        return this.z;
    }

    public void c(L1ItemInstance armor) {
        this.A = armor;
    }

    public L1ItemInstance o() {
        return this.A;
    }

    public void B(int i) {
        this.B = i;
    }

    public int p() {
        return this.B;
    }

    public void C(int i) {
        this.C = i;
    }

    public int au() {
        return this.C;
    }

    public L1PetType av() {
        return this.G;
    }

    public void aw() {
        this.I = GeneralThreadPool.a().a(new L1R_a(), 1000L, 200000L);
    }

    public void ax() {
        if (this.I != null) {
            this.I.cancel(true);
        }
    }

    public void a(L1PetInstance pet, L1ItemInstance weapon) {
        if (pet.n() == null) {
            this.c(pet, weapon);
        } else if (pet.n().equals(weapon)) {
            this.d(pet, pet.n());
        } else {
            this.d(pet, pet.n());
            this.c(pet, weapon);
        }
    }

    public void b(L1PetInstance pet, L1ItemInstance armor) {
        if (pet.o() == null) {
            this.e(pet, armor);
        } else if (pet.o().equals(armor)) {
            this.f(pet, pet.o());
        } else {
            this.f(pet, pet.o());
            this.e(pet, armor);
        }
    }

    private void c(L1PetInstance pet, L1ItemInstance weapon) {
        int itemId = weapon.N();
        L1PetItem petItem = PetItemTable.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.B(petItem.b());
        pet.C(petItem.c());
        pet.bN(petItem.e());
        pet.bP(petItem.f());
        pet.bR(petItem.g());
        pet.bV(petItem.h());
        pet.bX(petItem.i());
        pet.bH(petItem.j());
        pet.bJ(petItem.k());
        pet.cp(petItem.l());
        pet.co(petItem.m());
        pet.b(weapon);
        weapon.b(true);
    }

    private void d(L1PetInstance pet, L1ItemInstance weapon) {
        int itemId = weapon.N();
        L1PetItem petItem = PetItemTable.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.B(0);
        pet.C(0);
        pet.bN(-petItem.e());
        pet.bP(-petItem.f());
        pet.bR(-petItem.g());
        pet.bV(-petItem.h());
        pet.bX(-petItem.i());
        pet.bH(-petItem.j());
        pet.bJ(-petItem.k());
        pet.cp(-petItem.l());
        pet.co(-petItem.m());
        pet.b((L1ItemInstance)null);
        weapon.b(false);
    }

    private void e(L1PetInstance pet, L1ItemInstance armor) {
        int itemId = armor.N();
        L1PetItem petItem = PetItemTable.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.bL(petItem.d());
        pet.bN(petItem.e());
        pet.bP(petItem.f());
        pet.bR(petItem.g());
        pet.bV(petItem.h());
        pet.bX(petItem.i());
        pet.bH(petItem.j());
        pet.bJ(petItem.k());
        pet.cp(petItem.l());
        pet.co(petItem.m());
        pet.c(armor);
        armor.b(true);
    }

    private void f(L1PetInstance pet, L1ItemInstance armor) {
        int itemId = armor.N();
        L1PetItem petItem = PetItemTable.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.bL(-petItem.d());
        pet.bN(-petItem.e());
        pet.bP(-petItem.f());
        pet.bR(-petItem.g());
        pet.bV(-petItem.h());
        pet.bX(-petItem.i());
        pet.bH(-petItem.j());
        pet.bJ(-petItem.k());
        pet.cp(-petItem.l());
        pet.co(-petItem.m());
        pet.c((L1ItemInstance)null);
        armor.b(false);
    }

    private class L1R_a
    extends TimerTask {
        private L1R_a() {
        }

        @Override
        public void run() {
            if (L1PetInstance.this.ah() || L1PetInstance.this.eX()) {
                L1PetInstance.this.I.cancel(true);
                return;
            }
            int _food = L1PetInstance.this.fj() - 2;
            if (_food <= 0) {
                L1PetInstance.this.c_(0);
                L1PetInstance.this.e(3);
                L1PetType type = PetTypeTable.b().a(L1PetInstance.this.z());
                int id = type.h();
                if (id != 0) {
                    L1PetInstance.this.b(new S_NpcChatPacket(L1PetInstance.this, "$" + id, 0));
                }
            } else {
                L1PetInstance.this.c_(_food);
            }
            PetTable.a().a(L1PetInstance.this);
        }
    }
}
