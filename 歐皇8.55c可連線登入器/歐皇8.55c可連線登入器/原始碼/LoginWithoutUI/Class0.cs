using System;
using System.Diagnostics;
using System.Text;

// Token: 0x02000002 RID: 2
internal class Class0
{
	// Token: 0x06000001 RID: 1 RVA: 0x00002150 File Offset: 0x00000350
	public void method_0(Process process_1)
	{
		this.process_0 = process_1;
		this.intptr_1 = process_1.Handle;
	}

	// Token: 0x06000002 RID: 2 RVA: 0x00002165 File Offset: 0x00000365
	public void method_1()
	{
		Class3.CloseHandle(this.intptr_1);
		this.intptr_1 = IntPtr.Zero;
	}

	// Token: 0x06000003 RID: 3 RVA: 0x00002278 File Offset: 0x00000478
	public byte[] method_2(int int_0, int int_1)
	{
		byte[] result = new byte[int_1];
		Class3.ReadProcessMemory(this.intptr_1, int_0, result, int_1, 0);
		return result;
	}

	// Token: 0x06000004 RID: 4 RVA: 0x000022A0 File Offset: 0x000004A0
	public byte method_3(int int_0)
	{
		byte[] array = new byte[1];
		Class3.ReadProcessMemory(this.intptr_1, int_0, array, 1, 0);
		return array[0];
	}

	// Token: 0x06000005 RID: 5 RVA: 0x000022CC File Offset: 0x000004CC
	public short method_4(int int_0)
	{
		short[] array = new short[1];
		Class3.ReadProcessMemory(this.intptr_1, int_0, array, 2, 0);
		return array[0];
	}

	// Token: 0x06000006 RID: 6 RVA: 0x000022F8 File Offset: 0x000004F8
	public int method_5(int int_0)
	{
		int[] array = new int[1];
		Class3.ReadProcessMemory(this.intptr_1, int_0, array, 4, 0);
		return array[0];
	}

	// Token: 0x06000007 RID: 7 RVA: 0x00002324 File Offset: 0x00000524
	public string method_6(int int_0, int int_1)
	{
		byte[] bytes = new byte[int_1];
		Class3.ReadProcessMemory(this.intptr_1, int_0, bytes, int_1, 0);
		string text = Encoding.Default.GetString(bytes) + "\0";
		return text.Substring(0, text.IndexOf("\0"));
	}

	// Token: 0x06000008 RID: 8 RVA: 0x0000217E File Offset: 0x0000037E
	public void method_7(int int_0, byte[] byte_1)
	{
		Class3.WriteProcessMemory(this.intptr_1, int_0, byte_1, byte_1.Length, 0);
	}

	// Token: 0x06000009 RID: 9 RVA: 0x00002192 File Offset: 0x00000392
	public void method_8(int int_0, byte[] byte_1, int int_1)
	{
		Class3.WriteProcessMemory(this.intptr_1, int_0, byte_1, int_1, 0);
	}

	// Token: 0x0600000A RID: 10 RVA: 0x000021A4 File Offset: 0x000003A4
	public void method_9(int int_0, byte byte_1)
	{
		Class3.WriteProcessMemory(this.intptr_1, int_0, ref byte_1, 1, 0);
	}

	// Token: 0x0600000B RID: 11 RVA: 0x000021B7 File Offset: 0x000003B7
	public void method_10(int int_0, short short_0)
	{
		Class3.WriteProcessMemory(this.intptr_1, int_0, ref short_0, 2, 0);
	}

	// Token: 0x0600000C RID: 12 RVA: 0x000021CA File Offset: 0x000003CA
	public void method_11(int int_0, int int_1)
	{
		Class3.WriteProcessMemory(this.intptr_1, int_0, ref int_1, 4, 0);
	}

	// Token: 0x0600000D RID: 13 RVA: 0x00002374 File Offset: 0x00000574
	public void method_12(int int_0, string string_1)
	{
		byte[] bytes = Encoding.Default.GetBytes(string_1);
		Class3.WriteProcessMemory(this.intptr_1, int_0, bytes, bytes.Length, 0);
	}

	// Token: 0x04000001 RID: 1
	public IntPtr intptr_0;

	// Token: 0x04000002 RID: 2
	public IntPtr intptr_1;

	// Token: 0x04000003 RID: 3
	public Process process_0;

	// Token: 0x04000004 RID: 4
	public byte[] byte_0 = null;

	// Token: 0x04000005 RID: 5
	public string string_0 = "";
}
