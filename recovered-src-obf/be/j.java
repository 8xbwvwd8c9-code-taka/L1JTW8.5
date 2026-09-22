/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.ab;
import be.eu;
import bh.i;
import java.sql.Date;
import java.util.Calendar;

public class j
extends eu {
    public j(int objectId, String house_number) {
        int houseId = Integer.valueOf(house_number);
        i house = ab.a().a(houseId);
        this.c(110);
        this.a(objectId);
        this.a("agsel");
        this.a(house_number);
        if (house == null || house.j() == null) {
            this.b(0);
            return;
        }
        this.b(9);
        this.a(house.c());
        this.a(house.e());
        this.a("" + house.d());
        this.a(house.l());
        this.a(house.n());
        this.a("" + house.k());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(house.j().getTime()));
        this.a("" + (cal.get(2) + 1));
        this.a("" + cal.get(5));
        this.a("" + cal.get(11));
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_AuctionBoardRead";
    }
}

