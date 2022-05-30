package me.amelia.leancore.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockExplode implements Listener {
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
