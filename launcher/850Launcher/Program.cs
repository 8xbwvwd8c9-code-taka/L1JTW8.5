using System;
using System.Drawing;
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
                var main = new MainForm(appDir, config, helper);

                if (config.DeveloperMode)
                {
                    main.Shown += delegate
                    {
                        var dynamicProbe = new Form
                        {
                            Text = "850 自動動態 HP/MP 偵測",
                            Width = 820,
                            Height = 520,
                            MinimumSize = new Size(760, 460),
                            StartPosition = FormStartPosition.CenterParent
                        };
                        dynamicProbe.Controls.Add(new RuntimeDynamicProbeControl(appDir));
                        dynamicProbe.Show(main);
                    };
                }

                Application.Run(main);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString(), "850 Launcher startup error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
