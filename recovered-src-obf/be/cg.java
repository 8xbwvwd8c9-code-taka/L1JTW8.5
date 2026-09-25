/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.t;
import aq.f;
import be.eu;

public class cg
extends eu {
    public cg(f cha, String chat) {
        this.c(27);
        this.c(21);
        this.a(cha.fr());
        this.a(chat);
    }

    public cg(t npc, String chat, int type) {
        this.a(npc, chat, type);
    }

    private void a(t npc, String chat, int type) {
        switch (type) {
            case 21: {
                this.c(27);
                this.c(type);
                this.a(npc.fr());
                this.a(chat);
                break;
            }
            case 0: {
                this.c(27);
                this.c(type);
                this.a(npc.fr());
                this.a(String.valueOf(npc.et()) + ": " + chat);
                break;
            }
            case 2: {
                this.c(27);
                this.c(type);
                this.a(npc.fr());
                this.a("<" + npc.et() + "> " + chat);
                break;
            }
            case 3: {
                this.c(27);
                this.c(type);
                this.a(npc.fr());
                this.a("[" + npc.et() + "] " + chat);
                break;
            }
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_NpcChatPacket";
    }
}

