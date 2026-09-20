package l1r.be;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class S_Emblem extends ServerBasePacket {
   private static final Logger a = Logger.getLogger(S_Emblem.class.getName());

   public S_Emblem(int var1) {
      this.c(96);
      this.a(var1);
      byte[] var2 = new byte[384];

      try {
         File var3 = new File("./emblem/" + var1);
         var2 = Files.readAllBytes(var3.toPath());
      } catch (NoSuchFileException var4) {
         a.log(Level.SEVERE, "./emblem/" + var1 + " 不存在");
      } catch (Exception var5) {
         a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
      }

      this.a(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Emblem";
   }
}
