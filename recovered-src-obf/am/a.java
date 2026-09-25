/*
 * Decompiled with CFR 0.152.
 */
package am;

import bi.g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private static a b;
    private final ArrayList<String> c;

    public static a a() {
        if (b == null) {
            b = new a();
        }
        return b;
    }

    private a() {
        block8: {
            this.c = new ArrayList();
            LineNumberReader lnr = null;
            try {
                File mobDataFile = new File("./data/badnames.txt");
                lnr = new LineNumberReader(new BufferedReader(new FileReader(mobDataFile)));
                String line = null;
                while ((line = lnr.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    StringTokenizer st = new StringTokenizer(line, ";");
                    while (st.hasMoreTokens()) {
                        this.c.add(st.nextToken());
                    }
                }
                g.a(lnr);
            }
            catch (FileNotFoundException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                g.a(lnr);
                break block8;
            }
            catch (Exception e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    g.a(lnr);
                    throw throwable;
                }
                g.a(lnr);
                break block8;
            }
            g.a(lnr);
        }
    }

    public boolean a(String name) {
        for (String badName : this.c) {
            if (!name.toLowerCase().contains(badName.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    public String[] b() {
        return this.c.toArray(new String[this.c.size()]);
    }
}

