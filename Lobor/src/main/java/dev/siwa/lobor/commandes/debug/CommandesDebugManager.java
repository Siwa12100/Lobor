package dev.siwa.lobor.commandes.debug;

import dev.siwa.lobor.affichage.AfficheurTchat;
import dev.siwa.lobor.modele.boutons.BoutonSelle;
import dev.siwa.lobor.modele.montures.MontureCheval;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import dev.siwa.lobor.affichage.AfficheurDebug;

public class CommandesDebugManager implements CommandExecutor {

    private MontureCheval monture;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length <= 1) {
            return false;
        }
        //AfficheurDebug.afficherMessage((Player)sender, "Passage dans le debug manager et args.length > 1");
        Player p = (Player) sender;

        switch (args[1]) {
            case "creerCheval" :
                this.monture = new MontureCheval(p);
                break;

            case "supprimerCheval" :
                if (this.monture != null) {
                    this.monture.supprimer();
                }
                this.monture = null;
                break;

            case "immobiliserCheval" :
                if (this.monture != null) {
                    this.monture.immobiliser();
                }
                break;

            case "rendreChevalMobile" :
                if (this.monture != null) {
                    this.monture.rendreMobile();
                }
                break;

            case "faireMonterCheval" :
                if (this.monture != null) {
                    monture.faireMonterJoueur();
                }
                break;

            case "getBoutonSelle" :
                BoutonSelle.getBoutonSelle(p);
                break;

            case "removeBoutonSelle" :
                BoutonSelle.removeBoutonSelle(p);
                break;

            case "isSelleLa" :
                AfficheurTchat.afficherMessage(p, "La selle est absence : " + BoutonSelle.isBoutonSelleAbsent(p));
                break;

            default:
                return false;
        }
        return true;
    }
}
