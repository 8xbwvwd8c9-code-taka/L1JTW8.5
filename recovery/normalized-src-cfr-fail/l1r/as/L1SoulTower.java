/*
 * Decompiled with CFR 0.152.
 */
package l1r.as;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DoorTable;
import l1r.ao.MapsTable;
import l1r.ao.SoulTowerTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1FieldObjectInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1GroundInventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.ServerBasePacket;
import l1r.bh.L1DoorGfx;
import l1r.bi.GeneralThreadPool;

public class L1SoulTower {
    private static final Logger a = Logger.getLogger(L1SoulTower.class.getName());
    private static L1SoulTower b;
    private static final int c = 4001;
    private static final int d = 50;
    private static boolean[] e;

    static {
        e = new boolean[50];
    }

    public static L1SoulTower a() {
        if (b == null) {
            b = new L1SoulTower();
        }
        return b;
    }

    private L1SoulTower() {
        L1Map map = L1WorldMap.b().a(4001);
        int i = 1;
        while (i < 50) {
            try {
                L1Map clone = map.s();
                clone.a = 4001 + i;
                MapsTable.a().a(clone);
                L1WorldMap.b().a().put(clone.a, clone);
            }
            catch (CloneNotSupportedException e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
            ++i;
        }
    }

    public boolean a(L1PcInstance pc) {
        int i = 0;
        while (i < e.length) {
            if (!e[i]) {
                L1SoulTower.e[i] = true;
                GeneralThreadPool.a().b(new L1R_a(4001 + i, pc));
                return true;
            }
            ++i;
        }
        return false;
    }

    private ArrayList<L1NpcInstance> a(L1Location loc, int mobid, int amount) {
        int[] mobids = new int[amount];
        int i = 0;
        while (i < amount) {
            mobids[i] = mobid;
            ++i;
        }
        return this.a(loc, mobids);
    }

    private ArrayList<L1NpcInstance> a(L1Location loc, int[] mobsID) {
        ArrayList<L1NpcInstance> mob_list = new ArrayList<L1NpcInstance>();
        int[] nArray = mobsID;
        int n = mobsID.length;
        int n2 = 0;
        while (n2 < n) {
            int npcid = nArray[n2];
            L1NpcInstance npc = mobsID.length > 1 ? SpawnTable.a(npcid, loc.f(), loc.g(), loc.b(), 5, 5, true) : SpawnTable.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, true);
            mob_list.add(npc);
            ++n2;
        }
        return mob_list;
    }

