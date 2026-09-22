using System;
using System.Security.Cryptography;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class NativeCodeFingerprint
    {
        public uint Rva;
        public string Sha256 = "";
        public string Hex = "";
        public int BytesRead;
    }

    internal static class NativeCodeWindow
    {
        public static NativeCodeFingerprint Read(
            RuntimeMemoryProbe probe,
            RuntimeSnapshot runtime,
            uint rva,
            int size)
        {
            var result = new NativeCodeFingerprint
            {
                Rva = rva
            };

            if (probe == null ||
                runtime == null ||
                runtime.ModuleBase == IntPtr.Zero)
                return result;

            if (size < 1) size = 1;
            if (size > 4096) size = 4096;

            byte[] bytes;
            string error;

            var address = new IntPtr(
                runtime.ModuleBase.ToInt64() +
                rva);

            if (!probe.TryReadBytes(
                address,
                size,
                out bytes,
                out error))
                return result;

            result.BytesRead = bytes.Length;
            result.Hex = ToHex(bytes);

            using (var sha = SHA256.Create())
            {
                result.Sha256 =
                    ToCompactHex(
                        sha.ComputeHash(bytes));
            }

            return result;
        }

        private static string ToHex(
            byte[] data)
        {
            var sb = new StringBuilder(
                data.Length * 3);

            for (var i = 0; i < data.Length; i++)
            {
                if (i > 0)
                    sb.Append(' ');

                sb.Append(
                    data[i].ToString("X2"));
            }

            return sb.ToString();
        }

        private static string ToCompactHex(
            byte[] data)
        {
            var sb = new StringBuilder(
                data.Length * 2);

            foreach (var b in data)
                sb.Append(b.ToString("X2"));

            return sb.ToString();
        }
    }
}
