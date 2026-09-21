/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import java.util.ArrayList;

public class dy
extends eu {
    public dy(u pc) {
        ArrayList<Integer> buyList = dy.a(pc);
        this.c(60);
        this.a(100);
        this.b(buyList.size());
        for (int i2 : buyList) {
            this.a(i2);
        }
    }

    public static ArrayList<Integer> a(u pc) {
        int RC = 0;
        switch (pc.ay()) {
            case 0: {
                if (pc.ev() > 20) {
                    RC = 16;
                    break;
                }
                if (pc.ev() <= 10) break;
                RC = 8;
                break;
            }
            case 1: 
            case 7: {
                if (pc.ev() < 50) break;
                RC = 8;
                break;
            }
            case 2: {
                if (pc.ev() >= 24) {
                    RC = 23;
                    break;
                }
                if (pc.ev() >= 16) {
                    RC = 16;
                    break;
                }
                if (pc.ev() < 8) break;
                RC = 8;
                break;
            }
            case 3: {
                if (pc.ev() >= 12) {
                    RC = 23;
                    break;
                }
                if (pc.ev() >= 8) {
                    RC = 16;
                    break;
                }
                if (pc.ev() < 4) break;
                RC = 8;
                break;
            }
            case 4: {
                if (pc.ev() >= 24) {
                    RC = 16;
                    break;
                }
                if (pc.ev() < 12) break;
                RC = 8;
                break;
            }
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        int k2 = 0;
        while (k2 < RC) {
            if (!pc.h(k2 + 1)) {
                list.add(k2);
            }
            ++k2;
        }
        return list;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SkillBuy";
    }
}

