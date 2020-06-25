package sp.yeyu.headpetfeature.headpet;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Collection;
import java.util.Random;

public class CommandUtils {
    private static final String AT_ALL = "@a";
    private static final String AT_SELF = "@s";
    private static final String AT_RANDOM = "@r";

    public static void parseCommand(String commandString, Player player) {
        if (commandString.contains(AT_ALL)) {
            executeForAll(commandString);
            return;
        } else if (commandString.contains(AT_RANDOM)) {
            final Random random = new Random(Instant.now().toEpochMilli());
            final Collection<? extends Player> online = Bukkit.getOnlinePlayers();
            player = (Player) online.toArray()[random.nextInt(online.size())];
        }
        final String command = commandString.replaceAll(String.format("(%s)|(%s)", AT_RANDOM, AT_SELF), player.getDisplayName());
        final ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
        try {
            Bukkit.dispatchCommand(cs, command);
            LogUtils.info(String.format("Executing command at player: %s, Command: %s", player.getDisplayName(), command));
        } catch (Exception e) {
            LogUtils.error(String.format("Cannot execute command at player: %s, Command: %s", player.getDisplayName(), command), e);
        }
    }

    private static void executeForAll(String commandString) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            final ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
            try {
                Bukkit.dispatchCommand(cs, commandString.replace(AT_ALL, onlinePlayer.getDisplayName()));
            } catch (CommandException e) {
                LogUtils.error(String.format("Cannot execute command at player: %s, Command: %s", onlinePlayer.getDisplayName(), commandString), e);
            }
        }
    }
}
