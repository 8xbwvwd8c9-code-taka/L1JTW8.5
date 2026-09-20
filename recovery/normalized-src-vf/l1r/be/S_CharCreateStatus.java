package l1r.be;

public class S_CharCreateStatus extends ServerBasePacket {
   public static final int a = 2;
   public static final int b = 6;
   public static final int c = 9;
   public static final int d = 21;

   public S_CharCreateStatus(int var1) {
      this.c(172);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharCreateStatus";
   }
}
