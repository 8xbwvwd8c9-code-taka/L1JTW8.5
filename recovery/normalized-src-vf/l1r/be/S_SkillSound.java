package l1r.be;

public class S_SkillSound extends ServerBasePacket {
   public S_SkillSound(int var1, int var2) {
      this.c(180);
      this.a(var1);
      this.b(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SkillSound";
   }
}
