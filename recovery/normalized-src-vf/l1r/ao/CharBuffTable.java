package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharBuffTable {
   private static final Logger a = Logger.getLogger(CharBuffTable.class.getName());
   private static final int[] b = new int[]{
      2,
      3,
      26,
      42,
      43,
      52,
      54,
      67,
      29,
      99,
      101,
      109,
      110,
      111,
      114,
      115,
      117,
      148,
      149,
      150,
      151,
      155,
      156,
      159,
      163,
      166,
      168,
      186,
      32,
      14,
      68,
      71,
      78,
      104,
      47,
      56,
      55,
      158,
      167,
      153,
      134,
      171,
      174,
      173,
      176,
      206,
      216,
      217,
      191,
      193,
      211,
      183,
      181,
      64,
      185,
      190,
      195,
      202,
      89,
      188,
      201,
      106,
      218,
      120,
      112,
      33,
      11,
      1007,
      1001,
      1000,
      1016,
      1017,
      1026,
      1027,
      1002,
      1005,
      1031,
      1006,
      1037,
      1038,
      3000,
      3008,
      3001,
      3009,
      3002,
      3010,
      3003,
      3011,
      3004,
      3012,
      3005,
      3013,
      3006,
      3014,
      3016,
      3024,
      3017,
      3025,
      3018,
      3026,
      3019,
      3027,
      3020,
      3028,
      3021,
      3029,
      3022,
      3030,
      3032,
      3040,
      3033,
      3041,
      3034,
      3042,
      3035,
      3043,
      3036,
      3044,
      3037,
      3045,
      3038,
      3046,
      3007,
      3015,
      3023,
      3031,
      3039,
      3047,
      4001,
      4002,
      4003,
      4004,
      4005,
      4007,
      4086,
      4087,
      4088,
      4089,
      4090,
      4091,
      4006,
      4010,
      4008,
      4009,
      3048,
      3049,
      3050,
      3051,
      3052,
      3053,
      3054,
      3055,
      3056,
      4070,
      4078,
      4011,
      4012,
      4077,
      4013,
      4014,
      4015,
      4016,
      4017,
      4018,
      4019,
      4020,
      4021,
      4022,
      4023,
      4024,
      4025,
      4026,
      4027,
      4028,
      4029,
      4030,
      4031,
      4032,
      4033,
      4034,
      4035,
      4036,
      4037,
      4038,
      4039,
      4040,
      4041,
      4042,
      4043,
      4044,
      4045,
      4046,
      4047,
      4048,
      4049,
      4050,
      4051,
      4052,
      4053,
      4054,
      4055,
      4056,
      4057,
      4079,
      25009,
      25010,
      25011,
      25007,
      25012,
      4076,
      4080,
      4092
   };

   private CharBuffTable() {
   }

   private static void a(int var0, int var1, int var2, int var3, Timestamp var4) {
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = DatabaseFactory.a().b();
         var6 = var5.prepareStatement("INSERT INTO character_buff SET char_obj_id=?, skill_id=?, remaining_time=?, poly_id=?, limit_time=?");
         var6.setInt(1, var0);
         var6.setInt(2, var1);
         var6.setInt(3, var2);
         var6.setInt(4, var3);
         var6.setTimestamp(5, var4);
         var6.execute();
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5);
      }
   }

   private static void b(L1PcInstance var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("DELETE FROM character_buff WHERE char_obj_id=?");
         var2.setInt(1, var0.fr());
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public static void a(L1PcInstance var0) {
      b(var0);
      int[] var4 = b;
      int var3 = b.length;

      for (int var2 = 0; var2 < var3; var2++) {
         int var1 = var4[var2];
         int var5 = var0.bC(var1);
         if (var5 > 0) {
            int var6 = 0;
            if (var1 == 67) {
               var6 = var0.fe();
            }

            a(var0.fr(), var1, var5, var6, var0.bD(var1));
         }
      }
   }
}
