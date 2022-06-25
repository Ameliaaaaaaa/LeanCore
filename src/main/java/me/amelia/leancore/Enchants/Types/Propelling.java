/*
This was inspired (taken) from https://github.com/Flo56958/MineTinker
 */

package me.amelia.leancore.Enchants.Types;

import me.amelia.leancore.Enchants.Enchant;
import me.amelia.leancore.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class Propelling extends Enchant implements Listener {
    private final Main plugin;

    private final HashMap<String, Long> cooldowns = new HashMap<>();

    int cooldown = 5;
    int durabilityLoss = 10;
    double speedPerLevel = 0.05;

    public Propelling(Main plugin) {
        super(plugin, "Propelling", List.of(Material.ELYTRA));

        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event ) {
        Player player = event.getPlayer();

        if (player.isSneaking()) return;
        if (!player.isGliding()) return;

        ItemStack elytra = player.getInventory().getChestplate();

        if (elytra == null || elytra.getType() != Material.ELYTRA) return;

        // Doesn't actually work yet. I need to actually do the Lore stuff first.
        // if (!this.hasEnchant(elytra)) return;

        if (cooldown > 0) {
            long time = System.currentTimeMillis();

            Long playerTime = this.cooldowns.get(player.getUniqueId().toString());

            if (playerTime != null) {
                if (time - playerTime < this.cooldown * 1000L) return;

                this.cooldowns.remove(player.getUniqueId().toString());
            }
        }

        int maxDurability = elytra.getType().getMaxDurability();

        ItemMeta itemMeta = elytra.getItemMeta();

        if (itemMeta instanceof Damageable damageable && !itemMeta.isUnbreakable() && player.getGameMode() == GameMode.SURVIVAL) {
            int loss = durabilityLoss;

            if (maxDurability <= damageable.getDamage() + loss + 1) {
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5F, 0.5F);

                return;
            }

            damageable.setDamage(damageable.getDamage() + loss);

            elytra.setItemMeta(itemMeta);
        }

        int level = 1;

        Location loc = player.getLocation();

        Vector dir = loc.getDirection().normalize();

        player.setVelocity(player.getVelocity().add(dir.multiply(1 + speedPerLevel * level)));

        if (loc.getWorld() != null) loc.getWorld().spawnParticle(Particle.CLOUD, loc, 30, 0.5F, 0.5F, 0.5F, 0.0F);

        player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5F, 0.5F);

        if (cooldown > 0) this.cooldowns.put(player.getUniqueId().toString(), System.currentTimeMillis());
    }
}
