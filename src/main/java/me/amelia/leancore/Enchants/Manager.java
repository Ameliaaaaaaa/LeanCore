package me.amelia.leancore.Enchants;

import me.amelia.leancore.Enchants.Types.Propelling;
import me.amelia.leancore.Main;

public class Manager {
    private final Main plugin;

    private Propelling propelling;

    public Manager(Main plugin) {
        this.plugin = plugin;

        this.registerEnchants();
    }

    public void registerEnchants() {
        propelling = new Propelling(this.plugin);
    }

    public Propelling getPropelling() {
        return propelling;
    }
}
