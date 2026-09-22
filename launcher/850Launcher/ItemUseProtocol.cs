using System;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class ItemUseProtocol
    {
        // Recovered 850 server PacketHandler:
        // opcode 94 (0x5E) -> C_ItemUSe.
        public const byte LogicalOpcode = 94;

        public static byte[] BuildNormalItemLogicalPayload(uint objectId)
        {
            var packet = new byte[5];
            packet[0] = LogicalOpcode;
            packet[1] = (byte)(objectId & 0xFF);
            packet[2] = (byte)((objectId >> 8) & 0xFF);
            packet[3] = (byte)((objectId >> 16) & 0xFF);
            packet[4] = (byte)((objectId >> 24) & 0xFF);
            return packet;
        }

        public static string ToHex(byte[] data)
        {
            if (data == null || data.Length == 0)
                return "";

            var sb = new StringBuilder(data.Length * 3);
            for (var i = 0; i < data.Length; i++)
            {
                if (i > 0) sb.Append(' ');
                sb.Append(data[i].ToString("X2"));
            }
            return sb.ToString();
        }

        public static bool TryParseObjectId(string text, out uint objectId)
        {
            objectId = 0;
            if (string.IsNullOrWhiteSpace(text))
                return false;

            text = text.Trim();

            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
                return uint.TryParse(
                    text.Substring(2),
                    System.Globalization.NumberStyles.HexNumber,
                    System.Globalization.CultureInfo.InvariantCulture,
                    out objectId);

            return uint.TryParse(text, out objectId);
        }
    }
}
