package l1r.be;

public class S_CharList extends ServerBasePacket {
   public S_CharList(
      String var1,
      String var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17
   ) {
      this.c(47);
      this.a(var1);
      this.a(var2);
      this.c(var3);
      this.c(var4);
      this.b(var5);
      this.b(var6);
      this.b(var7);
      this.c(var8);
      this.c(var9);
      this.c(var10);
      this.c(var11);
      this.c(var12);
      this.c(var13);
      this.c(var14);
      this.c(var15);
      this.c(var9 >= 55 ? var16 : 0);
      this.a(var17);
      this.c((var9 ^ var10 ^ var11 ^ var12 ^ var13 ^ var14 ^ var15) & 0xFF);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharPacks";
   }
}
