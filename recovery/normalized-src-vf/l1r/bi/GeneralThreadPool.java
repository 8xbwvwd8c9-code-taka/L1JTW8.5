package l1r.bi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.l1j.server.Config;

public class GeneralThreadPool {
   private static final Logger a = Logger.getLogger(GeneralThreadPool.class.getName());
   private final Executor b;
   private final ScheduledExecutorService c;
   private final Executor d;
   private final ScheduledExecutorService e;
   private static GeneralThreadPool f;
   private int g = Config.o;
   private int h = Config.o * 3;
   private int i;
   private int j = Config.o * 2;

   public static GeneralThreadPool a() {
      if (f == null) {
         f = new GeneralThreadPool();
      }

      return f;
   }

   private GeneralThreadPool() {
      this.i = Config.o * 3;
      if (Config.c) {
         this.g = 200;
         this.h = 200;
         this.j = 400;
         this.i = 400;
      }

      this.b = Executors.newScheduledThreadPool(this.j);
      this.c = Executors.newScheduledThreadPool(this.i, new GeneralThreadPool.L1R_a("GerenalSTPool", 5, null));
      this.d = Executors.newScheduledThreadPool(this.h);
      this.e = Executors.newScheduledThreadPool(this.g, new GeneralThreadPool.L1R_a("PcMonitorSTPool", 5, null));
   }

   public String b() {
      String var1 = "";
      var1 = var1 + "系統線程池使用量:";
      var1 = var1 + "[EXECUTOR:" + ((ThreadPoolExecutor)this.b).getActiveCount() + "/" + this.j + "]\t";
      var1 = var1 + "[SCHEDULED:" + ((ThreadPoolExecutor)this.c).getActiveCount() + "/" + this.i + "]\r\n";
      var1 = var1 + "玩家線程池使用量:";
      var1 = var1 + "[EXECUTOR:" + ((ThreadPoolExecutor)this.d).getActiveCount() + "/" + this.h + "]\t";
      return var1 + "[SCHEDULED:" + ((ThreadPoolExecutor)this.e).getActiveCount() + "/" + this.g + "]";
   }

   public void a(Runnable var1) {
      try {
         this.b.execute(var1);
      } catch (OutOfMemoryError var3) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
      } catch (Exception var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }

   public ScheduledFuture<?> a(Runnable var1, long var2) {
      try {
         if (var2 <= 0L) {
            this.b.execute(var1);
            return null;
         } else {
            return this.c.schedule(var1, var2, TimeUnit.MILLISECONDS);
         }
      } catch (OutOfMemoryError var5) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
         return null;
      } catch (RejectedExecutionException var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         return null;
      } catch (Exception var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
         return null;
      }
   }

   public ScheduledFuture<?> a(Runnable var1, long var2, long var4) {
      try {
         return this.c.scheduleAtFixedRate(var1, var2, var4, TimeUnit.MILLISECONDS);
      } catch (OutOfMemoryError var7) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
         return null;
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return null;
      }
   }

   public void b(Runnable var1) {
      try {
         this.d.execute(var1);
      } catch (OutOfMemoryError var3) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
      } catch (Exception var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }

   public ScheduledFuture<?> b(Runnable var1, long var2) {
      try {
         if (var2 <= 0L) {
            this.d.execute(var1);
            return null;
         } else {
            return this.e.schedule(var1, var2, TimeUnit.MILLISECONDS);
         }
      } catch (OutOfMemoryError var5) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
         return null;
      } catch (RejectedExecutionException var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         return null;
      } catch (Exception var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
         return null;
      }
   }

   public ScheduledFuture<?> b(Runnable var1, long var2, long var4) {
      try {
         return this.e.scheduleAtFixedRate(var1, var2, var4, TimeUnit.MILLISECONDS);
      } catch (OutOfMemoryError var7) {
         a.log(Level.SEVERE, "[Error] Thread pool is out of Memory");
         a.log(Level.SEVERE, this.b());
         return null;
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return null;
      }
   }

   private class L1R_a implements ThreadFactory {
      private final int b;
      private final String c;
      private final AtomicInteger d = new AtomicInteger(1);
      private final ThreadGroup e;

      private L1R_a(String var2, int var3) {
         this.b = var3;
         this.c = var2;
         this.e = new ThreadGroup(this.c);
      }

      @Override
      public Thread newThread(Runnable var1) {
         Thread var2 = new Thread(this.e, var1);
         var2.setName(this.c + "-" + this.d.getAndIncrement());
         var2.setPriority(this.b);
         return var2;
      }

      // $VF: synthetic method
      L1R_a(String var2, int var3, GeneralThreadPool.L1R_a var4) {
         this(var2, var3);
      }
   }
}