    private class L1R_a
    extends Thread {
        private final L1PcInstance b;
        private final int c;

        private L1R_a(int _mapid, L1PcInstance _pc) {
            this.b = _pc;
            this.c = _mapid;
        }

        @Override
        public void run() {
            try {
                try {
                    long begin = System.currentTimeMillis();
                    L1Teleport.a(this.b, 32869, 32923, this.c, 2, true);
                    this.b.a(new S_PacketBox(195, 1800));
                    L1SoulTower.this.a(new L1Location(32801, 32812, this.c), 190045, 1);
                    L1SoulTower.this.a(new L1Location(32756, 32872, this.c), 190045, 1);
                    L1DoorInstance door1 = DoorTable.b().a(0, L1DoorGfx.a(12632), new L1Location(32843, 32878, this.c), 0, 1, false);
                    L1SoulTower.this.a(new L1Location(32849, 32923, this.c), 190031, 10);
                    L1SoulTower.this.a(new L1Location(32844, 32905, this.c), 190029, 6);
                    L1SoulTower.this.a(new L1Location(32844, 32905, this.c), 190030, 6);
                    this.a(10000L);
                    this.a("\\f=$18344");
                    this.a(3000L);
                    this.a("\\f=$18327");
                    this.a(3000L);
                    this.a("\\f=$18328");
                    ArrayList list = L1SoulTower.this.a(new L1Location(32843, 32894, this.c), 190034, 2);
                    this.a(list);
                    this.a("\\f=$18329");
                    L1SoulTower.this.a(new L1Location(32843, 32886, this.c), 190034, 2);
                    L1SoulTower.this.a(new L1Location(32843, 32886, this.c), 190029, 6);
                    L1NpcInstance keeper1 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32843, 32885, this.c), 190032, 1).get(0);
                    while (!keeper1.eX()) {
                        this.a(1000L);
                    }
                    door1.f();
                    this.a("\\f=$18338");
                    L1DoorInstance door2 = DoorTable.b().a(0, L1DoorGfx.a(6336), new L1Location(32842, 32848, this.c), 0, 1, false);
                    this.a(3000L);
                    this.a("\\f=$18347");
                    ArrayList<L1NpcInstance> list2 = new ArrayList<L1NpcInstance>();
                    list2.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190034, 4));
                    list2.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190035, 4));
                    list2.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190036, 4));
                    L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190029, 6);
                    this.a(list2);
                    this.a("\\f=$18330");
                    L1SoulTower.this.a(new L1Location(32859, 32858, this.c), 190034, 2);
                    L1NpcInstance keeper2 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32859, 32858, this.c), 190037, 1).get(0);
                    while (!keeper2.eX()) {
                        this.a(1000L);
                    }
                    this.a("\\f=$18333");
                    door2.f();
                    L1DoorInstance door3 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32812, this.c), 0, 1, false);
                    L1DoorInstance door4 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32813, this.c), 0, 1, false);
                    L1DoorInstance door5 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32814, this.c), 0, 1, false);
                    this.a(5000L);
                    this.a("\\f=$18331");
                    L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190035, 2);
                    L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190029, 3);
                    L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190034, 2);
                    L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190035, 2);
                    L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190030, 3);
                    L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190034, 2);
                    L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190035, 2);
                    L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190029, 3);
                    L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190034, 2);
                    L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190035, 2);
                    L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190030, 3);
                    L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190034, 2);
                    L1NpcInstance keeper3 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32833, 32809, this.c), 190034, 1).get(0);
                    while (!keeper3.eX()) {
                        this.a(1000L);
                    }
                    door3.f();
                    door4.f();
                    door5.f();
                    this.a("\\f=$18348");
                    L1DoorInstance door6 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32790, 32815, this.c), 0, 1, false);
                    L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190036, 3);
                    L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190034, 2);
                    L1NpcInstance keeper4 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190038, 1).get(0);
                    while (!keeper4.eX()) {
                        this.a(1000L);
                    }
                    door6.f();
                    this.a("\\f=$18340");
                    ArrayList<L1DoorInstance> door_list = new ArrayList<L1DoorInstance>();
                    int i = 32769;
                    while (i <= 32777) {
                        L1DoorInstance door = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(i, 32829, this.c), 0, 1, false);
                        door.c(0);
                        door_list.add(door);
                        ++i;
                    }
                    L1DoorInstance door0 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32760, 32819, this.c), 0, 1, false);
                    this.a(3000L);
                    this.a("\\f=$18349");
                    L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190036, 4);
                    L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190030, 4);
                    L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190034, 4);
                    L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190035, 4);
                    ArrayList list3 = L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190029, 4);
                    this.a(list3);
                    for (L1DoorInstance door : door_list) {
                        door.f();
                    }
                    ArrayList<L1DoorInstance> door_list2 = new ArrayList<L1DoorInstance>();
                    int i2 = 32763;
                    while (i2 <= 32776) {
                        L1DoorInstance door = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(i2, 32843, this.c), 0, 1, false);
                        door.c(0);
                        door_list2.add(door);
                        ++i2;
                    }
                    this.a(5000L);
                    this.a("\\f=$18332");
                    L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190036, 4);
                    L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190030, 4);
                    L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190034, 4);
                    L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190035, 4);
                    L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190029, 4);
                    this.a(15000L);
                    for (L1DoorInstance door : door_list2) {
                        door.f();
                    }
                    this.a("\\f=$18333");
                    ArrayList<L1DoorInstance> door_list3 = new ArrayList<L1DoorInstance>();
                    int i3 = 32749;
                    while (i3 <= 32751) {
                        L1DoorInstance door = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(i3, 32881, this.c), 0, 1, false);
                        door.c(0);
                        door_list3.add(door);
                        ++i3;
                    }
                    L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190036, 2);
                    L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190030, 2);
                    L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190034, 2);
                    L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190035, 2);
                    L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190029, 2);
                    L1NpcInstance keeper7 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190039, 1).get(0);
                    while (!keeper7.eX()) {
                        this.a(1000L);
                    }
                    this.a("\\f=$18341");
                    for (L1DoorInstance door : door_list3) {
                        door.f();
                    }
                    L1DoorInstance door8 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32769, 32905, this.c), 0, 1, false);
                    this.a(10000L);
                    this.a("\\f=$18334");
                    L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190036, 6);
                    L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190030, 6);
                    L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190034, 6);
                    L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190035, 6);
                    L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190029, 10);
                    this.a(15000L);
                    this.a("\\f=$18335");
                    L1NpcInstance keeper8 = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32765, 32906, this.c), 190033, 1).get(0);
                    while (!keeper8.eX()) {
                        this.a(1000L);
                    }
                    door8.f();
                    this.a("\\f=$18342");
                    this.a(3000L);
                    this.a("\\f=$18336");
                    L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190036, 4);
                    L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190030, 4);
                    L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190034, 4);
                    L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190035, 4);
                    L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190029, 4);
                    this.a(10000L);
                    this.a("\\f=$18337");
                    L1NpcInstance boss = (L1NpcInstance)L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190041, 1).get(0);
                    while (!boss.eX()) {
                        this.a(1000L);
                    }
                    int usetime = (int)((System.currentTimeMillis() - begin) / 1000L);
                    this.b.a(new S_PacketBox(196, usetime));
                    SoulTowerTable.a().a(this.b, usetime);
                    this.a("\\f=$18343");
                    this.a(2000L);
                    this.a("\\f=$18574");
                    this.a(2000L);
                    this.a("\\f=$18575");
                    this.a(2000L);
                    int i4 = 0;
                    while (i4 < 10) {
                        this.a("$" + (18576 + i4));
                        this.a(1000L);
                        ++i4;
                    }
                }
                catch (Exception exception) {
                    this.a();
                    e[this.c - 4001] = false;
                    System.out.println("[\u526f\u672c\u7d50\u675f]:\u5c4d\u9b42\u5854(" + this.c + ")");
                }
            }
            finally {
                this.a();
                e[this.c - 4001] = false;
                System.out.println("[\u526f\u672c\u7d50\u675f]:\u5c4d\u9b42\u5854(" + this.c + ")");
            }
        }

        private void a() {
            for (L1Object obj : L1World.a().b()) {
                if (obj.fp() != this.c) continue;
                if (obj instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)obj;
                    L1Teleport.a(pc, 33703, 32502, 4, 5, true);
                    continue;
                }
                if (obj instanceof L1DoorInstance) {
                    DoorTable.b().a(obj.fu());
                    continue;
                }
                if (obj instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance)obj;
                    if (npc instanceof L1FieldObjectInstance) continue;
                    npc.aa_();
                    continue;
                }
                if (!(obj instanceof L1ItemInstance)) continue;
                L1ItemInstance item = (L1ItemInstance)obj;
                L1GroundInventory groundInventory = L1World.a().a(item.fs(), item.ft(), item.fp());
                groundInventory.f(item);
            }
        }

        private void a(long milliseconds) throws InterruptedException {
            Thread.sleep(milliseconds);
            if (this.b.fp() != this.c || this.b.bE() == 0) {
                throw new InterruptedException();
            }
            if (this.b.eX()) {
                this.b.a(new S_NpcChatPacket(this.b, "$18636"));
                Thread.sleep(3000L);
                this.b.a(new S_NpcChatPacket(this.b, "$18637"));
                Thread.sleep(3000L);
            }
        }

        private void a(ServerBasePacket serverbasepacket) {
            for (L1PcInstance pc : L1World.a().c()) {
                if (pc.fp() != this.c) continue;
                pc.a(serverbasepacket);
            }
        }

        private void a(String msg) {
            this.a(new S_PacketBox(84, 2, msg));
        }

        private int a(ArrayList<L1NpcInstance> list) throws InterruptedException {
            int count = -1;
            while (count++ < 900) {
                boolean isAllDeath = false;
                for (L1NpcInstance mob : list) {
                    if (!mob.eX()) {
                        isAllDeath = false;
                        break;
                    }
                    isAllDeath = mob.eX();
                }
                if (isAllDeath) {
                    return count;
                }
                this.a(1000L);
            }
            throw new InterruptedException();
        }
    }
}
