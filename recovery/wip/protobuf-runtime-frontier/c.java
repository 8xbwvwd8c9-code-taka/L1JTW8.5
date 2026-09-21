package l1rpb;

import java.io.IOException;
import java.io.InputStream;

public abstract class c<MessageType extends y> implements ab<MessageType> {
   private static final n a = n.g();

   private ao a(MessageType var1) {
      return var1 instanceof b ? ((b)var1).e() : new ao(var1);
   }

   private MessageType b(MessageType var1) throws s {
      if (var1 != null && !var1.a()) {
         throw this.a(var1).b().a(var1);
      } else {
         return var1;
      }
   }

   public MessageType a(h var1) throws s {
      return this.d(var1, a);
   }

   public MessageType a(h var1, n var2) throws s {
      return this.b(this.d(var1, var2));
   }

   public MessageType b(h var1) throws s {
      return this.a(var1, a);
   }

   public MessageType a(g var1, n var2) throws s {
      try {
         h var4 = var1.k();
         y var3 = this.d(var4, var2);

         try {
            var4.a(0);
         } catch (s var6) {
            throw var6.a(var3);
         }

         return var3;
      } catch (s var7) {
         throw var7;
      } catch (IOException var8) {
         throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", var8);
      }
   }

   public MessageType a(g var1) throws s {
      return this.a(var1, a);
   }

   public MessageType b(g var1, n var2) throws s {
      return this.b(this.a(var1, var2));
   }

   public MessageType b(g var1) throws s {
      return this.b(var1, a);
   }

   public MessageType a(byte[] var1, int var2, int var3, n var4) throws s {
      try {
         h var5 = h.a(var1, var2, var3);
         y var6 = this.d(var5, var4);

         try {
            var5.a(0);
         } catch (s var8) {
            throw var8.a(var6);
         }

         return var6;
      } catch (s var9) {
         throw var9;
      } catch (IOException var10) {
         throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", var10);
      }
   }

   public MessageType a(byte[] var1, int var2, int var3) throws s {
      return this.a(var1, var2, var3, a);
   }

   public MessageType a(byte[] var1, n var2) throws s {
      return this.a(var1, 0, var1.length, var2);
   }

   public MessageType a(byte[] var1) throws s {
      return this.a(var1, 0, var1.length, a);
   }

   public MessageType b(byte[] var1, int var2, int var3, n var4) throws s {
      return this.b(this.a(var1, var2, var3, var4));
   }

   public MessageType b(byte[] var1, int var2, int var3) throws s {
      return this.b(var1, var2, var3, a);
   }

   public MessageType b(byte[] var1, n var2) throws s {
      return this.b(var1, 0, var1.length, var2);
   }

   public MessageType b(byte[] var1) throws s {
      return this.b(var1, a);
   }

   public MessageType a(InputStream var1, n var2) throws s {
      h var3 = h.a(var1);
      y var4 = this.d(var3, var2);

      try {
         var3.a(0);
         return var4;
      } catch (s var6) {
         throw var6.a(var4);
      }
   }

   public MessageType a(InputStream var1) throws s {
      return this.a(var1, a);
   }

   public MessageType b(InputStream var1, n var2) throws s {
      return this.b(this.a(var1, var2));
   }

   public MessageType b(InputStream var1) throws s {
      return this.b(var1, a);
   }

   public MessageType c(InputStream var1, n var2) throws s {
      int var3;
      try {
         int var4 = var1.read();
         if (var4 == -1) {
            return null;
         }

         var3 = h.a(var4, var1);
      } catch (IOException var5) {
         throw new s(var5.getMessage());
      }

      InputStream var6 = new b.a.a(var1, var3);
      return this.a(var6, var2);
   }

   public MessageType c(InputStream var1) throws s {
      return this.c(var1, a);
   }

   public MessageType d(InputStream var1, n var2) throws s {
      return this.b(this.c(var1, var2));
   }

   public MessageType d(InputStream var1) throws s {
      return this.d(var1, a);
   }

   // $VF: synthetic method
   @Override
   public Object e(InputStream var1, n var2) throws s {
      return this.c(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object e(InputStream var1) throws s {
      return this.c(var1);
   }

   // $VF: synthetic method
   @Override
   public Object f(InputStream var1, n var2) throws s {
      return this.d(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object f(InputStream var1) throws s {
      return this.d(var1);
   }

   // $VF: synthetic method
   @Override
   public Object g(InputStream var1, n var2) throws s {
      return this.a(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object g(InputStream var1) throws s {
      return this.a(var1);
   }

   // $VF: synthetic method
   @Override
   public Object h(InputStream var1, n var2) throws s {
      return this.b(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object h(InputStream var1) throws s {
      return this.b(var1);
   }

   // $VF: synthetic method
   @Override
   public Object c(byte[] var1, n var2) throws s {
      return this.a(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object c(byte[] var1) throws s {
      return this.a(var1);
   }

   // $VF: synthetic method
   @Override
   public Object c(byte[] var1, int var2, int var3, n var4) throws s {
      return this.a(var1, var2, var3, var4);
   }

   // $VF: synthetic method
   @Override
   public Object c(byte[] var1, int var2, int var3) throws s {
      return this.a(var1, var2, var3);
   }

   // $VF: synthetic method
   @Override
   public Object d(byte[] var1, n var2) throws s {
      return this.b(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object d(byte[] var1) throws s {
      return this.b(var1);
   }

   // $VF: synthetic method
   @Override
   public Object d(byte[] var1, int var2, int var3, n var4) throws s {
      return this.b(var1, var2, var3, var4);
   }

   // $VF: synthetic method
   @Override
   public Object d(byte[] var1, int var2, int var3) throws s {
      return this.b(var1, var2, var3);
   }

   // $VF: synthetic method
   @Override
   public Object c(g var1, n var2) throws s {
      return this.a(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object c(g var1) throws s {
      return this.a(var1);
   }

   // $VF: synthetic method
   @Override
   public Object d(g var1, n var2) throws s {
      return this.b(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object d(g var1) throws s {
      return this.b(var1);
   }

   // $VF: synthetic method
   @Override
   public Object c(h var1) throws s {
      return this.a(var1);
   }

   // $VF: synthetic method
   @Override
   public Object b(h var1, n var2) throws s {
      return this.a(var1, var2);
   }

   // $VF: synthetic method
   @Override
   public Object d(h var1) throws s {
      return this.b(var1);
   }
}
