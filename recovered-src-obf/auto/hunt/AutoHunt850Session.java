package auto.hunt;

import ap.s;
import ap.u;
import java.util.concurrent.ScheduledFuture;

public final class AutoHunt850Session {
    public interface TargetProvider {
        s select();
    }

    private final AutoHuntLifecycle lifecycle;
    private final AutoHuntTargetState<s> targetState;
    private final AutoHuntSession session;

    public AutoHunt850Session(final u pc, long periodMs) {
        this(pc, periodMs, null);
    }

    public AutoHunt850Session(final u pc, long periodMs, final TargetProvider targetProvider) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        this.lifecycle = new AutoHuntLifecycle();
        this.targetState = new AutoHuntTargetState<s>(lifecycle);
        this.session = new AutoHuntSession(
            lifecycle,
            new AutoHuntSession.HostState() {
                @Override
                public boolean isConnected() {
                    return pc.aK() != null;
                }

                @Override
                public boolean isDead() {
                    return pc.eX() || pc.ea() <= 0;
                }

                @Override
                public boolean isTeleporting() {
                    return pc.aR();
                }

                @Override
                public int getMapId() {
                    return pc.fp();
                }

                @Override
                public void onTick() {
                    if (targetProvider != null) {
                        targetState.replace(targetProvider.select());
                    }
                }
            },
            new AutoHuntSession.Scheduler() {
                @Override
                public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs) {
                    return bi.e.a().b(task, initialDelayMs, periodMs);
                }
            },
            periodMs
        );
    }

    public long start() {
        return session.start();
    }

    public void stop() {
        session.stop();
        targetState.clear();
    }

    public boolean isRunning() {
        return session.isRunning();
    }

    public s currentTarget() {
        return targetState.current();
    }

    public long getSessionGeneration() {
        return lifecycle.getSessionGeneration();
    }

    public long getActionGeneration() {
        return lifecycle.getActionGeneration();
    }
}