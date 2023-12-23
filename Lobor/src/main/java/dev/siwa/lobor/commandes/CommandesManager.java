package dev.siwa.lobor.commandes;

import dev.siwa.lobor.commandes.debug.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CommandesManager implements CommandExecutor {

    protected boolean modeDebug;
    protected CommandesDebugManager debugManager;


    public CommandesManager(boolean modeDebug) {
        this.modeDebug = modeDebug;
        AfficheurDebug.activer();

        if (isModeDebug()) {
            this.debugManager = new CommandesDebugManager();
        } else {
            this.debugManager = null;
        }

    }

    public boolean isModeDebug() {
        return this.modeDebug;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        AfficheurDebug.afficherMessage((Player) sender, "Passage dans le onCommand du manager principal de commandes");
        if (args.length != 0) {
            if (args[0].equals("-d")) {
                if (isModeDebug()) {
                    return this.debugManager.onCommand(sender, command, label, args);
                } else {
                    sender.sendMessage("[Lobor infos] : Le mode debug est désactivé !");
                    return false;
                }
            }
        }

        return false;
    }
}
