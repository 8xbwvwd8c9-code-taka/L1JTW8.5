/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.an;
import ao.o;
import ao.q;
import ap.u;
import aq.aq;
import aq.i;
import be.bw;
import be.ds;
import be.ee;
import bh.k;
import bj.d;
import java.util.logging.Level;
import java.util.logging.Logger;

public class bg
extends cv {
    private static final Logger a = Logger.getLogger(bg.class.getName());

    public bg(byte[] abyte0, d client) {
        block21: {
            int type;
            u pc;
            block26: {
                block25: {
                    block24: {
                        block23: {
                            block22: {
                                block20: {
                                    super(abyte0);
                                    pc = client.f();
                                    if (pc == null) {
                                        return;
                                    }
                                    type = this.c();
                                    if (type != 0 && type != 1 && type != 2) break block20;
                                    pc.a(new bw(pc, type));
                                    break block21;
                                }
                                if (type != 16 && type != 17 && type != 18) break block22;
                                int mailId = this.b();
                                k mail = an.a().c(mailId);
                                if (mail.f() == 0) {
                                    mail.c(1);
                                    an.a().a(mailId);
                                }
                                pc.a(new bw(mail, type));
                                break block21;
                            }
                            if (type != 32) break block23;
                            if (!pc.j().b(40308, 50)) {
                                pc.a(new ds(189));
                                return;
                            }
                            this.d();
                            String receiverName = this.g();
                            byte[] text = this.h();
                            u receiver = aq.a().a(receiverName);
                            if (receiver != null) {
                                if (this.a(receiver, bw.a) >= 40) {
                                    pc.a(new bw(type, false));
                                    return;
                                }
                                k mail = an.a().a(bw.a, receiver, pc, text, true);
                                pc.a(new bw(pc, mail, true));
                                k mail2 = an.a().a(bw.a, receiver, pc, text, false);
                                receiver.a(new bw(receiver, mail2, false));
                                receiver.b(new ee(receiver.fr(), 1091));
                            } else {
                                try {
                                    u restorePc = o.a().a(receiverName);
                                    if (restorePc == null) {
                                        pc.a(new bw(type, false));
                                        pc.a(new ds(109, receiverName));
                                        return;
                                    }
                                    if (this.a(restorePc, bw.a) >= 40) {
                                        pc.a(new bw(type, false));
                                        return;
                                    }
                                    k mail = an.a().a(bw.a, restorePc, pc, text, true);
                                    pc.a(new bw(pc, mail, true));
                                    an.a().a(bw.a, restorePc, pc, text, false);
                                }
                                catch (Exception e2) {
                                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                                }
                            }
                            pc.a(new bw(type, true));
                            break block21;
                        }
                        if (type != 33) break block24;
                        if (pc.aF() == 0) {
                            pc.a(new ds(1262));
                            return;
                        }
                        if (!pc.j().b(40308, 1000)) {
                            pc.a(new ds(189));
                            return;
                        }
                        this.d();
                        String clanName = this.g();
                        byte[] text = this.h();
                        i clan = q.a().c(clanName);
                        if (clan == null) {
                            pc.a(new ds(3982));
                            return;
                        }
                        for (String name : clan.p()) {
                            u member = aq.a().a(name);
                            if (member != null) {
                                int size = this.a(member, bw.b);
                                if (size >= 80) continue;
                                k mail = an.a().a(bw.b, member, pc, text, false);
                                member.a(new bw(member, mail, false));
                                member.b(new ee(member.fr(), 1091));
                                continue;
                            }
                            try {
                                u restorePc = o.a().a(name);
                                if (restorePc == null || this.a(restorePc, bw.b) >= 80) continue;
                                an.a().a(bw.b, restorePc, pc, text, false);
                            }
                            catch (Exception e3) {
                                a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                            }
                        }
                        break block21;
                    }
                    if (type != 64) break block25;
                    int mailId = this.b();
                    k mail = an.a().c(mailId);
                    mail.b(bw.c);
                    pc.a(new bw(mail, type));
                    an.a().a(mail);
                    break block21;
                }
                if (type != 48 && type != 49 && type != 50) break block26;
                int mailId = this.b();
                k mail = an.a().c(mailId);
                if (mail == null) {
                    return;
                }
                pc.a(new bw(mail, type));
                an.a().b(mailId);
                break block21;
            }
            if (type != 96 && type != 97 && type != 98) break block21;
            int count = this.b();
            int i2 = 0;
            while (i2 < count) {
                int mailId = this.b();
                k mail = an.a().c(mailId);
                if (mail != null) {
                    pc.a(new bw(mail, mail.b() + 48));
                    an.a().b(mailId);
                }
                ++i2;
            }
        }
    }

    private int a(u pc, int type) {
        return an.a().a(pc.fr(), type).size();
    }

    @Override
    public String a() {
        return "C_Mail";
    }
}

