/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import bh.a;
import bj.d;

public class t
extends eu {
    public t(int value, d client) {
        a account = client.e();
        int characterSlot = account.k();
        int maxAmount = l1j.server.a.au + characterSlot;
        this.c(50);
        this.c(value);
        this.c(maxAmount);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_CharAmount";
    }
}

