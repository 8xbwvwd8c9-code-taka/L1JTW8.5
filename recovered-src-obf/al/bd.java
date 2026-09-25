/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.at;
import ao.bg;
import ap.u;
import aq.ag;
import aq.am;
import be.ei;
import java.util.HashMap;
import java.util.StringTokenizer;

public class bd
implements l {
    private static final HashMap<Integer, Integer> a = new HashMap();

    private bd() {
    }

    public static l a() {
        return new bd();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            if (!a.containsKey(pc.fr())) {
                a.put(pc.fr(), 0);
            }
            int id = a.get(pc.fr());
            if (arg.isEmpty() || arg.equals("+")) {
                ++id;
            } else if (arg.equals("-")) {
                --id;
            } else {
                StringTokenizer st = new StringTokenizer(arg);
                id = Integer.parseInt(st.nextToken());
            }
            ag spawn = at.a().a(id);
            if (spawn == null) {
                spawn = bg.a().a(id);
            }
            if (spawn != null) {
                am.a(pc, spawn.f(), spawn.g(), spawn.n(), 5, true);
                pc.a(new ei("spawnid(" + id + ")\u5df2\u50b3\u9001\u5230"));
            } else {
                pc.a(new ei("spawnid(" + id + ")\u627e\u4e0d\u5230"));
            }
            a.put(pc.fr(), id);
        }
        catch (Exception exception) {
            pc.a(new ei(String.valueOf(cmdName) + " spawnid|+|-"));
        }
    }
}

