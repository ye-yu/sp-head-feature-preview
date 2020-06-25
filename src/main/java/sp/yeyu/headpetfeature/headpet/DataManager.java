package sp.yeyu.headpetfeature.headpet;

import com.google.common.base.CaseFormat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class DataManager {
    public static final String DEV_YML = "dev.yml";
    private static final File FILE = JavaPlugin
            .getProvidingPlugin(HeadPet.class)
            .getDataFolder()
            .toPath()
            .resolve(DEV_YML)
            .toFile();
    private static FileConfiguration fc = YamlConfiguration.loadConfiguration(FILE);

    static {
        LogUtils.forceInfo(String.format("Loading configuration file from: %s", FILE.toURI().toASCIIString()));
        initBool(Attrs.DEV_MODE, false);
        initBool(Attrs.LOG_DEBUG, true);
    }

    public static void reloadConfigurationFile() {
        fc = YamlConfiguration.loadConfiguration(FILE);
    }

    private static void initBool(Attrs attr, boolean defValue) {
        if (!fc.getKeys(true).contains(attr.constructName())) {
            fc.set(attr.constructName(), defValue);
            LogUtils.forceInfo(String.format("Saving %s to %s", attr, defValue));
            try {
                fc.save(FILE);
            } catch (IOException e) {
                LogUtils.error(String.format("Cannot access %s", FILE.toURI().toASCIIString()), e);
            }
        }
    }

    public static boolean getBool(Attrs attr) {
        return fc.getBoolean(attr.constructName());
    }

    public enum Attrs {
        DEV_MODE, LOG_DEBUG;

        public String constructName() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.toString());
        }
    }
}
