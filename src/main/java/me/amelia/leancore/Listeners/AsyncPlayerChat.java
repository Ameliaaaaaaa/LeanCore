package me.amelia.leancore.Listeners;

import me.amelia.leancore.Main;
import me.amelia.leancore.Utils.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class AsyncPlayerChat implements Listener {
    private final Main plugin;

    public AsyncPlayerChat(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!this.plugin.getConfig().getBoolean("Webhook.Enabled")) return;

        Webhook webhook = new Webhook(this.plugin.getConfig().getString("Webhook.URL"));

        String message = this.plugin.getConfig().getString("Webhook.Message").replace("{PLAYER}", event.getPlayer().getDisplayName());

        message = message.replace("{MESSAGE}", event.getMessage());

        webhook.setUsername("Lean");
        webhook.setContent(message);

        try {
            webhook.execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
