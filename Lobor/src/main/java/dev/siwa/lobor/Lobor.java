package dev.siwa.lobor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import dev.siwa.lobor.commands.giveHorse;

public final class Lobor extends JavaPlugin {

    private static Lobor instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        System.out.println("(Temporaire Lobor) : Activation du plugin lobor. \n");
        Bukkit.getServer().getPluginManager().registerEvents(new MainListener(), this);
        Bukkit.getServer().getPluginCommand("givehorse").setExecutor(new giveHorse());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
