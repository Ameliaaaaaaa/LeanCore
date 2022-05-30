package me.amelia.leancore.Recipes;

import me.amelia.leancore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChunkLoader implements Listener {
    Main plugin;

    ItemStack customItem;

    public ChunkLoader(Main plugin) {
        this.plugin = plugin;

        customItem = new ItemStack(Material.BEACON, 1);

        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + "Chunk Loader");
        itemMeta.setLore(List.of("" + ChatColor.LIGHT_PURPLE + "Keep Chunks Loaded"));

        customItem.setItemMeta(itemMeta);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = plugin.getConfig().getBoolean("" + e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            plugin.getConfig().set("" + e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ(), null);
            plugin.saveConfig();

            e.setDropItems(false);

            int x = e.getBlock().getChunk().getX();
            int z = e.getBlock().getChunk().getZ();

            Bukkit.getServer().getWorld("world").setChunkForceLoaded(x, z, false);

            giveItem(e.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON) && e.getItemInHand().getItemMeta().getDisplayName().equals("" + ChatColor.LIGHT_PURPLE + "Chunk Loader")) {
            boolean isChunkLoader = plugin.getConfig().getBoolean("" + e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ());

            if (isChunkLoader) {
                e.getPlayer().sendMessage("" + ChatColor.RED + "There is already a chunk loader in this chunk.");

                e.setCancelled(true);
            } else {
                plugin.getConfig().set("" + e.getBlockPlaced().getChunk().getX() + "_" + e.getBlockPlaced().getChunk().getZ(), Boolean.valueOf(true));
                plugin.saveConfig();

                Bukkit.getServer().getWorld("world").loadChunk(e.getBlockPlaced().getChunk().getX(), e.getBlockPlaced().getChunk().getZ());
                Bukkit.getServer().getWorld("world").setChunkForceLoaded(e.getBlockPlaced().getChunk().getX(), e.getBlockPlaced().getChunk().getZ(), true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (e.getClickedBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = plugin.getConfig().getBoolean("" + e.getClickedBlock().getChunk().getX() + "_" + e.getClickedBlock().getChunk().getZ());

            if (isChunkLoader) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL && e.getSlotType() == InventoryType.SlotType.RESULT) {
            if (e.getCurrentItem().getType().equals(Material.BEACON)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = plugin.getConfig().getBoolean("" + e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = plugin.getConfig().getBoolean("" + e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block block : (Block[])e.blockList().toArray((Object[])new Block[e.blockList().size()])) {
            if (block.getType() == Material.BEACON) e.blockList().remove(block);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) e.setCancelled(true);
    }
}
