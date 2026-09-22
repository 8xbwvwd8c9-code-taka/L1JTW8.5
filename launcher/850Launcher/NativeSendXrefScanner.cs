using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class NativeImportXref
    {
        public string ImportName = "";
        public uint IatRva;
        public IntPtr InstructionAddress;
        public uint InstructionRva;
        public string Kind = "";
    }

    internal static class NativeSendXrefScanner
    {
        public static List<NativeImportXref> Scan(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            PeImageInfo image,
            out string status)
        {
            var output = new List<NativeImportXref>();
            status = "";

            if (probe == null || runtime == null || image == null)
            {
                status = "輸入資料不足。";
                return output;
            }

            var imports = new List<PeImportEntry>();
            foreach (var entry in image.Imports)
            {
                if (IsNetworkSendImport(entry))
                    imports.Add(entry);
            }

            if (imports.Count == 0)
            {
                status = "Lin.bin2 import table 未找到 send/WSASend 類匯入。";
                return output;
            }

            foreach (var section in image.Sections)
            {
                if (!section.IsExecutable)
                    continue;

                var size = (int)Math.Min(
                    (long)Math.Max(section.VirtualSize, section.RawSize),
                    32L * 1024L * 1024L);

                if (size <= 0)
                    continue;

                byte[] bytes;
                string error;
                var start = new IntPtr(
                    runtime.ModuleBase.ToInt64() +
                    section.VirtualAddress);

                if (!probe.TryReadBytes(start, size, out bytes, out error))
                    continue;

                foreach (var entry in imports)
                {
                    var iatVa =
                        unchecked((uint)(
                            runtime.ModuleBase.ToInt64() +
                            entry.IatRva));

                    ScanForIatXrefs(
                        bytes,
                        start,
                        runtime.ModuleBase,
                        entry,
                        iatVa,
                        output);
                }
            }

            output.Sort(delegate(NativeImportXref a, NativeImportXref b)
            {
                var byImport = string.Compare(
                    a.ImportName,
                    b.ImportName,
                    StringComparison.OrdinalIgnoreCase);

                if (byImport != 0)
                    return byImport;

                return a.InstructionRva.CompareTo(b.InstructionRva);
            });

            status =
                "network send import=" + imports.Count +
                "，IAT call/jmp xref=" + output.Count + "。";

            return output;
        }

        private static void ScanForIatXrefs(
            byte[] bytes,
            IntPtr sectionStart,
            IntPtr moduleBase,
            PeImportEntry entry,
            uint iatVa,
            List<NativeImportXref> output)
        {
            var b0 = (byte)(iatVa & 0xFF);
            var b1 = (byte)((iatVa >> 8) & 0xFF);
            var b2 = (byte)((iatVa >> 16) & 0xFF);
            var b3 = (byte)((iatVa >> 24) & 0xFF);

            for (var i = 0; i <= bytes.Length - 6; i++)
            {
                if (bytes[i] != 0xFF)
                    continue;

                if (bytes[i + 1] != 0x15 &&
                    bytes[i + 1] != 0x25)
                    continue;

                if (bytes[i + 2] != b0 ||
                    bytes[i + 3] != b1 ||
                    bytes[i + 4] != b2 ||
                    bytes[i + 5] != b3)
                    continue;

                var address =
                    sectionStart.ToInt64() +
                    i;

                var rva =
                    address -
                    moduleBase.ToInt64();

                if (rva < 0 || rva > uint.MaxValue)
                    continue;

                output.Add(new NativeImportXref
                {
                    ImportName =
                        entry.Dll + "!" + entry.Name,
                    IatRva = entry.IatRva,
                    InstructionAddress = new IntPtr(address),
                    InstructionRva = (uint)rva,
                    Kind = bytes[i + 1] == 0x15
                        ? "CALL [IAT]"
                        : "JMP [IAT]"
                });
            }
        }

        private static bool IsNetworkSendImport(PeImportEntry entry)
        {
            if (entry == null || entry.ByOrdinal)
                return false;

            var name = entry.Name ?? "";

            return name.Equals("send", StringComparison.OrdinalIgnoreCase) ||
                   name.Equals("sendto", StringComparison.OrdinalIgnoreCase) ||
                   name.Equals("WSASend", StringComparison.OrdinalIgnoreCase) ||
                   name.Equals("WSASendTo", StringComparison.OrdinalIgnoreCase);
        }
    }
}
