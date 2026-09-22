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

        public static RuntimeFieldMap Parse(
            string name,
            string text)
        {
            if (string.IsNullOrWhiteSpace(text))
                return null;

            text = text.Trim();

            if (text.StartsWith(
                "RVA:",
                StringComparison.OrdinalIgnoreCase))
            {
                return new RuntimeFieldMap
                {
                    Name = name,
                    IsPointerChain = false,
                    BaseRva = ParseInt(
                        text.Substring(4))
                };
            }

            if (text.StartsWith(
                "PTR:",
                StringComparison.OrdinalIgnoreCase))
            {
                var body = text.Substring(4);

                var parts = body.Split(
                    new[] { '|' },
                    StringSplitOptions.RemoveEmptyEntries);

                if (parts.Length < 2)
                {
                    throw new InvalidDataException(
                        name +
                        " PTR 格式至少需要 baseRVA 與一個 offset。");
                }

                var map = new RuntimeFieldMap
                {
                    Name = name,
                    IsPointerChain = true,
                    BaseRva = ParseInt(parts[0])
                };

                for (var i = 1;
                     i < parts.Length;
                     i++)
                {
                    map.Offsets.Add(
                        ParseInt(parts[i]));
                }

                return map;
            }

            throw new InvalidDataException(
                name +
                " 格式錯誤。支援 RVA:0x1234 或 PTR:0x1234|0x10|0x20。");
        }

        private static int ParseInt(
            string text)
        {
            text = text.Trim();

            var negative =
                text.StartsWith("-");

            if (negative)
                text = text.Substring(1);

            int value;

            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
            {
                value = int.Parse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture);
            }
            else
            {
                value = int.Parse(
                    text,
                    NumberStyles.Integer,
                    CultureInfo.InvariantCulture);
            }

            return negative
                ? -value
                : value;
        }
    }

    internal sealed class RuntimeMap
    {
        public RuntimeFieldMap PlayerObjectId;
        public RuntimeFieldMap PlayerX;
        public RuntimeFieldMap PlayerY;
        public int PlayerXWidth = 4;
        public int PlayerYWidth = 4;

        public RuntimeFieldMap CurrentHp;
        public RuntimeFieldMap MaxHp;
        public RuntimeFieldMap CurrentMp;
        public RuntimeFieldMap MaxMp;

        public bool HasPlayerIdentity
        {
            get
            {
                return PlayerObjectId != null &&
                       PlayerX != null &&
                       PlayerY != null;
            }
        }

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

        private static int NormalizeCoordinateWidth(
            int width)
        {
            if (width == 2 ||
                width == 4)
                return width;

            throw new InvalidDataException(
                "Player X/Y width 只支援 2 或 4 bytes。");
        }

        public static RuntimeMap Load(
            string path)
        {
            var ini = IniDocument.Load(path);

            return new RuntimeMap
            {
                PlayerObjectId =
                    RuntimeFieldMap.Parse(
                        "PlayerObjectId",
                        ini.Get(
                            "Player",
                            "ObjectId",
                            "")),

                PlayerX =
                    RuntimeFieldMap.Parse(
                        "PlayerX",
                        ini.Get(
                            "Player",
                            "X",
                            "")),

                PlayerY =
                    RuntimeFieldMap.Parse(
                        "PlayerY",
                        ini.Get(
                            "Player",
                            "Y",
                            "")),

                PlayerXWidth =
                    NormalizeCoordinateWidth(
                        ini.GetInt(
                            "Player",
                            "XWidth",
                            4)),

                PlayerYWidth =
                    NormalizeCoordinateWidth(
                        ini.GetInt(
                            "Player",
                            "YWidth",
                            4)),

                CurrentHp =
                    RuntimeFieldMap.Parse(
                        "CurrentHP",
                        ini.Get(
                            "HPMP",
                            "CurrentHP",
                            "")),

                MaxHp =
                    RuntimeFieldMap.Parse(
                        "MaxHP",
                        ini.Get(
                            "HPMP",
                            "MaxHP",
                            "")),

                CurrentMp =
                    RuntimeFieldMap.Parse(
                        "CurrentMP",
                        ini.Get(
                            "HPMP",
                            "CurrentMP",
                            "")),

                MaxMp =
                    RuntimeFieldMap.Parse(
                        "MaxMP",
                        ini.Get(
                            "HPMP",
                            "MaxMP",
                            ""))
            };
        }
    }

    internal sealed class RuntimeMapReader :
        IDisposable
    {
        private const uint PROCESS_VM_READ =
            0x0010;

        private const uint PROCESS_QUERY_INFORMATION =
            0x0400;

        [DllImport(
            "kernel32.dll",
            SetLastError = true)]
        private static extern IntPtr OpenProcess(
            uint access,
            bool inheritHandle,
            int processId);

        [DllImport(
            "kernel32.dll",
            SetLastError = true)]
        private static extern bool CloseHandle(
            IntPtr handle);

        [DllImport(
            "kernel32.dll",
            SetLastError = true)]
        private static extern bool ReadProcessMemory(
            IntPtr processHandle,
            IntPtr baseAddress,
            byte[] buffer,
            int size,
            out IntPtr bytesRead);

        private IntPtr _handle =
            IntPtr.Zero;

        private IntPtr _moduleBase =
            IntPtr.Zero;

        public bool Attach(
            int pid,
            IntPtr moduleBase,
            out string error)
        {
            error = "";
            Dispose();

            _handle = OpenProcess(
                PROCESS_QUERY_INFORMATION |
                PROCESS_VM_READ,
                false,
                pid);

            if (_handle == IntPtr.Zero)
            {
                error =
                    "OpenProcess 失敗，Win32=" +
                    Marshal.GetLastWin32Error();

                return false;
            }

            _moduleBase = moduleBase;
            return true;
        }

        public bool TryReadInt32(
            RuntimeFieldMap map,
            out int value,
            out string error)
        {
            value = 0;

            IntPtr address;
            if (!TryResolveAddress(
                map,
                out address,
                out error))
                return false;

            return TryReadInt32At(
                address,
                out value,
                out error);
        }

        public bool TryReadUInt32(
            RuntimeFieldMap map,
            out uint value,
            out string error)
        {
            value = 0;

            IntPtr address;
            if (!TryResolveAddress(
                map,
                out address,
                out error))
                return false;

            return TryReadUInt32At(
                address,
                out value,
                out error);
        }

        public bool TryReadUnsigned(
            RuntimeFieldMap map,
            int width,
            out uint value,
            out string error)
        {
            value = 0;

            if (width == 2)
            {
                ushort v;
                if (!TryReadUInt16(
                    map,
                    out v,
                    out error))
                    return false;

                value = v;
                return true;
            }

            if (width == 4)
                return TryReadUInt32(
                    map,
                    out value,
                    out error);

            error =
                "unsigned runtime field width 只支援 2 或 4 bytes。";

            return false;
        }

        public bool TryReadUInt16(
            RuntimeFieldMap map,
            out ushort value,
            out string error)
        {
            value = 0;

            IntPtr address;
            if (!TryResolveAddress(
                map,
                out address,
                out error))
                return false;

            return TryReadUInt16At(
                address,
                out value,
                out error);
        }

        private bool TryResolveAddress(
            RuntimeFieldMap map,
            out IntPtr address,
            out string error)
        {
            address = IntPtr.Zero;
            error = "";

            if (_handle == IntPtr.Zero ||
                _moduleBase == IntPtr.Zero)
            {
                error = "尚未連接程序。";
                return false;
            }

            if (map == null)
            {
                error = "Runtime field map 為空。";
                return false;
            }

            try
            {
                long current =
                    _moduleBase.ToInt64() +
                    (long)(uint)map.BaseRva;

                if (!map.IsPointerChain)
                {
                    if (!IsX86Address(current))
                    {
                        error =
                            map.Name +
                            " RVA 解析後超出 x86 範圍。";

                        return false;
                    }

                    address =
                        new IntPtr(current);

                    return true;
                }

                if (map.Offsets.Count == 0)
                {
                    error =
                        map.Name +
                        " pointer chain 無 offset。";

                    return false;
                }

                uint pointer;
                if (!TryReadUInt32At(
                    new IntPtr(current),
                    out pointer,
                    out error))
                    return false;

                if (pointer < 0x10000)
                {
                    error =
                        map.Name +
                        " pointer chain root 為 NULL/無效。";

                    return false;
                }

                for (var i = 0;
                     i < map.Offsets.Count - 1;
                     i++)
                {
                    var nextAddress =
                        (long)pointer +
                        map.Offsets[i];

                    if (!IsX86Address(
                        nextAddress))
                    {
                        error =
                            map.Name +
                            " pointer chain 中間位址超出 x86 範圍。";

                        return false;
                    }

                    if (!TryReadUInt32At(
                        new IntPtr(
                            nextAddress),
                        out pointer,
                        out error))
                        return false;

                    if (pointer < 0x10000)
                    {
                        error =
                            map.Name +
                            " pointer chain 遇到 NULL/無效 pointer。";

                        return false;
                    }
                }

                var finalAddress =
                    (long)pointer +
                    map.Offsets[
                        map.Offsets.Count - 1];

                if (!IsX86Address(
                    finalAddress))
                {
                    error =
                        map.Name +
                        " final address 超出 x86 範圍。";

                    return false;
                }

                address =
                    new IntPtr(finalAddress);

                return true;
            }
            catch (Exception ex)
            {
                error = ex.Message;
                return false;
            }
        }

        private bool TryReadUInt32At(
            IntPtr address,
            out uint value,
            out string error)
        {
            value = 0;
            error = "";

            var buffer = new byte[4];
            IntPtr bytesRead;

            if (!ReadProcessMemory(
                _handle,
                address,
                buffer,
                4,
                out bytesRead) ||
                bytesRead.ToInt64() != 4)
            {
                error =
                    "ReadProcessMemory 失敗 @0x" +
                    address.ToInt64().ToString("X8") +
                    " Win32=" +
                    Marshal.GetLastWin32Error();

                return false;
            }

            value =
                BitConverter.ToUInt32(
                    buffer,
                    0);

            return true;
        }

        private bool TryReadUInt16At(
            IntPtr address,
            out ushort value,
            out string error)
        {
            value = 0;
            error = "";

            var buffer = new byte[2];
            IntPtr bytesRead;

            if (!ReadProcessMemory(
                _handle,
                address,
                buffer,
                2,
                out bytesRead) ||
                bytesRead.ToInt64() != 2)
            {
                error =
                    "ReadProcessMemory 失敗 @0x" +
                    address.ToInt64().ToString("X8") +
                    " Win32=" +
                    Marshal.GetLastWin32Error();

                return false;
            }

            value =
                BitConverter.ToUInt16(
                    buffer,
                    0);

            return true;
        }

        private bool TryReadInt32At(
            IntPtr address,
            out int value,
            out string error)
        {
            value = 0;
            error = "";

            var buffer = new byte[4];
            IntPtr bytesRead;

            if (!ReadProcessMemory(
                _handle,
                address,
                buffer,
                4,
                out bytesRead) ||
                bytesRead.ToInt64() != 4)
            {
                error =
                    "ReadProcessMemory 失敗 @0x" +
                    address.ToInt64().ToString("X8") +
                    " Win32=" +
                    Marshal.GetLastWin32Error();

                return false;
            }

            value =
                BitConverter.ToInt32(
                    buffer,
                    0);

            return true;
        }

        private static bool IsX86Address(
            long address)
        {
            return address >= 0x10000L &&
                   address <= uint.MaxValue;
        }

        public void Dispose()
        {
            if (_handle != IntPtr.Zero)
            {
                CloseHandle(_handle);
                _handle = IntPtr.Zero;
            }

            _moduleBase =
                IntPtr.Zero;
        }
    }
}
