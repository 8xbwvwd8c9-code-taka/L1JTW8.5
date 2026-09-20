/*
 * Decompiled with CFR 0.152.
 */
package ar;

import ar.a;

class i
extends a {
    i() {
    }

    @Override
    public int b(int ac2) {
        return ac2 / 5;
    }

    @Override
    public int c(int playerLevel) {
        return Math.min(10, playerLevel / 4);
    }

    @Override
    public int[] a() {
        return new int[]{8, 7, 12, 14, 14, 8};
    }

    @Override
    public int b() {
        return 12;
    }

    @Override
    public int c() {
        return 15;
    }

    @Override
    public int d() {
        return 12;
    }

    @Override
    public int d(int level) {
        return -(level / 8);
    }

    @Override
    public int e(int level) {
        return level / 10;
    }

    @Override
    public int f(int level) {
        return 0;
    }

    @Override
    public int g(int level) {
        return 0;
    }

    @Override
    public int h(int level) {
        return level / 8;
    }

    @Override
    public int i(int level) {
        return level / 8;
    }

    @Override
    public int e() {
        return 7;
    }

    @Override
    public int f() {
        return l1j.server.a.aD;
    }

    @Override
    public int g() {
        return l1j.server.a.aE;
    }

    @Override
    public int j(int wis) {
        if (wis <= 14) {
            return 6;
        }
        if (wis >= 15 && wis <= 17) {
            return 8;
        }
        if (wis >= 18 && wis <= 19) {
            return 8;
        }
        if (wis >= 20 && wis <= 20) {
            return 10;
        }
        if (wis >= 21 && wis <= 23) {
            return 10;
        }
        if (wis >= 24 && wis <= 24) {
            return 10;
        }
        if (wis >= 25 && wis <= 26) {
            return 12;
        }
        if (wis >= 27 && wis <= 29) {
            return 12;
        }
        if (wis >= 30 && wis <= 32) {
            return 14;
        }
        if (wis >= 33 && wis <= 34) {
            return 14;
        }
        if (wis >= 35 && wis <= 35) {
            return 16;
        }
        if (wis >= 36 && wis <= 38) {
            return 16;
        }
        if (wis >= 39 && wis <= 39) {
            return 16;
        }
        if (wis >= 40 && wis <= 41) {
            return 18;
        }
        if (wis >= 42 && wis <= 44) {
            return 18;
        }
        if (wis >= 45) {
            return 20;
        }
        return 0;
    }

    @Override
    public int k(int wis) {
        if (wis <= 14) {
            return 5;
        }
        if (wis >= 15 && wis <= 17) {
            return 5;
        }
        if (wis >= 18 && wis <= 19) {
            return 7;
        }
        if (wis >= 20 && wis <= 20) {
            return 5;
        }
        if (wis >= 21 && wis <= 23) {
            return 7;
        }
        if (wis >= 24 && wis <= 24) {
            return 9;
        }
        if (wis >= 25 && wis <= 26) {
            return 7;
        }
        if (wis >= 27 && wis <= 29) {
            return 9;
        }
        if (wis >= 30 && wis <= 32) {
            return 9;
        }
        if (wis >= 33 && wis <= 34) {
            return 11;
        }
        if (wis >= 35 && wis <= 35) {
            return 9;
        }
        if (wis >= 36 && wis <= 38) {
            return 11;
        }
        if (wis >= 39 && wis <= 39) {
            return 13;
        }
        if (wis >= 40 && wis <= 41) {
            return 11;
        }
        if (wis >= 42 && wis <= 44) {
            return 13;
        }
        if (wis >= 45) {
            return 13;
        }
        return 0;
    }

    @Override
    public String h() {
        return "W";
    }
}

