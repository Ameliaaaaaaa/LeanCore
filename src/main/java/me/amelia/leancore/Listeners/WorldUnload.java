package me.amelia.leancore.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldUnload implements Listener {

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        event.setCancelled(true);
    }

}
