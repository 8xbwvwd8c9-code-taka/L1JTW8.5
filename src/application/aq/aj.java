/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.bg;
import aq.e;
import bi.i;

public class aj {
    private static aj a;

    private aj() {
    }

    public static aj a() {
        if (a == null) {
            a = new aj();
        }
        return a;
    }

    public void a(int castleId) {
        int npcId = 81111;
        if (castleId == 7) {
            npcId = 81189;
        }
        int[] loc = e.a(castleId);
        bg.a(npcId, loc[0], loc[1], loc[2], 0, "");
        if (castleId == 7) {
            this.b();
        }
    }

    private void b() {
        int i2 = 1;
        while (i2 <= 4) {
            int[] loc = e.f(i2);
            bg.a(81189 + i2, loc[0], loc[1], loc[2], 0, "");
            ++i2;
        }
    }

    public void b(int castleId) {
        int[] loc = e.a(castleId);
        bg.a(81125, loc[0], loc[1], loc[2], 0, "");
    }

    public void c(int castleId) {
        int[] loc = new int[5];
        loc = e.c(castleId);
        int x2 = 0;
        int y2 = 0;
        int locx1 = loc[0];
        int locx2 = loc[1];
        int locy1 = loc[2];
        int locy2 = loc[3];
        int mapid = loc[4];
        int[] flagGfx = e.b(castleId);
        x2 = locx1;
        y2 = locy1;
        while (x2 <= locx2) {
            bg.a(81122, x2, y2, mapid, 0, null, flagGfx[0]);
            x2 += 8;
        }
        x2 = locx2;
        y2 = locy1;
        while (y2 <= locy2) {
            bg.a(81122, x2, y2, mapid, 0, null, flagGfx[1]);
            y2 += 8;
        }
        x2 = locx2;
        y2 = locy2;
        while (x2 >= locx1) {
            bg.a(81122, x2, y2, mapid, 0, null, flagGfx[0]);
            x2 -= 8;
        }
        x2 = locx1;
        y2 = locy2;
        while (y2 >= locy1) {
            bg.a(81122, x2, y2, mapid, 0, null, flagGfx[1]);
            y2 -= 8;
        }
    }

    public void d(int castleId) {
        int y2;
        int x2;
        String clanName = "\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castleId;
        if (castleId == 1) {
            x2 = 33112;
            while (x2 <= 33115) {
                y2 = 32768;
                while (y2 <= 32772) {
                    bg.a(190047, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 33116;
            while (x2 <= 33118) {
                y2 = 32763;
                while (y2 <= 32766) {
                    bg.a(190048, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 33116;
            while (x2 <= 33118) {
                y2 = 32774;
                while (y2 <= 32777) {
                    bg.a(190048, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
        } else if (castleId == 2) {
            x2 = 32792;
            while (x2 <= 32797) {
                y2 = 32317;
                while (y2 <= 32318) {
                    bg.a(190050, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 32792;
            while (x2 <= 32797) {
                y2 = 32308;
                while (y2 <= 32309) {
                    bg.a(190051, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
        } else if (castleId == 3) {
            x2 = 32591;
            while (x2 <= 32593) {
                y2 = 33407;
                while (y2 <= 33410) {
                    bg.a(190053, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 32596;
            while (x2 <= 32598) {
                y2 = 33407;
                while (y2 <= 33410) {
                    bg.a(190053, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 32596;
            while (x2 <= 32598) {
                y2 = 33402;
                while (y2 <= 33405) {
                    bg.a(190054, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 32596;
            while (x2 <= 32598) {
                y2 = 33412;
                while (y2 <= 33415) {
                    bg.a(190054, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
        } else if (castleId == 4) {
            x2 = 33629;
            while (x2 <= 33634) {
                y2 = 32732;
                while (y2 <= 32733) {
                    bg.a(190056, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 33629;
            while (x2 <= 33636) {
                y2 = 32700;
                while (y2 <= 32701) {
                    bg.a(190056, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 33623;
            while (x2 <= 33626) {
                y2 = 32727;
                while (y2 <= 32729) {
                    bg.a(190057, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
            x2 = 33637;
            while (x2 <= 33640) {
                y2 = 32727;
                while (y2 <= 32729) {
                    bg.a(190057, x2, y2, 4, 1, clanName);
                    ++y2;
                }
                ++x2;
            }
        }
        int[] loc = e.a(castleId);
        int i2 = -5;
        while (i2 <= 6) {
            bg.a(190046 + castleId * 3, loc[0] + i2, loc[1] + i2, loc[2], 1, clanName);
            ++i2;
        }
    }

    public void e(int castleId) {
        block13: {
            String clanName;
            block14: {
                block12: {
                    clanName = "\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castleId;
                    if (castleId != 1) break block12;
                    bg.a(190355 + i.a(6), 33099, 32770, 4, 2, 60000L, clanName);
                    int x2 = 33092;
                    while (x2 <= 33096) {
                        bg.a(190361, x2, 32768, 4, 2, 60500L, clanName);
                        ++x2;
                    }
                    x2 = 33092;
                    while (x2 <= 33096) {
                        bg.a(190362, x2, 32769, 4, 2, 60500L, clanName);
                        ++x2;
                    }
                    x2 = 33092;
                    while (x2 <= 33096) {
                        bg.a(190363, x2, 32770, 4, 2, 60500L, clanName);
                        ++x2;
                    }
                    x2 = 33092;
                    while (x2 <= 33096) {
                        bg.a(190364, x2, 32771, 4, 2, 60500L, clanName);
                        ++x2;
                    }
                    break block13;
                }
                if (castleId != 2) break block14;
                bg.a(190355 + i.a(6), 32785, 32331, 4, 0, 60000L, clanName);
                int y2 = 32334;
                while (y2 <= 32338) {
                    bg.a(190361, 32782, y2, 4, 0, 60500L, clanName);
                    ++y2;
                }
                y2 = 32334;
                while (y2 <= 32338) {
                    bg.a(190362, 32783, y2, 4, 0, 60500L, clanName);
                    ++y2;
                }
                y2 = 32334;
                while (y2 <= 32338) {
                    bg.a(190363, 32784, y2, 4, 0, 60500L, clanName);
                    ++y2;
                }
                y2 = 32334;
                while (y2 <= 32338) {
                    bg.a(190364, 32785, y2, 4, 0, 60500L, clanName);
                    ++y2;
                }
                break block13;
            }
            if (castleId != 4) break block13;
            bg.a(190355 + i.a(6), 33630, 32748, 4, 0, 60000L, clanName);
            int y3 = 32748;
            while (y3 <= 32752) {
                bg.a(190361, 33628, y3, 4, 0, 60500L, clanName);
                ++y3;
            }
            y3 = 32748;
            while (y3 <= 32752) {
                bg.a(190362, 33629, y3, 4, 0, 60500L, clanName);
                ++y3;
            }
            y3 = 32748;
            while (y3 <= 32752) {
                bg.a(190363, 33630, y3, 4, 0, 60500L, clanName);
                ++y3;
            }
            y3 = 32748;
            while (y3 <= 32752) {
                bg.a(190364, 33631, y3, 4, 0, 60500L, clanName);
                ++y3;
            }
        }
    }
}

