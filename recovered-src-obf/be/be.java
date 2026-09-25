/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class be
extends eu {
    public static final String a = "party";
    public static final String b = "monlist";
    public static final String c = "anicom";
    public static final String d = "noseeb";
    public static final String e = "nosell";
    public static final String f = "agsel";
    public static final String g = "";

    public be(int objid, String htmlid, String ... data) {
        this.a(objid, htmlid, data);
    }

    public be(int objid, String htmlid) {
        this.a(objid, htmlid, g);
    }

    private void a(int objid, String htmlid, String ... data) {
        this.c(110);
        this.a(objid);
        this.a(htmlid);
        this.c(0);
        if (data == null || data.length == 0) {
            this.b(0);
            this.b(0);
            return;
        }
        this.b(data.length);
        String[] stringArray = data;
        int n2 = data.length;
        int n3 = 0;
        while (n3 < n2) {
            String s2 = stringArray[n3];
            this.a(s2);
            ++n3;
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Html";
    }
}

