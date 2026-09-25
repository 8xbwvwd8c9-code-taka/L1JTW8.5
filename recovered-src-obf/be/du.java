/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.ah;
import ao.bc;
import ap.q;
import aq.al;
import as.a;
import be.eu;
import bh.j;
import bh.t;
import bh.u;
import java.util.List;

public class du
extends eu {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;
    public static int d = 3;

    public du(ap.t npc, int type) {
        if (type == a) {
            this.a(npc);
        } else if (type == b) {
            this.b(npc);
        } else if (type == c) {
            this.c(npc);
        }
    }

    public du(ap.t npc, ap.u pc, int type) {
        if (type == d) {
            this.a(npc, pc);
        }
    }

    private void a(ap.t npc) {
        this.c(51);
        this.a(npc.fr());
        al calc = new al(npc);
        t shop = bc.a().a(npc.z());
        if (shop == null) {
            this.b(0);
            return;
        }
        List<u> shopItems = shop.b();
        this.b(shopItems.size());
        int i2 = 0;
        while (i2 < shopItems.size()) {
            u shopItem = shopItems.get(i2);
            j item = shopItem.b();
            int price = calc.a((int)((double)shopItem.c() * l1j.server.a.L));
            this.a(shopItem.b().g());
            this.b(shopItem.b().m());
            this.a(price);
            if (shopItem.d() > 1) {
                this.a(String.valueOf(item.j()) + " (" + shopItem.d() + ")");
            } else if (item.aM() > 0) {
                this.a(String.valueOf(item.j()) + " (" + item.aM() + ")");
            } else {
                this.a(item.j());
            }
            this.a(item.U());
            j template = ah.a().a(item.g());
            if (template == null) {
                this.c(0);
            } else {
                q dummy = new q(template, 0);
                byte[] status = dummy.t();
                this.c(status.length);
                this.a(status);
            }
            ++i2;
        }
        this.b(7);
    }

    private void b(ap.t npc) {
        this.c(51);
        this.a(npc.fr());
        t shop = bc.a().a(npc.z());
        this.b(shop.b().size());
        int i2 = 0;
        while (i2 < shop.b().size()) {
            u shopItem = shop.b().get(i2);
            j item = shopItem.b();
            this.a(shopItem.b().g());
            this.b(shopItem.b().m());
            this.a(shopItem.c());
            if (shopItem.d() > 1) {
                this.a(String.valueOf(item.j()) + " (" + shopItem.d() + ")");
            } else {
                this.a(item.j());
            }
            this.a(item.U());
            j template = ah.a().a(item.g());
            if (template == null) {
                this.c(0);
            } else {
                q dummy = new q(template, 0);
                byte[] status = dummy.t();
                this.c(status.length);
                this.a(status);
            }
            ++i2;
        }
        if (npc.z() == 190005) {
            this.b(14921);
        } else if (npc.z() == 190045) {
            this.b(13258);
        } else if (npc.z() == 190095) {
            this.b(65533);
        } else if (npc.z() == 190353) {
            this.b(20222);
        }
    }

    private void c(ap.t npc) {
        this.c(51);
        this.a(npc.fr());
        t shop = bc.a().a(npc.z());
        if (shop == null) {
            this.b(0);
            return;
        }
        List<u> shopItems = shop.b();
        this.b(shopItems.size());
        int index = 0;
        while (index < shopItems.size()) {
            u shopItem = shopItems.get(index);
            this.a(index);
            this.b(shopItem.b().m());
            this.a((int)((double)shopItem.c() * l1j.server.a.L));
            this.a(as.a.a().a(shopItem.b().h()));
            this.a(shopItem.b().U());
            j template = ah.a().a(shopItem.b().g());
            q dummy = new q(template, 0);
            byte[] status = dummy.t();
            this.c(status.length);
            this.a(status);
            ++index;
        }
        this.b(7);
    }

    private void a(ap.t npc, ap.u pc) {
        this.c(51);
        this.a(npc.fr());
        al calc = new al(npc);
        t shop = bc.a().a(npc.z());
        if (shop == null) {
            this.b(0);
            return;
        }
        List<u> shopItems = shop.b();
        int[] sell_index = new int[]{};
        if (npc.z() == 190142) {
            if (pc.x()) {
                sell_index = new int[]{27, 28, 29, 30, 31, 32, 33};
            } else if (pc.A()) {
                int[] nArray = new int[23];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 13;
                nArray[13] = 14;
                nArray[14] = 15;
                nArray[15] = 16;
                nArray[16] = 17;
                nArray[17] = 18;
                nArray[18] = 19;
                nArray[19] = 20;
                nArray[20] = 21;
                nArray[21] = 22;
                nArray[22] = 23;
                sell_index = nArray;
            } else if (pc.B()) {
                int[] nArray = new int[13];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 12;
                sell_index = nArray;
            } else if (pc.C()) {
                sell_index = new int[]{24, 25, 26};
            } else if (pc.D()) {
                sell_index = new int[]{34, 35, 36, 37, 38};
            } else if (pc.E()) {
                sell_index = new int[]{39, 40, 41, 42, 43, 44, 45, 46};
            } else if (pc.F()) {
                sell_index = new int[]{47, 48, 49, 50};
            }
        } else if (npc.z() == 190488) {
            if (pc.x()) {
                int[] nArray = new int[23];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 12;
                nArray[13] = 13;
                nArray[14] = 14;
                nArray[15] = 15;
                nArray[16] = 16;
                nArray[17] = 17;
                nArray[18] = 18;
                nArray[19] = 19;
                nArray[20] = 20;
                nArray[21] = 21;
                nArray[22] = 22;
                sell_index = nArray;
            } else if (pc.z()) {
                int[] nArray = new int[8];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                sell_index = nArray;
            } else if (pc.A()) {
                int[] nArray = new int[46];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 12;
                nArray[13] = 13;
                nArray[14] = 14;
                nArray[15] = 15;
                nArray[16] = 23;
                nArray[17] = 24;
                nArray[18] = 25;
                nArray[19] = 26;
                nArray[20] = 27;
                nArray[21] = 28;
                nArray[22] = 29;
                nArray[23] = 30;
                nArray[24] = 31;
                nArray[25] = 32;
                nArray[26] = 33;
                nArray[27] = 34;
                nArray[28] = 35;
                nArray[29] = 36;
                nArray[30] = 37;
                nArray[31] = 38;
                nArray[32] = 39;
                nArray[33] = 40;
                nArray[34] = 41;
                nArray[35] = 42;
                nArray[36] = 43;
                nArray[37] = 44;
                nArray[38] = 45;
                nArray[39] = 46;
                nArray[40] = 47;
                nArray[41] = 48;
                nArray[42] = 49;
                nArray[43] = 50;
                nArray[44] = 51;
                nArray[45] = 52;
                sell_index = nArray;
            } else if (pc.B()) {
                int[] nArray = new int[36];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 12;
                nArray[13] = 13;
                nArray[14] = 14;
                nArray[15] = 15;
                nArray[16] = 23;
                nArray[17] = 24;
                nArray[18] = 25;
                nArray[19] = 26;
                nArray[20] = 27;
                nArray[21] = 28;
                nArray[22] = 29;
                nArray[23] = 30;
                nArray[24] = 31;
                nArray[25] = 32;
                nArray[26] = 33;
                nArray[27] = 34;
                nArray[28] = 35;
                nArray[29] = 36;
                nArray[30] = 37;
                nArray[31] = 38;
                nArray[32] = 39;
                nArray[33] = 40;
                nArray[34] = 41;
                nArray[35] = 53;
                sell_index = nArray;
            } else if (pc.C()) {
                int[] nArray = new int[20];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 8;
                nArray[9] = 9;
                nArray[10] = 10;
                nArray[11] = 11;
                nArray[12] = 12;
                nArray[13] = 13;
                nArray[14] = 14;
                nArray[15] = 15;
                nArray[16] = 54;
                nArray[17] = 55;
                nArray[18] = 56;
                nArray[19] = 57;
                sell_index = nArray;
            } else if (pc.D()) {
                sell_index = new int[]{58, 59, 60, 61, 62};
            } else if (pc.E()) {
                sell_index = new int[]{63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73};
            } else if (pc.F()) {
                int[] nArray = new int[13];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                nArray[5] = 5;
                nArray[6] = 6;
                nArray[7] = 7;
                nArray[8] = 74;
                nArray[9] = 75;
                nArray[10] = 76;
                nArray[11] = 77;
                nArray[12] = 78;
                sell_index = nArray;
            }
        }
        this.b(sell_index.length);
        int[] nArray = sell_index;
        int n2 = sell_index.length;
        int n3 = 0;
        while (n3 < n2) {
            int i2 = nArray[n3];
            u shopItem = shopItems.get(i2);
            j item = shopItem.b();
            int price = calc.a((int)((double)shopItem.c() * l1j.server.a.L));
            this.a(shopItem.b().g());
            this.b(shopItem.b().m());
            this.a(price);
            if (shopItem.d() > 1) {
                this.a(String.valueOf(item.j()) + " (" + shopItem.d() + ")");
            } else {
                this.a(item.j());
            }
            this.a(item.U());
            j template = ah.a().a(item.g());
            if (template == null) {
                this.c(0);
            } else {
                q dummy = new q(template, 0);
                byte[] status = dummy.t();
                this.c(status.length);
                this.a(status);
            }
            ++n3;
        }
        this.b(7);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ShopSellList";
    }
}

