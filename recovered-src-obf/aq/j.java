/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.q;
import ap.u;
import be.ba;
import be.ck;
import be.cm;
import be.do;
import be.ds;
import be.ee;

public class j {
    public static void a(u pc, q item) {
        int itemId = item.N();
        if ((itemId == 41284 || itemId == 41292 || itemId == 49056 || itemId == 49064 || itemId == 49251 || itemId == 49259) && pc.fj() != 225) {
            pc.a(new ds(74, item.c()));
            return;
        }
        int skillid = item.a().V();
        if (itemId == 640712 || itemId == 640713 || itemId == 640714) {
            pc.a(new ee(pc.fr(), skillid + 11782));
            pc.b(new ee(pc.fr(), skillid + 11782));
        } else if (itemId == 640715) {
            pc.a(new ee(pc.fr(), 14847));
            pc.b(new ee(pc.fr(), 14847));
        }
        j.a(pc, skillid, 900);
        pc.a(new ds(76, item.c()));
        pc.j().b(item, 1);
    }

    public static void a(u pc, int skillid, int time) {
        if (!pc.bB(skillid)) {
            if (skillid == 3000 || skillid == 3008) {
                pc.bY(10);
                pc.bZ(10);
                pc.ca(10);
                pc.cb(10);
            } else if (skillid == 3001 || skillid == 3009) {
                pc.bH(30);
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            } else if (skillid == 3002 || skillid == 3010) {
                pc.d(3);
            } else if (skillid == 3003 || skillid == 3011) {
                pc.bL(-1);
            } else if (skillid == 3004 || skillid == 3012) {
                pc.bJ(20);
            } else if (skillid == 3005 || skillid == 3013) {
                pc.c(3);
            } else if (skillid == 3006 || skillid == 3014) {
                pc.co(5);
            } else if (skillid != 3007 && skillid != 3015) {
                if (skillid == 3016 || skillid == 3024) {
                    pc.ck(1);
                    pc.cm(1);
                } else if (skillid == 3017 || skillid == 3025) {
                    pc.bH(30);
                    if (pc.q()) {
                        pc.aL().f(pc);
                    }
                    pc.bJ(30);
                } else if (skillid == 3018 || skillid == 3026) {
                    pc.bL(-2);
                } else if (skillid == 3019 || skillid == 3027) {
                    pc.cl(1);
                    pc.cn(1);
                } else if (skillid == 3020 || skillid == 3028) {
                    pc.c(2);
                    pc.d(2);
                } else if (skillid == 3021 || skillid == 3029) {
                    pc.co(10);
                } else if (skillid == 3022 || skillid == 3030) {
                    pc.cp(1);
                } else if (skillid != 3023 && skillid != 3031) {
                    if (skillid == 3032 || skillid == 3040) {
                        pc.cl(1);
                        pc.cn(2);
                    } else if (skillid == 3033 || skillid == 3041) {
                        pc.bH(50);
                        if (pc.q()) {
                            pc.aL().f(pc);
                        }
                        pc.bJ(50);
                    } else if (skillid == 3034 || skillid == 3042) {
                        pc.ck(1);
                        pc.cm(2);
                    } else if (skillid == 3035 || skillid == 3043) {
                        pc.bL(-3);
                    } else if (skillid == 3036 || skillid == 3044) {
                        pc.co(15);
                        pc.bY(10);
                        pc.bZ(10);
                        pc.ca(10);
                        pc.cb(10);
                    } else if (skillid == 3037 || skillid == 3045) {
                        pc.cp(2);
                        pc.d(2);
                    } else if (skillid == 3038 || skillid == 3046) {
                        pc.bH(30);
                        pc.c(2);
                        pc.a(new ba(pc.ea(), pc.ew()));
                        if (pc.q()) {
                            pc.aL().f(pc);
                        }
                    } else if (skillid != 3039 && skillid != 3047) {
                        if (skillid == 3048) {
                            pc.c(10);
                            pc.d(2);
                        } else if (skillid == 3049) {
                            time = 1800;
                            pc.ck(2);
                            pc.cm(1);
                            pc.co(10);
                            pc.bY(10);
                            pc.bZ(10);
                            pc.ca(10);
                            pc.cb(10);
                            pc.c(2);
                            pc.d(2);
                            pc.F(2);
                        } else if (skillid == 3050) {
                            time = 1800;
                            pc.cl(2);
                            pc.cn(1);
                            pc.co(10);
                            pc.bY(10);
                            pc.bZ(10);
                            pc.ca(10);
                            pc.cb(10);
                            pc.c(2);
                            pc.d(2);
                            pc.F(2);
                        } else if (skillid == 3051) {
                            time = 1800;
                            pc.cp(2);
                            pc.co(10);
                            pc.bY(10);
                            pc.bZ(10);
                            pc.ca(10);
                            pc.cb(10);
                            pc.c(2);
                            pc.d(3);
                            pc.F(2);
                        } else if (skillid == 3052) {
                            time = 1800;
                            pc.F(2);
                        } else if (skillid == 3053) {
                            time = 1200;
                            pc.ck(3);
                            pc.F(3);
                            pc.co(10);
                        } else if (skillid == 3054) {
                            time = 1200;
                            pc.cl(3);
                            pc.F(3);
                            pc.co(10);
                        } else if (skillid == 3055) {
                            time = 1200;
                            pc.cp(3);
                            pc.F(3);
                            pc.co(10);
                        } else if (skillid == 3056) {
                            time = 1200;
                            pc.F(3);
                        }
                    }
                }
            }
            pc.a(new do(pc));
            pc.a(new ck(pc));
        }
        pc.a(new cm(53, pc, j.a(skillid), time));
        pc.j(skillid, time * 1000);
    }

