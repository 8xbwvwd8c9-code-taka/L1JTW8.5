using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Runtime.InteropServices;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeFieldMap
    {
        public string Name = "";
        public bool IsPointerChain;
        public int BaseRva;
        public readonly List<int> Offsets = new List<int>();

        public static RuntimeFieldMap Parse(string name, string text)
        {
            if (string.IsNullOrWhiteSpace(text))
                return null;

            text = text.Trim();

            if (text.StartsWith("RVA:", StringComparison.OrdinalIgnoreCase))
            {
                return new RuntimeFieldMap
                {
                    Name = name,
                    IsPointerChain = false,
                    BaseRva = ParseInt(text.Substring(4))
                };
            }

            if (text.StartsWith("PTR:", StringComparison.OrdinalIgnoreCase))
            {
                var body = text.Substring(4);
                var parts = body.Split(new[] { '|' }, StringSplitOptions.RemoveEmptyEntries);
                if (parts.Length < 2)
                    throw new InvalidDataException(name + " PTR 格式至少需要 baseRVA 與一個 offset。");

                var map = new RuntimeFieldMap
                {
                    Name = name,
                    IsPointerChain = true,
                    BaseRva = ParseInt(parts[0])
                };

                for (var i = 1; i < parts.Length; i++)
                    map.Offsets.Add(ParseInt(parts[i]));

                return map;
            }

            throw new InvalidDataException(
                name + " 格式錯誤。支援 RVA:0x1234 或 PTR:0x1234|0x10|0x20。");
        }

        private static int ParseInt(string text)
        {
            text = text.Trim();
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
                return int.Parse(text.Substring(2), NumberStyles.HexNumber, CultureInfo.InvariantCulture);

            return int.Parse(text, NumberStyles.Integer, CultureInfo.InvariantCulture);
        }
    }

    internal sealed class RuntimeMap
    {
        public RuntimeFieldMap CurrentHp;
        public RuntimeFieldMap MaxHp;
        public RuntimeFieldMap CurrentMp;
        public RuntimeFieldMap MaxMp;

        public bool HasHpMp
        {
            get
            {
                return CurrentHp != null &&
                       MaxHp != null &&
                       CurrentMp != null &&
                       MaxMp != null;
            }
        }

        public static RuntimeMap Load(string path)
        {
            var ini = IniDocument.Load(path);
            return new RuntimeMap
            {
                CurrentHp = RuntimeFieldMap.Parse("CurrentHP", ini.Get("HPMP", "CurrentHP", "")),
                MaxHp = RuntimeFieldMap.Parse("MaxHP", ini.Get("HPMP", "MaxHP", "")),
                CurrentMp = RuntimeFieldMap.Parse("CurrentMP", ini.Get("HPMP", "CurrentMP", "")),
                MaxMp = RuntimeFieldMap.Parse("MaxMP", ini.Get("HPMP", "MaxMP", ""))
            };
        }
    }

    internal sealed class RuntimeMapReader : IDisposable
    {
        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_QUERY_INFORMATION = 0x0400;

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern IntPtr OpenProcess(uint access, bool inheritHandle, int processId);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool CloseHandle(IntPtr handle);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool ReadProcessMemory(
            IntPtr processHandle,
            IntPtr baseAddress,
            byte[] buffer,
            int size,
            out IntPtr bytesRead);

        private IntPtr _handle = IntPtr.Zero;
        private IntPtr _moduleBase = IntPtr.Zero;

        public bool Attach(int pid, IntPtr moduleBase, out string error)
        {
            error = "";
            Dispose();

            _handle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
            if (_handle == IntPtr.Zero)
            {
                error = "OpenProcess 失敗，Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            _moduleBase = moduleBase;
            return true;
        }

        public bool TryReadInt32(RuntimeFieldMap map, out int value, out string error)
        {
            value = 0;
            error = "";

            if (_handle == IntPtr.Zero || _moduleBase == IntPtr.Zero)
            {
                error = "尚未連接程序。";
                return false;
            }

            try
            {
                long address = _moduleBase.ToInt64() + (uint)map.BaseRva;

                if (!map.IsPointerChain)
                    return TryReadInt32At(new IntPtr(address), out value, out error);

                if (map.Offsets.Count == 0)
                {
                    error = "Pointer chain 無 offset。";
                    return false;
                }

                uint pointer;
                if (!TryReadUInt32At(new IntPtr(address), out pointer, out error))
                    return false;

                for (var i = 0; i < map.Offsets.Count - 1; i++)
                {
                    var nextAddress = unchecked((uint)(pointer + map.Offsets[i]));
                    if (!TryReadUInt32At(new IntPtr(nextAddress), out pointer, out error))
                        return false;

                    if (pointer == 0)
                    {
                        error = "Pointer chain 遇到 NULL。";
                        return false;
                    }
                }

                var finalAddress = unchecked((uint)(pointer + map.Offsets[map.Offsets.Count - 1]));
                return TryReadInt32At(new IntPtr(finalAddress), out value, out error);
            }
            catch (Exception ex)
            {
                error = ex.Message;
                return false;
            }
        }

        private bool TryReadUInt32At(IntPtr address, out uint value, out string error)
        {
            value = 0;
            error = "";
            var buffer = new byte[4];
            IntPtr bytesRead;

            if (!ReadProcessMemory(_handle, address, buffer, 4, out bytesRead) ||
                bytesRead.ToInt64() != 4)
            {
                error = "ReadProcessMemory 失敗 @0x" +
                        address.ToInt64().ToString("X8") +
                        " Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            value = BitConverter.ToUInt32(buffer, 0);
            return true;
        }

        private bool TryReadInt32At(IntPtr address, out int value, out string error)
        {
            value = 0;
            error = "";
            var buffer = new byte[4];
            IntPtr bytesRead;

            if (!ReadProcessMemory(_handle, address, buffer, 4, out bytesRead) ||
                bytesRead.ToInt64() != 4)
            {
                error = "ReadProcessMemory 失敗 @0x" +
                        address.ToInt64().ToString("X8") +
                        " Win32=" + Marshal.GetLastWin32Error();
                return false;
            }

            value = BitConverter.ToInt32(buffer, 0);
            return true;
        }

        public void Dispose()
        {
            if (_handle != IntPtr.Zero)
            {
                CloseHandle(_handle);
                _handle = IntPtr.Zero;
            }
            _moduleBase = IntPtr.Zero;
        }
    }
}
