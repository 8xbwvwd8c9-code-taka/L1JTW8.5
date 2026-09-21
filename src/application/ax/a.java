/*
 * Decompiled with CFR 0.152.
 */
package ax;

import ax.b;
import ax.e;
import ax.f;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class a
extends e {
    private static final String a = "./maps/";
    private static final String b = "./data/mapcache/";

    a() {
    }

    private b b(int mapId) throws IOException {
        File file = new File(b);
        if (!file.exists()) {
            file.mkdir();
        }
        b map = new f().a(mapId);
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(b + mapId + ".map")));
        out.writeInt(map.b());
        out.writeInt(map.c());
        out.writeInt(map.d());
        out.writeInt(map.e());
        out.writeInt(map.f());
        short[][] sArray = map.a();
        int n2 = sArray.length;
        int n3 = 0;
        while (n3 < n2) {
            short[] line;
            short[] sArray2 = line = sArray[n3];
            int n4 = line.length;
            int n5 = 0;
            while (n5 < n4) {
                short tile = sArray2[n5];
                out.writeShort(tile);
                ++n5;
            }
            ++n3;
        }
        out.flush();
        out.close();
        return map;
    }

    @Override
    public b a(int mapId) throws IOException {
        File file = new File(b + mapId + ".map");
        if (!file.exists()) {
            return this.b(mapId);
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(b + mapId + ".map")));
        int id = in.readInt();
        if (mapId != id) {
            System.out.println("Cached Map read error: " + mapId + " !=" + id);
            in.close();
            throw new FileNotFoundException();
        }
        int xLoc = in.readInt();
        int yLoc = in.readInt();
        int width = in.readInt();
        int height = in.readInt();
        short[][] tiles = new short[width][height];
        int i2 = 0;
        while (i2 < width) {
            int j2 = 0;
            while (j2 < height) {
                tiles[i2][j2] = in.readShort();
                ++j2;
            }
            ++i2;
        }
        in.close();
        b map = new b(id, tiles, xLoc, yLoc);
        return map;
    }

    @Override
    public Map<Integer, b> a() throws IOException {
        HashMap<Integer, b> maps = new HashMap<Integer, b>();
        for (int id : f.c()) {
            maps.put(id, this.a(id));
        }
        return maps;
    }
}

