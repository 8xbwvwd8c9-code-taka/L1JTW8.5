using System;
using System.Collections.Specialized;
using System.Diagnostics;
using System.IO;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;

// Token: 0x02000005 RID: 5
internal class Class3
{
	// Token: 0x06000014 RID: 20
	[DllImport("user32.dll")]
	public static extern IntPtr FindWindow(string string_0, string string_1);

	// Token: 0x06000015 RID: 21
	[DllImport("user32.dll")]
	public static extern IntPtr GetForegroundWindow();

	// Token: 0x06000016 RID: 22
	[DllImport("user32.dll")]
	public static extern uint GetWindowThreadProcessId(IntPtr intptr_0, out int int_13);

	// Token: 0x06000017 RID: 23
	[DllImport("kernel32.dll")]
	public static extern IntPtr OpenProcess(Class3.Enum0 enum0_0, int int_13, int int_14);

	// Token: 0x06000018 RID: 24
	[DllImport("kernel32.dll")]
	public static extern bool CloseHandle(IntPtr intptr_0);

	// Token: 0x06000019 RID: 25
	[DllImport("kernel32.dll")]
	public static extern bool ReadProcessMemory(IntPtr intptr_0, int int_13, byte[] byte_0, int int_14, int int_15);

	// Token: 0x0600001A RID: 26
	[DllImport("kernel32.dll")]
	public static extern bool ReadProcessMemory(IntPtr intptr_0, int int_13, short[] short_0, int int_14, int int_15);

	// Token: 0x0600001B RID: 27
	[DllImport("kernel32.dll")]
	public static extern bool ReadProcessMemory(IntPtr intptr_0, int int_13, int[] int_14, int int_15, int int_16);

	// Token: 0x0600001C RID: 28
	[DllImport("kernel32.dll")]
	public static extern bool WriteProcessMemory(IntPtr intptr_0, int int_13, byte[] byte_0, int int_14, int int_15);

	// Token: 0x0600001D RID: 29
	[DllImport("kernel32.dll")]
	public static extern bool WriteProcessMemory(IntPtr intptr_0, int int_13, ref byte byte_0, int int_14, int int_15);

	// Token: 0x0600001E RID: 30
	[DllImport("kernel32.dll")]
	public static extern bool WriteProcessMemory(IntPtr intptr_0, int int_13, ref short short_0, int int_14, int int_15);

	// Token: 0x0600001F RID: 31
	[DllImport("kernel32.dll")]
	public static extern bool WriteProcessMemory(IntPtr intptr_0, int int_13, ref int int_14, int int_15, int int_16);

	// Token: 0x06000020 RID: 32
	[DllImport("user32.dll")]
	public static extern bool IsZoomed(IntPtr intptr_0);

	// Token: 0x06000021 RID: 33
	[DllImport("user32.dll")]
	public static extern bool IsIconic(IntPtr intptr_0);

	// Token: 0x06000022 RID: 34
	[DllImport("user32.dll")]
	public static extern bool IsWindow(IntPtr intptr_0);

	// Token: 0x06000023 RID: 35
	[DllImport("user32.dll")]
	public static extern int GetClassName(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_13);

