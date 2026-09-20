/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.bq;
import be.ck;
import be.ds;
import be.ei;
import java.util.StringTokenizer;

public class az
implements l {
    private az() {
    }

    public static l a() {
        return new az();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String char_name = st.nextToken();
            String param = st.nextToken();
            int value = Integer.parseInt(st.nextToken());
            u target = null;
            target = char_name.equalsIgnoreCase("me") ? pc : aq.a().a(char_name);
            if (target == null) {
                pc.a(new ds(73, char_name));
                return;
            }
            if (param.equalsIgnoreCase("AC")) {
                target.bL((byte)(value - target.ey()));
            } else if (param.equalsIgnoreCase("MR")) {
                target.co((short)(value - target.W_()));
            } else if (param.equalsIgnoreCase("HIT")) {
                target.cm((short)(value - target.eT()));
            } else if (param.equalsIgnoreCase("DMG")) {
                target.ck((short)(value - target.eR()));
            } else {
                if (param.equalsIgnoreCase("HP")) {
                    target.m(value - target.bd());
                    target.bx(target.ew());
                } else if (param.equalsIgnoreCase("MP")) {
                    target.n(value - target.be());
                    target.by(target.ex());
                } else if (param.equalsIgnoreCase("LAWFUL")) {
                    target.cr(value);
                    bq s_lawful = new bq(target.fr(), target.fa());
                    target.a(s_lawful);
                    target.b(s_lawful);
                } else if (param.equalsIgnoreCase("KARMA")) {
                    target.A(value);
                } else if (param.equalsIgnoreCase("GM")) {
                    if (value > 200) {
                        value = 200;
                    }
                    target.ae(value);
                    target.a(new ei(String.valueOf(pc.et()) + "\u8ce6\u8207\u4f60GM\u6b0a\u9650\u3002"));
                } else if (param.equalsIgnoreCase("STR")) {
                    target.o(value - target.bf());
                } else if (param.equalsIgnoreCase("CON")) {
                    target.p(value - target.bg());
                } else if (param.equalsIgnoreCase("DEX")) {
                    target.q(value - target.bh());
                } else if (param.equalsIgnoreCase("INT")) {
                    target.s(value - target.bj());
                } else if (param.equalsIgnoreCase("WIS")) {
                    target.t(value - target.bk());
                } else if (param.equalsIgnoreCase("CHA")) {
                    target.r(value - target.bi());
                } else {
                    pc.a(new ei("\u72c0\u614b " + param + " \u4e0d\u660e\u3002"));
                    return;
                }
                target.I();
            }
            target.a(new ck(target));
            pc.a(new ei(String.valueOf(target.et()) + " \u7684" + param + "\u503c" + value + "\u88ab\u8b8a\u66f4\u4e86\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: " + cmdName + " \u73a9\u5bb6\u540d\u7a31|me \u5c6c\u6027 \u8b8a\u66f4\u503c \u3002"));
        }
    }
}

