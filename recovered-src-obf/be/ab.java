/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import bi.i;

public class ab
extends eu {
    public ab(u pc, String chat, int type) {
        switch (type) {
            case 0: {
                this.c(45);
                this.c(type);
                this.a(pc.ff() ? 0 : pc.fr());
                this.a(String.valueOf(pc.et()) + ": " + chat);
                break;
            }
            case 2: {
                this.c(45);
                this.c(type);
                this.a(pc.ff() ? 0 : pc.fr());
                this.a("<" + pc.et() + "> " + chat);
                this.b(pc.fs());
                this.b(pc.ft());
                break;
            }
            case 14: {
                this.c(45);
                this.c(type);
                this.a(pc.ff() ? 0 : pc.fr());
                this.a("(" + pc.et() + ") " + chat);
                break;
            }
            case 15: {
                this.c(45);
                this.c(type);
                this.a(pc.ff() ? 0 : pc.fr());
                this.a("{" + pc.et() + "} " + chat);
                break;
            }
            case 16: {
                this.c(119);
                this.a(pc.et());
                this.a(chat);
                break;
            }
            case 9: {
                this.c(107);
                this.c(type);
                this.a("-> (" + pc.et() + ") " + chat);
                break;
            }
            case 3: {
                this.c(107);
                this.c(type);
                String name = pc.l() ? "******" : pc.et();
                this.a("[" + name + "] " + chat);
                break;
            }
            case 4: {
                this.c(107);
                this.c(type);
                this.a("{" + pc.et() + "} " + chat);
                break;
            }
            case 13: {
                this.c(107);
                this.c(type);
                this.a("{{" + pc.et() + "}} " + chat);
                break;
            }
            case 11: {
                this.c(107);
                this.c(type);
                this.a("(" + pc.et() + ") " + chat);
                break;
            }
            case 12: {
                this.c(107);
                this.c(type);
                this.a("[" + pc.et() + "] " + chat);
                break;
            }
            case 17: {
                this.c(107);
                this.c(type);
                this.a("{" + pc.et() + "}" + chat);
                break;
            }
            case 18: {
                this.c(107);
                this.c(type);
                this.a("[" + pc.et() + "]" + chat);
                this.b(i.a(255));
            }
        }
    }

    public ab(String name, String chat, int color) {
        this.c(107);
        this.c(18);
        this.a("[" + name + "]" + chat);
        this.b(color);
    }

    public ab(String name, String chat) {
        this.c(107);
        this.c(19);
        this.a("[" + name + "]" + chat);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ChatPacket";
    }
}

