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
        private string _lastHash = "";
        private bool? _lastHashMatch;

        public ProcessRuntimeBridge(string appDir)
        {
            _expectedClientPath = Path.GetFullPath(Path.Combine(appDir, "Lin.bin2"));
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

                        if (!_lastHashMatch.HasValue)
                        {
                            string actual;
                            _lastHashMatch = ClientVerifier.IsAuthoritativeLinBin2(path, out actual);
                            _lastHash = actual;
                        }

                        snapshot.Status = _lastHashMatch == true
                            ? "已連接 Lin.bin2，PID=" + process.Id +
                              "，基址=0x" + module.BaseAddress.ToInt64().ToString("X8") +
                              "；等待 WP3/WP4 runtime mapping"
                            : "已找到 Lin.bin2，但 SHA256 與目前 850 authority 不符：" + _lastHash;

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
