/*
 * Decompiled with CFR 0.152.
 */
package bb;

import ap.t;
import aq.aq;
import be.cg;
import be.ds;
import bh.m;
import bi.e;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b
extends TimerTask {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private final t b;
    private final m c;

    public b(t npc, m npcChat) {
        this.b = npc;
        this.c = npcChat;
    }

    public void a() {
        e.a().a(this, this.c.c());
    }

    @Override
    public void run() {
        try {
            if (this.b == null || this.c == null) {
                return;
            }
            if (this.b.ac() != 0 || this.b.ah()) {
                return;
            }
            int chatInterval = this.c.i();
            String chatId1 = this.c.d();
            String chatId2 = this.c.e();
            String chatId3 = this.c.f();
            String chatId4 = this.c.g();
            String chatId5 = this.c.h();
            if (!chatId1.equals("")) {
                this.a(this.b, chatId1);
            }
            if (!chatId2.equals("")) {
                Thread.sleep(chatInterval);
                this.a(this.b, chatId2);
            }
            if (!chatId3.equals("")) {
                Thread.sleep(chatInterval);
                this.a(this.b, chatId3);
            }
            if (!chatId4.equals("")) {
                Thread.sleep(chatInterval);
                this.a(this.b, chatId4);
            }
            if (!chatId5.equals("")) {
                Thread.sleep(chatInterval);
                this.a(this.b, chatId5);
            }
            if (this.c.l()) {
                e.a().a(this, this.c.m());
            }
        }
        catch (Throwable e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private void a(t npc, String text) {
        if (this.c.b() == 0 && npc.eX()) {
            return;
        }
        if (this.c.b() == 1 && !npc.eX()) {
            return;
        }
        if (this.c.b() == 2 && npc.eX()) {
            return;
        }
        if (!text.contains("$")) {
            npc.b(new ds(Integer.parseInt(text)));
            return;
        }
        if (this.c.k()) {
            aq.a().a(new cg(npc, text, 3));
            return;
        }
        if (this.c.j()) {
            npc.d(new cg(npc, text, 2));
            return;
        }
        if (this.c.o()) {
            npc.b(new cg(npc, text, 21));
            return;
        }
        npc.b(new cg(npc, text, 0));
    }
}

