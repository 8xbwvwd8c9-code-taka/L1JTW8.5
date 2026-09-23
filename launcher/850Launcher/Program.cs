using System;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal static class Program
    {
        private const string BuildMarker = "AUTO-AUDIT-20260923-MULTI3";

        [STAThread]
        private static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            try
            {
                var appDir = AppDomain.CurrentDomain.BaseDirectory;
                WriteStartupMarker(appDir, "START");
                ItemCatalogLoader.EnsureExternalCatalog(appDir);
                DamageAccountingPolicyValidation.Run(appDir);

                var config = LauncherConfig.Load(Path.Combine(appDir, "launcher.ini"));
                var helper = HelperSettings.Load(Path.Combine(appDir, "helper.ini"));

                config.DeveloperMode = false;
                var main = new MainForm(appDir, config, helper);
                main.Text = "L1JTW 8.50 登入器 + 輔助 [" + BuildMarker + "]";

                main.Shown += delegate
                {
                    try
                    {
                        var tabs = FindTabControl(main);
                        if (tabs == null)
                        {
                            WriteStartupMarker(appDir, "TABCONTROL_NOT_FOUND");
                            return;
                        }

                        var audit = new TabPage("自動稽核") { Padding = new Padding(8) };
                        audit.Controls.Add(new AutoRuntimeAuditControl(appDir) { Dock = DockStyle.Fill });
                        tabs.TabPages.Add(audit);
                        tabs.SelectedTab = audit;
                        WriteStartupMarker(appDir, "AUTO_AUDIT_UI_READY");
                    }
                    catch (Exception ex)
                    {
                        WriteStartupMarker(appDir, "AUTO_AUDIT_UI_ERROR=" + ex.GetType().Name + ":" + ex.Message);
                    }
                };

                Application.Run(main);
            }
            catch (Exception ex)
            {
                try
                {
                    WriteStartupMarker(AppDomain.CurrentDomain.BaseDirectory,
                        "FATAL=" + ex.GetType().Name + ":" + ex.Message);
                }
                catch { }

                MessageBox.Show(ex.ToString(), "850 Launcher startup error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private static TabControl FindTabControl(Control root)
        {
            foreach (Control child in root.Controls)
            {
                var tabs = child as TabControl;
                if (tabs != null) return tabs;

                var nested = FindTabControl(child);
                if (nested != null) return nested;
            }
            return null;
        }

        private static void WriteStartupMarker(string appDir, string state)
        {
            try
            {
                File.WriteAllText(
                    Path.Combine(appDir, "launcher_startup_marker.txt"),
                    "TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine +
                    "BUILD=" + BuildMarker + Environment.NewLine +
                    "STATE=" + state + Environment.NewLine,
                    new UTF8Encoding(false));
            }
            catch { }
        }
    }
}
