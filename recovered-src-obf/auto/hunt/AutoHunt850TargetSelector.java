package auto.hunt;

import ap.AutoHuntTargetBridge;
import ap.s;
import ap.u;
import aq.aa;
import aq.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AutoHunt850TargetSelector {
    private static final int TARGET_RANGE = 10;
    private final AutoHuntBossResolver bossResolver;

    public AutoHunt850TargetSelector(AutoHuntBossResolver bossResolver) {
        if (bossResolver == null) throw new NullPointerException("bossResolver");
        this.bossResolver = bossResolver;
    }

    public s select(u pc, boolean treatBossAsNormal, boolean avoidOccupied,
                    int patrolX, int patrolY, int patrolRadius, int engageRange) {
        if (pc == null) return null;
        AutoHuntTargetPolicy policy = new AutoHuntTargetPolicy(TARGET_RANGE, engageRange);
        List<AutoHuntTargetPolicy.Candidate> candidates = new ArrayList<AutoHuntTargetPolicy.Candidate>();
        Map<Integer, s> monsters = new HashMap<Integer, s>();

        for (aa object : aq.aq.a().b((aa)pc, TARGET_RANGE)) {
            if (!(object instanceof s)) continue;
            s mob = (s)object;
            int npcId = mob.U_().b();
            boolean blockedEffect = mob.bB(33) || mob.bB(50) || mob.bB(1011) || mob.bB(1009);
            boolean unreachableMarker = mob.bB(pc.fr() + 100000);
            boolean meleeReachable = pc.c(mob.fs(), mob.ft(), 1);

            f currentTarget = AutoHuntTargetBridge.currentTarget(mob);
            boolean occupiedByOther = currentTarget != null
                    && currentTarget != pc
                    && mob.c(currentTarget.fs(), currentTarget.ft(), mob.U_().s());

            int distance = pc.fu().c(mob.fu());
            int anchorDistance = patrolRadius > 0
                    ? Math.max(Math.abs(mob.fs() - patrolX), Math.abs(mob.ft() - patrolY))
                    : 0;

            AutoHuntTargetPolicy.Candidate candidate = new AutoHuntTargetPolicy.Candidate(
                    mob.fr(),
                    bossResolver.isBoss(npcId),
                    npcId == 81257,
                    blockedEffect,
                    mob.eX(),
                    mob.ea(),
                    mob.ac() > 0,
                    mob.O(),
                    unreachableMarker,
                    meleeReachable,
                    occupiedByOther,
                    distance,
                    anchorDistance);
            candidates.add(candidate);
            monsters.put(mob.fr(), mob);
        }

        AutoHuntTargetPolicy.Candidate selected = policy.select(
                candidates, treatBossAsNormal, avoidOccupied, patrolRadius);
        return selected == null ? null : monsters.get(selected.objId);
    }
}
