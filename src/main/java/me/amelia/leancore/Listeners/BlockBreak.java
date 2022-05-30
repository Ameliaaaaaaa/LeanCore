package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Recipes.ChunkLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    private final Main plugin;

    public BlockBreak(Main plugin) {
        this.plugin = plugin;
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

            ChunkLoader chunkLoader = new ChunkLoader(plugin);

            chunkLoader.giveItem(e.getPlayer());
        }
    }
}
