/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.ay;
import ao.be;
import ap.g;
import ap.h;
import ap.s;
import ap.t;
import ap.u;
import ap.z;
import aq.aa;
import aq.ae;
import aq.aq;
import aq.f;
import be.ak;
import be.cn;
import be.dc;
import be.de;
import be.ds;
import be.ee;
import bh.v;
import bi.d;
import java.util.ArrayList;

public abstract class a {
    private static final int[] a = new int[]{1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, 88, 89, 90, 91, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 133, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 163, 164, 165, 166, 168, 169, 170, 171, 175, 176, 181, 185, 190, 195, 201, 204, 209, 211, 214, 216, 219, 233};

    public abstract void a(f var1, int var2, int var3, int var4, String var5);

    public abstract void a(f var1, int var2);

    public abstract void a(f var1);

    public void a(f user, int targetId, int skillid, int x2, int y2, String message) {
        if (this.b(user, targetId, skillid)) {
            this.a(user, targetId, x2, y2, message);
            this.g(user, skillid);
        } else {
            this.d(user, skillid);
        }
    }

    private void d(f user, int skillid) {
        if ((skillid == 5 || skillid == 69 || skillid == 131) && user instanceof u) {
            u pc = (u)user;
            pc.a(new cn(7, false));
        }
    }

    public boolean a(f _user, int _targetid, int _skillId) {
        u pc = (u)_user;
        if (pc.aR() || pc.ed()) {
            return false;
        }
        if ((pc.ff() || pc.N()) && !this.a(_skillId)) {
            return false;
        }
        return this.c(_user, _targetid, _skillId);
    }

