/*
 * Decompiled with CFR 0.152.
 */
package be;

import ai.d;
import ap.u;
import aq.f;
import be.eu;

public class g
extends eu {
    public static final int a = 0;
    public static final int b = 6;
    public static final int c = 8;
    public static final int d = 0;
    public static final int e = 2;
    public static final int f = 4;
    public static final int g = 8;

    public g(f atk, int targetid, int actid, int dmg, int effectType) {
        this.c(163);
        this.c(actid);
        this.a(atk.fr());
        this.a(targetid);
        this.b(dmg);
        this.c(atk.fb());
        this.a(0);
        this.c(effectType);
        if (effectType == 2) {
            this.a(this.a(atk));
        } else {
            this.a(0);
        }
        this.b(0);
    }

    private int a(f atker) {
        if (atker instanceof u) {
            u pc = (u)atker;
            int visual = pc.k();
            if (visual == 0) {
                return 0;
            }
            if (visual == 24) {
                return 13409;
            }
            if (visual == 50) {
                return 13410;
            }
            if (visual == 4) {
                return 13411;
            }
            if (visual == 46) {
                return 13412;
            }
            if (visual == 40 || visual == 58) {
                return 13413;
            }
            if (visual == 11) {
                return 13414;
            }
            if (visual == 88) {
                return 13415;
            }
            if (visual == 58) {
                return 13416;
            }
            if (visual == 54) {
                return 13417;
            }
            if (visual == 20) {
                return 13392;
            }
            if (visual == 62) {
                return 13398;
            }
        }
        return 0;
    }

    public g(f cha, f target, int actid, int gfxid, int dmg, int useType, int effectType) {
        this.c(163);
        this.c(actid);
        this.a(cha.fr());
        this.a(target.fr());
        this.b(dmg);
        this.c(cha.fb());
        this.a(ai.d.a().b());
        this.b(gfxid);
        this.c(useType);
        this.b(cha.fs());
        this.b(cha.ft());
        this.b(target.fs());
        this.b(target.ft());
        this.b(0);
        this.c(effectType);
        if (effectType == 2) {
            this.a(this.a(cha));
        } else {
            this.a(0);
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_AttackPacket";
    }
}

