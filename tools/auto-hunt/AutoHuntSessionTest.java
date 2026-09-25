import auto.hunt.AutoHuntLifecycle;
import auto.hunt.AutoHuntSession;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class AutoHuntSessionTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static final class Host implements AutoHuntSession.HostState {
        boolean connected = true;
        boolean dead;
        boolean teleporting;
        int mapId = 4;
        int ticks;
        public boolean isConnected() { return connected; }
        public boolean isDead() { return dead; }
        public boolean isTeleporting() { return teleporting; }
        public int getMapId() { return mapId; }
        public void onTick() { ticks++; }
    }

    private static final class FakeFuture implements ScheduledFuture<Object> {
        boolean cancelled;
        public boolean cancel(boolean mayInterruptIfRunning) { cancelled = true; return true; }
        public boolean isCancelled() { return cancelled; }
        public boolean isDone() { return cancelled; }
        public Object get() { return null; }
        public Object get(long timeout, TimeUnit unit) { return null; }
        public long getDelay(TimeUnit unit) { return 0; }
        public int compareTo(Delayed other) { return 0; }
    }

    private static final class Scheduler implements AutoHuntSession.Scheduler {
        int schedules;
        Runnable lastTask;
        FakeFuture lastFuture;
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs) {
            schedules++;
            lastTask = task;
            lastFuture = new FakeFuture();
            return lastFuture;
        }
    }

    public static void main(String[] args) {
        AutoHuntLifecycle lifecycle = new AutoHuntLifecycle();
        Host host = new Host();
        Scheduler scheduler = new Scheduler();
        AutoHuntSession session = new AutoHuntSession(lifecycle, host, scheduler, 200L);

        long g1 = session.start();
        check(session.start() == g1, "duplicate start must keep same generation");
        check(scheduler.schedules == 1, "duplicate start must not schedule twice");

        scheduler.lastTask.run();
        check(host.ticks == 1, "valid host state must execute one tick");

        long a1 = lifecycle.getActionGeneration();
        host.mapId = 69;
        scheduler.lastTask.run();
        check(host.ticks == 2, "map change alone must not stop session");
        check(lifecycle.getActionGeneration() > a1, "map change must invalidate delayed actions");

        host.dead = true;
        scheduler.lastTask.run();
        check(!session.isRunning(), "dead host must stop session");
        check(scheduler.lastFuture.cancelled, "dead host must cancel owned future");

        host.dead = false;
        long g2 = session.start();
        Runnable stale = scheduler.lastTask;
        check(g2 > g1, "restart must have newer generation");
        session.stop();
        long g3 = session.start();
        int ticksBeforeStale = host.ticks;
        stale.run();
        check(session.isRunning(), "stale task must not stop newer session");
        check(host.ticks == ticksBeforeStale, "stale task must not execute newer-session tick");
        check(lifecycle.isCurrentSession(g3), "newest generation must remain current");

        host.connected = false;
        scheduler.lastTask.run();
        check(!session.isRunning(), "disconnect must stop session");

        host.connected = true;
        session.start();
        host.teleporting = true;
        scheduler.lastTask.run();
        check(!session.isRunning(), "teleporting host must stop session");

        System.out.println("AUTO_HUNT_SESSION_TEST=PASS");
    }
}
