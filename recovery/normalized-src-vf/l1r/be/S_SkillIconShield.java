package l1r.be;

public class S_SkillIconShield extends ServerBasePacket {
   public S_SkillIconShield(int var1, int var2) {
      this.c(147);
      this.b(var2);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SkillIconShield";
   }
}
