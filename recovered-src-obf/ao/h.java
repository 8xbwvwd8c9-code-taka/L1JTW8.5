/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class h {
    private static final Logger a = Logger.getLogger(h.class.getName());
    private static final int[] b = new int[]{2, 3, 26, 42, 43, 52, 54, 67, 29, 99, 101, 109, 110, 111, 114, 115, 117, 148, 149, 150, 151, 155, 156, 159, 163, 166, 168, 186, 32, 14, 68, 71, 78, 104, 47, 56, 55, 158, 167, 153, 134, 171, 174, 173, 176, 206, 216, 217, 191, 193, 211, 183, 181, 64, 185, 190, 195, 202, 89, 188, 201, 106, 218, 120, 112, 33, 11, 1007, 1001, 1000, 1016, 1017, 1026, 1027, 1002, 1005, 1031, 1006, 1037, 1038, 3000, 3008, 3001, 3009, 3002, 3010, 3003, 3011, 3004, 3012, 3005, 3013, 3006, 3014, 3016, 3024, 3017, 3025, 3018, 3026, 3019, 3027, 3020, 3028, 3021, 3029, 3022, 3030, 3032, 3040, 3033, 3041, 3034, 3042, 3035, 3043, 3036, 3044, 3037, 3045, 3038, 3046, 3007, 3015, 3023, 3031, 3039, 3047, 4001, 4002, 4003, 4004, 4005, 4007, 4086, 4087, 4088, 4089, 4090, 4091, 4006, 4010, 4008, 4009, 3048, 3049, 3050, 3051, 3052, 3053, 3054, 3055, 3056, 4070, 4078, 4011, 4012, 4077, 4013, 4014, 4015, 4016, 4017, 4018, 4019, 4020, 4021, 4022, 4023, 4024, 4025, 4026, 4027, 4028, 4029, 4030, 4031, 4032, 4033, 4034, 4035, 4036, 4037, 4038, 4039, 4040, 4041, 4042, 4043, 4044, 4045, 4046, 4047, 4048, 4049, 4050, 4051, 4052, 4053, 4054, 4055, 4056, 4057, 4079, 25009, 25010, 25011, 25007, 25012, 4076, 4080, 4092};

    private h() {
    }

    private static void a(int objId, int skillId, int time, int polyId, Timestamp limit) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_buff SET char_obj_id=?, skill_id=?, remaining_time=?, poly_id=?, limit_time=?");
                    pstm.setInt(1, objId);
                    pstm.setInt(2, skillId);
                    pstm.setInt(3, time);
                    pstm.setInt(4, polyId);
                    pstm.setTimestamp(5, limit);
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

    private static void b(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_buff WHERE char_obj_id=?");
                    pstm.setInt(1, pc.fr());
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

    public static void a(u pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        boolean oldAutoCommit = true;
        try {
            con = l1j.server.b.a().b();
            c(con);
            oldAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            pstm = con.prepareStatement("DELETE FROM character_buff WHERE char_obj_id=?");
            pstm.setInt(1, pc.fr());
            pstm.executeUpdate();
            j.a(pstm);
            pstm = con.prepareStatement("INSERT INTO character_buff SET char_obj_id=?, skill_id=?, remaining_time=?, poly_id=?, limit_time=?");

            for (int skillId : b) {
                int timeSec = pc.bC(skillId);
                if (timeSec <= 0) {
                    continue;
                }
                int polyId = 0;
                if (skillId == 67) {
                    polyId = pc.fe();
                }
                pstm.setInt(1, pc.fr());
                pstm.setInt(2, skillId);
                pstm.setInt(3, timeSec);
                pstm.setInt(4, polyId);
                pstm.setTimestamp(5, pc.bD(skillId));
                if (pstm.executeUpdate() != 1) {
                    throw new SQLException("BUG-850-246 buff insert affected unexpected row count");
                }
            }

            con.commit();
        }
        catch (SQLException e2) {
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException rollbackError) {
                    a.log(Level.SEVERE, rollbackError.getLocalizedMessage(), rollbackError);
                }
            }
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        finally {
            j.a(pstm);
            if (con != null) {
                try {
                    con.setAutoCommit(oldAutoCommit);
                }
                catch (SQLException autoCommitError) {
                    a.log(Level.SEVERE, autoCommitError.getLocalizedMessage(), autoCommitError);
                }
            }
            j.a(con);
        }
    }

    private static void c(Connection con) throws SQLException {
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;
        try {
            pstm = con.prepareStatement("SELECT ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='character_buff'");
            rs = pstm.executeQuery();
            if (!rs.next() || !"InnoDB".equalsIgnoreCase(rs.getString("ENGINE"))) {
                throw new SQLException("BUG-850-246 requires character_buff InnoDB migration");
            }
        }
        finally {
            j.a(rs);
            j.a(pstm);
        }
    }
}

