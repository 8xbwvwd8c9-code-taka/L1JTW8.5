package l1r.ax;

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

class CachedMapReader extends MapReader {
   private static final String a = "./maps/";
   private static final String b = "./data/mapcache/";

   private L1Map b(int var1) throws IOException {
      File var2 = new File("./data/mapcache/");
      if (!var2.exists()) {
         var2.mkdir();
      }

      L1Map var3 = new TextMapReader().a(var1);
      DataOutputStream var4 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("./data/mapcache/" + var1 + ".map")));
      var4.writeInt(var3.b());
      var4.writeInt(var3.c());
      var4.writeInt(var3.d());
      var4.writeInt(var3.e());
      var4.writeInt(var3.f());
      short[][] var8;
      int var7 = (var8 = var3.a()).length;

      for (int var6 = 0; var6 < var7; var6++) {
         short[] var5 = var8[var6];
         short[] var12 = var5;
         int var11 = var5.length;

         for (int var10 = 0; var10 < var11; var10++) {
            short var9 = var12[var10];
            var4.writeShort(var9);
         }
      }

      var4.flush();
      var4.close();
      return var3;
   }

   @Override
   public L1Map a(int var1) throws IOException {
      File var2 = new File("./data/mapcache/" + var1 + ".map");
      if (!var2.exists()) {
         return this.b(var1);
      }

      DataInputStream var3 = new DataInputStream(new BufferedInputStream(new FileInputStream("./data/mapcache/" + var1 + ".map")));
      int var4 = var3.readInt();
      if (var1 != var4) {
         System.out.println("Cached Map read error: " + var1 + " !=" + var4);
         var3.close();
         throw new FileNotFoundException();
      }

      int var5 = var3.readInt();
      int var6 = var3.readInt();
      int var7 = var3.readInt();
      int var8 = var3.readInt();
      short[][] var9 = new short[var7][var8];

      for (int var10 = 0; var10 < var7; var10++) {
         for (int var11 = 0; var11 < var8; var11++) {
            var9[var10][var11] = var3.readShort();
         }
      }

      var3.close();
      return new L1Map(var4, var9, var5, var6);
   }

   @Override
   public Map<Integer, L1Map> a() throws IOException {
      HashMap var1 = new HashMap<>();

      for (int var2 : TextMapReader.c()) {
         var1.put(var2, this.a(var2));
      }

      return var1;
   }
}
