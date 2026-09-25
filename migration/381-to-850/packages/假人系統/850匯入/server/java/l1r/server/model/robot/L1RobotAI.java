package l1r.server.model.robot;

import java.util.Random;
import java.util.logging.Logger;

/**
 * 850 原生單一融合假人 AI 決策大腦
 * 雙核驅動：平時執行野外尋怪、階梯喝水、狂奔逃跑、擺攤回補；攻城時切換至破門倒塔摸冠。
 */
public class L1RobotAI implements Runnable {
    private static final Logger _log = Logger.getLogger(L1RobotAI.class.getName());

    public enum RobotMode {
        FIELD_HUNTING,      // 野外打怪
        TOWN_SHOPPING,      // 村莊擺攤
        TOWN_IDLE,          // 村莊發呆逛街
        TOWN_RESUPPLYING,   // 回村補給中
        SIEGE_WAR           // 定時攻城戰模式
    }

    public enum SiegeStage {
        NONE,
        ATTACK_OUTER_GATE,  // 攻擊外門
        ATTACK_INNER_GATE,  // 攻擊內門
        ATTACK_TOWER,       // 攻擊守護塔
        CROWN_CAPTURE       // 王族奪取皇冠
    }

    private RobotMode _currentMode = RobotMode.FIELD_HUNTING;
    private SiegeStage _siegeStage = SiegeStage.NONE;
    private final Random _random = new Random();
    private boolean _isActive = true;

    // 戰鬥與生存參數
    private double _hpRatio = 1.0;
    private long _lastPotionTime = 0;
    private int _fleeStep = 0;

    public L1RobotAI() {
    }

    @Override
    public void run() {
        while (_isActive) {
            try {
                switch (_currentMode) {
                    case FIELD_HUNTING:
                        actionFieldHunting();
                        break;
                    case SIEGE_WAR:
                        actionSiegeWar();
                        break;
                    case TOWN_SHOPPING:
                    case TOWN_IDLE:
                    case TOWN_RESUPPLYING:
                        actionTown();
                        break;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                _isActive = false;
                break;
            } catch (Exception e) {
                // 防崩潰保護
            }
        }
    }

    private void actionFieldHunting() {
        // 1. 階梯喝水判定 (紅水 90%, 橘水 70%, 白水 40%)
        checkPotion();

        // 2. 殘血逃生狂奔判定 (<25% 狂奔數步後回卷)
        if (_hpRatio < 0.25) {
            executeFleeAndTeleport();
            return;
        }

        // 3. 尋怪與打帶跑/卡刀走位
    }

    private void actionSiegeWar() {
        switch (_siegeStage) {
            case ATTACK_OUTER_GATE:
                // 集火外城門
                break;
            case ATTACK_INNER_GATE:
                // 挺進集火內城門
                break;
            case ATTACK_TOWER:
                // 圍攻守護塔
                break;
            case CROWN_CAPTURE:
                // 君主前進觸碰皇冠
                break;
            case NONE:
                break;
        }
    }

    private void actionTown() {
        // 擺攤或回村補給休息
    }

    private void checkPotion() {
        // 階梯式自動喝水邏輯
    }

    private void executeFleeAndTeleport() {
        // 轉身狂奔數步 + 連續喝水 + 按下回卷
    }

    public void switchMode(RobotMode mode) {
        _currentMode = mode;
    }

    public void setSiegeStage(SiegeStage stage) {
        _siegeStage = stage;
    }
}