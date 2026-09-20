/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.al.L1HpBar;
import l1r.al.L1Speed;
import l1r.am.MonsterListReader;
import l1r.ao.AccountTable;
import l1r.ao.CharBuffTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterMobsTable;
import l1r.ao.CharacterMobsWeekTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.ExpTable;
import l1r.ao.ItemTable;
import l1r.ao.QuestNewTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1FollowerInstance;
import l1r.ap.L1GuardInstance;
import l1r.ap.L1GuardianInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1ChatParty;
import l1r.aq.L1Clan;
import l1r.aq.L1EquipmentSlot;
import l1r.aq.L1ExcludingList;
import l1r.aq.L1Getback;
import l1r.aq.L1Karma;
import l1r.aq.L1Magic;
import l1r.aq.L1Master;
import l1r.aq.L1Object;
import l1r.aq.L1Party;
import l1r.aq.L1PinkName;
import l1r.aq.L1Quest;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1Trade;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.ar.L1ClassFeature;
import l1r.as.L1CastleWar;
import l1r.at.L1GameTimeClock;
import l1r.au.L1AccountInventory;
import l1r.au.L1CharInventory;
import l1r.au.L1ElvenInventory;
import l1r.au.L1Inventory;
import l1r.au.L1PcInventory;
import l1r.ay.L1PcAtkMonitor;
import l1r.ay.L1PcAutoUpdate;
import l1r.ay.L1PcEinMonitor;
import l1r.ay.L1PcExpMonitor;
import l1r.ay.L1PcInvisDelay;
import l1r.ba.HellTimer;
import l1r.bc.FishingTimer;
import l1r.bc.HpRegenerationTimer;
import l1r.bc.MpRegenerationTimer;
import l1r.be.S_ChangeLevel;
import l1r.be.S_ChangeName;
import l1r.be.S_CharEvent;
import l1r.be.S_CharTitle;
import l1r.be.S_Disconnect;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DoActionShop;
import l1r.be.S_EquipmentSlot;
import l1r.be.S_Extended;
import l1r.be.S_Fight;
import l1r.be.S_Fishing;
import l1r.be.S_GameTime;
import l1r.be.S_HPMeter;
import l1r.be.S_HPUpdate;
import l1r.be.S_Invis;
import l1r.be.S_Lawful;
import l1r.be.S_MPUpdate;
import l1r.be.S_Message_YN;
import l1r.be.S_OtherCharPacks;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_Poison;
import l1r.be.S_RedMessage;
import l1r.be.S_RemoveObject;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SummonPack;
import l1r.be.S_TrueTarget;
import l1r.be.ServerBasePacket;
import l1r.bf.S_044;
import l1r.bh.L1BookMark;
import l1r.bh.L1PrivateShopBuyList;
import l1r.bh.L1PrivateShopSellList;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcStat;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class L1PcInstance
extends L1Character {
    private static final Logger s = Logger.getLogger(L1PcInstance.class.getName());
    public static final int a = 61;
    public static final int b = 48;
    public static final int c = 138;
    public static final int d = 37;
    public static final int e = 734;
    public static final int f = 1186;
    public static final int g = 2786;
    public static final int h = 2796;
    public static final int i = 0;
    public static final int j = 1;
    public static final int k = 6658;
    public static final int l = 6661;
    public static final int m = 6671;
    public static final int n = 6650;
    public static final int o = 12490;
    public static final int p = 12494;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private int w = 0;
    public static final int q = 2;
    public static final int r = 1;
    private HpRegenerationTimer y;
    private ScheduledFuture<?> z;
    private MpRegenerationTimer A;
    private ScheduledFuture<?> B;
    private ScheduledFuture<?> C;
    private ScheduledFuture<?> D;
    private ScheduledFuture<?> E;
    private ScheduledFuture<?> F;
    private final CopyOnWriteArrayList<Integer> G = new CopyOnWriteArrayList();
    private final L1AccountInventory H;
    private final L1ElvenInventory I;
    private final L1CharInventory J;
    private final L1Inventory K = new L1Inventory();
    private final L1PcInventory L;
    private int M;
    private int N = 0;
    private boolean O = false;
    private int P;
    private L1ClassFeature Q = null;
    private int R = 0;
    private int S = 0;
    private int T = 0;
    private int U = 0;
    private String V = "";
    private int W = 0;
    private Timestamp X;
    private int Y = 0;
    private ClientThread Z;
    private L1Party aa;
    private L1ChatParty ab;
    private int ac;
    private int ad;
    private boolean ae;
    private int af;
    private boolean ag = false;
    private boolean ah = false;
    private boolean ai = false;
    private final CopyOnWriteArrayList<L1PrivateShopSellList> aj = new CopyOnWriteArrayList();
    private final ArrayList<L1PrivateShopBuyList> ak = new ArrayList();
    private byte[] al;
    private boolean am = false;
    private boolean an = false;
    private int ao = 0;
    private double ap = 0.0;
    private ScheduledFuture<?> aq;
    private final CopyOnWriteArrayList<L1BookMark> ar = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<L1ItemInstance> as = new CopyOnWriteArrayList();
    private final L1Quest at;
    private String au;
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
    private int aO;
    private int aP;
    private int aQ;
    private int aR = 0;
    private int aS = 0;
    private int aT = 0;
    private int aU = 0;
    private int aV = 0;
    private int aW = 0;
    private int aX = 0;
    private int aY = 0;
    private int aZ;
    private int ba;
    private boolean bb = false;
    private final L1EquipmentSlot bc;
    private long bd;
    private int be = 0;
    private int bf = 0;
    private static final long bg = 3000L;
    private ScheduledFuture<?> bh;
    private boolean bi = false;
    private final L1Karma bj = new L1Karma();
    private Timestamp bk;
    private Timestamp bl;
    private Timestamp bm;
    private Timestamp bn;
    private int bo = 0;
    private int bp = 0;
    private int bq = 0;
    private int br = 0;
    private int bs = 0;
    private int bt = 0;
    private int bu = 0;
    private int bv = 0;
    private int bw = 0;
    private boolean bx = false;
    private boolean by = true;
    private int bz = 10;
    private final L1ExcludingList bA = new L1ExcludingList();
    private final L1SpeedChecker bB = new L1SpeedChecker(this);
    private int bC = 0;
    private int bD = 0;
    private int bE = 0;
    private int bF = 0;
    private int bG;
    private boolean bH = true;
    private boolean bI = true;
    private boolean bJ = true;
    private boolean bK = true;
    private boolean bL = true;
    private int bM;
    private boolean bN = false;
    private int bO;
    private int bP;
    private byte bQ = 0;
    private long bR = 0L;
    private int bS;
    private int bT;
    private boolean bU = false;
    private int bV = 1;
    private int bW = 1;
    private boolean bX = false;
    private int bY;
    private int[] bZ = new int[3];
    private Timestamp ca;
    private int cb;
    private int cc = 0;
    private int cd = 0;
    private int ce = 0;
    private int cf = 0;
    private int cg = 0;
    private int ch = 0;
    private int ci = 0;
    private boolean cj = false;
    private int ck = 60;
    private int cl = 60;
    private int cm = 0;
    private boolean cn = false;
    private boolean co = false;
    private boolean cp = false;
    private FishingTimer cq = null;
    private int cr = 3;
    private int cs = 0;
    private boolean ct = false;
    private int cu = 21600;
    private int cv = 7200;
    private int cw = 21600;
    private int cx = 21600;
    private int cy = 7200;
    private int cz = 14400;
    private int cA = 14400;
    private int cB = 0;
    private boolean cC = false;
    private boolean cD = false;
    private boolean cE = false;
    private int cF = 0;
    private boolean cG = false;
    private boolean cH = false;
    private boolean cI = false;
    private int cJ = 0;
    private boolean cK = false;
    private boolean cL = false;
    private boolean cM = false;
    private boolean cN = false;
    private boolean cO = false;
    private boolean cP = false;
    private boolean cQ = false;
    private int cR = 0;
    private int cS = 0;
    private int cT = 0;
    private int cU = 0;
    private boolean cV = false;
    private boolean cW = false;
    private boolean cX = false;
    private boolean cY = false;
    private boolean cZ = false;
    private boolean da = false;
    private boolean db = false;
    private int[] dc = null;
    private int dd = 0;
    private int de = 0;
    private int df = 0;
    private int dg = 0;
    private int dh = 0;
    private int di = 0;
    private int dj = 0;
    private int dk = 0;
    private int dl = 0;
    private int dm = 0;
    private int dn = 0;
    private int cfr_renamed_0 = 0;
    private boolean dp = false;
    private int dq;
    private int dr = 0;
    private int ds = 0;
    private int dt = 0;
    private int[] du = new int[MonsterListReader.a().b()];
    private int[] dv = new int[MonsterListReader.a().b() * 3];
    private final HashMap<Integer, L1QuestNew> dw = new HashMap();
    private ArrayList<Integer> dx = new ArrayList();
    private ArrayList<Integer> dy = new ArrayList();
    private int dz = 0;
    private int dA = 0;
    private int dB = -1;
    private int[][] dC = null;

    public void c(int i) {
        this.u += i;
        this.t = Math.max(0, this.u);
    }

    public void d(int i) {
        this.w += i;
        this.v = Math.max(0, this.w);
    }

    public void e(int state) {
        this.A.a(state);
        this.y.a(state);
    }

    public void a() {
        if (this.z == null) {
            this.y = new HpRegenerationTimer(this);
            this.z = GeneralThreadPool.a().b(this.y, 1000L, 1000L);
        }
    }

    public void b() {
        if (this.z != null) {
            this.z.cancel(true);
            this.z = null;
        }
    }

    public void c() {
        if (this.B == null) {
            this.A = new MpRegenerationTimer(this);
            this.B = GeneralThreadPool.a().b(this.A, 1000L, 1000L);
        }
    }

    public void d() {
        if (this.B != null) {
            this.B.cancel(true);
            this.B = null;
        }
    }

    public void e() {
        this.es();
        this.F = GeneralThreadPool.a().b(new L1PcAutoUpdate(this.fr()), 0L, 200L);
    }

    public void f() {
        this.C = GeneralThreadPool.a().b(new L1PcExpMonitor(this.fr()), 0L, 500L);
        this.D = GeneralThreadPool.a().b(new L1PcEinMonitor(this.fr()), 0L, 1000L);
        this.E = GeneralThreadPool.a().b(new L1PcAtkMonitor(this.fr()), 0L, 300L);
        this.bh = GeneralThreadPool.a().a(new L1R_c(), 0L, 1000L);
    }

    private void fv() {
        if (this.F != null) {
            this.F.cancel(true);
            this.F = null;
        }
        if (this.C != null) {
            this.C.cancel(true);
            this.C = null;
        }
        if (this.D != null) {
            this.D.cancel(true);
            this.D = null;
        }
        if (this.E != null) {
            this.E.cancel(true);
            this.E = null;
        }
        if (this.bh != null) {
            this.bh.cancel(true);
            this.bh = null;
        }
    }

    public void g() {
        int char_level;
        int level = ExpTable.c(this.m());
        int gap = level - (char_level = this.ev());
        if (gap == 0) {
            this.a(new S_OwnCharStatus(this));
            if (this.bB(4076)) {
                int bouns_per;
                int current_per = ExpTable.a(char_level, this.m());
                int n = bouns_per = char_level <= 64 ? 10 : 5;
                if (current_per >= bouns_per) {
                    this.bz(4076);
                }
            }
            return;
        }
        if (gap > 0) {
            this.cJ(gap);
        } else if (gap < 0) {
            this.cK(gap);
        }
        int polyFrameGate = this.cL(level) - this.cL(char_level);
        if (polyFrameGate != 0) {
            this.a(new S_ChangeLevel(this));
            this.b(new S_ChangeLevel(this));
        }
    }

    @Override
    public void b(L1PcInstance receiver) {
        if (this.aA() || this.bN()) {
            return;
        }
        if (this.ff() && !receiver.bB(26003)) {
            return;
        }
        receiver.c((L1Object)this);
        receiver.a(new S_OtherCharPacks(this));
        if (this.ff() && receiver.bB(26003)) {
            receiver.a(new S_OtherCharPacks(this, true));
            receiver.a(new S_TrueTarget(this.fr(), true));
        }
        if (this.aK() != null && this.aK().e().a()) {
            receiver.a(new S_CharEvent(73, this.fr(), this.aK().e().h()));
        }
        if (this.q() && this.aL().d(receiver)) {
            receiver.a(new S_HPMeter(this));
        }
        if (this.aX()) {
            receiver.a(new S_DoActionShop(this.fr(), 70, this.aW()));
        } else if (this.cq()) {
            receiver.a(new S_Fishing(this.fr(), 71, this.cr(), this.cs()));
        }
    }

    private void fw() {
        for (L1Object known : this.eq()) {
            if (known == null || this.fu().e(known.fu())) continue;
            this.d(known);
            this.a(new S_RemoveObject(known));
        }
    }

    public void h() {
        this.fw();
        for (L1Object visible : L1World.a().b((L1Object)this, -1)) {
            L1MonsterInstance npc;
            L1MonsterInstance npc2;
            if (!this.b(visible)) {
                visible.b(this);
            } else if (visible instanceof L1MonsterInstance && (npc2 = (L1MonsterInstance)visible).ac() != 0) {
                npc2.d(this);
            }
            boolean isHpBar = false;
            if (visible instanceof L1MonsterInstance && (npc = (L1MonsterInstance)visible).d((L1Character)this)) {
                isHpBar = true;
            }
            if (!this.bB(26001) && !isHpBar || !L1HpBar.a(visible)) continue;
            this.a(new S_HPMeter((L1Character)visible));
        }
    }

    public void i() {
        if (this.bN() || this.ff() || this.aA()) {
            this.a(new S_Invis(this.fr(), 1));
        }
        if (this.aK() != null && this.aK().e().a()) {
            this.a(new S_CharEvent(73, this.fr(), this.aK().e().h()));
            this.b(new S_CharEvent(73, this.fr(), this.aK().e().h()));
        }
    }

    public void f(int skillid) {
        if (!this.G.contains(skillid)) {
            this.G.add(skillid);
        }
    }

    public void g(int skillid) {
        if (this.G.contains(skillid)) {
            this.G.remove((Object)skillid);
        }
    }

    public boolean h(int skillid) {
        return this.G.contains(skillid);
    }

    public L1PcInstance() {
        this.L = new L1PcInventory(this);
        this.H = new L1AccountInventory(this);
        this.I = new L1ElvenInventory(this);
        this.J = new L1CharInventory(this);
        this.at = new L1Quest(this);
        this.bc = new L1EquipmentSlot(this);
    }

    @Override
    public synchronized void a(int i) {
        if (this.ea() == i) {
            return;
        }
        int currentHp = i;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        this.a(new S_HPUpdate(currentHp, this.ew()));
        if (this.q()) {
            this.aL().f(this);
        }
    }

    @Override
    public synchronized void i_(int i) {
        if (this.eb() == i) {
            return;
        }
        int currentMp = i;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        this.a(new S_MPUpdate(currentMp, this.ex()));
    }

    public L1PcInventory j() {
        return this.L;
    }

    public int k() {
        if (this.v() == null) {
            return 0;
        }
        if (this.w() == 2) {
            return 88;
        }
        return this.v().a().aO();
    }

    public boolean l() {
        return this.N == 200;
    }

    public void i(int i) {
        this.P = i;
        this.Q = L1ClassFeature.a(i);
    }

    @Override
    public synchronized int m() {
        return this.R;
    }

    @Override
    public synchronized void k(int i) {
        this.R = i;
    }

    public void n() {
        this.X = new Timestamp(System.currentTimeMillis());
    }

    public int o() {
        if (this.X != null) {
            SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
            int BornTime = Integer.parseInt(SimpleDate.format(this.X.getTime()));
            return BornTime;
        }
        return 0;
    }

    private void a(List<L1PcInstance> playersArray) {
        for (L1PcInstance player : playersArray) {
            if (!player.b((L1Object)this)) continue;
            player.d(this);
            player.a(new S_RemoveObject(this));
        }
    }

    public void p() {
        try {
            L1Clan clan;
            if (this.eX()) {
                L1World.a().d(this);
                Thread.sleep(2000L);
                int[] loc = L1Getback.a(this);
                this.cG(loc[0]);
                this.cH(loc[1]);
                this.cE(loc[2]);
                this.a(this.ev());
                this.c_(40);
            }
            if (this.aF() != 0 && (clan = ClanTable.a().a(this.aF())) != null) {
                if (clan.o() == this.fr()) {
                    clan.i(0);
                }
                if (clan.b().size() <= 3) {
                    for (L1PcInstance clanMember : clan.b()) {
                        clanMember.bz(4084);
                        clanMember.a(new S_PacketBox(180, 450, 3240, 0));
                    }
                }
            }
            if (this.aO() != 0) {
                L1Trade.b(this);
            }
            if (this.cp() != 0) {
                this.aN(0);
                L1PcInstance fightPc = (L1PcInstance)L1World.a().a(this.cp());
                if (fightPc != null) {
                    fightPc.aN(0);
                    fightPc.a(new S_PacketBox(5, 0, 0));
                }
            }
            if (this.q()) {
                this.aL().b(this);
            }
            if (this.r()) {
                this.aM().c(this);
            }
            for (L1NpcInstance npc : this.ek().values()) {
                if (npc instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance)npc;
                    pet.ax();
                    pet.h();
                    this.ek().remove(pet.fr());
                    pet.aa_();
                    continue;
                }
                if (!(npc instanceof L1SummonInstance)) continue;
                L1SummonInstance summon = (L1SummonInstance)npc;
                for (L1PcInstance visiblePc : L1World.a().f(summon)) {
                    if (visiblePc.fr() == this.fr()) continue;
                    visiblePc.a(new S_SummonPack(summon, visiblePc, false));
                }
            }
            for (L1DollInstance doll : this.el().values()) {
                doll.e();
            }
            for (L1FollowerInstance follower : this.em().values()) {
                follower.V(true);
                follower.a(follower.z(), follower.fs(), follower.ft(), follower.fb(), follower.fp());
                follower.aa_();
            }
            L1BookMark.a(this.ba());
            CharBuffTable.a(this);
            this.ei();
            CharacterMobsTable.a().a(this);
            CharacterMobsWeekTable.a().c(this);
            QuestNewTable.a().c(this);
            CharacterEquipment.a().a(this);
            this.fv();
            this.aC(0);
            this.f(new Timestamp(System.currentTimeMillis()));
            L1Master.a().a(this);
            AccountTable.a().b(this.aK().e(), false);
            this.I();
            this.J();
            L1World.a().d(this);
            L1World.a().b(this);
            this.a(this.er());
            this.a(L1World.a().f(this));
            this.L.g();
            this.H.g();
            this.es();
            this.b();
            this.d();
            this.X(true);
            this.a((ClientThread)null);
        }
        catch (Exception e) {
            s.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public boolean q() {
        return this.aa != null;
    }

    public boolean r() {
        return this.ab != null;
    }

    public void a(ServerBasePacket serverbasepacket) {
        if (this.Z == null || this.bE() == 0) {
            return;
        }
        try {
            if (Config.e) {
                System.out.println(String.valueOf(this.bc()) + "/" + serverbasepacket.b() + "/" + serverbasepacket.a().length);
                System.out.println(LineageUtil.a(serverbasepacket.a()));
            }
            this.Z.a(serverbasepacket);
        }
        catch (Exception e) {
            s.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void c(L1PcInstance attacker) {
        this.a(attacker, 0);
    }

    @Override
    public void a(L1PcInstance attacker, int skillId) {
        if (attacker == null) {
            return;
        }
        if (this.aR()) {
            return;
        }
        if (this.ep() == 1 || attacker.ep() == 1 || this.a(this, attacker, false)) {
            new L1Attack(attacker, this, skillId).c();
            return;
        }
        if (this.ea() > 0 && !this.eX()) {
            boolean isCounterBarrier = false;
            L1Attack attack = new L1Attack(attacker, this, skillId);
            if (attack.a()) {
                if (this.bB(91)) {
                    L1Magic magic = new L1Magic(this, attacker);
                    boolean isProbability = magic.a(91);
                    boolean isShortDistance = attack.f();
                    if (isProbability && isShortDistance) {
                        isCounterBarrier = true;
                    }
                }
                if (this.bB(191) && Random.a(100) < 10) {
                    isCounterBarrier = true;
                }
                int advence = 0;
                double hpRange = 0.4 + (double)this.dH() * 0.01;
                if (this.bB(231) && this.ev() >= 80) {
                    advence = Math.min(5 + this.ev() - 80, 10);
                }
                if (this.bB(606) && (double)this.ea() < (double)this.ew() * hpRange && attack.f() && Random.a(100) < 34 + advence) {
                    isCounterBarrier = true;
                }
                if (this.bB(607) && (double)this.ea() < (double)this.ew() * hpRange && !attack.f() && Random.a(100) < 34 + advence) {
                    isCounterBarrier = true;
                }
                if (!isCounterBarrier) {
                    attacker.a((L1Character)this);
                    attack.b();
                    attack.a((L1Character)attacker, (L1Character)this);
                }
            }
            if (isCounterBarrier) {
                attack.b();
                attack.e();
                attack.g();
            } else {
                attack.c();
                attack.d();
            }
        }
    }

    public boolean a(L1PcInstance pc, L1Character target, boolean isRangeSkillTarget) {
        L1PcInstance targetpc = null;
        if (target instanceof L1PcInstance) {
            targetpc = (L1PcInstance)target;
        } else if (target instanceof L1PetInstance) {
            targetpc = (L1PcInstance)((L1PetInstance)target).M();
        } else if (target instanceof L1SummonInstance) {
            targetpc = (L1PcInstance)((L1SummonInstance)target).M();
        } else if (target instanceof L1MonsterInstance) {
            return false;
        }
        if (targetpc == null) {
            return true;
        }
        for (L1War war : L1World.a().i()) {
            if (pc.aF() == 0 || targetpc.aF() == 0 || !war.c(pc.aG(), targetpc.aG())) continue;
            return false;
        }
        if (target instanceof L1PcInstance) {
            L1PcInstance targetPc = (L1PcInstance)target;
            if (L1CastleWar.a().a((L1Character)pc) && L1CastleWar.a().a((L1Character)targetPc)) {
                return false;
            }
        }
        if (Config.R) {
            if (pc.fu().e(new Point(33080, 33376)) && pc.fu().d() && target.fu().e(new Point(33080, 33376)) && target.fu().d()) {
                return false;
            }
            return pc.fp() < 10500 || pc.fp() > 10502;
        }
        return !(isRangeSkillTarget ? this.fq().c(this.fu()) : !this.fq().b(this.fu()));
    }

    public void a(L1Character target) {
        for (L1NpcInstance pet : this.ek().values()) {
            if (pet instanceof L1PetInstance) {
                L1PetInstance pets = (L1PetInstance)pet;
                pets.g(target);
                continue;
            }
            if (!(pet instanceof L1SummonInstance)) continue;
            L1SummonInstance summon = (L1SummonInstance)pet;
            summon.g(target);
        }
    }

    public void s() {
        this.bz(60);
        this.bz(97);
    }

    public void a(L1Character attacker, int mpDamage) {
        int newMp;
        if (mpDamage <= 0 || this.eX()) {
            return;
        }
        this.s();
        if (attacker instanceof L1PcInstance) {
            L1PcInstance atk_pc = (L1PcInstance)attacker;
            L1PinkName.a(this, atk_pc);
        }
        if ((newMp = this.eb() - mpDamage) > this.ex()) {
            newMp = this.ex();
        } else if (newMp <= 0) {
            newMp = 0;
        }
        this.i_(newMp);
    }

    public void a(L1Character attacker, double damage, boolean isMagicDamage) {
        int newHp;
        if (this.ea() <= 0) {
            if (!this.eX()) {
                this.b(attacker);
            }
            return;
        }
        if (!this.b((L1Object)attacker) && attacker.fp() == this.fp() && !(attacker instanceof L1EffectInstance)) {
            attacker.b(this);
        }
        if (isMagicDamage) {
            double nowTime = System.currentTimeMillis();
            double interval = (20.0 - (nowTime - this.ap) / 100.0) % 20.0;
            if (damage > 0.0) {
                if (interval > 0.0) {
                    damage *= 1.0 - interval / 30.0;
                }
                if (damage < 1.0) {
                    damage = 0.0;
                }
                this.ap = nowTime;
            }
        }
        if (damage > 0.0) {
            this.s();
            if (attacker instanceof L1PcInstance) {
                L1PinkName.a(this, attacker);
            }
            this.bz(66);
            this.bz(153);
        }
        if (this.cG) {
            damage *= 1.5;
        }
        if (this.bB(219)) {
            damage *= 1.2;
        }
        if ((attacker instanceof L1PetInstance || attacker instanceof L1SummonInstance) && (this.ep() == 1 || attacker.ep() == 1 || this.a(this, attacker, false))) {
            damage = 0.0;
        }
        if ((newHp = this.ea() - (int)damage) <= 10 && this.bB(135)) {
            int mp = this.eb();
            if (damage <= (double)mp) {
                this.i_(mp - (int)damage);
                return;
            }
            this.i_(0);
            damage -= (double)mp;
        }
        if (this.cI && damage > 20.0 && Random.a(100) < 5) {
            this.a(new S_SkillSound(this.fr(), 2188));
            this.b(new S_SkillSound(this.fr(), 2188));
            int[] addMp = new int[]{15, 10, 20, 20, 20, 20, 20, 10};
            this.i_(this.eb() + addMp[this.ay()]);
        }
        if (this.cH && damage > 20.0 && Random.a(100) < 5) {
            this.a(new S_SkillSound(this.fr(), 2187));
            this.b(new S_SkillSound(this.fr(), 2187));
            int healHP = this.ew() / 10;
            if (this.bB(173)) {
                healHP /= 2;
            }
            if (this.bB(170)) {
                healHP *= 2;
            }
            newHp += healHP;
        }
        if (newHp > this.ew()) {
            newHp = this.ew();
        }
        if (newHp <= 0) {
            if (this.l()) {
                this.a(this.ew());
            } else {
                this.b(attacker);
            }
            return;
        }
        this.a(newHp);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(L1Character lastAttacker) {
        L1PcInstance l1PcInstance = this;
        synchronized (l1PcInstance) {
            if (this.eX()) {
                return;
            }
            this.X(true);
            this.cq(8);
        }
        if (this.aO() != 0) {
            L1Trade.b(this);
        }
        GeneralThreadPool.a().b(new L1R_b(lastAttacker));
    }

    public void t() {
        if (this.aq != null) {
            this.aq.cancel(true);
            this.aq = null;
        }
    }

    private void cI(int count) {
        int i = 0;
        while (i < count) {
            L1ItemInstance item = this.j().n();
            if (item != null) {
                this.j().a(item, item.d() ? item.E() : 1, (L1Inventory)L1World.a().a(this.fs(), this.ft(), this.fp()));
                this.a(new S_ServerMessage(638, item.s()));
            }
            ++i;
        }
    }

    private boolean c(L1Character lastAttacker) {
        if (this.aF() == 0 || Config.Y) {
            return false;
        }
        L1Clan clan = ClanTable.a().a(this.aF());
        L1PcInstance attacker = null;
        if (lastAttacker instanceof L1PcInstance) {
            attacker = (L1PcInstance)lastAttacker;
        } else if (lastAttacker instanceof L1PetInstance) {
            attacker = (L1PcInstance)((L1PetInstance)lastAttacker).M();
        } else if (lastAttacker instanceof L1SummonInstance) {
            attacker = (L1PcInstance)((L1SummonInstance)lastAttacker).M();
        } else {
            return false;
        }
        L1War clanWar = L1World.a().c(clan.f());
        if (clanWar == null || clanWar.c() != 2) {
            return false;
        }
        if (attacker == null || attacker.aF() == 0) {
            return false;
        }
        if (!clanWar.c(this.aG(), attacker.aG())) {
            return false;
        }
        if (this.fr() == clan.k()) {
            for (L1Clan enemyClan : clanWar.c(this.aG())) {
                clanWar.b(this.aG(), enemyClan.f());
            }
        }
        return true;
    }

    public void a(boolean isFull) {
        int oldLevel = this.ev();
        int needExp = ExpTable.b(oldLevel);
        int rate = isFull ? 2 : 1;
        int exp = 0;
        if (oldLevel < 45) {
            exp = (int)((double)needExp * 0.05 * (double)rate);
        } else if (oldLevel == 45) {
            exp = (int)((double)needExp * 0.045 * (double)rate);
        } else if (oldLevel == 46) {
            exp = (int)((double)needExp * 0.04 * (double)rate);
        } else if (oldLevel == 47) {
            exp = (int)((double)needExp * 0.035 * (double)rate);
        } else if (oldLevel == 48) {
            exp = (int)((double)needExp * 0.03 * (double)rate);
        } else if (oldLevel >= 49) {
            exp = (int)((double)needExp * 0.025 * (double)rate);
        }
        if (exp == 0) {
            return;
        }
        this.x(exp);
    }

    private void fx() {
        int oldLevel = this.ev();
        int needExp = ExpTable.b(oldLevel);
        int exp = 0;
        if (oldLevel >= 1 && oldLevel < 11) {
            exp = 0;
        } else if (oldLevel >= 11 && oldLevel < 45) {
            exp = (int)((double)needExp * 0.1);
        } else if (oldLevel == 45) {
            exp = (int)((double)needExp * 0.09);
        } else if (oldLevel == 46) {
            exp = (int)((double)needExp * 0.08);
        } else if (oldLevel == 47) {
            exp = (int)((double)needExp * 0.07);
        } else if (oldLevel == 48) {
            exp = (int)((double)needExp * 0.06);
        } else if (oldLevel >= 49) {
            exp = (int)((double)needExp * 0.05);
        }
        if (exp == 0) {
            return;
        }
        if (this.ca() >= 0) {
            this.aH(this.ca() + 1);
        }
        this.x(-exp);
    }

    public int u() {
        if (this.bB(174) || this.aC() == null) {
            return 0;
        }
        int er = this.aC().e(this.ev()) + CalcStat.b(this.eB());
        if (this.bB(160)) {
            er += 5;
        }
        if (this.bB(111)) {
            er += 12;
        }
        if (this.bB(90)) {
            er += 15;
        }
        if (this.bB(4062)) {
            ++er;
        }
        if (this.bB(4066)) {
            er += 3;
        }
        if (this.bB(4085)) {
            er += 5;
        }
        return er += this.cF;
    }

    public L1BookMark a(String name) {
        for (L1BookMark element : this.ar) {
            if (!element.c().equalsIgnoreCase(name)) continue;
            return element;
        }
        return null;
    }

    public L1BookMark l(int id) {
        for (L1BookMark element : this.ar) {
            if (element.a() != id) continue;
            return element;
        }
        return null;
    }

    public L1BookMark a(int x, int y) {
        if (x == 0 || y == 0) {
            return null;
        }
        for (L1BookMark element : this.ar) {
            if (element.d() != x || element.e() != y) continue;
            return element;
        }
        return null;
    }

    public L1ItemInstance v() {
        if (this.as.isEmpty()) {
            return null;
        }
        return this.as.get(Random.a(this.as.size()));
    }

    public boolean a(L1ItemInstance weapon) {
        return this.as.contains(weapon);
    }

    public int w() {
        return this.as.size();
    }

    public void a(CopyOnWriteArrayList<L1ItemInstance> weapon) {
        this.as = weapon;
    }

    public boolean x() {
        return this.aB() == 0 || this.aB() == 1;
    }

    public boolean z() {
        return this.aB() == 61 || this.aB() == 48;
    }

    public boolean A() {
        return this.aB() == 138 || this.aB() == 37;
    }

    public boolean B() {
        return this.aB() == 734 || this.aB() == 1186;
    }

    public boolean C() {
        return this.aB() == 2786 || this.aB() == 2796;
    }

    public boolean D() {
        return this.aB() == 6658 || this.aB() == 6661;
    }

    public boolean E() {
        return this.aB() == 6671 || this.aB() == 6650;
    }

    public boolean F() {
        return this.aB() == 12490 || this.aB() == 12494;
    }

    public void m(int i) {
        if ((i += this.av) >= Short.MAX_VALUE) {
            i = Short.MAX_VALUE;
        } else if (i < 1) {
            i = 1;
        }
        this.bH(i - this.av);
        this.av = i;
    }

    public void n(int i) {
        if ((i += this.aw) >= Short.MAX_VALUE) {
            i = Short.MAX_VALUE;
        } else if (i < 0) {
            i = 0;
        }
        this.bJ(i - this.aw);
        this.aw = i;
    }

    public void o(int i) {
        if ((i += this.ax) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bN(i - this.ax);
        this.ax = i;
    }

    public void p(int i) {
        if ((i += this.ay) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bP(i - this.ay);
        this.ay = i;
    }

    public void q(int i) {
        if ((i += this.az) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bR(i - this.az);
        this.az = i;
    }

    public void r(int i) {
        if ((i += this.aA) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bT(i - this.aA);
        this.aA = i;
    }

    public void s(int i) {
        if ((i += this.aB) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bV(i - this.aB);
        this.aB = i;
    }

    public void t(int i) {
        if ((i += this.aC) >= 127) {
            i = 127;
        } else if (i < 1) {
            i = 1;
        }
        this.bX(i - this.aC);
        this.aC = i;
    }

    public synchronized void u(int i) {
        this.aY += i;
    }

    @Override
    public void c_(int i) {
        super.c_(i);
        this.G();
    }

    public void G() {
        if (this.fj() >= 225) {
            this.bd = System.currentTimeMillis() / 1000L;
        }
        this.a(new S_Extended(this.fj() >= 225));
    }

    public void H() {
        ++this.be;
        this.a(new S_OwnCharStatus(this));
    }

    public static L1PcInstance b(String charName) {
        L1PcInstance result = null;
        try {
            result = CharacterTable.a().b(charName);
        }
        catch (Exception e) {
            s.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return result;
    }

    public void I() {
        if (this.bN()) {
            return;
        }
        if (this.cv()) {
            return;
        }
        try {
            CharacterTable.a().b(this);
        }
        catch (Exception e) {
            s.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void J() {
        for (L1ItemInstance item : this.j().d()) {
            this.j().i(item);
        }
    }

    public double K() {
        int weightReductionByMagic = 0;
        if (this.bB(14) || this.bB(218)) {
            weightReductionByMagic = 180;
        } else if (this.bB(134)) {
            weightReductionByMagic = 300;
        }
        double weightReduction = 1.0 + (double)(this.bS() / 100);
        double maxWeight = 1000.0 + Math.floor((this.ez() + this.eA()) / 2) * 100.0;
        maxWeight *= weightReduction;
        maxWeight += (double)(weightReductionByMagic + this.bT());
        return maxWeight *= Config.J;
    }

    public boolean L() {
        return this.bB(1027) || this.bB(1038);
    }

    public boolean M() {
        return this.bB(167);
    }

    public boolean N() {
        return this.bf > 0;
    }

    public synchronized void w(int counter) {
        this.bf += counter;
    }

    public void O() {
        this.w(1);
        GeneralThreadPool.a().b(new L1PcInvisDelay(this.fr()), 3000L);
    }

    public synchronized void x(int exp) {
        this.R += exp;
        if (this.R > 1859065562) {
            this.R = 1859065562;
        }
    }

    private void cJ(int gap) {
        this.Z();
        if (this.ev() == 99 && Config.ag) {
            ItemTable.a(this, 43000, 1);
        } else if (this.ev() == 70 && this.cE() > 0) {
            L1Master.a().a(this.cE(), this.et());
            ItemTable.a(this, 21143, 1);
        }
        int i = 0;
        while (i < gap) {
            int randomHp = CalcStat.a(this);
            int randomMp = CalcStat.b(this);
            this.m(randomHp);
            this.n(randomMp);
            ++i;
        }
        this.fB();
        this.fA();
        this.W();
        this.Y();
        this.I();
        if (this.ev() >= 51 && this.ev() - 50 > this.bA() && this.bf() + this.bh() + this.bg() + this.bj() + this.bk() + this.bi() < 270) {
            int bonus = this.ev() - 50 - this.bA();
            this.a(new S_Message_YN(479, String.valueOf(bonus)));
        }
        if (this.ev() >= 52) {
            int bufftime = 10800;
            this.j(4076, 10800000);
            this.a(new S_PacketBox(86, 173, 1, 675));
        }
        this.a(new S_OwnCharStatus(this));
        this.ac();
        QuestNewTable.a().a(this);
    }

    private void cK(int gap) {
        this.Z();
        int i = 0;
        while (i > gap) {
            int randomHp = CalcStat.a(this);
            int randomMp = CalcStat.b(this);
            this.m(-randomHp);
            this.n(-randomMp);
            --i;
        }
        this.fB();
        this.fA();
        this.W();
        this.Y();
        this.I();
        this.a(new S_OwnCharStatus(this));
        this.ac();
    }

    @Override
    public void y(int effectId) {
        this.a(new S_Poison(this.fr(), effectId));
        if (this.aA() || this.bN()) {
            return;
        }
        if (this.ff()) {
            this.c(new S_Poison(this.fr(), effectId));
        } else {
            this.b(new S_Poison(this.fr(), effectId));
        }
    }

    @Override
    public void z(int pt) {
        super.z(pt);
        this.a(new S_HPUpdate(this));
    }

    @Override
    public int P() {
        return this.bj.a();
    }

    @Override
    public void A(int i) {
        this.bj.a(i);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void B(int i) {
        L1Karma l1Karma = this.bj;
        synchronized (l1Karma) {
            this.bj.b(i);
        }
    }

    public int Q() {
        return this.bj.b();
    }

    public int R() {
        return this.bj.c();
    }

    private void fy() {
        this.bk = new Timestamp(System.currentTimeMillis());
    }

    public boolean S() {
        if (this.bk == null) {
            return false;
        }
        if (System.currentTimeMillis() - this.bk.getTime() > 86400000L) {
            this.b((Timestamp)null);
            return false;
        }
        return true;
    }

    private void fz() {
        this.bl = new Timestamp(System.currentTimeMillis());
    }

    public boolean T() {
        if (this.bl == null) {
            return false;
        }
        if (System.currentTimeMillis() - this.bl.getTime() > 86400000L) {
            this.c((Timestamp)null);
            return false;
        }
        return true;
    }

    @Override
    public int U() {
        return this.aC().c(this.ev());
    }

    public void V() {
        this.bz(29);
        this.bz(76);
        this.bz(152);
        this.bz(43);
        this.bz(54);
        this.bz(1001);
        this.bz(1037);
    }

    public void C(int i) {
        this.bo += i;
    }

    public void D(int i) {
        this.bp += i;
    }

    public void E(int i) {
        this.bq += i;
    }

    public void F(int i) {
        this.br += i;
    }

    public void G(int i) {
        this.bs += i;
    }

    public void H(int i) {
        this.bt += i;
    }

    public void I(int i) {
        this.bu += i;
    }

    public void J(int i) {
        this.bv += i;
    }

    private void fA() {
        int newBaseDmgup = this.aC().f(this.ev());
        int newBaseBowDmgup = this.aC().g(this.ev());
        this.ck(newBaseDmgup - this.aD);
        this.cl(newBaseBowDmgup - this.aE);
        this.aD = newBaseDmgup;
        this.aE = newBaseBowDmgup;
    }

    private void fB() {
        int newBaseHitup = this.aC().h(this.ev());
        int newBaseBowHitup = this.aC().i(this.ev());
        this.cm(newBaseHitup - this.aF);
        this.cn(newBaseBowHitup - this.aG);
        this.aF = newBaseHitup;
        this.aG = newBaseBowHitup;
    }

    public void W() {
        if (this.aC() == null) {
            return;
        }
        int newAc = 10 + CalcStat.a(this.eB()) + this.aC().d(this.ev());
        this.bL(newAc - this.bz);
        if (this.bz != newAc) {
            this.bz = newAc;
            this.a(new S_OwnCharAttrDef(this));
        }
    }

    @Override
    public int W_() {
        if (this.bB(153)) {
            return (this.x + this.aC().c()) / 4;
        }
        return this.x + this.aC().c();
    }

    public void Y() {
        int newBaseMr = this.ev() / 2;
        this.co((newBaseMr += CalcStat.e(this.eE())) - this.aH);
        this.aH = newBaseMr;
    }

    public void Z() {
        this.b(ExpTable.c(this.R));
        for (L1QuestNew qn : this.dS().values()) {
            if (qn.n() <= 0) continue;
            qn.a(this.ev());
        }
        if (this.y != null) {
            this.y.a();
        }
    }

    public void aa() {
        this.Z();
        this.fB();
        this.fA();
        this.Y();
        this.W();
        this.cc = 0;
    }

    public void ab() {
        long nowChatTimeInMillis = System.currentTimeMillis();
        if (this.bQ == 0) {
            this.bQ = (byte)(this.bQ + 1);
            this.bR = nowChatTimeInMillis;
            return;
        }
        long chatInterval = nowChatTimeInMillis - this.bR;
        if (chatInterval > 2000L) {
            this.bQ = 0;
            this.bR = 0L;
        } else {
            if (this.bQ >= 3) {
                this.j(1005, 120000);
                this.a(new S_PacketBox(36, 120));
                this.a(new S_ServerMessage(153));
                this.bQ = 0;
                this.bR = 0L;
            }
            this.bQ = (byte)(this.bQ + 1);
        }
    }

    public void b(int oldType, int newType) {
        switch (oldType) {
            case 1: {
                this.bL(2);
                this.co(-3);
                this.a(new S_Fight(0, 0));
                break;
            }
            case 2: {
                this.bL(4);
                this.co(-6);
                this.a(new S_Fight(1, 0));
                break;
            }
            case 3: {
                this.bL(6);
                this.co(-9);
                this.a(new S_Fight(2, 0));
                break;
            }
            case -1: {
                this.ck(-1);
                this.cl(-1);
                this.cp(-1);
                this.a(new S_Fight(3, 0));
                break;
            }
            case -2: {
                this.ck(-3);
                this.cl(-3);
                this.cp(-2);
                this.a(new S_Fight(4, 0));
                break;
            }
            case -3: {
                this.ck(-5);
                this.cl(-5);
                this.cp(-3);
                this.a(new S_Fight(5, 0));
            }
        }
        switch (newType) {
            case 1: {
                this.bL(-2);
                this.co(3);
                this.a(new S_Fight(0, 1));
                break;
            }
            case 2: {
                this.bL(-4);
                this.co(6);
                this.a(new S_Fight(1, 1));
                break;
            }
            case 3: {
                this.bL(-6);
                this.co(9);
                this.a(new S_Fight(2, 1));
                break;
            }
            case -1: {
                this.ck(1);
                this.cl(1);
                this.cp(1);
                this.a(new S_Fight(3, 1));
                break;
            }
            case -2: {
                this.ck(3);
                this.cl(3);
                this.cp(2);
                this.a(new S_Fight(4, 1));
                break;
            }
            case -3: {
                this.ck(5);
                this.cl(5);
                this.cp(3);
                this.a(new S_Fight(5, 1));
            }
        }
        this.a(new S_SPMR(this));
    }

    public void ac() {
        if (!Config.aO) {
            return;
        }
        if (this.ev() > Config.aP) {
            if (this.bB(8000)) {
                this.bz(8000);
                this.a(new S_Fight(6, 0));
            }
        } else if (!this.bB(8000)) {
            this.j(8000, 0);
            this.a(new S_Fight(6, 1));
        }
    }

    public void ad() {
        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
        for (L1ItemInstance item : this.j().d()) {
            int index;
            if (!item.D() || (index = this.j().l(item)) < 0) continue;
            items.put(item.fr(), index);
        }
        this.a(new S_EquipmentSlot(items));
    }

    public void a(int x, int y, int mapid) {
        this.bZ = new int[]{x, y, mapid};
    }

    public void ae() {
        if (this.eX()) {
            return;
        }
        if (this.j().f(640100)) {
            new L1Speed().a(this);
            this.j(25005, 600000);
        }
        if (this.j().f(640102) && !this.af()) {
            this.j(4058, 0);
            this.cp(30);
            this.a(new S_SPMR(this));
            this.bH(400);
            this.bJ(200);
            this.a(new S_MPUpdate(this.eb(), this.ex()));
            this.a(new S_HPUpdate(this.ea(), this.ew()));
            if (this.q()) {
                this.aL().f(this);
            }
            this.a(new S_PacketBox(144, 1));
            this.a(new S_ChangeName(this.fr(), this.et()));
            this.b(new S_ChangeName(this.fr(), this.et()));
            for (L1NpcInstance npc : this.ek().values()) {
                npc.b(new S_CharTitle(npc.fr(), String.valueOf(this.et()) + "\u7684"));
            }
            for (L1DollInstance doll : this.el().values()) {
                doll.b(new S_CharTitle(doll.fr(), String.valueOf(this.et()) + "\u7684"));
            }
        }
    }

    public boolean af() {
        return this.bB(4058);
    }

    public void K(int i) {
        this.cb = Math.min(Math.max(0, i), 3850000);
        int percent = this.cb / 7700;
        if (this.cc != percent) {
            this.cc = percent;
            this.a(new S_PacketBox(82, percent, this.cb));
        }
    }

    public int ag() {
        for (L1DollInstance doll : this.el().values()) {
            if (doll.X_().au() <= 0 || doll.X_().bs() <= Random.a(100) + 1) continue;
            this.a(new S_SkillSound(this.fr(), 6319));
            this.b(new S_SkillSound(this.fr(), 6319));
            return doll.X_().au();
        }
        return 0;
    }

    public int ah() {
        for (L1DollInstance doll : this.el().values()) {
            if (doll.X_().ap() <= 0 || doll.X_().bx() <= Random.a(100) + 1) continue;
            this.a(new S_SkillSound(this.fr(), 6329));
            this.b(new S_SkillSound(this.fr(), 6329));
            return doll.X_().ap();
        }
        return 0;
    }

    public boolean ai() {
        for (L1DollInstance doll : this.el().values()) {
            if (doll.X_().bt() <= 0 || doll.X_().bt() <= Random.a(100) + 1) continue;
            this.a(new S_SkillSound(this.fr(), 6320));
            this.b(new S_SkillSound(this.fr(), 6320));
            return true;
        }
        return false;
    }

    public void L(int i) {
        this.ce += i;
    }

    public void M(int i) {
        this.cf += i;
    }

    public boolean aj() {
        if (this.cg == 0) {
            return false;
        }
        return this.cg > Random.a(100) + 1;
    }

    public boolean ak() {
        if (this.ch == 0) {
            return false;
        }
        return this.ch > Random.a(100) + 1;
    }

    public boolean al() {
        if (this.ci == 0) {
            return false;
        }
        return this.ci > Random.a(100) + 1;
    }

    public boolean am() {
        if (!this.q()) {
            return false;
        }
        return this.aL().e(this);
    }

    public void an() {
        this.ck = Math.min(this.ck + 10, 120);
    }

    public void ao() {
        this.cl = Math.min(this.cl + 20, 100);
    }

    public void ap() {
        this.cu = 21600;
        this.cv = 7200;
        this.cw = 21600;
        this.cx = 21600;
        this.cy = 7200;
        this.cz = 14400;
        this.cA = 14400;
    }

    public int aq() {
        return this.cL(this.ev());
    }

    private int cL(int level) {
        int[] levelGate = new int[]{1, 15, 30, 45, 50, 52, 55, 60, 65, 70, 75, 80, 82, 84};
        int frameLevel = 0;
        while (frameLevel < levelGate.length) {
            if (level < levelGate[frameLevel]) {
                return frameLevel - 1;
            }
            ++frameLevel;
        }
        return levelGate.length - 1;
    }

    public boolean N(int itemobjid) {
        for (L1NpcInstance petNpc : this.ek().values()) {
            L1PetInstance pet;
            if (!(petNpc instanceof L1PetInstance) || itemobjid != (pet = (L1PetInstance)petNpc).k()) continue;
            return true;
        }
        return false;
    }

    public boolean O(int itemobjid) {
        for (L1DollInstance doll : this.el().values()) {
            if (itemobjid != doll.f()) continue;
            return true;
        }
        return false;
    }

    public void P(int i) {
        this.cF += i;
    }

    public void b(int itemid, int wish_count, int enchantlv) {
        this.dc = new int[]{itemid, wish_count, enchantlv};
    }

    public void Q(int i) {
        this.dd += i;
    }

    public void R(int i) {
        this.de += i;
    }

    public void S(int i) {
        this.df += i;
    }

    public void T(int i) {
        this.dg += i;
    }

    public void U(int i) {
        this.dh += i;
    }

    public void V(int i) {
        this.di += i;
    }

    public void W(int i) {
        this.dj += i;
    }

    public void X(int i) {
        this.dk += i;
    }

    public void Y(int i) {
        this.dl += i;
    }

    public void Z(int i) {
        this.dm += i;
    }

    public void aa(int i) {
        this.dn += i;
    }

    public void ab(int i) {
        this.cfr_renamed_0 += i;
    }

    public void ac(int i) {
        this.dr += i;
    }

    public int ar() {
        return this.t;
    }

    public int as() {
        return this.v;
    }

    public CopyOnWriteArrayList<Integer> at() {
        return this.G;
    }

    public L1AccountInventory au() {
        return this.H;
    }

    public L1ElvenInventory av() {
        return this.I;
    }

    public L1CharInventory aw() {
        return this.J;
    }

    public L1Inventory ax() {
        return this.K;
    }

    public int ay() {
        return this.M;
    }

    public void ad(int type) {
        this.M = type;
    }

    public int az() {
        return this.N;
    }

    public void ae(int accessLevel) {
        this.N = accessLevel;
    }

    public boolean aA() {
        return this.O;
    }

    public void b(boolean isGmInvis) {
        this.O = isGmInvis;
    }

    public int aB() {
        return this.P;
    }

    public L1ClassFeature aC() {
        return this.Q;
    }

    public int aD() {
        return this.S;
    }

    public void af(int PKcount) {
        this.S = PKcount;
    }

    public int aE() {
        return this.T;
    }

    public void ag(int PkCountForElf) {
        this.T = PkCountForElf;
    }

    public int aF() {
        return this.U;
    }

    public void ah(int clanid) {
        this.U = clanid;
    }

    public String aG() {
        return this.V;
    }

    public void c(String clanName) {
        this.V = clanName;
    }

    public int aH() {
        return this.W;
    }

    public void ai(int clanRank) {
        this.W = clanRank;
    }

    public Timestamp aI() {
        return this.X;
    }

    public void a(Timestamp birthday) {
        this.X = birthday;
    }

    public int aJ() {
        return this.Y;
    }

    public void aj(int sex) {
        this.Y = sex;
    }

    public ClientThread aK() {
        return this.Z;
    }

    public void a(ClientThread netConnection) {
        this.Z = netConnection;
    }

    public L1Party aL() {
        return this.aa;
    }

    public void a(L1Party party) {
        this.aa = party;
    }

    public L1ChatParty aM() {
        return this.ab;
    }

    public void a(L1ChatParty chatParty) {
        this.ab = chatParty;
    }

    public int aN() {
        return this.ac;
    }

    public void ak(int partyID) {
        this.ac = partyID;
    }

    public int aO() {
        return this.ad;
    }

    public void al(int tradeID) {
        this.ad = tradeID;
    }

    public boolean aP() {
        return this.ae;
    }

    public void c(boolean tradeOk) {
        this.ae = tradeOk;
    }

    public int aQ() {
        return this.af;
    }

    public void am(int tempID) {
        this.af = tempID;
    }

    public boolean aR() {
        return this.ag;
    }

    public void d(boolean isTeleport) {
        this.ag = isTeleport;
    }

    public boolean aS() {
        return this.ah;
    }

    public void e(boolean isDrink) {
        this.ah = isDrink;
    }

    public boolean aT() {
        return this.ai;
    }

    public void f(boolean isPinkName) {
        this.ai = isPinkName;
    }

    public CopyOnWriteArrayList<L1PrivateShopSellList> aU() {
        return this.aj;
    }

    public ArrayList<L1PrivateShopBuyList> aV() {
        return this.ak;
    }

    public byte[] aW() {
        return this.al;
    }

    public void a(byte[] shopChat) {
        this.al = shopChat;
    }

    public boolean aX() {
        return this.am;
    }

    public void g(boolean isPrivateShop) {
        this.am = isPrivateShop;
    }

    public boolean aY() {
        return this.an;
    }

    public void h(boolean isTradingInPrivateShop) {
        this.an = isTradingInPrivateShop;
    }

    public int aZ() {
        return this.ao;
    }

    public void an(int partnersPrivateShopItemCount) {
        this.ao = partnersPrivateShopItemCount;
    }

    public CopyOnWriteArrayList<L1BookMark> ba() {
        return this.ar;
    }

    public L1Quest bb() {
        return this.at;
    }

    public String bc() {
        return this.au;
    }

    public void d(String accountName) {
        this.au = accountName;
    }

    public int bd() {
        return this.av;
    }

    public int be() {
        return this.aw;
    }

    public int bf() {
        return this.ax;
    }

    public int bg() {
        return this.ay;
    }

    public int bh() {
        return this.az;
    }

    public int bi() {
        return this.aA;
    }

    public int bj() {
        return this.aB;
    }

    public int bk() {
        return this.aC;
    }

    public int bl() {
        return this.aD;
    }

    public int bm() {
        return this.aE;
    }

    public int bn() {
        return this.aF;
    }

    public int bo() {
        return this.aG;
    }

    public int bp() {
        return this.aH;
    }

    public int bq() {
        return this.aI;
    }

    public void ao(int originalStr) {
        this.aI = originalStr;
    }

    public int br() {
        return this.aJ;
    }

    public void ap(int originalCon) {
        this.aJ = originalCon;
    }

    public int bs() {
        return this.aK;
    }

    public void aq(int originalDex) {
        this.aK = originalDex;
    }

    public int bt() {
        return this.aL;
    }

    public void ar(int originalCha) {
        this.aL = originalCha;
    }

    public int bu() {
        return this.aM;
    }

    public void as(int originalInt) {
        this.aM = originalInt;
    }

    public int bv() {
        return this.aN;
    }

    public void at(int originalWis) {
        this.aN = originalWis;
    }

    public int bw() {
        return this.aO;
    }

    public void au(int advenHp) {
        this.aO = advenHp;
    }

    public int bx() {
        return this.aP;
    }

    public void av(int advenHp2) {
        this.aP = advenHp2;
    }

    public int by() {
        return this.aQ;
    }

    public void aw(int advenMp) {
        this.aQ = advenMp;
    }

    public int bz() {
        return this.aR;
    }

    public void ax(int highDamageLevel) {
        this.aR = highDamageLevel;
    }

    public int bA() {
        return this.aS;
    }

    public void ay(int bonusStats) {
        this.aS = bonusStats;
    }

    public int bB() {
        return this.aT;
    }

    public void az(int elixirStats) {
        this.aT = elixirStats;
    }

    public int bC() {
        return this.aU;
    }

    public void aA(int elfAttr) {
        this.aU = elfAttr;
    }

    public int bD() {
        return this.aV;
    }

    public void aB(int partnerId) {
        this.aV = partnerId;
    }

    public int bE() {
        return this.aW;
    }

    public void aC(int onlineStatus) {
        this.aW = onlineStatus;
    }

    public int bF() {
        return this.aX;
    }

    public void aD(int homeTownId) {
        this.aX = homeTownId;
    }

    public int bG() {
        return this.aY;
    }

    public void aE(int contribution) {
        this.aY = contribution;
    }

    public int bH() {
        return this.aZ;
    }

    public void aF(int pay) {
        this.aZ = pay;
    }

    public int bI() {
        return this.ba;
    }

    public void aG(int hellTime) {
        this.ba = hellTime;
    }

    public boolean bJ() {
        return this.bb;
    }

    public void i(boolean isBanned) {
        this.bb = isBanned;
    }

    public L1EquipmentSlot bK() {
        return this.bc;
    }

    public long bL() {
        return this.bd;
    }

    public int bM() {
        return this.be;
    }

    public boolean bN() {
        return this.bi;
    }

    public void j(boolean isGhost) {
        this.bi = isGhost;
    }

    public Timestamp bO() {
        return this.bk;
    }

    public void b(Timestamp lastPk) {
        this.bk = lastPk;
    }

    public Timestamp bP() {
        return this.bl;
    }

    public void c(Timestamp lastPkForElf) {
        this.bl = lastPkForElf;
    }

    public Timestamp bQ() {
        return this.bm;
    }

    public void d(Timestamp deleteTime) {
        this.bm = deleteTime;
    }

    public Timestamp bR() {
        return this.bn;
    }

    public void e(Timestamp tamUseTime) {
        this.bn = tamUseTime;
    }

    public int bS() {
        return this.bo;
    }

    public int bT() {
        return this.bp;
    }

    public int bU() {
        return this.bq;
    }

    public int bV() {
        return this.br;
    }

    public int bW() {
        return this.bs;
    }

    public int bX() {
        return this.bt;
    }

    public int bY() {
        return this.bu;
    }

    public int bZ() {
        return this.bv;
    }

    public int ca() {
        return this.bw;
    }

    public void aH(int expRes) {
        this.bw = expRes;
    }

    public boolean cb() {
        return this.bx;
    }

    public void k(boolean isGres) {
        this.bx = isGres;
    }

    public boolean cc() {
        return this.by;
    }

    public void l(boolean gresValid) {
        this.by = gresValid;
    }

    public L1ExcludingList cd() {
        return this.bA;
    }

    public L1SpeedChecker ce() {
        return this.bB;
    }

    public int cf() {
        return this.bC;
    }

    public void aI(int teleportX) {
        this.bC = teleportX;
    }

    public int cg() {
        return this.bD;
    }

    public void aJ(int teleportY) {
        this.bD = teleportY;
    }

    public int ch() {
        return this.bE;
    }

    public void aK(int teleportMapId) {
        this.bE = teleportMapId;
    }

    public int ci() {
        return this.bF;
    }

    public void aL(int teleportHeading) {
        this.bF = teleportHeading;
    }

    public int cj() {
        return this.bG;
    }

    public void aM(int tempCharGfxAtDead) {
        this.bG = tempCharGfxAtDead;
    }

    public boolean ck() {
        return this.bH;
    }

    public void m(boolean isCanWhisper) {
        this.bH = isCanWhisper;
    }

    public boolean cl() {
        return this.bI;
    }

    public void n(boolean isShowTradeChat) {
        this.bI = isShowTradeChat;
    }

    public boolean cm() {
        return this.bJ;
    }

    public void o(boolean isShowClanChat) {
        this.bJ = isShowClanChat;
    }

    public boolean cn() {
        return this.bK;
    }

    public void p(boolean isShowPartyChat) {
        this.bK = isShowPartyChat;
    }

    public boolean co() {
        return this.bL;
    }

    public void q(boolean isShowWorldChat) {
        this.bL = isShowWorldChat;
    }

    public int cp() {
        return this.bM;
    }

    public void aN(int fightId) {
        this.bM = fightId;
    }

    public boolean cq() {
        return this.bN;
    }

    public void r(boolean isFishing) {
        this.bN = isFishing;
    }

    public int cr() {
        return this.bO;
    }

    public void aO(int fishX) {
        this.bO = fishX;
    }

    public int cs() {
        return this.bP;
    }

    public void aP(int fishY) {
        this.bP = fishY;
    }

    public int ct() {
        return this.bS;
    }

    public void aQ(int callClanId) {
        this.bS = callClanId;
    }

    public int cu() {
        return this.bT;
    }

    public void aR(int callClanHeading) {
        this.bT = callClanHeading;
    }

    public boolean cv() {
        return this.bU;
    }

    public void s(boolean isInCharReset) {
        this.bU = isInCharReset;
    }

    public int cw() {
        return this.bV;
    }

    public void aS(int tempLevel) {
        this.bV = tempLevel;
    }

    public int cx() {
        return this.bW;
    }

    public void aT(int tempMaxLevel) {
        this.bW = tempMaxLevel;
    }

    public boolean cy() {
        return this.bX;
    }

    public void t(boolean isShapeChange) {
        this.bX = isShapeChange;
    }

    public int cz() {
        return this.bY;
    }

    public void aU(int partyType) {
        this.bY = partyType;
    }

    public int[] cA() {
        return this.bZ;
    }

    public Timestamp cB() {
        return this.ca;
    }

    public void f(Timestamp logoutTime) {
        this.ca = logoutTime;
    }

    public int cC() {
        return this.cb;
    }

    public int cD() {
        return this.cc;
    }

    public int cE() {
        return this.cd;
    }

    public void aV(int masterID) {
        this.cd = masterID;
    }

    public int cF() {
        return this.ce;
    }

    public int cG() {
        return this.cf;
    }

    public void aW(int poisonDollChance) {
        this.cg = poisonDollChance;
    }

    public void aX(int slowDollChance) {
        this.ch = slowDollChance;
    }

    public void aY(int curseDollChance) {
        this.ci = curseDollChance;
    }

    public boolean cH() {
        return this.cj;
    }

    public void u(boolean isBreathinWater) {
        this.cj = isBreathinWater;
    }

    public int cI() {
        return this.ck;
    }

    public void aZ(int bookmarkSpace) {
        this.ck = bookmarkSpace;
    }

    public int cJ() {
        return this.cl;
    }

    public void ba(int charStoreSpace) {
        this.cl = charStoreSpace;
    }

    public int cK() {
        return this.cm;
    }

    public void bb(int currentAttackID) {
        this.cm = currentAttackID;
    }

    public boolean cL() {
        return this.cn;
    }

    public void v(boolean isTripleArrow) {
        this.cn = isTripleArrow;
    }

    public boolean cM() {
        return this.co;
    }

    public void w(boolean isFoeSlayer) {
        this.co = isFoeSlayer;
    }

    public boolean cN() {
        return this.cp;
    }

    public void x(boolean isLoadWorldShop) {
        this.cp = isLoadWorldShop;
    }

    public FishingTimer cO() {
        return this.cq;
    }

    public void a(FishingTimer fishingTimer) {
        this.cq = fishingTimer;
    }

    public int cP() {
        return this.cr;
    }

    public void bc(int runeOpenStatus) {
        this.cr = runeOpenStatus;
    }

    public int cQ() {
        return this.cs;
    }

    public void bd(int worldShopAdenaRecord) {
        this.cs = worldShopAdenaRecord;
    }

    public boolean cR() {
        return this.ct;
    }

    public void y(boolean isHasMapTimer) {
        this.ct = isHasMapTimer;
    }

    public int cS() {
        return this.cu;
    }

    public void be(int mapTime_1) {
        this.cu = mapTime_1;
    }

    public int cT() {
        return this.cv;
    }

    public void bf(int mapTime_2) {
        this.cv = mapTime_2;
    }

    public int cU() {
        return this.cw;
    }

    public void bg(int mapTime_3) {
        this.cw = mapTime_3;
    }

    public int cV() {
        return this.cx;
    }

    public void bh(int mapTime_4) {
        this.cx = mapTime_4;
    }

    public int cW() {
        return this.cy;
    }

    public void bi(int mapTime_5) {
        this.cy = mapTime_5;
    }

    public int cX() {
        return this.cz;
    }

    public void bj(int mapTime_6) {
        this.cz = mapTime_6;
    }

    public int cY() {
        return this.cA;
    }

    public void bk(int mapTime_7) {
        this.cA = mapTime_7;
    }

    public int cZ() {
        return this.cB;
    }

    public void bl(int onlineGiftID) {
        this.cB = onlineGiftID;
    }

    public boolean da() {
        return this.cC;
    }

    public void z(boolean isEffectByEnchant_1) {
        this.cC = isEffectByEnchant_1;
    }

    public boolean db() {
        return this.cD;
    }

    public void A(boolean isEffectByEnchant_2) {
        this.cD = isEffectByEnchant_2;
    }

    public boolean dc() {
        return this.cE;
    }

    public void B(boolean isEffectByEnchant_3) {
        this.cE = isEffectByEnchant_3;
    }

    public void C(boolean effect_Berserker_Axe) {
        this.cG = effect_Berserker_Axe;
    }

    public boolean dd() {
        return this.cH;
    }

    public void D(boolean effect_Heal_Guard_Fafurion) {
        this.cH = effect_Heal_Guard_Fafurion;
    }

    public boolean de() {
        return this.cI;
    }

    public void E(boolean effect_Mana_Guard_Lindvior) {
        this.cI = effect_Mana_Guard_Lindvior;
    }

    public int df() {
        return this.cJ;
    }

    public void bm(int effect_Shield_of_Rebels) {
        this.cJ = effect_Shield_of_Rebels;
    }

    public boolean dg() {
        return this.cK;
    }

    public void F(boolean effect_DemeterBless) {
        this.cK = effect_DemeterBless;
    }

    public boolean dh() {
        return this.cL;
    }

    public void G(boolean effect_DemeterMpr) {
        this.cL = effect_DemeterMpr;
    }

    public boolean di() {
        return this.cM;
    }

    public void H(boolean effect_AncientDemeterBless) {
        this.cM = effect_AncientDemeterBless;
    }

    public boolean dj() {
        return this.cN;
    }

    public void I(boolean effect_HexagramMagic) {
        this.cN = effect_HexagramMagic;
    }

    public boolean dk() {
        return this.cO;
    }

    public void J(boolean effect_AncientHexagramMagic) {
        this.cO = effect_AncientHexagramMagic;
    }

    public boolean dl() {
        return this.cP;
    }

    public void K(boolean effect_HadesCloak) {
        this.cP = effect_HadesCloak;
    }

    public boolean dm() {
        return this.cQ;
    }

    public void L(boolean effect_GuardianAmulet) {
        this.cQ = effect_GuardianAmulet;
    }

    public int dn() {
        return this.cR;
    }

    public void bn(int effect_Bind) {
        this.cR = effect_Bind;
    }

    public int cfr_renamed_0() {
        return this.cS;
    }

    public void bo(int effect_Heal) {
        this.cS = effect_Heal;
    }

    public int dp() {
        return this.cT;
    }

    public void bp(int effect_SoulBarrier) {
        this.cT = effect_SoulBarrier;
    }

    public int dq() {
        return this.cU;
    }

    public void bq(int effect_ColdAttack) {
        this.cU = effect_ColdAttack;
    }

    public boolean dr() {
        return this.cV;
    }

    public void M(boolean effect_AbsoluteImperius) {
        this.cV = effect_AbsoluteImperius;
    }

    public boolean ds() {
        return this.cW;
    }

    public void N(boolean effect_Imperius) {
        this.cW = effect_Imperius;
    }

    public boolean dt() {
        return this.cX;
    }

    public void O(boolean effect_MoonAmulet) {
        this.cX = effect_MoonAmulet;
    }

    public boolean du() {
        return this.cY;
    }

    public void P(boolean effect_EverlastingLight1) {
        this.cY = effect_EverlastingLight1;
    }

    public boolean dv() {
        return this.cZ;
    }

    public void Q(boolean effect_EverlastingLight2) {
        this.cZ = effect_EverlastingLight2;
    }

    public boolean dw() {
        return this.da;
    }

    public void R(boolean effect_EverlastingLight3) {
        this.da = effect_EverlastingLight3;
    }

    public boolean dx() {
        return this.db;
    }

    public void S(boolean effect_OnePunch) {
        this.db = effect_OnePunch;
    }

    public int[] dy() {
        return this.dc;
    }

    public int dz() {
        return this.dd;
    }

    public int dA() {
        return this.de;
    }

    public int dB() {
        return this.df;
    }

    public int dC() {
        return this.dg;
    }

    public int dD() {
        return this.dh;
    }

    public int dE() {
        return this.di;
    }

    public int dF() {
        return this.dj;
    }

    public int dG() {
        return this.dk;
    }

    public int dH() {
        return this.dl;
    }

    public int dI() {
        return this.dm;
    }

    public int dJ() {
        return this.dn;
    }

    public int dK() {
        return this.cfr_renamed_0;
    }

    public boolean dL() {
        return this.dp;
    }

    public void T(boolean isAntiPoison) {
        this.dp = isAntiPoison;
    }

    public int dM() {
        return this.dq;
    }

    public void br(int innRoomNumber) {
        this.dq = innRoomNumber;
    }

    public int dN() {
        return this.dr;
    }

    public int dO() {
        return this.ds;
    }

    public void bs(int magicScrollCount) {
        this.ds = magicScrollCount;
    }

    public int dP() {
        return this.dt;
    }

    public void bt(int braveAvatarLevel) {
        this.dt = braveAvatarLevel;
    }

    public int[] dQ() {
        return this.du;
    }

    public void a(int[] monsterList) {
        this.du = monsterList;
    }

    public int[] dR() {
        return this.dv;
    }

    public void b(int[] stageList) {
        this.dv = stageList;
    }

    public HashMap<Integer, L1QuestNew> dS() {
        return this.dw;
    }

    public ArrayList<Integer> dT() {
        return this.dx;
    }

    public void a(ArrayList<Integer> equipList_1) {
        this.dx = equipList_1;
    }

    public ArrayList<Integer> dU() {
        return this.dy;
    }

    public void b(ArrayList<Integer> equipList_2) {
        this.dy = equipList_2;
    }

    public int dV() {
        return this.dz;
    }

    public void bu(int currentEquipPage) {
        this.dz = currentEquipPage;
    }

    public int dW() {
        return this.dA;
    }

    public void bv(int currentComboLevel) {
        this.dA = currentComboLevel;
    }

    public int dX() {
        return this.dB;
    }

    public void bw(int camp) {
        this.dB = camp;
    }

    public int[][] dY() {
        return this.dC;
    }

    public void a(int[][] weekMobList) {
        this.dC = weekMobList;
    }

    @Override
    public /* synthetic */ L1Inventory y() {
        return this.j();
    }

    private class L1R_a
    extends TimerTask {
        private L1R_a() {
        }

        @Override
        public void run() {
            L1PcInstance.this.a(new S_Disconnect(0));
        }
    }

    private class L1R_b
    implements Runnable {
        private final L1Character b;

        public L1R_b(L1Character _lastAttacker) {
            this.b = _lastAttacker;
        }

        @Override
        public void run() {
            try {
                L1PcInstance.this.a(0);
                L1PcInstance.this.l(false);
                while (L1PcInstance.this.aR()) {
                    Thread.sleep(300L);
                }
                L1PcInstance.this.b();
                L1PcInstance.this.d();
                L1PcInstance.this.fq().a(L1PcInstance.this.fu(), true);
                L1PcInstance.this.aM(L1PcInstance.this.bB(67) ? L1PcInstance.this.fe() : L1PcInstance.this.aB());
                new S_044().a((L1Character)L1PcInstance.this, 1);
                if (L1PcInstance.this.bB(4007)) {
                    L1PcInstance.this.bz(4007);
                }
                if (L1PcInstance.this.bB(3048)) {
                    L1PcInstance.this.bz(3048);
                }
                L1PcInstance.this.a(new S_DoActionGFX(L1PcInstance.this.fr(), 8));
                L1PcInstance.this.b(new S_DoActionGFX(L1PcInstance.this.fr(), 8));
                if (this.b != L1PcInstance.this && L1PcInstance.this.c(this.b)) {
                    return;
                }
                if (this.b instanceof L1PcInstance) {
                    L1PcInstance fightPc = (L1PcInstance)this.b;
                    if (L1PcInstance.this.cp() == fightPc.fr() && fightPc.cp() == L1PcInstance.this.fr()) {
                        L1PcInstance.this.aN(0);
                        L1PcInstance.this.a(new S_PacketBox(5, 0, 0));
                        fightPc.aN(0);
                        fightPc.a(new S_PacketBox(5, 0, 0));
                        return;
                    }
                    if (L1PcInstance.this.bB(8000) && fightPc.ev() > L1PcInstance.this.ev() + Config.aQ) {
                        L1PcInstance.this.a(new S_ServerMessage(3799));
                        return;
                    }
                }
                if (L1PcInstance.this.af()) {
                    return;
                }
                if (!L1PcInstance.this.fq().m()) {
                    L1PcInstance.this.a(new S_ServerMessage(3888));
                    return;
                }
                if (L1CastleWar.a().a((L1Character)L1PcInstance.this)) {
                    L1PcInstance.this.a(new S_ServerMessage(3800));
                    return;
                }
                if (!L1PcInstance.this.fu().e()) {
                    L1PcInstance.this.a(new S_ServerMessage(3800));
                    return;
                }
                if ((L1PcInstance.this.fp() == 1700 || L1PcInstance.this.fp() == 1703) && L1PcInstance.this.j().h(21397)) {
                    L1PcInstance.this.a(new S_ServerMessage(3803));
                    for (L1ItemInstance item : L1PcInstance.this.j().d()) {
                        if (item.N() != 21397 || !item.D()) continue;
                        L1PcInstance.this.j().f(item);
                        if (!(this.b instanceof L1PcInstance)) continue;
                        L1ItemInstance drop = ItemTable.a().b(640751);
                        L1World.a().a(L1PcInstance.this.fs() + Random.a(4) - 2, L1PcInstance.this.ft() + Random.a(4) - 2, L1PcInstance.this.fp()).d(drop);
                    }
                    return;
                }
                int lostRate = (int)((((double)L1PcInstance.this.fa() + 32768.0) / 1000.0 - 65.0) * 4.0);
                if (lostRate < 0) {
                    lostRate *= -1;
                    if (L1PcInstance.this.fa() < 0) {
                        lostRate *= 2;
                    }
                    if (lostRate > Random.a(1000)) {
                        int count = 1;
                        if (L1PcInstance.this.fa() <= -30000) {
                            count = Random.a(4) + 1;
                        } else if (L1PcInstance.this.fa() <= -20000) {
                            count = Random.a(3) + 1;
                        } else if (L1PcInstance.this.fa() <= -10000) {
                            count = Random.a(2) + 1;
                        } else if (L1PcInstance.this.fa() < 0) {
                            count = Random.a(1) + 1;
                        }
                        L1PcInstance.this.cI(count);
                    }
                }
                L1PcInstance.this.fx();
                L1PcInstance.this.l(true);
                if (this.b instanceof L1GuardInstance) {
                    if (L1PcInstance.this.aD() > 0) {
                        L1PcInstance.this.af(L1PcInstance.this.aD() - 1);
                    }
                } else if (this.b instanceof L1GuardianInstance && L1PcInstance.this.aE() > 0) {
                    L1PcInstance.this.ag(L1PcInstance.this.aE() - 1);
                }
                L1PcInstance.this.b((Timestamp)null);
                L1PcInstance.this.c((Timestamp)null);
                if (this.b instanceof L1PcInstance) {
                    L1PcInstance player = (L1PcInstance)this.b;
                    if (L1PcInstance.this.fa() >= 0 && !L1PcInstance.this.aT()) {
                        boolean isChangePkCount = false;
                        if (player.fa() < 30000) {
                            player.af(player.aD() + 1);
                            if (player.A() && L1PcInstance.this.A()) {
                                player.ag(player.aE() + 1);
                            }
                            isChangePkCount = true;
                        }
                        player.fy();
                        if (player.fa() == Short.MAX_VALUE) {
                            player.b((Timestamp)null);
                        }
                        if (player.A() && L1PcInstance.this.A()) {
                            player.fz();
                        }
                        int lawful = -1 * (int)(Math.pow(player.ev(), 3.0) * 0.08);
                        if (player.ev() < 50) {
                            lawful = -1 * (int)(Math.pow(player.ev(), 2.0) * 4.0);
                        }
                        if (player.fa() - 1000 < lawful) {
                            lawful = player.fa() - 1000;
                        }
                        if (lawful <= Short.MIN_VALUE) {
                            lawful = Short.MIN_VALUE;
                        }
                        player.cr(lawful);
                        player.a(new S_Lawful(player.fr(), player.fa()));
                        player.b(new S_Lawful(player.fr(), player.fa()));
                        if (isChangePkCount && player.aD() >= 5 && player.aD() < 10) {
                            player.a(new S_RedMessage(551, String.valueOf(player.aD()), "10"));
                        } else if (isChangePkCount && player.aD() >= 10) {
                            HellTimer.a().a(player, true);
                        }
                    } else {
                        L1PcInstance.this.f(false);
                    }
                }
                L1PcInstance.this.aq = GeneralThreadPool.a().a(new L1R_a(), 600000L);
            }
            catch (Exception e) {
                s.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_c
    extends TimerTask {
        private L1R_c() {
        }

        @Override
        public void run() {
            try {
                if (L1PcInstance.this.aK() == null) {
                    L1PcInstance.this.bh.cancel(true);
                    return;
                }
                int serverTime = L1GameTimeClock.a().b().c();
                if (serverTime % 300 == 0) {
                    L1PcInstance.this.a(new S_GameTime(serverTime));
                }
            }
            catch (Exception e) {
                s.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }
}
