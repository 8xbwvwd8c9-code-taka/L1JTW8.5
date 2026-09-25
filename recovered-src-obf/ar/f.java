/*
 * Decompiled with CFR 0.152.
 */
package ar;

import ar.a;

class f
extends a {
    f() {
    }

    @Override
    public int b(int ac2) {
        return ac2 / 2;
    }

    @Override
    public int c(int playerLevel) {
        return playerLevel / 50;
    }

    @Override
    public int[] a() {
        return new int[]{16, 12, 16, 8, 9, 10};
    }

    @Override
    public int b() {
        return 4;
    }

    @Override
    public int c() {
        return 0;
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
        return level / 4;
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
        return level / 3;
    }

    @Override
    public int i(int level) {
        return level / 3;
    }

    @Override
    public int e() {
        return 21;
    }

    @Override
    public int f() {
        return l1j.server.a.az;
    }

    @Override
    public int g() {
        return l1j.server.a.aA;
    }

    @Override
    public int j(int wis) {
        if (wis <= 9) {
            return 0;
        }
        if (wis >= 10 && wis <= 14) {
            return 1;
        }
        if (wis >= 15 && wis <= 17) {
            return 2;
        }
        if (wis >= 18 && wis <= 23) {
            return 2;
        }
        if (wis >= 24 && wis <= 24) {
            return 2;
        }
        if (wis >= 25 && wis <= 26) {
            return 3;
        }
        if (wis >= 27 && wis <= 29) {
            return 3;
        }
        if (wis >= 30 && wis <= 32) {
            return 4;
        }
        if (wis >= 33 && wis <= 35) {
            return 4;
        }
        if (wis >= 36 && wis <= 39) {
            return 4;
        }
        if (wis >= 40 && wis <= 41) {
            return 5;
        }
        if (wis >= 42 && wis <= 44) {
            return 5;
        }
        if (wis >= 45) {
            return 6;
        }
        return 0;
    }

    @Override
    public int k(int wis) {
        if (wis <= 9) {
            return 3;
        }
        if (wis >= 10 && wis <= 14) {
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
        if (wis >= 30 && wis <= 32) {
            return 3;
        }
        if (wis >= 33 && wis <= 35) {
            return 4;
        }
        if (wis >= 36 && wis <= 39) {
            return 5;
        }
        if (wis >= 40 && wis <= 41) {
            return 4;
        }
        if (wis >= 42 && wis <= 44) {
            return 5;
        }
        if (wis >= 45) {
            return 5;
        }
        return 0;
    }

    @Override
    public String h() {
        return "K";
    }
}

