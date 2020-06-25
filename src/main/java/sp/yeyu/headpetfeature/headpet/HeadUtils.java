package sp.yeyu.headpetfeature.headpet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class HeadUtils {
    public static ItemStack headFromBase64(String base64) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        //noinspection deprecation
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }
}