    private boolean b(f _user, int _targetid, int _skillId) {
        if (_user.ed() || _user.ej()) {
            return false;
        }
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.aR() || !this.f(_user, _skillId)) {
                pc.a(new ds(281));
                return false;
            }
            if ((pc.ff() || pc.N()) && !this.a(_skillId)) {
                pc.a(new ds(1003));
                return false;
            }
            if (pc.j().h() > 82) {
                pc.a(new ds(316));
                return false;
            }
            ae poly = ay.a().a(pc.fe());
            if (poly != null && !poly.e()) {
                pc.a(new ds(285));
                return false;
            }
            if (_skillId == 205 || _skillId == 210 || _skillId == 215 || _skillId == 220) {
                for (aa obj : aq.a().b((aa)pc, 3)) {
                    h effect;
                    if (!(obj instanceof h) || (effect = (h)obj).h() != _skillId) continue;
                    pc.a(new ds(1412));
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

    private boolean c(f _user, int _targetid, int _skillId) {
        v _skill = be.a().a(_skillId);
        int range = _skill.p();
        aa tobj = aq.a().a(_targetid);
        if (tobj instanceof f) {
            f _target = (f)tobj;
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

    private boolean e(f _user, int _skillId) {
        v _skill = be.a().a(_skillId);
        int _mpConsume = _skill.d();
        int _hpConsume = _skill.e();
        int currentMp = _user.eb();
        int currentHp = _user.ea();
        if (_user instanceof u) {
            u pc = (u)_user;
            _mpConsume = this.a(pc, _skill);
            if (currentHp - _hpConsume < 1) {
                pc.a(new ds(279));
                return false;
            }
            if (currentMp < _mpConsume) {
                pc.a(new ds(278));
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
        if (_user instanceof t) {
            t npc = (t)_user;
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

    private int a(u pc, v _skill) {
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
        _mpConsume -= _mpConsume * (double)d.d(pc.eD()) / 100.0;
        if (_skill.d() > 0) {
            _mpConsume = Math.max(_mpConsume, 1.0);
        }
        return (int)Math.round(_mpConsume);
    }

    private boolean f(f _user, int _skillId) {
        v _skill = be.a().a(_skillId);
        int magicattr = _skill.n();
        if (_user instanceof t) {
            return true;
        }
        u pc = (u)_user;
        return _skill.c() < 17 || _skill.c() > 22 || magicattr == 0 || magicattr == pc.bC() || pc.l();
    }

    private boolean a(int _skillId) {
        int[] nArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillId = nArray[n3];
            if (skillId == _skillId) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void g(f _user, int _skillId) {
        v _skill = be.a().a(_skillId);
        if (_skill.h() > 0) {
            bg.a.a(_user, _skill.h());
        }
    }

    public ArrayList<f> a(f user, f target, int area) {
        ArrayList<f> list = new ArrayList<f>();
        if (user.fr() != target.fr() && !(target instanceof h)) {
            list.add(target);
        }
        ArrayList<Object> objects = new ArrayList();
        objects = area == -2 ? aq.a().a((aa)user, target) : aq.a().b((aa)target, area);
        for (aa aa2 : objects) {
            t npc;
            u pc;
            if (user.fr() == aa2.fr() || !user.i(aa2.fs(), aa2.ft())) continue;
            if (aa2 instanceof u) {
                u tpc = (u)aa2;
                if (tpc.bN() || tpc.aA() || tpc.eX()) continue;
                pc = null;
                if (user instanceof u) {
                    pc = (u)user;
                } else if (user instanceof z || user instanceof ap.v) {
                    npc = (t)user;
                    if (npc.M() != null) {
                        pc = (u)npc.M();
                        if (tpc.fr() == pc.fr()) {
                            continue;
                        }
                    }
                } else if (user instanceof s) {
                    list.add(tpc);
                }
                if (pc == null || pc.a(pc, tpc, true)) continue;
                list.add(tpc);
                continue;
            }
            if (aa2 instanceof s) {
                s mob = (s)aa2;
                if (mob.eX() || mob.ac() == 1 || mob.ac() == 2 || user instanceof s) continue;
                list.add(mob);
                continue;
            }
            if (aa2 instanceof z || aa2 instanceof ap.v) {
                t pet = (t)aa2;
                pc = null;
                if (user instanceof u) {
                    pc = (u)user;
                } else if (user instanceof z || user instanceof ap.v) {
                    npc = (t)user;
                    if (npc.M() != null) {
                        pc = (u)npc.M();
                    }
                } else if (user instanceof s) {
                    list.add(pet);
                    continue;
                }
                u master = (u)pet.M();
                if (master == null || pc == null || master.fr() == pc.fr() || master.a(pc, master, true)) continue;
                list.add(pet);
                continue;
            }
            if (!(aa2 instanceof t) || !(user instanceof g)) continue;
            list.add((t)aa2);
        }
        return list;
    }

    protected void b(f _user, int msg) {
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ds(msg));
        }
    }

    protected void a(f target, v _skill) {
        this.c(target, _skill.t());
    }

    protected void c(f target, int gfxid) {
        if (target instanceof u) {
            u pc = (u)target;
            pc.a(new ee(target.fr(), gfxid));
            pc.a(new dc(485, pc));
        }
        target.b(new ee(target.fr(), gfxid));
    }

    protected void b(f _user, v _skill) {
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ak(pc.fr(), _skill.s()));
        }
        _user.b(new ak(_user.fr(), _skill.s()));
    }

    protected void a(f _user, v _skill, ArrayList<f> list) {
        int gfxid = _skill.t();
        int actid = _skill.s();
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new de(pc, list, gfxid, actid, 8));
        }
        _user.b(new de(_user, list, gfxid, actid, 8));
    }

    protected void a(f _user, f target, v _skill, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new be.g(pc, target.fr(), _skill.s(), dmg, 0));
        }
        _user.b(new be.g(_user, target.fr(), _skill.s(), dmg, 0));
    }

    protected void b(f _user, f target, v _skill, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new be.g(_user, target, _skill.s(), _skill.t(), dmg, 6, 0));
        }
        _user.b(new be.g(_user, target, _skill.s(), _skill.t(), dmg, 6, 0));
        if (dmg > 0) {
            target.a(new ak(target.fr(), 2), _user);
        }
    }

    protected void a(f _user, f target, int actid, int gfxid, int dmg) {
        _user.ct(_user.a(target));
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new be.g(_user, target, actid, gfxid, dmg, 6, 0));
        }
        _user.b(new be.g(_user, target, actid, gfxid, dmg, 6, 0));
        if (dmg > 0) {
            target.a(new ak(target.fr(), 2), _user);
        }
    }
}

