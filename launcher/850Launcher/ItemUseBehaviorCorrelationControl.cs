using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseBehaviorCorrelationControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;

        private ComboBox _items;
        private NumericUpDown _seconds;
        private Button _refresh;
        private Button _baseline;
        private Button _action;
        private Button _clear;
        private Label _status;
        private Label _summary;
        private ListView _hits;

        private HashSet<long> _baselineHits =
            new HashSet<long>();

        private int _baselinePasses;
        private long _baselineBytes;
        private int _baselinePid;
        private DateTime? _baselineProcessStartUtc;
        private string _baselineClientSha256 = "";
        private uint _baselineObjectId;
        private int _baselineItemId;
        private string _baselineItemName = "";
        private byte[] _baselinePattern;

        public ItemUseBehaviorCorrelationControl(
            string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 142,
                Padding = new Padding(8)
            };
            Controls.Add(top);

            top.Controls.Add(new Label
            {
                Text = "目前背包道具",
                Left = 12,
                Top = 16,
                Width = 88
            });

            _items = new ComboBox
            {
                Left = 104,
                Top = 12,
                Width = 390,
                DropDownStyle = ComboBoxStyle.DropDownList
            };
            top.Controls.Add(_items);

            _refresh = new Button
            {
                Text = "刷新背包",
                Left = 504,
                Top = 10,
                Width = 90
            };
            top.Controls.Add(_refresh);

            top.Controls.Add(new Label
            {
                Text = "捕捉秒數",
                Left = 606,
                Top = 16,
                Width = 60
            });

            _seconds = new NumericUpDown
            {
                Left = 670,
                Top = 12,
                Width = 54,
                Minimum = 3,
                Maximum = 15,
                Value = 6
            };
            top.Controls.Add(_seconds);

            _baseline = new Button
            {
                Text = "1. 建立無操作基線",
                Left = 12,
                Top = 48,
                Width = 150
            };
            top.Controls.Add(_baseline);

            _action = new Button
            {
                Text = "2. 捕捉手動 UseItem",
                Left = 170,
                Top = 48,
                Width = 150,
                Enabled = false
            };
            top.Controls.Add(_action);

            _clear = new Button
            {
                Text = "清除基線",
                Left = 328,
                Top = 48,
                Width = 90
            };
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 12,
                Top = 82,
                Width = 712,
                Height = 36,
                Text =
                    "先刷新背包並選道具。基線期間不要使用該道具；動作捕捉開始後切回遊戲手動使用一次。"
            };
            top.Controls.Add(_status);

            _summary = new Label
            {
                Left = 12,
                Top = 120,
                Width = 712,
                Height = 20,
                Text = "WP7 behavior evidence：尚未捕捉。"
            };
            top.Controls.Add(_summary);

            _hits = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _hits.Columns.Add("位址", 125);
            _hits.Columns.Add("基線", 70);
            _hits.Columns.Add("動作", 70);
            _hits.Columns.Add("新命中", 80);
            _hits.Columns.Add("說明", 300);
            Controls.Add(_hits);
            _hits.BringToFront();

            _refresh.Click += delegate { RefreshInventory(); };
            _baseline.Click += delegate { BeginBaseline(); };
            _action.Click += delegate { BeginAction(); };
            _clear.Click += delegate { ClearBaseline(); };
        }

        private void RefreshInventory()
        {
            var runtime = _bridge.Read();

            _items.Items.Clear();

            if (!runtime.Connected ||
                !runtime.ClientHashAuthoritative)
            {
                _status.Text =
                    "無法刷新：" + runtime.Status;
                return;
            }

            foreach (var item in runtime.Items)
            {
                if (item == null ||
                    item.ObjectId == 0 ||
                    item.ItemId <= 0 ||
                    item.Count <= 0)
                    continue;

                _items.Items.Add(
                    new InventoryChoice(item));
            }

            if (_items.Items.Count > 0)
                _items.SelectedIndex = 0;

            _status.Text =
                "背包已刷新，共 " +
                _items.Items.Count +
                " 筆。請選擇要手動使用的道具。";
        }

        private void BeginBaseline()
        {
            var choice =
                _items.SelectedItem as InventoryChoice;

            if (choice == null)
            {
                MessageBox.Show(
                    "請先按「刷新背包」並選擇道具。",
                    "尚未選擇道具",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
                return;
            }

            var runtime = _bridge.Read();

            if (!ValidateRuntimeForChoice(
                runtime,
                choice,
                out var error))
            {
                MessageBox.Show(
                    error,
                    "無法建立基線",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
                return;
            }

            var pattern =
                ItemUseProtocol.BuildNormalItemLogicalPayload(
                    choice.Item.ObjectId);

            SetBusy(
                true,
                "正在建立無操作基線；這段期間不要使用選定道具。",
                false);

            var durationMs =
                (int)_seconds.Value * 1000;

            var worker = new BackgroundWorker();

            worker.DoWork += delegate(
                object sender,
                DoWorkEventArgs e)
            {
                e.Result = Capture(
                    runtime,
                    pattern,
                    durationMs);
            };

            worker.RunWorkerCompleted += delegate(
                object sender,
                RunWorkerCompletedEventArgs e)
            {
                SetBusy(false, "", false);

                if (e.Error != null)
                {
                    _status.Text =
                        "基線捕捉失敗：" +
                        e.Error.Message;
                    return;
                }

                var capture =
                    (PatternCaptureResult)e.Result;

                if (!string.IsNullOrEmpty(
                    capture.Error))
                {
                    _status.Text =
                        "基線捕捉失敗：" +
                        capture.Error;
                    return;
                }

                _baselineHits =
                    capture.Hits;
                _baselinePasses =
                    capture.Passes;
                _baselineBytes =
                    capture.BytesScanned;
                _baselinePid =
                    runtime.ProcessId;
                _baselineProcessStartUtc =
                    runtime.ProcessStartTimeUtc;
                _baselineClientSha256 =
                    runtime.ClientSha256 ?? "";
                _baselineObjectId =
                    choice.Item.ObjectId;
                _baselineItemId =
                    choice.Item.ItemId;
                _baselineItemName =
                    choice.Item.Name ?? "";
                _baselinePattern =
                    pattern;

                _action.Enabled = true;

                _status.Text =
                    "基線完成：passes=" +
                    capture.Passes +
                    "，unique hits=" +
                    capture.Hits.Count +
                    "。現在按「捕捉手動 UseItem」，並在捕捉期間切回遊戲使用一次。";

                ShowHits(
                    _baselineHits,
                    null);
            };

            worker.RunWorkerAsync();
        }

        private void BeginAction()
        {
            if (_baselinePattern == null ||
                _baselineObjectId == 0)
            {
                _status.Text =
                    "請先建立無操作基線。";
                return;
            }

            var runtime = _bridge.Read();

            if (!ValidateSameBaselineRuntime(
                runtime,
                out var error))
            {
                _status.Text = error;
                return;
            }

            SetBusy(
                true,
                "動作捕捉中：現在切回遊戲，手動使用選定道具一次。",
                true);

            var durationMs =
                (int)_seconds.Value * 1000;

            var worker = new BackgroundWorker();

            worker.DoWork += delegate(
                object sender,
                DoWorkEventArgs e)
            {
                e.Result = Capture(
                    runtime,
                    _baselinePattern,
                    durationMs);
            };

            worker.RunWorkerCompleted += delegate(
                object sender,
                RunWorkerCompletedEventArgs e)
            {
                SetBusy(false, "", true);

                if (e.Error != null)
                {
                    _status.Text =
                        "動作捕捉失敗：" +
                        e.Error.Message;
                    return;
                }

                var action =
                    (PatternCaptureResult)e.Result;

                if (!string.IsNullOrEmpty(
                    action.Error))
                {
                    _status.Text =
                        "動作捕捉失敗：" +
                        action.Error;
                    return;
                }

                var newHits =
                    new HashSet<long>(
                        action.Hits);

                newHits.ExceptWith(
                    _baselineHits);

                var correlated =
                    _baselinePasses > 0 &&
                    action.Passes > 0 &&
                    newHits.Count > 0;

                ShowHits(
                    _baselineHits,
                    action.Hits);

                SaveEvidence(
                    runtime,
                    action,
                    newHits,
                    correlated);

                var summary =
                    ItemUseBehaviorEvidenceComparer
                    .Compare(
                        Path.Combine(
                            _appDir,
                            "itemuse_behavior_evidence.txt"));

                _summary.Text =
                    "WP7 behavior evidence：" +
                    summary.Status;

                _status.Text =
                    correlated
                        ? "行為關聯命中：動作期間出現 " +
                          newHits.Count +
                          " 個基線沒有的 5-byte UseItem pattern。"
                        : "本次未抓到新的 UseItem pattern；不判 PASS，可重做基線後再測。";
            };

            worker.RunWorkerAsync();
        }

        private PatternCaptureResult Capture(
            RuntimeSnapshot runtime,
            byte[] pattern,
            int durationMs)
        {
            var output =
                new PatternCaptureResult();

            using (var scanner =
                   new RuntimeBytePatternScanner())
            {
                string error;

                if (!scanner.Attach(
                    runtime.ProcessId,
                    out error))
                {
                    output.Error = error;
                    return output;
                }

                var watch =
                    Stopwatch.StartNew();

                while (watch.ElapsedMilliseconds <
                       durationMs)
                {
                    var pass =
                        scanner.ScanWritable(
                            pattern);

                    output.Passes++;
                    output.BytesScanned +=
                        pass.BytesScanned;

                    foreach (var address in
                             pass.Addresses)
                    {
                        output.Hits.Add(
                            address.ToInt64());
                    }

                    if (pass.CandidateLimitReached)
                    {
                        output.CandidateLimitReached = true;
                        break;
                    }

                    if (watch.ElapsedMilliseconds <
                        durationMs)
                    {
                        Thread.Sleep(25);
                    }
                }
            }

            return output;
        }

        private bool ValidateRuntimeForChoice(
            RuntimeSnapshot runtime,
            InventoryChoice choice,
            out string error)
        {
            error = "";

            if (runtime == null ||
                !runtime.Connected ||
                !runtime.ClientHashAuthoritative)
            {
                error =
                    runtime == null
                        ? "Runtime snapshot 為空。"
                        : runtime.Status;
                return false;
            }

            var found = false;

            foreach (var item in runtime.Items)
            {
                if (item != null &&
                    item.ObjectId ==
                        choice.Item.ObjectId &&
                    item.ItemId ==
                        choice.Item.ItemId)
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                error =
                    "選定 ObjectId 已不在目前背包，請重新刷新。";
                return false;
            }

            return true;
        }

        private bool ValidateSameBaselineRuntime(
            RuntimeSnapshot runtime,
            out string error)
        {
            error = "";

            if (runtime == null ||
                !runtime.Connected ||
                !runtime.ClientHashAuthoritative)
            {
                error =
                    "850 runtime 已失效，請重新建立基線。";
                return false;
            }

            if (runtime.ProcessId !=
                _baselinePid ||
                runtime.ProcessStartTimeUtc !=
                    _baselineProcessStartUtc ||
                !string.Equals(
                    runtime.ClientSha256,
                    _baselineClientSha256,
                    StringComparison.OrdinalIgnoreCase))
            {
                error =
                    "Client process instance 已變更，請重新建立基線。";
                return false;
            }

            return true;
        }

        private void ShowHits(
            HashSet<long> baseline,
            HashSet<long> action)
        {
            var all =
                new SortedSet<long>();

            if (baseline != null)
                all.UnionWith(baseline);

            if (action != null)
                all.UnionWith(action);

            _hits.BeginUpdate();
            _hits.Items.Clear();

            foreach (var address in all)
            {
                var inBaseline =
                    baseline != null &&
                    baseline.Contains(address);

                var inAction =
                    action != null &&
                    action.Contains(address);

                var isNew =
                    inAction &&
                    !inBaseline;

                _hits.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            "0x" +
                            address.ToString("X8"),
                            inBaseline ? "YES" : "",
                            inAction ? "YES" : "",
                            isNew ? "YES" : "",
                            isNew
                                ? "動作期間新出現的 logical UseItem pattern"
                                : "基線已存在或僅基線命中"
                        }));
            }

            _hits.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            PatternCaptureResult action,
            HashSet<long> newHits,
            bool correlated)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "itemuse_behavior_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine(
                    "TIME=" +
                    DateTime.Now.ToString(
                        "yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine(
                    "PID=" +
                    runtime.ProcessId);
                sb.AppendLine(
                    "PROCESS_START_UTC=" +
                    (runtime.ProcessStartTimeUtc.HasValue
                        ? runtime.ProcessStartTimeUtc.Value.ToString("o")
                        : ""));
                sb.AppendLine(
                    "CLIENT_SHA256=" +
                    (runtime.ClientSha256 ?? ""));
                sb.AppendLine(
                    "CLIENT_AUTHORITY=" +
                    (runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine(
                    "OBJECT_ID=" +
                    _baselineObjectId);
                sb.AppendLine(
                    "ITEM_ID=" +
                    _baselineItemId);
                sb.AppendLine(
                    "ITEM_NAME=" +
                    _baselineItemName);
                sb.AppendLine(
                    "PATTERN=" +
                    ItemUseProtocol.ToHex(
                        _baselinePattern));
                sb.AppendLine(
                    "BASELINE_PASSES=" +
                    _baselinePasses);
                sb.AppendLine(
                    "BASELINE_BYTES=" +
                    _baselineBytes);
                sb.AppendLine(
                    "BASELINE_UNIQUE_HITS=" +
                    _baselineHits.Count);
                sb.AppendLine(
                    "ACTION_PASSES=" +
                    action.Passes);
                sb.AppendLine(
                    "ACTION_BYTES=" +
                    action.BytesScanned);
                sb.AppendLine(
                    "ACTION_UNIQUE_HITS=" +
                    action.Hits.Count);
                sb.AppendLine(
                    "NEW_HITS=" +
                    newHits.Count);
                sb.AppendLine(
                    "CANDIDATE_LIMIT=" +
                    (action.CandidateLimitReached ? 1 : 0));
                sb.AppendLine(
                    "CORRELATED=" +
                    (correlated ? 1 : 0));
                sb.AppendLine(
                    "MEMORY_WRITE=NO");

                foreach (var address in newHits)
                {
                    sb.AppendLine(
                        "NEW_HIT=0x" +
                        address.ToString("X8"));
                }

                sb.AppendLine();

                File.AppendAllText(
                    path,
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void ClearBaseline()
        {
            _baselineHits.Clear();
            _baselinePasses = 0;
            _baselineBytes = 0;
            _baselinePid = 0;
            _baselineProcessStartUtc = null;
            _baselineClientSha256 = "";
            _baselineObjectId = 0;
            _baselineItemId = 0;
            _baselineItemName = "";
            _baselinePattern = null;
            _action.Enabled = false;
            _hits.Items.Clear();
            _status.Text =
                "基線已清除。請重新刷新背包並建立基線。";
        }

        private void SetBusy(
            bool busy,
            string status,
            bool actionPhase)
        {
            _refresh.Enabled = !busy;
            _baseline.Enabled = !busy;
            _clear.Enabled = !busy;
            _items.Enabled = !busy;
            _seconds.Enabled = !busy;

            _action.Enabled =
                !busy &&
                _baselinePattern != null;

            if (!string.IsNullOrEmpty(status))
                _status.Text = status;
        }

        private sealed class PatternCaptureResult
        {
            public readonly HashSet<long> Hits =
                new HashSet<long>();

            public int Passes;
            public long BytesScanned;
            public bool CandidateLimitReached;
            public string Error = "";
        }

        private sealed class InventoryChoice
        {
            public readonly InventoryItem Item;

            public InventoryChoice(
                InventoryItem item)
            {
                Item = item;
            }

            public override string ToString()
            {
                return (Item.Name ?? "") +
                       " [ItemId=" +
                       Item.ItemId +
                       "] ObjectId=" +
                       Item.ObjectId +
                       " Count=" +
                       Item.Count;
            }
        }
    }
}
