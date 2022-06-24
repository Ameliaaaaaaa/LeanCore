package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Utils.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoin implements Listener {

    private final Main plugin;

    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!plugin.getConfig().getBoolean("Webhook.Enabled")) return;

        Webhook webhook = new Webhook(plugin.getConfig().getString("Webhook.URL"));

        webhook.setUsername("Lean");
        webhook.setContent(plugin.getConfig().getString("Webhook.PlayerJoin").replace("{PLAYER}", e.getPlayer().getDisplayName()));

        try {
            webhook.execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
