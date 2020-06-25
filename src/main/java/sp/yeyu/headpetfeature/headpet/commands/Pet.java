package sp.yeyu.headpetfeature.headpet.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sp.yeyu.headpetfeature.headpet.DataManager;
import sp.yeyu.headpetfeature.headpet.HeadUtils;
import sp.yeyu.headpetfeature.headpet.LogUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Pet implements CommandExecutor {

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
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        try {
            HeadUtils.giveHeadFunction((Player) sender, args.length == 0 ? "Sample" : Arrays.stream(args).map(StringUtils::capitalize).collect(Collectors.joining(" ")));
        } catch (FileNotFoundException | IllegalArgumentException e) {
            sender.sendMessage(String.format("%sError occurred. Refer to console log.", ChatColor.RED));
            LogUtils.error(e.getMessage(), e);
        }
        return true;
    }
}
