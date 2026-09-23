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

                // Engineering-only runtime discovery must not depend on a visible developer UI.
                // Hide all developer tabs and keep discovery/report generation headless.
                config.DeveloperMode = false;
                var main = new MainForm(appDir, config, helper);

                // Invisible host keeps AutoRuntimeAuditControl loaded on the WinForms message loop.
                // The control performs read-only collection and writes auto_runtime_audit_report.txt.
                var auditHost = new Form
                {
                    Text = "850 Runtime Audit Host",
                    ShowInTaskbar = false,
                    FormBorderStyle = FormBorderStyle.None,
                    StartPosition = FormStartPosition.Manual,
                    Left = -32000,
                    Top = -32000,
                    Width = 1,
                    Height = 1,
                    Opacity = 0
                };
                auditHost.Controls.Add(new AutoRuntimeAuditControl(appDir)
                {
                    Dock = DockStyle.Fill
                });

                main.Shown += delegate
                {
                    auditHost.Show(main);
                };
                main.FormClosed += delegate
                {
                    auditHost.Close();
                };

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
