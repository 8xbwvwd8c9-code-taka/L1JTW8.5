/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ArmorSetTable;
import l1r.ao.InnTable;
import l1r.ao.ItemTable;
import l1r.ao.MagicDollTable;
import l1r.ao.NpcTable;
import l1r.ao.PetItemTable;
import l1r.ao.PetTable;
import l1r.ao.WeaponSkillTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1PetItem;
import l1r.bi.BinaryOutputStream;
import l1r.bi.GeneralThreadPool;

public class L1ItemInstance
extends L1Object {
    private static final Logger O = Logger.getLogger(L1ItemInstance.class.getName());
    private boolean P = false;
    private boolean Q = false;
    private int R = 1;
    private int S = 1;
    private int T = 0;
    private int U;
    private int V;
    private Timestamp W = null;
    private int X;
    private int Y;
    private int Z;
    private L1Item aa;
    private int ab = 0;
    public int[] a = new int[2];
    public int[] b = new int[2];
    public boolean[] c = new boolean[2];
    public boolean[] d = new boolean[2];
    public int[] e = new int[2];
    public boolean[] f = new boolean[2];
    public int[] g = new int[2];
    public int[] h = new int[2];
    public int[] i = new int[2];
    public Timestamp[] j = new Timestamp[2];
    public Timestamp[] k = new Timestamp[2];
    public int[] l = new int[2];
    public int[] m = new int[2];
    public int[] n = new int[2];
    public int[] o = new int[2];
    public int[] p = new int[2];
    public int[] q = new int[2];
    public int[] r = new int[2];
    private int ac = 0;
    private int ad = 0;
    private int ae = 0;
    private int af = 0;
    private ScheduledFuture<?> ag;
    private L1PcInstance ah;
    private int ai = 0;
    private ScheduledFuture<?> aj;
    private boolean ak = false;
    private boolean al = false;
    private int am = 0;
    private boolean an = false;
    public static final int s = 0;
    public static final int t = 1;
    public static final int u = 2;
    public static final int v = 4;
    public static final int w = 8;
    public static final int x = 16;
    public static final int y = 32;
    public static final int z = 64;
    public static final int A = 128;
    public static final int B = 256;
    public static final int C = 512;
    public static final int D = 1024;
    public static final int E = 2048;
    public static final int F = 4096;
    public static final int G = 8192;
    public static final int H = 16384;
    public static final int I = 32768;
    public static final int J = 65536;
    public static final int K = 131072;
    public static final int L = 262144;
    public static final int M = 524288;
    public static final int N = 0x100000;
    private int ao = 0;
    private int ap = 0;
    private int aq = 0;
    private int ar = 0;
    private int as = 0;
    private int at = 0;
    private int au = 0;
    private int av = 0;
    private int aw = 0;
    private int ax = 0;
    private int ay = 0;
    private int az = 0;
    private int aA = 0;
    private int aB = 0;
    private int aC = 0;
    private int aD = 0;
    private int aE = 0;
    private int aF = 0;
    private int aG = 0;
    private int aH = 0;
    private int aI = 0;
    private int aJ = 0;
    private int aK = 0;
    private int aL = 0;
    private int aM = 0;
    private int aN = 0;
    private int aO = 0;
    private int aP = 0;
    private int aQ = 0;
    private int aR = 0;
    private int aS = 0;
    private int aT = 0;
    private int aU = 0;
    private int aV = 0;
    private int aW = 0;
    private int aX = 0;
    private int aY = 0;
    private int aZ = 0;
    private int ba = 0;
    private int bb = 0;
    private int bc = 0;
    private int bd = 0;
    private int be = 0;
    private int bf = 0;
    private int bg = 0;
    private int bh = 0;
    private int bi = 0;
    private int bj = 0;
    private int bk = 0;
    private boolean bl = false;
    private boolean bm = false;
    private boolean bn = false;
    private boolean bo = false;
    private int bp = 0;
    private int bq = 0;
    private int br = 0;
    private final boolean bs = false;
    private Timestamp bt = null;
    private int bu = 0;
    private int bv = 0;
    private int bw = 0;
    private int bx = 0;
    private int by = 0;
    private int bz = 0;
    private int bA = 0;
    private int bB = 0;
    private int bC = 0;
    private int bD = 0;
    private int bE = 0;
    private int bF = 0;
    private int bG = 0;
    private int bH = 0;
    private String bI;
    private int bJ = 0;
    private int bK = 0;
    private int bL = 0;
    private int bM = 0;
    private int bN = 0;
    private int bO = 0;
    private int bP = 0;
    private int bQ = 0;
    private int bR = 0;
    private boolean bS = false;
    private boolean bT = false;
    private int bU = 0;
    private int bV = 0;
    private int bW = 0;
    private String bX;

    public void a(int _enchantLevel) {
        this.T = _enchantLevel;
        this.bH();
    }

    public void b(int i) {
        this.U = Math.max(0, Math.min(i, 127));
    }

    public L1Item a() {
        return this.aa;
    }

    public String b() {
        return this.aa.h();
    }

    public String c() {
        return this.P ? this.aa.j() : this.aa.i();
    }

    public boolean d() {
        return this.aa.aF();
    }

    public int e() {
        return this.aa.m();
    }

    public boolean f() {
        return this.a().f() == 0;
    }

    public boolean g() {
        return this.a().f() == 1;
    }

    public boolean h() {
        return this.a().f() == 2;
    }

    public boolean i() {
        if (!this.h()) {
            return false;
        }
        return this.a().aP() == 9 || this.a().aP() == 11 || this.a().aP() == 12 || this.a().aP() == 13;
    }

    public boolean j() {
        if (!this.h()) {
            return false;
        }
        return this.a().aP() == 30;
    }

    public boolean k() {
        return this.a().a();
    }

    public boolean l() {
        return this.a().b();
    }

    public int m() {
        return this.a().W();
    }

    public L1ItemInstance(L1Item item, int _count) {
        this.aa = item;
        this.ab = item.g();
        this.S = item.r();
        this.R = _count;
    }

    public void a(L1Item item) {
        this.aa = item;
        this.ab = item.g();
        this.S = item.r();
    }

    public void n() {
        if (this.ab == 21204) {
            this.A();
        } else if (this.ab >= 21152 && this.ab <= 21155) {
            this.z();
        } else if (this.ab >= 21246 && this.ab <= 21257) {
            this.w();
        } else if (this.ab == 21446) {
            this.y();
        } else if (this.aa.aP() == 17 || this.aa.aP() == 22) {
            this.x();
        } else if (this.g()) {
            if (this.ab == 404) {
                this.bo = true;
            } else if (this.ab == 403) {
                this.bp = 5;
            } else if (this.ab == 66) {
                this.bd = this.G() * 4 + 4;
                this.aY = this.G();
            }
            WeaponSkillTable.a().a(this);
        }
    }

    private void bH() {
        if (this.G() <= 0) {
            return;
        }
        if (this.ab == 21308 && this.G() >= 5) {
            this.bk = this.G() - 4;
        } else if (this.ab == 21309 && this.G() >= 5) {
            this.aJ = this.G() - 4;
        } else if (this.ab == 21384 && this.G() >= 8) {
            this.av = this.G() - 7;
        } else if (this.ab == 21472) {
            this.as = this.G() * 5;
        } else if (this.ab == 21222 && this.G() >= 7) {
            this.aG = this.G() - 6;
        } else if (this.ab == 21105 && this.G() >= 5) {
            this.aM = this.G() - 4;
        } else if (this.ab == 21106 && this.G() >= 5) {
            this.aL = this.G() - 4;
        } else if (this.ab == 20079 && this.G() >= 7) {
            this.av = this.G() - 6;
        } else if (this.ab == 21351 && this.G() >= 5) {
            this.as = (int)Math.ceil(((double)this.G() - 4.0) / 2.0) * 25;
        } else if (this.ab == 21352 && this.G() >= 5) {
            this.av = (int)Math.ceil(((double)this.G() - 4.0) / 2.0);
        } else if (this.ab == 21353 && this.G() >= 5) {
            this.aG = (int)Math.ceil(((double)this.G() - 4.0) / 2.0);
        } else if (this.ab == 21516 && this.G() >= 5) {
            this.aM = this.G() - 4;
            this.aK = this.G() - 4;
        } else if (this.ab == 21518 && this.G() >= 5) {
            this.aw = (this.G() - 4) * 4;
        } else if (this.ab == 21519 && this.G() >= 5) {
            this.aJ = this.G() - 4;
        } else if (this.ab == 21525 && this.G() >= 8) {
            this.aE = this.G() - 7;
        } else if (this.ab == 21526) {
            this.aw = this.G() * 3;
        } else if (this.ab == 21527 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hit = new int[]{5, 10, 20};
            int[] pvp = new int[]{3, 5, 8};
            int[] str = new int[]{1, 3, 6};
            int[] nArray = new int[3];
            nArray[1] = -1;
            nArray[2] = -2;
            int[] dexint = nArray;
            int[] rate = new int[]{3, 10, 15};
            this.ba = hit[lv];
            this.aY = hit[lv];
            this.aZ = hit[lv];
            this.bd = pvp[lv];
            this.az = str[lv];
            this.aA = dexint[lv];
            this.aC = dexint[lv];
            this.bq = rate[lv];
        } else if (this.ab == 21528 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hit = new int[]{2, 5, 10};
            int[] pvp = new int[]{3, 5, 8};
            int[] dex = new int[]{1, 3, 6};
            int[] nArray = new int[3];
            nArray[1] = -1;
            nArray[2] = -2;
            int[] strint = nArray;
            int[] rate = new int[]{3, 10, 15};
            this.aK = hit[lv];
            this.bd = pvp[lv];
            this.aA = dex[lv];
            this.az = strint[lv];
            this.aC = strint[lv];
            this.bq = rate[lv];
        } else if (this.ab == 21529 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hit = new int[]{2, 5, 10};
            int[] pvp = new int[]{3, 5, 8};
            int[] intel = new int[]{1, 3, 6};
            int[] nArray = new int[3];
            nArray[1] = -1;
            nArray[2] = -2;
            int[] strdex = nArray;
            int[] rate = new int[]{3, 10, 15};
            this.aI = hit[lv];
            this.bd = pvp[lv];
            this.aC = intel[lv];
            this.az = strdex[lv];
            this.aA = strdex[lv];
            this.bq = rate[lv];
        } else if (this.ab == 21530 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] resist = new int[]{5, 10, 15};
            int[] pvp = new int[]{3, 5, 8};
            int[] rate = new int[]{3, 10, 15};
            this.aW = resist[lv];
            this.aV = resist[lv];
            this.aX = resist[lv];
            this.aw = resist[lv];
            this.be = pvp[lv];
            this.bq = rate[lv];
        } else if (this.ab == 21531 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hpmp = new int[]{10, 20, 30};
            int[] pvp = new int[]{3, 6, 8};
            this.as = hpmp[lv];
            this.at = hpmp[lv];
            this.aL = pvp[lv];
            this.aG = pvp[lv];
        } else if (this.ab == 21532 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hpmp = new int[]{10, 20, 30};
            int[] pvp = new int[]{3, 6, 8};
            this.as = hpmp[lv];
            this.at = hpmp[lv];
            this.aM = pvp[lv];
            this.aG = pvp[lv];
        } else if (this.ab == 21533 && this.G() >= 7) {
            int lv = Math.min(this.G() - 7, 2);
            int[] hpmp = new int[]{10, 20, 30};
            int[] sp = new int[]{1, 2, 4};
            int[] pvp = new int[]{3, 6, 8};
            this.as = hpmp[lv];
            this.at = hpmp[lv];
            this.av = sp[lv];
            this.aG = pvp[lv];
        } else if (this.ab == 21534) {
            int lv = Math.min(this.G(), 8);
            int[] hp = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] nArray = new int[9];
            nArray[4] = 1;
            nArray[5] = 2;
            nArray[6] = 3;
            nArray[7] = 3;
            nArray[8] = 3;
            int[] ac = nArray;
            int[] nArray2 = new int[9];
            nArray2[5] = 1;
            nArray2[6] = 2;
            nArray2[7] = 3;
            nArray2[8] = 4;
            int[] dmg = nArray2;
            int[] nArray3 = new int[9];
            nArray3[6] = 1;
            nArray3[7] = 3;
            nArray3[8] = 5;
            int[] cri = nArray3;
            this.as = hp[lv];
            this.au = ac[lv];
            this.aL = dmg[lv];
            this.bg = cri[lv];
        } else if (this.ab == 21535) {
            int lv = Math.min(this.G(), 8);
            int[] hp = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] nArray = new int[9];
            nArray[4] = 1;
            nArray[5] = 2;
            nArray[6] = 3;
            nArray[7] = 3;
            nArray[8] = 3;
            int[] ac = nArray;
            int[] nArray4 = new int[9];
            nArray4[5] = 1;
            nArray4[6] = 2;
            nArray4[7] = 3;
            nArray4[8] = 4;
            int[] dmg = nArray4;
            int[] nArray5 = new int[9];
            nArray5[6] = 1;
            nArray5[7] = 3;
            nArray5[8] = 5;
            int[] cri = nArray5;
            this.as = hp[lv];
            this.au = ac[lv];
            this.aM = dmg[lv];
            this.bh = cri[lv];
        } else if (this.ab == 21536) {
            int lv = Math.min(this.G(), 8);
            int[] hp = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] nArray = new int[9];
            nArray[4] = 1;
            nArray[5] = 2;
            nArray[6] = 3;
            nArray[7] = 3;
            nArray[8] = 3;
            int[] ac = nArray;
            int[] nArray6 = new int[9];
            nArray6[5] = 1;
            nArray6[6] = 2;
            nArray6[7] = 3;
            nArray6[8] = 4;
            int[] dmg = nArray6;
            int[] nArray7 = new int[9];
            nArray7[6] = 1;
            nArray7[7] = 2;
            nArray7[8] = 3;
            int[] cri = nArray7;
            this.as = hp[lv];
            this.au = ac[lv];
            this.aJ = dmg[lv];
            this.bi = cri[lv];
        } else if (this.ab == 21537) {
            int lv = Math.min(this.G(), 8);
            int[] hp = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] nArray = new int[9];
            nArray[3] = 1;
            nArray[4] = 2;
            nArray[5] = 3;
            nArray[6] = 5;
            nArray[7] = 6;
            nArray[8] = 7;
            int[] ac = nArray;
            int[] nArray8 = new int[9];
            nArray8[5] = 1;
            nArray8[6] = 2;
            nArray8[7] = 3;
            nArray8[8] = 4;
            int[] dmg = nArray8;
            int[] nArray9 = new int[9];
            nArray9[5] = 1;
            nArray9[6] = 3;
            nArray9[7] = 5;
            nArray9[8] = 7;
            int[] cri = nArray9;
            this.as = hp[lv];
            this.au = ac[lv];
            this.aG = dmg[lv];
            this.aw = cri[lv];
        } else if (this.ab == 124 && this.G() >= 7) {
            this.av = this.G() - 6;
        } else if (this.ab == 20107 && this.G() >= 3) {
            this.av = this.G() - 2;
        } else if (this.ab >= 393 && this.ab <= 400 && this.G() >= 8) {
            this.bd = this.G() - 8 + 2;
        } else if (this.ab >= 401 && this.ab <= 404) {
            this.bd = this.G() * 3 + 3;
        } else if (this.ab >= 21509 && this.ab <= 21511 && this.G() >= 7) {
            this.as = 10 + (this.G() - 6) * 10;
            this.be = 1 + (this.G() - 6) * 2;
        } else if (this.ab >= 21363 && this.ab <= 21365) {
            this.bK();
        } else if (this.ab >= 21406 && this.ab <= 21408) {
            this.bS();
        } else if (this.ab >= 21409 && this.ab <= 21411) {
            this.bT();
        } else if (this.ab >= 21447 && this.ab <= 21451) {
            this.bR();
        } else if (this.ab >= 21412 && this.ab <= 21414 && this.G() >= 7) {
            this.bN();
        } else if (this.ab >= 21415 && this.ab <= 21417 && this.G() >= 9) {
            this.bO();
        } else if (this.ab >= 21433 && this.ab <= 21436) {
            this.bQ();
        } else if (this.ab == 21441 && this.G() >= 7) {
            this.bP();
        } else if (this.ab == 21442 && this.G() >= 10) {
            this.bg = 7;
        } else if (this.ab == 21443 && this.G() >= 10) {
            this.bi = 7;
        } else if (this.ab == 21444 && this.G() >= 10) {
            this.bh = 7;
        } else if (this.ab == 21445 && this.G() >= 10) {
            this.bf = 100;
        } else if (this.ab == 21372) {
            this.bM();
        } else if (this.ab == 21373) {
            this.bL();
        } else if (this.aa != null && this.i()) {
            this.bI();
        }
    }

    public int o() {
        int mr = this.aa.R();
        if (this.N() == 21402 || this.N() == 20011 || this.N() == 20110 || this.N() == 21513 || this.N() >= 21123 && this.N() <= 21126) {
            mr += this.G();
        }
        if (this.N() >= 21241 && this.N() <= 21244 || this.N() == 21383 || this.N() == 20079) {
            mr += this.G() * 3;
        }
        if (this.N() >= 21319 && this.N() <= 21322) {
            mr += this.G();
        }
        if (this.N() == 20056 || this.N() == 120056 || this.N() == 220056 || this.N() == 21306) {
            mr += this.G() * 2;
        }
        return mr;
    }

    public int p() {
        if (this.a().l() == 0) {
            return 0;
        }
        return Math.max(this.E() * this.a().l() / 1000, 1);
    }

    public void q() {
        Arrays.fill(this.a, this.E());
        Arrays.fill(this.b, this.N());
        Arrays.fill(this.c, this.D());
        Arrays.fill(this.d, this.U());
        Arrays.fill(this.f, this.C());
        Arrays.fill(this.e, this.G());
        Arrays.fill(this.g, this.H());
        Arrays.fill(this.h, this.I());
        Arrays.fill(this.j, this.bb());
        Arrays.fill(this.i, this.M());
        Arrays.fill(this.k, this.J());
        Arrays.fill(this.l, this.F());
        Arrays.fill(this.m, this.K());
        Arrays.fill(this.n, this.L());
        Arrays.fill(this.o, this.X());
        Arrays.fill(this.p, this.Y());
        Arrays.fill(this.q, this.Z());
        Arrays.fill(this.r, this.aa());
    }

    public String r() {
        return this.c(this.R);
    }

    public String c(int count) {
        L1Pet pet;
        StringBuilder name = new StringBuilder(this.aL(count));
        int itemId = this.N();
        if ((itemId == 40314 || itemId == 40316) && (pet = PetTable.a().b(this.fr())) != null) {
            L1Npc npc = NpcTable.a().a(pet.c());
            name.append("[Lv." + pet.e() + " " + pet.d() + "]HP" + pet.f() + " " + npc.A());
        }
        if (this.f()) {
            if (this.a().aP() == 2) {
                if (this.T()) {
                    name.append(" ($10)");
                }
                if ((itemId == 40001 || itemId == 40002) && this.M() <= 0) {
                    name.append(" ($11)");
                }
            } else if ((this.a().aP() == 17 || this.a().aP() == 22) && this.U()) {
                name.append(" ($117)");
            }
        }
        if (this.D()) {
            if (this.g()) {
                name.append(" ($9)");
            } else if (this.h()) {
                name.append(" ($117)");
            }
        }
        return name.toString();
    }

    public String s() {
        return this.aL(this.R);
    }

    private String aL(int count) {
        StringBuilder name = new StringBuilder();
        if (this.C()) {
            int attrEnchantLevel;
            if (this.g() && (attrEnchantLevel = this.L()) > 0) {
                if (this.K() == 1) {
                    name.append((new String[]{"", "$6124", "$6125", "$6126", "$14364", "$14368"})[attrEnchantLevel]);
                } else if (this.K() == 2) {
                    name.append((new String[]{"", "$6115", "$6116", "$6117", "$14361", "$14365"})[attrEnchantLevel]);
                } else if (this.K() == 4) {
                    name.append((new String[]{"", "$6118", "$6119", "$6120", "$14362", "$14366"})[attrEnchantLevel]);
                } else if (this.K() == 8) {
                    name.append((new String[]{"", "$6121", "$6122", "$6123", "$14363", "$14367"})[attrEnchantLevel]);
                }
            }
            if (this.g() || this.h()) {
                if (this.G() >= 0) {
                    name.append("+" + this.G() + " ");
                } else if (this.G() < 0) {
                    name.append(String.valueOf(String.valueOf(this.G())) + " ");
                }
            }
        }
        if (this.N() == 21363 && this.G() >= 10) {
            name.append("$23516");
        } else if (this.N() == 21364 && this.G() >= 10) {
            name.append("$23515");
        } else if (this.N() == 21365 && this.G() >= 10) {
            name.append("$23517");
        } else {
            name.append(this.c());
        }
        if (this.C()) {
            if (this.a().aM() > 0) {
                name.append(" (" + this.I() + ")");
            }
            if (this.N() == 20383) {
                name.append(" (" + this.I() + ")");
            }
            if (this.a().T() > 0 && !this.f()) {
                name.append(" [" + this.M() + "]");
            }
        }
        if (this.N() == 640615 && this.M() != 0) {
            name.append(" -" + (this.M() - 1399));
        }
        if (this.bb() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(" [MM-dd HH:mm]");
            String formatDate = sdf.format(this.bb());
            name.append(formatDate);
        }
        if (this.N() == 40312 && this.M() != 0) {
            name.append(InnTable.a(this));
        }
        if (count > 1) {
            name.append(" (" + count + ")");
        }
        return name.toString();
    }

    public byte[] t() {
        BinaryOutputStream os = new BinaryOutputStream();
        L1PetItem petItem = PetItemTable.a().a(this.N());
        if (petItem != null) {
            if (petItem.n() == 1) {
                os.c(1);
                os.c(this.a().v());
                os.c(this.a().w());
                os.c(this.a().k());
                os.a(this.p());
            } else {
                os.c(19);
                os.c(-petItem.d());
                os.c(this.a().k());
                os.c(-1);
                os.a(this.p());
            }
            os.c(62);
            os.c(1);
            if (petItem.b() != 0) {
                os.c(5);
                os.c(petItem.b());
            }
            if (petItem.c() != 0) {
                os.c(6);
                os.c(petItem.c());
            }
            if (petItem.e() != 0) {
                os.c(8);
                os.c(petItem.e());
            }
            if (petItem.g() != 0) {
                os.c(9);
                os.c(petItem.g());
            }
            if (petItem.f() != 0) {
                os.c(10);
                os.c(petItem.f());
            }
            if (petItem.i() != 0) {
                os.c(11);
                os.c(petItem.i());
            }
            if (petItem.h() != 0) {
                os.c(12);
                os.c(petItem.h());
            }
            if (petItem.j() != 0) {
                os.c(14);
                os.b(petItem.j());
            }
            if (petItem.m() != 0) {
                os.c(15);
                os.b(petItem.m());
            }
            if (petItem.l() != 0) {
                os.c(17);
                os.c(petItem.l());
            }
            if (petItem.k() != 0) {
                os.c(32);
                os.c(petItem.k());
            }
        } else {
            int n;
            int desc;
            if (this.f()) {
                switch (this.a().aP()) {
                    case 2: {
                        os.c(22);
                        os.b(this.a().c());
                        break;
                    }
                    case 7: {
                        os.c(21);
                        os.b(this.a().V());
                        break;
                    }
                    case 29: {
                        os.c(21);
                        os.b(120);
                        break;
                    }
                    case 0: 
                    case 15: {
                        os.c(1);
                        os.c(this.a().v());
                        os.c(this.a().w());
                        break;
                    }
                    default: {
                        os.c(23);
                    }
                }
                os.c(this.a().k());
                os.a(this.p());
            } else {
                if (this.g()) {
                    os.c(1);
                    os.c(this.a().v());
                    os.c(this.a().w());
                    os.c(this.a().k());
                    os.a(this.p());
                }
                if (this.h()) {
                    os.c(19);
                    int ac = this.a().X();
                    if (ac < 0) {
                        ac = -ac;
                    }
                    os.c(ac);
                    os.c(this.a().k());
                    os.c(43 + this.a().ap());
                    os.a(this.p());
                }
                if (this.G() != 0) {
                    os.c(2);
                    if (this.i()) {
                        os.c(0);
                    } else {
                        os.c(this.G());
                    }
                }
                if (this.H() != 0) {
                    os.c(3);
                    os.c(this.H());
                }
                if (this.a().e()) {
                    os.c(4);
                }
                int bit = 0;
                bit |= this.a().y() ? 1 : 0;
                bit |= this.a().z() ? 2 : 0;
                bit |= this.a().A() ? 4 : 0;
                bit |= this.a().B() ? 8 : 0;
                bit |= this.a().C() ? 16 : 0;
                bit |= this.a().D() ? 32 : 0;
                bit |= this.a().E() ? 64 : 0;
                int n2 = this.a().F() ? 128 : 0;
                os.c(7);
                os.c(bit |= n2);
            }
            if (this.a().G() + this.az != 0) {
                os.c(8);
                os.c(this.a().G() + this.az);
            }
            if (this.a().H() + this.aA != 0) {
                os.c(9);
                os.c(this.a().H() + this.aA);
            }
            if (this.a().I() + this.aB != 0) {
                os.c(10);
                os.c(this.a().I() + this.aB);
            }
            if (this.a().K() + this.aD != 0) {
                os.c(11);
                os.c(this.a().K() + this.aD);
            }
            if (this.a().J() + this.aC != 0) {
                os.c(12);
                os.c(this.a().J() + this.aC);
            }
            if (this.a().L() + this.aE != 0) {
                os.c(13);
                os.c(this.a().L() + this.aE);
            }
            if (this.N() == 21204) {
                if (this.a().M() != 0) {
                    os.c(14);
                    os.b(this.a().M());
                }
            } else if (this.a().M() + this.as != 0) {
                os.c(14);
                os.b(this.a().M() + this.as);
            }
            if (this.o() + this.aw != 0) {
                os.c(15);
                os.b(this.o() + this.aw);
            }
            if (this.N() == 126 || this.N() == 127) {
                os.c(16);
            }
            if (this.a().Q() + this.av != 0) {
                os.c(17);
                os.c(this.a().Q() + this.av);
            }
            if (this.a().S() || this.bS) {
                os.c(18);
            }
            if (this.a().ab() + this.aK != 0) {
                os.c(24);
                os.c(this.a().ab() + this.aK);
            }
            if (this.a().ag() + this.aP != 0) {
                os.c(27);
                os.c(this.a().ag() + this.aP);
            }
            if (this.a().ae() + this.aN != 0) {
                os.c(28);
                os.c(this.a().ae() + this.aN);
            }
            if (this.a().af() + this.aO != 0) {
                os.c(29);
                os.c(this.a().af() + this.aO);
            }
            if (this.a().ah() + this.aQ != 0) {
                os.c(30);
                os.c(this.a().ah() + this.aQ);
            }
            if (this.a().N() + this.at != 0) {
                os.c(32);
                os.b(this.a().N() + this.at);
            }
            if (this.a().al() != 0) {
                os.c(33);
                os.c(1);
                os.c(this.a().al());
            }
            if (this.a().aj() + this.aR != 0) {
                os.c(33);
                os.c(2);
                os.c(this.a().aj() + this.aR);
            }
            if (this.a().ak() != 0) {
                os.c(33);
                os.c(3);
                os.c(this.a().ak());
            }
            if (this.a().an() != 0) {
                os.c(33);
                os.c(4);
                os.c(this.a().an());
            }
            if (this.a().ai() + this.aW != 0) {
                os.c(33);
                os.c(5);
                os.c(this.a().ai() + this.aW);
            }
            if (this.a().am() + this.aV != 0) {
                os.c(33);
                os.c(6);
                os.c(this.a().am() + this.aV);
            }
            if (this.a().ao() != 0) {
                os.c(33);
                os.c(7);
                os.c(this.a().ao());
            }
            if (this.N() == 262) {
                os.c(34);
            }
            if (this.a().ad() + this.aM != 0) {
                os.c(35);
                os.c(this.a().ad() + this.aM);
            }
            if (this.a().aw() + this.bW + this.bj != 0) {
                os.c(36);
                os.c(this.a().aw() + this.bW + this.bj);
            }
            if (this.a().O() + this.ax != 0) {
                os.c(37);
                os.c(this.a().O() + this.ax);
            }
            if (this.a().P() + this.ay != 0) {
                os.c(38);
                os.c(this.a().P() + this.ay);
            }
            if (this.a().as() + this.aI != 0) {
                os.c(40);
                os.c(this.a().as() + this.aI);
            }
            if (this.ab >= 21246 && this.ab <= 21257 && (desc = this.bJ()) > 0) {
                os.c(45);
                os.c(desc);
            }
            if (this.a().ac() + this.aL != 0) {
                os.c(47);
                os.c(this.a().ac() + this.aL);
                if (this.bK != 0) {
                    os.c(39);
                    os.a("$20274:" + this.bK + "%");
                }
            }
            if (this.a().aa() + this.aJ != 0) {
                os.c(48);
                os.c(this.a().aa() + this.aJ);
            }
            if (this.a().at() + this.bi != 0) {
                os.c(50);
                os.b(this.a().at() + this.bi);
            }
            if (this.bb != 0) {
                os.c(51);
                os.c(this.bb);
            }
            if (this.au != 0) {
                os.c(56);
                os.c(this.au);
            }
            if (this.bT) {
                os.c(57);
                os.a(18976);
            }
            if (this.B()) {
                os.c(57);
                os.a(19128);
            }
            if (this.a().aq() + this.bd != 0) {
                os.c(59);
                os.c(this.a().aq() + this.bd);
            }
            if (this.a().ar() + this.be != 0) {
                os.c(60);
                os.c(this.a().ar() + this.be);
            }
            if (this.bb() != null) {
                try {
                    os.c(61);
                    Date baseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("1997-01-01 16:00");
                    os.a(((int)(this.bb().getTime() / 1000L) - (int)(baseDate.getTime() / 1000L)) * 6);
                }
                catch (ParseException baseDate) {
                    // empty catch block
                }
            }
            if (this.a().Y() + this.aG != 0) {
                os.c(63);
                os.c(this.a().Y() + this.aG);
                if (this.bP > 0) {
                    os.c(39);
                    os.a("$20274:" + this.bP + "%");
                }
            }
            if (this.a().au() + this.aF != 0) {
                os.c(65);
                os.c(this.a().au() + this.aF);
                os.c(1);
            }
            if (this.i()) {
                os.c(67);
                if (this.a().aP() == 11 || this.a().aP() == 13) {
                    os.c(43);
                } else if (this.a().aP() == 9) {
                    os.c(44);
                } else if (this.a().aP() == 12) {
                    os.c(45);
                }
            }
            if (this.bQ + this.bk != 0) {
                os.c(68);
                os.c(this.bQ + this.bk);
            }
            if (this.bX != null) {
                os.c(74);
                os.a(this.bX);
            }
            if (this.bV != 0) {
                os.c(87);
                os.c(this.bV);
            }
            if (this.bU != 0) {
                os.c(88);
                os.c(this.bU);
            }
            if (this.a().Z() != 0) {
                os.c(90);
                os.b(this.a().Z());
            }
            if (this.bc != 0) {
                os.c(93);
                os.c(this.bc);
            }
            if (this.bo) {
                os.c(94);
            }
            if (this.a().av() + this.aH != 0) {
                os.c(97);
                os.c(this.a().av() + this.aH);
            }
            if (this.a().ay() + this.aY != 0) {
                os.c(98);
                os.a(23521);
                os.b(this.a().ay() + this.aY);
            }
            if (this.a().az() + this.aZ != 0) {
                os.c(98);
                os.a(24131);
                os.b(this.a().az() + this.aZ);
            }
            if (this.a().aA() + this.ba != 0) {
                os.c(98);
                os.a(11147);
                os.b(this.a().aA() + this.ba);
            }
            if (this.bh != 0) {
                os.c(99);
                os.c(this.bh);
            }
            if (this.bg != 0) {
                os.c(100);
                os.c(this.bg);
            }
            if (this.br != 0) {
                os.c(101);
                os.c(this.br);
            }
            if (this.bp != 0) {
                os.c(102);
                os.c(this.bp);
            }
            if (this.bf != 0) {
                os.c(104);
                os.b(this.bf);
            }
            if (this.N() == 21204) {
                os.c(49);
                os.c(1);
                int i = 1;
                while (i <= 4) {
                    String text = this.aM(i);
                    if (text != null) {
                        if (text.contains(",")) {
                            String[] stringArray = text.split(",");
                            n = stringArray.length;
                            int n3 = 0;
                            while (n3 < n) {
                                String s = stringArray[n3];
                                os.c(39);
                                os.a(s);
                                ++n3;
                            }
                        } else {
                            os.c(39);
                            os.a(text);
                        }
                    }
                    ++i;
                }
                os.c(49);
                os.c(0);
            }
            ArrayList<ArmorSetTable.L1R_a> resultList = ArmorSetTable.a().a(this.N());
            for (ArmorSetTable.L1R_a as : resultList) {
                if (as.b().length > 1) {
                    os.c(39);
                    os.a("\\aL" + as.A());
                    if (resultList.size() == 1) {
                        int[] nArray = as.b();
                        int n4 = nArray.length;
                        n = 0;
                        while (n < n4) {
                            int i = nArray[n];
                            os.c(39);
                            os.a("\\fR " + ItemTable.a().a(i).j());
                            ++n;
                        }
                    }
                    os.c(69);
                    os.c(this.W() ? 1 : 2);
                }
                if (as.c() > 0 && as.d() > 0) {
                    os.c(71);
                    os.b(as.d());
                }
                if (as.e() < 0) {
                    os.c(56);
                    os.c(-as.e());
                }
                if (as.f() > 0) {
                    os.c(14);
                    os.b(as.f());
                }
                if (as.g() > 0) {
                    os.c(32);
                    os.b(as.g());
                }
                if (as.h() > 0) {
                    os.c(37);
                    os.c(as.h());
                }
                if (as.i() > 0) {
                    os.c(38);
                    os.c(as.i());
                }
                if (as.j() > 0) {
                    os.c(15);
                    os.b(as.j());
                }
                if (as.k() > 0) {
                    os.c(8);
                    os.c(as.k());
                }
                if (as.l() > 0) {
                    os.c(9);
                    os.c(as.l());
                }
                if (as.m() > 0) {
                    os.c(10);
                    os.c(as.m());
                }
                if (as.n() > 0) {
                    os.c(11);
                    os.c(as.n());
                }
                if (as.o() > 0) {
                    os.c(13);
                    os.c(as.o());
                }
                if (as.p() > 0) {
                    os.c(12);
                    os.c(as.p());
                }
                if (as.u() > 0) {
                    os.c(48);
                    os.c(as.u());
                }
                if (as.v() > 0) {
                    os.c(47);
                    os.c(as.v());
                }
                if (as.w() > 0) {
                    os.c(24);
                    os.c(as.w());
                }
                if (as.x() > 0) {
                    os.c(35);
                    os.c(as.x());
                }
                if (as.y() > 0) {
                    os.c(17);
                    os.c(as.y());
                }
                if (as.q() > 0) {
                    os.c(28);
                    os.c(as.q());
                }
                if (as.r() > 0) {
                    os.c(29);
                    os.c(as.r());
                }
                if (as.s() > 0) {
                    os.c(27);
                    os.c(as.s());
                }
                if (as.t() > 0) {
                    os.c(30);
                    os.c(as.t());
                }
                if (as.z() > 0) {
                    os.c(63);
                    os.c(as.z());
                }
                if (as.b().length <= 1) continue;
                os.c(69);
                os.c(0);
            }
            if (!this.f()) {
                os.c(39);
                os.a("\u5b89\u5b9a\u503c:" + (this.a().x() < 0 ? "\u4e0d\u53ef\u5f37\u5316" : Integer.valueOf(this.a().x())));
            }
            if (this.h()) {
                if (this.a().aP() == 15) {
                    os.c(39);
                    os.a("\\aE\u7b2c1\u8f14\u52a9\u6b04\u4f4d");
                } else if (this.a().aP() == 18) {
                    os.c(39);
                    os.a("\\aE\u7b2c2\u8f14\u52a9\u6b04\u4f4d");
                } else if (this.a().aP() == 16) {
                    os.c(39);
                    os.a("\\aE\u7b2c3\u8f14\u52a9\u6b04\u4f4d");
                }
            }
            if (this.g() && (!this.a().aE() || this.a().aO() == 0 || this.a().aO() == 40 || this.a().aO() == 58 || this.a().aO() == 58)) {
                os.c(39);
                os.a("\u4e0d\u6703\u640d\u58de");
            }
        }
        byte[] result = os.b();
        try {
            os.close();
        }
        catch (IOException e) {
            O.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return result;
    }

    private void f(L1PcInstance pc) {
        if (this.ah != null && this.ah.bE() > 0) {
            this.ah.a(new S_ServerMessage(308, this.s()));
        }
        if (pc != null && pc == this.ah && pc.j().f(this.N()) && this.h() && this.D() && this.a().aP() == 2) {
            pc.bL(3);
            pc.a(new S_OwnCharStatus(pc));
        }
        this.ac = 0;
        this.ad = 0;
        this.ae = 0;
        this.af = 0;
        this.ag = null;
    }

    public void b(L1PcInstance pc, int skillTime) {
        if (pc == null || !this.h() || this.a().aP() != 2) {
            return;
        }
        if (this.ag != null) {
            this.ag.cancel(true);
            this.f(pc);
        }
        if (this.D()) {
            pc.bL(-3);
            pc.a(new S_OwnCharStatus(pc));
        }
        this.ac = 3;
        this.ah = pc;
        this.ag = GeneralThreadPool.a().a(new L1R_b(), skillTime);
    }

    public void a(L1PcInstance pc, int skillId, int skillTime) {
        if (pc == null || !this.g()) {
            return;
        }
        if (this.ag != null) {
            this.ag.cancel(true);
            this.f(pc);
        }
        switch (skillId) {
            case 8: {
                this.ae = 1;
                this.af = 1;
                break;
            }
            case 12: {
                this.ad = 2;
                break;
            }
            case 48: {
                this.ad = 2;
                this.af = 2;
                break;
            }
            case 107: {
                this.ad = 2;
            }
        }
        this.ah = pc;
        this.ag = GeneralThreadPool.a().a(new L1R_b(), skillTime);
    }

    public void d(L1PcInstance pc) {
        this.ai = pc.fr();
        GeneralThreadPool.a().a(new L1R_c(), 10000L);
    }

    public void e(L1PcInstance pc) {
        if (this.M() > 0) {
            this.aj = GeneralThreadPool.a().a(new L1R_a(pc), 1000L, 1000L);
        }
    }

    public void u() {
        if (this.aj != null) {
            this.aj.cancel(true);
        }
    }

    public int v() {
        int bless;
        if (!this.C()) {
            return 0;
        }
        int statusX = 1;
        if (!this.a().s()) {
            statusX |= 2;
        }
        if (this.a().t()) {
            statusX |= 4;
        }
        if (this.a().x() < 0) {
            statusX |= 8;
        }
        if (this.a().x() < 0) {
            statusX |= 0x10;
        }
        if ((bless = this.F()) >= 128 && bless <= 131) {
            statusX |= 2;
            statusX |= 4;
            statusX |= 8;
            statusX |= 0x20;
        } else if (bless > 131) {
            statusX |= 0x40;
        }
        if (this.a().aF()) {
            statusX |= 0x80;
        }
        return statusX;
    }

    private void bI() {
        int level = Math.min(this.G(), 9);
        if (this.a().aP() == 11 || this.a().aP() == 13) {
            int[] nArray = new int[10];
            nArray[1] = 5;
            nArray[2] = 10;
            nArray[3] = 20;
            nArray[4] = 30;
            nArray[5] = 40;
            nArray[6] = 40;
            nArray[7] = 50;
            nArray[8] = 50;
            nArray[9] = 100;
            int[] hp = nArray;
            int[] nArray2 = new int[10];
            nArray2[4] = 1;
            nArray2[5] = 2;
            nArray2[6] = 4;
            nArray2[7] = 5;
            nArray2[8] = 6;
            nArray2[9] = 8;
            int[] dmg = nArray2;
            int[] nArray3 = new int[10];
            nArray3[5] = 4;
            nArray3[6] = 6;
            nArray3[7] = 10;
            nArray3[8] = 14;
            nArray3[9] = 16;
            int[] potion = nArray3;
            int[] nArray4 = new int[10];
            nArray4[5] = 1;
            nArray4[6] = 2;
            nArray4[7] = 3;
            nArray4[8] = 4;
            nArray4[9] = 5;
            int[] dr = nArray4;
            int[] nArray5 = new int[10];
            nArray5[6] = 2;
            nArray5[7] = 4;
            nArray5[8] = 8;
            nArray5[9] = 10;
            int[] magicHit = nArray5;
            int[] nArray6 = new int[10];
            nArray6[7] = 1;
            nArray6[8] = 2;
            nArray6[9] = 5;
            int[] pvpDmg = nArray6;
            int[] nArray7 = new int[10];
            nArray7[9] = 10;
            int[] mr = nArray7;
            this.as = hp[level];
            this.aL = dmg[level];
            this.aM = dmg[level];
            this.aF = potion[level];
            this.aG = dr[level];
            this.aI = magicHit[level];
            this.bd = pvpDmg[level];
            this.aw = mr[level];
        } else if (this.a().aP() == 9) {
            int[] nArray = new int[10];
            nArray[1] = 5;
            nArray[2] = 10;
            nArray[3] = 20;
            nArray[4] = 30;
            nArray[5] = 40;
            nArray[6] = 40;
            nArray[7] = 50;
            nArray[8] = 50;
            nArray[9] = 100;
            int[] hp = nArray;
            int[] nArray8 = new int[10];
            nArray8[4] = 1;
            nArray8[5] = 3;
            nArray8[6] = 4;
            nArray8[7] = 4;
            nArray8[8] = 5;
            nArray8[9] = 7;
            int[] dmg = nArray8;
            int[] nArray9 = new int[10];
            nArray9[4] = 1;
            nArray9[5] = 3;
            nArray9[6] = 3;
            nArray9[7] = 4;
            nArray9[8] = 5;
            nArray9[9] = 7;
            int[] dr = nArray9;
            int[] nArray10 = new int[10];
            nArray10[5] = 1;
            nArray10[6] = 3;
            nArray10[7] = 4;
            nArray10[8] = 5;
            nArray10[9] = 7;
            int[] ac = nArray10;
            int[] nArray11 = new int[10];
            nArray11[6] = 1;
            nArray11[7] = 2;
            nArray11[8] = 4;
            nArray11[9] = 5;
            int[] sp = nArray11;
            int[] nArray12 = new int[10];
            nArray12[7] = 1;
            nArray12[8] = 2;
            nArray12[9] = 5;
            int[] pvpDmg = nArray12;
            int[] nArray13 = new int[10];
            nArray13[9] = 5;
            int[] fear = nArray13;
            this.as = hp[level];
            this.aL = dmg[level];
            this.aM = dmg[level];
            this.aG = dr[level];
            this.au = ac[level];
            this.av = sp[level];
            this.bd = pvpDmg[level];
            this.aX = fear[level];
        } else if (this.a().aP() == 12) {
            int[] nArray = new int[10];
            nArray[1] = 5;
            nArray[2] = 10;
            nArray[3] = 20;
            nArray[4] = 30;
            nArray[5] = 40;
            nArray[6] = 40;
            nArray[7] = 50;
            nArray[8] = 50;
            nArray[9] = 75;
            int[] mp = nArray;
            int[] nArray14 = new int[10];
            nArray14[4] = 1;
            nArray14[5] = 2;
            nArray14[6] = 4;
            nArray14[7] = 4;
            nArray14[8] = 5;
            nArray14[9] = 7;
            int[] dr = nArray14;
            int[] nArray15 = new int[10];
            nArray15[5] = 3;
            nArray15[6] = 5;
            nArray15[7] = 7;
            nArray15[8] = 10;
            nArray15[9] = 15;
            int[] mr = nArray15;
            int[] nArray16 = new int[10];
            nArray16[6] = 1;
            nArray16[7] = 2;
            nArray16[8] = 3;
            nArray16[9] = 5;
            int[] ac = nArray16;
            int[] nArray17 = new int[10];
            nArray17[7] = 1;
            nArray17[8] = 2;
            nArray17[9] = 5;
            int[] pvpDr = nArray17;
            int[] nArray18 = new int[10];
            nArray18[9] = 5;
            int[] fear = nArray18;
            this.at = mp[level];
            this.aG = dr[level];
            this.aw = mr[level];
            this.au = ac[level];
            this.be = pvpDr[level];
            this.aX = fear[level];
        }
    }

    private int bJ() {
        int result = 0;
        int status = this.ao;
        if ((status & 8) == 8) {
            result = 1;
        } else if ((status & 0x20000) == 131072) {
            result = 7;
        } else if ((status & 0x20) == 32) {
            result = 10;
        } else if ((status & 0x10) == 16) {
            result = 11;
        } else if ((status & 3) == 3) {
            result = 12;
        } else if ((status & 0x10000) == 65536) {
            result = 13;
        } else if ((status & 4) == 4) {
            result = 14;
        } else if ((status & 0x4000) == 16384) {
            result = 15;
        } else if ((status & 0x8000) == 32768) {
            result = 16;
        }
        return result;
    }

    private void bK() {
        int lv = Math.min(this.G(), 10);
        int[] nArray = new int[11];
        nArray[2] = 10;
        nArray[3] = 10;
        nArray[4] = 20;
        nArray[5] = 20;
        nArray[6] = 30;
        nArray[7] = 30;
        nArray[8] = 40;
        nArray[9] = 50;
        nArray[10] = 60;
        int[] hp = nArray;
        int[] nArray2 = new int[11];
        nArray2[6] = 1;
        nArray2[7] = 2;
        nArray2[8] = 2;
        nArray2[9] = 3;
        nArray2[10] = 3;
        int[] dr = nArray2;
        int[] nArray3 = new int[11];
        nArray3[7] = 1;
        nArray3[8] = 2;
        nArray3[9] = 4;
        nArray3[10] = 5;
        int[] pvp_dmg = nArray3;
        int[] nArray4 = new int[11];
        nArray4[5] = 1;
        nArray4[6] = 1;
        nArray4[7] = 2;
        nArray4[8] = 3;
        nArray4[9] = 3;
        nArray4[10] = 4;
        int[] dmg = nArray4;
        if (this.N() == 21363) {
            int[] nArray5 = new int[11];
            nArray5[5] = 1;
            nArray5[6] = 1;
            nArray5[7] = 2;
            nArray5[8] = 3;
            nArray5[9] = 3;
            nArray5[10] = 4;
            int[] sp = nArray5;
            this.as = hp[lv];
            this.av = sp[lv];
            this.aG = dr[lv];
            this.bd = pvp_dmg[lv];
        } else if (this.N() == 21364) {
            this.as = hp[lv];
            this.aL = dmg[lv];
            this.aG = dr[lv];
            this.bd = pvp_dmg[lv];
        } else if (this.N() == 21365) {
            this.as = hp[lv];
            this.aM = dmg[lv];
            this.aG = dr[lv];
            this.bd = pvp_dmg[lv];
        }
    }

    private void bL() {
        int lv = Math.min(this.G(), 9);
        int[] hp = new int[]{10, 10, 10, 10, 20, 30, 40, 50, 70, 100};
        int[] mp = new int[]{10, 10, 10, 10, 20, 20, 20, 20, 20, 30};
        int[] nArray = new int[10];
        nArray[7] = 5;
        nArray[8] = 15;
        nArray[9] = 30;
        int[] attr = nArray;
        int[] nArray2 = new int[10];
        nArray2[7] = 1;
        nArray2[8] = 3;
        nArray2[9] = 5;
        int[] hpr_mpr = nArray2;
        int[] nArray3 = new int[10];
        nArray3[8] = 1;
        nArray3[9] = 3;
        int[] dr = nArray3;
        this.as = hp[lv];
        this.at = mp[lv];
        this.aQ = attr[lv];
        this.aP = attr[lv];
        this.aN = attr[lv];
        this.aO = attr[lv];
        this.ax = hpr_mpr[lv];
        this.ay = hpr_mpr[lv];
        this.aG = dr[lv];
    }

    private void bM() {
        int lv = Math.min(this.G(), 9);
        int[] hp = new int[]{10, 10, 10, 10, 20, 30, 40, 50, 70, 100};
        int[] mp = new int[]{10, 10, 10, 10, 20, 20, 20, 20, 20, 30};
        int[] nArray = new int[10];
        nArray[7] = 1;
        nArray[8] = 3;
        nArray[9] = 5;
        int[] dmg = nArray;
        int[] nArray2 = new int[10];
        nArray2[8] = 1;
        nArray2[9] = 2;
        int[] sp = nArray2;
        int[] nArray3 = new int[10];
        nArray3[9] = 1;
        int[] str_int_dex = nArray3;
        this.as = hp[lv];
        this.at = mp[lv];
        this.aL = dmg[lv];
        this.aM = dmg[lv];
        this.av = sp[lv];
        this.az = str_int_dex[lv];
        this.aC = str_int_dex[lv];
        this.aA = str_int_dex[lv];
    }

    private void bN() {
        this.aw = 15 + (this.G() - 7) * 2;
        this.aG = 5;
        if (this.N() == 21412) {
            this.az = 2;
            this.as = 15;
            this.ax = 3;
        } else if (this.N() == 21413) {
            this.aA = 2;
            this.as = 10;
            this.at = 10;
            this.ax = 2;
            this.ay = 1;
        } else if (this.N() == 21414) {
            this.aC = 2;
            this.as = 10;
            this.at = 10;
            this.ay = 3;
        }
    }

    private void bO() {
        int lv = Math.min(this.G() - 9, 2);
        int[] str = new int[]{4, 4, 5};
        int[] mr = new int[]{20, 22, 25};
        int[] hpr = new int[]{5, 6, 7};
        int[] hp = new int[]{50, 70, 100};
        int[] hpmp = new int[]{25, 35, 50};
        int[] res = new int[]{10, 12, 15};
        int[] nArray = new int[3];
        nArray[1] = 2;
        nArray[2] = 5;
        int[] pvpdr = nArray;
        this.aG = 7;
        this.bn = true;
        this.bj = 20;
        this.aw = mr[lv];
        this.aW = res[lv];
        this.be = pvpdr[lv];
        if (this.N() == 21415) {
            this.az = str[lv];
            this.ax = hpr[lv];
            this.as = hp[lv];
        } else if (this.N() == 21416) {
            this.aA = str[lv];
            this.ax = hpr[lv];
            this.as = hp[lv];
        } else if (this.N() == 21417) {
            this.aC = str[lv];
            this.ay = hpr[lv];
            this.as = hpmp[lv];
            this.at = hpmp[lv];
        }
    }

    private void bP() {
        int lv = Math.min(this.G() - 7, 3);
        int[] str = new int[]{1, 2, 3, 3};
        int[] hp = new int[]{70, 80, 90, 100};
        int[] mp = new int[]{30, 40, 50, 60};
        int[] dmg = new int[]{6, 7, 8, 9};
        int[] hpr = new int[]{3, 5, 5, 5};
        int[] pvpdmg = new int[]{1, 3, 5, 7};
        this.az = str[lv];
        this.aC = str[lv];
        this.aA = str[lv];
        this.as = hp[lv];
        this.at = mp[lv];
        this.aL = dmg[lv];
        this.aM = dmg[lv];
        this.av = dmg[lv];
        this.aJ = dmg[lv];
        this.aK = dmg[lv];
        this.ax = hpr[lv];
        this.ay = hpr[lv];
        this.bd = pvpdmg[lv];
    }

    private void bQ() {
        int lv = Math.min(this.G(), 5);
        int[] nArray = new int[6];
        nArray[1] = 10;
        nArray[2] = 20;
        nArray[3] = 30;
        nArray[4] = 40;
        nArray[5] = 50;
        int[] hpmp = nArray;
        int[] nArray2 = new int[6];
        nArray2[1] = 1;
        nArray2[2] = 1;
        nArray2[3] = 2;
        nArray2[4] = 2;
        nArray2[5] = 5;
        int[] dr = nArray2;
        int[] nArray3 = new int[6];
        nArray3[1] = 1;
        nArray3[2] = 1;
        nArray3[3] = 2;
        nArray3[4] = 3;
        nArray3[5] = 5;
        int[] hprmpr = nArray3;
        int[] nArray4 = new int[6];
        nArray4[4] = 1;
        nArray4[5] = 3;
        int[] sp = nArray4;
        int[] nArray5 = new int[6];
        nArray5[5] = 1;
        int[] all = nArray5;
        this.as = hpmp[lv];
        this.at = hpmp[lv];
        this.ax = hprmpr[lv];
        this.ay = hprmpr[lv];
        this.aG = dr[lv];
        this.av = sp[lv];
        this.az = all[lv];
        this.aA = all[lv];
        this.aB = all[lv];
        this.aC = all[lv];
        this.aD = all[lv];
        this.aE = all[lv];
    }

    private void bR() {
        int lv = Math.min(this.G(), 9);
        int[] nArray = new int[10];
        nArray[1] = 15;
        nArray[2] = 20;
        nArray[3] = 25;
        nArray[4] = 30;
        nArray[5] = 35;
        nArray[6] = 40;
        nArray[7] = 45;
        nArray[8] = 50;
        nArray[9] = 55;
        int[] hp = nArray;
        int[] nArray2 = new int[10];
        nArray2[2] = 1;
        nArray2[3] = 2;
        nArray2[4] = 3;
        nArray2[5] = 3;
        nArray2[6] = 3;
        nArray2[7] = 3;
        nArray2[8] = 3;
        nArray2[9] = 3;
        int[] ac = nArray2;
        int[] nArray3 = new int[10];
        nArray3[5] = 1;
        nArray3[6] = 2;
        nArray3[7] = 3;
        nArray3[8] = 4;
        nArray3[9] = 5;
        int[] dr = nArray3;
        this.as = hp[lv];
        this.au = ac[lv];
        this.aG = dr[lv];
    }

    private void bS() {
        int lv = Math.min(this.G(), 5);
        int[] nArray = new int[6];
        nArray[5] = 1;
        int[] ac = nArray;
        int[] nArray2 = new int[6];
        nArray2[2] = 3;
        nArray2[3] = 5;
        nArray2[4] = 7;
        nArray2[5] = 15;
        int[] mr = nArray2;
        int[] nArray3 = new int[6];
        nArray3[2] = 90;
        nArray3[3] = 100;
        nArray3[4] = 110;
        nArray3[5] = 120;
        int[] hp = nArray3;
        int[] nArray4 = new int[6];
        nArray4[2] = 70;
        nArray4[3] = 80;
        nArray4[4] = 90;
        nArray4[5] = 100;
        int[] mp = nArray4;
        int[] nArray5 = new int[6];
        nArray5[2] = 1;
        nArray5[3] = 1;
        nArray5[4] = 2;
        nArray5[5] = 3;
        int[] str_dex_int = nArray5;
        int[] nArray6 = new int[6];
        nArray6[2] = 3;
        nArray6[3] = 3;
        nArray6[4] = 5;
        nArray6[5] = 8;
        int[] dmg_hit_dr = nArray6;
        int[] nArray7 = new int[6];
        nArray7[3] = 10;
        nArray7[4] = 12;
        nArray7[5] = 16;
        int[] potion = nArray7;
        int[] nArray8 = new int[6];
        nArray8[3] = 5;
        nArray8[4] = 10;
        nArray8[5] = 20;
        int[] exp = nArray8;
        this.au = ac[lv];
        this.aw = mr[lv];
        this.as = hp[lv];
        this.at = mp[lv];
        this.aG = dmg_hit_dr[lv];
        this.aF = potion[lv];
        this.bj = exp[lv];
        if (this.N() == 21406) {
            this.az = str_dex_int[lv];
            this.aL = dmg_hit_dr[lv];
            this.aJ = dmg_hit_dr[lv];
        } else if (this.N() == 21407) {
            this.aA = str_dex_int[lv];
            this.aM = dmg_hit_dr[lv];
            this.aK = dmg_hit_dr[lv];
        } else if (this.N() == 21408) {
            this.aC = str_dex_int[lv];
            this.av = str_dex_int[lv];
            this.aI = dmg_hit_dr[lv];
            this.aJ = dmg_hit_dr[lv];
        }
    }

    private void bT() {
        int lv = Math.min(this.G(), 5);
        int[] hp = new int[]{70, 80, 90, 100, 110, 120};
        int[] mp = new int[]{50, 60, 70, 80, 90, 100};
        int[] str = new int[]{1, 1, 1, 2, 3, 3};
        int[] sp = new int[]{1, 1, 1, 2, 3, 5};
        int[] dmg_hit_pvpdr = new int[]{1, 1, 3, 5, 7, 10};
        int[] pvpDmg = new int[]{3, 3, 3, 5, 7, 10};
        int[] nArray = new int[6];
        nArray[3] = 1;
        nArray[4] = 2;
        nArray[5] = 3;
        int[] critical = nArray;
        int[] nArray2 = new int[6];
        nArray2[3] = 5;
        nArray2[4] = 7;
        nArray2[5] = 15;
        int[] res = nArray2;
        this.as = hp[lv];
        this.at = mp[lv];
        this.be = dmg_hit_pvpdr[lv];
        this.bd = pvpDmg[lv];
        this.aW = res[lv];
        if (this.N() == 21409) {
            this.az = str[lv];
            this.aL = dmg_hit_pvpdr[lv];
            this.aJ = dmg_hit_pvpdr[lv];
            this.bg = critical[lv];
        } else if (this.N() == 21410) {
            this.aA = str[lv];
            this.aM = dmg_hit_pvpdr[lv];
            this.aK = dmg_hit_pvpdr[lv];
            this.bh = critical[lv];
        } else if (this.N() == 21411) {
            this.aC = str[lv];
            this.av = sp[lv];
            this.aI = dmg_hit_pvpdr[lv];
            this.aJ = dmg_hit_pvpdr[lv];
            this.bi = critical[lv];
        }
    }

    public void w() {
        this.aN = 0;
        this.aQ = 0;
        this.aP = 0;
        this.aO = 0;
        this.aR = 0;
        this.aW = 0;
        this.aV = 0;
        this.ax = 0;
        this.ay = 0;
        this.au = 0;
        this.aw = 0;
        this.as = 0;
        this.at = 0;
        int status = this.ao;
        if ((status & 8) == 8) {
            this.aN = 10;
            this.aQ = 10;
            this.aP = 10;
            this.aO = 10;
        } else if ((status & 0x20000) == 131072) {
            this.aR = 10;
        } else if ((status & 0x20) == 32) {
            this.aW = 10;
        } else if ((status & 0x10) == 16) {
            this.aV = 10;
        } else if ((status & 1) == 1) {
            this.ax = 1;
        } else if ((status & 2) == 2) {
            this.ay = 1;
        } else if ((status & 0x10000) == 65536) {
            this.au = 1;
        } else if ((status & 4) == 4) {
            this.aw = 10;
        } else if ((status & 0x4000) == 16384) {
            this.as = 50;
        } else if ((status & 0x8000) == 32768) {
            this.at = 30;
        }
    }

    public void d(int classType) {
        if (classType == 0) {
            this.aG = 3;
        } else if (classType == 1) {
            this.as = 50;
        } else if (classType == 2) {
            this.at = 50;
        } else if (classType == 3) {
            this.ay = 3;
        } else if (classType == 4) {
            this.au = 3;
        } else if (classType == 5) {
            this.aJ = 3;
        } else if (classType == 6) {
            this.bk = 5;
        } else if (classType == 7) {
            this.as = 50;
        }
        if (this.N() >= 21345 && this.N() <= 21349) {
            if (classType == 0) {
                this.aJ = 2;
            } else if (classType == 1) {
                this.aL = 1;
            } else if (classType == 2) {
                this.aM = 1;
            } else if (classType == 3) {
                this.av = 1;
            } else if (classType == 4) {
                this.at = 30;
            } else if (classType == 5) {
                this.aG = 1;
            } else if (classType == 6) {
                this.as = 50;
            } else if (classType == 7) {
                this.aw = 5;
            }
        }
    }

    public void x() {
        this.as = 0;
        this.at = 0;
        this.bU = 0;
        this.au = 0;
        this.aw = 0;
        this.aG = 0;
        MagicDollTable.b().a(this);
        if (this.ao > 0) {
            int status = this.ao;
            if ((status & 0x4000) == 16384) {
                this.as += 10;
                this.bU += 3;
            } else if ((status & 0x10000) == 65536) {
                ++this.au;
                ++this.aw;
            } else if ((status & 0x8000) == 32768) {
                this.at += 10;
                this.as += 30;
            } else if ((status & 2) == 2) {
                this.bU += 8;
            } else if ((status & 0x40000) == 262144) {
                ++this.aG;
            }
        } else if (this.ap > 0) {
            int status = this.ap;
            if ((status & 0x4000) == 16384) {
                this.as += 20;
                this.bU += 5;
            } else if ((status & 0x10000) == 65536) {
                this.au += 5;
                this.aw += 5;
            } else if ((status & 0x8000) == 32768) {
                this.at += 30;
                this.as += 50;
            } else if ((status & 2) == 2) {
                this.bU += 15;
            } else if ((status & 0x40000) == 262144) {
                this.aG += 2;
            }
        } else if (this.aq > 0) {
            int status = this.aq;
            if ((status & 0x4000) == 16384) {
                this.as += 50;
                this.bU += 10;
                ++this.aG;
            } else if ((status & 0x10000) == 65536) {
                this.au += 10;
                this.aw += 10;
                ++this.aG;
            } else if ((status & 0x8000) == 32768) {
                this.at += 70;
                this.as += 80;
            } else if ((status & 2) == 2) {
                this.bU += 21;
            } else if ((status & 0x40000) == 262144) {
                this.aG += 7;
            }
        }
    }

    public void y() {
        int i = 1;
        while (i <= 4) {
            int status = 0;
            if (i == 1) {
                status = this.ao;
            } else if (i == 2) {
                status = this.ap;
            } else if (i == 3) {
                status = this.aq;
            } else if (i == 4) {
                status = this.ar;
            }
            int value = 0;
            if ((status & 0x800) == 2048) {
                value = 1;
            } else if ((status & 0x1000) == 4096) {
                value = 2;
            } else if ((status & 0x2000) == 8192) {
                value = 3;
            } else if ((status & 0x100000) == 0x100000) {
                value = 4;
            }
            if ((status & 1) == 1) {
                this.ax = value;
            } else if ((status & 2) == 2) {
                this.ay = value;
            } else if ((status & 4) == 4) {
                this.aw = value;
            } else if ((status & 8) == 8) {
                this.aN = 3 + value * 3;
                this.aQ = 3 + value * 3;
                this.aP = 3 + value * 3;
                this.aO = 3 + value * 3;
            } else if ((status & 0x10) == 16) {
                this.aV = value;
            } else if ((status & 0x20) == 32) {
                this.aW = value;
            } else if ((status & 0x20000) == 131072) {
                this.aR = value;
            } else if ((status & 0x40) == 64) {
                this.aL = value;
            } else if ((status & 0x80) == 128) {
                this.aM = value;
            } else if ((status & 0x80000) == 524288) {
                this.aJ = value;
                this.aK = value;
            } else if ((status & 0x100) == 256) {
                this.bb = value;
                this.bd = value;
            } else if ((status & 0x200) == 512) {
                this.bc = value;
                this.be = value;
            } else if ((status & 0x400) == 1024) {
                this.av = value;
            } else if ((status & 0x4000) == 16384) {
                this.as = value * 30;
            } else if ((status & 0x8000) == 32768) {
                this.at = value * 20;
            } else if ((status & 0x10000) == 65536) {
                this.au = value;
            } else if ((status & 0x40000) == 262144) {
                this.aG = value;
            }
            ++i;
        }
    }

    public void z() {
        this.ax = 0;
        this.ay = 0;
        this.aV = 0;
        this.aW = 0;
        this.as = 0;
        this.at = 0;
        this.au = 0;
        int status = this.ao;
        if ((status & 1) == 1) {
            this.ax = 1;
        } else if ((status & 2) == 2) {
            this.ay = 1;
        } else if ((status & 0x10) == 16) {
            this.aV = 10;
        } else if ((status & 0x20) == 32) {
            this.aW = 10;
        } else if ((status & 0x4000) == 16384) {
            this.as = 50;
        } else if ((status & 0x8000) == 32768) {
            this.at = 30;
        } else if ((status & 0x10000) == 65536) {
            this.au = 1;
        }
    }

    public void A() {
        this.bu = 0;
        this.bv = 0;
        this.bw = 0;
        this.bx = 0;
        this.bA = 0;
        this.bz = 0;
        this.by = 0;
        this.bB = 0;
        this.bC = 0;
        this.bD = 0;
        this.bE = 0;
        this.bF = 0;
        this.bG = 0;
        this.bH = 0;
        this.bl = false;
        this.bm = false;
        this.bn = false;
        int i = 1;
        while (i <= 4) {
            int status = 0;
            if (i == 1) {
                status = this.ao;
            } else if (i == 2) {
                status = this.ap;
            } else if (i == 3) {
                status = this.aq;
            } else if (i == 4) {
                status = this.ar;
            }
            if (status != 0) {
                if ((status & 1) == 1) {
                    this.bu = 5;
                } else if ((status & 2) == 2) {
                    this.bv = 5;
                } else if ((status & 4) == 4) {
                    this.bw = 10;
                } else if ((status & 8) == 8) {
                    this.bx = 10;
                    this.bA = 10;
                    this.bz = 10;
                    this.by = 10;
                } else if ((status & 0x10) == 16) {
                    this.bB = 10;
                } else if ((status & 0x20) == 32) {
                    this.bC = 10;
                } else if ((status & 0x40) == 64) {
                    this.bD = 5;
                } else if ((status & 0x80) == 128) {
                    this.aM = 5;
                } else if ((status & 0x100) == 256) {
                    this.bF = 1;
                } else if ((status & 0x200) == 512) {
                    this.bG = 10;
                } else if ((status & 0x400) == 1024) {
                    this.bH = 5;
                } else if ((status & 0x800) == 2048) {
                    this.bl = true;
                } else if ((status & 0x1000) == 4096) {
                    this.bm = true;
                } else if ((status & 0x2000) == 8192) {
                    this.bn = true;
                }
            }
            ++i;
        }
    }

    private String aM(int field) {
        String result = "";
        int status = 0;
        if (field == 1) {
            status = this.ao;
        } else if (field == 2) {
            status = this.ap;
        } else if (field == 3) {
            status = this.aq;
        } else if (field == 4) {
            status = this.ar;
        }
        String fieldName = "$" + (21742 + field) + ":";
        result = String.valueOf(result) + fieldName;
        if (status == 0) {
            return null;
        }
        if ((status & 1) == 1) {
            result = String.valueOf(result) + "$7374 +5";
        } else if ((status & 2) == 2) {
            result = String.valueOf(result) + "$7375 +5";
        } else if ((status & 4) == 4) {
            result = String.valueOf(result) + "$1150 +10";
        } else if ((status & 8) == 8) {
            result = String.valueOf(result) + "$1060 +10,$1062 +10,$1059 +10,$1061 +10";
        } else if ((status & 0x10) == 16) {
            result = String.valueOf(result) + "$3434 +10";
        } else if ((status & 0x20) == 32) {
            result = String.valueOf(result) + "$3433 +10";
        } else if ((status & 0x40) == 64) {
            result = String.valueOf(result) + "$5533 +5";
        } else if ((status & 0x80) == 128) {
            result = String.valueOf(result) + "$5529 +5";
        } else if ((status & 0x100) == 256) {
            result = String.valueOf(result) + "$15766 +10";
        } else if ((status & 0x200) == 512) {
            result = String.valueOf(result) + "$21756 +10";
        } else if ((status & 0x400) == 1024) {
            result = String.valueOf(result) + "$1139 +5";
        } else if ((status & 0x800) == 2048) {
            result = String.valueOf(result) + "$1140";
        } else if ((status & 0x1000) == 4096) {
            result = String.valueOf(result) + "$15484";
        } else if ((status & 0x2000) == 8192) {
            result = String.valueOf(result) + "$21755";
        }
        return result;
    }

    public boolean B() {
        return this.a().ax();
    }

    @Override
    public void c(L1PcInstance player) {
    }

    public boolean C() {
        return this.P;
    }

    public void a(boolean identified) {
        this.P = identified;
    }

    public boolean D() {
        return this.Q;
    }

    public void b(boolean equipped) {
        this.Q = equipped;
    }

    public int E() {
        return this.R;
    }

    public void e(int count) {
        this.R = count;
    }

    public int F() {
        return this.S;
    }

    public void f(int bless) {
        this.S = bless;
    }

    public int G() {
        return this.T;
    }

    public int H() {
        return this.U;
    }

    public int I() {
        return this.V;
    }

    public void g(int chargeCount) {
        this.V = chargeCount;
    }

    public Timestamp J() {
        return this.W;
    }

    public void a(Timestamp lastUsed) {
        this.W = lastUsed;
    }

    public int K() {
        return this.X;
    }

    public void h(int attrEnchantKind) {
        this.X = attrEnchantKind;
    }

    public int L() {
        return this.Y;
    }

    public void i(int attrEnchantLevel) {
        this.Y = attrEnchantLevel;
    }

    public int M() {
        return this.Z;
    }

    public void j(int tempValue) {
        this.Z = tempValue;
    }

    public int N() {
        return this.ab;
    }

    public int O() {
        return this.ac;
    }

    public int P() {
        return this.ad;
    }

    public int Q() {
        return this.ae;
    }

    public int R() {
        return this.af;
    }

    public int S() {
        return this.ai;
    }

    public boolean T() {
        return this.ak;
    }

    public void c(boolean nowLighting) {
        this.ak = nowLighting;
    }

    public boolean U() {
        return this.al;
    }

    public void d(boolean nowDollUsed) {
        this.al = nowDollUsed;
    }

    public int V() {
        return this.am;
    }

    public void k(int equipIndex) {
        this.am = equipIndex;
    }

    public boolean W() {
        return this.an;
    }

    public void e(boolean showSetDesc) {
        this.an = showSetDesc;
    }

    public int X() {
        return this.ao;
    }

    public void l(int superEnchantField_1) {
        this.ao = superEnchantField_1;
    }

    public int Y() {
        return this.ap;
    }

    public void m(int superEnchantField_2) {
        this.ap = superEnchantField_2;
    }

    public int Z() {
        return this.aq;
    }

    public void n(int superEnchantField_3) {
        this.aq = superEnchantField_3;
    }

    public int aa() {
        return this.ar;
    }

    public void o(int superEnchantField_4) {
        this.ar = superEnchantField_4;
    }

    public int ab() {
        return this.as;
    }

    public void p(int hpByEnchant) {
        this.as = hpByEnchant;
    }

    public int ac() {
        return this.at;
    }

    public void q(int mpByEnchant) {
        this.at = mpByEnchant;
    }

    public int ad() {
        return this.au;
    }

    public void r(int acByEnchant) {
        this.au = acByEnchant;
    }

    public int ae() {
        return this.av;
    }

    public void s(int spByEnchant) {
        this.av = spByEnchant;
    }

    public int af() {
        return this.aw;
    }

    public void t(int mrByEnchant) {
        this.aw = mrByEnchant;
    }

    public int ag() {
        return this.ax;
    }

    public void u(int hprByEnchant) {
        this.ax = hprByEnchant;
    }

    public int ah() {
        return this.ay;
    }

    public void v(int mprByEnchant) {
        this.ay = mprByEnchant;
    }

    public int ai() {
        return this.az;
    }

    public void w(int strByEnchant) {
        this.az = strByEnchant;
    }

    public int aj() {
        return this.aA;
    }

    public void x(int dexByEnchant) {
        this.aA = dexByEnchant;
    }

    public int ak() {
        return this.aB;
    }

    public void y(int conByEnchant) {
        this.aB = conByEnchant;
    }

    public int al() {
        return this.aC;
    }

    public void z(int intByEnchant) {
        this.aC = intByEnchant;
    }

    public int am() {
        return this.aD;
    }

    public void A(int wisByEnchant) {
        this.aD = wisByEnchant;
    }

    public int an() {
        return this.aE;
    }

    public void B(int chaByEnchant) {
        this.aE = chaByEnchant;
    }

    public int ao() {
        return this.aF;
    }

    public void C(int healPotionRegeneration) {
        this.aF = healPotionRegeneration;
    }

    public int ap() {
        return this.aG;
    }

    public void D(int damageReductionByEnchant) {
        this.aG = damageReductionByEnchant;
    }

    public int aq() {
        return this.aH;
    }

    public void E(int antiDamageReductionByEnchant) {
        this.aH = antiDamageReductionByEnchant;
    }

    public int ar() {
        return this.aI;
    }

    public void F(int magicHitByEnchant) {
        this.aI = magicHitByEnchant;
    }

    public int as() {
        return this.aJ;
    }

    public void G(int hitModifierByEnchant) {
        this.aJ = hitModifierByEnchant;
    }

    public int at() {
        return this.aK;
    }

    public void H(int bowHitModifierByEnchant) {
        this.aK = bowHitModifierByEnchant;
    }

    public int au() {
        return this.aL;
    }

    public void I(int dmgModifierByEnchant) {
        this.aL = dmgModifierByEnchant;
    }

    public int av() {
        return this.aM;
    }

    public void J(int bowDmgModifierByEnchant) {
        this.aM = bowDmgModifierByEnchant;
    }

    public int aw() {
        return this.aN;
    }

    public void K(int defenseWaterByEnchant) {
        this.aN = defenseWaterByEnchant;
    }

    public int ax() {
        return this.aO;
    }

    public void L(int defenseWindByEnchant) {
        this.aO = defenseWindByEnchant;
    }

    public int ay() {
        return this.aP;
    }

    public void M(int defenseFireByEnchant) {
        this.aP = defenseFireByEnchant;
    }

    public int az() {
        return this.aQ;
    }

    public void N(int defenseEarthByEnchant) {
        this.aQ = defenseEarthByEnchant;
    }

    public int aA() {
        return this.aR;
    }

    public void O(int registStoneByEnchant) {
        this.aR = registStoneByEnchant;
    }

    public int aB() {
        return this.aS;
    }

    public void P(int registSleepByEnchant) {
        this.aS = registSleepByEnchant;
    }

    public int aC() {
        return this.aT;
    }

    public void Q(int registFreezeByEnchant) {
        this.aT = registFreezeByEnchant;
    }

    public int aD() {
        return this.aU;
    }

    public void R(int registBlindByEnchant) {
        this.aU = registBlindByEnchant;
    }

    public int aE() {
        return this.aV;
    }

    public void S(int registSustainByEnchant) {
        this.aV = registSustainByEnchant;
    }

    public int aF() {
        return this.aW;
    }

    public void T(int registStunByEnchant) {
        this.aW = registStunByEnchant;
    }

    public int aG() {
        return this.aX;
    }

    public void U(int registFearByEnchant) {
        this.aX = registFearByEnchant;
    }

    public int aH() {
        return this.aY;
    }

    public void V(int stunLevelByEnchant) {
        this.aY = stunLevelByEnchant;
    }

    public int aI() {
        return this.aZ;
    }

    public void W(int fearLevelByEnchant) {
        this.aZ = fearLevelByEnchant;
    }

    public int aJ() {
        return this.ba;
    }

    public void X(int breakLevelByEnchant) {
        this.ba = breakLevelByEnchant;
    }

    public int aK() {
        return this.bb;
    }

    public void Y(int dodgeByEnchant) {
        this.bb = dodgeByEnchant;
    }

    public int aL() {
        return this.bc;
    }

    public void Z(int erByEnchant) {
        this.bc = erByEnchant;
    }

    public int aM() {
        return this.bd;
    }

    public void aa(int pvpDamageByEnchant) {
        this.bd = pvpDamageByEnchant;
    }

    public int aN() {
        return this.be;
    }

    public void ab(int pvpDamageReductionByEnchant) {
        this.be = pvpDamageReductionByEnchant;
    }

    public int aO() {
        return this.bf;
    }

    public void ac(int rndDamageReductionByEnchant) {
        this.bf = rndDamageReductionByEnchant;
    }

    public int aP() {
        return this.bg;
    }

    public void ad(int criticalByEnchant) {
        this.bg = criticalByEnchant;
    }

    public int aQ() {
        return this.bh;
    }

    public void ae(int bowCriticalByEnchant) {
        this.bh = bowCriticalByEnchant;
    }

    public int aR() {
        return this.bi;
    }

    public void af(int magicCriticalByEnchant) {
        this.bi = magicCriticalByEnchant;
    }

    public int aS() {
        return this.bj;
    }

    public void ag(int expByEnchant) {
        this.bj = expByEnchant;
    }

    public int aT() {
        return this.bk;
    }

    public void ah(int weightReductionByEnchant) {
        this.bk = weightReductionByEnchant;
    }

    public boolean aU() {
        return this.bl;
    }

    public void f(boolean isEffectByEnchant_1) {
        this.bl = isEffectByEnchant_1;
    }

    public boolean aV() {
        return this.bm;
    }

    public boolean aW() {
        return this.bn;
    }

    public boolean aX() {
        return this.bo;
    }

    public int aY() {
        return this.bp;
    }

    public void ai(int titanHpByEnchant) {
        this.bp = titanHpByEnchant;
    }

    public int aZ() {
        return this.bq;
    }

    public void aj(int coldAttackByEnchant) {
        this.bq = coldAttackByEnchant;
    }

    public int ba() {
        return this.br;
    }

    public void ak(int foeSlayerDmgByEnchant) {
        this.br = foeSlayerDmgByEnchant;
    }

    public Timestamp bb() {
        return this.bt;
    }

    public void b(Timestamp limitTime) {
        this.bt = limitTime;
    }

    public int bc() {
        return this.bu;
    }

    public void al(int hprByEnchantSuper) {
        this.bu = hprByEnchantSuper;
    }

    public int bd() {
        return this.bv;
    }

    public void am(int mprByEnchantSuper) {
        this.bv = mprByEnchantSuper;
    }

    public int be() {
        return this.bw;
    }

    public void an(int mrByEnchantSuper) {
        this.bw = mrByEnchantSuper;
    }

    public int bf() {
        return this.bx;
    }

    public void ao(int defenseWaterByEnchantSuper) {
        this.bx = defenseWaterByEnchantSuper;
    }

    public int bg() {
        return this.by;
    }

    public void ap(int defenseWindByEnchantSuper) {
        this.by = defenseWindByEnchantSuper;
    }

    public int bh() {
        return this.bz;
    }

    public void aq(int defenseFireByEnchantSuper) {
        this.bz = defenseFireByEnchantSuper;
    }

    public int bi() {
        return this.bA;
    }

    public void ar(int defenseEarthByEnchantSuper) {
        this.bA = defenseEarthByEnchantSuper;
    }

    public int bj() {
        return this.bB;
    }

    public void as(int registSustainByEnchantSuper) {
        this.bB = registSustainByEnchantSuper;
    }

    public int bk() {
        return this.bC;
    }

    public void at(int registStunByEnchantSuper) {
        this.bC = registStunByEnchantSuper;
    }

    public int bl() {
        return this.bD;
    }

    public void au(int dmgModifierByEnchantSuper) {
        this.bD = dmgModifierByEnchantSuper;
    }

    public int bm() {
        return this.bE;
    }

    public void av(int bowDmgModifierByEnchantSuper) {
        this.bE = bowDmgModifierByEnchantSuper;
    }

    public int bn() {
        return this.bF;
    }

    public void aw(int dodgeByEnchantSuper) {
        this.bF = dodgeByEnchantSuper;
    }

    public int bo() {
        return this.bG;
    }

    public void ax(int erByEnchantSuper) {
        this.bG = erByEnchantSuper;
    }

    public int bp() {
        return this.bH;
    }

    public void ay(int spByEnchantSuper) {
        this.bH = spByEnchantSuper;
    }

    public String bq() {
        return this.bI;
    }

    public void a(String nameidByDoll) {
        this.bI = nameidByDoll;
    }

    public int br() {
        return this.bJ;
    }

    public void az(int currentGfxidByDoll) {
        this.bJ = currentGfxidByDoll;
    }

    public int bs() {
        return this.bK;
    }

    public void aA(int dmgChanceByDoll) {
        this.bK = dmgChanceByDoll;
    }

    public int bt() {
        return this.bL;
    }

    public void aB(int evasionChanceByDoll) {
        this.bL = evasionChanceByDoll;
    }

    public int bu() {
        return this.bM;
    }

    public void aC(int poisonChanceByDoll) {
        this.bM = poisonChanceByDoll;
    }

    public int bv() {
        return this.bN;
    }

    public void aD(int slowChanceByDoll) {
        this.bN = slowChanceByDoll;
    }

    public int bw() {
        return this.bO;
    }

    public void aE(int curseChanceByDoll) {
        this.bO = curseChanceByDoll;
    }

    public int bx() {
        return this.bP;
    }

    public void aF(int damageReductionChanceByDoll) {
        this.bP = damageReductionChanceByDoll;
    }

    public int by() {
        return this.bQ;
    }

    public void aG(int weightReductionByDoll) {
        this.bQ = weightReductionByDoll;
    }

    public int bz() {
        return this.bR;
    }

    public void aH(int makingItemIdByDoll) {
        this.bR = makingItemIdByDoll;
    }

    public boolean bA() {
        return this.bS;
    }

    public void g(boolean isSpeedUpByDoll) {
        this.bS = isSpeedUpByDoll;
    }

    public boolean bB() {
        return this.bT;
    }

    public void h(boolean isBreathWaterByDoll) {
        this.bT = isBreathWaterByDoll;
    }

    public int bC() {
        return this.bU;
    }

    public void aI(int mprByDoll) {
        this.bU = mprByDoll;
    }

    public int bD() {
        return this.bV;
    }

    public void aJ(int hprByDoll) {
        this.bV = hprByDoll;
    }

    public int bE() {
        return this.bW;
    }

    public void aK(int expByDoll) {
        this.bW = expByDoll;
    }

    public String bF() {
        return this.bX;
    }

    public void b(String skillName) {
        this.bX = skillName;
    }

    private class L1R_a
    extends TimerTask {
        private final L1PcInstance b;

        private L1R_a(L1PcInstance pc) {
            this.b = pc;
        }

        @Override
        public void run() {
            if (this.b == null || this.b.bE() == 0) {
                L1ItemInstance.this.aj.cancel(true);
                return;
            }
            if (L1ItemInstance.this.M() > 1) {
                L1ItemInstance.this.j(L1ItemInstance.this.M() - 1);
                this.b.j().b(L1ItemInstance.this);
            } else {
                this.b.j().b(L1ItemInstance.this, 1);
                L1ItemInstance.this.aj.cancel(true);
            }
        }
    }

    private class L1R_b
    implements Runnable {
        private L1R_b() {
        }

        @Override
        public void run() {
            try {
                L1ItemInstance.this.f(L1ItemInstance.this.ah);
            }
            catch (Exception e) {
                O.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_c
    extends TimerTask {
        private L1R_c() {
        }

        @Override
        public void run() {
            L1ItemInstance.this.ai = 0;
        }
    }
}
