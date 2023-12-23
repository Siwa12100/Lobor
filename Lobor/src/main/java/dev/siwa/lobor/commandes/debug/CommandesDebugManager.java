package dev.siwa.lobor.commandes.debug;

import dev.siwa.lobor.modele.montures.MontureCheval;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandesDebugManager implements CommandExecutor {

    private MontureCheval monture;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length <= 1) {
            return  false;
        }

        AfficheurDebug.afficherMessage((Player)sender, "Passage dans le debug manager et args.length > 1");

        Player p = (Player) sender;
        //MontureCheval monture = null;

        switch (args[1]) {

            case "creerCheval" :
                // afficheurDebug.afficherMessage((Player)sender, "Passage dans le case creerCheval");
                this.monture = new MontureCheval(p);
                // afficheurDebug.afficherMessage((Player)sender, "Cheval créé ! ");
                break;

            case "supprimerCheval" :
                // afficheurDebug.afficherMessage((Player)sender, "debut de la suppresion du cheval ");
                if (this.monture != null) {
                    this.monture.supprimer();
                    // afficheurDebug.afficherMessage((Player)sender, "Cheval supprimé ! ");
                }
                this.monture = null;
                break;

            case "immobiliserCheval" :
                // afficheurDebug.afficherMessage((Player)sender, "debut de l'immobilisation du cheval ");
                if (this.monture != null) {
                    this.monture.immobiliser();
                    // afficheurDebug.afficherMessage((Player)sender, "Cheval immobilisé ! ");
                }
                break;

            case "rendreChevalMobile" :
                // afficheurDebug.afficherMessage((Player)sender, "debut du rendre mobile cheval  ");
                if (this.monture != null) {
                    this.monture.rendreMobile();
                    // afficheurDebug.afficherMessage((Player)sender, "Cheval rendu mobile  ! ");
                }
                break;

            case "faireMonterCheval" :
                if (this.monture != null) {
                    // afficheurDebug.afficherMessage((Player)sender, "debut du faire monter cheval  ");
                    monture.faireMonterJoueur();
                    // afficheurDebug.afficherMessage((Player)sender, "joueur sur le cheval !");
                }

            default:
                return false;
        }
        return true;
    }
}
