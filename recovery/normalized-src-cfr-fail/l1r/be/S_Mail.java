/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.util.ArrayList;
import l1r.ao.MailTable;
import l1r.ap.L1PcInstance;
import l1r.be.ServerBasePacket;
import l1r.bh.L1Mail;

public class S_Mail
extends ServerBasePacket {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;

    public S_Mail(L1PcInstance pc, int type) {
        ArrayList<L1Mail> mails = MailTable.a().a(pc.fr(), type);
        this.c(205);
        this.c(type);
        this.b(mails.size());
        for (L1Mail mail : mails) {
            this.a(mail.a());
            this.c(mail.f());
            this.a((int)(mail.e().getTime() / 1000L));
            this.c(mail.c().equalsIgnoreCase(pc.et()) ? 1 : 0);
            this.a(mail.c().equalsIgnoreCase(pc.et()) ? mail.d() : mail.c());
            this.a(mail.g());
        }
    }

    public S_Mail(L1PcInstance pc, L1Mail mail, boolean isDraft) {
        this.c(205);
        this.c(mail.b() == 0 ? 80 : 81);
        this.a(mail.a());
        this.c(isDraft ? 1 : 0);
        this.a(pc.et());
        this.a(mail.g());
    }

    public S_Mail(int type, boolean isSuccess) {
        this.c(205);
        this.c(type);
        this.c(isSuccess ? 1 : 0);
    }

    public S_Mail(L1Mail mail, int type) {
        if (type == 48 || type == 49 || type == 50 || type == 64) {
            this.c(205);
            this.c(type);
            this.a(mail.a());
            this.c(1);
            return;
        }
        this.c(205);
        this.c(type);
        this.a(mail.a());
        this.a(mail.h());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Mail";
    }
}
