package l1r.bj;

public final class CipherServer {
   private final byte[] a = new byte[256];
   private final byte[] b = new byte[256];

   public CipherServer(int var1) {
      int[] var2 = new int[]{var1, -1827678238};
      var2[0] = Integer.rotateRight(var2[0] ^ -1674521287, 13);
      var2[1] ^= var2[0] ^ 2087905683;
      byte[] var3 = new byte[8];

      for (int var4 = 0; var4 < 2; var4++) {
         for (int var5 = 0; var5 < 4; var5++) {
            var3[(var4 << 2) + var5] = (byte)(var2[var4] >> (var5 << 3) & 0xFF);
         }
      }

      for (int var8 = 0; var8 < 256; var8++) {
         this.a[var8] = (byte)var8;
         this.b[var8] = (byte)var8;
      }

      int var9 = 0;

      for (int var10 = 0; var10 < 256; var10++) {
         var9 = var3[var10 % 8] + var9 + this.a[var10] & 0xFF;
         this.a(this.a, var10, var9);
      }

      int var11 = 0;

      for (int var6 = 0; var6 < 256; var6++) {
         var11 = var3[var6 % 8] + var11 + this.b[var6] & 0xFF;
         this.a(this.b, var6, var11);
      }

      var3 = null;
   }

   public final byte[] a(byte[] var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = var1.length;

      for (int var5 = 0; var5 < var4; var5++) {
         var2 = var2 + 1 & 0xFF;
         var3 = this.b[var2] + var3 & 0xFF;
         this.a(this.b, var2, var3);
         var1[var5] ^= this.b[this.b[var2] + this.b[var3] & 0xFF];
      }

      return var1;
   }

   public final byte[] b(byte[] var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = var1.length;

      for (int var5 = 0; var5 < var4; var5++) {
         var2 = var2 + 1 & 0xFF;
         var3 = this.a[var2] + var3 & 0xFF;
         this.a(this.a, var2, var3);
         var1[var5] ^= this.a[this.a[var2] + this.a[var3] & 0xFF];
      }

      return var1;
   }

   private final void a(byte[] var1, int var2, int var3) {
      byte var4 = var1[var2];
      var1[var2] = var1[var3];
      var1[var3] = var4;
   }
}
