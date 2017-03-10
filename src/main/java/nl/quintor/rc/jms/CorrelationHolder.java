package nl.quintor.rc.jms;

public class CorrelationHolder {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static void set(final String correlationId) {
        THREAD_LOCAL.set(correlationId);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
