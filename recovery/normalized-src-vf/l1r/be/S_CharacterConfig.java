package l1r.be;

public class S_CharacterConfig extends ServerBasePacket {
   public S_CharacterConfig(byte[] var1) {
      this.c(121);
      this.c(41);
      this.a(var1.length);
      this.a(var1);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharacterConfig";
   }
}
