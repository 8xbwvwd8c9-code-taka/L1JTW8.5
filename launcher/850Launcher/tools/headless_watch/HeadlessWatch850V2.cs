using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;

internal static class HeadlessWatch850V2
{
    private const uint PROCESS_VM_READ = 0x0010;
    private const uint PROCESS_QUERY_INFORMATION = 0x0400;
    private const uint THREAD_SUSPEND_RESUME = 0x0002;
    private const uint THREAD_GET_CONTEXT = 0x0008;
    private const uint THREAD_SET_CONTEXT = 0x0010;
    private const uint THREAD_QUERY_INFORMATION = 0x0040;

    private const uint CONTEXT_I386 = 0x00010000;
    private const uint CONTEXT_CONTROL = CONTEXT_I386 | 0x00000001;
    private const uint CONTEXT_INTEGER = CONTEXT_I386 | 0x00000002;
    private const uint CONTEXT_DEBUG_REGISTERS = CONTEXT_I386 | 0x00000010;
    private const uint CONTEXT_REQUIRED = CONTEXT_CONTROL | CONTEXT_INTEGER | CONTEXT_DEBUG_REGISTERS;

    private const uint EXCEPTION_DEBUG_EVENT = 1;
    private const uint CREATE_THREAD_DEBUG_EVENT = 2;
    private const uint CREATE_PROCESS_DEBUG_EVENT = 3;
    private const uint EXIT_PROCESS_DEBUG_EVENT = 5;

    private const uint EXCEPTION_BREAKPOINT = 0x80000003;
    private const uint EXCEPTION_SINGLE_STEP = 0x80000004;
    private const uint DBG_CONTINUE = 0x00010002;
    private const uint DBG_EXCEPTION_NOT_HANDLED = 0x80010001;

    [StructLayout(LayoutKind.Explicit, Size = 716)]
    private struct Context32
    {
        [FieldOffset(0x00)] public uint ContextFlags;
        [FieldOffset(0x04)] public uint Dr0;
        [FieldOffset(0x08)] public uint Dr1;
        [FieldOffset(0x0C)] public uint Dr2;
        [FieldOffset(0x10)] public uint Dr3;
        [FieldOffset(0x14)] public uint Dr6;
        [FieldOffset(0x18)] public uint Dr7;
        [FieldOffset(0x9C)] public uint Edi;
        [FieldOffset(0xA0)] public uint Esi;
        [FieldOffset(0xA4)] public uint Ebx;
        [FieldOffset(0xA8)] public uint Edx;
        [FieldOffset(0xAC)] public uint Ecx;
        [FieldOffset(0xB0)] public uint Eax;
        [FieldOffset(0xB4)] public uint Ebp;
        [FieldOffset(0xB8)] public uint Eip;
        [FieldOffset(0xC0)] public uint EFlags;
        [FieldOffset(0xC4)] public uint Esp;
    }

    // DEBUG_EVENT begins with code/pid/tid, followed by a union. For an
    // EXCEPTION_DEBUG_EVENT the first DWORD in the union is ExceptionCode.
    [StructLayout(LayoutKind.Explicit, Size = 176)]
    private struct DebugEvent
    {
        [FieldOffset(0)] public uint EventCode;
        [FieldOffset(4)] public uint ProcessId;
        [FieldOffset(8)] public uint ThreadId;
        [FieldOffset(12)] public uint ExceptionCode;
    }

    private sealed class Watch
    {
        public string Name = "";
        public uint Address;
        public int Hits;
    }

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool DebugActiveProcess(uint processId);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool DebugActiveProcessStop(uint processId);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool DebugSetProcessKillOnExit(bool killOnExit);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool WaitForDebugEvent(out DebugEvent debugEvent, uint milliseconds);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool ContinueDebugEvent(uint processId, uint threadId, uint continueStatus);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern IntPtr OpenThread(uint access, bool inheritHandle, uint threadId);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern IntPtr OpenProcess(uint access, bool inheritHandle, uint processId);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool CloseHandle(IntPtr handle);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern uint SuspendThread(IntPtr thread);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern uint ResumeThread(IntPtr thread);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool GetThreadContext(IntPtr thread, ref Context32 context);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool SetThreadContext(IntPtr thread, ref Context32 context);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool ReadProcessMemory(IntPtr process, IntPtr address, byte[] buffer, int size, out IntPtr read);

