package auto.hunt;

import ap.s;
import ap.u;

public interface AutoHuntTargetSelector {
    s select(u pc,
             boolean treatBossAsNormal,
             boolean avoidOccupied,
             int patrolX,
             int patrolY,
             int patrolRadius,
             int engageRange);
}
