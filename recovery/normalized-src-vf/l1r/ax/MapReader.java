package l1r.ax;

import java.io.IOException;
import java.util.Map;

public abstract class MapReader {
   public abstract Map<Integer, L1Map> a() throws IOException;

   public abstract L1Map a(int var1) throws IOException;

   public static MapReader b() {
      return new CachedMapReader();
   }
}
