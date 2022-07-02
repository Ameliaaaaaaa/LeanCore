package me.amelia.leancore.GUIs;

import me.amelia.leancore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EnchantsGUI implements Listener {
    // Todo - Pull enchants automatically.
    private final Main plugin;

    private final Inventory inventory;

    public EnchantsGUI(Main plugin) {
        this.plugin = plugin;

        this.inventory = Bukkit.createInventory(null, 27, ChatColor.LIGHT_PURPLE + "Enchants GUI");

        this.initializeItems();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void initializeItems() {
        this.inventory.setItem(13, createGuiItem(Material.ELYTRA, "Propelling", "Propelling Enchant."));

        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.inventory.getItem(i) == null) this.inventory.setItem(i, new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1, (short) 6));
        }
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);

        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!event.getInventory().equals(this.inventory)) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getRawSlot() == 13) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (itemStack.getType() == Material.ELYTRA) {
                this.plugin.getEnchantsManager().getPropelling().applyEnchant(itemStack);
            } else {
                player.sendMessage(ChatColor.GOLD + "Propelling" + ChatColor.RED + " cannot be added to " + ChatColor.GOLD + itemStack.getType().name() + ChatColor.RED + ".");
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event) {
        if (event.getInventory().equals(inventory)) event.setCancelled(true);
    }
}
