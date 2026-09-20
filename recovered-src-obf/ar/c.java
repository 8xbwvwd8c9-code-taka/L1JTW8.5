/*
 * Decompiled with CFR 0.152.
 */
package ar;

import ar.a;

class c
extends a {
    c() {
    }

    @Override
    public int b(int ac2) {
        return ac2 / 3;
    }

    @Override
    public int c(int playerLevel) {
        return Math.min(4, playerLevel / 9);
    }

    @Override
    public int[] a() {
        return new int[]{13, 11, 14, 10, 10, 8};
    }

    @Override
    public int b() {
        return 9;
    }

    @Override
    public int c() {
        return 18;
    }

    @Override
    public int d() {
        return 0;
    }

    @Override
    public int d(int level) {
        return -(level / 6);
    }

    @Override
    public int e(int level) {
        return level / 5;
    }

    @Override
    public int f(int level) {
        return level / 10;
    }

    @Override
    public int g(int level) {
        return 0;
    }

    @Override
    public int h(int level) {
        return level / 5;
    }

    @Override
    public int i(int level) {
        return level / 5;
    }

    @Override
    public int e() {
        return 15;
    }

    @Override
    public int f() {
        return l1j.server.a.aH;
    }

    @Override
    public int g() {
        return l1j.server.a.aI;
    }

    @Override
    public int j(int wis) {
        if (wis <= 14) {
            return 2;
        }
        if (wis >= 15 && wis <= 17) {
            return 3;
        }
        if (wis >= 18 && wis <= 23) {
            return 3;
        }
        if (wis >= 24 && wis <= 24) {
            return 3;
        }
        if (wis >= 25 && wis <= 26) {
            return 4;
        }
        if (wis >= 27 && wis <= 29) {
            return 4;
        }
        if (wis >= 30 && wis <= 35) {
            return 5;
        }
        if (wis >= 36 && wis <= 38) {
            return 5;
        }
        if (wis >= 39 && wis <= 39) {
            return 5;
        }
        if (wis >= 40 && wis <= 44) {
            return 6;
        }
        if (wis >= 45) {
            return 7;
        }
        return 0;
    }

    @Override
    public int k(int wis) {
        if (wis <= 14) {
            return 2;
        }
        if (wis >= 15 && wis <= 17) {
            return 2;
        }
        if (wis >= 18 && wis <= 23) {
            return 3;
        }
        if (wis >= 24 && wis <= 24) {
            return 4;
        }
        if (wis >= 25 && wis <= 26) {
            return 3;
        }
        if (wis >= 27 && wis <= 29) {
            return 4;
        }
        if (wis >= 30 && wis <= 35) {
            return 4;
        }
        if (wis >= 36 && wis <= 38) {
            return 5;
        }
        if (wis >= 39 && wis <= 39) {
            return 6;
        }
        if (wis >= 40 && wis <= 44) {
            return 5;
        }
        if (wis >= 45) {
            return 6;
        }
        return 0;
    }

    @Override
    public String h() {
        return "R";
    }
}

