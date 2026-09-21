package l1rpb;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class p extends l1rpb.a implements Serializable {
   private static final long a = 1L;
   protected static boolean m = false;

   protected p() {
   }

   protected p(p.a<?> var1) {
   }

   @Override
   public ab<? extends x> m() {
      throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
   }

   static void ac() {
      m = true;
   }

   protected abstract p.g l();

   @Override
   public k.a J() {
      return this.l().a;
   }

   private Map<k.f, Object> h() {
      TreeMap var1 = new TreeMap<>();
      k.a var2 = this.l().a;

      for (k.f var4 : var2.h()) {
         if (var4.n()) {
            List var5 = (List<?>)this.b(var4);
            if (!var5.isEmpty()) {
               var1.put(var4, var5);
            }
         } else if (this.a_(var4)) {
            var1.put(var4, this.b(var4));
         }
      }

      return var1;
   }

   @Override
   public boolean a() {
      for (k.f var2 : this.J().h()) {
         if (var2.k() && !this.a_(var2)) {
            return false;
         }

         if (var2.g() == k.f.a.i) {
            if (var2.n()) {
               for (x var5 : (List)this.b(var2)) {
                  if (!var5.a()) {
                     return false;
                  }
               }
            } else if (this.a_(var2) && !((x)this.b(var2)).a()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public Map<k.f, Object> a_() {
      return Collections.unmodifiableMap(this.h());
   }

   @Override
   public boolean a_(k.f var1) {
      return this.l().a(var1).b(this);
   }

   @Override
   public Object b(k.f var1) {
      return this.l().a(var1).a(this);
   }

   @Override
   public int c(k.f var1) {
      return this.l().a(var1).c(this);
   }

   @Override
   public Object a(k.f var1, int var2) {
      return this.l().a(var1).a(this, var2);
   }

   @Override
   public ap b_() {
      throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
   }

   protected boolean a(l1rpb.h var1, ap.a var2, n var3, int var4) throws IOException {
      return var2.a(var4, var1);
   }

   protected void ad() {
   }

   protected abstract x.a b(p.b var1);

   public static <ContainingType extends x, Type> p.h<ContainingType, Type> a(final x var0, final int var1, Class var2, x var3) {
      return new p.h<>(new p.f() {
         @Override
         public k.f a() {
            return var0.J().i().get(var1);
         }
      }, var2, var3);
   }

   public static <ContainingType extends x, Type> p.h<ContainingType, Type> a(Class var0, x var1) {
      return new p.h<>(null, var0, var1);
   }

   private static Method b(Class var0, String var1, Class... var2) {
      try {
         return var0.getMethod(var1, var2);
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException("Generated message class \"" + var0.getName() + "\" missing method \"" + var1 + "\".", var4);
      }
   }

   private static Object b(Method var0, Object var1, Object... var2) {
      try {
         return var0.invoke(var1, var2);
      } catch (IllegalAccessException var5) {
         throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", var5);
      } catch (InvocationTargetException var6) {
         Throwable var4 = var6.getCause();
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof Error) {
            throw (Error)var4;
         } else {
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", var4);
         }
      }
   }

   protected Object I() throws ObjectStreamException {
      return new q.g(this);
   }

   public abstract static class a<BuilderType extends p.a> extends l1rpb.a.a<BuilderType> {
      private p.b a;
      private p.a<BuilderType>.a b;
      private boolean c;
      private ap d = ap.c();

      protected a() {
         this(null);
      }

      protected a(p.b var1) {
         this.a = var1;
      }

      void aA() {
         this.a = null;
      }

      protected void q_() {
         if (this.a != null) {
            this.r_();
         }
      }

      protected void r_() {
         this.c = true;
      }

      protected boolean s_() {
         return this.c;
      }

      public BuilderType ai() {
         throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
      }

      public BuilderType ah() {
         this.d = ap.c();
         this.t_();
         return (BuilderType)this;
      }

      protected abstract p.g l();

      @Override
      public k.a J() {
         return this.l().a;
      }

      @Override
      public Map<k.f, Object> a_() {
         return Collections.unmodifiableMap(this.k());
      }

      private Map<k.f, Object> k() {
         TreeMap var1 = new TreeMap<>();
         k.a var2 = this.l().a;

         for (k.f var4 : var2.h()) {
            if (var4.n()) {
               List var5 = (List)this.b(var4);
               if (!var5.isEmpty()) {
                  var1.put(var4, var5);
               }
            } else if (this.a_(var4)) {
               var1.put(var4, this.b(var4));
            }
         }

         return var1;
      }

      @Override
      public x.a g(k.f var1) {
         return this.l().a(var1).a();
      }

      @Override
      public x.a a(k.f var1) {
         return this.l().a(var1).e(this);
      }

      @Override
      public boolean a_(k.f var1) {
         return this.l().a(var1).b(this);
      }

      @Override
      public Object b(k.f var1) {
         Object var2 = this.l().a(var1).a(this);
         return var1.n() ? Collections.unmodifiableList((List)var2) : var2;
      }

      public BuilderType a(k.f var1, Object var2) {
         this.l().a(var1).a(this, var2);
         return (BuilderType)this;
      }

      public BuilderType d(k.f var1) {
         this.l().a(var1).d(this);
         return (BuilderType)this;
      }

      @Override
      public int c(k.f var1) {
         return this.l().a(var1).c(this);
      }

      @Override
      public Object a(k.f var1, int var2) {
         return this.l().a(var1).a(this, var2);
      }

      public BuilderType a(k.f var1, int var2, Object var3) {
         this.l().a(var1).a(this, var2, var3);
         return (BuilderType)this;
      }

      public BuilderType b(k.f var1, Object var2) {
         this.l().a(var1).b(this, var2);
         return (BuilderType)this;
      }

      public final BuilderType c(ap var1) {
         this.d = var1;
         this.t_();
         return (BuilderType)this;
      }

      public final BuilderType d(ap var1) {
         this.d = ap.a(this.d).a(var1).b();
         this.t_();
         return (BuilderType)this;
      }

      @Override
      public boolean a() {
         for (k.f var2 : this.J().h()) {
            if (var2.k() && !this.a_(var2)) {
               return false;
            }

            if (var2.g() == k.f.a.i) {
               if (var2.n()) {
                  for (x var5 : (List)this.b(var2)) {
                     if (!var5.a()) {
                        return false;
                     }
                  }
               } else if (this.a_(var2) && !((x)this.b(var2)).a()) {
                  return false;
               }
            }
         }

         return true;
      }

      @Override
      public final ap b_() {
         return this.d;
      }

      protected boolean a(l1rpb.h var1, ap.a var2, n var3, int var4) throws IOException {
         return var2.a(var4, var1);
      }

      protected p.b aE() {
         if (this.b == null) {
            this.b = new p.a.a();
         }

         return this.b;
      }

      protected final void t_() {
         if (this.c && this.a != null) {
            this.a.a();
            this.c = false;
         }
      }

      // $VF: synthetic method
      @Override
      public l1rpb.a.a a(ap var1) {
         return this.d(var1);
      }

      // $VF: synthetic method
      @Override
      public l1rpb.a.a e() {
         return this.ah();
      }

      // $VF: synthetic method
      @Override
      public l1rpb.a.a d() {
         return this.ai();
      }

      // $VF: synthetic method
      @Override
      public x.a b(ap var1) {
         return this.d(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a e(ap var1) {
         return this.c(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a c(k.f var1, Object var2) {
         return this.b(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a b(k.f var1, int var2, Object var3) {
         return this.a(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public x.a f(k.f var1) {
         return this.d(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a d(k.f var1, Object var2) {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a i() {
         return this.ai();
      }

      // $VF: synthetic method
      @Override
      public x.a j() {
         return this.ah();
      }

      // $VF: synthetic method
      @Override
      public y.a g() {
         return this.ai();
      }

      // $VF: synthetic method
      @Override
      public y.a h() {
         return this.ah();
      }

      // $VF: synthetic method
      @Override
      public l1rpb.b.a f() {
         return this.ai();
      }

      // $VF: synthetic method
      @Override
      public Object clone() throws CloneNotSupportedException {
         return this.ai();
      }

      private class a implements p.b {
         private a() {
         }

         @Override
         public void a() {
            a.this.t_();
         }
      }
   }

   protected interface b {
      void a();
   }

   public abstract static class c<MessageType extends p.d, BuilderType extends p.c> extends p.a<BuilderType> implements p.e<MessageType> {
      private o<k.f> a = o.b();

      protected c() {
      }

      protected c(p.b var1) {
         super(var1);
      }

      public BuilderType B() {
         this.a = o.b();
         return super.ah();
      }

      public BuilderType A() {
         throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
      }

      private void k() {
         if (this.a.d()) {
            this.a = this.a.e();
         }
      }

      private void e(p.h<MessageType, ?> var1) {
         if (var1.a().u() != this.J()) {
            throw new IllegalArgumentException("Extension is for type \"" + var1.a().u().d() + "\" which does not match message type \"" + this.J().d() + "\".");
         }
      }

      @Override
      public final <Type> boolean a(p.h<MessageType, Type> var1) {
         this.e(var1);
         return this.a.a(var1.a());
      }

      @Override
      public final <Type> int b(p.h<MessageType, List<Type>> var1) {
         this.e(var1);
         k.f var2 = var1.a();
         return this.a.d(var2);
      }

      @Override
      public final <Type> Type c(p.h<MessageType, Type> var1) {
         this.e(var1);
         k.f var2 = var1.a();
         Object var3 = this.a.b(var2);
         if (var3 == null) {
            if (var2.n()) {
               return (Type)Collections.emptyList();
            } else {
               return (Type)(var2.g() == k.f.a.i ? var1.b() : var1.a(var2.r()));
            }
         } else {
            return (Type)var1.a(var3);
         }
      }

      @Override
      public final <Type> Type a(p.h<MessageType, List<Type>> var1, int var2) {
         this.e(var1);
         k.f var3 = var1.a();
         return (Type)var1.b(this.a.a(var3, var2));
      }

      public final <Type> BuilderType a(p.h<MessageType, Type> var1, Type var2) {
         this.e(var1);
         this.k();
         k.f var3 = var1.a();
         this.a.a(var3, var1.c(var2));
         this.t_();
         return (BuilderType)this;
      }

      public final <Type> BuilderType a(p.h<MessageType, List<Type>> var1, int var2, Type var3) {
         this.e(var1);
         this.k();
         k.f var4 = var1.a();
         this.a.a(var4, var2, var1.d(var3));
         this.t_();
         return (BuilderType)this;
      }

      public final <Type> BuilderType b(p.h<MessageType, List<Type>> var1, Type var2) {
         this.e(var1);
         this.k();
         k.f var3 = var1.a();
         this.a.b(var3, var1.d(var2));
         this.t_();
         return (BuilderType)this;
      }

      public final <Type> BuilderType d(p.h<MessageType, ?> var1) {
         this.e(var1);
         this.k();
         this.a.c(var1.a());
         this.t_();
         return (BuilderType)this;
      }

      protected boolean af() {
         return this.a.i();
      }

      private o<k.f> m() {
         this.a.c();
         return this.a;
      }

      @Override
      public boolean a() {
         return super.a() && this.af();
      }

      @Override
      protected boolean a(l1rpb.h var1, ap.a var2, n var3, int var4) throws IOException {
         return l1rpb.a.a.a(var1, var2, var3, this.J(), this, null, var4);
      }

      @Override
      public Map<k.f, Object> a_() {
         Map var1 = super.k();
         var1.putAll(this.a.g());
         return Collections.unmodifiableMap(var1);
      }

      @Override
      public Object b(k.f var1) {
         if (var1.t()) {
            this.h(var1);
            Object var2 = this.a.b(var1);
            if (var2 == null) {
               return var1.g() == k.f.a.i ? l.a(var1.w()) : var1.r();
            } else {
               return var2;
            }
         } else {
            return super.b(var1);
         }
      }

      @Override
      public int c(k.f var1) {
         if (var1.t()) {
            this.h(var1);
            return this.a.d(var1);
         } else {
            return super.c(var1);
         }
      }

      @Override
      public Object a(k.f var1, int var2) {
         if (var1.t()) {
            this.h(var1);
            return this.a.a(var1, var2);
         } else {
            return super.a(var1, var2);
         }
      }

      @Override
      public boolean a_(k.f var1) {
         if (var1.t()) {
            this.h(var1);
            return this.a.a(var1);
         } else {
            return super.a_(var1);
         }
      }

      public BuilderType e(k.f var1, Object var2) {
         if (var1.t()) {
            this.h(var1);
            this.k();
            this.a.a(var1, var2);
            this.t_();
            return (BuilderType)this;
         } else {
            return super.a(var1, var2);
         }
      }

      public BuilderType e(k.f var1) {
         if (var1.t()) {
            this.h(var1);
            this.k();
            this.a.c(var1);
            this.t_();
            return (BuilderType)this;
         } else {
            return super.d(var1);
         }
      }

      public BuilderType c(k.f var1, int var2, Object var3) {
         if (var1.t()) {
            this.h(var1);
            this.k();
            this.a.a(var1, var2, var3);
            this.t_();
            return (BuilderType)this;
         } else {
            return super.a(var1, var2, var3);
         }
      }

      public BuilderType f(k.f var1, Object var2) {
         if (var1.t()) {
            this.h(var1);
            this.k();
            this.a.b(var1, var2);
            this.t_();
            return (BuilderType)this;
         } else {
            return super.b(var1, var2);
         }
      }

      protected final void a(p.d var1) {
         this.k();
         this.a.a(var1.a);
         this.t_();
      }

      private void h(k.f var1) {
         if (var1.u() != this.J()) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
         }
      }

      // $VF: synthetic method
      @Override
      public p.a b(k.f var1, Object var2) {
         return this.f(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public p.a a(k.f var1, int var2, Object var3) {
         return this.c(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public p.a d(k.f var1) {
         return this.e(var1);
      }

      // $VF: synthetic method
      @Override
      public p.a a(k.f var1, Object var2) {
         return this.e(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public p.a ah() {
         return this.B();
      }

      // $VF: synthetic method
      @Override
      public p.a ai() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public l1rpb.a.a e() {
         return this.B();
      }

      // $VF: synthetic method
      @Override
      public l1rpb.a.a d() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public x.a c(k.f var1, Object var2) {
         return this.f(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a b(k.f var1, int var2, Object var3) {
         return this.c(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public x.a f(k.f var1) {
         return this.e(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a d(k.f var1, Object var2) {
         return this.e(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a i() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public x.a j() {
         return this.B();
      }

      // $VF: synthetic method
      @Override
      public y.a g() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public y.a h() {
         return this.B();
      }

      // $VF: synthetic method
      @Override
      public l1rpb.b.a f() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public Object clone() throws CloneNotSupportedException {
         return this.A();
      }
   }

   public abstract static class d<MessageType extends p.d> extends p implements p.e<MessageType> {
      private final o<k.f> a;

      protected d() {
         this.a = o.a();
      }

      protected d(p.c<MessageType, ?> var1) {
         super(var1);
         this.a = var1.m();
      }

      private void d(p.h<MessageType, ?> var1) {
         if (var1.a().u() != this.J()) {
            throw new IllegalArgumentException("Extension is for type \"" + var1.a().u().d() + "\" which does not match message type \"" + this.J().d() + "\".");
         }
      }

      @Override
      public final <Type> boolean a(p.h<MessageType, Type> var1) {
         this.d(var1);
         return this.a.a(var1.a());
      }

      @Override
      public final <Type> int b(p.h<MessageType, List<Type>> var1) {
         this.d(var1);
         k.f var2 = var1.a();
         return this.a.d(var2);
      }

      @Override
      public final <Type> Type c(p.h<MessageType, Type> var1) {
         this.d(var1);
         k.f var2 = var1.a();
         Object var3 = this.a.b(var2);
         if (var3 == null) {
            if (var2.n()) {
               return (Type)Collections.emptyList();
            } else {
               return (Type)(var2.g() == k.f.a.i ? var1.b() : var1.a(var2.r()));
            }
         } else {
            return (Type)var1.a(var3);
         }
      }

      @Override
      public final <Type> Type a(p.h<MessageType, List<Type>> var1, int var2) {
         this.d(var1);
         k.f var3 = var1.a();
         return (Type)var1.b(this.a.a(var3, var2));
      }

      protected boolean W() {
         return this.a.i();
      }

      @Override
      public boolean a() {
         return super.a() && this.W();
      }

      @Override
      protected boolean a(l1rpb.h var1, ap.a var2, n var3, int var4) throws IOException {
         return l1rpb.a.a.a(var1, var2, var3, this.J(), null, this.a, var4);
      }

      @Override
      protected void ad() {
         this.a.c();
      }

      protected p.d<MessageType>.a X() {
         return new p.d.a(false);
      }

      protected p.d<MessageType>.a Y() {
         return new p.d.a(true);
      }

      protected int Z() {
         return this.a.j();
      }

      protected int aa() {
         return this.a.k();
      }

      protected Map<k.f, Object> ab() {
         return this.a.g();
      }

      @Override
      public Map<k.f, Object> a_() {
         Map var1 = super.h();
         var1.putAll(this.ab());
         return Collections.unmodifiableMap(var1);
      }

      @Override
      public boolean a_(k.f var1) {
         if (var1.t()) {
            this.d(var1);
            return this.a.a(var1);
         } else {
            return super.a_(var1);
         }
      }

      @Override
      public Object b(k.f var1) {
         if (var1.t()) {
            this.d(var1);
            Object var2 = this.a.b(var1);
            if (var2 == null) {
               return var1.g() == k.f.a.i ? l.a(var1.w()) : var1.r();
            } else {
               return var2;
            }
         } else {
            return super.b(var1);
         }
      }

      @Override
      public int c(k.f var1) {
         if (var1.t()) {
            this.d(var1);
            return this.a.d(var1);
         } else {
            return super.c(var1);
         }
      }

      @Override
      public Object a(k.f var1, int var2) {
         if (var1.t()) {
            this.d(var1);
            return this.a.a(var1, var2);
         } else {
            return super.a(var1, var2);
         }
      }

      private void d(k.f var1) {
         if (var1.u() != this.J()) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
         }
      }

      protected class a {
         private final Iterator<Entry<k.f, Object>> b = d.this.a.h();
         private Entry<k.f, Object> c;
         private final boolean d;

         private a(boolean var2) {
            if (this.b.hasNext()) {
               this.c = this.b.next();
            }

            this.d = var2;
         }

         public void a(int var1, i var2) throws IOException {
            while (this.c != null && this.c.getKey().f() < var1) {
               k.f var3 = this.c.getKey();
               if (!this.d || var3.h() != as.b.i || var3.n()) {
                  o.a(var3, this.c.getValue(), var2);
               } else if (this.c instanceof t.a) {
                  var2.b(var3.f(), ((t.a)this.c).a().c());
               } else {
                  var2.d(var3.f(), (x)this.c.getValue());
               }

               if (this.b.hasNext()) {
                  this.c = this.b.next();
               } else {
                  this.c = null;
               }
            }
         }
      }
   }

   public interface e<MessageType extends p.d> extends aa {
      <Type> boolean a(p.h<MessageType, Type> var1);

      <Type> int b(p.h<MessageType, List<Type>> var1);

      <Type> Type c(p.h<MessageType, Type> var1);

      <Type> Type a(p.h<MessageType, List<Type>> var1, int var2);
   }

   private interface f {
      k.f a();
   }

   public static final class g {
      private final k.a a;
      private final p.g.a[] b;
      private String[] c;
      private volatile boolean d;

      public g(k.a var1, String[] var2, Class<? extends p> var3, Class<? extends p.a> var4) {
         this(var1, var2);
         this.a(var3, var4);
      }

      public g(k.a var1, String[] var2) {
         this.a = var1;
         this.c = var2;
         this.b = new p.g.a[var1.h().size()];
         this.d = false;
      }

      public p.g a(Class<? extends p> var1, Class<? extends p.a> var2) {
         if (this.d) {
            return this;
         }

         synchronized (this) {
            if (this.d) {
               return this;
            }

            for (int var4 = 0; var4 < this.b.length; var4++) {
               k.f var5 = this.a.h().get(var4);
               if (var5.n()) {
                  if (var5.g() == k.f.a.i) {
                     this.b[var4] = new p.g.d(var5, this.c[var4], var1, var2);
                  } else if (var5.g() == k.f.a.h) {
                     this.b[var4] = new p.g.b(var5, this.c[var4], var1, var2);
                  } else {
                     this.b[var4] = new p.g.c(var5, this.c[var4], var1, var2);
                  }
               } else if (var5.g() == k.f.a.i) {
                  this.b[var4] = new p.g.g(var5, this.c[var4], var1, var2);
               } else if (var5.g() == k.f.a.h) {
                  this.b[var4] = new p.g.e(var5, this.c[var4], var1, var2);
               } else {
                  this.b[var4] = new p.g.f(var5, this.c[var4], var1, var2);
               }
            }

            this.d = true;
            this.c = null;
            return this;
         }
      }

      private p.g.a a(k.f var1) {
         if (var1.u() != this.a) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
         } else if (var1.t()) {
            throw new IllegalArgumentException("This type does not have extensions.");
         } else {
            return this.b[var1.a()];
         }
      }

      private interface a {
         Object a(p var1);

         Object a(p.a var1);

         void a(p.a var1, Object var2);

         Object a(p var1, int var2);

         Object a(p.a var1, int var2);

         void a(p.a var1, int var2, Object var3);

         void b(p.a var1, Object var2);

         boolean b(p var1);

         boolean b(p.a var1);

         int c(p var1);

         int c(p.a var1);

         void d(p.a var1);

         x.a a();

         x.a e(p.a var1);
      }

      private static final class b extends p.g.c {
         private final Method k = p.b(this.a, "valueOf", k.e.class);
         private final Method l = p.b(this.a, "getValueDescriptor");

         b(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            super(var1, var2, var3, var4);
         }

         @Override
         public Object a(p var1) {
            List var2 = new ArrayList();

            for (Object var4 : (List)super.a(var1)) {
               var2.add(p.b(this.l, var4));
            }

            return Collections.unmodifiableList(var2);
         }

         @Override
         public Object a(p.a var1) {
            List var2 = new ArrayList();

            for (Object var4 : (List)super.a(var1)) {
               var2.add(p.b(this.l, var4));
            }

            return Collections.unmodifiableList(var2);
         }

         @Override
         public Object a(p var1, int var2) {
            return p.b(this.l, super.a(var1, var2));
         }

         @Override
         public Object a(p.a var1, int var2) {
            return p.b(this.l, super.a(var1, var2));
         }

         @Override
         public void a(p.a var1, int var2, Object var3) {
            super.a(var1, var2, p.b(this.k, null, var3));
         }

         @Override
         public void b(p.a var1, Object var2) {
            super.b(var1, p.b(this.k, null, var2));
         }
      }

      private static class c implements p.g.a {
         protected final Class a;
         protected final Method b;
         protected final Method c;
         protected final Method d;
         protected final Method e;
         protected final Method f;
         protected final Method g;
         protected final Method h;
         protected final Method i;
         protected final Method j;

         c(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            this.b = p.b(var3, "get" + var2 + "List");
            this.c = p.b(var4, "get" + var2 + "List");
            this.d = p.b(var3, "get" + var2, int.class);
            this.e = p.b(var4, "get" + var2, int.class);
            this.a = this.d.getReturnType();
            this.f = p.b(var4, "set" + var2, int.class, this.a);
            this.g = p.b(var4, "add" + var2, this.a);
            this.h = p.b(var3, "get" + var2 + "Count");
            this.i = p.b(var4, "get" + var2 + "Count");
            this.j = p.b(var4, "clear" + var2);
         }

         @Override
         public Object a(p var1) {
            return p.b(this.b, var1);
         }

         @Override
         public Object a(p.a var1) {
            return p.b(this.c, var1);
         }

         @Override
         public void a(p.a var1, Object var2) {
            this.d(var1);

            for (Object var4 : (List)var2) {
               this.b(var1, var4);
            }
         }

         @Override
         public Object a(p var1, int var2) {
            return p.b(this.d, var1, var2);
         }

         @Override
         public Object a(p.a var1, int var2) {
            return p.b(this.e, var1, var2);
         }

         @Override
         public void a(p.a var1, int var2, Object var3) {
            p.b(this.f, var1, var2, var3);
         }

         @Override
         public void b(p.a var1, Object var2) {
            p.b(this.g, var1, var2);
         }

         @Override
         public boolean b(p var1) {
            throw new UnsupportedOperationException("hasField() called on a repeated field.");
         }

         @Override
         public boolean b(p.a var1) {
            throw new UnsupportedOperationException("hasField() called on a repeated field.");
         }

         @Override
         public int c(p var1) {
            return (Integer)p.b(this.h, var1);
         }

         @Override
         public int c(p.a var1) {
            return (Integer)p.b(this.i, var1);
         }

         @Override
         public void d(p.a var1) {
            p.b(this.j, var1);
         }

         @Override
         public x.a a() {
            throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
         }

         @Override
         public x.a e(p.a var1) {
            throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
         }
      }

      private static final class d extends p.g.c {
         private final Method k = p.b(this.a, "newBuilder");

         d(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            super(var1, var2, var3, var4);
         }

         private Object a(Object var1) {
            return this.a.isInstance(var1) ? var1 : ((x.a)p.b(this.k, null)).c((x)var1).ak();
         }

         @Override
         public void a(p.a var1, int var2, Object var3) {
            super.a(var1, var2, this.a(var3));
         }

         @Override
         public void b(p.a var1, Object var2) {
            super.b(var1, this.a(var2));
         }

         @Override
         public x.a a() {
            return (x.a)p.b(this.k, null);
         }
      }

      private static final class e extends p.g.f {
         private Method h = p.b(this.a, "valueOf", k.e.class);
         private Method i = p.b(this.a, "getValueDescriptor");

         e(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            super(var1, var2, var3, var4);
         }

         @Override
         public Object a(p var1) {
            return p.b(this.i, super.a(var1));
         }

         @Override
         public Object a(p.a var1) {
            return p.b(this.i, super.a(var1));
         }

         @Override
         public void a(p.a var1, Object var2) {
            super.a(var1, p.b(this.h, null, var2));
         }
      }

      private static class f implements p.g.a {
         protected final Class<?> a;
         protected final Method b;
         protected final Method c;
         protected final Method d;
         protected final Method e;
         protected final Method f;
         protected final Method g;

         f(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            this.b = p.b(var3, "get" + var2);
            this.c = p.b(var4, "get" + var2);
            this.a = this.b.getReturnType();
            this.d = p.b(var4, "set" + var2, this.a);
            this.e = p.b(var3, "has" + var2);
            this.f = p.b(var4, "has" + var2);
            this.g = p.b(var4, "clear" + var2);
         }

         @Override
         public Object a(p var1) {
            return p.b(this.b, var1);
         }

         @Override
         public Object a(p.a var1) {
            return p.b(this.c, var1);
         }

         @Override
         public void a(p.a var1, Object var2) {
            p.b(this.d, var1, var2);
         }

         @Override
         public Object a(p var1, int var2) {
            throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
         }

         @Override
         public Object a(p.a var1, int var2) {
            throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
         }

         @Override
         public void a(p.a var1, int var2, Object var3) {
            throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
         }

         @Override
         public void b(p.a var1, Object var2) {
            throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
         }

         @Override
         public boolean b(p var1) {
            return (Boolean)p.b(this.e, var1);
         }

         @Override
         public boolean b(p.a var1) {
            return (Boolean)p.b(this.f, var1);
         }

         @Override
         public int c(p var1) {
            throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
         }

         @Override
         public int c(p.a var1) {
            throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
         }

         @Override
         public void d(p.a var1) {
            p.b(this.g, var1);
         }

         @Override
         public x.a a() {
            throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
         }

         @Override
         public x.a e(p.a var1) {
            throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
         }
      }

      private static final class g extends p.g.f {
         private final Method h = p.b(this.a, "newBuilder");
         private final Method i;

         g(k.f var1, String var2, Class<? extends p> var3, Class<? extends p.a> var4) {
            super(var1, var2, var3, var4);
            this.i = p.b(var4, "get" + var2 + "Builder");
         }

         private Object a(Object var1) {
            return this.a.isInstance(var1) ? var1 : ((x.a)p.b(this.h, null)).c((x)var1).aj();
         }

         @Override
         public void a(p.a var1, Object var2) {
            super.a(var1, this.a(var2));
         }

         @Override
         public x.a a() {
            return (x.a)p.b(this.h, null);
         }

         @Override
         public x.a e(p.a var1) {
            return (x.a)p.b(this.i, var1);
         }
      }
   }

   public static final class h<ContainingType extends x, Type> {
      private p.f a;
      private final Class b;
      private final x c;
      private final Method d;
      private final Method e;

      private h(p.f var1, Class var2, x var3) {
         if (x.class.isAssignableFrom(var2) && !var2.isInstance(var3)) {
            throw new IllegalArgumentException("Bad messageDefaultInstance for " + var2.getName());
         }

         this.a = var1;
         this.b = var2;
         this.c = var3;
         if (ac.class.isAssignableFrom(var2)) {
            this.d = p.b(var2, "valueOf", k.e.class);
            this.e = p.b(var2, "getValueDescriptor");
         } else {
            this.d = null;
            this.e = null;
         }
      }

      public void a(final k.f var1) {
         if (this.a != null) {
            throw new IllegalStateException("Already initialized.");
         }

         this.a = new p.f() {
            @Override
            public k.f a() {
               return var1;
            }
         };
      }

      public k.f a() {
         if (this.a == null) {
            throw new IllegalStateException("getDescriptor() called before internalInit()");
         } else {
            return this.a.a();
         }
      }

      public x b() {
         return this.c;
      }

      private Object a(Object var1) {
         k.f var2 = this.a();
         if (!var2.n()) {
            return this.b(var1);
         }

         if (var2.g() != k.f.a.i && var2.g() != k.f.a.h) {
            return var1;
         }

         List var3 = new ArrayList();

         for (Object var5 : (List)var1) {
            var3.add(this.b(var5));
         }

         return var3;
      }

      private Object b(Object var1) {
         k.f var2 = this.a();
         switch (var2.g()) {
            case i:
               if (this.b.isInstance(var1)) {
                  return var1;
               }

               return this.c.N().c((x)var1).ak();
            case h:
               return p.b(this.d, null, (k.e)var1);
            default:
               return var1;
         }
      }

      private Object c(Object var1) {
         k.f var2 = this.a();
         if (!var2.n()) {
            return this.d(var1);
         }

         if (var2.g() != k.f.a.h) {
            return var1;
         }

         List var3 = new ArrayList();

         for (Object var5 : (List)var1) {
            var3.add(this.d(var5));
         }

         return var3;
      }

      private Object d(Object var1) {
         k.f var2 = this.a();
         switch (var2.g()) {
            case h:
               return p.b(this.e, var1);
            default:
               return var1;
         }
      }
   }
}
