/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import l1r.am.ListSprReader__obf_c;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Attribute;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DoorPack;
import l1r.be.S_RemoveObject;
import l1r.bh.L1DoorGfx;
import l1r.bh.L1Npc;

public class L1DoorInstance
extends L1NpcInstance {
    private final L1DoorGfx y;
    private int z = 0;
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 29;
    private int E = 0;
    private final int F;
    private final int G;

    public L1DoorInstance(L1Npc template, int doorId, L1DoorGfx gfx, L1Location loc, int hp, int keeper, boolean isOpening) {
        super(template);
        this.f_(doorId);
        this.bG(hp);
        this.a(hp);
        this.a(loc);
        this.q(loc.f());
        this.r(loc.g());
        this.c(gfx.b());
        this.F = gfx.e();
        this.G = gfx.f();
        int baseLoc = gfx.b() == 0 ? loc.f() : loc.g();
        this.d(baseLoc + gfx.d());
        this.e(baseLoc + gfx.c());
        this.f(keeper);
        if (isOpening) {
            this.f();
        }
        this.y = gfx;
    }

    public int ab_() {
        return Math.abs(this.y.d()) + Math.abs(this.y.c());
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        if (this.ew() == 0) {
            return;
        }
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_DoorPack(this));
        this.d(perceivedFrom);
    }

    @Override
    public void aa_() {
        this.X(true);
        this.d((L1PcInstance)null);
        this.k(true);
        if (this.y() != null) {
            this.y().g();
        }
        this.t();
        this.k = null;
        L1World.a().d(this);
        L1World.a().b(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            pc.d(this);
            pc.a(new S_RemoveObject(this));
        }
        this.es();
    }

    @Override
    public void b(L1Character attacker, int damage) {
        if (this.ew() == 0) {
            return;
        }
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        int newHp = this.ea() - damage;
        if (newHp <= 0 && !this.eX()) {
            this.av();
            return;
        }
        this.bx(newHp);
        this.au();
    }

    private void au() {
        int newStatus = 0;
        if (this.ew() * 1 / 6 > this.ea()) {
            newStatus = 36;
        } else if (this.ew() * 2 / 6 > this.ea()) {
            newStatus = 35;
        } else if (this.ew() * 3 / 6 > this.ea()) {
            newStatus = 34;
        } else if (this.ew() * 4 / 6 > this.ea()) {
            newStatus = 33;
        } else if (this.ew() * 5 / 6 > this.ea()) {
            newStatus = 32;
        }
        if (this.eY() == newStatus) {
            return;
        }
        this.cq(newStatus);
        this.b(new S_DoActionGFX(this.fr(), newStatus));
    }

    @Override
    public void a(int i) {
        int currentHp = i;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
    }

    private void av() {
        this.bx(0);
        this.X(true);
        int status = 36;
        if (ListSprReader__obf_c.a().b(this.fe(), 37)) {
            status = 37;
        }
        this.cq(status);
        this.fq().a(this.fu(), true);
        this.b(new S_DoActionGFX(this.fr(), 37));
        this.d((L1PcInstance)null);
    }

    private void d(L1PcInstance pc) {
        int y;
        int x;
        int entranceX = this.k();
        int entranceY = this.l();
        int leftEdgeLocation = this.ac_();
        int rightEdgeLocation = this.n();
        int size = rightEdgeLocation - leftEdgeLocation;
        if (size == 0) {
            this.a(pc, entranceX, entranceY);
        } else if (this.j() == 0) {
            x = leftEdgeLocation;
            while (x <= rightEdgeLocation) {
                this.a(pc, x, entranceY);
                ++x;
            }
        } else {
            y = leftEdgeLocation;
            while (y <= rightEdgeLocation) {
                this.a(pc, entranceX, y);
                ++y;
            }
        }
        if (this.j() == 0) {
            x = leftEdgeLocation - 1;
            while (x <= rightEdgeLocation + 1) {
                this.fq().a(x, this.ft(), this.aw());
                ++x;
            }
        } else {
            y = leftEdgeLocation - 1;
            while (y <= rightEdgeLocation + 1) {
                this.fq().a(this.fs(), y, this.aw());
                ++y;
            }
        }
    }

    private boolean aw() {
        return this.eX() || this.o() == 28;
    }

    private void a(L1PcInstance pc, int x, int y) {
        this.fq().a(x, y, this.aw(), this.j());
        if (pc != null) {
            pc.a(new S_Attribute(x, y, this.j(), this.aw()));
        } else {
            this.b(new S_Attribute(x, y, this.j(), this.aw()));
        }
    }

    public void f() {
        if (!this.eX() && !this.aw()) {
            this.B(28);
            this.b(new S_DoActionGFX(this.fr(), 28));
            this.d((L1PcInstance)null);
        }
    }

    public void g() {
        if (!this.eX() && this.aw()) {
            this.B(29);
            this.b(new S_DoActionGFX(this.fr(), 29));
            this.d((L1PcInstance)null);
        }
    }

    public void h() {
        if (this.ew() <= 1) {
            return;
        }
        this.X(false);
        this.a(this.ew());
        this.cq(0);
        this.B(28);
        this.g();
    }

    public int i() {
        return this.z;
    }

    public void f_(int i) {
        this.z = i;
    }

    public int j() {
        return this.A;
    }

    public void c(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException();
        }
        this.A = i;
    }

    public int k() {
        return this.fs() + this.F;
    }

    public int l() {
        return this.ft() + this.G;
    }

    public int ac_() {
        return this.B;
    }

    public void d(int i) {
        this.B = i;
    }

    public int n() {
        return this.C;
    }

    public void e(int i) {
        this.C = i;
    }

    public int o() {
        return this.D;
    }

    private void B(int newStatus) {
        if (newStatus != 28 && newStatus != 29) {
            throw new IllegalArgumentException();
        }
        this.D = newStatus;
    }

    public int p() {
        return this.E;
    }

    public void f(int i) {
        this.E = i;
    }
}
