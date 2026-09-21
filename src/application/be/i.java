/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class i
extends eu {
    public i(int objid, ArrayList<bh.i> sellList) {
        this.c(227);
        this.a(objid);
        this.b(sellList.size());
        for (bh.i house : sellList) {
            this.a(house.b());
            this.a(house.c());
            this.b(house.d());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(house.j().getTime()));
            this.c(cal.get(2) + 1);
            this.c(cal.get(5));
            this.a(house.k());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_AuctionBoard";
    }
}

