/*
 * Decompiled with CFR 0.152.
 */
package al;

import ai.b;
import al.l;
import ap.u;
import be.ei;
import java.util.HashMap;
import java.util.StringTokenizer;

public class s
implements l {
    private static final HashMap<Integer, String> a = new HashMap();

    private s() {
    }

    public static l a() {
        return new s();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            if (!a.containsKey(pc.fr())) {
                a.put(pc.fr(), "");
            }
            String faviCom = a.get(pc.fr());
            if (arg.startsWith("set")) {
                StringTokenizer st = new StringTokenizer(arg);
                st.nextToken();
                if (!st.hasMoreTokens()) {
                    pc.a(new ei("\u6307\u4ee4\u4e0d\u5b58\u5728\u3002"));
                    return;
                }
                StringBuilder cmd = new StringBuilder();
                String temp = st.nextToken();
                if (temp.equalsIgnoreCase(cmdName)) {
                    pc.a(new ei(String.valueOf(cmdName) + " \u4e0d\u80fd\u52a0\u5165\u81ea\u5df1\u7684\u540d\u5b57\u3002"));
                    return;
                }
                cmd.append(String.valueOf(temp) + " ");
                while (st.hasMoreTokens()) {
                    cmd.append(String.valueOf(st.nextToken()) + " ");
                }
                faviCom = cmd.toString().trim();
                a.put(pc.fr(), faviCom);
                pc.a(new ei(String.valueOf(faviCom) + " \u88ab\u767b\u8a18\u5728\u597d\u53cb\u540d\u55ae\u3002"));
            } else if (arg.startsWith("show")) {
                pc.a(new ei("\u76ee\u524d\u767b\u8a18\u7684\u6307\u4ee4: " + faviCom));
            } else if (faviCom.isEmpty()) {
                pc.a(new ei("\u6c92\u6709\u88ab\u767b\u8a18\u7684\u540d\u5b57\u3002"));
            } else {
                StringBuilder cmd = new StringBuilder();
                StringTokenizer st = new StringTokenizer(arg);
                StringTokenizer st2 = new StringTokenizer(faviCom);
                while (st2.hasMoreTokens()) {
                    String temp = st2.nextToken();
                    if (temp.startsWith("%")) {
                        cmd.append(String.valueOf(st.nextToken()) + " ");
                        continue;
                    }
                    cmd.append(String.valueOf(temp) + " ");
                }
                while (st.hasMoreTokens()) {
                    cmd.append(String.valueOf(st.nextToken()) + " ");
                }
                pc.a(new ei(cmd + " \u5be6\u884c\u3002"));
                b.a().a(pc, cmd.toString());
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " set \u73a9\u5bb6\u540d\u7a31 " + "| " + cmdName + " show | " + cmdName + " [\u6578\u91cf]\u3002"));
        }
    }
}

