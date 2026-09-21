/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class ds
extends eu {
    public ds(String name, q item) {
        this.c(115);
        this.b(3736);
        this.a(item.s());
        System.out.println(item.s());
        this.a(item.e());
        this.a(name);
        this.b(0);
    }

    public ds(int type, int val) {
        this.c(115);
        this.b(type);
        this.a(val);
    }

    public ds(int type, String s2, int val) {
        this.c(115);
        this.b(type);
        this.a(s2);
        this.a(val);
        this.b(0);
    }

    public ds(int type) {
        this.c(115);
        this.b(type);
        this.c(0);
        if (type == 418) {
            this.b(2560);
        }
    }

    public ds(int type, String ... msgs) {
        this.c(115);
        this.b(type);
        this.c(msgs.length);
        String[] stringArray = msgs;
        int n2 = msgs.length;
        int n3 = 0;
        while (n3 < n2) {
            String msg = stringArray[n3];
            this.a(msg);
            ++n3;
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ServerMessage";
    }
}

