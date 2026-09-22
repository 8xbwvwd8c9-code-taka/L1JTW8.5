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
        public DateTime? ProcessStartTimeUtc = null;
        public IntPtr ModuleBase = IntPtr.Zero;
        public int ModuleSize;
        public uint? PlayerObjectId = null;
        public ushort? PlayerX = null;
        public ushort? PlayerY = null;
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
            _inventoryBridge = new MappedInventoryBridge(appDir);
        }

        private void ApplyPlayerMap(
            RuntimeSnapshot snapshot)
        {
            if (!File.Exists(_runtimeMapPath))
                return;

            RuntimeMap map;
            try
            {
                map = RuntimeMap.Load(
                    _runtimeMapPath);
            }
            catch
            {
                return;
            }

            if (!map.HasPlayerIdentity)
                return;

            using (var reader =
                   new RuntimeMapReader())
            {
                string error;

                if (!reader.Attach(
                    snapshot.ProcessId,
                    snapshot.ModuleBase,
                    out error))
                    return;

                uint objectId;
                ushort x;
                ushort y;

                if (!reader.TryReadUInt32(
                    map.PlayerObjectId,
                    out objectId,
                    out error))
                    return;

                if (!reader.TryReadUInt16(
                    map.PlayerX,
                    out x,
                    out error))
                    return;

                if (!reader.TryReadUInt16(
                    map.PlayerY,
                    out y,
                    out error))
                    return;

                if (objectId == 0)
                    return;

                snapshot.PlayerObjectId =
                    objectId;

                snapshot.PlayerX = x;
                snapshot.PlayerY = y;
            }
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

        public bool TryRefreshHpMp(
            RuntimeSnapshot snapshot,
            out string error)
        {
            error = "";

            if (snapshot == null ||
                !snapshot.Connected ||
                snapshot.ProcessId <= 0 ||
                snapshot.ModuleBase == IntPtr.Zero)
            {
                error = "尚未有可用的 Lin.bin2 runtime snapshot。";
                return false;
            }

            RuntimeMap map;
            try
            {
                map = RuntimeMap.Load(_runtimeMapPath);
            }
            catch (Exception ex)
            {
                error = "runtime-map.ini 格式錯誤：" + ex.Message;
                return false;
            }

            if (!map.HasHpMp)
            {
                error = "HP/MP 映射尚未設定。";
                return false;
            }

            try
            {
                var process = Process.GetProcessById(
                    snapshot.ProcessId);

                if (process.HasExited)
                {
                    error = "Lin.bin2 程序已結束。";
                    return false;
                }

                if (snapshot.ProcessStartTimeUtc.HasValue)
                {
                    DateTime actualStart;
                    try
                    {
                        actualStart =
                            process.StartTime.ToUniversalTime();
                    }
                    catch
                    {
                        actualStart = DateTime.MinValue;
                    }

                    if (actualStart != DateTime.MinValue &&
                        actualStart !=
                            snapshot.ProcessStartTimeUtc.Value)
                    {
                        error = "PID 已被新程序重用，等待完整刷新。";
                        return false;
                    }
                }
            }
            catch (Exception ex)
            {
                error = "程序驗證失敗：" + ex.Message;
                return false;
            }

            using (var reader = new RuntimeMapReader())
            {
                if (!reader.Attach(
                    snapshot.ProcessId,
                    snapshot.ModuleBase,
                    out error))
                    return false;

                int currentHp;
                int maxHp;
                int currentMp;
                int maxMp;

                if (!reader.TryReadInt32(
                    map.CurrentHp,
                    out currentHp,
                    out error))
                    return false;

                if (!reader.TryReadInt32(
                    map.MaxHp,
                    out maxHp,
                    out error))
                    return false;

                if (!reader.TryReadInt32(
                    map.CurrentMp,
                    out currentMp,
                    out error))
                    return false;

                if (!reader.TryReadInt32(
                    map.MaxMp,
                    out maxMp,
                    out error))
                    return false;

                if (currentHp < 0 ||
                    maxHp <= 0 ||
                    currentHp > maxHp ||
                    currentMp < 0 ||
                    maxMp < 0 ||
                    currentMp > maxMp)
                {
                    error = "HP/MP 快速讀取值不合理，mapping 尚未可信。";
                    return false;
                }

                snapshot.CurrentHp = currentHp;
                snapshot.MaxHp = maxHp;
                snapshot.CurrentMp = currentMp;
                snapshot.MaxMp = maxMp;

                return true;
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
                        try
                        {
                            snapshot.ProcessStartTimeUtc =
                                process.StartTime.ToUniversalTime();
                        }
                        catch
                        {
                            snapshot.ProcessStartTimeUtc = null;
                        }
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

                        ApplyPlayerMap(snapshot);
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
