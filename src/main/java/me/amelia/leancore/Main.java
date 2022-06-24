package me.amelia.leancore;

import me.amelia.leancore.Listeners.*;
import me.amelia.leancore.Recipes.ChunkLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        setupConfig();
        registerListeners();
        registerRecipes();
    }

    public void onDisable() {}

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new WorldUnload(), this);
    }

    public void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void registerRecipes() {
        new ChunkLoader(this).registerRecipe();
    }
}
