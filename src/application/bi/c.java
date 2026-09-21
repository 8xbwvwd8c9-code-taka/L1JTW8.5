/*
 * Decompiled with CFR 0.152.
 */
package bi;

import ap.u;

public class c {
    private c() {
    }

    public static int a(u pc) {
        int hp = 1;
        if (pc.x()) {
            hp = 14;
        } else if (pc.z()) {
            hp = 16;
        } else if (pc.A()) {
            hp = 15;
        } else if (pc.B()) {
            hp = 12;
        } else if (pc.C()) {
            hp = 12;
        } else if (pc.D()) {
            hp = 16;
        } else if (pc.E()) {
            hp = 14;
        } else if (pc.F()) {
            hp = 16;
        }
        return hp;
    }

    public static int b(u pc) {
        int mp = 1;
        if (pc.x()) {
            mp = 2;
        } else if (pc.z()) {
            mp = 1;
        } else if (pc.A()) {
            mp = 4;
        } else if (pc.B()) {
            mp = 6;
        } else if (pc.C()) {
            mp = 3;
        } else if (pc.D()) {
            mp = 2;
        } else if (pc.E()) {
            mp = 5;
        } else if (pc.F()) {
            mp = 1;
        }
        return mp;
    }

    public static int c(u pc) {
        int mp = 1;
        if (pc.x()) {
            switch (pc.eE()) {
                case 11: {
                    mp = 2;
                    break;
                }
                case 12: 
                case 13: 
                case 14: 
                case 15: {
                    mp = 3;
                    break;
                }
                case 16: 
                case 17: 
                case 18: {
                    mp = 4;
                    break;
                }
                default: {
                    mp = 2;
                    break;
                }
            }
        } else if (pc.z() || pc.F()) {
            switch (pc.eE()) {
                case 9: 
                case 10: 
                case 11: {
                    mp = 1;
                    break;
                }
                case 12: 
                case 13: {
                    mp = 2;
                    break;
                }
                default: {
                    mp = 1;
                    break;
                }
            }
        } else if (pc.A()) {
            switch (pc.eE()) {
                case 12: 
                case 13: 
                case 14: 
                case 15: {
                    mp = 4;
                    break;
                }
                case 16: 
                case 17: 
                case 18: {
                    mp = 6;
                    break;
                }
                default: {
                    mp = 4;
                    break;
                }
            }
        } else if (pc.B()) {
            switch (pc.eE()) {
                case 12: 
                case 13: 
                case 14: 
                case 15: {
                    mp = 6;
                    break;
                }
                case 16: 
                case 17: 
                case 18: {
                    mp = 8;
                    break;
                }
                default: {
                    mp = 6;
                    break;
                }
            }
        } else if (pc.C()) {
            switch (pc.eE()) {
                case 10: 
                case 11: {
                    mp = 3;
                    break;
                }
                case 12: 
                case 13: 
                case 14: 
                case 15: {
                    mp = 4;
                    break;
                }
                case 16: 
                case 17: 
                case 18: {
                    mp = 6;
                    break;
                }
                default: {
                    mp = 3;
                    break;
                }
            }
        } else if (pc.D()) {
            mp = 2;
        } else if (pc.E()) {
            switch (pc.eE()) {
                case 12: 
                case 13: 
                case 14: 
                case 15: {
                    mp = 5;
                    break;
                }
                case 16: 
                case 17: 
                case 18: {
                    mp = 6;
                    break;
                }
                default: {
                    mp = 5;
                }
            }
        }
        return mp;
    }
}

