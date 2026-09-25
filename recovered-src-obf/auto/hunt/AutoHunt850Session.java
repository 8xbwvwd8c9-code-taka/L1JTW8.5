package auto.hunt;

import ap.u;
import java.util.concurrent.ScheduledFuture;

public final class AutoHunt850Session {
    private final AutoHuntLifecycle lifecycle;
    private final AutoHuntSession session;

    public AutoHunt850Session(final u pc, long periodMs) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        this.lifecycle = new AutoHuntLifecycle();
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
                    // Phase-1 host gate only. Combat/move/skill logic is added later.
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
    }

    public boolean isRunning() {
        return session.isRunning();
    }

    public long getSessionGeneration() {
        return lifecycle.getSessionGeneration();
    }

    public long getActionGeneration() {
        return lifecycle.getActionGeneration();
    }
}
