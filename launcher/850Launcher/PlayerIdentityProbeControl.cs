using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class PlayerIdentityProbeControl :
        UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe =
            new RuntimeMemoryProbe();

        private TextBox _objectId;
        private TextBox _x;
        private TextBox _y;
        private ComboBox _xWidth;
        private ComboBox _yWidth;
        private Button _first;
        private Button _refine;
        private Button _clear;
        private Label _status;
        private Label _counts;
        private ListView _results;

        private List<IntPtr> _objectCandidates =
            new List<IntPtr>();

        private List<IntPtr> _xCandidates =
            new List<IntPtr>();

        private List<IntPtr> _yCandidates =
            new List<IntPtr>();

        private int _pid;
        private IntPtr _moduleBase =
            IntPtr.Zero;

        private int _moduleSize;

        public PlayerIdentityProbeControl(
            string appDir)
        {
            _appDir = appDir;
            _bridge =
                new ProcessRuntimeBridge(
                    appDir);

            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 118,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            top.Controls.Add(
                new Label
                {
                    Text = "ObjectId",
                    Left = 12,
                    Top = 15,
                    Width = 58
                });

            _objectId = new TextBox
            {
                Left = 74,
                Top = 11,
                Width = 110
            };

            top.Controls.Add(_objectId);

            top.Controls.Add(
                new Label
                {
                    Text = "X",
                    Left = 202,
                    Top = 15,
                    Width = 18
                });

            _x = new TextBox
            {
                Left = 224,
                Top = 11,
                Width = 72
            };

            top.Controls.Add(_x);

            _xWidth =
                NewWidthCombo(
                    304,
                    11);

            top.Controls.Add(_xWidth);

            top.Controls.Add(
                new Label
                {
                    Text = "Y",
                    Left = 374,
                    Top = 15,
                    Width = 18
                });

            _y = new TextBox
            {
                Left = 396,
                Top = 11,
                Width = 72
            };

            top.Controls.Add(_y);

            _yWidth =
                NewWidthCombo(
                    476,
                    11);

            top.Controls.Add(_yWidth);

            _first = new Button
            {
                Text = "首次掃描",
                Left = 12,
                Top = 48,
                Width = 100
            };

            _refine = new Button
            {
                Text = "再次篩選",
                Left = 120,
                Top = 48,
                Width = 100,
                Enabled = false
            };

            _clear = new Button
            {
                Text = "清除結果",
                Left = 228,
                Top = 48,
                Width = 100
            };

            top.Controls.Add(_first);
            top.Controls.Add(_refine);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 344,
                Top = 52,
                Width = 380,
                Height = 40,
                Text =
                    "ObjectId 可由角色 DB 確認；X/Y 以目前角色座標為準。"
            };

            top.Controls.Add(_status);

            _counts = new Label
            {
                Left = 12,
                Top = 88,
                Width = 700,
                Text =
                    "候選：ObjectId -- / X -- / Y -- / 鄰近組合 --"
            };

            top.Controls.Add(_counts);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _results.Columns.Add(
                "ObjectId 位址",
                120);

            _results.Columns.Add(
                "X 位址",
                120);

            _results.Columns.Add(
                "Y 位址",
                120);

            _results.Columns.Add(
                "Span",
                80);

            _results.Columns.Add(
                "ObjectId RVA",
                120);

            Controls.Add(_results);
            _results.BringToFront();

            _first.Click +=
                delegate
                {
                    BeginFirstScan();
                };

            _refine.Click +=
                delegate
                {
                    BeginRefine();
                };

            _clear.Click +=
                delegate
                {
                    ClearResults();
                };
        }

        private static ComboBox NewWidthCombo(
            int left,
            int top)
        {
            var box = new ComboBox
            {
                Left = left,
                Top = top,
                Width = 54,
                DropDownStyle =
                    ComboBoxStyle.DropDownList
            };

            box.Items.Add("2");
            box.Items.Add("4");
            box.SelectedItem = "4";

            return box;
        }

        private void BeginFirstScan()
        {
            uint objectId;
            ushort x;
            ushort y;

            try
            {
                objectId =
                    ParseUInt32(
                        _objectId.Text,
                        "ObjectId");

                x =
                    ParseUInt16(
                        _x.Text,
                        "X");

                y =
                    ParseUInt16(
                        _y.Text,
                        "Y");
            }
            catch (Exception ex)
            {
                MessageBox.Show(
                    ex.Message,
                    "輸入錯誤",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Warning);

                return;
            }

            var runtime =
                _bridge.Read();

            if (!runtime.Connected)
            {
                MessageBox.Show(
                    runtime.Status,
                    "尚未連接遊戲",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);

                return;
            }

            var xWidth =
                SelectedWidth(_xWidth);

            var yWidth =
                SelectedWidth(_yWidth);

            SetBusy(
                true,
                "正在唯讀掃描 ObjectId / X / Y...");

            _pid =
                runtime.ProcessId;

            _moduleBase =
                runtime.ModuleBase;

            _moduleSize =
                runtime.ModuleSize;

            var worker =
                new BackgroundWorker();

            worker.DoWork +=
                delegate(
                    object sender,
                    DoWorkEventArgs e)
                {
                    string error;

                    if (!_probe.Attach(
                        runtime.ProcessId,
                        out error))
                    {
                        throw new InvalidOperationException(
                            error);
                    }

                    var data =
                        new PlayerProbeData();

                    var objectResult =
                        _probe.FirstScan(
                            new Dictionary<string, int>
                            {
                                {
                                    "ObjectId",
                                    unchecked(
                                        (int)objectId)
                                }
                            });

                    data.ObjectCandidates =
                        GetCandidates(
                            objectResult,
                            "ObjectId");

                    if (xWidth == 4 &&
                        yWidth == 4)
                    {
                        var xy =
                            _probe.FirstScan(
                                new Dictionary<string, int>
                                {
                                    { "X", x },
                                    { "Y", y }
                                });

                        data.XCandidates =
                            GetCandidates(
                                xy,
                                "X");

                        data.YCandidates =
                            GetCandidates(
                                xy,
                                "Y");
                    }
                    else if (xWidth == 2 &&
                             yWidth == 2)
                    {
                        var xy =
                            _probe.FirstScanUInt16(
                                new Dictionary<string, ushort>
                                {
                                    { "X", x },
                                    { "Y", y }
                                });

                        data.XCandidates =
                            GetCandidates(
                                xy,
                                "X");

                        data.YCandidates =
                            GetCandidates(
                                xy,
                                "Y");
                    }
                    else
                    {
                        data.XCandidates =
                            FirstScanCoordinate(
                                "X",
                                x,
                                xWidth);

                        data.YCandidates =
                            FirstScanCoordinate(
                                "Y",
                                y,
                                yWidth);
                    }

                    e.Result = data;
                };

            worker.RunWorkerCompleted +=
                delegate(
                    object sender,
                    RunWorkerCompletedEventArgs e)
                {
                    SetBusy(
                        false,
                        "");

                    if (e.Error != null)
                    {
                        _status.Text =
                            "掃描失敗：" +
                            e.Error.Message;

                        return;
                    }

                    var data =
                        (PlayerProbeData)e.Result;

                    _objectCandidates =
                        data.ObjectCandidates;

                    _xCandidates =
                        data.XCandidates;

                    _yCandidates =
                        data.YCandidates;

                    RefreshResults();

                    _refine.Enabled =
                        true;

                    _status.Text =
                        "首次掃描完成。移動角色後更新 X/Y，再按「再次篩選」。";

                    SaveEvidence(
                        "FIRST",
                        objectId,
                        x,
                        y,
                        xWidth,
                        yWidth);
                };

            worker.RunWorkerAsync();
        }

        private void BeginRefine()
        {
            if (_objectCandidates.Count == 0 ||
                _xCandidates.Count == 0 ||
                _yCandidates.Count == 0)
            {
                MessageBox.Show(
                    "請先完成首次掃描。");

                return;
            }

            uint objectId;
            ushort x;
            ushort y;

            try
            {
                objectId =
                    ParseUInt32(
                        _objectId.Text,
                        "ObjectId");

                x =
                    ParseUInt16(
                        _x.Text,
                        "X");

                y =
                    ParseUInt16(
                        _y.Text,
                        "Y");
            }
            catch (Exception ex)
            {
                MessageBox.Show(
                    ex.Message,
                    "輸入錯誤",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Warning);

                return;
            }

            var runtime =
                _bridge.Read();

            if (!runtime.Connected ||
                runtime.ProcessId != _pid)
            {
                MessageBox.Show(
                    "Lin.bin2 程序已變更。請重新首次掃描。",
                    "程序已變更");

                return;
            }

            var xWidth =
                SelectedWidth(_xWidth);

            var yWidth =
                SelectedWidth(_yWidth);

            SetBusy(
                true,
                "正在依照新的 ObjectId / X / Y 篩選...");

            var worker =
                new BackgroundWorker();

            worker.DoWork +=
                delegate(
                    object sender,
                    DoWorkEventArgs e)
                {
                    string error;

                    if (!_probe.Attach(
                        runtime.ProcessId,
                        out error))
                    {
                        throw new InvalidOperationException(
                            error);
                    }

                    var data =
                        new PlayerProbeData();

                    var objectResult =
                        _probe.Refine(
                            new Dictionary<string, int>
                            {
                                {
                                    "ObjectId",
                                    unchecked(
                                        (int)objectId)
                                }
                            },
                            new Dictionary<string, List<IntPtr>>
                            {
                                {
                                    "ObjectId",
                                    _objectCandidates
                                }
                            });

                    data.ObjectCandidates =
                        GetCandidates(
                            objectResult,
                            "ObjectId");

                    data.XCandidates =
                        RefineCoordinate(
                            "X",
                            x,
                            xWidth,
                            _xCandidates);

                    data.YCandidates =
                        RefineCoordinate(
                            "Y",
                            y,
                            yWidth,
                            _yCandidates);

                    e.Result = data;
                };

            worker.RunWorkerCompleted +=
                delegate(
                    object sender,
                    RunWorkerCompletedEventArgs e)
                {
                    SetBusy(
                        false,
                        "");

                    if (e.Error != null)
                    {
                        _status.Text =
                            "篩選失敗：" +
                            e.Error.Message;

                        return;
                    }

                    var data =
                        (PlayerProbeData)e.Result;

                    _objectCandidates =
                        data.ObjectCandidates;

                    _xCandidates =
                        data.XCandidates;

                    _yCandidates =
                        data.YCandidates;

                    RefreshResults();

                    _status.Text =
                        "再次篩選完成。可重複移動角色繼續縮小候選。";

                    SaveEvidence(
                        "REFINE",
                        objectId,
                        x,
                        y,
                        xWidth,
                        yWidth);
                };

            worker.RunWorkerAsync();
        }

        private List<IntPtr> FirstScanCoordinate(
            string name,
            ushort value,
            int width)
        {
            if (width == 2)
            {
                return GetCandidates(
                    _probe.FirstScanUInt16(
                        new Dictionary<string, ushort>
                        {
                            { name, value }
                        }),
                    name);
            }

            return GetCandidates(
                _probe.FirstScan(
                    new Dictionary<string, int>
                    {
                        { name, value }
                    }),
                name);
        }

        private List<IntPtr> RefineCoordinate(
            string name,
            ushort value,
            int width,
            List<IntPtr> previous)
        {
            if (width == 2)
            {
                return GetCandidates(
                    _probe.RefineUInt16(
                        new Dictionary<string, ushort>
                        {
                            { name, value }
                        },
                        new Dictionary<string, List<IntPtr>>
                        {
                            { name, previous }
                        }),
                    name);
            }

            return GetCandidates(
                _probe.Refine(
                    new Dictionary<string, int>
                    {
                        { name, value }
                    },
                    new Dictionary<string, List<IntPtr>>
                    {
                        { name, previous }
                    }),
                name);
        }

        private static List<IntPtr> GetCandidates(
            ProbeResult result,
            string name)
        {
            List<IntPtr> values;

            return result.Candidates.TryGetValue(
                name,
                out values)
                ? values
                : new List<IntPtr>();
        }

        private void RefreshResults()
        {
            var clusters =
                PlayerIdentityClusterer.Find(
                    _objectCandidates,
                    _xCandidates,
                    _yCandidates,
                    0x200,
                    100);

            _counts.Text =
                "候選：ObjectId " +
                _objectCandidates.Count +
                " / X " +
                _xCandidates.Count +
                " / Y " +
                _yCandidates.Count +
                " / 鄰近組合 " +
                clusters.Count;

            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var cluster in clusters)
            {
                _results.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            "0x" +
                            cluster.ObjectIdAddress
                                .ToString("X8"),

                            "0x" +
                            cluster.XAddress
                                .ToString("X8"),

                            "0x" +
                            cluster.YAddress
                                .ToString("X8"),

                            "0x" +
                            cluster.Span
                                .ToString("X"),

                            ToRva(
                                cluster.ObjectIdAddress)
                        }));
            }

            _results.EndUpdate();
        }

        private string ToRva(
            long address)
        {
            if (_moduleBase == IntPtr.Zero ||
                _moduleSize <= 0)
                return "";

            var start =
                _moduleBase.ToInt64();

            var end =
                start +
                _moduleSize;

            if (address < start ||
                address >= end)
                return "非主模組";

            return "0x" +
                   (address - start)
                   .ToString("X8");
        }

        private void SaveEvidence(
            string stage,
            uint objectId,
            ushort x,
            ushort y,
            int xWidth,
            int yWidth)
        {
            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "player_identity_probe_evidence.txt");

                var clusters =
                    PlayerIdentityClusterer.Find(
                        _objectCandidates,
                        _xCandidates,
                        _yCandidates,
                        0x200,
                        30);

                var sb =
                    new StringBuilder();

                sb.AppendLine(
                    "TIME=" +
                    DateTime.Now.ToString(
                        "yyyy-MM-dd HH:mm:ss"));

                sb.AppendLine(
                    "STAGE=" +
                    stage);

                sb.AppendLine(
                    "PID=" +
                    _pid);

                sb.AppendLine(
                    "MODULE_BASE=0x" +
                    _moduleBase.ToInt64()
                    .ToString("X8"));

                sb.AppendLine(
                    "OBJECT_ID=" +
                    objectId);

                sb.AppendLine(
                    "X=" +
                    x);

                sb.AppendLine(
                    "Y=" +
                    y);

                sb.AppendLine(
                    "X_WIDTH=" +
                    xWidth);

                sb.AppendLine(
                    "Y_WIDTH=" +
                    yWidth);

                sb.AppendLine(
                    "OBJECT_CANDIDATES=" +
                    _objectCandidates.Count);

                sb.AppendLine(
                    "X_CANDIDATES=" +
                    _xCandidates.Count);

                sb.AppendLine(
                    "Y_CANDIDATES=" +
                    _yCandidates.Count);

                sb.AppendLine(
                    "CLUSTERS=" +
                    clusters.Count);

                sb.AppendLine(
                    "MEMORY_WRITE=NO");

                sb.AppendLine();

                foreach (var cluster in clusters)
                {
                    sb.AppendLine(
                        "OBJECT=0x" +
                        cluster.ObjectIdAddress
                            .ToString("X8") +
                        " X=0x" +
                        cluster.XAddress
                            .ToString("X8") +
                        " Y=0x" +
                        cluster.YAddress
                            .ToString("X8") +
                        " SPAN=0x" +
                        cluster.Span
                            .ToString("X") +
                        " OBJECT_RVA=" +
                        ToRva(
                            cluster.ObjectIdAddress));
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

        private void ClearResults()
        {
            _probe.Detach();

            _objectCandidates.Clear();
            _xCandidates.Clear();
            _yCandidates.Clear();

            _pid = 0;
            _moduleBase =
                IntPtr.Zero;

            _moduleSize = 0;

            _results.Items.Clear();

            _counts.Text =
                "候選：ObjectId -- / X -- / Y -- / 鄰近組合 --";

            _status.Text =
                "結果已清除。";

            _refine.Enabled =
                false;
        }

        private void SetBusy(
            bool busy,
            string status)
        {
            _first.Enabled =
                !busy;

            _refine.Enabled =
                !busy &&
                _objectCandidates.Count > 0 &&
                _xCandidates.Count > 0 &&
                _yCandidates.Count > 0;

            _clear.Enabled =
                !busy;

            if (!string.IsNullOrEmpty(
                status))
            {
                _status.Text =
                    status;
            }
        }

        private static int SelectedWidth(
            ComboBox box)
        {
            return Convert.ToString(
                box.SelectedItem) == "2"
                ? 2
                : 4;
        }

        private static uint ParseUInt32(
            string text,
            string name)
        {
            text =
                (text ?? "").Trim();

            uint value;

            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
            {
                if (!uint.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                {
                    throw new InvalidDataException(
                        name +
                        " 格式錯誤。");
                }
            }
            else if (!uint.TryParse(
                text,
                out value))
            {
                throw new InvalidDataException(
                    name +
                    " 格式錯誤。");
            }

            if (value == 0)
            {
                throw new InvalidDataException(
                    name +
                    " 必須大於 0。");
            }

            return value;
        }

        private static ushort ParseUInt16(
            string text,
            string name)
        {
            uint value =
                ParseUInt32(
                    text,
                    name);

            if (value >
                ushort.MaxValue)
            {
                throw new InvalidDataException(
                    name +
                    " 超出 0..65535。");
            }

            return (ushort)value;
        }

        protected override void Dispose(
            bool disposing)
        {
            if (disposing)
                _probe.Dispose();

            base.Dispose(disposing);
        }

        private sealed class PlayerProbeData
        {
            public List<IntPtr> ObjectCandidates =
                new List<IntPtr>();

            public List<IntPtr> XCandidates =
                new List<IntPtr>();

            public List<IntPtr> YCandidates =
                new List<IntPtr>();
        }
    }
}