	// Token: 0x06000024 RID: 36
	[DllImport("user32.dll")]
	public static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_13);

	// Token: 0x06000025 RID: 37
	[DllImport("kernel32.dll")]
	public static extern int VirtualAllocEx(IntPtr intptr_0, int int_13, uint uint_0, Class3.Enum1 enum1_0, Class3.Enum2 enum2_0);

	// Token: 0x06000026 RID: 38
	[DllImport("kernel32.dll")]
	public static extern int VirtualFreeEx(IntPtr intptr_0, int int_13, int int_14, int int_15);

	// Token: 0x06000027 RID: 39
	[DllImport("kernel32.dll")]
	public static extern IntPtr CreateRemoteThread(IntPtr intptr_0, int int_13, int int_14, int int_15, int int_16, int int_17, int int_18);

	// Token: 0x06000028 RID: 40
	[DllImport("kernel32.dll")]
	public static extern uint WaitForSingleObject(IntPtr intptr_0, uint uint_0);

	// Token: 0x06000029 RID: 41
	[DllImport("ntdll.dll")]
	public static extern int RtlAdjustPrivilege(int int_13, bool bool_0, bool bool_1, ref int int_14);

	// Token: 0x0600002A RID: 42
	[DllImport("user32.dll")]
	public static extern int RegisterWindowMessage(string string_0);

	// Token: 0x0600002B RID: 43
	[DllImport("user32.dll")]
	public static extern int RegisterShellHookWindow(IntPtr intptr_0);

	// Token: 0x0600002C RID: 44
	[DllImport("user32.dll")]
	public static extern int DeregisterShellHookWindow(IntPtr intptr_0);

	// Token: 0x0600002D RID: 45
	[DllImport("shell32.dll", EntryPoint = "#181")]
	public static extern int shell32_181(IntPtr intptr_0, int int_13);

	// Token: 0x0600002E RID: 46
	[DllImport("user32.dll")]
	public static extern IntPtr GetDC(IntPtr intptr_0);

	// Token: 0x0600002F RID: 47
	[DllImport("user32.dll")]
	public static extern int ReleaseDC(IntPtr intptr_0, IntPtr intptr_1);

	// Token: 0x06000030 RID: 48
	[DllImport("gdi32.dll")]
	public static extern bool TextOut(IntPtr intptr_0, int int_13, int int_14, string string_0, int int_15);

	// Token: 0x06000031 RID: 49
	[DllImport("user32.dll", SetLastError = true)]
	public static extern bool PostMessage(IntPtr intptr_0, uint uint_0, int int_13, int int_14);

	// Token: 0x06000032 RID: 50
	[DllImport("kernel32.dll")]
	public static extern uint GetTickCount();

	// Token: 0x06000033 RID: 51
	[DllImport("user32.dll")]
	public static extern bool EnumWindows(Class3.Delegate0 delegate0_0, int int_13);

	// Token: 0x06000034 RID: 52
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	public static extern int GetPrivateProfileInt(string string_0, string string_1, int int_13, string string_2);

	// Token: 0x06000035 RID: 53
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	public static extern int GetPrivateProfileString(string string_0, string string_1, string string_2, StringBuilder stringBuilder_0, int int_13, string string_3);

	// Token: 0x06000036 RID: 54
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	public static extern int GetPrivateProfileSection(string string_0, byte[] byte_0, int int_13, string string_1);

	// Token: 0x06000037 RID: 55
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	public static extern int WritePrivateProfileString(string string_0, string string_1, string string_2, string string_3);

	// Token: 0x06000038 RID: 56
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	public static extern bool WritePrivateProfileSection(string string_0, string string_1, string string_2);

	// Token: 0x06000039 RID: 57 RVA: 0x000024F0 File Offset: 0x000006F0
	public static int smethod_0(string string_0, string string_1, int int_13, string string_2)
	{
		return Class3.GetPrivateProfileInt(string_0, string_1, int_13, string_2);
	}

	// Token: 0x0600003A RID: 58 RVA: 0x00002508 File Offset: 0x00000708
	public static string smethod_1(string string_0, string string_1, string string_2, string string_3)
	{
		StringBuilder stringBuilder = new StringBuilder(1024);
		Class3.GetPrivateProfileString(string_0, string_1, string_2, stringBuilder, stringBuilder.Capacity, string_3);
		return stringBuilder.ToString();
	}

	// Token: 0x0600003B RID: 59 RVA: 0x0000253C File Offset: 0x0000073C
	public static NameValueCollection smethod_2(string string_0, string string_1)
	{
		NameValueCollection nameValueCollection = new NameValueCollection();
		if (File.Exists(string_1))
		{
			int num = Convert.ToInt32(new FileInfo(string_1).Length);
			byte[] array = new byte[num + 1];
			int privateProfileSection = Class3.GetPrivateProfileSection(string_0, array, num, string_1);
			int num2 = 0;
			for (int i = 0; i <= privateProfileSection - 1; i++)
			{
				if (array[i] == 0)
				{
					string @string = Encoding.Default.GetString(array, num2, i - num2);
					num2 = i + 1;
					int num3 = @string.IndexOf("=");
					string name = @string.Substring(0, num3);
					string value = @string.Substring(num3 + 1);
					nameValueCollection.Add(name, value);
				}
			}
		}
		return nameValueCollection;
	}

	// Token: 0x0600003C RID: 60 RVA: 0x000025FC File Offset: 0x000007FC
	public static int smethod_3(string string_0, string string_1, string string_2, string string_3)
	{
		return Class3.WritePrivateProfileString(string_0, string_1, string_2, string_3);
	}

	// Token: 0x0600003D RID: 61 RVA: 0x00002213 File Offset: 0x00000413
	public static bool smethod_4(string string_0, string string_1, string string_2)
	{
		return Class3.WritePrivateProfileSection(string_0, string_1, string_2);
	}

	// Token: 0x0600003E RID: 62 RVA: 0x00002614 File Offset: 0x00000814
	public static string smethod_5(IntPtr intptr_0)
	{
		StringBuilder stringBuilder = new StringBuilder(1024);
		Class3.GetClassName(intptr_0, stringBuilder, stringBuilder.Capacity);
		return stringBuilder.ToString();
	}

	// Token: 0x0600003F RID: 63 RVA: 0x00002644 File Offset: 0x00000844
	public static string smethod_6(IntPtr intptr_0)
	{
		StringBuilder stringBuilder = new StringBuilder(1024);
		Class3.GetWindowText(intptr_0, stringBuilder, stringBuilder.Capacity);
		return stringBuilder.ToString();
	}

	// Token: 0x06000040 RID: 64
	[DllImport("kernel32.dll")]
	public static extern bool SetProcessWorkingSetSize(IntPtr intptr_0, int int_13, int int_14);

	// Token: 0x06000041 RID: 65
	[DllImport("user32.dll")]
	public static extern IntPtr GetDesktopWindow();

	// Token: 0x06000042 RID: 66
	[DllImport("user32.dll")]
	public static extern IntPtr GetWindow(IntPtr intptr_0, Class3.Enum3 enum3_0);

	// Token: 0x06000043 RID: 67
	[DllImport("kernel32.dll")]
	private static extern IntPtr OpenThread(Class3.Enum4 enum4_0, bool bool_0, uint uint_0);

	// Token: 0x06000044 RID: 68
	[DllImport("kernel32.dll")]
	private static extern uint SuspendThread(IntPtr intptr_0);

	// Token: 0x06000045 RID: 69
	[DllImport("kernel32.dll")]
	private static extern int ResumeThread(IntPtr intptr_0);

	// Token: 0x06000046 RID: 70
	[DllImport("user32.dll", SetLastError = true)]
	private static extern uint GetWindowThreadProcessId(IntPtr intptr_0, out uint uint_0);

	// Token: 0x06000047 RID: 71 RVA: 0x00002674 File Offset: 0x00000874
	public static void smethod_7(int int_13)
	{
		Process processById = Process.GetProcessById(int_13);
		if (!(processById.ProcessName == string.Empty))
		{
			foreach (object obj in processById.Threads)
			{
				ProcessThread processThread = (ProcessThread)obj;
				IntPtr intPtr = Class3.OpenThread(Class3.Enum4.flag_1, false, (uint)processThread.Id);
				if (!(intPtr == IntPtr.Zero))
				{
					Class3.SuspendThread(intPtr);
					Class3.CloseHandle(intPtr);
				}
			}
		}
	}

	// Token: 0x06000048 RID: 72 RVA: 0x0000270C File Offset: 0x0000090C
	public static void smethod_8(int int_13)
	{
		Process processById = Process.GetProcessById(int_13);
		if (!(processById.ProcessName == string.Empty))
		{
			foreach (object obj in processById.Threads)
			{
				ProcessThread processThread = (ProcessThread)obj;
				IntPtr intPtr = Class3.OpenThread(Class3.Enum4.flag_1, false, (uint)processThread.Id);
				if (!(intPtr == IntPtr.Zero))
				{
					int num;
					do
					{
						num = Class3.ResumeThread(intPtr);
					}
					while (num > 0);
					Class3.CloseHandle(intPtr);
				}
			}
		}
	}

	// Token: 0x06000049 RID: 73
	[DllImport("user32.dll")]
	public static extern bool RegisterHotKey(IntPtr intptr_0, int int_13, int int_14, int int_15);

	// Token: 0x0600004A RID: 74
	[DllImport("user32.dll")]
	public static extern bool GetWindowRect(IntPtr intptr_0, ref Class3.Struct0 struct0_0);

	// Token: 0x0600004B RID: 75
	[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
	public static extern int SetWindowsHookEx(int int_13, Class3.Delegate1 delegate1_0, IntPtr intptr_0, int int_14);

	// Token: 0x0600004C RID: 76
	[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
	public static extern bool UnhookWindowsHookEx(int int_13);

	// Token: 0x0600004D RID: 77
	[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
	public static extern int CallNextHookEx(int int_13, int int_14, int int_15, IntPtr intptr_0);

	// Token: 0x0600004E RID: 78
	[DllImport("kernel32.dll")]
	public static extern IntPtr GetModuleHandle(string string_0);

	// Token: 0x0600004F RID: 79
	[DllImport("user32.dll")]
	public static extern bool SetForegroundWindow(IntPtr intptr_0);

	// Token: 0x04000006 RID: 6
	public const int int_0 = 1;

	// Token: 0x04000007 RID: 7
	public const int int_1 = 2;

	// Token: 0x04000008 RID: 8
	public const int int_2 = 3;

	// Token: 0x04000009 RID: 9
	public const int int_3 = 4;

	// Token: 0x0400000A RID: 10
	public const int int_4 = 5;

	// Token: 0x0400000B RID: 11
	public const int int_5 = 6;

	// Token: 0x0400000C RID: 12
	public const int int_6 = 7;

	// Token: 0x0400000D RID: 13
	public const int int_7 = 8;

	// Token: 0x0400000E RID: 14
	public const int int_8 = 4;

	// Token: 0x0400000F RID: 15
	public const int int_9 = 64;

	// Token: 0x04000010 RID: 16
	public const int int_10 = 4096;

	// Token: 0x04000011 RID: 17
	public const int int_11 = 32768;

	// Token: 0x04000012 RID: 18
	public const int int_12 = 16384;

	// Token: 0x02000006 RID: 6
	public enum Enum0 : uint
	{
		// Token: 0x04000014 RID: 20
		const_0 = 2035711u,
		// Token: 0x04000015 RID: 21
		const_1 = 1u,
		// Token: 0x04000016 RID: 22
		const_2,
		// Token: 0x04000017 RID: 23
		const_3 = 8u,
		// Token: 0x04000018 RID: 24
		const_4 = 16u,
		// Token: 0x04000019 RID: 25
		const_5 = 32u,
		// Token: 0x0400001A RID: 26
		const_6 = 64u,
		// Token: 0x0400001B RID: 27
		const_7 = 512u,
		// Token: 0x0400001C RID: 28
		const_8 = 1024u,
		// Token: 0x0400001D RID: 29
		const_9 = 1048576u
	}

	// Token: 0x02000007 RID: 7
	[Flags]
	public enum Enum1
	{
		// Token: 0x0400001F RID: 31
		flag_0 = 4096,
		// Token: 0x04000020 RID: 32
		flag_1 = 8192,
		// Token: 0x04000021 RID: 33
		flag_2 = 16384,
		// Token: 0x04000022 RID: 34
		flag_3 = 32768,
		// Token: 0x04000023 RID: 35
		flag_4 = 524288,
		// Token: 0x04000024 RID: 36
		flag_5 = 4194304,
		// Token: 0x04000025 RID: 37
		flag_6 = 1048576,
		// Token: 0x04000026 RID: 38
		flag_7 = 2097152,
		// Token: 0x04000027 RID: 39
		flag_8 = 536870912
	}

	// Token: 0x02000008 RID: 8
	[Flags]
	public enum Enum2
	{
		// Token: 0x04000029 RID: 41
		flag_0 = 16,
		// Token: 0x0400002A RID: 42
		flag_1 = 32,
		// Token: 0x0400002B RID: 43
		flag_2 = 64,
		// Token: 0x0400002C RID: 44
		flag_3 = 128,
		// Token: 0x0400002D RID: 45
		flag_4 = 1,
		// Token: 0x0400002E RID: 46
		flag_5 = 2,
		// Token: 0x0400002F RID: 47
		flag_6 = 4,
		// Token: 0x04000030 RID: 48
		flag_7 = 8,
		// Token: 0x04000031 RID: 49
		flag_8 = 256,
		// Token: 0x04000032 RID: 50
		flag_9 = 512,
		// Token: 0x04000033 RID: 51
		flag_10 = 1024
	}

	// Token: 0x02000009 RID: 9
	// (Invoke) Token: 0x06000052 RID: 82
	public delegate bool Delegate0(IntPtr intptr_0, int int_0);

	// Token: 0x0200000A RID: 10
	public enum Enum3 : uint
	{
		// Token: 0x04000035 RID: 53
		const_0,
		// Token: 0x04000036 RID: 54
		const_1,
		// Token: 0x04000037 RID: 55
		const_2,
		// Token: 0x04000038 RID: 56
		const_3,
		// Token: 0x04000039 RID: 57
		const_4,
		// Token: 0x0400003A RID: 58
		const_5,
		// Token: 0x0400003B RID: 59
		const_6
	}

	// Token: 0x0200000B RID: 11
	[Flags]
	public enum Enum4
	{
		// Token: 0x0400003D RID: 61
		flag_0 = 1,
		// Token: 0x0400003E RID: 62
		flag_1 = 2,
		// Token: 0x0400003F RID: 63
		flag_2 = 8,
		// Token: 0x04000040 RID: 64
		flag_3 = 16,
		// Token: 0x04000041 RID: 65
		flag_4 = 32,
		// Token: 0x04000042 RID: 66
		flag_5 = 64,
		// Token: 0x04000043 RID: 67
		flag_6 = 128,
		// Token: 0x04000044 RID: 68
		flag_7 = 256,
		// Token: 0x04000045 RID: 69
		flag_8 = 512
	}

	// Token: 0x0200000C RID: 12
	public struct Struct0
	{
		// Token: 0x06000055 RID: 85 RVA: 0x0000221D File Offset: 0x0000041D
		[CompilerGenerated]
		public int method_0()
		{
			return this.int_0;
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00002225 File Offset: 0x00000425
		[CompilerGenerated]
		public void method_1(int int_4)
		{
			this.int_0 = int_4;
		}

		// Token: 0x06000057 RID: 87 RVA: 0x0000222E File Offset: 0x0000042E
		[CompilerGenerated]
		public int method_2()
		{
			return this.int_1;
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00002236 File Offset: 0x00000436
		[CompilerGenerated]
		public void method_3(int int_4)
		{
			this.int_1 = int_4;
		}

		// Token: 0x06000059 RID: 89 RVA: 0x0000223F File Offset: 0x0000043F
		[CompilerGenerated]
		public int method_4()
		{
			return this.int_2;
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00002247 File Offset: 0x00000447
		[CompilerGenerated]
		public void method_5(int int_4)
		{
			this.int_2 = int_4;
		}

		// Token: 0x0600005B RID: 91 RVA: 0x00002250 File Offset: 0x00000450
		[CompilerGenerated]
		public int method_6()
		{
			return this.int_3;
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00002258 File Offset: 0x00000458
		[CompilerGenerated]
		public void method_7(int int_4)
		{
			this.int_3 = int_4;
		}

		// Token: 0x04000046 RID: 70
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private int int_0;

		// Token: 0x04000047 RID: 71
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private int int_1;

		// Token: 0x04000048 RID: 72
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private int int_2;

		// Token: 0x04000049 RID: 73
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private int int_3;
	}

	// Token: 0x0200000D RID: 13
	public struct Struct1
	{
		// Token: 0x0400004A RID: 74
		public int int_0;

		// Token: 0x0400004B RID: 75
		public int int_1;

		// Token: 0x0400004C RID: 76
		public int int_2;

		// Token: 0x0400004D RID: 77
		public int int_3;

		// Token: 0x0400004E RID: 78
		public int int_4;
	}

	// Token: 0x0200000E RID: 14
	// (Invoke) Token: 0x0600005E RID: 94
	public delegate int Delegate1(int int_0, int int_1, IntPtr intptr_0);
}
