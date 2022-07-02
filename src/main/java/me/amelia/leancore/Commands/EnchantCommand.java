package me.amelia.leancore.Commands;

import me.amelia.leancore.Main;
import me.despical.commandframework.CommandArguments;

public class EnchantCommand {
    private final Main plugin;

    public EnchantCommand(Main plugin) {
        this.plugin = plugin;

        this.plugin.getCommands().registerCommands(this);
    }

    @me.despical.commandframework.Command(
            name = "leancore.enchant",
            desc = "Enchant GUI.",
            senderType = me.despical.commandframework.Command.SenderType.PLAYER,
            permission = "leancore.enchant",
            usage = "/leancore enchant"
    )
    public void mainCommand(CommandArguments arguments) {
        this.plugin.getEnchantsGUI().openInventory(arguments.getSender());
    }

}