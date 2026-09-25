/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ak.a;
import al.l;
import ap.f;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import be.ak;
import be.as;
import be.c;
import be.cm;
import be.ds;
import be.dx;
import be.ee;
import be.ei;
import bh.e;
import bi.g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private static b b;
    private static HashMap<Integer, String> c;

    static {
        c = new HashMap();
    }

    private b() {
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private boolean a(u pc, String name, String arg) {
        e command;
        block5: {
            block4: {
                try {
                    command = ak.a.a(name);
                    if (command != null) break block4;
                    return false;
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    return false;
                }
            }
            if (pc.az() >= command.b()) break block5;
            pc.a(new ds(74, "\u6307\u4ee4" + name));
            return true;
        }
        l exe = g.c(command.c());
        exe.a(pc, name, arg);
        System.out.println(String.valueOf(pc.et()) + "\u4f7f\u7528 ." + name + " " + arg + "\u7684\u6307\u4ee4\u3002");
        return true;
    }

    public void a(u gm2, String cmdLine) {
        StringTokenizer token = new StringTokenizer(cmdLine);
        if (!token.hasMoreTokens()) {
            gm2.a(new ei("\u6307\u4ee4\u53c3\u6578\u4e0d\u8db3\u3002"));
            return;
        }
        String cmd = token.nextToken();
        String param = "";
        while (token.hasMoreTokens()) {
            param = param + token.nextToken() + ' ';
        }
        param = param.trim();
        gm2.a(new be.a(3, 1));
        if (cmd.equalsIgnoreCase("1")) {
            StringTokenizer st = new StringTokenizer(param);
            if (!st.hasMoreTokens()) {
                gm2.a(new ei("\u6307\u4ee4\u53c3\u6578\u4e0d\u8db3\u3002"));
                return;
            }
            int val = Integer.parseInt(st.nextToken());
            q item = gm2.j().b(40308);
            gm2.a(new as(item.fr(), val, true));
            return;
        }
        if (cmd.equalsIgnoreCase("ca")) {
            StringTokenizer st = new StringTokenizer(param);
            if (st.countTokens() < 2) {
                gm2.a(new ei("\u6307\u4ee4\u53c3\u6578\u4e0d\u8db3\u3002"));
                return;
            }
            int val = Integer.parseInt(st.nextToken());
            int val2 = Integer.parseInt(st.nextToken());
            gm2.a(new ee(gm2.fr(), val));
            gm2.a(new ak(gm2.fr(), val2));
            return;
        }
        if (cmd.equalsIgnoreCase("6")) {
            q item = gm2.j().b(40308);
            int i2 = 0;
            while (i2 < 256) {
                gm2.a(new c(item, i2));
                ++i2;
            }
        } else {
            if (cmd.equalsIgnoreCase("4")) {
                StringTokenizer st = new StringTokenizer(param);
                if (!st.hasMoreTokens()) {
                    gm2.a(new ei("\u6307\u4ee4\u53c3\u6578\u4e0d\u8db3\u3002"));
                    return;
                }
                int val = Integer.parseInt(st.nextToken());
                f[] fArray = ao.t.b().c();
                int n2 = fArray.length;
                int n3 = 0;
                while (n3 < n2) {
                    f door = fArray[n3];
                    if (door.fe() == val) {
                        am.a(gm2, door.fs(), door.ft(), door.fp(), 0, true);
                        break;
                    }
                    ++n3;
                }
                return;
            }
            if (cmd.equalsIgnoreCase("3")) {
                for (aa object : aq.a().b((aa)gm2, 3)) {
                    t npc;
                    if (!(object instanceof t) || (npc = (t)object).z() != 45060) continue;
                    npc.aa_();
                }
                return;
            }
            if (cmd.equalsIgnoreCase("2")) {
                try {
                    File mobDataFile = new File("./Result.txt");
                    LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(mobDataFile)));
                    String line = null;
                    String s2 = "";
                    while ((line = lnr.readLine()) != null) {
                        line = line.trim();
                        s2 = String.valueOf(s2) + line + " ";
                        if (!line.startsWith("#") && !line.startsWith(" ") && line.length() != 0) continue;
                    }
                    String[] ss = s2.trim().split(" ");
                    byte[] data = new byte[ss.length];
                    int i3 = 0;
                    while (i3 < ss.length) {
                        data[i3] = this.a(ss[i3])[0];
                        ++i3;
                    }
                    gm2.a(new cm(data));
                    lnr.close();
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("0")) {
                gm2.j(1026, 1200000);
                gm2.a(new dx(gm2.fr(), 5, 1200));
                gm2.b(new dx(gm2.fr(), 5, 1200));
                gm2.cv(5);
                gm2.a(new ee(gm2.fr(), 12360));
                gm2.b(new ee(gm2.fr(), 12360));
                return;
            }
            if (cmd.equalsIgnoreCase("cp")) {
                l1j.server.a.d = !l1j.server.a.d;
                gm2.a(new ei("Config.C_PACKET= " + l1j.server.a.d));
                return;
            }
            if (cmd.equalsIgnoreCase("sp")) {
                l1j.server.a.e = !l1j.server.a.e;
                gm2.a(new ei("Config.S_PACKET= " + l1j.server.a.e));
                return;
            }
            if (cmd.equalsIgnoreCase("clearinv")) {
                for (q item : gm2.j().d()) {
                    if (item.N() == 640104 || item.N() == 640382 || item.N() == 40308 || item.N() == 640268 || item.N() == 640312 || item.N() == 640621 || item.N() == 640769 || item.G() >= 5 || item.D()) continue;
                    gm2.j().f(item);
                }
                gm2.a(new ei("Inventory clear !!"));
                return;
            }
        }
        if (this.a(gm2, cmd, param)) {
            if (!cmd.equalsIgnoreCase("r")) {
                c.put(gm2.fr(), cmdLine);
            }
            return;
        }
        if (cmd.equalsIgnoreCase("r")) {
            if (!c.containsKey(gm2.fr())) {
                gm2.a(new ds(74, "\u6307\u4ee4" + cmd));
                return;
            }
            this.b(gm2, param);
            return;
        }
        gm2.a(new ei("\u6307\u4ee4 " + cmd + " \u4e0d\u5b58\u5728\u3002"));
    }

    private void b(u pc, String arg) {
        try {
            String lastCmd = c.get(pc.fr());
            if (arg.isEmpty()) {
                pc.a(new ei("\u6307\u4ee4 " + lastCmd + " \u91cd\u65b0\u57f7\u884c\u3002"));
                this.a(pc, lastCmd);
            } else {
                StringTokenizer token = new StringTokenizer(lastCmd);
                String cmd = String.valueOf(token.nextToken()) + " " + arg;
                pc.a(new ei("\u6307\u4ee4 " + cmd + " \u57f7\u884c\u3002"));
                this.a(pc, cmd);
            }
        }
        catch (Exception e2) {
            pc.a(new ei(".r \u6307\u4ee4\u932f\u8aa4\u3002"));
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private byte[] a(String hexString) {
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        int i2 = 0;
        while (i2 < length) {
            int low;
            int high = Character.digit(hex[i2 * 2], 16);
            int value = high << 4 | (low = Character.digit(hex[i2 * 2 + 1], 16));
            if (value > 127) {
                value -= 256;
            }
            rawData[i2] = (byte)value;
            ++i2;
        }
        return rawData;
    }
}

