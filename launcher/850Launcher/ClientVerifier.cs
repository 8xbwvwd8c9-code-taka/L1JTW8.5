using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class ClientVerifier
    {
        internal const string LinBin2Sha256 =
            "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4";

        public static string ComputeSha256(string path)
        {
            using (var sha = SHA256.Create())
            using (var stream = File.OpenRead(path))
            {
                var hash = sha.ComputeHash(stream);
                var sb = new StringBuilder(hash.Length * 2);
                foreach (var b in hash) sb.Append(b.ToString("X2"));
                return sb.ToString();
            }
        }

        public static bool IsAuthoritativeLinBin2(string path, out string actual)
        {
            actual = ComputeSha256(path);
            return string.Equals(actual, LinBin2Sha256, StringComparison.OrdinalIgnoreCase);
        }
    }
}
