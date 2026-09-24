/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class n {
    private static final Logger a = Logger.getLogger(n.class.getName());
    private static n b;

    public static n a() {
        if (b == null) {
            b = new n();
        }
        return b;
    }

    public void a(u pc) {
        block8: {
            ResultSet rs;
            PreparedStatement pstm;
            Connection con;
            block6: {
                String[] numbers;
                block7: {
                    con = null;
                    pstm = null;
                    rs = null;
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_mobs_week WHERE login=?");
                    pstm.setString(1, pc.bc());
                    rs = pstm.executeQuery();
                    if (!rs.next()) break block6;
                    numbers = rs.getString("numbers").split(",");
                    if (numbers.length >= 9) break block7;
                    j.a(rs, pstm, con);
                    return;
                }
                try {
                    try {
                        int[][] result = new int[numbers.length][4];
                        String[] counts = rs.getString("counts").split(",");
                        String[] kills = rs.getString("kills").split(",");
                        String[] states = rs.getString("states").split(",");
                        int i2 = 0;
                        while (i2 < numbers.length) {
                            result[i2][0] = Integer.parseInt(numbers[i2]);
                            result[i2][1] = Integer.parseInt(counts[i2]);
                            result[i2][2] = Integer.parseInt(kills[i2]);
                            result[i2][3] = Integer.parseInt(states[i2]);
                            ++i2;
                        }
                        pc.a(result);
                    }
                    catch (Exception e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(rs, pstm, con);
                        break block8;
                    }
                }
                catch (Throwable throwable) {
                    j.a(rs, pstm, con);
                    throw throwable;
                }
            }
            j.a(rs, pstm, con);
        }
    }

    public void b(u pc) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_mobs_week (login,numbers,counts,kills,states) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE numbers=VALUES(numbers), counts=VALUES(counts), kills=VALUES(kills), states=VALUES(states)");
                    String numbers = "";
                    String count = "";
                    String kill = "";
                    String state = "";
                    int i2 = 0;
                    while (i2 < pc.dY().length) {
                        numbers = String.valueOf(numbers) + pc.dY()[i2][0] + ",";
                        count = String.valueOf(count) + pc.dY()[i2][1] + ",";
                        kill = String.valueOf(kill) + pc.dY()[i2][2] + ",";
                        state = String.valueOf(state) + pc.dY()[i2][3] + ",";
                        ++i2;
                    }
                    pstm.setString(1, pc.bc());
                    pstm.setString(2, numbers);
                    pstm.setString(3, count);
                    pstm.setString(4, kill);
                    pstm.setString(5, state);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block6;
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

    public void c(u pc) {
        block7: {
            if (pc.dY() == null) {
                return;
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_mobs_week (login,numbers,counts,kills,states) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE numbers=VALUES(numbers), counts=VALUES(counts), kills=VALUES(kills), states=VALUES(states)");
                    String numbers = "";
                    String count = "";
                    String kill = "";
                    String state = "";
                    int i2 = 0;
                    while (i2 < pc.dY().length) {
                        numbers = String.valueOf(numbers) + pc.dY()[i2][0] + ",";
                        count = String.valueOf(count) + pc.dY()[i2][1] + ",";
                        kill = String.valueOf(kill) + pc.dY()[i2][2] + ",";
                        state = String.valueOf(state) + pc.dY()[i2][3] + ",";
                        ++i2;
                    }
                    pstm.setString(1, pc.bc());
                    pstm.setString(2, numbers);
                    pstm.setString(3, count);
                    pstm.setString(4, kill);
                    pstm.setString(5, state);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block7;
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

    public void b() {
        this.deleteAllDurable();
    }

    public boolean deleteAllDurable() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("DELETE FROM character_mobs_week");
            pstm.executeUpdate();
            return true;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }
}

