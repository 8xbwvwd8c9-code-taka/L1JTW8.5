package l1r.bj;

public class Cipher {
   private static final int a = -1674521287;
   private static final int b = -1827678238;
   private static final int c = 2087905683;
   private static final int d = 679411651;
   private final byte[] e = new byte[8];
   private final byte[] f = new byte[8];
   private final byte[] g = new byte[256];
   private final byte[] h = new byte[256];

   public Cipher(int var1) {
      byte var2 = 0;
      int var3 = 0;
      int[] var4 = new int[]{var1 ^ -1674521287, -1827678238};
      var4[0] = Integer.rotateLeft(var4[0], 19);
      var4[1] ^= var4[0] ^ 2087905683;

      for (int var5 = 0; var5 < 2; var5++) {
         for (int var6 = 0; var6 < 4; var6++) {
            byte var7 = (byte)(var4[var5] >> var6 * 8 & 0xFF);
            this.f[var5 * 4 + var6] = var7;
            this.e[var5 * 4 + var6] = var7;
         }
      }

      for (int var9 = 0; var9 < 256; var9++) {
         this.g[var9] = (byte)var9;
      }

      for (int var10 = 0; var10 < 256; var10++) {
         var3 = this.g[var10] + var3 + this.e[var10 % 8] & 0xFF;
         var2 = this.g[var3];
         this.g[var3] = this.g[var10];
         this.g[var10] = var2;
      }

      System.arraycopy(this.g, 0, this.h, 0, 256);
   }

   public void a(byte[] var1) {
      int var2 = var1.length + 1;
      int var3 = 0;
      int var4 = 0;
      byte var5 = 0;

      for (int var6 = 1; var6 < var2; var6++) {
         int var7 = var3 + this.g[var6 & 0xFF];
         var3 = var7 & 0xFF;
         var4 = var6 & 0xFF;
         var5 = this.g[var4];
         this.g[var4] = this.g[var3];
         this.g[var3] = var5;
         var1[var6 - 1] = (byte)(var1[var6 - 1] ^ this.g[this.g[var3] + this.g[var4] & 0xFF]);
      }
   }

   public byte[] b(byte[] var1) {
      var1[0] = (byte)(var1[0] ^ this.f[5] ^ var1[1]);
      var1[1] = (byte)(var1[1] ^ this.f[4] ^ var1[2]);
      var1[2] = (byte)(var1[2] ^ this.f[3] ^ var1[3]);
      var1[3] ^= this.f[2];
      int var2 = var1.length;

      for (int var3 = var2 - 1; var3 >= 1; var3--) {
         var1[var3] = (byte)(var1[var3] ^ var1[var3 - 1] ^ this.f[var3 & 7]);
      }

      var1[0] ^= this.f[0];
      var2 -= 4;
      byte[] var5 = new byte[var2];
      System.arraycopy(var1, 4, var5, 0, var2);
      this.a(this.f, var5);
      return var5;
   }

   public byte[] c(byte[] var1) {
      byte[] var2 = new byte[var1.length + 4];
      System.arraycopy(var1, 0, var2, 4, var1.length);
      var2[0] ^= this.e[0];

      for (int var3 = 1; var3 < var2.length; var3++) {
         var2[var3] = (byte)(var2[var3] ^ var2[var3 - 1] ^ this.e[var3 & 7]);
      }

      var2[3] ^= this.e[2];
      var2[2] = (byte)(var2[2] ^ this.e[3] ^ var2[3]);
      var2[1] = (byte)(var2[1] ^ this.e[4] ^ var2[2]);
      var2[0] = (byte)(var2[0] ^ this.e[5] ^ var2[1]);
      this.a(this.e, var1);
      return var2;
   }

   public byte[] d(byte[] var1) {
      int var2 = var1.length + 1;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      for (int var6 = 1; var6 < var2; var6++) {
         var3 = var6 & 0xFF;
         int var9 = var4 + this.h[var3];
         var4 = var9 & 0xFF;
         byte var10 = this.h[var3];
         this.h[var3] = this.h[var4];
         this.h[var4] = (byte)(var10 & 0xFF);
         int var7 = var6 - 1;
         var1[var7] ^= this.h[this.h[var3] + this.h[var4] & 0xFF];
      }

      return var1;
   }

   private void a(byte[] var1, byte[] var2) {
      for (int var3 = 0; var3 < 4; var3++) {
         var1[var3] ^= var2[var3];
      }

      int var5 = ((var1[7] & 255) << 24 | (var1[6] & 255) << 16 | (var1[5] & 255) << 8 | var1[4] & 255) + 679411651;

      for (int var4 = 0; var4 < 4; var4++) {
         var1[var4 + 4] = (byte)(var5 >> var4 * 8 & 0xFF);
      }
   }
}
