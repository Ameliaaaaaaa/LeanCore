/*
This was inspired (taken) from https://github.com/Flo56958/MineTinker
 */

package me.amelia.leancore.Enchants;

import me.amelia.leancore.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class Enchant {
    private final Main plugin;

    private final String name;

    private final List<Material> allowedItems;

    public Enchant(Main plugin, String name, List<Material> allowedItems) {
        this.plugin = plugin;
        this.name = name;
        this.allowedItems = allowedItems;
    }

    public String getName() {
        return this.name;
    }

    public List<Material> getAllowedItems() {
        return this.allowedItems;
    }

    public boolean applyEnchant(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(Arrays.asList(this.getName()));

        itemStack.setItemMeta(itemMeta);

        return true;
    }

    public boolean hasEnchant(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return false;

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        return container.has(new NamespacedKey(this.plugin, this.getName()), PersistentDataType.INTEGER);
    }
}
