/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import java.util.concurrent.CopyOnWriteArrayList;

public class co
extends eu {
    public co(int type, u pc) {
        switch (type) {
            case 104: {
                this.a(pc);
                break;
            }
            case 105: {
                this.b(pc);
                break;
            }
            case 106: {
                this.c(pc);
                break;
            }
            case 110: {
                this.d(pc);
            }
        }
    }

    private void a(u pc) {
        u leader = pc.aL().a();
        CopyOnWriteArrayList<u> memberList = pc.aL().c();
        this.c(121);
        this.c(104);
        this.c(memberList.size() - 1);
        this.a(leader.fr());
        this.a(leader.et());
        this.c(leader.ay());
        this.b(0);
        this.c(leader.ea() * 100 / leader.ew());
        this.c(leader.eb() * 100 / leader.ex());
        this.a(leader.fp());
        this.b(leader.fs());
        this.b(leader.ft());
        this.a(0);
        this.c(1);
        int i2 = 0;
        while (i2 < memberList.size()) {
            u member = memberList.get(i2);
            if (member != null && member.fr() != leader.fr()) {
                this.a(member.fr());
                this.a(member.et());
                this.c(member.ay());
                this.b(0);
                this.c(member.ea() * 100 / member.ew());
                this.c(member.eb() * 100 / member.ex());
                this.a(member.fp());
                this.b(member.fs());
                this.b(member.ft());
                this.a(0);
                this.c(0);
            }
            ++i2;
        }
    }

    private void b(u pc) {
        this.c(121);
        this.c(105);
        this.a(pc.fr());
        this.a(pc.et());
        this.c(pc.ay());
        this.b(0);
        this.a(pc.fp());
        this.b(pc.fs());
        this.b(pc.ft());
    }

    private void c(u pc) {
        this.c(121);
        this.c(106);
        this.a(pc.fr());
        this.b(0);
    }

    private void d(u pc) {
        if (pc.aL() == null) {
            return;
        }
        CopyOnWriteArrayList<u> memberList = pc.aL().c();
        this.c(121);
        this.c(110);
        this.c(memberList.size());
        for (u member : memberList) {
            this.a(member.fr());
            this.a(member.fp());
            this.b(member.fs());
            this.b(member.ft());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Party";
    }
}

