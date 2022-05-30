package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Utils.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class PlayerQuit implements Listener {

    private final Main plugin;

    public PlayerQuit(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        if (!plugin.getConfig().getBoolean("Webhook.Enabled")) return;

        Webhook webhook = new Webhook(plugin.getConfig().getString("Webhook.URL"));

        webhook.setUsername("Lean");
        webhook.setContent(plugin.getConfig().getString("Webhook.PlayerQuit").replace("{PLAYER}", e.getPlayer().getDisplayName()));

        try {
            webhook.execute();
        } catch (MalformedURLException ex) {
            System.out.println("Invalid webhook URL");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
