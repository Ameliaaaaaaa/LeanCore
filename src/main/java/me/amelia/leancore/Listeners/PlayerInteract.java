package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    private final Main plugin;

    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
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
}
