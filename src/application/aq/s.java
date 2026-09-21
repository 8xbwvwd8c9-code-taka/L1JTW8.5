/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import be.ba;
import be.bv;
import be.ci;
import be.ck;
import be.cl;
import be.cm;
import be.dc;
import be.do;
import java.sql.Timestamp;

public class s {
    public static void a(u pc, int skillId) {
        if (!pc.bB(skillId)) {
            switch (skillId) {
                case 4072: {
                    pc.bH(50);
                    pc.ck(2);
                    pc.c(3);
                    pc.bN(1);
                    break;
                }
                case 4073: {
                    pc.bH(25);
                    pc.cl(2);
                    pc.bJ(25);
                    pc.c(1);
                    pc.d(1);
                    pc.bR(1);
                    break;
                }
                case 4074: {
                    pc.bJ(50);
                    pc.d(3);
                    pc.bV(1);
                    pc.cp(2);
                    pc.a(new do(pc));
                    break;
                }
                case 4075: {
                    pc.bH(30);
                    pc.bJ(30);
                    pc.bL(-5);
                    pc.co(10);
                    pc.F(1);
                    pc.a(new do(pc));
                    pc.a(new ci(pc));
                }
            }
            if (pc.q()) {
                pc.aL().f(pc);
            }
            pc.a(new ck(pc));
        }
        pc.j(skillId, 600000);
    }

    public static void a(u pc, int skillId, int time) {
        int type = skillId - 3929;
        if (!pc.bB(skillId)) {
            switch (skillId) {
                case 4013: {
                    pc.bH(10);
                    break;
                }
                case 4014: {
                    pc.bH(20);
                    break;
                }
                case 4015: {
                    pc.bH(30);
                    break;
                }
                case 4016: {
                    pc.bH(40);
                    break;
                }
                case 4017: {
                    pc.bH(50);
                    pc.c(1);
                    break;
                }
                case 4018: {
                    pc.bH(60);
                    pc.c(2);
                    break;
                }
                case 4019: {
                    pc.bH(70);
                    pc.c(3);
                    break;
                }
                case 4020: {
                    pc.bH(80);
                    pc.c(4);
                    pc.cm(1);
                    break;
                }
                case 4021: {
                    pc.bH(100);
                    pc.c(5);
                    pc.cm(2);
                    pc.ck(2);
                    pc.bN(1);
                    pc.a(new cl(pc));
                    break;
                }
                case 4022: {
                    pc.bH(5);
                    pc.bJ(3);
                    break;
                }
                case 4023: {
                    pc.bH(10);
                    pc.bJ(6);
                    break;
                }
                case 4024: {
                    pc.bH(15);
                    pc.bJ(10);
                    break;
                }
                case 4025: {
                    pc.bH(20);
                    pc.bJ(15);
                    break;
                }
                case 4026: {
                    pc.bH(25);
                    pc.bJ(20);
                    break;
                }
                case 4027: {
                    pc.bH(30);
                    pc.bJ(20);
                    pc.c(1);
                    break;
                }
                case 4028: {
                    pc.bH(35);
                    pc.bJ(20);
                    pc.c(1);
                    pc.d(1);
                    break;
                }
                case 4029: {
                    pc.bH(40);
                    pc.bJ(25);
                    pc.c(2);
                    pc.d(1);
                    break;
                }
                case 4030: {
                    pc.bH(50);
                    pc.bJ(30);
                    pc.c(2);
                    pc.d(2);
                    pc.cl(2);
                    pc.cn(2);
                    pc.bR(1);
                    pc.a(new cl(pc));
                    break;
                }
                case 4031: {
                    pc.bJ(5);
                    break;
                }
                case 4032: {
                    pc.bJ(10);
                    break;
                }
                case 4033: {
                    pc.bJ(15);
                    break;
                }
                case 4034: {
                    pc.bJ(20);
                    break;
                }
                case 4035: {
                    pc.bJ(25);
                    pc.d(1);
                    break;
                }
                case 4036: {
                    pc.bJ(30);
                    pc.d(2);
                    break;
                }
                case 4037: {
                    pc.bJ(35);
                    pc.d(3);
                    break;
                }
                case 4038: {
                    pc.bJ(40);
                    pc.d(4);
                    break;
                }
                case 4039: {
                    pc.bJ(50);
                    pc.d(5);
                    pc.bV(1);
                    pc.cp(1);
                    pc.a(new do(pc));
                    pc.a(new cl(pc));
                    break;
                }
                case 4040: {
                    pc.co(2);
                    break;
                }
                case 4041: {
                    pc.co(4);
                    break;
                }
                case 4042: {
                    pc.co(6);
                    break;
                }
                case 4043: {
                    pc.co(8);
                    break;
                }
                case 4044: {
                    pc.co(10);
                    pc.bL(-1);
                    break;
                }
                case 4045: {
                    pc.co(10);
                    pc.bL(-2);
                    break;
                }
                case 4046: {
                    pc.co(10);
                    pc.bL(-3);
                    break;
                }
                case 4047: {
                    pc.co(15);
                    pc.bL(-4);
                    pc.F(1);
                    break;
                }
                case 4048: {
                    pc.co(20);
                    pc.bL(-5);
                    pc.bP(1);
                    pc.F(3);
                    break;
                }
            }
            if (type >= 84 && type <= 92) {
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            } else if (type >= 93 && type <= 101) {
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            } else if (type >= 102 && type <= 110) {
                pc.a(new bv(pc.eb(), pc.ex()));
            } else if (type >= 111 && type <= 119) {
                pc.a(new do(pc));
                pc.a(new ci(pc));
                pc.a(new cl(pc));
            }
        }
        pc.j(skillId, time * 1000);
    }

