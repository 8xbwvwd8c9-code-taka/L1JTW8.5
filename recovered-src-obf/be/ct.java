/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.t;
import ap.v;
import ap.z;
import be.eu;

public class ct
extends eu {
    public ct(t npc, int exppercet) {
        this.c(110);
        if (npc instanceof v) {
            v pet = (v)npc;
            this.a(pet.fr());
            this.a("anicom");
            this.c(0);
            this.b(11);
            switch (pet.j()) {
                case 1: {
                    this.a("$469");
                    break;
                }
                case 2: {
                    this.a("$470");
                    break;
                }
                case 3: {
                    this.a("$471");
                    break;
                }
                case 5: {
                    this.a("$472");
                    break;
                }
                default: {
                    this.a("$471");
                }
            }
            this.a(Integer.toString(pet.ea()));
            this.a(Integer.toString(pet.ew()));
            this.a(Integer.toString(pet.eb()));
            this.a(Integer.toString(pet.ex()));
            this.a(Integer.toString(pet.ev()));
            this.a("");
            String s2 = "$610";
            if (pet.fj() > 80) {
                s2 = "$612";
            } else if (pet.fj() > 60) {
                s2 = "$611";
            } else if (pet.fj() > 30) {
                s2 = "$610";
            } else if (pet.fj() > 10) {
                s2 = "$609";
            } else if (pet.fj() >= 0) {
                s2 = "$608";
            }
            this.a(s2);
            this.a(Integer.toString(exppercet));
            this.a(Integer.toString(pet.fa()));
            this.a(String.valueOf(pet.fj()));
        } else if (npc instanceof z) {
            z summon = (z)npc;
            this.a(summon.fr());
            this.a("moncom");
            this.c(0);
            this.b(6);
            switch (summon.i()) {
                case 1: {
                    this.a("$469");
                    break;
                }
                case 2: {
                    this.a("$470");
                    break;
                }
                case 3: {
                    this.a("$471");
                    break;
                }
                case 5: {
                    this.a("$472");
                    break;
                }
                default: {
                    this.a("$471");
                }
            }
            this.a(Integer.toString(summon.ea()));
            this.a(Integer.toString(summon.ew()));
            this.a(Integer.toString(summon.eb()));
            this.a(Integer.toString(summon.ex()));
            this.a(Integer.toString(summon.ev()));
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PetMenuPacket";
    }
}

