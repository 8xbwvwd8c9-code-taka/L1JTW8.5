using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class PeImportEntry
    {
        public string Dll = "";
        public string Name = "";
        public uint IatRva;
        public bool ByOrdinal;
        public ushort Ordinal;
    }

    internal sealed class PeSectionInfo
    {
        public string Name = "";
        public uint VirtualAddress;
        public uint VirtualSize;
        public uint RawAddress;
        public uint RawSize;
        public uint Characteristics;

        public bool IsExecutable
        {
            get { return (Characteristics & 0x20000000U) != 0; }
        }
    }

    internal sealed class PeImageInfo
    {
        public uint ImageBase;
        public uint SizeOfImage;
        public readonly List<PeSectionInfo> Sections = new List<PeSectionInfo>();
        public readonly List<PeImportEntry> Imports = new List<PeImportEntry>();
    }

    internal static class PeImportParser
    {
        public static PeImageInfo Parse(string path)
        {
            var data = File.ReadAllBytes(path);

            if (data.Length < 0x100)
                throw new InvalidDataException("PE 檔案太小。");

            if (ReadU16(data, 0) != 0x5A4D)
                throw new InvalidDataException("不是 MZ/PE 檔案。");

            var pe = (int)ReadU32(data, 0x3C);
            if (pe < 0 || pe + 0x100 > data.Length)
                throw new InvalidDataException("PE header offset 無效。");

            if (ReadU32(data, pe) != 0x00004550)
                throw new InvalidDataException("PE signature 不正確。");

            var fileHeader = pe + 4;
            var sectionCount = ReadU16(data, fileHeader + 2);
            var optionalSize = ReadU16(data, fileHeader + 16);
            var optional = fileHeader + 20;

            if (optional + optionalSize > data.Length)
                throw new InvalidDataException("Optional header 超出檔案。");

            var magic = ReadU16(data, optional);
            if (magic != 0x10B)
                throw new InvalidDataException("只支援 PE32/x86。");

            var image = new PeImageInfo
            {
                ImageBase = ReadU32(data, optional + 28),
                SizeOfImage = ReadU32(data, optional + 56)
            };

            var sectionTable = optional + optionalSize;
            for (var i = 0; i < sectionCount; i++)
            {
                var off = sectionTable + (i * 40);
                if (off + 40 > data.Length)
                    break;

                image.Sections.Add(new PeSectionInfo
                {
                    Name = ReadAsciiZ(data, off, 8),
                    VirtualSize = ReadU32(data, off + 8),
                    VirtualAddress = ReadU32(data, off + 12),
                    RawSize = ReadU32(data, off + 16),
                    RawAddress = ReadU32(data, off + 20),
                    Characteristics = ReadU32(data, off + 36)
                });
            }

            // PE32 data directory #1 = Import Table, optional + 96 + 8.
            var importRva = ReadU32(data, optional + 104);
            if (importRva == 0)
                return image;

            var importOffset = RvaToOffset(importRva, image.Sections, data.Length);
            if (importOffset < 0)
                throw new InvalidDataException("Import directory RVA 無法轉成檔案 offset。");

            for (var descriptorIndex = 0; descriptorIndex < 4096; descriptorIndex++)
            {
                var desc = importOffset + (descriptorIndex * 20);
                if (desc + 20 > data.Length)
                    break;

                var originalFirstThunk = ReadU32(data, desc);
                var nameRva = ReadU32(data, desc + 12);
                var firstThunk = ReadU32(data, desc + 16);

                if (originalFirstThunk == 0 && nameRva == 0 && firstThunk == 0)
                    break;

                var nameOffset = RvaToOffset(nameRva, image.Sections, data.Length);
                var dll = nameOffset >= 0 ? ReadAsciiZ(data, nameOffset, 260) : "";

                var lookupRva = originalFirstThunk != 0 ? originalFirstThunk : firstThunk;
                var lookupOffset = RvaToOffset(lookupRva, image.Sections, data.Length);
                if (lookupOffset < 0)
                    continue;

                for (var thunkIndex = 0; thunkIndex < 65536; thunkIndex++)
                {
                    var thunkOffset = lookupOffset + (thunkIndex * 4);
                    if (thunkOffset + 4 > data.Length)
                        break;

                    var thunk = ReadU32(data, thunkOffset);
                    if (thunk == 0)
                        break;

                    var entry = new PeImportEntry
                    {
                        Dll = dll,
                        IatRva = firstThunk + (uint)(thunkIndex * 4)
                    };

                    if ((thunk & 0x80000000U) != 0)
                    {
                        entry.ByOrdinal = true;
                        entry.Ordinal = (ushort)(thunk & 0xFFFF);
                        entry.Name = "#" + entry.Ordinal;
                    }
                    else
                    {
                        var ibnOffset = RvaToOffset(thunk, image.Sections, data.Length);
                        if (ibnOffset < 0 || ibnOffset + 2 >= data.Length)
                            continue;

                        entry.Name = ReadAsciiZ(data, ibnOffset + 2, 260);
                    }

                    image.Imports.Add(entry);
                }
            }

            return image;
        }

        private static int RvaToOffset(
            uint rva,
            IList<PeSectionInfo> sections,
            int fileLength)
        {
            foreach (var section in sections)
            {
                var span = Math.Max(section.VirtualSize, section.RawSize);
                if (rva < section.VirtualAddress ||
                    rva >= section.VirtualAddress + span)
                    continue;

                var delta = rva - section.VirtualAddress;
                var offset = (long)section.RawAddress + delta;

                if (offset < 0 || offset >= fileLength)
                    return -1;

                return (int)offset;
            }

            // Header RVAs may map directly.
            if (rva < (uint)fileLength)
                return (int)rva;

            return -1;
        }

        private static ushort ReadU16(byte[] data, int offset)
        {
            if (offset < 0 || offset + 2 > data.Length)
                throw new InvalidDataException("PE read16 out of range.");

            return BitConverter.ToUInt16(data, offset);
        }

        private static uint ReadU32(byte[] data, int offset)
        {
            if (offset < 0 || offset + 4 > data.Length)
                throw new InvalidDataException("PE read32 out of range.");

            return BitConverter.ToUInt32(data, offset);
        }

        private static string ReadAsciiZ(byte[] data, int offset, int maxLength)
        {
            if (offset < 0 || offset >= data.Length)
                return "";

            var end = offset;
            var max = Math.Min(data.Length, offset + maxLength);

            while (end < max && data[end] != 0)
                end++;

            return Encoding.ASCII.GetString(data, offset, end - offset);
        }
    }
}
