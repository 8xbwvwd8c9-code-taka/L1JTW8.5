import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Starts the normal 850 server with the legacy packet trace gate enabled.
 *
 * This class deliberately uses reflection so it has no compile-time dependency
 * on recovered server sources. Normal server startup is unchanged; the debug
 * gate is enabled only when this bootstrap is used.
 */
public final class PacketDebugBootstrap {
    private PacketDebugBootstrap() {
    }

    public static void main(String[] args) throws Exception {
        Class<?> configClass = Class.forName("l1j.server.Config");
        Field debugField = configClass.getField("d");

        boolean previous = debugField.getBoolean(null);
        debugField.setBoolean(null, true);
        System.out.println("[850_PACKET_DEBUG] Config.d: " + previous + " -> true");

        Class<?> serverClass = Class.forName("l1j.server.Server");
        Method main = serverClass.getMethod("main", String[].class);

        try {
            main.invoke(null, (Object) args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw e;
        }
    }
}
