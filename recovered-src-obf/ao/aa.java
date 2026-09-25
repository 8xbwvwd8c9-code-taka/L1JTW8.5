/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.af;
import ap.q;
import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class aa {
    private static final Logger a = Logger.getLogger(aa.class.getName());
    private static aa b;

    public static aa a() {
        if (b == null) {
            b = new aa();
        }
        return b;
    }

    private aa() {
        long begin = System.currentTimeMillis();
        System.out.print("cleaning History Tables...");
        this.a("history_chat", 3);
        this.a("history_enchant", 7);
        this.a("history_trade", 7);
        this.a("history_pickup", 7);
        this.a("history_resolvent", 7);
        this.a("history_sell", 3);
        this.a("history_warehouse", 3);
        this.a("history_warehouse_clan", 7);
        this.a("history_warehouse_elf", 7);
        this.a("history_world_shop", 15);
        System.out.println("OK! " + (System.currentTimeMillis() - begin) + " ms");
    }

    public void a(u pc, String type, q item) {
        this.a("history_enchant", pc, type, item, 1);
    }

    public void a(u pc, String type, q item, int count) {
        this.a("history_give", pc, type, item, count);
    }

    public void b(u pc, String type, q item, int count) {
        this.a("history_trade", pc, type, item, count);
    }

    public void c(u pc, String type, q item, int count) {
        this.a("history_pickup", pc, type, item, count);
    }

    public void b(u pc, String type, q item) {
        this.a("history_resolvent", pc, type, item, 1);
    }

    public void d(u pc, String type, q item, int count) {
        this.a("history_sell", pc, type, item, count);
    }

    public void e(u pc, String type, q item, int count) {
        this.a("history_warehouse", pc, type, item, count);
    }

    public void f(u pc, String type, q item, int count) {
        this.a("history_warehouse_clan", pc, type, item, count);
    }

    public void g(u pc, String type, q item, int count) {
        this.a("history_warehouse_elf", pc, type, item, count);
    }

    public void h(u pc, String type, q item, int count) {
        this.a("history_world_shop", pc, type, item, count);
    }

    private void a(String tableName, u pc, String type, q item, int count) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO " + tableName + " SET account=?, char_name=?, type=?, itemid=?, description=?, record_time=?");
                    pstm.setString(1, pc.bc());
                    pstm.setString(2, pc.eu());
                    pstm.setString(3, type);
                    pstm.setInt(4, item.N());
                    String description = String.valueOf(this.a(item)) + " " + count + "\u500b";
                    pstm.setString(5, description);
                    pstm.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    public void a(u pc, String type, String description) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO history_chat SET account=?, char_name=?, type=?, description=?, record_time=?");
                    pstm.setString(1, pc.bc());
                    pstm.setString(2, pc.eu());
                    pstm.setString(3, type);
                    pstm.setString(4, description);
                    pstm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    private void a(String tableName, int day) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM " + tableName + " WHERE  record_time < ?");
                    pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis() - (long)(day * 86400 * 1000)));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    private String a(q item) {
        int attrEnchantLevel;
        StringBuilder name = new StringBuilder();
        if (item.F() == 0) {
            name.append("\u795d\u798f\u7684 ");
        } else if (item.F() == 2) {
            name.append("\u8a5b\u5492\u7684 ");
        }
        if (item.g() && (attrEnchantLevel = item.L()) > 0) {
            if (item.K() == 1) {
                name.append((new String[]{"", "\u5730\u4e4b", "\u5d29\u88c2", "\u5730\u9748", "\u8f1d\u5ca9", "\u99ac\u666e\u52d2"})[attrEnchantLevel]);
            } else if (item.K() == 2) {
                name.append((new String[]{"", "\u706b\u4e4b", "\u70c8\u7130", "\u706b\u9748", "\u8d64\u708e", "\u5e15\u683c\u91cc\u5967"})[attrEnchantLevel]);
            } else if (item.K() == 4) {
                name.append((new String[]{"", "\u6c34\u4e4b", "\u6d77\u562f", "\u6c34\u9748", "\u971c\u51cd", "\u4f0a\u5a03"})[attrEnchantLevel]);
            } else if (item.K() == 8) {
                name.append((new String[]{"", "\u98a8\u4e4b", "\u66b4\u98a8", "\u98a8\u9748", "\u84bc\u5d50", "\u6c99\u54c8"})[attrEnchantLevel]);
            }
        }
        if (item.g() || item.h()) {
            if (item.G() >= 0) {
                name.append("+" + item.G() + " ");
            } else if (item.G() < 0) {
                name.append(String.valueOf(String.valueOf(item.G())) + " ");
            }
        }
        if (item.N() == 21363 && item.G() >= 10) {
            name.append("\u7d55\u5c0d\u596a\u9b42T\u6064(\u9b54\u6cd5)");
        } else if (item.N() == 21364 && item.G() >= 10) {
            name.append("\u7d55\u5c0d\u596a\u9b42T\u6064(\u8fd1\u6230)");
        } else if (item.N() == 21365 && item.G() >= 10) {
            name.append("\u7d55\u5c0d\u596a\u9b42T\u6064(\u9060\u653b)");
        } else {
            name.append(item.b());
        }
        if (item.a().aM() > 0) {
            name.append(" (" + item.I() + ")");
        }
        if (item.N() == 20383) {
            name.append(" (" + item.I() + ")");
        }
        if (item.a().T() > 0 && !item.f()) {
            name.append(" [" + item.M() + "]");
        }
        if (item.N() == 640615 && item.M() != 0) {
            name.append(" -" + (item.M() - 1399));
        }
        if (item.bb() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(" [MM-dd HH:mm]");
            String formatDate = sdf.format(item.bb());
            name.append(formatDate);
        }
        if (item.N() == 40312 && item.M() != 0) {
            name.append(af.a(item));
        }
        return name.toString();
    }
}

