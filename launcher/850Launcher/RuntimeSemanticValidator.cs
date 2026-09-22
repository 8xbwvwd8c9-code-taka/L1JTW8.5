using System;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSemanticExpectation
    {
        public bool CheckHpMp;
        public int CurrentHp;
        public int MaxHp;
        public int CurrentMp;
        public int MaxMp;

        public bool CheckPlayer;
        public uint PlayerObjectId;
        public ushort PlayerX;
        public ushort PlayerY;
    }

    internal sealed class RuntimeSemanticValidationResult
    {
        public bool Connected;
        public bool HpMpMapped;
        public bool PlayerMapped;
        public bool HpMpPass;
        public bool PlayerPass;
        public string HpMpStatus = "";
        public string PlayerStatus = "";
    }

    internal static class RuntimeSemanticValidator
    {
        public static RuntimeSemanticValidationResult Validate(
            RuntimeSnapshot runtime,
            RuntimeSemanticExpectation expected)
        {
            var result =
                new RuntimeSemanticValidationResult();

            if (runtime == null ||
                !runtime.Connected)
            {
                result.HpMpStatus =
                    "Lin.bin2 尚未連接。";

                result.PlayerStatus =
                    "Lin.bin2 尚未連接。";

                return result;
            }

            result.Connected = true;

            if (expected.CheckHpMp)
            {
                result.HpMpMapped =
                    runtime.CurrentHp.HasValue &&
                    runtime.MaxHp.HasValue &&
                    runtime.CurrentMp.HasValue &&
                    runtime.MaxMp.HasValue;

                if (!result.HpMpMapped)
                {
                    result.HpMpStatus =
                        "HP/MP mapping 尚未解析出完整數值。";
                }
                else
                {
                    result.HpMpPass =
                        runtime.CurrentHp.Value ==
                            expected.CurrentHp &&
                        runtime.MaxHp.Value ==
                            expected.MaxHp &&
                        runtime.CurrentMp.Value ==
                            expected.CurrentMp &&
                        runtime.MaxMp.Value ==
                            expected.MaxMp;

                    result.HpMpStatus =
                        result.HpMpPass
                            ? "HP/MP 語意驗證 PASS。"
                            : "HP/MP 實際讀值與輸入真值不符。";
                }
            }
            else
            {
                result.HpMpStatus =
                    "本次未驗證 HP/MP。";
            }

            if (expected.CheckPlayer)
            {
                result.PlayerMapped =
                    runtime.PlayerObjectId.HasValue &&
                    runtime.PlayerX.HasValue &&
                    runtime.PlayerY.HasValue;

                if (!result.PlayerMapped)
                {
                    result.PlayerStatus =
                        "Player mapping 尚未解析出 objectId/X/Y。";
                }
                else
                {
                    result.PlayerPass =
                        runtime.PlayerObjectId.Value ==
                            expected.PlayerObjectId &&
                        runtime.PlayerX.Value ==
                            expected.PlayerX &&
                        runtime.PlayerY.Value ==
                            expected.PlayerY;

                    result.PlayerStatus =
                        result.PlayerPass
                            ? "Player objectId/X/Y 語意驗證 PASS。"
                            : "Player 實際讀值與輸入真值不符。";
                }
            }
            else
            {
                result.PlayerStatus =
                    "本次未驗證 Player。";
            }

            return result;
        }
    }
}
