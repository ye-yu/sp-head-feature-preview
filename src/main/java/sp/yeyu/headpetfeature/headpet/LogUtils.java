package sp.yeyu.headpetfeature.headpet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class LogUtils {

    private static final Logger LOGGER = LogManager.getLogger();

    private static String getLastCallingClass() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final int lookback = 4;
        final String className = stackTrace.length < lookback ? HeadPet.class.toString() : stackTrace[lookback - 1].getClassName();
        final String[] split = className.split("\\.");
        return split[split.length - 1];
    }

    public static void info(String msg) {
        if (!DataManager.getBool(DataManager.Attrs.LOG_DEBUG)) return;
        LOGGER.info(String.format("[%s] (%s) %s", JavaPlugin.getProvidingPlugin(HeadPet.class).getName(), getLastCallingClass(), msg));
    }

    public static void forceInfo(String msg) {
        LOGGER.info(String.format("(%s) %s", getLastCallingClass(), msg));
    }

    public static void error(String msg, Exception e) {
        LOGGER.info(String.format("[%s] (%s) %s", JavaPlugin.getProvidingPlugin(HeadPet.class).getName(), getLastCallingClass(), msg), e);
    }
}
