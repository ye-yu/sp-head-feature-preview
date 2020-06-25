package sp.yeyu.headpetfeature.headpet.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sp.yeyu.headpetfeature.headpet.DataManager;
import sp.yeyu.headpetfeature.headpet.HeadUtils;

public class SamplePet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!DataManager.getBool(DataManager.Attrs.DEV_MODE)) {
            sender.sendMessage(String.format("%sDev mode is off. Please set it in [%s%s%s].",
                    ChatColor.GOLD,
                    ChatColor.AQUA,
                    DataManager.DEV_YML,
                    ChatColor.GOLD));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(String.format("%sOnly players can execute this command.", ChatColor.GOLD));
            return true;
        }

        final String sampleBase64 = "ewogICJ0aW1lc3RhbXAiIDogMTU5MzA3MjMyMjU1OSwKICAicHJvZmlsZUlkIiA6ICJkOTRlYTlhODMzMzY0NmM2OTAwYTY1YzlmYWFmNzk0OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJwbGFuY18iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VmMDQwMzMxYTAwYzQ0Nzg0YzM5ODFjYTgxMjkyODY5YmVlYTg3YjhlMjFmMTYzN2Q0OWQ4NjBhM2I2NTQwZCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
        final ItemStack head = HeadUtils.headFromBase64(sampleBase64);
        final Player player = (Player) sender;
        player.getInventory().addItem(head);
        return true;
    }
}
