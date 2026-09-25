/*
 * Decompiled with CFR 0.152.
 */
package ai;

import aj.aa;
import aj.ab;
import aj.ac;
import aj.ad;
import aj.ae;
import aj.af;
import aj.ag;
import aj.ah;
import aj.ai;
import aj.aj;
import aj.ak;
import aj.al;
import aj.am;
import aj.an;
import aj.ao;
import aj.ap;
import aj.aq;
import aj.ar;
import aj.as;
import aj.at;
import aj.au;
import aj.av;
import aj.aw;
import aj.ax;
import aj.ay;
import aj.az;
import aj.b;
import aj.ba;
import aj.bb;
import aj.bc;
import aj.bd;
import aj.be;
import aj.bf;
import aj.bg;
import aj.bh;
import aj.bi;
import aj.bj;
import aj.bk;
import aj.bl;
import aj.bm;
import aj.bn;
import aj.bo;
import aj.bp;
import aj.bq;
import aj.br;
import aj.bs;
import aj.bt;
import aj.bu;
import aj.bv;
import aj.bw;
import aj.bx;
import aj.by;
import aj.bz;
import aj.c;
import aj.ca;
import aj.cb;
import aj.cc;
import aj.cd;
import aj.ce;
import aj.cf;
import aj.cg;
import aj.ch;
import aj.ci;
import aj.cj;
import aj.ck;
import aj.cl;
import aj.cm;
import aj.cn;
import aj.co;
import aj.cp;
import aj.cq;
import aj.cr;
import aj.cs;
import aj.ct;
import aj.cu;
import aj.cv;
import aj.f;
import aj.g;
import aj.h;
import aj.i;
import aj.j;
import aj.k;
import aj.l;
import aj.m;
import aj.n;
import aj.o;
import aj.p;
import aj.q;
import aj.r;
import aj.s;
import aj.t;
import aj.u;
import aj.v;
import aj.w;
import aj.x;
import aj.y;
import aj.z;
import bj.d;
import l1j.server.a;

public class e {
    private final d a;

    public e(d clientthread) {
        this.a = clientthread;
    }

