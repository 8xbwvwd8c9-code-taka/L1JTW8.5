/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import java.util.concurrent.CopyOnWriteArrayList;

public class dd
extends eu {
    public static final int a = 64;
    public static final int b = 65;
    public static final int c = 66;
    public static final int d = 67;
    public static final int e = 68;
    public static final int f = 69;
    public static final int g = 70;

    public dd(int type) {
        this.c(121);
        this.c(type);
        if (type == 64) {
            this.c(5);
        }
    }

    public dd(CopyOnWriteArrayList<u> playerList, u pc) {
        this.c(121);
        this.c(66);
        this.b(playerList.size());
        this.b(playerList.indexOf(pc));
        for (u player : playerList) {
            if (player == null) continue;
            this.a(player.et());
        }
    }

    public dd(int maxLap, int lap) {
        this.c(121);
        this.c(67);
        this.b(maxLap);
        this.b(lap);
    }

    public dd(String winnerName, int time) {
        this.c(121);
        this.c(68);
        this.a(winnerName);
        this.a(time * 1000);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Race";
    }
}

