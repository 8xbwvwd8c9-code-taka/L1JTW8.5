package auto.hunt;

import java.util.concurrent.ScheduledFuture;

public final class AutoHuntSession {
    public interface HostState {
        boolean isConnected();
        boolean isDead();
        boolean isTeleporting();
        int getMapId();
        void onTick();
    }

    public interface Scheduler {
        ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs);
    }

    private final AutoHuntLifecycle lifecycle;
    private final HostState host;
    private final Scheduler scheduler;
    private final long periodMs;
    private ScheduledFuture<?> future;

    public AutoHuntSession(AutoHuntLifecycle lifecycle, HostState host, Scheduler scheduler, long periodMs) {
        if (lifecycle == null || host == null || scheduler == null) {
            throw new NullPointerException();
        }
        if (periodMs <= 0L) {
            throw new IllegalArgumentException("periodMs must be positive");
        }
        this.lifecycle = lifecycle;
        this.host = host;
        this.scheduler = scheduler;
        this.periodMs = periodMs;
    }

    public synchronized long start() {
        if (isRunning()) {
            return lifecycle.getSessionGeneration();
        }
        final long token = lifecycle.start();
        ScheduledFuture<?> scheduled = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runTick(token);
            }
        }, 0L, periodMs);
        if (scheduled == null) {
            lifecycle.stop();
            throw new IllegalStateException("scheduler rejected auto-hunt session");
        }
        future = scheduled;
        return token;
    }

    public synchronized void stop() {
        stopCurrent(lifecycle.getSessionGeneration());
    }

    public synchronized boolean isRunning() {
        return lifecycle.isActive() && future != null && !future.isCancelled() && !future.isDone();
    }

    private void runTick(long token) {
        if (!lifecycle.isCurrentSession(token)) {
            return;
        }
        if (!host.isConnected() || host.isDead() || host.isTeleporting()) {
            stopCurrent(token);
            return;
        }
        lifecycle.observeMap(host.getMapId());
        if (!lifecycle.isCurrentSession(token)) {
            return;
        }
        host.onTick();
    }

    private synchronized void stopCurrent(long token) {
        if (!lifecycle.isCurrentSession(token)) {
            return;
        }
        lifecycle.stop();
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }
}
