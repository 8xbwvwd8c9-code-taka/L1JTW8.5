/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.i;
import be.ac;
import be.at;
import be.cm;
import be.ds;
import be.ee;
import bj.d;
import java.util.ArrayList;

public class bt
extends cv {
    public bt(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int cmd = this.c();
        if (cmd == 1) {
            int rank = this.c();
            String name = this.g();
            i clan = q.a().a(pc.aF());
            if (clan == null) {
                return;
            }
            u targetPc = clan.c(name);
            if (targetPc == null) {
                pc.a(new ds(2069));
                return;
            }
            if (pc.aF() != targetPc.aF()) {
                pc.a(new ds(201, name));
                return;
            }
            if (name.equalsIgnoreCase(pc.et())) {
                pc.a(new ds(2068));
                return;
            }
            if (rank < 2 || rank > 13) {
                pc.a(new ds(781));
                return;
            }
            int userRank = pc.aH();
            ArrayList<Integer> rankList = new ArrayList<Integer>();
            switch (userRank) {
                case 4: {
                    rankList.add(3);
                    rankList.add(6);
                    rankList.add(12);
                    rankList.add(5);
                    rankList.add(2);
                    rankList.add(9);
                    rankList.add(13);
                    rankList.add(8);
                    rankList.add(7);
                    break;
                }
                case 3: {
                    rankList.add(6);
                    rankList.add(12);
                    rankList.add(5);
                    rankList.add(2);
                    rankList.add(9);
                    rankList.add(13);
                    rankList.add(8);
                    rankList.add(7);
                    break;
                }
                case 6: {
                    rankList.add(12);
                    rankList.add(5);
                    rankList.add(2);
                    rankList.add(13);
                    rankList.add(8);
                    rankList.add(7);
                    break;
                }
                case 10: {
                    rankList.add(9);
                    rankList.add(13);
                    rankList.add(8);
                    rankList.add(7);
                    break;
                }
                case 9: {
                    rankList.add(13);
                    rankList.add(8);
                    rankList.add(7);
                    break;
                }
                default: {
                    pc.a(new ds(518));
                    return;
                }
            }
            if (!rankList.contains(rank) || !rankList.contains(targetPc.aH())) {
                pc.a(new ds(2068));
                return;
            }
            if (rank == 3) {
                if (!targetPc.x()) {
                    pc.a(new ds(2064));
                    return;
                }
                if (targetPc.ev() < 25) {
                    pc.a(new ds(2471));
                    return;
                }
            } else if ((rank == 9 || rank == 6) && targetPc.ev() < 40) {
                pc.a(new ds(2065));
                return;
            }
            if ((userRank == 9 || userRank == 6) && pc.ev() < 40) {
                pc.a(new ds(2473));
                return;
            }
            targetPc.ai(rank);
            targetPc.I();
            targetPc.a(new cm(27, rank, name));
            targetPc.a(new ac(targetPc));
            pc.a(new cm(27, rank, name));
        } else if (cmd != 2 && cmd != 3 && cmd != 4) {
            if (cmd == 5) {
                if (pc.v() == null) {
                    pc.a(new ds(1973));
                    return;
                }
                if (pc.fj() >= 225) {
                    int addHp = 0;
                    int gfxId1 = 8683;
                    int gfxId2 = 829;
                    long curTime = System.currentTimeMillis() / 1000L;
                    int fullTime = (int)((curTime - pc.bL()) / 60L);
                    if (fullTime <= 0) {
                        pc.a(new ds(1974));
                        return;
                    }
                    if (fullTime >= 1 && fullTime <= 29) {
                        addHp = (int)((double)pc.ew() * ((double)fullTime / 100.0));
                    } else if (fullTime >= 30) {
                        int weaponEnchantLv = pc.v().G();
                        if (weaponEnchantLv <= 6) {
                            gfxId1 = 8684;
                            gfxId2 = 8907;
                            addHp = (int)((double)pc.ew() * (20.0 + (double)bi.i.a(20) / 100.0));
                        } else if (weaponEnchantLv == 7 || weaponEnchantLv == 8) {
                            gfxId1 = 8685;
                            gfxId2 = 8909;
                            addHp = (int)((double)pc.ew() * ((double)(30 + bi.i.a(20)) / 100.0));
                        } else if (weaponEnchantLv == 9 || weaponEnchantLv == 10) {
                            gfxId1 = 8773;
                            gfxId2 = 8910;
                            addHp = (int)((double)pc.ew() * ((double)(50 + bi.i.a(10)) / 100.0));
                        } else if (weaponEnchantLv >= 11) {
                            gfxId1 = 8686;
                            gfxId2 = 8908;
                            addHp = (int)((double)pc.ew() * 0.7);
                        }
                    }
                    pc.a(new ee(pc.fr(), gfxId1));
                    pc.b(new ee(pc.fr(), gfxId1));
                    pc.a(new ee(pc.fr(), gfxId2));
                    pc.b(new ee(pc.fr(), gfxId2));
                    pc.c_(0);
                    pc.a(new cm(11, 0));
                    pc.a(pc.ea() + addHp);
                    pc.a(new at(false));
                }
            } else if (cmd == 6) {
                int gfxId = 8683;
                long curTime = System.currentTimeMillis() / 1000L;
                int fullTime = (int)((curTime - pc.bL()) / 60L);
                if (pc.v() == null) {
                    pc.a(new ds(1973));
                    return;
                }
                if (fullTime >= 30) {
                    int weaponEnchantLv = pc.v().G();
                    if (weaponEnchantLv <= 6) {
                        gfxId = 8684;
                    } else if (weaponEnchantLv >= 7 && weaponEnchantLv <= 8) {
                        gfxId = 8685;
                    } else if (weaponEnchantLv >= 9 && weaponEnchantLv <= 10) {
                        gfxId = 8773;
                    } else if (weaponEnchantLv >= 11) {
                        gfxId = 8686;
                    }
                }
                ee sound = new ee(pc.fr(), gfxId);
                pc.a(sound);
                pc.b(sound);
            }
        }
    }

    @Override
    public String a() {
        return "C_Rank";
    }
}

