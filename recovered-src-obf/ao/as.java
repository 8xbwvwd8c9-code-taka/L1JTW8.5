/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bh.m;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class as {
    private static final Logger a = Logger.getLogger(as.class.getName());
    private static as b;
    private final HashMap<Integer, m> c = new HashMap();
    private final HashMap<Integer, m> d = new HashMap();
    private final HashMap<Integer, m> e = new HashMap();

    public static as a() {
        if (b == null) {
            b = new as();
        }
        return b;
    }

    private as() {
        this.b();
    }

    private void b() {
        block8: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM npcchat");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        m npcChat = new m();
                        npcChat.a(rs.getInt("npc_id"));
                        npcChat.b(rs.getInt("chat_timing"));
                        npcChat.c(rs.getInt("start_delay_time"));
                        npcChat.a(rs.getString("chat_id1"));
                        npcChat.b(rs.getString("chat_id2"));
                        npcChat.c(rs.getString("chat_id3"));
                        npcChat.d(rs.getString("chat_id4"));
                        npcChat.e(rs.getString("chat_id5"));
                        npcChat.d(rs.getInt("chat_interval"));
                        npcChat.a(rs.getBoolean("is_shout"));
                        npcChat.b(rs.getBoolean("is_world_chat"));
                        npcChat.c(rs.getBoolean("is_repeat"));
                        npcChat.e(rs.getInt("repeat_interval"));
                        npcChat.f(rs.getInt("chance"));
                        npcChat.d(rs.getBoolean("is_screen_only"));
                        if (npcChat.b() == 0) {
                            this.c.put(new Integer(npcChat.a()), npcChat);
                            continue;
                        }
                        if (npcChat.b() == 1) {
                            this.d.put(new Integer(npcChat.a()), npcChat);
                            continue;
                        }
                        if (npcChat.b() != 2) continue;
                        this.e.put(new Integer(npcChat.a()), npcChat);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block8;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public m a(int i2) {
        return this.c.get(new Integer(i2));
    }

    public m b(int i2) {
        return this.d.get(new Integer(i2));
    }

    public m c(int i2) {
        return this.e.get(new Integer(i2));
    }
}

