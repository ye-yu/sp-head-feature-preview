package sp.yeyu.headpetfeature.headpet;

import org.bukkit.plugin.java.JavaPlugin;
import sp.yeyu.headpetfeature.headpet.commands.SamplePet;

public final class HeadPet extends JavaPlugin {

    @Override
    public void onEnable() {
        DataManager.reloadConfigurationFile();
        getCommand("samplepet").setExecutor(new SamplePet());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
