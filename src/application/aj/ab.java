/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aa;
import aq.aq;
import be.ca;
import be.ds;
import bj.d;

public class ab
extends cv {
    private static final String a = "[C] C_CreateParty";

    public ab(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int type = this.c();
        if (type == 0 || type == 1) {
            int targetId = this.b();
            aa temp = aq.a().a(targetId);
            if (temp instanceof u) {
                u targetPc = (u)temp;
                if (pc.fr() == targetPc.fr()) {
                    return;
                }
                if (!pc.fu().e(targetPc.fu()) || pc.fu().c(targetPc.fu()) > 7) {
                    pc.a(new ds(952));
                    return;
                }
                if (targetPc.q()) {
                    pc.a(new ds(415));
                    return;
                }
                if (pc.q()) {
                    if (pc.aL().e(pc)) {
                        targetPc.aU(type);
                        targetPc.ak(pc.fr());
                        switch (type) {
                            case 0: {
                                targetPc.a(new ca(953, pc.et()));
                                break;
                            }
                            case 1: {
                                targetPc.a(new ca(954, pc.et()));
                            }
                        }
                    } else {
                        pc.a(new ds(416));
                    }
                } else {
                    pc.aU(type);
                    targetPc.ak(pc.fr());
                    switch (type) {
                        case 0: {
                            targetPc.a(new ca(953, pc.et()));
                            break;
                        }
                        case 1: {
                            targetPc.a(new ca(954, pc.et()));
                        }
                        default: {
                            break;
                        }
                    }
                }
            }
        } else if (type == 4 || type == 5) {
            String name = this.g();
            u targetPc = aq.a().a(name);
            if (targetPc == null) {
                pc.a(new ds(109));
                return;
            }
            if (pc.fr() == targetPc.fr()) {
                return;
            }
            if (!pc.fu().e(targetPc.fu()) || pc.fu().c(targetPc.fu()) > 7) {
                pc.a(new ds(952));
                return;
            }
            if (targetPc.q()) {
                pc.a(new ds(415));
                return;
            }
            if (pc.q()) {
                if (pc.aL().e(pc)) {
                    targetPc.aU(type);
                    targetPc.ak(pc.fr());
                    switch (type) {
                        case 4: {
                            targetPc.a(new ca(953, pc.et()));
                            break;
                        }
                        case 5: {
                            targetPc.a(new ca(954, pc.et()));
                        }
                    }
                } else {
                    pc.a(new ds(416));
                }
            } else {
                pc.aU(type);
                targetPc.ak(pc.fr());
                switch (type) {
                    case 4: {
                        targetPc.a(new ca(953, pc.et()));
                        break;
                    }
                    case 5: {
                        targetPc.a(new ca(954, pc.et()));
                    }
                }
            }
        } else if (type == 2) {
            String name = this.g();
            u targetPc = aq.a().a(name);
            if (targetPc == null) {
                pc.a(new ds(109));
                return;
            }
            if (pc.fr() == targetPc.fr()) {
                return;
            }
            if (!pc.fu().e(targetPc.fu()) || pc.fu().c(targetPc.fu()) > 7) {
                pc.a(new ds(952));
                return;
            }
            if (targetPc.r()) {
                pc.a(new ds(415));
                return;
            }
            if (pc.r()) {
                if (pc.aM().b(pc)) {
                    targetPc.ak(pc.fr());
                    targetPc.a(new ca(951, pc.et()));
                } else {
                    pc.a(new ds(416));
                }
            } else {
                targetPc.ak(pc.fr());
                targetPc.a(new ca(951, pc.et()));
            }
        } else if (type == 3) {
            if (pc.aL() == null || !pc.aL().e(pc)) {
                pc.a(new ds(1697));
                return;
            }
            int targetId = this.b();
            aa obj = aq.a().a(targetId);
            if (obj == null || pc.fr() == obj.fr() || !(obj instanceof u)) {
                return;
            }
            if (!pc.fu().e(obj.fu()) || pc.fu().c(obj.fu()) > 7) {
                pc.a(new ds(1695));
                return;
            }
            u targetPc = (u)obj;
            if (!targetPc.q()) {
                pc.a(new ds(1696));
                return;
            }
            pc.a(new ca(1703, ""));
            pc.aL().g(targetPc);
        }
    }

    @Override
    public String a() {
        return a;
    }
}