    public void a(byte[] abyte0) throws Exception {
        int i2 = abyte0[0] & 0xFF;
        cv cpacket = null;
        switch (i2) {
            case 100: {
                cpacket = new ay(abyte0, this.a);
                break;
            }
            case 153: {
                cpacket = new m(abyte0, this.a);
                break;
            }
            case 169: {
                cpacket = new q(abyte0, this.a);
                break;
            }
            case 221: {
                cpacket = new be(abyte0, this.a);
                break;
            }
            case 238: {
                cpacket = new bh(abyte0, this.a);
                break;
            }
            case 115: {
                cpacket = new bi(abyte0, this.a);
                break;
            }
            case 245: {
                cpacket = new cg(abyte0, this.a);
                break;
            }
            case 126: {
                cpacket = new cf(abyte0, this.a);
                break;
            }
            case 19: {
                cpacket = new bu(abyte0, this.a);
                break;
            }
            case 254: {
                cpacket = new j(abyte0, this.a);
                break;
            }
            case 129: {
                cpacket = new v(abyte0, this.a);
                break;
            }
            case 186: {
                cpacket = new cd(abyte0, this.a);
                break;
            }
            case 188: {
                cpacket = new av(abyte0, this.a);
                break;
            }
            case 178: {
                cpacket = new ao(abyte0, this.a);
                break;
            }
            case 249: {
                cpacket = new t(abyte0, this.a);
                break;
            }
            case 84: {
                cpacket = new ai(abyte0, this.a);
                break;
            }
            case 177: {
                cpacket = new cl(abyte0, this.a);
                break;
            }
            case 15: {
                cpacket = new l(abyte0, this.a);
                break;
            }
            case 43: {
                cpacket = new bp(abyte0, this.a);
                break;
            }
            case 39: {
                cpacket = new r(abyte0, this.a);
                break;
            }
            case 44: {
                cpacket = new bk(abyte0, this.a);
                break;
            }
            case 128: {
                cpacket = new cr(abyte0, this.a);
                break;
            }
            case 151: {
                cpacket = new am(abyte0, this.a);
                break;
            }
            case 132: {
                cpacket = new ci(abyte0, this.a);
                break;
            }
            case 135: {
                cpacket = new al(abyte0, this.a);
                break;
            }
            case 220: {
                cpacket = new co(abyte0, this.a);
                break;
            }
            case 77: {
                cpacket = new aj.a(abyte0, this.a);
                break;
            }
            case 141: {
                cpacket = new aa(abyte0, this.a);
                break;
            }
            case 72: {
                cpacket = new ca(abyte0, this.a);
                break;
            }
            case 233: {
                cpacket = new aj(abyte0, this.a);
                break;
            }
            case 104: {
                cpacket = new ag(abyte0, this.a);
                break;
            }
            case 58: {
                cpacket = new br(abyte0, this.a);
                break;
            }
            case 223: {
                cpacket = new ce(abyte0, this.a);
                break;
            }
            case 121: {
                cpacket = new cb(abyte0, this.a);
                break;
            }
            case 7: {
                cpacket = new n(abyte0, this.a);
                break;
            }
            case 2: {
                cpacket = new cm(abyte0, this.a);
                break;
            }
            case 189: {
                cpacket = new ae(abyte0, this.a);
                break;
            }
            case 171: {
                cpacket = new bb(abyte0, this.a);
                break;
            }
            case 118: {
                cpacket = new f(abyte0, this.a);
                break;
            }
            case 69: {
                cpacket = new g(abyte0, this.a);
                break;
            }
            case 5: {
                cpacket = new bx(abyte0, this.a);
                break;
            }
            case 25: {
                cpacket = new bs(abyte0, this.a);
                break;
            }
            case 82: {
                cpacket = new ch(abyte0, this.a);
                break;
            }
            case 209: {
                cpacket = new cn(abyte0, this.a);
                break;
            }
            case 31: {
                cpacket = new b(abyte0, this.a);
                break;
            }
            case 117: {
                cpacket = new u(abyte0, this.a);
                break;
            }
            case 73: {
                cpacket = new cp(abyte0, this.a);
                break;
            }
            case 173: {
                cpacket = new y(abyte0, this.a);
                break;
            }
            case 215: {
                cpacket = new bv(abyte0, this.a);
                break;
            }
            case 35: {
                cpacket = new p(abyte0, this.a);
                break;
            }
            case 163: {
                cpacket = new ak(abyte0, this.a);
                break;
            }
            case 143: {
                cpacket = new bd(abyte0, this.a);
                break;
            }
            case 101: {
                cpacket = new bq(abyte0, this.a);
                break;
            }
            case 106: 
            case 111: {
                cpacket = new aj.d(abyte0, this.a);
                break;
            }
            case 83: {
                cpacket = new aj.e(abyte0, this.a);
                break;
            }
            case 23: {
                cpacket = new ah(abyte0, this.a);
                break;
            }
            case 88: {
                cpacket = new h(abyte0, this.a);
                break;
            }
            case 52: {
                cpacket = new k(abyte0, this.a);
                break;
            }
            case 145: {
                cpacket = new af(abyte0, this.a);
                break;
            }
            case 49: {
                cpacket = new x(abyte0, this.a);
                break;
            }
            case 14: {
                cpacket = new bm(abyte0, this.a);
                break;
            }
            case 9: {
                cpacket = new bo(abyte0, this.a);
                break;
            }
            case 95: {
                cpacket = new cu(abyte0, this.a);
                break;
            }
            case 41: {
                cpacket = new aw(abyte0, this.a);
                break;
            }
            case 45: {
                cpacket = new bj(abyte0, this.a);
                break;
            }
            case 38: {
                cpacket = new ad(abyte0, this.a);
                break;
            }
            case 59: {
                cpacket = new bw(abyte0, this.a);
                break;
            }
            case 26: {
                cpacket = new bc(abyte0, this.a);
                break;
            }
            case 11: {
                cpacket = new bl(abyte0, this.a);
                break;
            }
            case 191: {
                cpacket = new i(abyte0, this.a);
                break;
            }
            case 252: {
                cpacket = new ac(abyte0, this.a);
                break;
            }
            case 202: {
                cpacket = new cs(abyte0, this.a);
                break;
            }
            case 158: {
                cpacket = new bf(abyte0, this.a);
                break;
            }
            case 152: {
                cpacket = new cc(abyte0, this.a);
                break;
            }
            case 60: {
                cpacket = new u(abyte0, this.a);
                break;
            }
            case 217: {
                cpacket = new ba(abyte0, this.a);
                break;
            }
            case 90: {
                cpacket = new z(abyte0, this.a);
                break;
            }
            case 4: {
                cpacket = new aq(abyte0, this.a);
                break;
            }
            case 32: {
                cpacket = new o(abyte0, this.a);
                break;
            }
            case 94: {
                cpacket = new az(abyte0, this.a);
                break;
            }
            case 127: {
                cpacket = new ab(abyte0, this.a);
                break;
            }
            case 56: {
                cpacket = new an(abyte0, this.a);
                break;
            }
            case 167: {
                cpacket = new c(abyte0, this.a);
                break;
            }
            case 244: {
                cpacket = new au(abyte0, this.a);
                break;
            }
            case 20: {
                cpacket = new at(abyte0, this.a);
                break;
            }
            case 148: {
                cpacket = new ap(abyte0, this.a);
                break;
            }
            case 89: {
                cpacket = new ck(abyte0, this.a);
                break;
            }
            case 54: {
                cpacket = new as(abyte0, this.a);
                break;
            }
            case 28: {
                cpacket = new by(abyte0, this.a);
                break;
            }
            case 33: {
                cpacket = new bn(abyte0, this.a);
                break;
            }
            case 61: {
                cpacket = new cq(abyte0, this.a);
                break;
            }
            case 208: {
                cpacket = new cj(abyte0, this.a);
                break;
            }
            case 74: {
                cpacket = new w(abyte0, this.a);
                break;
            }
            case 241: {
                cpacket = new ar(abyte0, this.a);
                break;
            }
            case 201: {
                cpacket = new ax(abyte0, this.a);
                break;
            }
            case 247: {
                cpacket = new bg(abyte0, this.a);
                break;
            }
            case 251: {
                cpacket = new s(abyte0, this.a);
                break;
            }
            case 113: {
                cpacket = new bz(abyte0, this.a);
                break;
            }
            case 222: {
                cpacket = new ct(abyte0, this.a);
                break;
            }
            case 22: {
                cpacket = new bt(abyte0, this.a);
            }
        }
        if (l1j.server.a.d && i2 != 45) {
            String name = cpacket == null ? "" : cpacket.getClass().getSimpleName();
            System.out.println("opcode: " + i2 + " [" + name + "]");
            System.out.println(bi.g.a(abyte0));
        }
        if (cpacket != null) {
            cpacket.i();
        }
    }
}

