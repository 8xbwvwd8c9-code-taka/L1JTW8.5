using System;

namespace L1JTW850Launcher
{
    internal static class SkillUseProtocol
    {
        // 850 recovered server PacketHandler:
        // opcode 128 (0x80) -> C_UseSkill.
        public const byte LogicalOpcode = 128;

        public static void GetRowColumn(
            int skillId,
            out byte row,
            out byte column)
        {
            if (skillId <= 0)
                throw new ArgumentOutOfRangeException("skillId");

            var zeroBased = skillId - 1;
            var rowValue = zeroBased / 8;
            var columnValue = zeroBased % 8;

            if (rowValue > byte.MaxValue)
                throw new ArgumentOutOfRangeException(
                    "skillId",
                    "skillId 超出 850 row/column byte 可表示範圍。");

            row = (byte)rowValue;
            column = (byte)columnValue;
        }

        public static byte[] BuildGeneralLogicalPayload(
            int skillId,
            uint targetId,
            ushort targetX,
            ushort targetY)
        {
            byte row;
            byte column;
            GetRowColumn(
                skillId,
                out row,
                out column);

            var packet = new byte[11];

            packet[0] = LogicalOpcode;
            packet[1] = row;
            packet[2] = column;

            WriteUInt32LE(
                packet,
                3,
                targetId);

            WriteUInt16LE(
                packet,
                7,
                targetX);

            WriteUInt16LE(
                packet,
                9,
                targetY);

            return packet;
        }

        public static byte[] BuildXyOnlyLogicalPayload(
            int skillId,
            ushort targetX,
            ushort targetY)
        {
            if (skillId != 58 &&
                skillId != 63)
            {
                throw new ArgumentException(
                    "850 C_UseSkill 的 XY-only 分支目前只證明 skillId 58/63。",
                    "skillId");
            }

            byte row;
            byte column;
            GetRowColumn(
                skillId,
                out row,
                out column);

            var packet = new byte[7];

            packet[0] = LogicalOpcode;
            packet[1] = row;
            packet[2] = column;

            WriteUInt16LE(
                packet,
                3,
                targetX);

            WriteUInt16LE(
                packet,
                5,
                targetY);

            return packet;
        }

        public static byte[] BuildBookmarkLogicalPayload(
            int skillId,
            ushort mapId,
            ushort x,
            ushort y)
        {
            if (skillId != 5 &&
                skillId != 69)
            {
                throw new ArgumentException(
                    "850 C_UseSkill 的 bookmark 分支目前只證明 skillId 5/69。",
                    "skillId");
            }

            byte row;
            byte column;
            GetRowColumn(
                skillId,
                out row,
                out column);

            var packet = new byte[9];

            packet[0] = LogicalOpcode;
            packet[1] = row;
            packet[2] = column;

            WriteUInt16LE(
                packet,
                3,
                mapId);

            WriteUInt16LE(
                packet,
                5,
                x);

            WriteUInt16LE(
                packet,
                7,
                y);

            return packet;
        }

        public static bool IsMessageSkill(
            int skillId)
        {
            return skillId == 116 ||
                   skillId == 118;
        }

        private static void WriteUInt16LE(
            byte[] target,
            int offset,
            ushort value)
        {
            target[offset] =
                (byte)(value & 0xFF);

            target[offset + 1] =
                (byte)((value >> 8) & 0xFF);
        }

        private static void WriteUInt32LE(
            byte[] target,
            int offset,
            uint value)
        {
            target[offset] =
                (byte)(value & 0xFF);

            target[offset + 1] =
                (byte)((value >> 8) & 0xFF);

            target[offset + 2] =
                (byte)((value >> 16) & 0xFF);

            target[offset + 3] =
                (byte)((value >> 24) & 0xFF);
        }
    }

    internal sealed class SkillUseResult
    {
        public bool Success;
        public string Status = "";
    }

    internal interface ISkillUseBridge
    {
        bool IsMapped { get; }

        SkillUseResult UseGeneral(
            int skillId,
            uint targetId,
            ushort targetX,
            ushort targetY);
    }

    internal sealed class UnmappedSkillUseBridge :
        ISkillUseBridge
    {
        public bool IsMapped
        {
            get { return false; }
        }

        public SkillUseResult UseGeneral(
            int skillId,
            uint targetId,
            ushort targetX,
            ushort targetY)
        {
            return new SkillUseResult
            {
                Success = false,
                Status =
                    "WP9 尚未完成：850 native skill-use path 未證明。"
            };
        }
    }
}
