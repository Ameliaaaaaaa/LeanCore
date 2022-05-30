package me.amelia.leancore.Recipes;

import me.amelia.leancore.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChunkLoader {
    Main plugin;

    ItemStack customItem;

    public ChunkLoader(Main plugin) {
        this.plugin = plugin;

        customItem = new ItemStack(Material.BEACON, 1);

        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + "Chunk Loader");
        itemMeta.setLore(List.of("" + ChatColor.LIGHT_PURPLE + "Keep Chunks Loaded"));

        customItem.setItemMeta(itemMeta);
    }

    public void registerRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "chunk_loader");

        ShapedRecipe recipe = new ShapedRecipe(key, customItem);

        recipe.shape("III", "IBI", "III");

        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BEACON);

        plugin.getServer().addRecipe(recipe);
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(customItem);
    }
}
