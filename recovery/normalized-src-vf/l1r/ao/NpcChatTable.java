package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bh.L1NpcChat;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class NpcChatTable {
   private static final Logger a = Logger.getLogger(NpcChatTable.class.getName());
   private static NpcChatTable b;
   private final HashMap<Integer, L1NpcChat> c = new HashMap<>();
   private final HashMap<Integer, L1NpcChat> d = new HashMap<>();
   private final HashMap<Integer, L1NpcChat> e = new HashMap<>();

   public static NpcChatTable a() {
      if (b == null) {
         b = new NpcChatTable();
      }

      return b;
   }

   private NpcChatTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM npcchat");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1NpcChat var4 = new L1NpcChat();
            var4.a(var3.getInt("npc_id"));
            var4.b(var3.getInt("chat_timing"));
            var4.c(var3.getInt("start_delay_time"));
            var4.a(var3.getString("chat_id1"));
            var4.b(var3.getString("chat_id2"));
            var4.c(var3.getString("chat_id3"));
            var4.d(var3.getString("chat_id4"));
            var4.e(var3.getString("chat_id5"));
            var4.d(var3.getInt("chat_interval"));
            var4.a(var3.getBoolean("is_shout"));
            var4.b(var3.getBoolean("is_world_chat"));
            var4.c(var3.getBoolean("is_repeat"));
            var4.e(var3.getInt("repeat_interval"));
            var4.f(var3.getInt("chance"));
            var4.d(var3.getBoolean("is_screen_only"));
            if (var4.b() == 0) {
               this.c.put(new Integer(var4.a()), var4);
            } else if (var4.b() == 1) {
               this.d.put(new Integer(var4.a()), var4);
            } else if (var4.b() == 2) {
               this.e.put(new Integer(var4.a()), var4);
            }
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1NpcChat a(int var1) {
      return this.c.get(new Integer(var1));
   }

   public L1NpcChat b(int var1) {
      return this.d.get(new Integer(var1));
   }

   public L1NpcChat c(int var1) {
      return this.e.get(new Integer(var1));
   }
}
