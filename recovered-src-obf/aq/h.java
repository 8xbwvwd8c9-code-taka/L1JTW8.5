/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import be.ds;
import java.util.concurrent.CopyOnWriteArrayList;
import l1j.server.a;

public class h {
    private final CopyOnWriteArrayList<u> a = new CopyOnWriteArrayList();
    private u b = null;

    public void a(u pc) {
        if (pc == null) {
            throw new NullPointerException();
        }
        if (this.a.size() == l1j.server.a.X && !this.b.l() || this.a.contains(pc)) {
            return;
        }
        if (this.a.isEmpty()) {
            this.f(pc);
        }
        this.a.add(pc);
        pc.a(this);
    }

    private void e(u pc) {
        if (!this.a.contains(pc)) {
            return;
        }
        this.a.remove(pc);
        pc.a((h)null);
    }

    public boolean a() {
        return this.a.size() < l1j.server.a.X;
    }

    public int b() {
        return l1j.server.a.X - this.a.size();
    }

    private void f(u pc) {
        this.b = pc;
    }

    public u c() {
        return this.b;
    }

    public boolean b(u pc) {
        return pc.fr() == this.b.fr();
    }

    public String d() {
        String _result = new String("");
        for (u pc : this.a) {
            _result = String.valueOf(_result) + pc.et() + " ";
        }
        return _result;
    }

    private void g() {
        u[] members;
        u[] uArray = members = this.e();
        int n2 = members.length;
        int n3 = 0;
        while (n3 < n2) {
            u member = uArray[n3];
            this.e(member);
            member.a(new ds(418));
            ++n3;
        }
    }

    public void c(u pc) {
        u[] members = this.e();
        if (this.b(pc)) {
            this.g();
        } else if (this.f() == 2) {
            this.e(pc);
            u leader = this.c();
            this.e(leader);
            this.a(pc, pc);
            this.a(leader, pc);
        } else {
            this.e(pc);
            u[] uArray = members;
            int n2 = members.length;
            int n3 = 0;
            while (n3 < n2) {
                u member = uArray[n3];
                this.a(member, pc);
                ++n3;
            }
            this.a(pc, pc);
        }
    }

    public void d(u pc) {
        if (this.f() == 2) {
            this.e(pc);
            u leader = this.c();
            this.e(leader);
        } else {
            this.e(pc);
        }
        pc.a(new ds(419));
    }

    public u[] e() {
        return this.a.toArray(new u[this.a.size()]);
    }

    public int f() {
        return this.a.size();
    }

    private void a(u sendTo, u left) {
        sendTo.a(new ds(420, left.et()));
    }
}

