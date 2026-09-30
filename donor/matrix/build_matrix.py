"""Rebuild semantic-only comparison from existing read-only canonical CSVs."""
import csv
import json
from pathlib import Path
from textwrap import dedent

ROOT = Path(__file__).resolve().parent
MAPS = Path('I:/850C-Client-RE/maps')
P381 = 'donor/381/FEATURE_SEMANTICS.md'
P880 = 'donor/880/FEATURE_SEMANTICS.md'


def donor(status, semantics, source):
    return dict(status=status, semantics=semantics, sources=[source])


def facts(selectors):
    result = []
    for source, key_column, key in selectors:
        with (MAPS / (source + '.csv')).open(encoding='utf-8-sig', newline='') as stream:
            matches = [r for r in csv.DictReader(stream) if r[key_column] == key]
        if len(matches) != 1:
            raise ValueError('Expected one canonical fact: ' + key)
        row = matches[0]
        result.append(dict(source=source, key=key, status=row['status'], evidence_ref=row['evidence_ref']))
    return result


def f(key):
    return ('functions', 'name', key)


def e(key):
    return ('entity_resolution', 'kind', key)


def g(key):
    return ('globals', 'name', key)


UNKNOWN381 = donor('UNKNOWN', '既有設定來源沒有直接宣告此執行語意。', P381)
UNKNOWN880 = donor('UNKNOWN', '此次 Java 證據未確認此功能。', P880)


def row(key, d381, d880, selectors, disposition, interfaces):
    contracts = facts(selectors)
    eligible = bool(contracts) and all(c['status'] in ('CONFIRMED', 'STABLE') for c in contracts)
    return dict(feature_id=key, donor_381=d381, donor_880=d880,
                client_850=dict(status='CONFIRMED_BOUNDARY' if eligible else 'UNAVAILABLE',
                                contracts=contracts, availability='METADATA_ONLY' if eligible else 'UNAVAILABLE'),
                reconstruction=dict(disposition=disposition, interfaces=interfaces))


