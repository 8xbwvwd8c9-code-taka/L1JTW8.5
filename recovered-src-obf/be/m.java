/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import bh.b;

public class m
extends eu {
    public m(int number) {
        this.e(number);
    }

    private void e(int number) {
        b topic = b.a(number);
        this.c(138);
        this.a(number);
        this.a(topic.b());
        this.a(topic.d());
        this.a(topic.c());
        this.a(topic.e());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_BoardRead";
    }
}

