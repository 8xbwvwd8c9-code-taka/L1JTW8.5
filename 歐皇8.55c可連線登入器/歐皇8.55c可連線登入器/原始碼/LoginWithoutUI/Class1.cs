using System;
using System.Diagnostics;
using System.Threading;

// Token: 0x02000003 RID: 3
internal class Class1
{
	// Token: 0x0600000F RID: 15 RVA: 0x000021F7 File Offset: 0x000003F7
	private static void Main(string[] args)
	{
		new Class1.Class2().method_0();
	}

	// Token: 0x02000004 RID: 4
	private class Class2
	{
		// Token: 0x06000012 RID: 18 RVA: 0x000023A0 File Offset: 0x000005A0
		public void method_0()
		{
			try
			{
				try
				{
					Process process = Process.Start(new ProcessStartInfo
					{
						FileName = "Lin.bin2",
						UseShellExecute = false,
						Arguments = "2130706433"
					});
					process.Exited += this.method_1;
					process.EnableRaisingEvents = true;
					process.WaitForInputIdle();
					int num = 0;

                    Console.WriteLine("正在啟動天堂主程式...");

                    string line;
                    System.IO.StreamReader file = new System.IO.StreamReader(@"ip.ini");


                    string ip = file.ReadLine();
                    int port = Convert.ToInt32(file.ReadLine());
                    file.Close();

                    while (num++ < 300)
					{
						if (!process.HasExited)
						{
							process.Refresh();
						}
						//Console.WriteLine("Wait Lineage Process Loading....MainWindowHandle=" + process.MainWindowHandle.ToString());
						if (process.MainWindowHandle != IntPtr.Zero)
						{
							Class0 @class = new Class0();
							@class.method_0(process);
							string string_ = ip;
							int int_ = port;
							if (@class.method_5(22238576) == 1712212502)
							{
								new Class4(@class).method_0(string_, int_);
							}
							goto IL_109;
						}
						Thread.Sleep(100);
					}
					process.Kill();
				}
				catch (Exception ex)
				{
					Console.WriteLine(ex.Message);
				}
				IL_109:;
			}
			finally
			{
			}
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000220B File Offset: 0x0000040B
		private void method_1(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}
	}
}
