/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class et
extends eu {
    public et(u pc) {
        String lawfulness = "";
        int lawful = pc.fa();
        if (lawful < 0) {
            lawfulness = "(Chaotic)";
        } else if (lawful >= 0 && lawful < 500) {
            lawfulness = "(Neutral)";
        } else if (lawful >= 500) {
            lawfulness = "(Lawful)";
        }
        this.c(107);
        this.c(8);
        String title = "";
        String clan = "";
        if (!pc.eZ().equalsIgnoreCase("")) {
            title = String.valueOf(pc.eZ()) + " ";
        }
        if (pc.aF() > 0) {
            clan = "[" + pc.aG() + "]";
        }
        this.a(String.valueOf(title) + pc.et() + " " + lawfulness + " " + clan);
        this.a(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_WhoCharinfo";
    }
}

