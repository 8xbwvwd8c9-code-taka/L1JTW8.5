package l1r.ar;

public abstract class L1ClassFeature {
   public static L1ClassFeature a(int var0) {
      if (var0 == 0 || var0 == 1) {
         return new L1RoyalClassFeature();
      } else if (var0 == 138 || var0 == 37) {
         return new L1ElfClassFeature();
      } else if (var0 == 61 || var0 == 48) {
         return new L1KnightClassFeature();
      } else if (var0 == 734 || var0 == 1186) {
         return new L1WizardClassFeature();
      } else if (var0 == 2786 || var0 == 2796) {
         return new L1DarkElfClassFeature();
      } else if (var0 == 6658 || var0 == 6661) {
         return new L1DragonKnightClassFeature();
      } else if (var0 == 6671 || var0 == 6650) {
         return new L1IllusionistClassFeature();
      } else if (var0 != 12490 && var0 != 12494) {
         throw new IllegalArgumentException();
      } else {
         return new L1WarriorClassFeature();
      }
   }

   public abstract int b(int var1);

   public abstract int c(int var1);

   public abstract int[] a();

   public abstract int b();

   public abstract int c();

   public abstract int d();

   public abstract int d(int var1);

   public abstract int e(int var1);

   public abstract int f(int var1);

   public abstract int g(int var1);

   public abstract int h(int var1);

   public abstract int i(int var1);

   public abstract int e();

   public abstract int f();

   public abstract int g();

   public abstract int j(int var1);

   public abstract int k(int var1);

   public abstract String h();
}
