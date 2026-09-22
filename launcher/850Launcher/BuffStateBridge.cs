using System;
using System.Collections.Generic;

namespace L1JTW850Launcher
{
    internal sealed class BuffStateSnapshot
    {
        public bool Mapped;
        public string Status = "UNMAPPED";
        public readonly HashSet<int> ActiveSkillIds =
            new HashSet<int>();
    }

    internal interface IBuffStateBridge
    {
        bool IsMapped { get; }
        BuffStateSnapshot Read(RuntimeSnapshot runtime);
    }

    internal sealed class UnmappedBuffStateBridge :
        IBuffStateBridge
    {
        public bool IsMapped
        {
            get { return false; }
        }

        public BuffStateSnapshot Read(
            RuntimeSnapshot runtime)
        {
            return new BuffStateSnapshot
            {
                Mapped = false,
                Status =
                    "WP9 尚未完成：850 Buff 狀態來源未證明。"
            };
        }
    }

    internal sealed class AutoBuffTickResult
    {
        public bool Ready;
        public bool ActionAttempted;
        public bool ActionSucceeded;
        public int SkillId;
        public string Status = "";
    }

    internal sealed class AutoBuffController
    {
        private readonly HelperSettings _settings;
        private readonly SkillCatalog _catalog;
        private readonly ISkillUseBridge _skillUse;
        private readonly IBuffStateBridge _buffState;
        private readonly Dictionary<int, DateTime> _lastAttemptUtc =
            new Dictionary<int, DateTime>();

        public AutoBuffController(
            HelperSettings settings,
            SkillCatalog catalog,
            ISkillUseBridge skillUse,
            IBuffStateBridge buffState)
        {
            _settings = settings;
            _catalog = catalog;
            _skillUse = skillUse;
            _buffState = buffState;
        }

        public AutoBuffTickResult Tick(
            RuntimeSnapshot runtime)
        {
            var result = new AutoBuffTickResult();

            if (_settings == null ||
                !_settings.AutoBuff)
            {
                result.Status = "自動狀態未啟用。";
                return result;
            }

            var selected =
                _settings.GetBuffSkillIds();

            if (selected.Count == 0)
            {
                result.Status =
                    "尚未選擇要維持的技能。";
                return result;
            }

            if (_buffState == null ||
                !_buffState.IsMapped)
            {
                result.Status =
                    "已選擇 " + selected.Count +
                    " 個技能；Buff 狀態來源尚未驗證，因此不自動施放。";
                return result;
            }

            if (_skillUse == null ||
                !_skillUse.IsMapped)
            {
                result.Status =
                    "Buff 狀態已可讀，但 WP9 native SkillUse bridge 尚未驗證。";
                return result;
            }

            var state = _buffState.Read(runtime);
            if (state == null ||
                !state.Mapped)
            {
                result.Status =
                    state == null
                        ? "Buff 狀態讀取失敗。"
                        : state.Status;
                return result;
            }

            if (!runtime.PlayerObjectId.HasValue ||
                !runtime.PlayerX.HasValue ||
                !runtime.PlayerY.HasValue)
            {
                result.Status =
                    "Buff/SkillUse 已映射，但玩家 objectId/X/Y 尚未完成 WP3 驗證。";
                return result;
            }

            result.Ready = true;

            foreach (var skillId in selected)
            {
                if (state.ActiveSkillIds.Contains(
                    skillId))
                    continue;

                var entry =
                    _catalog.Get(skillId);

                if (entry == null)
                    continue;

                DateTime last;
                if (_lastAttemptUtc.TryGetValue(
                    skillId,
                    out last))
                {
                    var delay = entry.ReuseDelay;
                    if (delay < 500)
                        delay = 500;

                    if ((DateTime.UtcNow - last)
                        .TotalMilliseconds < delay)
                        continue;
                }

                result.SkillId = skillId;
                result.ActionAttempted = true;
                _lastAttemptUtc[skillId] =
                    DateTime.UtcNow;

                var use =
                    _skillUse.UseGeneral(
                        skillId,
                        runtime.PlayerObjectId.Value,
                        runtime.PlayerX.Value,
                        runtime.PlayerY.Value);

                result.ActionSucceeded =
                    use.Success;

                result.Status =
                    use.Success
                        ? "已施放：" +
                          _catalog.Resolve(skillId)
                        : use.Status;

                return result;
            }

            result.Status =
                "選定 Buff 目前皆為有效狀態。";

            return result;
        }
    }
}
