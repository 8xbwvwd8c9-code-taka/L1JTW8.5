/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.util.ArrayList;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.be.ServerBasePacket;

public class S_PetList
extends ServerBasePacket {
    public S_PetList(int npcObjId, L1PcInstance pc) {
        this.a(npcObjId, pc);
    }

    private void a(int npcObjId, L1PcInstance pc) {
        ArrayList<L1ItemInstance> amuletList = new ArrayList<L1ItemInstance>();
        for (L1ItemInstance item : pc.j().d()) {
            if (item.N() != 40314 && item.N() != 40316 || this.a(pc, item)) continue;
            amuletList.add(item);
        }
        if (!amuletList.isEmpty()) {
            this.c(162);
            this.a(npcObjId);
            this.b(amuletList.size());
            this.c(12);
            for (L1ItemInstance item : amuletList) {
                this.a(item.fr());
                this.c(0);
                this.b(item.e());
                this.c(item.F());
                this.a(item.E());
                this.c(item.C() ? 1 : 0);
                this.a(item.r());
            }
        } else {
            return;
        }
        this.a(115);
    }

    private boolean a(L1PcInstance pc, L1ItemInstance item) {
        for (L1NpcInstance petNpc : pc.ek().values()) {
            if (!(petNpc instanceof L1PetInstance)) continue;
            L1PetInstance pet = (L1PetInstance)petNpc;
            if (item.fr() != pet.k()) continue;
            return true;
        }
        return false;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PetList";
    }
}
