/*
 * Decompiled with CFR 0.152.
 */
package bh;

import ao.q;
import aq.i;
import java.util.ArrayList;
import java.util.Calendar;

public class d {
    private final int b;
    private final String c;
    private Calendar d;
    private int e;
    private int f;
    private int g;
    private boolean h = false;
    private final ArrayList<a> i;
    private final ArrayList<a> j;
    public static final String a = "\u5b89\u5b89\u59b3\u597d\u518d\u898b_";
    private static final int k = 1;
    private static final int l = 2;
    private static final int m = 3;
    private static final int n = 4;
    private static final int o = 5;
    private static final int p = 6;
    private static final int q = 7;

    public d(int id, String name) {
        this.b = id;
        this.c = name;
        this.i = new ArrayList();
        this.j = new ArrayList();
        switch (this.b) {
            case 1: {
                this.i.add(new a(190285, "$4210"));
                this.i.add(new a(190286, "$4211"));
                this.i.add(new a(190287, "$4212"));
                this.i.add(new a(190288, "$4213"));
                this.j.add(new a(11111, "$240"));
                break;
            }
            case 2: {
                this.i.add(new a(190289, "$4214"));
                this.i.add(new a(190290, "$4215"));
                this.i.add(new a(190291, "$4216"));
                this.i.add(new a(190292, "$4217"));
                this.j.add(new a(11111, "$511"));
                break;
            }
            case 3: {
                this.i.add(new a(190293, "$4218"));
                this.i.add(new a(190294, "$4219"));
                this.i.add(new a(190295, "$4220"));
                this.i.add(new a(190296, "$4221"));
                this.j.add(new a(11111, "$240"));
                break;
            }
            case 4: {
                this.i.add(new a(190297, "$4222"));
                this.i.add(new a(190298, "$4223"));
                this.i.add(new a(190299, "$4224"));
                this.i.add(new a(190300, "$4225"));
                break;
            }
            case 5: {
                this.i.add(new a(190301, "$4226"));
                this.i.add(new a(190302, "$4227"));
                this.i.add(new a(190303, "$4228"));
                this.i.add(new a(190304, "$4229"));
                break;
            }
            case 6: {
                this.i.add(new a(190305, "$4230"));
                this.i.add(new a(190306, "$4231"));
                this.i.add(new a(190307, "$4232"));
                this.i.add(new a(190308, "$4233"));
                break;
            }
            case 7: {
                this.i.add(new a(190309, "$4234"));
                this.i.add(new a(190310, "$4235"));
                this.i.add(new a(190311, "$4236"));
                this.i.add(new a(190312, "$4237"));
            }
        }
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public Calendar c() {
        return this.d;
    }

    public Calendar d() {
        Calendar warEndTime = (Calendar)this.d.clone();
        warEndTime.add(l1j.server.a.ai, l1j.server.a.ah);
        return warEndTime;
    }

    public void a(Calendar i2) {
        this.d = i2;
    }

    public int e() {
        return this.e;
    }

    public void a(int i2) {
        this.e = i2;
    }

    public int f() {
        if (this.f < 0) {
            return 0;
        }
        return this.f;
    }

    public void b(int i2) {
        this.f = i2;
    }

    public int g() {
        return this.g;
    }

    public void c(int i2) {
        this.g = i2;
    }

    public int h() {
        i clan;
        if (this.g > 0 && (clan = ao.q.a().a(this.g)) != null) {
            return clan.k();
        }
        return 0;
    }

    public int i() {
        int counts = 0;
        for (a m2 : this.i) {
            counts += m2.c;
        }
        return counts;
    }

    public boolean j() {
        return this.h;
    }

    public void a(boolean isNowWar) {
        this.h = isNowWar;
    }

    public ArrayList<a> k() {
        return this.i;
    }

    public ArrayList<a> l() {
        return this.j;
    }

    public class a {
        public int a;
        public String b;
        public int c = 0;

        private a(int _npcid, String _nameid) {
            this.a = _npcid;
            this.b = _nameid;
        }
    }
}

