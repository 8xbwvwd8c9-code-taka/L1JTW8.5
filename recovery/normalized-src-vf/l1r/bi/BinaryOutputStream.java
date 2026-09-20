package l1r.bi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.l1j.server.Config;

public class BinaryOutputStream extends OutputStream {
   private static final Logger a = Logger.getLogger(BinaryOutputStream.class.getName());
   private static final String b = Config.k;
   private final ByteArrayOutputStream c = new ByteArrayOutputStream();

   @Override
   public void write(int var1) throws IOException {
      this.c.write(var1);
   }

   public void a(int var1) {
      this.c.write(var1 & 0xFF);
      this.c.write(var1 >> 8 & 0xFF);
      this.c.write(var1 >> 16 & 0xFF);
      this.c.write(var1 >> 24 & 0xFF);
   }

   public void b(int var1) {
      this.c.write(var1 & 0xFF);
      this.c.write(var1 >> 8 & 0xFF);
   }

   public void c(int var1) {
      this.c.write(var1 & 0xFF);
   }

   public void d(int var1) {
      this.c.write(var1);
   }

   public void a(long var1) {
      this.c.write((int)(var1 & 255L));
   }

   public void a(double var1) {
      long var3 = Double.doubleToRawLongBits(var1);
      this.c.write((int)(var3 & 255L));
      this.c.write((int)(var3 >> 8 & 255L));
      this.c.write((int)(var3 >> 16 & 255L));
      this.c.write((int)(var3 >> 24 & 255L));
      this.c.write((int)(var3 >> 32 & 255L));
      this.c.write((int)(var3 >> 40 & 255L));
      this.c.write((int)(var3 >> 48 & 255L));
      this.c.write((int)(var3 >> 56 & 255L));
   }

   public void a(String var1) {
      try {
         if (var1 != null) {
            this.c.write(var1.getBytes(b));
         }
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      this.c.write(0);
   }

   public void a(byte[] var1) {
      try {
         if (var1 != null) {
            this.c.write(var1);
         }
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }
   }

   public int a() {
      return this.c.size() + 2;
   }

   public byte[] b() {
      return this.c.toByteArray();
   }
}