def main():
    rows = [
        row('controller_lifecycle', UNKNOWN381,
            donor('SOURCE_CONFIRMED', 'startAI 提交 Runnable；死亡、離線、生命耗盡或 inactive 終止；正常路徑清目標，例外清理保證未知。', P880 + ': lifecycle_start/stop/cleanup'),
            [f('bot_controller_state_singleton_getter'), g('bot_controller_state_singleton'), f('bot_stop_routine_shim')],
            '純 Python 協調器生命週期；native stop 是 CANDIDATE，維持不可用。', ['BotController', 'Adapter', 'Snapshot']),
        row('target_lookup_id', UNKNOWN381,
            donor('SOURCE_CONFIRMED', '每輪先 checkTarget；無目標才 searchTarget；noTarget/onTarget 分流，內部政策未分析。', P880 + ': target_validation/dispatch'),
            [f('bot_controller_get_target_id'), e('ENTITY_LOOKUP'), e('ENTITY_ID_FIELD')],
            '接受外部明確意圖及目標 snapshot；不建立搜尋或攻擊策略。', ['Adapter', 'Snapshot', 'Intent', 'Policy']),
        row('action_queue', UNKNOWN381, UNKNOWN880, [e('ENTITY_ACTION_QUEUE')],
            '消費已確認 queue 語意 metadata；目前沒有 queue 實作或 native queue binding。', ['BotController', 'Intent']),
        row('item_action', donor('CONFIG_DECLARED', '補水、刪除、溶解、變身、娃娃皆有名稱導向設定；未確認執行器。', P381 + ': potion_threshold/item_delete/item_dissolve/polymorph_maintain/doll_maintain'),
            UNKNOWN880, [f('item_action_execute'), f('item_resolver_singleton_getter')],
            '850 邊界已確認，但目前不在 scaffold allowlist；沒有道具 native binding。', ['Intent', 'Adapter', 'Policy']),
        row('ui_settings', donor('CONFIG_DECLARED', '每角色設定與群組巨集持久化；chat 啟用狀態重登關閉。', P381 + ': chat_rotation/hotkey_groups'),
            donor('SOURCE_CONFIRMED', 'Hang_fu 顯示技能選擇、間隔、魔力量、範圍和開關；縮寫完整語意未知。', P880 + ': configuration_display'),
            [('ui_windows', 'window_name', 'BotWindow'), ('ui_events', 'event_name', 'BotOpenUI')],
            'UI native contracts 全部 CANDIDATE，不提供 callback binding；僅比較配置形狀。', ['Snapshot']),
        row('hpmp_observation', donor('CONFIG_DECLARED', 'HP 低門檻補水支援絕對值或百分比；門檻等號與排程未確認。', P381 + ': potion_threshold'),
            UNKNOWN880, [g('player_hp_current'), g('player_mp_current'), f('hpevent_ctor'), f('mpevent_ctor')],
            'event 邊界確認不等於可讀 HP/MP 合約；direct globals 是 CANDIDATE，保持 unavailable。', ['Snapshot']),
        row('timing_and_pause', donor('CONFIG_DECLARED', '定時命令與聊天輪播有間隔設定；排程漂移未確認。', P381 + ': timed_command/chat_rotation'),
            donor('SOURCE_CONFIRMED', '睡眠或麻痺暫停 AIProcess；主迴圈依速度資料等待。', P880 + ': incapacitation_pause/movement_timing'), [],
            '控制器支援顯式 pause/resume；不移植 donor 時間係數或狀態欄位。', ['BotController']),
        row('auto_hunt_policy', UNKNOWN381,
            donor('SOURCE_CONFIRMED', '存在地圖權限與離起點超範圍處置；未確認選敵攻擊內部算法。', P880 + ': map_permission/origin_radius'), [],
            'DenyPolicy 預設拒絕；不採用地圖、半徑、傳送、攻擊或選怪策略。', ['Policy', 'DenyPolicy']),
        row('buff_and_conversion', donor('CONFIG_DECLARED', 'buff master 與名稱清單；HPMP 轉換只有選單目錄。', P381 + ': buff_maintain/hpmp_conversion'), UNKNOWN880, [],
            'buff 維持與轉換功能 unavailable；不分析施法管理器。', []),
        row('maintenance_actions', donor('CONFIG_DECLARED', '解毒、煉石、變身、娃娃有配置；觸發與完成驗證未確認。', P381 + ': antidote/stone_refine/polymorph_maintain/doll_maintain'), UNKNOWN880, [],
            '保留比較證據；缺乏 confirmed 完整能力，維持 unavailable。', []),
        row('environment_and_display', donor('CONFIG_DECLARED_OR_KEY_ONLY', '全白天、抽水、CPU、吃肉宣告；修理、名稱與浮字等只確認鍵存在。', P381 + ': environment_toggles/display_and_repair_flags'), UNKNOWN880, [],
            '功能鍵不轉成 850 原生能力。', []),
        row('pet_summon_spirit', donor('UNKNOWN', '寵物 HP、召喚物、精靈維持沒有直接宣告。', P381 + ': pet_hp_maintain/summon_maintain/elf_spirit_maintain'), UNKNOWN880, [],
            '資料不足，無法確認；所有動作 unavailable。', []),
    ]

    direct381 = {
        'item_action': ['P:4-7', 'P:37-93'], 'ui_settings': ['P:11-14', 'P:17-36'],
        'hpmp_observation': ['P:4-7', 'M:1-31'], 'timing_and_pause': ['P:11-16'],
        'buff_and_conversion': ['P:8-10', 'M:33-34', 'M:36-218'],
        'maintenance_actions': ['P:86-101', 'M:220-336'],
        'environment_and_display': ['P:102-116'],
        'pet_summon_spirit': ['P:1-116 and M:1-336 (no direct declaration)'],
    }
    direct880 = {
        'controller_lifecycle': ['PcAI:16-25', 'PcAI:53-65', 'PcAI:77-92'],
        'target_lookup_id': ['PcAI:133-158'], 'ui_settings': ['Hang_fu:43-78'],
        'timing_and_pause': ['PcAI:33-46', 'PcAI:176-228'],
        'auto_hunt_policy': ['PcAI:93-124'],
    }
    for r in rows:
        r['donor_381']['direct_source_lines'] = direct381.get(r['feature_id'], [])
        r['donor_880']['direct_source_lines'] = direct880.get(r['feature_id'], [])
        if r['feature_id'] == 'buff_and_conversion':
            r['donor_381']['subfeature_statuses'] = {'buff_maintain': 'CONFIG_DECLARED', 'hpmp_conversion': 'CATALOG_ONLY'}
        if r['feature_id'] == 'environment_and_display':
            r['donor_381']['subfeature_statuses'] = {'environment_toggles': 'CONFIG_DECLARED', 'display_and_repair_flags': 'KEY_PRESENT_ONLY'}
    document = dict(schema_version=1, native_binding=False, canonical_maps=str(MAPS),
                    direct_sources={'P': 'I:/L381/Atu-381客戶端/atum_profiles/冏冏冏.ini',
                                    'M': 'I:/L381/Atu-381客戶端/atum_menu.txt',
                                    'PcAI': 'I:/L880C/880c服務端/src/com/lineage/server/model/Instance/PcAI.java',
                                    'Hang_fu': 'I:/L880C/880c服務端/src/com/lineage/data/item_etcitem/teleport/Hang_fu.java'}, rows=rows)
    (ROOT / 'feature_matrix.json').write_text(json.dumps(document, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    header = dedent('''\
    # 381 / 880 / 850 功能矩陣

    381 是文字設定宣告，880 是服務端 Java 語意，850 是既有 maps 的契約狀態；三者的證據層級分開列出。METADATA_ONLY 只代表可閱讀已確認語意，所有 native 呼叫均未綁定。UNAVAILABLE 表示候選、未知、缺乏完整能力或本 scaffold 尚未暴露該能力。

    來源：[381 證據](../381/FEATURE_SEMANTICS.md)、[880 證據](../880/FEATURE_SEMANTICS.md)、唯讀 `I:/850C-Client-RE/maps/`。JSON 保留逐個 canonical key 與 evidence_ref，可由 validator 比對；不含位址、opcode 或 ABI。

    | 功能 | 381 宣告 | 880 Java 語意 | 850 狀態 | 重建處置 |
    |---|---|---|---|---|
    ''')
    lines = [header]
    for r in rows:
        contracts = '; '.join(c['key'] + '=' + c['status'] for c in r['client_850']['contracts']) or 'UNKNOWN / 無所需合約'
        lines.append('| ' + ' | '.join([r['feature_id'], r['donor_381']['semantics'], r['donor_880']['semantics'], contracts, r['reconstruction']['disposition']]) + ' |\n')
    lines.append('\n重建介面採現有 `BotController`、`Adapter.observe/perform`、`Snapshot`、`Intent`、`Policy` 與 `DenyPolicy`。controller 狀態是平台中立協調語意，不是 850 原生狀態機還原。候選值不升級；donor 預設設定不複製為使用者偏好。\n')
    (ROOT / 'FEATURE_MATRIX.md').write_text(''.join(lines), encoding='utf-8')


if __name__ == "__main__":
    main()
