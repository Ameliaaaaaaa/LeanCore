package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Utils.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerQuit implements Listener {
    private final Main plugin;

    public PlayerQuit(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        if (!this.plugin.getConfig().getBoolean("Webhook.Enabled")) return;

        Webhook webhook = new Webhook(this.plugin.getConfig().getString("Webhook.URL"));

        webhook.setUsername("Lean");
        webhook.setContent(this.plugin.getConfig().getString("Webhook.PlayerQuit").replace("{PLAYER}", event.getPlayer().getDisplayName()));

        try {
            webhook.execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
