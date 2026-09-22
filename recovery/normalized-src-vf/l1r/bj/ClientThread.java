package l1r.bj;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.GameServer;
import l1r.ai.PacketHandler;
import l1r.ao.AccountTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_Disconnect;
import l1r.be.ServerBasePacket;
import l1r.bh.L1Account;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class ClientThread implements Runnable {
   private static final Logger c = Logger.getLogger(ClientThread.class.getName());
   private final InputStream d;
   private final OutputStream e;
   private final PacketHandler f;
   private ClientThread.L1R_a g;
   private final Socket h;
   private CipherClient i;
   private CipherServer j;
   public int a = 0;
   public long b = 0L;
   private ServerBasePacket k;
   private L1Account l;
   private L1PcInstance m = null;
   private final String n;
   private long o = System.currentTimeMillis();
   private long p = System.currentTimeMillis();
   private L1PcInstance q;
   private CopyOnWriteArrayList<String> r = new CopyOnWriteArrayList<>();
   private boolean s = false;
   private boolean t = false;

   public ClientThread(Socket var1) throws IOException {
      this.h = var1;
      this.n = var1.getInetAddress().getHostAddress();
      this.d = var1.getInputStream();
      this.e = new BufferedOutputStream(var1.getOutputStream());
      this.f = new PacketHandler(this);
   }

   private byte[] k() throws Exception {
      int var1 = this.d.read();
      int var2 = this.d.read();
      if (var1 < 0 || var2 < 0) {
         c.log(Level.SEVERE, " 【關閉連線】客戶端斷線了 ip=" + this.n);
         return null;
      }

      int var3 = (var2 << 8) + var1 - 2;
      if (var3 > 0 && var3 <= 65533) {
         byte[] var4 = new byte[var3];
         int var5 = 0;

         for (int var6 = 0; var6 != -1 && var5 < var3; var5 += var6) {
            var6 = this.d.read(var4, var5, var3 - var5);
         }

         if (var5 != var3) {
            c.log(Level.SEVERE, " 【關閉連線】客戶端封包長度不符合 ip=" + this.n);
            return null;
         } else {
            return this.i.b(var4);
         }
      } else {
         c.log(Level.SEVERE, " 【關閉連線】客戶端封包長度過大或過小 ip=" + this.n);
         return null;
      }
   }

   @Override
   public void run() {
      try {
         this.g = new ClientThread.L1R_a(null);
         this.g.a();
         this.a = Random.a(Integer.MAX_VALUE) + 1;
         int var1 = 7;
         this.e.write(7);
         this.e.write(0);
         this.e.write(165);
         this.e.write((byte)(this.a & 0xFF));
         this.e.write((byte)(this.a >> 8 & 0xFF));
         this.e.write((byte)(this.a >> 16 & 0xFF));
         this.e.write((byte)(this.a >> 24 & 0xFF));
         this.e.flush();
         System.out.println("(" + this.n + ") 連結到伺服器。");
         System.out.println("使用了 " + LineageUtil.a() + "MB 的記憶體");
         System.out.println(GeneralThreadPool.a().b());
         System.out.println("等待客戶端連接...");
         this.i = new CipherClient(this.a);
         this.j = new CipherServer(this.a);

         while (!this.s) {
            this.l();
            byte[] var2 = this.k();
            if (var2 == null) {
               if (this.k != null) {
                  byte[] var14 = this.k.a();
                  if ((var14[0] & 255) != 207) {
                     String var15 = "opcode: " + (var14[0] & 0xFF) + " [" + this.k.b() + "]\r\n" + LineageUtil.a(var14);
                     LineageUtil.a("./data/log/packetLog.txt", var15);
                  }
               }
               break;
            }

            byte[] var3 = new byte[var2.length - 4];
            System.arraycopy(var2, 4, var3, 0, var3.length);
            int var4 = var3[0] & 255;
            if (var4 != 158 || this.m == null) {
               if (var4 == 171 || var4 == 72) {
                  long var5 = System.currentTimeMillis();
                  this.b = var5;
               }

               this.f.a(var3);
            }
         }
      } catch (SocketException var11) {
      } catch (Exception var12) {
         c.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         this.c();
      }
   }

   public String a() {
      return this.l == null ? null : this.l.d();
   }

   private void l() {
      if (this.m != null) {
         if (Config.s * 1000 < System.currentTimeMillis() - this.o) {
            this.m.I();
            this.o = System.currentTimeMillis();
         }

         if (Config.t * 1000 < System.currentTimeMillis() - this.p) {
            this.m.J();
            this.p = System.currentTimeMillis();
         }
      }
   }

   public String b() {
      String var1 = null;
      if (!this.r.isEmpty()) {
         var1 = this.r.get(0);
         this.r.remove(0);
      }

      return var1;
   }

   public void a(int var1) {
      try {
         this.a(new S_Disconnect(var1));
      } catch (Exception var6) {
         c.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      } finally {
         this.c();
      }
   }

   public void c() {
      if (!this.s) {
         this.s = true;
         GameServer.a().b(this);

         try {
            this.h.close();
         } catch (Exception var10) {
            c.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            if (this.m != null) {
               synchronized (this.m) {
                  this.m.p();
                  this.m = null;
               }
            }

            if (this.l != null) {
               AccountTable.a().a(this.l, false);
               AccountTable.a().b(this.l, false);
               AccountTable.a().e(this.l.d());
            }

            System.out.println(this.n + " 【關閉連線】(" + L1World.a().c().size() + ")");
            System.out.println(GeneralThreadPool.a().b());
            System.out.println("使用了: " + LineageUtil.a() + "MB 的記憶體");
         }
      }
   }

   public void d() {
      if (!this.s) {
         this.s = true;
         GameServer.a().b(this);

         try {
            this.h.close();
         } catch (Exception var10) {
            c.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            if (this.m != null) {
               synchronized (this.m) {
                  this.m.p();
                  this.m = null;
               }
            }
         }
      }
   }

   public void a(ServerBasePacket var1) {
      this.g.a(var1);
   }

   public L1Account e() {
      return this.l;
   }

   public void a(L1Account var1) {
      this.l = var1;
   }

   public L1PcInstance f() {
      return this.m;
   }

   public void a(L1PcInstance var1) {
      this.m = var1;
   }

   public String g() {
      return this.n;
   }

   public L1PcInstance h() {
      return this.q;
   }

   public void b(L1PcInstance var1) {
      this.q = var1;
   }

   public CopyOnWriteArrayList<String> i() {
      return this.r;
   }

   public void a(CopyOnWriteArrayList<String> var1) {
      this.r = var1;
   }

   public boolean j() {
      return this.t;
   }

   public void a(boolean var1) {
      this.t = var1;
   }

   class L1R_a extends TimerTask {
      private final Queue<ServerBasePacket> b = new ConcurrentLinkedQueue<>();

      private L1R_a() {
      }

      private final void a(ServerBasePacket var1) {
         this.b.offer(var1);
      }

      private void a() {
         GeneralThreadPool.a().b(this, 0L);
      }

      private void a(int var1) {
         GeneralThreadPool.a().b(this, var1);
      }

      @Override
      public void run() {
         try {
            if (ClientThread.this.s) {
               throw new Exception();
            }

            while (!this.b.isEmpty()) {
               ServerBasePacket var1 = this.b.poll();
               byte[] var2 = var1.a();
               if (var2 != null && var2.length > 0) {
                  byte[] var3 = (byte[])var2.clone();
                  ClientThread.this.j.b(var3);
                  int var4 = var3.length + 2;
                  byte[] var5 = new byte[var4];
                  System.arraycopy(var3, 0, var5, 2, var3.length);
                  var5[0] = (byte)(var4 & 0xFF);
                  var5[1] = (byte)(var4 >> 8 & 0xFF);
                  ClientThread.this.e.write(var5);
                  ClientThread.this.e.flush();
                  ClientThread.this.k = var1;
               }
            }

            this.a(10);
         } catch (Exception var6) {
            this.b.clear();
            ClientThread.this.c();
         }
      }

      // $VF: synthetic method
      L1R_a(ClientThread.L1R_a var2) {
         this();
      }
   }
}
