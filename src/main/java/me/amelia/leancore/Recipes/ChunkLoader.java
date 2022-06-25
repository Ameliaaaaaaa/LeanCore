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
    private final Main plugin;

    private final ItemStack customItem;

    public ChunkLoader(Main plugin) {
        this.plugin = plugin;

        this.customItem = new ItemStack(Material.BEACON, 1);

        ItemMeta itemMeta = this.customItem.getItemMeta();

        itemMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + "Chunk Loader");
        itemMeta.setLore(List.of("" + ChatColor.LIGHT_PURPLE + "Keep Chunks Loaded"));

        this.customItem.setItemMeta(itemMeta);

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void registerRecipe() {
        NamespacedKey key = new NamespacedKey(this.plugin, "chunk_loader");

        ShapedRecipe recipe = new ShapedRecipe(key, this.customItem);

        recipe.shape("III", "IBI", "III");

        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BEACON);

        this.plugin.getServer().addRecipe(recipe);
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(this.customItem);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = this.plugin.getConfig().getBoolean("" + event.getBlock().getChunk().getX() + "_" + event.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            this.plugin.getConfig().set("" + event.getBlock().getChunk().getX() + "_" + event.getBlock().getChunk().getZ(), null);
            this.plugin.saveConfig();

            event.setDropItems(false);

            int x = event.getBlock().getChunk().getX();
            int z = event.getBlock().getChunk().getZ();

            Bukkit.getServer().getWorld("world").setChunkForceLoaded(x, z, false);

            this.giveItem(event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.BEACON) && event.getItemInHand().getItemMeta().getDisplayName().equals("" + ChatColor.LIGHT_PURPLE + "Chunk Loader")) {
            boolean isChunkLoader = this.plugin.getConfig().getBoolean("" + event.getBlock().getChunk().getX() + "_" + event.getBlock().getChunk().getZ());

            if (isChunkLoader) {
                event.getPlayer().sendMessage("" + ChatColor.RED + "There is already a chunk loader in this chunk.");

                event.setCancelled(true);
            } else {
                this.plugin.getConfig().set("" + event.getBlockPlaced().getChunk().getX() + "_" + event.getBlockPlaced().getChunk().getZ(), Boolean.valueOf(true));
                this.plugin.saveConfig();

                Bukkit.getServer().getWorld("world").loadChunk(event.getBlockPlaced().getChunk().getX(), event.getBlockPlaced().getChunk().getZ());
                Bukkit.getServer().getWorld("world").setChunkForceLoaded(event.getBlockPlaced().getChunk().getX(), event.getBlockPlaced().getChunk().getZ(), true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (event.getClickedBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = this.plugin.getConfig().getBoolean("" + event.getClickedBlock().getChunk().getX() + "_" + event.getClickedBlock().getChunk().getZ());

            if (isChunkLoader) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL && event.getSlotType() == InventoryType.SlotType.RESULT) {
            if (event.getCurrentItem().getType().equals(Material.BEACON)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (event.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = this.plugin.getConfig().getBoolean("" + event.getBlock().getChunk().getX() + "_" + event.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (event.getBlock().getType().equals(Material.BEACON)) {
            boolean isChunkLoader = this.plugin.getConfig().getBoolean("" + event.getBlock().getChunk().getX() + "_" + event.getBlock().getChunk().getZ());

            if (!isChunkLoader) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : (Block[]) event.blockList().toArray((Object[])new Block[event.blockList().size()])) {
            if (block.getType() == Material.BEACON) event.blockList().remove(block);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getBlock().getType().equals(Material.BEACON)) event.setCancelled(true);
    }
}
