package l1r.be;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.l1j.server.Config;

public abstract class ServerBasePacket {
   private static final Logger a = Logger.getLogger(ServerBasePacket.class.getName());
   protected static final String aV = Config.k;
   ByteArrayOutputStream aW = new ByteArrayOutputStream();

   protected ServerBasePacket() {
   }

   protected void a(int var1) {
      this.aW.write(var1 & 0xFF);
      this.aW.write(var1 >> 8 & 0xFF);
      this.aW.write(var1 >> 16 & 0xFF);
      this.aW.write(var1 >> 24 & 0xFF);
   }

   protected void b(int var1) {
      this.aW.write(var1 & 0xFF);
      this.aW.write(var1 >> 8 & 0xFF);
   }

   protected void c(int var1) {
      this.aW.write(var1 & 0xFF);
   }

   protected void d(int var1) {
      this.aW.write(var1);
   }

   protected void a(long var1) {
      this.aW.write((int)(var1 & 255L));
   }

   protected void a(double var1) {
      long var3 = Double.doubleToRawLongBits(var1);
      this.aW.write((int)(var3 & 255L));
      this.aW.write((int)(var3 >> 8 & 255L));
      this.aW.write((int)(var3 >> 16 & 255L));
      this.aW.write((int)(var3 >> 24 & 255L));
      this.aW.write((int)(var3 >> 32 & 255L));
      this.aW.write((int)(var3 >> 40 & 255L));
      this.aW.write((int)(var3 >> 48 & 255L));
      this.aW.write((int)(var3 >> 56 & 255L));
   }

   protected void a(String var1) {
      try {
         if (var1 != null) {
            this.aW.write(var1.getBytes(aV));
         }
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      this.aW.write(0);
   }

   protected void a(byte[] var1) {
      try {
         if (var1 != null && var1.length > 0) {
            this.aW.write(var1);
         }
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }
   }

   public int c() {
      return this.aW.size() + 2;
   }

   public byte[] d() {
      return this.aW.toByteArray();
   }

   public abstract byte[] a() throws IOException;

   public String b() {
      return this.getClass().getSimpleName();
   }
}
