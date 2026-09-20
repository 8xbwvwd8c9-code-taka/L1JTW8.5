package l1r.bi;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
   public static int a(int var0) {
      return var0 <= 0 ? 0 : ThreadLocalRandom.current().nextInt(var0);
   }
}
