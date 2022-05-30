package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Utils.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class AsyncPlayerChat implements Listener {

    private final Main plugin;

    public AsyncPlayerChat(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (!plugin.getConfig().getBoolean("Webhook.Enabled")) return;

        Webhook webhook = new Webhook(plugin.getConfig().getString("Webhook.URL"));

        String message = plugin.getConfig().getString("Webhook.Message").replace("{PLAYER}", e.getPlayer().getDisplayName());

        message = message.replace("{MESSAGE}", e.getMessage());

        webhook.setUsername("Lean");
        webhook.setContent(message);

        try {
            webhook.execute();
        } catch (MalformedURLException ex) {
            System.out.println("Invalid webhook URL");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