    public static void a(u pc, int skillid) {
        if (skillid == 3000 || skillid == 3008) {
            pc.bY(-10);
            pc.bZ(-10);
            pc.ca(-10);
            pc.cb(-10);
        } else if (skillid == 3001 || skillid == 3009) {
            pc.bH(-30);
            if (pc.q()) {
                pc.aL().f(pc);
            }
        } else if (skillid == 3002 || skillid == 3010) {
            pc.d(-3);
        } else if (skillid == 3003 || skillid == 3011) {
            pc.bL(1);
        } else if (skillid == 3004 || skillid == 3012) {
            pc.bJ(-20);
        } else if (skillid == 3005 || skillid == 3013) {
            pc.c(-3);
        } else if (skillid == 3006 || skillid == 3014) {
            pc.co(-5);
        } else if (skillid != 3007 && skillid != 3015) {
            if (skillid == 3016 || skillid == 3024) {
                pc.ck(-1);
                pc.cm(-1);
            } else if (skillid == 3017 || skillid == 3025) {
                pc.bH(-30);
                pc.bJ(-30);
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            } else if (skillid == 3018 || skillid == 3026) {
                pc.bL(2);
            } else if (skillid == 3019 || skillid == 3027) {
                pc.cl(-1);
                pc.cn(-1);
            } else if (skillid == 3020 || skillid == 3028) {
                pc.c(-2);
                pc.d(-2);
            } else if (skillid == 3021 || skillid == 3029) {
                pc.co(-10);
            } else if (skillid == 3022 || skillid == 3030) {
                pc.cp(-1);
            } else if (skillid != 3023 && skillid != 3031) {
                if (skillid == 3032 || skillid == 3040) {
                    pc.cl(-1);
                    pc.cn(-2);
                } else if (skillid == 3033 || skillid == 3041) {
                    pc.bH(-50);
                    pc.bJ(-50);
                    if (pc.q()) {
                        pc.aL().f(pc);
                    }
                } else if (skillid == 3034 || skillid == 3042) {
                    pc.ck(-1);
                    pc.cm(-2);
                } else if (skillid == 3035 || skillid == 3043) {
                    pc.bL(3);
                } else if (skillid == 3036 || skillid == 3044) {
                    pc.co(-15);
                    pc.bY(-10);
                    pc.bZ(-10);
                    pc.ca(-10);
                    pc.cb(-10);
                } else if (skillid == 3037 || skillid == 3045) {
                    pc.cp(-2);
                    pc.d(-2);
                } else if (skillid == 3038 || skillid == 3046) {
                    pc.bH(-30);
                    pc.c(-2);
                    if (pc.q()) {
                        pc.aL().f(pc);
                    }
                } else if (skillid != 3039 && skillid != 3047) {
                    if (skillid == 3048) {
                        pc.c(-10);
                        pc.d(-2);
                    } else if (skillid == 3049) {
                        pc.ck(-2);
                        pc.cm(-1);
                        pc.co(-10);
                        pc.bY(-10);
                        pc.bZ(-10);
                        pc.ca(-10);
                        pc.cb(-10);
                        pc.c(-2);
                        pc.d(-2);
                        pc.F(-2);
                    } else if (skillid == 3050) {
                        pc.cl(-2);
                        pc.cn(-1);
                        pc.co(-10);
                        pc.bY(-10);
                        pc.bZ(-10);
                        pc.ca(-10);
                        pc.cb(-10);
                        pc.c(-2);
                        pc.d(-2);
                        pc.F(-2);
                    } else if (skillid == 3051) {
                        pc.cp(-2);
                        pc.co(-10);
                        pc.bY(-10);
                        pc.bZ(-10);
                        pc.ca(-10);
                        pc.cb(-10);
                        pc.c(-2);
                        pc.d(-3);
                        pc.F(-2);
                    } else if (skillid == 3052) {
                        pc.F(-2);
                    } else if (skillid == 3053) {
                        pc.ck(-3);
                        pc.F(-3);
                        pc.co(-10);
                    } else if (skillid == 3054) {
                        pc.cl(-3);
                        pc.F(-3);
                        pc.co(-10);
                    } else if (skillid == 3055) {
                        pc.cp(-3);
                        pc.F(-3);
                        pc.co(10);
                    } else if (skillid == 3056) {
                        pc.F(-3);
                    }
                }
            }
        }
        pc.c_(Math.min(pc.fj() + 20, 225));
        pc.a(new cm(11, pc.fj()));
        pc.a(new cm(53, pc, j.a(skillid), 0));
        pc.a(new do(pc));
    }

    private static int a(int skillid) {
        int type = 0;
        if (skillid >= 3000 && skillid <= 3031) {
            type = skillid - 3000;
        } else if (skillid >= 3032 && skillid <= 3047) {
            type = skillid - 2995;
        } else if (skillid == 3048) {
            type = 54;
        } else if (skillid >= 3049 && skillid <= 3052) {
            type = skillid - 2892;
        } else if (skillid >= 3053 && skillid <= 3056) {
            type = skillid - 2849;
        }
        return type;
    }
}

