package me.amelia.leancore;

import me.amelia.leancore.Commands.EnchantCommand;
import me.amelia.leancore.Commands.TabCompletion;
import me.amelia.leancore.Enchants.Manager;
import me.amelia.leancore.Listeners.*;
import me.amelia.leancore.Recipes.ChunkLoader;
import me.despical.commandframework.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public CommandFramework commands;

    public Manager enchantsManager;

    public void onEnable() {
        this.setupConfig();
        this.registerListeners();
        this.registerRecipes();

        this.commands = new CommandFramework(this);
        this.enchantsManager = new Manager(this);

        this.registerCommands();
    }

    public void onDisable() {}

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldUnload(), this);
    }

    public void setupConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public void registerRecipes() {
        new ChunkLoader(this).registerRecipe();
    }

    public void registerCommands() {
        new EnchantCommand(this);
        new TabCompletion(this);
    }

    public CommandFramework getCommands() {
        return this.commands;
    }

    public Manager getEnchantsManager() {
        return this.enchantsManager;
    }
}
