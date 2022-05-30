package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockPiston implements Listener {
    private final Main plugin;

    public BlockPiston(Main plugin) {
        this.plugin = plugin;
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
}
