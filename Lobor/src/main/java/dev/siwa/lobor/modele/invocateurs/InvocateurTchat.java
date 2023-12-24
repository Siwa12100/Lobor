package dev.siwa.lobor.modele.invocateurs;

import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvocateurTchat extends InvocateurClassique implements IInvocateur, CommandExecutor {


    public InvocateurTchat(MonturesManager manager) {
        super(manager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(args.length > 1)) {
            return false;
        }

        Player joueur = (Player) sender;

        if (args[0].equals("-i")) {

            switch(args[1]) {

                case "getCheval" :
                    this.getCheval(joueur);
                    break;

                case "removeCheval" :
                    this.removeCheval((Player) sender);
                    break;

                default:
                    return false;
            }
        }
        return true;
    }
}
