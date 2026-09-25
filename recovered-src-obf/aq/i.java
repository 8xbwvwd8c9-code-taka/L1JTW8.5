/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.q;
import ap.u;
import aq.aq;
import au.c;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class i {
    public static final int a = 2;
    public static final int b = 3;
    public static final int c = 4;
    public static final int d = 5;
    public static final int e = 6;
    public static final int f = 12;
    public static final int g = 7;
    public static final int h = 8;
    public static final int i = 9;
    public static final int j = 10;
    public static final int k = 13;
    private int l = 0;
    private String m;
    private Timestamp n;
    private String o;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private String s;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private final CopyOnWriteArrayList<String> w = new CopyOnWriteArrayList();
    private final c x = new c(this);
    private int y = 1;
    private int z = 0;
    private byte[] A = new byte[20];
    private final CopyOnWriteArrayList<Integer> B = new CopyOnWriteArrayList();

    public static boolean a(int rank) {
        return rank >= 3 && rank <= 13 && rank != 7;
    }

    public static boolean b(int rank) {
        int[] accessRank;
        int[] nArray = accessRank = new int[]{10, 9, 4, 3, 6};
        int n2 = accessRank.length;
        int n3 = 0;
        while (n3 < n2) {
            int access = nArray[n3];
            if (rank == access) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public boolean a() {
        return this.t != 0;
    }

    public void a(String member_name) {
        if (!this.w.contains(member_name)) {
            this.w.add(member_name);
        }
    }

    public void b(String member_name) {
        if (this.w.contains(member_name)) {
            this.w.remove(member_name);
        }
    }

    public ArrayList<u> b() {
        ArrayList<u> onlineMembers = new ArrayList<u>();
        for (String name : this.w) {
            u pc = aq.a().a(name);
            if (pc == null || onlineMembers.contains(pc)) continue;
            onlineMembers.add(pc);
        }
        return onlineMembers;
    }

    public u c(String name) {
        if (this.w.contains(name)) {
            return aq.a().a(name);
        }
        return null;
    }

    public c c() {
        return this.x;
    }

    public void d(String s2) {
        String[] ids;
        if (s2 == null) {
            return;
        }
        String[] stringArray = ids = s2.split(",");
        int n2 = ids.length;
        int n3 = 0;
        while (n3 < n2) {
            int clanid;
            String id = stringArray[n3];
            if (id.trim().length() != 0 && !this.B.contains(clanid = Integer.parseInt(id))) {
                this.B.add(clanid);
            }
            ++n3;
        }
    }

    public String d() {
        String text = "";
        for (int id : this.B) {
            if (ao.q.a().a(id) == null) continue;
            text = String.valueOf(text) + id + ",";
        }
        return text;
    }

    public int e() {
        return this.l;
    }

    public void c(int clanId) {
        this.l = clanId;
    }

    public String f() {
        return this.m;
    }

    public void e(String clanName) {
        this.m = clanName;
    }

    public Timestamp g() {
        return this.n;
    }

    public void a(Timestamp foundDate) {
        this.n = foundDate;
    }

    public String h() {
        return this.o;
    }

    public void f(String announcement) {
        this.o = announcement;
    }

    public int i() {
        return this.p;
    }

    public void d(int emblemId) {
        this.p = emblemId;
    }

    public int j() {
        return this.q;
    }

    public void e(int emblemStatus) {
        this.q = emblemStatus;
    }

    public int k() {
        return this.r;
    }

    public void f(int leaderId) {
        this.r = leaderId;
    }

    public String l() {
        return this.s;
    }

    public void g(String leaderName) {
        this.s = leaderName;
    }

    public int m() {
        return this.t;
    }

    public void g(int castleId) {
        this.t = castleId;
    }

    public int n() {
        return this.u;
    }

    public void h(int houseId) {
        this.u = houseId;
    }

    public int o() {
        return this.v;
    }

    public void i(int warehouseUsingChar) {
        this.v = warehouseUsingChar;
    }

    public CopyOnWriteArrayList<String> p() {
        return this.w;
    }

    public int q() {
        return this.y;
    }

    public void j(int joinTypeOpen) {
        this.y = joinTypeOpen;
    }

    public int r() {
        return this.z;
    }

    public void k(int joinType) {
        this.z = joinType;
    }

    public byte[] s() {
        return this.A;
    }

    public void a(byte[] joinCode) {
        this.A = joinCode;
    }

    public CopyOnWriteArrayList<Integer> t() {
        return this.B;
    }
}

