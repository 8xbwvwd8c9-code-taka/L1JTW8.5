using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSnapshot
    {
        public bool Connected;
        public string Status = "UNMAPPED";
        public int ProcessId;
        public string ProcessPath = "";
        public IntPtr ModuleBase = IntPtr.Zero;
        public int ModuleSize;
        public int? CurrentHp = null;
        public int? MaxHp = null;
        public int? CurrentMp = null;
        public int? MaxMp = null;
        public readonly List<InventoryItem> Items = new List<InventoryItem>();
    }

    internal sealed class InventoryItem
    {
        public uint ObjectId = 0;
        public int ItemId = 0;
        public long Count = 0;
        public string Name = "";
        public int? Enchant = null;
        public bool? Equipped = null;
    }

    internal interface IRuntimeBridge
    {
        RuntimeSnapshot Read();
    }

    internal sealed class ProcessRuntimeBridge : IRuntimeBridge
    {
        private readonly string _expectedClientPath;
        private readonly string _runtimeMapPath;
        private readonly IInventoryBridge _inventoryBridge;
        private string _lastHash = "";
        private bool? _lastHashMatch;

        public ProcessRuntimeBridge(string appDir)
        {
            _expectedClientPath = Path.GetFullPath(Path.Combine(appDir, "Lin.bin2"));
            _runtimeMapPath = Path.Combine(appDir, "runtime-map.ini");
            _inventoryBridge = new UnmappedInventoryBridge();
        }

        private void ApplyHpMpMap(RuntimeSnapshot snapshot)
        {
            if (!File.Exists(_runtimeMapPath))
            {
                snapshot.Status += "；等待 runtime-map.ini";
                return;
            }

            RuntimeMap map;
            try
            {
                map = RuntimeMap.Load(_runtimeMapPath);
            }
            catch (Exception ex)
            {
                snapshot.Status += "；runtime-map.ini 格式錯誤：" + ex.Message;
                return;
            }

            if (!map.HasHpMp)
            {
                snapshot.Status += "；HP/MP 映射尚未設定";
                return;
            }

            using (var reader = new RuntimeMapReader())
            {
                string error;
                if (!reader.Attach(snapshot.ProcessId, snapshot.ModuleBase, out error))
                {
                    snapshot.Status += "；映射讀取失敗：" + error;
                    return;
                }

                int currentHp;
                int maxHp;
                int currentMp;
                int maxMp;

                if (!reader.TryReadInt32(map.CurrentHp, out currentHp, out error))
                {
                    snapshot.Status += "；CurrentHP 讀取失敗：" + error;
                    return;
                }

                if (!reader.TryReadInt32(map.MaxHp, out maxHp, out error))
                {
                    snapshot.Status += "；MaxHP 讀取失敗：" + error;
                    return;
                }

                if (!reader.TryReadInt32(map.CurrentMp, out currentMp, out error))
                {
                    snapshot.Status += "；CurrentMP 讀取失敗：" + error;
                    return;
                }

                if (!reader.TryReadInt32(map.MaxMp, out maxMp, out error))
                {
                    snapshot.Status += "；MaxMP 讀取失敗：" + error;
                    return;
                }

                snapshot.CurrentHp = currentHp;
                snapshot.MaxHp = maxHp;
                snapshot.CurrentMp = currentMp;
                snapshot.MaxMp = maxMp;
                snapshot.Status += "；HP/MP 映射已載入";
            }
        }

        public RuntimeSnapshot Read()
        {
            var snapshot = new RuntimeSnapshot();

            try
            {
                foreach (var process in Process.GetProcesses())
                {
                    try
                    {
                        if (process.HasExited) continue;
                        var module = process.MainModule;
                        if (module == null) continue;

                        var path = Path.GetFullPath(module.FileName);
                        if (!string.Equals(path, _expectedClientPath, StringComparison.OrdinalIgnoreCase))
                            continue;

                        snapshot.Connected = true;
                        snapshot.ProcessId = process.Id;
                        snapshot.ProcessPath = path;
                        snapshot.ModuleBase = module.BaseAddress;
                        snapshot.ModuleSize = module.ModuleMemorySize;

                        if (!_lastHashMatch.HasValue)
                        {
                            string actual;
                            _lastHashMatch = ClientVerifier.IsAuthoritativeLinBin2(path, out actual);
                            _lastHash = actual;
                        }

                        if (_lastHashMatch != true)
                        {
                            snapshot.Status =
                                "已找到 Lin.bin2，但 SHA256 與目前 850 authority 不符：" + _lastHash;
                            return snapshot;
                        }

                        snapshot.Status =
                            "已連接 Lin.bin2，PID=" + process.Id +
                            "，基址=0x" + module.BaseAddress.ToInt64().ToString("X8");

                        ApplyHpMpMap(snapshot);

                        var inventory = _inventoryBridge.Read(snapshot);
                        foreach (var item in inventory.Items)
                            snapshot.Items.Add(item);

                        return snapshot;
                    }
                    catch
                    {
                        // Access denied / protected process / transient exit: skip.
                    }
                }

                snapshot.Status = "尚未偵測到已啟動的 850 Lin.bin2。";
                return snapshot;
            }
            catch (Exception ex)
            {
                snapshot.Status = "程序偵測失敗：" + ex.Message;
                return snapshot;
            }
        }
    }
}
