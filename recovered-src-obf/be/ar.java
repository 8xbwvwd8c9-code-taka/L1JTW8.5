/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ar
extends eu {
    private static final Logger a = Logger.getLogger(ar.class.getName());

    public ar(int emblemId) {
        this.c(96);
        this.a(emblemId);
        byte[] data = new byte[384];
        try {
            File file = new File("./emblem/" + emblemId);
            data = Files.readAllBytes(file.toPath());
        }
        catch (NoSuchFileException e2) {
            a.log(Level.SEVERE, "./emblem/" + emblemId + " \u4e0d\u5b58\u5728");
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
        }
        this.a(data);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Emblem";
    }
}

