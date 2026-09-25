/*
 * Decompiled with CFR 0.152.
 */
package ax;

import ax.b;
import ax.e;
import bi.g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class f
extends e {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private static final String b = "./maps/";

    @Override
    public b a(int mapId) throws IOException {
        String line;
        LineNumberReader in = new LineNumberReader(new BufferedReader(new FileReader(b + mapId + ".txt")));
        int x_begin = Integer.parseInt(in.readLine());
        int x_end = Integer.parseInt(in.readLine());
        int y_begin = Integer.parseInt(in.readLine());
        int y_end = Integer.parseInt(in.readLine());
        short[][] map = new short[x_end - x_begin + 1][y_end - y_begin + 1];
        int y2 = 0;
        while ((line = in.readLine()) != null) {
            if (line.trim().length() == 0 || line.startsWith("#")) continue;
            int x2 = 0;
            StringTokenizer tok = new StringTokenizer(line, ",");
            while (tok.hasMoreTokens()) {
                short tile;
                map[x2][y2] = tile = Short.parseShort(tok.nextToken());
                ++x2;
            }
            ++y2;
        }
        in.close();
        System.out.println("Load Map= " + mapId + ".txt ......OK");
        b l1v1map = new b((short)mapId, map, x_begin, y_begin);
        return l1v1map;
    }

    @Override
    public Map<Integer, b> a() throws IOException {
        HashMap<Integer, b> maps = new HashMap<Integer, b>();
        List<Integer> all_mapid = f.c();
        for (int mapId : all_mapid) {
            try {
                b map = this.a(mapId);
                maps.put(mapId, map);
            }
            catch (IOException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
        return maps;
    }

    public static List<Integer> c() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        File mapDir = new File(b);
        String[] stringArray = mapDir.list();
        int n2 = stringArray.length;
        int n3 = 0;
        while (n3 < n2) {
            block4: {
                String name = stringArray[n3];
                File mapFile = new File(mapDir, name);
                if (mapFile.exists() && g.a(mapFile).toLowerCase().equals("txt")) {
                    int id = 0;
                    try {
                        String idStr = g.b(mapFile);
                        id = Integer.parseInt(idStr);
                    }
                    catch (NumberFormatException e2) {
                        break block4;
                    }
                    ids.add(id);
                }
            }
            ++n3;
        }
        return ids;
    }
}

