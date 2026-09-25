/*
 * Decompiled with CFR 0.152.
 */
package be;

import ai.d;
import aq.f;
import be.eu;
import java.util.ArrayList;

public class de
extends eu {
    public static final int a = 0;
    public static final int b = 8;

    public de(f cha, ArrayList<f> targetList, int spellgfx, int actionId, int type) {
        this.c(150);
        this.c(actionId);
        this.a(cha.fr());
        this.b(cha.fs());
        this.b(cha.ft());
        if (type == 0) {
            this.c(cha.fb());
        } else if (type == 8) {
            f target = targetList.get(0);
            int newHeading = de.a(cha.fs(), cha.ft(), target.fs(), target.ft());
            cha.ct(newHeading);
            this.c(cha.fb());
        }
        this.a(d.a().b());
        this.b(spellgfx);
        this.c(type);
        this.b(0);
        this.b(targetList.size());
        for (f target : targetList) {
            this.a(target.fr());
            this.b(target.fo());
        }
        this.c(0);
        this.b(0);
    }

    private static int a(int myx, int myy, int tx, int ty) {
        int newheading = 0;
        if (tx > myx && ty > myy) {
            newheading = 3;
        }
        if (tx < myx && ty < myy) {
            newheading = 7;
        }
        if (tx > myx && ty == myy) {
            newheading = 2;
        }
        if (tx < myx && ty == myy) {
            newheading = 6;
        }
        if (tx == myx && ty < myy) {
            newheading = 0;
        }
        if (tx == myx && ty > myy) {
            newheading = 4;
        }
        if (tx < myx && ty > myy) {
            newheading = 5;
        }
        if (tx > myx && ty < myy) {
            newheading = 1;
        }
        return newheading;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_RangeSkill";
    }
}

