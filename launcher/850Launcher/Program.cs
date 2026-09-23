using System;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal static class Program
    {
        [STAThread]
        private static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            try
            {
                var appDir = AppDomain.CurrentDomain.BaseDirectory;
                var config = LauncherConfig.Load(System.IO.Path.Combine(appDir, "launcher.ini"));
                var helper = HelperSettings.Load(System.IO.Path.Combine(appDir, "helper.ini"));
                Application.Run(new MainForm(appDir, config, helper));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString(), "850 Launcher startup error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
