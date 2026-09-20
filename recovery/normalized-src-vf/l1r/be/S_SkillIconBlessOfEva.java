package l1r.be;

public class S_SkillIconBlessOfEva extends ServerBasePacket {
   public S_SkillIconBlessOfEva(int var1, int var2) {
      this.c(111);
      this.a(var1);
      this.b(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }
}
