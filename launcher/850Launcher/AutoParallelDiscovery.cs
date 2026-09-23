using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoParallelDiscovery
    {
        private sealed class Expectation
        {
            public int ItemId;
            public int Count;
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _inventoryRunning;
        private bool _buffRunning;
        private bool _sendRunning;
        private bool _buffDone;
        private bool _sendDone;
        private DateTime _inventoryRetryAfterUtc = DateTime.MinValue;
        private string _inventoryStatus = "WAITING";
        private string _buffStatus = "WAITING";
        private string _sendStatus = "WAITING";

        public AutoParallelDiscovery(string appDir)
        {
            _appDir = appDir;
        }

        public string InventoryStatus { get { lock (_sync) return _inventoryRunning ? "RUNNING" : _inventoryStatus; } }
        public string BuffStatus { get { lock (_sync) return _buffRunning ? "RUNNING" : _buffStatus; } }
        public string SendStatus { get { lock (_sync) return _sendRunning ? "RUNNING" : _sendStatus; } }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null || !runtime.Connected || !runtime.ClientHashAuthoritative || runtime.ProcessId <= 0)
                return;

            lock (_sync)
            {
                if (_pid != runtime.ProcessId)
                {
                    _pid = runtime.ProcessId;
                    _inventoryRunning = false;
                    _buffRunning = false;
                    _sendRunning = false;
                    _buffDone = false;
                    _sendDone = false;
                    _inventoryRetryAfterUtc = DateTime.MinValue;
                    _inventoryStatus = "READY";
                    _buffStatus = "READY";
                    _sendStatus = "READY";
                }

                if (!_inventoryRunning && DateTime.UtcNow >= _inventoryRetryAfterUtc)
                {
                    _inventoryRunning = true;
                    ThreadPool.QueueUserWorkItem(delegate { RunInventoryLane(runtime); });
                }

                if (!_buffRunning && !_buffDone)
                {
                    _buffRunning = true;
                    ThreadPool.QueueUserWorkItem(delegate { RunBuffLane(runtime); });
                }

                if (!_sendRunning && !_sendDone)
                {
                    _sendRunning = true;
                    ThreadPool.QueueUserWorkItem(delegate { RunSendLane(runtime); });
                }
            }
        }

        private void RunInventoryLane(RuntimeSnapshot runtime)
        {
            try
            {
                var expectations = LoadLatestExpectations();
                if (expectations.Count == 0)
                {
                    SaveInventoryWaiting(runtime, "inventory_validation_evidence.txt 沒有可重用的 [EXPECTED] itemId=count。");
                    lock (_sync) _inventoryStatus = "WAITING_EXPECTED";
                    return;
                }

                if (expectations.Count > 12)
                    expectations.RemoveRange(12, expectations.Count - 12);

                using (var probe = new RuntimeMemoryProbe())
                {
                    string error;
                    if (!probe.Attach(runtime.ProcessId, out error))
                        throw new InvalidOperationException(error);

                    var targets = new Dictionary<string, int>();
                    foreach (var item in expectations)
                        targets["ITEM_" + item.ItemId] = item.ItemId;

                    var scan = probe.FirstScan(targets);
                    var sb = NewHeader(runtime, "AUTO_INVENTORY_EXPECTATION_DISCOVERY");
                    sb.AppendLine("EXPECTATIONS=" + expectations.Count);
                    sb.AppendLine("SCAN_STATUS=" + scan.Status);
                    sb.AppendLine("MEMORY_WRITE=NO");
                    sb.AppendLine();
                    sb.AppendLine("[EXPECTED]");
                    foreach (var item in expectations)
                        sb.AppendLine(item.ItemId + "=" + item.Count);
                    sb.AppendLine();

                    var offsetHits = new Dictionary<int, int>();
                    var totalAnchors = 0;
                    var exactPairs = 0;

                    foreach (var item in expectations)
                    {
                        List<IntPtr> anchors;
                        if (!scan.Candidates.TryGetValue("ITEM_" + item.ItemId, out anchors))
                            anchors = new List<IntPtr>();

                        totalAnchors += anchors.Count;
                        sb.AppendLine("[ITEM " + item.ItemId + "]");
                        sb.AppendLine("EXPECTED_COUNT=" + item.Count);
                        sb.AppendLine("ITEM_ID_ANCHORS=" + anchors.Count);

                        var shown = Math.Min(80, anchors.Count);
                        for (var i = 0; i < shown; i++)
                        {
                            var center = anchors[i];
                            var rows = probe.ReadDwords(center, 0x80, 0x84);
                            var pairFound = false;
                            foreach (var row in rows)
                            {
                                if (row.Address == center || row.Value != item.Count)
                                    continue;

                                var offsetLong = row.Address.ToInt64() - center.ToInt64();
                                if (offsetLong < int.MinValue || offsetLong > int.MaxValue)
                                    continue;

                                var offset = (int)offsetLong;
                                int hits;
                                offsetHits.TryGetValue(offset, out hits);
                                offsetHits[offset] = hits + 1;
                                exactPairs++;
                                pairFound = true;
                                sb.AppendLine(
                                    "PAIR ITEM_ADDR=0x" + center.ToInt64().ToString("X8") +
                                    " COUNT_ADDR=0x" + row.Address.ToInt64().ToString("X8") +
                                    " OFFSET=" + offset +
                                    " COUNT=" + row.Value);
                            }

                            if (!pairFound && i < 8)
                            {
                                sb.AppendLine("ANCHOR ADDR=0x" + center.ToInt64().ToString("X8") + " NEARBY=");
                                var nearbyShown = 0;
                                foreach (var row in rows)
                                {
                                    if (nearbyShown >= 20) break;
                                    var off = row.Address.ToInt64() - center.ToInt64();
                                    sb.AppendLine("  OFF=" + off + " DEC=" + row.Value + " ADDR=0x" + row.Address.ToInt64().ToString("X8"));
                                    nearbyShown++;
                                }
                            }
                        }
                        sb.AppendLine();
                    }

                    var bestOffset = 0;
                    var bestHits = 0;
                    foreach (var kv in offsetHits)
                    {
                        if (kv.Value > bestHits)
                        {
                            bestOffset = kv.Key;
                            bestHits = kv.Value;
                        }
                    }

                    sb.AppendLine("[OFFSET_HISTOGRAM]");
                    var offsets = new List<KeyValuePair<int, int>>(offsetHits);
                    offsets.Sort(delegate(KeyValuePair<int, int> a, KeyValuePair<int, int> b)
                    {
                        var c = b.Value.CompareTo(a.Value);
                        return c != 0 ? c : a.Key.CompareTo(b.Key);
                    });
                    for (var i = 0; i < Math.Min(30, offsets.Count); i++)
                        sb.AppendLine("OFFSET=" + offsets[i].Key + " HITS=" + offsets[i].Value);
                    sb.AppendLine();
                    sb.AppendLine("TOTAL_ITEM_ID_ANCHORS=" + totalAnchors);
                    sb.AppendLine("EXACT_COUNT_PAIRS=" + exactPairs);
                    sb.AppendLine("BEST_COUNT_OFFSET=" + bestOffset);
                    sb.AppendLine("BEST_COUNT_OFFSET_HITS=" + bestHits);

                    File.WriteAllText(
                        Path.Combine(_appDir, "auto_inventory_discovery_evidence.txt"),
                        sb.ToString(),
                        new UTF8Encoding(false));

                    lock (_sync)
                    {
                        if (bestHits >= 2)
                            _inventoryStatus = "PASS_CANDIDATES offset=" + bestOffset + " hits=" + bestHits;
                        else if (totalAnchors > 0)
                            _inventoryStatus = "ITEM_ID_ANCHORS=" + totalAnchors + " pair=" + exactPairs;
                        else
                            _inventoryStatus = "NO_ANCHORS_RETRY";
                    }
                }
            }
            catch (Exception ex)
            {
                SaveLaneError("auto_inventory_discovery_evidence.txt", runtime, "AUTO_INVENTORY_EXPECTATION_DISCOVERY", ex);
                lock (_sync) _inventoryStatus = "ERROR_RETRY";
            }
            finally
            {
                lock (_sync)
                {
                    _inventoryRunning = false;
                    _inventoryRetryAfterUtc = DateTime.UtcNow.AddSeconds(45);
                }
            }
        }

        private void RunBuffLane(RuntimeSnapshot runtime)
        {
            try
            {
                var client = Path.Combine(_appDir, "Lin.bin2");
                if (!File.Exists(client))
                    throw new FileNotFoundException("Lin.bin2 not found", client);

                var image = PeImportParser.Parse(client);
                using (var probe = new RuntimeMemoryProbe())
                {
                    string error;
                    if (!probe.Attach(runtime.ProcessId, out error))
                        throw new InvalidOperationException(error);

                    string rootStatus;
                    var roots = NativeReceiveXrefScanner.Scan(probe, runtime, image, out rootStatus);
                    var flow = NativeReceiveFlowScanner.Build(probe, runtime, image, roots, 4);
                    var markerCount = 0;
                    var sb = NewHeader(runtime, "AUTO_BUFF_RECEIVE_DISCOVERY");
                    sb.AppendLine("RECV_ROOT_XREFS=" + roots.Count);
                    sb.AppendLine("FLOW_NODES=" + flow.Nodes.Count);
                    sb.AppendLine("ROOT_STATUS=" + rootStatus);
                    sb.AppendLine("FLOW_STATUS=" + flow.Status);
                    sb.AppendLine("BUFF_OPCODE_MARKER=HEURISTIC_ONLY");
                    sb.AppendLine("MEMORY_WRITE=NO");
                    sb.AppendLine();

                    foreach (var root in roots)
                    {
                        sb.AppendLine(
                            "ROOT import=" + root.ImportName +
                            " iat_rva=0x" + root.IatRva.ToString("X8") +
                            " call_rva=0x" + root.InstructionRva.ToString("X8") +
                            " kind=" + root.Kind);
                    }
                    sb.AppendLine();

                    foreach (var node in flow.Nodes)
                    {
                        if (!string.IsNullOrEmpty(node.BuffOpcodeMarker)) markerCount++;
                        sb.AppendLine(
                            "NODE depth=" + node.Depth +
                            " func_rva=0x" + node.FunctionRva.ToString("X8") +
                            " parent_rva=0x" + node.ParentFunctionRva.ToString("X8") +
                            " trigger_rva=0x" + node.TriggerCallRva.ToString("X8") +
                            " marker=" + node.BuffOpcodeMarker +
                            " source=" + node.Source);
                    }
                    sb.AppendLine();
                    sb.AppendLine("BUFF_MARKERS=" + markerCount);

                    File.WriteAllText(
                        Path.Combine(_appDir, "auto_buff_receive_evidence.txt"),
                        sb.ToString(),
                        new UTF8Encoding(false));

                    lock (_sync)
                    {
                        _buffStatus = roots.Count > 0
                            ? "PASS roots=" + roots.Count + " nodes=" + flow.Nodes.Count + " markers=" + markerCount
                            : "NO_RECV_ROOT";
                        _buffDone = true;
                    }
                }
            }
            catch (Exception ex)
            {
                SaveLaneError("auto_buff_receive_evidence.txt", runtime, "AUTO_BUFF_RECEIVE_DISCOVERY", ex);
                lock (_sync)
                {
                    _buffStatus = "ERROR";
                    _buffDone = true;
                }
            }
            finally
            {
                lock (_sync) _buffRunning = false;
            }
        }

        private void RunSendLane(RuntimeSnapshot runtime)
        {
            try
            {
                var client = Path.Combine(_appDir, "Lin.bin2");
                if (!File.Exists(client))
                    throw new FileNotFoundException("Lin.bin2 not found", client);

                var image = PeImportParser.Parse(client);
                using (var probe = new RuntimeMemoryProbe())
                {
                    string error;
                    if (!probe.Attach(runtime.ProcessId, out error))
                        throw new InvalidOperationException(error);

                    string status;
                    var xrefs = NativeSendXrefScanner.Scan(probe, runtime, image, out status);
                    var sb = NewHeader(runtime, "AUTO_SEND_DISCOVERY");
                    sb.AppendLine("XREFS=" + xrefs.Count);
                    sb.AppendLine("STATUS=" + status);
                    sb.AppendLine("MEMORY_WRITE=NO");
                    sb.AppendLine();
                    foreach (var xref in xrefs)
                    {
                        sb.AppendLine(
                            "IMPORT=" + xref.ImportName +
                            " IAT_RVA=0x" + xref.IatRva.ToString("X8") +
                            " CALL_RVA=0x" + xref.InstructionRva.ToString("X8") +
                            " CALL_VA=0x" + xref.InstructionAddress.ToInt64().ToString("X8") +
                            " KIND=" + xref.Kind);
                    }

                    File.WriteAllText(
                        Path.Combine(_appDir, "auto_send_discovery_evidence.txt"),
                        sb.ToString(),
                        new UTF8Encoding(false));

                    lock (_sync)
                    {
                        _sendStatus = "DONE xrefs=" + xrefs.Count;
                        _sendDone = true;
                    }
                }
            }
            catch (Exception ex)
            {
                SaveLaneError("auto_send_discovery_evidence.txt", runtime, "AUTO_SEND_DISCOVERY", ex);
                lock (_sync)
                {
                    _sendStatus = "ERROR";
                    _sendDone = true;
                }
            }
            finally
            {
                lock (_sync) _sendRunning = false;
            }
        }

        private List<Expectation> LoadLatestExpectations()
        {
            var path = Path.Combine(_appDir, "inventory_validation_evidence.txt");
            var result = new List<Expectation>();
            if (!File.Exists(path)) return result;

            var current = new List<Expectation>();
            var inExpected = false;
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                if (line == "[EXPECTED]")
                {
                    current = new List<Expectation>();
                    inExpected = true;
                    continue;
                }
                if (!inExpected) continue;
                if (line.Length == 0)
                {
                    if (current.Count > 0) result = current;
                    inExpected = false;
                    continue;
                }
                if (line.StartsWith("[", StringComparison.Ordinal))
                {
                    if (current.Count > 0) result = current;
                    inExpected = false;
                    continue;
                }

                var eq = line.IndexOf('=');
                int itemId;
                int count;
                if (eq > 0 &&
                    int.TryParse(line.Substring(0, eq).Trim(), out itemId) &&
                    int.TryParse(line.Substring(eq + 1).Trim(), out count) &&
                    itemId > 0 && count >= 0)
                {
                    current.Add(new Expectation { ItemId = itemId, Count = count });
                }
            }
            if (inExpected && current.Count > 0) result = current;
            return result;
        }

        private void SaveInventoryWaiting(RuntimeSnapshot runtime, string reason)
        {
            var sb = NewHeader(runtime, "AUTO_INVENTORY_EXPECTATION_DISCOVERY");
            sb.AppendLine("STATUS=WAITING_EXPECTED");
            sb.AppendLine("REASON=" + reason);
            sb.AppendLine("MEMORY_WRITE=NO");
            File.WriteAllText(
                Path.Combine(_appDir, "auto_inventory_discovery_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
        }

        private static StringBuilder NewHeader(RuntimeSnapshot runtime, string mode)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=" + mode);
            sb.AppendLine("PID=" + runtime.ProcessId);
            sb.AppendLine("PROCESS_START_UTC=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
            return sb;
        }

        private void SaveLaneError(string fileName, RuntimeSnapshot runtime, string mode, Exception ex)
        {
            try
            {
                var sb = NewHeader(runtime, mode);
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(Path.Combine(_appDir, fileName), sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
