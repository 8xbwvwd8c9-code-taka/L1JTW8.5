package auto.hunt;

/**
 * Identity-based target ownership for one auto-hunt session.
 * Replacing or clearing a target invalidates delayed move/attack work.
 */
public final class AutoHuntTargetState<T> {
    private final AutoHuntLifecycle lifecycle;
    private T current;

    public AutoHuntTargetState(AutoHuntLifecycle lifecycle) {
        if (lifecycle == null) throw new NullPointerException("lifecycle");
        this.lifecycle = lifecycle;
    }

    public synchronized T current() {
        return current;
    }

    public synchronized boolean replace(T next) {
        if (current == next) {
            return false;
        }
        current = next;
        lifecycle.invalidateAction();
        return true;
    }

    public synchronized boolean clear() {
        return replace(null);
    }
}