    private static uint ParseUInt(string value)
    {
        value = value.Trim();
        if (value.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            return uint.Parse(value.Substring(2), NumberStyles.HexNumber, CultureInfo.InvariantCulture);
        return uint.Parse(value, CultureInfo.InvariantCulture);
    }

    private static string HexBytes(byte[] bytes, int count)
    {
        if (bytes == null || count <= 0) return "";
        var sb = new StringBuilder(count * 3);
        for (var i = 0; i < count; i++)
        {
            if (i != 0) sb.Append(' ');
            sb.Append(bytes[i].ToString("X2", CultureInfo.InvariantCulture));
        }
        return sb.ToString();
    }

    private static byte[] ReadBytes(IntPtr process, uint address, int count, out int got)
    {
        var buffer = new byte[count];
        IntPtr read;
        got = 0;
        if (!ReadProcessMemory(process, new IntPtr(unchecked((int)address)), buffer, count, out read))
            return buffer;
        got = (int)Math.Min(count, read.ToInt64());
        return buffer;
    }

    private static string ReadDwordText(IntPtr process, uint address)
    {
        int got;
        var bytes = ReadBytes(process, address, 4, out got);
        if (got != 4) return "UNREADABLE";
        return "0x" + BitConverter.ToUInt32(bytes, 0).ToString("X8", CultureInfo.InvariantCulture);
    }

    private static bool ConfigureThread(uint tid, IList<Watch> watches, bool clearOnly, out string error)
    {
        error = "";
        var access = THREAD_SUSPEND_RESUME | THREAD_GET_CONTEXT | THREAD_SET_CONTEXT | THREAD_QUERY_INFORMATION;
        var thread = OpenThread(access, false, tid);
        if (thread == IntPtr.Zero)
        {
            error = "OpenThread Win32=" + Marshal.GetLastWin32Error();
            return false;
        }

        var suspended = false;
        try
        {
            var previous = SuspendThread(thread);
            if (previous == 0xFFFFFFFF)
            {
                error = "SuspendThread Win32=" + Marshal.GetLastWin32Error();
                return false;
            }
            suspended = true;

            var context = new Context32 { ContextFlags = CONTEXT_REQUIRED };
            if (!GetThreadContext(thread, ref context))
            {
                error = "GetThreadContext Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            context.Dr0 = 0;
            context.Dr1 = 0;
            context.Dr2 = 0;
            context.Dr3 = 0;
            context.Dr6 = 0;
            context.Dr7 = 0;

            if (!clearOnly)
            {
                for (var i = 0; i < watches.Count; i++)
                {
                    var address = watches[i].Address;
                    if ((address & 3) != 0)
                    {
                        error = "watch address must be 4-byte aligned: 0x" + address.ToString("X8", CultureInfo.InvariantCulture);
                        return false;
                    }

                    if (i == 0) context.Dr0 = address;
                    else if (i == 1) context.Dr1 = address;
                    else if (i == 2) context.Dr2 = address;
                    else if (i == 3) context.Dr3 = address;

                    // Local enable = bit 2*i. RW=01 (write), LEN=11 (4 bytes).
                    context.Dr7 |= (1u << (i * 2));
                    context.Dr7 |= (0xDu << (16 + (i * 4)));
                }
            }

            if (!SetThreadContext(thread, ref context))
            {
                error = "SetThreadContext Win32=" + Marshal.GetLastWin32Error();
                return false;
            }
            return true;
        }
        finally
        {
            if (suspended) ResumeThread(thread);
            CloseHandle(thread);
        }
    }

    private static void ConfigureAllThreads(uint pid, IList<Watch> watches, bool clearOnly, TextWriter log)
    {
        try
        {
            var process = Process.GetProcessById((int)pid);
            foreach (ProcessThread threadInfo in process.Threads)
            {
                string error;
                if (!ConfigureThread((uint)threadInfo.Id, watches, clearOnly, out error))
                    log.WriteLine("THREAD_CONFIG_FAIL TID=" + threadInfo.Id + " CLEAR=" + (clearOnly ? 1 : 0) + " ERROR=" + error);
            }
        }
        catch (Exception ex)
        {
            log.WriteLine("THREAD_ENUM_FAIL ERROR=" + ex.Message);
        }
    }

    private static bool ReadContextAndClearDr6(uint tid, out Context32 observed, out string error)
    {
        observed = new Context32 { ContextFlags = CONTEXT_REQUIRED };
        error = "";
        var thread = OpenThread(THREAD_GET_CONTEXT | THREAD_SET_CONTEXT | THREAD_QUERY_INFORMATION, false, tid);
        if (thread == IntPtr.Zero)
        {
            error = "OpenThread Win32=" + Marshal.GetLastWin32Error();
            return false;
        }

        try
        {
            if (!GetThreadContext(thread, ref observed))
            {
                error = "GetThreadContext Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            var originalDr6 = observed.Dr6;
            var cleared = observed;
            cleared.Dr6 = 0;
            if (!SetThreadContext(thread, ref cleared))
            {
                error = "SetThreadContext(clear DR6) Win32=" + Marshal.GetLastWin32Error();
                return false;
            }
            observed.Dr6 = originalDr6;
            return true;
        }
        finally
        {
            CloseHandle(thread);
        }
    }

    private static bool IsInModule(uint value, ulong moduleStart, ulong moduleEnd)
    {
        var v = (ulong)value;
        return v >= moduleStart && v < moduleEnd;
    }

    public static int Main(string[] args)
    {
        uint pid = 0;
        uint moduleBase = 0;
        uint moduleSize = 0;
        var output = "headless_watch_850.txt";
        var timeoutSeconds = 90;
        var maxHits = 24;
        var startUtc = "";
        var watches = new List<Watch>();

        for (var i = 0; i < args.Length; i++)
        {
            var arg = args[i];
            if (arg == "--pid" && i + 1 < args.Length) pid = ParseUInt(args[++i]);
            else if (arg == "--base" && i + 1 < args.Length) moduleBase = ParseUInt(args[++i]);
            else if (arg == "--size" && i + 1 < args.Length) moduleSize = ParseUInt(args[++i]);
            else if (arg == "--output" && i + 1 < args.Length) output = args[++i];
            else if (arg == "--timeout-sec" && i + 1 < args.Length) timeoutSeconds = int.Parse(args[++i], CultureInfo.InvariantCulture);
            else if (arg == "--max-hits" && i + 1 < args.Length) maxHits = int.Parse(args[++i], CultureInfo.InvariantCulture);
            else if (arg == "--start-utc" && i + 1 < args.Length) startUtc = args[++i];
            else if (arg == "--watch" && i + 1 < args.Length)
            {
                var raw = args[++i];
                var eq = raw.IndexOf('=');
                if (eq <= 0) throw new ArgumentException("--watch format is NAME=0xADDRESS");
                watches.Add(new Watch { Name = raw.Substring(0, eq), Address = ParseUInt(raw.Substring(eq + 1)) });
            }
        }

        if (pid == 0 || moduleBase == 0 || moduleSize == 0 || watches.Count == 0)
        {
            Console.Error.WriteLine("usage: HeadlessWatch850V2 --pid N --base 0xVA --size 0xSIZE --watch NAME=0xVA [--watch ...] --output file");
            return 2;
        }
        if (watches.Count > 4) throw new ArgumentException("x86 hardware debug registers allow at most four watchpoints.");
        foreach (var watch in watches)
            if ((watch.Address & 3) != 0) throw new ArgumentException("watch must be 4-byte aligned: " + watch.Name);

        var moduleStart = (ulong)moduleBase;
        var moduleEnd = moduleStart + (ulong)moduleSize;
        if (moduleEnd > ((ulong)uint.MaxValue + 1UL))
            throw new ArgumentException("module range exceeds x86 address space.");

        var parent = Path.GetDirectoryName(Path.GetFullPath(output));
        if (!string.IsNullOrEmpty(parent)) Directory.CreateDirectory(parent);

        using (var log = new StreamWriter(output, false, new UTF8Encoding(false)))
        {
            log.AutoFlush = true;
            log.WriteLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture));
            log.WriteLine("MODE=HEADLESS_X86_HARDWARE_WRITE_WATCH_V2");
            log.WriteLine("PID=" + pid);
            log.WriteLine("PROCESS_START_UTC=" + startUtc);
            log.WriteLine("MODULE_BASE=0x" + moduleBase.ToString("X8", CultureInfo.InvariantCulture));
            log.WriteLine("MODULE_SIZE=0x" + moduleSize.ToString("X8", CultureInfo.InvariantCulture));
            log.WriteLine("DEBUG_REGISTERS_USED=YES");
            log.WriteLine("TARGET_MEMORY_WRITE=NO");
            log.WriteLine("WRITEPROCESSMEMORY=NO");
            for (var i = 0; i < watches.Count; i++)
                log.WriteLine("WATCH[" + i + "]=" + watches[i].Name + "@0x" + watches[i].Address.ToString("X8", CultureInfo.InvariantCulture));

            var processHandle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
            if (processHandle == IntPtr.Zero)
            {
                log.WriteLine("STATUS=BLOCKED_OPEN_PROCESS Win32=" + Marshal.GetLastWin32Error());
                return 3;
            }

            var attached = false;
            try
            {
                if (!DebugActiveProcess(pid))
                {
                    log.WriteLine("STATUS=BLOCKED_DEBUG_ATTACH Win32=" + Marshal.GetLastWin32Error());
                    return 4;
                }
                attached = true;
                DebugSetProcessKillOnExit(false);
                ConfigureAllThreads(pid, watches, false, log);

                log.WriteLine("STATUS=WATCHING");
                var deadline = DateTime.UtcNow.AddSeconds(Math.Max(1, timeoutSeconds));
                var totalHits = 0;
                var exited = false;

                while (DateTime.UtcNow < deadline && totalHits < maxHits && !exited)
                {
                    DebugEvent debugEvent;
                    if (!WaitForDebugEvent(out debugEvent, 250))
                        continue;

                    var continueCode = DBG_CONTINUE;
                    try
                    {
                        if (debugEvent.EventCode == CREATE_THREAD_DEBUG_EVENT || debugEvent.EventCode == CREATE_PROCESS_DEBUG_EVENT)
                        {
                            string error;
                            if (!ConfigureThread(debugEvent.ThreadId, watches, false, out error))
                                log.WriteLine("THREAD_ARM_FAIL TID=" + debugEvent.ThreadId + " ERROR=" + error);
                        }
                        else if (debugEvent.EventCode == EXIT_PROCESS_DEBUG_EVENT)
                        {
                            exited = true;
                        }
                        else if (debugEvent.EventCode == EXCEPTION_DEBUG_EVENT)
                        {
                            if (debugEvent.ExceptionCode == EXCEPTION_SINGLE_STEP)
                            {
                                Context32 context;
                                string error;
                                if (ReadContextAndClearDr6(debugEvent.ThreadId, out context, out error))
                                {
                                    var dr6 = context.Dr6;
                                    for (var wi = 0; wi < watches.Count; wi++)
                                    {
                                        if ((dr6 & (1u << wi)) == 0) continue;
                                        watches[wi].Hits++;
                                        totalHits++;

                                        var eipInModule = IsInModule(context.Eip, moduleStart, moduleEnd);
                                        var eipRva = eipInModule ? (uint)((ulong)context.Eip - moduleStart) : 0u;
                                        var postWriteValue = ReadDwordText(processHandle, watches[wi].Address);

                                        // x86 instructions can be up to 15 bytes. Capture 24 bytes before
                                        // post-write EIP so a decoder can align the writer, plus forward context.
                                        int codeGot;
                                        var codeStart = context.Eip >= 24 ? context.Eip - 24 : 0u;
                                        var code = ReadBytes(processHandle, codeStart, 64, out codeGot);
                                        int stackGot;
                                        var stack = ReadBytes(processHandle, context.Esp, 64, out stackGot);

                                        var returnCandidates = new List<string>();
                                        for (var offset = 0; offset + 4 <= stackGot; offset += 4)
                                        {
                                            var value = BitConverter.ToUInt32(stack, offset);
                                            if (IsInModule(value, moduleStart, moduleEnd))
                                                returnCandidates.Add("+0x" + ((ulong)value - moduleStart).ToString("X8", CultureInfo.InvariantCulture));
                                            if (returnCandidates.Count >= 8) break;
                                        }

                                        log.WriteLine(
                                            "HIT NAME=" + watches[wi].Name +
                                            " N=" + watches[wi].Hits +
                                            " TID=" + debugEvent.ThreadId +
                                            " WATCH=0x" + watches[wi].Address.ToString("X8", CultureInfo.InvariantCulture) +
                                            " POST_WRITE_VALUE=" + postWriteValue +
                                            " EIP=0x" + context.Eip.ToString("X8", CultureInfo.InvariantCulture) +
                                            " EIP_RVA=" + (eipInModule ? "0x" + eipRva.ToString("X8", CultureInfo.InvariantCulture) : "OUTSIDE") +
                                            " EAX=0x" + context.Eax.ToString("X8", CultureInfo.InvariantCulture) +
                                            " EBX=0x" + context.Ebx.ToString("X8", CultureInfo.InvariantCulture) +
                                            " ECX=0x" + context.Ecx.ToString("X8", CultureInfo.InvariantCulture) +
                                            " EDX=0x" + context.Edx.ToString("X8", CultureInfo.InvariantCulture) +
                                            " ESI=0x" + context.Esi.ToString("X8", CultureInfo.InvariantCulture) +
                                            " EDI=0x" + context.Edi.ToString("X8", CultureInfo.InvariantCulture) +
                                            " EBP=0x" + context.Ebp.ToString("X8", CultureInfo.InvariantCulture) +
                                            " ESP=0x" + context.Esp.ToString("X8", CultureInfo.InvariantCulture) +
                                            " DR6=0x" + dr6.ToString("X8", CultureInfo.InvariantCulture));
                                        log.WriteLine("  CODE_FROM=0x" + codeStart.ToString("X8", CultureInfo.InvariantCulture) + " BYTES=" + HexBytes(code, codeGot));
                                        log.WriteLine("  STACK=" + HexBytes(stack, stackGot));
                                        log.WriteLine("  RET_CANDIDATE_RVAS=" + string.Join(",", returnCandidates.ToArray()));
                                    }
                                }
                                else
                                {
                                    log.WriteLine("CONTEXT_FAIL TID=" + debugEvent.ThreadId + " ERROR=" + error);
                                }
                                continueCode = DBG_CONTINUE;
                            }
                            else if (debugEvent.ExceptionCode == EXCEPTION_BREAKPOINT)
                            {
                                continueCode = DBG_CONTINUE;
                            }
                            else
                            {
                                continueCode = DBG_EXCEPTION_NOT_HANDLED;
                            }
                        }
                    }
                    finally
                    {
                        ContinueDebugEvent(debugEvent.ProcessId, debugEvent.ThreadId, continueCode);
                    }
                }

                log.WriteLine("[SUMMARY]");
                log.WriteLine("TOTAL_HITS=" + totalHits);
                foreach (var watch in watches) log.WriteLine("WATCH_HITS " + watch.Name + "=" + watch.Hits);
                log.WriteLine("STATUS=" + (totalHits > 0 ? "PASS_OBSERVED_WRITE" : "NO_WRITE_OBSERVED"));
                log.WriteLine("TARGET_MEMORY_WRITE=NO");
                return totalHits > 0 ? 0 : 5;
            }
            finally
            {
                if (attached)
                {
                    ConfigureAllThreads(pid, watches, true, log);
                    if (!DebugActiveProcessStop(pid))
                        log.WriteLine("DETACH_FAIL Win32=" + Marshal.GetLastWin32Error());
                    else
                        log.WriteLine("DETACH=PASS");
                }
                CloseHandle(processHandle);
            }
        }
    }
}
