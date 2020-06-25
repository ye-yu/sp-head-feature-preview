package sp.yeyu.headpetfeature.headpet;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HeadUtils implements Listener {

    private static final String TEXTURE = "texture";
    private static final String COMMAND = "command";
    private static final String DEV_MODE = "devMode";
    private static final String DESC = "description";
    private static final String COLOR = "color";
    private static final String INDICATOR = String.format("%sUtility Pet", ChatColor.GRAY);

    public static ItemStack headFromBase64(String base64) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        //noinspection deprecation
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    public static void giveHeadFunction(final Player player, final String functionName) throws IllegalArgumentException, FileNotFoundException {
        final FileConfiguration headFunction = getHeadFunction(functionName);
        if (!DataManager.getBool(DataManager.Attrs.DEV_MODE) && headFunction.getBoolean(DEV_MODE)) {
            player.sendMessage(String.format("%sThe head %s%s %scan only be obtained in dev mode. Configure settings in %s%s%s.",
                    ChatColor.YELLOW,
                    ChatColor.AQUA,
                    functionName,
                    ChatColor.YELLOW,
                    ChatColor.AQUA,
                    DataManager.DEV_YML,
                    ChatColor.YELLOW
            ));
            return;
        }
        final ItemStack head = HeadUtils.headFromBase64(headFunction.getString(TEXTURE));
        final ItemMeta itemMeta = head.getItemMeta();
        final ChatColor displayColor = headFunction.getString(COLOR) == null ? ChatColor.LIGHT_PURPLE : ChatColor.getByChar(headFunction.getString(COLOR));
        itemMeta.setDisplayName(String.format("%s%s", displayColor, functionName));
        if (headFunction.getString(DESC) != null) {
            itemMeta.setLore(Lists.newArrayList(INDICATOR, headFunction.getString(DESC)));
        } else {
            itemMeta.setLore(Lists.newArrayList(INDICATOR));
        }
        head.setItemMeta(itemMeta);
        player.getInventory().addItem(head);
    }

    private static FileConfiguration getHeadFunction(final String functionName) throws IllegalArgumentException, FileNotFoundException {
        final File file = JavaPlugin.getProvidingPlugin(HeadPet.class).getDataFolder().toPath().resolve(functionName + ".yml").toFile();
        if (!file.exists())
            throw new FileNotFoundException(String.format("%s.yml does not exist in the plugin folder.", functionName));
        final YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        if (!validateHeadFunction(yaml))
            throw new IllegalArgumentException(String.format("Set values for %s, %s, %s, and %s (optional), %s (optional) correctly in %s.yml",
                    TEXTURE,
                    COMMAND,
                    DEV_MODE,
                    DESC,
                    COLOR,
                    functionName));
        return yaml;
    }

    private static boolean validateHeadFunction(final FileConfiguration fc) {
        return fc.getString(TEXTURE) != null && fc.getString(COMMAND) != null && fc.getString(DEV_MODE) != null;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack skull = player.getItemInHand();
        if (skull.getType() == Material.SKULL_ITEM) {
            final ItemMeta itemMeta = skull.getItemMeta();
            final List<String> lore = itemMeta.getLore();
            if (Objects.isNull(lore) || lore.isEmpty()) return;
            if (lore.get(0).equalsIgnoreCase(INDICATOR)) {
                final String functionName = ChatColor.stripColor(itemMeta.getDisplayName());
                LogUtils.info(String.format("Retrieving function %s.yml", functionName));
                try {
                    final FileConfiguration headFunction = getHeadFunction(functionName);
                    CommandUtils.parseCommand(headFunction.getString(COMMAND), player);
                } catch (FileNotFoundException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
                event.setCancelled(true);
            }
        }
    }
}
