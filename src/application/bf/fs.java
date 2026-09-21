/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.ai;
import aq.f;
import bf.a;
import bh.v;

public class fs
extends a {
    private final int a = 205;
    private final v b = be.a().a(205);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        int[][] offsets = nArrayArray;
        int heading = _user.fb();
        int[] offest = offsets[heading];
        if (_user instanceof u) {
            u pc = (u)_user;
            int spawnx = _user.fs() + offest[0];
            int spawny = _user.ft() + offest[1];
            int spawntime = this.b.v() * 1000;
            ai.a().a(6706, spawntime, spawnx, spawny, _user.fp(), pc, 205);
        }
    }

    @Override
    public void a(f cha) {
    }
}

