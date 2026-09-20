/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class af {
    private static final Logger o = Logger.getLogger(af.class.getName());
    public static final int a = 1;
    public static final int b = 10;
    public static final int c = 11;
    public static final int d = 35;
    public static final int e = 36;
    public static final int f = 37;
    public static final int g = 38;
    public static final int h = 40;
    public static final int i = 41;
    public static final int j = 42;
    public static final int k = 43;
    public static final int l = 44;
    public static final int m = 45;
    public static final int n = 255;
    private final u p;
    private final HashMap<Integer, Integer> q = new HashMap();

    public af(u owner) {
        this.p = owner;
    }

    public void a() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_quests WHERE char_id=?");
                    pstm.setInt(1, this.p.fr());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int questid = rs.getInt("quest_id");
                        int step = rs.getInt("quest_step");
                        this.q.put(questid, step);
                    }
                }
                catch (SQLException e2) {
                    o.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    public int a(int quest_id) {
        if (!this.q.containsKey(quest_id)) {
            return 0;
        }
        return this.q.get(quest_id);
    }

    public void a(int quest_id, int step) {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    if (this.q.containsKey(quest_id)) {
                        pstm = con.prepareStatement("UPDATE character_quests SET quest_step = ? WHERE char_id = ? AND quest_id = ?");
                        pstm.setInt(1, step);
                        pstm.setInt(2, this.p.fr());
                        pstm.setInt(3, quest_id);
                        pstm.execute();
                    } else {
                        pstm = con.prepareStatement("INSERT INTO character_quests SET char_id = ?, quest_id = ?, quest_step = ?");
                        pstm.setInt(1, this.p.fr());
                        pstm.setInt(2, quest_id);
                        pstm.setInt(3, step);
                        pstm.execute();
                    }
                }
                catch (SQLException e2) {
                    o.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(pstm);
                bi.j.a(con);
                throw throwable;
            }
            bi.j.a(pstm);
            bi.j.a(con);
        }
        this.q.put(quest_id, step);
    }

    public void b(int quest_id) {
        this.a(quest_id, 255);
    }

    public boolean c(int quest_id) {
        return this.a(quest_id) == 255;
    }
}

