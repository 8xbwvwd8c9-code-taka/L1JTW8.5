/*
 * Decompiled with CFR 0.152.
 */
package l1r.bf;

import java.util.ArrayList;
import l1r.ao.PolyTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1DotaInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Paralysis;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RangeSkill;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bg.L1SkillDelay;
import l1r.bh.L1Skills;
import l1r.bi.CalcStat;

public abstract class L1SkillExecutor {
    private static final int[] a = new int[]{1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, 88, 89, 90, 91, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 133, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 163, 164, 165, 166, 168, 169, 170, 171, 175, 176, 181, 185, 190, 195, 201, 204, 209, 211, 214, 216, 219, 233};

    public abstract void a(L1Character var1, int var2, int var3, int var4, String var5);

    public abstract void a(L1Character var1, int var2);

    public abstract void a(L1Character var1);

    public void a(L1Character user, int targetId, int skillid, int x, int y, String message) {
        if (this.b(user, targetId, skillid)) {
            this.a(user, targetId, x, y, message);
            this.g(user, skillid);
        } else {
            this.d(user, skillid);
        }
    }

    private void d(L1Character user, int skillid) {
        if ((skillid == 5 || skillid == 69 || skillid == 131) && user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)user;
            pc.a(new S_Paralysis(7, false));
        }
    }

    public boolean a(L1Character _user, int _targetid, int _skillId) {
        L1PcInstance pc = (L1PcInstance)_user;
        if (pc.aR() || pc.ed()) {
            return false;
        }
        if ((pc.ff() || pc.N()) && !this.a(_skillId)) {
            return false;
        }
        return this.c(_user, _targetid, _skillId);
    }

    private boolean b(L1Character _user, int _targetid, int _skillId) {
        if (_user.ed() || _user.ej()) {
            return false;
        }
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            if (pc.aR() || !this.f(_user, _skillId)) {
                pc.a(new S_ServerMessage(281));
                return false;
            }
            if ((pc.ff() || pc.N()) && !this.a(_skillId)) {
                pc.a(new S_ServerMessage(1003));
                return false;
            }
            if (pc.j().h() > 82) {
                pc.a(new S_ServerMessage(316));
                return false;
            }
            L1PolyMorph poly = PolyTable.a().a(pc.fe());
            if (poly != null && !poly.e()) {
                pc.a(new S_ServerMessage(285));
                return false;
            }
            if (_skillId == 205 || _skillId == 210 || _skillId == 215 || _skillId == 220) {
                for (L1Object obj : L1World.a().b((L1Object)pc, 3)) {
                    L1EffectInstance effect;
                    if (!(obj instanceof L1EffectInstance) || (effect = (L1EffectInstance)obj).h() != _skillId) continue;
                    pc.a(new S_ServerMessage(1412));
                    return false;
                }
            }
        }
        if (_user.bB(64) || _user.bB(161) || _user.bB(1007) || _user.bB(202)) {
            this.b(_user, 280);
            return false;
        }
        if (!this.c(_user, _targetid, _skillId)) {
            return false;
        }
        return this.e(_user, _skillId);
    }

    private boolean c(L1Character _user, int _targetid, int _skillId) {
        L1Skills _skill = SkillsTable.a().a(_skillId);
        int range = _skill.p();
        L1Object tobj = L1World.a().a(_targetid);
        if (tobj instanceof L1Character) {
            L1Character _target = (L1Character)tobj;
            if (range < 0) {
                if (!_user.fu().e(_target.fu())) {
                    return false;
                }
            } else if (_skillId == 116 || _skillId == 118) {
                if (!_user.fq().h()) {
                    return false;
                }
            } else if (_skillId == 51) {
                if (!_user.fq().o()) {
                    this.b(_user, 79);
                    return false;
                }
            } else if (_user.fu().c(_target.fu()) > range || !_user.i(_target.fs(), _target.ft())) {
                this.b(_user, 280);
                return false;
            }
        }
        return true;
    }

    private boolean e(L1Character _user, int _skillId) {
        L1Skills _skill = SkillsTable.a().a(_skillId);
        int _mpConsume = _skill.d();
        int _hpConsume = _skill.e();
        int currentMp = _user.eb();
        int currentHp = _user.ea();
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            _mpConsume = this.a(pc, _skill);
            if (currentHp - _hpConsume < 1) {
                pc.a(new S_ServerMessage(279));
                return false;
            }
            if (currentMp < _mpConsume) {
                pc.a(new S_ServerMessage(278));
                return false;
            }
            pc.a(pc.ea() - _hpConsume);
            pc.i_(pc.eb() - _mpConsume);
            int lawful = pc.fa() + _skill.o();
            lawful = Math.min(lawful, Short.MAX_VALUE);
            lawful = Math.max(lawful, -32767);
            pc.cr(lawful);
            int itemConsume = _skill.f();
            int itemConsumeCount = _skill.g();
            if (itemConsume == 0) {
                return true;
            }
            int itemConsumeLow = itemConsume;
            if (itemConsume == 40318) {
                itemConsumeLow = 640735;
            } else if (itemConsume == 40319) {
                itemConsumeLow = 640736;
            } else if (itemConsume == 40321) {
                itemConsumeLow = 640737;
            } else if (itemConsume == 49158) {
                itemConsumeLow = 640731;
            } else if (itemConsume == 49157) {
                itemConsumeLow = 640734;
            } else if (itemConsume == 49156) {
                itemConsumeLow = 640738;
            } else if (itemConsume == 40068) {
                itemConsumeLow = 640730;
            }
            if (pc.ev() < 55 && pc.j().b(itemConsumeLow, itemConsumeCount)) {
                return true;
            }
            return pc.j().b(itemConsume, itemConsumeCount);
        }
        if (_user instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)_user;
            if (currentHp - _hpConsume < 1 || currentMp < _mpConsume) {
                return false;
            }
            int current_hp = npc.ea() - _hpConsume;
            npc.a(current_hp);
            int current_mp = npc.eb() - _mpConsume;
            npc.i_(current_mp);
        }
        return true;
    }

    private int a(L1PcInstance pc, L1Skills _skill) {
        int _skillId = _skill.a();
        double _mpConsume = _skill.d();
        if (_skillId == 26 && pc.j().h(20013)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 43 && pc.j().h(20013)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 1 && pc.j().h(20014)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 19 && pc.j().h(20014)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 12 && pc.j().h(20015)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 13 && pc.j().h(20015)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 42 && pc.j().h(20015)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 43 && pc.j().h(20008)) {
            _mpConsume /= 2.0;
        } else if (_skillId == 43 && pc.j().h(20023)) {
            _mpConsume = 25.0;
        } else if (_skillId == 54 && pc.j().h(20023)) {
            _mpConsume /= 2.0;
        }
        _mpConsume -= _mpConsume * (double)CalcStat.d(pc.eD()) / 100.0;
        if (_skill.d() > 0) {
            _mpConsume = Math.max(_mpConsume, 1.0);
        }
        return (int)Math.round(_mpConsume);
    }

    private boolean f(L1Character _user, int _skillId) {
        L1Skills _skill = SkillsTable.a().a(_skillId);
        int magicattr = _skill.n();
        if (_user instanceof L1NpcInstance) {
            return true;
        }
        L1PcInstance pc = (L1PcInstance)_user;
        return _skill.c() < 17 || _skill.c() > 22 || magicattr == 0 || magicattr == pc.bC() || pc.l();
    }

    private boolean a(int _skillId) {
        int[] nArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            int skillId = nArray[n2];
            if (skillId == _skillId) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void g(L1Character _user, int _skillId) {
        L1Skills _skill = SkillsTable.a().a(_skillId);
        if (_skill.h() > 0) {
            L1SkillDelay.a(_user, _skill.h());
        }
    }

    public ArrayList<L1Character> a(L1Character user, L1Character target, int area) {
        ArrayList<L1Character> list = new ArrayList<L1Character>();
        if (user.fr() != target.fr() && !(target instanceof L1EffectInstance)) {
            list.add(target);
        }
        ArrayList<Object> objects = new ArrayList();
        objects = area == -2 ? L1World.a().a((L1Object)user, target) : L1World.a().b((L1Object)target, area);
        for (L1Object l1Object : objects) {
            L1NpcInstance npc;
            L1PcInstance pc;
            if (user.fr() == l1Object.fr() || !user.i(l1Object.fs(), l1Object.ft())) continue;
            if (l1Object instanceof L1PcInstance) {
                L1PcInstance tpc = (L1PcInstance)l1Object;
                if (tpc.bN() || tpc.aA() || tpc.eX()) continue;
                pc = null;
                if (user instanceof L1PcInstance) {
                    pc = (L1PcInstance)user;
                } else if (user instanceof L1SummonInstance || user instanceof L1PetInstance) {
                    npc = (L1NpcInstance)user;
                    if (npc.M() != null) {
                        pc = (L1PcInstance)npc.M();
                        if (tpc.fr() == pc.fr()) {
                            continue;
                        }
                    }
                } else if (user instanceof L1MonsterInstance) {
                    list.add(tpc);
                }
                if (pc == null || pc.a(pc, tpc, true)) continue;
                list.add(tpc);
                continue;
            }
            if (l1Object instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)l1Object;
                if (mob.eX() || mob.ac() == 1 || mob.ac() == 2 || user instanceof L1MonsterInstance) continue;
                list.add(mob);
                continue;
            }
            if (l1Object instanceof L1SummonInstance || l1Object instanceof L1PetInstance) {
                L1NpcInstance pet = (L1NpcInstance)l1Object;
                pc = null;
                if (user instanceof L1PcInstance) {
                    pc = (L1PcInstance)user;
                } else if (user instanceof L1SummonInstance || user instanceof L1PetInstance) {
                    npc = (L1NpcInstance)user;
                    if (npc.M() != null) {
                        pc = (L1PcInstance)npc.M();
                    }
                } else if (user instanceof L1MonsterInstance) {
                    list.add(pet);
                    continue;
                }
                L1PcInstance master = (L1PcInstance)pet.M();
                if (master == null || pc == null || master.fr() == pc.fr() || master.a(pc, master, true)) continue;
                list.add(pet);
                continue;
            }
            if (!(l1Object instanceof L1NpcInstance) || !(user instanceof L1DotaInstance)) continue;
            list.add((L1NpcInstance)l1Object);
        }
        return list;
    }

    protected void b(L1Character _user, int msg) {
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_ServerMessage(msg));
        }
    }

    protected void a(L1Character target, L1Skills _skill) {
        this.c(target, _skill.t());
    }

    protected void c(L1Character target, int gfxid) {
        if (target instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)target;
            pc.a(new S_SkillSound(target.fr(), gfxid));
            pc.a(new S_ProtoBuffers(485, pc));
        }
        target.b(new S_SkillSound(target.fr(), gfxid));
    }

    protected void b(L1Character _user, L1Skills _skill) {
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_DoActionGFX(pc.fr(), _skill.s()));
        }
        _user.b(new S_DoActionGFX(_user.fr(), _skill.s()));
    }

    protected void a(L1Character _user, L1Skills _skill, ArrayList<L1Character> list) {
        int gfxid = _skill.t();
        int actid = _skill.s();
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_RangeSkill(pc, list, gfxid, actid, 8));
        }
        _user.b(new S_RangeSkill(_user, list, gfxid, actid, 8));
    }

    protected void a(L1Character _user, L1Character target, L1Skills _skill, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_AttackPacket(pc, target.fr(), _skill.s(), dmg, 0));
        }
        _user.b(new S_AttackPacket(_user, target.fr(), _skill.s(), dmg, 0));
    }

    protected void b(L1Character _user, L1Character target, L1Skills _skill, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_AttackPacket(_user, target, _skill.s(), _skill.t(), dmg, 6, 0));
        }
        _user.b(new S_AttackPacket(_user, target, _skill.s(), _skill.t(), dmg, 6, 0));
        if (dmg > 0) {
            target.a(new S_DoActionGFX(target.fr(), 2), _user);
        }
    }

    protected void a(L1Character _user, L1Character target, int actid, int gfxid, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)_user;
            pc.a(new S_AttackPacket(_user, target, actid, gfxid, dmg, 6, 0));
        }
        _user.b(new S_AttackPacket(_user, target, actid, gfxid, dmg, 6, 0));
        if (dmg > 0) {
            target.a(new S_DoActionGFX(target.fr(), 2), _user);
        }
    }
}
