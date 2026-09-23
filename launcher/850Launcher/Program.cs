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
                var main = new MainForm(appDir, config, helper);

                if (config.DeveloperMode)
                {
                    main.Shown += delegate
                    {
                        var tabs = FindTabControl(main);
                        if (tabs == null) return;

                        while (tabs.TabPages.Count > 7)
                            tabs.TabPages.RemoveAt(7);

                        var audit = new TabPage("自動稽核") { Padding = new Padding(8) };
                        audit.Controls.Add(new AutoRuntimeAuditControl(appDir));
                        tabs.TabPages.Add(audit);
                        tabs.SelectedTab = audit;
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
    }
}
