package l1r.aj;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.l1j.server.Config;

public abstract class ClientBasePacket {
   private static final Logger a = Logger.getLogger(ClientBasePacket.class.getName());
   private byte[] b;
   private int c;

   public ClientBasePacket(byte[] var1) {
      this.b = var1;
      this.c = 1;
   }

   public int b() {
      int var1 = this.b[this.c++] & 255;
      var1 |= this.b[this.c++] << 8 & 0xFF00;
      var1 |= this.b[this.c++] << 16 & 0xFF0000;
      return var1 | this.b[this.c++] << 24 & 0xFF000000;
   }

   public int c() {
      return this.b[this.c++] & 0xFF;
   }

   public int d() {
      int var1 = this.b[this.c++] & 255;
      return var1 | this.b[this.c++] << 8 & 0xFF00;
   }

   public int e() {
      int var1 = this.b[this.c++] & 255;
      var1 |= this.b[this.c++] << 8 & 0xFF00;
      return var1 | this.b[this.c++] << 16 & 0xFF0000;
   }

   public double f() {
      long var1 = this.b[this.c++] & 0xFF;
      var1 |= this.b[this.c++] << 8 & 0xFF00;
      var1 |= this.b[this.c++] << 16 & 0xFF0000;
      var1 |= this.b[this.c++] << 24 & 0xFF000000;
      var1 |= (long)this.b[this.c++] << 32 & 1095216660480L;
      var1 |= (long)this.b[this.c++] << 40 & 280375465082880L;
      var1 |= (long)this.b[this.c++] << 48 & 71776119061217280L;
      var1 |= (long)this.b[this.c++] << 56 & -72057594037927936L;
      return Double.longBitsToDouble(var1);
   }

   public byte[] a(int var1) {
      byte[] var2 = new byte[var1];

      for (int var3 = this.c; var3 < this.c + var1; var3++) {
         var2[var3 - this.c] = this.b[var3];
      }

      this.c += var1;
      return var2;
   }

   public String g() {
      String var1 = null;

      try {
         var1 = new String(this.b, this.c, this.b.length - this.c, Config.k);
         var1 = var1.substring(0, var1.indexOf(0));
         this.c = this.c + var1.getBytes(Config.k).length + 1;
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      return var1;
   }

   public byte[] h() {
      byte[] var1 = new byte[this.b.length - this.c];

      try {
         System.arraycopy(this.b, this.c, var1, 0, this.b.length - this.c);
         this.c = this.b.length;
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      return var1;
   }

   public void i() {
      this.b = null;
   }

   public String a() {
      return "[C] " + this.getClass().getSimpleName();
   }
}
