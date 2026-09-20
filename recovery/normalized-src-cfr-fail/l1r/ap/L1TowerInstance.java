/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.util.ArrayList;
import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ap.L1AttackerInstance;
import l1r.ap.L1KeeperInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnWar;
import l1r.aq.L1Teleport;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.be.S_CastleMaster;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NPCPack;
import l1r.bh.L1Castle;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;

public class L1TowerInstance
extends L1NpcInstance {
    private L1Character y;
    private int z;
    private int A;
    private final ArrayList<L1KeeperInstance> B = new ArrayList();

    public L1TowerInstance(L1Npc template) {
        super(template);
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_NPCPack(this));
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void b(L1Character attacker, int damage) {
        if (this.B.isEmpty()) {
            for (L1Object obj : L1World.a().b((L1Object)this, 20)) {
                if (!(obj instanceof L1KeeperInstance)) continue;
                this.B.add((L1KeeperInstance)obj);
            }
        }
        for (L1KeeperInstance keeper : this.B) {
            keeper.c(attacker, damage);
        }
        if (this.z == 0) {
            this.z = this.h() ? 7 : L1CastleLocation.a(this.fs(), this.ft(), this.fp());
        }
        if (this.z > 0 && L1CastleWar.a().a(this.z)) {
            if (this.z == 7 && !this.h()) {
                int subTowerDeadCount = 0;
                for (L1Object l1object : L1World.a().b()) {
                    Object tower;
                    if (l1object instanceof L1TowerInstance && ((L1TowerInstance)(tower = (L1TowerInstance)l1object)).h() && ((L1Character)tower).eX() && ++subTowerDeadCount == 4) break;
                }
                if (subTowerDeadCount < 3) {
                    return;
                }
            }
            L1Character cha = null;
            if (attacker instanceof L1PcInstance) {
                cha = attacker;
            } else if (attacker instanceof L1PetInstance) {
                cha = ((L1PetInstance)attacker).M();
            } else if (attacker instanceof L1SummonInstance) {
                cha = ((L1SummonInstance)attacker).M();
            } else if (attacker instanceof L1AttackerInstance) {
                cha = attacker;
            }
            if (cha == null) {
                return;
            }
            boolean existDefenseClan = false;
            for (L1Clan clan : ClanTable.a().b().values()) {
                int clanCastleId = clan.m();
                if (clanCastleId != this.z) continue;
                existDefenseClan = true;
                break;
            }
            if (cha instanceof L1PcInstance) {
                boolean isInWar;
                L1PcInstance pc = (L1PcInstance)cha;
                L1War clanWar = L1World.a().c(pc.aG());
                boolean bl = isInWar = clanWar != null && clanWar.d() == this.z;
                if (existDefenseClan && !isInWar) {
                    return;
                }
            }
            if (this.ea() > 0 && !this.eX()) {
                int newHp = this.ea() - damage;
                if (newHp <= 0 && !this.eX()) {
                    this.bx(0);
                    this.X(true);
                    this.cq(35);
                    this.y = attacker;
                    this.A = 0;
                    L1R_a death = new L1R_a();
                    GeneralThreadPool.a().a(death);
                }
                if (newHp > 0) {
                    this.a(newHp);
                    if (this.ew() * 1 / 4 > this.ea()) {
                        if (this.A != 3) {
                            this.b(new S_DoActionGFX(this.fr(), 34));
                            this.cq(34);
                            this.A = 3;
                        }
                    } else if (this.ew() * 2 / 4 > this.ea()) {
                        if (this.A != 2) {
                            this.b(new S_DoActionGFX(this.fr(), 33));
                            this.cq(33);
                            this.A = 2;
                        }
                    } else if (this.ew() * 3 / 4 > this.ea() && this.A != 1) {
                        this.b(new S_DoActionGFX(this.fr(), 32));
                        this.cq(32);
                        this.A = 1;
                    }
                }
            } else if (!this.eX()) {
                this.X(true);
                this.cq(35);
                this.y = attacker;
                L1R_a death = new L1R_a();
                GeneralThreadPool.a().a(death);
            }
        }
    }

    @Override
    public void a(int i) {
        int currentHp = i;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
    }

    public boolean h() {
        return this.U_().b() == 81190 || this.U_().b() == 81191 || this.U_().b() == 81192 || this.U_().b() == 81193;
    }

    private class L1R_a
    implements Runnable {
        private L1R_a() {
        }

        @Override
        public void run() {
            L1TowerInstance.this.bx(0);
            L1TowerInstance.this.X(true);
            L1TowerInstance.this.cq(35);
            L1TowerInstance.this.fq().a(L1TowerInstance.this.fu(), true);
            L1TowerInstance.this.b(new S_DoActionGFX(L1TowerInstance.this.fr(), 35));
            if (L1TowerInstance.this.y instanceof L1AttackerInstance) {
                int castle_id = L1CastleLocation.a(L1TowerInstance.this.fs(), L1TowerInstance.this.ft(), L1TowerInstance.this.fp());
                for (L1Clan defClan : ClanTable.a().b().values()) {
                    if (castle_id != defClan.m()) continue;
                    defClan.g(0);
                    ClanTable.a().b(defClan);
                    break;
                }
                L1Castle l1castale = CastleTable.a().a(castle_id);
                l1castale.c(0);
                L1World.a().a(new S_CastleMaster(castle_id, 0));
                for (L1Object obj : L1World.a().b(L1TowerInstance.this.fp()).values()) {
                    L1AttackerInstance attacker;
                    if (obj instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)obj;
                        if (!L1CastleLocation.a(castle_id, pc)) continue;
                        int[] loc = L1CastleLocation.e(castle_id);
                        int locx = loc[0];
                        int locy = loc[1];
                        int mapid = loc[2];
                        L1Teleport.a(pc, locx, locy, mapid, 5, true);
                        continue;
                    }
                    if (!(obj instanceof L1AttackerInstance) || !L1CastleLocation.a(castle_id, attacker = (L1AttackerInstance)obj)) continue;
                    attacker.aa_();
                }
                String npcClanName = ((L1AttackerInstance)L1TowerInstance.this.y).ap();
                L1War npcClanWar = L1World.a().c(npcClanName);
                if (npcClanWar != null) {
                    npcClanWar.a(npcClanName);
                }
                L1CastleWar.a().a(l1castale);
                return;
            }
            if (!L1TowerInstance.this.h()) {
                L1SpawnWar.a().b(L1TowerInstance.this.z);
            }
        }
    }
}
