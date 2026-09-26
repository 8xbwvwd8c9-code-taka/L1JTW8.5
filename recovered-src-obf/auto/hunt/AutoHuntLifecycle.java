package auto.hunt;

/**
 * Small synchronized lifecycle state machine for one player's auto-hunt session.
 * It deliberately has no game-core dependencies so its stale-work invariants can
 * be regression-tested independently from the recovered 850 classes.
 */
public final class AutoHuntLifecycle {
    private long sessionGeneration;
    private long actionGeneration;
    private boolean active;
    private int observedMapId = Integer.MIN_VALUE;

    public synchronized long start() {
        if (active) {
            return sessionGeneration;
        }
        active = true;
        sessionGeneration++;
        actionGeneration++;
        observedMapId = Integer.MIN_VALUE;
        return sessionGeneration;
    }

    public synchronized void stop() {
        if (!active) {
            return;
        }
        active = false;
        sessionGeneration++;
        actionGeneration++;
        observedMapId = Integer.MIN_VALUE;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized long getSessionGeneration() {
        return sessionGeneration;
    }

    public synchronized long getActionGeneration() {
        return actionGeneration;
    }

    public synchronized boolean isCurrentSession(long token) {
        return active && token == sessionGeneration;
    }

    public synchronized boolean isCurrentAction(long sessionToken, long actionToken) {
        return isCurrentSession(sessionToken) && actionToken == actionGeneration;
    }

    public synchronized void invalidateAction() {
        actionGeneration++;
    }

    public synchronized void observeMap(int mapId) {
        if (observedMapId == mapId) {
            return;
        }
        observedMapId = mapId;
        actionGeneration++;
    }
}