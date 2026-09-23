using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeProbeControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private TextBox _curHp;
        private TextBox _maxHp;
        private TextBox _curMp;
        private TextBox _maxMp;
        private Button _firstScan;
        private Button _refine;
        private Button _clear;
        private Label _status;
        private Label _counts;
        private ListView _results;

        private Dictionary<string, List<IntPtr>> _candidates =
            new Dictionary<string, List<IntPtr>>(StringComparer.OrdinalIgnoreCase);

        private int _scanPid;
        private DateTime? _processStartUtc;
        private string _clientSha256 = "";
        private bool _clientHashAuthoritative;
        private IntPtr _moduleBase = IntPtr.Zero;
        private int _moduleSize;

        public RuntimeProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var header = new Panel { Dock = DockStyle.Top, Height = 118, Padding = new Padding(8) };
            Controls.Add(header);

            AddValue(header, "目前 HP", 12, 14, out _curHp);
            AddValue(header, "最大 HP", 182, 14, out _maxHp);
            AddValue(header, "目前 MP", 352, 14, out _curMp);
            AddValue(header, "最大 MP", 522, 14, out _maxMp);

            _firstScan = new Button { Text = "首次掃描", Left = 12, Top = 48, Width = 100 };
            _refine = new Button { Text = "再次篩選", Left = 120, Top = 48, Width = 100, Enabled = false };
            _clear = new Button { Text = "清除結果", Left = 228, Top = 48, Width = 100 };
            header.Controls.Add(_firstScan);
            header.Controls.Add(_refine);
            header.Controls.Add(_clear);

            _status = new Label
            {
                Left = 344,
                Top = 53,
                Width = 380,
                Text = "等待已登入的 Lin.bin2。"
            };
            header.Controls.Add(_status);

            _counts = new Label
            {
                Left = 12,
                Top = 84,
                Width = 700,
                Text = "候選：HP -- / MaxHP -- / MP -- / MaxMP --"
            };
            header.Controls.Add(_counts);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _results.Columns.Add("欄位", 90);
            _results.Columns.Add("位址", 110);
            _results.Columns.Add("Lin.bin2 RVA", 120);
            _results.Columns.Add("備註", 320);
            Controls.Add(_results);
            _results.BringToFront();

            _firstScan.Click += delegate { BeginFirstScan(); };
            _refine.Click += delegate { BeginRefine(); };
            _clear.Click += delegate { ClearResults(); };
        }

        private static void AddValue(Control parent, string label, int x, int y, out TextBox box)
        {
            parent.Controls.Add(new Label { Text = label, Left = x, Top = y + 4, Width = 64 });
            box = new TextBox { Left = x + 68, Top = y, Width = 88 };
            parent.Controls.Add(box);
        }

        private Dictionary<string, int> ReadValues()
        {
            var values = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
            values["CurrentHP"] = ParsePositive(_curHp.Text, "目前 HP");
            values["MaxHP"] = ParsePositive(_maxHp.Text, "最大 HP");
            values["CurrentMP"] = ParsePositive(_curMp.Text, "目前 MP");
            values["MaxMP"] = ParsePositive(_maxMp.Text, "最大 MP");
            return values;
        }

        private static int ParsePositive(string text, string name)
        {
            int value;
            if (!int.TryParse(text.Trim(), out value) || value <= 0)
                throw new InvalidDataException(name + " 必須是大於 0 的整數。");
            return value;
        }

        private void BeginFirstScan()
        {
            Dictionary<string, int> values;
            try
            {
                values = ReadValues();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected)
            {
                MessageBox.Show(runtime.Status, "尚未連接遊戲", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            SetBusy(true, "正在唯讀掃描 Lin.bin2 程序記憶體...");
            _scanPid = runtime.ProcessId;
            _processStartUtc = runtime.ProcessStartTimeUtc;
            _clientSha256 = runtime.ClientSha256;
            _clientHashAuthoritative = runtime.ClientHashAuthoritative;
            _moduleBase = runtime.ModuleBase;
            _moduleSize = runtime.ModuleSize;

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                string error;
                if (!_probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                e.Result = _probe.FirstScan(values);
            };
            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                SetBusy(false, "");
                if (e.Error != null)
                {
                    _status.Text = "掃描失敗：" + e.Error.Message;
                    return;
                }

                var result = (ProbeResult)e.Result;
                _candidates = result.Candidates;
                _status.Text = result.Status + " 已掃描 " + FormatBytes(result.BytesScanned) + "。";
                _refine.Enabled = true;
                RefreshResults();
                SaveEvidence("FIRST", values, result);
            };
            worker.RunWorkerAsync();
        }

        private void BeginRefine()
        {
            if (_candidates.Count == 0)
            {
                MessageBox.Show("請先執行首次掃描。");
                return;
            }

            Dictionary<string, int> values;
            try
            {
                values = ReadValues();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected || runtime.ProcessId != _scanPid)
            {
                MessageBox.Show(
                    "Lin.bin2 程序已變更或重新啟動。請清除後重新做首次掃描。",
                    "程序已變更",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
                return;
            }

            SetBusy(true, "正在依照新 HP/MP 數值篩選候選位址...");

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                e.Result = _probe.Refine(values, _candidates);
            };
            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                SetBusy(false, "");
                if (e.Error != null)
                {
                    _status.Text = "篩選失敗：" + e.Error.Message;
                    return;
                }

                var result = (ProbeResult)e.Result;
                _candidates = result.Candidates;
                _status.Text = result.Status;
                RefreshResults();
                SaveEvidence("REFINE", values, result);
            };
            worker.RunWorkerAsync();
        }

        private void RefreshResults()
        {
            var currentHp = Count("CurrentHP");
            var maxHp = Count("MaxHP");
            var currentMp = Count("CurrentMP");
            var maxMp = Count("MaxMP");

            var clusters = ProbeClusterer.Find(_candidates, 0x100, 50);
            _counts.Text =
                "候選：HP " + currentHp +
                " / MaxHP " + maxHp +
                " / MP " + currentMp +
                " / MaxMP " + maxMp +
                " / 0x100 鄰近組合 " + clusters.Count;

            _results.BeginUpdate();
            _results.Items.Clear();

            AddClusters(ProbeClusterer.Find(_candidates, 0x100, 50));
            AddCandidates("CurrentHP", "目前 HP", 150);
            AddCandidates("MaxHP", "最大 HP", 150);
            AddCandidates("CurrentMP", "目前 MP", 150);
            AddCandidates("MaxMP", "最大 MP", 150);

            _results.EndUpdate();
        }

        private void AddClusters(List<ProbeCluster> clusters)
        {
            foreach (var cluster in clusters)
            {
                var note =
                    "MaxHP=0x" + cluster.MaxHp.ToString("X8") +
                    " MP=0x" + cluster.CurrentMp.ToString("X8") +
                    " MaxMP=0x" + cluster.MaxMp.ToString("X8") +
                    " span=0x" + cluster.Span.ToString("X");

                _results.Items.Add(new ListViewItem(new[]
                {
                    "鄰近組合",
                    "0x" + cluster.CurrentHp.ToString("X8"),
                    ToRva(cluster.CurrentHp),
                    note
                }));
            }
        }

        private int Count(string key)
        {
            List<IntPtr> list;
            return _candidates.TryGetValue(key, out list) ? list.Count : 0;
        }

        private void AddCandidates(string key, string display, int limit)
        {
            List<IntPtr> list;
            if (!_candidates.TryGetValue(key, out list)) return;

            var shown = Math.Min(limit, list.Count);
            for (var i = 0; i < shown; i++)
            {
                var address = list[i].ToInt64();
                var rva = ToRva(address);
                var note = i == 0 && list.Count > limit
                    ? "僅顯示前 " + limit + " 筆；完整候選已輸出到 runtime_probe_candidates.txt。"
                    : "";

                _results.Items.Add(new ListViewItem(new[]
                {
                    display,
                    "0x" + address.ToString("X8"),
                    rva,
                    note
                }));
            }
        }

        private string ToRva(long address)
        {
            if (_moduleBase == IntPtr.Zero || _moduleSize <= 0) return "";

            var start = _moduleBase.ToInt64();
            var end = start + _moduleSize;
            if (address < start || address >= end) return "非主模組";

            return "0x" + (address - start).ToString("X8");
        }

        private void ClearResults()
        {
            _probe.Detach();
            _scanPid = 0;
            _processStartUtc = null;
            _clientSha256 = "";
            _clientHashAuthoritative = false;
            _moduleBase = IntPtr.Zero;
            _moduleSize = 0;
            _candidates.Clear();
            _results.Items.Clear();
            _counts.Text = "候選：HP -- / MaxHP -- / MP -- / MaxMP --";
            _status.Text = "結果已清除。";
            _refine.Enabled = false;
        }

        private void SetBusy(bool busy, string text)
        {
            _firstScan.Enabled = !busy;
            _refine.Enabled = !busy && _candidates.Count > 0;
            _clear.Enabled = !busy;
            if (text.Length > 0) _status.Text = text;
        }

        private void SaveEvidence(string stage, IDictionary<string, int> values, ProbeResult result)
        {
            try
            {
                var path = Path.Combine(_appDir, "runtime_probe_evidence.txt");
                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("STAGE=" + stage);
                sb.AppendLine("PID=" + _scanPid);
                sb.AppendLine("PROCESS_START_UTC=" + (_processStartUtc.HasValue ? _processStartUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + (_clientSha256 ?? ""));
                sb.AppendLine("CLIENT_AUTHORITY=" + (_clientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=0x" + _moduleBase.ToInt64().ToString("X8"));
                sb.AppendLine("MODULE_SIZE=" + _moduleSize);
                sb.AppendLine("CURRENT_HP=" + values["CurrentHP"]);
                sb.AppendLine("MAX_HP=" + values["MaxHP"]);
                sb.AppendLine("CURRENT_MP=" + values["CurrentMP"]);
                sb.AppendLine("MAX_MP=" + values["MaxMP"]);
                sb.AppendLine("CURRENT_HP_CANDIDATES=" + Count("CurrentHP"));
                sb.AppendLine("MAX_HP_CANDIDATES=" + Count("MaxHP"));
                sb.AppendLine("CURRENT_MP_CANDIDATES=" + Count("CurrentMP"));
                sb.AppendLine("MAX_MP_CANDIDATES=" + Count("MaxMP"));
                sb.AppendLine("STATUS=" + result.Status);
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine();

                var clusters = ProbeClusterer.Find(_candidates, 0x100, 20);
                sb.AppendLine("[NEAR_CLUSTERS_0x100]");
                foreach (var cluster in clusters)
                {
                    sb.AppendLine(
                        "HP=0x" + cluster.CurrentHp.ToString("X8") +
                        " MaxHP=0x" + cluster.MaxHp.ToString("X8") +
                        " MP=0x" + cluster.CurrentMp.ToString("X8") +
                        " MaxMP=0x" + cluster.MaxMp.ToString("X8") +
                        " Span=0x" + cluster.Span.ToString("X") +
                        " HP_RVA=" + ToRva(cluster.CurrentHp));
                }
                sb.AppendLine();

                AppendTop(sb, "CurrentHP", 40);
                AppendTop(sb, "MaxHP", 40);
                AppendTop(sb, "CurrentMP", 40);
                AppendTop(sb, "MaxMP", 40);

                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
                SaveFullCandidateSnapshot(stage, values, result);
            }
            catch
            {
                // Evidence export is best-effort and must not break the probe UI.
            }
        }

        private void SaveFullCandidateSnapshot(string stage, IDictionary<string, int> values, ProbeResult result)
        {
            var path = Path.Combine(_appDir, "runtime_probe_candidates.txt");
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("STAGE=" + stage);
            sb.AppendLine("PID=" + _scanPid);
            sb.AppendLine("PROCESS_START_UTC=" + (_processStartUtc.HasValue ? _processStartUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + (_clientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (_clientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + _moduleBase.ToInt64().ToString("X8"));
            sb.AppendLine("MODULE_SIZE=" + _moduleSize);
            sb.AppendLine("CURRENT_HP=" + values["CurrentHP"]);
            sb.AppendLine("MAX_HP=" + values["MaxHP"]);
            sb.AppendLine("CURRENT_MP=" + values["CurrentMP"]);
            sb.AppendLine("MAX_MP=" + values["MaxMP"]);
            sb.AppendLine("CURRENT_HP_CANDIDATES=" + Count("CurrentHP"));
            sb.AppendLine("MAX_HP_CANDIDATES=" + Count("MaxHP"));
            sb.AppendLine("CURRENT_MP_CANDIDATES=" + Count("CurrentMP"));
            sb.AppendLine("MAX_MP_CANDIDATES=" + Count("MaxMP"));
            sb.AppendLine("STATUS=" + result.Status);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            var clusters = ProbeClusterer.Find(_candidates, 0x100, int.MaxValue);
            sb.AppendLine("[NEAR_CLUSTERS_0x100_ALL]");
            foreach (var cluster in clusters)
            {
                sb.AppendLine(
                    "HP=0x" + cluster.CurrentHp.ToString("X8") +
                    " MaxHP=0x" + cluster.MaxHp.ToString("X8") +
                    " MP=0x" + cluster.CurrentMp.ToString("X8") +
                    " MaxMP=0x" + cluster.MaxMp.ToString("X8") +
                    " Span=0x" + cluster.Span.ToString("X") +
                    " HP_RVA=" + ToRva(cluster.CurrentHp));
            }
            sb.AppendLine();

            AppendAll(sb, "CurrentHP");
            AppendAll(sb, "MaxHP");
            AppendAll(sb, "CurrentMP");
            AppendAll(sb, "MaxMP");

            File.WriteAllText(path, sb.ToString(), new UTF8Encoding(false));
        }

        private void AppendTop(StringBuilder sb, string key, int limit)
        {
            List<IntPtr> list;
            if (!_candidates.TryGetValue(key, out list)) return;

            sb.AppendLine("[" + key + "]");
            var shown = Math.Min(limit, list.Count);
            for (var i = 0; i < shown; i++)
            {
                var address = list[i].ToInt64();
                sb.AppendLine(
                    "ADDR=0x" + address.ToString("X8") +
                    " RVA=" + ToRva(address));
            }
            sb.AppendLine();
        }

        private void AppendAll(StringBuilder sb, string key)
        {
            List<IntPtr> list;
            if (!_candidates.TryGetValue(key, out list)) return;

            sb.AppendLine("[" + key + "_ALL]");
            for (var i = 0; i < list.Count; i++)
            {
                var address = list[i].ToInt64();
                sb.AppendLine(
                    "INDEX=" + i +
                    " ADDR=0x" + address.ToString("X8") +
                    " RVA=" + ToRva(address));
            }
            sb.AppendLine();
        }

        private static string FormatBytes(long bytes)
        {
            if (bytes >= 1024L * 1024L * 1024L)
                return (bytes / (1024.0 * 1024.0 * 1024.0)).ToString("0.00") + " GB";
            if (bytes >= 1024L * 1024L)
                return (bytes / (1024.0 * 1024.0)).ToString("0.00") + " MB";
            return bytes + " bytes";
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }
    }
}
