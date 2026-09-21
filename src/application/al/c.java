/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.be;
import ap.u;
import be.d;
import be.dc;
import be.ee;
import be.ei;
import bh.v;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c
implements l {
    private static final Logger a = Logger.getLogger(c.class.getName());

    private c() {
    }

    public static l a() {
        return new c();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            String skill_name = "";
            int skill_id = 0;
            int object_id = pc.fr();
            pc.a(new ee(object_id, 227));
            pc.b(new ee(object_id, 227));
            CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
            if (pc.x()) {
                v l1skills;
                int i2 = 1;
                while (i2 <= 16) {
                    l1skills = be.a().a(i2);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i2);
                    ++i2;
                }
                i2 = 113;
                while (i2 <= 125) {
                    l1skills = be.a().a(i2);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i2);
                    ++i2;
                }
            } else if (pc.z()) {
                v l1skills;
                int i3 = 1;
                while (i3 <= 8) {
                    l1skills = be.a().a(i3);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i3);
                    ++i3;
                }
                i3 = 87;
                while (i3 <= 92) {
                    l1skills = be.a().a(i3);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i3);
                    ++i3;
                }
            } else if (pc.A()) {
                v l1skills;
                int i4 = 1;
                while (i4 <= 48) {
                    l1skills = be.a().a(i4);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i4);
                    ++i4;
                }
                i4 = 129;
                while (i4 <= 176) {
                    l1skills = be.a().a(i4);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i4);
                    ++i4;
                }
            } else if (pc.B()) {
                int i5 = 1;
                while (i5 <= 80) {
                    v l1skills = be.a().a(i5);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i5);
                    ++i5;
                }
            } else if (pc.C()) {
                v l1skills;
                int i6 = 1;
                while (i6 <= 16) {
                    l1skills = be.a().a(i6);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i6);
                    ++i6;
                }
                i6 = 97;
                while (i6 <= 112) {
                    l1skills = be.a().a(i6);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i6);
                    ++i6;
                }
                v l1skills2 = be.a().a(233);
                skill_name = l1skills2.b();
                skill_id = l1skills2.a();
                be.a().a(object_id, skill_id, skill_name, 0, 0);
                list.add(233);
                v skills = be.a().a(609);
                skill_name = skills.b();
                skill_id = skills.a();
                be.a().a(object_id, skill_id, skill_name, 0, 0);
                pc.a(new dc(402, 9));
            } else if (pc.D()) {
                int i7 = 181;
                while (i7 <= 196) {
                    v l1skills = be.a().a(i7);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i7);
                    ++i7;
                }
            } else if (pc.E()) {
                int i8 = 201;
                while (i8 <= 222) {
                    v l1skills = be.a().a(i8);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i8);
                    ++i8;
                }
            } else if (pc.F()) {
                v l1skills;
                int i9 = 1;
                while (i9 <= 8) {
                    l1skills = be.a().a(i9);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i9);
                    ++i9;
                }
                i9 = 225;
                while (i9 <= 231) {
                    l1skills = be.a().a(i9);
                    skill_name = l1skills.b();
                    skill_id = l1skills.a();
                    be.a().a(object_id, skill_id, skill_name, 0, 0);
                    list.add(i9);
                    ++i9;
                }
                i9 = 601;
                while (i9 <= 608) {
                    if (i9 != 604) {
                        l1skills = be.a().a(i9);
                        skill_name = l1skills.b();
                        skill_id = l1skills.a();
                        be.a().a(object_id, skill_id, skill_name, 0, 0);
                        pc.a(new dc(402, i9 - 600));
                    }
                    ++i9;
                }
            }
            pc.a(new d(pc, list));
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            pc.a(new ei(String.valueOf(cmdName) + " \u6307\u4ee4\u932f\u8aa4\u3002"));
        }
    }
}

