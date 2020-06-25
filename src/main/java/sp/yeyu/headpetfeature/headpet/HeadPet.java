package sp.yeyu.headpetfeature.headpet;

import org.bukkit.plugin.java.JavaPlugin;
import sp.yeyu.headpetfeature.headpet.commands.Pet;

public final class HeadPet extends JavaPlugin {

    @Override
    public void onEnable() {
        DataManager.reloadConfigurationFile();
        getCommand("pet").setExecutor(new Pet());
        getServer().getPluginManager().registerEvents(new HeadUtils(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
