package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    private final Main plugin;

    public BlockPlace(Main plugin) {
        this.plugin = plugin;
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
}
