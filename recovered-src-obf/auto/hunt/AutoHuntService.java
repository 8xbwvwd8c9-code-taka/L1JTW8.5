package auto.hunt;

import ap.u;
import java.util.HashMap;
import java.util.Map;

public final class AutoHuntService {
    private static final long DEFAULT_PERIOD_MS = 200L;
    private static final Object LOCK = new Object();
    private static final Map<Integer, Entry> SESSIONS = new HashMap<Integer, Entry>();

    private AutoHuntService() {
    }

    public static long start(u pc) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        synchronized (LOCK) {
            int objId = pc.fr();
            Entry current = SESSIONS.get(objId);
            if (current != null) {
                if (current.pc == pc && current.session.isRunning()) {
                    return current.session.start();
                }
                current.session.stop();
                SESSIONS.remove(objId);
            }
            AutoHunt850Session session = new AutoHunt850Session(pc, DEFAULT_PERIOD_MS);
            Entry created = new Entry(pc, session);
            SESSIONS.put(objId, created);
            try {
                return session.start();
            }
            catch (RuntimeException ex) {
                SESSIONS.remove(objId);
                throw ex;
            }
        }
    }

    public static void stop(u pc) {
        if (pc == null) {
            return;
        }
        synchronized (LOCK) {
            int objId = pc.fr();
            Entry current = SESSIONS.get(objId);
            if (current == null || current.pc != pc) {
                return;
            }
            SESSIONS.remove(objId);
            current.session.stop();
        }
    }

    public static boolean isRunning(u pc) {
        if (pc == null) {
            return false;
        }
        synchronized (LOCK) {
            Entry current = SESSIONS.get(pc.fr());
            return current != null && current.pc == pc && current.session.isRunning();
        }
    }

    private static final class Entry {
        private final u pc;
        private final AutoHunt850Session session;

        private Entry(u pc, AutoHunt850Session session) {
            this.pc = pc;
            this.session = session;
        }
    }
}
