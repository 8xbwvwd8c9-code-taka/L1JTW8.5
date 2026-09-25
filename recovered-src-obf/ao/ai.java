/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ai {
    private static final Logger a = Logger.getLogger(ai.class.getName());
    private static ai b;

    public static ai a() {
        if (b == null) {
            b = new ai();
        }
        return b;
    }

    public void a(int itemObjectId, int code, String sender, String receiver, String date, int templateId, byte[] subject, byte[] content) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            PreparedStatement pstm2 = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM letter ORDER BY item_object_id");
                    rs = pstm.executeQuery();
                    pstm2 = con.prepareStatement("INSERT INTO letter SET item_object_id=?, code=?, sender=?, receiver=?, date=?, template_id=?, subject=?, content=?");
                    pstm2.setInt(1, itemObjectId);
                    pstm2.setInt(2, code);
                    pstm2.setString(3, sender);
                    pstm2.setString(4, receiver);
                    pstm2.setString(5, date);
                    pstm2.setInt(6, templateId);
                    pstm2.setBytes(7, subject);
                    pstm2.setBytes(8, content);
                    pstm2.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm2);
                    j.a(rs, pstm, con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm2);
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(pstm2);
            j.a(rs, pstm, con);
        }
    }

    public void a(int itemObjectId) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM letter WHERE item_object_id=?");
                    pstm.setInt(1, itemObjectId);
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
}