    public static void b(u pc, int skillId, int time) {
        if (!pc.bB(skillId)) {
            switch (skillId) {
                case 4086: {
                    pc.bR(1);
                    pc.cn(5);
                    pc.cl(3);
                    break;
                }
                case 4087: {
                    pc.bR(1);
                    pc.cn(5);
                    pc.cl(3);
                    break;
                }
                case 4088: {
                    pc.bN(1);
                    pc.cm(5);
                    pc.ck(3);
                    break;
                }
                case 4089: {
                    pc.bN(1);
                    pc.cm(5);
                    pc.ck(3);
                    break;
                }
                case 4090: {
                    pc.bV(1);
                    pc.bJ(50);
                    pc.d(5);
                    break;
                }
                case 4091: {
                    pc.bV(1);
                    pc.bJ(50);
                    pc.d(5);
                    break;
                }
                case 4081: {
                    pc.bL(-2);
                    pc.ck(4);
                    pc.F(1);
                    break;
                }
                case 4082: {
                    pc.bL(-2);
                    pc.cl(4);
                    pc.F(1);
                    break;
                }
                case 4083: {
                    pc.bL(-2);
                    pc.cp(3);
                    pc.F(1);
                    break;
                }
                case 4067: {
                    pc.cm(30);
                    pc.ck(30);
                    pc.cn(30);
                    pc.cl(30);
                    pc.cp(30);
                    pc.a(new dc(skillId, time, 0, 5985, 0, 4067, 4067, 4068, 5));
                    break;
                }
                case 4068: {
                    pc.bL(-50);
                    pc.a(new dc(skillId, time, 0, 5984, 0, 4080, 4080, 4081, 5));
                    break;
                }
                case 4006: {
                    pc.cm(3);
                    pc.ck(3);
                    pc.d(2);
                    break;
                }
                case 4010: {
                    pc.cm(3);
                    pc.ck(3);
                    pc.cn(3);
                    pc.cl(3);
                    pc.cp(3);
                    break;
                }
                case 4008: {
                    pc.bH(50);
                    pc.c(4);
                    pc.a(new ba(pc.ea(), pc.ew()));
                    if (!pc.q()) break;
                    pc.aL().f(pc);
                    break;
                }
                case 4009: {
                    pc.bJ(40);
                    pc.d(4);
                    pc.a(new bv(pc.eb(), pc.ex()));
                    break;
                }
                case 4049: {
                    pc.ce(3);
                    pc.cA(1);
                    pc.a(new cm(88, pc.fk()));
                    break;
                }
                case 4050: {
                    pc.cg(3);
                    break;
                }
                case 4051: {
                    pc.cf(3);
                    break;
                }
                case 4052: {
                    pc.cd(3);
                    pc.ck(2);
                    break;
                }
                case 4053: {
                    pc.ci(3);
                    pc.cA(1);
                    pc.a(new cm(88, pc.fk()));
                    break;
                }
                case 4054: {
                    pc.ch(3);
                    pc.cA(1);
                    pc.a(new cm(88, pc.fk()));
                    break;
                }
                case 4055: {
                    pc.ck(2);
                    pc.cA(1);
                    pc.a(new cm(88, pc.fk()));
                    break;
                }
            }
            pc.a(new ck(pc));
            pc.a(new do(pc));
        }
        pc.j(skillId, time * 1000);
    }

    public static void a(u pc, int skillId, int time, Timestamp limitTime) {
        if (pc.bB(skillId)) {
            pc.bz(skillId);
        }
        switch (skillId) {
            case 4011: {
                pc.bL(-2);
                pc.bZ(50);
                pc.a(new cm(100, 82, time));
                break;
            }
            case 4012: {
                pc.c(3);
                pc.d(1);
                pc.bY(50);
                pc.a(new cm(100, 85, time));
                break;
            }
            case 4077: {
                pc.cp(1);
                pc.co(1);
                pc.ca(50);
                pc.a(new cm(100, 88, time));
                break;
            }
            case 4056: {
                pc.bH(100);
                pc.bJ(50);
                pc.c(3);
                pc.d(3);
                pc.cb(30);
                pc.ck(1);
                pc.cm(5);
                pc.C(40);
                break;
            }
            case 4057: {
                pc.bH(80);
                pc.bJ(10);
                pc.bZ(30);
                pc.bL(-8);
                break;
            }
            case 4079: {
                pc.bH(100);
                pc.bJ(50);
                pc.c(3);
                pc.d(3);
                pc.bY(30);
                pc.ck(1);
                pc.cm(5);
                pc.C(40);
            }
        }
        pc.a(new ba(pc.ea(), pc.ew()));
        if (pc.q()) {
            pc.aL().f(pc);
        }
        pc.a(new bv(pc.eb(), pc.ex()));
        pc.a(new cl(pc));
        pc.a(new ci(pc));
        if (limitTime != null) {
            pc.a(skillId, time * 1000, limitTime);
        } else {
            pc.j(skillId, time * 1000);
        }
    }
}

