package dev.siwa.lobor;

import org.bukkit.plugin.java.JavaPlugin;
import dev.siwa.lobor.commandes.CommandesManager;

import java.util.Objects;

public final class Lobor extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //getCommand("lobor").setExecutor(new CommandesManager(true));
        Objects.requireNonNull(getCommand("lobor")).setExecutor(new CommandesManager(true));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
