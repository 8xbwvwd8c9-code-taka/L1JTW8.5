/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ap.u;
import bh.k;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class an {
    private static final Logger a = Logger.getLogger(an.class.getName());
    private static an b;
    private static CopyOnWriteArrayList<k> c;

    static {
        c = new CopyOnWriteArrayList();
    }

    public static an a() {
        if (b == null) {
            b = new an();
        }
        return b;
    }

    private an() {
        this.b();
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mail");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        k mail = new k();
                        mail.a(rs.getInt("id"));
                        mail.b(rs.getInt("type"));
                        mail.a(rs.getString("sender"));
                        mail.b(rs.getString("receiver"));
                        mail.a(rs.getTimestamp("date"));
                        mail.c(rs.getInt("read_status"));
                        mail.a(rs.getBytes("subject"));
                        mail.b(rs.getBytes("content"));
                        mail.d(rs.getInt("inbox_id"));
                        c.add(mail);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(int mailId) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE mail SET read_status=1 WHERE id=?");
                    pstm.setInt(1, mailId);
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

    public void a(k mail) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE mail SET type=? WHERE id=?");
                    pstm.setInt(1, mail.b());
                    pstm.setInt(2, mail.a());
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

    public void b(int mailId) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM mail WHERE id=?");
                    pstm.setInt(1, mailId);
                    pstm.execute();
                    for (k mail : c) {
                        if (mail.a() != mailId) continue;
                        c.remove(mail);
                        break;
                    }
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

    public k a(int type, u receiver, u writer, byte[] text, boolean isTemp) {
        k mail;
        block11: {
            int spacePosition1 = 0;
            int spacePosition2 = 0;
            int i2 = 0;
            while (i2 < text.length) {
                if (text[i2] == 0 && text[i2 + 1] == 0) {
                    if (spacePosition1 == 0) {
                        spacePosition1 = i2;
                    } else if (spacePosition1 != 0 && spacePosition2 == 0) {
                        spacePosition2 = i2;
                        break;
                    }
                }
                i2 += 2;
            }
            int subjectLength = spacePosition1 + 2;
            int contentLength = spacePosition2 - spacePosition1 + 1;
            if (contentLength <= 0) {
                contentLength = 1;
            }
            byte[] subject = new byte[subjectLength];
            byte[] content = new byte[contentLength];
            System.arraycopy(text, 0, subject, 0, subjectLength);
            System.arraycopy(text, subjectLength, content, 0, contentLength);
            mail = new k();
            mail.a(d.a().d());
            mail.b(type);
            mail.a(writer.et());
            mail.b(receiver.et());
            mail.a(new Timestamp(System.currentTimeMillis()));
            mail.a(subject);
            mail.b(content);
            mail.c(0);
            mail.d(isTemp ? writer.fr() : receiver.fr());
            Connection con = null;
            PreparedStatement pstm2 = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm2 = con.prepareStatement("INSERT INTO mail SET id=?, type=?, sender=?, receiver=?, date=?, read_status=?, subject=?, content=?, inbox_id=?");
                    pstm2.setInt(1, mail.a());
                    pstm2.setInt(2, mail.b());
                    pstm2.setString(3, writer.et());
                    pstm2.setString(4, receiver.et());
                    pstm2.setTimestamp(5, mail.e());
                    pstm2.setInt(6, mail.f());
                    pstm2.setBytes(7, mail.g());
                    pstm2.setBytes(8, mail.h());
                    pstm2.setInt(9, isTemp ? writer.fr() : receiver.fr());
                    pstm2.execute();
                    c.add(mail);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm2);
                    j.a(con);
                    break block11;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm2);
                j.a(con);
                throw throwable;
            }
            j.a(pstm2);
            j.a(con);
        }
        return mail;
    }

    public ArrayList<k> a(int objid, int type) {
        ArrayList<k> result = new ArrayList<k>();
        for (k mail : c) {
            if (mail.i() != objid || mail.b() != type) continue;
            result.add(mail);
        }
        return result;
    }

    public k c(int mailId) {
        for (k mail : c) {
            if (mail.a() != mailId) continue;
            return mail;
        }
        return null;
    }
}

