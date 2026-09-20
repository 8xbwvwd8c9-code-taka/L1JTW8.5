package l1r.bj;

public final class CipherClient {
   private final byte[] a = new byte[8];
   private final byte[] b = new byte[8];
   private final byte[] c = new byte[4];

   public CipherClient(int var1) {
      int[] var2 = new int[]{var1, -1827678238};
      var2[0] = Integer.rotateRight(var2[0] ^ -1674521287, 13);
      var2[1] ^= var2[0] ^ 2087905683;

      for (int var3 = 0; var3 < 2; var3++) {
         for (int var4 = 0; var4 < 4; var4++) {
            this.a[(var3 << 2) + var4] = (byte)(var2[var3] >> (var4 << 3) & 0xFF);
         }
      }
   }

   public byte[] a(byte[] var1) {
      for (int var2 = 0; var2 < this.c.length; var2++) {
         this.c[var2] = var1[var2 + 4];
      }

      var1[0] ^= this.b[0];

      for (int var3 = 1; var3 < var1.length; var3++) {
         var1[var3] = (byte)(var1[var3] ^ var1[var3 - 1] ^ this.b[var3 & 7]);
      }

      var1[3] ^= this.b[2];
      var1[2] = (byte)(var1[2] ^ this.b[3] ^ var1[3]);
      var1[1] = (byte)(var1[1] ^ this.b[4] ^ var1[2]);
      var1[0] = (byte)(var1[0] ^ this.b[5] ^ var1[1]);
      this.a(this.b, this.c);
      return var1;
   }

   public final byte[] b(byte[] var1) {
      var1[0] = (byte)(var1[0] ^ this.a[5] ^ var1[1]);
      var1[1] = (byte)(var1[1] ^ this.a[4] ^ var1[2]);
      var1[2] = (byte)(var1[2] ^ this.a[3] ^ var1[3]);
      var1[3] ^= this.a[2];

      for (int var2 = var1.length - 1; var2 >= 1; var2--) {
         var1[var2] = (byte)(var1[var2] ^ var1[var2 - 1] ^ this.a[var2 & 7]);
      }

      var1[0] ^= this.a[0];

      for (int var3 = 0; var3 < 4; var3++) {
         this.c[var3] = var1[var3 + 4];
      }

      this.a(this.a, this.c);
      return var1;
   }

   private final void a(byte[] var1, byte[] var2) {
      for (int var3 = 0; var3 < 4; var3++) {
         var1[var3] ^= var2[var3];
      }

      int var5 = ((var1[7] & 255) << 24 | (var1[6] & 255) << 16 | (var1[5] & 255) << 8 | var1[4] & 255) + 679411651;

      for (int var4 = 0; var4 < 4; var4++) {
         var1[var4 + 4] = (byte)(var5 >> (var4 << 3) & 0xFF);
      }
   }
}
