/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import l1r.ao.ShopWorldTable;
import l1r.ap.L1ItemInstance;
import l1r.be.ServerBasePacket;

public class S_CharEvent
extends ServerBasePacket {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 4;
    public static final int e = 10;
    public static final int f = 12;
    public static final int g = 36;
    public static final int h = 37;
    public static final int i = 42;
    public static final int j = 48;
    public static final int k = 49;
    public static final int l = 60;
    public static final int m = 64;
    public static final int n = 65;
    public static final int o = 66;
    public static final int p = 67;
    public static final int q = 68;
    public static final int r = 72;
    public static final int s = 73;

    public S_CharEvent(int type) {
        this.c(42);
        this.c(type);
        if (type == 10) {
            this.a(2);
        }
    }

    public S_CharEvent(int type, int value) {
        this.c(42);
        this.c(type);
        switch (type) {
            case 72: {
                this.a(value);
                this.a((int)(new Date().getTime() / 1000L));
                this.a((int)(new Date().getTime() / 1000L) + 31536000);
                this.b(1);
                break;
            }
            case 37: {
                this.a(value);
                this.a(0);
                break;
            }
            case 48: 
            case 49: {
                this.a(value);
            }
        }
    }

    public S_CharEvent(int type, int objid, int emblemId) {
        this.c(42);
        this.c(type);
        this.a(objid);
        this.a(emblemId);
        this.b(0);
    }

    public S_CharEvent(HashMap<Integer, ShopWorldTable.L1R_b> list) {
        this.c(42);
        this.c(36);
        this.c(1);
        this.c(1);
        this.c(1);
        this.b(list.size());
        ArrayList<ShopWorldTable.L1R_b> arrayList = new ArrayList<ShopWorldTable.L1R_b>(list.values());
        Collections.sort(arrayList, new Comparator<ShopWorldTable.L1R_b>(){

            public int a(ShopWorldTable.L1R_b d1, ShopWorldTable.L1R_b d2) {
                if (d2.f && d1.f) {
                    return d2.a.N() - d1.a.N();
                }
                return (d2.f ? 1 : 0) - (d1.f ? 1 : 0);
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((ShopWorldTable.L1R_b)object, (ShopWorldTable.L1R_b)object2);
            }
        });
        for (ShopWorldTable.L1R_b data : arrayList) {
            L1ItemInstance item = data.a;
            this.a(item.N());
            this.b(item.e());
            this.b(item.m());
            this.c(0);
            this.a(data.b);
            this.a(item.b());
            this.c(23);
            this.c(item.a().k());
            this.c(0);
            this.c(data.c);
            this.c(data.f ? 1 : 0);
            byte[] b = new byte[20];
            this.a(b);
            this.c(data.d);
            this.c(data.e ? 1 : 0);
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_CharEvent";
    }
}
