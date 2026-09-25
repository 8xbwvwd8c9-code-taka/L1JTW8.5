package auto.hunt;

import java.util.List;

public final class AutoHuntTargetPolicy {
    private final int targetRange;
    private final int engageRange;

    public AutoHuntTargetPolicy(int targetRange, int engageRange) {
        if (targetRange < 1) throw new IllegalArgumentException("targetRange");
        if (engageRange < 0) throw new IllegalArgumentException("engageRange");
        this.targetRange = targetRange;
        this.engageRange = engageRange;
    }

    public Candidate select(List<Candidate> candidates, boolean treatBossAsNormal, int patrolRadius) {
        Candidate best = null;
        for (Candidate candidate : candidates) {
            if (!isSelectable(candidate, treatBossAsNormal, patrolRadius)) continue;
            if (best == null
                    || candidate.distance < best.distance
                    || candidate.distance == best.distance && candidate.anchorDistance < best.anchorDistance
                    || candidate.distance == best.distance && candidate.anchorDistance == best.anchorDistance
                        && candidate.objId < best.objId) {
                best = candidate;
            }
        }
        return best;
    }

    public boolean isSelectable(Candidate c, boolean treatBossAsNormal, int patrolRadius) {
        if (c == null) return false;
        if (c.boss && !treatBossAsNormal) return false;
        if (c.excludedNpc || c.blockedEffect) return false;
        if (c.dead || c.hp <= 0 || c.hidden || c.attackSpeed == 0) return false;
        if (!c.reachable) return false;
        if (c.distance < 0 || c.distance > targetRange) return false;
        if (patrolRadius > 0) {
            int overflow = Math.max(0, c.anchorDistance - patrolRadius);
            if (overflow > engageRange) return false;
        }
        return true;
    }

    public static final class Candidate {
        public final int objId;
        public final boolean boss;
        public final boolean excludedNpc;
        public final boolean blockedEffect;
        public final boolean dead;
        public final int hp;
        public final boolean hidden;
        public final int attackSpeed;
        public final boolean reachable;
        public final int distance;
        public final int anchorDistance;

        public Candidate(int objId, boolean boss, boolean excludedNpc, boolean blockedEffect,
                         boolean dead, int hp, boolean hidden, int attackSpeed,
                         boolean reachable, int distance, int anchorDistance) {
            this.objId = objId;
            this.boss = boss;
            this.excludedNpc = excludedNpc;
            this.blockedEffect = blockedEffect;
            this.dead = dead;
            this.hp = hp;
            this.hidden = hidden;
            this.attackSpeed = attackSpeed;
            this.reachable = reachable;
            this.distance = distance;
            this.anchorDistance = anchorDistance;
        }
    }
}
